package net.machinemuse.numina.gui

import net.machinemuse.numina.death.GuiGameOverPlus
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.stats.AchievementList
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:59 PM, 10/15/13
 */
object NuminaGuiHandler extends IGuiHandler {
  override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
    ID match {
      case _ => null
    }
  }

  @SideOnly(Side.CLIENT)
  override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
    Minecraft.getMinecraft.thePlayer.addStat(AchievementList.OPEN_INVENTORY, 1)
    ID match {
      case 0 => new GuiGameOverPlus()
      case _ => null
    }
  }
}