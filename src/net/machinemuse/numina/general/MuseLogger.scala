package net.machinemuse.numina.general


import java.util.logging.Logger

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.FMLLog
import net.machinemuse.numina.basemod.NuminaConfig

/**
 * Logger access class. May become more fleshed out in the future.
 *
 * @author MachineMuse
 *
 */
object MuseLogger {
  val logger = Logger.getLogger("MachineMuse")
  logger.setParent(FMLLog.getLogger)

  def logDebug(string: String) = {
    var debugging = true
    try {
      if (!NuminaConfig.isDebugging) debugging = false
    } catch {
      case _: Exception =>
    }
    if (debugging) logger.info("[" + FMLCommonHandler.instance().getEffectiveSide + "] " + string)
    None
  }

  def logError(string: String) = {
    logger.warning("[" + FMLCommonHandler.instance().getEffectiveSide + "] " + string)
    None
  }

  def logInfo(string:String) = {
    logger.info(string)
    None
  }

  def logException(string: String, exception: Throwable) = {
    logger.warning("[" + FMLCommonHandler.instance().getEffectiveSide + "] " + string)
    exception.printStackTrace()
    None
  }
}
