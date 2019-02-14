package com.gyy.cron.impl.db;

import com.gyy.cron.JobException;
import com.gyy.cron.ScheduledConfig;
import com.gyy.cron.Timer;
import com.gyy.cron.api.IJob;

import java.util.concurrent.ExecutorService;

public class DBTimer extends Timer {

    private ExecutorService service;

    public void start() throws JobException {

    }

    public void stop() throws JobException {

    }

    public void addTask(IJob job) throws JobException {

    }

    public void removeTask(String name) throws JobException {

    }

    public void initDB(ScheduledConfig config) throws JobException{
        this.config = config;
    }
}
