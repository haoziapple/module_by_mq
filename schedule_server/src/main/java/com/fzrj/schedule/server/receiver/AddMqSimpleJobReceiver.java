package com.fzrj.schedule.server.receiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzrj.schedule.bean.mqctrl.AddMqSimpleBean;
import com.fzrj.schedule.service.schedule.ScheduleService;
import com.rabbitmq.client.Channel;

/**
 * @className:com.fzrj.schedule.server.receiver.AddMqSimpleJobReceiver
 * @description:添加一般类型Mq定时任务消息接收类
 * @version:v1.0.0
 * @date:2016年12月22日 下午8:35:32
 * @author:WangHao
 */
public class AddMqSimpleJobReceiver implements ChannelAwareMessageListener
{
	private static Logger logger = LogManager.getLogger(AddMqSimpleJobReceiver.class);

	@Autowired
	private ScheduleService scheduleService;

	@Override
	public void onMessage(Message message, Channel channel) throws Exception
	{
		AddMqSimpleBean addMqSimpleBean = null;
		try
		{
			String msg = new String(message.getBody(), "UTF-8");
			logger.debug("添加一般类型Http定时任务消息接收msg:" + msg);
			ObjectMapper mapper = new ObjectMapper(); // 转换器
			addMqSimpleBean = mapper.readValue(msg, AddMqSimpleBean.class);
		}
		catch (Exception e)
		{
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			logger.error("添加一般类型Mq定时任务,请求非法", e);
			return;
		}

		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		scheduleService.addMqSimpleJob(addMqSimpleBean.getMqMsgBean(), addMqSimpleBean.getSimpleJobBean(),
				addMqSimpleBean.isOverWrite());
	}
}
