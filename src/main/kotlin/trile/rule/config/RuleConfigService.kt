package trile.rule.config

import org.springframework.stereotype.Service


@Service
class RuleService(private val ruleSetRepo: RuleSetDefinitionRepository) {

  fun getAllRules(): List<RuleSetDefinition> = ruleSetRepo.findAll()

  fun saveRuleSet(ruleSet: RuleSetDefinition): RuleSetDefinition {
    return ruleSetRepo.save(ruleSet)
  }

  fun deleteRuleSet(id: Long) {
    ruleSetRepo.deleteById(id)
  }
}
