package com.fzrj.schedule.bean.mqctrl;

import com.fzrj.schedule.bean.http.CronHttpReq;
import com.fzrj.schedule.bean.job.JobBean;

/**
 * @className:com.fzrj.schedule.bean.mqctrl.AddHttpCronBean
 * @description:添加cron类型http定时任务请求bean(mq控制层专用)
 * @version:v1.0.0
 * @date:2016年12月19日 下午6:32:15
 * @author:WangHao
 */
public class AddHttpCronBean
{
	private CronHttpReq cronHttpReq;

	private JobBean jobBean;

	private boolean overWrite;

	public CronHttpReq getCronHttpReq()
	{
		return cronHttpReq;
	}

	public void setCronHttpReq(CronHttpReq cronHttpReq)
	{
		this.cronHttpReq = cronHttpReq;
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
		return "AddHttpCronBean [cronHttpReq=" + cronHttpReq + ", jobBean=" + jobBean + ", overWrite=" + overWrite
				+ "]";
	}
}
