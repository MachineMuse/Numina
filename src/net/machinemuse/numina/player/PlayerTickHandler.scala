package net.machinemuse.numina.player

import net.minecraftforge.event.ForgeSubscribe
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent
import net.minecraft.entity.player.EntityPlayer
import net.machinemuse.numina.scala.OptionCast

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 1:30 PM, 9/19/13
 */
object PlayerTickHandler {
  @ForgeSubscribe
  def onPlayerTick(e: LivingUpdateEvent) {
    OptionCast[EntityPlayer](e).map(player => player.refreshDisplayName())
  }
}
