package ui;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import org.forms.XMLReader;
import org.wsoperate.DownloadUsbServiceStub;
import org.wsoperate.DownloadUsbServiceStub.Downloadusb;
import org.wsoperate.DownloadUsbServiceStub.Uploadusb;

public class UsbWorkProcess extends Thread {
	private Socket s;
	
	public UsbWorkProcess(Socket s)
	{
		this.s=s;
	}
	public void run()
	{
		System.out.println("Usb Upload Process start!");
		System.out.println("Address:   "+s.getInetAddress().getHostAddress());
		try {
			//DataInputStream in = new DataInputStream(s.getInputStream());
			//String usbinfo=in.readUTF();
			DataInputStream in = new DataInputStream(s.getInputStream()); 
			byte[] tmp = new byte[128];
			int res=in.read(tmp);
			if(res>128)
			{
				System.out.println("usb upload info length illegal!");
				s.close();
				return;
			}
			byte[] rs=new byte[res];
			for(int i=0;i<res;i++)
				rs[i]=tmp[i];
			String usbinfo=new String(rs,"GB2312");
			String term[]=usbinfo.split(" ");
			if(term.length!=4)
			{
				System.out.println("usb upload info format illegal!");
				s.close();
				return;
			}
			usbinfo=PTM_connect.pubkey+";;;"+usbinfo;
			//usbinfo.trim();
			XMLReader xmlreader = new XMLReader();
			String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("usbop");
			DownloadUsbServiceStub stub = new DownloadUsbServiceStub(null, trans_value);
			Uploadusb infofunction = new Uploadusb();
			infofunction.setIn0(usbinfo);
			boolean result = stub.uploadusb(infofunction).getOut();
			if(result)
				System.out.println("Usb Upload Process success!");
			else
				System.out.println("Usb Upload Process fail!");
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Usb Upload Process error!");
		}
	}
}
