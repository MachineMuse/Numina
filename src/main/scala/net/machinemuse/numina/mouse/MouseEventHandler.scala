package net.machinemuse.numina.mouse

import net.minecraftforge.client.event.MouseEvent
import net.minecraft.client.Minecraft
import net.machinemuse.numina.item.ModeChangingItem
import net.machinemuse.numina.scala.OptionCast
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:40 PM, 9/5/13
 */
object MouseEventHandler {
  @SubscribeEvent def onMouseEvent(e: MouseEvent) {
    if (e.getDwheel != 0) {
      for {
        player <- Option(Minecraft.getMinecraft.thePlayer)
        stack <- Option(player.inventory.getCurrentItem)
        item <- OptionCast[ModeChangingItem](stack.getItem)
        if player.isSneaking
      } {
        item.cycleMode(stack, player, e.getDwheel / 120)
        e.setCanceled(true)
      }
    }
  }
}