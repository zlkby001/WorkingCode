package ui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadProcess extends Thread {
	private int res;
	private ClientSocket sock;
	private String pubkey;
	private String whitelist_path="C://TCC//platform.xml";//"C://platform.xml";
	
	public int Getres()
    {
    	return res;
    }
	
	public DownloadProcess(ClientSocket socket,String pubkey,String whitelist_path) {
		sock = socket;
		res = -1;
		this.pubkey=pubkey;
		this.whitelist_path=whitelist_path;
	}
	public void run(){
		String RecvFile = whitelist_path;// ��ȡ����ʧ�ܵ��ļ�
		
		try{
			    DataOutputStream ps = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream())); 
			    DataInputStream inputStream = null; 	 
			    File outFile = new File(RecvFile);
				BufferedOutputStream rev = new BufferedOutputStream(
						new FileOutputStream(outFile));

			    System.out.println("Sending...");

			    int bufferSize = 1024; 
			    byte[] buf = new byte[bufferSize]; 
	//���Ӽ���ʱ��
			    long begintime = System.currentTimeMillis();
			   
	//===========================zhyjun========================================
			    ps.writeInt(2);
				ps.flush();	

				byte[] buf1=pubkey.getBytes();
				ps.writeInt(buf1.length);
				ps.flush();
				ps.write(buf1,0,buf1.length);
				ps.flush();	
	//===========================zhyjun========================================		    

			     System.out.println("\nFinished\n");
			     
			     try { 
			    	inputStream = sock.getMessageStream(); 
			    	byte[] res_byte = new byte[1024];
			    	res = inputStream.readInt();
			    	//inputStream.read(res_byte,0,8);	    			    	
			    	//res = byteArray2Int(res_byte,0);
			    	int totallength=0;//���ܵ��İ����ܹ�����
				      int filelength=0;
				      if(res==0){//���ذ���ɹ�
				          //inputStream=sock.getMessageStream();
				          filelength=inputStream.readInt();//���ܰ���ĳ���
				          System.out.print("���ܰ���ĳ���Ϊ��"+filelength+"\n");
				          //��ʼѭ�������ļ���ÿ�ν���1024�ֽ�
				          int length=0;
				          while(true){        
				           //inputStream=sock.getMessageStream();
				           length=inputStream.read(res_byte);
				           if(length<0){
				            System.out.print("���ذ���ʧ��\n");
				            res=1000;
				            break;
				           }
				           rev.write(res_byte, 0, length);//д�뵽�ļ�
				           totallength+=length;
				           if(totallength==filelength){
				            System.out.print("���ذ���ɹ�\n");
				            res=1001;
				            break;
				           }
				          
				          }//end while
				          
				         }//end if
				      rev.close();  //java �� ͨ��outputstream���ļ���д�����֮��һ��Ҫclose��stream�����򲻻�д��
			     } catch (Exception e) { 
			    	 System.out.print("��ȡ����˽��ʧ��\n"); 
			    	 e.printStackTrace();
			    	 res = 104; 
			     } 
			     sock.shutDownConnection();
			     
	//����ʱ������		     
			     long endtime = System.currentTimeMillis();
			     System.out.print("ʱ�仨�ѣ�"); 
			     System.out.print(endtime-begintime);
			     System.out.print("\n"); 
			     
			}catch(IOException e){
				
				System.out.println(e.getMessage());
				res= 101;
			}
	}
}
