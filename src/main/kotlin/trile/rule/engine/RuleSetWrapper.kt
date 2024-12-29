package trile.rule.engine

import trile.common.getOrThrow
import trile.rule.action.Action
import trile.rule.condition.Condition
import trile.rule.condition.ConditionType
import trile.rule.model.ConditionDefinition

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
