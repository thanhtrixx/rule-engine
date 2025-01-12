package trile

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import trile.common.Log
import trile.rule.model.RuleSetDefinition

@SpringBootApplication
class RuleEngineApplication(
  private val ruleSetsByUseCase: Map<String, RuleSetDefinition>,
) : ApplicationRunner, Log {
  override fun run(args: ApplicationArguments?) {
    l.info("Loaded rule config: [$ruleSetsByUseCase]")
  }
}

fun main(args: Array<String>) {
  runApplication<RuleEngineApplication>(*args)
}
