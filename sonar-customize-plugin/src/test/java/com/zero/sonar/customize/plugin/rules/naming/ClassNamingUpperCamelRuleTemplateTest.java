package com.zero.sonar.customize.plugin.rules.naming;

import org.junit.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

/**
 * @author zero
 * @since 2024/5/7 15:37
 */
public class ClassNamingUpperCamelRuleTemplateTest {

    @Test
    public void verifyNoIssue() {
        ClassNamingUpperCamelRuleTemplate checker = new ClassNamingUpperCamelRuleTemplate();
        checker.setWhiteKeys("(CLAZZ|DO)");
        CheckVerifier.newVerifier()
                .onFiles("src/test/resources/java/CLAZZInvalidButWhite.java")
                .withCheck(checker)
                .verifyNoIssues();
    }

    @Test
    public void verifyIssue() {
        ClassNamingUpperCamelRuleTemplate checker = new ClassNamingUpperCamelRuleTemplate();
        checker.setWhiteKeys("(DO)");   // 无 CLAZZ 的白名单

        CheckVerifier.newVerifier()
                .onFiles("src/test/resources/java/CLAZZInvalid.java")
                .withCheck(checker)
                .verifyIssueOnFile("Class Name [CLAZZInvalid] Not UpperCamel Style Naming.");
    }
}
