package net.machinemuse.numina.network;

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import net.machinemuse.numina.general.MuseLogger;
import net.machinemuse.numina.scala.MuseNumericRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.List;

/**
 * @author MachineMuse
 *
 * Ported to Java by lehjr on 10/23/16.
 */
@ChannelHandler.Sharable
public final class MusePacketHandler extends MessageToMessageCodec<FMLProxyPacket, MusePacket>
{
    public static String networkChannelName;
    public static MuseNumericRegistry<IMusePackager> packagers;
    public static EnumMap<Side, FMLEmbeddedChannel> channels;


    private MusePacketHandler() {
        this.networkChannelName = "Numina";
        this.packagers = new MuseNumericRegistry<>();
        this.channels = NetworkRegistry.INSTANCE.newChannel(this.networkChannelName, new ChannelHandler[] { this });
    }

    static {
        new MusePacketHandler();
    }

    public void encode(final ChannelHandlerContext ctx, final MusePacket msg, final List<Object> out) {
        try {
            out.add(msg.getFMLProxyPacket());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decode(final ChannelHandlerContext ctx, final FMLProxyPacket msg, final List<Object> out) {
        final DataInputStream data = new DataInputStream((InputStream)new ByteBufInputStream(msg.payload()));
        int packetType = 0;
        try {
            final INetHandler handler = msg.handler();
            if (handler instanceof NetHandlerPlayServer) {
                final EntityPlayerMP playerServer = ((NetHandlerPlayServer)handler).playerEntity;
                packetType = data.readInt();
                IMusePackager packagerServer = this.packagers.get(packetType);
                MusePacket packetServer = packagerServer.read(data, playerServer);
                packetServer.handleServer(playerServer);
            }
            else {
                if (!(handler instanceof NetHandlerPlayClient)) {
                    throw new IOException("Error with (INetHandler) handler. Should be instance of NetHandlerPlayClient.");
                }
                final EntityClientPlayerMP playerClient = Minecraft.getMinecraft().thePlayer;
                packetType = data.readInt();
                IMusePackager packagerClient = this.packagers.get(packetType);
                MusePacket packetClient = packagerClient.read(data, playerClient);
                packetClient.handleClient(playerClient);
            }
        }
        catch (IOException exception) {
            MuseLogger.logException("PROBLEM READING PACKET IN DECODE STEP D:", exception);
        }
    }
}