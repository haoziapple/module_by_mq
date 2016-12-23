package com.fzrj.schedule.client.util;

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
			e.printStackTrace();
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
			e.printStackTrace();
			return null;
		}
	}
}
