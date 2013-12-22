package net.machinemuse.numina.basemod

import net.minecraftforge.common.MinecraftForge
import net.machinemuse.numina.mouse.MouseEventHandler
import net.machinemuse.numina.render.{FOVUpdateEventHandler, RenderGameOverlayEventHandler}
import net.machinemuse.numina.network.{MusePacketRecipeUpdate, MusePacket}
import net.minecraft.entity.player.{EntityPlayerMP, EntityPlayer}
import net.minecraft.client.Minecraft
import net.machinemuse.numina.recipe.JSONRecipeList
import cpw.mods.fml.common.{FMLCommonHandler, IPlayerTracker}
import cpw.mods.fml.common.network.{IConnectionHandler, Player, PacketDispatcher}
import net.machinemuse.numina.general.MuseLogger
import net.minecraft.network.packet.{Packet1Login, NetHandler}
import net.minecraft.network.{NetLoginHandler, INetworkManager}
import net.minecraft.server.MinecraftServer
import cpw.mods.fml.relauncher.Side

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:57 AM, 9/3/13
 */
trait NuminaProxy {
  def PreInit() = {}

  def Init() = {}

  def PostInit() = {}

  def sendPacketToClient(packet: MusePacket, player: EntityPlayer) = {
    player.asInstanceOf[EntityPlayerMP].playerNetServerHandler.sendPacketToPlayer(packet.getPacket131)
  }

  def sendPacketToServer(packet: MusePacket) = {
    Minecraft.getMinecraft.thePlayer.sendQueue.addToSendQueue(packet.getPacket131)
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

object NuminaPlayerTracker extends IPlayerTracker {
  def onPlayerLogin(player: EntityPlayer) {}

  def onPlayerLogout(player: EntityPlayer) {}

  def onPlayerChangedDimension(player: EntityPlayer) {}

  def onPlayerRespawn(player: EntityPlayer) {}
}

object NuminaConnectionTracker extends IConnectionHandler {

  def playerLoggedIn(player: Player, netHandler: NetHandler, manager: INetworkManager): Unit = {
    if(FMLCommonHandler.instance().getEffectiveSide == Side.SERVER) {
      for (recipe <- JSONRecipeList.getJSONRecipesList.toArray) {
        val recipeArray = Array(recipe)
        val recipeAsString: String = JSONRecipeList.gson.toJson(recipeArray)
        PacketDispatcher.sendPacketToPlayer(
          new MusePacketRecipeUpdate(player, recipeAsString).getPacket131,
          player
        )
      }
    }
  }

  def connectionReceived(netHandler: NetLoginHandler, manager: INetworkManager): String = "" // Return non-empty to refuse the connection

  def connectionOpened(netClientHandler: NetHandler, server: String, port: Int, manager: INetworkManager): Unit = {}

  def connectionOpened(netClientHandler: NetHandler, server: MinecraftServer, manager: INetworkManager): Unit = {}

  def connectionClosed(manager: INetworkManager): Unit = {}

  def clientLoggedIn(clientHandler: NetHandler, manager: INetworkManager, login: Packet1Login): Unit = {}
}

