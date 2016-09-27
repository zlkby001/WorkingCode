package ui;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;



public class Downloader {
	private int timeout = 10000;
	
	public int download_whitelist(String server_ip,int server_port,Label list,ProgressBar bar,String pubkey,String whitelist_path){
		int res=-1;
		try {
			// System.out.println(server_ip);
			// System.out.println(server_port);
			list.setText("正在连接服务器...");
			list.update();
			//ClientSocket sock = new ClientSocket(server_ip, server_port);
			ClientSocket sock = new ClientSocket(server_ip, 4999);
			try {
				sock.CreateConnection(this.timeout);
				System.out.print("连接服务器成功" + "\n");
				list.setText("连接服务器成功!");
				list.update();

			} catch (Exception e) {
				System.out.print("连接服务器失败" + "\n");
				list.setText("连接服务器失败，请检测网络连接和服务器地址是否正确！");
				list.update();
				return 103;
			}
			DownloadProcess thread = new DownloadProcess(sock,pubkey,whitelist_path);
			thread.start();
			while(thread.Getres()!=1000 && thread.Getres()!=1001 && 
		   			thread.Getres()!=101 && thread.Getres()!=104 && thread.Getres()!=2 && thread.Getres()!=3 && thread.Getres()!=1)
		   	{
		   		list.setText("正在等待服务器响应,请等待.");
		   		list.update();
		   		bar.setSelection(10);
		   		Thread.sleep(500);
		   		list.setText("正在等待服务器响应,请等待..");
		   		list.update();
		   		bar.setSelection(30);
		   		Thread.sleep(500);
		   		list.setText("正在等待服务器响应,请等待...");
		   		list.update();
		   		bar.setSelection(50);
		   		Thread.sleep(500);
		   		list.setText("正在等待服务器响应,请等待....");
		   		list.update();
		   		bar.setSelection(70);
		   		Thread.sleep(500);
		   		list.setText("正在等待服务器响应,请等待.....");
		   		list.update();
		   		bar.setSelection(90);
		   		Thread.sleep(500);
		   		list.setText("正在等待服务器响应,请等待......");
		   		list.update();
		   		bar.setSelection(100);
		   		Thread.sleep(500);
		   		
		   	}
			sock.shutDownConnection();
			res = thread.Getres();
			//res = thread.Getres();
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			return -1;
		}

		return res;
	}

}
