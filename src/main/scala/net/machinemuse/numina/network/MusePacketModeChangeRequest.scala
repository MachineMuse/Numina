/**
 *
 */
package net.machinemuse.numina.network

import java.io.DataInputStream

import net.machinemuse.numina.item.ModeChangingItem
import net.machinemuse.numina.scala.OptionCast
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:28 PM, 5/6/13
 */
object MusePacketModeChangeRequest extends MusePackager {
  def read(d: DataInputStream, p: EntityPlayer) = {
    val slot = readInt(d)
    val mode = readString(d)
    new MusePacketModeChangeRequest(p, mode, slot)
  }
}

class MusePacketModeChangeRequest(player: EntityPlayer, mode: String, slot: Int) extends MusePacket {
  val packager = MusePacketModeChangeRequest

  def write {
    writeInt(slot)
    writeString(mode)
  }

  override def handleServer(player: EntityPlayerMP) {
    if (slot > -1 && slot < 9) {
      for {
        stack <- Option(player.inventory.mainInventory(slot))
        item <- OptionCast[ModeChangingItem](stack.getItem)
      } {
        val modes = item.getValidModes(stack, player)
        if (modes.contains(mode)) {
          OptionCast[ModeChangingItem](stack.getItem).foreach(i => i.setActiveMode(stack, mode))
        }
      }
    }
  }

}
