package com.fzrj.schedule.service.schedule.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzrj.schedule.bean.http.CronHttpReq;
import com.fzrj.schedule.bean.http.HttpReqBean;
import com.fzrj.schedule.bean.http.SimpleHttpReq;
import com.fzrj.schedule.bean.job.JobBean;
import com.fzrj.schedule.service.job.HttpJob;
import com.fzrj.schedule.service.schedule.ScheduleService;

/**
 * @className:com.fzrj.schedule.service.schedule.impl.ScheduleServiceImpl
 * @description:定时调度器实现类
 * @version:v1.0.0
 * @date:2016年12月16日 下午2:39:22
 * @author:WangHao
 */
@Service
public class ScheduleServiceImpl implements ScheduleService
{
	private static Logger logger = LogManager.getLogger(ScheduleServiceImpl.class);
	@Autowired
	private Scheduler scheduler;

	@Override
	public int addHttpCronJob(CronHttpReq cronHttpReq, JobBean jobBean, boolean overWrite)
	{
		if (overWrite)
		{
			this.deleteJob(jobBean);// 覆盖原有定时任务，先删除
		}
		logger.debug("添加cron类型的Http请求定时任务" + cronHttpReq + jobBean);
		// 设定job参数map
		jobBean.setMap(this.getHttpJobMap(cronHttpReq));
		// 创建JobDetail
		JobDetail jobDetail = this.createHttpJob(jobBean);
		// 创建trigger
		CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity(jobBean.getTriggerName())
				.withSchedule(CronScheduleBuilder.cronSchedule(cronHttpReq.getCronExpression())).build();
		try
		{
			scheduler.scheduleJob(jobDetail, trigger);
			return 0;
		}
		catch (SchedulerException e)
		{
			logger.error("添加cron类型的Http请求定时任务异常", e);
			return -1;
		}
	}

	@Override
	public int addHttpSimpleJob(SimpleHttpReq simpleHttpReq, JobBean jobBean, boolean overWrite)
	{
		if (overWrite)
		{
			this.deleteJob(jobBean);// 覆盖原有定时任务，先删除
		}
		logger.debug("添加一般类型的Http请求定时任务" + simpleHttpReq + jobBean);
		// 设定job参数map
		jobBean.setMap(this.getHttpJobMap(simpleHttpReq));
		// 创建JobDetail
		JobDetail jobDetail = this.createHttpJob(jobBean);
		// 创建trigger
		SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity(jobBean.getTriggerName())
				.startAt(simpleHttpReq.getStartTime()).endAt(simpleHttpReq.getEndTime())
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInMilliseconds(simpleHttpReq.getRepeatInterval())
						.withRepeatCount(simpleHttpReq.getRepeatCount()))
				.build();
		try
		{
			scheduler.scheduleJob(jobDetail, simpleTrigger);
			return 0;
		}
		catch (SchedulerException e)
		{
			logger.error("添加一般类型的Http请求定时任务异常", e);
			return -1;
		}
	}

	@Override
	public int deleteJob(JobBean jobBean)
	{
		logger.debug("删除定时任务" + jobBean);
		try
		{
			scheduler.pauseTrigger(new TriggerKey(jobBean.getTriggerName()));// 停止触发器
			scheduler.unscheduleJob(new TriggerKey(jobBean.getTriggerName()));// 移除触发器
			scheduler.deleteJob(new JobKey(jobBean.getJobName()));// 删除任务
			return 0;
		}
		catch (SchedulerException e)
		{
			logger.error("删除定时任务异常", e);
			return -1;
		}
	}

	/**
	 * 获取Http定时Job的参数Map
	 * 
	 * @throws JsonProcessingException
	 */
	private Map<String, String> getHttpJobMap(HttpReqBean httpReqBean)
	{
		// job参数Map
		Map<String, String> jobMap = new HashMap<String, String>();
		jobMap.put(HttpJob.URL_KEY, httpReqBean.getReqUrl());
		jobMap.put(HttpJob.BODY_KEY, httpReqBean.getReqBody());
		jobMap.put(HttpJob.METHOD_KEY, httpReqBean.getReqMethod());
		jobMap.put(HttpJob.FORMAT_KEY, httpReqBean.getReqFormat());
		// 定时Http的请求头map
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		try
		{
			jobMap.put(HttpJob.HEAD_KEY, mapper.writeValueAsString(httpReqBean.getHeadMap()));
		}
		catch (JsonProcessingException e)
		{
			logger.error("设定Http定时job,设置Http头异常", e);
		}
		return jobMap;
	}

	/**
	 * 创建Http定时Job
	 */
	private JobDetail createHttpJob(JobBean jobBean)
	{
		JobDetail job = JobBuilder.newJob(HttpJob.class).usingJobData(new JobDataMap(jobBean.getMap()))
				.withIdentity(jobBean.getJobName(), jobBean.getGroupName()).build();
		return job;
	}
}
