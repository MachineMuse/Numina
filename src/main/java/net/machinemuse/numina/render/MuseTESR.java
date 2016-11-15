package net.machinemuse.numina.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:28 PM, 8/3/13
 *
 * Ported to Java by lehjr on 10/23/16.
 */
public class MuseTESR extends TileEntitySpecialRenderer {
    public void bindTextureByName(String tex) {
        this.bindTexture(new ResourceLocation(tex));
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
        this.renderAt(tileentity, d0, d1, d2, f);
    }

    public void renderAt(TileEntity var1, double var2, double var4, double var6, float var8) {

    }
}
