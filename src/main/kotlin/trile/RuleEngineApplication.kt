package trile

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import trile.common.Log
import trile.rule.model.Rule

@SpringBootApplication
class RuleEngineApplication(
  private val rule: Rule,
) : ApplicationRunner, Log {
  override fun run(args: ApplicationArguments?) {
    l.info("Loaded rule config: [$rule]")
  }
}

fun main(args: Array<String>) {
  runApplication<RuleEngineApplication>(*args)
}
