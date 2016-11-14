package net.machinemuse.numina.item

import net.minecraft.item.Item
import net.minecraft.client.renderer.texture.IIconRegister

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:54 PM, 10/15/13
 */
trait NuminaItemBase extends Item {
  val name: String
  setUnlocalizedName(name)

//  val creativeTab: CreativeTabs
//  setCreativeTab(creativeTab)

  //also remember to setHasSubtypes

  override def registerIcons(register:IIconRegister) {
    iconRegistration(register)
  }

  def iconRegistration(register:IIconRegister)

//  override def requiresMultipleRenderPasses = true // Required in order to have it use the itemstack-based icon functions

}
