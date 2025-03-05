package com.zero.sonar.customize.plugin.rules;

import org.junit.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

/**
 * @author zero
 * @since 2025/3/5 10:11
 */
public class DisallowedBigDecimalEqualsRuleTest {

    @Test
    public void verifyIssues() {
        CheckVerifier.newVerifier()
                .onFiles("src/test/resources/java/BigDecimalEquals.java")
                .withCheck(new DisallowedBigDecimalEqualsRule())
                .verifyIssues();
    }
}
