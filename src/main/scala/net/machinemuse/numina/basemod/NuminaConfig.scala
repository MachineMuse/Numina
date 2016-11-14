package net.machinemuse.numina.basemod

import java.io.File

import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.machinemuse.numina.general.MuseLogger
import net.minecraftforge.common.config.Configuration

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:31 AM, 9/3/13
 */
object NuminaConfig {

  var config: Configuration = null

  def init(event: FMLPreInitializationEvent) {
    if (config == null) {
      val configFile: File = new File(event.getModConfigurationDirectory + "/machinemuse/numina.cfg")
      config = new Configuration(configFile)
    }
    // Initialize config file
    isDebugging
    useFOVFix
    useSounds
    config.save()
  }

  var hasWarned = false

  def warnOnce(s:String) = {
    if(!hasWarned) {
      MuseLogger.logError("WARNING: unlocalizedName is deprecated; please use registryName or itemStackName instead!")
      hasWarned=true
    }
  }

  def fovFixDefaultState = getConfigBoolean(Configuration.CATEGORY_GENERAL, "Default state of FOVfix on login (enabled = true, disabled = false)", default = true)

  def useFOVFix = getConfigBoolean(Configuration.CATEGORY_GENERAL, "Ignore speed boosts for field of view", default = true)

  def isDebugging = getConfigBoolean(Configuration.CATEGORY_GENERAL, "Debugging info", default = false)

  def useSounds = getConfigBoolean(Configuration.CATEGORY_GENERAL, "Use sounds", default = true)

  def getConfigBoolean(category: String, name: String, default: Boolean) = {
    val ret = config.get(category, name, default)
    ret.getBoolean(default)
  }
}
