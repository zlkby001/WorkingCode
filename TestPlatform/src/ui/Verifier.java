package ui;
import Integrity.IntegrityProducer;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ProgressBar;
//import org.reg.ClientSocket;
//import org.reg.IntegrityProducerGenerator;
//import org.reg.VerifyProcess;


public class Verifier {
	private IntegrityProducer producer = IntegrityProducerGenerator.getProducer();
	private int timeout = 10000;

	
	public int verifiy(String server_ip, int server_port, Label list, ProgressBar bar,String component,String pubkey, ArrayList<ErrorListBean> alertlog){

		int res = 0;
		
		String FILENAME = "c:\\TCC\\platform.xml";//"c:\\platform.xml";
		//String FILENAME = "c:\\test.txt";
		String RecvFile = "C:\\TCC\\result0";//"C:\\result0";//获取返回失败的文件
		
		try{
			//System.out.println(server_ip);
			//System.out.println(server_port);
				list.setText("正在连接服务器...");
				list.update();
		   	ClientSocket sock = new ClientSocket(server_ip,4999);
		   	//ClientSocket sock = new ClientSocket(server_ip,server_port);
		   	try { 
		   		sock.CreateConnection(this.timeout); 
		   		System.out.print("连接服务器成功" + "\n"); 
		   			list.setText("连接服务器成功!");
		   			list.update();
		   				
		   		
		   	} catch (Exception e) { 
		   			System.out.print("连接服务器失败" + "\n"); 
		   			list.setText("连接服务器失败，请检测网络连接和服务器地址是否正确！");
		   			list.update();
		   			return 103; 
		   	} 
		   	

		   	//get file
		   	if ( producer.generateFile(FILENAME, component, alertlog) == -1 ){
		   		return 102;
		   	}

		   	VerifyProcess thread = new VerifyProcess(sock,pubkey);
		   	thread.start();
		   	while(thread.Getres()!=1000 && thread.Getres()!=1001 && thread.Getres()!=0 &&
		   			thread.Getres()!=101 && thread.Getres()!=104 && thread.Getres()!=2 && 
		   			thread.Getres()!=3 && thread.Getres()!=9 && thread.Getres()!=10 && thread.Getres()!=11)
		   	{
		   		list.setText("正在等待服务器响应,请等待.");
		   		list.update();
		   		bar.setSelection(10);
		   		Thread.sleep(500);
		   		list.setText("正在等待服务器响应,请等待..");
		   		list.update();
		   		bar.setSelection(30);
		   		Thread.sleep(500);
		   		list.setText("正在等待服务器响应,请等待...");
		   		bar.setSelection(50);
		   		list.update();
		   		Thread.sleep(500);
		   		list.setText("正在等待服务器响应,请等待....");
		   		bar.setSelection(70);
		   		list.update();
		   		Thread.sleep(500);
		   		list.setText("正在等待服务器响应,请等待.....");
		   		list.update();
		   		bar.setSelection(90);
		   		Thread.sleep(500);
		   		list.setText("正在等待服务器响应,请等待......");
		   		list.update();
		   		bar.setSelection(100);
		   		Thread.sleep(500);
		   		
		   	}


		     sock.shutDownConnection();
		     res = thread.Getres();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return -1;
		}
		
		return res;
	
	}
	
	private static byte[] tolh(int n) { 
		byte[] b = new byte[4]; 
		b[0] = (byte) (n & 0xff); 
		b[1] = (byte) (n >> 8 & 0xff); 
		b[2] = (byte) (n >> 16 & 0xff); 
		b[3] = (byte) (n >> 24 & 0xff); 
		return b; 
		} 
	
   private static final int byteArray2Int(byte[] data, int startIndex){
	   if (data == null)
	       throw new IllegalArgumentException("byte[] is null!");
	   if (data.length < 4)
	       throw new IllegalArgumentException("byte[] size > 4 !");
	   int retVal = 0;
	   int j = 0;
	   for (int i = startIndex; i <= startIndex + 3; i++) {
	       int ret = (int) ((data[i] & 0xFF) * (Math.pow(256, j++)));
	       retVal += ret;
	   }
	   return retVal;
   }

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	} 

}

