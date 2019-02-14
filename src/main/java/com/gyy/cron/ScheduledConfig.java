package com.gyy.cron;

import java.util.concurrent.*;

public class ScheduledConfig {
    private int min;
    private int max;

    public ScheduledConfig setMin(int min){
        this.min = min;
        return this;
    }

    public ScheduledConfig setMax(int max){
        this.max = max;
        return this;
    }

    public ExecutorService getExecutors(){
        if(min < 1 || max < 1){
            return Executors.newSingleThreadExecutor();
        }
        if(min == max) {
            return Executors.newFixedThreadPool(min);
        }
        if(min < max){
            return new ThreadPoolExecutor(min, max, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        }else{
            return new ThreadPoolExecutor(max, min, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        }
    }
}
