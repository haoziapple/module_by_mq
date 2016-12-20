package com.fzrj.schedule.client.demo;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.fzrj.schedule.bean.http.SimpleHttpReq;
import com.fzrj.schedule.bean.job.JobBean;
import com.fzrj.schedule.bean.mqctrl.AddHttpSimpleBean;
import com.fzrj.schedule.client.ScheduleMsg;

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
		// 新建一般类型定时任务
		SimpleHttpReq simpleHttpReq = new SimpleHttpReq("http://localhost:8080", "{\"settleKey\":\"hgstxHehjhmjNzjR\"}",
				new Date());
		JobBean jobBean = new JobBean("testJobKey", "shuichan_plat");
		AddHttpSimpleBean addHttpSimpleBean = new AddHttpSimpleBean(simpleHttpReq, jobBean, true);
		ScheduleMsg.addHttpSimpleJob(addHttpSimpleBean);
	}
}
