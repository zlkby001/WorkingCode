/*package ui;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.forms.XMLReader;
import org.wsoperate.DownloadUsbServiceStub;
import org.wsoperate.KnowledgeServiceStub;
import org.wsoperate.DownloadUsbServiceStub.Downloadusb;

public class UsbDownloaderProcess{
	private String usbfile="C:\\usbwl";
	private Shell shell;
	private MessageBox mb;
	//private int res=0;
	
	
	private void inform(final String info)
	{			
		mb.setMessage(info);
		mb.open();					
	}
	
	
	public UsbDownloaderProcess(Shell shell)
	{
		this.shell = shell;
		this.mb = new MessageBox(shell);
		
	}
	
	
	public void start()
	{
		XMLReader xmlreader = new XMLReader();
		final String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("usbop");
		
		shell.getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				
			try {
			DownloadUsbServiceStub stub = new DownloadUsbServiceStub(null, trans_value);
			Downloadusb infofunction = new Downloadusb();
			infofunction.setIn0(PTM_connect.pubkey);
			try {
				String result = stub.downloadusb(infofunction).getOut();
				if(dump_usbinfo_into_file(result))
					inform("下载usb白名单成功!");
				else
					inform("写入文件过程出现异常!");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				inform("连接服务器失败!");
			}
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			inform("下载过程出现异常!");
		}
			}
			
		});	
		
	   
	}
	
	
	private boolean dump_usbinfo_into_file(String result)
	{
		try {
			FileWriter wr=new FileWriter(usbfile);
			wr.write(result);
			wr.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
*/
package ui;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.forms.XMLReader;
import org.wsoperate.DownloadUsbServiceStub;
import org.wsoperate.KnowledgeServiceStub;
import org.wsoperate.DownloadUsbServiceStub.Downloadusb;

public class UsbDownloaderProcess extends Thread {
	private String usbfile="C:\\TCC\\usbwl";//"C:\\usbwl";
	private Shell shell;
	//private int res=0;
	private void inform(final String info)
	{	
		shell.getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				MessageBox mb=new MessageBox(shell);
				mb.setMessage(info);
				mb.open();
			}
			
		});
		
	}
	public UsbDownloaderProcess(Shell shell)
	{
		//this.mb=mb;
		this.shell=shell;
	}
	public void run()
	{
		XMLReader xmlreader = new XMLReader();
		String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("usbop");
		//Shell sh=new Shell();

		//MessageBox mb = new MessageBox((Shell)parent);
		try {
			DownloadUsbServiceStub stub = new DownloadUsbServiceStub(null, trans_value);
			Downloadusb infofunction = new Downloadusb();
			infofunction.setIn0(PTM_connect.pubkey);
			try {
				String result = stub.downloadusb(infofunction).getOut();
				if(dump_usbinfo_into_file(result))
					inform("下载usb白名单成功!");
				else
					inform("写入文件过程出现异常!");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				inform("连接服务器失败!");
			}
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			inform("下载过程出现异常!");
		}
	   
	}
	private boolean dump_usbinfo_into_file(String result)
	{
		try {
			FileWriter wr=new FileWriter(usbfile);
			wr.write(result);
			wr.flush();
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
