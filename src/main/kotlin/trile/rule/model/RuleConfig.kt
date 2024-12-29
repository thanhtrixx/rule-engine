package trile.rule.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import trile.rule.action.ActionType
import trile.rule.condition.ConditionType
import trile.rule.model.Constants.Companion.EMPTY_CONDITIONS
import trile.rule.model.Constants.Companion.EMPTY_MAP

@Configuration
@ConfigurationProperties(prefix = "rules")
data class RuleConfiguration(
  val paymentUseCases: Map<String, RuleSetDefinition>
)

data class RuleSetDefinition(
  val name: String,
  val rules: List<RuleDefinition>
)

data class RuleDefinition(
  val name: String?,
  val conditions: List<ConditionDefinition> = emptyList(),
  val actions: List<ActionDefinition>
)

data class ConditionDefinition(
  val name: String?,
  val type: ConditionType,
  val parameters: Map<String, String> = EMPTY_MAP,
  val or: List<ConditionDefinition> = EMPTY_CONDITIONS,
  val and: List<ConditionDefinition> = EMPTY_CONDITIONS,
  val not: List<ConditionDefinition> = EMPTY_CONDITIONS,
)

data class ActionDefinition(
  val name: String?,
  val type: ActionType,
  val parameters: Map<String, String> = EMPTY_MAP
)

internal class Constants {
  companion object {
    const val EMPTY_STRING = ""
    val EMPTY_MAP = emptyMap<String, String>()
    val EMPTY_CONDITIONS = emptyList<ConditionDefinition>()
  }
}
