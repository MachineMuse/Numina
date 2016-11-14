package net.machinemuse.numina.render

import java.lang.String
import scala.Predef.String
import java.util
import net.minecraft.util.ResourceLocation
import net.minecraft.client.Minecraft

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:38 PM, 9/6/13
 */
object MuseTextureUtils {
  private var TEXTURE_MAP: String = "textures/atlas/items.png"
  private val texturestack: util.Stack[String] = new util.Stack[String]
  val ITEM_TEXTURE_QUILT: String = "textures/atlas/items.png"
  val BLOCK_TEXTURE_QUILT: String = "textures/atlas/blocks.png"

  def pushTexture(filename: String) {
    texturestack.push(TEXTURE_MAP)
    TEXTURE_MAP = filename
    bindTexture(TEXTURE_MAP)
  }

  def popTexture() {
    TEXTURE_MAP = texturestack.pop
    bindTexture(TEXTURE_MAP)
  }

  def bindTexture(tex: String) {
    Minecraft.getMinecraft.renderEngine.bindTexture(new ResourceLocation(tex))
  }
}
