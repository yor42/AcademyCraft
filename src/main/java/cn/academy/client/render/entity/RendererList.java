package cn.academy.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author WeAthFolD
 *
 */
public class RendererList<T extends Entity> extends Render<T> {
    
    List<Render<T>> renderers = new ArrayList<>();
    
    public RendererList(RenderManager rm, Render<T> ...rs) {
        super(rm);
        renderers.addAll(Arrays.asList(rs));
    }
    
    public RendererList<T> append(Render<T> e) {
        renderers.add(e);
        return this;
    }

    @Override
    public void doRender(T ent, double x,
            double y, double z, float a, float b) {
        for(Render<T> r : renderers)
            r.doRender(ent, x, y, z, a, b);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }

}