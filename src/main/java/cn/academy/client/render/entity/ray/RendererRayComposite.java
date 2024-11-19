package cn.academy.client.render.entity.ray;

import cn.academy.client.render.entity.RendererList;
import cn.academy.entity.EntityRayBase;
import cn.academy.entity.IRay;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * @author WeAthFolD
 *
 */
public class RendererRayComposite<T extends EntityRayBase&IRay> extends RendererList<T> {
    
    public RendererRayGlow<T> glow;
    public RendererRayCylinder<T> cylinderIn, cylinderOut;
    
    public RendererRayComposite(RenderManager manager, String name) {
        super(manager);
        append(glow = RendererRayGlow.createFromName(manager, name));
        append(cylinderIn = new RendererRayCylinder<>(manager, 0.05f));
        append(cylinderOut = new RendererRayCylinder<>(manager, 0.08f));
        cylinderIn.headFix = 0.98;
    }
    
    @Override
    public void doRender(T ent, double x,
            double y, double z, float a, float b) {
        ((IRay)ent).onRenderTick();
        super.doRender(ent, x, y, z, a, b);
    }
    
    public void plainDoRender(T ent, double x,
            double y, double z, float a, float b) {
        super.doRender(ent, x, y, z, a, b);
    }

    @Override
    protected ResourceLocation getEntityTexture(T p_110775_1_) {
        return null;
    }

}