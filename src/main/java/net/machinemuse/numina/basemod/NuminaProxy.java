package net.machinemuse.numina.basemod;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:57 AM, 9/3/13
 *
 * Ported to Java by lehjr on 10/26/16.
 */
public interface NuminaProxy
{
    void PreInit();

    void Init();

    void PostInit();

//    void sendPacketToClient(final MusePacket p0, final EntityPlayerMP p1);
//
//    void sendPacketToServer(final MusePacket p0);
}
