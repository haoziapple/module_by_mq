package com.fzrj.schedule.service.job;

import java.io.UnsupportedEncodingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;
import com.fzrj.schedule.service.JobMapConverter;
import com.fzrj.schedule.service.SpringContextUtil;

/**
 * @className:com.fzrj.schedule.service.job.MqMsgJob
 * @description:消息队列定时任务类
 * @version:v1.0.0
 * @date:2016年12月22日 下午7:27:10
 * @author:WangHao
 */
public class MqMsgJob implements Job
{
	private static Logger logger = LogManager.getLogger(MqMsgJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{

		JobDataMap data = context.getMergedJobDataMap();
		logger.debug("开始执行MQ定时任务 JobDataMap data:" + data);
		MqMsgBean mqMsgBean = JobMapConverter.getMqJob(data);

		RabbitTemplate rabbitTemplate = (RabbitTemplate) SpringContextUtil.getBean("rabbitTemplate");
		Message message = null;

		try
		{
			message = new Message(mqMsgBean.getMsgBody().getBytes("UTF-8"), new MessageProperties());
		}
		catch (UnsupportedEncodingException e)
		{
			logger.error("获取定时消息异常", e);
		}

		logger.debug("发送MQ定时信息msg:" + message);
		rabbitTemplate.send(mqMsgBean.getExchangeName(), mqMsgBean.getRoutingKey(), message);

	}
}
