package cn.ac.sklois.imm.admin;

import java.sql.Connection;
import java.sql.DriverManager;

public class databasebean {
	public static boolean ifmysql=true;
	public static final String DBDRIVER = "com.beyondb.jdbc.BeyondbDriver"; // 驱动类类名
	public static final String DBURL = "jdbc:beyondb://localhost:II7/immdb_new"; // 连接URL
	public static final String DBUSER = "tcwg"; // 数据库用户名
	public static final String DBPASSWORD = "123456"; // 数据库密码
	private static Connection connection = null;
	
	
	public static Connection getConnection(){
		if(connection==null){
			try {
				if (!ifmysql) {
					Class.forName(databasebean.DBDRIVER); // 注册驱动
					connection = DriverManager.getConnection(databasebean.DBURL,
							databasebean.DBUSER, databasebean.DBPASSWORD); // 获得连接对象
				} else {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					connection = DriverManager
							.getConnection("jdbc:mysql://localhost:3306/immdb_new?user=root&password=tcwg&useUnicode=true&characterEncoding=utf8");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		
		return connection;
	}	
	
	
	public static void closeConnection()throws Exception{
		if(connection!=null){
			connection.close();
		}
	}
}


