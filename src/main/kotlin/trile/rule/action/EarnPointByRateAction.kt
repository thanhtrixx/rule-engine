package trile.rule.action

import org.springframework.stereotype.Component
import trile.common.Log
import trile.common.getAndToFloat
import trile.rule.model.RuleContext

@Component
class EarnPointByRateAction : Action<Float>, Log {
  override fun execute(context: RuleContext, parameter: Float) {
    l.info("Earn-point with amount [${context.amount}], rate [$parameter]")
  }

  override val type = ActionType.EARN_POINT_BY_RATE

  override fun convertParameter(parameters: Map<String, String>) = parameters.getAndToFloat("rate")
}
