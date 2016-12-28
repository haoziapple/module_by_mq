package com.fzrj.schedule.bean.job;

import java.util.HashMap;
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
	private Map<String, String> map=new HashMap<String, String>();
	
	public JobBean()
	{
		super();
	}

	/**
	 * 通过平台名platName与jobKey确定一个定时任务，同一平台里jobKey唯一
	 * 
	 * @param jobKey--
	 * @param platName
	 */
	public JobBean(String jobKey, String platName)
	{
		super();
		this.jobName = jobKey;
		this.groupName = platName;
		this.triggerName = jobKey;
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
