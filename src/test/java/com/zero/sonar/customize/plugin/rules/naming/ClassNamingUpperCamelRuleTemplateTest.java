package com.zero.sonar.customize.plugin.rules.naming;

import org.junit.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.java.checks.verifier.FilesUtils;

/**
 * @author zero
 * @since 2024/5/7 15:37
 */
public class ClassNamingUpperCamelRuleTemplateTest {

    @Test
    public void verifyNoIssue() {
        ClassNamingUpperCamelRuleTemplate checker = new ClassNamingUpperCamelRuleTemplate();
        checker.setWhiteKeys("(WAKAKA|DO)");
        CheckVerifier.newVerifier()
                .onFile("target/test-classes/java/WAKAKAJob.java")
                .withClassPath(FilesUtils.getClassPath("target/test-classes/java"))
                .withCheck(checker)
                .verifyNoIssues();
    }

    @Test
    public void verifyIssue() {
        ClassNamingUpperCamelRuleTemplate checker = new ClassNamingUpperCamelRuleTemplate();
        checker.setWhiteKeys("(WAKAKA|DO)");
        CheckVerifier.newVerifier()
                .onFile("target/test-classes/java/StudentDTO.java")
                .withClassPath(FilesUtils.getClassPath("target/test-classes/java"))
                .withCheck(checker)
                .verifyIssues();
    }
}
