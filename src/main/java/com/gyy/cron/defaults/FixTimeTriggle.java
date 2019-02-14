package com.gyy.cron.defaults;

import com.gyy.cron.JobType;
import com.gyy.cron.api.ITrigger;

public class FixTimeTriggle implements ITrigger {
    public JobType getType() {
        return JobType.FIX_TIME;
    }

    public String getCron() {
        return null;
    }

    public boolean cycle() {
        return false;
    }

    public boolean single() {
        return true;
    }

    public long nextTime() {
        return 0;
    }
}
