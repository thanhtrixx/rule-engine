package trile.rule.action

import trile.rule.model.RuleContext


interface Action {
  fun execute(context: RuleContext, parameter: Any?)
  val type: ActionType
  fun convertParameter(parameters: Map<String, String>): Any?
}

enum class ActionType {
  EARN_POINT_BY_RATE,
  EARN_POINTS_PER_SPENDING,
  EARN_FIXED_POINTS,
}
