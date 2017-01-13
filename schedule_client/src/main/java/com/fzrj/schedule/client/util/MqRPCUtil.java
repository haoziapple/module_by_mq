package com.fzrj.schedule.client.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @className:com.fzrj.schedule.client.util.MqRPCUtil
 * @description:使用MQ进行RPC调用工具类
 * @version:v1.0.0
 * @date:2017年1月10日 上午11:24:01
 * @author:WangHao
 */
public class MqRPCUtil
{
	// 将来有可能使用自带的RpcClient改写
	// RpcClient rpc = new RpcClient(channel, exchangeName, routingKey);
	// byte[] primitiveCall(byte[] message);
	// String stringCall(String message)
	// Map mapCall(Map message)
	// Map mapCall(Object[] keyValuePairs)

	private static Logger logger = LogManager.getLogger(MqRPCUtil.class);
	// 发送请求与回调通道
	private Channel channel;
	// 回调队列
	private String replyQueueName;
	private QueueingConsumer consumer;

	public MqRPCUtil() throws IOException, TimeoutException
	{
		// 创建新的channel用于RPC调用
		channel = MqChannelFactory.createChannel();
		// 为每一个客户端获取一个随机的回调队列
		replyQueueName = channel.queueDeclare().getQueue();
		// 绑定队列
		channel.queueBind(replyQueueName, ConfigUtil.getPlatExchange(), replyQueueName);
		// 为每一个客户端创建一个消费者（用于监听回调队列，获取结果）
		consumer = new QueueingConsumer(channel);
		// 消费者与队列关联,回调消息时自动确认的
		channel.basicConsume(replyQueueName, true, consumer);
	}

	/**
	 * @Description:远程调用方法
	 * @param mqMsgBean
	 * @return
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2017年1月10日 下午2:57:47
	 */
	public String call(MqMsgBean mqMsgBean)
	{
		String response = null;
		String corrId = java.util.UUID.randomUUID().toString();

		// 设置replyTo和correlationId属性值
		BasicProperties props = new BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();

		// 发送消息到rpc_queue队列
		try
		{
			channel.basicPublish(mqMsgBean.getExchangeName(), ConfigUtil.RPC_PRE + "_" + mqMsgBean.getRoutingKey(),
					props, mqMsgBean.getMsgBody().getBytes());
			while (true)
			{
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				if (delivery.getProperties().getCorrelationId().equals(corrId))
				{
					response = new String(delivery.getBody(), "UTF-8");
					break;
				}
			}
		}
		catch (Exception e)
		{
			logger.error("远程MQ调用方法异常", e);
		}
		return response;
	}

	/**
	 * @Description:删除RPC队列与通道
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2017年1月13日 上午10:17:58
	 */
	public void cleanRPCChannel()
	{
		try
		{
			// 删除回调队列
			channel.queueDelete(replyQueueName);
			channel.close();
		}
		catch (Exception e)
		{
			logger.error("删除RPC回调队列异常", e);
		}
	}
}
