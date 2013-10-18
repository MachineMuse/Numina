/**
 *
 */
package net.machinemuse.numina.network

import cpw.mods.fml.common.network.Player
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import java.io.DataInputStream
import scala.Predef._
import net.machinemuse.numina.item.ModeChangingItem
import net.machinemuse.numina.scala.OptionCast

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:28 PM, 5/6/13
 */
object MusePacketModeChangeRequest extends MusePackager {
  def read(d: DataInputStream, p: Player) = {
    val slot = readInt(d)
    val mode = readString(d)
    new MusePacketModeChangeRequest(p, mode, slot)
  }
}

class MusePacketModeChangeRequest(player: Player, mode: String, slot: Int) extends MusePacket(player) {
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
          OptionCast[ModeChangingItem](stack.getItem).map(i => i.setActiveMode(stack, mode))
        }
      }
    }
  }

}
