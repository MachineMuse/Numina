package net.machinemuse.numina.render;

import net.machinemuse.numina.general.MuseMathUtils;
import net.machinemuse.numina.geometry.Colour;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:37 PM, 9/6/13
 *
 * Ported to Java by lehjr on 10/25/16.
 */
public final class MuseIconUtils
{
    /**
     * Draws a MuseIcon
     *
     * @param x
     * @param y
     * @param icon
     * @param colour
     */
    public static void drawIconAt(final double x, final double y, final IIcon icon, final Colour colour) {
        drawIconPartial(x, y, icon, colour, 0, 0, 16, 16);
    }

    public static void drawIconPartialOccluded(final double x, final double y, final IIcon icon, final Colour colour, final double left, final double top, final double right, final double bottom) {
        double xmin = MuseMathUtils.clampDouble(left - x, 0, 16);
        double ymin = MuseMathUtils.clampDouble(top - y, 0, 16);
        double xmax = MuseMathUtils.clampDouble(right - x, 0, 16);
        double ymax = MuseMathUtils.clampDouble(bottom - y, 0, 16);
        drawIconPartial(x, y, icon, colour, xmin, ymin, xmax, ymax);
    }

    /**
     * Draws a MuseIcon
     *
     * @param x
     * @param y
     * @param icon
     * @param colour
     */
    public static void drawIconPartial(final double x, final double y, final IIcon icon, final Colour colour, final double left, final double top, final double right, final double bottom) {
        if (icon == null) {
            return;
        }
        GL11.glPushMatrix();
        RenderState.on2D();
        RenderState.blendingOn();
        if (colour != null) {
            colour.doGL();
        }
        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        float u1 = icon.getMinU();
        float v1 = icon.getMinV();
        float u2 = icon.getMaxU();
        float v2 = icon.getMaxV();
        double xoffset1 = left * (u2 - u1) / 16.0f;
        double yoffset1 = top * (v2 - v1) / 16.0f;
        double xoffset2 = right * (u2 - u1) / 16.0f;
        double yoffset2 = bottom * (v2 - v1) / 16.0f;
        tess.addVertexWithUV(x + left, y + top, 0, u1 + xoffset1, v1 + yoffset1);
        tess.addVertexWithUV(x + left, y + bottom, 0, u1 + xoffset1, v1 + yoffset2);
        tess.addVertexWithUV(x + right, y + bottom, 0, u1 + xoffset2, v1 + yoffset2);
        tess.addVertexWithUV(x + right, y + top, 0, u1 + xoffset2, v1 + yoffset1);
        tess.draw();
        RenderState.blendingOff();
        RenderState.off2D();
        GL11.glPopMatrix();
    }
}