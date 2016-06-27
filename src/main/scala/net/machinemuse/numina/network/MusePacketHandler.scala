/**
 *
 */
package net.machinemuse.numina.network

import java.io._
import java.util

import io.netty.buffer.ByteBufInputStream
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageCodec
import net.machinemuse.numina.general.MuseLogger
import net.machinemuse.numina.scala.MuseNumericRegistry
import net.minecraft.client.Minecraft
import net.minecraft.client.network.NetHandlerPlayClient
import net.minecraft.network.NetHandlerPlayServer
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket

/**
 * @author MachineMuse
 */
@Sharable
object MusePacketHandler extends MessageToMessageCodec[FMLProxyPacket, MusePacket]{
  val networkChannelName: String = "Numina"
  val packagers = new MuseNumericRegistry[MusePackager]
  val channels = NetworkRegistry.INSTANCE.newChannel(networkChannelName, this)


  override def decode(ctx: ChannelHandlerContext, msg: FMLProxyPacket, out: util.List[AnyRef]): Unit = {
    val data = new DataInputStream(new ByteBufInputStream(msg.payload))
    var packetType: Int = 0
    try {
      msg.handler match {
        case h: NetHandlerPlayServer =>
          val player = h.playerEntity
          packetType = data.readInt
          for {// For comprehension for an option
            packager <- MusePacketHandler.packagers.get(packetType)
            packet = packager.read(data, player)
          } {
            packet handleServer player
          }
        case h: NetHandlerPlayClient =>
          val player = Minecraft.getMinecraft.thePlayer
          packetType = data.readInt
          for {// For comprehension for an option
            packager <- MusePacketHandler.packagers.get(packetType)
            packet = packager.read(data, player)
          } {
            packet handleClient player
          }
      }
    } catch {
      case e: IOException => MuseLogger.logException("PROBLEM READING PACKET IN DECODE STEP D:", e)
    }
  }

  override def encode(ctx: ChannelHandlerContext, msg: MusePacket, out: util.List[AnyRef]) = {
    out.add(msg.getFMLProxyPacket)
  }

}

