package net.machinemuse.numina.basemod

import cpw.mods.fml.common.{FMLCommonHandler, SidedProxy, Mod}
import cpw.mods.fml.common.event._
import java.io.File
import net.machinemuse.numina.network.NuminaPackets
import net.machinemuse.numina.recipe.JSONRecipeList
import net.machinemuse.numina.sound.proxy.{Musique, MusiqueCommon}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:06 AM, 6/18/13
 */
@Mod(modid = "numina", modLanguage = "scala")
object Numina {
  @SidedProxy(clientSide = "net.machinemuse.numina.basemod.NuminaProxyClient", serverSide = "net.machinemuse.numina.basemod.NuminaProxyServer")
  var proxy: NuminaProxy = null

  @SidedProxy(clientSide = "net.machinemuse.numina.sound.proxy.MusiqueClient", serverSide = "net.machinemuse.numina.sound.proxy.MusiqueServer")
  var musique: MusiqueCommon = null

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
    NuminaPackets.init
    Musique.init(musique)
  }

  @Mod.EventHandler def postinit(e: FMLPostInitializationEvent) {
    proxy.PostInit()
  }
  
  @Mod.EventHandler def serverstarting(e: FMLServerStartingEvent) {
    //JSONRecipeList.loadRecipesFromDir(configDir.getAbsolutePath + "/machinemuse/recipes")
    JSONRecipeList.loadRecipesFromDir(configDir.getAbsolutePath + "/machinemuse/recipes/test.recipes")
  }

  @Mod.EventHandler def serverstart(e: FMLServerStartedEvent) {
    FMLCommonHandler.instance().bus().register(NuminaPlayerTracker)
  }
}
