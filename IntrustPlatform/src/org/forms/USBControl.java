package org.forms;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import ui.UsbDownloaderProcess;

public class USBControl extends Dialog {

	protected Object result;
	protected Shell shell;
	private int x=0;
	private int y=0;
	public static int count=0;
	
	private MessageBox mb;
	public static Button btn_CloseMonitor;
	private Button btn_download;
	static String wl_FilterPath = "C:\\TCC\\wl";//"C:\\wl";//默认白名单路径
	boolean isOpened = true;
	private String usbcontrolfile="C:\\TCC\\usbctrl";//"C:\\Windows\\usbctrl";
	private Label lblUsb;
	private Button button;
	private Button btn_UsbWl;
	private String imagePath = MainForm.imagePath;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public USBControl(Shell parent, int style) {
		super(parent, style);		
		if(!getUsbStatus()){
			isOpened = false;
		}
		
		setText("SWT Dialog");
		count++;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}
	
	

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		
		shell = new Shell(getParent(), SWT.RESIZE | SWT.TITLE);
		shell.setSize(689, 435);
		//shell.setSize(712, 431);
		shell.setText("USB管控");
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 689;
		int h = 390;
		shell.setBounds((int)(scrSize.width-w)/2,(int)(scrSize.height-h)/2,w, h);
		shell.setVisible(true);
		
		mb = new MessageBox(shell.getShell());
		
		Group ugroup = new Group(shell,SWT.NONE);
		ugroup.setBounds(10, 45, 656, 277);
		
		lblUsb = new Label(shell, SWT.NONE);
		lblUsb.setText("USB管控");
		lblUsb.setFont(SWTResourceManager.getFont("楷体_GB2312", 18, SWT.BOLD));
		lblUsb.setBounds(292, 10, 104, 29);
		
		button = new Button(ugroup, SWT.FLAT);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				count--;
				shell.close();
			}
		});
		button.setText("退出");
		button.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		button.setBounds(303, 231, 77, 31);
		btn_CloseMonitor = new Button(ugroup, SWT.FLAT);
		btn_CloseMonitor.setBounds(20, 28, 191, 185);
		btn_CloseMonitor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
		     if(Password.count == 0){		
				Password pswd = new Password(shell, 0);
				pswd.open();
				if(!(pswd.isTrue)){
					return;					
				}
				
				if(isOpened==true){
					try {
						FileWriter wr=new FileWriter(usbcontrolfile);
						wr.write("0");
						wr.flush();
						wr.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					btn_CloseMonitor.setImage(SWTResourceManager.getImage(imagePath + "usbStart.png"));
					isOpened = false;
					mb.setMessage("USB管控已经关闭！");
				}
				else{
					try {
						FileWriter wr=new FileWriter(usbcontrolfile);
						wr.write("1");
						wr.flush();
						wr.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					btn_CloseMonitor.setImage(SWTResourceManager.getImage(imagePath + "usbShutdown.png"));
					isOpened = true;
					mb.setMessage("USB管控已经开启！");
				}
				mb.open();
		     		
		     }	
			}
		});
		btn_CloseMonitor
				.setImage(SWTResourceManager.getImage(imagePath + "usbShutdown.png"));
		if(isOpened==false){
			btn_CloseMonitor
			.setImage(SWTResourceManager.getImage(imagePath + "usbStart.png"));
		}
		
		btn_download = new Button(ugroup, SWT.FLAT);
		btn_download.setBounds(234, 28, 191, 185);
		btn_download.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				UsbDownloaderProcess usbdownloader=new UsbDownloaderProcess(shell);
				usbdownloader.start();
				//mb.setMessage("下载U盘白名单成功！");
				//mb.open();
			}
		});
		btn_download.setImage(SWTResourceManager.getImage(imagePath + "usbWhiteListDownload.png"));
		
		btn_UsbWl = new Button(ugroup, SWT.FLAT);
		btn_UsbWl.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (UsbWhiteList.count == 0) {
					UsbWhiteList usbwl = new UsbWhiteList(
							shell, 0);
					// int x1 = getLocation().x + getSize().x/2;
					// int y1 = getLocation().y + getSize().y/2;
					usbwl.open();
				}	
			}
		});
		btn_UsbWl.setImage(SWTResourceManager.getImage(imagePath + "usbWhiteList.png"));
		btn_UsbWl.setBounds(444, 28, 191, 185);
		
		//添加窗体鼠标事件
		shell.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub				
				x=e.x;
				y=e.y;				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				x=y=-1;
			}
		});
		
		//鼠标移动事件
		shell.addMouseMoveListener(new MouseMoveListener(){
			@Override
			public void mouseMove(MouseEvent e) {
				// TODO Auto-generated method stub
				if(x>0)
				{
					shell.setLocation(e.x-x + shell.getLocation().x,
						e.y-y + shell.getLocation().y);
				}
			}
		});
	}
	
	/**
	 * 检查USB管控是否打开
	 * @return
	 */
	private boolean getUsbStatus() {
		int status =0;
		boolean res = false;
		String strFile = usbcontrolfile;
		File mfile = new File(strFile);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(mfile);
			br = new BufferedReader(fr);

			String s = null;
			// Set MeasurementSet = new HashSet();
			if ((s = br.readLine()) != null) {
				status = Integer.parseInt(s);	
				if(status == 1){
					res = true;
				}
			}
		} catch (FileNotFoundException e) {
			// Debug.println("Measurement file not found");
			// e.printStackTrace();
			try {
				FileWriter wr=new FileWriter(usbcontrolfile);
				wr.write("1");
				wr.flush();
				wr.close();
				return true;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException ioe) {
			// Debug.println("Measurement file read error");
			// ioe.printStackTrace();
			System.out.println("文件读取错误：" + mfile);
		} finally {
			try {
				if (br != null) {
					br.close();
					br = null;
				}
				if (fr != null) {
					fr.close();
					fr = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
}
