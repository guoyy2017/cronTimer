package com.gyy.cron;

public enum JobType {
    SINGLE("single"),FIX_TIME("fix_time"),CYCLE("cycle"),CRON("cron");
    private String value;
    JobType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
