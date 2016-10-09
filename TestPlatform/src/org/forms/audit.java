package org.forms;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.wsoperate.AuditLogServiceStub;


//import org.forms.AuditLogServiceStub.uploadAuditLog;
import ui.PTM_connect;




/*
 *
User、type 与 action对应关系表（user：0—普通用户，1—超级用户）
User (int)	Type (int)	Action (string)
0	1	注册
1	1	注册
0	2	验证—状态显示
1	2	验证—状态显示
1	2	验证—进程管控
1	3	白名单下载—进程管控
1	3	白名单下载—度量更新
1	4	开启进程管控—进程管控
1	4	关闭进程管控—进程管控
1	4	开启进程管控—度量更新
1	4	关闭进程管控—度量更新
1	4	开启进程管控—设置
1	4	关闭进程管控—设置
1	5	开启USB管控—设置
1	5	关闭USB管控—设置
1	6	开启监控自启动—设置
1	6	关闭监控自启动—设置
 *
 */


public class audit {

	
	
	public static boolean log_record(String user, String name,String type, String action){		
		
		String LogFile = "C:/TCC/audit_log";		
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time= df.format(new Date());// new Date()为获取当前系统时间
	
		String LogContent = user + "#" + name+ "#" + type + "#" +action + "#" + time + "\n";
						
		try{
	            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
	            FileWriter writer = new FileWriter(LogFile, true);	            
	            writer.write(LogContent);
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }				
		return true;						
	}
	

	public static boolean log_upload(){
		
		File file=new File("C:/TCC/audit_log");    
		if(!file.exists())    
			return true;
		     
	//	return file.delete();//delete LogFile
		
		
	
		String LogFile = "C:/TCC/audit_log";	
	//	long pre_len=(new File(LogFile).length());
		//String ip = ServerIPaddress.getIp();
		//int port = ServerIPaddress.getPort();
		//int port=4999;
		try { 
			 
			 {
				 XMLReader xmlreader = new XMLReader();
				 String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("auditlogop");
				 
				 FileReader fr=new FileReader(LogFile);
				 BufferedReader br=new BufferedReader(fr);
				// br.skip(pre_len);
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
				 AuditLogServiceStub stub = new AuditLogServiceStub(null, trans_value);
				
				 AuditLogServiceStub.UploadAuditLog infofunction = new AuditLogServiceStub.UploadAuditLog ();
				// System.out.println("ss1"+ss);
				 ss=PTM_connect.pubkey+"::"+ss;
				// System.out.println("ss"+ss);
				 infofunction.setIn0(ss);
					boolean result = stub.uploadAuditLog(infofunction).getOut();
					if(!result)
						return false;//4;
					else
						try{
				           
				            FileWriter writer = new FileWriter(LogFile);	            
				            writer.write("");
				            writer.close();
				        } catch (IOException e) {
				            e.printStackTrace();
				            return false;
				        }	
						return true;
				
						//return true;//0;
			 }			 
			
		 } catch (Exception e) { 
			 System.out.print("上传报警日志失败\n"); 
			 e.printStackTrace();
			 return false;//4;
			
		 } 
		
		
				
		//delete LogFile
		
	}
}
