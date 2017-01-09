package com.fzrj.schedule.server.receiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;
import com.fzrj.schedule.service.mqmsg.MqMsgService;
import com.rabbitmq.client.Channel;

/**
 * @className:com.fzrj.schedule.server.receiver.RouteMsgReceiver
 * @description:转发MQ消息接收类
 * @version:v1.0.0
 * @date:2017年1月9日 下午2:40:55
 * @author:WangHao
 */
public class RouteMsgReceiver implements ChannelAwareMessageListener
{
	private static Logger logger = LogManager.getLogger(RouteMsgReceiver.class);
	@Autowired
	private MqMsgService mqMsgService;

	/**
	 * 收到消息后立即进行转发
	 */
	@Override
	public void onMessage(Message message, Channel channel) throws Exception
	{
		MqMsgBean mqMsgBean = null;
		try
		{
			String msg = new String(message.getBody(), "UTF-8");
			logger.debug("转发MQ消息msg:" + msg);
			ObjectMapper mapper = new ObjectMapper(); // 转换器
			mqMsgBean = mapper.readValue(msg, MqMsgBean.class);
		}
		catch (Exception e)
		{
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			logger.error("转发MQ消息msg,请求非法", e);
			return;
		}

		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		mqMsgService.sendMqMsg(mqMsgBean);
	}

}
