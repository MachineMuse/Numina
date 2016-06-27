package net.machinemuse.numina.basemod

import java.io.File

import net.machinemuse.numina.network.NuminaPackets
import net.machinemuse.numina.recipe.JSONRecipeList
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent, FMLServerStartedEvent}
import net.minecraftforge.fml.common.{FMLCommonHandler, Mod, SidedProxy}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:06 AM, 6/18/13
 */
@Mod(modid = "numina", modLanguage = "scala")
object Numina {
  @SidedProxy(clientSide = "net.machinemuse.numina.basemod.NuminaProxyClient", serverSide = "net.machinemuse.numina.basemod.NuminaProxyServer")
  var proxy: NuminaProxy = null
  var configDir: java.io.File = null

  @Mod.EventHandler def preinit(e: FMLPreInitializationEvent) {
    NuminaConfig.init(e)
    configDir = e.getModConfigurationDirectory

    val recipesFolder = new File(configDir, "machinemuse/recipes")
    recipesFolder.mkdirs()
    recipesFolder.mkdir()
    //MinecraftForge.EVENT_BUS.register(PlayerTickHandler)
    //    MinecraftForge.EVENT_BUS.register(DeathEventHandler)
    //    NetworkRegistry.instance.registerGuiHandler(Numina, NuminaGuiHandler)
    proxy.PreInit()
  }

  @Mod.EventHandler def init(e: FMLInitializationEvent) {
    proxy.Init()
    NuminaPackets.init()
  }

  @Mod.EventHandler def postinit(e: FMLPostInitializationEvent) {
    proxy.PostInit()
  }

  @Mod.EventHandler def serverstart(e: FMLServerStartedEvent) {
    JSONRecipeList.loadRecipesFromDir(Numina.configDir.toString + "/machinemuse/recipes/")
    FMLCommonHandler.instance().bus().register(NuminaPlayerTracker)
  }
}
