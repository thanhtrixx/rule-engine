package trile.rule.engine

import org.springframework.stereotype.Service
import trile.rule.action.Action
import trile.rule.condition.Condition
import trile.rule.model.RuleConfiguration
import trile.rule.model.RuleContext

@Service
class RuleEngine(
  ruleConfig: RuleConfiguration,
  conditions: List<Condition<*>>,
  actions: List<Action<*>>
) {

  private val conditionMap = conditions.associateBy { it.type }

  private val actionMap = actions.associateBy { it.type }

//  private val workersMapBySource: Map<String, RuleExecutor> =
//    ruleConfig.purchaseTypes
//      .map {
//        it.key to RuleExecutor(it.value.name, createRules(it.value.rules))
//      }.toMap()
//
//  private fun getRuleExecutors(source: String): RuleExecutor =
//    workersMapBySource[source] ?: throw RuntimeException("Can not find rule executor for source: [$source]")

//  private fun <T> RuleConfiguration.RuleSetDefinition.RuleDefinition.ConditionDefinition.toConditionWithParameter(): ConditionWithParameter<out T> {
//    val condition = conditionMap[this.type] ?: throw RuntimeException("AA")
//    return ConditionWithParameter(condition, condition.convertParameter(parameters))
//  }
//
//
//  private fun RuleConfiguration.RuleSetDefinition.RuleDefinition.ActionDefinition.toActionWrapper(): RuleSetWrapper.ActionWrapper {
//    val action = actionMap[type]
//      ?: throw RuntimeException("Can not find action for type: [${type}]")
//    return RuleSetWrapper.ActionWrapper(action, action.convertParameter(parameters))
//  }
//
//
//  private fun createRules(ruleSetConfigurationDefinitions: List<RuleConfig.Rule.RuleSet.RuleDefinition>): List<RuleSetWrapper> {
//    return ruleSetConfigurationDefinitions.map { rule ->
//      RuleSetWrapper(
//        conditions = rule.conditions.map { it.toConditionWrapper() },
//        actions = rule.actions.map { it.toActionWrapper() }
//      )
//    }
//  }
//
//  private class RuleExecutor(
//    private val name: String,
//    private val rules: List<RuleSetWrapper>
//  ) {
//
//    fun execute(context: RuleContext) {
//      for (rule in rules) {
//        if (rule.conditions.all { it.condition.evaluate(context, it.parameter) }) {
//          rule.actions.forEach { it.action.execute(context, it.parameter) }
//        }
//      }
//    }
//  }

}
