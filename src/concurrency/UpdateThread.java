package concurrency;

import gfx.Vector;
import main.Main;
import sim.Particle;

/**
 * Created by tomas on 5/5/2019.
 */
public class UpdateThread extends Thread {
    private int startIndex, ppt;

    public UpdateThread(int startIndex, int ppt){
        super(1);
        this.startIndex = startIndex;
        this.ppt = ppt;
        i = startIndex;
    }

    private boolean finished = true;

    private int i, totalMass;
    public static Vector cm;

    @Override
    public void run() {
        if(!finished){
            findCM();

            if(i < startIndex + ppt){
                Particle p = Main.getSimulation().getPlayback().getFrame()[i];

                method1(p);

                i++;
            }
            else{
                finished = true;
                i = startIndex;
                Main.getSimulation().threadFinished();
            }
        }
    }

    private void method1(Particle p){ // Much faster, less accurate, differences in particle mass have diminished effect
        Vector r = cm.minus(p.getPos());
        p.setVel(p.getVel().plus(r.times(Main.getSimulation().G * totalMass / (float) Math.pow(r.len(), 2))));
    }

    private void method2(Particle p){ // Slower, individually calculates the gravitational forces for each particle, mass variations are noticeable
        Vector acc = new Vector(0, 0);
        for(Particle pp : Main.getSimulation().getPlayback().getFrame()){
            Vector r = p.getPos().minus(pp.getPos());
            if(r.len() != 0)
                acc = acc.plus(r.times(Main.getSimulation().G * pp.getMass() / (float) Math.pow(r.len(), 2)));
        }
        p.setVel(p.getVel().plus(acc));
    }

    private void findCM(){
        totalMass = 0;
        Vector sum = new Vector(0, 0);
        for(Particle p : Main.getSimulation().getPlayback().getFrame()){
            sum = sum.plus(p.getPos().times(p.getMass()));
            totalMass += p.getMass();
        }
        cm = sum.times(1f / totalMass);
    }

    public void nextFrame(){
        finished = false;
    }
}
