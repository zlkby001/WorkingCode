package cn.ac.sklois.imm.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ics.util.DBConnection;

public class audit {
public static boolean log_record(int userid, String name, String user,String type, String action){		
		
	//	String LogFile = "C:/TCC/audit_log";		
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time= df.format(new Date());// new Date()为获取当前系统时间
	
		String LogContent = name + "#" + user+ "#" + type + "#" +action + "#" + time + "\n";
		System.out.println("loggggg"+LogContent);				
		Connection con1 = DBConnection.getConnection();
		Statement stmt = null;
	//	ResultSet result1 = null;
		if(name==null){
		String str;
		int i =1;
			 try {
				   FileReader filereader = new FileReader("logininfo.txt");
		            BufferedReader buffererReader = new BufferedReader(filereader);
		   
		        	 
		   		//	System.out.println(wsearch1.getDigest());
		   		//	bufferedWriter.write((String.valueOf(wsearch1.getFileName()) + " | "+String.valueOf(wsearch1.getDigest())).getBytes("UTF8"));
		            while((str=buffererReader.readLine())!=null){
		            	if(i==1){
		            		user = str;
		            	}if(i==2){
		            		name=str;
		            	}
		            	if(i==3){
		            		userid=Integer.parseInt(str);
		            	}
		   		i++;
		            }
		           
		    
		           
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		}
		if(name.equalsIgnoreCase("manager")){
			name="管理员";
			}
		try {
			stmt = con1.createStatement();
			String sql1="insert into auditlog(userid,role,user,type,action,time) values("
					+userid
					+",'"
					+name
					+"','"
					+user
					+"','"
					+type
					+"','"
					+action
					+"','"	
					+time
					+"')"
					;
			System.out.println("loggggg"+LogContent+"userid"+userid);
				stmt.executeUpdate(sql1);
			//	result1.close();
				stmt.close();
				con1.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		       return false;
		}			
		//System.out.println("loggggg"+LogContent+"userid"+userid);
		return true;						
	}
}
