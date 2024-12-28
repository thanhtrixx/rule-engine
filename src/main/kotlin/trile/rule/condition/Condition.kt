package trile.rule.condition

import trile.rule.model.RuleContext


interface Condition<T> {

  fun evaluate(context: RuleContext, parameter: T): Boolean
  val type: ConditionType
  fun convertParameter(parameters: Map<String, String>): T
}

enum class ConditionType {
  EQUALS,
  GREATER_THAN,
  LESS_THAN,
  IN_RANGE,
  IN_CATEGORIES,
  CONTAINS
}
