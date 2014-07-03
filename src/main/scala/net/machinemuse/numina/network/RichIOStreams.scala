package net.machinemuse.numina.network

import java.io.{DataOutputStream, DataInputStream}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.CompressedStreamTools

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 1:15 AM, 09/05/13
 */
object RichInputStream {
  implicit def toRichStream(in: DataInputStream): RichInputStream = new RichInputStream(in)

  class RichInputStream(in: DataInputStream) {
    def readIntArray = (for (k <- 0 until in.readInt) yield in.readInt).toArray

    /**
     * Reads an ItemStack from the InputStream
     */
    def readItemStack = {
      val item: Item = Item.itemRegistry.getObject(in.readString).asInstanceOf[Item]
      val stackSize: Byte = in.readByte
      val damageAmount: Short = in.readShort
      val stack = new ItemStack(item, stackSize, damageAmount)
      stack.stackTagCompound = readNBTTagCompound
      stack
    }

    /**
     * Reads a compressed NBTTagCompound from the InputStream
     */
    def readNBTTagCompound = {
      val nbtSize: Short = in.readShort
      nbtSize match {
        case -1 => null
        case s =>
          val fullData = new Array[Byte](s)
          in.readFully(fullData)
          CompressedStreamTools.decompress(fullData)
      }
    }

    /**
     * Reads a string from a packet
     */

    def readString = {
      val builder: StringBuilder = StringBuilder.newBuilder
      for (i <- 0 until in.readShort) builder.append(in.readChar)
      builder.toString()
    }
  }

}

object RichOutputStream {
  implicit def toRichStream(out: DataOutputStream): RichOutputStream = new RichOutputStream(out)

  class RichOutputStream(out: DataOutputStream) {


  }

}