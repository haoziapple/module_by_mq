package com.fzrj.schedule.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @className:com.fzrj.schedule.client.util.ConfigUtil
 * @description:读取配置工具类
 * @version:v1.0.0
 * @date:2016年12月23日 下午6:02:25
 * @author:WangHao
 */
public class ConfigUtil
{
	private static Logger logger = LogManager.getLogger(MqConnectionFactory.class);

	private static Properties properties = null;
	// 交换机名前缀
	private static final String EXCHANGE_PREFIX = "exchange";
	// 队列前缀
	private static final String QUEUE_PREFIX = "queue";
	// 定时器交换机前缀
	private static final String SCH_EXCHANGE_PRE = "schedule_exchange";
	// 添加cron类Http任务前缀
	private static final String ADD_HTTP_CRON_JOB_PRE = "addHttpCronJob";
	// 添加一般类Http任务前缀
	private static final String ADD_HTTP_SIMPLE_JOB_PRE = "addHttpSimpleJob";
	// 添加cron类MQ任务前缀
	private static final String ADD_MQ_CRON_JOB_PRE = "addMqCronJob";
	// 添加一般类MQ任务前缀
	private static final String ADD_MQ_SIMPLE_JOB_PRE = "addMqSimpleJob";
	// 删除任务前缀
	private static final String DELETE_JOB_PRE = "deleteJob";

	// mq服务器地址
	private static String host;
	// mq服务器端口号
	private static String port;
	// mq用户名
	private static String userName;
	// mq密码
	private static String password;
	// mq环境
	private static String env;
	// 定时任务服务器名
	private static String scheduleServer;
	// 客户端平台名
	private static String platName;
	// 客户端服务名
	private static String platServer;

	static
	{
		InputStream is = MqConnectionFactory.class.getResourceAsStream("/mq.properties");
		try
		{
			properties = new Properties();
			properties.load(is);
			host = properties.getProperty("rabbitmq.host");
			port = properties.getProperty("rabbitmq.port");
			userName = properties.getProperty("rabbitmq.username");
			password = properties.getProperty("rabbitmq.password");
			env = properties.getProperty("rabbitmq.env");
			scheduleServer = properties.getProperty("rabbitmq.schedule.server");
			platName = properties.getProperty("rabbitmq.client.platname");
			platServer = properties.getProperty("rabbitmq.client.platserver");
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

	public static String getHost()
	{
		return host;
	}

	public static String getPort()
	{
		return port;
	}

	public static String getUserName()
	{
		return userName;
	}

	public static String getPassword()
	{
		return password;
	}

	public static String getPlatName()
	{
		return platName;
	}

	public static String getPlatServer()
	{
		return platServer;
	}

	// 获取平台专用交换机名
	public static String getPlatExchange()
	{
		return EXCHANGE_PREFIX + "_" + platName + "_" + env;
	}

	// 获取平台某个服务的专用队列名
	public static String getPlatQueue()
	{
		return QUEUE_PREFIX + "_" + platServer + "_" + env;
	}

	// 获取定时器交换机名
	public static String getSchExchange()
	{
		return SCH_EXCHANGE_PRE + "_" + env;
	}

	// 获取cron类http任务路由键
	public static String getHttpCronKey()
	{
		return ADD_HTTP_CRON_JOB_PRE + "_" + env + "_" + scheduleServer;
	}

	// 获取一般类http任务路由键
	public static String getHttpSimpleKey()
	{
		return ADD_HTTP_SIMPLE_JOB_PRE + "_" + env + "_" + scheduleServer;
	}

	// 获取cron类MQ任务路由键
	public static String getMqCronKey()
	{
		return ADD_MQ_CRON_JOB_PRE + "_" + env + "_" + scheduleServer;
	}

	// 获取一般类MQ任务路由键
	public static String getMqSimpleKey()
	{
		return ADD_MQ_SIMPLE_JOB_PRE + "_" + env + "_" + scheduleServer;
	}

	// 获取删除任务路由键
	public static String getDelJobKey()
	{
		return DELETE_JOB_PRE + "_" + env + "_" + scheduleServer;
	}

}
