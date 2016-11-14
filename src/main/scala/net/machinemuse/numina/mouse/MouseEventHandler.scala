package net.machinemuse.numina.mouse

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.MouseEvent
import net.minecraft.client.Minecraft
import net.machinemuse.numina.item.ModeChangingItem
import net.machinemuse.numina.scala.OptionCast

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:40 PM, 9/5/13
 */
object MouseEventHandler {
  @SubscribeEvent def onMouseEvent(e: MouseEvent) {
    if (e.dwheel != 0) {
      for {
        player <- Option(Minecraft.getMinecraft.thePlayer)
        stack <- Option(player.getCurrentEquippedItem)
        item <- OptionCast[ModeChangingItem](stack.getItem)
        if player.isSneaking
      } {
        item.cycleMode(stack, player, e.dwheel / 120)
        e.setCanceled(true)
      }
    }
  }
}