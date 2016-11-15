package net.machinemuse.numina.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:46 AM, 11/13/13
 *
 * Ported to Java lehjr on 10/10/16.
 */
public class MuseTileEntity extends TileEntity {

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
    }

    public Integer getInteger(NBTTagCompound nbt, String name) {
        if (nbt.hasKey(name))
            return nbt.getInteger(name);
        else
            return null;
    }

    public Double getDouble(NBTTagCompound nbt, String name) {
        if (nbt.hasKey(name))
            return nbt.getDouble(name);
        else
            return null;
    }

    public Boolean getBoolean(NBTTagCompound nbt, String name) {
        if (nbt.hasKey(name))
            return nbt.getBoolean(name);
        else
            return null;
    }

    public ItemStack getItemStack(NBTTagCompound nbt, String name) {
        if (nbt.hasKey(name))
            return ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(name));
        else
            return null;
    }

    public void writeItemStack(NBTTagCompound nbt, String name, ItemStack stack) {
        NBTTagCompound itemnbt = new NBTTagCompound();
        stack.writeToNBT(itemnbt);
        nbt.setTag(name, itemnbt);
    }
}