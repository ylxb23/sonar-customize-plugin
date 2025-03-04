package com.zero.sonar.customize.plugin.rules.naming;

import org.junit.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

/**
 * @author zero
 * @since 2024/5/6 15:58
 */
public class PackageNamingRuleTest {


    @Test
    public void verifyNoIssues() {
        CheckVerifier.newVerifier()
                .onFiles("src/test/resources/java/PackageValid.java")
                .withCheck(new PackageNamingRule())
                .verifyNoIssues();
    }
    @Test
    public void verifyIssues() {
        CheckVerifier.newVerifier()
                .onFiles("src/test/resources/java/PackageInvalid.java")
                .withCheck(new PackageNamingRule())
                .verifyIssues();
    }
}
