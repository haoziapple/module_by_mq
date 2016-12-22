package com.fzrj.schedule.bean.mqctrl;

import com.fzrj.schedule.bean.job.CronJobBean;
import com.fzrj.schedule.bean.jobdetail.http.HttpReqBean;

/**
 * @className:com.fzrj.schedule.bean.mqctrl.AddHttpCronBean
 * @description:添加cron类型http定时任务请求bean(mq控制层专用)
 * @version:v1.0.0
 * @date:2016年12月19日 下午6:32:15
 * @author:WangHao
 */
public class AddHttpCronBean
{
	private HttpReqBean httpReqBean;

	private CronJobBean cronJobBean;

	/**
	 * 是否覆盖，true将先尝试删除原有定时任务
	 */
	private boolean overWrite;

	public AddHttpCronBean(HttpReqBean httpReqBean, CronJobBean cronJobBean, boolean overWrite)
	{
		this.httpReqBean = httpReqBean;
		this.cronJobBean = cronJobBean;
		this.overWrite = overWrite;
	}

	public HttpReqBean getHttpReqBean()
	{
		return httpReqBean;
	}


	public boolean isOverWrite()
	{
		return overWrite;
	}

	public CronJobBean getCronJobBean()
	{
		return cronJobBean;
	}

	@Override
	public String toString()
	{
		return "AddHttpCronBean [httpReqBean=" + httpReqBean + ", cronJobBean=" + cronJobBean + ", overWrite="
				+ overWrite + "]";
	}
}
