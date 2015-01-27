package net.machinemuse.numina.network

import java.io.{DataOutputStream, DataInputStream, InputStreamReader, InputStream, IOException}

import cpw.mods.fml.common.network.internal.FMLProxyPacket
import cpw.mods.fml.common.network.simpleimpl.IMessage
import cpw.mods.fml.relauncher.{Side, SideOnly}
import io.netty.buffer.{ByteBufOutputStream, Unpooled, ByteBuf}
import net.machinemuse.numina.basemod.Numina
import net.machinemuse.numina.general.MuseLogger
import net.minecraft.client.entity.EntityClientPlayerMP
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{CompressedStreamTools, NBTTagCompound}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:58 AM, 09/05/13
 */
abstract class MusePacket {
  val packager: MusePackager

  def write()

  val bytes = Unpooled.buffer()
  val dataout = new DataOutputStream(new ByteBufOutputStream(bytes))

  /**
   * Gets the MC packet associated with this MusePacket
   *
   * @return Packet250CustomPayload
   */
  def getFMLProxyPacket: FMLProxyPacket = {
    dataout.writeInt(MusePacketHandler.packagers.inverse.get(packager).get)
    write()
    new FMLProxyPacket(bytes, MusePacketHandler.networkChannelName)
  }

  def getPacket131 = this

  /**
   * Called by the network manager since it does all the packet mapping
   *
   * @param player
   */
  @SideOnly(Side.CLIENT)
  def handleClient(player: EntityClientPlayerMP) {}

  def handleServer(player: EntityPlayerMP) {}

  def writeInt(i: Int) {
    dataout.writeInt(i)
  }


  def writeIntArray(data: Array[Int]) {
    try {
      dataout.writeInt(data.length)
      for (k <- 0 until data.length) {
        dataout.writeInt(data(k))
      }
    } catch {
      case e: IOException => {
        e.printStackTrace()
      }
    }
  }

  def writeBoolean(b: Boolean) {
    try {
      dataout.writeBoolean(b)
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

  def writeDouble(i: Double) {
    try {
      dataout.writeDouble(i)
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

  /**
   * Writes the IC2ItemTest's ID (short), then size (byte), then damage. (short)
   */
  def writeItemStack(stack: ItemStack) {
    try {
      if (stack == null) {
        dataout.writeShort(-1)
      }
      else {
        val nbt = new NBTTagCompound
        stack.writeToNBT(nbt)
        writeNBTTagCompound(nbt)
      }
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

  /**
   * Writes a compressed NBTTagCompound to the OutputStream
   */
  protected def writeNBTTagCompound(nbt: NBTTagCompound) {
    if (nbt == null) {
      dataout.writeShort(-1)
    } else {
      val compressednbt: Array[Byte] = CompressedStreamTools.compress(nbt)
      dataout.writeShort(compressednbt.length.toShort)
      dataout.write(compressednbt)
    }
  }

  /**
   * Writes a String to the DataOutputStream
   */
  def writeString(string: String) {
    try {
      dataout.writeShort(string.length)
      dataout.writeChars(string)
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }
  
  def writeStream(input: InputStream) {
      try {
          val reader: InputStreamReader = new InputStreamReader(input)
          val out: String = reader.toString
          dataout.writeBytes(out)
      } catch {
        case e: IOException => e.printStackTrace()
      }
  }
}

trait MusePackager {
  val READ_ERROR: Short = -150

  import net.machinemuse.numina.network.RichInputStream._

  def read(datain: DataInputStream, player: EntityPlayer): MusePacket

  def readByte(datain: DataInputStream): Byte = datain.readByte

  def readShort(datain: DataInputStream): Short = datain.readShort

  def readInt(datain: DataInputStream): Int = datain.readInt

  def readLong(datain: DataInputStream): Long = datain.readLong

  def readBoolean(datain: DataInputStream): Boolean = datain.readBoolean

  def readFloat(datain: DataInputStream): Float = datain.readFloat

  def readDouble(datain: DataInputStream): Double = datain.readDouble

  def readIntArray(datain: DataInputStream): Array[Int] = datain.readIntArray

  def readString(datain: DataInputStream): String = datain.readString

  def readItemStack(datain: DataInputStream): ItemStack = datain.readItemStack

  def readNBTTagCompound(datain: DataInputStream): NBTTagCompound = datain.readNBTTagCompound

  def safeRead[T](codec: () => T): Option[T] = {
    try {
      Some(codec.apply())
    } catch {
      case e: IOException => MuseLogger.logException("PROBLEM READING DATA FROM PACKET D:", e)
    }
  }
}
