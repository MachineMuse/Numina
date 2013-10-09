package net.machinemuse.numina.recipe

import net.minecraft.item.{Item, ItemStack}
import com.google.gson.Gson
import net.machinemuse.numina.general.MuseLogger

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:03 PM, 10/8/13
 */
object CustomShapedOreRecipe {
  def getJson() {
    val gson = new Gson()
    MuseLogger.logDebug(gson.toJson(new CustomShapedOreRecipe(
      Array("QWE", "ASD", "ZXC"),
      Map('Q' -> "Queen",
        'W' -> "Wall",
        'E' -> "Egg"),
      new ItemStack(Item.diamond)
    )))
  }
}

case class CustomShapedOreRecipe(grid: Array[String], map: Map[Char, String], output: ItemStack) {
  def register(): Unit = {
    //GameRegistry.addRecipe(new ShapedOreRecipe(output, input))
  }
}
