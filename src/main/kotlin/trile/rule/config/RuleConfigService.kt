package trile.rule.config

import trile.rule.model.RuleSetDefinition


interface RuleConfigService {
  fun getAllRuleSets(): List<RuleSetDefinition>
  fun createRuleSet(ruleSet: RuleSetDefinition): RuleSetDefinition
  fun updateRuleSet(id: Long, ruleSet: RuleSetDefinition): RuleSetDefinition?
  fun deleteRuleSet(id: Long)
}
