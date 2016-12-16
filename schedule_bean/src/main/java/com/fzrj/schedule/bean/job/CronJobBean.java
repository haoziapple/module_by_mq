package com.fzrj.schedule.bean.job;

/**
 * @className:com.fzrj.schedule.bean.job.CronJobBean
 * @description:cron表达式任务bean
 * @version:v1.0.0
 * @date:2016年11月23日 下午1:52:20
 * @author:WangHao
 */
public class CronJobBean extends JobBean
{
	public CronJobBean(String jobName, String groupName, String triggerName)
	{
		super(jobName, groupName, triggerName);
	}

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
