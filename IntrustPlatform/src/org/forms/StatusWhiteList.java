package org.forms;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.axis2.AxisFault;
import org.dboperate.DBOperate;
import org.dboperate.MeasurementLogUpdate;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import ui.Loader;
import ui.MeasurementBean;
import ui.PTM_connect;
import ui.ServerIPaddress;
import ui.TCCUtilit;
import ui.Verifier;
import ui.WhiteFilterBean;

import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.wsoperate.WhitelistOperate;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;


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
public class StatusWhiteList extends Composite {
	
	private TableViewer viewer;
	private Shell shell;
	private Button jWPDown;
	private Label jLabelRecommend;
	private Label jLabelNew;
	private Button jRadioRecommend;
	private Button jRadioNew;
	private Loader loader = new Loader();
	private Label lbl_ItemCount;
	public Label lbl_WhiteListVersion;
	private ArrayList white_list = new ArrayList();
	private Text txt_Check;
	
	private DBOperate db;
	private String par = null;
	private String wlPath = "C:/TCC/wl";
    private File wlfile = new File(wlPath);
    private String clearsql = "delete from 'wl'";
    static final String insert ="insert into wl values ";
    private Text text;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StatusWhiteList(Composite parent, int style) {
		super(parent, style);
		shell=this.getShell();
		
		this.setBounds(132, 0, 750, 462);
		this.setVisible(true);	
		
		loadData();
		
		viewer = new TableViewer(this,SWT.FULL_SELECTION |SWT.MULTI |SWT.BORDER);
		
		final Table table = viewer.getTable();
		table.setBounds(10, 46, 731, 408);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setRedraw(true);
		
		TableColumn tbCol_Time = new TableColumn(table, SWT.NONE);
		tbCol_Time.setWidth(80);
		tbCol_Time.setText("序号");
		boolean ssc = true;
		viewer.setSorter(ssc?Sorter.INDEX_ASC:Sorter.INDEX_DESC);
		tbCol_Time.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.INDEX_ASC:Sorter.INDEX_DESC);
				asc=!asc;
			}
		});

		
		TableColumn tbCol_Path = new TableColumn(table, SWT.NONE);
		tbCol_Path.setWidth(124);
		tbCol_Path.setText("进程");
		tbCol_Path.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.PN_ASC:Sorter.PN_DESC);
				asc=!asc;
			}
		});

		
		TableColumn tbCol_Info = new TableColumn(table, SWT.NONE);
		tbCol_Info.setWidth(137);
		tbCol_Info.setText("Hash值");
		tbCol_Info.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.MV_ASC:Sorter.MV_DESC);
				asc=!asc;
			}
		});
		
		TableColumn tbCol_Type = new TableColumn(table, SWT.NONE);
		tbCol_Type.setWidth(100);
		tbCol_Type.setText("类型");
		tbCol_Type.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.TYPENAME_ASC:Sorter.TYPENAME_DESC);
				asc=!asc;
			}
		});
		
		TableColumn tbCol_Hash = new TableColumn(table, SWT.NONE);
		tbCol_Hash.setWidth(235);
		tbCol_Hash.setText("描述信息");
		tbCol_Hash.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.PS_ASC:Sorter.PS_DESC);
				asc=!asc;
			}
		});
		
		viewer.setContentProvider(new TableViewerContentProvider());
		viewer.setLabelProvider(new TableViewerLabelProvider());
		viewer.setInput(white_list);				//===== 设置数据源 ======
		
		/*
		lbl_WhiteListVersion = new Label(this, SWT.NONE);
		lbl_WhiteListVersion.setBounds(262, 12, 140, 22);
		lbl_WhiteListVersion.setText(getFileModifiedTime("c:/wl"));
		
		Label lbl_Version = new Label(this, SWT.NONE);
		lbl_Version.setBounds(182, 15, 80, 22);
		lbl_Version.setText("白名单版本：");
		*/
		
		lbl_ItemCount = new Label(this, SWT.NONE);
		lbl_ItemCount.setBounds(26, 15, 145, 24);
		lbl_ItemCount.setText("白名单进程： " + white_list.size()+ "个");
		
		//复制列表内容
		viewer.getTable().addMouseListener(new MouseAdapter() {  
            public void mouseDown(MouseEvent e) {  
                
                    Menu menu = new Menu(table);  
                    table.setMenu(menu);  
                    MenuItem item = new MenuItem(menu, SWT.PUSH);  
                    item.setText("复制");  
                    item.addListener(SWT.Selection, new Listener() {  
                        public void handleEvent(Event event) { 
                        	TableItem item = table.getItem(table.getSelectionIndex());
                        	WhiteFilterBean mbean = (WhiteFilterBean)item.getData();
                            java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            StringSelection contents=new StringSelection(mbean.getProgramName()+"  " + mbean.getMeasureHexValue() +
                            		"  "+ mbean.getTypeName()+"  " + mbean.getProcessDescription());
                            clipboard.setContents( contents,null); 
                        
                          
                        } });  
                }  
              
		}); 
		
		
		
		//添加查询排序按钮
		Button btn_Check = new Button(this, SWT.NONE);
		btn_Check.setBounds(281,12, 60, 25);
		btn_Check.setText("查询");
		btn_Check.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.addFilter(new MyFilter());
			}
		});
		txt_Check = new Text(this,SWT.BORDER);
		txt_Check.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txt_Check.setBounds(347, 12, 100, 23);
		txt_Check.setText("");

		Text txt_Sort = new Text(this, SWT.CENTER);
		txt_Sort.setEnabled(false);
		txt_Sort.setEditable(false);
		//txt_Sort.setBackground(SWTResourceManager.getColor(236,233,216));
		txt_Sort.setBounds(487, 17, 126, 23);
		txt_Sort.setText("点击每个表头进行排序");
		//txt_Sort.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		/*
		
		{
			jRadioNew = new Button(this, SWT.RADIO | SWT.LEFT);
			jRadioNew.setText("最新版本号");
			jRadioNew.setBounds(414, 9, 94, 20);
		}			
		{
			jRadioRecommend = new Button(this, SWT.RADIO | SWT.LEFT);
			jRadioRecommend.setText("推荐版本号");
			jRadioRecommend.setBounds(414, 27, 94, 20);
		}
		{
			jLabelNew = new Label(this, SWT.NONE);
			jLabelNew.setBounds(508, 9, 137, 17);
			jLabelNew.setText("new");
		}			
		{
			jLabelRecommend = new Label(this, SWT.NONE);
			jLabelRecommend.setBounds(508, 32, 137, 20);
			jLabelRecommend.setText("recommend");
		}
		
		{
			jWPDown = new Button(this, SWT.PUSH | SWT.CENTER);
			jWPDown.setText("\u767d\u540d\u5355\u4e0b\u8f7d");
			jWPDown.setBounds(664, 9, 74, 30);
			jWPDown.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {		
					//下载白名单
					
					
					db = org.forms.MainForm.db;
					if(jRadioNew.getSelection() == true){
						System.out.println("选择下载最新版本");
						String res[] = par.split("&&&");
						BufferedReader reader = null;
						WhitelistOperate wlop = new WhitelistOperate();
					    String wlcontent = wlop.downloadWl(Integer.parseInt(res[0]));
											    
					    String temp[] = wlcontent.split("###");
						try {
							db.cleartable("delete from wlversion");
							db.inserttable("insert into wlversion values " + "(" + "'" + res[0] + "'" + ")", 4);
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
						//System.out.println("下载成功");
						//此处有提示框
						MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);
						mb.setText("执行结果");
						mb.setMessage("白名单下载成功！");
						mb.open();
						//JOptionPane.showMessageDialog(null, "白名单下载成功！"); 
					}
					else if(jRadioRecommend.getSelection() == true) {
						System.out.println("选择下载推荐版本");
						String res[] = par.split("&&&");
						BufferedReader reader = null;
						WhitelistOperate wlop = new WhitelistOperate();
					    String wlcontent = wlop.downloadWl(Integer.parseInt(res[2]));
					    
					    String temp[] = wlcontent.split("###");
						try {
							db.cleartable("delete from wlversion");
							db.inserttable("insert into wlversion values " + "(" + "'" + res[2] + "'" + ")", 4);
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
						//System.out.println("下载成功");
						//此处有提示框
						MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);
						mb.setText("执行结果");
						mb.setMessage("白名单下载成功！");
						mb.open();
						//JOptionPane.showMessageDialog(null, "白名单下载成功！"); 
					}
					else {
						//System.out.println("未选择版本");
						//此处有提示框
						MessageBox mb = new MessageBox(new Shell(), SWT.ICON_INFORMATION);
						mb.setText("提示");
						mb.setMessage("请选择需要下载的白名单版本！");
						mb.open();
						//JOptionPane.showMessageDialog(null, "请选择需要下载的白名单版本！"); 
					}
					
				}
				});
			

		}	
		*/
		refresh();
		
	}

	//添加表格过滤器实现查找功能	
	public class MyFilter extends ViewerFilter {
	    public boolean select(Viewer viewer, Object parentElement, Object element) {
	    	WhiteFilterBean mb = (WhiteFilterBean) element;
	       return mb.getProgramName().startsWith(txt_Check.getText());
	    } 
	}

	
	//添加排序器实现排序功能
	public static class Sorter extends ViewerSorter{
		
		private static final int INDEX = 1;
		private static final int MV = 2;
		private static final int TYPENAME = 3;
		private static final int PS = 4;
		private static final int PN = 5;
		
		public static final Sorter INDEX_ASC = new Sorter(INDEX);
		public static final Sorter INDEX_DESC = new Sorter(-INDEX);
		public static final Sorter MV_ASC = new Sorter(MV);
		public static final Sorter MV_DESC = new Sorter(-MV);
		public static final Sorter TYPENAME_ASC = new Sorter(TYPENAME);
		public static final Sorter TYPENAME_DESC = new Sorter(-TYPENAME);
		public static final Sorter PS_ASC = new Sorter(PS);
 		public static final Sorter PS_DESC = new Sorter(-PS);
 		public static final Sorter PN_ASC = new Sorter(PN);
 		public static final Sorter PN_DESC = new Sorter(-PN);
 		
 		private int sortType;
		private Sorter(int sortType) {
			// TODO Auto-generated constructor stub
			this.sortType = sortType;
		}
		public int compare(Viewer viewer,Object e1,Object e2){
			WhiteFilterBean mb1 =  (WhiteFilterBean)e1;
			WhiteFilterBean mb2 =  (WhiteFilterBean)e2;
			switch(sortType){
			case INDEX:{
				Integer in1 = mb1.getIndex();
			    Integer in2= mb2.getIndex();
				return in1.compareTo(in2);
			}
			case -INDEX:{
				Integer in1 = mb1.getIndex();
			    Integer in2= mb2.getIndex();
				return in2.compareTo(in1);
			}
			case MV:{
				String mv1 = mb1.getMeasureHexValue();
				String mv2 = mb2.getMeasureHexValue();
				return mv1.compareTo(mv2);
			}
			case -MV:{
				String mv1 = mb1.getMeasureHexValue();
				String mv2 = mb2.getMeasureHexValue();
				return mv2.compareTo(mv1);
			}
			case TYPENAME:{
				String ty1 = mb1.getTypeName();
				String ty2 = mb2.getTypeName();
				return ty1.compareTo(ty2);
			}
			case -TYPENAME:{
				String ty1 = mb1.getTypeName();
				String ty2 = mb2.getTypeName();
				return ty2.compareTo(ty1);
			}
			case PS:{
				String ps1 = mb1.getProcessDescription();
				String ps2 = mb2.getProcessDescription();
				return ps1.compareTo(ps2);
			}
			case -PS:{
				String ps1 = mb1.getProcessDescription();
				String ps2 = mb2.getProcessDescription();
				return ps2.compareTo(ps1);
			}
			case PN:{
				String ps1 = mb1.getProgramName();
				String ps2 = mb2.getProgramName();
				return ps1.compareTo(ps2);
			}
			case -PN:{
				String ps1 = mb1.getProgramName();
				String ps2 = mb2.getProgramName();
				return ps2.compareTo(ps1);
			}
			}
			return 0;
		}
	}
	
	public void refresh() {
		loadData();
		viewer.refresh();
		lbl_ItemCount.setText("白名单进程： " + white_list.size()+ "个");
		//lbl_WhiteListVersion.setText(getFileModifiedTime("c:/wl"));
	}
	
	/**
	 * 获取最新进程数据
	 */
	private void loadData() {
	//	System.out.println("white_list.size()"+white_list.size());
		// this.data_list.clear();
		this.white_list = loader.getWhiteList1();
//		System.out.println("Size:"+white_list.size());
		
	}


	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public String getFileModifiedTime(String name){		
		File file = new File(name);
	    //毫秒数
	    long modifiedTime = file.lastModified();	//相对时间	    
	    //通过毫秒数构造日期 即可将毫秒数转换为日期
	    Date d = new Date(modifiedTime);
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String dateTime = formatter.format(new Date(file.lastModified()));
	     return dateTime;
		
	}
	
	/**
	 * The content provider of the tableViewer
	 * 
	 * @author yjyj
	 * 
	 */
	class TableViewerContentProvider implements
			IStructuredContentProvider {
		public Object[] getElements(Object element) {
			// TODO Auto-generated method stub			
			return ((ArrayList) element).toArray();
		}

		public void dispose() {
			// TODO Auto-generated method stub
		}

		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
			// TODO Auto-generated method stub
		}
	}


	/*
	 * The label provider of the tableViewer
	 */
	class TableViewerLabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * 设置数据源与列序号之间的对应关系；
		 * 若参数为String，则第一列显示其内容；否则显示MeasurementBean内容		
		 */
		public String getColumnText(Object element, int col) {
			// TODO Auto-generated method stub
			if (element instanceof String) {
				switch (col) {
				case 0:
					return (String) element;
				default:
					return null;
				}
			} 
			
			else {
				WhiteFilterBean mBean = (WhiteFilterBean) element;
				
				switch (col) {
				case 0:
					return new Integer(mBean.getIndex()).toString();
				case 1:
					return mBean.getProgramName();
				case 2:
					return mBean.getMeasureHexValue();
				case 3:
					return mBean.getTypeName();
				case 4:
					return mBean.getProcessDescription();
					

				default:
					return null;
				}
			}

		}

		// The following functions have no use in this class
		public void addListener(ILabelProviderListener arg0) {
			// TODO Auto-generated method stub
		}

		public void dispose() {
			// TODO Auto-generated method stub
		}

		public boolean isLabelProperty(Object arg0, String arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		public void removeListener(ILabelProviderListener arg0) {
			// TODO Auto-generated method stub
		}
	}
	

	/**
	 * 新建线程，用于刷新数据
	 * @author tcwg
	 *
	 */
	class TimeRefresh extends Thread {
		public void run() {
			while (true) {				
				try {
					sleep(3000);
				} catch (Exception e) {
				}
				shell.getDisplay().syncExec(new Runnable() {
					public void run() {						
						refresh();
						viewer.setItemCount(white_list.size());
						lbl_ItemCount.setText("白名单进程： " + white_list.size()+ "个");
						
						//System.out.println(comboText + "  " + white_list.size());
					}
				});

			}
		}
	}
	
	public void setwlversion(String parameter) {
		par = parameter;
		if(par == null) {
			jRadioNew.setEnabled(false);
			jLabelNew.setText("服务器连接失败");
			jLabelNew.setEnabled(false);
			jRadioRecommend.setEnabled(false);
			jLabelRecommend.setText("服务器连接失败");
			jLabelRecommend.setEnabled(false);
			jWPDown.setEnabled(false);
		}
		else {
			if(par.equals("non_existent")) {
				//jRadioNew.setEnabled(false);
				jLabelNew.setText("无最新版本号");
				jLabelNew.setEnabled(false);
				jRadioRecommend.setEnabled(false);
				jLabelRecommend.setText("无推荐版本号");
				jLabelRecommend.setEnabled(false);
				jWPDown.setEnabled(false);
			}
			else {
				String res[] = par.split("&&&");
				if(res[1].equals("true")) {
					jLabelNew.setText(res[0]+"，管理员修改产生 ");
				}
				else {
					jLabelNew.setText(res[0]+"，非管理员修改产生 ");
				}
				jRadioNew.setEnabled(true);
				jLabelNew.setEnabled(true);
				if(res[2].equals("non_existent")) {
					jLabelRecommend.setText("无推荐版本号");
					jRadioRecommend.setEnabled(false);
					jLabelRecommend.setEnabled(false);
				}
				else {
					if(res[3].equals("true")) {
						jLabelRecommend.setText(res[2]+"，管理员修改产生 ");
					}
					else {
						jLabelRecommend.setText(res[2]+"，非管理员修改产生 ");
					}
					jRadioRecommend.setEnabled(true);
					jLabelRecommend.setEnabled(true);					
				}
				jWPDown.setEnabled(true);
			}				
		}
	}
}
