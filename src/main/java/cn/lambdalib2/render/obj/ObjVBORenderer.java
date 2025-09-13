package cn.lambdalib2.render.obj;

import cn.lambdalib2.render.obj.ObjModel.Face;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class ObjVBORenderer {

    private final ObjModel model;
    private final Map<String, int[]> partInfo = new HashMap<>(); // partName -> {vertex_offset, vertex_count}
    private int vboID = -1;
    private boolean initialized = false;

    public ObjVBORenderer(ObjModel model) {
        this.model = model;
    }

    private void initialize() {
        if (initialized) return;

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, model.vertexBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // Pre-calculate rendering information for each part.
        // The vertex data is now ordered by faces, then by groups.
        int offset = 0;
        for (String group : model.faces.keySet()) {
            int count = model.faces.get(group).size() * 3; // 3 vertices per face
            partInfo.put(group, new int[]{offset, count});
            offset += count;
        }

        initialized = true;
    }

    public void renderAll() {
        if (!initialized) {
            initialize();
        }

        if (vboID == -1 || model.vertices.isEmpty()) return;

        glBindBuffer(GL_ARRAY_BUFFER, vboID);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        final int stride = 8 * 4; // (pos(3) + normal(3) + uv(2)) * sizeof(float)
        glVertexPointer(3, GL_FLOAT, stride, 0);
        glNormalPointer(GL_FLOAT, stride, 3 * 4);
        glTexCoordPointer(2, GL_FLOAT, stride, 6 * 4);

        glDrawArrays(GL_TRIANGLES, 0, model.vertices.size());

        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        glDisableClientState(GL_NORMAL_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void renderPart(String part) {
        if (!initialized) {
            initialize();
        }

        int[] info = partInfo.get(part);
        if (info == null || vboID == -1) {
            return;
        }

        glBindBuffer(GL_ARRAY_BUFFER, vboID);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        final int stride = 8 * 4;
        glVertexPointer(3, GL_FLOAT, stride, 0);
        glNormalPointer(GL_FLOAT, stride, 3 * 4);
        glTexCoordPointer(2, GL_FLOAT, stride, 6 * 4);

        glDrawArrays(GL_TRIANGLES, info[0], info[1]);

        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        glDisableClientState(GL_NORMAL_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void dispose() {
        if (vboID != -1) {
            glDeleteBuffers(vboID);
            vboID = -1;
        }
        initialized = false;
    }
}