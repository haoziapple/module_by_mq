package com.fzrj.schedule.client.builder;

import java.util.Date;

import com.fzrj.schedule.bean.ArgChecker;
import com.fzrj.schedule.bean.job.CronJobBean;
import com.fzrj.schedule.bean.job.JobBean;
import com.fzrj.schedule.bean.job.SimpleJobBean;
import com.fzrj.schedule.client.util.ConfigUtil;

/**
 * @className:com.fzrj.schedule.client.builder.JobBuilder
 * @description:定时任务建造器
 * @version:v1.0.0
 * @date:2017年1月3日 下午3:53:46
 * @author:WangHao
 */
public class JobBuilder
{
	// 任务平台名
	private String _platName = ConfigUtil.getPlatName();
	// 任务key唯一标识
	private String _jobKey = null;

	// 任务cron表达式
	private String _cronExp = null;
	// 任务指定触发时间
	private Date _startTime = null;
	// 任务延迟触发时间(毫秒)
	private Long _pendTime = null;
	// 任务重试次数(默认0次)
	private int _repeatCount = 0;
	// 任务重试间隔(默认10s)
	private long _repeatInterval = 10000L;

	/**
	 * 设置任务标识
	 */
	public JobBuilder IdentifyWith(String jobKey)
	{
		_jobKey = jobKey;
		return this;
	}

	/**
	 * 设置spring任务指定触发时间
	 */
	public JobBuilder setStartTime(Date startTime)
	{
		ArgChecker.checkArgument(null == _pendTime, "任务已经设置了延迟触发时间");
		_startTime = startTime;
		return this;
	}

	/**
	 * 设置spring任务延迟触发时间
	 */
	public JobBuilder setPendTime(Long pendTime)
	{
		ArgChecker.checkArgument(null == _startTime, "任务已经设置了指定触发时间");
		_pendTime = pendTime;
		return this;
	}

	/**
	 * 设置任务重试策略
	 */
	public JobBuilder setRepeatPolicy(int repeatCount, int repeatInterval)
	{
		ArgChecker.checkArgument(repeatCount >= 0 && repeatInterval >= 0, "重试次数和重试间隔不可小于0");
		_repeatCount = repeatCount;
		_repeatInterval = repeatInterval;
		return this;
	}

	public JobBuilder setCronExp(String cronExp)
	{
		_cronExp = cronExp;
		return this;
	}

	/**
	 * 创建cron类型定时任务
	 */
	public CronJobBean buildCronJob()
	{
		// 任务key不可为空
		ArgChecker.checkArgument(null != _jobKey, "任务key不可为空");
		// cron表达式不可为空
		ArgChecker.checkArgument(null != _cronExp, " cron表达式不可为空");
		return new CronJobBean(_jobKey, _platName, _cronExp);
	}

	/**
	 * 创建一般类型定时任务
	 */
	public SimpleJobBean buildSimpleJob()
	{
		// 任务key不可为空
		ArgChecker.checkArgument(null != _jobKey, "任务key不可为空");
		// 指定触发时间与延迟触发时间不可都为空
		ArgChecker.checkArgument(null != _startTime || null != _pendTime, "指定触发时间与延迟触发时间不可都为空");
		SimpleJobBean simpleJobBean;
		if (null != _startTime)
		{
			simpleJobBean = new SimpleJobBean(_jobKey, _platName, _startTime, _repeatCount, _repeatInterval);
		}
		else
		{
			simpleJobBean = new SimpleJobBean(_jobKey, _platName, _pendTime, _repeatCount, _repeatInterval);
		}
		return simpleJobBean;
	}

	/**
	 * 创建一般的jobBean
	 */
	public JobBean buildJob()
	{
		return new JobBean(_jobKey, _platName);
	}
}
