package com.fzrj.schedule.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @className:com.fzrj.schedule.service.SpringContextUtil
 * @description:获取spring上下文工具类
 * @version:v1.0.0
 * @date:2016年12月20日 下午1:43:13
 * @author:WangHao
 */
public class SpringContextUtil implements ApplicationContextAware
{
	private static ApplicationContext applicationContext; // Spring应用上下文环境

	/*
	 * 实现了ApplicationContextAware 接口，必须实现该方法；
	 * 通过传递applicationContext参数初始化成员变量applicationContext
	 */

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException
	{
		return (T) applicationContext.getBean(name);
	}
}
