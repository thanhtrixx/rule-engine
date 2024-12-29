package trile.rule.condition

import java.math.BigDecimal
import org.springframework.stereotype.Component
import trile.common.getAndToBigDecimal
import trile.rule.model.ConditionDefinition
import trile.rule.model.TransactionContext

@Component
class EqualsCondition : Condition<BigDecimal> {
  override fun evaluate(context: TransactionContext, parameter: BigDecimal): Boolean {
    return context.amount == parameter
  }

  override val type = ConditionType.EQUALS

  override fun convertParameter(conditionDefinition: ConditionDefinition) =
    conditionDefinition.parameters.getAndToBigDecimal("amount")
}
