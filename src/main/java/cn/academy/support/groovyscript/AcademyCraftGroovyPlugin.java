package cn.academy.support.groovyscript;

import cn.academy.AcademyCraft;
import cn.academy.support.groovyscript.modules.ImagFusor;
import cn.academy.support.groovyscript.modules.MetalFormer;
import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.GroovyContainer;
import com.cleanroommc.groovyscript.documentation.linkgenerator.LinkGeneratorHooks;

import static cn.academy.AcademyCraft.NAME;

public class AcademyCraftGroovyPlugin implements GroovyPlugin {

    public static final ImagFusor FUSOR = new ImagFusor();
    public static final MetalFormer FORMER = new MetalFormer();

    @Override
    public String getModId() {
        return AcademyCraft.MODID;
    }

    @Override
    public String getContainerName() {
        return NAME;
    }

    @Override
    public void onCompatLoaded(GroovyContainer<?> groovyContainer) {
        groovyContainer.addProperty(FUSOR);
        groovyContainer.addProperty(FORMER);
        LinkGeneratorHooks.registerLinkGenerator(new LinkGenerator());
    }
}
