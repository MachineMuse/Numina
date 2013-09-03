package net.machinemuse.numina.scala

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:16 PM, 6/28/13
 */
object OptionCast {
  def apply[T: Manifest](x: Any): Option[T] = {
    if (implicitly[Manifest[T]].runtimeClass.isInstance(x)) {
      Some(x.asInstanceOf[T])
    } else {
      None
    }
  }
}
