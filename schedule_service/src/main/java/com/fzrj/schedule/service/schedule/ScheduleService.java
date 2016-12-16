package com.fzrj.schedule.service.schedule;

import com.fzrj.schedule.bean.http.CronHttpReq;
import com.fzrj.schedule.bean.http.SimpleHttpReq;
import com.fzrj.schedule.bean.job.JobBean;

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
	int addHttpCronJob(CronHttpReq cronHttpReq, JobBean jobBean, boolean overWrite);

	/**
	 * @Description:添加一般类型的Http请求定时任务
	 * @param simpleJobBean
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月16日 下午2:25:10
	 */
	int addHttpSimpleJob(SimpleHttpReq simpleHttpReq, JobBean jobBean, boolean overWrite);

	/**
	 * @Description:删除定时任务
	 * @param jobBean
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月16日 下午2:36:52
	 */
	int deleteJob(JobBean jobBean);
}
