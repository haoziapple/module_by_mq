package com.fzrj.schedule.constant;

/**
 * @className:com.fzrj.schedule.constant.HttpConstant
 * @description:Http相关常量类
 * @version:v1.0.0
 * @date:2016年12月16日 上午9:35:06
 * @author:WangHao
 */
public class HttpConstant
{
	/**
	 * 请求方法
	 */
	public static class METHOD
	{
		public static final String POST = "POST";

		public static final String GET = "GET";
	}

	/**
	 * 请求格式
	 */
	public static class FORMAT
	{
		public static final String XML = "XML";

		public static final String JSON = "JSON";
	}
	
	/**
	 * 返回错误信息
	 */
	public static class RspBody
	{
		// 未取得返回结果
		public static final String NO_RSP_ERR = "NO RESPONSE";
	}
}
