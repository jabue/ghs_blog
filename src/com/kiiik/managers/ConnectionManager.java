package com.kiiik.managers;

import org.apache.mina.core.service.IoAcceptor;

/**
 * 系统连接管理类接口
 * 
 * @author YCL
 *
 */
public interface ConnectionManager {
	/**
	 * 面向客户端传字符串默认端口
	 */
	final int DEFAULT_PORT = 8888;
	
	/**
	 * 面向客户端传文件的接口
	 */
	final int FILE_PORT = 8889;
	
	/**
	 * 获取系统的socket Acceptor
	 * @return
	 */
	public IoAcceptor getServerAcceptor();
	
	/**
	 * 关闭系统socket
	 */
	public void stopClientListeners();
}
