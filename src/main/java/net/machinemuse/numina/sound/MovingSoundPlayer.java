package net.machinemuse.numina.sound;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Ported to Java by lehjr on 10/22/16.
 */
public class MovingSoundPlayer extends MovingSound {
    private static EntityPlayer player;


    public MovingSoundPlayer(final EntityPlayer player, final ResourceLocation resourceLocation, final float newvolume, final float newpitch, final boolean newrepeat) {
        super(resourceLocation);
        this.player = player;

        this.field_147663_c = newpitch;
        this.volume = newvolume;
        this.repeat = newrepeat;
    }

    public EntityPlayer player() {
        return this.player;
    }

    public MovingSoundPlayer updatePitch(final float newpitch) {
        this.field_147663_c = newpitch;
        return this;
    }

    public MovingSoundPlayer updateVolume(final float newvolume) {
        this.volume = (4.0f * this.volume + newvolume) / 5.0f;
        return this;
    }

    public MovingSoundPlayer updateRepeat(final boolean newrepeat) {
        this.repeat = newrepeat;
        return this;
    }

    @Override
    public ISound.AttenuationType getAttenuationType() {
        return ISound.AttenuationType.LINEAR;
    }

    public void stopPlaying() {
        super.donePlaying = true;
    }

    @Override
    public void update() {
        this.xPosF = (float)this.player().posX;
        this.yPosF = (float)this.player().posY;
        this.zPosF = (float)this.player().posZ;
    }
}