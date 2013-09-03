package net.machinemuse.numina.basemod

import cpw.mods.fml.common.{FMLCommonHandler, Mod}
import cpw.mods.fml.common.network.NetworkMod
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.common.Mod.{ServerStarting, PostInit, Init, PreInit}
import cpw.mods.fml.common.event.{FMLServerStartingEvent, FMLPostInitializationEvent, FMLInitializationEvent, FMLPreInitializationEvent}
import net.machinemuse.numina.general.MuseLogger
import net.machinemuse.numina.command.{CommandNick, Commander}
import net.minecraftforge.common.MinecraftForge

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:06 AM, 6/18/13
 */
@Mod(modid = "Numina", modLanguage = "scala")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, tinyPacketHandler = classOf[NuminaPacketHandler])
object Numina {
  //@SidedProxy(clientSide = "net.machinemuse.anima.ClientProxy", serverSide = "net.machinemuse.anima.ServerProxy")
  var proxy: NuminaProxy = if (FMLCommonHandler.instance().getSide == Side.CLIENT) NuminaProxyClient else NuminaProxyServer

  @Mod.EventHandler def preinit(e: FMLPreInitializationEvent) {
    NuminaConfig.init(e)
    MuseLogger.logDebug("test")
    proxy.PreInit()
  }

  @Mod.EventHandler def init(e: FMLInitializationEvent) {
    proxy.Init()
  }

  @Mod.EventHandler def postinit(e: FMLPostInitializationEvent) {
    proxy.PostInit()
  }

  @Mod.EventHandler def serverstart(e: FMLServerStartingEvent) {
    Commander.init()
  }
}
