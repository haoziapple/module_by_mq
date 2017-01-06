package com.fzrj.schedule.client;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fzrj.schedule.client.bean.SpringJobBean;
import com.fzrj.schedule.client.bean.SpringJobExecutor;
import com.fzrj.schedule.client.util.ConfigUtil;
import com.fzrj.schedule.client.util.JsonUtil;
import com.fzrj.schedule.client.util.MqConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * @className:com.fzrj.schedule.client.MqReceiver
 * @description:队列消息接收类(可起动多个接收类)
 * @version:v1.0.0
 * @date:2016年12月23日 下午1:25:51
 * @author:WangHao
 */
public class MqReceiver
{
	private static Logger logger = LogManager.getLogger(MqReceiver.class);

	private static Channel channel = null;

	/*
	 * 启动消息接收器
	 */
	public static void startConsumer()
	{
		try
		{
			channel = MqConnectionFactory.getInstance();
			// 同时只会接收一条未处理的消息
			// 消息未处理完之前，服务器不会再向该消费者投递消息
			channel.basicQos(1);
			// 声明队列
			channel.queueDeclare(ConfigUtil.getPlatQueue(), true, false, false, null);
			// 声明交换机
			channel.exchangeDeclare(ConfigUtil.getPlatExchange(), "direct");
			// 绑定队列
			channel.queueBind(ConfigUtil.getPlatQueue(), ConfigUtil.getPlatExchange(), ConfigUtil.getPlatQueue());

			Consumer consumer = new DefaultConsumer(channel)
			{
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException
				{
					String message = new String(body, "UTF-8");
					logger.debug("mq定时任务接收消息" + message);

					try
					{
						// 根据消息调用相应的Spring中bean的方法
						SpringJobBean springJobBean = JsonUtil.convertStringToObj(message, SpringJobBean.class);
						SpringJobExecutor.invokeBeanMethod(springJobBean);
					}
					catch (Exception e)
					{
						logger.error("mq定时任务执行异常", e);
					}
					finally
					{
						// 确认接收消息
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
				}
			};
			channel.basicConsume(ConfigUtil.getPlatQueue(), false, consumer);
			logger.debug("消息监听器初始化完成-exchange:" + ConfigUtil.getPlatExchange() + " queue:" + ConfigUtil.getPlatQueue());
		}
		catch (Exception e)
		{
			logger.error("初始化队列消息接收类异常", e);
		}
	}
}
