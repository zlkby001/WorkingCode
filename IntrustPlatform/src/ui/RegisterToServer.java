package ui;

public class RegisterToServer {
	private int timeout = 10000;
	public int user_register(String server_ip,int server_port,UserBean user){
		int res=-2;
		try {
			ClientSocket sock = new ClientSocket(server_ip, server_port);
			try {
				sock.CreateConnection(this.timeout);
				System.out.print("连接服务器成功" + "\n");
			} catch (Exception e) {
				System.out.print("连接服务器失败" + "\n");
				return 103;
			}
			RegisterProcess thread = new RegisterProcess(sock,user);
			thread.start();
			
			while(thread.Getres()!=1000 && thread.Getres()!=1001 && thread.Getres()!=105&& thread.Getres()!=-1 && thread.Getres()!=0 &&
		   			thread.Getres()!=101 && thread.Getres()!=104 && thread.Getres()!=2 && thread.Getres()!=3 && thread.Getres()!=1)
			{
		   			thread.sleep(500);
			}
			sock.shutDownConnection();
			res = thread.Getres();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
		return res;
	}
}
