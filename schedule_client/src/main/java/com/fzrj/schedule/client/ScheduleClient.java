package com.fzrj.schedule.client;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzrj.schedule.bean.job.JobBean;
import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;
import com.fzrj.schedule.bean.mqctrl.AddHttpCronBean;
import com.fzrj.schedule.bean.mqctrl.AddHttpSimpleBean;
import com.fzrj.schedule.bean.mqctrl.AddMqCronBean;
import com.fzrj.schedule.bean.mqctrl.AddMqSimpleBean;
import com.fzrj.schedule.client.util.ConfigUtil;
import com.fzrj.schedule.client.util.MqChannelFactory;
import com.fzrj.schedule.client.util.MqRPCUtil;
import com.rabbitmq.client.Channel;
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
	private static Logger logger = LogManager.getLogger(ScheduleClient.class);

	/**
	 * 添加Cron类型的Http定时任务
	 * 
	 * @throws IOException
	 */
	public static void addHttpCronJob(AddHttpCronBean addHttpCronBean)
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		Channel channel = null;// MQ通道
		try
		{
			String reqStr = mapper.writeValueAsString(addHttpCronBean);
			// 发送消息
			channel = MqChannelFactory.createChannel();
			channel.basicPublish(ConfigUtil.getSchExchange(), ConfigUtil.getHttpCronKey(),
					MessageProperties.PERSISTENT_TEXT_PLAIN, reqStr.getBytes("UTF-8"));
		}
		catch (Exception e)
		{
			logger.error("添加Cron类型的Http定时任务异常", e);
		}
		finally
		{
			// 关闭通道
			MqChannelFactory.CloseChannel(channel);
		}
	}

	/**
	 * 添加一般类型的Http定时任务
	 */
	public static void addHttpSimpleJob(AddHttpSimpleBean addhttpSimpleBean)
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		Channel channel = null;// MQ通道
		try
		{
			String reqStr = mapper.writeValueAsString(addhttpSimpleBean);
			// 发送消息
			channel = MqChannelFactory.createChannel();
			channel.basicPublish(ConfigUtil.getSchExchange(), ConfigUtil.getHttpSimpleKey(),
					MessageProperties.PERSISTENT_TEXT_PLAIN, reqStr.getBytes("UTF-8"));
		}
		catch (Exception e)
		{
			logger.error("添加一般类型的Http定时任务异常", e);
		}
		finally
		{
			// 关闭通道
			MqChannelFactory.CloseChannel(channel);
		}
	}

	/**
	 * 添加cron类型的mq定时任务
	 */
	public static void addMqCronJob(AddMqCronBean addMqCronBean)
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		Channel channel = null;// MQ通道
		try
		{
			String reqStr = mapper.writeValueAsString(addMqCronBean);
			// 发送消息
			channel = MqChannelFactory.createChannel();
			channel.basicPublish(ConfigUtil.getSchExchange(), ConfigUtil.getMqCronKey(),
					MessageProperties.PERSISTENT_TEXT_PLAIN, reqStr.getBytes("UTF-8"));
		}
		catch (Exception e)
		{
			logger.error("添加cron类型的mq定时任务异常", e);
		}
		finally
		{
			// 关闭通道
			MqChannelFactory.CloseChannel(channel);
		}
	}

	/**
	 * 添加一般类型的mq定时任务
	 */
	public static void addMqSimpleJob(AddMqSimpleBean addMqSimpleBean)
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		Channel channel = null;// MQ通道
		try
		{
			String reqStr = mapper.writeValueAsString(addMqSimpleBean);
			// 发送消息
			channel = MqChannelFactory.createChannel();
			channel.basicPublish(ConfigUtil.getSchExchange(), ConfigUtil.getMqSimpleKey(),
					MessageProperties.PERSISTENT_TEXT_PLAIN, reqStr.getBytes("UTF-8"));
		}
		catch (Exception e)
		{
			logger.error("添加一般类型的mq定时任务异常", e);
		}
		finally
		{
			// 关闭通道
			MqChannelFactory.CloseChannel(channel);
		}
	}

	/**
	 * 删除定时任务
	 */
	public static void deleteJob(JobBean jobBean)
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		Channel channel = null;// MQ通道
		try
		{
			String reqStr = mapper.writeValueAsString(jobBean);
			// 发送消息
			channel = MqChannelFactory.createChannel();
			channel.basicPublish(ConfigUtil.getSchExchange(), ConfigUtil.getDelJobKey(),
					MessageProperties.PERSISTENT_TEXT_PLAIN, reqStr.getBytes("UTF-8"));
		}
		catch (Exception e)
		{
			logger.error("删除定时任务异常", e);
		}
		finally
		{
			// 关闭通道
			MqChannelFactory.CloseChannel(channel);
		}
	}

	/**
	 * 通过调度器转发消息，立即调用某个服务的方法
	 */
	public static void routeMsg(MqMsgBean mqMsgBean)
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		Channel channel = null;// MQ通道
		try
		{
			String reqStr = mapper.writeValueAsString(mqMsgBean);
			// 发送消息
			channel = MqChannelFactory.createChannel();
			channel.basicPublish(ConfigUtil.getSchExchange(), ConfigUtil.getRouteMsgKey(),
					MessageProperties.PERSISTENT_TEXT_PLAIN, reqStr.getBytes("UTF-8"));
		}
		catch (Exception e)
		{
			logger.error("通过调度器转发消息，立即调用某个服务的方法异常", e);
		}
		finally
		{
			// 关闭通道
			MqChannelFactory.CloseChannel(channel);
		}
	}

	/**
	 * @Description:使用mq进行rpc远程调用,不再通过调度器转发
	 * @param mqMsgBean
	 * @return
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2017年1月10日 上午11:10:12
	 */
	public static String mqRPC(MqMsgBean mqMsgBean)
	{
		String result = null;
		MqRPCUtil mqRPCUtil = null;
		try
		{
			mqRPCUtil = new MqRPCUtil();
			// 获取RPC调用结果
			result = mqRPCUtil.call(mqMsgBean);
			// 清理RPC通道
			mqRPCUtil.cleanRPCChannel();
		}
		catch (Exception e)
		{
			logger.error("初始化mqRPC工具类异常", e);
		}

		return result;
	}
}
