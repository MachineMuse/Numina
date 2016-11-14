package net.machinemuse.numina.render

import net.machinemuse.numina.general.MuseMathUtils
import org.lwjgl.opengl.GL11
import net.minecraft.client.renderer.Tessellator
import net.machinemuse.numina.geometry.Colour
import net.minecraft.util.IIcon

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:37 PM, 9/6/13
 */
object MuseIconUtils {
  /**
   * Draws a MuseIcon
   *
   * @param x
   * @param y
   * @param icon
   * @param colour
   */
  def drawIconAt(x: Double, y: Double, icon: IIcon, colour: Colour) {
    drawIconPartial(x, y, icon, colour, 0, 0, 16, 16)
  }

  def drawIconPartialOccluded(x: Double, y: Double, icon: IIcon, colour: Colour, left: Double, top: Double, right: Double, bottom: Double) {
    val xmin: Double = MuseMathUtils.clampDouble(left - x, 0, 16)
    val ymin: Double = MuseMathUtils.clampDouble(top - y, 0, 16)
    val xmax: Double = MuseMathUtils.clampDouble(right - x, 0, 16)
    val ymax: Double = MuseMathUtils.clampDouble(bottom - y, 0, 16)
    drawIconPartial(x, y, icon, colour, xmin, ymin, xmax, ymax)
  }

  /**
   * Draws a MuseIcon
   *
   * @param x
   * @param y
   * @param icon
   * @param colour
   */
  def drawIconPartial(x: Double, y: Double, icon: IIcon, colour: Colour, left: Double, top: Double, right: Double, bottom: Double) {
    if (icon == null) {
      return
    }
    GL11.glPushMatrix()
    RenderState.on2D()
    RenderState.blendingOn()
    if (colour != null) {
      colour.doGL()
    }
    val tess: Tessellator = Tessellator.instance
    tess.startDrawingQuads()
    val u1: Float = icon.getMinU
    val v1: Float = icon.getMinV
    val u2: Float = icon.getMaxU
    val v2: Float = icon.getMaxV
    val xoffset1: Double = left * (u2 - u1) / 16.0f
    val yoffset1: Double = top * (v2 - v1) / 16.0f
    val xoffset2: Double = right * (u2 - u1) / 16.0f
    val yoffset2: Double = bottom * (v2 - v1) / 16.0f
    tess.addVertexWithUV(x + left, y + top, 0, u1 + xoffset1, v1 + yoffset1)
    tess.addVertexWithUV(x + left, y + bottom, 0, u1 + xoffset1, v1 + yoffset2)
    tess.addVertexWithUV(x + right, y + bottom, 0, u1 + xoffset2, v1 + yoffset2)
    tess.addVertexWithUV(x + right, y + top, 0, u1 + xoffset2, v1 + yoffset1)
    tess.draw
    RenderState.blendingOff()
    RenderState.off2D()
    GL11.glPopMatrix()
  }
}
