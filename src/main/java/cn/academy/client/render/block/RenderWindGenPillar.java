package cn.academy.client.render.block;

import cn.academy.Resources;
import cn.academy.block.tileentity.TileWindGenPillar;
import cn.lambdalib2.registry.mc.RegTileEntityRender;
import cn.lambdalib2.render.obj.ObjLegacyRender;
import cn.lambdalib2.render.obj.ObjVBORenderer;
import cn.lambdalib2.render.obj.ObjVaoRenderer;
import cn.lambdalib2.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderWindGenPillar extends TileEntitySpecialRenderer<TileWindGenPillar> {

    @RegTileEntityRender(TileWindGenPillar.class)
    public static final RenderWindGenPillar instance = new RenderWindGenPillar();

    private final ObjVaoRenderer mdl = Resources.getModel("windgen_pillar");

    private final ResourceLocation texture = Resources.getTexture("models/windgen_pillar");

    @Override
    public void render(TileWindGenPillar te, double x, double y, double z, float partialTicks,
                       int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        RenderUtils.loadTexture(texture);
        GlStateManager.translate(x + .5, y, z + .5);
        mdl.justRenderAll();
        GlStateManager.popMatrix();
    }

}