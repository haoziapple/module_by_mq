package com.fzrj.schedule.server;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @className:com.fzrj.schedule.server.MsgRecoverer
 * @description:消息异常处理类
 * @version:v1.0.0
 * @date:2016年12月16日 下午7:44:34
 * @author:WangHao
 */
public class MsgRecoverer implements MessageRecoverer
{
	private static Logger logger = LogManager.getLogger(MsgRecoverer.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private int maxRecTimes;

	@Override
	public void recover(Message message, Throwable cause)
	{
		if (!this.reachMaxRecTimes(message, cause))
		{
			// 将异常消息重新发送
			this.rabbitTemplate.send(message.getMessageProperties().getReceivedExchange(),
					message.getMessageProperties().getReceivedRoutingKey(), message);
		}

		logger.error("处理消息异常,msg:" + new String(message.getBody()), cause);
	}

	/**
	 * 处理异常消息头，并返回判断是否回队操作
	 */
	private boolean reachMaxRecTimes(Message message, Throwable cause)
	{
		Map<String, Object> headers = message.getMessageProperties().getHeaders();
		System.out.println(headers);
		int recTimes = headers.get("recTimes") == null ? 0 : (int) headers.get("recTimes");
		if (recTimes >= maxRecTimes)
		{
			return true;
		}
		headers.put("exception-stacktrace", getErrStackTrace(cause));
		headers.put("exception-message", cause.getCause() != null ? cause.getCause().getMessage() : cause.getMessage());
		headers.put("original-exchange", message.getMessageProperties().getReceivedExchange());
		headers.put("original-routingKey", message.getMessageProperties().getReceivedRoutingKey());
		headers.put("recTimes", recTimes + 1);
		return false;
	}

	/**
	 * 获取异常堆栈信息
	 */
	private String getErrStackTrace(Throwable cause)
	{
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter, true);
		cause.printStackTrace(printWriter);
		return stringWriter.getBuffer().toString();
	}

	public int getMaxRecTimes()
	{
		return maxRecTimes;
	}

	public void setMaxRecTimes(int maxRecTimes)
	{
		this.maxRecTimes = maxRecTimes;
	}
}
