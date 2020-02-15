package concurrency;

/**
 * Created by tomas on 5/4/2019.
 */
public abstract class Thread implements Runnable {
    private long delta;

    public Thread(long delta){
        this.delta = delta;
    }

    public long getDelta() {
        return delta;
    }
}
