package com.zero.sonar.customize.plugin.rules;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;

/**
 * BigDecimal 数据类型的等值比较不允许使用 equals方法
 * @author zero
 * @since 2025/3/5 9:41
 */
@Rule(key="DisallowedBigDecimalEqualsRule")
public class DisallowedBigDecimalEqualsRule extends IssuableSubscriptionVisitor {
    private static final String CLASS_BIGDECIMAL = "java.math.BigDecimal";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.IF_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        IfStatementTree ist = (IfStatementTree) tree;
        // 获取if语句内的判断条件
        ExpressionTree et = ist.condition();
        if(et instanceof MethodInvocationTree mit) {
            // 如果是调用的方法进行比较
            ExpressionTree methodSelect = mit.methodSelect();
            if(methodSelect instanceof MemberSelectExpressionTree mset) {
                if(mset.expression().symbolType().is(CLASS_BIGDECIMAL)
                        && "equals".equals(mset.identifier().name())) {
                    reportIssue(tree, "BigDecimal 数据类型的等值比较不允许使用 equals方法.");
                }
            }
        }
    }
}
