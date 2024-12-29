package trile.rule.condition

import trile.rule.model.TransactionContext


interface Condition<out T> {

  fun evaluate(context: TransactionContext, parameter: @UnsafeVariance T): Boolean
  val type: ConditionType
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
