package net.machinemuse.numina.basemod

import net.minecraftforge.common.MinecraftForge
import net.machinemuse.numina.mouse.MouseEventHandler
import net.machinemuse.numina.render.{FOVUpdateEventHandler, RenderGameOverlayEventHandler}
import net.machinemuse.numina.network.MusePacket
import net.minecraft.entity.player.{EntityPlayerMP, EntityPlayer}
import net.minecraft.client.Minecraft

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:57 AM, 9/3/13
 */
trait NuminaProxy {
  def PreInit() = {}

  def Init() = {}

  def PostInit() = {}

  def sendPacketToClient(packet:MusePacket, player:EntityPlayer) = {
    player.asInstanceOf[EntityPlayerMP].playerNetServerHandler.sendPacketToPlayer(packet.getPacket131)
  }
  def sendPacketToServer(packet:MusePacket) = {
    Minecraft.getMinecraft.thePlayer.sendQueue.addToSendQueue(packet.getPacket131)
  }
}

object NuminaProxyClient extends NuminaProxy {
  override def Init() = {
    MinecraftForge.EVENT_BUS.register(MouseEventHandler)
    MinecraftForge.EVENT_BUS.register(RenderGameOverlayEventHandler)
    MinecraftForge.EVENT_BUS.register(FOVUpdateEventHandler)
  }
}

object NuminaProxyServer extends NuminaProxy {
}