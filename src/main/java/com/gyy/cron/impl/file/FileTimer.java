package com.gyy.cron.impl.file;

import com.gyy.cron.*;
import com.gyy.cron.api.IJob;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class FileTimer extends Timer {

    private ExecutorService service;
    private String taskFile;
    private String logDir;
    private java.util.Timer timer = new java.util.Timer();
    private List<IJob> tasks;
    private boolean hasChange;//修改
    private long lastUpdated = System.currentTimeMillis();
    private long updateTime = 5000;//5s有变更更新一次

    private synchronized boolean setChange(boolean change){
        if(hasChange != change) {
            hasChange = change;
            return true;
        }else {
            return false;
        }
    }

    public void start() throws JobException {
        List<IJob> tasks = FileUtil.readFile(taskFile);
        if(tasks == null){
            throw new JobException("read task file error,please check");
        }
        this.tasks = tasks;
        service = config.getExecutors();
        //1000ms/次
        timer.schedule(new FileTask(), 0 , 1000);
        new Thread(
                new Runnable() {
                    public void run() {
                        init = true;
                        while (init){
                            try{
                                IJob job = jobs.poll(3, TimeUnit.SECONDS);
                                if(job != null){
                                    runJob(job);
                                }
                            }catch (Exception ex){
                                logger.error("jobs get error, continue", ex);
                            }
                        }
                        over.countDown();
                    }
                }
        ).start();
    }

    /**
     * 执行任务
     * @param job
     */
    private void runJob(final IJob job){
        service.submit(new Runnable() {
            public void run() {
                long begin = System.currentTimeMillis();
                TaskLog log = new TaskLog();
                log.setJob(job.getClass().getName());
                log.setName(job.getName());
                log.setBegin(begin);
                long taskId = 0;
                log.setTaskId(taskId);
                try{
                    log.setParams(null);
                    job.run(taskId, null);
                    log.setState(JobState.STATE_S);
                }catch (Exception ex){
                    logger.error("run job "+log.getJob()+"error", ex);
                    log.setState(JobState.STATE_X);
                }
                long end = System.currentTimeMillis();
                log.setBegin(end);
                log.setCost(end - begin);
                FileUtil.writeLog(logDir, log);
            }
        });
    }

    public void stop() throws JobException {
        if(init){
            init = false;
            try{
                /**
                 * 等3秒
                 */
                over.await(3, TimeUnit.SECONDS);
            }catch (Exception ex){
                logger.error("wait task main thread stop error", ex);
            }
        }
        if(service != null){
            service.shutdown();
        }
        timer.cancel();
        /**
         * 保存操作
         */
        if(tasks.size() > 0){
            FileUtil.writeFile(taskFile, tasks);
        }
    }

    public void addTask(IJob job) throws JobException {

    }

    public void removeTask(String name) throws JobException {

    }

    public void initFile(ScheduledConfig config, String taskFile, String logDir) throws JobException{
        this.config = config;
        this.taskFile = taskFile;
        this.logDir = logDir;
    }

    class FileTask extends TimerTask{

        public void run() {
            for(IJob job:tasks){
                //任务可执行
                if(job.getState().equals(JobState.STATE_U)){
                    long nextTime = job.nextTime();
                    if(nextTime == -1){
                        job.setState(JobState.STATE_S);
                        setChange(true);
                    }else if(nextTime == 0){
                        job.setState(JobState.STATE_S);
                        setChange(true);
                        try {
                            jobs.put(job);
                        }catch (Exception ex){

                        }
                    }else{
                        //本次可执行
                        if(nextTime <= System.currentTimeMillis()){
                            if(!job.multi()){//不可多实例
                                if(job.getTrigger().single()){
                                    job.setState(JobState.STATE_S);
                                }else {
                                    job.setState(JobState.STATE_R);
                                }
                            }else{
                                job.setState(JobState.STATE_U);
                            }
                            setChange(true);
                            try {
                                jobs.put(job);
                            }catch (Exception ex){

                            }
                        }
                    }
                }
            }
            if(System.currentTimeMillis() - lastUpdated >= updateTime){
                if(setChange(false)){
                    //更新文件
                    FileUtil.writeFile(taskFile, tasks);
                }
                lastUpdated = System.currentTimeMillis();
            }
        }
    }
}
