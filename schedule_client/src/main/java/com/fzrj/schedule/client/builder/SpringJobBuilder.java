package com.fzrj.schedule.client.builder;

import java.util.Date;

/**
 * @className:com.fzrj.schedule.client.builder.SpringJobBuilder
 * @description:Spring定时任务建造器
 * @version:v1.0.0
 * @date:2016年12月30日 下午2:44:13
 * @author:WangHao
 */
public class SpringJobBuilder
{
	// 调用服务名
	private String serverName = null;
	// 调用bean名称(在spring容器中注册的名称)
	private String beanName = null;
	// 调用方法名称
	private String methodName = null;
	// 调用参数实例
	private Object paramBean = null;
	// 任务key,一般使用orderCode
	private String key = null;
	// 任务指定触发时间
	private Date StartTime = null;
	// 任务延迟触发时间(毫秒)
	private Long pendTime = null;
	// 任务重试次数(默认0次)
	private int repeatCount = 0;
	// 任务重试间隔(默认10s)
	private long repeatInterval = 10000L;

}
