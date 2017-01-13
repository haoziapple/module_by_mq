package com.fzrj.schedule.client.util;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rabbitmq.client.Channel;

/**
 * @className:com.fzrj.schedule.client.util.MqChannelFactory
 * @description:rabbitmq通道工厂类
 * @version:v1.0.0
 * @date:2017年1月13日 上午9:37:52
 * @author:WangHao
 */
public class MqChannelFactory
{
	private static Logger logger = LogManager.getLogger(MqChannelFactory.class);

	/**
	 * @Description:通过连接获取通道 channel不是线程安全的！每个线程需要使用自己channel
	 * @return
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2017年1月13日 上午9:41:08
	 */
	public static Channel createChannel()
	{
		Channel channel = null;
		try
		{
			channel = MqConnectionFactory.getInstance().createChannel();
		}
		catch (IOException e)
		{
			logger.error("通过连接获取通道异常", e);
		}
		return channel;
	}

	/**
	 * @Description:关闭通道
	 * @param channel
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2017年1月13日 上午9:46:38
	 */
	public static void CloseChannel(Channel channel)
	{
		if (channel != null)
		{
			try
			{
				channel.close();
			}
			catch (Exception e)
			{
				logger.error("关闭通道异常", e);
			}
		}
	}
}
