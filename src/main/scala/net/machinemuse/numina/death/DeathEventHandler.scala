package net.machinemuse.numina.death

import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraft.entity.player.EntityPlayer
import net.machinemuse.numina.scala.OptionCast
import net.machinemuse.numina.basemod.Numina
import net.machinemuse.numina.general.MuseLogger
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraft.client.gui.GuiGameOver
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:31 PM, 10/15/13
 */
object DeathEventHandler {
  @SubscribeEvent def onLivingDeath(e: LivingDeathEvent) {
    OptionCast[EntityPlayer](e.getEntityLiving) map {
      player =>
        e.setCanceled(true)
        player.openGui(Numina, 0, player.worldObj, player.posX.toInt, player.posY.toInt, player.posZ.toInt)
        MuseLogger.logDebug("Death")
//        player.setHealth(10f)
    }
  }

  @SubscribeEvent def onOpenGui(e: GuiOpenEvent) {
    if (e.getGui.isInstanceOf[GuiGameOver]) {
      e.setCanceled(true)
      val player = Minecraft.getMinecraft.thePlayer
      player.openGui(Numina, 0, player.worldObj, player.posX.toInt, player.posY.toInt, player.posZ.toInt)
    }
  }
}
