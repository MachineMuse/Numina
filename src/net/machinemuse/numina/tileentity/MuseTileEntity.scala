package net.machinemuse.numina.tileentity

import net.minecraft.tileentity.TileEntity
import net.minecraft.network.INetworkManager
import net.minecraft.network.packet.{Packet, Packet132TileEntityData}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.item.ItemStack

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:46 AM, 11/13/13
 */
class MuseTileEntity extends TileEntity {

  override def onDataPacket(net: INetworkManager, pkt: Packet132TileEntityData) {
    readFromNBT(pkt.data)
    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
  }

  override def getDescriptionPacket: Packet = {
    val tag = new NBTTagCompound
    writeToNBT(tag)
    new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, tag)
  }

  def getInteger(nbt: NBTTagCompound, name: String) = if (nbt.hasKey(name)) Some(nbt.getInteger(name)) else None

  def getDouble(nbt: NBTTagCompound, name: String) = if (nbt.hasKey(name)) Some(nbt.getDouble(name)) else None

  def getBoolean(nbt: NBTTagCompound, name: String) = if (nbt.hasKey(name)) Some(nbt.getBoolean(name)) else None

  def getItemStack(nbt: NBTTagCompound, name: String) = if (nbt.hasKey(name)) Some(ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(name))) else None

  def writeItemStack(nbt:NBTTagCompound, name:String, stack:ItemStack) {
    val itemnbt = new NBTTagCompound()
    stack.writeToNBT(itemnbt)
    nbt.setCompoundTag(name, itemnbt)
  }
}
