package net.machinemuse.numina.geometry

import org.lwjgl.BufferUtils
import java.nio.DoubleBuffer
import java.util

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:52 PM, 9/6/13
 */
object GradientAndArcCalculator {
  /**
   * Efficient algorithm for drawing circles and arcs in pure opengl!
   *
   * @param startangle Start angle in radians
   * @param endangle   End angle in radians
   * @param radius     Radius of the circle (used in calculating number of segments to draw as well as size of the arc)
   * @param xoffset    Convenience parameter, added to every vertex
   * @param yoffset    Convenience parameter, added to every vertex
   * @param zoffset    Convenience parameter, added to every vertex
   * @return
   */
  def getArcPoints(startangle: Double, endangle: Double, radius: Double, xoffset: Double, yoffset: Double, zoffset: Double): DoubleBuffer = {
    val numVertices: Int = Math.ceil(Math.abs((endangle - startangle) * 2 * Math.PI)).asInstanceOf[Int]
    val theta: Double = (endangle - startangle) / numVertices
    val buffer: DoubleBuffer = BufferUtils.createDoubleBuffer(numVertices * 3)
    var x: Double = radius * Math.sin(startangle)
    var y: Double = radius * Math.cos(startangle)
    val tf: Double = Math.tan(theta)
    val rf: Double = Math.cos(theta)
    var tx: Double = .0
    var ty: Double = .0
    for (i <- 0 until numVertices) {
      buffer.put(x + xoffset)
      buffer.put(y + yoffset)
      buffer.put(zoffset)
      tx = y
      ty = -x
      x += tx * tf
      y += ty * tf
      x *= rf
      y *= rf

    }
    buffer.flip
    buffer
  }

  /**
   * Creates a list of points linearly interpolated between points a and b noninclusive.
   *
   * @return A list of num points
   */
  def pointsInLine(num: Int, a: MusePoint2D, b: MusePoint2D): util.List[MusePoint2D] = {
    val points = new util.ArrayList[MusePoint2D]
    num match {
      case -1 =>
      case 0 =>
      case 1 => points.add(b.minus(a).times(0.5F).plus(a))
      case _ => {
        val step: MusePoint2D = b.minus(a).times(1.0F / (num + 1))
        for (i <- 0 until num) {
          points.add(a.plus(step.times(i + 1)))
        }
      }
    }
    points
  }

  /**
   * Returns a DoubleBuffer full of colours that are gradually interpolated
   *
   * @param c1
   * @param c2
   * @param numsegments
   * @return
   */
  def getColourGradient(c1: Colour, c2: Colour, numsegments: Int): DoubleBuffer = {
    val buffer: DoubleBuffer = BufferUtils.createDoubleBuffer(numsegments * 4)
    for (i <- 0 until numsegments) {
      val c3: Colour = c1.interpolate(c2, i / numsegments)
      buffer.put(c3.r)
      buffer.put(c3.g)
      buffer.put(c3.b)
      buffer.put(c3.a)
    }
    buffer.flip
    return buffer
  }
}
