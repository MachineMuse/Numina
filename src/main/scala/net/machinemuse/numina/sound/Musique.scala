package net.machinemuse.numina.sound

import net.machinemuse.numina.basemod.NuminaConfig
import net.machinemuse.numina.general.MuseLogger
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{ResourceLocation, SoundCategory, SoundEvent}
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
 * Created by Claire on 8/27/2015.
 */
object Musique {
  @SideOnly(Side.CLIENT)
  val soundMap = new collection.mutable.HashMap[SoundEvent, MovingSoundPlayer]

  @SideOnly(Side.CLIENT)
  def mcsound = Minecraft.getMinecraft.getSoundHandler

  def options = Minecraft.getMinecraft.gameSettings

  val soundprefix = "Numina"

  def playClientSound(player: EntityPlayer, soundIn: SoundEvent, categoryIn: SoundCategory, volume: Float) = {
    //FIXME: commented out for building
    if ((FMLCommonHandler.instance.getEffectiveSide == Side.CLIENT) && NuminaConfig.useSounds) {
      // creates a sound
      val sound = new PositionedSoundRecord(soundIn, categoryIn, volume, 1.0F, player.getPosition)
      mcsound.playSound(sound)
    }
  }

  def makeSoundString(player: EntityPlayer, soundname: String): String = soundprefix + player.getCommandSenderEntity + soundname

  def playerSound(player: EntityPlayer, soundEvt: SoundEvent, volume: Float, pitch: Float = 1.0f, continuous: Boolean = true, categoryIn: SoundCategory) = {
    if ((FMLCommonHandler.instance.getEffectiveSide == Side.CLIENT) && NuminaConfig.useSounds) {
//      val soundID = makeSoundString(player, soundname)
      var sound:MovingSoundPlayer = soundMap.get(soundEvt).getOrElse(null)
      if(sound != null && (sound.isDonePlaying || !sound.canRepeat)) {
        stopPlayerSound(player, soundEvt)
        sound = null
      }
      if (sound != null) {
        sound.updateVolume(volume).updatePitch(pitch).updateRepeat(continuous)
      } else {
        MuseLogger.logDebug("New sound: " + soundEvt.getSoundName)
        val newsound = new MovingSoundPlayer(player, soundEvt, volume*2.0f, pitch, continuous, categoryIn)

        mcsound.playSound(newsound)
        soundMap.put(soundEvt, newsound)
      }
    }
  }

  def stopPlayerSound(player: EntityPlayer, soundEvt: SoundEvent) = {
    if ((FMLCommonHandler.instance.getEffectiveSide == Side.CLIENT) && NuminaConfig.useSounds) {
//      val soundID = makeSoundString(player, soundEvt)
      soundMap.get(soundEvt).map {sound =>
        MuseLogger.logDebug("Sound stopped: " + soundEvt.getSoundName)
        sound.stopPlaying()
        mcsound.stopSound(sound)
        soundMap.remove(soundEvt)
      }
    }
  }

}
