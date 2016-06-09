package net.machinemuse.numina.render

import net.machinemuse.numina.general.MuseMathUtils
import net.machinemuse.numina.geometry.Colour
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.{VertexBuffer, Tessellator}
import org.lwjgl.opengl.GL11

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
  def drawIconAt(x: Double, y: Double, icon: TextureAtlasSprite, colour: Colour) {
    drawIconPartial(x, y, icon, colour, 0, 0, 16, 16)
  }

  def drawIconPartialOccluded(x: Double, y: Double, icon: TextureAtlasSprite, colour: Colour, left: Double, top: Double, right: Double, bottom: Double) {
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
  def drawIconPartial(x: Double, y: Double, icon: TextureAtlasSprite, colour: Colour, left: Double, top: Double, right: Double, bottom: Double) {

    if (icon != null) {
      GL11.glPushMatrix()
      RenderState.on2D()
      RenderState.blendingOn()
      if (colour != null) {
        colour.doGL()
      }

      val tess: Tessellator = Tessellator.getInstance()
      val vertexBuffer: VertexBuffer = tess.getBuffer;

      vertexBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
      val u1: Float = icon.getMinU
      val v1: Float = icon.getMinV
      val u2: Float = icon.getMaxU
      val v2: Float = icon.getMaxV
      val xoffset1: Double = left * (u2 - u1) / 16.0f
      val yoffset1: Double = top * (v2 - v1) / 16.0f
      val xoffset2: Double = right * (u2 - u1) / 16.0f
      val yoffset2: Double = bottom * (v2 - v1) / 16.0f

      vertexBuffer.pos(x + left, y + top, 0)
      vertexBuffer.tex(u1 + xoffset1, v1 + yoffset1)
      vertexBuffer.endVertex()

      vertexBuffer.pos(x + left, y + bottom, 0)
      vertexBuffer.tex(u1 + xoffset1, v1 + yoffset2)
      vertexBuffer.endVertex()

      vertexBuffer.pos(x + right, y + bottom, 0)
      vertexBuffer.tex(u1 + xoffset2, v1 + yoffset2)
      vertexBuffer.endVertex()

      vertexBuffer.pos(x + right, y + top, 0)
      vertexBuffer.tex(u1 + xoffset2, v1 + yoffset1)
      vertexBuffer.endVertex()

      tess.draw
      RenderState.blendingOff()
      RenderState.off2D()
      GL11.glPopMatrix()
    }
    else
      System.out.println("Your icon is null!!")
  }
}