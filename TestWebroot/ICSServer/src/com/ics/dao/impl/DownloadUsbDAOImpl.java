package com.ics.dao.impl;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ics.dao.DownloadUsbDAO;
import com.ics.util.DBConnection;
import com.ics.util.syslogthread;

public class DownloadUsbDAOImpl implements DownloadUsbDAO{
	public String downloadusb(String pk)
	{
		String result="";
		try 
		{
			Connection con = DBConnection.getConnection();
			Statement stmt = null;
			ResultSet rs = null;
			stmt = con.createStatement();
			String sql="select id from users where pubkey='"+pk+"'";
			rs=stmt.executeQuery(sql);
			rs.next();
			int id=rs.getInt(1);
			sql="select manufacture,sn from usblocal where terminalid="+id;
			rs=stmt.executeQuery(sql);
			//String result=null;
			String manu=null;
			String sn=null;
			while(rs.next())
			{
				manu=rs.getString(1);
				sn=rs.getString(2);
				result+=manu;
				result+=" ";
				result+=sn;
				result+="\r\n";
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//return "error";
			e.printStackTrace();
			return "error";
		}
		return result;
	}
	
	//modified by zhyjun at 2015/10/28
	public boolean uploadusb(String usbinfo)
	{
		
		String rs[]=usbinfo.split(";;;");
		if(rs.length!=2)
			return false;
		syslogthread sys=new syslogthread();
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = null;
			ResultSet result = null;
			stmt = con.createStatement();			
			String sql="select id,name,manufacture,client_area,sequence,ip,mac,os,EK,device_type,fid from users where pubkey='"+rs[0]+"'";
			result=stmt.executeQuery(sql);
			if(!result.next())
			{
				return false;
			}
			int id=result.getInt(1);
			sys.pubkey=rs[0];
			sys.users_id=id;
			sys.name=result.getString(2);
			sys.manufacture=result.getString(3);
			sys.client_area=result.getString(4);
			sys.sequence=result.getString(5);;
			sys.ip=result.getString(6);
			sys.mac=result.getString(7);
			sys.os=result.getString(8);
			sys.EK=result.getString(9);
			sys.device_type=result.getString(10);
			sys.fid=result.getInt(11);
			
			String term[]=rs[1].split(" ");
			if(term.length!=4)
				return false;
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date=df.format(new Date());
			sql="insert into usbmeasure(manufacture,sn,version,producer,terminalid,date) values("
					+"'"
					+term[0]
					+"','"
					+term[1]
					+"','"
					+term[3]
					+"','"
					+term[2]
					+"',"
					+id
					+",'"
					+date
					+"')";
			stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
			ResultSet k = stmt.getGeneratedKeys();  
	        if(k.next()){  
	            sys.Usbmeasure_id= k.getInt(1);  
	        } 
			sys.usb_manufacture=term[0];
	        sys.sn=term[1];
	        sys.version=term[3];
	        sys.producer=term[2];
	        sys.date=date;
	        sys.sendUsb();
			
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
	
	/*
	//加syslog功能前的usb报警日志上传函数
	public boolean uploadusb(String usbinfo)
	{
		
		String rs[]=usbinfo.split(";;;");
		if(rs.length!=2)
			return false;
		
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
			String term[]=rs[1].split(" ");
			if(term.length!=4)
				return false;
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date=df.format(new Date());
			sql="insert into usbmeasure(manufacture,sn,version,producer,terminalid,date) values("
					+"'"
					+term[0]
					+"','"
					+term[1]
					+"','"
					+term[3]
					+"','"
					+term[2]
					+"',"
					+id
					+",'"
					+date
					+"')";
			stmt.executeUpdate(sql);
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
	*/
}
