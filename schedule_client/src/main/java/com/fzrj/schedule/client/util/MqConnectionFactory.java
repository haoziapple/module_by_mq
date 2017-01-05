package com.fzrj.schedule.client.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @className:com.fzrj.schedule.client.util.MqConnectionFactory
 * @description:rabbitmq连接工厂类
 * @version:v1.0.0
 * @date:2016年12月19日 下午8:15:02
 * @author:WangHao
 */
public class MqConnectionFactory
{
	private static Logger logger = LogManager.getLogger(MqConnectionFactory.class);

	private static ConnectionFactory factory = null;
	// 单例模式，保证一个服务只使用一个消息通道
	private static Channel channel = null;
	private static Object lock = new Object();

	public static Channel getInstance() throws IOException, TimeoutException
	{
		if (channel == null)
		{
			synchronized (lock)
			{
				if (channel == null)
				{
					if (factory == null)
						factory = new ConnectionFactory();
					factory.setHost(ConfigUtil.getHost());
					factory.setPort(Integer.parseInt(ConfigUtil.getPort()));
					factory.setUsername(ConfigUtil.getUserName());
					factory.setPassword(ConfigUtil.getPassword());
					logger.debug("初始化rabbitmq连接工厂:" + factory.getHost());
					channel = factory.newConnection().createChannel();
				}
			}
		}
		return channel;
	}

}
