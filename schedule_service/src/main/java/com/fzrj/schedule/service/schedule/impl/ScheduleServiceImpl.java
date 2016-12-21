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
import com.fzrj.schedule.bean.http.HttpReqBean;
import com.fzrj.schedule.bean.job.CronJobBean;
import com.fzrj.schedule.bean.job.JobBean;
import com.fzrj.schedule.bean.job.SimpleJobBean;
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
	public int addHttpCronJob(HttpReqBean httpReqBean, CronJobBean cronJobBean, boolean overWrite) throws SchedulerException
	{
		if (overWrite)
		{
			this.deleteJob(cronJobBean);// 覆盖原有定时任务，先删除
		}
		logger.debug("添加cron类型的Http请求定时任务" + httpReqBean + cronJobBean);
		// 设定job参数map
		cronJobBean.setMap(this.getHttpJobMap(httpReqBean));
		// 创建JobDetail
		JobDetail jobDetail = this.createHttpJob(cronJobBean);
		// 创建trigger
		CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity(cronJobBean.getTriggerName())
				.withSchedule(CronScheduleBuilder.cronSchedule(cronJobBean.getCronExpression())).build();
		try
		{
			scheduler.scheduleJob(jobDetail, trigger);
			return 0;
		}
		catch (SchedulerException e)
		{
			throw e;
		}
	}

	@Override
	public int addHttpSimpleJob(HttpReqBean httpReqBean, SimpleJobBean simpleJobBean, boolean overWrite) throws SchedulerException
	{
		if (overWrite)
		{
			this.deleteJob(simpleJobBean);// 覆盖原有定时任务，先删除
		}
		// 计算任务开始时间,如果设置了pendTime的话
		simpleJobBean.calculateStartTime();
		logger.debug("添加一般类型的Http请求定时任务" + httpReqBean + simpleJobBean);
		// 设定job参数map
		simpleJobBean.setMap(this.getHttpJobMap(httpReqBean));
		// 创建JobDetail
		JobDetail jobDetail = this.createHttpJob(simpleJobBean);
		// 创建trigger
		SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity(simpleJobBean.getTriggerName())
				.startAt(simpleJobBean.getStartTime()).endAt(simpleJobBean.getEndTime())
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInMilliseconds(simpleJobBean.getRepeatInterval())
						.withRepeatCount(simpleJobBean.getRepeatCount()))
				.build();
		try
		{
			scheduler.scheduleJob(jobDetail, simpleTrigger);
			return 0;
		}
		catch (SchedulerException e)
		{
			throw e;
		}
	}

	@Override
	public int deleteJob(JobBean jobBean) throws SchedulerException
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
			throw e;
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
