package cn.academy.client.render.block;

import cn.academy.Resources;
import cn.academy.block.tileentity.TileSolarGen;
import cn.lambdalib2.multiblock.RenderBlockMulti;
import cn.lambdalib2.registry.mc.RegTileEntityRender;
import cn.lambdalib2.render.obj.ObjLegacyRender;
import cn.lambdalib2.util.RenderUtils;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author WeAthFolD
 */
public class RenderSolarGen extends RenderBlockMulti<TileSolarGen> {

    @RegTileEntityRender(TileSolarGen.class)
    public static RenderSolarGen instance = new RenderSolarGen();

    private final ObjLegacyRender _mdl = Resources.getModel("solar");

    private final ResourceLocation _texture = Resources.getTexture("models/solar");

    @Override
    public void drawAtOrigin(TileSolarGen te) {
        GL11.glRotated(90, 0, 1, 0);
        float SCALE = 0.014f;
        GL11.glScaled(SCALE, SCALE, SCALE);
        RenderUtils.loadTexture(_texture);
        _mdl.renderAll();
    }

}