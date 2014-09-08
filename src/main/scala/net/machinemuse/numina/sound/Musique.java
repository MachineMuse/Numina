package net.machinemuse.numina.sound;

import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;

/**
 * Taken from 8283Lib.
 *
 * @author Parker8283
 */
public class Musique implements ISound {
    private ResourceLocation soundLoc;
    private float volume, pitch, xPos, yPos, zPos;
    private boolean canRepeat;
    private int repeatDelay;
    private AttenuationType type;

    public Musique(String sound) {
        this(sound, 1.0F);
    }

    public Musique(String sound, float volume) {
        this(sound, volume, 1.0F);
    }

    public Musique(String sound, float volume, float pitch) {
        this(sound, volume, pitch, 0.0F, 0.0F, 0.0F, AttenuationType.NONE);
    }

    public Musique(String sound, float xPos, float yPos, float zPos) {
        this(sound, 1.0F, 1.0F, xPos, yPos, zPos, AttenuationType.LINEAR);
    }

    public Musique(String sound, boolean canRepeat, int repeatDelay) {
        this(sound, 1.0F, 1.0F, canRepeat, repeatDelay, 0.0F, 0.0F, 0.0F, AttenuationType.NONE);
    }

    public Musique(String sound, float volume, float pitch, float xPos, float yPos, float zPos, AttenuationType type) {
        this(sound, volume, pitch, false, 0, xPos, yPos, zPos, type);
    }

    public Musique(String sound, float volume, float pitch, boolean canRepeat, int repeatDelay, float xPos, float yPos, float zPos, AttenuationType type) {
        this(new ResourceLocation(sound), volume, pitch, canRepeat, repeatDelay, xPos, yPos, zPos, type);
    }

    public Musique(ResourceLocation soundLoc) {
        this(soundLoc, 1.0F);
    }

    public Musique(ResourceLocation soundLoc, float volume) {
        this(soundLoc, volume, 1.0F);
    }

    public Musique(ResourceLocation soundLoc, float volume, float pitch) {
        this(soundLoc, volume, pitch, 0.0F, 0.0F, 0.0F, AttenuationType.NONE);
    }

    public Musique(ResourceLocation soundLoc, float xPos, float yPos, float zPos) {
        this(soundLoc, 1.0F, 1.0F, xPos, yPos, zPos, AttenuationType.LINEAR);
    }

    public Musique(ResourceLocation soundLoc, boolean canRepeat, int repeatDelay) {
        this(soundLoc, 1.0F, 1.0F, canRepeat, repeatDelay, 0.0F, 0.0F, 0.0F, AttenuationType.NONE);
    }

    public Musique(ResourceLocation soundLoc, float volume, float pitch, float xPos, float yPos, float zPos, AttenuationType type) {
        this(soundLoc, volume, pitch, false, 0, xPos, yPos, zPos, type);
    }

    public Musique(ResourceLocation soundLoc, float volume, float pitch, boolean canRepeat, int repeatDelay, float xPos, float yPos, float zPos, AttenuationType type) {
        this.soundLoc = soundLoc;
        this.volume = volume;
        this.pitch = pitch;
        this.canRepeat = canRepeat;
        this.repeatDelay = repeatDelay;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.type = type;
    }

    public Musique setVolume(float volume) {
        this.volume = volume;
        return this;
    }

    public Musique setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public Musique setVolumeAndPitch(float volume, float pitch) {
        this.volume = volume;
        this.pitch = pitch;
        return this;
    }

    public Musique setLocation(float xPos, float yPos, float zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.type = AttenuationType.LINEAR;
        return this;
    }

    public Musique removeLocation() {
        this.xPos = this.yPos = this.zPos = 0.0F;
        this.type = AttenuationType.NONE;
        return this;
    }

    public Musique setRepeatable(int repeatDelay) {
        this.canRepeat = true;
        this.repeatDelay = repeatDelay;
        return this;
    }

    public Musique unsetRepeatable() {
        this.canRepeat = false;
        this.repeatDelay = 0;
        return this;
    }

    @Override
    public ResourceLocation getPositionedSoundLocation() {
        return soundLoc;
    }

    @Override
    public boolean canRepeat() {
        return canRepeat;
    }

    @Override
    public int getRepeatDelay() {
        return repeatDelay;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public float getXPosF() {
        return xPos;
    }

    @Override
    public float getYPosF() {
        return yPos;
    }

    @Override
    public float getZPosF() {
        return zPos;
    }

    @Override
    public AttenuationType getAttenuationType() {
        return type;
    }
}