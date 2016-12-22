package com.fzrj.schedule.service.schedule.impl;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fzrj.schedule.bean.job.SimpleJobBean;
import com.fzrj.schedule.service.job.TestJob;
import com.fzrj.schedule.service.schedule.TestScheuleService;

/**
 * @className:com.fzrj.schedule.service.schedule.impl.TestScheuleServiceImpl
 * @description:测试定时任务Service实现类
 * @version:v1.0.0
 * @date:2016年12月22日 下午1:04:52
 * @author:WangHao
 */
@Service
public class TestScheuleServiceImpl implements TestScheuleService
{
	@Autowired
	private Scheduler scheduler;

	@Override
	public void addtestJob(SimpleJobBean simpleJobBean) throws SchedulerException
	{
		JobDetail job = JobBuilder.newJob(TestJob.class).usingJobData(new JobDataMap(simpleJobBean.getMap()))
				.withIdentity(simpleJobBean.getJobName(), simpleJobBean.getGroupName()).build();
		SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
				.withIdentity(simpleJobBean.getTriggerName(), simpleJobBean.getGroupName())
				.startAt(simpleJobBean.getStartTime()).endAt(simpleJobBean.getEndTime())
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInMilliseconds(simpleJobBean.getRepeatInterval())
						.withRepeatCount(simpleJobBean.getRepeatCount()))
				.build();
		try
		{
			scheduler.scheduleJob(job, simpleTrigger);
		}
		catch (SchedulerException e)
		{
			throw e;
		}
	}

}
