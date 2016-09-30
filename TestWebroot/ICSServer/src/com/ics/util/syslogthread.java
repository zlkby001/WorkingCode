package com.ics.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class syslogthread{
	//public static String syslogIP;
	//public static int syslogPort;
	//public static String syslogOpen;
	//public static int index=1;
	public static void getLocalMac() throws SocketException {
		// TODO Auto-generated method stub
		//获取网卡，获取地址
		InetAddress ia = null;
		
			try {
				ia = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		if(mac==null)
		{
			System.out.println("获取网卡信息失败");
			lmac=null;
			return;
		}
		//System.out.println("mac数组长度："+mac.length);
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append(":");
			}
			//字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			//System.out.println("每8位:"+str);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		lmac=sb.toString().toUpperCase();
		System.out.println("本机MAC地址:"+lmac);
	}
	
	private static String lmac;
	public String msg;  //每条UDP数据内容
	
	//终端信息
	public String name;
	public String manufacture;
	public String client_area;
	public String sequence;
	public String ip;
	public String mac;
	public String os;
	public String EK;
	public String device_type;
	public int fid;
	public String pubkey;
	public int users_id;
	
	//进程报警日志信息
	public int Process_id;
	public String filename;
	public String digest;
	public String issuedate;
	public int acknowledge;
	
	//USB报警日志信息
	public int Usbmeasure_id;
	public String usb_manufacture;
	public String version;
	public String producer;
	public String date;
	public String sn;
	
	private void genProcessHeader()
	{
		if(lmac==null||lmac.equals(""))
			msg="InTrust_00:00:00:00:00:00 MessageType='Process',";
		else
			msg="InTrust_"+lmac+" MessageType='Process',";
	}
	
	private void genUsbHeader()
	{
		if(lmac==null||lmac.equals(""))
			msg="InTrust_00:00:00:00:00:00 MessageType='Usbmeasure',";
		else
			msg="InTrust_"+lmac+" MessageType='Usbmeasure',";
	}
	
	private void addUserInfo()
	{
		msg=msg+"users_id='"+users_id+"',name='"+name+"',manufacture='"+manufacture+"',"
				+"client_area='"+client_area+"',sequence='"+sequence+"',IP='"+ip+"',MAC='"+mac+"',"
				+"pubkey='"+pubkey+"',os='"+os+"',EK='"+EK+"',device_type='"+device_type+"',"
				+"fid='"+fid+"',";
	}
	
	private void addProcessInfo()
	{
		msg=msg+"Process_id='"+Process_id+"',filename='"+filename+"',digest='"+digest+"',"
				+"issuedate='"+issuedate+"',acknowledge='"+acknowledge+"'";
	}
	
	private void addUsbInfo()
	{
		msg=msg+"Usbmeasure_id='"+Usbmeasure_id+"',manufacture='"+usb_manufacture+"',version='"+version+"',"
				+"producer='"+producer+"',date='"+date+"',sn='"+sn+"'";
	}
	
	private void genProcessUDP(){
		genProcessHeader();
		addUserInfo();
		addProcessInfo();
	}
	
	private void genUsbUDP(){
		genUsbHeader();
		addUserInfo();
		addUsbInfo();
	}
	
	public void sendProcess(){
		genProcessUDP();
		syslogUDP();
	}
	
	public void sendUsb(){
		genUsbUDP();
		syslogUDP();
	}
	
	private void syslogUDP() { //发送一条报警日志,日志内容在msg中
		//System.out.println(index+"syslogUDP Thread.run()"); 
		//index++;
		if(msg==null||msg.equals(""))
			return;
		
		//System.out.println(index+"syslogUDP Thread.run()");
        byte[] buf = msg.getBytes();   //String msg代表了字符串的语义,而转为byte后是什么类型的字节码取决于getByte函数指定的编码方式
        								//getbytes不指定编码方式,则获取的bytes编码方式为操作系统默认编码方式   也可指定某一种编码
        							   //如 msg.getBytes("UTF-8");
        try {  
            InetAddress address = InetAddress.getByName(syslog.syslogIP);  //服务器地址  
            int port = syslog.syslogPort;  //服务器的端口号  
            //创建发送方的数据报信息  
            DatagramPacket dataGramPacket = new DatagramPacket(buf, buf.length, address, port);  
   
            DatagramSocket socket = new DatagramSocket();  //创建套接字  
            socket.send(dataGramPacket);  //通过套接字发送数据  
            System.out.println(msg);
             //index++;
            
             try {
				Thread.sleep(syslog.minCycle);  //减缓发送速率，否则测试程序会丢包
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            //接收服务器反馈数据  
            
           // byte[] backbuf = new byte[1024];  
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
	
	/*
	public void run() { 
		System.out.println("MyThread.run()"); 
		
		String msg="InTrust_00:00:00:00:00:00 MessageType='Process',";
		msg=msg+"users_id='"+users_id+"',name='"+name+"',manufacture='"+manufacture+"',"
				+"client_area='"+client_area+"',sequence='"+sequence+"',IP='"+ip+"',MAC='"+mac+"',"
				+"pubkey='"+pubkey+"',os='"+os+"',EK='"+EK+"',device_type='"+device_type+"',"
				+"fid='"+fid+"',Process_id='"+Process_id+"',filename='"+filename+"',digest='"+digest+"',"
				+"issuedate='"+issuedate+"',acknowledge='"+acknowledge+"'";
        byte[] buf = msg.getBytes();  
        try {  
            InetAddress address = InetAddress.getByName(syslog.syslogIP);  //服务器地址  
            int port = syslog.syslogPort;  //服务器的端口号  
            //创建发送方的数据报信息  
            DatagramPacket dataGramPacket = new DatagramPacket(buf, buf.length, address, port);  
   
            DatagramSocket socket = new DatagramSocket();  //创建套接字  
            socket.send(dataGramPacket);  //通过套接字发送数据  
              
            //接收服务器反馈数据  
            
           // byte[] backbuf = new byte[1024];  
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
	}*/
}
