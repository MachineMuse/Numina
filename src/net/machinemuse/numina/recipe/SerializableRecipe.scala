package net.machinemuse.numina.recipe

import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.world.World
import net.minecraftforge.oredict.OreDictionary
import java.lang.Object
import java.util.ArrayList
import net.machinemuse.numina.scala.OptionCast


/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:52 PM, 10/8/13
 */
case class SerializableRecipe(inputs:Array[Array[String]], output:ItemStack) extends IRecipe {
  private final val MAX_CRAFT_GRID_WIDTH: Int = 3
  private final val MAX_CRAFT_GRID_HEIGHT: Int = 3


  private def checkMatch(inv: InventoryCrafting, startX: Int, startY: Int, mirror: Boolean): Boolean = {
    val width = inputs(0).length
    val height = inputs.length
    for(x <- 0 until MAX_CRAFT_GRID_WIDTH) {
      for(y <- 0 until MAX_CRAFT_GRID_HEIGHT) {
        val subX: Int = x - startX
        val subY: Int = y - startY
        var target: String = null
        if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
          if (mirror) {
            target = inputs(width - subX - 1)(subY)
          }
          else {
            target = inputs(subX)(subY)
          }
        }

        val stackInSlot: ItemStack = inv.getStackInRowAndColumn(x, y)

        Option(stackInSlot) match {
          case Some(stack) => if(!matchItemToString(stackInSlot, target)) return false
          case None => return false
        }
      }
    }
    true
  }
  def matchItemToString(stack:ItemStack, name:String): Boolean = {
    true
  }

  def getCraftingResult(inventorycrafting: InventoryCrafting): ItemStack = output

  def getRecipeSize: Int = (0 /: inputs) ((acc,el) => acc + el.length)

  def getRecipeOutput: ItemStack = output



  private def checkItemEquals(target: ItemStack, input: ItemStack): Boolean = {
    if (input == null && target != null || input != null && target == null) {
      return false
    }
    target.itemID == input.itemID && (target.getItemDamage == OreDictionary.WILDCARD_VALUE || target.getItemDamage == input.getItemDamage)
  }

  def matches(inventorycrafting: InventoryCrafting, world: World): Boolean = {true}
}