package org.dboperate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBOperate {
	public Connection connection = null;
	
	private Statement statement = null;
	private Statement statement1 = null;	//用于度量日志数据库查询操作
	private Statement statement2 = null;
	private Statement statement3 = null;
	private Statement statement4 = null;	//用于白名单查询操作
	private Statement statement5 = null;	//用于度量日志数据库写操作
	private Statement statement6 = null;
	private Statement statement7 = null;
	private Statement statement8 = null;	//用于白名单写操作
	
	private Statement statement9 = null;	//for measurevalue uploading
	private Statement statement10 = null;	//for alarm uploading

	public void connectDB() throws ClassNotFoundException, SQLException {	
		Class.forName("org.sqlite.JDBC");
		//create a database connection
		connection = DriverManager.getConnection("jdbc:sqlite:data.db");
		connection.setAutoCommit(false); 
		statement = connection.createStatement();
		statement1 = connection.createStatement();
		statement2 = connection.createStatement();
		statement3 = connection.createStatement();
		statement4 = connection.createStatement();
		statement5 = connection.createStatement();
		statement6 = connection.createStatement();
		statement7 = connection.createStatement();
		statement8 = connection.createStatement();
		statement9 = connection.createStatement();
		statement10 = connection.createStatement();
	}
			
	public void cleartable(String sql) throws Exception, SQLException, InterruptedException {
		synchronized(this) {
			statement.execute(sql);
		}		
	}
	
	public void inserttable(String sql, int type) throws SQLException, InterruptedException {
		synchronized(this) {
			if(type == 1) {
				statement5.executeUpdate(sql);
			}
			if(type == 2) {
				statement6.executeUpdate(sql);
			}
			if(type == 4) {
				System.out.println("prints"+sql);
				statement8.executeUpdate(sql);
			}
		}		
	}
	
	public ResultSet selecttable(String sql, int type) throws SQLException, InterruptedException {
		synchronized(this) {		
			ResultSet tmp = null;
			if(type == 1) {
				tmp = statement1.executeQuery(sql);
			}
			if(type == 2) {
				tmp = statement2.executeQuery(sql);
			}
			if(type == 3) {
				tmp = statement3.executeQuery(sql);
			}
			if(type == 4) {
				tmp = statement4.executeQuery(sql);
			}
			if(type == 9) {
				tmp = statement9.executeQuery(sql);
			}
			if(type == 10) {
				tmp = statement10.executeQuery(sql);
			}
			return tmp;
		}		
	}

	public void commit() throws SQLException, InterruptedException {
		synchronized(this) {
		connection.commit();
		}
	}
	
}
