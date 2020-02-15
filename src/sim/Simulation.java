package sim;

import concurrency.Thread;
import concurrency.ThreadPool;
import concurrency.UpdateThread;
import gfx.Color;
import gfx.Vector;
import main.Main;
import utils.RenderUtils;

import java.util.Scanner;

public class Simulation {
    public final int PLAYBACK_FPS = 60, ITERATIONS = 600, THREAD_COUNT = 69; // Optimal PPT = 100
    public final float G = 0.0005f;

    public int threadsFinished = 0;

    private String title;
    private Playback playback;
    private ThreadPool threadPool;

    public Simulation(String title) {
        this.title = title;
    }

    public Simulation(String title, Playback playback) {
        this.title = title;
        this.playback = playback;
    }

    public synchronized void threadFinished(){
        threadsFinished++;
    }

    public void startSimulation(){
        int ispacing = 2, xo = 370, yo = 260, width = 100, height = 100, count = 0;
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                if(Math.sqrt(Math.pow(xo + x * ispacing - (xo + width * ispacing / 2f), 2) + Math.pow(yo + y * ispacing - (yo + height * ispacing / 2f), 2)) < width * ispacing / 2f && Math.sqrt(Math.pow(xo + x * ispacing - (xo + width * ispacing / 2f), 2) + Math.pow(yo + y * ispacing - (yo + height * ispacing / 2f), 2)) > width * ispacing / 6f)
                    count++;
        System.out.println("Simulating " + count + " particles");
        playback = new Playback(ITERATIONS, count);
        Particle[] frame = new Particle[count];
        int current = 0;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if(Math.sqrt(Math.pow(xo + x * ispacing - (xo + width * ispacing / 2f), 2) + Math.pow(yo + y * ispacing - (yo + height * ispacing / 2f), 2)) < width * ispacing / 2f && Math.sqrt(Math.pow(xo + x * ispacing - (xo + width * ispacing / 2f), 2) + Math.pow(yo + y * ispacing - (yo + height * ispacing / 2f), 2)) > width * ispacing / 6f){
                    frame[current] = new Particle(
                            new Vector(xo + x * ispacing, yo + y * ispacing),
                            new Vector((yo + y * ispacing - (yo + height * ispacing / 2f)) * 0.015f, -(xo + x * ispacing - (xo + width * ispacing / 2f)) * 0.015f),
                            1
                    );
                    current++;
                }
            }
        }
        playback.firstFrame(frame);

        final int PPT = count / THREAD_COUNT,
            RPPT = count % THREAD_COUNT;
        Thread[] threads = new Thread[THREAD_COUNT];
        if(RPPT == 0)
            for(int i = 0; i < THREAD_COUNT; i++)
                threads[i] = new UpdateThread(i * PPT, PPT);
        else{
            int last = 0;
            for(int i = 0; i < THREAD_COUNT; i++){
                if(i < RPPT){
                    threads[i] = new UpdateThread(last, PPT + 1);
                    last += PPT + 1;
                }
                else{
                    threads[i] = new UpdateThread(last, PPT);
                    last += PPT;
                }
            }
        }

        threadPool = new ThreadPool(threads);
        threadPool.startAll();
        threadPool.resume();

        long start = System.currentTimeMillis();

        while(playback.getKeyFrame() < ITERATIONS){
            try{
                java.lang.Thread.sleep(1);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            if(threadsFinished >= threads.length){
                playback.nextFrame();
                System.out.println("Frame: " + playback.getKeyFrame());
                threadsFinished = 0;
                threadPool.resume();
            }
        }

        threadPool.close();

        Scanner reader = new Scanner(System.in);
        System.out.println("Simulation finished in " + ((int) ((System.currentTimeMillis() - start) / 100)) / 10d + "s. Saving... ");
    }

    public void render(){
        Main.getWindow().getCamera().update();

        if(playback.getKeyFrame() >= playback.getIterations())
            playback.setKeyFrame(0);

        RenderUtils.renderRectangle(Color.BLACK, 0, 0, Main.getWindow().getWidth(), Main.getWindow().getHeight());
        playback.renderFrame();

        playback.setKeyFrame(playback.getKeyFrame() + 1);
    }

    public Playback getPlayback() {
        return playback;
    }

    public String getTitle() {
        return title;
    }

    public ThreadPool getThreadPool() {
        return threadPool;
    }
}
