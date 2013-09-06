package net.machinemuse.numina.render

import org.lwjgl.opengl.GL11
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.{RenderHelper, OpenGlHelper}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:41 PM, 9/6/13
 */
object RenderState {

  import GL11._

  /**
   * 2D rendering mode on/off
   */
  def on2D() {
    glPushAttrib(GL_ENABLE_BIT)
    glDisable(GL_DEPTH_TEST)
    glDisable(GL_CULL_FACE)
    glDisable(GL_LIGHTING)
  }

  def off2D() {
    glPopAttrib()
  }

  /**
   * Arrays on/off
   */
  def arraysOnC() {
    glEnableClientState(GL_VERTEX_ARRAY)
    glEnableClientState(GL_COLOR_ARRAY)
  }

  def arraysOnT() {
    glEnableClientState(GL_VERTEX_ARRAY)
    glEnableClientState(GL_TEXTURE_COORD_ARRAY)
  }

  def arraysOff() {
    glDisableClientState(GL_VERTEX_ARRAY)
    glDisableClientState(GL_COLOR_ARRAY)
    glDisableClientState(GL_TEXTURE_COORD_ARRAY)
  }

  /**
   * Call before doing any pure geometry (ie. with colours rather than textures).
   */
  def texturelessOn() {
    glDisable(GL_TEXTURE_2D)
  }

  /**
   * Call after doing pure geometry (ie. with colours) to go back to the texture mode (default).
   */
  def texturelessOff() {
    glEnable(GL_TEXTURE_2D)
  }

  /**
   * Call before doing anything with alpha blending
   */
  def blendingOn() {
    glPushAttrib(GL_COLOR_BUFFER_BIT | GL_LIGHTING_BIT)
    if (Minecraft.isFancyGraphicsEnabled) {
      glShadeModel(GL_SMOOTH)
      glDisable(GL_ALPHA_TEST)
      glEnable(GL_BLEND)
      glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    }
  }

  /**
   * Call after doing anything with alpha blending
   */
  def blendingOff() {
    glPopAttrib()
  }

  def scissorsOn(x: Double, y: Double, w: Double, h: Double) {
    glPushAttrib(GL_ENABLE_BIT | GL_SCISSOR_BIT)
    glPushMatrix()
    val mc: Minecraft = Minecraft.getMinecraft
    val dw: Int = mc.displayWidth
    val dh: Int = mc.displayHeight
    val res: ScaledResolution = new ScaledResolution(mc.gameSettings, dw, dh)
    val newx: Double = x * res.getScaleFactor
    val newy: Double = dh - h * res.getScaleFactor - y * res.getScaleFactor
    val neww: Double = w * res.getScaleFactor
    val newh: Double = h * res.getScaleFactor
    glEnable(GL_SCISSOR_TEST)
    glScissor(newx.asInstanceOf[Int], newy.asInstanceOf[Int], neww.asInstanceOf[Int], newh.asInstanceOf[Int])
  }

  def scissorsOff() {
    glPopMatrix()
    glPopAttrib()
  }

  private var lightmapLastX = .0f
  private var lightmapLastY = .0f

  def glowOn() {
    GL11.glPushAttrib(GL11.GL_LIGHTING_BIT)
    lightmapLastX = OpenGlHelper.lastBrightnessX
    lightmapLastY = OpenGlHelper.lastBrightnessY
    RenderHelper.disableStandardItemLighting()
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F)
  }

  def glowOff() {
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapLastX, lightmapLastY)
    GL11.glPopAttrib()
  }
}
