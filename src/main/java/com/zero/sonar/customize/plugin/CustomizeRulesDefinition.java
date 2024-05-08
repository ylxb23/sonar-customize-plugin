package com.zero.sonar.customize.plugin;

import com.zero.sonar.customize.plugin.rules.naming.ClassNamingUpperCamelRuleTemplate;
import com.zero.sonar.customize.plugin.rules.naming.PackageNamingRule;
import com.zero.sonar.customize.plugin.rules.naming.VariableNamingLowerCamelRuleTemplate;
import org.sonar.api.SonarRuntime;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import java.util.*;

/**
 * 规则定义
 * @author zero
 */
public class CustomizeRulesDefinition implements RulesDefinition {

    private static final String RESOURCE_BASE_PATH = "org/sonar/l10n/java/rules/java";

    public static final String LANGUAGE_JAVA = "java";
    public static final String REPOSITORY_KEY = "zero-customize";
    public static final String REPOSITORY_NAME = "Zero Customized Repository";

    public static final Set<String> TEMPLATE_RULE_KEYS = new HashSet<>();

    private final SonarRuntime sonarRuntime;

    public CustomizeRulesDefinition(SonarRuntime runtime) {
        this.sonarRuntime = runtime;
        initDefineTemplateRules();
    }

    private void initDefineTemplateRules() {
        TEMPLATE_RULE_KEYS.add("ClassNamingUpperCamelRuleTemplate");
        TEMPLATE_RULE_KEYS.add("VariableNamingLowerCamelRuleTemplate");
    }

    @Override
    public void define(Context context) {
        // 规则仓库，在规则过滤器"Repository"中会显示 Name 和 Language的组合，可通过仓库过滤查询规则。
        NewRepository zeroRepository = context.createRepository(REPOSITORY_KEY, LANGUAGE_JAVA).setName(REPOSITORY_NAME);
        //
        RuleMetadataLoader zeroMetadata = new RuleMetadataLoader(RESOURCE_BASE_PATH, this.sonarRuntime);
        zeroMetadata.addRulesByAnnotatedClass(zeroRepository, allRules());

        zeroRepository.rules().stream().filter(Objects::nonNull)
                // 仅对实现 TemplateRule 接口的规则设置为模板
                .filter(rule -> TEMPLATE_RULE_KEYS.contains(rule.key()))
                .forEach(rule -> {
                    rule.setTemplate(true);
                });
        zeroRepository.done();
    }

    private List<Class<?>> allRules() {
        List<Class<? extends JavaCheck>> all = new ArrayList<>();
        all.addAll(registryCheckRuleList());
        all.addAll(registryTestCheckRuleList());
        return Collections.unmodifiableList(all);
    }

    /**
     * 所有用以扫描正常代码的规则在这里备注加入这里
     * @return check rule list
     */
    public static List<Class<? extends JavaCheck>> registryCheckRuleList() {
        List<Class<? extends JavaCheck>> rules = new ArrayList<>();
        rules.add(PackageNamingRule.class);
        rules.add(ClassNamingUpperCamelRuleTemplate.class);
        rules.add(VariableNamingLowerCamelRuleTemplate.class);
        return rules;
    }

    /**
     * 扫描测试类的规则加入到这里
     * @return test check rule list
     */
    public static List<Class<? extends JavaCheck>> registryTestCheckRuleList() {
        List<Class<? extends JavaCheck>> rules = new ArrayList<>();

        return rules;
    }
}
