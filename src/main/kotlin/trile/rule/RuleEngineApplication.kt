package trile.rule

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import trile.rule.model.Rule

@SpringBootApplication
class RuleEngineApplication(
  private val rule: Rule,
): ApplicationRunner {
  override fun run(args: ApplicationArguments?) {
    println(rule)
  }
}

fun main(args: Array<String>) {
  runApplication<RuleEngineApplication>(*args)
}
