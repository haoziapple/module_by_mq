package com.fzrj.schedule.client;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzrj.schedule.bean.job.JobBean;
import com.fzrj.schedule.bean.mqctrl.AddHttpCronBean;
import com.fzrj.schedule.bean.mqctrl.AddHttpSimpleBean;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * @className:com.fzrj.schedule.client.ScheduleMsg
 * @description:定时任务消息发送类
 * @version:v1.0.0
 * @date:2016年12月19日 下午8:20:15
 * @author:WangHao
 */
public class ScheduleMsg
{
	private static String exchangeName = "schedule_exchange_dev";

	private static final String ADD_HTTP_CRON_JOB_KEY = "addHttpCronJob";

	private static final String ADD_HTTP_SIMPLE_JOB_KEY = "addHttpSimpleJob";

	private static final String DELETE_JOB_KEY = "deleteJob";

	/**
	 * 添加Cron类型的Http定时任务
	 */
	public static void addHttpCronJob(AddHttpCronBean addHttpCronBean) throws IOException, TimeoutException
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		String reqStr = mapper.writeValueAsString(addHttpCronBean);
		// 发送消息
		Connection connection = MqConnectionFactory.getInstance().newConnection();
		Channel channel = connection.createChannel();
		channel.basicPublish(exchangeName, ADD_HTTP_CRON_JOB_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,
				reqStr.getBytes("UTF-8"));
	}

	/**
	 * 添加一般类型的Http定时任务
	 */
	public static void addHttpSimpleJob(AddHttpSimpleBean addhttpSimpleBean) throws IOException, TimeoutException
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		String reqStr = mapper.writeValueAsString(addhttpSimpleBean);
		// 发送消息
		Connection connection = MqConnectionFactory.getInstance().newConnection();
		Channel channel = connection.createChannel();
		channel.basicPublish(exchangeName, ADD_HTTP_SIMPLE_JOB_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,
				reqStr.getBytes("UTF-8"));
	}

	/**
	 * 删除定时任务
	 */
	public static void deleteJob(JobBean jobBean) throws IOException, TimeoutException
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		String reqStr = mapper.writeValueAsString(jobBean);
		// 发送消息
		Connection connection = MqConnectionFactory.getInstance().newConnection();
		Channel channel = connection.createChannel();
		channel.basicPublish(exchangeName, DELETE_JOB_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,
				reqStr.getBytes("UTF-8"));
	}
}
