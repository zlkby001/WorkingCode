package ui;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegisterProcess extends Thread {
	private int res;
	private ClientSocket sock;
	private UserBean user;
	
	public int Getres()
    {
    	return res;
    }
	
	public RegisterProcess(ClientSocket socket,UserBean user) {
		sock = socket;
		this.user = user;
		res = -2;
	}
	
	public void run(){
		try{
		    DataOutputStream ps = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream())); 
		    DataInputStream inputStream = null; 
		    System.out.println("Sending...");
		    int bufferSize = 1024; 
		    byte[] buf = new byte[bufferSize]; 
//增加计算时间
		    long begintime = System.currentTimeMillis();
		    ps.writeInt(0);
			ps.flush();	
			
			//byte[] buf1=user.getName().getBytes();
			byte[] buf1=user.getName().getBytes("GBK");
			//byte[] buf3=user.getName().getBytes("");
			ps.writeInt(buf1.length);
			ps.flush();
			ps.write(buf1,0,buf1.length);
			ps.flush();	
			
			buf1=user.getLab().getBytes("GBK");
			ps.writeInt(buf1.length);
			ps.flush();
			ps.write(buf1,0,buf1.length);
			ps.flush();
			
			buf1=user.getIp().getBytes("GBK");
			ps.writeInt(buf1.length);
			ps.flush();
			ps.write(buf1,0,buf1.length);
			ps.flush();
			
			buf1=user.getMac().getBytes("GBK");
			ps.writeInt(buf1.length);
			ps.flush();
			ps.write(buf1,0,buf1.length);
			ps.flush();
			
			buf1=user.getOs().getBytes("GBK");
			ps.writeInt(buf1.length);
			ps.flush();
			ps.write(buf1,0,buf1.length);
			ps.flush();
			
			buf1=user.getManufacture().getBytes("GBK");
			ps.writeInt(buf1.length);
			ps.flush();
			ps.write(buf1,0,buf1.length);
			ps.flush();
			
			buf1=user.getSequence().getBytes("GBK");
			ps.writeInt(buf1.length);
			ps.flush();
			ps.write(buf1,0,buf1.length);
			ps.flush();
			
			buf1=user.getPubkey().getBytes("GBK");
			ps.writeInt(buf1.length);
			ps.flush();
			ps.write(buf1,0,buf1.length);
			ps.flush();
			
			buf1=user.getEK().getBytes("GBK");
			ps.writeInt(buf1.length);
			ps.flush();
			ps.write(buf1,0,buf1.length);
			ps.flush();
			
			System.out.println("\nFinished\n");
			 try { 
			    	inputStream = sock.getMessageStream(); 
			    	res = inputStream.readInt();
			 } catch (Exception e) { 
		    	 System.out.print("获取服务端结果失败\n"); 
		    	 e.printStackTrace();
		    	 res = 104; 
		     } 
		     sock.shutDownConnection();
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
