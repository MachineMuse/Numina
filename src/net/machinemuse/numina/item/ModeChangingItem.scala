package net.machinemuse.numina.item

import net.minecraft.item.ItemStack
import net.minecraft.util.Icon
import net.minecraft.entity.player.EntityPlayer

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:11 PM, 9/3/13
 */
trait ModeChangingItem {
  def setActiveMode(stack: ItemStack, newMode: String) = NuminaItemUtils.getTagCompound(stack).setString("mode", newMode)

  def getActiveMode(stack: ItemStack): String = NuminaItemUtils.getTagCompound(stack).getString("mode")

  def getPrevModeIcon(stack: ItemStack): Option[Icon]

  def getCurrentModeIcon(stack: ItemStack): Option[Icon]

  def getNextModeIcon(stack: ItemStack): Option[Icon]

  def cycleMode(stack: ItemStack, dmode: Int)

  def cycleModeForward(stack: ItemStack) = cycleMode(stack, 1)

  def cycleModeBackward(stack: ItemStack) = cycleMode(stack, -1)
}
