package org.forms;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.axis2.AxisFault;

import org.dboperate.DBOperate;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.forms.XMLReader;
import org.wsoperate.KnowledgeServiceStub;
import org.wsoperate.MetricServiceStub;
import org.wsoperate.WSOperator;

import ui.PTM_connect;
import ui.UploadAlertProcess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
//重写SubUpload布局
public class SubUpload extends Dialog{

	protected Object result;
	protected Shell shell;
	private int x=0;
	private int y=0;
	public static int count=0;

    private Button btn_OK;
	private Button btn_Save;	

	private Label Label;
	private Label Title;
	private Label ConnectInfoLabel;
	private Text txt_Time;	
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SubUpload(Shell parent, int style) {
		super(parent, style);		
		
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
		shell.setSize(380, 240);
		shell.setText("报警日志上传设置");
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 380;
		int h = 240;
		shell.setBounds((int)(scrSize.width-w)/2,(int)(scrSize.height-h)/2,w, h);
		shell.setVisible(true);
		
		Group sgroup = new Group(shell,SWT.NONE);
		sgroup.setBounds(30, 50, 308, 130);
		
		Title = new Label(shell, SWT.NONE);
		Title.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		Title.setBounds(134, 10, 98, 34);
		Title.setText("报警日志");
		
		Label = new Label(sgroup, SWT.NONE);
		Label.setText("设置自动上传频率(秒)：");
		Label.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));
		Label.setBounds(34, 31, 181, 22);
		
		txt_Time=new Text(sgroup,SWT.BORDER|SWT.CENTER);//带边框
		txt_Time.setFont(SWTResourceManager.getFont("黑体", 9, SWT.NORMAL));
                txt_Time.setText(UploadAlertProcess.f);
		txt_Time.setBounds(216,29,56,24);
	    
		btn_OK = new Button(sgroup, SWT.FLAT);
		btn_OK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String str1 = txt_Time.getText();
						
						 UploadAlertProcess.f=str1;
						 XMLReader xmlreader = new XMLReader();
						 xmlreader.insertEle("uploadfrequence", str1);
						 synchronized(PTM_connect.p){
								PTM_connect.p.notify();
							}
			
						System.out.println("自动上传配置保存成功！");
						ConnectInfoLabel = new Label(shell, SWT.CENTER);
						ConnectInfoLabel.setText("自动上传配置保存成功！");
						ConnectInfoLabel.setBounds(162, 237, 150, 20);
						
			}
		});
		btn_OK.setText("保存");
		btn_OK.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		btn_OK.setBounds(118, 91, 70, 30);
		
		
		btn_Save = new Button(sgroup, SWT.FLAT);
		btn_Save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				synchronized(PTM_connect.p){
						PTM_connect.p.notify();
					}
			}
		});
		btn_Save.setText("手动上传");
		btn_Save.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		btn_Save.setBounds(22, 91, 70, 30);	
		
		Button btn_Close = new Button(sgroup, SWT.NONE);
		btn_Close.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
				count--;
			}
		});
		btn_Close.setBounds(215, 91, 70, 30);
		btn_Close.setText("退出");
	}
}

/*public class SubUpload extends javax.swing.JDialog {
	//private String filePath = "C:/WINDOWS/measurevalue";	//度量日志读取
	//private File mlfile = new File(filePath);	
	//private BufferedReader mlreader = null; 
	//static final String insert ="insert into measurevalue values ";
	//private static String rPath = "C:/WINDOWS/ClientData/configuration.txt";
	//private static BufferedReader reader = null;  
    //private static File rfile = new File(rPath);
	//private static String wPath = "C:/WINDOWS/ClientData/configuration.txt";
	private JLabel jConnectInfoLabel;
	//private static File wfile = new File(wPath);
	private String pubkeyPath = "C:/TCC/PubKey";//"C:/WINDOWS/PubKey";	        	         	
    private File pubkeyfile = new File(pubkeyPath);
    private BufferedReader pkreader = null;
    private String TCM_PK;
    
	private JButton jOK;
	private JButton jSave;	
	//private static String upload;
	//private static String upload1;
	//private static String upload2;
	//private static String uploadflag;
	private JPanel jPanel;
	private JLabel jLabel;
	private JLabel jLabel2;
	//private JTextField jTimeField2;
	private JTextField jTimeField1;	
	//private JCheckBox jCheckBox;
	
	private DBOperate db;
	
	*//**
	* Auto-generated main method to display this JDialog
	*//*
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SubUpload inst = new SubUpload();
				inst.setVisible(true);
			}
		});
	}
	
	
	public SubUpload() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		setVisible(false);
		setResizable(false);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(203, 232, 207));
		setTitle("报警日志");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		//读取配置内容	
		
		if(!rfile.exists()) {
			rfile.createNewFile();
			uploadflag = "false";
			upload1 = "00"; 
			upload2 = "00"; 
		}
		else {
			reader = new BufferedReader(new FileReader(rfile));			
			uploadflag = reader.readLine();
			upload = reader.readLine();
			if((uploadflag == null) || uploadflag.equals("")) {
				uploadflag = "false";
			}
			if((upload != null) && !upload.equals("")) {
				String uploadtmp[] = upload.split(";;;");		
				if(0<uploadtmp.length) upload1 = uploadtmp[0];
				if(1<uploadtmp.length) upload2 = uploadtmp[1];
			}
			else {
				upload1 = "00"; 
				upload2 = "00"; 
			}
			reader.close();
		}
		
		
		{
		   	jOK = new JButton("手动上传");
			getContentPane().add(jOK);
			jOK.setBounds(89, 136, 93, 25);
			jOK.setFocusable(false);	
			jOK.setFont(new java.awt.Font("宋体",0,12));
			jOK.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					synchronized(PTM_connect.p){
						PTM_connect.p.notify();
					}
					
					
					db = org.forms.MainForm.db;
					//测试服务器连接
					WSOperator ws = new WSOperator();
					if(ws.getWSStatus() == true) {	
						jConnectInfoLabel.setText("服务器连接成功");
					}
					else {
						jConnectInfoLabel.setText("服务器连接失败");
						return;
					}
					
					try {
						pkreader = new BufferedReader(new FileReader(pubkeyfile));
						TCM_PK = pkreader.readLine();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
											
					String client_metrictime = "";
					String span_metrictime1 = "";
					String span_metrictime2 = "";
					//get the latest time in measurement database
					try {
						ResultSet rs = db.selecttable("select max(time) from measurevalue", 9);
						if(rs.next()) {
							client_metrictime = rs.getString("max(time)");
						}
						rs.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//System.out.println(client_metrictime);	
											
					
					//get the span of time to e uploaded												
					XMLReader xmlreader = new XMLReader();
				    String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("metricop");
				    MetricServiceStub metricstub = null;
					try {
						metricstub = new MetricServiceStub(null, trans_value);
					} catch (AxisFault e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
				    MetricServiceStub.QueryMetricTime mtfunction = new MetricServiceStub.QueryMetricTime();	
				    mtfunction.setIn0(TCM_PK);
				    mtfunction.setIn1(client_metrictime);
				    String mtresult = null;
					try {
						mtresult = metricstub.queryMetricTime(mtfunction).getOut();
					} catch (RemoteException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
				    
				    //upload the measurevalue
					String measurelist = "";
				    if(mtresult.equals("all")) {
				    	try {
							ResultSet rs = db.selecttable("select * from measurevalue where time<='" +client_metrictime+ "'", 9);
							while(rs.next()) {
								measurelist = measurelist + "null" + "&&&" + rs.getString("processname") + "&&&" 
								            + rs.getString("path") + "&&&" + rs.getString("hashvalue") + "&&&"
								            + rs.getString("fathername") + "&&&" + rs.getString("fatherid") + "&&&"
								            + rs.getString("time") + ";;;";
							}
							rs.close();
							//System.out.println(measurelist);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
				    	
				    	if(measurelist.equals("")) {
				    		System.out.println("数据库中没有可上传数据");
				    		jConnectInfoLabel.setText("数据库中没有可上传数据");
				    		return;
				    	}
				    	
				    	MetricServiceStub.UploadMetricLog upmfunction = new MetricServiceStub.UploadMetricLog();
					    upmfunction.setIn0(TCM_PK);
					    upmfunction.setIn1(measurelist);
						try {
							boolean upmres = metricstub.uploadMetricLog(upmfunction).getOut();
							if(upmres == false) {
								System.out.println("度量日志上传错误!");
								jConnectInfoLabel.setText("度量日志上传错误!");
								return;
						    	//JOptionPane.showMessageDialog(null, "度量日志上传错误!");
						    }
							else {
								//clear measurement database
								try {
									db.cleartable("delete from 'measurevalue'");
									mlreader = new BufferedReader(new FileReader(mlfile));
									String tmpString = mlreader.readLine();       	
									while((tmpString  != null) && !tmpString.equals("")) { 
										//从度量日志中读取数据      
										//序号，Hash值，路径和进程名，父进程名，Linux系统，父进程id，度量时间
										String mldata[] = null; //保存内容
										mldata = tmpString.split(" "); 
										String processpn[] = mldata[2].split("/");
										
										//向度量日志数据库中写数据
										String sql = "("  + "'"+ processpn[processpn.length-1] +"'" + "," + "'"+ mldata[2] +"'" + "," + "'"+ mldata[1] +"'" + "," + "'"+ mldata[3] +"'" +  "," + "'"+ mldata[5] +"'" +  "," + "'"+ mldata[6] +"'" + ")";  	        	   		
										db.inserttable(insert + sql, 1);
										tmpString = mlreader.readLine();					
									}        	   		      	   			      	   		
									mlreader.close();
									db.commit();
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							 }
						} catch (RemoteException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
				    }
				    else {
				    	String temp[] = mtresult.split(";;;");
						span_metrictime1 = temp[0];
						span_metrictime2 = temp[1];
						if(!span_metrictime1.equals(span_metrictime2)) {
							try {
								ResultSet rs = db.selecttable("select * from measurevalue where time>'" +span_metrictime1+ "' and time<='" +span_metrictime2+ "'", 9);
								while(rs.next()) {
									measurelist = measurelist + "null" + "&&&" + rs.getString("processname") + "&&&" 
									            + rs.getString("path") + "&&&" + rs.getString("hashvalue") + "&&&"
									            + rs.getString("fathername") + "&&&" + rs.getString("fatherid") + "&&&"
									            + rs.getString("time") + ";;;";
								}
								rs.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							if(measurelist.equals("")) {
					    		System.out.println("数据库中没有可上传数据");
					    		jConnectInfoLabel.setText("数据库中没有可上传数据");
					    		return;
					    	}
							
							MetricServiceStub.UploadMetricLog upmfunction = new MetricServiceStub.UploadMetricLog();
						    upmfunction.setIn0(TCM_PK);
						    upmfunction.setIn1(measurelist);
							try {
								boolean upmres = metricstub.uploadMetricLog(upmfunction).getOut();
								if(upmres == false) {
									System.out.println("度量日志上传错误!");
									jConnectInfoLabel.setText("度量日志上传错误!");
									return;
							    	//JOptionPane.showMessageDialog(null, "度量日志上传错误!");
							    }
								else {
									//clear measurement database
									try {
										db.cleartable("delete from 'measurevalue'");
										mlreader = new BufferedReader(new FileReader(mlfile));
										String tmpString = mlreader.readLine();       	
										while((tmpString  != null) && !tmpString.equals("")) { 
											//从度量日志中读取数据      
											//序号，Hash值，路径和进程名，父进程名，Linux系统，父进程id，度量时间
											String mldata[] = null; //保存内容
											mldata = tmpString.split(" "); 
											String processpn[] = mldata[2].split("/");
											
											//向度量日志数据库中写数据
											String sql = "("  + "'"+ processpn[processpn.length-1] +"'" + "," + "'"+ mldata[2] +"'" + "," + "'"+ mldata[1] +"'" + "," + "'"+ mldata[3] +"'" +  "," + "'"+ mldata[5] +"'" +  "," + "'"+ mldata[6] +"'" + ")";  	        	   		
											db.inserttable(insert + sql, 1);
											tmpString = mlreader.readLine();					
										}        	   		      	   			      	   		
										mlreader.close();
										db.commit();
										} catch (Exception e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
								    }
							} catch (RemoteException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}								
						}
						else {
							System.out.println("度量日志无需上传！");
						}
						
				    }
				    System.out.println("度量日志上传成功!");
				    jConnectInfoLabel.setText("度量日志上传成功!");
				    //JOptionPane.showMessageDialog(null, "度量日志上传成功!");
				     
				}
				
				
			});
		}
		
		{		
			if(uploadflag.equals("true")) jCheckBox = new JCheckBox("自动更新", true);
			else jCheckBox = new JCheckBox("自动更新");			
			getContentPane().add(jCheckBox);
			jCheckBox.setBounds(15, 15, 150, 35);
			jCheckBox.setBackground(Color.WHITE);
			jCheckBox.setFont(new java.awt.Font("宋体",0,12));
			jCheckBox.setFocusable(false);				
			jCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					if(jCheckBox.isSelected()) {
						uploadflag = "true";
						jTimeField1.setEditable(true);
						jTimeField2.setEditable(true);
					}
					else {
						uploadflag = "false";
						jTimeField1.setEditable(false);
						jTimeField2.setEditable(false);
					}
				}	
			});
		}
		{
			jPanel = new JPanel();
			getContentPane().add(jPanel);
			jPanel.setBounds(14, 32, 360, 60);
			jPanel.setLayout(null);
			jPanel.setBackground(new Color(203, 232, 207));
			jPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			{
				jLabel = new JLabel("设置自动上传频率(秒)：");
				jPanel.add(jLabel);
				jLabel.setBounds(77, 20, 140, 20);
				jLabel.setFont(new java.awt.Font("宋体",0,12));
			}
			{
				jTimeField1 = new JTextField();
				jPanel.add(jTimeField1);
				jTimeField1.setBounds(218, 20, 40, 20);
				//if(upload1==null || upload1.equals("")) jTimeField1.setText("00");
				//else jTimeField1.setText(upload1);
				//String fre=UploadAlertProcess.f;
				jTimeField1.setText(UploadAlertProcess.f);
				//if(uploadflag.equals("false")) jTimeField1.setEditable(false);
				jTimeField1.setFont(new java.awt.Font("Calibri",0,14));
			}
			
			
			{
				jLabel2 = new JLabel();
				jPanel.add(jLabel2);
				jLabel2.setText("秒");
				jLabel2.setBounds(185, 20, 5, 20);
				jLabel2.setFont(new java.awt.Font("宋体",0,12));
			}
			
			{
				jTimeField2 = new JTextField();
				jPanel.add(jTimeField2);
				jTimeField2.setBounds(195, 20, 40, 20);
				jTimeField2.setFont(new java.awt.Font("Calibri",0,14));
				if(upload2==null || upload2.equals("")) jTimeField2.setText("00");
				else jTimeField2.setText(upload2);
				if(uploadflag.equals("false")) jTimeField2.setEditable(false);
			}
			{
				jSave = new JButton("保存");
				getContentPane().add(jSave);
				jSave.setBounds(216, 136, 81, 25);
				jSave.setFont(new java.awt.Font("宋体",0,12));
				jSave.setFocusable(false);
				ActionListener savebuttonAL = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String str1 = jTimeField1.getText();
						
						 UploadAlertProcess.f=str1;
						 XMLReader xmlreader = new XMLReader();
						 xmlreader.insertEle("uploadfrequence", str1);
						 synchronized(PTM_connect.p){
								PTM_connect.p.notify();
							}
						
						String str2 = jTimeField2.getText();				
						if(str1.equals("") || str2.equals("")) {
							System.out.println("自动上传时间输入有误，请重新输入！");
							jConnectInfoLabel.setText("自动上传时间输入有误，请重新输入！");
							//JOptionPane.showMessageDialog(null, "err1:自动上传时间输入有误，请重新输入！");
							return;
						}
						else {
							if(!str1.matches("[0-2][0-9]") || !str2.matches("[0-6][0-9]")) {
								System.out.println("自动上传时间输入有误，请重新输入！");
								jConnectInfoLabel.setText("自动上传时间输入有误，请重新输入！");
								//JOptionPane.showMessageDialog(null, "err2:自动上传时间输入有误，请重新输入！");
								return;
							}
							else {
								if(Integer.parseInt(str1)<0 || Integer.parseInt(str1)>23 || Integer.parseInt(str2)<0 || Integer.parseInt(str2)>59) {
									System.out.println("自动上传时间输入有误，请重新输入！");
									jConnectInfoLabel.setText("自动上传时间输入有误，请重新输入！");
									//JOptionPane.showMessageDialog(null, "err3:自动上传时间输入有误，请重新输入！");
									return;
								}							
							}
						}
						try {
							PrintWriter bfWriter = new PrintWriter(new FileWriter(wfile));
							bfWriter.println(uploadflag);
							bfWriter.println(str1+";;;"+str2);
							bfWriter.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						//判断是否自动上传
						   
						if(uploadflag.equals("true")) {
							UploadJobOperate.removeJob("uploadjob");
							ActiveUploadJob uploadjob = new ActiveUploadJob();
							//UploadJobOperate.addJob("uploadjob", "org.forms.ActiveUploadJob", "0 "+str2+" "+str1+" * * ?");								
						}
						else {
							UploadJobOperate.removeJob("uploadjob");
						}
						
						
						System.out.println("自动上传配置保存成功！");
						jConnectInfoLabel.setText("自动上传配置保存成功！");
						//JOptionPane.showMessageDialog(null, "自动上传配置保存成功！");
					}
				};
				jSave.addActionListener(savebuttonAL);
			}
			{
				jConnectInfoLabel = new JLabel();
				getContentPane().add(jConnectInfoLabel);
				jConnectInfoLabel.setBounds(123, 176, 145, 15);
			}
		this.setSize(400, 230);
}
}}
*/