package net.machinemuse.numina.network

import java.io.DataInputStream

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.client.entity.EntityClientPlayerMP
import net.machinemuse.numina.sound.proxy.{Musique, MusiqueCommonPlayer}

/*
* Added by Korynkai
* 2014-12-20
*
*/

object MusiqueUpdatePacket extends MusePackager {
  def read(d: DataInputStream, p: EntityPlayer) = {
    var username: String = readString(d)
    var resource: String = readString(d)
    var stopSound: Boolean = readBoolean(d)
    var volume: Float = readDouble(d).toFloat
    var pitch: Float = readDouble(d).toFloat
    var player: MusiqueCommonPlayer = new MusiqueCommonPlayer(p.worldObj.getPlayerEntityByName(username), resource, volume, pitch)
    new MusiqueUpdatePacket(player, stopSound)
  }
}

class MusiqueUpdatePacket(sourcePlayer: MusiqueCommonPlayer, stopSound: Boolean) extends MusePacket {
  val packager = MusiqueUpdatePacket

  @SideOnly(Side.CLIENT)
  override def handleClient(player: EntityClientPlayerMP) {
    if (sourcePlayer.player.getCommandSenderName != player.getCommandSenderName) {
      if (!stopSound) {
        Musique.playerSound(sourcePlayer.player, sourcePlayer.resource, sourcePlayer.volume, sourcePlayer.pitch)
      } else {
        Musique.stopPlayerSound(sourcePlayer.player, sourcePlayer.resource)
      }
    }
  }

  override def handleServer(player: EntityPlayerMP) {
    if (!player.worldObj.isRemote) {
      PacketSender.sendToAllAround(this, sourcePlayer.player, 10.0d)
    }
  }

  def write() {
    writeString(sourcePlayer.player.getCommandSenderName)
    writeString(sourcePlayer.resource)
    writeBoolean(stopSound)
    writeDouble(sourcePlayer.volume.toDouble)
    writeDouble(sourcePlayer.pitch.toDouble)
  }

}
