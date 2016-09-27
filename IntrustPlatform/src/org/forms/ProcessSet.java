package org.forms;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.dboperate.AlarmLogUpdate;
import org.dboperate.DBOperate;
import org.dboperate.MeasurementLogUpdate;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;

import com.sun.jna.ptr.IntByReference;


import ui.Downloader;
import ui.ErrorListBean;
import ui.LibMeasure;
import ui.PTM_connect;
import ui.ServerIPaddress;
import ui.TCCUtilit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.wsoperate.WhitelistOperate;


public class ProcessSet extends Composite {	
		
	//平台证明-白名单下载//
	Shell shell;	
	static Composite page;
	static String wl_FilterPath = "C:\\TCC\\wl";//"C:\\wl";//默认白名单路径
	
//	private ArrayList error_list = new ArrayList();
	static Text wl_text;	
	static Label dwl_state;
	private Downloader downloader=new Downloader();
	private TCCUtilit utilit = new TCCUtilit();
//	private TableViewer viewer ;
	private MessageBox mb;
//	private Table table;
//	private Group group3;
	private ProgressBar prgBar;
	public static Label mod_label;
	public static Button btn_set_wl;
	
//	private int rwoNo = 0;

	private DBOperate db;
	private String wlPath = "C:/TCC/wl";//"C:/wl";
    private File wlfile = new File(wlPath);
    private String clearsql = "delete from 'wl'";
    static final String insert ="insert into wl values ";
    public StatusMeasureLog measure;
    
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ProcessSet(Composite parent, int style) {
		super(parent, style);
		shell = new Shell();
		
		this.setBounds(0,110,900,500);
		this.setVisible(false);			
		
		GenUI(this);	
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void GenUI(Composite parentCom){
		mb= new MessageBox(shell, SWT.ICON_INFORMATION);
		
		page = new Composite (parentCom, SWT.NONE);
		page.setBounds(0, 0, 900, 531);
		
		
		measure = new StatusMeasureLog(page,SWT.NORMAL);	
		measure.setBounds(41, 0, 846, 341);
		measure.setVisible(true);
//		Methods.Alert();
		
		//Step 1---白名单下载
		final Group group1=new Group(page,SWT.NONE);
		group1.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		group1.setBounds(40, 347, 814, 76);
		group1.setText("白名单下载");
		prgBar = new ProgressBar(group1, SWT.NONE);
		prgBar.setLocation(192, 14);
		prgBar.setSize(598, 27);
		
		
		
		final Button start_button = new Button(group1, SWT.PUSH);		
		start_button.setBounds(44, 22, 93, 39);
		
		           
		start_button.setText("开始下载");
		
		start_button.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){

				int res = -1;
				PTM_connect.Load_pubkey();
				String ip = ServerIPaddress.getIp();
				int port = ServerIPaddress.getPort();
				try {
					if (ip.length() == 0) {
						res = 203;
					} else {

						if (!utilit.IPisCorrect(ip)) {
							res = 201;
						} else {

							try {
								
//								res = downloader.download_whitelist(ip, port,
//										dwl_state, prgBar, PTM_connect.pubkey,
//										wl_FilterPath);
								
								
								WhitelistOperate wlop = new WhitelistOperate();
								String wlcontent = wlop.downloadWl(-1);										    
								String temp[] = wlcontent.split("###");
								
								 FileWriter fileWriter = new FileWriter(wlfile);
						         BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
								
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
								
													
								String whitelist[] = temp[1].split(";;;");
								System.out.println(whitelist.length);
								for(int i=0; i<whitelist.length; i++) {
									//System.out.println(whitelist[i]);
									String tempstr[] = whitelist[i].split("&&&");
									//String sql = "("  + "'"+ tempstr[0] +"'" + "," + "'"+ tempstr[1] + "'" + "," + "'"+ tempstr[2] + "'" + ")";
								/*	System.out.println(i);
									System.out.println(tempstr[1]);
									System.out.println(tempstr[0]);*/
							
									bufferedWriter.write(tempstr[1]+" "+tempstr[0]);
									bufferedWriter.newLine();
						

									
							} 
								bufferedWriter.close();
					            fileWriter.close(); 
								res=0;
								
							/**

								
								ArrayList list = new ArrayList();

								File mdllfile = new File(wlPath);

								FileReader fr = null;

								BufferedReader br = null;
//								if (!mdllfile.exists()) {
//									try {
//										mdllfile.createNewFile();
//
//									} catch (IOException e1) {
//										// TODO Auto-generated catch block
//										e1.printStackTrace();
//									}
//								}
								try {
									fr = new FileReader(mdllfile);

									br = new BufferedReader(fr);

									String s = null;

									//int id = 1;

									String[] arr = null;

									while ((s = br.readLine()) != null) {

										// int p = s.indexOf(" ");
									
										list.add(s);
										Comparator comparator = new Comparator(){
											   public int compare(Object o1, Object o2) {
											    //首先比较名字，如果相同则比较时间
												  
											    
											   }
										};

									
									}

								

								} catch (FileNotFoundException e1) {

									System.out.println("文件未找到：" + mdllfile);

								

								} catch (IOException ioe) {

									System.out.println("文件读取错误：" + mdllfile);

							

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
									} catch (IOException e1) {

										e1.printStackTrace();
									}
								}
								 FileReader filereader = new FileReader(wlfile);
								 BufferedReader bufferedReader = new BufferedReader(filereader);
								
								  for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )   {
									    for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )   {
									      if  (list.get(j).equals(list.get(i)))   {
									        list.remove(j);
									      }
									    }
								  }
								  **/
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

				case 0:
					mb.setMessage("白名单下载成功");
					dwl_state.setText("白名单下载成功");
					dwl_state.update();
					//set_wl.setEnabled(true);
					break;

				case 1:
					mb.setMessage("服务端获取白名单失败");
					dwl_state.setText("服务端获取白名单失败");
					dwl_state.update();
					break;

				case 1001:
					mb.setMessage("白名单下载成功");
					dwl_state.setText("白名单下载成功");
					dwl_state.update();
					//set_wl.setEnabled(true);
					break;

				case 1000:
					mb.setMessage("接收白名单失败");
					dwl_state.setText("接受白名单失败");
					dwl_state.update();
					break;

				case 2:
					mb.setMessage("服务器接收客户端请求失败");
					dwl_state.setText("服务器接收客户端请求失败");
					dwl_state.update();
					break;

				case 3:
					mb.setMessage("服务器内部错误");
					dwl_state.setText("服务器内部错误");
					dwl_state.update();
					break;

				case 101:
					mb.setMessage("向服务器发送请求失败");
					dwl_state.setText("向服务器发送请求失败");
					dwl_state.update();
					break;

				case 102:
					mb.setMessage("获取度量文件失败");
					dwl_state.setText("获取度量文件失败");
					dwl_state.update();
					break;
				case 103:
					mb.setMessage("连接服务器失败");
					dwl_state.setText("连接服务器失败");
					dwl_state.update();
					break;
				case 104:
					mb.setMessage("获取服务端结果失败");
					dwl_state.setText("获取服务端结果失败");
					dwl_state.update();
					
					
					
					break;

				case 201:
					mb.setMessage("服务器地址不合法");
					dwl_state.setText("服务器地址不合法");
					dwl_state.update();
					break;

				case 202:
					mb.setMessage("服务器端口不合法");
					dwl_state.setText("服务器端口不合法");
					dwl_state.update();
					break;
				case 203:
					mb.setMessage("请输入服务器地址和端口");
					dwl_state.setText("请输入服务器地址和端口");
					dwl_state.update();
					break;
				default:
					mb.setMessage("未知错误" + res);
					dwl_state.setText("未知错误");
					dwl_state.update();

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
	
		dwl_state = new Label(group1, SWT.HORIZONTAL | SWT.CENTER| SWT.BORDER);
		dwl_state.setBounds(192, 47, 569, 23);
		
		dwl_state.setText("等待下载");
		
		//Step2---白名单模式
		final Group group2=new Group(page,SWT.NONE);
		group2.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		group2.setBounds(40, 429, 814, 69);
		group2.setText("白名单模式");
		
		
		btn_set_wl = new Button(group2, SWT.PUSH);		
		btn_set_wl.setBounds(45, 22, 91, 36);
		
		//gb_set_wl.horizontalSpan = 3;
		           
		btn_set_wl.setText("开 启");
		
		mod_label = new Label(group2, SWT.HORIZONTAL | SWT.CENTER| SWT.BORDER);
		mod_label.setBounds(192, 22, 597, 36);
		
		//gd_mod_label.horizontalSpan=3;
		
		//mod_label.setAlignment(SWT.CENTER);
		
		Color color1 = new Color(shell.getDisplay(), 255, 255, 159);
		mod_label.setBackground(color1); 
		Color color2 = new Color(shell.getDisplay(), 120, 42, 105);
		mod_label.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		FontData newFontData = mod_label.getFont().getFontData()[0];
	    newFontData.setStyle(SWT.BOLD);
	    //newFontData.
	    newFontData.setHeight(14);
	    Font newFont = new Font(shell.getDisplay(), newFontData);
	    mod_label.setFont(newFont);
		//mod_label.setFont(new Font(shell.getDisplay(),"宋体",15,SWT.NORMAL));
		LibMeasure lms=new LibMeasure();
	    if(lms.TM_getCurrentMode()==11){
	    	LibMeasure.whitemode=1;
	    	btn_set_wl.setEnabled(false);
			mod_label.setText("白名单模式");
		}else if(lms.TM_getCurrentMode()==10)
		{
			LibMeasure.whitemode=0;
			btn_set_wl.setEnabled(true);
			mod_label.setText("普通模式");
		}else
		{
			mod_label.setText("未知模式,Error Code:"+lms.TM_getCurrentMode());
		}
	    
	  //指定路径后，设置白名单
		btn_set_wl.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				
				Password pswd = new Password(shell, 0);
				pswd.open();
				if(!(pswd.isTrue)){
					return;					
				}
				
				LibMeasure lm=new LibMeasure();
				IntByReference count=new IntByReference();
				lm.TM_clearWhiteList();
				int ret=lm.TM_setWhiteList(wl_FilterPath, count);
				switch(ret){
				case 0:					
					if (addctrlnum()==false)
					{
						mb.setMessage("本功能试用结束，请获取授权后再使用!");
						mb.open();
						return;
					}
					
					LibMeasure.whitemode=1;
					mb.setMessage("开启白名单模式");
					mod_label.setText("设置白名单成功！");
					mod_label.update();	
					
					//Update.cmm_button.setEnabled(true);
					Methods.updateWidgetsMode();	//修改相关控件内容
					//addctrlnum();
					
					break;
				case 1:
					mb.setMessage("internal error of libmeasure!");
					break;
				case 101:
					mb.setMessage("error of calling libmeasure!");
					break;
				default: 
					mb.setMessage("未知错误"+ret);
				}
				ret=lm.TM_startWhiteMode();//开启白名单模式
				if(ret==0){
					mod_label.setText(mod_label.getText()+"  白名单模式已经开启！");
					mod_label.update();
					LibMeasure.whitemode=1;
					
//					thd_Refresh.resume();		//begin to refresh the table;
				}
				mb.open();
			}
		});
		
		//Step3---错误进程报警
		//
		
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
