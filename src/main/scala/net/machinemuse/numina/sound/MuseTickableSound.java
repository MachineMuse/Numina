package net.machinemuse.numina.sound;

import net.minecraft.client.audio.ITickableSound;

public class MuseTickableSound extends MuseSound implements ITickableSound {
    private ISoundSource soundSource;

    public MuseTickableSound(ISoundSource soundSource) {
        super(soundSource.getSound());
        this.soundSource = soundSource;
    }

    @Override
    public boolean isDonePlaying() {
        return !soundSource.shouldSoundPlay();
    }

    @Override
    public void update() {
        soundSource.onSoundTick();
    }
}
