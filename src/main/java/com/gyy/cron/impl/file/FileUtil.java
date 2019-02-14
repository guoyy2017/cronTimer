package com.gyy.cron.impl.file;

import com.alibaba.fastjson.JSON;
import com.gyy.cron.TaskLog;
import com.gyy.cron.api.IJob;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {
    private static final Logger logger = Logger.getLogger(FileUtil.class);
    static final String CODEING = "UTF-8";

    /**
     * 读取初始化任务列表
     * @param taskFile
     * @return
     */
    public static List<IJob> readFile(String taskFile){
        //初始化用
        try{
            File file = new File(taskFile);
            if(file.exists()) {
                List<String> tasks = FileUtils.readLines(file, CODEING);
                List<IJob> jobs = new LinkedList<IJob>();
                for(String task:tasks){
                    jobs.add(parseString(task));
                }
                return jobs;
            }else{
                return new LinkedList<IJob>();
            }
        }catch (Exception ex){
            logger.error("read task error", ex);
        }
        return null;
    }

    /**
     * 读取临时文件新增任务
     * @param taskFile 任务文件+.add
     * @return
     */
    public static List<IJob> readAddFile(String taskFile){
        //先判断文件是否存在，存在就rename成.tmp，读取内容
        try{
            File addFile = new File(taskFile+".add");
            if(addFile.exists()){
                String tmpAddFile = taskFile+".add.tmp";
                File tmpFile = new File(tmpAddFile);
                FileUtils.moveFile(addFile, tmpFile);
                return readFile(tmpAddFile);
            }
        }catch (Exception ex){
            logger.error("read add task error", ex);
        }
        return null;
    }

    /**
     * 更新文件
     * @param taskFile
     */
    public static void writeFile(String taskFile, List<IJob> jobs){
        //更新文件内容
        try {
            List<String> tasks = new LinkedList<String>();
            for(IJob job:jobs){
                tasks.add(parseJob(job));
            }
            String tmpFile = taskFile+".tmp";
            FileUtils.deleteQuietly(new File(tmpFile));
            FileUtils.moveFile(new File(taskFile), new File(tmpFile));
            FileUtils.writeLines(new File(taskFile), tasks, CODEING);
        }catch (Exception ex){
            logger.error("flush task file error", ex);
        }
    }

    /**
     * 追加日志内容
     */
    public static synchronized void writeLog(String logFile, TaskLog log){
        //写执行日志
        String logInfo = JSON.toJSONString(log);
        try {
            List<String> logs = new LinkedList<String>();
            logs.add(logInfo);
            FileUtils.writeLines(new File(logFile), logs, CODEING, true);
        }catch (IOException ex){
            logger.error(String.format("log not in file=%s info=%s", logFile, logInfo));
            logger.error("writeLog error", ex);
        }
    }

    /**
     * 字符串变任务
     * @param task
     * @return
     */
    public static IJob parseString(String task){
        return null;
    }

    /**
     * 任务变字符串
     * @param job
     * @return
     */
    public static String parseJob(IJob job){
        return null;
    }
}
