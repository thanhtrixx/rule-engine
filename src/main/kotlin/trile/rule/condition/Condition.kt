package trile.rule.condition

import trile.rule.model.RuleContext


interface Condition {

  fun evaluate(context: RuleContext, parameter: Any?): Boolean
  val type: ConditionType
  fun convertParameter(parameters: Map<String, String>): Any?
}

enum class ConditionType {
  EQUALS,
  GREATER_THAN,
  LESS_THAN,
  IN_RANGE,
  IN_CATEGORIES,
  CONTAINS
}

data class ConditionWrapper(
  val condition: Condition,
  val parameter: Any?
)
