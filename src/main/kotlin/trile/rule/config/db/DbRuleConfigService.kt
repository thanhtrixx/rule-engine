package trile.rule.config.db

import org.springframework.stereotype.Service
import trile.common.Log
import trile.rule.config.RuleConfigService
import trile.rule.model.RuleSetDefinition

@Service
//@ConditionalOnProperty(prefix = "rules", name = ["source"], havingValue = "DB")
class DbRuleConfigService(
  private val ruleSetRepository: RuleSetDefinitionRepository,
) : RuleConfigService, Log {


  override fun getAllRuleSets(): List<RuleSetDefinition> {
    return ruleSetRepository.findAll().map { entity ->
      RuleSetDefinition(useCase = entity.useCase, name = entity.name, rules = entity.rules)
    }
  }


  override fun createRuleSet(ruleSet: RuleSetDefinition): RuleSetDefinition {
    val entity = RuleSetDefinitionEntity(useCase = ruleSet.useCase, name = ruleSet.name, rules = ruleSet.rules)
    val savedEntity = ruleSetRepository.save(entity)
    return RuleSetDefinition(useCase = ruleSet.useCase, name = savedEntity.name, rules = savedEntity.rules)
  }

  override fun updateRuleSet(id: Long, ruleSet: RuleSetDefinition): RuleSetDefinition {
    return ruleSetRepository.findById(id).map { existingEntity ->
      existingEntity.useCase = ruleSet.useCase
      existingEntity.name = ruleSet.name
      existingEntity.rules = ruleSet.rules
      val updatedEntity = ruleSetRepository.save(existingEntity)
      RuleSetDefinition(useCase = updatedEntity.useCase, name = updatedEntity.name, rules = updatedEntity.rules)
    }.orElseThrow()
  }

  override fun deleteRuleSet(id: Long) {
    ruleSetRepository.deleteById(id)
  }
}
