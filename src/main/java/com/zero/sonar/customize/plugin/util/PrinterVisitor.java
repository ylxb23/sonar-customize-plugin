package com.zero.sonar.customize.plugin.util;

import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.Tree;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

/**
 * 打印语法树结构
 * @author zero
 * @since 2024/5/10 8:50
 */
public class PrinterVisitor extends BaseTreeVisitor {
    private static final int INDENT_SPACES = 4;

    private final StringBuilder sb;
    private int indentLevel;

    public PrinterVisitor() {
        sb = new StringBuilder();
        indentLevel = 0;
    }

    public static void print(Tree tree, Consumer<String> output) {
        PrinterVisitor pv = new PrinterVisitor();
        pv.scan(tree);
        output.accept(pv.sb.toString());
    }

    private StringBuilder indent() {
        return sb.append(" ".repeat(Math.max(0, INDENT_SPACES * indentLevel)));
    }

    @Override
    protected void scan(List<? extends Tree> trees) {
        if (!trees.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
            sb.append(" : [\n");
            super.scan(trees);
            indent().append("]\n");
        }
    }

    @Override
    protected void scan(@Nullable Tree tree) {
        if (tree != null) {
            Class<?>[] interfaces = tree.getClass().getInterfaces();
            if (interfaces.length > 0) {
                indent().append(interfaces[0].getSimpleName()).append("\n");
            }
        }
        indentLevel++;
        super.scan(tree);
        indentLevel--;
    }
}
