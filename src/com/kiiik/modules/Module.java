package com.kiiik.modules;

import com.kiiik.starter.StoreServer;

/**
 * Moduel接口
 * 
 * @author YCL
 *
 */
public interface Module {
	
	/**
	 * 返回模块名称
	 * @return
	 */
	String getName();
	
	/**
	 * 初始化模块
	 * @param server
	 */
	void initialize(StoreServer server);
	
	/**
	 * 启动模块
	 */
	void start();
	
	/**
	 * 停止模块
	 */
	void stop();
	
	/**
	 * 销毁模块
	 */
	void destroy();

}
