package com.kiiik.starter;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kiiik.handler.BlogHandler;
import com.kiiik.handler.ErrorHandler;
import com.kiiik.handler.GuestHandler;
import com.kiiik.handler.LoginHandler;
import com.kiiik.handler.LogoutHandler;
import com.kiiik.modules.*;
import com.kiiik.util.Log;
import com.kiiik.managers.*;

/**
 * 系统管理类，用于加载系统中的所有模块，并对各个模块进行初始化
 * 
 * @author YCL
 * 
 */
public class StoreServer {

	private static StoreServer instance;

	private ClassLoader loader;

	/**
	 * 存放系统中所有模块
	 */
	private Map<Class, Module> modules = new LinkedHashMap<Class, Module>();

	/**
	 * 返回系统主类
	 * 
	 * @return StoreServer
	 */
	public static StoreServer getInstance() {
		return instance;
	}

	/**
	 * 创建服务并启动
	 */
	public StoreServer() {
		// 单例模式
		Log.info("开始加载服务器启动类.");
		if (instance != null) {
			throw new IllegalStateException("A server is already running");
		}
		instance = this;
		loader = Thread.currentThread().getContextClassLoader();
		start();
	}

	/**
	 * 启动系统
	 */
	private void start() {
		// TODO Auto-generated method stub
		/*
		 * 
		 * 
		 */
		Thread mainThread = new Thread() {

			@Override
			public void run() {
				// 加载系统模块
				loadModules();
				// 初始化系统模块
				initModules();
				// 启动系统模块
				startModules();
			}
		};
		mainThread.start();
		/*
         * 
         * 
         */
	}

	/**
	 * 系统系统模块
	 */
	private void startModules() {
		// TODO Auto-generated method stub
		Log.info("启动模块中.");
		for (Module module : modules.values()) {
			boolean started = false;
			try {
				module.start();
			} catch (Exception e) {
				if (started && module != null) {
					module.stop();
					module.destroy();
				}
			}
		}
	}

	/**
	 * 初始化系统模块
	 */
	private void initModules() {
		// TODO Auto-generated method stub
		// Log.info("初始化模块");
		for (Module module : modules.values()) {
			try {
				module.initialize(this);
			} catch (Exception e) {
				e.printStackTrace();
				// Remove the failed initialized module
				this.modules.remove(module.getClass());
				module.stop();
			}
		}
	}

	/**
	 * 加载系统模块
	 */
	private void loadModules() {
		// TODO Auto-generated method stub
		Log.info("加载模块.");
		Log.console("Load server Handlers.");
		// 加载登录处理器
		Log.console("Load LoginHandler module.");
		loadModule(LoginHandler.class.getName());
		// 加载游客处理器
		Log.console("Load LoginHandler module.");
		loadModule(GuestHandler.class.getName());
		// 加载错误消息处理器
		Log.console("Load ErrorHandler module.");
		loadModule(ErrorHandler.class.getName());
		// 加载错误消息处理器
		Log.console("Load BlogHandler module.");
		loadModule(BlogHandler.class.getName());
		// 加载登出处理器
		Log.console("Load LogoutHandler module.");
		loadModule(LogoutHandler.class.getName());
		// 加载连接处理器
		Log.console("Load ClientConnection module.");
		loadModule(ClientConnection.class.getName());
	}

	private void loadModule(String module) {
		// TODO Auto-generated method stub
		try {
			Class modClass = loader.loadClass(module);
			Module mod = (Module) modClass.newInstance();
			this.modules.put(modClass, mod);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取ConnectionManager
	 * 
	 * @return ConnectionManager
	 */
	public ConnectionManager getConnectionManager() {
		return (ConnectionManager) modules.get(ClientConnection.class);
	}

	/**
	 * 获取LoginHandler
	 * 
	 * @return
	 */
	public LoginHandler getLoginHandler() {
		return (LoginHandler) modules.get(LoginHandler.class);
	}

	/**
	 * 获取LogoutHandler
	 * 
	 * @return
	 */
	public LogoutHandler getLogoutHandler() {
		return (LogoutHandler) modules.get(LogoutHandler.class);
	}
}
