package net.machinemuse.numina.tileentity

import net.machinemuse.numina.general.MuseLogger
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.FMLCommonHandler

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:46 AM, 11/13/13
 */
class MuseTileEntity extends TileEntity {
  override def onDataPacket(net: NetworkManager, pkt: SPacketUpdateTileEntity) {
    readFromNBT(pkt.getNbtCompound)
    val state = getWorld.getBlockState(getPos)
    getWorld.notifyBlockUpdate(getPos, state, state, 3)
  }

  override def getUpdatePacket(): SPacketUpdateTileEntity = {
    new SPacketUpdateTileEntity(getPos, getBlockMetadata, getUpdateTag)
  }

  override def getUpdateTag(): NBTTagCompound = writeToNBT(new NBTTagCompound())

  def getInteger(nbt: NBTTagCompound, name: String) = if (nbt.hasKey(name)) Some(nbt.getInteger(name)) else None

  def getDouble(nbt: NBTTagCompound, name: String) = if (nbt.hasKey(name)) Some(nbt.getDouble(name)) else None

  def getBoolean(nbt: NBTTagCompound, name: String) = if (nbt.hasKey(name)) Some(nbt.getBoolean(name)) else None

  def getItemStack(nbt: NBTTagCompound, name: String) = if (nbt.hasKey(name)) Some(ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(name))) else None

  def writeItemStack(nbt:NBTTagCompound, name:String, stack:ItemStack) {
    val itemnbt = new NBTTagCompound()
    stack.writeToNBT(itemnbt)
    nbt.setTag(name, itemnbt)
  }

  override def shouldRefresh(world: World,
                             pos: BlockPos,
                             oldState: IBlockState,
                             newSate: IBlockState): Boolean = oldState.getBlock != newSate.getBlock

}
