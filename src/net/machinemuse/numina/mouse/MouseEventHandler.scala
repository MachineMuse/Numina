package net.machinemuse.numina.mouse

import net.minecraftforge.event.ForgeSubscribe
import net.minecraftforge.client.event.MouseEvent
import net.minecraft.client.Minecraft
import net.machinemuse.numina.item.ModeChangingItem
import net.machinemuse.numina.scala.OptionCast

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:40 PM, 9/5/13
 */
object MouseEventHandler {
  @ForgeSubscribe def onMouseEvent(e: MouseEvent) {
    if (e.dwheel != 0) {
      for {
        p <- Option(Minecraft.getMinecraft.thePlayer)
        stack <- Option(p.getCurrentEquippedItem)
        item <- OptionCast[ModeChangingItem](stack.getItem)
        if p.isSneaking
      } {
        item.cycleMode(stack, e.dwheel / 120)
        e.setCanceled(true)
      }
    }
  }
}