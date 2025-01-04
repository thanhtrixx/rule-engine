package trile.rule.engine

import trile.rule.action.Action
import trile.rule.condition.Condition

data class RuleSetWrapper<C, A>(
  val conditions: List<ConditionWithParameter<C>>,
  val actions: List<ActionWithParameter<A>>
)

data class ConditionWithParameter<T>(
  val condition: Condition<T>,
  val parameter: T
)

data class ActionWithParameter<T>(
  val action: Action<T>,
  val parameter: T
)
