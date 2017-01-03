package com.fzrj.schedule.client;

import java.util.Date;

import com.fzrj.schedule.bean.job.CronJobBean;
import com.fzrj.schedule.bean.job.JobBean;
import com.fzrj.schedule.bean.job.SimpleJobBean;
import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;
import com.fzrj.schedule.bean.mqctrl.AddMqCronBean;
import com.fzrj.schedule.bean.mqctrl.AddMqSimpleBean;
import com.fzrj.schedule.client.factory.JobFactory;
import com.fzrj.schedule.client.factory.MqMsgFactory;

/**
 * @className:com.fzrj.schedule.client.SpringJobClient
 * @description:Spring定时任务客户端类
 * @version:v1.0.0
 * @date:2016年12月29日 下午3:48:24
 * @author:WangHao
 */
public class SpringJobClient
{
	/**
	 * @Description:删除Spring定时任务
	 * @param beanName(调用的bean名称)
	 * @param methodName(调用的方法名称)
	 * @param key(任务key,同一个方法中唯一，一般使用orderCode)
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月29日 下午2:10:46
	 */
	public static void delSpringJob(String beanName, String methodName, String key)
	{
		JobBean jobBean = JobFactory.buildJob(getSpringJobName(beanName, methodName, key));
		ScheduleClient.deleteJob(jobBean);
	}

	/**
	 * @Description:添加Spring定时任务，指定触发时间,不重试
	 * @param beanName
	 * @param methodName
	 * @param paramBean
	 * @param key
	 * @param startTime
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月30日 上午11:23:29
	 */
	public static void addSpringJob(String beanName, String methodName, Object paramBean, String key, Date startTime)
	{
		addSpringJob(beanName, methodName, paramBean, key, startTime, 0, 0);
	}

	public static void addSpringJob(String beanName, String methodName, Object paramBean, String key, Long pendTime)
	{
		addSpringJob(beanName, methodName, paramBean, key, pendTime, 0, 0);
	}

	public static void addSpringJob(String beanName, String methodName, Object paramBean, String key, Date startTime,
			String serverName)
	{
		addSpringJob(beanName, methodName, paramBean, key, startTime, 0, 0, serverName);
	}

	public static void addSpringJob(String beanName, String methodName, Object paramBean, String key, Long pendTime,
			String serverName)
	{
		addSpringJob(beanName, methodName, paramBean, key, pendTime, 0, 0, serverName);
	}

	/**
	 * @Description:添加Spring定时任务，指定触发时间与重试机制
	 * @param beanName(调用的bean名称)
	 * @param methodName(调用的方法名称)
	 * @param paramBean(调用的参数bean)
	 * @param key(任务key,同一个方法中唯一，一般使用orderCode)
	 * @param startTime(指定的触发时间)
	 * @param repeatCount(重试次数)
	 * @param repeatInterval(重试间隔,单位毫秒)
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月29日 下午3:22:21
	 */
	public static void addSpringJob(String beanName, String methodName, Object paramBean, String key, Date startTime,
			int repeatCount, long repeatInterval)
	{
		// 使用MQ组件的定时任务
		SimpleJobBean simpleJobBean = JobFactory.buildSimpleJob(getSpringJobName(beanName, methodName, key), startTime,
				repeatCount, repeatInterval);
		MqMsgBean mqMsgBean = MqMsgFactory.buildMqMsg(beanName, methodName, paramBean);
		ScheduleClient.addMqSimpleJob(new AddMqSimpleBean(mqMsgBean, simpleJobBean, true));
	}

	// 添加Spring定时任务，指定触发时间, 指定服务名
	public static void addSpringJob(String beanName, String methodName, Object paramBean, String key, Date startTime,
			int repeatCount, long repeatInterval, String serverName)
	{
		// 使用MQ组件的定时任务
		SimpleJobBean simpleJobBean = JobFactory.buildSimpleJob(getSpringJobName(beanName, methodName, key), startTime,
				repeatCount, repeatInterval);
		MqMsgBean mqMsgBean = MqMsgFactory.buildMqMsg(beanName, methodName, paramBean, serverName);
		ScheduleClient.addMqSimpleJob(new AddMqSimpleBean(mqMsgBean, simpleJobBean, true));
	}

	/**
	 * @Description:添加Spring定时任务，指定触发延迟
	 * @param beanName(调用的bean名称)
	 * @param methodName(调用的方法名称)
	 * @param paramBean(调用的参数bean)
	 * @param key(任务key,同一个方法中唯一，一般使用orderCode)
	 * @param pendTime(指定的触发时间)
	 * @param repeatCount(重试次数)
	 * @param repeatInterval(重试间隔,单位毫秒)
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月29日 下午2:27:25
	 */
	public static void addSpringJob(String beanName, String methodName, Object paramBean, String key, Long pendTime,
			int repeatCount, long repeatInterval)
	{
		// 使用MQ组件的定时任务
		SimpleJobBean simpleJobBean = JobFactory.buildSimpleJob(getSpringJobName(beanName, methodName, key), pendTime,
				repeatCount, repeatInterval);
		MqMsgBean mqMsgBean = MqMsgFactory.buildMqMsg(beanName, methodName, paramBean);
		ScheduleClient.addMqSimpleJob(new AddMqSimpleBean(mqMsgBean, simpleJobBean, true));
	}

	/**
	 * @Description:添加Spring定时任务，指定触发延迟,指定服务名
	 * @param beanName
	 * @param methodName
	 * @param paramBean
	 * @param key
	 * @param pendTime
	 * @param repeatCount
	 * @param repeatInterval
	 * @param serverName
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月30日 上午11:24:31
	 */
	public static void addSpringJob(String beanName, String methodName, Object paramBean, String key, Long pendTime,
			int repeatCount, long repeatInterval, String serverName)
	{
		// 使用MQ组件的定时任务
		SimpleJobBean simpleJobBean = JobFactory.buildSimpleJob(getSpringJobName(beanName, methodName, key), pendTime,
				repeatCount, repeatInterval);
		MqMsgBean mqMsgBean = MqMsgFactory.buildMqMsg(beanName, methodName, paramBean, serverName);
		ScheduleClient.addMqSimpleJob(new AddMqSimpleBean(mqMsgBean, simpleJobBean, true));
	}

	/**
	 * @Description:添加cron类定时任务
	 * @param beanName
	 * @param methodName
	 * @param paramBean
	 * @param key
	 * @param cronExp
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月29日 下午3:36:32
	 */
	public static void addSpringCronJob(String beanName, String methodName, Object paramBean, String key,
			String cronExp)
	{
		CronJobBean cronJobBean = JobFactory.buildCronJob(getSpringJobName(beanName, methodName, key), cronExp);
		MqMsgBean mqMsgBean = MqMsgFactory.buildMqMsg(beanName, methodName, paramBean);
		ScheduleClient.addMqCronJob(new AddMqCronBean(mqMsgBean, cronJobBean, true));
	}

	/**
	 * @Description:添加cron类定时任务, 指定服务名
	 * @param beanName
	 * @param methodName
	 * @param paramBean
	 * @param key
	 * @param cronExp
	 * @param serverName
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月30日 上午11:24:45
	 */
	public static void addSpringCronJob(String beanName, String methodName, Object paramBean, String key,
			String cronExp, String serverName)
	{
		CronJobBean cronJobBean = JobFactory.buildCronJob(getSpringJobName(beanName, methodName, key), cronExp);
		MqMsgBean mqMsgBean = MqMsgFactory.buildMqMsg(beanName, methodName, paramBean, serverName);
		ScheduleClient.addMqCronJob(new AddMqCronBean(mqMsgBean, cronJobBean, true));
	}

	// 组装MQ定时任务名称
	private static String getSpringJobName(String beanName, String methodName, String key)
	{
		return beanName + "_" + methodName + ":" + key;
	}
}
