package com.kiiik.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kiiik.info.SelfSelectedCodeInfo;
import com.kiiik.managers.DbConnectionManager;

/**
 * 用户昵称
 * 
 * @author YCL
 * 
 */
public class NickNameProvider {

	public static NickNameProvider instance = null;
	
	private static final String GET_NICKNAME = "SELECT NICK FROM tbl_user WHERE USERNAME = ?";
	
	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static NickNameProvider getInstance() {
		if (instance == null) {
			instance = new NickNameProvider();
		}

		return instance;
	}
	
	/**
	 * 获取用户昵称信息
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public String getNickname(String username)
			throws SQLException {
		String result = "";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet resultSet = null;
		try {
			conn = DbConnectionManager.getDbConneciton();
			psmt = conn.prepareStatement(GET_NICKNAME);
			psmt.setString(1, username);
			resultSet = psmt.executeQuery();
			while (resultSet.next()) {
				result = resultSet.getString(1);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			conn.close();
			psmt.close();
			resultSet.close();
		}
		return result;
	}
}
