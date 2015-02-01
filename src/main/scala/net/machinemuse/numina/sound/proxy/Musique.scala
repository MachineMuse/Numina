package net.machinemuse.numina.sound.proxy

import cpw.mods.fml.relauncher.{Side, SideOnly}
import cpw.mods.fml.common.{SidedProxy, FMLCommonHandler}
import net.minecraft.client.audio.{SoundHandler, SoundPoolEntry}
import net.minecraft.client.audio.ISound.AttenuationType
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
//import net.machinemuse.numina.network.{MusiqueUpdatePacket, PacketSender}
import net.machinemuse.numina.sound.MuseSound

import java.io.InputStream
import org.gagravarr.ogg.OggPacketReader
import org.gagravarr.ogg.audio.OggAudioStatistics
import org.gagravarr.vorbis.VorbisFile
import org.gagravarr.ogg.audio.{OggAudioHeaders, OggAudioStream}
//import com.qmxtech.oggaudiodata._
import scala.concurrent.duration._
import scala.collection.mutable.ListBuffer

/*
 * Added by Korynkai
 * 2014-12-20
 *
 */

trait MusiqueCommon {
  def init() {}

  def clientSound(sound: String, volume: Float) {}

  def stopClientSound(sound: String) {}

  def stopAllClientSounds() {}

  def playerSound(player: EntityPlayer, sound: String, volume: Float, pitch: Float) {}

  def stopPlayerSound(player: EntityPlayer, sound: String) {}

  def stopPlayerSounds(player: EntityPlayer) {}

  def stopAllPlayerSounds() {}
}

case class MusiqueCommonPlayer(player: EntityPlayer, resource: String, volume: Float, pitch: Float)

class MusiqueServer extends MusiqueCommon  /* Unnecessary? Or would it eliminate a step in the network chain? {
  var currentPlayerSounds: ListBuffer[MusiqueCommonPlayer] = null

  override def init() {
    currentPlayerSounds = new ListBuffer[MusiqueCommonPlayer]
  }

  override def clientSound(sound: String, volume: Float) {
    // Do nothing. Reserved for client.
  }

  override def stopClientSound(sound: String) {
    // Do nothing. Reserved for client.
  }

  override def stopAllClientSounds() {
    // Do nothing. Reserved for client.
  }

  override def playerSound(player: EntityPlayer, resource: String, volume: Float, pitch: Float) {
    if (player != null) {
      if (!player.worldObj.isRemote) {
        // Handle directional sound.
      }
    }
  }

  override def stopPlayerSound(player: EntityPlayer, resource: String) {
    if (player != null) {
      if (!player.worldObj.isRemote) {
        // Stop directional sound.
      }
    }
  }

  override def stopPlayerSounds(player: EntityPlayer) {
    if (player != null) {
      if (!player.worldObj.isRemote) {
        // Stop all directional sounds made by player.
      }
    }
  }

  override def stopAllPlayerSounds() {
    if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
      // Stop all directional sounds.
    }
  }
} */

@SideOnly(Side.CLIENT)
case class MusiqueTimedInstance(sound: MuseSound, duration: Deadline)

@SideOnly(Side.CLIENT)
case class MusiquePlayerSound(player: MusiqueCommonPlayer, instance: MusiqueTimedInstance)

@SideOnly(Side.CLIENT)
case class MusiqueClientSound(resource: String, instance: MusiqueTimedInstance)

class MusiqueClient extends MusiqueCommon {

  var currentPlayerSounds: ListBuffer[MusiquePlayerSound] = null
  var currentClientSounds: ListBuffer[MusiqueClientSound] = null

  override def init() {
    currentPlayerSounds = new ListBuffer[MusiquePlayerSound]
    currentClientSounds = new ListBuffer[MusiqueClientSound]
  }
  
  private def getDeadline(sound: MuseSound): Deadline = {
      val opr: OggPacketReader = new OggPacketReader(
                                    Minecraft.getMinecraft.getResourceManager.getResource(
                                        new SoundPoolEntry(
                                            Minecraft.getMinecraft.getSoundHandler.getSound(
                                                sound.getPositionedSoundLocation
                                            ).func_148720_g
                                        ).getSoundPoolEntryLocation
                                    ).getInputStream
                                )

        val oas: OggAudioStatistics = new OggAudioStatistics(opr.asInstanceOf[OggAudioHeaders], opr.asInstanceOf[OggAudioStream])
        
        oas.calculate
        
        return oas.getDurationSeconds.seconds.fromNow
  }

  override def clientSound(resource: String, volume: Float) {
    if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
      var registered: Boolean = false
      var entry: MusiqueClientSound = null

      for (entry <- currentClientSounds
        if entry.resource == resource) {
          if (entry.instance.duration <= Deadline.now) {
            currentClientSounds -= entry
            registered = false
          } else {
            registered = true
          }
      }

      if (registered == false) {
        val sound: MuseSound = new MuseSound(resource, volume, 1.0f)
        Minecraft.getMinecraft.getSoundHandler.playSound(sound)

        // Show me a better way of getting this value or comparable and I'll implement it. -- Korynkai
        // val duration: Deadline = OggAudioData.getSeconds(
        //                           Minecraft.getMinecraft.getResourceManager.getResource(
        //                             new SoundPoolEntry(
        //                               Minecraft.getMinecraft.getSoundHandler.getSound(
        //                                 sound.getPositionedSoundLocation
        //                               ).func_148720_g
        //                             ).getSoundPoolEntryLocation
        //                           ).getInputStream
        //                          ) seconds fromNow

        currentClientSounds += new MusiqueClientSound(resource, new MusiqueTimedInstance(sound, getDeadline(sound)))
      }
    }
  }

  override def stopClientSound(resource: String) {
    if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
      var entry: MusiqueClientSound = null
      for (entry <- currentClientSounds
        if entry.resource == resource) {
          if (entry.instance.duration > Deadline.now) {
            Minecraft.getMinecraft.getSoundHandler.stopSound(entry.instance.sound)
          }
      }
      currentClientSounds -= entry
    }
  }

  override def stopAllClientSounds() {
    if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
      var entry: MusiqueClientSound = null
      for (entry <- currentClientSounds) {
        if (entry.instance.duration > Deadline.now) {
          Minecraft.getMinecraft.getSoundHandler.stopSound(entry.instance.sound)
        }
      }
      currentClientSounds -= entry
    }
  }

  override def playerSound(player: EntityPlayer, resource: String, volume: Float, pitch: Float) {
    if (player != null) {
      if (player.worldObj.isRemote) {
        var registered: Boolean = false
        var entry: MusiquePlayerSound = null

        for (entry <- currentPlayerSounds
          if entry.player.player == player; if entry.player.resource == resource) {
            if (entry.instance.duration <= Deadline.now) {
              currentPlayerSounds -= entry
              registered = false
            } else {
              registered = true
            }
        }

        if (registered == false) {
          var sound: MuseSound = null
          if (player == Minecraft.getMinecraft.thePlayer) {
            sound = new MuseSound(resource, volume, pitch)
            //PacketSender.sendToServer(new MusiqueUpdatePacket(new MusiqueCommonPlayer(player, resource, volume, pitch), false))
          } else {
            // Directional sounds broken? Hmm... Need to determine how to achive this... -- Korynkai
            //sound = new MuseSound(resource, volume, pitch, player.posX.asInstanceOf[Float], player.posY.asInstanceOf[Float], player.posZ.asInstanceOf[Float], AttenuationType.NONE)
          }
          // Temporary null check
          if (sound != null) {

          Minecraft.getMinecraft.getSoundHandler.playSound(sound)

          // Show me a better way of getting this value or comparable and I'll implement it. -- Korynkai
        //   val duration: Deadline = OggAudioData.getSeconds(
        //                             Minecraft.getMinecraft.getResourceManager.getResource(
        //                               new SoundPoolEntry(
        //                                 Minecraft.getMinecraft.getSoundHandler.getSound(
        //                                   sound.getPositionedSoundLocation // NPE occurs here when not working directionally... See above conditional.
        //                                 ).func_148720_g
        //                               ).getSoundPoolEntryLocation
        //                             ).getInputStream
        //                           ) seconds fromNow

          currentPlayerSounds += new MusiquePlayerSound(new MusiqueCommonPlayer(player, resource, volume, pitch), new MusiqueTimedInstance(sound, getDeadline(sound)))
          } // End of temporary null check
        }
      }
    }
  }

  override def stopPlayerSound(player: EntityPlayer, resource: String) {
    if (player != null) {
      if (player.worldObj.isRemote) {
        var entry: MusiquePlayerSound = null
        for (entry <- currentPlayerSounds
          if entry.player.player == player; if entry.player.resource == resource) {
            if (entry.instance.duration > Deadline.now) {
              Minecraft.getMinecraft.getSoundHandler.stopSound(entry.instance.sound)
              if (player == Minecraft.getMinecraft.thePlayer) {
                // PacketSender.sendToServer(new MusiqueUpdatePacket(entry.player, true)) // Update directional sounds
              }
            }
        }
        currentPlayerSounds -= entry
      }
    }
  }

  override def stopPlayerSounds(player: EntityPlayer) {
    if (player != null) {
      if (player.worldObj.isRemote) {
        var entry: MusiquePlayerSound = null
        for (entry <- currentPlayerSounds
          if entry.player.player == player) {
            if (entry.instance.duration > Deadline.now) {
              Minecraft.getMinecraft.getSoundHandler.stopSound(entry.instance.sound)
              if (player == Minecraft.getMinecraft.thePlayer) {
                // PacketSender.sendToServer(new MusiqueUpdatePacket(entry.player, true)) // Update directional sounds
              }
            }
        }
        currentPlayerSounds -= entry
      }
    }
  }

  override def stopAllPlayerSounds() {
    if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
      var entry: MusiquePlayerSound = null
      for (entry <- currentPlayerSounds) {
        if (entry.instance.duration > Deadline.now) {
          Minecraft.getMinecraft.getSoundHandler.stopSound(entry.instance.sound)
          if (entry.player.player == Minecraft.getMinecraft.thePlayer) {
            // PacketSender.sendToServer(new MusiqueUpdatePacket(entry.player, true)) // Update directional sounds
          }
        }
      }
      currentPlayerSounds -= entry
    }
  }
}

object Musique {
  var musique: MusiqueCommon = null

  def init(proxy: MusiqueCommon) {
    musique = proxy
    musique.init()
  }

  def clientSound(resource: String, volume: Float) {
    if (musique != null) {
      musique.clientSound(resource, volume)
    }
  }

  def stopClientSound(resource: String) {
    if (musique != null) {
      musique.stopClientSound(resource)
    }
  }

  def stopAllClientSounds() {
    if (musique != null) {
      musique.stopAllClientSounds()
    }
  }

  def playerSound(player: EntityPlayer, resource: String, volume: Float, pitch: Float) {
    if (musique != null) {
      musique.playerSound(player, resource, volume, pitch)
    }
  }

  def stopPlayerSound(player: EntityPlayer, resource: String) {
    if (musique != null) {
      musique.stopPlayerSound(player, resource)
    }
  }

  def stopPlayerSounds(player: EntityPlayer) {
    if (musique != null) {
      musique.stopPlayerSounds(player)
    }
  }

  def stopAllPlayerSounds() {
    if (musique != null) {
      musique.stopAllPlayerSounds()
    }
  }
}
