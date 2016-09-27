package org.forms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.dboperate.DBOperate;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.wsoperate.RegistServiceStub;
import org.wsoperate.WSOperator;

import ui.*;

public class Register extends Composite {
	public static IPText txt_ServerIp;
	public static Text txt_ServerPort;
	Combo jBasicInfoTextArea;
	Combo jBasicInfoTextName;
	Combo jBasicInfoTextType;
	Text jBasicInfoTextInfo;
	private Text txt_Productor;
	private Text txt_SerialNo;
	private Text txt_Os;
	private Text txt_Mac;
	private Text txt_PublicKey;
	private Text text_IP;		

	private String TCM_EK;
	private String TCM_PK;
    private String TCM_ver;
    private String TCM_Manufacturer;
    private String IP_adr;
    private String MAC_adr;
    private String os_info;
    private String client_area;
    private String device_name;
    private String device_info;
    private String device_type;
    
    private String server_IP;
    private String server_port;
    
	static Text ptm_pubkey_text;
	private UserBean user = new UserBean();
//	static Composite page;
	private TCCUtilit utilit = new TCCUtilit();
	private RegisterToServer register = new RegisterToServer();
	
	
	public ArrayList l1 = new ArrayList();
	public final ArrayList id1 = new ArrayList();
	final ArrayList l2 = new ArrayList();
	final ArrayList id2 = new ArrayList();
	final ArrayList l3 = new ArrayList();
	final ArrayList id3 = new ArrayList();
	

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Register(Composite parent, int style) {
		super(parent, SWT.NONE);
		// setBackground(new Color(getDisplay(),255,255,100));
		setBounds(0, 110, 900, 500);
		setVisible(false);

   	
		
		
		Label lbl_ServerIPInfo = new Label(this, SWT.NONE);
		lbl_ServerIPInfo.setImage(SWTResourceManager.getImage("images\\IconServer.png"));
		lbl_ServerIPInfo.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		lbl_ServerIPInfo.setBounds(103, 25, 145, 27);
		lbl_ServerIPInfo.setText("服务器IP信息");

		Group group_IPInfo = new Group(this, SWT.NONE);
		group_IPInfo.setBounds(56, 58, 376, 142);

		Label lbl_ServerIP = new Label(group_IPInfo, SWT.NONE);
		lbl_ServerIP.setFont(SWTResourceManager.getFont("宋体", 13, SWT.NORMAL));
		lbl_ServerIP.setBounds(10, 34, 58, 21);
		lbl_ServerIP.setText("IP地址：");

		Label lbl_ServerPort = new Label(group_IPInfo, SWT.NONE);
		lbl_ServerPort
				.setFont(SWTResourceManager.getFont("宋体", 13, SWT.NORMAL));
		lbl_ServerPort.setBounds(10, 95, 58, 21);
		lbl_ServerPort.setText("端口号：");

		txt_ServerIp = new IPText(group_IPInfo, SWT.BORDER);
		txt_ServerIp.setBackground(SWTResourceManager.getColor(204, 232, 207));
		txt_ServerIp.setText(ServerIPaddress.getIp());
		txt_ServerIp.setBounds(88, 25, 253, 30);

		txt_ServerPort = new Text(group_IPInfo, SWT.BORDER);
		txt_ServerPort.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));
		txt_ServerPort.setText(ServerIPaddress.getPort() +"");
		txt_ServerPort.setBounds(88, 86, 253, 30);

		Label lblNewLabel = new Label(group_IPInfo, SWT.NONE);
		lblNewLabel.setBounds(195, 36, 10, 17);
		lblNewLabel.setText(".");

		Label label = new Label(group_IPInfo, SWT.NONE);
		label.setBounds(135, 36, 4, 21);
		label.setText(".");


		Label lbl_BasicInfo = new Label(this, SWT.NONE);
		lbl_BasicInfo.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		lbl_BasicInfo.setBounds(530, 25, 152, 27);
		lbl_BasicInfo.setText("基本信息");

		Group group_BasicInfo = new Group(this, SWT.NONE);
		group_BasicInfo.setBounds(490, 58, 382, 142);
		
		
		//==============================zhyjun=====================================
		{
			Label jBasicInfoLabelArea = new Label(group_BasicInfo, SWT.NONE);
			jBasicInfoLabelArea.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
			jBasicInfoLabelArea.setBounds(10, 10, 70, 20);
			jBasicInfoLabelArea.setText("厂区：");
		}

		{
			Label jBasicInfoLabelName = new Label(group_BasicInfo, SWT.NONE);
			jBasicInfoLabelName.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
			jBasicInfoLabelName.setBounds(10, 40, 70, 20);
			jBasicInfoLabelName.setText("工艺：");
		}
		
		{
			Label jBasicInfoLabelType = new Label(group_BasicInfo, SWT.NONE);
			jBasicInfoLabelType.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
			jBasicInfoLabelType.setBounds(10, 70, 70, 20);
			jBasicInfoLabelType.setText("系统类型：");
		}
		
		{
			Label jBasicInfoLabelArea = new Label(group_BasicInfo, SWT.NONE);
			jBasicInfoLabelArea.setFont(SWTResourceManager.getFont("宋体", 13, SWT.NORMAL));
			jBasicInfoLabelArea.setBounds(10, 100, 70, 20);
			jBasicInfoLabelArea.setText("设备名称：");
		}
		
		/*
		
		jBasicInfoTextArea = new Text(group_BasicInfo, SWT.BORDER);
		jBasicInfoTextArea.setBounds(85, 10, 250, 20);

		jBasicInfoTextName = new Text(group_BasicInfo, SWT.BORDER);
		jBasicInfoTextName.setBounds(85, 40, 250, 20);
		
		String[] type = {"工程师站", "操作员站"};
		jBasicInfoTextType = new Combo(group_BasicInfo, SWT.DROP_DOWN | SWT.READ_ONLY);
		jBasicInfoTextType.setBounds(85, 70, 250, 20);
		jBasicInfoTextType.setItems(type);

		jBasicInfoTextInfo = new Text(group_BasicInfo, SWT.BORDER);
		jBasicInfoTextInfo.setBounds(85, 100, 250, 20);
		
		*/
		
		//String[] type=null;
		jBasicInfoTextArea = new Combo(group_BasicInfo, SWT.DROP_DOWN | SWT.READ_ONLY);
		jBasicInfoTextArea.setBounds(85, 10, 250, 20);
		//jBasicInfoTextArea.setItems(type);
		
		jBasicInfoTextName = new Combo(group_BasicInfo, SWT.DROP_DOWN | SWT.READ_ONLY);
		jBasicInfoTextName.setBounds(85, 40, 250, 20);
		//jBasicInfoTextName.setItems(type);
		
		jBasicInfoTextType = new Combo(group_BasicInfo, SWT.DROP_DOWN | SWT.READ_ONLY);
		jBasicInfoTextType.setBounds(85, 70, 250, 20);
		//jBasicInfoTextType.setItems(type);
		
		jBasicInfoTextInfo = new Text(group_BasicInfo, SWT.BORDER);
		jBasicInfoTextInfo.setBounds(85, 100, 250, 20);
		

		
		final DBOperate db=org.forms.MainForm.db;
		/*
		//JSONArray tree = new JSONArray();
			try {
				ResultSet l1_rs = db.selecttable("select id,name from l1 ", 1);
				
				while(l1_rs.next())
				{
					
					l1.add(l1_rs.getString("name"));
					id1.add(l1_rs.getString("id"));
						
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//String l1_array[]=l1.toArray();
			String tmp[]=new String[l1.size()];
			l1.toArray(tmp);
			jBasicInfoTextArea.setItems(tmp);
			*/
			jBasicInfoTextArea.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					int index=jBasicInfoTextArea.getSelectionIndex();
					String index1=(String)id1.get(index);
					l2.clear();
					id2.clear();
					l3.clear();
					id3.clear();
					//id1.
					try {
						ResultSet l2_rs = db.selecttable("select id,name from l2 where fid = "+index1, 1);
						
						while(l2_rs.next())
						{
							
							l2.add(l2_rs.getString("name"));
							id2.add(l2_rs.getString("id"));
								
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String tmp[]=new String[l2.size()];
					//jBasicInfoTextArea.setItems((String[])l1.toArray(tmp));
					String tmp1[]={};
					jBasicInfoTextName.setItems((String[])l2.toArray(tmp));
					jBasicInfoTextType.setItems(tmp1);
					jBasicInfoTextType.clearSelection();
					
				}
			});
			jBasicInfoTextName.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					
					int index=jBasicInfoTextName.getSelectionIndex();
					String index1=(String) id2.get(index);
					l3.clear();
					id3.clear();
					try {
						ResultSet l3_rs = db.selecttable("select id,name from l3 where fid = "+index1, 1);
						
						while(l3_rs.next())
						{
							l3.add(l3_rs.getString("name"));
							id3.add(l3_rs.getString("id"));
								
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String tmp[]=new String[l3.size()];
					jBasicInfoTextType.setItems((String[])l3.toArray(tmp));
					
				}
			});
			
		//==============================zhyjun=====================================
		
		
		
		/*
		{
			Label jBasicInfoLabelArea = new Label(group_BasicInfo, SWT.NONE);
			jBasicInfoLabelArea.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
			jBasicInfoLabelArea.setBounds(10, 10, 70, 20);
			jBasicInfoLabelArea.setText("区域：");
		}

		{
			Label jBasicInfoLabelName = new Label(group_BasicInfo, SWT.NONE);
			jBasicInfoLabelName.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
			jBasicInfoLabelName.setBounds(10, 40, 70, 20);
			jBasicInfoLabelName.setText("设备名称：");
		}
		
		{
			Label jBasicInfoLabelType = new Label(group_BasicInfo, SWT.NONE);
			jBasicInfoLabelType.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
			jBasicInfoLabelType.setBounds(10, 70, 70, 20);
			jBasicInfoLabelType.setText("设备类型：");
		}
		
		{
			Label jBasicInfoLabelArea = new Label(group_BasicInfo, SWT.NONE);
			jBasicInfoLabelArea.setFont(SWTResourceManager.getFont("宋体", 13, SWT.NORMAL));
			jBasicInfoLabelArea.setBounds(10, 100, 70, 20);
			jBasicInfoLabelArea.setText("设备描述：");
		}
		
		jBasicInfoTextArea = new Text(group_BasicInfo, SWT.BORDER);
		jBasicInfoTextArea.setBounds(85, 10, 250, 20);

		jBasicInfoTextName = new Text(group_BasicInfo, SWT.BORDER);
		jBasicInfoTextName.setBounds(85, 40, 250, 20);
		
		String[] type = {"工程师站", "操作员站"};
		jBasicInfoTextType = new Combo(group_BasicInfo, SWT.DROP_DOWN | SWT.READ_ONLY);
		jBasicInfoTextType.setBounds(85, 70, 250, 20);
		jBasicInfoTextType.setItems(type);

		jBasicInfoTextInfo = new Text(group_BasicInfo, SWT.BORDER);
		jBasicInfoTextInfo.setBounds(85, 100, 250, 20);
		
		ArrayList l1 = new ArrayList();
		final ArrayList id1 = null;
		final ArrayList l2 = null;
		final ArrayList id2 = null;
		final ArrayList l3 = null;
		final ArrayList id3 = null;
		final DBOperate db=org.forms.MainForm.db;
		
		//JSONArray tree = new JSONArray();
			try {
				ResultSet l1_rs = db.selecttable("select id,name from l1", 2);
				
				while(l1_rs.next())
				{
					String name=l1_rs.getString("name");
					l1.add(name);
					id1.add(l1_rs.getInt("id"));
						
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//String l1_array[]=l1.toArray();
			//jBasicInfoTextType.setItems((String[])l1.toArray());
			 * */
			 

		Group group_TcmInfo = new Group(this, SWT.NONE);
		group_TcmInfo.setBounds(56, 227, 376, 184);

		Label lbl_Productor = new Label(group_TcmInfo, SWT.NONE);
		lbl_Productor.setFont(SWTResourceManager.getFont("宋体", 13, SWT.NORMAL));
		lbl_Productor.setBounds(28, 43, 40, 16);
		lbl_Productor.setText("厂商：");
		
		txt_Productor = new Text(group_TcmInfo, SWT.BORDER);
		txt_Productor.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));//改变字体大小样式
		txt_Productor.setEditable(false);
		txt_Productor.setBounds(91, 30, 251, 28);
		PTM_connect.Load_TCM_info();
		txt_Productor.setText(PTM_connect.TCM_Manufacture);
		user.setManufacture(PTM_connect.TCM_Manufacture);		

		Label lbl_SerialNo = new Label(group_TcmInfo, SWT.NONE);
		lbl_SerialNo.setFont(SWTResourceManager.getFont("宋体", 13, SWT.NORMAL));
		lbl_SerialNo.setBounds(10, 91, 61, 22);
		lbl_SerialNo.setText("版本号：");

		txt_SerialNo = new Text(group_TcmInfo, SWT.BORDER);
		txt_SerialNo.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));//改变字体大小样式
		txt_SerialNo.setEditable(false);
		txt_SerialNo.setBounds(90, 85, 251, 28);
		txt_SerialNo.setText(PTM_connect.TCM_Version);
		user.setSequence(PTM_connect.TCM_Version);

		txt_PublicKey = new Text(group_TcmInfo, SWT.BORDER);
		txt_PublicKey.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));//改变字体大小样式
		txt_PublicKey.setEditable(false);
		txt_PublicKey.setBounds(91, 140, 251, 28);		
		PTM_connect.Load_pubkey();
		txt_PublicKey.setText(PTM_connect.pubkey);
		user.setPubkey(PTM_connect.pubkey);
		PTM_connect.Load_pubEK();
		user.setEK(PTM_connect.pubEK);

		Label lbl_PublicKey = new Label(group_TcmInfo, SWT.NONE);
		lbl_PublicKey.setFont(SWTResourceManager.getFont("宋体", 13, SWT.NORMAL));
		lbl_PublicKey.setBounds(28, 146, 40, 22);
		lbl_PublicKey.setText("公钥：");

		Group group_TerminalInfo = new Group(this, SWT.NONE);
		group_TerminalInfo.setBounds(490, 227, 382, 184);

		txt_Os = new Text(group_TerminalInfo, SWT.BORDER);
		txt_Os.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));//改变字体大小样式
		txt_Os.setEditable(false);
		txt_Os.setBounds(84, 32, 254, 28);	
		Properties props=System.getProperties(); //获得系统属性集 
		String osName = props.getProperty("os.name"); //操作系统名称
		String osVersion = props.getProperty("os.version"); //操作系统版本
		txt_Os.setText(osName+"    "+osVersion);
		user.setOs(osName+"    "+osVersion);

		txt_Mac = new Text(group_TerminalInfo, SWT.BORDER);
		txt_Mac.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));//改变字体大小样式
		txt_Mac.setEditable(false);
		txt_Mac.setBounds(84, 84, 254, 28);
		String [] macList = getMACAddress(); //MAC地址
		String macTotal = null;
		if(macList[0]==null){		
			macTotal = "N/A";
		}else{
			macTotal = "(1)" + macList[0];
			for(int i=1; i<10; i++){
				
				if(macList[i]!=null){
					macTotal = macTotal + "  (" + (i+1) + ")" + macList[i];
				}else break;
			}	
		}
				
		txt_Mac.setText(macTotal);
		user.setMac(macTotal);

		Label lblOs = new Label(group_TerminalInfo, SWT.NONE);
		lblOs.setFont(SWTResourceManager.getFont("宋体", 13, SWT.NORMAL));
		lblOs.setBounds(10, 43, 68, 17);
		lblOs.setText("OS版本：");

		Label lblMac = new Label(group_TerminalInfo, SWT.NONE);
		lblMac.setFont(SWTResourceManager.getFont("宋体", 13, SWT.NORMAL));
		lblMac.setBounds(34, 95, 44, 17);
		lblMac.setText("MAC：");
		

		text_IP = new Text(group_TerminalInfo, SWT.BORDER);
		text_IP.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));//改变字体大小样式
		text_IP.setEditable(false);
		text_IP.setBounds(84, 134, 254, 28);
		String ip = getIpAddress(); //IP地址
		text_IP.setText(ip);
		user.setIp(ip);

		Label lbl_TerminalIP = new Label(group_TerminalInfo, SWT.NONE);
		lbl_TerminalIP.setBounds(9, 145, 69, 17);
		lbl_TerminalIP.setFont(SWTResourceManager.getFont("宋体", 13, SWT.NORMAL));
		lbl_TerminalIP.setText("IP地址：");
		

		Label lbl_TcmInfo = new Label(this, SWT.NONE);
		lbl_TcmInfo.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		lbl_TcmInfo.setBounds(103, 206, 166, 27);
		lbl_TcmInfo.setText("TCM信息");

		Label lbl_TerminalInfo = new Label(this, SWT.NONE);
		lbl_TerminalInfo.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		lbl_TerminalInfo.setBounds(530, 206, 166, 27);
		lbl_TerminalInfo.setText("终端信息");

		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
//				ServerIPaddress.ip = txt
				RegisterInfo();

			}
		});
		button.setFont(SWTResourceManager.getFont("汉仪旗黑-55S", 12, SWT.NORMAL));
		button.setBounds(299, 442, 84, 33);
		button.setText("\u6CE8\u518C");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setImage(SWTResourceManager.getImage("images\\IconServer.png"));
		label_1.setBounds(66, 25, 30, 27);
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setImage(SWTResourceManager.getImage("images\\IconPepole.png"));
		label_2.setBounds(500, 25, 30, 27);
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setImage(SWTResourceManager.getImage("images\\IconTPM.png"));
		label_3.setBounds(66, 194, 30, 45);
		
		Label label_4 = new Label(this, SWT.NONE);
		label_4.setImage(SWTResourceManager.getImage("images\\IconTerminal.png"));
		label_4.setBounds(500, 206, 30, 27);
		
		Button button_Refresh = new Button(this, SWT.NONE);
		button_Refresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				//by yangbo
				//////////////
				jBasicInfoTextArea.deselectAll();
    			jBasicInfoTextName.deselectAll();
    			jBasicInfoTextType.deselectAll();
    			//////////////////
				
				TreeDownloaderProcess tree_thread=new TreeDownloaderProcess();
				tree_thread.start();
				try {
		            Thread.sleep(1000);
		        } catch (InterruptedException e) {
		            e.printStackTrace(); 
		        }
				
				 final DBOperate db=org.forms.MainForm.db;
	        		l1.clear();
	        		id1.clear();
	        		l2.clear();
	        		id2.clear();
	        		l3.clear();
	        		id3.clear();//JSONArray tree = new JSONArray();
	        		
	        		//下载厂区列表，可放在注册子页面的构造函数里
	        			try {
	        				ResultSet l1_rs = db.selecttable("select id,name from l1 ", 1);
	        				
	        				while(l1_rs.next())
	        				{
	        					
	        					l1.add(l1_rs.getString("name"));
	        					id1.add(l1_rs.getString("id"));
	        						
	        				}
	        			} catch (SQLException e1) {
	        				// TODO Auto-generated catch block
	        				e1.printStackTrace();
	        			} catch (InterruptedException e1) {
	        				// TODO Auto-generated catch block
	        				e1.printStackTrace();
	        			}
	        			
	        			//String l1_array[]=l1.toArray();
	        			String tmp[]=new String[l1.size()];
	        			l1.toArray(tmp);
	        		//	jBasicInfoTextName.
	        		
	        			jBasicInfoTextArea.setItems(tmp);
	        			jBasicInfoTextName.deselectAll();
	        			jBasicInfoTextType.deselectAll();
	        			jBasicInfoTextName.removeAll();
	        			jBasicInfoTextType.removeAll();
			}
		});
		button_Refresh.setText("刷新基本信息");
		button_Refresh.setFont(SWTResourceManager.getFont("汉仪旗黑-55S", 12, SWT.NORMAL));
		button_Refresh.setBounds(516, 442, 127, 33);
	}

	/**
	 * 
	 */
	public void RegisterInfo() {
		server_IP = txt_ServerIp.getText();
		server_port = txt_ServerPort.getText();
		//检查IP地址和port格式
		try {
			System.out.println(server_IP);
			System.out.println(server_port);
			if (server_IP.length() == 0) {
				MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);
				mb.setText("执行结果");
				mb.setMessage("请输入服务器地址");
				mb.open();
				return;
			} else {
				if (!utilit.IPisCorrect(server_IP)) {
					MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);					
					mb.setText("执行结果");
					mb.setMessage("服务器地址不合法");
					mb.open();
					return;
				}
			}
			if (server_port.length() == 0) {
				MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);
				mb.setText("执行结果");
				mb.setMessage("请输入端口号");
				mb.open();
				return;
			} 
			else {
				if(Integer.parseInt(server_port)<1 || Integer.parseInt(server_port)>65535) {
					MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);
					mb.setText("执行结果");
					mb.setMessage("端口号不合法");
					mb.open();
					return;
				}
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//保存修改后的IP地址和port		
		ServerIPaddress.setIp( txt_ServerIp.getText());
		ServerIPaddress.setPort( Methods.StringtoInt(txt_ServerPort.getText()));		
		Methods.updateCommunicationSet();
		
		//更新XML配置文件中IP地址和port
	    XMLReader xmlreader = new XMLReader();
	    xmlreader.insertEle("WSIP", "http://"+server_IP+":"+server_port); 
	    
		//查看服务器连接状态
		WSOperator ws = new WSOperator();
		if(ws.getWSStatus() == false) {
			//System.out.println("服务器连接失败");
			//此处有提示框
			MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);					
			mb.setText("执行结果");
			mb.setMessage("服务器连接失败！");
			mb.open();
			//JOptionPane.showMessageDialog(null, "服务器连接失败！");
			return;
		}
		else {
			System.out.println("服务器连接成功");
		}
				    
		
		
		//平台注册流程		
		TCM_PK = txt_PublicKey.getText();
	    TCM_ver = txt_SerialNo.getText();
	    TCM_Manufacturer = txt_Productor.getText();
	    IP_adr = text_IP.getText();
	    MAC_adr = txt_Mac.getText();
	    os_info = txt_Os.getText();
	    
		//TCM_PK,TCM_ver,CM_Manufacturer已读取
		//IP_adr,MAC_adr,os_info已读取
		if(jBasicInfoTextArea.getText()!=null && !jBasicInfoTextArea.getText().equals(""))
			client_area = jBasicInfoTextArea.getText();
		else {
			MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);					
			mb.setText("提示");
			mb.setMessage("区域信息输入有误，请重新输入！");
			mb.open();
			//JOptionPane.showMessageDialog(null, "区域信息输入有误，请重新输入！");
			return;
		}
		if(jBasicInfoTextName.getText()!=null && !jBasicInfoTextName.getText().equals(""))
			device_name = jBasicInfoTextName.getText();
		else {
			MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);					
			mb.setText("提示");
			mb.setMessage("设备名称输入有误，请重新输入！");
			mb.open();
			//JOptionPane.showMessageDialog(null, "设备名称输入有误，请重新输入！");
			return;
		}
		
		if(jBasicInfoTextInfo.getText()!=null && !jBasicInfoTextInfo.getText().equals(""))
			device_info = jBasicInfoTextInfo.getText();
		else {
			MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);					
			mb.setText("提示");
			mb.setMessage("设备描述输入有误，请重新输入！");
			mb.open();
			//JOptionPane.showMessageDialog(null, "设备描述输入有误，请重新输入！");	
			return;
		}
		
		if(jBasicInfoTextType.getText()!=null && !jBasicInfoTextType.getText().equals(""))
			//client_area = jBasicInfoTextType.getText();
			device_type = (String) jBasicInfoTextType.getText();
		else {
			MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);					
			mb.setText("提示");
			mb.setMessage("区域信息输入有误，请重新输入！");
			mb.open();
			//JOptionPane.showMessageDialog(null, "区域信息输入有误，请重新输入！");
			return;
		}
		
		//=========================zhyjun=========================================
		int index=jBasicInfoTextType.getSelectionIndex();
//		String tree_id=(String)id3.get(index);
//		String tree_name=(String)l3.get(index);
		//=========================zhyjun=========================================
		
		
		
		//System.out.println("device_type:"+device_type);
		
		//读取PUBEK        	         	
	    File pubkeyfile = new File("C:/TCC/pub_EK");//"C:/WINDOWS/pub_EK");
		try {
			BufferedReader pkreader = new BufferedReader(new FileReader(pubkeyfile));
			TCM_EK = pkreader.readLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	        	   	    
		
		String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("registop");
	    try {
	    	RegistServiceStub stub = new RegistServiceStub(null, trans_value);					    	
			RegistServiceStub.Register regfunction = new RegistServiceStub.Register();
			regfunction.setIn0(TCM_PK);
			regfunction.setIn1(TCM_ver);
			regfunction.setIn2(TCM_Manufacturer);
			regfunction.setIn3(IP_adr);
			regfunction.setIn4(MAC_adr);
			regfunction.setIn5(os_info);
			regfunction.setIn6(client_area);
			regfunction.setIn7(device_name);
			regfunction.setIn8(device_info);
			regfunction.setIn9(device_type);
			regfunction.setIn10(TCM_EK);
			
		    int result = stub.register(regfunction).getOut();
		    if(result == 0) {
		    	//System.out.println("客户端已注册！");
				//此处有提示框
		    	MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);					
				mb.setText("提示");
				mb.setMessage("客户端已注册！");
				mb.open();
		    	//JOptionPane.showMessageDialog(null, "客户端已注册！");
		    }		    		
		    else if(result == 1) 
		    {
		    	//System.out.println("客户端注册成功！");
		    	//保存注册信息
		    	File file = new File("C:\\TCC\\RegisterInfo.txt");
		    	BufferedWriter wr = null;
		    	
		    	try {
			    	wr = new BufferedWriter(new FileWriter(file));
			    	//String tempString = null;
			    	wr.write(client_area);
			    	wr.write("\n");
			    	wr.write(device_name);
			    	wr.write("\n");
			    	wr.write(device_type);
			    	wr.write("\n");
			    	wr.write(device_info);
			    	wr.write("\n");
			    	wr.close();
		    	} catch (IOException e) {
		    		e.printStackTrace();
		    	} finally {
			    	if (wr != null) {
				    	try {
				    		wr.close();
				    	} catch (IOException e1) {
				    	}
			    	}
		    	}
		    	
		    			    		    	
		    	
				//此处有提示框
		    	clearWhiteList();
		    	MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);					
				mb.setText("提示");
				mb.setMessage("客户端注册成功！");
				mb.open();
		    	//JOptionPane.showMessageDialog(null, "客户端注册成功！");
		    }  
		    else if(result == 2){
		    	MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);					
				mb.setText("提示");
				mb.setMessage("客户端注册数量已达上限！");
				mb.open();
		    }

			
		} catch (RemoteException e1) {
		
			e1.printStackTrace();
		}	
	}
	
	/**
	 * 清空白名单
	 */
	private void clearWhiteList(){
		File f1 = new File("c:/TCC/wl");
		File f2 = new File("c:/TCC/usbwl");
		FileWriter fw;
		FileWriter fw2;
		try {
			fw = new FileWriter(f1);
			fw.write("");
			fw.close();
			
			fw2 = new FileWriter(f2);
			fw2.write("");
			fw2.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取Mac地址
	 */
	public static String [] getMACAddress(){ 
		  
        String[] address = new String[10];// = "N/A"; 
        for(int i=0; i<10; i++){
        	address[i] = null;
        }
        String os = System.getProperty("os.name");          
        if (os != null && os.startsWith("Windows")) {   
            try {
                String command = "cmd.exe /c ipconfig /all";
                Process p = Runtime.getRuntime().exec(command);
                BufferedReader br =new BufferedReader(new InputStreamReader(p.getInputStream(),"GB2312"));
                String line; 
                int j = 0;
                while ((line = br.readLine()) != null) {
                 //  if (line.indexOf("Physical Address") > 0 ) 
                   if ((line.indexOf("物理地址") > 0)||(line.indexOf("Physical Address") > 0) )
                    { 
                        int index = line.indexOf(":");
                        index += 2;
                        
                        if(line.substring(index).trim().length()>18){
                        	//过滤其他混乱的MAC
                        	continue;
                        }
                        address[j] = line.substring(index).trim();
                        j++;
                        //break;
                    }
                }
                br.close();
                return address;
            }
            catch (Exception e) {
            	return address;
            }
        }  
        return address;    
    } 
	
	/**
	 * 获取IP地址
	 * @return
	 */
//	private static String getIpAddress() {  
//        try{
//        	InetAddress address = InetAddress.getLocalHost(); 
//        	return address.getHostAddress(); 
//        }
//        catch (Exception e) {
//        	return "N/A";
//        }          
//    }	
	public String getIpAddress() {  
		String addressList = "";
		int n = 0;
		
		 String os = System.getProperty("os.name");          
	        if (os != null && os.startsWith("Windows")) {   
	            try {
	                String command = "cmd.exe /c ipconfig /all";
	                Process p = Runtime.getRuntime().exec(command);
	                BufferedReader br =new BufferedReader(new InputStreamReader(p.getInputStream(),"GB2312"));
	                String line; 
	                int j = 0;
	                while ((line = br.readLine()) != null) {
	                 //  if (line.indexOf("Physical Address") > 0 ) 
	                   if ((line.indexOf("IPv4 地址") > 0)||(line.indexOf("IP Address") > 0) )
	                    { 
	                        int index = line.indexOf(":");
	                        index += 2;                       
	                        
	                       // if(line.substring(index).trim().length()>19)//过滤IPV6
		                	//	continue;
		                    
		                	if(n==0){
		                		n++;
		                		addressList = "(1)" + line.substring(index).trim();	                		
		                	}else{
			                	addressList = addressList + "  (" + (n+1) + ")" + line.substring(index).trim();
			                    n++;
		                	}
	                    }
	                }
	                br.close();
	                if(addressList=="")
	    	        	return "N/A";
	    	        else 
	    	        	return addressList;
	            }
	            catch (Exception e) {
	            	return "N/A";  
	            }
	        }else
	        	return "N/A";  
	}
	
	
	
	
//	public String getIpAddress() {  
//		String addressList = "";
//		int n = 0;
//		//String[] ret = null;  
//	    try {
//	    	InetAddress addr = InetAddress.getLocalHost();  
//	    	String hostName = addr.getHostName(); 
//	       
//	        if (hostName.length() > 0) {  
//	            InetAddress[] addrs = InetAddress.getAllByName(hostName);  
//	            if (addrs.length > 0) {
//	            	addressList = "(1)" + addrs[0].getHostAddress();
//	                //ret = new String[addrs.length];  
//	                for (int i = 1; i < addrs.length; i++) {  
//	                    //ret[i] = addrs[i].getHostAddress();
//	                	if(addrs[i].getHostAddress().length()>19)//过滤IPV6
//	                		continue;
//	                    
//	                	if(n==0){
//	                		n++;
//	                		addressList = "(1)" + addrs[0].getHostAddress();	                		
//	                	}else{
//		                	addressList = addressList + "  (" + (n+1) + ")" + addrs[i].getHostAddress();
//		                    n++;
//	                	}
//	                }  
//	            }  
//	        }
//	        
//	        if(addressList=="")
//	        	return "N/A";
//	        else 
//	        	return addressList;
//	  
//	     } catch (Exception ex) {  
//	           return "N/A";  
//	     }  
//	    // return ret;  
//	}  
	
		
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
