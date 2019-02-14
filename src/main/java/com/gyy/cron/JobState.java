package com.gyy.cron;

public enum JobState {
    /**
     * U 可执行
     * R 执行中 不可多实例时用
     * S 执行完成
     * X 执行异常
     * G 挂起状态
     */
    STATE_U("U"), STATE_R("R"),STATE_S("S"), STATE_G("G"), STATE_X("X");
    private String value;
    JobState(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
