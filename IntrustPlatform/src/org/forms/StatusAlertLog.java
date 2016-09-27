package org.forms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.dboperate.AlarmLogUpdate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.sun.jna.ptr.IntByReference;

import ui.Downloader;
import ui.ErrorListBean;
import ui.LibMeasure;
import ui.Loader;
import ui.PTM_connect;
import ui.ServerIPaddress;
import ui.TCCUtilit;
import ui.Verifier;

public class StatusAlertLog extends Composite {

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	private Loader loader = new Loader();
	private Verifier verifier = new Verifier();
	private CheckboxTableViewer viewer;
	private Table table;
	private Group group3;
	private Label label_Tips;
	private MessageBox mb;
	private ArrayList error_list = new ArrayList();
	private ArrayList<ErrorListBean> upload_list = new ArrayList<ErrorListBean>();
	static String el_FilterPath = "C:\\TCC\\errorlist.log";//"C:\\errorlist.log";// 默认错误列表路径
	public static int total = 0;// 报警日志条数
	//public TimeRefresh thd_Refresh = new TimeRefresh();
	private Shell shell;

	// 平台证明-白名单下载//
	static Composite page;
	static String wl_FilterPath = "C:\\TCC\\wl";//"C:\\wl";// 默认白名单路径

	static Label dwl_state;
	private Downloader downloader = new Downloader();
	private TCCUtilit utilit = new TCCUtilit();
	public static Label mod_label;

	private String wlPath = "C:/TCC/wl";//"C:/wl";
	private File wlfile = new File(wlPath);
	private String clearsql = "delete from 'wl'";
	static final String insert = "insert into wl values ";
	
	public static void resetTotal()
	{
		total = 0;
	}

	public StatusAlertLog(Composite parent, int style) {
		super(parent, SWT.NONE);
		shell = new Shell();

		this.setBounds(132, 0, 950, 550);
		this.setVisible(true);

		GenGUI(this);

		//viewer = new TableViewer(group3, SWT.CHECK | SWT.BORDER | SWT.FULL_SELECTION);
		viewer = CheckboxTableViewer.newCheckList(group3, SWT.BORDER | SWT.FULL_SELECTION);
		//viewer = new CheckboxTableViewer(group3, SWT.BORDER | SWT.FULL_SELECTION);
		table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(10, 48, 723, 361);	

		TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		TableColumn tableColumn = tableViewerColumn.getColumn();
		tableColumn.setWidth(60);
		tableColumn.setText("序号");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(viewer,
				SWT.NONE);
		TableColumn tableColumn_2 = tableViewerColumn_2.getColumn();
		tableColumn_2.setWidth(280);
		tableColumn_2.setText("进程");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(viewer,
				SWT.NONE);
		TableColumn tableColumn_1 = tableViewerColumn_1.getColumn();
		tableColumn_1.setWidth(150);
		tableColumn_1.setText("时间");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(viewer,
				SWT.NONE);
		TableColumn tableColumn_3 = tableViewerColumn_3.getColumn();
		tableColumn_3.setWidth(80);
		tableColumn_3.setText("类型");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(viewer,
				SWT.NONE);
		TableColumn tableColumn_4 = tableViewerColumn_4.getColumn();
		tableColumn_4.setWidth(250);
		tableColumn_4.setText("描述信息");

		label_Tips = new Label(group3, SWT.NONE);
		label_Tips.setBounds(40, 20, 317, 22);
		label_Tips.setText("报警日志条数： " + table.getItemCount() + "条");

		// 设置内容器
		viewer.setContentProvider(new ContentProvider());
		// 设置标签器
		viewer.setLabelProvider(new TableLabelProvider());
		// 把数据集合给tableView
		viewer.setInput(error_list);

		refreshData();

	//	thd_Refresh.start();
		//thd_Refresh.suspend();

	}

	public void GenGUI(Composite parentCom) {
		mb = new MessageBox(shell, SWT.ICON_INFORMATION);

		page = new Composite(parentCom, SWT.NONE);
		page.setBounds(0, 0, 795, 566);

		group3 = new Group(page, SWT.NONE);
		group3.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		group3.setBounds(10, 26, 808, 550);
		group3.setText("错误进程报警");

		Button btn_ClearAlert = new Button(group3, SWT.NONE);
		btn_ClearAlert.setBounds(385, 10, 112, 32);
		btn_ClearAlert.setText("清空报警日志");
		btn_ClearAlert.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (MessageDialog.openConfirm(shell, "确认", "您确定要清空报警日志吗？")) {
					try {
						FileOutputStream file = new FileOutputStream(
								el_FilterPath);
						file.write(new String("").getBytes());
						file.close();
						refreshData();
						table.removeAll();
						viewer.refresh();
						total=0;//add
						//label_Tips.setText("报警日志条数： 0条");
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						Methods.Alert("找不到报警日志文件，清空失败！");
					} catch (IOException e1) {
						e1.printStackTrace();
						Methods.Alert("写入文件失败！");
					}
				}
			}
		});

		final Button obtain_el = new Button(group3, SWT.PUSH);
		obtain_el.setBounds(503, 10, 112, 32);
		obtain_el.setText("获取报警日志");

		// 点击获取按钮后的动作
		obtain_el.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				getList();
				//newList();
		}});
		
		//add by yangbo
		final Button verify_selected = new Button(group3, SWT.PUSH);
		verify_selected.setBounds(621, 10, 112, 32);
		verify_selected.setText("验证选中项");

		// 点击获取按钮后的动作
		verify_selected.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				upload_list.clear();
				Object[] objects = viewer.getCheckedElements();
				
				for(int i=0; i<objects.length; i++){
					upload_list.add((ErrorListBean)objects[i]);
				}
				
				int res = 0;
				String server_ip = ServerIPaddress.getIp();
				int server_port = ServerIPaddress.getPort();
				
					if (server_ip.equals("")||server_port==0)	//ip地址或者端口号为空
						res = 201;
					else {
						

						System.out.println(server_ip);
						System.out.println(server_port);

						label_Tips.setText("服务器IP地址为：" + server_ip);
						label_Tips.update();

						label_Tips.setText("服务器端口为：" + server_port);
						label_Tips.update();
						// state_list.add("服务器IP地址为："+server_ip_text.getText());
						// state_list.add("\n服务器端口为："+server_port_text.getText());
						// state_list.dispose();

						if (server_ip.length() == 0) {
							res = 203;
						} else {

							if (!utilit.IPisCorrect(server_ip)) {
								res = 201;
							} else {

								try {
									PTM_connect.Load_pubkey();
									String swcom = "alertlog";
									System.out
											.println("the selected software component:"
													+ swcom);
									
									res = verifier.verifiy(server_ip,server_port,
											label_Tips,new ProgressBar(shell,SWT.NONE),swcom,
											PTM_connect.pubkey,upload_list);
									// verifier.verifiy(server_ip_text.getText(),
									// Integer.parseInt(server_port_text.getText()),
									// state_label);

								} catch (NumberFormatException nfe) {
									res = 202;
								}
							}

						}

					}

				MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION);

				mb.setText("执行结果");

				System.out.println(res);
				switch (res) {
				case 0:
					mb.setMessage("上传选中项完成");
					label_Tips.setText("上传选中项完成");
					label_Tips.update();
					break;

				case 1:
					mb.setMessage("上传选中项完成");
					label_Tips.setText("上传选中项完成");
					label_Tips.update();
					break;
				case 1000:
					mb.setMessage("上传选中项完成");
					label_Tips.setText("上传选中项完成");
					label_Tips.update();
					break;
				case 1001:
					mb.setMessage("上传选中项完成");
					label_Tips.setText("上传选中项完成");
					label_Tips.update();
					break;

				case 2:
					mb.setMessage("上传选中项失败");
					label_Tips.setText("上传选中项失败");
					label_Tips.update();
					break;

				case 3:
					mb.setMessage("上传选中项失败");
					label_Tips.setText("上传选中项失败");
					label_Tips.update();
					break;

				case 101:
					mb.setMessage("上传选中项失败");
					label_Tips.setText("上传选中项失败");
					label_Tips.update();
					break;

				case 102:
					mb.setMessage("上传选中项失败");
					label_Tips.setText("上传选中项失败");
					label_Tips.update();
					break;
				case 103:
					mb.setMessage("连接服务器失败");
					label_Tips.setText("连接服务器失败");
					label_Tips.update();
					break;
				case 104:
					mb.setMessage("获取验证结果失败");
					label_Tips.setText("获取验证结果失败");
					label_Tips.update();
					break;

				case 201:
					mb.setMessage("服务器地址不合法");
					label_Tips.setText("服务器地址不合法");
					label_Tips.update();
					break;

				case 202:
					mb.setMessage("服务器端口不合法");
					label_Tips.setText("服务器端口不合法");
					label_Tips.update();
					break;
				case 203:
					mb.setMessage("请输入服务器地址和端口");
					label_Tips.setText("请输入服务器地址和端口");
					label_Tips.update();
					break;
				case 10:
					mb.setMessage("接收身份验证结果失败");
					label_Tips.setText("接收身份验证结果失败");
					label_Tips.update();
					break;
				case 9:
					mb.setMessage("身份验证失败");
					label_Tips.setText("身份验证失败");
					label_Tips.update();
					break;
				case 11:
					mb.setMessage("PTM签名生成失败");
					label_Tips.setText("PTM签名生成失败");
					label_Tips.update();
					break;
				default:
					mb.setMessage("未知错误" + res);
					label_Tips.setText("未知错误");
				    label_Tips.update();

				}

				mb.open();				
			}
		});
		
		//创建导出按钮
	    Button btn_Export = new Button(group3,SWT.PUSH);
		btn_Export.setBounds(580, 420, 112, 32);
		btn_Export.setText("导出");
		btn_Export.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				saveFile();
		
			}
		});
		
		final Button select_all = new Button(group3, SWT.PUSH);
		select_all.setBounds(180, 420, 112, 32);
		select_all.setText("选择全部");

		// 点击获取按钮后的动作
		select_all.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				viewer.setAllChecked(true);
			}
		});
		
		final Button cancel_selected = new Button(group3, SWT.PUSH);
		cancel_selected.setBounds(380, 420, 112, 32);
		cancel_selected.setText("取消选择");

		// 点击获取按钮后的动作
		cancel_selected.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				viewer.setAllChecked(false);
			}
		});
	}
	private void saveFile(){
		FileDialog dialog = new FileDialog(shell,SWT.SAVE);
		dialog.setFilterPath(System.getProperty("SystemRoot"));
		dialog.setFilterExtensions(new String[]{"*.txt","*.*"});
		dialog.setFilterNames(new String[]{"Text Files(*.txt}","All Files(*.*)"});
		String file = dialog.open();
		if(file!=null){
			 try {
				   FileWriter fileWriter = new FileWriter(file);
		            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		           for (int i = 0; i < error_list.size(); i++) {
		        	  ErrorListBean bean = (ErrorListBean) (error_list.get(i));
		        	  bufferedWriter.write(String.valueOf(bean.getProgramName()) + " ");
		        	  bufferedWriter.write(String.valueOf(bean.getMeasuretime()) + " ");
		              bufferedWriter.write(String.valueOf(bean.getTypeName()) + " ");
		              bufferedWriter.write(String.valueOf(bean.getProcessDescription()) + " ");
		              bufferedWriter.newLine();  
		            }
		            bufferedWriter.close();
		            fileWriter.close(); 
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			System.out.print(file);
		}
		
	}
	
	public void newList()
	{
		 TableItem[] items = table.getItems(); 
		 if(items.length>100){
        // 循环所有行  
        for (int i = items.length - 1; i >= 100; i--)  
        {  
           
            // 否则选中，查找该表格中是否有该行  
            int index = table.indexOf(items[i]);  
            // 如果没有该行，继续循环  
            if (index < 0)  
                continue;  
            // 如果有该行，删除该行  
            // items[index].dispose();  
            table.remove(index); 
	}
        
  }

	}
	
	
	public void getList(){
		// System.out.println("error_list size:"+error_list.size());
		if (viewer == null) {
			System.out.println("viewer is null");
			return;
		}

		
		LibMeasure lm = new LibMeasure();
		IntByReference count = new IntByReference();
		int ret = lm.TM_getErrorList(el_FilterPath, count);
		switch (ret) {
		case 0:
			mb.setMessage("sucess to get errorlist!");
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
		// show data to list
		// loadData();
		int addCount = refreshData();

		// int item = table.getItemCount();
		mb.setMessage(mb.getMessage() + " " + addCount + "个新的错误事件");
		label_Tips.setText("刷新完成！共" + error_list.size() + "条报警日志，增加了"
				+ addCount + "条！");

		table.removeAll();
		viewer.refresh();
		// viewer.setItemCount(error_list.size());

		// 将报警日志增量式写入数据库
		AlarmLogUpdate alarmlogUpdate = new AlarmLogUpdate(
				org.forms.MainForm.db);
		alarmlogUpdate.update();

		mb.open();
	}

	/**
	 * 新建线程，用于刷新数据
	 * 
	 * @author tcwg
	 * 
	 */
//	class TimeRefresh extends Thread {
//		public void run() {
//			//for (int i = 0; i < 20; i++) {
//			while(true){
//				try {
//					//sleep(3000);
//					sleep(60000);
//				} catch (Exception e) {
//				}
//				shell.getDisplay().syncExec(new Runnable() {
//					public void run() {
//						//total = 0;
//						refreshData();
//						table.removeAll();
//						viewer.refresh();
//						//System.out.println(" !!!!!! " );
//					}
//				});
//
//			}
//		}
//	}

	private void loadData() {
		File efile = new File(el_FilterPath);

		BufferedReader br = null;
		if (error_list.size() != 0) {
			error_list.clear();
		}
		try {
			// fr = new FileReader(efile);
			// br = new BufferedReader(fr);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					efile), "GB2312"));

			String s = null;
			while ((s = br.readLine()) != null) {
				int p1 = s.indexOf(" ");
				int p2 = s.indexOf(" ", p1 + 1);
				String mv = s.substring(0, p1).toUpperCase();
				String pname = s.substring(p1 + 1, p2);
				String mt = s.substring(p2 + 1);
				int mp1=mt.indexOf("-");
				int mp2=mt.indexOf("-",mp1+1);
				int mp3=mt.indexOf("-",mp2+1);
				int mp4=mt.indexOf("-",mp3+1);
				int mp5=mt.indexOf("-",mp4+1);
				String mtdate=mt.substring(0, mp3);
				String mth=mt.substring(mp3+1, mp4);
				String mtm=mt.substring(mp4+1,mp5);
				String mts=mt.substring(mp5+1);
				String mtnew=mtdate+" "+mth+":"+mtm+":"+mts;
				
				//mt[10]=' ';
				//mt.
				//char[] mtchar=mt.toCharArray();
				//mtchar[10]=' ';
				//mtchar[13]=':';
				//mtchar[16]=':';
				//mt.getChars(0, 18, mtchar, 19);
				//mt=null;
				//mt+=mtchar;
				//String mtnew = mtchar.toString();
				//mt=mtchar.toString();
				ErrorListBean elb = new ErrorListBean();
				elb.setMeasuretime(mtnew);
				elb.setMeasureValue(mv);
				elb.setProgramName(pname);
				//add by Yangbo
				loader.getErrorlistItemDescription(elb);			
				
				error_list.add(0, elb);
				//error_list.add(elb);
			}		
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到" + efile);
		} catch (IOException ioe) {
			System.out.println("文件读取错误：" + efile);
		} finally {
			try {
				if (br != null) {
					br.close();
					br = null;
				}
				// if(fr != null)
				// {
				// fr.close();
				// fr = null;
				// }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for(int i = 0, len = error_list.size(); i < len; i++){
			ErrorListBean temp = (ErrorListBean)error_list.get(i);
			if(temp.getTypeName().equals("未知")){
				error_list.remove(i);
				error_list.add(temp);
				len--;
				i--;				
			}			
		}
		// total = error_list.size();
	}

	protected int refreshData() {

		LibMeasure lm = new LibMeasure();
		IntByReference count = new IntByReference();
		int ret = lm.TM_getErrorList(el_FilterPath, count);

		// show data to list
		loadData();
		table.removeAll();
		viewer.refresh();

		int totalhere = 0;
		int r = 0;
		if (this.error_list.size() > 0) {

			Table table = viewer.getTable();
			totalhere = table.getItemCount();
		}
		r=totalhere - total;
		if (r > 0) {
			label_Tips.setText("刷新完成！共" + totalhere + "条报警日志，增加了"
					+ (totalhere - total) + "条！");
			total = totalhere;
		}

		// System.out.println("error_list:" + error_list.size());
		// System.out.println("list:" + listsize);
		return r;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {

			if (inputElement instanceof List) { // System.out.println("ContentProvider:");
				return ((ArrayList) inputElement).toArray();
			} else {
				return new Object[0];
			}
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	class TableLabelProvider implements ITableLabelProvider {
		public String getColumnText(Object element, int columnIndex) {
			// System.out.println("item:"+table.getItemCount());
			if (element instanceof ErrorListBean) {
				ErrorListBean bean = (ErrorListBean) element;
				if (columnIndex == 0) {
					return table.getItemCount() + "";
				} else if (columnIndex == 1) {
					return bean.getProgramName();
				} else if (columnIndex == 2) {
					return bean.getMeasuretime();
				}else if (columnIndex == 3) {
					return bean.getTypeName();
				}else if (columnIndex == 4) {
					return bean.getProcessDescription();
				}
			}
			return null;
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub

		}
	}

}
