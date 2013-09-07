package net.machinemuse.numina.network

import java.io.DataInputStream
import cpw.mods.fml.common.network.Player
import net.minecraft.client.entity.EntityClientPlayerMP
import net.machinemuse.numina.command.{NicknameMap, CommandNick}
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.command.PlayerSelector
import net.minecraft.entity.player.EntityPlayer

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 8:50 PM, 9/6/13
 */
object MusePacketNameChangeRequest extends MusePackager {
  def read(d: DataInputStream, p: Player) = {
    val username = readString(d)
    val newnick = readString(d)
    new MusePacketNameChangeRequest(p, username, newnick)
  }
}

class MusePacketNameChangeRequest(player: Player, username: String, newnick: String) extends MusePacket(player) {
  override val packager = MusePacketNameChangeRequest

  override def write() {
    writeString(username)
    writeString(newnick)
  }

  @SideOnly(Side.CLIENT)
  override def handleClient(player: EntityClientPlayerMP) {
    NicknameMap.removeName(username)
    NicknameMap.putName(username, newnick)
    val target = Option(PlayerSelector.matchOnePlayer(player, username))
    target.map(t=> t.refreshDisplayName())
  }
}
