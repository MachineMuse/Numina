package net.machinemuse.numina.network

import java.io.{DataInputStream, DataOutputStream}

import net.minecraft.item.ItemStack
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
    def readItemStack = ItemStack.loadItemStackFromNBT(readNBTTagCompound)

    /**
     * Reads a compressed NBTTagCompound from the InputStream
     */
    def readNBTTagCompound = {
      val length = in.readShort()
      if (length != -1) {
        CompressedStreamTools.readCompressed(in)
      } else {
        null
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