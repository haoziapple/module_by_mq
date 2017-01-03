package com.fzrj.schedule.client.builder;

import com.fzrj.schedule.bean.ArgChecker;
import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;
import com.fzrj.schedule.client.bean.SpringJobBean;
import com.fzrj.schedule.client.util.ConfigUtil;
import com.fzrj.schedule.client.util.JsonUtil;

/**
 * @className:com.fzrj.schedule.client.builder.MqMsgBuilder
 * @description:定时消息建造类
 * @version:v1.0.0
 * @date:2017年1月3日 下午4:51:27
 * @author:WangHao
 */
public class MqMsgBuilder
{
	// 调用服务名,默认调用自身服务
	private String _serverName = ConfigUtil.getPlatServer();
	// 调用bean名称(在spring容器中注册的名称)
	private String _beanName = null;
	// 调用方法名称
	private String _methodName = null;
	// 调用参数实例
	private Object _paramBean = null;

	/**
	 * 设置spring任务属性
	 */
	public MqMsgBuilder setSpringJob(String beanName, String methodName, Object paramBean)
	{
		_beanName = beanName;
		_methodName = methodName;
		_paramBean = paramBean;
		return this;
	}

	/**
	 * 设置spring任务调用的服务
	 */
	public MqMsgBuilder setSpringServer(String serverName)
	{
		ArgChecker.checkArgument(ConfigUtil.getServerList().contains(serverName), "定时任务调用服务不在列表中");
		_serverName = serverName;
		return this;
	}

	/**
	 * 创建MQ消息bean
	 */
	public MqMsgBean buildMqMsg()
	{
		// 任务属性不可为空
		ArgChecker.checkArgument(null == _serverName || null == _beanName || null == _methodName || null == _paramBean,
				"任务属性不可为空");
		SpringJobBean springJobBean = new SpringJobBean(_beanName, _methodName,
				JsonUtil.convertObjToString(_paramBean));
		return new MqMsgBean(ConfigUtil.getPlatExchange(), ConfigUtil.getPlatQueue(_serverName),
				JsonUtil.convertObjToString(springJobBean));
	}
}
