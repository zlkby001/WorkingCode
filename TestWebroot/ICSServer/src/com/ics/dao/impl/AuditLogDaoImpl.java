package com.ics.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ics.dao.AuditLogDao;
import com.ics.util.DBConnection;

public class AuditLogDaoImpl implements AuditLogDao{
	
	public String downloadadminpasswd() {

		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = null;
			ResultSet rs = null;
			stmt = con.createStatement();			
			String sql="SELECT Name,Password,UserType FROM admins where EndType='client' and pass='Y'";
			rs=stmt.executeQuery(sql);
//			if(!rs.next())
//			{
//				return null;
//			}
//			System.out.println("name:"+rs.getString("name") );
//			System.out.println("password:"+rs.getString("password") );
//			System.out.println("usertype:"+rs.getString("usertype") );
			String	clientlist = "";
			while (rs.next()) {
		
			clientlist += rs.getString("name").trim() + "&&&"
						+ rs.getString("password").trim() + "&&&"
						+ rs.getString("usertype").trim() + ";;;";

			}
			//String passwd=result.getString(1);											
			rs.close();
			stmt.close();
			con.close();
			
			return clientlist;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public boolean uploadAuditLog(String auditlog)
	{
		System.out.print("test1111");
		String rs[]=auditlog.split("::");
		
		//System.out.print("rs:"+rs);
		System.out.print(rs.length);
		if(rs.length!=2)
			
			return false;
		//String pubkey=rs[0];
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = null;
			ResultSet result = null;
			stmt = con.createStatement();			
			String sql="select id from users where pubkey='"+rs[0]+"'";
			result=stmt.executeQuery(sql);
			if(!result.next())
			{
				return false;
			}
			int id=result.getInt(1);

			
			String term[]=rs[1].split(";;");
			for(int i=0;i<term.length;i++)
			{
				String tmp[]=term[i].split("#");
				
//				if(tmp.length!=4)
//					continue;
				
				//alert优化
				//sql="update users set alertcount=(alertcount+1),latestalertcount=(latestalertcount+1),alertdate='"+tmp[2]+"' where id="+id;
				//stmt.executeUpdate(sql);
				
				
				sql="insert into auditlog(userid,role,user,type,action,time) values("
						+id
						+",'"
						+tmp[0]
						+"','"
						+tmp[1]
						+"','"
						+tmp[2]
						+"','"
						+tmp[3]
						+"','"	
						+tmp[4]
						+"')"
						;
//				System.out.println("tem2"+tmp[2]);
//				System.out.println("tem0"+tmp[0]);
//				System.out.println("tem1"+tmp[1]);
//				System.out.println("tem3"+tmp[3]);
//				System.out.println("tem4"+tmp[4]);
				stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				
		        
				//syslogUDP(sys);   //串行方式
		        //sys.start();  //并行方式
			}
			
			result.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
