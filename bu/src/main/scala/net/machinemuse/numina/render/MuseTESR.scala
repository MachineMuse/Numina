package net.machinemuse.numina.render

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.util.ResourceLocation
import net.minecraft.tileentity.TileEntity

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:28 PM, 8/3/13
 */
abstract class MuseTESR extends TileEntitySpecialRenderer {
  def bindTextureByName(tex: String) {
    this.bindTexture(new ResourceLocation(tex))
  }

  def renderTileEntityAt(tileentity: TileEntity, d0: Double, d1: Double, d2: Double, f: Float): Unit = {
    renderAt(tileentity, d0, d1, d2, f)
  }
  def renderAt(tileentity: TileEntity, d0: Double, d1: Double, d2: Double, f: Float):Unit
}