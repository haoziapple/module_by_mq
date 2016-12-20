package com.fzrj.schedule.bean.http;

import java.util.Date;

/**
 * @className:com.fzrj.schedule.bean.http.SimpleHttpReq
 * @description:简单定时器Http请求
 * @version:v1.0.0
 * @date:2016年11月23日 下午5:26:58
 * @author:WangHao
 */
public class SimpleHttpReq extends HttpReqBean
{
	/**
	 * 默认不重试
	 */
	public SimpleHttpReq(String reqUrl, String reqBody, Date startTime)
	{
		super(reqUrl, reqBody);
		this.startTime = startTime;
		this.repeatInterval = 1L;
		Date endDate = new Date();
		endDate.setTime(startTime.getTime() + 10000L);
		this.endTime = endDate;
	}

	public SimpleHttpReq(String reqUrl, String reqBody, Date startTime, int repeatCount, long repeatInterval)
	{
		super(reqUrl, reqBody);
		this.startTime = startTime;
		this.repeatCount = repeatCount;
		this.repeatInterval = repeatInterval;
		this.endTime = new Date();
		this.endTime.setTime(startTime.getTime() + repeatCount * repeatInterval + 10000L);
	}

	/**
	 * 等待一段时间后执行任务
	 * 
	 * @param reqUrl
	 * @param reqBody
	 * @param pendTime
	 */
	public SimpleHttpReq(String reqUrl, String reqBody, long pendTime)
	{
		super(reqUrl, reqBody);
		this.pendTime = pendTime;
	}

	public SimpleHttpReq(String reqUrl, String reqBody, long pendTime, int repeatCount, long repeatInterval)
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
