package trile.rule.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import trile.rule.action.ActionType
import trile.rule.condition.ConditionType

@Configuration
@ConfigurationProperties(prefix = "rules")
data class RuleConfiguration(
  val purchaseTypes: Map<String, RuleSetDefinition>
) {
  data class RuleSetDefinition(
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
        val type: ConditionType,
        val parameters: Map<String, String> = emptyMap()
      )

      data class ActionDefinition(
        val name: String?,
        val type: ActionType,
        val parameters: Map<String, String> = emptyMap()
      )
    }
  }
}
