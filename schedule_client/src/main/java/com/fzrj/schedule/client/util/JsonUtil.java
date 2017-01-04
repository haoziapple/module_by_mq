package com.fzrj.schedule.client.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @className:com.fzrj.schedule.client.util.JsonUtil
 * @description:json转化工具类
 * @version:v1.0.0
 * @date:2016年12月23日 上午11:30:45
 * @author:WangHao
 */
public class JsonUtil
{

	private static Logger logger = LogManager.getLogger(JsonUtil.class);

	/**
	 * javabean转json
	 */
	public static String convertObjToString(Object bean)
	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		try
		{
			return mapper.writeValueAsString(bean);
		}
		catch (JsonProcessingException e)
		{
			logger.error("bean转Json字串异常", e);
			return null;
		}
	}

	/**
	 * json转javabean
	 */
	public static <T> T convertStringToObj(String jsonString, Class<T> valueType)

	{
		ObjectMapper mapper = new ObjectMapper(); // 转换器
		try
		{
			return mapper.readValue(jsonString, valueType);
		}
		catch (Exception e)
		{
			logger.error("Json字串转换bean异常", e);
			return null;
		}
	}

	public static void main(String[] args)
	{
		System.out.println(convertObjToString(null));
	}
}
