package trile.rule.action

import trile.rule.model.TransactionContext


interface Action<out T> {
  fun execute(context: TransactionContext, parameter: @UnsafeVariance T)
  val type: ActionType
}

interface ActionParameterConverter<out T> {
  fun convertParameter(parameters: Map<String, String>): T
}

enum class ActionType {
  EARN_POINT_BY_RATE,
  EARN_POINTS_PER_SPENDING,
  EARN_FIXED_POINTS,
}
