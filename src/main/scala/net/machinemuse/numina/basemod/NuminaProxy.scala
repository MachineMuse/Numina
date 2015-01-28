package net.machinemuse.numina.basemod

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent
import cpw.mods.fml.common.network.simpleimpl.IMessage
import cpw.mods.fml.common.Loader;
import net.machinemuse.numina.general.MuseLogger
import net.machinemuse.numina.mouse.MouseEventHandler
import net.machinemuse.numina.network.{MusePacketRecipeUpdate, MusePacket, PacketSender}
import net.machinemuse.numina.recipe.JSONRecipeList
import net.machinemuse.numina.render.{FOVUpdateEventHandler, RenderGameOverlayEventHandler}
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.common.MinecraftForge

import java.io.{ByteArrayOutputStream, ByteArrayInputStream, OutputStreamWriter}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:57 AM, 9/3/13
 */
trait NuminaProxy {
  def PreInit() = {}

  def Init() = {}

  def PostInit() = {}

  @deprecated(message = "Use the PacketHandler INSTANCE directly", since = "MC 1.7.10")
  def sendPacketToClient(packet: MusePacket, player: EntityPlayerMP) = {
    PacketSender.sendTo(packet, player)
  }

  @deprecated(message = "Use the PacketHandler INSTANCE directly", since = "MC 1.7.10")
  def sendPacketToServer(packet: MusePacket) = {
    PacketSender.sendToServer(packet)
  }
}

class NuminaProxyClient extends NuminaProxy {
  override def PreInit() = {
  	import scala.util.control.Breaks._
    import scala.collection.JavaConversions._
    
    val iter = com.google.common.reflect.ClassPath.from(ClassLoader.getSystemClassLoader).getAllClasses
    while (iter.hasNext) {
    	val c = iter.next
    	if ( c.getName == "OggAudioData") break
    	
    	if ( !iter.hasNext ) throw new net.machinemuse.numina.gui.OggAudioDataRequiredDisplayException
    }
    /*System.out.println(com.google.common.reflect.Reflection.getPackageName("com.qmxtech.oggaudiodata.OggAudioData"))
    val m: java.lang.reflect.Method = classOf[ClassLoader].getDeclaredMethod("findLoadedClass", classOf[String] )
    m.setAccessible(true)
	  if (m.invoke(ClassLoader.getSystemClassLoader, "com.qmxtech.oggaudiodata.OggAudioData") == null) {
        throw new net.machinemuse.numina.gui.OggAudioDataRequiredDisplayException
    }*/
  }
  override def Init() = {
    MuseLogger.logDebug("Client Proxy Started")
    MinecraftForge.EVENT_BUS.register(MouseEventHandler)
    MinecraftForge.EVENT_BUS.register(RenderGameOverlayEventHandler)
    MinecraftForge.EVENT_BUS.register(FOVUpdateEventHandler)
  }
}

class NuminaProxyServer extends NuminaProxy

object NuminaPlayerTracker {
  @SubscribeEvent def onPlayerLogin(event: PlayerLoggedInEvent) {
    if (!FMLCommonHandler.instance().getMinecraftServerInstance.isSinglePlayer) {
      for (recipe <- JSONRecipeList.getJSONRecipesList.toArray) {
        val os: ByteArrayOutputStream = new ByteArrayOutputStream()
        val writer: OutputStreamWriter = new OutputStreamWriter(os)
        val recipeArray = Array(recipe)
        
        JSONRecipeList.gson.toJson(recipeArray, writer)

        PacketSender.sendTo(new MusePacketRecipeUpdate(event.player, new ByteArrayInputStream(os.toByteArray)), event.player.asInstanceOf[EntityPlayerMP])
      }
    }
  }
}

