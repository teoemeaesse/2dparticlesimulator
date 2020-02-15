package gfx;

import java.io.Serializable;

/**
 * Created by tomas on 15/07/2018.
 */
public class Vector implements Serializable {
    private float xc, yc;

    public Vector(float xc, float yc) {
        this.xc = xc;
        this.yc = yc;
    }

    public Vector(Vector v){
        this.xc = v.getXc();
        this.yc = v.getYc();
    }

    public Vector plus(Vector vec){
        return new Vector(xc + vec.xc, yc + vec.yc);
    }

    public Vector minus(Vector vec){
        return new Vector(getXc() - vec.getXc(), getYc() - vec.getYc());
    }

    public Vector times(float coef){
        return new Vector(xc * coef, yc * coef);
    }

    public float len(){
        return (float) Math.sqrt(xc * xc + yc * yc);
    }

    public float angle(){
        return (float) Math.toDegrees(Math.atan2(yc, xc));
    }

    public float getXc() {
        return xc;
    }

    public float getYc() {
        return yc;
    }
}
