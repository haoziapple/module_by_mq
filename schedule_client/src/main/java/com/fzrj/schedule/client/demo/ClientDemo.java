package com.fzrj.schedule.client.demo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.fzrj.schedule.bean.job.JobBean;
import com.fzrj.schedule.bean.job.SimpleJobBean;
import com.fzrj.schedule.bean.jobdetail.http.HttpReqBean;
import com.fzrj.schedule.bean.mqctrl.AddHttpSimpleBean;
import com.fzrj.schedule.client.ScheduleClient;
import com.fzrj.schedule.client.builder.SpringJobBuilder;
import com.fzrj.schedule.client.factory.JobFactory;

/**
 * @className:com.fzrj.schedule.client.demo.ClientDemo
 * @description:定时器客户端jar包demo
 * @version:v1.0.0
 * @date:2016年12月20日 上午11:07:28
 * @author:WangHao
 */
public class ClientDemo
{
	public static void main(String[] args) throws IOException, TimeoutException
	{

	}

	// 添加一般类型Mq定时任务
	public static void addMqSimpleJobDemo() throws IOException, TimeoutException
	{
		// 20s后发起
		SpringJobBuilder builder = new SpringJobBuilder().setJobCode("testJobCode")
				.setSpringJob("methodSpringBean", "testMethod", new JobBean("test", "test")).setPendTime(20000L);
		// 任务将调用methodSpringBean的testMethod方法，参数为new出来的JobBean
		ScheduleClient.addMqSimpleJob(builder.buildMqSimpleJob());
	}

	// 添加一般类型Http定时任务
	public static void addHttpSimpleJobDemo() throws IOException, TimeoutException
	{
		// 新建一般类型定时任务
		HttpReqBean httpReqBean = new HttpReqBean("http://localhost:8080", "{\"settleKey\":\"hgstxHehjhmjNzjR\"}");
		// 20s后执行
		SimpleJobBean simpleJobBean = JobFactory.buildSimpleJob("testJobKey", 20000L);
		ScheduleClient.addHttpSimpleJob(new AddHttpSimpleBean(httpReqBean, simpleJobBean, true));
	}
}
