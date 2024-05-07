package com.zero.sonar.customize.plugin.rules.naming;

import org.junit.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.java.checks.verifier.FilesUtils;

/**
 * @author zero
 * @since 2024/5/6 15:36
 */
public class ClassNamingUpperCamelRuleTest {

    @Test
    public void verify() {
        CheckVerifier.newVerifier()
                .onFile("target/test-classes/java/WAKAKAJob.java")
                .withCheck(new ClassNamingUpperCamelRule())
                .withClassPath(FilesUtils.getClassPath("target/test-classes/java"))
                .verifyIssues();
    }
}
