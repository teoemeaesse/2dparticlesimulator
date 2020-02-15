package utils;

import gfx.Color;

import org.lwjgl.BufferUtils;
import java.nio.DoubleBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by tomas on 11/04/2018.
 */
public class RenderUtils {
    public static void renderRectangle(Color color, float x, float y, int width, int height){
        glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        glBegin(GL_QUADS);
            glVertex2f(x, y);
            glVertex2f(x + width, y);
            glVertex2f(x + width, y + height);
            glVertex2f(x, y + height);
        glEnd();
        glColor4f(1, 1, 1, 1);
        glLoadIdentity();
    }
}
