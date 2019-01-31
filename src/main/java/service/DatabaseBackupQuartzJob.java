package service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: wt
 * @Date: 2019/1/31 15:42
 */
public class DatabaseBackupQuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext var1) throws JobExecutionException{
        DataBaseBackupService dataBaseBackupService = new DataBaseBackupService();
        dataBaseBackupService.backup();
    }
}
