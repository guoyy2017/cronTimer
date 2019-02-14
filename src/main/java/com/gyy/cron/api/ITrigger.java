package com.gyy.cron.api;

import com.gyy.cron.JobType;

/**
 * 触发器
 */
public interface ITrigger {
    /**
     * 执行类型
     * @return
     */
    JobType getType();

    /**
     * 任务表达式
     * @return
     */
    String getCron();

    /**
     * 是否循环执行
     * JobType.CYCLE
     * @return
     */
    boolean cycle();

    /**
     * 是否单次
     * JobType.SINGLE
     * @return
     */
    boolean single();

    /**
     * 下一次执行时间
     * @return -1 失效 0 立即 ***ms 具体时间
     */
    long nextTime();
}
