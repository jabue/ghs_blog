package com.kiiik.util;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志类
 * 
 * @author kf13
 * 
 */
public class Log {

	private static String PROJECT_PATH = System.getProperty("user.dir");
	private static String LOG_PATH = PROJECT_PATH + "\\logs\\";

	private File fileName = null;
	private static File pathName = null;

	public Log() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String path = LOG_PATH + df.format(new Date()) + ".log";
		fileName = new File(LOG_PATH);
		pathName = new File(path);

		try {
			if (!fileName.exists()) {
				fileName.mkdirs();
			}
			if (!pathName.exists()) {
				pathName.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 写日志
	 */
	public static void info(String contend) {
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String className = new Throwable().getStackTrace()[1].getClassName();
		String funcName2 = new Throwable().getStackTrace()[1].getMethodName();
		String temp = df1.format(new Date()) + "[ClassName:" + className + ";"
				+ "Function:" + funcName2 + "]:" + contend + "\r\n";
		try {
			FileWriter writer = new FileWriter(pathName, true);
			writer.write(temp);
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/*
	 * 打印控制台
	 */
	public static void console(String contend) {
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String temp = "[" + df1.format(new Date()) + "]:" + contend;
		System.out.println(temp);
	}

	public static void main(String[] args) {
		Log log = new Log();
		Log.info("hah");
	}
}
