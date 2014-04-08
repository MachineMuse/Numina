/**
 *
 */
package net.machinemuse.numina.network

import cpw.mods.fml.common.network.{ITinyPacketHandler, IPacketHandler, NetworkRegistry, Player}
import net.minecraft.client.entity.EntityClientPlayerMP
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.network.{NetServerHandler, INetworkManager}
import net.minecraft.network.packet.{Packet131MapData, NetHandler, Packet250CustomPayload}
import java.io._
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.Side
import net.machinemuse.numina.general.MuseLogger
import net.machinemuse.numina.scala.MuseNumericRegistry

/**
 * @author MachineMuse
 */
object MusePacketHandler {
  val networkChannelName: String = "Numina"
  val packagers = new MuseNumericRegistry[MusePackager]
}

class MusePacketHandler extends IPacketHandler with ITinyPacketHandler {
  NetworkRegistry.instance.registerChannel(this, MusePacketHandler.networkChannelName)

  override def onPacketData(manager: INetworkManager, payload: Packet250CustomPayload, player: Player) {
    if (payload.channel == MusePacketHandler.networkChannelName) {
      val repackaged = repackage250(payload, player)
      repackaged map {
        packet => FMLCommonHandler.instance.getEffectiveSide match {
          case Side.SERVER => packet handleServer player.asInstanceOf[EntityPlayerMP]
          case Side.CLIENT => packet handleClient player.asInstanceOf[EntityClientPlayerMP]
        }
      }
    }
  }

  def repackage250(payload: Packet250CustomPayload, player: Player): Option[MusePacket] = {
    val data: DataInputStream = new DataInputStream(new ByteArrayInputStream(payload.data))
    var packetType: Int = 0
    try {
      packetType = data.readInt
      MusePacketHandler.packagers.get(packetType).map(packager => packager.read(data, player))
    } catch {
      case e: IOException => {
        MuseLogger.logException("PROBLEM READING PACKET250 D:", e)
      }
    }
  }

  override def handle(handler: NetHandler, payload: Packet131MapData) {
    val data: DataInputStream = new DataInputStream(new ByteArrayInputStream(payload.itemData))
    try {
      val packetType: Int = payload.uniqueID
      val player = handler.getPlayer
      val repackaged = MusePacketHandler.packagers.get(packetType).map(packager => packager.read(data, player.asInstanceOf[Player]))
      repackaged map {
        packet => FMLCommonHandler.instance.getEffectiveSide match {
          case Side.SERVER => packet handleServer player.asInstanceOf[EntityPlayerMP]
          case Side.CLIENT => packet handleClient player.asInstanceOf[EntityClientPlayerMP]
        }
      }
    } catch {
      case e: IOException => {
        MuseLogger.logException("PROBLEM READING PACKET131 D:", e)
      }
    }
  }

}

