package rule.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import trile.rule.config.RuleSetDefinition

interface RuleConfigProvider {
    fun loadRules(): List<RuleSetDefinition>
}

@Component
@ConfigurationProperties(prefix = "rules")
class YamlRuleConfigLoader : RuleConfigProvider {
    private var useCases: Map<String, RuleSetDefinition> = emptyMap()
    override fun loadRules(): List<RuleSetDefinition> = useCases.values.toList()
}
