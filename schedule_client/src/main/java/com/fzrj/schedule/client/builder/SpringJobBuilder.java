package com.fzrj.schedule.client.builder;

import java.util.Date;

import com.fzrj.schedule.bean.ArgChecker;
import com.fzrj.schedule.bean.job.CronJobBean;
import com.fzrj.schedule.bean.job.JobBean;
import com.fzrj.schedule.bean.job.SimpleJobBean;
import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;
import com.fzrj.schedule.bean.mqctrl.AddMqCronBean;
import com.fzrj.schedule.bean.mqctrl.AddMqSimpleBean;
import com.fzrj.schedule.client.util.ConfigUtil;

/**
 * @className:com.fzrj.schedule.client.builder.SpringJobBuilder
 * @description:Spring定时任务建造器
 * @version:v1.0.0
 * @date:2016年12月30日 下午2:44:13
 * @author:WangHao
 */
public class SpringJobBuilder
{
	// 调用服务名,默认调用自身服务
	private String _serverName = ConfigUtil.getPlatServer();
	// 调用bean名称(在spring容器中注册的名称)
	private String _beanName = null;
	// 调用方法名称
	private String _methodName = null;
	// 调用参数实例
	private Object _paramBean = null;
	// 任务编号,一般使用orderCode
	private String _jobCode = null;
	// 任务cron表达式
	private String _cronExp = null;
	// 任务指定触发时间
	private Date _startTime = null;
	// 任务延迟触发时间(毫秒)
	private Long _pendTime = null;
	// 任务重试次数(默认0次)
	private int _repeatCount = 0;
	// 任务重试间隔(默认10s)
	@SuppressWarnings("unused")
	private long _repeatInterval = 10000L;

	/**
	 * 设置spring任务属性
	 */
	public SpringJobBuilder setSpringJob(String beanName, String methodName, Object paramBean)
	{
		_beanName = beanName;
		_methodName = methodName;
		_paramBean = paramBean;
		return this;
	}

	/**
	 * 设置spring任务调用的服务
	 */
	public SpringJobBuilder setSpringServer(String serverName)
	{
		ArgChecker.checkArgument(ConfigUtil.getServerList().contains(serverName), "定时任务调用服务不在列表中");
		_serverName = serverName;
		return this;
	}

	/**
	 * 设置任务编号
	 */
	public SpringJobBuilder setJobCode(String jobCode)
	{
		_jobCode = jobCode;
		return this;
	}

	/**
	 * 设置spring任务指定触发时间
	 */
	public SpringJobBuilder setStartTime(Date startTime)
	{
		ArgChecker.checkArgument(null == _pendTime, "任务已经设置了延迟触发时间");
		_startTime = startTime;
		return this;
	}

	/**
	 * 设置spring任务延迟触发时间
	 */
	public SpringJobBuilder setPendTime(Long pendTime)
	{
		ArgChecker.checkArgument(null == _startTime, "任务已经设置了指定触发时间");
		_pendTime = pendTime;
		return this;
	}

	/**
	 * 设置任务重试策略
	 */
	public SpringJobBuilder setRepeatPolicy(int repeatCount, int repeatInterval)
	{
		ArgChecker.checkArgument(repeatCount < 0 || repeatInterval < 0, "重试次数和重试间隔不可小于0");
		_repeatCount = repeatCount;
		_repeatInterval = repeatInterval;
		return this;
	}

	public SpringJobBuilder setCronExp(String cronExp)
	{
		_cronExp = cronExp;
		return this;
	}

	/**
	 * 创建cron类型定时任务
	 */
	public AddMqCronBean buildMqCronJob()
	{
		CronJobBean cronJobBean = new JobBuilder().IdentifyWith(this.getSpringJobName()).setCronExp(_cronExp)
				.buildCronJob();
		MqMsgBean mqMsgBean = new MqMsgBuilder().setSpringJob(_beanName, _methodName, _paramBean)
				.setSpringServer(_serverName).buildMqMsg();
		return new AddMqCronBean(mqMsgBean, cronJobBean, true);
	}

	/**
	 * 创建一般类型定时任务
	 */
	public AddMqSimpleBean buildMqSimpleJob()
	{
		SimpleJobBean simpleJobBean = new JobBuilder().IdentifyWith(this.getSpringJobName()).setPendTime(_pendTime)
				.setStartTime(_startTime).setRepeatPolicy(_repeatCount, _repeatCount).buildSimpleJob();
		MqMsgBean mqMsgBean = new MqMsgBuilder().setSpringJob(_beanName, _methodName, _paramBean)
				.setSpringServer(_serverName).buildMqMsg();

		return new AddMqSimpleBean(mqMsgBean, simpleJobBean, true);
	}

	/**
	 * 创建一般的jobBean
	 */
	public JobBean buildJob()
	{
		return new JobBuilder().IdentifyWith(this.getSpringJobName()).buildJob();
	}

	// 组装MQ定时任务名称
	private String getSpringJobName()
	{
		// 任务属性不可为空
		ArgChecker.checkArgument(null == _serverName || null == _beanName || null == _methodName, "任务属性不可为空");
		return _serverName + "_" + _beanName + "_" + _methodName + ":" + _jobCode;
	}
}
