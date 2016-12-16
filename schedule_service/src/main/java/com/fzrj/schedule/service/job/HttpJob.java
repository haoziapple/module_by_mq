package com.fzrj.schedule.service.job;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzrj.schedule.bean.http.HttpReqBean;
import com.fzrj.schedule.constant.HttpConstant;
import com.fzrj.schedule.service.httputil.HttpFactory;

/**
 * @className:com.fzrj.schedule.service.job.HttpJob
 * @description:发送Http请求的定时任务
 * @version:v1.0.0
 * @date:2016年12月16日 上午10:01:46
 * @author:WangHao
 */
public class HttpJob implements Job
{
	private static Logger logger = LogManager.getLogger(HttpJob.class);

	public static final String URL_KEY = "url";

	public static final String BODY_KEY = "body";

	public static final String METHOD_KEY = "method";

	public static final String FORMAT_KEY = "format";

	public static final String HEAD_KEY = "head";

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		JobDataMap data = context.getMergedJobDataMap();
		HttpReqBean httpReqBean=this.getHttpJob(data);
		String rsp = HttpFactory.sendHttpReq(httpReqBean);
		if (HttpConstant.RspBody.NO_RSP_ERR.equals(rsp))
		{
			// TODO 返回结果为空，继续执行
		}
		else
		{
			// TODO 执行成功,删除定时器
			String jobKey = data.getString("jobKey");
		}
	}

	/**
	 * 从map里解析获取Http请求
	 */
	@SuppressWarnings("unchecked")
	private HttpReqBean getHttpJob(JobDataMap data)
	{
		// 获取请求头
		String headJson = data.getString(HEAD_KEY);
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
		HttpReqBean httpReqBean = new HttpReqBean(data.getString(URL_KEY), headMap);
		httpReqBean.setReqBody(data.getString(BODY_KEY));
		httpReqBean.setReqFormat(data.getString(FORMAT_KEY));
		httpReqBean.setReqMethod(data.getString(METHOD_KEY));
		return httpReqBean;
	}

}
