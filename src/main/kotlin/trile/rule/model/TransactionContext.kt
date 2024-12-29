package trile.rule.model

import java.math.BigDecimal
import trile.rule.model.Constants.Companion.EMPTY_MAP
import trile.rule.model.Constants.Companion.EMPTY_STRING

data class TransactionContext(
  val type: String,
  val amount: BigDecimal,
  val channel: String = EMPTY_STRING,
  val category: String = EMPTY_STRING,
  val additionalInfo: Map<String, String> = EMPTY_MAP
)
