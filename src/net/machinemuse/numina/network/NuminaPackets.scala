package net.machinemuse.numina.network

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 9:20 PM, 9/6/13
 */
object NuminaPackets {
  def init() {
    MusePacketHandler.packagers.put(20, MusePacketNameChangeRequest)
  }
}
