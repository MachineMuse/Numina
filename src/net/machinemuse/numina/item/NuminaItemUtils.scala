package net.machinemuse.numina.item

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:19 PM, 9/3/13
 */
object NuminaItemUtils {
  def getTagCompound(stack:ItemStack) = {
    if(stack.hasTagCompound)    {
      stack.getTagCompound
    } else {
      val tag = new NBTTagCompound
      stack.setTagCompound(tag)
      tag
    }
  }

}
