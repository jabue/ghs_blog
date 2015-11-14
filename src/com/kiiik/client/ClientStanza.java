package com.kiiik.client;

import org.apache.mina.core.session.IoSession;

import com.kiiik.handler.MessageHandler;
import com.kiiik.handler.MessageHandlerDispatcher;

/**
 * 客户端回话封装类
 * 
 * @author YCL
 * 
 */
public class ClientStanza {
	// 消息发送器
	private MessageSender messageSender = null;

	public ClientStanza(IoSession session) {
		messageSender = new MessageSender(session);
	}

	/**
	 * 用户消息处理方法
	 * 
	 * @param message
	 */
	public void routeMessage(Message message){
		MessageHandler handler = null;
		// 如果某handler不存在
		if(!MessageHandlerDispatcher.isHandlerExist(message.getId())){
			Message returnPacket = Message.createErrorMessage(Message.NO_MESSAGE_HANDLER);
			messageSender.sendMsg(returnPacket);
			return;
		}
		// 根据id获取消息处理器handler
		handler = MessageHandlerDispatcher.getHandler(message.getId());
		// 处理消息
		handler.process(message, messageSender);
	}
}
