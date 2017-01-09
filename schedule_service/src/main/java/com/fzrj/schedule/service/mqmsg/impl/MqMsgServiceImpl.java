package com.fzrj.schedule.service.mqmsg.impl;

import java.io.UnsupportedEncodingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;
import com.fzrj.schedule.service.mqmsg.MqMsgService;

/**
 * @className:com.fzrj.schedule.service.mqmsg.impl.MqMsgServiceImpl
 * @description:Mq消息Service实现类
 * @version:v1.0.0
 * @date:2017年1月9日 下午2:00:02
 * @author:WangHao
 */
@Service
public class MqMsgServiceImpl implements MqMsgService
{
	private static Logger logger = LogManager.getLogger(MqMsgServiceImpl.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void sendMqMsg(MqMsgBean mqMsgBean)
	{
		Message message = null;
		try
		{
			message = new Message(mqMsgBean.getMsgBody().getBytes("UTF-8"), new MessageProperties());
		}
		catch (UnsupportedEncodingException e)
		{
			logger.error("获取定时消息异常", e);
		}

		logger.debug("发送MQ定时信息msg:" + mqMsgBean);
		rabbitTemplate.send(mqMsgBean.getExchangeName(), mqMsgBean.getRoutingKey(), message);
	}
}
