package net.machinemuse.numina.general

import net.machinemuse.numina.basemod.NuminaConfig
import org.apache.logging.log4j.LogManager

/**
 * Logger access class. May become more fleshed out in the future.
 *
 * @author MachineMuse
 *
 */
object MuseLogger {
  val logger = LogManager.getLogger("MachineMuse")

  def logDebug(string: String) = {
    var debugging = true
    try {
      if (!NuminaConfig.isDebugging) debugging = false
    } catch {
      case _: Exception =>
    }
    if (debugging) logger.info(string)
    None
  }

  def logError(string: String) = {
    logger.warn(string)
    None
  }

  def logInfo(string:String) = {
    logger.info(string)
    None
  }

  def logException(string: String, exception: Throwable) = {
    logger.warn(string)
    exception.printStackTrace()
    None
  }
}
