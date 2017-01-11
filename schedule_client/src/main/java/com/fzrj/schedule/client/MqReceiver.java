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
import com.rabbitmq.client.AMQP.BasicProperties;
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

	private static Channel RPCChannel = null;

	/**
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

	/**
	 * 启动RPC调用消息接收器
	 */
	public static void startRPCConsumer()
	{
		try
		{
			RPCChannel = MqConnectionFactory.getRPCInstance();
			// 同时只会接收一条未处理的消息
			// 消息未处理完之前，服务器不会再向该消费者投递消息
			RPCChannel.basicQos(1);
			// 声明队列
			RPCChannel.queueDeclare(ConfigUtil.getRPCPlatQueue(), true, false, false, null);
			// 声明交换机
			RPCChannel.exchangeDeclare(ConfigUtil.getPlatExchange(), "direct");
			// 绑定队列
			RPCChannel.queueBind(ConfigUtil.getRPCPlatQueue(), ConfigUtil.getPlatExchange(),
					ConfigUtil.getRPCPlatQueue());

			logger.debug("等待RPC调用请求");
			Consumer consumer2 = new DefaultConsumer(RPCChannel)
			{
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException
				{
					String message = new String(body, "UTF-8");
					logger.debug("mq定时任务接收消息" + message);
					// 获取请求中的correlationId属性值，并将其设置到结果消息的correlationId属性中
					BasicProperties props = properties;
					BasicProperties replyProps = new BasicProperties.Builder().correlationId(props.getCorrelationId())
							.build();
					// 获取回调队列名字
					String callQueueName = props.getReplyTo();
					String response = "";

					try
					{
						// 根据消息调用相应的Spring中bean的方法
						SpringJobBean springJobBean = JsonUtil.convertStringToObj(message, SpringJobBean.class);
						response = SpringJobExecutor.RPCInvokeBeanMethod(springJobBean);
					}
					catch (Exception e)
					{
						logger.error("mq定时任务执行异常", e);
					}
					finally
					{
						// 先发送回调结果
						RPCChannel.basicPublish(ConfigUtil.getPlatExchange(), callQueueName, replyProps,
								response.getBytes());
						// 确认接收消息
						RPCChannel.basicAck(envelope.getDeliveryTag(), false);
					}
				}
			};
			RPCChannel.basicConsume(ConfigUtil.getRPCPlatQueue(), false, consumer2);
			logger.debug("启动RPC调用消息接收器完成-exchange:" + ConfigUtil.getPlatExchange() + " queue:"
					+ ConfigUtil.getRPCPlatQueue());
		}
		catch (Exception e)
		{
			logger.error("初始化远程RPC调用消息接收类异常", e);
		}
	}
}
