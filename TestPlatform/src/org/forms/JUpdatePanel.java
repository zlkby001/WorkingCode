package org.forms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.dboperate.DBOperate;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ui.LibMeasure;

public class JUpdatePanel extends Composite {
	private Label jCloseLabel;
	public Button jCloseButton;
	private Label jInstallLabel;
	public Button jInstallButton;
	private Label jWhitePaperLabel;
	public Button jWhitePaperButton;
	private Label jArrowLabel1;
	private Label jArrowLabel2;
	private SubGenerateWP subinst;

	private DBOperate db;
	private String activePath = "C:/TCC/processtemp.log";//"C:/WINDOWS/processtemp.log";	
	private String activePath0 = "C:/TCC/process.log";//"C:/WINDOWS/process.log";
	private String wlPath = "C:/TCC/whitelist.txt";//"C:/whitelist.txt";
	private File file = new File(activePath);
    private File file0 = new File(activePath0);
    private File wlfile = new File(wlPath);
    private BufferedReader reader = null;  
	private BufferedWriter writer = null;
	
	static final String insert ="insert into wl values ";
	private String clearsql = "delete from 'activetemp'";
	

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	 * @return 
	*/

	public JUpdatePanel(Composite parent, int style) {
		super(parent, style);
		
		setVisible(false);
		setBounds(0,110,900,500);
		
		{
			jCloseLabel = new Label(this, SWT.SEPARATOR);
			jCloseLabel.setText("关闭监控");
			jCloseLabel.setBounds(100, 120, 120, 120);
		}
		{
			jCloseButton = new Button(this, SWT.NONE);
			jCloseButton.setBounds(55, 300, 210, 40);
			
			//判断保护模式是否开启
			LibMeasure lms = new LibMeasure();
			if (lms.TM_getCurrentMode() == 11) {
				jCloseButton.setEnabled(true);
			} else if (lms.TM_getCurrentMode() == 10) {
				jCloseButton.setEnabled(false);
			}			

			jCloseButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {		
					// TODO Auto-generated method stub
					jCloseButton.setEnabled(false);
					jInstallButton.setEnabled(true);
					jWhitePaperButton.setEnabled(false);
					
					LibMeasure lms = new LibMeasure();
					lms.TM_startNormalMode();
					while(lms.TM_getCurrentMode() != 10) {
						lms.TM_getCurrentMode();
					}
				}
			});
		}
		{
			jInstallLabel = new Label(this, SWT.SEPARATOR);
			jInstallLabel.setText("安装工控软件");
			jInstallLabel.setBounds(365, 95, 165, 165);
		}
		{
			jInstallButton = new Button(this, SWT.NONE);
			jInstallButton.setBounds(345, 300, 210, 40);
			
			//判断保护模式是否开启
			LibMeasure lms = new LibMeasure();
			if (lms.TM_getCurrentMode() == 11) {
				jInstallButton.setEnabled(false);
			} else if (lms.TM_getCurrentMode() == 10) {
				jInstallButton.setEnabled(true);
			}
				
			jInstallButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {		
					// TODO Auto-generated method stub
					jCloseButton.setEnabled(false);
					jInstallButton.setEnabled(false);
					jWhitePaperButton.setEnabled(true);
				}
			});
		}
		{
			jWhitePaperLabel = new Label(this, SWT.SEPARATOR);
			jWhitePaperLabel.setBounds(655, 105, 165, 165);
		}
		{
			jWhitePaperButton = new Button(this, SWT.NONE);
			jWhitePaperButton.setBounds(625, 300, 210, 40);
			jWhitePaperButton.setEnabled(false);
			
			jWhitePaperButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {		
					// TODO Auto-generated method stub
					subinst = new SubGenerateWP();
					subinst.setLocationRelativeTo(null);	
					subinst.setVisible(true);
					
					db = org.forms.MainForm.db;
					
					try {
						db.cleartable(clearsql);
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					//从TCM平台获取当前进程文件
					LibMeasure lms = new LibMeasure();
					lms.TM_getCurrentProcess();
					try {
						reader = new BufferedReader(new FileReader(file0));
						String tmpString = reader.readLine();					
						while((tmpString  != null) && !tmpString.equals("")) {
							String wldata[] = null;		        		
							wldata = tmpString.split(" ");		        	   		
							db.inserttable("insert into activetemp values " + "(" + "'"+ wldata[1] + "'" + "," + "'"+ wldata[2] + "'" + ")", 4);
							tmpString = reader.readLine();
						}			        	
						reader.close();
						db.commit();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					ResultSet result;			        	
					FileWriter fw;
					PrintWriter bfWriter = null;
					try {
						fw = new FileWriter(file);
						fw.write("");
						fw.close();						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
						bfWriter=new PrintWriter(new FileWriter(file));
						result = db.selecttable("select distinct path,hashvalue from activetemp", 4);
						while(result.next()) {
							bfWriter.println("00 " + result.getString("path") + " " + result.getString("hashvalue"));
						}
						result.close();
						bfWriter.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
																				
					String res[] = {"全选", "进程名称", "版本号", "描述信息", "厂商", "发布日期", "是否工控软件", "可信度", "hash值", "进程路径", "知识库推荐"};					
					WLTableFrame jTableFrame = new WLTableFrame(res);	
					subinst.dispose();
					jTableFrame.setVisible(true);
				}
			});
		}
	}
}
