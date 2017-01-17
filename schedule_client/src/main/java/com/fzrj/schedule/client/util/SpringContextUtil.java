package com.fzrj.schedule.client.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.fzrj.schedule.client.MqReceiver;

/**
 * @className:com.fzrj.schedule.client.util.SpringContextUtil
 * @description:spring上下文工具类(用于获取spring容器中的bean)
 * @version:v1.0.0
 * @date:2016年12月23日 下午9:00:36
 * @author:WangHao
 */
public class SpringContextUtil implements ApplicationContextAware, InitializingBean, DisposableBean
{
	private static Logger logger = LogManager.getLogger(SpringContextUtil.class);

	private static ApplicationContext applicationContext; // Spring应用上下文环境

	/*
	 * 实现了ApplicationContextAware 接口，必须实现该方法；
	 * 通过传递applicationContext参数初始化成员变量applicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		SpringContextUtil.applicationContext = applicationContext;

	}

	public static ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException
	{
		return (T) applicationContext.getBean(name);
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		int consumerNum = 2;
		logger.debug("启动" + consumerNum + "个队列消息接收线程");
		for (int i = 0; i < consumerNum; i++)
		{
			MqReceiver.startConsumer();
			MqReceiver.startRPCConsumer();
		}
		logger.debug("启动" + consumerNum + "个RPC调用的接收线程");

	}

	@Override
	public void destroy() throws Exception
	{
		logger.debug("关闭消息队列通道与连接");
		// 停止队列消息接收线程
		MqConnectionFactory.closeConnection();
	}

}
