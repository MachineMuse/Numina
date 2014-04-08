package net.machinemuse.numina.item

import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import scala.Array

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 8:00 PM, 9/3/13
 */
trait InventoriedItem {
  def canStoreItem(stack: ItemStack): Boolean

  def getSelectedSlot(stack: ItemStack): Int = {
    val tag = NuminaItemUtils.getTagCompound(stack)
    if (tag.hasKey("selected")) tag.getInteger("selected")
    else {
      tag.setInteger("selected", 0)
      0
    }
  }

  def setSelectedSlot(stack:ItemStack, i:Int) = {
    val tag = NuminaItemUtils.getTagCompound(stack)
    tag.setInteger("selected", i)
  }

  def getContentsAsNBTTagList(stack: ItemStack) = {
    val tag = NuminaItemUtils.getTagCompound(stack)
    if (tag.hasKey("contents")) tag.getTagList("contents")
    else {
      val list = new NBTTagList()
      tag.setTag("contents", list)
      list
    }
  }

  def getContents(stack: ItemStack): Seq[ItemStack] = {
    val list = getContentsAsNBTTagList(stack)
    for (i <- 0 until list.tagCount()) yield {
      ItemStack.loadItemStackFromNBT(list.tagAt(i).asInstanceOf[NBTTagCompound])
    }
  }

  def setContents(stack: ItemStack, contents: Seq[ItemStack]) {
    val list = new NBTTagList()
    contents foreach {
      item =>
        list.appendTag(item.writeToNBT(new NBTTagCompound()))
    }
    NuminaItemUtils.getTagCompound(stack).setTag("contents", list)
  }

  def insertItem(bag:ItemStack, stackToInsert:ItemStack) {
    setContents(bag, getContents(bag) :+ stackToInsert)
  }

  def getNumStacks(stack:ItemStack) = {
    val tag = NuminaItemUtils.getTagCompound(stack)
    if (tag.hasKey("contents")) tag.getTagList("contents").tagCount() else 0
  }
}
