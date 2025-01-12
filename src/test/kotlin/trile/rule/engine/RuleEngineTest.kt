package trile.rule.engine

import io.mockk.spyk
import io.mockk.verify
import java.math.BigDecimal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import trile.rule.action.Action
import trile.rule.action.ActionType
import trile.rule.condition.Condition
import trile.rule.condition.ConditionType
import trile.rule.model.RuleSetDefinition
import trile.rule.model.TransactionContext

@SpringBootTest
class RuleEngineTest {

  private lateinit var ruleEngine: RuleEngine

  @Autowired
  private lateinit var conditionMap: Map<ConditionType, Condition<Any>>

  @Autowired
  private lateinit var actionMap: Map<ActionType, Action<Any>>

  @Autowired
  private lateinit var ruleSetsByUseCase: Map<String, RuleSetDefinition>

  private lateinit var spyConditionMap: Map<ConditionType, Condition<Any>>
  private lateinit var spyActionMap: Map<ActionType, Action<Any>>

  @BeforeEach
  fun setup() {
    spyConditionMap = conditionMap.mapValues { spyk(it.value) }
    spyActionMap = actionMap.mapValues { spyk(it.value) }
    ruleEngine = RuleEngine(ruleSetsByUseCase, spyConditionMap, spyActionMap)
  }

  @Test
  fun `should execute multiple actions for regular card when conditions match`() {
    val context = TransactionContext(type = "regular-card", amount = 10000.toBigDecimal())

    ruleEngine.executeRules(context)

    verify {
      spyActionMap[ActionType.EARN_POINT_BY_RATE]!!.execute(context, 0.02.toBigDecimal())
      spyActionMap[ActionType.EARN_POINT_BY_RATE]!!.execute(context, 0.05.toBigDecimal())
    }
  }

  @Test
  fun `should execute actions for super card when any condition matches`() {
    val context1 = TransactionContext(type = "super-card", amount = 10000.toBigDecimal())
    val context2 = TransactionContext(type = "super-card", amount = 10001.toBigDecimal())

    ruleEngine.executeRules(context1)
    ruleEngine.executeRules(context2)

    verify(exactly = 2) {
      spyActionMap[ActionType.EARN_POINT_BY_RATE]!!.execute(any(), 0.1.toBigDecimal())
    }
  }

  @Test
  fun `should not execute actions when conditions fail`() {
    val context = TransactionContext(type = "regular-card", amount = 5000.toBigDecimal())

    ruleEngine.executeRules(context)

    verify(exactly = 0) {
      spyActionMap[ActionType.EARN_POINT_BY_RATE]!!.execute(any(), any(BigDecimal::class))
    }
  }

  @Test
  fun `should execute actions for online card with not equals condition`() {
    val context = TransactionContext(type = "online-card", amount = 10000.toBigDecimal())

    ruleEngine.executeRules(context)

    verify {
      spyActionMap[ActionType.EARN_POINT_BY_RATE]!!.execute(context, 0.04.toBigDecimal())
    }
  }

  @Test
  fun `should skip execution if executor is not found`() {
    val context = TransactionContext(type = "unknown-card", amount = 10000.toBigDecimal())

    ruleEngine.executeRules(context)

    verify(exactly = 0) {
      spyActionMap[ActionType.EARN_POINT_BY_RATE]!!.execute(any(), any(BigDecimal::class))
    }
  }
}
