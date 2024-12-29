package trile.common

import org.apache.logging.log4j.kotlin.cachedLoggerOf

abstract class Log {
  protected val l
    get() = cachedLoggerOf(this.javaClass)
}
