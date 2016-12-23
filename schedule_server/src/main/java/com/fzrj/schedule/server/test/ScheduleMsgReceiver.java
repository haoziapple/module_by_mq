package com.fzrj.schedule.server.test;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.fzrj.schedule.bean.job.SimpleJobBean;
import com.fzrj.schedule.bean.jobdetail.http.HttpReqBean;
import com.fzrj.schedule.service.schedule.ScheduleService;
import com.fzrj.schedule.service.schedule.TestScheuleService;
import com.rabbitmq.client.Channel;

/**
 * @className:com.fzrj.schedule.server.test.ScheduleMsgReceiver
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

	@Autowired
	private TestScheuleService testScheuleService;

	@Override
	public void onMessage(Message message, Channel channel) throws Exception
	{
		String msg = new String(message.getBody(), "UTF-8");
		logger.info("收到消息:" + msg);

		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		if ("exception test3".equals(msg))
		{
			throw new Exception("exception test");
		}

		if ("schedule简单任务测试1".equals(msg))
		{
			Date startTime = new Date();
			System.out.println("开始" + startTime);
			// 30 s 后执行
			startTime.setTime(startTime.getTime() + 30 * 1000);
			long starTime = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++)
			{
				SimpleJobBean simpleJobBean = new SimpleJobBean(Integer.toString(i), "platName", startTime);
				testScheuleService.addtestJob(simpleJobBean);
			}
			long endTime = System.currentTimeMillis();
			System.out.println("shcedule高并发测试,运行时间:" + (endTime - starTime));
		}

		if ("shcedule高并发测试".equals(msg))
		{
			Date startTime = new Date();
			System.out.println("开始" + startTime);
			// 30 s 后执行
			startTime.setTime(startTime.getTime() + 30 * 1000);
			long starTime = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++)
			{
				SimpleJobBean simpleJobBean = new SimpleJobBean(Integer.toString(i), "platName", startTime);
				HttpReqBean httpReqBean = new HttpReqBean("http://localhost:8080/aquatic_order_service/index.jsp", "");
				httpReqBean.setReqMethod("GET");
				scheduleService.addHttpSimpleJob(httpReqBean, simpleJobBean, false);
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
