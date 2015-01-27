package net.machinemuse.numina.basemod

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent
import cpw.mods.fml.common.network.simpleimpl.IMessage
import net.machinemuse.numina.general.MuseLogger
import net.machinemuse.numina.mouse.MouseEventHandler
import net.machinemuse.numina.network.{MusePacketRecipeUpdate, MusePacket, PacketSender}
import net.machinemuse.numina.recipe.JSONRecipeList
import net.machinemuse.numina.render.{FOVUpdateEventHandler, RenderGameOverlayEventHandler}
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.common.MinecraftForge

import java.io.{ByteArrayInputStream, InputStream, InputStreamReader}

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
        val recipeArray = Array(recipe)
        InputStream is = new ByteArrayInputStream(JSONRecipeList.gson.toJson(recipeArray).getBytes
        PacketSender.sendTo(new MusePacketRecipeUpdate(event.player, new InputStreamReader(is)), event.player)
      }
    }
  }
}

