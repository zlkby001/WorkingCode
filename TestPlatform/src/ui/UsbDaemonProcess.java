package ui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UsbDaemonProcess extends Thread {
	public static boolean stop=false;

	//public static boolean stop=false;
	public void run()
	{
		System.out.println("Usb Daemon Process start!");
		try {
			ServerSocket ss=new ServerSocket(2989);
		//	while(true)
			//{
			//	sleep(1000);
			//	System.out.println(stop);
			//}

			while(true)
			{
				Socket s=ss.accept();
				if(!stop)
				{
				UsbWorkProcess work=new UsbWorkProcess(s);
				work.start();
				}
				else
				{
					System.out.println("usb upload: request received but user not login!");
					s.close();
				}
				//if(stop)
				//{
					//System.out.println("Usb Daemon Process stop!");
					//break;
				//}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Usb Daemon Process stop!");
		}
	}


}

