package trile.rule.config

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import trile.rule.model.RuleSetDefinition

@RestController
@RequestMapping("/api/rules")
class RuleConfigController(
  private val ruleConfigService: RuleConfigService,
  private val ruleSetsByUseCase: Map<String, RuleSetDefinition>
) {

  @GetMapping
  fun getAllRules(): ResponseEntity<List<RuleSetDefinition>> {
    return ResponseEntity.ok(ruleConfigService.getAllRuleSets())
  }

  @GetMapping("rule-set-by-use-case")
  fun getRuleSetByUserCase() = ruleSetsByUseCase

//  @PostMapping
//  fun createRuleSet(@RequestBody ruleSet: RuleSetDefinition): ResponseEntity<RuleSetDefinition> {
//    return ResponseEntity.ok(ruleService.saveRuleSet(ruleSet))
//  }
//
//  @DeleteMapping("/{id}")
//  fun deleteRuleSet(@PathVariable id: Long): ResponseEntity<Void> {
//    ruleService.deleteRuleSet(id)
//    return ResponseEntity.noContent().build()
//  }
}
