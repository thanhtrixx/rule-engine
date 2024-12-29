package trile.rule.action

import java.math.BigDecimal
import org.springframework.stereotype.Component
import trile.common.Log
import trile.common.getAndToBigDecimal
import trile.rule.model.TransactionContext

@Component
class EarnPointByRateAction : Action<BigDecimal>, Log() {
  override fun execute(context: TransactionContext, parameter: BigDecimal) {
    l.info("Earn-point with amount [${context.amount}], rate [$parameter], point [${context.amount.multiply(parameter)}]")
  }

  override val type = ActionType.EARN_POINT_BY_RATE

  override fun convertParameter(parameters: Map<String, String>) = parameters.getAndToBigDecimal("rate")
}
