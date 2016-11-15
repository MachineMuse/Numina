package net.machinemuse.numina.basemod;

import net.machinemuse.numina.event.FOVUpdateEventHandler;
import net.machinemuse.numina.event.KeybindKeyHandler;
import net.machinemuse.numina.general.MuseLogger;
import net.machinemuse.numina.mouse.MouseEventHandler;
import net.machinemuse.numina.render.RenderGameOverlayEventHandler;
import net.minecraftforge.common.MinecraftForge;

/**
 * Ported to Java by lehjr on 10/26/16.
 */
public class NuminaProxyClient implements NuminaProxy
{
    @Override
    public void PreInit() {
//        NuminaProxy$class.PreInit(this);
    }

    @Override
    public void PostInit() {
//        NuminaProxy$class.PostInit(this);
    }

//    @Override
//    public void sendPacketToClient(final MusePacket packet, final EntityPlayerMP player) {
//        NuminaProxy.sendPacketToClient(this, packet, player);
//    }
//
//    @Override
//    public void sendPacketToServer(final MusePacket packet) {
//        NuminaProxy$class.sendPacketToServer(this, packet);
//    }

    @Override
    public void Init() {
        MuseLogger.logDebug("Client Proxy Started");
        MinecraftForge.EVENT_BUS.register((Object)new MouseEventHandler());
        MinecraftForge.EVENT_BUS.register((Object)new RenderGameOverlayEventHandler());
        MinecraftForge.EVENT_BUS.register((Object)new FOVUpdateEventHandler());
        MinecraftForge.EVENT_BUS.register((Object)new KeybindKeyHandler());
    }

    public NuminaProxyClient() {
//        NuminaProxy$class.$init$(this);
    }
}
