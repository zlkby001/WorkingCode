package org.forms;

//测试提示信息：服务器连接情况，上传白名单操作的返回值
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import org.dboperate.MeasurementLogUpdate;
import org.wsoperate.WSOperator;
import org.wsoperate.WhitelistOperate;
import org.wsoperate.WhitelistServiceStub;

import org.dboperate.DBOperate;

public class Status extends Composite {	
	
	public static Button btn_WhiteList ;	
//	StatusMeasureLog cs1 = new StatusMeasureLog(this,0);		
	StatusAlertLog cs2 = new StatusAlertLog(this,0);
	StatusActiveProcess cs3 = new StatusActiveProcess(this,0);
	StatusWhiteList cs4 = new StatusWhiteList(this,0);

	private DBOperate db;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Status(Composite parent, int style) {
		super(parent, style);

		this.setBounds(0,110,900,500);
		this.setVisible(false);	
		
		Label lbl_Separator = new Label(this, SWT.SEPARATOR);
		lbl_Separator.setBounds(124, 10, 8, 469);
		lbl_Separator.setText("");
		
		Composite this_Left = new Composite(this, SWT.NONE);
		this_Left.setBounds(0, 0, 126, 462);
		
		Button btn_MeasureLog = new Button(this_Left, SWT.NONE);
		btn_MeasureLog.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {		
				//将度量日志增量式写入数据库 以及 通过新增度量日志的hash值向服务器查询知识库内容
				MeasurementLogUpdate measurementlogUpdate  = new MeasurementLogUpdate(org.forms.MainForm.db);
				measurementlogUpdate.update();
				
//				cs1.setVisible(true);
				cs2.setVisible(false);
				cs3.setVisible(false);
				cs4.setVisible(false);				
//				cs1.thd_Refresh.resume();	//唤醒				
				
			}
		});
		btn_MeasureLog.setFont(SWTResourceManager.getFont("黑体", 13, SWT.NORMAL));
		btn_MeasureLog.setBounds(10, 356, 106, 60);
		btn_MeasureLog.setText("进程度量");
		btn_MeasureLog.setVisible(false);
		
		Button btn_ActiveProcess = new Button(this_Left, SWT.NONE);
		btn_ActiveProcess.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				cs1.setVisible(false);
				cs2.setVisible(false);
				cs3.setVisible(true);
				cs4.setVisible(false);
//				cs1.thd_Refresh.suspend();	//阻塞							
			}
		});
		btn_ActiveProcess.setText("活动进程");
		btn_ActiveProcess.setFont(SWTResourceManager.getFont("黑体", 13, SWT.NORMAL));
		btn_ActiveProcess.setBounds(10, 35, 106, 60);
		
		btn_WhiteList = new Button(this_Left, SWT.NONE);
		btn_WhiteList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {					
				//测试服务器是否连接
				/*
				WSOperator ws = new WSOperator();
				String wlversionres = null;
				if(ws.getWSStatus() == true) {	//服务器连接成功
					System.out.println("服务器连接成功");
					//读取本地白名单版本号，并判断是否为-1，若为-1，则自动上传白名单到服务器并修改本地版本号
					db = org.forms.MainForm.db;
					//从服务器获取白名单版本信息
					WhitelistOperate wlop = new WhitelistOperate();
				    wlversionres = wlop.getWlVersion();
					
				    String whitelistcontent = "";
					int wlversion;					
					try {
						ResultSet rs = db.selecttable("select * from wlversion ", 4);
						if(rs.next()) {
							if(rs.getString("version").equals("-1")) {
								if(wlversionres.equals("non_existent")) {
									wlversion = 1;
								}
								else {
									String res[] = wlversionres.split("&&&");
									wlversion = Integer.parseInt(res[0])+1;
								}	
								ResultSet wlrs = db.selecttable("select * from wl", 4);
								while(wlrs.next()) {
									whitelistcontent = whitelistcontent+wlrs.getString("processname")+"&&&"+wlrs.getString("path")+"&&&"+wlrs.getString("hashvalue")+";;;";				
								}								
								
								if(whitelistcontent.equals("")) {
									System.out.println("数据库没有需要上传的白名单");
								}
					        	boolean flag = wlop.uploadWl(wlversion, whitelistcontent);
					        	System.out.println("上传白名单操作的返回值："+flag);
								if(flag == true) {
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
									db.commit();
								}
							}
						}
						rs.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
					//重新从服务器获取白名单版本信息
				    wlversionres = wlop.getWlVersion();
				}
				else {	//服务器连接失败
					//do nothing...
					System.out.println("服务器连接失败");
				}
				*/
//				cs1.setVisible(false);
				cs2.setVisible(false);
				cs3.setVisible(false);
				cs4.setVisible(true);
				cs4.refresh();
//				cs1.thd_Refresh.suspend();	//阻塞
				//将白名单的版本信息传给白名单显示界面
				//cs4.setwlversion(wlversionres);
				
			}
		});
		btn_WhiteList.setText("白名单");
		btn_WhiteList.setFont(SWTResourceManager.getFont("黑体", 13, SWT.NORMAL));
		btn_WhiteList.setBounds(10, 229, 106, 60);
		
		Button btn_Alert = new Button(this_Left, SWT.NONE);
		btn_Alert.setFont(SWTResourceManager.getFont("黑体", 13, SWT.NORMAL));
		btn_Alert.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
//				cs1.setVisible(false);
				cs2.setVisible(true);
				cs2.getList();
				cs2.newList();
				cs3.setVisible(false);
				cs4.setVisible(false);
			}
		});
		btn_Alert.setBounds(10, 128, 106, 60);
		btn_Alert.setText("报警日志");
		
		if(UserInfo.userType.equals("")){
			btn_WhiteList.setEnabled(false);
		}		

		cs2.setVisible(false);
		cs3.setVisible(true);
		cs4.setVisible(false);
		
	}
	
	/**
	 * 开启度量日志刷新
	 */
	public void startRefresh(){
//		cs1.thd_Refresh.resume();
	}
	
	/**
	 * 停止度量日志和活动进程的刷新
	 */
	public void stopRefresh(){
//		cs1.thd_Refresh.suspend();
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
