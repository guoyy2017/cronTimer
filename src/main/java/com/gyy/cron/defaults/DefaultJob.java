package com.gyy.cron.defaults;

import com.gyy.cron.JobException;

import java.util.Map;

public class DefaultJob extends AbstractJob {

    public DefaultJob(String name, String desc, boolean multi) {
        super(name, desc, multi);
    }

    public boolean run(long taskId, Map<String, Object> params) throws JobException {
        logger.info(String.format("run task=%s taskId=%l", getName(), taskId));
        return false;
    }
}
