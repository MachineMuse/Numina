package net.machinemuse.numina.basemod

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.PlayerEvent.{PlayerRespawnEvent, PlayerChangedDimensionEvent, PlayerLoggedOutEvent, PlayerLoggedInEvent}
import cpw.mods.fml.common.network.simpleimpl.IMessage
import net.machinemuse.numina.network.message.MusePacketRecipeUpdate
import net.minecraftforge.common.MinecraftForge
import net.machinemuse.numina.mouse.MouseEventHandler
import net.machinemuse.numina.render.{FOVUpdateEventHandler, RenderGameOverlayEventHandler}
import net.machinemuse.numina.network.MusePacketHandler
import net.minecraft.entity.player.EntityPlayerMP
import net.machinemuse.numina.recipe.JSONRecipeList
import cpw.mods.fml.common.FMLCommonHandler
import net.machinemuse.numina.general.MuseLogger

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:57 AM, 9/3/13
 */
trait NuminaProxy {
  def PreInit() = {}

  def Init() = {}

  def PostInit() = {}

  @deprecated(message = "Use the SimpleNetworkWrapper instance directly", since = "MC 1.7")
  def sendPacketToClient(packet: IMessage, player: EntityPlayerMP) = {
    MusePacketHandler.INSTANCE.sendTo(packet, player)
  }

  @deprecated(message = "Use the SimpleNetworkWrapper instance directly", since = "MC 1.7")
  def sendPacketToServer(packet: IMessage) = {
    MusePacketHandler.INSTANCE.sendToServer(packet)
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
  @SubscribeEvent
  def onPlayerLogin(event: PlayerLoggedInEvent) {
    if(!FMLCommonHandler.instance().getMinecraftServerInstance.isSinglePlayer) {
      for (recipe <- JSONRecipeList.getJSONRecipesList.toArray) {
        val recipeArray = Array(recipe)
        val recipeAsString: String = JSONRecipeList.gson.toJson(recipeArray)
        MusePacketHandler.INSTANCE.sendTo(new MusePacketRecipeUpdate(recipeAsString), event.player.asInstanceOf[EntityPlayerMP])
      }
    }
  }

  @SubscribeEvent
  def onPlayerLogout(event: PlayerLoggedOutEvent) {}

  @SubscribeEvent
  def onPlayerChangedDimension(event: PlayerChangedDimensionEvent) {}

  @SubscribeEvent
  def onPlayerRespawn(event: PlayerRespawnEvent) {}
}

