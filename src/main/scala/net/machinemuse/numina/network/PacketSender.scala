package net.machinemuse.numina.network

import cpw.mods.fml.common.network.FMLEmbeddedChannel
import cpw.mods.fml.common.network.FMLOutboundHandler
import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler
import cpw.mods.fml.common.network.simpleimpl.SimpleChannelHandlerWrapper
import cpw.mods.fml.common.network.simpleimpl.SimpleIndexedCodec
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
import cpw.mods.fml.relauncher.Side
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.network.{Packet, INetHandler}
import net.minecraft.server.MinecraftServer
import net.minecraft.tileentity.TileEntity
import java.io.IOException
import java.util.EnumMap

object PacketSender {
  def getPacketFrom(message: MusePacket): Packet = {
    channels.get(Side.SERVER).generatePacketFrom(message)
  }

  def sendToAll(message: MusePacket) {
    channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL)
    channels.get(Side.SERVER).writeOutbound(message)
  }

  def sendTo(message: MusePacket, player: EntityPlayerMP) {
    channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER)
    channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player)
    channels.get(Side.SERVER).writeOutbound(message)
  }

  def sendToAllAround(message: MusePacket, point: NetworkRegistry.TargetPoint) {
    channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT)
    channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point)
    channels.get(Side.SERVER).writeOutbound(message)
  }

  def sendToDimension(message: MusePacket, dimensionId: Int) {
    channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION)
    channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(new Integer(dimensionId))
    channels.get(Side.SERVER).writeOutbound(message)
  }

  def sendToServer(message: MusePacket) {
    channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER)
    channels.get(Side.CLIENT).writeOutbound(message)
  }

  def sendToAllAround(packet: MusePacket, entity: TileEntity, d: Double) {
    this.sendToAllAround(packet, new NetworkRegistry.TargetPoint(entity.getWorldObj.provider.dimensionId, entity.xCoord, entity.yCoord, entity.zCoord, d))
  }

  def sendToAllAround(packet: MusePacket, entity: Entity, d: Double) {
    this.sendToAllAround(packet, new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, d))
  }

  private var channels: EnumMap[Side, FMLEmbeddedChannel] = MusePacketHandler.channels
}