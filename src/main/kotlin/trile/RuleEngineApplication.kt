package trile

import java.math.BigDecimal
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import trile.common.Log
import trile.rule.engine.RuleEngine
import trile.rule.model.RuleConfiguration
import trile.rule.model.TransactionContext

@SpringBootApplication
class RuleEngineApplication(
  private val ruleConfig: RuleConfiguration,
  private val ruleEngine: RuleEngine
) : ApplicationRunner, Log() {
  override fun run(args: ApplicationArguments?) {
    l.info("Loaded rule config: [$ruleConfig]")


//    val context = TransactionContext(type = "regular-card", amount = BigDecimal.valueOf(10000))
//    l.info("Executing with context: [$context]")
//    ruleEngine.executeRules(context)
//
//    val notRunContext = TransactionContext(type = "regular-card", amount = BigDecimal.valueOf(10))
//    l.info("Executing with context: [$notRunContext]")
//    ruleEngine.executeRules(notRunContext)

    val superCardRunContext = TransactionContext(type = "super-card", amount = BigDecimal.valueOf(10))
    ruleEngine.executeRules(superCardRunContext)

    val superCardNotRunContext = TransactionContext(type = "super-card", amount = BigDecimal.valueOf(10000))
    ruleEngine.executeRules(superCardNotRunContext)
  }
}

fun main(args: Array<String>) {
  runApplication<RuleEngineApplication>(*args)
}
