package net.machinemuse.numina.network.message

import cpw.mods.fml.common.network.simpleimpl.{MessageContext, IMessageHandler, IMessage}
import io.netty.buffer.ByteBuf
import net.machinemuse.numina.item.ModeChangingItem
import net.machinemuse.numina.scala.OptionCast
import net.minecraft.entity.player.EntityPlayerMP

class MusePacketModeChangeRequest(slot: Int, mode: String) extends IMessage with IMessageHandler[MusePacketModeChangeRequest, IMessage] {
  var slotNew: Int = 0
  var modeNew: String = ""

  override def fromBytes(buf: ByteBuf) = {
    slotNew = buf.readInt()
    val length = buf.readInt()
    modeNew = new String(buf.readBytes(length).array())
  }

  override def toBytes(buf: ByteBuf) = {
    buf.writeInt(slot)
    buf.writeInt(mode.length)
    buf.writeBytes(mode.getBytes)
  }

  override def onMessage(message: MusePacketModeChangeRequest, ctx: MessageContext): IMessage = {
    val player: EntityPlayerMP = ctx.getServerHandler.playerEntity
    if (slotNew > -1 && slotNew < 9) {
      for {
        stack <- Option(player.inventory.mainInventory(slotNew))
        item <- OptionCast[ModeChangingItem](stack.getItem)
      } {
        val modes = item.getValidModes(stack, player)
        if (modes.contains(modeNew)) {
          OptionCast[ModeChangingItem](stack.getItem).map(i => i.setActiveMode(stack, modeNew))
        }
      }
    }
    null
  }
}
