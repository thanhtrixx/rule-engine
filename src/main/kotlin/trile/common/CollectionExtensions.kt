package trile.common

import java.math.BigDecimal

object CollectionExtensions {


}

fun <V> Map<String, V>?.getOrDefault(key: String, default: V) =
  if (this == null || !this.containsKey(key)) default
  else this[key]

fun <V> Map<String, V>?.getOrThrow(key: String, errorMessage: String = "Map does not contains $key"): V =
  if (this == null) {
    throw RuntimeException("Map is null")
  } else {
    this[key] ?: throw RuntimeException(errorMessage)
  }

fun <K, V> Map<K, V>.getOrThrow(key: K, errorMessage: String = "Map does not contains $key"): V =
  this[key] ?: throw RuntimeException(errorMessage)

fun Map<String, String>?.getAndToBigDecimal(key: String): BigDecimal = BigDecimal(this.getOrThrow(key))
fun Map<String, String>?.getAndToFloat(key: String): Float = this.getOrThrow(key).toFloat()

//fun main() {
//  val nullMap: Map<String, String>? = null
//  val emptyMap = emptyMap<String, String>()
//  val map = mapOf("key" to "value")
//
//  println(
//    try {
//      nullMap.getOrThrow("a")
//    } catch (e: RuntimeException) {
//      "ERROR $e"
//    }
//  )
//
//  println(
//    try {
//      emptyMap.getOrThrow("a")
//    } catch (e: RuntimeException) {
//      "ERROR $e"
//    }
//  )
//
//  println(
//    try {
//      map.getOrThrow("a")
//    } catch (e: RuntimeException) {
//      "ERROR $e"
//    }
//  )
//
//  println(
//    try {
//      nullMap.getOrDefault("a", "DEFAULT")
//    } catch (e: RuntimeException) {
//      "ERROR $e"
//    }
//  )
//
//  println(
//    try {
//      emptyMap.getOrDefault("a", "DEFAULT")
//    } catch (e: RuntimeException) {
//      "ERROR $e"
//    }
//  )
//
//  println(
//    try {
//      map.getOrDefault("a", "DEFAULT")
//    } catch (e: RuntimeException) {
//      "ERROR $e"
//    }
//  )
//
//  println(
//    try {
//      map.getOrDefault("key", "DEFAULT")
//    } catch (e: RuntimeException) {
//      "ERROR $e"
//    }
//  )
//}
