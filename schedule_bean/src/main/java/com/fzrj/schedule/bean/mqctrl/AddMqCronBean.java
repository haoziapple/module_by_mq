package com.fzrj.schedule.bean.mqctrl;

import com.fzrj.schedule.bean.job.CronJobBean;
import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;

/**
 * @className:com.fzrj.schedule.bean.mqctrl.AddMqCronBean
 * @description:添加cron类型Mq定时任务请求bean(控制层专用)
 * @version:v1.0.0
 * @date:2016年12月22日 下午8:27:21
 * @author:WangHao
 */
public class AddMqCronBean
{
	private MqMsgBean mqMsgBean;

	private CronJobBean cronJobBean;

	/**
	 * 是否覆盖，true将先尝试删除原有定时任务
	 */
	private boolean overWrite;

	public AddMqCronBean(MqMsgBean mqMsgBean, CronJobBean cronJobBean, boolean overWrite)
	{
		this.mqMsgBean = mqMsgBean;
		this.cronJobBean = cronJobBean;
		this.overWrite = overWrite;
	}

	public MqMsgBean getMqMsgBean()
	{
		return mqMsgBean;
	}

	public CronJobBean getCronJobBean()
	{
		return cronJobBean;
	}

	public boolean isOverWrite()
	{
		return overWrite;
	}

	@Override
	public String toString()
	{
		return "AddMqCronBean [mqMsgBean=" + mqMsgBean + ", cronJobBean=" + cronJobBean + ", overWrite=" + overWrite
				+ "]";
	}
}
