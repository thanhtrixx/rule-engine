package trile.rule

import org.springframework.boot.fromApplication
import org.springframework.boot.with
import trile.RuleEngineApplication


fun main(args: Array<String>) {
  fromApplication<RuleEngineApplication>().with(TestcontainersConfiguration::class).run(*args)
}
