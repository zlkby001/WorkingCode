package ui;

import java.net.*; 
import java.io.*; 
public class ClientSocket { 
	private String ip; 
	private int port; 
	private Socket socket = null; 
	DataOutputStream out = null; 
	DataInputStream getMessageStream = null;
	
	
	public ClientSocket(String ip, int port) { 
	this.ip = ip; 
	this.port = port; 
	}
	
	
/** *//** 
* ����socket���� 
* 
* @throws Exception 
* exception 
*/ 
public void CreateConnection(int timeout) throws Exception { 
try { 
//socket = new Socket(ip, port);
socket = new Socket();
socket.connect(new InetSocketAddress(this.ip, this.port), timeout);

} catch (Exception e) { 
//e.printStackTrace(); 
if (socket != null) 
socket.close(); 
throw e; 
} finally { 
} 
} 
public void sendMessage(String sendMessage) throws Exception { 
try { 
	out = new DataOutputStream(socket.getOutputStream()); 
if (sendMessage.equals("Windows")) { 
out.writeByte(0x1); 
out.flush(); 
return; 
} 
if (sendMessage.equals("Unix")) { 
out.writeByte(0x2); 
out.flush(); 
return; 
} 

if (sendMessage.equals("Linux")) { 
out.writeByte(0x3); 
out.flush(); 
} else { 
out.writeUTF(sendMessage); 
out.flush(); 
} 
} catch (Exception e) { 
e.printStackTrace(); 
if (out != null) 
out.close(); 
throw e; 
} finally { 
} 
} 

	public DataInputStream getMessageStream() throws Exception { 
		try { 
			getMessageStream = new DataInputStream(new BufferedInputStream(socket.getInputStream())); 
			return getMessageStream; 
		} catch (Exception e) { 
			e.printStackTrace(); 
			if (getMessageStream != null) 
				getMessageStream.close(); 
			throw e; 
		} finally { 
			
		} 
	} 
	
	public void shutDownConnection() { 
		try { 
			if (out != null) 
				out.close(); 
			if (getMessageStream != null) 
				getMessageStream.close(); 
			if (socket != null) 
				socket.close(); 
		} catch (Exception e) { 
			
		} 
	} 

public OutputStream getOutputStream()throws IOException{
	return socket.getOutputStream();

}
} 