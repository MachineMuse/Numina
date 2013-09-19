package net.machinemuse.numina.basemod

import cpw.mods.fml.common.{FMLCommonHandler, Mod}
import cpw.mods.fml.common.network.NetworkMod
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.common.event.{FMLServerStartingEvent, FMLPostInitializationEvent, FMLInitializationEvent, FMLPreInitializationEvent}
import net.machinemuse.numina.general.MuseLogger
import net.machinemuse.numina.command.Commander
import net.machinemuse.numina.network.{NuminaPackets, MusePacketHandler}
import net.minecraftforge.common.MinecraftForge
import net.machinemuse.numina.player.PlayerTickHandler

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:06 AM, 6/18/13
 */
@Mod(modid = "numina", modLanguage = "scala")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, tinyPacketHandler = classOf[MusePacketHandler])
object Numina {
  //@SidedProxy(clientSide = "net.machinemuse.anima.ClientProxy", serverSide = "net.machinemuse.anima.ServerProxy")
  var proxy: NuminaProxy = if (FMLCommonHandler.instance().getSide == Side.CLIENT) NuminaProxyClient else NuminaProxyServer

  @Mod.EventHandler def preinit(e: FMLPreInitializationEvent) {
    NuminaConfig.init(e)
    MuseLogger.logDebug("test")
    MinecraftForge.EVENT_BUS.register(PlayerTickHandler)
    proxy.PreInit()
  }

  @Mod.EventHandler def init(e: FMLInitializationEvent) {
    proxy.Init()
    NuminaPackets.init()
  }

  @Mod.EventHandler def postinit(e: FMLPostInitializationEvent) {
    proxy.PostInit()
  }

  @Mod.EventHandler def serverstart(e: FMLServerStartingEvent) {
    Commander.init()
  }
}
