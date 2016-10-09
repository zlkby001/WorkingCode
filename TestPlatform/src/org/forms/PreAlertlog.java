package org.forms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.dboperate.AlarmLogUpdate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.forms.StatusAlertLog.ContentProvider;
import org.forms.StatusAlertLog.TableLabelProvider;
import org.forms.StatusAlertLog.TimeRefresh;

import ui.ErrorListBean;
import ui.LibMeasure;
import ui.Loader;

import com.sun.jna.ptr.IntByReference;


	public class PreAlertlog extends Dialog{
		protected static int count;
		protected Shell shell;
		protected static Shell shellPreAlert;
		private CheckboxTableViewer viewer;
		private Color color;
		private Object result;
		private static MessageBox mb;
		private Group group;
		private Table table;
		private Label label_Tips;
		private ArrayList error_list = new ArrayList();
		public static int total = 0;
		static String el_FilterPath = "C:\\TCC\\load_errorlist.log";
		private Loader loader = new Loader();
		public TimeRefresh thd_Refresh = new TimeRefresh();
		public PreAlertlog(Shell parent, int style) {
		
			super(parent, style);

			count++;
			
			// TODO Auto-generated constructor stub
		}
		public Object open() {
			Display display = Display.getDefault();	
			
			createContents();

			shell.open();

			shellPreAlert.layout();

			// tablerefresh();
		//	Display display = getParent().getDisplay();

			color = new Color(Display.getDefault(), new RGB(239, 203, 228));
			while (!shellPreAlert.isDisposed()) {

				if (!display.readAndDispatch()) {

					display.sleep();
				}
			}
			return result;
		}
		void createContents() {
			// TODO Auto-generated method stub
			final Display display = Display.getDefault();

			final Shell shellPreAlert = new Shell(getParent(), SWT.RESIZE | SWT.TITLE);
			
			shellPreAlert.setSize(797, 533);
			
			shellPreAlert.setVisible(true);
			
	
			
			shellPreAlert.setText("报警信息");

			Rectangle displayBounds = display.getPrimaryMonitor().getBounds();

			Rectangle shellBounds = shellPreAlert.getBounds();

			int x = displayBounds.x + (displayBounds.width - shellBounds.width) >> 1;

			int y = displayBounds.y + (displayBounds.height - shellBounds.height) >> 1;

			shellPreAlert.setLocation(x, y);

			mb = new MessageBox(shellPreAlert.getShell());
			
			group = new Group(shellPreAlert, SWT.NONE);
			group.setBounds(0, 0, 795, 566);
			group.setRedraw(true);

			group.setLayout(null);
			viewer = CheckboxTableViewer.newCheckList(group, SWT.BORDER | SWT.FULL_SELECTION);
			
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

			label_Tips = new Label(group, SWT.NONE);
			label_Tips.setBounds(40, 20, 317, 22);
			label_Tips.setText("报警日志条数： " + table.getItemCount() + "条");
			
			Button btn_ClearAlert = new Button(group, SWT.NONE);
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
 
			final Button obtain_el = new Button(group, SWT.PUSH);
			obtain_el.setBounds(503, 10, 112, 32);
			obtain_el.setText("获取报警日志");
			
			Button btnNewButton = new Button(group, SWT.NONE);
			btnNewButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					shellPreAlert.close();

					count--;
				}
			});
			btnNewButton.setBounds(515, 425, 122, 61);
			btnNewButton.setText("关闭");

			// 点击获取按钮后的动作
			obtain_el.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					getList();
					//newList();
			}});

			// 设置内容器
			viewer.setContentProvider(new ContentProvider());
			// 设置标签器
			viewer.setLabelProvider(new TableLabelProvider());
			// 把数据集合给tableView
			viewer.setInput(error_list);

			refreshData();

			thd_Refresh.start();
			//thd_Refresh.suspend();
//			shellPreAlert.open();
//
//			shellPreAlert.layout();
//
//			while (!shellPreAlert.isDisposed()) {
//
//				if (!display.readAndDispatch()) {
//
//					display.sleep();
//				}
//			}
		}
		
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
		class TimeRefresh extends Thread {
			public void run() {
				//for (int i = 0; i < 20; i++) {
				while(true){
					try {
						//sleep(3000);
						sleep(60000);
					} catch (Exception e) {
					}
					shellPreAlert.getDisplay().syncExec(new Runnable() {
						public void run() {
							//total = 0;
							refreshData();
							table.removeAll();
							viewer.refresh();
							//System.out.println(" !!!!!! " );
						}
					});

				}
			}
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
