package com.fzrj.schedule.server.test;

import org.springframework.stereotype.Component;

import com.fzrj.schedule.bean.job.JobBean;

@Component
public class MethodSpringBean
{
	public void testMethod(JobBean jobBean)
	{
		System.out.println(jobBean);
	}
}
