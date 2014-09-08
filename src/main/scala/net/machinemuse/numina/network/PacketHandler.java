package net.machinemuse.numina.network;

import net.machinemuse.numina.network.message.MusePacketModeChangeRequest;
import net.machinemuse.numina.network.message.MusePacketRecipeUpdate;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("numina");

    public static void init() {
        //TODO Register packets here
        INSTANCE.registerMessage(MusePacketModeChangeRequest.class, MusePacketModeChangeRequest.class, 0, Side.SERVER);
        INSTANCE.registerMessage(MusePacketRecipeUpdate.class, MusePacketRecipeUpdate.class, 1, Side.CLIENT);
    }
}
