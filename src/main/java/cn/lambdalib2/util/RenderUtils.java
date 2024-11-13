package cn.lambdalib2.util;

import java.lang.reflect.Field;

import cn.lambdalib2.render.legacy.Tessellator;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex3d;

/**
 * Utils for legacy GL rendering.
 * Note that you should use modern GL as much as possible and avoid this.
 * @author WeAthFolD
 */
@Deprecated
public class RenderUtils {
    
//    public static ResourceLocation src_glint = new ResourceLocation("textures/misc/enchanted_item_glint.png");

    private static int textureState = -1;
    
    //-----------------Quick aliases-----------------------------
    
    /**
     * Stores the current texture state. stack depth: 1
     */
    public static void pushTextureState() {
        if(textureState != -1) {
            throw new RuntimeException("RenderUtils:Texture State Overflow");
        }
        textureState = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
    }
    
    /**
     * Restores the stored texture state. stack depth: 1
     */
    public static void popTextureState() {
        if(textureState == -1) {
            throw new RuntimeException("RenderUtils:Texture State Underflow");
        }
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureState);
        textureState = -1;
    }

    public static void glVertexAndUV(Vec3d vertex, double u, double v) {
        GL11.glTexCoord2d(u, v);
        glVertex3d(vertex.x, vertex.y, vertex.z);
    }

    public static void glVertexAndUV(double x, double y, double z, double u, double v) {
        glTexCoord2d(u, v);
        glVertex3d(x, y, z);
    }

    public static void glTranslate(Vec3d v) {
        GL11.glTranslated(v.x, v.y, v.z);
    }

    public static void loadTexture(ResourceLocation src) {
        Minecraft.getMinecraft().renderEngine.bindTexture(src);
    }

    public static void drawEquippedItem(double width, ResourceLocation front, ResourceLocation back) {
        drawEquippedItem(width, front, back, 0, 0, 1, 1, false);
    }
    
    private static void drawEquippedItem(double w, ResourceLocation front, ResourceLocation back, 
            double u1, double v1, double u2, double v2, boolean faceOnly) {
        Tessellator t = Tessellator.instance;
        Vec3d a1 = new Vec3d(0, 0, w),
            a2 = new Vec3d(1, 0, w),
            a3 = new Vec3d(1, 1, w),
            a4 = new Vec3d(0, 1, w),
            a5 = new Vec3d(0, 0, -w),
            a6 = new Vec3d(1, 0, -w),
            a7 = new Vec3d(1, 1, -w),
            a8 = new Vec3d(0, 1, -w);

        Minecraft mc = Minecraft.getMinecraft();

        GL11.glPushMatrix();

        RenderUtils.loadTexture(back);
        t.startDrawingQuads();
        t.setNormal(0.0F, 0.0F, 1.0F);
        addVertex(a1, u2, v2);
        addVertex(a2, u1, v2);
        addVertex(a3, u1, v1);
        addVertex(a4, u2, v1);
        t.draw();

        RenderUtils.loadTexture(front);
        t.startDrawingQuads();
        t.setNormal(0.0F, 0.0F, -1.0F);
        addVertex(a8, u2, v1);
        addVertex(a7, u1, v1);
        addVertex(a6, u1, v2);
        addVertex(a5, u2, v2);
        t.draw();

        int var7;
        float var8;
        double var9;
        float var10;

        /*
         * Gets the width/16 of the currently bound texture, used to fix the
         * side rendering issues on textures != 16
         */
        int tileSize = 32;
        float tx = 1.0f / (32 * tileSize);
        float tz = 1.0f / tileSize;

        if(!faceOnly) {
            t.startDrawingQuads();
            t.setNormal(-1.0F, 0.0F, 0.0F);
            for (var7 = 0; var7 < tileSize; ++var7) {
                var8 = (float) var7 / tileSize;
                var9 = u2 - (u2 - u1) * var8 - tx;
                var10 = 1.0F * var8;
                t.addVertexWithUV(var10, 0.0D, -w, var9, v2);
                t.addVertexWithUV(var10, 0.0D, w, var9, v2);
                t.addVertexWithUV(var10, 1.0D, w, var9, v1);
                t.addVertexWithUV(var10, 1.0D, -w, var9, v1);

                t.addVertexWithUV(var10, 1.0D, w, var9, v1);
                t.addVertexWithUV(var10, 0.0D, w, var9, v2);
                t.addVertexWithUV(var10, 0.0D, -w, var9, v2);
                t.addVertexWithUV(var10, 1.0D, -w, var9, v1);
            }
            t.draw();
        }

        GL11.glPopMatrix();
    }

    private static void addVertex(Vec3d vec, double u, double v) {
        Tessellator t = Tessellator.instance;
        t.addVertexWithUV(vec.x, vec.y, vec.z, u, v);
    }

    public static void addVertexLegacy(Vec3d vertex, double u, double v) {
        GL11.glTexCoord2d(u, v);
        GL11.glVertex3d(vertex.x, vertex.y, vertex.z);
    }

    //--- SMC Support
    static final String _shadersClassName = "shadersmodcore.client.Shaders";
    static boolean smcSupportInit = false;
    static boolean smcPresent = false;
    static Field fIsShadowPass;

    /**
     * Judge whether the current rendering context is in shadow pass.
     * @return If in vanilla Minecraft: always false; If ShaderMod is installed: as mentioned above.
     */
    public static boolean isInShadowPass() {
        if(!smcSupportInit) {
            initSMCSupport();
        }
        
        if(smcPresent) {
            try {
                return fIsShadowPass.getBoolean(null);
            } catch(Exception e) {
                Debug.error("Exception in isInShadowPass", e);
            }
        }
        return false;
    }
    
    private static void initSMCSupport() {
        try {
            Class shadersClass = Class.forName(_shadersClassName);
            fIsShadowPass = shadersClass.getField("isShadowPass");
            smcPresent = true;
            Debug.log("LambdaLib SMC support successfully initialized.");
        } catch(Exception e) {
            Debug.error("LambdaLib SMC support isn't initialized.");
            smcPresent = false;
        }
        
        smcSupportInit = true;
    }

    public static void drawBlackout() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GLU.gluOrtho2D(1, 0, 1, 0);
        
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        
        GL11.glColor4d(0, 0, 0, 0.7);
        GL11.glTranslated(0, 0, 0);
        HudUtils.colorRect(0, 0, 1, 1);
        
        GL11.glPopMatrix();
        
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor4d(1, 1, 1, 1);
        
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
    
}
