package net.machinemuse.numina.network

import java.io.DataInputStream

import net.machinemuse.numina.scala.OptionCast
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 8:50 PM, 9/6/13
 */
object MusePacketNameChangeRequest extends MusePackager {
  def read(d: DataInputStream, p: EntityPlayer) = {
    val username = readString(d)
    val newnick = readString(d)
    new MusePacketNameChangeRequest(p, username, newnick, 0)
  }
}

class MusePacketNameChangeRequest(player: EntityPlayer, username: String, newnick: String, entityID: Int) extends MusePacket {
  override val packager = MusePacketNameChangeRequest

  override def write() {
    writeString(username)
    writeString(newnick)
  }

  @SideOnly(Side.CLIENT)
  override def handleClient(player: EntityPlayerSP) {
//    NicknameMap.removeName(username)
//    NicknameMap.putName(username, newnick)

    OptionCast[EntityPlayer](player.worldObj.getEntityByID(entityID)) map {
      t => t.refreshDisplayName()
    }
  }
}
