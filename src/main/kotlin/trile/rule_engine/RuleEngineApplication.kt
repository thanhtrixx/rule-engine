package trile.rule_engine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RuleEngineApplication

fun main(args: Array<String>) {
    runApplication<RuleEngineApplication>(*args)
}
