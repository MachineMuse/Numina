package net.machinemuse.numina.sound

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.machinemuse.numina.basemod.NuminaConfig
import net.machinemuse.numina.general.MuseLogger
import net.machinemuse.numina.scala.OptionCast
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.{MovingSound, PositionedSoundRecord, ISound}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation

import scala.collection
import scala.collection.parallel.mutable

/**
 * Created by Claire on 8/27/2015.
 */
object Musique {
  val soundMap = new collection.mutable.HashMap[String, MovingSoundPlayer]

  def mcsound = Minecraft.getMinecraft.getSoundHandler

  def options = Minecraft.getMinecraft.gameSettings

  val soundprefix = "Numina"

  def playClientSound(soundResourceLocation: String, volume: Float) = {
    if ((FMLCommonHandler.instance.getEffectiveSide == Side.CLIENT) && NuminaConfig.useSounds) {

      // creates a sound
      val sound = PositionedSoundRecord.func_147674_a(new ResourceLocation(soundResourceLocation), volume)
      mcsound.playSound(sound)
    }
  }

  def makeSoundString(player: EntityPlayer, soundname: String): String = soundprefix + player.getCommandSenderName + soundname

  def playerSound(player: EntityPlayer, soundname: String, volume: Float, pitch: Float = 1.0f, continuous: Boolean = true) = {
    if ((FMLCommonHandler.instance.getEffectiveSide == Side.CLIENT) && NuminaConfig.useSounds) {
      val soundID = makeSoundString(player, soundname)
      var sound:MovingSoundPlayer = soundMap.get(soundID).getOrElse(null)
      if(sound != null && (sound.isDonePlaying || !sound.canRepeat)) {
        stopPlayerSound(player, soundname)
        sound = null
      }
      if (sound != null) {
        sound.updateVolume(volume).updatePitch(pitch).updateRepeat(continuous)
      } else {
        MuseLogger.logDebug("New sound: " + soundname)
        val newsound = new MovingSoundPlayer(player, new ResourceLocation(soundname), volume*2.0f, pitch, continuous)
        mcsound.playSound(newsound)
        soundMap.put(soundID, newsound)
      }
    }
  }

  def stopPlayerSound(player: EntityPlayer, soundname: String) = {
    if ((FMLCommonHandler.instance.getEffectiveSide == Side.CLIENT) && NuminaConfig.useSounds) {
      val soundID = makeSoundString(player, soundname)
      soundMap.get(soundID).map {sound =>
        MuseLogger.logDebug("Sound stopped: " + soundname)
        sound.stopPlaying()
        mcsound.stopSound(sound)
        soundMap.remove(soundID)
      }
    }
  }

}
