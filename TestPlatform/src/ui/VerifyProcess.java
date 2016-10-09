package ui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class VerifyProcess extends Thread
{
    private int res;
    private ClientSocket sock;
    private String pubkey;
    public VerifyProcess(ClientSocket socket,String pubkey)
    {
    	sock = socket;
		res = -1;
		this.pubkey=pubkey;
    }
    public int Getres()
    {
    	return res;
    }

    public void run()
    {

		String FILENAME = "C://TCC//platform.xml";//"C://platform.xml";
		//String FILENAME = "c:\\test.txt";
		String RecvFile = "C://TCC//result0";//"C://result0";//获取返回失败的文件
		
		try{

		 // sendfile
		    File myFile = new File (FILENAME);
		    int total_length = (int) myFile.length();
		    
		    File outFile=new File(RecvFile);//输出文件
		    
		    BufferedInputStream dis = new BufferedInputStream(new FileInputStream(myFile)); 

		    DataOutputStream ps = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream())); 
		    DataInputStream inputStream = null; 	 
		    
		    BufferedOutputStream rev=new BufferedOutputStream(new FileOutputStream(outFile));

		    System.out.println("Sending...");
		    System.out.println(myFile.length());
		    int bufferSize = 1024; 
		    byte[] buf = new byte[bufferSize];
		    int res1=0;
//增加计算时间
		    long begintime = System.currentTimeMillis();
		   
//===========================send code and pubkey========================================
		    ps.writeInt(1);
			ps.flush();	

			byte[] buf1=pubkey.getBytes();
			ps.writeInt(buf1.length);
			ps.flush();
			ps.write(buf1,0,buf1.length);
			ps.flush();	
//===========================send code and pubkey========================================
			
			
//==========================receive nonce===========================			
			try { 
		    	inputStream = sock.getMessageStream(); 
		    	//byte[] nonce = new byte[32];
		    	//res = inputStream.readInt();
		    	PTM_connect.PTM_nonce = new byte[32];
		        //int nonce_length=inputStream.readInt();
		    	res=inputStream.read(PTM_connect.PTM_nonce,0,32);
		    	//res=inputStream.read(nonce,0,32);		
		    	//PTM_connect.PTM_nonce = new String(nonce);	
		    	//System.out.print(PTM_connect.PTM_nonce+"\n"); 
		    	//System.out.print(PTM_connect.PTM_nonce.length+"\n");
		    	
		    	//FileOutputStream fos = new FileOutputStream("nonce_t");

		        //fos.write(PTM_connect.PTM_nonce);
		        //fos.close();
		     } catch (Exception e) { 
		    	 System.out.print("获取挑战随机数失败\n"); 
		    	 e.printStackTrace();
		    	 res = 105; 
		     } 
			
			
//==========================receive nonce===========================
			
//==========================generate and send signature====================
			//ps.writeInt(1);
			//ps.flush();
		     int exe_res = PTM_connect.TCM_sign_N(PTM_connect.PTM_nonce);
				if (exe_res!=0)
				{
					res = 11;
					System.out.print("TCM签名生成失败\n"); 
					return;
				}
				
				
				File sig = new File ("C:\\TCC\\signature");
				//String signature="FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
				String signature = new String();
				if(!sig.exists()){
					res = 11;
					System.out.print("TCM签名生成失败\n"); 
					return;
				}			
			else
			{/*
			    try {
			       	BufferedReader br = new BufferedReader((new InputStreamReader(
			    			new FileInputStream(sig))));
			       	signature = br.readLine();
			    	if(signature==null){
						res = 11;
						System.out.print("PTM签名生成失败\n"); 
						return;
					}				
			    	
			    	br.close();
			       } catch (Exception ex) {			    	   
			    	   res = 11;
			    	   System.out.print("PTM签名生成失败\n"); 
			    	   ex.printStackTrace();
			       }*/
				
				 ByteArrayOutputStream bos = new ByteArrayOutputStream((int)sig.length());  
			        BufferedInputStream in = null;  
			        try{  
			            in = new BufferedInputStream(new FileInputStream(sig));  
			            int buf_size = 64;  
			            byte[] buffer = new byte[buf_size];  
			            int len = 0;  
			            while(-1 != (len = in.read(buffer,0,buf_size))){  
			                bos.write(buffer,0,len);  
			            }  
			            buf1 = bos.toByteArray();  
			        }catch (IOException e) {
			        	res = 11;
				    	System.out.print("TCM签名生成失败\n"); 
			            e.printStackTrace();  
			            
			        }
			}			
			//System.out.print(signature);
			//buf1=signature.getBytes();
			ps.writeInt(buf1.length);
			ps.flush();
			ps.write(buf1,0,buf1.length);
			ps.flush();
	//==========================generate and send signature====================
			
			
    //===========================zhyjun send SML========================================	
			ps.writeInt((int)myFile.length() );
		    //ps.writeLong(myFile.length());
		    //ps.write(this.tolh(total_length));
		    ps.flush();
		    //ps.writeChars(String.valueOf(myFile.length()));
		    
		        
		     //ps.writeLong((long) myFile.length()); 
		     //ps.flush();
		     
		    
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

		    	 //System.out.println(read);
		    	 //System.out.println("\n");
		    	 //System.out.write(buf);
		    	 //System.out.println("\n");
		    	 
		    	 ps.flush();
		    	 
		     } 
		     
		     int left_length = (int) total_length%bufferSize;
		     //System.out.print(left_length);
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
		     System.out.println("\nFinished\n");	
//===========================zhyjun send SML========================================
			

			
//==========================receive authentication result of nonce signature=====================
			try { 
		    	inputStream = sock.getMessageStream(); 
		    	res1 = inputStream.readInt();		      		        		       		    	
		     } catch (Exception e) { 
		    	 System.out.print("获取签名验证结果失败\n"); 
		    	 e.printStackTrace();
		    	 res = 106; 
		     } 
//==========================receive authentication result of nonce signature=====================
			
			
			

			if(res1==8)
		{
				System.out.println("\n身份+完整性验证成功\n");
		
				
		    	     		    		     
		
		     
		     
		     
		     //=============================接收组件度量结果======================
		     try { 
		    	inputStream = sock.getMessageStream(); 
		    	byte[] res_byte = new byte[1024];
		    	res = inputStream.readInt();
		    	//inputStream.read(res_byte,0,8);	    			    	
		    	//res = byteArray2Int(res_byte,0);
		    	 int totallength=0;//接受到的总共长度
		      int filelength=0;
		      if(res==1){//如果验证不正确，则接受返回错误的文件
		          //inputStream=sock.getMessageStream();
		          filelength=inputStream.readInt();//接受长度
		          
		          System.out.print("接受验证失败的长度为："+filelength+"\n");
		          //System.out.println("Line:"+inputStream.readLine());
		          //开始循环接受文件，每次接受1024字节
		          int length=0;
		          while(true){        
		           //inputStream=sock.getMessageStream();
		           length=inputStream.read(res_byte);
		           //System.out.println("Res_byte:"+res_byte.toString());
		           if(length<0){
		            System.out.print("接受文件失败\n");
		            res=1000;
		            break;
		           }
		           rev.write(res_byte, 0, length);//写入到文件
		           totallength+=length;
		           if(totallength==filelength){
		            System.out.print("接受文件成功\n");
		            res=1001;
		            break;
		           }
		          
		          }//end while
		          
		         }//end if
		         
		         rev.close();

		    	
		     } catch (Exception e) { 
		    	 System.out.print("获取验证结果失败\n"); 
		    	 e.printStackTrace();
		    	 res = 104; 
		     } 
		}
			else if(res1==9){
				System.out.print("身份+完整性验证失败\n");
				res=9;
			}
			else{
				System.out.print("接收身份验证结果码异常\n");
				res=10;
			}

		     sock.shutDownConnection();
//增加时间计算点		     
		     long endtime = System.currentTimeMillis();
		     System.out.print("时间花费："); 
		     System.out.print(endtime-begintime);
		     System.out.print("\n"); 
		     
		}catch(IOException e){			
			System.out.println(e.getMessage());
			res= 101;
		}
		
    }

}
