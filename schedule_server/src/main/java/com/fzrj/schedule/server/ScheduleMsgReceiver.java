package com.fzrj.schedule.server;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.fzrj.schedule.bean.http.SimpleHttpReq;
import com.fzrj.schedule.bean.job.JobBean;
import com.fzrj.schedule.service.schedule.ScheduleService;
import com.rabbitmq.client.Channel;

/**
 * @className:com.fzrj.schedule.server.ScheduleMsgReceiver
 * @description:定时器消息接收器(测试用)
 * @version:v1.0.0
 * @date:2016年12月16日 下午5:02:59
 * @author:WangHao
 */
public class ScheduleMsgReceiver implements ChannelAwareMessageListener
{
	private static Logger logger = LogManager.getLogger(ScheduleMsgReceiver.class);

	@Autowired
	private ScheduleService scheduleService;

	@Override
	public void onMessage(Message message, Channel channel) throws Exception
	{
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		String msg = new String(message.getBody(), "UTF-8");
		logger.info("收到消息:" + msg);
		if ("exception test1".equals(msg))
		{
			throw new Exception("exception test");
		}

		if ("shcedule高并发测试".equals(msg))
		{
			Date startTime = new Date();
			System.out.println("开始" + startTime);
			startTime.setTime(startTime.getTime() + 30 * 1000);
			long starTime = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++)
			{
				JobBean jobBean = new JobBean(Integer.toString(i), "platName");
				SimpleHttpReq simpleHttpReq = new SimpleHttpReq("http://localhost:8080/aquatic_order_service/index.jsp",
						"", startTime);
				simpleHttpReq.setReqMethod("GET");
				scheduleService.addHttpSimpleJob(simpleHttpReq, jobBean, false);
			}
			long endTime = System.currentTimeMillis();
			System.out.println("shcedule高并发测试,运行时间:" + (endTime - starTime));
		}

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

}
