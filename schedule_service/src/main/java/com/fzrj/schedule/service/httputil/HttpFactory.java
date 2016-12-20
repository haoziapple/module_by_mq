package com.fzrj.schedule.service.httputil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fzrj.schedule.bean.http.HttpReqBean;
import com.fzrj.schedule.constant.HttpConstant;

/**
 * @className:com.fzrj.schedule.service.httputil.HttpFactory
 * @description:Http请求工厂类
 * @version:v1.0.0
 * @date:2016年12月15日 下午3:37:21
 * @author:WangHao
 */
public class HttpFactory
{
	private static Logger logger = LogManager.getLogger(HttpFactory.class);

	/**
	 * @Description:发送Http请求
	 * @param httpReq
	 * @return
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2016年12月15日 下午4:16:43
	 */
	public static String sendHttpReq(HttpReqBean httpReq)
	{
		// 返回字符串
		String responseString = HttpConstant.RspBody.NO_RSP_ERR;

		HttpURLConnection httpURLConnection = null;
		OutputStream ops = null;
		InputStream ips = null;
		ByteArrayOutputStream baops = null;

		// 设置访问地址
		URL url;
		try
		{
			url = new URL(httpReq.getReqUrl());
			// 打开连接
			httpURLConnection = (HttpURLConnection) url.openConnection();
			// 设置默认请求头
			setDefaultHttpHead(httpURLConnection, httpReq);
			// 设置定制请求头(有可能覆盖之前设置的头信息)
			for (Map.Entry<String, String> entry : httpReq.getHeadMap().entrySet())
			{
				httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
			}

			httpURLConnection.connect();
			// 此处getOutputStream会隐含的进行connect()调用(即：如同调用上面的connect()方法，
			// 所以在开发中不调用上述的connect()也可以)。
			// 发送请求数据
			// 获取URLConnection对象输出流，向服务器提交数据
			ops = httpURLConnection.getOutputStream();
			ops.write(httpReq.getReqBody().getBytes("UTF-8"));
			ops.flush();
			// 根据ResponseCode判断连接是否成功
			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				// HTTP请求返回成功
				ips = httpURLConnection.getInputStream();// 获取输入流
				baops = new ByteArrayOutputStream();// 字节输出流
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = ips.read(buffer)) != -1)
				{
					baops.write(buffer, 0, len);
				}
				byte[] data = baops.toByteArray();// 返回的二进制数据

				responseString = new String(data, "UTF-8");
			}
		}
		catch (Exception e)
		{
			logger.error("发送Http请求异常", e);
		}
		finally
		{
			if (baops != null)
				try
				{
					baops.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				} // 关闭字节输出流
			if (ips != null)
				try
				{
					ips.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				} // 关闭输入流
			if (ops != null)
				try
				{
					ops.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				} // 关闭输出流
			if (httpURLConnection != null)
				httpURLConnection.disconnect();// 关闭连接
		}

		return responseString;
	}

	/**
	 * 设置默认的请求头
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws ProtocolException
	 */
	public static void setDefaultHttpHead(HttpURLConnection httpURLConnection, HttpReqBean httpReq)
			throws UnsupportedEncodingException, ProtocolException
	{
		// TODO 发送POST请求必须设置如下两行?
		httpURLConnection.setDoOutput(true);// 打开输出流，以便向服务器提交数据
		httpURLConnection.setDoInput(true);// 打开输入流，以便从服务器获取数据
		// 设置通用请求属性
		if (HttpConstant.METHOD.GET.equals(httpReq.getReqMethod()))
		{
			httpURLConnection.setRequestMethod("GET");// 设置以GET方式提交数据
		}
		else
		{
			httpURLConnection.setRequestMethod("POST");// 设置以POST方式提交数据
			// Post请求不能使用缓存
			httpURLConnection.setUseCaches(false);
		}
		// 设置请求体的类型为类型
		if (HttpConstant.FORMAT.XML.equals(httpReq.getReqFormat()))
		{
			httpURLConnection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		}
		else
		{
			httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		}
		httpURLConnection.setConnectTimeout(30 * 1000);// 连接超时(单位毫秒)
		httpURLConnection.setReadTimeout(30 * 1000);// 读取超时(单位毫秒)
		httpURLConnection.setRequestProperty("Accept", "*/*");
		httpURLConnection.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
		httpURLConnection.setRequestProperty("Connection", "keep-alive");
		httpURLConnection.setRequestProperty("Content-Length",
				String.valueOf(httpReq.getReqBody().getBytes("UTF-8").length));// 设置请求体的长度
		httpURLConnection.setRequestProperty("Pragma", "no-cache");
		httpURLConnection.setRequestProperty("Enctype", "multipart/form-data");
		httpURLConnection.setInstanceFollowRedirects(true);
	}
}
