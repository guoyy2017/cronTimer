package com.gyy.cron.api;

import com.gyy.cron.JobException;
import com.gyy.cron.JobState;

import java.util.Map;

/**
 * 任务
 */
public interface IJob {
    /**
     * 获取任务名称
     * @return
     */
    String getName();

    /**
     * 获取任务描述
     * @return
     */
    String getDesc();

    /**
     * 获取触发器
     * @return
     */
    ITrigger getTrigger();

    /**
     * 设置触发器
     * @param trigger
     */
    void setTrigger(ITrigger trigger);

    /**
     * 获取任务状态
     * @return
     */
    JobState getState();

    /**
     * 修改状态
     * @param state
     */
    void setState(JobState state);

    /**
     * 更新上次执行时间(调度触发时间，非执行时间)
     * @param time
     */
    void updateRunTime(long time);

    /**
     * 是否多实例存在
     * @return
     */
    boolean multi();

    /**
     * 下一次执行时间
     * @return -1 失效 0 立即 ****ms 指定时间
     */
    long nextTime();

    /**
     * 执行任务
     * @param taskId 任务编号
     * @param params 任务参数
     * @return true 成功 false 失败
     * @throws JobException 执行异常
     */
    boolean run(long taskId, Map<String, Object> params) throws JobException;
}
