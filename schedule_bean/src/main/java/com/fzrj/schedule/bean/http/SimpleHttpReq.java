package com.fzrj.schedule.bean.http;

import java.util.Date;
import java.util.Map;

/**
 * @className:com.fzrj.schedule.bean.http.SimpleHttpReq
 * @description:简单定时器Http请求
 * @version:v1.0.0
 * @date:2016年11月23日 下午5:26:58
 * @author:WangHao
 */
public class SimpleHttpReq extends HttpReqBean
{
	public SimpleHttpReq(String reqUrl, Map<String, String> head)
	{
		super(reqUrl, head);
	}

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
	private long repeatInterval;

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
