package org.forms;
import com.cloudgarden.resource.SWTResourceManager;

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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;

import ui.LibMeasure;


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
public class JUpdateComposite extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	
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
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
		
	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	
	public JUpdateComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			setVisible(false);
			setBounds(0, 110, 900, 500);
			{
				jCloseLabel = new Label(this, SWT.NONE);
				jCloseLabel.setText("关闭监控的图标");
				jCloseLabel.setBounds(100, 120, 120, 120);
				jCloseLabel.setFont(SWTResourceManager.getFont("楷体_GB2312", 22, 1, false, false));
			}
			{
				jCloseButton = new Button(this, SWT.NONE);
				jCloseButton.setText("关闭监控");
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
							lms.TM_startNormalMode();
						}
						LibMeasure.whitemode = 0;
						Methods.updateWidgetsMode();	//修改相关控件内容
					}
				});
			}
			{
				jInstallLabel = new Label(this, SWT.NONE);
				jInstallLabel.setText("安装工控软件的图标");
				jInstallLabel.setBounds(365, 95, 165, 165);
				jInstallLabel.setFont(SWTResourceManager.getFont("楷体_GB2312", 22, 1, false, false));				
			}
			{
				jInstallButton = new Button(this, SWT.NONE);
				jInstallButton.setText("安装工控软件");
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
				jWhitePaperLabel = new Label(this, SWT.NONE);
				jWhitePaperLabel.setText("生成白名单的图标");
				jWhitePaperLabel.setBounds(655, 105, 165, 165);
				jWhitePaperLabel.setFont(SWTResourceManager.getFont("楷体_GB2312", 22, 1, false, false));				
			}
			{
				jWhitePaperButton = new Button(this, SWT.NONE);
				jWhitePaperButton.setText("生成白名单");
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
						int prores = lms.TM_getCurrentProcess();
						System.out.println("获取当前进程文件的返回值"+prores);
						while(prores!=0) {
							prores = lms.TM_getCurrentProcess();
							System.out.println("获取当前进程文件的返回值"+prores);
						}
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
