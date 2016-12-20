package com.fzrj.schedule.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rabbitmq.client.ConnectionFactory;

/**
 * @className:com.fzrj.schedule.client.MqConnectionFactory
 * @description:rabbitmq连接工厂类
 * @version:v1.0.0
 * @date:2016年12月19日 下午8:15:02
 * @author:WangHao
 */
public class MqConnectionFactory
{
	private static Logger logger = LogManager.getLogger(MqConnectionFactory.class);

	private static Properties properties = null;

	public static String HOST;

	public static String PORT;

	public static String USERNAME;

	public static String PASSWORD;

	public static String ENV;

	public static String KEY;

	static
	{
		InputStream is = MqConnectionFactory.class.getResourceAsStream("/mq.properties");
		try
		{
			properties = new Properties();
			properties.load(is);
			HOST = properties.getProperty("rabbitmq.host");
			PORT = properties.getProperty("rabbitmq.port");
			USERNAME = properties.getProperty("rabbitmq.username");
			PASSWORD = properties.getProperty("rabbitmq.password");
			ENV = properties.getProperty("rabbitmq.env");
			KEY = properties.getProperty("rabbitmq.server.key");

		}
		catch (Exception e)
		{
			logger.error("支付常量类初始化异常", e);
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private static ConnectionFactory factory = null;
	private static Object lock = new Object();

	public static ConnectionFactory getInstance()
	{
		if (factory == null)
		{
			synchronized (lock)
			{
				if (factory == null)
				{
					factory = new ConnectionFactory();
					factory.setHost(HOST);
					factory.setPort(Integer.parseInt(PORT));
					factory.setUsername(USERNAME);
					factory.setPassword(PASSWORD);
				}
			}
		}
		return factory;
	}
}
