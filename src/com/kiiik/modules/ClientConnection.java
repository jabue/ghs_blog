package com.kiiik.modules;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.kiiik.managers.ConnectionManager;
import com.kiiik.provider.StoreGlobals;
import com.kiiik.starter.StoreServer;
import com.kiiik.util.Log;
import com.kiiik.handler.*;

/**
 * 连接管理类，包括接收，读取和终止连接
 * 
 * @author YCL
 * 
 */
public class ClientConnection extends BasicModule implements ConnectionManager {

	// private IoAcceptor acceptor;
	private SocketAcceptor sAcceptor;

	public ClientConnection() {
		super("Connection Manager");
		// TODO Auto-generated constructor stub
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
		startListeners();
	}

	/**
	 * 启动socket，并对某一端口进行监听
	 */
	private synchronized void startListeners() {
		// TODO Auto-generated method stub
		// Setup port info
		try {
			// acceptor = new NioSocketAcceptor();
			sAcceptor = new NioSocketAcceptor();
			sAcceptor.getFilterChain().addLast("logger", new LoggingFilter());
			sAcceptor.getFilterChain().addLast("codec",
					new ProtocolCodecFilter(new TextLineCodecFactory()));
			// 设置mina连接池
			sAcceptor.getFilterChain().addLast("threadPool",
					new ExecutorFilter(Executors.newCachedThreadPool()));
			sAcceptor.setHandler(new ClientConnectionHandler());
			sAcceptor.getSessionConfig().setReadBufferSize(2048);
			// 设置空闲连接断开时间
			sAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
			// 设置任务队列大小
			sAcceptor.setBacklog(50);
			sAcceptor.bind(new InetSocketAddress(StoreGlobals
					.getCONNECTION_PORT()));
			Log.info("服务器已经启动.");
			// Log.console("Database URL=" + StoreGlobals.getDBURL());
			Log.console("Connection Port="
					+ StoreGlobals.getCONNECTION_PORT());
			Log.console("Server is started.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.info("服务器启动失败.");
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
		stopClientListeners();
	}

	/**
	 * 销毁模块方法
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public IoAcceptor getServerAcceptor() {
		// TODO Auto-generated method stub
		return sAcceptor;
	}

	@Override
	public void stopClientListeners() {
		// TODO Auto-generated method stub
		if (sAcceptor != null) {
			sAcceptor.unbind();
			sAcceptor = null;
		}
	}
}
