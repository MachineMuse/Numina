package net.machinemuse.numina.network

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.network.{Packet, INetHandler}
import net.minecraft.server.MinecraftServer
import net.minecraft.tileentity.TileEntity
import java.io.IOException
import java.util.EnumMap

import net.minecraftforge.fml.common.network.{NetworkRegistry, FMLEmbeddedChannel, FMLOutboundHandler}
import net.minecraftforge.fml.relauncher.Side

object PacketSender {
  def getPacketFrom(message: MusePacket): Packet[_] = {
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
    this.sendToAllAround(packet, new NetworkRegistry.TargetPoint(entity.getWorld.provider.getDimension, entity.getPos.getX, entity.getPos.getY, entity.getPos.getZ, d))
  }

  def sendToAllAround(packet: MusePacket, entity: Entity, d: Double) {
    this.sendToAllAround(packet, new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, d))
  }

  private var channels: EnumMap[Side, FMLEmbeddedChannel] = MusePacketHandler.channels
}