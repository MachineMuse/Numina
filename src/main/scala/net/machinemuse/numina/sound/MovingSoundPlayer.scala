package net.machinemuse.numina.sound

import net.minecraft.client.audio.ISound.AttenuationType
import net.minecraft.client.audio.MovingSound
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation

/**
 * Created by Claire on 8/27/2015.
 */
class MovingSoundPlayer(val player:EntityPlayer, resourceLocation:ResourceLocation, newvolume:Float, newpitch:Float, newrepeat:Boolean = false) extends MovingSound(resourceLocation) {
  this.field_147663_c = newpitch
  this.volume = newvolume
  this.repeat = newrepeat

  def updatePitch (newpitch:Float) = {
    field_147663_c = newpitch
    this
  }

  def updateVolume (newvolume:Float) = {
    volume = newvolume
    this
  }

  def updateRepeat (newrepeat:Boolean) = {
    repeat = newrepeat
    this
  }



  override def getAttenuationType: AttenuationType = AttenuationType.LINEAR

  def stopPlaying() = {
    this.donePlaying = true
  }

  override def update(): Unit = {
    this.xPosF = player.posX.toFloat
    this.yPosF = player.posY.toFloat
    this.zPosF = player.posZ.toFloat
  }

}
