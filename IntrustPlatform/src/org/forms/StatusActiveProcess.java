package org.forms;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import ui.ErrorListBean;
import ui.LibMeasure;
import ui.Loader;
import ui.MeasurementBean;
import ui.PTM_connect;
import ui.ServerIPaddress;
import ui.TCCUtilit;
import ui.Verifier;
import org.eclipse.wb.swt.SWTResourceManager;

public class StatusActiveProcess extends Composite {
	
	private Shell shell;
	// private Text server_port_text;
	private String wlPath = "C:/TCC/wl";
	private Loader loader = new Loader();
	private TableViewer viewer;
	private Table table;
	private ArrayList data_list = new ArrayList();
    private Text txt_Check;
	// private CCombo combo;
	private String comboText = "";
	// private Label lbl_ItemCount;

	public static IPText txt_ServerIP;
	private Verifier verifier = new Verifier();
	private TCCUtilit utilit = new TCCUtilit();
	private int total = 0;// 完整性个数
	private int old_total = loader.getMeasurements("Active").size();
	public static Text txt_ServerPort;
	private Group info_group;
	private Label label_Tips;
	private Color color;
	

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public StatusActiveProcess(Composite parent, int style) {
		super(parent, style);
		shell = this.getShell();
		this.setBounds(132, 0, 780, 600);
		this.setVisible(true);
//		color = Display.getDefault().getSystemColor(SWT.COLOR_MAGENTA);
		color = new Color(Display.getDefault(),new RGB(239,203,228));

		loadData();
		creatContents();

//		table.addListener(SWT.MeasureItem, new Listener() { // TODO 修改行高度
//					public void handleEvent(Event event) {
//						event.width = table.getGridLineWidth(); // 设置宽度
//						event.height = (int) Math.floor(event.gc
//								.getFontMetrics().getHeight() * 1.5); // 设置高度为字体高度的1.5倍
//					}
//				});
		
		tableRefresh();

	}

	public void creatContents() {

		final Group up_group = new Group(this, SWT.SHADOW_NONE);
		up_group.setBounds(0, 10, 405, 87);

		final Label sever_ip_label = new Label(up_group,SWT.HORIZONTAL
				| SWT.CENTER);
		sever_ip_label.setBounds(10, 19, 72, 22);
		sever_ip_label.setAlignment(SWT.CENTER);
		sever_ip_label.setText("服务器地址：");

		txt_ServerIP = new IPText(up_group, SWT.BORDER);
		txt_ServerIP.setEnabled(false);
		txt_ServerIP.setBackground(SWTResourceManager.getColor(204, 232, 207));
		txt_ServerIP.setBounds(84, 19, 172, 22);
		txt_ServerIP.setText(ServerIPaddress.getIp());
		

		final Button verify_button = new Button(up_group, SWT.NONE);
		verify_button.setBounds(278, 19, 102, 46);
		final GridData gd_verify_button = new GridData(SWT.RIGHT, SWT.CENTER,
				true, true);
		gd_verify_button.heightHint = 30;
		gd_verify_button.minimumWidth = 60;
		gd_verify_button.minimumHeight = 30;
		// verify_button.setLayoutData(gd_verify_button);
		verify_button.setText("验证");

		Label label = new Label(up_group, SWT.NONE);
		label.setBounds(33, 50, 48, 22);
		label.setText("端口号：");

		txt_ServerPort = new Text(up_group, SWT.BORDER | SWT.CENTER);//设置文字子体位置居中
		txt_ServerPort.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));//设置文本子体大小
		txt_ServerPort.setEnabled(false);
		txt_ServerPort.setText(ServerIPaddress.getPort() + "");
		txt_ServerPort.setBounds(84, 47, 172, 22);

		//
		final Group state_group = new Group(this, SWT.None);
		state_group.setBounds(411, 10, 355, 87);
		final GridData gd_state_composite = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		gd_state_composite.heightHint = 40;
		gd_state_composite.minimumHeight = 100;
		gd_state_composite.widthHint = 418;

		final Label state_label = new Label(state_group, SWT.SINGLE);
		state_label.setBounds(10, 14, 211, 41);
		state_label.setAlignment(SWT.CENTER);
		state_label.setText("实时验证状态显示，验证不正确时，可以点击左边的查看详情按钮！");

		final ProgressBar prgBar = new ProgressBar(state_group, SWT.NONE);
		prgBar.setBounds(10, 61, 211, 16);

		final Button btviewerror = new Button(state_group, SWT.NONE);
		btviewerror.setBounds(242, 14, 103, 51);

		final GridData gd_state_list = new GridData(SWT.FILL, SWT.CENTER, true,
				true);
		gd_state_list.widthHint = 400;

		btviewerror.setText("未授权进程");
		btviewerror.setEnabled(false);

		btviewerror.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {// 查看验证失败详情

				if (ProcessVerifyFailList.count == 0) {
					ProcessVerifyFailList plist = new ProcessVerifyFailList(
							shell, 0);
					plist.open();

				}
			}
		});

		verify_button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				btviewerror.setEnabled(false);// 每次点验证时将查看详情按钮disable掉
				ServerIPaddress.setIp(txt_ServerIP.getText());
				ServerIPaddress.setPort(Methods.StringtoInt(txt_ServerPort
						.getText()));
				Methods.updateCommunicationSet();
				int res = 0;
				String server_ip = ServerIPaddress.getIp();
				int server_port = ServerIPaddress.getPort();

				if (server_ip.equals("") || server_port == 0) // ip地址或者端口号为空
					res = 201;
				else {

					System.out.println(server_ip);
					System.out.println(server_port);

					state_label.setText("服务器IP地址为：" + server_ip);
					state_label.update();

					state_label.setText("服务器端口为：" + server_port);
					state_label.update();
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
								String swcom = "Active";
								System.out
										.println("the selected software component:"
												+ swcom);
								res = verifier.verifiy(server_ip, server_port,
										state_label, prgBar, swcom,
										PTM_connect.pubkey, new ArrayList<ErrorListBean>());
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
					mb.setMessage("验证正确");
					state_label.setText("验证正确");
					state_label.update();
					break;

				case 1:
					mb.setMessage("验证不正确");
					state_label.setText("验证不正确");
					state_label.update();
					break;
				case 1000:
					mb.setMessage("验证不正确,但接受验证失败的文件没有成功！");
					state_label.setText("验证不正确，接受反馈失败");
					state_label.update();
					break;
				case 1001:
					mb.setMessage("验证不正确,接收反馈文件成功！");
					state_label.setText("验证不正确，接受反馈文件成功！");
					state_label.update();
					btviewerror.setEnabled(true);

					break;

				case 2:
					mb.setMessage("服务器接受文件失败");
					state_label.setText("服务器接受文件失败");
					state_label.update();
					break;

				case 3:
					mb.setMessage("服务器内部错误");
					state_label.setText("服务器内部错误");
					state_label.update();
					break;

				case 101:
					mb.setMessage("向服务器发送文件失败");
					state_label.setText("向服务器发送文件失败");
					state_label.update();
					break;

				case 102:
					mb.setMessage("获取度量文件失败");
					state_label.setText("获取度量文件失败");
					state_label.update();
					break;
				case 103:
					mb.setMessage("连接服务器失败");
					state_label.setText("连接服务器失败");
					state_label.update();
					break;
				case 104:
					mb.setMessage("获取验证结果失败");
					state_label.setText("获取验证结果失败");
					state_label.update();
					break;

				case 201:
					mb.setMessage("服务器地址不合法");
					state_label.setText("服务器地址不合法");
					state_label.update();
					break;

				case 202:
					mb.setMessage("服务器端口不合法");
					state_label.setText("服务器端口不合法");
					state_label.update();
					break;
				case 203:
					mb.setMessage("请输入服务器地址和端口");
					state_label.setText("请输入服务器地址和端口");
					state_label.update();
					break;
				case 10:
					mb.setMessage("接收身份验证结果失败");
					state_label.setText("接收身份验证结果失败");
					state_label.update();
					break;
				case 9:
					mb.setMessage("身份验证失败");
					state_label.setText("身份验证失败");
					state_label.update();
					break;
				case 11:
					mb.setMessage("PTM签名生成失败");
					state_label.setText("PTM签名生成失败");
					state_label.update();
					break;
				default:
					mb.setMessage("未知错误" + res);
					state_label.setText("未知错误");
					state_label.update();

				}

				mb.open();

			}
		});
		// shell.setImage(new Image(Display.getDefault(),"res/ico.ico"));

		info_group = new Group(this, SWT.NONE);
		info_group.setBounds(0, 103, 766, 365);

		info_group.setRedraw(true);
		info_group.setLayout(null);
		
		//创建查询及输入框
		//查询功能
		Button btn_Check = new Button(info_group, SWT.NONE);
		btn_Check.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.addFilter(new MyFilter());
				txt_Check.setText("");
				
			}
		});
		btn_Check.setBounds(140,10, 60, 25);
		btn_Check.setText("查询");
	    txt_Check = new Text(info_group,SWT.BORDER);
		txt_Check.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txt_Check.setBounds(210, 10, 100, 23);
		txt_Check.setText("");
		Text txt_Sort = new Text(info_group, SWT.CENTER);
		txt_Sort.setEnabled(false);
		txt_Sort.setEditable(false);
		//txt_Sort.setBackground(SWTResourceManager.getColor(236,233,216));
		txt_Sort.setBounds(471, 16, 120, 19);
		txt_Sort.setText("点击每个表头进行排序");
		txt_Sort.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		
		viewer = new TableViewer(info_group, SWT.FULL_SELECTION | SWT.MULTI
				| SWT.BORDER);
		table = viewer.getTable();
		table.setLocation(0, 40);
		table.setSize(756, 281);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setRedraw(true);

		final TableColumn newColumnTableColumn = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn.setResizable(true);
		newColumnTableColumn.setAlignment(SWT.CENTER);
		newColumnTableColumn.setWidth(50);
		newColumnTableColumn.setText("序号");
		//
		newColumnTableColumn.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.INDEX_ASC:Sorter.INDEX_DESC);
				asc=!asc;
				tableRefresh();
			}
		});
		
		//加入进程名称列
		TableColumn tblName = new TableColumn(table, SWT.CENTER);
		tblName.setResizable(true);
		tblName.setAlignment(SWT.CENTER);
		tblName.setWidth(107);
		tblName.setText("进程");
		tblName.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.PN_ASC:Sorter.PN_DESC);
				asc=!asc;
				tableRefresh();
			}
		});
        //
		final TableColumn newColumnTableColumn_1 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_1.setResizable(true);
		newColumnTableColumn_1.setAlignment(SWT.CENTER);
		newColumnTableColumn_1.setWidth(151);
		newColumnTableColumn_1.setText("进程路径");

		final TableColumn newColumnTableColumn_2 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_2.setAlignment(SWT.CENTER);
		newColumnTableColumn_2.setWidth(164);
		newColumnTableColumn_2.setText("Hash值");
		//
		newColumnTableColumn_2.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.MV_ASC:Sorter.MV_DESC);
				asc=!asc;
				tableRefresh();
			}
			
		});
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnHash = new TableColumn(table, SWT.NONE);
		tblclmnHash.setWidth(107);
		tblclmnHash.setText("类型");
		//
		tblclmnHash.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.TYPENAME_ASC:Sorter.TYPENAME_DESC);
				asc=!asc;
				tableRefresh();
			}
		});

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(250);
		tableColumn.setText("描述信息");
		//
		tableColumn.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.PS_ASC:Sorter.PS_DESC);
				asc=!asc;
				tableRefresh();
			}
		});

		label_Tips = new Label(info_group, SWT.NONE);
		label_Tips.setLocation(10, 336);
		label_Tips.setSize(326, 19);
		label_Tips.setText("当前活动进程： " + data_list.size() + "个");

		Button btn_Refresh = new Button(info_group, SWT.NONE);
		btn_Refresh.setBounds(419, 327, 103, 31);
		btn_Refresh.setText("刷新");
		btn_Refresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txt_Check.setText("");
				refresh();
				tableRefresh();
				viewer.setItemCount(data_list.size());

			}
		});
	
		//复制列表内容
		table.addMouseListener(new MouseAdapter() {  
            public void mouseDown(MouseEvent e) {  
                
                    Menu menu = new Menu(table);  
                    table.setMenu(menu);  
                    MenuItem item = new MenuItem(menu, SWT.PUSH);  
                    item.setText("复制");  
                    item.addListener(SWT.Selection, new Listener() {  
                        public void handleEvent(Event event) { 
                        	TableItem item = table.getItem(table.getSelectionIndex());
                        	MeasurementBean mbean = (MeasurementBean)item.getData();
                        	String fName1 = mbean.getProgram_name().trim(); 
              			    File tempFile =new File( fName1.trim());  
              		        String fileName = tempFile.getName();
                            java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            StringSelection contents=new StringSelection(fileName+"  "+ mbean.getProgram_name()+"  " + mbean.getMv() +
                            		"  "+ mbean.getTypeName()+"  " + mbean.getProcessDescription());
                            clipboard.setContents( contents,null); 
                        
                          
                        } });  
                }  
              
		}); 
 
      //创建导出按钮
		Button btn_Export = new Button(info_group, SWT.NONE);
		btn_Export.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				saveFile();
				
				MessageBox ms = new MessageBox(shell);
				ms.setMessage("活动进程文件'HuoDongJinCheng'已导出到路径："+FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
				ms.open();
		
			}
		});
		btn_Export.setBounds(530, 327, 103, 31);
		btn_Export.setText("导出");
		
		
		viewer.setContentProvider(new TableViewerContentProvider());
		viewer.setLabelProvider(new TableViewerLabelProvider());
		viewer.setInput(data_list);

	}
	//添加导出功能
	private void saveFile(){
		//JFileChooser jf = new JFileChooser();  
	//	jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY);  
		//jf.showDialog(,null);  
		//Composite banner = new Composite(shell, SWT.NONE);
		//jf.showSaveDialog(null);
	//	jf.setVisible(true);
		//File fi = jf.getSelectedFile();  
	//	if(fi!=null){
		//String f = fi.getAbsolutePath();  
		//System.out.println("save: "+f);
		
		File desktopDir = FileSystemView.getFileSystemView()
		.getHomeDirectory();
		String desktopPath = desktopDir.getAbsolutePath();
		
		try{  
		    FileWriter out = new FileWriter(desktopPath+"/HuoDongJinCheng.txt");  
		    BufferedWriter bufferedWriter = new BufferedWriter(out);
	           for (int i = 0; i < data_list.size(); i++) {
	        	  MeasurementBean merr = (MeasurementBean) (data_list.get(i));
	        	  bufferedWriter.write(String.valueOf(merr.getSoftwareName()) + " ");
	        	  bufferedWriter.write(String.valueOf(merr.getProgram_name()) + " ");
	              bufferedWriter.write(String.valueOf(merr.getMv()) + " ");
	              bufferedWriter.write(String.valueOf(merr.getTypeName()) + " ");
	              bufferedWriter.write(String.valueOf(merr.getProcessDescription()) + " ");
	              bufferedWriter.newLine();  
	            }
	            bufferedWriter.close();
	            out.close(); 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
	//	}
/**
		JFileChooser jf = new JFileChooser();  
		jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY);  
		//jf.showDialog(,null);  
		//Composite banner = new Composite(shell, SWT.NONE);
		jf.showSaveDialog(null);
		jf.setVisible(true);
		File fi = jf.getSelectedFile();  
		if(fi!=null){
		String f = fi.getAbsolutePath();  
		System.out.println("save: "+f);  
		try{  
		    FileWriter out = new FileWriter(f);  
		    BufferedWriter bufferedWriter = new BufferedWriter(out);
	           for (int i = 0; i < data_list.size(); i++) {
	        	  MeasurementBean merr = (MeasurementBean) (data_list.get(i));
	        	  bufferedWriter.write(String.valueOf(merr.getSoftwareName()) + " ");
	        	  bufferedWriter.write(String.valueOf(merr.getProgram_name()) + " ");
	              bufferedWriter.write(String.valueOf(merr.getMv()) + " ");
	              bufferedWriter.write(String.valueOf(merr.getTypeName()) + " ");
	              bufferedWriter.write(String.valueOf(merr.getProcessDescription()) + " ");
	              bufferedWriter.newLine();  
	            }
	            bufferedWriter.close();
	            out.close(); 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
		}
		**/
		  
	
//		FileDialog dialog = new FileDialog(shell,SWT.SAVE);
//		dialog.setFilterPath(System.getProperty("SystemRoot"));
//		dialog.setFilterExtensions(new String[]{"*.txt","*.*"});
//		dialog.setFilterNames(new String[]{"Text Files(*.txt}","All Files(*.*)"});
//		String file = dialog.open();
//		if(file!=null){
//			try {
//				   FileWriter fileWriter = new FileWriter(file);
//		            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//		           for (int i = 0; i < data_list.size(); i++) {
//		        	  MeasurementBean merr = (MeasurementBean) (data_list.get(i));
//		        	  bufferedWriter.write(String.valueOf(merr.getSoftwareName()) + " ");
//		        	  bufferedWriter.write(String.valueOf(merr.getProgram_name()) + " ");
//		              bufferedWriter.write(String.valueOf(merr.getMv()) + " ");
//		              bufferedWriter.write(String.valueOf(merr.getTypeName()) + " ");
//		              bufferedWriter.write(String.valueOf(merr.getProcessDescription()) + " ");
//		              bufferedWriter.newLine();  
//		            }
//		            bufferedWriter.close();
//		            fileWriter.close(); 
//		        } catch (Exception e) {
//		            e.printStackTrace();
//		        }
//			System.out.print(file);
//		}
//		if(file == null){
//			return;
//		}
	
	}
	
	
	//添加表格过滤器实现查找功能	
	public class MyFilter extends ViewerFilter {
	    public boolean select(Viewer viewer, Object parentElement, Object element) {
	       MeasurementBean mb = (MeasurementBean) element;
	       return mb.getSoftwareName().startsWith(txt_Check.getText());
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
			MeasurementBean mb1 =  (MeasurementBean)e1;
			MeasurementBean mb2 =  (MeasurementBean)e2;
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
				String mv1 = mb1.getMv();
				String mv2 = mb2.getMv();
				return mv1.compareTo(mv2);
			}
			case -MV:{
				String mv1 = mb1.getMv();
				String mv2 = mb2.getMv();
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
				String ps1 = mb1.getSoftwareName();
				String ps2 = mb2.getSoftwareName();
				return ps1.compareTo(ps2);
			}
			case -PN:{
				String ps1 = mb1.getSoftwareName();
				String ps2 = mb2.getSoftwareName();
				return ps2.compareTo(ps1);
			}
			}
			return 0;
		}
	}
	

	private void refresh() {
		loadData();
		viewer.refresh();

		int totalhere = 0;
		if (this.data_list.size() >0) {

			Table table = viewer.getTable();
			totalhere = table.getItemCount();
		}
		if(totalhere - old_total>=0){

		label_Tips.setText("刷新完成！共" + totalhere + "个完整性值，增加了"
				+ (totalhere - old_total) + "个！");
		old_total = totalhere;
		}
		else{
			label_Tips.setText("刷新完成！共" + totalhere + "个完整性值，减少了"
					+ (old_total-totalhere  ) + "个！");
			old_total = totalhere;
			}
		
		MessageBox mb = new MessageBox(shell, 0);
		mb.setText("刷新完成");
		mb.setMessage(label_Tips.getText());
		mb.open();

	}

	/**
	 * 获取最新进程数据
	 */
	private void loadData() {
		// this.data_list.clear();
		LibMeasure lm = new LibMeasure();
		lm.TM_getCurrentProcess();
		this.data_list = loader.getMeasurements("Active");
		// System.out.println("Size:"+data_list.size());
		total = data_list.size();
	}

	/**
	 * 将报警信息与白名单比较，得出表格中需要着色显示的项
	 */
	private void tableRefresh() {
		ArrayList w = new Loader().getWhiteList(); // 白名单
	/*	for (int i = 0; i < data_list.size(); i++) {
			MeasurementBean merr = (MeasurementBean) (data_list.get(i));
			int flag = 0;
			for (int k = 0; k < w.size(); k++) {
				MeasurementBean mw = (MeasurementBean) (w.get(k));
				if (merr.getMv().toUpperCase().equals(mw.getMv().toUpperCase())) {
					flag = 1;
					break;
				}
			}
			// System.out.print("flag: "+flag);
			if (flag == 0)
				table.getItems()[i].setBackground(color);
			flag = 0;
		}*/
 
		 for (int i = 0; i < table.getItemCount(); i++) {
			 TableItem item = table.getItem(i);
		
			 MeasurementBean bean =(MeasurementBean)item.getData();
		
				int flag = 0;
				for (int k = 0; k < w.size(); k++) {
					MeasurementBean mw = (MeasurementBean) (w.get(k));
		
					if (bean.getMv().toUpperCase().equals(mw.getMv().toUpperCase())) {
						flag = 1;
						break;
					}
				}
				if (flag == 0)
					table.getItems()[i].setBackground(color);
				flag = 0;
			
			}
			table.redraw();
		}


	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * The content provider of the tableViewer
	 * 
	 * @author yjyj
	 * 
	 */
	class TableViewerContentProvider implements IStructuredContentProvider {
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
		 * 设置数据源与列序号之间的对应关系； 若参数为String，则第一列显示其内容；否则显示MeasurementBean内容
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
				
				MeasurementBean mBean = (MeasurementBean) element;
				
				//切割路径获取软件名
				 /* String fName1 = mBean.getProgram_name().trim(); 
				  File tempFile =new File( fName1.trim());  
			      String fileName = tempFile.getName();
			      */
				switch (col) {
				case 0:
					return new Integer(mBean.getIndex()).toString();
				case 1:
					//return new Integer(mBean.getProcessID()).toString();
					//return fileName;
					return mBean.getSoftwareName();
				case 2:
					return mBean.getProgram_name();
				case 3:
					return mBean.getMv();
				case 4:
					return mBean.getTypeName();
				case 5:
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
}
