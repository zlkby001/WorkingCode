package ui;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class jc_tcchost_test {
	
	
	//static{
	//	System.load("C:\\dwl.dll");
	//}
	
	//static String pub_key = "error";
	//static byte [] pub_key = new byte[65];//"error";
	
	//StringBuffer a=new StringBuffer("I am a ");
	public interface Tcchost extends Library { 
		
         
		Tcchost INSTANCE = (Tcchost)Native.loadLibrary("tcchost", Tcchost.class);
		 
		/* 1.Generate key */
		public int gen_Key(String filename);
		 
		/* 2.Send white list */
		public int send_WL(String filename); 

        /* 3.Send measurement value and get illegal list */
		public int send_MV(String filename_MV, String filename_IL);
        
	}
	
    public static void main(String[] args) {
    	//Testdll nt=(Testdll)Native.loadLibrary("testdll",Testdll.class);
    	//Dwl nt=(Dwl)Native.loadLibrary("dwl",Dwl.class);
    	//String file = "C:\\white_list2";
    	//nt.send_WL(file);
    	//Testdll1.INSTANCE.say(teststr);
    	System.out.println("Started!");
    	
    	
    	
    	int c = 1;
    	int ret = 0;
    	Scanner sc = new Scanner(System.in);
    	
    	//c = System.in.read();
    	while(c!=0){
    		c = sc.nextInt();
    		
    		if (c==1){
    			
    			
    			ret = Tcchost.INSTANCE.gen_Key("C:\\pub_key.txt");
    			//System.out.println("ret = "+ret);
    			//System.out.println("pub_key = "+pub_key);
    			if(ret!=0){
    				System.out.println("error!");
    			}
    			else{
    				readFileByBytes("C:\\pub_key.txt");
    				//System.out.println("pub_key long:"+pub_key.length());
    				
    			}
    				
    			continue;
    		}
    		
    		if (c==2){
    			
    			try {
    					//String na = new String("C:/你好/aaa.txt".getBytes("UTF8"),"GBK");
    			
    					String na = new String("C:\\你好\\aaa.txt");
    			File FileInfo = new File("FilePath");
				BufferedWriter output;
				
					output = new BufferedWriter(new FileWriter(FileInfo));
					output.write(na);
					output.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
    			//ret = Tcchost.INSTANCE.send_WL("C:\\whitelist.txt");
    			//String na = new String("C:\\whitelist.txt");//("C:\\你好\\aaa.txt");
    			//byte[] midbytes=na.getBytes("UTF8");
    			
    			
    			ret = Tcchost.INSTANCE.send_WL("C:\\aaa.txt");
    			System.out.println("ret = "+ret);
    			continue;
    		}
    		
    		if (c==3){
    			ret = Tcchost.INSTANCE.send_MV("C:\\measurevalue.txt","C:\\illegal_list.txt");
    			System.out.println("ret = "+ret);
    			continue;
    		}
    		
    	}
    	
    	System.out.println("Exited!");
 
    }
    
    
    public static void readFileByBytes(String fileName){ 
    	File file = new File(fileName); 
    	InputStream in = null; 
    	try { 
    		System.out.println("aaa"); 
    		in = new FileInputStream(file); 
    		int tempbyte;     		
    		while((tempbyte=in.read()) != -1){ 
    			//System.out.write(tempbyte);
    			//System.out.println(Integer.toHexString(tempbyte).toUpperCase());
    			System.out.print(String.format("%02x",tempbyte).toUpperCase());
    			
    			
    		}     		
    		in.close(); 
    	} 
    	catch (IOException e) { 
    		e.printStackTrace(); 
    		return ; 
    	}
    }    
   
}
