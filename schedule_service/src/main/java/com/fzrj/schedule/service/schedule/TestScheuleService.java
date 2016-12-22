package com.fzrj.schedule.service.schedule;

import org.quartz.SchedulerException;

import com.fzrj.schedule.bean.job.SimpleJobBean;

/**
 * @className:com.fzrj.schedule.service.schedule.TestScheuleService
 * @description:测试定时任务Service接口类
 * @version:v1.0.0
 * @date:2016年12月22日 上午11:03:52
 * @author:WangHao
 */
public interface TestScheuleService
{
	void addtestJob(SimpleJobBean simpleJobBean) throws SchedulerException;
}
