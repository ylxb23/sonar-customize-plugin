package com.zero.sonar.customize.plugin.rules.naming;

import org.junit.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.java.checks.verifier.FilesUtils;

/**
 * @author zero
 * @since 2024/5/6 15:58
 */
public class VariableNamingLowerCamelRuleTemplateTest {


    @Test
    public void verify() {
        VariableNamingLowerCamelRuleTemplate templateInstance = new VariableNamingLowerCamelRuleTemplate();
        templateInstance.setWhiteKeys("(AO|BO|DAO|DO|DTO|PO|VO|ID|PK)");
        CheckVerifier.newVerifier()
                .onFile("target/test-classes/java/HumanDO.java")
                .withCheck(templateInstance)
                .withClassPath(FilesUtils.getClassPath("target/test-classes"))
                .verifyIssues();
    }
}
