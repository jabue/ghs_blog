package com.kiiik.starter;

import com.kiiik.provider.StoreGlobals;
import com.kiiik.util.Log;

/**
 * 存储服务器启动类
 * 
 * @author YCL
 * 
 */
public class StoreStarter {

	public static void main(String[] args) {

		StoreStarter storeStarter = new StoreStarter();
		storeStarter.Init(args);
		// 初始化日志类
		Log log = new Log();
		storeStarter.Start();
	}

	/**
	 * 服务器初始化方法
	 * 
	 * @param args
	 */
	@SuppressWarnings("null")
	private void Init(String[] args) {
		// 运行参数数量不正确
		if (args.length == 0 || args.length == 1 || args.length > 3) {
			System.out.println("WARNING: NONE CONNECTION URL OF DB!");
			StoreGlobals.setDBURL("jdbc:mysql://localhost/ghs");
			StoreGlobals.setCONNECTION_PORT(9995);
			return;
		}

		String dbURL = "jdbc:mysql://" + args[0] + "/ghs";
		StoreGlobals.setDBURL(dbURL);
		StoreGlobals.setCONNECTION_PORT(Integer.parseInt(args[1]));
	}

	/**
	 * 服务器启动方法
	 */
	private void Start() {
		Log.info("服务器正在启动中.");
		try {
			Thread.currentThread().getContextClassLoader()
					.loadClass("com.kiiik.starter.StoreServer").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
