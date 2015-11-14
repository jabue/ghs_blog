package com.kiiik.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.kiiik.info.SelfSelectedCodeInfo;
import com.kiiik.managers.DbConnectionManager;

/**
 * 自选股的数据服务类（包括自选股信息的读库和写库操作）
 * 
 * @author YCL
 * 
 */
public class SelfSelectedProvider {

	public static SelfSelectedProvider instance = null;

	private static final String GET_SELFSELECTED = "SELECT username, selfselectedcode FROM selfselectedtable WHERE username = ?";
	private static final String SET_SELFSELECTED = "REPLACE INTO selfselectedtable (username,selfselectedcode) VALUES (?,?)";

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static SelfSelectedProvider getInstance() {
		if (instance == null) {
			instance = new SelfSelectedProvider();
		}

		return instance;
	}

	/**
	 * 保存自选股配置信息
	 * 
	 * @param selfSelectedCodeInfo
	 * @throws SQLException
	 */
	public void saveSelfSelectedCode(SelfSelectedCodeInfo selfSelectedCodeInfo)
			throws SQLException {
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = DbConnectionManager.getDbConneciton();
			if (selfSelectedCodeInfo.getCodekind().equalsIgnoreCase(
					"selfselectedcode")) {
				psmt = conn.prepareStatement(SET_SELFSELECTED);
				psmt.setString(1, selfSelectedCodeInfo.getUserID());
				psmt.setString(2, selfSelectedCodeInfo.getCodeConfig());
				psmt.executeUpdate();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			conn.close();
			psmt.close();
		}
	}

	/**
	 * 获取自选股配置信息
	 * 
	 * @param selfSelectedCodeInfo
	 * @return
	 * @throws SQLException
	 */
	public String getSelfSelectedCode(SelfSelectedCodeInfo selfSelectedCodeInfo)
			throws SQLException {
		String result = "";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet resultSet = null;
		try {
			conn = DbConnectionManager.getDbConneciton();
			psmt = conn.prepareStatement(GET_SELFSELECTED);
			psmt.setString(1, selfSelectedCodeInfo.getUserID());
			resultSet = psmt.executeQuery();
			while (resultSet.next()) {
				result = resultSet.getString(2);
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
