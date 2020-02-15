package gfx;

import concurrency.RenderThread;
import concurrency.ThreadPool;
import concurrency.UpdateThread;
import main.Main;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by tomas on 11/04/2018.
 */
public class Window {
    private int width, height;
    private int handle;
    private String title;

    private Camera camera;
    private ThreadPool pool;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        if(!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        handle = ((int) glfwCreateWindow(width, height, title, NULL, NULL));
        if(handle == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(handle, (window, key, scancode, action, mods) -> camera.handleKeyInput(key, action));

        try(MemoryStack stack = stackPush()){
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(handle, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                    handle,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }
        glfwMakeContextCurrent(handle);
        GL.createCapabilities();

        glfwSwapInterval(1);

        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, Main.getWindow().getWidth(), 0.0, Main.getWindow().getHeight(), -1.0, 1.0);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glViewport(0, 0, Main.getWindow().getWidth(), Main.getWindow().getHeight());
        glClearColor(0f, 0f, 0f, 1);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glfwShowWindow(handle);

        glfwMakeContextCurrent(NULL);

        camera = new Camera(3f);
        pool = new ThreadPool(new RenderThread());
    }

    public void start(){
        pool.startAll();

        while(!glfwWindowShouldClose(handle)){
            glfwWaitEvents();
        }

        pool.close();

        try{
            while(!pool.isClosed())
                Thread.sleep(50);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public int getHandle() {
        return handle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Camera getCamera() {
        return camera;
    }
}
