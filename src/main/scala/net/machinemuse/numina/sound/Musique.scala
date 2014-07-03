/*package net.machinemuse.numina.sound

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.Side
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.entity.player.EntityPlayer
import scala.Predef.String
import net.machinemuse.numina.basemod.NuminaConfig
import net.machinemuse.numina.general.MuseLogger

/**
 * Handles sound mechanics
 */
object Musique {

  def mcsound = Minecraft.getMinecraft.getSoundHandler

  def soundsystem = mcsound.sndSystem

  def options = Minecraft.getMinecraft.gameSettings

  val soundprefix = "Numina"

  //TODO See if this is for MC only or if it is used in MPS. Assuming it's MC only for the time being.
  def playClientSound(soundname: String, volume: Float) {
    try {
      if ((FMLCommonHandler.instance.getEffectiveSide == Side.CLIENT) && NuminaConfig.useSounds) {
        val pitch: Float = 1.0f
        mcsound.playSound(new PositionedSoundRecord(soundname, volume, pitch, 0F, 0F, 0F))
      }
    } catch {
      case e: NullPointerException => MuseLogger.logDebug("No Soundsystem")
    }
  }

  def makeSoundString(player: EntityPlayer, soundname: String): String = soundprefix + player.getCommandSenderName + soundname

  def playerSound(player: EntityPlayer, soundname: String, volume: Float, pitch: Float = 1.0f, continuous: Boolean = true) {
    try {
      if ((FMLCommonHandler.instance.getEffectiveSide == Side.CLIENT) && NuminaConfig.useSounds) {
        val pitch: Float = 1.0f
        val unknownflag = true
        val soundid = makeSoundString(player, soundname)
        if (!soundsystem.playing(soundid)) {
          val soundfile = getSoundPoolEntry(soundname)
          val amp: Float = 16.0F * Math.max(1, volume)
          soundsystem.newSource(unknownflag, soundid, soundfile.getSoundUrl, soundfile.getSoundName, false, player.posX.toFloat, player.posY.toFloat, player.posZ.toFloat, 2, amp)
          soundsystem.setLooping(soundid, continuous)
          soundsystem.play(soundid)
        }
        soundsystem.setPitch(soundid, pitch)
        soundsystem.setPosition(soundid, player.posX.toFloat, player.posY.toFloat, player.posZ.toFloat)
        soundsystem.setVolume(soundid, Math.min(volume, 1) * this.options.soundVolume)
        soundsystem.setVelocity(soundid, player.motionX.toFloat, player.motionY.toFloat, player.motionZ.toFloat)
      }
    } catch {
      case e: NullPointerException => MuseLogger.logDebug("No Soundsystem")
    }
  }

  def stopPlayerSound(player: EntityPlayer, soundname: String) {
    try {
      if ((FMLCommonHandler.instance.getEffectiveSide eq Side.CLIENT) && NuminaConfig.useSounds) {
        val soundid = makeSoundString(player, soundname)
        val vol = soundsystem.getVolume(soundid) - 0.1f
        if (vol > 0) {
          soundsystem.setVolume(soundid, vol)
        } else {
          soundsystem.stop(makeSoundString(player, soundname))
        }
      }
    } catch {
      case e: NullPointerException => MuseLogger.logDebug("No Soundsystem")
    }
  }

  def getSoundPoolEntry(s: String) = mcsound.soundPoolSounds.getRandomSoundFromSoundPool(s)
}*/