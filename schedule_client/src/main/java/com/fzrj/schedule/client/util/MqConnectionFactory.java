package com.fzrj.schedule.client.util;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rabbitmq.client.Connection;
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

	private static ConnectionFactory factory = new ConnectionFactory();
	// MQ连接实例
	private static Connection connection = null;
	private static Object lock = new Object();

	static
	{

		factory.setHost(ConfigUtil.getHost());
		factory.setPort(Integer.parseInt(ConfigUtil.getPort()));
		factory.setUsername(ConfigUtil.getUserName());
		factory.setPassword(ConfigUtil.getPassword());
		// 设置连接自动重连
		factory.setAutomaticRecoveryEnabled(true);
		logger.debug("初始化rabbitmq连接工厂:" + factory.getHost());

	}

	/**
	 * @Description:获取MQ连接实例
	 * @return
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2017年1月6日 上午9:22:14
	 */
	public static Connection getInstance()
	{
		if (connection == null)
		{
			synchronized (lock)
			{
				if (connection == null)
				{
					try
					{
						connection = factory.newConnection();
					}
					catch (Exception e)
					{
						logger.error("获取MQ连接异常", e);
					}
				}
			}
		}
		return connection;
	}

	/**
	 * @Description:关闭MQ连接
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2017年1月6日 上午9:21:52
	 */
	public static void closeConnection()
	{
		if (connection != null)
		{
			try
			{
				connection.close();
			}
			catch (IOException e)
			{
				logger.error("关闭MQ连接异常", e);
			}
		}
	}

}
