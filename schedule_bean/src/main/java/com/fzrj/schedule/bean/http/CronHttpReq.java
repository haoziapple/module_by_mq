package com.fzrj.schedule.bean.http;

import java.util.Map;

/**
 * @className:com.fzrj.schedule.bean.http.CronHttpReq
 * @description:Cron表达式的Http请求
 * @version:v1.0.0
 * @date:2016年11月23日 下午5:37:24
 * @author:WangHao
 */
public class CronHttpReq extends HttpReqBean
{
	public CronHttpReq(String reqUrl, Map<String, String> head)
	{
		super(reqUrl, head);
	}

	/**
	 * cron表达式 参考:http://cron.qqe2.com/
	 */
	private String cronExpression = "";

	public String getCronExpression()
	{
		return cronExpression;
	}

	public void setCronExpression(String cronExpression)
	{
		this.cronExpression = cronExpression;
	}
}
