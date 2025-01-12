package trile

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import trile.common.Log
import trile.rule.config.RuleConfiguration

@SpringBootApplication
class RuleEngineApplication(
  private val ruleConfig: RuleConfiguration,
) : ApplicationRunner, Log {
  override fun run(args: ApplicationArguments?) {
    l.info("Loaded rule config: [$ruleConfig]")
  }
}

fun main(args: Array<String>) {
  runApplication<RuleEngineApplication>(*args)
}
