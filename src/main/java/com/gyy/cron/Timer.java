package com.gyy.cron;

import com.gyy.cron.api.IJob;
import com.gyy.cron.impl.db.DBTimer;
import com.gyy.cron.impl.file.FileTimer;
import com.gyy.cron.impl.mq.MQTimer;
import com.gyy.cron.impl.redis.RedisTimer;
import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Timer {
    protected Logger logger = Logger.getLogger(this.getClass());

    /**
     * 获取文件任务服务
     * @return
     */
    public static Timer getFileTimer(){
        return new FileTimer();
    }

    /**
     * 获取数据库任务服务
     * @return
     */
    public static Timer getDBTimer(){
        return new DBTimer();
    }

    /**
     * 获取redis任务服务
     * @return
     */
    public static Timer getRedisTimer(){
        return new RedisTimer();
    }

    /**
     * 获取MQ任务服务
     * @return
     */
    public static Timer getMQTimer(){
        return new MQTimer();
    }

    /**
     * 文件初始化
     * @param config 执行配置
     * @param taskFile 任务文件
     * @param logDir 日志目录 日志按照天生成
     * @throws JobException
     */
    public void initFile(ScheduledConfig config, String taskFile, String logDir) throws JobException{
        throw new JobException("暂不支持");
    }

    /**
     * DB初始化
     * @param config 执行配置
     * @throws JobException
     */
    public void initDB(ScheduledConfig config) throws JobException{
        throw new JobException("暂不支持");
    }

    /**
     * redis初始化
     * @param config 执行配置
     * @throws JobException
     */
    public void initRedis(ScheduledConfig config) throws JobException{
        throw new JobException("暂不支持");
    }

    /**
     * MQ初始化
     * @param config 执行配置
     * @throws JobException
     */
    public void initMQ(ScheduledConfig config) throws JobException{
        throw new JobException("暂不支持");
    }

    /**
     * 执行任务配置
     */
    protected ScheduledConfig config;

    /**
     * 可执行任务列表
     */
    protected LinkedBlockingQueue<IJob> jobs = new LinkedBlockingQueue<IJob>();

    /**
     * 是否初始化
     */
    protected boolean init;

    /**
     * 结束锁
     */
    protected CountDownLatch over = new CountDownLatch(1);

    /**
     * 启动
     * @throws JobException
     */
    public abstract void start() throws JobException;

    /**
     * 暂停
     * @throws JobException
     */
    public abstract void stop() throws JobException;

    /**
     * 增加任务
     * @param job 任务
     * @throws JobException
     */
    public abstract void addTask(IJob job) throws JobException;

    /**
     * 移除任务
     * @param name 任务名称
     * @throws JobException
     */
    public abstract void removeTask(String name) throws JobException;
}
