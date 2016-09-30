package com.ics.dao.impl;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.ics.dao.AlertDao;
import com.ics.util.DBConnection;
import com.ics.util.syslog;
import com.ics.util.syslogthread;

//modified by zhyjun at 2015/10/28
public class AlertDaoImpl implements AlertDao{

	public boolean uploadalert(String alertinfo)
	{
		String rs[]=alertinfo.split("::");
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
			
			String term[]=rs[1].split(";;");
			//if(term.length!=3)
				//return false;
			for(int i=0;i<term.length;i++)
			{
				String tmp[]=term[i].split(" ");
				if(tmp.length!=3)
					continue;
				
				//alert优化
				sql="update users set alertcount=(alertcount+1),latestalertcount=(latestalertcount+1),alertdate='"+tmp[2]+"' where id="+id;
				stmt.executeUpdate(sql);
				
				
				sql="insert into alert(filename,digest,issuedate,userid,acknowledge) values("
						+"'"
						+tmp[1]
						+"','"
						+tmp[0]
						+"','"
						+tmp[2]
						+"',"
						+id 
						+","
						+"0)"
						;
				stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				ResultSet k = stmt.getGeneratedKeys();  
		        if(k.next()){  
		            sys.Process_id = k.getInt(1);  
		        }  
		        sys.filename=tmp[1];
		        sys.digest=tmp[0];
		        sys.issuedate=tmp[2];
		        sys.acknowledge=0;
		        sys.sendProcess();
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
		//sys.start();
		
		return true;
	
	}
	
	/*
	 //syslog之前的函数
	public boolean uploadalert(String alertinfo)
	{
		String rs[]=alertinfo.split("::");
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
			String term[]=rs[1].split(";;");
			//if(term.length!=3)
				//return false;
			for(int i=0;i<term.length;i++)
			{
				String tmp[]=term[i].split(" ");
				if(tmp.length!=3)
					continue;
				sql="insert into alert(filename,digest,issuedate,userid,acknowledge) values("
						+"'"
						+tmp[1]
						+"','"
						+tmp[0]
						+"','"
						+tmp[2]
						+"',"
						+id 
						+","
						+"0)"
						;
				stmt.executeUpdate(sql);
				syslogUDP();
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
	*/


	/*
	private void syslogUDP(syslogthread sys){

		String msg="InTrust_00:00:00:00:00:00 MessageType='Process',";
		msg=msg+"users_id='"+sys.users_id+"',name='"+sys.name+"',manufacture='"+sys.manufacture+"',"
				+"client_area='"+sys.client_area+"',sequence='"+sys.sequence+"',IP='"+sys.ip+"',MAC='"+sys.mac+"',"
				+"pubkey='"+sys.pubkey+"',os='"+sys.os+"',EK='"+sys.EK+"',device_type='"+sys.device_type+"',"
				+"fid='"+sys.fid+"',Process_id='"+sys.Process_id+"',filename='"+sys.filename+"',digest='"+sys.digest+"',"
				+"issuedate='"+sys.issuedate+"',acknowledge='"+sys.acknowledge+"'";
        byte[] buf = msg.getBytes();  
        try {  
            InetAddress address = InetAddress.getByName(syslog.syslogIP);  //服务器地址  
            int port = syslog.syslogPort;  //服务器的端口号  
            //创建发送方的数据报信息  
            DatagramPacket dataGramPacket = new DatagramPacket(buf, buf.length, address, port);  
   
            DatagramSocket socket = new DatagramSocket();  //创建套接字  
            socket.send(dataGramPacket);  //通过套接字发送数据  
              
            //接收服务器反馈数据  
            
            //byte[] backbuf = new byte[1024];  
            //DatagramPacket backPacket = new DatagramPacket(backbuf, backbuf.length);  
            //socket.receive(backPacket);  //接收返回数据  
            //String backMsg = new String(backbuf, 0, backPacket.getLength());  
            //System.out.println("服务器返回的数据为:" + backMsg);  
              
            
            socket.close();  
              
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	*/
}
