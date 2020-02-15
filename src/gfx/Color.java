package gfx;

import java.io.Serializable;

/**
 * Created by tomas on 12/04/2018.
 */
public class Color implements Serializable {
    private float r, g, b, a;

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public static final Color
            LIGHT_BLUE = new Color(175, 210, 245, 255),
            RED = new Color(255, 100, 150, 255),
            GREEN = new Color(100, 255, 150, 255),
            WHITE = new Color(255, 255, 255, 255),
            GRAY = new Color(110, 120, 150, 255),
            DARK_GRAY = new Color(40, 42, 45, 255),
            BLACK = new Color(0, 0, 0, 255)
    ;

    public Color darker(float intensity){ // 0 - 1, 0 returns the same color, 1 returns black
        return new Color(r - r * intensity, g - g * intensity, b - b * intensity, a);
    }

    public Color bluer(float intensity){
        return new Color(r, g, b + (255 - b) * intensity, a);
    }

    public Color setA(float a){
        return new Color(r, g, b, a);
    }

    public float getRed() {
        return r / 255f;
    }
    public float getGreen() {
        return g / 255f;
    }
    public float getBlue() {
        return b / 255f;
    }
    public float getAlpha() {
        return a / 255f;
    }
    public float[] getRGB(){
        return new float[]{getRed(), getGreen(), getBlue()};
    }

    public float getRed255(){
        return r;
    }
    public float getGreen255(){
        return g;
    }
    public float getBlue255(){
        return b;
    }
    public float getAlpha255(){
        return a;
    }
}
