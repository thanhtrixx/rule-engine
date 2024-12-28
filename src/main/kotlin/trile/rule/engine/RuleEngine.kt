package trile.rule.engine

import org.springframework.stereotype.Service
import trile.common.Log
import trile.common.getOrThrow
import trile.rule.action.Action
import trile.rule.condition.Condition
import trile.rule.model.RuleConfiguration
import trile.rule.model.TransactionContext

@Service
class RuleEngine(
  ruleConfig: RuleConfiguration,
  conditions: List<Condition<Any>>,
  actions: List<Action<Any>>
) : Log {

  private val conditionMap =
    conditions.associateBy { it.type }.also { l.info("Loaded conditions [${it.keys.joinToString()}]") }

  private val actionMap = actions.associateBy { it.type }.also { l.info("Loaded actions [${it.keys.joinToString()}]") }

  private val executorByType: Map<String, RuleExecutor> =
    ruleConfig.paymentUseCases
      .map {
        it.key to RuleExecutor(it.value.name, createRules(it.value.rules))
      }.toMap()

  fun executeRules(context: TransactionContext) {
    val executor = executorByType[context.type]
    if (executor == null) {
      l.info("Cannot get executor for type ${context.type}. Ignored this transaction")
      return
    }
    executor.execute(context)
  }

  private fun RuleConfiguration.RuleSetDefinition.RuleDefinition.ConditionDefinition.toConditionWithParameter(): ConditionWithParameter<Any> {
    val condition = conditionMap.getOrThrow(this.type, "Can not find condition for type [$type]")
    return ConditionWithParameter(condition = condition, parameter = condition.convertParameter(parameters))
  }


  private fun RuleConfiguration.RuleSetDefinition.RuleDefinition.ActionDefinition.toActionWithParameter(): ActionWithParameter<Any> {
    val action = actionMap.getOrThrow(type, "Can not find action for type: [${type}]")
    return ActionWithParameter(action = action, parameter = action.convertParameter(parameters))
  }


  private fun createRules(ruleSetConfigurationDefinitions: List<RuleConfiguration.RuleSetDefinition.RuleDefinition>): List<RuleSetWrapper<Any, Any>> {
    return ruleSetConfigurationDefinitions.map { rule ->
      RuleSetWrapper(
        conditions = rule.conditions.map { it.toConditionWithParameter() },
        actions = rule.actions.map { it.toActionWithParameter() }
      )
    }
  }

  private class RuleExecutor(
    private val name: String,
    private val rules: List<RuleSetWrapper<Any, Any>>
  ) {

    fun execute(context: TransactionContext) {
      for (rule in rules) {
        if (rule.conditions.all { it.condition.evaluate(context, it.parameter) }) {
          rule.actions.forEach { it.action.execute(context, it.parameter) }
        }
      }
    }
  }

}
