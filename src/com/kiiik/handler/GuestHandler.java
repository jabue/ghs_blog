package com.kiiik.handler;

import java.sql.SQLException;

import com.kiiik.client.Message;
import com.kiiik.client.MessageSender;
import com.kiiik.modules.BasicModule;
import com.kiiik.services.BlogMessageService;
import com.kiiik.starter.StoreServer;

/**
 * 游客blog消息Handler
 * 
 * @author YCL
 * 
 */
public class GuestHandler extends BasicModule implements MessageHandler {

	static final String GUEST_HANDLER = "guest";

	private BlogMessageService blogMessageService = null;

	public GuestHandler() {
		super("GuestHandler");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(Message message, MessageSender sender) {
		// TODO Auto-generated method stub
		// sender.sendMsg("The message is in hand of guesthandler.");
		try {
			// 请求博客消息
			if (message.getQueryCmd().equalsIgnoreCase("message")) {
				blogMessageService.reqstAllBlogMessage(message, sender);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 此处添加模块初始化配置
	 */
	@Override
	public void initialize(StoreServer server) {
		// TODO Auto-generated method stub
	}

	/**
	 * 模块启动配置
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub
		super.start();
		MessageHandlerDispatcher.addHandler(GUEST_HANDLER, this);
		try {
			blogMessageService = BlogMessageService.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 停止模块配置方法
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
		MessageHandlerDispatcher.removeHandler(GUEST_HANDLER);
		blogMessageService = null;
	}

	/**
	 * 销毁模块方法
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
