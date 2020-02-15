package sim;

import gfx.Vector;

public class Playback {
    private Particle[][] playback;
    private int keyFrame = 0;

    public Playback(int iterations, int particleCount){
        playback = new Particle[iterations][particleCount];
    }

    public Playback(float[][][] save){
        final int X = 0, Y = 1, XV = 2, YV = 3, M = 4;
        playback = new Particle[save.length][save[0].length];
        for(int k = 0; k < save.length; k++)
            for(int p = 0; p < save[0].length; p++)
                playback[k][p] = new Particle(new Vector(save[k][p][X], save[k][p][Y]), new Vector(save[k][p][XV], save[k][p][YV]), (int) save[k][p][M]);
    }

    public void firstFrame(Particle[] frame){
        playback[0] = frame;
    }

    public void updateFrame(){
        for(Particle p : playback[keyFrame]){
            p.update();
        }
    }
    public void renderFrame(){
        for(Particle p : playback[keyFrame])
            p.render();
    }

    private static Particle[] deepCopy(Particle[] original){
        Particle[] copy = new Particle[original.length];
        for(int i = 0; i < copy.length; i++)
            copy[i] = new Particle(original[i].getPos(), original[i].getVel(), original[i].getMass());
        return copy;
    }

    public float[][][] getPlayback(){
        float[][][] playback = new float[this.playback.length][this.playback[0].length][5];
        for(int k = 0; k < this.playback.length; k++)
            for(int p = 0; p < this.playback[0].length; p++)
                playback[k][p] = this.playback[k][p].getArray();
        return playback;
    }

    public void nextFrame(){
        updateFrame();
        keyFrame++;
        if(keyFrame < playback.length)
            playback[keyFrame] = deepCopy(playback[keyFrame - 1]);
    }

    public Particle[] getFrame(){
        return playback[keyFrame];
    }

    public int getKeyFrame() {
        return keyFrame;
    }

    public void setKeyFrame(int keyFrame) {
        this.keyFrame = keyFrame;
    }

    public int getIterations(){
        return playback.length;
    }
}
