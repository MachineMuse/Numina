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
    isDebugging
    useSounds
    config.save()
  }

  def useSounds = config.get(Configuration.CATEGORY_GENERAL, "Use Sounds", true).getBoolean(true)


  def isDebugging = config.get(Configuration.CATEGORY_GENERAL, "Debugging info", false).getBoolean(false)

}
