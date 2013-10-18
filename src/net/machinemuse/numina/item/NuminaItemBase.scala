package net.machinemuse.numina.item

import net.minecraft.item.Item
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.client.renderer.texture.IconRegister

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:54 PM, 10/15/13
 */
trait NuminaItemBase extends Item {
  val name: String
  setUnlocalizedName(name)

//  val creativeTab: CreativeTabs
//  setCreativeTab(creativeTab)

  val maxdamage: Int
  setMaxDamage(maxdamage)

  val noRepair: Boolean
  if (noRepair) setNoRepair()

  val maxstacksize:Int
  setMaxStackSize(maxstacksize)

  //also remember to setHasSubtypes

  override def registerIcons(register:IconRegister) {
    iconRegistration(register)
  }

  def iconRegistration(register:IconRegister)

//  override def requiresMultipleRenderPasses = true // Required in order to have it use the itemstack-based icon functions

}
