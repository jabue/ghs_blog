package com.kiiik.handler;

import com.kiiik.client.Message;
import com.kiiik.client.MessageSender;

/**
 * 
 * 所有消息处理类的接口函数
 * 
 * @author kf13
 * 
 */
public interface MessageHandler {
	public void process(Message message, MessageSender sender);
}
