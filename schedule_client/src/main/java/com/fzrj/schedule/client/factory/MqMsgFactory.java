package com.fzrj.schedule.client.factory;

import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;
import com.fzrj.schedule.client.bean.SpringJobBean;
import com.fzrj.schedule.client.util.ConfigUtil;
import com.fzrj.schedule.client.util.JsonUtil;

/**
 * @className:com.fzrj.schedule.client.factory.MqMsgFactory
 * @description:消息工厂类
 * @version:v1.0.0
 * @date:2016年12月23日 下午8:44:17
 * @author:WangHao
 */
public class MqMsgFactory
{
	/**
	 * 创建定时任务消息(发送给本服务)
	 */
	public static MqMsgBean buildMqMsg(String beanName, String methodName, Object paramBean)
	{
		SpringJobBean springJobBean = new SpringJobBean(beanName, methodName, JsonUtil.convertObjToString(paramBean));
		return new MqMsgBean(ConfigUtil.getPlatExchange(), ConfigUtil.getPlatQueue(),
				JsonUtil.convertObjToString(springJobBean));
	}

	/**
	 * 创建定时任务消息(参数bean使用json字串)
	 */
	public static MqMsgBean buildMqMsg(String beanName, String methodName, String paramBeanJson)
	{
		SpringJobBean springJobBean = new SpringJobBean(beanName, methodName, paramBeanJson);
		return new MqMsgBean(ConfigUtil.getPlatExchange(), ConfigUtil.getPlatQueue(),
				JsonUtil.convertObjToString(springJobBean));
	}

	/**
	 * 创建定时任务消息(发送给其他服务)
	 */
	public static MqMsgBean buildMqMsg(String beanName, String methodName, Object paramBean, String serverName)
	{
		SpringJobBean springJobBean = new SpringJobBean(beanName, methodName, JsonUtil.convertObjToString(paramBean));
		return new MqMsgBean(ConfigUtil.getPlatExchange(), ConfigUtil.getPlatQueue(serverName),
				JsonUtil.convertObjToString(springJobBean));
	}

	/**
	 * 创建定时任务消息(参数bean使用json字串,发送给其他服务)
	 */
	public static MqMsgBean buildMqMsg(String beanName, String methodName, String paramBeanJson, String serverName)
	{
		SpringJobBean springJobBean = new SpringJobBean(beanName, methodName, paramBeanJson);
		return new MqMsgBean(ConfigUtil.getPlatExchange(), ConfigUtil.getPlatQueue(serverName),
				JsonUtil.convertObjToString(springJobBean));
	}
}
