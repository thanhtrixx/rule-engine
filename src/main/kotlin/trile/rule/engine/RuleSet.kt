package trile.rule.engine

import trile.rule.action.Action
import trile.rule.condition.Condition

data class RuleSet(
  val conditions: List<ConditionWrapper>,
  val actions: List<ActionWrapper>
) {
  data class ConditionWrapper(
    val condition: Condition,
    val parameter: Any?
  )

  data class ActionWrapper(
    val action: Action,
    val parameter: Any?
  )
}




