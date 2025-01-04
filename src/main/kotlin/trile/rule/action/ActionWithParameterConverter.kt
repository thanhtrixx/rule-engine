package trile.rule.action

import trile.common.getAndToBigDecimal


inline fun <reified T> Action<T>.convertParameter(parameters: Map<String, String>): T {
  return when {
    this.type == ActionType.EARN_POINT_BY_RATE -> parameters.getAndToBigDecimal("rate") as T
    else -> TODO()
  }
}
