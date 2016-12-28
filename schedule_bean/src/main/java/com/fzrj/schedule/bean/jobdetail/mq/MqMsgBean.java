package com.fzrj.schedule.bean.jobdetail.mq;

/**
 * @className:com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean
 * @description:队列消息信息bean
 * @version:v1.0.0
 * @date:2016年12月22日 下午7:39:11
 * @author:WangHao
 */
public class MqMsgBean
{
	public static final String EXCHANGE_NAME_KEY = "exchangeName";

	public static final String ROUTING_KEY = "routingKey";

	public static final String MSG_BODY_KEY = "msgBody";

	/**
	 * 交换机名称
	 */
	private String exchangeName = "";

	/**
	 * 路由键
	 */
	private String routingKey = "";

	/**
	 * 消息体
	 */
	private String msgBody = "";
	
	public MqMsgBean()
	{
		super();
	}

	public MqMsgBean(String exchangeName, String routingKey, String msgBody)
	{
		this.exchangeName = exchangeName;
		this.routingKey = routingKey;
		this.msgBody = msgBody;
	}

	public String getExchangeName()
	{
		return exchangeName;
	}

	public String getRoutingKey()
	{
		return routingKey;
	}

	public String getMsgBody()
	{
		return msgBody;
	}

	@Override
	public String toString()
	{
		return "MqMsgBean [exchangeName=" + exchangeName + ", routingKey=" + routingKey + ", msgBody=" + msgBody + "]";
	}
}
