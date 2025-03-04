package com.zero.sonar.customize.plugin.rules.naming;

import org.junit.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

/**
 * @author zero
 * @since 2024/5/6 15:58
 */
public class VariableNamingLowerCamelRuleTemplateTest {

    @Test
    public void verifyNoIssue() {
        VariableNamingLowerCamelRuleTemplate templateInstance = new VariableNamingLowerCamelRuleTemplate();
        templateInstance.setWhiteKeys("(DTO|PO|KAKA)");
        CheckVerifier.newVerifier()
                .onFiles("src/test/resources/java/PropertyInvalidButWhite.java")
                .withCheck(templateInstance)
                .verifyNoIssues();
    }
    @Test
    public void verifyIssue() {
        VariableNamingLowerCamelRuleTemplate templateInstance = new VariableNamingLowerCamelRuleTemplate();
        templateInstance.setWhiteKeys("(AO|BO|DAO|DO|DTO|PO|VO|ID|PK)");
        CheckVerifier.newVerifier()
                .onFiles("target/test-classes/java/PropertyInvalid.java")
                .withCheck(templateInstance)
                .verifyIssues();
    }
}
