package org.forms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.events.KeyAdapter;

import org.wsoperate.RegistOperate;
import org.wsoperate.WSOperator;
import org.wsoperate.WhitelistOperate;

import ui.AlertUploader;
import ui.ClientSocket;
import ui.DownloadProcess;
import ui.LibMeasure;
import ui.PTM_connect;
import ui.ServerIPaddress;
import ui.TCCUtilit;
import ui.TreeDownloaderProcess;
import ui.UploadAlertProcess;
import ui.UsbDaemonProcess;


public class Home extends Composite {

	private static Label periodtime;
	 private static MessageBox messageBox;
	private static Shell countDown;	
	private static int tcount;
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
	private TCCUtilit utilit = new TCCUtilit();
	static Label dwl_state;
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
		
		
		final Button btn = new Button(csLogin,SWT.NONE);
		btn.setText("登录");
		btn.setEnabled(false);
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
		btn.setBounds(10,128,80,30);
		
		Button btnNewButton = new Button(csLogin, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int res = -1;
				ArrayList<String> userinfolist=new ArrayList<String>();
				PTM_connect.Load_pubkey();
				String ip = ServerIPaddress.getIp();
				int port = ServerIPaddress.getPort();
				try {
					if (ip.length() == 0) {
						res = 203;
					} else {

						if (!TCCUtilit.IPisCorrect(ip)) {
							res = 201;
						} else {

							try {
								
//								res = downloader.download_whitelist(ip, port,
//										dwl_state, prgBar, PTM_connect.pubkey,
//										wl_FilterPath);
								
								
								//WhitelistOperate wlop = new WhitelistOperate();
							//	UserManage um = new UserManage();
								String uicontent = UserManage.pwd_download();	
								System.out.println("download:"+uicontent);
								if(uicontent!=""){
									System.out.println("download1:"+uicontent);
								
								String temp[] = uicontent.split(";;;");
								for(int i=0; i<temp.length; i++) {
									userinfolist.add(temp[i]);
									System.out.println("temp[i]: "+temp[i]+"----"+temp[0]);
//									try{
//										
//										ResultSet resultset = null;
//										Class.forName("org.sqlite.JDBC");
//										Connection con = DriverManager.getConnection("jdbc:sqlite://C:/TCC/user.db");
//										Statement stat = con.createStatement();
//									//	String sql= "UPDATE userInfo SET passWord='"+result+"' WHERE userName='admin'";
//										String sql ="INSERT INTO userInfo (userName, passWord, UserType)VALUES ('"+ temps[0] + "','" + temps[1]+"','"+ temps[2]+"')";
//										stat.executeUpdate(sql);
//
//										stat.close();
//										con.close();
//							        } catch (Exception e1) {
//							            e1.printStackTrace();
//							        }
								}
//								
//								 FileWriter fileWriter = new FileWriter(wlfile);
//						         BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
								
								//FileWriter fw;
								//PrintWriter bfWriter = null;
							/*	try {
									fw = new FileWriter(wlfile);
									fw.write("");
									fw.close();
									bfWriter=new PrintWriter(new FileWriter(wlfile)); 
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}*/
								
													
//								String whitelist[] = temp[1].split(";;;");
//								System.out.println(whitelist.length);
//								for(int i=0; i<whitelist.length; i++) {
//									//System.out.println(whitelist[i]);
//									String tempstr[] = whitelist[i].split("&&&");
//									//String sql = "("  + "'"+ tempstr[0] +"'" + "," + "'"+ tempstr[1] + "'" + "," + "'"+ tempstr[2] + "'" + ")";
//								/*	System.out.println(i);
//									System.out.println(tempstr[1]);
//									System.out.println(tempstr[0]);*/
//									bufferedWriter.write(tempstr[1]+" "+tempstr[0]);
//									bufferedWriter.newLine();
//						
//
//									
//							} 
								res=0;
								}else{
									res=110;
								}
//								bufferedWriter.close();
//					            fileWriter.close(); 
								
							}catch (NumberFormatException nfe) {
								res = 202;
							}
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION);

				mb.setText("执行结果");

				// System.out.println(res);
				switch (res) {
				case 110:
					try{
						
						ResultSet resultset = null;
						Class.forName("org.sqlite.JDBC");
						Connection con = DriverManager.getConnection("jdbc:sqlite://C:/TCC/user.db");
						Statement stat = con.createStatement();
						stat.executeUpdate("delete from userInfo;");
						stat.close();
						con.close();
					  } catch (Exception e1) {
				            e1.printStackTrace();
				        }
					  mb.setMessage("用户信息下载成功");
				
						break;
				case 0:
					try{
						
						ResultSet resultset = null;
						Class.forName("org.sqlite.JDBC");
						Connection con = DriverManager.getConnection("jdbc:sqlite://C:/TCC/user.db");
						Statement stat = con.createStatement();
						stat.executeUpdate("delete from userInfo;");
						stat.close();
						con.close();
					  } catch (Exception e1) {
				            e1.printStackTrace();
				        }
					  
//					  
						for(int i=0;i<userinfolist.size();i++){
						try{
						
						ResultSet resultset = null;
						Class.forName("org.sqlite.JDBC");
						Connection con = DriverManager.getConnection("jdbc:sqlite://C:/TCC/user.db");
						Statement stat = con.createStatement();
					//	String sql= "UPDATE userInfo SET passWord='"+result+"' WHERE userName='admin'";
					
							System.out.println("userinfolist"+userinfolist.size());
							String userinfo=userinfolist.get(i);
							System.out.print("userinfo: "+userinfo);
							String temps[]=userinfo.split("&&&");
							System.out.println("temp: "+temps[0]+temps[1]+temps[2]);
						String sql ="INSERT INTO userInfo (userName, passWord, UserType)VALUES ('"+ temps[0] + "','" + temps[1]+"','"+ temps[2]+"')";
						stat.executeUpdate(sql);
						stat.close();
						con.close();
					
						
					
			        } catch (Exception e1) {
			            e1.printStackTrace();
			        }
						}
					  
					mb.setMessage("用户信息下载成功");
					//dwl_state.setText("用户信息下载成功");
				//	dwl_state.update();
					//set_wl.setEnabled(true);
					break;

				case 1:
					mb.setMessage("服务端获取用户信息失败");
//					dwl_state.setText("服务端获取用户信息失败");
//					dwl_state.update();
					break;

				case 1001:
					mb.setMessage("用户信息下载成功");
//					dwl_state.setText("用户信息下载成功");
//					dwl_state.update();
					//set_wl.setEnabled(true);
					break;

				case 1000:
					mb.setMessage("接收用户信息失败");
//					dwl_state.setText("接受用户信息失败");
//					dwl_state.update();
					break;

				case 2:
					mb.setMessage("服务器接收客户端请求失败");
//					dwl_state.setText("服务器接收客户端请求失败");
//					dwl_state.update();
					break;

				case 3:
					mb.setMessage("服务器内部错误，用户信息更新失败");
//					dwl_state.setText("服务器内部错误");
//					dwl_state.update();
					break;

				case 101:
					mb.setMessage("向服务器发送请求失败，用户信息更新失败");
//					dwl_state.setText("向服务器发送请求失败");
//					dwl_state.update();
					break;

				case 102:
					mb.setMessage("获取度量文件失败");
//					dwl_state.setText("获取度量文件失败");
//					dwl_state.update();
					break;
				case 103:
					mb.setMessage("连接服务器失败，用户信息更新失败");
//					dwl_state.setText("连接服务器失败");
//					dwl_state.update();
					break;
				case 104:
					mb.setMessage("获取服务端结果失败");
//					dwl_state.setText("获取服务端结果失败");
//					dwl_state.update();
					
					
					
					break;

				case 201:
					mb.setMessage("服务器地址不合法");
//					dwl_state.setText("服务器地址不合法");
//					dwl_state.update();
					break;

				case 202:
					mb.setMessage("服务器端口不合法");
//					dwl_state.setText("服务器端口不合法");
//					dwl_state.update();
					break;
				case 203:
					mb.setMessage("请输入服务器地址和端口");
//					dwl_state.setText("请输入服务器地址和端口");
//					dwl_state.update();
					break;
				default:
					mb.setMessage("连接服务器失败,用户信息更新失败");
//					dwl_state.setText("未知错误");
//					dwl_state.update();

				}
				// add by Yangbo
				//res = 0;
				//System.out.println(wl_FilterPath);

				//if ((res == 0 || res == 1001)) {

					//mb.setMessage("白名单下载成功");
					//dwl_state.setText("白名单下载成功");
					//dwl_state.update();

				//}
				mb.open();
				btn.setEnabled(true);
				/*
				
				//从服务器获取白名单版本信息
				WhitelistOperate wlop = new WhitelistOperate();
			    String wlversionres = wlop.getWlVersion();
			    int wlversion;
			    if(wlversionres.equals("non_existent")) {
					System.out.println("获取白名单最新版本号有误");
					return;
				}
				else {
					String res[] = wlversionres.split("&&&");
					wlversion = Integer.parseInt(res[0]);
				}
				
			    //下载最新版本白名单			
				db = org.forms.MainForm.db;
				BufferedReader reader = null;
				String wlcontent = wlop.downloadWl(wlversion);										    
				String temp[] = wlcontent.split("###");
				try {
					db.cleartable("delete from wlversion");
					db.inserttable("insert into wlversion values " + "(" + "'" + wlversion + "'" + ")", 4);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				FileWriter fw;
				PrintWriter bfWriter = null;
				try {
					fw = new FileWriter(wlfile);
					fw.write("");
					fw.close();
					bfWriter=new PrintWriter(new FileWriter(wlfile)); 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					db.cleartable(clearsql);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
									
				String whitelist[] = temp[1].split(";;;");
				for(int i=0; i<whitelist.length; i++) {
					//System.out.println(whitelist[i]);
					String tempstr[] = whitelist[i].split("&&&");
					String sql = "("  + "'"+ tempstr[0] +"'" + "," + "'"+ tempstr[1] + "'" + "," + "'"+ tempstr[2] + "'" + ")";
					try {
						db.inserttable(insert + sql, 4);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					bfWriter.println(tempstr[1]+" "+tempstr[0]);
				}
				try {
					db.commit();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				bfWriter.close();
				//此处有提示框
				MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);
				mb.setText("执行结果");
				mb.setMessage("白名单下载成功！");
				mb.open();
				*/
				
			}
		});
		btnNewButton.setBounds(106, 128, 80, 30);
		btnNewButton.setText("更新用户");
		
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
				txt_Id.setText("");
				txt_Psd.setText("");
				label_Name.setText("");
				Status.btn_WhiteList.setEnabled(false);
				MainForm.toolBar.setSize(110,110);		
				AlertUploader.start=false;
				//UsbDaemonProcess.stop=true;
				synchronized(thread){
					thread.notify();
				}
				audit.log_record(MainForm.UserIdentity,UserInfo.userAcc, "登陆与退出", "退出");
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
		//btn_Uppsw.setBounds(110,88,100,29);
		//btn_Uppsw.setText("修改密码");
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
		label_5.setText("  中科院软件研究所TCA实验室\n青岛海天炜业过程控制技术股份有限公司");
		label_5.setBounds(650, 453, 286, 37);
		
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
		
		if(MainForm.count>=3){
			countDownShell countdownShell = new countDownShell(shell, 0);
			countdownShell.open();
			return;
		}
		
		
		String id = txt_Id.getText();
		String psd = txt_Psd.getText();	
		boolean flag = false;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite://C:/TCC/user.db");
			con.setAutoCommit(false);
			Statement stat = con.createStatement();
			con.commit();
			ResultSet rs = stat.executeQuery("SELECT * FROM userInfo;");
			
		//	String strSql = "select * from userInfo where Name='" + id + "' and passWord='"+psd + "'and UserType = 'normal'" ;
			//Guest账户登录
			while(rs.next()){
			if(rs.getString("userName").equals(id) && rs.getString("passWord").equals(psd)&&rs.getString("UserType").equals("normal")){
			
			csLogin.setVisible(false);
			csUserInfo.setVisible(true);
			MainForm.UserIdentity="普通用户";
			UserInfo.userType="1";
			UserInfo.userNameString="普通用户";
			UserInfo.userAcc=rs.getString("userName");
			UserInfo.code=rs.getString("passWord");
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
			audit.log_record(MainForm.UserIdentity,UserInfo.userAcc, "登陆与退出", "登陆");
			MainForm.count=0;
			flag=true;
			return;
//============================modified by zhyjun=========================================================
		}
			
	//	}
	//	else{
			//System.out.println("name: "+rs.getString("userName")+"password:"+rs.getString("passWord"));
			if(id.equals(rs.getString("userName"))&& psd.equals(rs.getString("passWord"))&&("super").equals(rs.getString("UserType")))
			 //if(id.equals("admin")&&psd.equals("123"))
			 {	//管理员账户登录				
			//	System.out.println("password acctually"+rs.getString("passWord"));
			csLogin.setVisible(false);
			csUserInfo.setVisible(true);
			MainForm.UserIdentity="超级用户";
			
			UserInfo.userType = "2";
			UserInfo.userNameString="超级用户";
			UserInfo.userAcc=rs.getString("userName");
			UserInfo.code=rs.getString("passWord");
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
			label_Name.setText(UserInfo.userNameString);
			audit.log_record(MainForm.UserIdentity,UserInfo.userAcc, "登陆与退出", "登陆");
			MainForm.count=0;
			flag=true;
			return;
//============================modified by zhyjun=========================================================
		}
			flag=false;
			
		
//		else {
//			Methods.Alert("用户名或密码错误！");
//			//return;
//		}
		
//		if(UserInfo.userType.equals("")){
//			label_Name.setText("未知");
//		}
//		else
//			label_Name.setText(UserInfo.userNameString);
	//删除单位判断，防止程序终止
	/*	if(UserInfo.userType.equals("")){
			label_Company.setText("未知");
		}
		else
			label_Company.setText("中科院软件所"); */
		
		//Methods.Alert(UserInfo.userNameString);
//		if(!UserInfo.userNameString.equals("guest")&&!UserInfo.userNameString.equals("")){
//			Methods.Alert("如果未注册，请先进行平台注册。");
//		}
	}
			if(flag==false){
			
				
		
				audit.log_record("未知","未知用户或登陆密码错误", "登陆与退出", "登陆失败");
				
				System.out.println("登陆失败");
				MainForm.count++;
				System.out.println("MainFormcount000: "+MainForm.count);
			}
			
		//	System.out.println("MainFormcount: "+MainForm.count);
		if(flag==false&&MainForm.count<=3){
			Methods.Alert("用户名或密码错误！");
			
		}if(flag==false&&MainForm.count>3){
//			Timer timer = new Timer();
//			timer.schedule(new TimerTask(){
//				public void run(){
//					Methods.Alert("登陆次数过多！");
//				}
//			}, 500);
	
//			final int timing=5; 
//			messageBox = new MessageBox(new Shell(), SWT.ICON_WARNING);
//			messageBox.setText("111");
	
				
//				messageBox.open();
		
		
//			
//			shell.getDisplay().asyncExec(new Runnable() {
//				@Override
//				public void run() {
//					
//					RunTime(timing);
//					// TODO Auto-generated method stub
//					// Date date = new Date(this.scheduledExecutionTime());
//			
//					
//					
//				}
//			});
		/**
			Display display2 = Display.getDefault();

			countDown = new Shell( SWT.RESIZE | SWT.TITLE);

			countDown.setSize(350, 140);
			
			Button	btnNewButton = new Button(countDown, SWT.NONE);
			btnNewButton.setEnabled(true);
			btnNewButton.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
			btnNewButton.setBounds(124, 69, 82, 26);
			btnNewButton.setText("确 定");
			btnNewButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					
					shell.close();
					
				}
			});		

			countDown.setText("登陆次数过多");

			Rectangle displayBounds = display2.getPrimaryMonitor()
					.getBounds();

			Rectangle shellBounds = countDown.getBounds();

			int x = displayBounds.x
					+ (displayBounds.width - shellBounds.width) >> 1;

			int y = displayBounds.y
					+ (displayBounds.height - shellBounds.height) >> 1;

					countDown.setLocation(x, y);

			

			
			
			countDown.layout();
			 periodtime = new Label(countDown, SWT.NONE);
			 periodtime.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
			periodtime.setBounds(30, 40, 240, 40);
			final int second = 5;
			periodtime.setText("登陆次数过多，一分钟后再次登陆");
			
			timer= new TimRefresh();
			timer.start();
			**/
	
		}
		
		rs.close();
		stat.close();
		con.close();
	//}
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	  }
	/**
	class TimRefresh extends Thread {
		int i = 0;
		public void run() {
			while(i<5) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				i++;
				System.out.println("i:"+i);
				if(i>=5){
					MainForm.count=0;
				}
				
				shell.getDisplay().syncExec(new Runnable() {
					public void run() {
						countDown.open();
						//if(i==9)
							//btnOK.setEnabled(true);
					
					}
				});
				
			}
		}
	}
	**/
	public void RunTime(int time){
		int second=0;
		new Thread().start();
		while(second<time){
			
			try{
				
				Thread.sleep(1000);
			System.out.println(second);
			second++;
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		shell.getDisplay().syncExec(new Runnable() {
			public void run() {
				countDown.open();
			}
		});
		}
		if(second>=time){
		
			MainForm.count=0;
		}
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
