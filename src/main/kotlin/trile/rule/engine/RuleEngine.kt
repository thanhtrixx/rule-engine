package trile.rule.engine

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import trile.common.Log
import trile.common.getOrThrow
import trile.rule.action.Action
import trile.rule.action.ActionParameterConverter
import trile.rule.action.ActionType
import trile.rule.condition.Condition
import trile.rule.condition.ConditionParameterConverter
import trile.rule.condition.ConditionType
import trile.rule.condition.OperatorConditionParameter
import trile.rule.condition.OperatorType
import trile.rule.config.ActionDefinition
import trile.rule.config.ConditionDefinition
import trile.rule.config.RuleDefinition
import trile.rule.model.RuleConfiguration
import trile.rule.model.TransactionContext

@Service
class RuleEngine(
  ruleConfig: RuleConfiguration,
  private val conditionMap: Map<ConditionType, Condition<Any>>,
  private val actionMap: Map<ActionType, Action<Any>>
) : Log {

  private val ruleExecutorsByType: Map<String, RuleExecutor> =
    ruleConfig.useCases
      .map {
        it.key to RuleExecutor(it.value.name, createRules(it.value.rules))
      }.toMap()

  fun executeRules(context: TransactionContext) {
    l.info("Executing with context [$context]")
    val executor = ruleExecutorsByType[context.type]
    if (executor == null) {
      l.info("Cannot get executor for type ${context.type}. Ignored this transaction")
      return
    }
    executor.execute(context)
  }

  private inline fun <reified T> ConditionDefinition.toConditionWithParameter(conditionMap: Map<ConditionType, Condition<T>>): ConditionWithParameter<T> {
    val condition =
      conditionMap.getOrThrow(this.type, "Can not find condition for type [$type]")

    val parameter = when {
      condition is ConditionParameterConverter<*> -> condition.convertParameter(parameters)
      condition.type == ConditionType.OPERATOR -> toOperatorConditionParameter(conditionMap as Map<ConditionType, Condition<Any>>)
      else -> TODO()
    }
    return ConditionWithParameter(
      condition = condition,
      parameter = parameter as T
    )
  }

  private inline fun <reified T> ActionDefinition.toActionWithParameter(actionMap: Map<ActionType, Action<T>>): ActionWithParameter<T> {
    val action = actionMap.getOrThrow(type, "Can not find action for type: [${type}]")
    val parameter = when {
      action is ActionParameterConverter<*> -> action.convertParameter(parameters)
      else -> TODO()
    }
    return ActionWithParameter(action = action, parameter = parameter as T)
  }

  private fun ConditionDefinition.toOperatorConditionParameter(
    conditionMap: Map<ConditionType, Condition<Any>>
  ): OperatorConditionParameter {
    val (type, conditions) = when {
      this.not.isNotEmpty() -> OperatorType.NOT to this.not
      this.and.isNotEmpty() -> OperatorType.AND to this.and
      this.or.isNotEmpty() -> OperatorType.OR to this.or

      else -> throw RuntimeException("Missing sub condition")
    }
    return OperatorConditionParameter(
      type = type,
      conditions = conditions.map { it.toConditionWithParameter(conditionMap) })
  }

  private fun createRules(ruleSetConfigurationDefinitions: List<RuleDefinition>): List<RuleSetWrapper<Any, Any>> {
    return ruleSetConfigurationDefinitions.map { rule ->
      RuleSetWrapper(
        conditions = rule.conditions.map { it.toConditionWithParameter(conditionMap) },
        actions = rule.actions.map { it.toActionWithParameter(actionMap) }
      )
    }
  }

  private class RuleExecutor(
    private val name: String,
    private val rules: List<RuleSetWrapper<Any, Any>>
  ) : Log {

    fun execute(context: TransactionContext) {
      for (rule in rules) {
        if (rule.conditions.all { it.condition.evaluate(context, it.parameter) }) {
          rule.actions.forEach { it.action.execute(context, it.parameter) }
        }
      }
      l.info("Executed $name")
    }
  }
}

@Configuration
internal class RuleContext(
  private val conditions: List<Condition<Any>>,
  private val actions: List<Action<Any>>
) : Log {

  @Bean
  fun buildConditionMap() =
    conditions.associateBy { it.type }.also { l.info("Loaded conditions [${it.keys.joinToString()}]") }

  @Bean
  fun buildActionMap() = actions.associateBy { it.type }.also { l.info("Loaded actions [${it.keys.joinToString()}]") }
}
