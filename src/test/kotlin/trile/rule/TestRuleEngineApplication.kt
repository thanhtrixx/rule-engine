package trile.rule

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
  fromApplication<RuleEngineApplication>().with(TestcontainersConfiguration::class).run(*args)
}
