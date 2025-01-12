package trile.rule.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import trile.rule.model.RuleSetDefinition

@Configuration
@ConfigurationProperties(prefix = "rules")
data class RuleConfiguration(
  val useCases: Map<String, RuleSetDefinition>
)
