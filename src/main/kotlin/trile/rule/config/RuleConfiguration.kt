package trile.rule.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import trile.rule.model.RuleSetDefinition

@Configuration
data class RuleConfiguration(
  private val ruleProperties: RuleProperties,
  private val ruleConfigService: RuleConfigService
) {

  @Bean
  fun ruleSetsByUseCase(): Map<String, RuleSetDefinition> {
    return when (ruleProperties.source) {
      RuleConfigurationSource.YAML -> ruleProperties.ruleSets
      RuleConfigurationSource.DB -> ruleConfigService.getAllRuleSets()
    }.associateBy { it.useCase }
  }
}

@Configuration
@ConfigurationProperties(prefix = "rules")
data class RuleProperties(
  var source: RuleConfigurationSource = RuleConfigurationSource.YAML,
  var ruleSets: List<RuleSetDefinition> = emptyList()
)

enum class RuleConfigurationSource {
  DB,
  YAML
}
