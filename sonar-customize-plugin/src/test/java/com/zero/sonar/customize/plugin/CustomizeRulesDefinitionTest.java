package com.zero.sonar.customize.plugin;

import org.junit.Assert;
import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;

import java.util.List;

/**
 * @author zero
 * @since 2024/5/6 14:22
 */
public class CustomizeRulesDefinitionTest {

    @Test
    public void testRuleDefinition() {
        CustomizeRulesDefinition customizeRulesDefinition = new CustomizeRulesDefinition(new MockSonarRuntimeContext());
        RulesDefinition.Context context = new RulesDefinition.Context();
        customizeRulesDefinition.define(context);
        RulesDefinition.Repository repository = context.repository(CustomizeRulesDefinition.REPOSITORY_KEY);

        Assert.assertNotNull(repository);
        List<RulesDefinition.Rule> rules = repository.rules();
        Assert.assertEquals(CustomizeRulesDefinition.registryCheckRuleList().size() + CustomizeRulesDefinition.registryTestCheckRuleList().size(), rules.size());
    }

}
