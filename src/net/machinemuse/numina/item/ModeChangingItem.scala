package net.machinemuse.numina.item

import net.minecraft.item.ItemStack
import net.minecraft.util.Icon
import net.machinemuse.numina.item.NuminaItemUtils

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:11 PM, 9/3/13
 */
trait ModeChangingItem {
  def getActiveMode(stack: ItemStack): String = NuminaItemUtils.getTagCompound(stack).getString("mode")

  def getPrevModeIcon(stack: ItemStack): Icon

  def getCurrentModeIcon(stack: ItemStack): Icon

  def getNextModeIcon(stack: ItemStack): Icon

  def cycleModeForward(stack: ItemStack)

  def cycleModeBackward(stack: ItemStack)
}
