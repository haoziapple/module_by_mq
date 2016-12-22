package com.fzrj.schedule.service.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @className:com.fzrj.schedule.service.job.TestJob
 * @description:测试用定时任务类
 * @version:v1.0.0 
 * @date:2016年12月22日 上午9:56:49
 * @author:WangHao
 */
public class TestJob implements Job
{
	private static Logger logger = LogManager.getLogger(TestJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		logger.info("开始执行定时任务");
		System.out.println(context.getTrigger().getKey());
		System.out.println(context.getJobDetail().getKey());
		logger.info("结束执行定时任务");
	}
}
