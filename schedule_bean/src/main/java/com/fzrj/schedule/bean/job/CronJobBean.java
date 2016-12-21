package com.fzrj.schedule.bean.job;

/**
 * @className:com.fzrj.schedule.bean.job.CronJobBean
 * @description:Cron类型定时任务
 * @version:v1.0.0
 * @date:2016年12月21日 下午4:09:29
 * @author:WangHao
 */
public class CronJobBean extends JobBean
{

	public CronJobBean(String jobKey, String platName, String cronExpression)
	{
		super(jobKey, platName);
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
