package com.zero.sonar.customize.plugin.rules.naming;

import com.zero.sonar.customize.plugin.rules.TemplateRule;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.InputFileScannerContext;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 大驼峰命名规则模板，允许的模型对象命名白名单有：AO|BO|DAO|DO|DTO|PO|VO，并可以通过配置白名单过滤掉不扫描的类名
 * @author zero
 * @since 2024/4/28 15:58
 */
@Rule(key = "ClassNamingUpperCamelRuleTemplate")
public class ClassNamingUpperCamelRuleTemplate extends IssuableSubscriptionVisitor {

    private static final Logger LOGGER = Loggers.get(ClassNamingUpperCamelRuleTemplate.class);
    private static final Pattern UPPER_CAMEL_PATTERN = Pattern.compile("^I?([A-Z][a-z0-9]+)+([A-Z])?$");
    private static final String REPLACE_BLANK_PATTERN = "(AO|BO|DAO|DO|DTO|PO|VO)";

    @RuleProperty(key = "UPPER_NAMING_WHITE_LIST", description = "大驼峰命名规则白名单", defaultValue = "")
    private String whiteList;

    private Set<String> whites = null;

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.CLASS, Tree.Kind.INTERFACE, Tree.Kind.ENUM, Tree.Kind.ANNOTATION_TYPE, Tree.Kind.RECORD);
    }

    @Override
    public void visitNode(Tree tree) {
        if(tree instanceof ClassTree ct) {
            IdentifierTree it = ct.simpleName();
            if(it != null) {
                String clazzName = it.name();
                if(whites == null) {
                    whites = Arrays.stream(whiteList.split("[,;]")).collect(Collectors.toSet());
                }
                if(whites.contains(clazzName)) {
                    super.visitNode(tree);
                    return;
                }
                clazzName = clazzName.replaceAll(REPLACE_BLANK_PATTERN, "");
                if(!UPPER_CAMEL_PATTERN.matcher(clazzName).matches()) {
                    reportIssue(tree, "Class Name [" + it.name() + "] Not UpperCamel Style Naming.");
                    LOGGER.info("Visit [{}, {}}], Naming Not UpperCamel Style", ct.symbol().name(), it.name());
                }
            }
        }
        super.visitNode(tree);
    }

    @Override
    public boolean scanWithoutParsing(InputFileScannerContext inputFileScannerContext) {
        return super.scanWithoutParsing(inputFileScannerContext);
    }
}
