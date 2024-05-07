package com.zero.sonar.customize.plugin.rules.naming;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.InputFileScannerContext;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author zero
 * @since 2024/4/29 15:08
 */
@Rule(key = "VariableNamingLowerCamelRule")
public class VariableNamingLowerCamelRule extends IssuableSubscriptionVisitor {

    private static final Logger LOGGER = Loggers.get(VariableNamingLowerCamelRule.class);
    private static final Pattern LOWER_CAMEL_PATTERN = Pattern.compile("^[a-z][a-z0-9]*([A-Z][a-z0-9]+)*([A-Z])?$");
    private static final String REPLACE_BLANK_PATTERN = "(AO|BO|DAO|DO|DTO|PO|VO|PK)";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.VARIABLE, Tree.Kind.METHOD);
    }

    @Override
    public void visitNode(Tree tree) {
        if(tree instanceof VariableTree vt) {
            // 参数
            String vn = vt.simpleName().name();
            // 排除 static | final 修饰的属性
            if(vt.symbol().isStatic() || vt.symbol().isFinal()) {
                super.visitNode(tree);
                return;
            }
            vn = vn.replaceAll(REPLACE_BLANK_PATTERN, "");
            if(!LOWER_CAMEL_PATTERN.matcher(vn).matches()) {
                reportIssue(tree, "Variable Name [" + vn + "] Not LowerCamel Style Naming.");
                LOGGER.info("Visit [{}], Naming Not LowerCamel Style", vt.simpleName().name());
            }
        } else if(tree instanceof MethodTree mt) {
            String mn = mt.simpleName().name();
            mn = mn.replaceAll(REPLACE_BLANK_PATTERN, "");
            if(!LOWER_CAMEL_PATTERN.matcher(mn).matches()) {
                reportIssue(tree, "Method Name [" + mn + "] Not LowerCamel Style Naming.");
                LOGGER.info("Visit [{}], Naming Not LowerCamel Style", mt.simpleName().name());
            }
        }
        super.visitNode(tree);
    }

    @Override
    public boolean scanWithoutParsing(InputFileScannerContext inputFileScannerContext) {
        return super.scanWithoutParsing(inputFileScannerContext);
    }
}
