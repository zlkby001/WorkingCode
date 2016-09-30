package cn.ac.sklois.imm.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

//import com.sun.jna.Library;
//import com.sun.jna.Native;
//import com.sun.jna.ptr.IntByReference;

public class AuthVerify {
/*	
	public interface libverify extends Library{

		//注意java类库或者方法中已经有verify了，如果使用verify.dll，则调用方法时会提示找不到，因此修改为libverify
		libverify INSTANCE = (libverify)Native.loadLibrary("libverify", libverify.class);
		
		public int verify_license();
	}
	
	public int Auth_Verify()
	{
		//libverify lv=(libverify)Native.loadLibrary("verify",libverify.class);
		//if(lv==null)
			//return 101;
		return libverify.INSTANCE.verify_license();
	}
	*/
	public static String WinSigFile = "C:/WINDOWS/authorised_server";
	public static String WinSigDir = "C:/WINDOWS";
	//公钥数据
	private static byte[] pubKey = {
		48, -126, 1, 34, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -126, 
		1, 15, 0, 48, -126, 1, 10, 2, -126, 1, 1, 0, -92, -89, -111, -93, 27, -56, 3, 110, 
		-4, 91, 24, 41, 71, -46, -118, 92, 80, -123, -49, -91, -71, 48, -3, -59, 58, -110, -64, 
		115, -3, 88, -44, -32, -35, 12, 87, 16, -99, -53, 77, -72, -27, -38, 105, -116, 76, -49, 
		93, -59, 101, -23, 117, -97, 112, 100, -123, 107, 58, -28, 102, -124, 47, 11, -70, 8, 89,
		-117, 54, -1, 16, 122, 86, 122, -62, -66, -120, 70, -35, 89, 78, -58, -17, 90, 12, 67,
		-110, -73, -76, 39, 60, -13, 57, 72, -92, -60, -76, 82, 123, -40, 15, -111, -43, -69, 55, 75,
		77, 115, -113, 80, -94, 100, -108, -1, 92, -19, -99, -59, -109, -9, 25, 67, 64, 34, -103, -36,
		37, -33, 85, 10, 0, -1, -87, -21, -5, -93, -83,	102, 125, -64, 86, 115, -1, -94, 38, 57, 17, -102,
		-41, -117, 45, 98, -115, -43, 0, 56, 97, -65, -11, -5, -128, 74, -42, 102, -128, -25, 98, 29, 14, 37,
		78, 64, -39, 44, -120, 103, -58, -104, 38, -94, -93, -47, 61, 24, 4, -96, -64, -67, 64, 102, 26, 100, -26,
		71, 89, 72, -24, 121, 95, 66, 64, -117, -33, 49, -112, -41, 121, 87, -11, -127, 21, 106, 7, 119, 70, -114,
		-92, -68, 111, -98, -17, 98, 3, -83, -115, -31, -6,	26, 5, 26, 50, 75, 11, -63, -1, 120, -79, -37, -108,
		-115, 4, -84, -74, 82, 12, -96, 22, 31, 124, 120, -115, -25, -35, 37, 118, 112, 52, 19, 2, 3, 1, 0, 1
		};
	
	private PublicKey PubkeyforVerif = null;		
	
	
	
	private static String host_type = null;
	//private static String auth_time_flag = null;
	private static String expired_time = null;
	private static String host_tag = null;
	private static String TCM_EK = null;
	
	private static String Time_now = null;
	private static String Time_trial_exp = null;
	private static int res = 0;
	
	/*private boolean ExportEK(){
	
		
		Runtime rt = Runtime.getRuntime();
		Process p = null;		
		
		try{
						
			p = rt.exec("exportEK.exe");
			//p.waitFor();
			
			
			final InputStream is1 = p.getInputStream();   
			
			final InputStream is2 = p.getErrorStream();  
			
			
			new Thread() {  
				public void run() {  
					BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));  
					try {  
						String line1 = null;  
						while ((line1 = br1.readLine()) != null) {  
						if (line1 != null){}  
						}  
					} catch (IOException e) {  
						e.printStackTrace();  
					}  
					finally{  
						try {  
							is1.close();  
						} catch (IOException e) {  
							e.printStackTrace();  
						}  
					}  
				}  
			}.start();  
                           
			new Thread() {   
				public void  run() {   
					BufferedReader br2 = new  BufferedReader(new  InputStreamReader(is2));   
					try {   
						String line2 = null ;   
						while ((line2 = br2.readLine()) !=  null ) {   
							if (line2 != null){}  
						}   
					} catch (IOException e) {   
						e.printStackTrace();  
					}   
					finally{  
						try {  
							is2.close();  
						} catch (IOException e) {  
							e.printStackTrace();  
						}  
					}  
				}   
			}.start(); 
			
				
                                
			res = p.waitFor();  
			p.destroy();   	
			
			
		} catch(Exception e){
			try {
				p.getErrorStream().close();
				p.getInputStream().close();
				p.getOutputStream().close();
			} catch (Exception e1) {
			}
			
			return false;
		}
		if(res == 1)
			return false;
		
		return true;
	}*/
	
	//获取TCM EK 公钥
	private boolean GetpubEK(){
		byte[] EKtemp = new byte[1024];
		byte[] pubEK;
		int readEKLength = 0;
		InputStream EKfile = null; 
		
		
		
		
		try {
			EKfile = new FileInputStream("C:/TCC/pub_EK");
			readEKLength = EKfile.read(EKtemp);
			EKfile.close();
			
		} catch (FileNotFoundException e) {
			System.out.println( "pu_EK file not found!" );
	  		return false;
		} catch (IOException e) {
			System.out.println("Read pu_EK file Error!" );
	  		return false;
		} 
		
		pubEK = new byte[readEKLength];
		System.arraycopy(EKtemp, 0, pubEK, 0, readEKLength);	
		host_tag = new String(pubEK);
		return true;
	}
	
	//获取CPUID
	/*
	private boolean GetCPUID(){
		
		String result = "";
		//String os = getOsName();  
		  
		try {
			
		    File file = File.createTempFile("tmp", ".vbs");  
		    file.deleteOnExit();  
		    FileWriter fw = new java.io.FileWriter(file);  
		   
		    String vbs = "On Error Resume Next \r\n\r\n" + "strComputer = \".\"  \r\n"  
		    		+ "Set objWMIService = GetObject(\"winmgmts:\" _ \r\n"  
		    		+ "    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") \r\n"  
		    		+ "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")  \r\n "  
		    		+ "For Each objItem in colItems\r\n " + "    Wscript.Echo objItem.ProcessorId  \r\n "  
		    		+ "    exit for  ' do the first cpu only! \r\n" + "Next                    ";  
		   
		    fw.write(vbs);  
		    fw.close();  
		    Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());  
		    String tmppath=file.getPath();
		    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));  
		    String line;  
		    while ((line = input.readLine()) != null) {  
		         	result += line;  
		    }  
		    input.close();  
		    file.delete();  
		} catch (Exception e) { 
			System.out.println("Get CPUID Error!" );
			return false;
		}  
		
		if (result.trim().length() < 1 || result == null) {  
			System.out.println("Get CPUID Error!" );
			return false;  
		}  
		
		host_tag = result.trim();
		return true;  	
	}
	*/
	
	private boolean GetCPUID(){
		 Process process;
		 String serial="";
		try {
				process = Runtime.getRuntime().exec(
				new String[] { "wmic", "cpu", "get", "ProcessorId" });
				process.getOutputStream().close();
			    Scanner sc = new Scanner(process.getInputStream());
			    String property = sc.next();
			    serial = sc.next();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			host_tag = serial.trim();
			return true;
	}
	
	private void GetTimeNow(){
		
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = new Date();
		Time_now = dft.format( beginDate ); 		
	}
	
	//计算试用期到期日期
	private boolean GetTimeTrialExpired(){		
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) + 30);
		try {
			Date endDate = dft.parse(dft.format(date.getTime()));
			
			//String begin = dft.format( beginDate ); 
			//System.out.println(begin); 
			
			Time_trial_exp = dft.format( endDate ); 
			//System.out.println(expiredtime);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			
			return false;
		}
		return true;		
	}
	
	//加载公钥
	private boolean LoadPubKey(){		
		//byte[] temp = new byte[2048];
		//int readLength = 0;
		//byte[] SigData;
		
		//InputStream inputPubKey = null; 
		//InputStream inputSignature = null; 
		 
		
		try {
			//inputPubKey = new FileInputStream("PubKey");
			//readLength = inputPubKey.read(temp); 
			//inputPubKey.close();
			
			//byte[] pubKey = new byte[readLength];
			//System.arraycopy(temp, 0, pubKey, 0, readLength);
			//priKey = temp;
			
			//System.out.println("inpubKey = " + pubKey.length);
			//System.out.println("inpubKey = " + Arrays.toString(pubKey));
			
		
			// 构造X509EncodedKeySpec对象
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(pubKey);
			// RSA算法
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// 取公钥匙对象
			PubkeyforVerif = keyFactory.generatePublic(bobPubKeySpec);
		  	
		} catch (Exception e) {	
			System.out.println("Get pubkey Error!" );
			return false;
		} 
	
		return true;
	}
	
	///验证签名
	// 返回值： 
	// -1: 验证出错或验证不通过
	
	// -3: 试用期已经过期
	// 0: 授权通过
	// time_trial_exp（8位十进制数字）: 试用期截止日期
	public int Verify(){
		String sigFile = WinSigFile;
		byte[] temp = new byte[2048];
		int readLength = 0;
		byte[] SigData;		
		
		//获得当前时间
		GetTimeNow();
		
		
		//加载公钥
		if(LoadPubKey() == false){
			return -1;
		}
		
		
		//读取签名文件
		InputStream inputSignature = null;
		
		//第一次使用（未授权）
		try {
			inputSignature = new FileInputStream(sigFile);
		} catch (FileNotFoundException e1) {			
			
			if(GetTimeTrialExpired()==false){
				System.out.println("Set trial expired time error!");
				return -1;
			}
			
			try {
				OutputStream writer = new FileOutputStream(sigFile);
				writer.write("X".getBytes("ISO8859-1"));
			    writer.write("\n".getBytes("ISO8859-1"));
		    	
		    	//writer.write("0".getBytes("ISO8859-1"));
		        //writer.write("\n".getBytes("ISO8859-1"));
		    	//writer.write(auth_time_flag);
		        //writer.write("\n");
		        writer.write(Time_trial_exp.getBytes("ISO8859-1")); 
		        writer.write("\n".getBytes("ISO8859-1"));		        
		        
		        writer.flush(); 
		        writer.close();  
			} catch (Exception e) {
				 System.out.println("Initial authorized file error!"); 
		    	 return -1;
			}
			
		}
		
		try{
			
			inputSignature = new FileInputStream(sigFile);
			
			readLength = inputSignature.read(temp); 
			inputSignature.close();
			
			byte[] b_host_type = new byte[1];
			byte[] b_auth_time_flag = new byte[1];
			byte[] b_expired_time = new byte[8];
			
			System.arraycopy(temp, 0, b_host_type, 0, 1);
			host_type =  new String(b_host_type,"ISO8859-1");
			
			//System.arraycopy(temp, 2, b_auth_time_flag, 0, 1);
			//auth_time_flag =  new String(b_auth_time_flag,"ISO8859-1");
			
			System.arraycopy(temp, 2, b_expired_time, 0, 8);
			expired_time =  new String(b_expired_time,"ISO8859-1");
			
			//试用期判定
			if(host_type.equals("X")){
				int time_now = Integer.parseInt(Time_now);
				int time_expired = Integer.parseInt(expired_time);				
				if((time_expired > time_now) || (time_expired == time_now) ){
					int time_trial_exp = (Integer.parseInt(expired_time));					
					return time_trial_exp;
				}else{
					return -3;
				}
			}
			if(host_type.equals("W")){
				int time_now = Integer.parseInt(Time_now);
				int time_expired = Integer.parseInt(expired_time);				
				if((time_expired > time_now) || (time_expired == time_now) ){
					int time_trial_exp = (Integer.parseInt(expired_time));					
					//return time_trial_exp;
				}else{
					return -3;
				}
			}
			
			//System.out.println(auth_time_flag);
			//System.out.println(expired_time);
			//System.out.println(host_tag);
			//System.out.println(expired_time);
			
			SigData = new byte[readLength-11];
			
			System.arraycopy(temp, 11, SigData, 0, readLength-11);
			//System.out.println(Arrays.toString(SigData));
			
			
			String inputforVerify;
				if(host_type.equals("W")){
				//读取CPUID
				if(GetCPUID() == false){
					return -1;
				}	
				
				//读取EK公钥
//				if(GetpubEK() == false){
//					return -1;
//				}
				inputforVerify = host_type + expired_time + host_tag;
			}
			
			//读取主机标识 并填充验证数据
			//读取CPUID
			if(GetCPUID() == false){
				return -1;
			}	
			inputforVerify = host_type + expired_time + host_tag;
			/*if(host_type.equals("A")){
				//读取CPUID
				if(GetCPUID() == false){
					return -1;
				}	
				
				//读取EK公钥
				if(GetpubEK() == false){
					return -1;
				}
				inputforVerify = host_type + expired_time + host_tag + TCM_EK;
				
			}else{
				//读取CPUID
				if(GetCPUID() == false){
					return -1;
				}	
				inputforVerify = host_type + expired_time + host_tag;
			}*/
			
			//System.out.println(host_type);
			//System.out.println(expired_time);
			//System.out.println(host_tag);
			
			//String inputforVerify = host_type + auth_time_flag + expired_time + host_tag;
			//System.out.println(Arrays.toString(inputforVerify.getBytes()));
			Signature signatureChecker = Signature.getInstance("SHA1withRSA");
		  	signatureChecker.initVerify(PubkeyforVerif);
		  	
		  	signatureChecker.update(inputforVerify.getBytes("ISO8859-1"));
		  	//signatureChecker.update("yangbo".getBytes());
		  	//signatureChecker.update("tcwg".getBytes());
		  	
		   // 验证签名是否正常
		  	if (signatureChecker.verify(SigData)){
		  		System.out.println("RSA Verify: Successful!" );
		  		int time_trial_exp1 = (Integer.parseInt(expired_time));	
		  		if(host_type.equals("W"))
		  			return time_trial_exp1;
		  		else return 0;
		  		/*int time_now = Integer.parseInt(Time_now);
		  		int time_expired = Integer.parseInt(expired_time);
		  		if (expired_time.equals("99991231")){
		  			return 0;		  			
		  		}else if((time_expired > time_now) || (time_expired == time_now) ){
		  			return time_expired;
		  		}else{
		  			return -2;
		  		}*/
		  	}
		  	else{
		  		//System.out.println("!!!***");	
		  		System.out.println("Verify: Failed!" );
		  		return -1;
		  	}
		  		
	
		} catch (IOException e) {
			System.out.println("Read authorized file Error!" );
	  		return -1;
		} catch (Exception e) {
			System.out.println("Verify Error!" );
			return -1;
		}
		//return true;
	}
}