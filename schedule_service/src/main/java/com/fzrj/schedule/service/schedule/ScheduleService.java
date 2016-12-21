package com.fzrj.schedule.service.schedule;

import org.quartz.SchedulerException;

import com.fzrj.schedule.bean.http.HttpReqBean;
import com.fzrj.schedule.bean.job.CronJobBean;
import com.fzrj.schedule.bean.job.JobBean;
import com.fzrj.schedule.bean.job.SimpleJobBean;

/**
 * @className:com.fzrj.schedule.service.schedule.ScheduleService
 * @description:定时调度器接口类
 * @version:v1.0.0
 * @date:2016年12月16日 下午2:22:14
 * @author:WangHao
 */
public interface ScheduleService
{

	/**
	 * @Description:添加cron类型的Http请求定时任务
	 * @param cronHttpReq
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月16日 下午2:22:12
	 */
	int addHttpCronJob(HttpReqBean httpReqBean, CronJobBean cronJobBean, boolean overWrite) throws SchedulerException;

	/**
	 * @Description:添加一般类型的Http请求定时任务
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月16日 下午2:25:10
	 */
	int addHttpSimpleJob(HttpReqBean httpReqBean, SimpleJobBean simpleJobBean, boolean overWrite) throws SchedulerException;

	/**
	 * @Description:删除定时任务
	 * @param jobBean
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月16日 下午2:36:52
	 */
	int deleteJob(JobBean jobBean) throws SchedulerException;
}
