package com.fzrj.schedule.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzrj.schedule.bean.mqctrl.AddMqCronBean;
import com.fzrj.schedule.service.schedule.ScheduleService;
import com.rabbitmq.client.Channel;

/**
 * @className:com.fzrj.schedule.server.AddMqCronJobReceiver
 * @description:添加Cron类型Mq定时任务消息接收类
 * @version:v1.0.0
 * @date:2016年12月22日 下午8:32:55
 * @author:WangHao
 */
public class AddMqCronJobReceiver implements ChannelAwareMessageListener
{
	private static Logger logger = LogManager.getLogger(AddMqCronJobReceiver.class);

	@Autowired
	private ScheduleService scheduleService;

	@Override
	public void onMessage(Message message, Channel channel) throws Exception
	{
		AddMqCronBean addMqCronBean = null;
		try
		{
			String msg = new String(message.getBody(), "UTF-8");
			logger.debug("添加Cron类型Http定时任务消息接收msg:" + msg);
			ObjectMapper mapper = new ObjectMapper(); // 转换器
			addMqCronBean = mapper.readValue(msg, AddMqCronBean.class);
		}
		catch (Exception e)
		{
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			logger.error("添加Cron类型Mq定时任务,请求非法", e);
			return;
		}

		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		scheduleService.addMqCronJob(addMqCronBean.getMqMsgBean(), addMqCronBean.getCronJobBean(),
				addMqCronBean.isOverWrite());
	}
}
