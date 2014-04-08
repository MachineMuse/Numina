package net.machinemuse.numina.general

import java.util.Random

object MuseMathUtils {
  val random = new Random

  def nextDouble: Double = random.nextDouble

  def clampDouble(value: Double, min: Double, max: Double): Double = {
    if (value < min) min
    else if (value > max)  max
    else value
  }

  def sumsq(x: Double, y: Double, z: Double) = x * x + y * y + z * z

  def pythag(x: Double, y: Double, z: Double) = Math.sqrt(x * x + y * y + z * z)
}