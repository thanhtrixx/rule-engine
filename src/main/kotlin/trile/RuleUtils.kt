package trile

object RuleUtils {


}

fun <V> Map<String, V>?.getOrDefault(key: String, default: V) =
  if (this == null || !this.containsKey(key)) default
  else this[key]

fun <V> Map<String, V>?.getOrThrow(key: String) = when {
  this == null -> throw RuntimeException("Map is null")
  !this.containsKey(key) -> throw RuntimeException("Map does not contain key $key")
  else -> this[key]
}

fun main() {
  val nullMap: Map<String, String>? = null
  val emptyMap = emptyMap<String, String>()
  val map = mapOf("key" to "value")

  println(
    try {
      nullMap.getOrThrow("a")
    } catch (e: RuntimeException) {
      "ERROR $e"
    }
  )

  println(
    try {
      emptyMap.getOrThrow("a")
    } catch (e: RuntimeException) {
      "ERROR $e"
    }
  )

  println(
    try {
      map.getOrThrow("a")
    } catch (e: RuntimeException) {
      "ERROR $e"
    }
  )

  println(
    try {
      nullMap.getOrDefault("a", "DEFAULT")
    } catch (e: RuntimeException) {
      "ERROR $e"
    }
  )

  println(
    try {
      emptyMap.getOrDefault("a", "DEFAULT")
    } catch (e: RuntimeException) {
      "ERROR $e"
    }
  )

  println(
    try {
      map.getOrDefault("a", "DEFAULT")
    } catch (e: RuntimeException) {
      "ERROR $e"
    }
  )

  println(
    try {
      map.getOrDefault("key", "DEFAULT")
    } catch (e: RuntimeException) {
      "ERROR $e"
    }
  )
}
