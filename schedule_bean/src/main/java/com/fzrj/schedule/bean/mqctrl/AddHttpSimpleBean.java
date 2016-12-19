package com.fzrj.schedule.bean.mqctrl;

import com.fzrj.schedule.bean.http.SimpleHttpReq;
import com.fzrj.schedule.bean.job.JobBean;

/**
 * @className:com.fzrj.schedule.bean.mqctrl.AddHttpSimpleBean
 * @description:添加一般类型http定时任务请求bean(mq控制层专用)
 * @version:v1.0.0
 * @date:2016年12月19日 下午6:34:52
 * @author:WangHao
 */
public class AddHttpSimpleBean
{
	private SimpleHttpReq simpleHttpReq;

	private JobBean jobBean;

	private boolean overWrite;

	public SimpleHttpReq getSimpleHttpReq()
	{
		return simpleHttpReq;
	}

	public void setSimpleHttpReq(SimpleHttpReq simpleHttpReq)
	{
		this.simpleHttpReq = simpleHttpReq;
	}

	public JobBean getJobBean()
	{
		return jobBean;
	}

	public void setJobBean(JobBean jobBean)
	{
		this.jobBean = jobBean;
	}

	public boolean isOverWrite()
	{
		return overWrite;
	}

	public void setOverWrite(boolean overWrite)
	{
		this.overWrite = overWrite;
	}

	@Override
	public String toString()
	{
		return "AddHttpSimpleBean [simpleHttpReq=" + simpleHttpReq + ", jobBean=" + jobBean + ", overWrite=" + overWrite
				+ "]";
	}
}
