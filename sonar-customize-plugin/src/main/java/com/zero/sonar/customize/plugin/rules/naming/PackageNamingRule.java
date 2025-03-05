package com.zero.sonar.customize.plugin.rules.naming;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.PackageDeclarationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 包命名规则
 * @author zero
 * @since 2024/4/29 15:13
 */
@Rule(key = "PackageNamingRule")
public class PackageNamingRule extends IssuableSubscriptionVisitor {
    private static final Logger LOGGER = Loggers.get(PackageNamingRule.class);
    private static final Pattern PACKAGE_NAMING_PATTERN = Pattern.compile("^[a-z0-9]+(\\.[a-z][a-z0-9]*)*$");


    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.PACKAGE);
    }

    @Override
    public void visitNode(Tree tree) {
        if(tree instanceof PackageDeclarationTree pdt) {
            String pkg = "";
            if(pdt.packageName() instanceof MemberSelectExpressionTree mset) {
                pkg = mset.identifier().name();
            } else if(pdt.packageName() instanceof IdentifierTree it) {
                pkg = it.name();
            }
            if(!PACKAGE_NAMING_PATTERN.matcher(pkg).matches()) {
                reportIssue(tree, "Package Name [" + pkg + "] Not Match Naming Rule.");
                LOGGER.info("Visit [{}], Not Match Package Naming Style", pkg);
            }
        }
        super.visitNode(tree);
    }
}
