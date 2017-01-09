package com.fzrj.schedule.service.job;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;
import com.fzrj.schedule.service.JobMapConverter;
import com.fzrj.schedule.service.SpringContextUtil;
import com.fzrj.schedule.service.mqmsg.MqMsgService;

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
		logger.debug("开始执行MQ定时任务 JobDataMap data:" + Arrays.asList(data.getKeys()));
		MqMsgBean mqMsgBean = JobMapConverter.getMqJob(data);
		// 发送MQ消息
		MqMsgService mqMsgService = (MqMsgService) SpringContextUtil.getBean("mqMsgServiceImpl");
		mqMsgService.sendMqMsg(mqMsgBean);
	}
}
