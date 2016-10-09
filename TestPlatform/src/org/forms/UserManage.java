package org.forms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.axis2.client.Options;
import org.wsoperate.AuditLogServiceStub;

import ui.PTM_connect;

public class UserManage {
	private static String result = null;
	public static String pwd_download(){
		
	//int i =0;
		     
	//	return file.delete();//delete LogFile
		
		
	
		try { 
			 
			 
				 XMLReader xmlreader = new XMLReader();
				 String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("auditlogop");
		
				 AuditLogServiceStub stub = new AuditLogServiceStub(null, trans_value);
				
				 AuditLogServiceStub.Downloadadminpasswd infofunction = new AuditLogServiceStub.Downloadadminpasswd ();
//				 if(stub.downloadadminpasswd(infofunction).getOut()){
//					 return null;
//					 
//				 }
				
				 Options options = stub._getServiceClient().getOptions();
				 options.setTimeOutInMilliSeconds(2000);//设置超时(单位是毫秒)
				// stub._getServiceClient().setOptions(options);
				 
				 result = stub.downloadadminpasswd(infofunction).getOut();
				 if(result==""){
				System.out.println("result"+result+"null");
				 }
				
					
				  
					//	return result;
				
						//return true;//0;
			 		 
			
		 } catch (Exception e) { 
			 System.out.print("下载失败\n"); 
			 e.printStackTrace();
			// return true;//4;
			
		 }
		return result; 
		
		
				
		//delete LogFile
		
	}
}
