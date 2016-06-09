package net.machinemuse.numina.player

import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent
import net.minecraft.entity.player.{EntityPlayerMP, EntityPlayer}
import net.machinemuse.numina.scala.OptionCast
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 1:30 PM, 9/19/13
 */
object PlayerTickHandler {
  @SubscribeEvent
  def onPlayerTick(e: LivingUpdateEvent) {
    OptionCast[EntityPlayer](e).map(player => player.refreshDisplayName())
  }

}
