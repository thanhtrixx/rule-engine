package trile.rule.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "rules")
data class Rule(
  val purchaseTypes: Map<String, RuleBySource>
) {
  data class RuleBySource(
    val name: String,
    val rules: List<RuleDefinition>
  ) {
    data class RuleDefinition(
      val name: String?,
      val conditions: List<ConditionDefinition> = emptyList(),
      val actions: List<ActionDefinition>
    ) {
      data class ConditionDefinition(
        val name: String?,
        val type: String,
        val parameters: Map<String, String> = emptyMap()
      )

      data class ActionDefinition(
        val name: String?,
        val type: String,
        val parameters: Map<String, String> = emptyMap()
      )
    }
  }
}
