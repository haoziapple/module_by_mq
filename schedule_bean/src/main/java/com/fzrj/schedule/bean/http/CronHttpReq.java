package com.fzrj.schedule.bean.http;

/**
 * @className:com.fzrj.schedule.bean.http.CronHttpReq
 * @description:Cron表达式的Http请求
 * @version:v1.0.0
 * @date:2016年11月23日 下午5:37:24
 * @author:WangHao
 */
public class CronHttpReq extends HttpReqBean
{
	public CronHttpReq(String reqUrl, String reqBody, String cronExpression)
	{
		super(reqUrl, reqBody);
		this.cronExpression = cronExpression;
	}

	/**
	 * cron表达式 参考:http://cron.qqe2.com/
	 */
	private String cronExpression = "";

	public String getCronExpression()
	{
		return cronExpression;
	}
}
