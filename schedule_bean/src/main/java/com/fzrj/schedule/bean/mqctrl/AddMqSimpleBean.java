package com.fzrj.schedule.bean.mqctrl;

import com.fzrj.schedule.bean.job.SimpleJobBean;
import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;

/**
 * @className:com.fzrj.schedule.bean.mqctrl.AddMqSimpleBean
 * @description:添加一般类型Mq定时任务请求bean(控制层专用)
 * @version:v1.0.0
 * @date:2016年12月22日 下午8:29:44
 * @author:WangHao
 */
public class AddMqSimpleBean
{
	private MqMsgBean mqMsgBean;

	private SimpleJobBean simpleJobBean;

	private boolean overWrite;
	
	public AddMqSimpleBean()
	{
		super();
	}

	public AddMqSimpleBean(MqMsgBean mqMsgBean, SimpleJobBean simpleJobBean, boolean overWrite)
	{
		this.mqMsgBean = mqMsgBean;
		this.simpleJobBean = simpleJobBean;
		this.overWrite = overWrite;
	}

	public MqMsgBean getMqMsgBean()
	{
		return mqMsgBean;
	}

	public SimpleJobBean getSimpleJobBean()
	{
		return simpleJobBean;
	}

	public boolean isOverWrite()
	{
		return overWrite;
	}

	@Override
	public String toString()
	{
		return "AddMqSimpleBean [mqMsgBean=" + mqMsgBean + ", simpleJobBean=" + simpleJobBean + ", overWrite="
				+ overWrite + "]";
	}
}
