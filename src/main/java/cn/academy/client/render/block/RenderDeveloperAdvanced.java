package cn.academy.client.render.block;

import cn.academy.Resources;
import cn.academy.block.tileentity.TileDeveloper;
import cn.lambdalib2.multiblock.RenderBlockMulti;
import cn.lambdalib2.registry.mc.RegTileEntityRender;
import cn.lambdalib2.render.obj.ObjLegacyRender;
import cn.lambdalib2.render.obj.ObjVBORenderer;
import cn.lambdalib2.render.obj.ObjVaoRenderer;
import cn.lambdalib2.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderDeveloperAdvanced extends RenderBlockMulti<TileDeveloper> {

    @RegTileEntityRender(TileDeveloper.Advanced.class)
    public static final RenderDeveloperAdvanced instance = new RenderDeveloperAdvanced();

    private final ResourceLocation texture = Resources.getTexture("models/developer_advanced");
    private final ObjVaoRenderer mdl = Resources.getModel("developer_advanced");

    @Override
    public void drawAtOrigin(TileDeveloper te) {
        // Use GlStateManager for better state management
        GlStateManager.pushMatrix();

        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Disable lighting if the model has its own lighting, or looks better without it
        RenderHelper.disableStandardItemLighting();

        GlStateManager.rotate(180f, 0, 1, 0);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);

        RenderUtils.loadTexture(texture);

        mdl.justRenderAll(); // begin/render/end wrapped in one call

        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }

}