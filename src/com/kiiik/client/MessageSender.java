package com.kiiik.client;

import org.apache.mina.core.session.IoSession;

import com.kiiik.util.Log;

/**
 * 最终的消息发送类
 * 
 * @author kf23
 * 
 */
public class MessageSender {

	private IoSession session = null;

	public MessageSender(IoSession session) {
		this.session = session;
	}

	/**
	 * 向客户端发送消息方法
	 * 
	 * @param message
	 */
	public void sendMsg(Message message) {
		String msg = message.toString();
		session.write(msg);
		Log.console("Server:" + msg);
	}

	/**
	 * 向客户端发送String
	 * 
	 * @param message
	 */
	public void sendMsg(String message) {
		session.write(message);
		Log.console("Server:" + message);
	}

	/**
	 * 关闭客户端连接
	 */
	public void closeSession() {
		session.close();
	}
}
