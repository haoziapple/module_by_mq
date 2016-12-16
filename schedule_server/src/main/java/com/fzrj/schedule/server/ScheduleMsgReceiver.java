package com.fzrj.schedule.server;

import java.io.UnsupportedEncodingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.fzrj.schedule.service.schedule.ScheduleService;
import com.rabbitmq.client.Channel;

/**
 * @className:com.fzrj.schedule.server.ScheduleMsgReceiver
 * @description:定时器消息接收器
 * @version:v1.0.0
 * @date:2016年12月16日 下午5:02:59
 * @author:WangHao
 */
public class ScheduleMsgReceiver implements MessageListener, ChannelAwareMessageListener
{
	private static Logger logger = LogManager.getLogger(ScheduleMsgReceiver.class);

	@Autowired
	private ScheduleService scheduleService;

	@Override
	public void onMessage(Message message)
	{
		try
		{
			logger.debug("定时器消息接收器" + new String(message.getBody(), "UTF-8"));
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message message, Channel channel) throws Exception
	{
		try
		{
			String msg = new String(message.getBody(), "UTF-8");
			logger.info("定时器消息接收器(需手动ack)" + new String(message.getBody(), "UTF-8"));
			// false只确认当前一个消息收到，true确认所有consumer获得的消息
			if ("1".equals(msg))
			{
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			}
			// 拒绝当前消息，并将消息重新入队
			if ("2".equals(msg))
			{
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
			}
			// 拒绝消息并将消息重新入队
			if ("3".equals(msg))
			{
				channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
			}
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		finally
		{

		}
	}
}
