package sim;

import gfx.Color;
import gfx.Vector;
import main.Main;
import utils.RenderUtils;

public class Particle {
    private Vector pos, vel;
    private int mass;

    public Particle(Vector pos, Vector vel, int mass) {
        this.pos = pos;
        this.vel = vel;
        this.mass = mass;
    }

    public void update(){
        pos = pos.plus(vel);
    }

    public void render(){
        RenderUtils.renderRectangle(
                new Color(255, 20, 10, 30 + getVel().len()),
                pos.getXc() + Main.getWindow().getCamera().getOffset().getXc(),
                pos.getYc() + Main.getWindow().getCamera().getOffset().getYc(),
                1, 1);
    }

    public float[] getArray(){
        return new float[]{pos.getXc(), pos.getYc(), vel.getXc(), vel.getYc(), mass};
    }

    public Vector getPos() {
        return pos;
    }

    public void setVel(Vector vel) {
        this.vel = vel;
    }

    public Vector getVel() {
        return vel;
    }

    public int getMass() {
        return mass;
    }
}
