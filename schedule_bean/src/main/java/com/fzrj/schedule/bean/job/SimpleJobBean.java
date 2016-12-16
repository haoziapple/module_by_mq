package com.fzrj.schedule.bean.job;

import java.util.Date;

/**
 * @className:com.fzrj.schedule.bean.job.SimpleJobBean
 * @description:简单定时任务bean
 * @version:v1.0.0
 * @date:2016年11月23日 下午1:17:19
 * @author:WangHao
 */
public class SimpleJobBean extends JobBean
{
	/**
	 * 任务开始时间
	 */
	private Date startTime;
	/**
	 * 任务结束时间
	 */
	private Date endTime;
	/**
	 * 任务重试次数
	 */
	private int repeatCount;
	/**
	 * 任务间隔时间(毫秒)
	 */
	private long repeatInterval;

	public SimpleJobBean(String jobName, String groupName, String triggerName)
	{
		super(jobName, groupName, triggerName);
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	public int getRepeatCount()
	{
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount)
	{
		this.repeatCount = repeatCount;
	}

	public long getRepeatInterval()
	{
		return repeatInterval;
	}

	public void setRepeatInterval(long repeatInterval)
	{
		this.repeatInterval = repeatInterval;
	}

}
