package com.kiiik.handler;

import com.kiiik.client.Message;
import com.kiiik.client.MessageSender;
import com.kiiik.info.CodeInfo;
import com.kiiik.info.UserInfo;
import com.kiiik.modules.BasicModule;
import com.kiiik.starter.StoreServer;

/**
 * 用户登录验证信息handler
 * 
 * @author YCL
 * 
 */
public class LoginHandler extends BasicModule implements MessageHandler {

	public LoginHandler() {
		super("LoginHandler");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(Message message, MessageSender sender) {
		// TODO Auto-generated method stub
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
		MessageHandlerDispatcher.addHandler("login", this);
	}
	
	/**
	 * 停止模块配置方法
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
		MessageHandlerDispatcher.removeHandler("login");
	}

	/**
	 * 销毁模块方法
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
