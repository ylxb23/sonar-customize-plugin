package com.zero.sonar.customize.plugin;

import org.sonar.plugins.java.api.CheckRegistrar;

/**
 * 规则注册器
 * @author zero
 * @since 2024-04-28
 */
public class CustomizeFileCheckRegistrar implements CheckRegistrar {

    @Override
    public void register(RegistrarContext context) {
        context.registerClassesForRepository(CustomizeRulesDefinition.REPOSITORY_KEY,
                CustomizeRulesDefinition.registryCheckRuleList(), CustomizeRulesDefinition.registryTestCheckRuleList());
    }
}
