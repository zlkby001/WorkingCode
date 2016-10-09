package ui;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.swt.widgets.MessageBox;
import org.forms.XMLReader;

import com.sun.jna.ptr.IntByReference;

public class UploadAlertProcess extends Thread {
	private String pubkey;
	private int res;
	private String errorfile="C://TCC//errorlist.log";//"C://errorlist.log";
	private ArrayList error_list = new ArrayList();
	private long pre_len;
	private long post_len;
	private AlertUploader uploader;
	public static String f="30";
	
	public UploadAlertProcess(String pubkey)
    {
    	//sock = socket;
		res = -1;
		this.pubkey=pubkey;
		pre_len=-1;
		post_len=-1;
		XMLReader xmlreader = new XMLReader();
		f= xmlreader.searchXML("uploadfrequence");
		uploader=new AlertUploader(pubkey);
    }
	
	
	private int send(long pre_len,long post_len)
	{
		String ip = ServerIPaddress.getIp();
		int port = ServerIPaddress.getPort();
		try{
			ClientSocket sock = new ClientSocket(ip, port);
			
			try {
				int timeout = 10000;
				sock.CreateConnection(timeout);
				System.out.print("连接服务器成功" + "\n");
				
			} catch (Exception e1) {
				System.out.print("连接服务器失败" + "\n");
				return 100;
				
			}
		    DataOutputStream ps = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream())); 
		    //DataInputStream inputStream = null; 	 		   
		    System.out.println("Sending...");
		    int bufferSize = 1024; 
		    byte[] buf = new byte[bufferSize]; 
		    long begintime = System.currentTimeMillis();		   
//===========================zhyjun========================================
		    ps.writeInt(3);
			ps.flush();	

			byte[] buf1=pubkey.getBytes();
			ps.writeInt(buf1.length);
			ps.flush();
			ps.write(buf1,0,buf1.length);
			ps.flush();	
//===========================zhyjun========================================		    


		     
		     try { 
		    	BufferedInputStream dis = new BufferedInputStream(new FileInputStream(new File(errorfile))); 
		    	//inputStream = sock.getMessageStream(); 
		    	//byte[] res_byte = new byte[1024];
		    	//res = inputStream.readInt();
			      //if(res==0){
			    	  //int total_length=(int)post_len;
			    	  int total_length=(int)post_len-(int)pre_len;
			    	  dis.skip(pre_len);
			    	  ps.writeInt(total_length); 
					  ps.flush();
					  int n = (int) total_length/bufferSize ;
					     //System.out.print(n);
					  int i = 0;
					  for( ; i <n ; i++) { 
					    int read = 0; 
					    if (dis != null) { 

					    	read = dis.read(buf);

					    } 
					    if (read == -1) { 
					    	break; 
					    } 
					    ps.write(buf, 0, read);
					    ps.flush();  	 
					    } 
					  
					     
					     int left_length = (int) total_length%bufferSize;					   
					     int read = 0; 
						 if (dis != null) { 
							 //read = dis.read(buf,i*bufferSize, left_length);
							 read = dis.read(buf);							 
						 } 
						 if (read == -1) { 
							 System.out.println("read left error");
						     dis.close();
						     sock.shutDownConnection();
						     System.exit(1);
						 } 
						 ps.write(buf, 0, read);
						 //System.out.println("send one");
						 //System.out.println(read);
						 //System.out.println("\n");
						 //System.out.write(buf,0,read);
						 //System.out.println("\n");

					     
					     ps.flush(); 
					     dis.close();
					     DataInputStream inputStream = null;
					     try { 
						    	inputStream = sock.getMessageStream(); 
						    	res = inputStream.readInt();		      		        		       		    	
						     } catch (Exception e) { 
						    	 System.out.print("接收报警日志上传结果失败\n"); 
						    	 e.printStackTrace();
						    	 return 3; 
						    	 
						     } 
					     System.out.println("\nFinished\n");	
			          
			         //}
		     } catch (Exception e) { 
		    	 System.out.print("上传报警日志失败\n"); 
		    	 e.printStackTrace();
		    	 return 4;
		    	
		     } 
		     sock.shutDownConnection();
		     
//����ʱ������		     
		     long endtime = System.currentTimeMillis();
		     System.out.print(endtime-begintime);
		     System.out.print("\n"); 
		     return res;
		     
		}catch(IOException e){
			
			System.out.println(e.getMessage());
			return 5;			
		}
	}
	
	public void upload_alter(){
		File efile = new File(errorfile);
		if(pre_len==-1)
			pre_len=efile.length();
		LibMeasure lm=new LibMeasure();
		IntByReference count=new IntByReference();
		int ret=lm.TM_getErrorList(errorfile, count);
		switch(ret){
		case 0:
			System.out.println("sucess to get errorlist!");
			break;
		case 1:
			System.out.println("internal error of libmeasure!");
			break;
		case 101:
			System.out.println("error of calling libmeasure!");
			break;
		default: 
			System.out.println("未知错误"+ret);
		}
		if(ret==0)
		{
			post_len=efile.length();
			if(post_len>pre_len)
			{
				if(send(pre_len,post_len)==1)
					pre_len=post_len;
				else
					System.out.println("error of uploading alert!");
			}
			else
				if(post_len<pre_len)
					pre_len=0;
		}
		//loadData();
		
	}
	
	public void run()
	{
		System.out.println("upload_alter thread begin");
		while(true)
		{		
			try {
				synchronized(this){
				int f1=Integer.parseInt(f);
				wait(f1*1000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			uploader.upload_alter();
			if(!uploader.start)
				break;
		}
		System.out.println("upload_alter thread stop");
	}
}
