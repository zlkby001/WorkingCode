package com.ics.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.ac.sklois.imm.admin.databasebean;

public class DBConnection {

	private static final String DBDRIVER = "com.beyondb.jdbc.BeyondbDriver"; // ��������
	private static final String DBURL = "jdbc:beyondb://localhost:II7/immdb_new"; // ����URL
	private static final String DBUSER = "tcwg"; // ��ݿ��û���
	private static final String DBPASSWORD = "123456"; // ��ݿ�����
	//private static final boolean ifmysql=true;
	public static Connection getConnection() {
		Connection conn = null; // ����һ�����Ӷ���
		if(databasebean.ifmysql)
		{
		try {
			//Class.forName(DBDRIVER); // ע����
			//conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD); // ������Ӷ���
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
				Class.forName(DBDRIVER); // ע����
				conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD); // ������Ӷ���
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
		if (conn != null) { // ���conn���Ӷ���Ϊ��
			try {
				conn.close(); // �ر�conn���Ӷ������
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(PreparedStatement pstmt) {
		if (pstmt != null) { // ���pstmtԤ�������Ϊ��
			try {
				pstmt.close(); // �ر�pstmtԤ�������
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) { // ���rs������Ϊnull
			try {
				rs.close(); // �ر�rs������
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
