package trile.rule.condition

import trile.rule.model.TransactionContext


interface Condition<out T> {
  fun evaluate(context: TransactionContext, parameter: @UnsafeVariance T): Boolean
  val type: ConditionType
}

interface ConditionParameterConverter<T> {
  fun convertParameter(parameters: Map<String, String>): T
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
