package net.machinemuse.numina.sound

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.machinemuse.numina.basemod.NuminaConfig
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.{PositionedSoundRecord, ISound}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation

/**
 * Created by Claire on 8/27/2015.
 */
object Musique {

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
    // TBI
  }

  def stopPlayerSound(player: EntityPlayer, soundname: String) = {
    // TBI
  }

}
