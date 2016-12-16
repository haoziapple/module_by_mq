package com.fzrj.schedule.bean.job;

import java.util.Map;

/**
 * @className:com.fzrj.schedule.bean.job.JobBean
 * @description:定时器任务JobBeanq
 * @version:v1.0.0
 * @date:2016年11月23日 下午1:19:00
 * @author:WangHao
 */
public class JobBean
{
	/**
	 * 任务名(在一组内，任务名唯一)
	 */
	private String jobName;

	/**
	 * 组名
	 */
	private String groupName;

	/**
	 * 触发器名
	 */
	private String triggerName;

	/**
	 * 任务携带参数map
	 */
	private Map<String, String> map;

	public JobBean(String jobName, String groupName, String triggerName)
	{
		super();
		this.jobName = jobName;
		this.groupName = groupName;
		this.triggerName = triggerName;
	}

	public Map<String, String> getMap()
	{
		return map;
	}

	public void setMap(Map<String, String> map)
	{
		this.map = map;
	}

	public String getJobName()
	{
		return jobName;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public String getTriggerName()
	{
		return triggerName;
	}

	@Override
	public String toString()
	{
		return "JobBean [jobName=" + jobName + ", groupName=" + groupName + ", triggerName=" + triggerName + ", map="
				+ map + "]";
	}
}
