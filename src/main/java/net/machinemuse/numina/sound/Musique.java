package net.machinemuse.numina.sound;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.machinemuse.numina.basemod.NuminaConfig;
import net.machinemuse.numina.general.MuseLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

/**
 * Created by Claire on 8/27/2015.
 *
 * Ported to Java by lehjr on 10/22/16.
 */
@SideOnly(Side.CLIENT)
public final class Musique
{
    private static final HashMap<String, MovingSoundPlayer> soundMap = new HashMap<>();
    private static final String soundprefix = "Numina";

    @SideOnly(value=Side.CLIENT)
    public static SoundHandler mcsound() {
        return Minecraft.getMinecraft().getSoundHandler();
    }

    public GameSettings options() {
        return Minecraft.getMinecraft().gameSettings;
    }

    public static void playClientSound(String soundResourceLocation, float volume) {
        if ((FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) && NuminaConfig.useSounds()) {
            // creates a sound
            PositionedSoundRecord sound = PositionedSoundRecord.func_147674_a(new ResourceLocation(soundResourceLocation), volume);
            mcsound().playSound(sound);
        }
    }

    public static String makeSoundString(final EntityPlayer player, final String soundname) {
        return soundprefix + player.getCommandSenderName() + soundname;
    }

    public static void playerSound(final EntityPlayer player, final String soundname, final float volume, Float pitch, Boolean continuous) {
        pitch = (pitch != null) ? pitch : 1.0F;
        continuous = (continuous != null) ? continuous : true;
        if ((FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) && NuminaConfig.useSounds()) {
            String soundID = makeSoundString(player, soundname);
            MovingSoundPlayer sound = soundMap.get(soundID);
            if (sound != null && (sound.isDonePlaying() || !sound.canRepeat())) {
                stopPlayerSound(player, soundname);
                sound = null;
            }
            if (sound != null) {
                sound.updateVolume(volume).updatePitch(pitch).updateRepeat(continuous);
            } else {
                MuseLogger.logDebug("New sound: " + soundname);
                MovingSoundPlayer newsound = new MovingSoundPlayer(player, new ResourceLocation(soundname), volume * 2.0f, pitch, continuous);
                mcsound().playSound(newsound);
                soundMap.put(soundID, newsound);
            }
        }
    }

    public static void stopPlayerSound(final EntityPlayer player, final String soundname) {
        if ((FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) && NuminaConfig.useSounds()) {
            String soundID = makeSoundString(player, soundname);
            MovingSoundPlayer sound = soundMap.get(soundID);
            MuseLogger.logDebug("Sound stopped: " + soundname);
            if (sound != null) {
                sound.stopPlaying();
                mcsound().stopSound(sound);
            }
            soundMap.remove(soundID);
        }
    }
}