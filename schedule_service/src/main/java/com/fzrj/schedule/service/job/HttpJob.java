package com.fzrj.schedule.service.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.fzrj.schedule.bean.jobdetail.http.HttpReqBean;
import com.fzrj.schedule.constant.HttpConstant;
import com.fzrj.schedule.service.JobMapConverter;
import com.fzrj.schedule.service.SpringContextUtil;
import com.fzrj.schedule.service.httputil.HttpFactory;

/**
 * @className:com.fzrj.schedule.service.job.HttpJob
 * @description:发送Http请求的定时任务
 * @version:v1.0.0
 * @date:2016年12月16日 上午10:01:46
 * @author:WangHao
 */
public class HttpJob implements Job
{
	private static Logger logger = LogManager.getLogger(HttpJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		logger.info("开始执行Http定时任务");
		JobDataMap data = context.getMergedJobDataMap();
		HttpReqBean httpReqBean = JobMapConverter.getHttpJob(data);
		String rsp = HttpFactory.sendHttpReq(httpReqBean);
		if (HttpConstant.RspBody.NO_RSP_ERR.equals(rsp))
		{
			// TODO 返回结果为空，等待下次自动重试
		}
		else
		{
			// 执行成功,删除定时器
			Scheduler scheduler = (Scheduler) SpringContextUtil.getBean("scheduler");
			try
			{
				scheduler.pauseTrigger(context.getTrigger().getKey());
				scheduler.unscheduleJob(context.getTrigger().getKey());
				scheduler.deleteJob(context.getJobDetail().getKey());
			}
			catch (SchedulerException e)
			{
				logger.error("执行成功,删除定时器异常", e);
			}
		}
		logger.info("执行Http定时任务结束");
	}
}
