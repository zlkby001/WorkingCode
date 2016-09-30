package com.ics.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {

	private static final String DBDRIVER = "com.beyondb.jdbc.BeyondbDriver"; // 驱动类类名
	private static final String DBURL = "jdbc:beyondb://localhost:II7/immdb_new"; // 连接URL
	private static final String DBUSER = "tcwg"; // 数据库用户名
	private static final String DBPASSWORD = "123456"; // 数据库密码
	private static final boolean ifmysql=true;
	public static Connection getConnection() {
		Connection conn = null; // 声明一个连接对象
		if(ifmysql)
		{
		try {
			//Class.forName(DBDRIVER); // 注册驱动
			//conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD); // 获得连接对象
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/immdb_new?user=root&password=tcwg&useUnicode=true&characterEncoding=utf8");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else
		{
			try {
				Class.forName(DBDRIVER); // 注册驱动
				conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD); // 获得连接对象
				//Class.forName("com.mysql.jdbc.Driver").newInstance();
				//conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/immdb_new?user=root&password=tcwg&useUnicode=true&characterEncoding=utf8");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	public static void close(Connection conn) {
		if (conn != null) { // 如果conn连接对象不为空
			try {
				conn.close(); // 关闭conn连接对象对象
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(PreparedStatement pstmt) {
		if (pstmt != null) { // 如果pstmt预处理对象不为空
			try {
				pstmt.close(); // 关闭pstmt预处理对象
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) { // 如果rs结果集对象不为null
			try {
				rs.close(); // 关闭rs结果集对象
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
