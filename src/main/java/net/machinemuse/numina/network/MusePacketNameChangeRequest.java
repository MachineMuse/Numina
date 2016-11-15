package net.machinemuse.numina.network;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;

import java.io.DataInputStream;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 8:50 PM, 9/6/13
 *
 * Ported to Java by lehjr on 11/14/16.
 */
public class MusePacketNameChangeRequest extends MusePacket {
    String username;
    String newnick;
    int entityID;

    public MusePacketNameChangeRequest(EntityPlayer player, String username, String newnick, int entityID) {
        this.username = username;
        this.newnick = newnick;
        this.entityID = entityID;
    }

    @Override
    public IMusePackager packager() {
        return getPackagerInstance();
    }

    @Override
    public void write() {
        writeString(username);
        writeString(newnick);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleClient(EntityClientPlayerMP player) {
        EntityPlayer anotherPlayer = (EntityPlayer) player.worldObj.getEntityByID(entityID);
        anotherPlayer.refreshDisplayName();
    }

    private static MusePacketNameChangeRequestPackager PACKAGERINSTANCE;

    public static MusePacketNameChangeRequestPackager getPackagerInstance() {
        if (PACKAGERINSTANCE == null)
            PACKAGERINSTANCE = new MusePacketNameChangeRequestPackager();
        return PACKAGERINSTANCE;
    }

    public static class MusePacketNameChangeRequestPackager extends MusePackager {
        @Override
        public MusePacket read(DataInputStream datain, EntityPlayer player) {
            String username = readString(datain);
            String newnick = readString(datain);
            return new MusePacketNameChangeRequest(player, username, newnick, 0);
        }
    }
}






