package net.machinemuse.numina.sound

import net.minecraft.client.audio.ISound.AttenuationType
import net.minecraft.client.audio.MovingSound
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.{ResourceLocation, SoundCategory, SoundEvent}

/**
 * Created by Claire on 8/27/2015.
 */

// TODO: fix this the proper way. The last 2 params were added to fix building, but it won't be compatible with MPS
class MovingSoundPlayer(val player:EntityPlayer,
                        soundIn: SoundEvent,
                        newvolume:Float,
                        newpitch:Float,
                        newrepeat:Boolean = false,
                        categoryIn: SoundCategory) extends MovingSound(soundIn, categoryIn) {

  this.pitch = newpitch
  this.volume = newvolume
  this.repeat = newrepeat

  def updatePitch (newpitch:Float) = {
    pitch = newpitch
    this
  }

  def updateVolume (newvolume:Float) = {
    volume = (4.0f*volume + newvolume)/5.0f
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
