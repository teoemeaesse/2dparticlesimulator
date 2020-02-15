package concurrency;

import main.Main;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by tomas on 5/5/2019.
 */
public class RenderThread extends Thread {
    public RenderThread(){
        super((long) (1000f / Main.getSimulation().PLAYBACK_FPS));
    }

    @Override
    public void run() {
        glfwMakeContextCurrent(Main.getWindow().getHandle());
        GL.createCapabilities();
        glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        Main.getSimulation().render();

        glfwSwapBuffers(Main.getWindow().getHandle());
        glfwMakeContextCurrent(MemoryUtil.NULL);
    }
}
