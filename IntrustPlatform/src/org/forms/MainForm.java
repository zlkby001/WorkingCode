package org.forms;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.nio.channels.FileLock;

import ui.*;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import org.dboperate.DBOperate;
import org.dboperate.MeasurementLogUpdate;

public class MainForm {

	/**
	 * 创建主窗体
	 * @param args
	 */
    static String errorfile = "C:/TCC/errorlist.log";
    
	private static String errorfileplus = "C:/TCC/errorlistall.log";
	private static int lineNum;
	private static File efile;
	private static int counterrorlist;
	public static Thread d = new TimeRefresh();
	
	protected Shell shell;	
	private static int x=0;		//记录指针X坐标
	private static int y=0;		//记录指针Y坐标
	private int num = 1;	//当前页面序号
	public static ToolBar toolBar;
	public static Label lbl_RegisterInfo;
	public static String message="";
	private MessageBox mb;
	private String usbcontrolfile="C:\\TCC\\usbctrl";//"C:\\Windows\\usbctrl";
	static Timer timer;
	Composite cs1,cs3,cs5;
	Update cs4;
	Status cs2;
	ProcessSet cs6;
	public static boolean auhorized = false;
	//以下路径绝对路径用于Eclipse可视化编程显示，相对路径用于打包生成通用可执行程序
	//public static String imagePath = "D:\\Java\\WorkSpace\\Platform\\images\\";//images\\
  
	//public static String imagePath ="C:\\Program Files\\TrustedTerminalPlatform\\images\\";//images\\
	public static String imagePath ="images\\";
//	JUpdateComposite cs7;
	
	final ArrayList<Composite> list = new ArrayList<Composite>();	
	
	String path = System.getProperty("user.dir");
	public static DBOperate db = new DBOperate();  //数据库
	
	public static void main(String[] args) {
		//建立数据库连接
		try {
			db.connectDB();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//if(new MeasureDeamon().check()==false){
		//	return;
		//}
		ServerIPaddress.getIpConfig();	//获取IP地址配置信息
		MainForm mainForm = new MainForm();	
		try {					
			mainForm.open();
			LibMeasure.minitor=0;//add,end the thread when exit
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Time call to check verification
	 */
	public void timeToVerify(){  
		timer = new Timer(); 
		time24hToVerify Verify= new time24hToVerify(shell);
        timer.schedule(Verify, 86400000, 86400000);  
    }  
	
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();		
		createContents();
		shell.open();
		shell.layout();
		timeToVerify();
		d.start();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void createContents(){
		
		//add by zhyjun============================
		//创建主窗体
		Display display = Display.getDefault();
		shell = new Shell(display,SWT.PRIMARY_MODAL);
		shell.setSize(748, 566);
		getUsbStatus();
		mb = new MessageBox(shell);
		
		if(checkInstance()==true){
			MessageBox mb = new MessageBox(shell,SWT.OK|SWT.ICON_ERROR);
			mb.setText("错误");
			mb.setMessage("工控系统可信安全平台已经运行！");
			mb.open();
			System.exit(0);
		}
		
		
		shell.setImage(SWTResourceManager.getImage("images\\Monitor.jpg"));
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 945;
		int h = 630;
		shell.setBounds((int)(scrSize.width-w)/2,(int)(scrSize.height-h)/2,w, h);
		shell.setText("可信终端平台");		
//		shell.setBackground(new Color(display,255,255,100));
		
		Composite csTop = new Composite(shell, SWT.FLAT);
		csTop.setBounds(0, 0, 943, 110);
		csTop.setBackground(SWTResourceManager.getColor(0, 0, 128));
		csTop.setBackgroundImage(SWTResourceManager.getImage(imagePath + "back_win64.png"));
		
		//创建工具栏
		toolBar = new ToolBar(csTop, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(0, 0, 110, 110);
		toolBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));		
		toolBar.setBackgroundImage(SWTResourceManager.getImage(imagePath + "back_win64.png"));
		final ToolItem tim1 = new ToolItem(toolBar, SWT.NONE);
		tim1.setWidth(20);
		tim1.setHotImage(SWTResourceManager.getImage(imagePath + "home3.png"));
		//tim1.addListener(eventType, listener)
		tim1.setImage(SWTResourceManager.getImage(imagePath + "home2.png"));
		final ToolItem tim2 = new ToolItem(toolBar, SWT.PUSH);	
		tim2.setImage(SWTResourceManager.getImage(imagePath + "measure.png"));
		tim2.setHotImage(SWTResourceManager.getImage(imagePath + "measure3.png"));

		final ToolItem tim3 = new ToolItem(toolBar, SWT.PUSH);
		tim3.setWidth(90);
		tim3.setImage(SWTResourceManager.getImage(imagePath + "register.png"));	
		tim3.setHotImage(SWTResourceManager.getImage(imagePath + "register3.png"));
		final ToolItem tim6 = new ToolItem(toolBar, SWT.NONE);	
		tim6.setImage(SWTResourceManager.getImage(imagePath + "process.png"));
		tim6.setHotImage(SWTResourceManager.getImage(imagePath + "process3.png"));
		final ToolItem tim4 = new ToolItem(toolBar, SWT.PUSH);
		tim4.setImage(SWTResourceManager.getImage(imagePath + "update.png"));
		tim4.setHotImage(SWTResourceManager.getImage(imagePath + "update3.png"));
		final ToolItem tim5 = new ToolItem(toolBar, SWT.PUSH);
		tim5.setImage(SWTResourceManager.getImage(imagePath + "set.png"));
		tim5.setHotImage(SWTResourceManager.getImage(imagePath + "set3.png"));
		
		ToolBar toolBar_1 = new ToolBar(csTop, SWT.FLAT | SWT.RIGHT);
		toolBar_1.setBackground(SWTResourceManager.getColor(33,34,126));
		toolBar_1.setBounds(876, 0, 67, 26);
		
		//窗口最小化
		ToolItem tbitm_Min = new ToolItem(toolBar_1, SWT.NONE);
		tbitm_Min.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.setMinimized(true);
			}
		});
		tbitm_Min.setImage(SWTResourceManager.getImage(imagePath + "Min.png"));
		
		//窗口关闭
		ToolItem tbitm_Close = new ToolItem(toolBar_1, SWT.NONE);
		tbitm_Close.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(UserInfo.userNameString.equals("guest")||UserInfo.userNameString.equals("")){
					Methods.Alert("对不起，您没有权限关闭客户端！");
					return;
				}
				else{
					if(MessageDialog.openConfirm(shell, "确认", "您确定要退出客户端吗？")){
						synchronized(PTM_connect.p){
							PTM_connect.p.notify();
						}
						// LibMeasure lm = new LibMeasure();
						// lm.TM_startNormalMode(); //操作系统恢复正常模式
						System.exit(0);
					}
				}
			}
		});
		tbitm_Close.setImage(SWTResourceManager.getImage(imagePath + "Close.png"));
		
		lbl_RegisterInfo = new Label(csTop, SWT.NONE);
		lbl_RegisterInfo.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_RegisterInfo.setBackground(SWTResourceManager.getColor(33,34,126));
		lbl_RegisterInfo.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		lbl_RegisterInfo.setBounds(688, 10, 178, 18);
		lbl_RegisterInfo.setText("未注册版本，无授权数据");

		//调用外部类生成页面
		Home home = new Home(shell,0);	
		home.setBounds(0, 110, 925, 500);	
		cs1 = home;		
		Status sta = new Status(shell,0);
		cs2 = sta;		
		final Register reg = new Register(shell,0);
		cs3 = reg;	
		Update update = new Update(shell,0);
		cs4 = update;		
		Setup set = new Setup(shell,0);
		cs5 = set;
		ProcessSet proSet = new ProcessSet(shell,0);
		cs6 = proSet;
		JUpdateComposite easyupdate = new JUpdateComposite(shell,0);
		list.add(cs1);
		list.add(cs2);
		list.add(cs3);
		list.add(cs4);
		list.add(cs5);
		list.add(cs6);
		cs1.setVisible(true);
		
		
		
		
		//顶部按钮控件注册监听器
		Listener lis1 = new Listener() {  //显示1号页面
            public void handleEvent(Event event) {              	
               changeComposite(list,1);
               tim1.setImage(SWTResourceManager.getImage(imagePath + "home2.png"));
               tim2.setImage(SWTResourceManager.getImage(imagePath + "measure.png"));
               tim3.setImage(SWTResourceManager.getImage(imagePath + "register.png"));
               tim4.setImage(SWTResourceManager.getImage(imagePath + "update.png"));
               tim5.setImage(SWTResourceManager.getImage(imagePath + "set.png"));
               tim6.setImage(SWTResourceManager.getImage(imagePath + "process.png"));
            }  
        };  
        Listener lis2 = new Listener() {  //显示2号页面
            public void handleEvent(Event event) {            	
            	changeComposite(list,2); 
            	tim1.setImage(SWTResourceManager.getImage(imagePath + "home.png"));
                tim2.setImage(SWTResourceManager.getImage(imagePath + "measure2.png"));
                tim3.setImage(SWTResourceManager.getImage(imagePath + "register.png"));
                tim4.setImage(SWTResourceManager.getImage(imagePath + "update.png"));
                tim5.setImage(SWTResourceManager.getImage(imagePath + "set.png"));
                tim6.setImage(SWTResourceManager.getImage(imagePath + "process.png"));
//               if(MainForm.userId.equals("")){
//       			btn_WhiteList.setEnabled(false);
//       		}
            }  
        };
        
        
        Listener lis3 = new Listener() {  //显示3号页面
            public void handleEvent(Event event) {  
            	if(UserInfo.userType.equals("")){
            		Methods.Alert("对不起，只有管理员可以使用此功能！");
            		return;
            	}
            	
            	boolean isRegistered = false;
            	String rd_area = null;
            	String rd_name = null;
            	String rd_type = null;
            	String rd_info = null;
            	//读取已有的注册信息
        		File file = new File("C:\\TCC\\RegisterInfo.txt");
            	if (file.exists()==true){
            		isRegistered = true;
        			BufferedReader rd = null;	    	
        	    	try {		    	
        		    	rd = new BufferedReader(new FileReader(file));
        		    	
        		    	if((rd_area = rd.readLine()) == null){
        		    		isRegistered = false;
        		    	}		    	
        		    	if((rd_name = rd.readLine()) == null){
        		    		isRegistered = false;
        		    	}		    	
        		    	if((rd_type = rd.readLine()) == null){
        		    		isRegistered = false;
        		    	}
        		    	if((rd_info = rd.readLine()) == null){
        		    		isRegistered = false;
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
            	
            	}
            	
            	changeComposite(list,3);
            	tim1.setImage(SWTResourceManager.getImage(imagePath + "home.png"));
                tim2.setImage(SWTResourceManager.getImage(imagePath + "measure.png"));
                tim3.setImage(SWTResourceManager.getImage(imagePath + "register2.png"));
                tim4.setImage(SWTResourceManager.getImage(imagePath + "update.png"));
                tim5.setImage(SWTResourceManager.getImage(imagePath + "set.png"));
                tim6.setImage(SWTResourceManager.getImage(imagePath + "process.png"));
                
                final DBOperate db=org.forms.MainForm.db;
        		reg.l1.clear();
        		reg.id1.clear();
        		reg.l2.clear();
        		reg.id2.clear();
        		reg.l3.clear();
        		reg.id3.clear();//JSONArray tree = new JSONArray();
        			try {
        				ResultSet l1_rs = db.selecttable("select id,name from l1 ", 1);
        				
        				while(l1_rs.next())
        				{
        					
        					reg.l1.add(l1_rs.getString("name"));
        					reg.id1.add(l1_rs.getString("id"));
        						
        				}
        			} catch (SQLException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			} catch (InterruptedException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
        			
        			//String l1_array[]=l1.toArray();
        			String tmp[]=new String[reg.l1.size()];
        			reg.l1.toArray(tmp);
        			reg.jBasicInfoTextArea.setItems(tmp);
        			
        			if(isRegistered == true){
        				reg.jBasicInfoTextArea.setText(rd_area);
        				
        				//2
        				int index=reg.jBasicInfoTextArea.getSelectionIndex();
        				if(index<0){
        					reg.jBasicInfoTextArea.setItems(tmp);
        					isRegistered = false;
    						
    					}else{
        				
        				
	    					String index1=(String)reg.id1.get(index);
	    					
	        				try {
	    						ResultSet l2_rs = db.selecttable("select id,name from l2 where fid = "+index1, 1);
	    						
	    						while(l2_rs.next())
	    						{
	    							
	    							reg.l2.add(l2_rs.getString("name"));
	    							reg.id2.add(l2_rs.getString("id"));
	    								
	    						}
	    					} catch (SQLException e1) {
	    						// TODO Auto-generated catch block
	    						e1.printStackTrace();
	    					} catch (InterruptedException e1) {
	    						// TODO Auto-generated catch block
	    						e1.printStackTrace();
	    					}
	    					String tmp1[]=new String[reg.l2.size()];
	    					//jBasicInfoTextArea.setItems((String[])l1.toArray(tmp));
	    					String tmp2[]={};
	    					reg.jBasicInfoTextName.setItems((String[])reg.l2.toArray(tmp1));
	    					reg.jBasicInfoTextName.setText(rd_name);
	    					
	    					reg.jBasicInfoTextType.setItems(tmp2);
	    					reg.jBasicInfoTextType.clearSelection();
	    					
	    					//3
	    					int index2 = reg.jBasicInfoTextName.getSelectionIndex();
	    					if(index2<0){
	        					isRegistered = false;
	    						
	    					}else{
		    					String index3=(String) reg.id2.get(index2);
		    					try {
		    						ResultSet l3_rs = db.selecttable("select id,name from l3 where fid = "+index3, 1);
		    						
		    						while(l3_rs.next())
		    						{
		    							reg.l3.add(l3_rs.getString("name"));
		    							reg.id3.add(l3_rs.getString("id"));
		    								
		    						}
		    					} catch (SQLException e1) {
		    						// TODO Auto-generated catch block
		    						e1.printStackTrace();
		    					} catch (InterruptedException e1) {
		    						// TODO Auto-generated catch block
		    						e1.printStackTrace();
		    					}
		    					String tmp3[]=new String[reg.l3.size()];
		    					reg.jBasicInfoTextType.setItems((String[])reg.l3.toArray(tmp3));
		    					reg.jBasicInfoTextType.setText(rd_type);
		    					
		    					//4
		        				reg.jBasicInfoTextInfo.setText(rd_info);
	    					}
    					}
    					
        			}
            }  
        };  
        Listener lis4 = new Listener() {  //显示4号页面
            public void handleEvent(Event event) {  
            	if(UserInfo.userType.equals("")){
            		Methods.Alert("对不起，只有管理员可以使用此功能！");
            		return;
            	}
            	   
            	if(isTrialOK()==false){
            		mb.setText("错误");
        			mb.setMessage("本功能为高级功能，请获取授权后再使用！");
        			mb.open();
        			return;
            	}

            	changeComposite(list,4);
            	cs4.disableButtons();
            	tim1.setImage(SWTResourceManager.getImage(imagePath + "home.png"));
                tim2.setImage(SWTResourceManager.getImage(imagePath + "measure.png"));
                tim3.setImage(SWTResourceManager.getImage(imagePath + "register.png"));
                tim4.setImage(SWTResourceManager.getImage(imagePath + "update2.png"));
                tim5.setImage(SWTResourceManager.getImage(imagePath + "set.png"));
                tim6.setImage(SWTResourceManager.getImage(imagePath + "process.png"));
            }  
        };  
        Listener lis5 = new Listener() {  //显示5号页面
            public void handleEvent(Event event) {  
            	if(UserInfo.userType.equals("")){
            		Methods.Alert("对不起，只有管理员可以使用此功能！");
            		return;
            	}

            	if(isTrialOK()==false){
            		mb.setText("错误");
        			mb.setMessage("本功能为高级功能，请获取授权后再使用！");
        			mb.open();
        			return;
            	}
            	
            	changeComposite(list,5);
            	tim1.setImage(SWTResourceManager.getImage(imagePath + "home.png"));
                tim2.setImage(SWTResourceManager.getImage(imagePath + "measure.png"));
                tim3.setImage(SWTResourceManager.getImage(imagePath + "register.png"));
                tim4.setImage(SWTResourceManager.getImage(imagePath + "update.png"));
                tim5.setImage(SWTResourceManager.getImage(imagePath + "set2.png"));
                tim6.setImage(SWTResourceManager.getImage(imagePath + "process.png"));
            }  
        };  
        Listener lis6 = new Listener() {  //显示6号页面
            public void handleEvent(Event event) {  
            	if(UserInfo.userType.equals("")){
            		Methods.Alert("对不起，只有管理员可以使用此功能！");
            		return;
            	}   
            	
            	if(isTrialOK()==false){
            		mb.setText("错误");
        			mb.setMessage("本功能为高级功能，请获取授权后再使用！");
        			mb.open();
        			return;
            	}
            	
            	changeComposite(list,6);
            	tim1.setImage(SWTResourceManager.getImage(imagePath + "home.png"));
                tim2.setImage(SWTResourceManager.getImage(imagePath + "measure.png"));
                tim3.setImage(SWTResourceManager.getImage(imagePath + "register.png"));
                tim4.setImage(SWTResourceManager.getImage(imagePath + "update.png"));
                tim5.setImage(SWTResourceManager.getImage(imagePath + "set.png"));
                tim6.setImage(SWTResourceManager.getImage(imagePath + "process2.png"));
            }  
        };  
        tim1.addListener(SWT.Selection, lis1);
        tim2.addListener(SWT.Selection, lis2);
        tim3.addListener(SWT.Selection, lis3);
        tim4.addListener(SWT.Selection, lis4);
        tim5.addListener(SWT.Selection, lis5);
        tim6.addListener(SWT.Selection, lis6);
        
        MouseListener mouseListener = new MouseListener(){

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
				
		};
		
		MouseMoveListener mouseMoveListener = new MouseMoveListener(){
			@Override
			public void mouseMove(MouseEvent e) {
				// TODO Auto-generated method stub
				if(x>0)
				{
					shell.setLocation(e.x-x + shell.getLocation().x,
						e.y-y + shell.getLocation().y);
				}
			}			
		};
		
		//添加窗体鼠标事件
        cs1.addMouseListener(mouseListener);
        cs2.addMouseListener(mouseListener);
        cs3.addMouseListener(mouseListener);
        cs4.addMouseListener(mouseListener);
        cs5.addMouseListener(mouseListener);
        cs6.addMouseListener(mouseListener);
        csTop.addMouseListener(mouseListener);
		
		//鼠标移动事件
        cs1.addMouseMoveListener(mouseMoveListener);
        cs2.addMouseMoveListener(mouseMoveListener);
        cs3.addMouseMoveListener(mouseMoveListener);
        cs4.addMouseMoveListener(mouseMoveListener);
        cs5.addMouseMoveListener(mouseMoveListener);
        cs6.addMouseMoveListener(mouseMoveListener);
        csTop.addMouseMoveListener(mouseMoveListener);
        
        
        verify();//验证权限
        
	}
	
	/**
	 * 新建线程，用于刷新数据
	 * 
	 * @author tcwg
	 * 
	 */
	public static class TimeRefresh extends Thread {
		public void run() {
			while(true){
				
				 efile = new File(errorfile);
				 StatusAlertLog.resetTotal();
					
				 if(!efile.exists()){
					 try {
						efile.createNewFile();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
				FileReader fr  = null;
				FileReader frr  = null;
				FileWriter fw = null;
				BufferedReader br = null;
				BufferedReader brr = null;
				FileWriter errorfileall = null;
				try {
					 errorfileall = new FileWriter(
			    			  errorfileplus, true);
					  fr = new FileReader(efile);
					frr = new FileReader(efile);
					br = new BufferedReader(frr);
					brr =new BufferedReader(fr);
					 String line = null;
					  lineNum = 0;
					 String newline = null;
					 while ((line = br.readLine()) != null) {
						 lineNum = lineNum+1;
					   //   System.out.println("[" + lineNum + "]" + line);// 行号和行内容
					 }
					      if(lineNum>2000){					    	
					    	 while( (newline=brr.readLine())!=null){
					    	//	System.out.println(newline);
					    		 errorfileall.write(newline+"\r\n");
					    		// errorfileall.close();
					    	 }
					    	 
					    	 fw = new FileWriter(efile);
					    	 fw.write("");
					      } 
					    	
					      
					   
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("文件不存在：");
				//	efile = new File(errorfile);
					return;
				}catch (IOException ioe) {
					
					System.out.println("文件读取错误：" );

					return;

				} finally {

					try {
						if(errorfileall!=null){
							errorfileall.close();
							errorfileall=null;
						}
						if(fw!=null){
							fw.close();
							fw = null;
						}
						if(brr!=null){
							brr.close();
							brr=null;
						}

						if (br != null) {

							br.close();

							br = null;
						}
						if (frr != null) {

							frr.close();

							frr = null;
						}
						if (fr != null) {

							fr.close();

							fr = null;
						}
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
				
				try {
					sleep(60000);
			
				} catch (Exception e) {
					e.printStackTrace(); 
				}					
			}
			}
		}



	/**
	 * 切换页面
	 * @param list
	 * @param n
	 */
	private void changeComposite(ArrayList<Composite> list, int n){		
		if(n==6){
			cs6.measure.thd_Refresh.resume();
		}
		else{
			cs6.measure.thd_Refresh.suspend();
		}
		
		list.get(this.num - 1).setVisible(false);
		list.get(n-1).redraw();
		list.get(n-1).setVisible(true);
		this.num = n;
	}
	
	/**
	 * 验证系统授权
	 */
	private void verify(){
		String tipStr = "";		//提示信息
		int type = -100;		//授权类型
		long cmp = -1;			//剩余天数
		long authcmp=-1;
		
		AuthVerify av=new AuthVerify();
		int ret=av.Verify();
		System.out.println("verify ret value:"+ret);
		
		
if(ret>800000000&&ret<900000000){
			
			int authdate = ret%800000000;
			ret=2;
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			Date authlastDate = null;
			String authdate1=String.valueOf(authdate);
			try {
				authlastDate = formatter1.parse(authdate1);		//鍒版湡鏃堕棿
			} catch (ParseException e) {
				e.printStackTrace();
				type = -100;
			}
			Date currentDate = new Date();	//褰撳墠鏃堕棿
			cmp = (authlastDate.getTime() - currentDate.getTime())/(24*60*60*1000) + 1;
		}
		if(ret>90000000&&ret<1000000000){
			String authtime = (ret+"").substring(0, 9);
			int auth_time = (Integer.parseInt(authtime));
			ret=1;
			int authdate = auth_time%900000000;
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			Date authlastDate = null;
			String authdate1=String.valueOf(authdate);
			try {
				authlastDate = formatter1.parse(authdate1);		//鍒版湡鏃堕棿
			} catch (ParseException e) {
				e.printStackTrace();
				type = -100;
			}
			Date currentDate = new Date();	//褰撳墠鏃堕棿
			authcmp = (authlastDate.getTime() - currentDate.getTime())/(24*60*60*1000) + 1;
		}

		
		
		
		if(ret==-1||ret==0||ret==-3||ret==1||ret==2)
			type = ret;
		else {	
			
			type=2;			
			String strDate = (ret+"").substring(0, 8);
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date lastDate = null;
			try {
				lastDate = formatter.parse(strDate);		//到期时间
			} catch (ParseException e) {
				e.printStackTrace();
				type = -100;
			}
			Date currentDate = new Date();	//当前时间
			cmp = (lastDate.getTime() - currentDate.getTime())/(24*60*60*1000) + 1;
		}/*else{
			type=1;			
			String strDate = (ret+"").substring(0, 8);
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date lastDate = null;
			try {
				lastDate = formatter.parse(strDate);		//到期时间
			} catch (ParseException e) {
				e.printStackTrace();
				type = -100;
			}
			Date currentDate = new Date();	//当前时间
			cmp = (lastDate.getTime() - currentDate.getTime())/(24*60*60*1000) + 1;
		}*/
		
		
		switch(type){
		
		case -1:			
			tipStr = "请获取授权后再使用软件！";
			message = "授权错误，请获取授权后再使用软件！";
			mb.setText("可信终端软件");
			mb.setMessage("授权错误，请获取授权后再使用软件！");		
			mb.open();
			System.exit(0);
			break;
			
		/*case -2:			
			tipStr = "软件授权已过期！";
			message = "软件授权已过期，请重新获取授权后再使用软件！";
			mb.setText("可信终端软件");
			mb.setMessage("软件授权已过期，请重新获取授权后再使用软件！");		
			mb.open();
			System.exit(0);
			break;
		*/
		
		case -3:			
			tipStr = "软件试用已过期！";
			message = "软件试用已过期，请获取授权后再使用软件！";
			mb.setText("可信终端软件");
			mb.setMessage("软件试用已过期，请获取授权后再使用软件！");		
			mb.open();
			System.exit(0);
			break;
			
		case 1:			
			//tipStr = "已（短期）注册授权！";
			tipStr = "试用版，短期授权剩余"+ authcmp +"天";
			auhorized = true;
			break;
			
		case 0:
			tipStr = "已注册授权";
			auhorized = true;
			break;
		//case 1:
			/*if(cmp<0){
				tipStr = "软件授权已过期";				
				message = "软件授权已过期，请重新获取授权后再使用软件！";					
			}
			else
			{*/
			//	tipStr = "已注册，有效期剩余"+ cmp +"天";
			//}
			//break;
		case 2:			
			/*if(cmp<0){
				tipStr = "软件试用已过期";
				message = "软件试用已过期，请获取授权后再使用软件！";
			}
			else
			{*/
				tipStr = "未注册，试用期剩余"+ cmp +"天";
			//}
			break;
		default:
			tipStr = "获取授权失败！";
			message = "获取授权失败，请获取授权后再使用软件！";
			mb.setText("可信终端软件");
			mb.setMessage("获取授权失败，请获取授权后再使用软件！");		
			mb.open();
			System.exit(0);			
		}
		
		lbl_RegisterInfo.setText(tipStr);
		
		if(type==2||type==1){
			lbl_RegisterInfo.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			Home.lbl_Tips.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		}	
		 Home.lbl_Tips.setText(tipStr);
		
		if(type==2||type==1)
		{
			mb.setText("可信终端软件");
			mb.setMessage("免责声明：非合法授权软件，试用期间一切问题及后果由使用者自行负责！");		
			mb.open();
		}
	}
	
	/**
	 * 判断该应用是否已启动
	 * @return 若不存在实例返回false，若存在实例返回true
	 */
	private boolean checkInstance(){
		FileLock lock=null;
		try
		{
		      //获得实例标志文件
		      File flagFile=new File("c:/TCC/instance");//c:/instance");
		      //如果不存在就新建一个
		      if(!flagFile.exists())flagFile.createNewFile();
		      //获得文件锁
		      lock=new FileOutputStream("c:/TCC/instance").getChannel().tryLock();
		      		     
		}catch(Exception ex){
			ex.printStackTrace();
			//System.out.println("Lock Fail!");
		}
		 return false;
	}
	
	/**
	 * 检查USB管控状态，首次启动强制开启
	 * @return 若不存在实例返回false，若存在实例返回true
	 */
	private void getUsbStatus() {
		
		
		String strFile = usbcontrolfile;
		File mfile = new File(strFile);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(mfile);
			br = new BufferedReader(fr);
			
			
		} catch (FileNotFoundException e) {
			// Debug.println("Measurement file not found");
			// e.printStackTrace();
			try {
				FileWriter wr=new FileWriter(usbcontrolfile);
				wr.write("1");
				wr.flush();
				wr.close();
				return;
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
	
	private boolean isTrialOK(){//检测 是否在试用版下，管控次数超过3次
		
		if(auhorized==false){//仍然处于试用期
			File file = new File("C:\\TCC\\ctrlnum");	    	
	    	BufferedReader rd = null;	    	
	    	String readnum = null;
			int rdnum = 0;			
			
			if (file.exists()!=true){
        		return true;
        	}					
			
	    	try {		    	
		    	rd = new BufferedReader(new FileReader(file));
		    	
		    	if((readnum = rd.readLine()) == null){
		    		file.delete();
		    		rd.close();		
		    		return true;
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
			if(rdnum>4||rdnum==4){
				return false;
			}else{
				return true;
			}
	    	  	
	    	
		}else{
			return true;
		}
	}
	
}


