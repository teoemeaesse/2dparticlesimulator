package gfx;

import java.io.Serializable;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;

/**
 * Created by tomas on 15/07/2018.
 */
public class Camera implements Serializable {
    private boolean wDown, aDown, sDown, dDown, shiftDown;
    private Vector offset;
    private float speed;

    public Camera(float speed) {
        this.speed = speed;
        offset = new Vector(0, 0);
    }

    public void update(){
        float multiplier = shiftDown ? 2.0f : 1.0f;

        if(wDown)
            translate(new Vector(0, -speed * multiplier));
        if(aDown)
            translate(new Vector(speed * multiplier, 0));
        if(sDown)
            translate(new Vector(0, speed * multiplier));
        if(dDown)
            translate(new Vector(-speed * multiplier, 0));
    }

    public void handleKeyInput(int key, int action) {
        if(action == GLFW_PRESS){
            if(key == GLFW_KEY_W)
                wDown = true;
            if(key == GLFW_KEY_A)
                aDown = true;
            if(key == GLFW_KEY_S)
                sDown = true;
            if(key == GLFW_KEY_D)
                dDown = true;
            if(key == GLFW_KEY_LEFT_SHIFT)
                shiftDown = true;
        }
        if(action == GLFW_RELEASE){
            if(key == GLFW_KEY_W)
                wDown = false;
            if(key == GLFW_KEY_A)
                aDown = false;
            if(key == GLFW_KEY_S)
                sDown = false;
            if(key == GLFW_KEY_D)
                dDown = false;
            if(key == GLFW_KEY_LEFT_SHIFT)
                shiftDown = false;
        }
    }

    public void translate(Vector vec){
        offset = offset.plus(vec);
    }

    public Vector getOffset() {
        return offset;
    }
}
