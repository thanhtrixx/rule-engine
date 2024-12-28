package trile.rule.engine

import org.springframework.stereotype.Service
import trile.rule.model.RuleContext

@Service
class RuleEngine {

  private class RuleExecutor(
    private val name: String,
    private val rules: List<RuleSet>
  ) {

    fun execute(context: RuleContext) {
      for (rule in rules) {
        if (rule.conditions.all { it.condition.evaluate(context, it.parameter) }) {
          rule.actions.forEach { it.action.execute(context, it.parameter) }
        }
      }
    }
  }

}
