package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: wt
 * @Date: 2019/1/31 9:17
 */
public class HelloWorldQuartzJob implements Job {
    /**
     * 在实现Job接口中的execute方法编写所需要定时执行的任务
     * @在param var1
     * @throws JobExecutionException
     */
    @Override
    public  void execute(JobExecutionContext var1) throws JobExecutionException {
        System.out.println("Hello World!");
    }
}
