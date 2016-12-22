package com.fzrj.schedule.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDataMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzrj.schedule.bean.jobdetail.http.HttpReqBean;
import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;

/**
 * @className:com.fzrj.schedule.service.JobMapConverter
 * @description:定时任务Map转换类
 * @version:v1.0.0
 * @date:2016年12月22日 上午9:26:09
 * @author:WangHao
 */
public class JobMapConverter
{
	private static Logger logger = LogManager.getLogger(JobMapConverter.class);

	/**
	 * 将http任务bean转化为map
	 */
	public static Map<String, String> getHttpJobMap(HttpReqBean httpReqBean)
	{
		// job参数Map
		Map<String, String> jobMap = new HashMap<String, String>();
		jobMap.put(HttpReqBean.URL_KEY, httpReqBean.getReqUrl());
		jobMap.put(HttpReqBean.BODY_KEY, httpReqBean.getReqBody());
		jobMap.put(HttpReqBean.METHOD_KEY, httpReqBean.getReqMethod());
		jobMap.put(HttpReqBean.FORMAT_KEY, httpReqBean.getReqFormat());
		// 定时Http的请求头map
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		try
		{
			jobMap.put(HttpReqBean.HEAD_KEY, mapper.writeValueAsString(httpReqBean.getHeadMap()));
		}
		catch (JsonProcessingException e)
		{
			logger.error("设定Http定时job,设置Http头异常", e);
		}
		return jobMap;
	}

	/**
	 * 将jobMap转化为http任务bean
	 */
	@SuppressWarnings("unchecked")
	public static HttpReqBean getHttpJob(JobDataMap data)
	{
		// 获取请求头
		String headJson = data.getString(HttpReqBean.HEAD_KEY);
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		Map<String, String> headMap = new HashMap<String, String>();
		try
		{
			headMap = mapper.readValue(headJson, Map.class);
		}
		catch (Exception e)
		{
			logger.error("执行Http定时任务,获取请求头失败", e);
		}
		HttpReqBean httpReqBean = new HttpReqBean(data.getString(HttpReqBean.URL_KEY),
				data.getString(HttpReqBean.BODY_KEY));
		httpReqBean.setHeadMap(headMap);
		httpReqBean.setReqFormat(data.getString(HttpReqBean.FORMAT_KEY));
		httpReqBean.setReqMethod(data.getString(HttpReqBean.METHOD_KEY));
		return httpReqBean;
	}

	/**
	 * 将MQ任务bean转化为map
	 */
	public static Map<String, String> getMqJobMap(MqMsgBean mqMsgBean)
	{
		// job参数Map
		Map<String, String> jobMap = new HashMap<String, String>();
		jobMap.put(MqMsgBean.EXCHANGE_NAME_KEY, mqMsgBean.getExchangeName());
		jobMap.put(MqMsgBean.ROUTING_KEY, mqMsgBean.getRoutingKey());
		jobMap.put(MqMsgBean.MSG_BODY_KEY, mqMsgBean.getMsgBody());
		return jobMap;
	}

	/**
	 * 将jobMap转化为MQ任务
	 */
	public static MqMsgBean getMqJob(JobDataMap data)
	{
		MqMsgBean mqMsgBean = new MqMsgBean(data.getString(MqMsgBean.EXCHANGE_NAME_KEY),
				data.getString(MqMsgBean.ROUTING_KEY), data.getString(MqMsgBean.ROUTING_KEY));
		return mqMsgBean;
	}
}
