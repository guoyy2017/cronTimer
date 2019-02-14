package com.gyy.cron.defaults;

import com.gyy.cron.JobException;
import com.gyy.cron.JobState;
import com.gyy.cron.api.IJob;
import com.gyy.cron.api.ITrigger;
import org.apache.log4j.Logger;

import java.util.Map;

public abstract class AbstractJob implements IJob {
    protected Logger logger = Logger.getLogger(this.getClass());

    public AbstractJob(String name, String desc, boolean multi){
        this.name = name;
        this.desc = desc;
        this.multi = multi;
    }

    private String name;
    private String desc;
    private ITrigger trigger;
    private boolean multi;
    private JobState state;

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }

    public ITrigger getTrigger() {
        return this.trigger;
    }

    public void setTrigger(ITrigger trigger) {
        this.trigger = trigger;
    }

    public JobState getState() {
        return this.state;
    }

    public void setState(JobState state) {
        this.state = state;
    }

    public void updateRunTime(long time) {

    }

    public boolean multi() {
        return this.multi;
    }

    public long nextTime() {
        if(state.equals(JobState.STATE_U)){
            return trigger.nextTime();
        }
        return -1;
    }
}
