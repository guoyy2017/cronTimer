package com.gyy.cron.defaults;

import com.gyy.cron.JobType;
import com.gyy.cron.api.ITrigger;

public class CronTriggle implements ITrigger {
    public JobType getType() {
        return JobType.CRON;
    }

    public String getCron() {
        return null;
    }

    public boolean cycle() {
        return true;
    }

    public boolean single() {
        return false;
    }

    public long nextTime() {
        return 0;
    }
}
