package com.kiiik.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kiiik.provider.StoreGlobals;

/**
 * 数据库管理类
 * 
 * @author YCL
 *
 */
public class DbConnectionManager {
	
	private static Connection con = null;
	
	//private static String url = "jdbc:mysql://localhost/kiiikstoredb";
    private static String user = "root";
    private static String pwd = "kiiik";
    
    /**
     * 获取数据库连接
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getDbConneciton() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	Class.forName("com.mysql.jdbc.Driver").newInstance();
    	con = DriverManager.getConnection(StoreGlobals.getDBURL(),user, pwd);
    	return con;
    }
    
    /**
     * 关闭数据库连接
     * @throws SQLException
     */
    public static void closeDbConnection() throws SQLException{
    	if(!con.isClosed()){
    		con.close();
    	}
    }
    
    /**
     * test
     * @param args
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	Connection conn = DbConnectionManager.getDbConneciton();
    	PreparedStatement psmt = conn.prepareStatement("select * from ofuser");
    	ResultSet resultSet = psmt.executeQuery();
    	while (resultSet.next()) {
			System.out.println(resultSet.getString(1));
		}
    }
}
