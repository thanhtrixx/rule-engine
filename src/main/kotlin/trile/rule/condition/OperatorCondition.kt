package trile.rule.condition

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import trile.rule.engine.ConditionWithParameter
import trile.rule.model.TransactionContext

@Order(value = Ordered.LOWEST_PRECEDENCE - 1000)
@Component
class OperatorCondition : Condition<OperatorConditionParameter> {

  override fun evaluate(
    context: TransactionContext, parameter: OperatorConditionParameter
  ): Boolean {
    return when (parameter.type) {
      OperatorType.NOT -> !parameter.conditions.all { it.condition.evaluate(context, it.parameter) }
      OperatorType.AND -> parameter.conditions.all { it.condition.evaluate(context, it.parameter) }
      OperatorType.OR -> parameter.conditions.any { it.condition.evaluate(context, it.parameter) }
    }
  }

  override val type: ConditionType = ConditionType.OPERATOR
}

data class OperatorConditionParameter(
  val type: OperatorType,
  val conditions: List<ConditionWithParameter<Any>>
)

enum class OperatorType {
  NOT,
  AND,
  OR
}
