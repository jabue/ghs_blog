package com.kiiik.handler;

import com.kiiik.client.Message;
import com.kiiik.client.MessageSender;
import com.kiiik.modules.BasicModule;
import com.kiiik.starter.StoreServer;

/**
 * blog消息Handler
 * 
 * @author YCL
 * 
 */
public class BlogHandler extends BasicModule implements MessageHandler {

	static final String BLOG_HANDLER = "blog";

	public BlogHandler() {
		super("BlogHandler");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(Message message, MessageSender sender) {
		// TODO Auto-generated method stub
		sender.sendMsg(message);
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
		MessageHandlerDispatcher.addHandler(BLOG_HANDLER, this);
	}

	/**
	 * 停止模块配置方法
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
		MessageHandlerDispatcher.removeHandler(BLOG_HANDLER);
	}

	/**
	 * 销毁模块方法
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
