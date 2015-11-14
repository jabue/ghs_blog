package com.kiiik.modules;

import com.kiiik.starter.StoreServer;

/**
 * 所有系统模块的父类
 * 
 * @author YCL
 * 
 */
public class BasicModule implements Module {

	private String name;
	
	public BasicModule(String moduleName) {
		if (moduleName == null) {
            this.name = "No name assigned";
        }
        else {
            this.name = moduleName;
        }
	}
	
	/**
	 * 获取模块名称
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	@Override
	public void initialize(StoreServer server) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
