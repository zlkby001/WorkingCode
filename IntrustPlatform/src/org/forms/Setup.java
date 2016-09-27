package org.forms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

import org.dboperate.DBOperate;
import org.dboperate.MeasurementLogUpdate;

import org.forms.SubUpload;
import org.wsoperate.WSOperator;

import com.sun.jna.ptr.IntByReference;


import ui.KnowledgeDownloaderProcess;
import ui.LibMeasure;

public class Setup extends Composite {
	private MessageBox mb;
	public static Button btn_CloseMonitor;
	private Shell shell;

	private Button btn_UpdateLog;
	private Button btn_download;
	static String wl_FilterPath = "C:\\TCC\\wl";//"C:\\wl";//默认白名单路径
	private Button btn_usbMonitor;	
	private Button btn_selfstart;
	private String imagePath = MainForm.imagePath;
	
	private String selfstartfile = "C:\\TCC\\autoctl";
	
	private Boolean selfsartisOpen = false;
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */

	public Setup(final Composite parent, int style) {
		super(parent, style);
		setBounds(0, 110, 900, 500);
		setVisible(false);
		shell = (Shell)parent;

		mb = new MessageBox(this.getShell());
		btn_CloseMonitor = new Button(this, SWT.FLAT);		
		btn_CloseMonitor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			 if(Password.count == 0){	
				Password pswd = new Password(shell, 0);
				pswd.open();
				if(!(pswd.isTrue)){
					return;					
				}
				
				
				LibMeasure lm = new LibMeasure();

				if (LibMeasure.whitemode == 1) {
					int ret = lm.TM_startNormalMode();
					switch (ret) {
					case 0:
						mb.setMessage("保护模式已关闭！");
						LibMeasure.whitemode = 0;
						Methods.updateWidgetsMode();
						break;
					case 1:
						mb.setMessage("internal error of libmeasure!");
						break;
					case 101:
						mb.setMessage("error of calling libmeasure!");
						break;
					default:
						mb.setMessage("未知错误" + ret);
					}
					mb.open();
				}

				else {
					//lm.TM_clearWhiteList();
					if (addctrlnum()==false)
					{
						mb.setMessage("本功能试用结束，请获取授权后再使用!");
						mb.open();
						return;
					}
					
					IntByReference count=new IntByReference();
					lm.TM_clearWhiteList();//先清空白名单
					
					int ret=lm.TM_setWhiteList(wl_FilterPath, count);
					ret = lm.TM_startWhiteMode();// 开启白名单模式
					if (ret == 0) {
						LibMeasure.whitemode = 1;
						mb.setMessage("保护模式已开启！");
						Methods.updateWidgetsMode();
						
					}
					mb.open();
				}
			}
		  }	 
		});
		btn_CloseMonitor
				.setImage(SWTResourceManager.getImage(imagePath + "StartMonitor.png"));
		btn_CloseMonitor.setBounds(100, 55, 191, 185);
		
		LibMeasure lms = new LibMeasure();
		if (lms.TM_getCurrentMode() == 11) {
			LibMeasure.whitemode = 1;
			btn_CloseMonitor.setImage(SWTResourceManager
					.getImage(imagePath + "CloseMonitor.png"));
		} else if (lms.TM_getCurrentMode() == 10) {
			LibMeasure.whitemode = 0;
			btn_CloseMonitor.setImage(SWTResourceManager
					.getImage(imagePath + "StartMonitor.png"));
		} else {
			//mb.setText("未知模式,Error Code:" + lms.TM_getCurrentMode());
		}	


		// 通信设置按钮与注册事件
		Button btn_communication = new Button(this, SWT.FLAT);
		btn_communication
				.setImage(SWTResourceManager
						.getImage(imagePath + "Communication.png"));
		btn_communication.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (SetupCommunicationSet.count == 0) {
					SetupCommunicationSet com = new SetupCommunicationSet(
							shell, 0);
					// int x1 = getLocation().x + getSize().x/2;
					// int y1 = getLocation().y + getSize().y/2;
					com.open();
				}

			}
		});
		btn_communication.setBounds(380, 55, 191, 188);

		//度量日志上传按钮
		btn_UpdateLog = new Button(this, SWT.FLAT);
		btn_UpdateLog.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			//	SubUpload inst = new SubUpload();
			//	inst.setLocationRelativeTo(null);					
			//	inst.setVisible(true);
			if(SubUpload.count == 0){	
				SubUpload inst = new SubUpload(shell,0);
				inst.open();
			}
		  }
		});
//		btn_UpdateLog.setText("报警日志");
		btn_UpdateLog.setBounds(655, 55, 191, 185);
		btn_UpdateLog
		.setImage(SWTResourceManager
				.getImage(imagePath + "AlertLog.png"));
		
		btn_download = new Button(this, SWT.FLAT);
		btn_download.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//============================================zhyjun================================================
        		KnowledgeDownloaderProcess thread=new KnowledgeDownloaderProcess();
        		thread.run();
        		//============================================zhyjun================================================
			}
		});
		btn_download.setImage(SWTResourceManager.getImage(imagePath + "download.png"));
		btn_download.setBounds(100, 283, 191, 185);
		
		btn_usbMonitor = new Button(this, SWT.FLAT);
		btn_usbMonitor.setImage(SWTResourceManager.getImage(imagePath + "usbMonitor.png"));
		btn_usbMonitor.setBounds(380, 281, 191, 188);
		btn_usbMonitor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (USBControl.count == 0) {
					USBControl usb = new USBControl(
							shell, 0);
					// int x1 = getLocation().x + getSize().x/2;
					// int y1 = getLocation().y + getSize().y/2;
					usb.open();
				}	
			}
		});
		
		
		//设置开机自启动
		// 通信设置按钮与注册事件
		btn_selfstart = new Button(this, SWT.FLAT);
		btn_selfstart.setImage(SWTResourceManager
						.getImage(imagePath + "SelfStartOn.png"));
		getSelfstartStatus();
		if(selfsartisOpen){
			
			btn_selfstart.setImage(SWTResourceManager
					.getImage(imagePath + "SelfStartOff.png"));
		}
		
		btn_selfstart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			  if(Password.count == 0){	
				Password pswd = new Password(shell, 0);
				pswd.open();
	          	
				if(!(pswd.isTrue)){
					return;					
				}
				
				if(selfsartisOpen==true){
					try {
						FileWriter wr=new FileWriter(selfstartfile);
						wr.write("0");
						wr.flush();
						wr.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					btn_selfstart.setImage(SWTResourceManager.getImage(imagePath + "SelfStartOn.png"));
					selfsartisOpen = false;
					mb.setMessage("监控开机自启动已经关闭！");
				}
				else{
					try {
						FileWriter wr=new FileWriter(selfstartfile);
						wr.write("1");
						wr.flush();
						wr.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					btn_selfstart.setImage(SWTResourceManager.getImage(imagePath + "SelfStartOff.png"));
					selfsartisOpen = true;
					mb.setMessage("监控开机自启动已经开启！");
				}
				mb.open();
					
			  }
		    }
		});
		btn_selfstart.setBounds(655, 281, 191, 188);
		
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private void getSelfstartStatus() {
		int status =0;
		
		String strFile = selfstartfile;
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
					selfsartisOpen = true;
				}
			}
		} catch (FileNotFoundException e) {
			// Debug.println("Measurement file not found");
			// e.printStackTrace();
			//System.out.println("文件未找到：" + mfile);
			try {
				FileWriter wr=new FileWriter(selfstartfile);
				wr.write("0");
				wr.flush();
				wr.close();
				selfsartisOpen = false;
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
		
	}
	
	public boolean addctrlnum(){
		if (MainForm.auhorized==true)
			return true;
		
		//保存信息
    	File file = new File("C:\\TCC\\ctrlnum");
    	BufferedWriter wr = null;
    	BufferedReader rd = null;
    	boolean isOK = true;
    	if (file.exists()!=true){//文件不存在，首次点击    		
    		try {
    	    	wr = new BufferedWriter(new FileWriter(file));
    	    	
    	    	wr.write("1");
    	    	wr.write("\n");	    	
    	    	wr.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        	} finally {
    	    	if (wr != null) {
    		    	try {
    		    		wr.close();
    		    	} catch (IOException e1) { return false;
    		    	}
    	    	}
        	}
    		return true;
    	}    	

    	//文件存在，非首次点击
		String readnum = null;
		int rdnum = 0;		    	
    	try {		    	
	    	rd = new BufferedReader(new FileReader(file));
	    	
	    	if((readnum = rd.readLine()) == null){
	    		isOK = false;
	    	} 
	    	rd.close();		    	
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
	    	if (rd != null) {
		    	try {
		    		rd.close();
		    	} catch (IOException e1) {
		    	}
	    	}
    	}
    	
    	rdnum = Integer.parseInt(readnum);
    	try {
	    	wr = new BufferedWriter(new FileWriter(file));
	    	if(isOK == false){
	    	wr.write("1");
	    	wr.write("\n");	
	    	}else{
	    		rdnum++;
	    		String temp = String.valueOf(rdnum);
	    		wr.write(temp);
		    	wr.write("\n");	
	    	}	    	
	    	wr.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
	    	if (wr != null) {
		    	try {
		    		wr.close();
		    	} catch (IOException e1) { return false;
		    	}
	    	}
    	}
    	
    	if (rdnum==4 || rdnum>4)
    		return false;
    	else return true;
	}
}
