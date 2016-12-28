package com.fzrj.schedule.bean.jobdetail.http;

import java.util.HashMap;
import java.util.Map;

import com.fzrj.schedule.constant.HttpConstant;

/**
 * @className:com.fzrj.schedule.bean.jobdetail.http.HttpReqBean
 * @description:Http请求bean
 * @version:v1.0.0
 * @date:2016年11月23日 下午2:51:16
 * @author:WangHao
 */
public class HttpReqBean
{
	public static final String URL_KEY = "url";

	public static final String BODY_KEY = "body";

	public static final String METHOD_KEY = "method";

	public static final String FORMAT_KEY = "format";

	public static final String HEAD_KEY = "head";

	/**
	 * 请求url
	 */
	private String reqUrl;

	/**
	 * 请求头
	 */
	private Map<String, String> headMap = new HashMap<String, String>();
	
	public HttpReqBean()
	{
		super();
	}

	public HttpReqBean(String reqUrl, String reqBody)
	{
		super();
		this.reqUrl = reqUrl;
		this.reqBody = reqBody;
	}

	/**
	 * 默认POST请求
	 */
	private String reqMethod = HttpConstant.METHOD.POST;

	/**
	 * 默认JSON格式请求
	 */
	private String reqFormat = HttpConstant.FORMAT.JSON;

	/**
	 * 请求体
	 */
	private String reqBody = "";

	public String getReqUrl()
	{
		return reqUrl;
	}

	public void setReqUrl(String reqUrl)
	{
		this.reqUrl = reqUrl;
	}

	public String getReqMethod()
	{
		return reqMethod;
	}

	public void setReqMethod(String reqMethod)
	{
		this.reqMethod = reqMethod;
	}

	public String getReqFormat()
	{
		return reqFormat;
	}

	public void setReqFormat(String reqFormat)
	{
		this.reqFormat = reqFormat;
	}

	public String getReqBody()
	{
		return reqBody;
	}

	public void setReqBody(String reqBody)
	{
		this.reqBody = reqBody;
	}

	public Map<String, String> getHeadMap()
	{
		return headMap;
	}

	public void setHeadMap(Map<String, String> headMap)
	{
		this.headMap = headMap;
	}

	@Override
	public String toString()
	{
		return "HttpReqBean [reqUrl=" + reqUrl + ", headMap=" + headMap + ", reqMethod=" + reqMethod + ", reqFormat="
				+ reqFormat + ", reqBody=" + reqBody + "]";
	}
}
