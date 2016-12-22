package com.fzrj.schedule.client.demo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.fzrj.schedule.bean.job.SimpleJobBean;
import com.fzrj.schedule.bean.jobdetail.http.HttpReqBean;
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
		HttpReqBean httpReqBean = new HttpReqBean("http://localhost:8080", "{\"settleKey\":\"hgstxHehjhmjNzjR\"}");
		// 10秒后执行
		SimpleJobBean simpleJobBean = new SimpleJobBean("testJobKey", "shuichan_plat", 10000L);

		AddHttpSimpleBean addHttpSimpleBean = new AddHttpSimpleBean(httpReqBean, simpleJobBean, true);
		ScheduleMsg.addHttpSimpleJob(addHttpSimpleBean);
	}
}
