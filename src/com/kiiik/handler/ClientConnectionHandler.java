package com.kiiik.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.kiiik.client.ClientStanza;
import com.kiiik.client.Message;
import com.kiiik.client.XmlParser;
import com.kiiik.info.SelfSelectedCodeInfo;
import com.kiiik.info.UserInfo;
import com.kiiik.util.Log;
import com.kiiik.util.StringProcessor;

/**
 * Mina消息接收、处理类
 * 
 * @author YCL
 * 
 */
public class ClientConnectionHandler extends IoHandlerAdapter {

	static final String MESSAGE_PARSER = "message_parser";
	static final String CLIENT_STANZA = "client_stanza";
	static final String CLIENT_ADDRESS = "client_address";

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		session.close();
	}

	/**
	 * mina socket
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// 获取到Message消息
		String contend = message.toString();
		Log.console("Client:" + contend);
		// 获取用户消息解析器
		XmlParser xmlParser = (XmlParser) session.getAttribute(MESSAGE_PARSER);
		// 生成Message消息包
		Message packet = xmlParser.parseToMessage(contend);
		// 获取用户消息stanza
		ClientStanza clientStanza = (ClientStanza) session
				.getAttribute(CLIENT_STANZA);
		// 路由用户消息
		clientStanza.routeMessage(packet);
	}

	/**
	 * 监听客户端连接状态
	 */
	public void sessionOpened(IoSession session) {
		Log.console("Client " + session.getRemoteAddress() + " login.");
		// 消息解析器
		XmlParser xmlParser = new XmlParser();
		// 客户连接
		ClientStanza clientStanza = new ClientStanza(session);
		// 设置session属性
		session.setAttribute(CLIENT_ADDRESS, session.getRemoteAddress());
		session.setAttribute(MESSAGE_PARSER, xmlParser);
		session.setAttribute(CLIENT_STANZA, clientStanza);
	}

	/**
	 * 监听客户端登出连接状态
	 */
	public void sessionClosed(IoSession session) {
		Log.console("Client " + session.getAttribute(CLIENT_ADDRESS)
				+ " logout.");
	}

	/**
	 * 当服务器端空闲时调用此方法
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// System.out.println( "IDLE " + session.getIdleCount( status ));
		if (session.isBothIdle()) {
			session.close();
		}
	}
}
