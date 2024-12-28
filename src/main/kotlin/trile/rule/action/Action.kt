package trile.rule.action

import trile.rule.model.RuleContext


interface Action<T> {
  fun execute(context: RuleContext, parameter: T)
  val type: ActionType
  fun convertParameter(parameters: Map<String, String>): T
}

enum class ActionType {
  EARN_POINT_BY_RATE,
  EARN_POINTS_PER_SPENDING,
  EARN_FIXED_POINTS,
}
