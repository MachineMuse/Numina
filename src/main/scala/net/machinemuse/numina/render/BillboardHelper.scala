package net.machinemuse.numina.render

import java.nio.FloatBuffer
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:21 AM, 11/13/13
 */
object BillboardHelper {

  val matrix:FloatBuffer = BufferUtils.createFloatBuffer(16)



  def unRotate {
    matrix.clear()
    GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, matrix)
    val scalex: Float = pythag(matrix.get(0), matrix.get(1), matrix.get(2))
    val scaley: Float = pythag(matrix.get(4), matrix.get(5), matrix.get(6))
    val scalez: Float = pythag(matrix.get(8), matrix.get(9), matrix.get(10))
    for (i <- 0 until 12) {
      matrix.put(i, 0)
    }
    matrix.put(0, scalex)
    matrix.put(5, scaley)
    matrix.put(10, scalez)
    GL11.glLoadMatrix(matrix)
  }

  private def pythag(x: Float, y: Float, z: Float) = Math.sqrt(x * x + y * y + z * z).toFloat

}
