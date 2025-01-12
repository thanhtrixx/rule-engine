//package trile.rule.config
//
//import jakarta.transaction.Transactional
//import org.springframework.boot.CommandLineRunner
//import org.springframework.stereotype.Component
//import trile.common.Log
//import trile.rule.config.db.RuleSetDefinitionRepository
//import trile.rule.model.RuleSetDefinition
//
//@Component
//class RuleDataInitializer(
//  private val ruleConfigService: RuleConfigService,
//  private val ruleProperties: RuleProperties,
//  private val ruleSetDefinitionRepository: RuleSetDefinitionRepository,
//  private val ruleSetsByUseCase: Map<String, RuleSetDefinition>,
//) : CommandLineRunner, Log {
//
//  @Transactional
//  override fun run(vararg args: String?) {
//    l.info("Loaded rule config: [$ruleSetsByUseCase]")
//
//    ruleSetDefinitionRepository.findAll().forEach { ruleSetDefinitionRepository.deleteById(it.id) }
//    ruleProperties.ruleSets.forEach { ruleConfigService.createRuleSet(it) }
//  }
//}
