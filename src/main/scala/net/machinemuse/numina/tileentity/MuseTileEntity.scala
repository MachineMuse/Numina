package net.machinemuse.numina.tileentity

import net.minecraft.block.state.IBlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.network.NetworkManager
import net.minecraft.network.Packet
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.item.ItemStack

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:46 AM, 11/13/13
 */
class MuseTileEntity extends TileEntity {
  // TODO: test and clean up
  // getDescriptionPacket is no longer used available

  override def onDataPacket(net: NetworkManager, pkt: SPacketUpdateTileEntity) {
    readFromNBT(pkt.getNbtCompound())

    // 1.7.10
//    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
    // 1.8.9
//    worldObj.markBlockForUpdate(pos)

    // 1.9.4 ?? TODO: does this still need markBlockForUpdate??
  }

  override def getUpdatePacket: SPacketUpdateTileEntity = {
    val tag = new NBTTagCompound
    writeToNBT(tag)
    new SPacketUpdateTileEntity(getPos, 0, tag)
  }

  override def getUpdateTag : NBTTagCompound = writeToNBT(new NBTTagCompound())


  def getInteger(nbt: NBTTagCompound, name: String) = if (nbt.hasKey(name)) Some(nbt.getInteger(name)) else None

  def getDouble(nbt: NBTTagCompound, name: String) = if (nbt.hasKey(name)) Some(nbt.getDouble(name)) else None

  def getBoolean(nbt: NBTTagCompound, name: String) = if (nbt.hasKey(name)) Some(nbt.getBoolean(name)) else None

  def getItemStack(nbt: NBTTagCompound, name: String) = if (nbt.hasKey(name)) Some(ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(name))) else None

  def writeItemStack(nbt:NBTTagCompound, name:String, stack:ItemStack) {
    val itemnbt = new NBTTagCompound()
    stack.writeToNBT(itemnbt)
    nbt.setTag(name, itemnbt)
  }
}
