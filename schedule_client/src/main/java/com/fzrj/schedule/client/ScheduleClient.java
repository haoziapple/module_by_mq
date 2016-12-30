package com.fzrj.schedule.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzrj.schedule.bean.job.JobBean;
import com.fzrj.schedule.bean.mqctrl.AddHttpCronBean;
import com.fzrj.schedule.bean.mqctrl.AddHttpSimpleBean;
import com.fzrj.schedule.bean.mqctrl.AddMqCronBean;
import com.fzrj.schedule.bean.mqctrl.AddMqSimpleBean;
import com.fzrj.schedule.client.util.ConfigUtil;
import com.fzrj.schedule.client.util.MqConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * @className:com.fzrj.schedule.client.ScheduleClient
 * @description:定时任务消息发送类
 * @version:v1.0.0
 * @date:2016年12月19日 下午8:20:15
 * @author:WangHao
 */
public class ScheduleClient
{
	/**
	 * 添加Cron类型的Http定时任务
	 */
	public static void addHttpCronJob(AddHttpCronBean addHttpCronBean)
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		try
		{
			String reqStr = mapper.writeValueAsString(addHttpCronBean);
			// 发送消息
			Connection connection = MqConnectionFactory.getInstance().newConnection();
			Channel channel = connection.createChannel();
			channel.basicPublish(ConfigUtil.getSchExchange(), ConfigUtil.getHttpCronKey(),
					MessageProperties.PERSISTENT_TEXT_PLAIN, reqStr.getBytes("UTF-8"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 添加一般类型的Http定时任务
	 */
	public static void addHttpSimpleJob(AddHttpSimpleBean addhttpSimpleBean)
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		try
		{
			String reqStr = mapper.writeValueAsString(addhttpSimpleBean);
			// 发送消息
			Connection connection = MqConnectionFactory.getInstance().newConnection();
			Channel channel = connection.createChannel();
			channel.basicPublish(ConfigUtil.getSchExchange(), ConfigUtil.getHttpSimpleKey(),
					MessageProperties.PERSISTENT_TEXT_PLAIN, reqStr.getBytes("UTF-8"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 添加cron类型的mq定时任务
	 */
	public static void addMqCronJob(AddMqCronBean addMqCronBean)
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		try
		{
			String reqStr = mapper.writeValueAsString(addMqCronBean);
			// 发送消息
			Connection connection = MqConnectionFactory.getInstance().newConnection();
			Channel channel = connection.createChannel();
			channel.basicPublish(ConfigUtil.getSchExchange(), ConfigUtil.getMqCronKey(),
					MessageProperties.PERSISTENT_TEXT_PLAIN, reqStr.getBytes("UTF-8"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 添加一般类型的mq定时任务
	 */
	public static void addMqSimpleJob(AddMqSimpleBean addMqSimpleBean)
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		try
		{
			String reqStr = mapper.writeValueAsString(addMqSimpleBean);
			// 发送消息
			Connection connection = MqConnectionFactory.getInstance().newConnection();
			Channel channel = connection.createChannel();
			channel.basicPublish(ConfigUtil.getSchExchange(), ConfigUtil.getMqSimpleKey(),
					MessageProperties.PERSISTENT_TEXT_PLAIN, reqStr.getBytes("UTF-8"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 删除定时任务
	 */
	public static void deleteJob(JobBean jobBean)
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		try
		{
			String reqStr = mapper.writeValueAsString(jobBean);
			// 发送消息
			Connection connection = MqConnectionFactory.getInstance().newConnection();
			Channel channel = connection.createChannel();
			channel.basicPublish(ConfigUtil.getSchExchange(), ConfigUtil.getDelJobKey(),
					MessageProperties.PERSISTENT_TEXT_PLAIN, reqStr.getBytes("UTF-8"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
