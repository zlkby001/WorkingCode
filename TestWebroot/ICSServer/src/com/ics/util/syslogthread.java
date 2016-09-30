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
		//��ȡ��������ȡ��ַ
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
			System.out.println("��ȡ������Ϣʧ��");
			lmac=null;
			return;
		}
		//System.out.println("mac���鳤�ȣ�"+mac.length);
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append(":");
			}
			//�ֽ�ת��Ϊ����
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			//System.out.println("ÿ8λ:"+str);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		lmac=sb.toString().toUpperCase();
		System.out.println("����MAC��ַ:"+lmac);
	}
	
	private static String lmac;
	public String msg;  //ÿ��UDP��������
	
	//�ն���Ϣ
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
	
	//���̱�����־��Ϣ
	public int Process_id;
	public String filename;
	public String digest;
	public String issuedate;
	public int acknowledge;
	
	//USB������־��Ϣ
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
	
	private void syslogUDP() { //����һ��������־,��־������msg��
		//System.out.println(index+"syslogUDP Thread.run()"); 
		//index++;
		if(msg==null||msg.equals(""))
			return;
		
		//System.out.println(index+"syslogUDP Thread.run()");
        byte[] buf = msg.getBytes();   //String msg�������ַ���������,��תΪbyte����ʲô���͵��ֽ���ȡ����getByte����ָ���ı��뷽ʽ
        								//getbytes��ָ�����뷽ʽ,���ȡ��bytes���뷽ʽΪ����ϵͳĬ�ϱ��뷽ʽ   Ҳ��ָ��ĳһ�ֱ���
        							   //�� msg.getBytes("UTF-8");
        try {  
            InetAddress address = InetAddress.getByName(syslog.syslogIP);  //��������ַ  
            int port = syslog.syslogPort;  //�������Ķ˿ں�  
            //�������ͷ������ݱ���Ϣ  
            DatagramPacket dataGramPacket = new DatagramPacket(buf, buf.length, address, port);  
   
            DatagramSocket socket = new DatagramSocket();  //�����׽���  
            socket.send(dataGramPacket);  //ͨ���׽��ַ�������  
            System.out.println(msg);
             //index++;
            
             try {
				Thread.sleep(syslog.minCycle);  //�����������ʣ�������Գ���ᶪ��
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            //���շ�������������  
            
           // byte[] backbuf = new byte[1024];  
            //DatagramPacket backPacket = new DatagramPacket(backbuf, backbuf.length);  
            //socket.receive(backPacket);  //���շ�������  
            //String backMsg = new String(backbuf, 0, backPacket.getLength());  
            //System.out.println("���������ص�����Ϊ:" + backMsg);  
            
            
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
            InetAddress address = InetAddress.getByName(syslog.syslogIP);  //��������ַ  
            int port = syslog.syslogPort;  //�������Ķ˿ں�  
            //�������ͷ������ݱ���Ϣ  
            DatagramPacket dataGramPacket = new DatagramPacket(buf, buf.length, address, port);  
   
            DatagramSocket socket = new DatagramSocket();  //�����׽���  
            socket.send(dataGramPacket);  //ͨ���׽��ַ�������  
              
            //���շ�������������  
            
           // byte[] backbuf = new byte[1024];  
            //DatagramPacket backPacket = new DatagramPacket(backbuf, backbuf.length);  
            //socket.receive(backPacket);  //���շ�������  
            //String backMsg = new String(backbuf, 0, backPacket.getLength());  
            //System.out.println("���������ص�����Ϊ:" + backMsg);  
            
            
            socket.close();  
              
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
	}*/
}
