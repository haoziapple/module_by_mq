package com.fzrj.schedule.bean.job;

import java.util.Date;

public class SimpleJobBean extends JobBean
{
	/**
	 * 指定任务开始时间，不重试
	 */
	public SimpleJobBean(String reqUrl, String reqBody, Date startTime)
	{
		super(reqUrl, reqBody);
		this.startTime = startTime;
		Date endDate = new Date();
		endDate.setTime(startTime.getTime() + 10000L);
		this.endTime = endDate;
	}

	/**
	 * 指定任务开始时间与重试机制
	 */
	public SimpleJobBean(String reqUrl, String reqBody, Date startTime, int repeatCount, long repeatInterval)
	{
		super(reqUrl, reqBody);
		this.startTime = startTime;
		this.repeatCount = repeatCount;
		this.repeatInterval = repeatInterval;
		this.endTime = new Date();
		this.endTime.setTime(startTime.getTime() + repeatCount * repeatInterval + 10000L);
	}
	
	/**
	 * 指定任务延迟时间，不重试
	 */
	public SimpleJobBean(String reqUrl, String reqBody, long pendTime)
	{
		super(reqUrl, reqBody);
		this.pendTime = pendTime;
	}
	
	/**
	 * 指定任务延迟时间与重试机制
	 */
	public SimpleJobBean(String reqUrl, String reqBody, long pendTime, int repeatCount, long repeatInterval)
	{
		super(reqUrl, reqBody);
		this.pendTime = pendTime;
		this.repeatCount = repeatCount;
		this.repeatInterval = repeatInterval;
	}

	/**
	 * @Description:根据等待时间计算任务时间
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月20日 下午8:27:50
	 */
	public void calculateStartTime()
	{
		if (this.pendTime != 0)
		{
			Date nowDate = new Date();
			startTime = new Date();
			startTime.setTime(nowDate.getTime() + pendTime);
			this.endTime = new Date();
			this.endTime.setTime(startTime.getTime() + repeatCount * repeatInterval + 10000L);
		}
	}

	/**
	 * 等待时间
	 */
	private long pendTime = 0;

	/**
	 * 开始执行时间
	 */
	private Date startTime;

	/**
	 * 结束执行时间
	 */
	private Date endTime;

	/**
	 * 重复执行次数，默认不重复
	 */
	private int repeatCount = 0;

	/**
	 * 重复执行间隔(毫秒)
	 */
	private long repeatInterval = 0;

	public Date getStartTime()
	{
		return startTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public int getRepeatCount()
	{
		return repeatCount;
	}

	public long getRepeatInterval()
	{
		return repeatInterval;
	}

	public long getPendTime()
	{
		return pendTime;
	}
}
