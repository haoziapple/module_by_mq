package com.fzrj.schedule.service.mqmsg;

import com.fzrj.schedule.bean.jobdetail.mq.MqMsgBean;

/**
 * @className:com.fzrj.schedule.service.mqmsg.MqMsgService
 * @description:Mq消息Service接口类
 * @version:v1.0.0
 * @date:2017年1月9日 下午1:58:28
 * @author:WangHao
 */
public interface MqMsgService
{
	/**
	 * @Description:发送Mq消息
	 * @param mqMsgBean
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2017年1月9日 下午1:58:04
	 */
	void sendMqMsg(MqMsgBean mqMsgBean);
}
