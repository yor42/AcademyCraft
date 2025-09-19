package cn.academy.support.groovyscript;

import cn.academy.AcademyCraft;
import com.cleanroommc.groovyscript.documentation.linkgenerator.BasicLinkGenerator;
import com.cleanroommc.groovyscript.documentation.linkgenerator.ILinkGenerator;

public class LinkGenerator extends BasicLinkGenerator implements ILinkGenerator {

    @Override
    public String id() {
        return "academy";
    }

    @Override
    protected String version() {
        return "v"+ AcademyCraft.VERSION;
    }

    @Override
    protected String domain() {
        return "https://github.com/yor42/AcademyCraft/";
    }
}
