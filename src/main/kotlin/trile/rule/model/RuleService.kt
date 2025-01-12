//package trile.rule.model
//
//import org.springframework.stereotype.Service
//import trile.rule.config.RuleSetDefinition
//import trile.rule.config.RuleSetDefinitionRepository
//
//@Service
//class RuleService(private val ruleSetRepo: RuleSetDefinitionRepository) {
//
//  fun getAllRules(): List<RuleSetDefinition> = ruleSetRepo.findAll()
//
//  fun saveRuleSet(ruleSet: RuleSetDefinition): RuleSetDefinition {
//    return ruleSetRepo.save(ruleSet)
//  }
//
//  fun deleteRuleSet(id: Long) {
//    ruleSetRepo.deleteById(id)
//  }
//}
