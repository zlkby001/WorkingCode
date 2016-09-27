package ui;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;




import com.sun.jna.Library;
import com.sun.jna.Native;

public class PTM_connect {
	
	//static public boolean hasPTM = true;
	
	static public String TCM_Manufacture = "NULL";
	
	static public String TCM_Version = "NULL";	
	
	static public byte[] PTM_nonce;
	
	static public boolean read_pubEK_ok = true;
	
	static public boolean read_pubky_ok = true;
	
	static public String pubkey = "Read Error!";
	static public String pubEK = "Read Error!";
	static public UploadAlertProcess p;
	//static public UsbDaemonProcess usb;
	
	/*
	static public void connect_test(){
		Socket socket = new Socket();  
		try{
			socket.connect(new InetSocketAddress("192.168.7.2", 2888),1000);  
		}
		catch(IOException e){
			hasPTM = false;			
		}
		
		try{
			socket.close(); 
		}
		catch(IOException e){
					
		}		
	}
	
	static public void Save_pubkey(String pubkey){
		File PubKeyInfo = new File("PubKey");
		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter(PubKeyInfo));
			output.write(pubkey);
			output.close();
		} catch (IOException e2) {			
			e2.printStackTrace();
			//return -1;
		}
		//return 0;
	}
	*/
	static public void Load_pubkey(){
		File PubKeyInfo = new File ("C:\\TCC\\PubKey");//"C:\\windows\\PubKey");
		//ring pubkey=new String();
		if(!PubKeyInfo.exists())
		{
			read_pubky_ok = false;
			pubkey = "Read Error!";
			return ;
		}
			
		else
		{
		    try {
		       	BufferedReader br = new BufferedReader((new InputStreamReader(
		    			new FileInputStream(PubKeyInfo))));
		       	pubkey = br.readLine();
		    	if(pubkey==null||pubkey=="Read Error!")
		    	{
		    		read_pubky_ok = false;
		    		pubkey = "Read Error!";
		    	}
		    	
		    	br.close();
		       } catch (Exception ex) {
		        ex.printStackTrace();
		       }
		    return ;
		}
		
	}
	
	static public void Load_pubEK(){
		File PubEKInfo = new File ("C:\\TCC\\pub_EK");//"C:\\windows\\pub_EK");
		//ring pubkey=new String();
		if(!PubEKInfo.exists())
		{
			read_pubEK_ok = false;
			pubEK = "Read Error!";
			return ;
		}
			
		else
		{
		    try {
		       	BufferedReader br = new BufferedReader((new InputStreamReader(
		    			new FileInputStream(PubEKInfo))));
		       	pubEK = br.readLine();
		    	if(pubEK==null||pubEK=="Read Error!")
		    	{
		    		read_pubEK_ok = false;
		    		pubEK = "Read Error!";
		    	}
		    	
		    	br.close();
		       } catch (Exception ex) {
		        ex.printStackTrace();
		       }
		    return ;
		}		
	}
	
	static public void Load_TCM_info(){
		File TCMInfo = new File ("C:\\TCC\\TCM_info.txt");//"C:\\windows\\TCM_info.txt");
		//String buff=new String();
		if(!TCMInfo.exists())
		{
			TCM_Manufacture = "NULL";
			TCM_Version = "NULL";
			return ;
		}
			
		else
		{
		    try {
		       	BufferedReader br = new BufferedReader((new InputStreamReader(
		    			new FileInputStream(TCMInfo))));
		       	TCM_Manufacture = br.readLine();
		    	if(TCM_Manufacture==null||TCM_Manufacture=="NULL")
		    	{
		    		TCM_Manufacture = "NULL";
		    		return;
		    	}
		    	
		    	TCM_Version = br.readLine();
		    	if(TCM_Version==null||TCM_Version=="NULL")
		    	{
		    		TCM_Version = "NULL";
		    		return;
		    	}
		    	
		    	br.close();
		       } catch (Exception ex) {
		        ex.printStackTrace();
		       }
		    return;
		}
		
	}
	/*
	static public int PTM_gen_Key(StringBuffer pubkey){		
		
		int ret = 0;
		ret = PTM_cmd.INSTANCE.gen_Key("pub_key");
		
		if(ret!=0){
			return -1;
		}
		else{
			readFileByBytes("pub_key",pubkey);			
			deleteFile("pub_key");			
			return 0;
		}			
	}
	
	static public int PTM_send_WL(String filename){		
		
		int res = -1;
		try{			
			byte[] midbytes = filename.getBytes("gbk");
			//System.out.println("filename.length() = "+filename.length());
			//System.out.println("midbytes.length() = "+midbytes.length);
			
			res =  PTM_cmd.INSTANCE.send_WL(midbytes.length,midbytes);
		
		}
		catch(IOException e){			
		}	
		
		return res;	
		//return PTM_cmd.INSTANCE.send_WL(filename);
	}
	
	static public int PTM_send_MV(String filename_MV, String filename_IL){		*/
		
		/*
		int res = -1;
		try{			
			byte[] midbytes1 = filename_MV.getBytes("gbk");
			byte[] midbytes2 = filename_IL.getBytes("gbk");
			res =  PTM_cmd.INSTANCE.send_MV(midbytes1.length, midbytes1, midbytes2.length, midbytes2);
		
		}
		catch(IOException e){			
		}	
		
		return res;	*/
	//	return PTM_cmd.INSTANCE.send_MV(filename_MV, filename_IL);
	//}
	
	static public int TCM_sign_N(byte[] nonce){		
		
		
		return PTM_cmd.INSTANCE.sign_Nonce(nonce);
	}
	
	
	
	public interface PTM_cmd extends Library { 
		
        
		PTM_cmd INSTANCE = (PTM_cmd)Native.loadLibrary("tcchost", PTM_cmd.class);
		 
		/* 1.Generate key */
		//public int gen_Key(String filename);
		 
		/* 2.Send white list */
		//public int send_WL(int filenamelen, byte[] filename); 

        /* 3.Send measurement value and get illegal list */
		//public int send_MV(String filename_MV, String filename_IL);
		
		/* 4.Send nonce and get the signature of nonce */
        public int sign_Nonce(byte[] nonce);
        
	}
	
	public static void readFileByBytes(String fileName,StringBuffer pubkey){ 
    	File file = new File(fileName); 
    	InputStream in = null; 
    	try { 
    		//System.out.println("以字节为单位读取文件内容，一次读一个字节："); 
    		// 一次读一个字节 
    		in = new FileInputStream(file); 
    		int tempbyte;     		
    		while((tempbyte=in.read()) != -1){ 
    			//System.out.write(tempbyte);
    			//System.out.println(Integer.toHexString(tempbyte).toUpperCase());
    			pubkey.append(String.format("%02x",tempbyte).toUpperCase());    			
    			
    			//System.out.print(String.format("%02x",tempbyte).toUpperCase());			
    			
    		}     		
    		in.close(); 
    	} 
    	catch (IOException e) { 
    		e.printStackTrace(); 
    		return ; 
    	}
    } 
    
    public static boolean deleteFile(String sPath) {  
        boolean flag = false;  
        File file = new File(sPath);  
        // 路径为文件且不为空则进行删除  
        if (file.isFile() && file.exists()) {  
            file.delete();  
            flag = true;  
        }  
        return flag;  
    } 
	
}
