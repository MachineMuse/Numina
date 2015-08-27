package net.machinemuse.numina.sound

import net.minecraft.client.audio.ISound
import net.minecraft.client.audio.ISound.AttenuationType
import net.minecraft.util.ResourceLocation

/**
 * Created by Claire on 8/27/2015.
 */
class MuseGuiSound (val repeatDelay: Int = 0,
                    val attenuationType:AttenuationType = AttenuationType.LINEAR,
                    val canRepeat:Boolean = false

                     ) extends ISound {

  override def getXPosF: Float = ???

  override def getYPosF: Float = ???

  override def getZPosF: Float = ???


  override def getPitch: Float = ???

  override def getVolume: Float = ???


  override def getAttenuationType: AttenuationType = attenuationType

  override def getRepeatDelay: Int = repeatDelay

  override def getPositionedSoundLocation: ResourceLocation = ???

}