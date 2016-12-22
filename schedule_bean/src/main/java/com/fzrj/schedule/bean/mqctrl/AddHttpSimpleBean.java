package com.fzrj.schedule.bean.mqctrl;

import com.fzrj.schedule.bean.job.SimpleJobBean;
import com.fzrj.schedule.bean.jobdetail.http.HttpReqBean;

/**
 * @className:com.fzrj.schedule.bean.mqctrl.AddHttpSimpleBean
 * @description:添加一般类型http定时任务请求bean(mq控制层专用)
 * @version:v1.0.0
 * @date:2016年12月19日 下午6:34:52
 * @author:WangHao
 */
public class AddHttpSimpleBean
{
	private HttpReqBean httpReqBean;

	private SimpleJobBean simpleJobBean;

	private boolean overWrite;

	public AddHttpSimpleBean(HttpReqBean httpReqBean, SimpleJobBean simpleJobBean, boolean overWrite)
	{
		this.httpReqBean = httpReqBean;
		this.simpleJobBean = simpleJobBean;
		this.overWrite = overWrite;
	}

	public boolean isOverWrite()
	{
		return overWrite;
	}

	public HttpReqBean getHttpReqBean()
	{
		return httpReqBean;
	}

	public SimpleJobBean getSimpleJobBean()
	{
		return simpleJobBean;
	}

	@Override
	public String toString()
	{
		return "AddHttpSimpleBean [httpReqBean=" + httpReqBean + ", simpleJobBean=" + simpleJobBean + ", overWrite="
				+ overWrite + "]";
	}
}
