package net.machinemuse.numina.player

import net.minecraft.entity.player.{EntityPlayerMP, EntityPlayer}

/**
 * Created by Claire Semple on 9/9/2014.
 */
object NuminaPlayerUtils {
  def resetFloatKickTicks(player: EntityPlayer) {
    player match {
      case p: EntityPlayerMP => p.connection.floatingTickCount = 0
      case _ =>
    }
  }
}
