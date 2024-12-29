package trile.rule.condition

import trile.rule.model.ConditionDefinition
import trile.rule.model.TransactionContext


interface Condition<out T> {

  fun evaluate(context: TransactionContext, parameter: @UnsafeVariance T): Boolean
  val type: ConditionType
  fun convertParameter(conditionDefinition: ConditionDefinition): T
}

enum class ConditionType {
  OPERATOR,
  EQUALS,
  GREATER_THAN,
  LESS_THAN,
  IN_RANGE,
  IN_CATEGORIES,
  CONTAINS,
}
