package com.fzrj.schedule.client.factory;

import java.util.Date;

import com.fzrj.schedule.bean.job.CronJobBean;
import com.fzrj.schedule.bean.job.JobBean;
import com.fzrj.schedule.bean.job.SimpleJobBean;
import com.fzrj.schedule.client.util.ConfigUtil;

/**
 * @className:com.fzrj.schedule.client.factory.JobFactory
 * @description:定时任务工厂类(隐藏任务组名的实现)
 * @version:v1.0.0
 * @date:2016年12月23日 下午8:33:59
 * @author:WangHao
 */
public class JobFactory
{
	// 创建一般的jobBean
	public static JobBean buildJob(String jobKey)
	{
		return new JobBean(jobKey, ConfigUtil.getPlatName());
	}

	// 创建Cron类jobBean
	public static CronJobBean buildCronJob(String jobKey, String cronExpression)
	{
		return new CronJobBean(jobKey, ConfigUtil.getPlatName(), cronExpression);
	}

	// 创建一般类型的jobBean,延迟执行
	public static SimpleJobBean buildSimpleJob(String jobKey, long pendTime)
	{
		return buildSimpleJob(jobKey, pendTime, 0, 0);
	}

	// 创建一般类型的jobBean,延迟执行,重试机制
	public static SimpleJobBean buildSimpleJob(String jobKey, long pendTime, int repeatCount, long repeatInterval)
	{
		return new SimpleJobBean(jobKey, ConfigUtil.getPlatName(), pendTime, repeatCount, repeatInterval);
	}

	// 创建一般类型的jobBean,指定时间
	public static SimpleJobBean buildSimpleJob(String jobKey, Date startTime)
	{
		return buildSimpleJob(jobKey, startTime, 0, 0);
	}

	// 创建一般类型的jobBean,指定时间,重试机制
	public static SimpleJobBean buildSimpleJob(String jobKey, Date startTime, int repeatCount, long repeatInterval)
	{
		return new SimpleJobBean(jobKey, ConfigUtil.getPlatName(), startTime, repeatCount, repeatInterval);
	}
}
