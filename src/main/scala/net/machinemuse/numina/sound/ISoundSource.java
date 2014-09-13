package net.machinemuse.numina.sound;

import net.minecraft.client.audio.ISound;

public interface ISoundSource {

    public ISound getSound();

    public boolean shouldSoundPlay();

    public void onSoundTick();
}
