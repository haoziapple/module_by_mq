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

	@Override
	public void recover(Message message, Throwable cause)
	{
		Map<String, Object> headers = message.getMessageProperties().getHeaders();
		headers.put("x-exception-stacktrace", getErrStackTrace(cause));
		headers.put("x-exception-message",
				cause.getCause() != null ? cause.getCause().getMessage() : cause.getMessage());
		headers.put("x-original-exchange", message.getMessageProperties().getReceivedExchange());
		headers.put("x-original-routingKey", message.getMessageProperties().getReceivedRoutingKey());
		// 将异常消息重新发送
		this.rabbitTemplate.send(message.getMessageProperties().getReceivedExchange(),
				message.getMessageProperties().getReceivedRoutingKey(), message);

		logger.error("处理消息异常,重新发送,msg:" + new String(message.getBody()), cause);
		
		// TODO 重新入队一定次数后，说明此服务不可用，将发送告警并拒绝所有消息
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

}
