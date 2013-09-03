package net.machinemuse.numina.basemod

import cpw.mods.fml.common.network.ITinyPacketHandler
import net.minecraft.network.packet.{Packet131MapData, NetHandler}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:56 AM, 9/3/13
 */
class NuminaPacketHandler  extends ITinyPacketHandler {
  def handle(handler: NetHandler, mapData: Packet131MapData) {}
}
