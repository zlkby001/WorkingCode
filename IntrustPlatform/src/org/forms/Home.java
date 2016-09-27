package org.forms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.events.KeyAdapter;
import org.wsoperate.RegistOperate;
import org.wsoperate.WSOperator;

import ui.AlertUploader;
import ui.ClientSocket;
import ui.DownloadProcess;
import ui.LibMeasure;
import ui.PTM_connect;
import ui.ServerIPaddress;
import ui.TreeDownloaderProcess;
import ui.UploadAlertProcess;
import ui.UsbDaemonProcess;


public class Home extends Composite {
	public static Label lbl_ModeImage;
	public static Label lbl_Mode;
	private Label label_Company;
	private Label label_LoginTime;
	private Label label_Name;
	public static Label lbl_Tips;
	private Text  txt_Psd;
	private Text txt_Id;
	private String imagePath = MainForm.imagePath;
	private Shell shell;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	private Composite csLogin = new Composite(this,SWT.NONE);
	private Composite csUserInfo = new Composite(this,SWT.NONE);
	public UploadAlertProcess thread;
	public UsbDaemonProcess usb_thread;
//	public static int count = 0;
	
	public Home(Composite parent, int style) {
		super(parent, style);

		this.setSize(910,500);
		this.setVisible(false);			
		shell = (Shell)parent;
		
		//创建用户登录面板
		csLogin.setBounds(660, 0, 220, 180);
//		csLogin.setBackground(new Color(getDisplay(),255,100,255));
		Label label = new Label(csLogin, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("黑体", 13, SWT.NORMAL));
		label.setBounds(10, 10, 112, 23);
		label.setText("管理员登录");
		
		Label lbl_Id = new Label(csLogin, SWT.NONE);
		lbl_Id.setBounds(10, 56, 33, 23);
		lbl_Id.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		lbl_Id.setText("账号");		
		
		Label lbl_Psd = new Label(csLogin, SWT.NONE);
		lbl_Psd.setBounds(10, 90, 33, 20);
		lbl_Psd.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		lbl_Psd.setText("密码");
		
		txt_Id = new Text(csLogin, SWT.BORDER);
		txt_Id.setToolTipText("请输入您的ID");
		txt_Id.setText("guest");
		txt_Id.setBounds(51, 53, 112, 23);		
		txt_Id.forceFocus();
		
		txt_Psd = new Text(csLogin, SWT.BORDER | SWT.PASSWORD);
		txt_Psd.setBounds(51, 87, 114, 23);
		txt_Psd.setToolTipText("请输入您的密码");
		
		
		Button btn = new Button(csLogin,SWT.NONE);
		btn.setText("登录");
		btn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Login();
			/*	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				String dateTime = formatter.format(new Date());
				label_LoginTime.setText(dateTime);*/
				
			}
		});
		
		/**
		 * 监听键盘事件
		 */
		txt_Psd.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
                    // 让按键原有的功能失效
                    e.doit = false;
                    Login();
                }
            }
 
            public void keyReleased(KeyEvent e) {
            }
        });
		btn.setBounds(65,131,80,30);
		
		//创建用户信息面板
		csUserInfo.setBounds(660, 0, 220, 180);
		Label label_1 = new Label(csUserInfo, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		label_1.setBounds(3, 28, 60, 29);//修改x位置
		label_1.setText("用户名：");
		
		label_Name = new Label(csUserInfo, SWT.NONE);
		label_Name.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		label_Name.setBounds(69, 28, 103, 29);//修改x位置
		
	//删除单位标签及文本输入框	
	/*	Label label_2 = new Label(csUserInfo, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		label_2.setBounds(33, 63, 45, 29);
		label_2.setText("单位：");
		
		label_Company = new Label(csUserInfo, SWT.NONE);
		label_Company.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		label_Company.setBounds(84, 63, 111, 36); */
		
		
		Label label_3 = new Label(csUserInfo, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		label_3.setBounds(3, 58, 75, 24);//修改y位置
		label_3.setText("登录时间：");
		
		label_LoginTime = new Label(csUserInfo, SWT.NONE);
		label_LoginTime.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		label_LoginTime.setBounds(84, 58, 135, 24);//修改y位置
	/*	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String dateTime = formatter.format(new Date());
		label_LoginTime.setText(dateTime);*/
		
		Button btn_Exit = new Button(csUserInfo, SWT.NONE);
		btn_Exit.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent e) {
				UserInfo.userType = "";
				UserInfo.userNameString = "";
				csUserInfo.setVisible(false);
				csLogin.setVisible(true);
				txt_Id.setText("guest");
				txt_Psd.setText("");
				label_Name.setText("");
				Status.btn_WhiteList.setEnabled(false);
				MainForm.toolBar.setSize(110,110);		
				AlertUploader.start=false;
				//UsbDaemonProcess.stop=true;
				synchronized(thread){
					thread.notify();
				}
				UsbDaemonProcess.stop=true;
				//usb_thread.stop();
				//synchronized(usb_thread){
					//usb_thread.notify();
				//}
			}
		});
		btn_Exit.setBounds(13, 88, 75, 29);
		btn_Exit.setText("退出");
		
		//创建修改密码按钮
		
		Button btn_Uppsw = new Button(csUserInfo, SWT.NONE);
		btn_Uppsw.setBounds(110,88,100,29);
		btn_Uppsw.setText("修改密码");
		btn_Uppsw.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txt_Id.getText().equals("guest")){
            		Methods.Alert("对不起，只有管理员可以修改密码！");
            		return;
            	}else
            	{
					Updatepassword up = new Updatepassword(shell,0);
					up.open();
				}
			}
		});		
		
		//创建系统简介信息
		Label lb1 = new Label(this,SWT.NONE);
		lb1.setAlignment(SWT.CENTER);
		lb1.setImage(SWTResourceManager.getImage(imagePath + "System.png"));
		lb1.setBounds(10,118,219,211);
		Label lb2 = new Label(this,SWT.NONE);
		lb2.setText("可信终端平台");
		lb2.setBounds(262, 149, 200, 50);
		lb2.setFont(SWTResourceManager.getFont("黑体", 22, SWT.BOLD));		
		Label lb3 = new Label(this,SWT.NONE);
		lb3.setText("是一款专门为工业控制系统定制开发\n的终端环境管控软件，使用可信计算\n"
					+"技术，具有安全性高、管控能力强等\n特点。");
		lb3.setBounds(262, 199, 350, 130);
		lb3.setFont(SWTResourceManager.getFont("黑体", 14, SWT.BOLD));	
		
		
		
		Label lblNewLabel = new Label(this, SWT.SEPARATOR);
		lblNewLabel.setBounds(642, 20, 8, 462);
		lblNewLabel.setText("");
		
		lbl_Mode = new Label(this, SWT.NONE);
		lbl_Mode.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		lbl_Mode.setBounds(702, 341, 133, 24);
		lbl_Mode.setText("保护模式已开启");
		
		lbl_Tips = new Label(this, SWT.NONE);
		lbl_Tips.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		lbl_Tips.setBounds(660, 384, 186, 29);
		lbl_Tips.setText("未注册 版本");
		
		lbl_ModeImage = new Label(this, SWT.NONE);
		lbl_ModeImage.setImage(SWTResourceManager.getImage(imagePath + "UnprotectedMode.png"));		
		lbl_ModeImage.setBounds(680, 186, 166, 166);
		
//		Label label_4 = new Label(this, SWT.NONE);
//		label_4.setImage(SWTResourceManager.getImage(imagePath + "TcaLogo.JPG"));
//		label_4.setBounds(660, 409, 115, 50);
		
		Label label_5 = new Label(this, SWT.NONE);
		label_5.setText("中科院软件研究所TCA实验室\n青岛海天炜业过程控制技术股份有限公司\n@Version 2.0.2");
		label_5.setBounds(650, 453, 286, 57);
		
		Image img1 = SWTResourceManager.getImage(imagePath + "ProtectMode.png");
		Image img2 = SWTResourceManager.getImage(imagePath + "UnprotectedMode.png");

		LibMeasure lms = new LibMeasure();
		if (lms.TM_getCurrentMode() == 11) {
			LibMeasure.whitemode = 1;
			lbl_ModeImage.setImage(img1);
			lbl_Mode.setText("保护模式已开启");
		} else if (lms.TM_getCurrentMode() == 10) {
			LibMeasure.whitemode = 0;
			lbl_ModeImage.setImage(img2);
			lbl_Mode.setText("保护模式已关闭");
		} else {
			//mb.setText("未知模式,Error Code:" + lms.TM_getCurrentMode());
		}	

//		Listener listener = new Listener () {
//		      public void handleEvent (Event e) {
//		    	  if(e.type==SWT.Dispose)
//		    		  count--;		         
//		      }			
//		   };
//		this.addListener (SWT.Dispose, listener);
		 
	}

	/**
	 * 用户登录
	 */
	
	
	
	private void Login(){
		
	
		
		Methods.updateCommunicationSet();	//更新页面IP地址
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String dateTime = formatter.format(new Date());
		label_LoginTime.setText(dateTime);
		
		if(!MainForm.message.equals("")){
			MessageBox mb=new MessageBox(getShell(),SWT.ICON_WARNING);
			mb.setText("警告");
			mb.setMessage(MainForm.message);
			mb.open();
			return;
		}					
		
		
		
		String id = txt_Id.getText();
		String psd = txt_Psd.getText();	
		
		if(id.equals("guest")){		//Guest账户登录
			csLogin.setVisible(false);
			csUserInfo.setVisible(true);
			UserInfo.userType="1";
			UserInfo.userNameString="guest";
			MainForm.toolBar.setSize(324,110);
//============================modified by zhyjun=========================================================
			TreeDownloaderProcess tree_thread=new TreeDownloaderProcess();
			tree_thread.start();
			UsbDaemonProcess.stop=false;
			usb_thread=new UsbDaemonProcess();
			//PTM_connect.usb=usb_thread;
			usb_thread.start();
			
			PTM_connect.Load_pubkey();									
			thread = new UploadAlertProcess(PTM_connect.pubkey);
			PTM_connect.p=thread;
			thread.start();
			label_Name.setText(UserInfo.userNameString);
//============================modified by zhyjun=========================================================
		}
		else{
			try {
				Class.forName("org.sqlite.JDBC");
				Connection con = DriverManager.getConnection("jdbc:sqlite://C:/TCC/user.db");
				con.setAutoCommit(false);
				Statement stat = con.createStatement();
				con.commit();
				ResultSet rs = stat.executeQuery("SELECT * FROM userInfo;");
			if(rs.getString("userName").equals(id) && rs.getString("passWord").equals(psd))
			 //if(id.equals("admin")&&psd.equals("123"))
			 {	//管理员账户登录				
			csLogin.setVisible(false);
			csUserInfo.setVisible(true);
			UserInfo.userType = "2";
			UserInfo.userNameString=id;
//			Methods.Alert("登录成功!");
			Status.btn_WhiteList.setEnabled(true);
			MainForm.toolBar.setSize(638,110);
			
			//============================modified by zhyjun=========================================================
			TreeDownloaderProcess tree_thread=new TreeDownloaderProcess();
			tree_thread.start();
			UsbDaemonProcess.stop=false;
			usb_thread=new UsbDaemonProcess();
			usb_thread.start();
			PTM_connect.Load_pubkey();									
			thread = new UploadAlertProcess(PTM_connect.pubkey);
			PTM_connect.p=thread;
			thread.start(); 
//============================modified by zhyjun=========================================================
		}
		else {
			Methods.Alert("用户名或密码错误！");
		}
		
		if(UserInfo.userType.equals("")){
			label_Name.setText("未知");
		}
		else
			label_Name.setText(UserInfo.userNameString);
	//删除单位判断，防止程序终止
	/*	if(UserInfo.userType.equals("")){
			label_Company.setText("未知");
		}
		else
			label_Company.setText("中科院软件所"); */
		
		//Methods.Alert(UserInfo.userNameString);
		if(!UserInfo.userNameString.equals("guest")&&!UserInfo.userNameString.equals("")){
			Methods.Alert("如果未注册，请先进行平台注册。");
		}
		stat.close();
		con.close();
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	  }
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
