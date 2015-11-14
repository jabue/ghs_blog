package com.kiiik.handler;

import java.sql.SQLException;

import com.kiiik.client.Message;
import com.kiiik.client.MessageSender;
import com.kiiik.info.CodeInfo;
import com.kiiik.info.SelfSelectedCodeInfo;
import com.kiiik.modules.BasicModule;
import com.kiiik.provider.SelfSelectedProvider;
import com.kiiik.starter.StoreServer;

/**
 * 退出登录处理器
 * 
 * @author YCL
 * 
 */
public class LogoutHandler extends BasicModule implements MessageHandler {

	public LogoutHandler() {
		super("logouthandler");
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
		MessageHandlerDispatcher.addHandler("logout", this);
	}

	/**
	 * 停止模块配置方法
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
		MessageHandlerDispatcher.removeHandler("logout");
	}

	/**
	 * 销毁模块方法
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
