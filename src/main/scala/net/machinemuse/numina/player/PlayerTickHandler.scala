package net.machinemuse.numina.player

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.machinemuse.numina.scala.OptionCast
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 1:30 PM, 9/19/13
 */
object PlayerTickHandler {
  @SubscribeEvent
  def onPlayerTick(e: LivingUpdateEvent) {
    OptionCast[EntityPlayer](e).foreach(player => player.refreshDisplayName())
  }

}
