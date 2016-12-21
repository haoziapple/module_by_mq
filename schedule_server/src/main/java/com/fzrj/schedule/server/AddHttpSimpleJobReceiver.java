package com.fzrj.schedule.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzrj.schedule.bean.mqctrl.AddHttpSimpleBean;
import com.fzrj.schedule.service.schedule.ScheduleService;
import com.rabbitmq.client.Channel;

/**
 * @className:com.fzrj.schedule.server.AddHttpSimpleJobReceiver
 * @description:添加一般类型Http定时任务消息接收类
 * @version:v1.0.0
 * @date:2016年12月19日 下午7:49:38
 * @author:WangHao
 */
public class AddHttpSimpleJobReceiver implements ChannelAwareMessageListener
{
	private static Logger logger = LogManager.getLogger(AddHttpCronJobReceiver.class);

	@Autowired
	private ScheduleService scheduleService;

	@Override
	public void onMessage(Message message, Channel channel) throws Exception
	{
		AddHttpSimpleBean addhttpSimpleBean = null;
		try
		{
			String msg = new String(message.getBody(), "UTF-8");
			logger.debug("添加一般类型Http定时任务消息接收msg:" + msg);
			ObjectMapper mapper = new ObjectMapper(); // 转换器
			addhttpSimpleBean = mapper.readValue(msg, AddHttpSimpleBean.class);
		}
		catch (Exception e)
		{
			logger.error("添加一般类型Http定时任务,请求非法", e);
			return;
		}
		finally
		{
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}
		// 处理定时器逻辑的抛出异常会被MsgRecoverer处理
		scheduleService.addHttpSimpleJob(addhttpSimpleBean.getHttpReqBean(), addhttpSimpleBean.getSimpleJobBean(),
				addhttpSimpleBean.isOverWrite());
	}

}
