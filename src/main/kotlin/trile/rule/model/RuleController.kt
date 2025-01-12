//package trile.rule.model
//
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.DeleteMapping
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//@RequestMapping("/api/rules")
//class RuleController(private val ruleService: RuleService) {
//
//  @GetMapping
//  fun getAllRules(): ResponseEntity<List<RuleSetDefinition>> {
//    return ResponseEntity.ok(ruleService.getAllRules())
//  }
//
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
//}
