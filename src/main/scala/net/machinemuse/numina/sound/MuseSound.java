package net.machinemuse.numina.sound;

import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;

public class MuseSound implements ISound {
    private ResourceLocation soundLoc;
    private float volume, pitch, xPos, yPos, zPos;
    private boolean canRepeat;
    private int repeatDelay;
    private AttenuationType type;

    public MuseSound(String sound) {
        this(sound, 1.0F);
    }

    public MuseSound(String sound, float volume) {
        this(sound, volume, 1.0F);
    }

    public MuseSound(String sound, float volume, float pitch) {
        this(sound, volume, pitch, 0.0F, 0.0F, 0.0F, AttenuationType.NONE);
    }

    public MuseSound(String sound, float xPos, float yPos, float zPos) {
        this(sound, 1.0F, 1.0F, xPos, yPos, zPos, AttenuationType.LINEAR);
    }

    public MuseSound(String sound, boolean canRepeat, int repeatDelay) {
        this(sound, 1.0F, 1.0F, canRepeat, repeatDelay, 0.0F, 0.0F, 0.0F, AttenuationType.NONE);
    }

    public MuseSound(String sound, float volume, float pitch, float xPos, float yPos, float zPos, AttenuationType type) {
        this(sound, volume, pitch, false, 0, xPos, yPos, zPos, type);
    }

    public MuseSound(String sound, float volume, float pitch, boolean canRepeat, int repeatDelay, float xPos, float yPos, float zPos, AttenuationType type) {
        this(new ResourceLocation(sound), volume, pitch, canRepeat, repeatDelay, xPos, yPos, zPos, type);
    }

    public MuseSound(ResourceLocation soundLoc) {
        this(soundLoc, 1.0F);
    }

    public MuseSound(ResourceLocation soundLoc, float volume) {
        this(soundLoc, volume, 1.0F);
    }

    public MuseSound(ResourceLocation soundLoc, float volume, float pitch) {
        this(soundLoc, volume, pitch, 0.0F, 0.0F, 0.0F, AttenuationType.NONE);
    }

    public MuseSound(ResourceLocation soundLoc, float xPos, float yPos, float zPos) {
        this(soundLoc, 1.0F, 1.0F, xPos, yPos, zPos, AttenuationType.LINEAR);
    }

    public MuseSound(ResourceLocation soundLoc, boolean canRepeat, int repeatDelay) {
        this(soundLoc, 1.0F, 1.0F, canRepeat, repeatDelay, 0.0F, 0.0F, 0.0F, AttenuationType.NONE);
    }

    public MuseSound(ResourceLocation soundLoc, float volume, float pitch, float xPos, float yPos, float zPos, AttenuationType type) {
        this(soundLoc, volume, pitch, false, 0, xPos, yPos, zPos, type);
    }

    public MuseSound(ResourceLocation soundLoc, float volume, float pitch, boolean canRepeat, int repeatDelay, float xPos, float yPos, float zPos, AttenuationType type) {
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

    public MuseSound(ISound other) {
        this.soundLoc = other.getPositionedSoundLocation();
        this.volume = other.getVolume();
        this.pitch = other.getPitch();
        this.canRepeat = other.canRepeat();
        this.repeatDelay = other.getRepeatDelay();
        this.xPos = other.getXPosF();
        this.yPos = other.getYPosF();
        this.zPos = other.getZPosF();
        this.type = other.getAttenuationType();
    }

    public MuseSound setVolume(float volume) {
        this.volume = volume;
        return this;
    }

    public MuseSound setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public MuseSound setVolumeAndPitch(float volume, float pitch) {
        this.volume = volume;
        this.pitch = pitch;
        return this;
    }

    public MuseSound setLocation(float xPos, float yPos, float zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.type = AttenuationType.LINEAR;
        return this;
    }

    public MuseSound removeLocation() {
        this.xPos = this.yPos = this.zPos = 0.0F;
        this.type = AttenuationType.NONE;
        return this;
    }

    public MuseSound setRepeatable(int repeatDelay) {
        this.canRepeat = true;
        this.repeatDelay = repeatDelay;
        return this;
    }

    public MuseSound unsetRepeatable() {
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