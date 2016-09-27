package ui;

import java.util.ArrayList;
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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.swt.widgets.MessageBox;
import org.forms.MainForm;
import org.forms.StatusAlertLog;
import org.forms.XMLReader;
import org.forms.MainForm.TimeRefresh;
import org.wsoperate.AlertServiceStub;
import org.wsoperate.DownloadUsbServiceStub;
import org.wsoperate.AlertServiceStub.Uploadalert;
import org.wsoperate.DownloadUsbServiceStub.Downloadusb;

import com.sun.jna.ptr.IntByReference;

public class AlertUploader {
	private String pubkey;
	private int res;
	private static String errorfile="C://TCC//errorlist.log";//"C://errorlist.log";
	//private ArrayList error_list = new ArrayList();
	public static long pre_len=(new File(errorfile).length());
	public static long post_len=-1;
	public static boolean start=true;
	private static File efile;
	private static String errorfileplus = "C:/TCC/errorlistall.log";
	private static int lineNum;
	//public static Thread d = new TimeRefresh();
	public AlertUploader(String pubkey){
		this.pubkey=pubkey;
		res=-1;
		start=true;
		
		//pre_len=(new File(errorfile).length());
		
	}
	
	private int send()
	{
		String ip = ServerIPaddress.getIp();
		//int port = ServerIPaddress.getPort();
		//int port=4999;
		try { 
			 
			 {
				 XMLReader xmlreader = new XMLReader();
				 String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("alertop");
				 FileReader fr=new FileReader(errorfile);
				 BufferedReader br=new BufferedReader(fr);
				 //br.skip(pre_len);
				 String s=null;
				 String ss="";
				 while((s=br.readLine())!=null)
				 {
					 ss+=s;
					 ss+=";;";
					 //String s=br.readLine();
					 //System.out.println(s);
				 }
				 //System.out.println(ss);
				 AlertServiceStub stub = new AlertServiceStub(null, trans_value);
				Uploadalert infofunction = new Uploadalert();
				ss=PTM_connect.pubkey+"::"+ss;
				infofunction.setIn0(ss);
					boolean result = stub.uploadalert(infofunction).getOut();
					
					if(!result)
						return 4;
					else{
						TimeRefresh();
						return 0;}
					
			 }
			
			 /*
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
		          
		          */
		 } catch (Exception e) { 
			 System.out.print("上传报警日志失败\n"); 
			 e.printStackTrace();
			 return 4;
			
		 } 
		 
		 //sock.shutDownConnection();
		 
//����ʱ������		     
		 //long endtime = System.currentTimeMillis();
		// System.out.print(endtime-begintime);
		 //System.out.print("\n"); 
		 //return res;
	}

		public void TimeRefresh() {
		
				
				 efile = new File(errorfile);
				 StatusAlertLog.resetTotal();
					
				 if(!efile.exists()){
					 try {
						efile.createNewFile();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
				FileReader fr  = null;
				FileReader frr  = null;
				FileWriter fw = null;
				BufferedReader br = null;
				BufferedReader brr = null;
				FileWriter errorfileall = null;
				try {
					 errorfileall = new FileWriter(
			    			  errorfileplus, true);
					  fr = new FileReader(efile);
						frr = new FileReader(efile);
						br = new BufferedReader(frr);
						brr =new BufferedReader(fr);
					 String line = null;
					  lineNum = 0;
					 String newline = null;
					 
					 while ((line = br.readLine()) != null) {
						 // System.out.println("br.readLine()"+br.readLine());
						 lineNum = lineNum+1;
					    //  System.out.println("[" + lineNum + "]" + line);// 行号和行内容
					 }
					 System.out.println("linenum"+lineNum);
					      if(lineNum>0){	
					    	 // System.out.println("newline1"+newline);
					    	//  System.out.println("br.readLine()"+br.readLine());
					    	 while( (newline=brr.readLine())!=null){
					    		//  System.out.println("newline2"+newline);
					    	//	System.out.println(newline);
					    		 errorfileall.write(newline+"\r\n");
					    		// errorfileall.close();
					    	 }
					    	 
					    	 fw = new FileWriter(efile);
					    	 fw.write("");
					    	// System.out.println("new line"+newline);
					      } 
					    	
					      
					   
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("文件不存在：");
				//	efile = new File(errorfile);
					return;
				}catch (IOException ioe) {
					
					System.out.println("文件读取错误：" );

					return;

				} finally {

					try {
						if(errorfileall!=null){
							errorfileall.close();
							errorfileall=null;
						}
						if(fw!=null){
							fw.close();
							fw = null;
						}
						if(brr!=null){
							brr.close();
							brr=null;
						}

						if (br != null) {

							br.close();

							br = null;
						}
						if (frr != null) {

							frr.close();

							frr = null;
						}
						if (fr != null) {

							fr.close();

							fr = null;
						}
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
				
//				try {
//					sleep(60000);
//			
//				} catch (Exception e) {
//					e.printStackTrace(); 
//				}					
			}
			
		
	
	public void upload_alter(){
		//File efile = new File(errorfile);
		//if(pre_len==-1)
			//pre_len=efile.length();
		//System.out.println("begin!");
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
			File efile=new File(errorfile);
			post_len=efile.length();
			if(post_len>pre_len)
			{
				if(send()==0)   //报警日志bug
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

}
