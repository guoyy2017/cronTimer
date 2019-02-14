package com.gyy.cron.impl.mq;

import com.gyy.cron.JobException;
import com.gyy.cron.ScheduledConfig;
import com.gyy.cron.Timer;
import com.gyy.cron.api.IJob;

import java.util.concurrent.ExecutorService;

public class MQTimer extends Timer {

    private ExecutorService service;

    public void start() throws JobException {

    }

    public void stop() throws JobException {

    }

    public void addTask(IJob job) throws JobException {

    }

    public void removeTask(String name) throws JobException {

    }


    public void initMQ(ScheduledConfig config) throws JobException{
        this.config = config;
    }
}
