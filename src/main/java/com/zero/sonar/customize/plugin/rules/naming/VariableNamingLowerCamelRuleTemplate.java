package com.zero.sonar.customize.plugin.rules.naming;

import com.zero.sonar.customize.plugin.util.PrinterVisitor;
import org.jetbrains.annotations.NotNull;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.InputFileScannerContext;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 小驼峰命名规则模板，适用于属性名、方法名
 * @author zero
 * @since 2024/4/29 15:08
 */
@Rule(key = "VariableNamingLowerCamelRuleTemplate")
public class VariableNamingLowerCamelRuleTemplate extends IssuableSubscriptionVisitor {

    private static final Logger LOGGER = Loggers.get(VariableNamingLowerCamelRuleTemplate.class);
    private static final Pattern LOWER_CAMEL_PATTERN = Pattern.compile("^[a-z][a-z0-9]*([A-Z][a-z0-9]+)*([A-Z])?$");
    private static final String DEFAULT_REPLACE_BLANK_PATTERN = "(AO|BO|DAO|DO|DTO|PO|VO|ID|PK)";

    @RuleProperty(key = "LOWER_NAMING_WHITE_KEYS", description = "关键字白名单,格式:(key1|key2|key3)", defaultValue = DEFAULT_REPLACE_BLANK_PATTERN)
    private String whiteKeys;

    @Override
    public void scanFile(@NotNull JavaFileScannerContext context) {
        super.scanFile(context);
        PrinterVisitor.print(context.getTree(), LOGGER::debug);
    }

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.VARIABLE, Tree.Kind.METHOD);
    }

    @Override
    public void visitNode(@NotNull Tree tree) {
        if(tree instanceof VariableTree vt) {
            String vn = vt.simpleName().name();
            // 排除 static | final 修饰的属性
            if(vt.symbol().isFinal()) {
                super.visitNode(tree);
                return;
            }
            vn = vn.replaceAll(whiteKeys, "");
            if(!vn.isEmpty() && !LOWER_CAMEL_PATTERN.matcher(vn).matches()) {
                reportIssue(tree, "Variable Name [" + vn + "] Not LowerCamel Style Naming.");
                LOGGER.info("Visit [{}], Naming Not LowerCamel Style", vt.simpleName().name());
            }
        } else if(tree instanceof MethodTree mt) {
            String mn = mt.simpleName().name();
            mn = mn.replaceAll(whiteKeys, "");
            if(!mn.isEmpty() && !LOWER_CAMEL_PATTERN.matcher(mn).matches()) {
                reportIssue(tree, "Method Name [" + mn + "] Not LowerCamel Style Naming.");
                LOGGER.info("Visit [{}], Naming Not LowerCamel Style", mt.simpleName().name());
            }
        }
        super.visitNode(tree);
    }

    @Override
    public boolean scanWithoutParsing(@NotNull InputFileScannerContext inputFileScannerContext) {
        return super.scanWithoutParsing(inputFileScannerContext);
    }

    public String getWhiteKeys() {
        return whiteKeys;
    }

    public void setWhiteKeys(String whiteKeys) {
        this.whiteKeys = whiteKeys == null ? "" : whiteKeys.trim();
    }
}
