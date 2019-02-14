package com.gyy.cron;

public class JobException extends Exception {
    public JobException(String msg){
        super(msg);
    }

    public JobException(String msg, Exception ex){
        super(msg, ex);
    }

    public JobException(Exception ex){
        super(ex);
    }
}
