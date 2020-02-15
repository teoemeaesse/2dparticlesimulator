package concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by tomas on 5/4/2019.
 */
public class ThreadPool {
    private ScheduledExecutorService executorService;
    private Thread[] threads;

    public ThreadPool(Thread ... threads){
        this.threads = threads;
        executorService = Executors.newScheduledThreadPool(threads.length);
    }

    public void startAll(){
        for(Thread t : threads)
            executorService.scheduleAtFixedRate(t, 0, t.getDelta(), TimeUnit.MILLISECONDS);
    }

    public void resume(){
        for(Thread t : threads)
            ((UpdateThread) t).nextFrame();
    }

    public void close(){
        executorService.shutdown();
    }

    public boolean isClosed(){
        return executorService.isTerminated();
    }
}
