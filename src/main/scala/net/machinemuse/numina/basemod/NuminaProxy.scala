package net.machinemuse.numina.basemod

import net.machinemuse.numina.general.MuseLogger
import net.machinemuse.numina.mouse.MouseEventHandler
import net.machinemuse.numina.network.{MusePacket, MusePacketRecipeUpdate, PacketSender}
import net.machinemuse.numina.recipe.JSONRecipeList
import net.machinemuse.numina.render.{FOVUpdateEventHandler, FOVUpdateToggleKeyHandler, RenderGameOverlayEventHandler}
import net.minecraft.client.settings.KeyBinding
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

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
    MinecraftForge.EVENT_BUS.register(FOVUpdateToggleKeyHandler)
    MinecraftForge.EVENT_BUS.register(FOVUpdateEventHandler)
  }
}

class NuminaProxyServer extends NuminaProxy

object NuminaPlayerTracker {
  @SubscribeEvent def onPlayerLogin(event: PlayerLoggedInEvent) {
    if (!FMLCommonHandler.instance().getMinecraftServerInstance.isSinglePlayer) {
      for (recipe <- JSONRecipeList.getJSONRecipesList.toArray) {
        val recipeArray = Array(recipe)
        val recipeAsString: String = JSONRecipeList.gson.toJson(recipeArray)
        PacketSender.sendTo(new MusePacketRecipeUpdate(event.player, recipeAsString), event.player.asInstanceOf[EntityPlayerMP])
      }
    }
  }
}

