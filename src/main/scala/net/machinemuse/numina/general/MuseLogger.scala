package net.machinemuse.numina.general

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.FMLLog
import net.machinemuse.numina.basemod.NuminaConfig
import org.apache.logging.log4j.{Logger, LogManager}

/**
 * Logger access class. May become more fleshed out in the future.
 *
 * @author MachineMuse
 *
 */
object MuseLogger {
  val logger: Logger = LogManager.getLogger("Numina")

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
    logger.error(string)
    None
  }

  def logInfo(string:String) = {
    logger.info(string)
    None
  }

  def logException(string: String, exception: Throwable) = {
    logger.error(string)
    exception.printStackTrace()
    None
  }
}
