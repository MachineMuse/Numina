package net.machinemuse.numina.basemod

import net.minecraftforge.common.Configuration
import java.io.File
import cpw.mods.fml.common.event.FMLPreInitializationEvent

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
    useSounds
    useFOVFix
    config.save()
  }

  def useSounds = getConfigBoolean(Configuration.CATEGORY_GENERAL, "Use Sounds", default = true)

  def useFOVFix = getConfigBoolean(Configuration.CATEGORY_GENERAL, "Ignore speed boosts for field of view", default = true)

  def isDebugging = getConfigBoolean(Configuration.CATEGORY_GENERAL, "Debugging info", default = false)

  def getConfigBoolean(category: String, name: String, default: Boolean) = {
    val ret = config.get(category, name, default)
    ret.getBoolean(default)
  }
}
