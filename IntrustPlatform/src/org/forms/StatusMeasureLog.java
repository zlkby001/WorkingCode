package org.forms;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
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
import ui.Loader;
import ui.MeasurementBean;
import ui.PTM_connect;
import ui.ServerIPaddress;
import ui.TCCUtilit;
import ui.Verifier;
import org.eclipse.wb.swt.SWTResourceManager;

public class StatusMeasureLog extends Composite {

	private Shell shell;
	private Loader loader = new Loader();
	private Verifier verifier = new Verifier();
	private TableViewer viewer;
	private ArrayList data_list = new ArrayList();
	private TCCUtilit utilit = new TCCUtilit();

	private int total = 0;// 完整性个数
	private String comboText = "";
	private Group info_group;
	private Label label_Tips;
	public Thread thd_Refresh = new TimeRefresh();
	private Text text;
	private Text text_1;
	private String fileName;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public StatusMeasureLog(Composite parent, int style) {
		super(parent, style);

		this.setBounds(132, 0, 780, 600);
		this.setVisible(true);
		createContents();
		//new TimeRefresh().start();	
		
		refresh(); // 刷新，显示当前全部进程		
		thd_Refresh.start();	//启动刷新线程
		thd_Refresh.suspend();	//阻塞刷新线程
		
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = this.getShell();

		loadData();
		// up_composite.setVisible(false);
		final GridData gd_up_composite = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		gd_up_composite.heightHint = 46;
		gd_up_composite.minimumHeight = 100;
		gd_up_composite.widthHint = 418;
		final GridData gd_sever_ip_label = new GridData(SWT.FILL, SWT.CENTER,
				true, true);
		gd_sever_ip_label.widthHint = 30;
		final GridData gd_server_ip_text = new GridData(SWT.FILL, SWT.CENTER,
				true, true);
		gd_server_ip_text.heightHint = 19;
		final GridData component_verify_combo = new GridData(SWT.FILL,
				SWT.CENTER, true, true);
		component_verify_combo.widthHint = 30;
		// combo.setLayoutData(component_verify_combo);
		Set<String> ComboSet = getComboSet();
		// String[] items = new String[ComboSet.size()+1];
		// ComboSet.toArray(items);
		// items[ComboSet.size()] = " ALL";
		//		
		// Arrays.sort(items,String.CASE_INSENSITIVE_ORDER);
		String[] items1 = new String[ComboSet.size()];
		ComboSet.toArray(items1);

		Arrays.sort(items1, String.CASE_INSENSITIVE_ORDER);
		// String[] items = new String[ComboSet.size()+1];
		String[] items = new String[ComboSet.size() + 2];
		for (int i = 0; i < ComboSet.size(); i++)
			items[i] = items1[i];
		items[ComboSet.size()] = "ALL";
		items[ComboSet.size() + 1] = "";
		comboText = "All";
		final GridData gd_verify_button = new GridData(SWT.RIGHT, SWT.CENTER,
				true, true);
		gd_verify_button.heightHint = 30;
		gd_verify_button.minimumWidth = 60;
		gd_verify_button.minimumHeight = 30;
		final GridData gd_state_composite = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		gd_state_composite.heightHint = 40;
		gd_state_composite.minimumHeight = 100;
		gd_state_composite.widthHint = 418;
		
		final GridData gd_state_list = new GridData(SWT.FILL, SWT.CENTER, true,
				true);
		// gd_state_list.heightHint = 30;
		gd_state_list.widthHint = 400;
		// shell.setImage(new Image(Display.getDefault(),"res/ico.ico"));

		info_group = new Group(this, SWT.NONE);
		info_group.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		info_group.setText("进程度量");
		info_group.setBounds(0, 10, 807, 304);

		info_group.setRedraw(true);
		info_group.setLayout(null);
		viewer = new TableViewer(info_group,SWT.FULL_SELECTION |SWT.MULTI |SWT.BORDER);
		viewer.setContentProvider(new TableViewerContentProvider());
		viewer.setLabelProvider(new TableViewerLabelProvider());
		viewer.setInput(data_list);				//===== 设置数据源 ======

		final Table table = viewer.getTable();
		table.setBounds(10, 76, 783, 202);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setRedraw(true);

		final TableColumn newColumnTableColumn = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn.setResizable(true);
		newColumnTableColumn.setAlignment(SWT.CENTER);
		newColumnTableColumn.setWidth(50);
		newColumnTableColumn.setText("序号");
		newColumnTableColumn.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.INDEX_ASC:Sorter.INDEX_DESC);
				asc=!asc;
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
			}
		});

		final TableColumn newColumnTableColumn_1 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_1.setResizable(true);
		newColumnTableColumn_1.setWidth(151);
		newColumnTableColumn_1.setText("进程路径");
		

		final TableColumn newColumnTableColumn_2 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_2.setAlignment(SWT.CENTER);
		newColumnTableColumn_2.setWidth(164);
		newColumnTableColumn_2.setText("Hash值");
		newColumnTableColumn_2.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.MV_ASC:Sorter.MV_DESC);
				asc=!asc;
			}
		});
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnHash = new TableColumn(table, SWT.NONE);
		tblclmnHash.setWidth(107);
		tblclmnHash.setText("类型");
		tblclmnHash.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.TYPENAME_ASC:Sorter.TYPENAME_DESC);
				asc=!asc;
			}
		});

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(273);
		tableColumn.setText("描述信息");
		tableColumn.addSelectionListener(new SelectionAdapter() {
			boolean asc = true;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.setSorter(asc?Sorter.PS_ASC:Sorter.PS_DESC);
				asc=!asc;
			}
		});
		
		label_Tips = new Label(info_group, SWT.NONE);
		label_Tips.setBounds(20, 284, 372, 19);
		label_Tips.setText("全部进程数量："+table.getItemCount()+"个");
		
		final Label state_label = new Label(info_group, SWT.SINGLE);
		state_label.setBounds(137, 51, 525, 19);
		state_label.setAlignment(SWT.CENTER);		
		state_label.setText("实时验证状态显示，验证不正确时，可以点击左边的查看详情按钮！");
		
				final Button verify_button = new Button(info_group, SWT.NONE);
				verify_button.setBounds(10, 20, 102, 41);
				// verify_button.setLayoutData(gd_verify_button);
				verify_button.setText("验证");
				
				final ProgressBar prgBar = new ProgressBar(info_group,SWT.NONE);
				prgBar.setBounds(137, 20, 525, 25);
				
				//创建查询，排序，导出按钮以及相应的文本框
				Button btnNewButton = new Button(this, SWT.NONE);
				btnNewButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						//viewer.addFilter(new MyFilter());
						Queryresult qr = new Queryresult(shell,0);
						qr.open();
					}
				});
				btnNewButton.setBounds(450, 320, 72, 22);
				btnNewButton.setText("查询");
				
				/*text = new Text(this, SWT.BORDER);
				text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				text.setBounds(445, 320, 79, 21);
				text.setText("");*/
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
			                    	MeasurementBean mbean = (MeasurementBean)item.getData();
		                        	String fName1 = mbean.getProgram_name().trim(); 
		              			    File tempFile =new File( fName1.trim());  
		              		        fileName = tempFile.getName();
			                        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			                        StringSelection contents=new StringSelection(fileName+"  " + mbean.getMv() +
			                        		"  "+ mbean.getTypeName()+"  " + mbean.getProcessDescription());
			                        clipboard.setContents( contents,null); 
			                    
			                      
			                    } });  
			            }  
			          
				}); 
				Button btnNewButton_1 = new Button(this, SWT.NONE);
				btnNewButton_1.setBounds(708, 320, 72, 22);
				btnNewButton_1.setText("导出");
				btnNewButton_1.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						saveFile();
					}
				});
				
				final Button btviewerror = new Button(info_group, SWT.NONE);
				btviewerror.setBounds(690, 20, 103, 41);
				
						btviewerror.setText("未授权进程");
						btviewerror.setEnabled(false);
						
						Text txt_Sort = new Text(this, SWT.CENTER);
						txt_Sort.setEnabled(false);
						txt_Sort.setEditable(false);
						txt_Sort.setBounds(561, 325, 120, 22);
						//txt_Sort.setBackground(SWTResourceManager.getColor(236,233,216));
						txt_Sort.setText("点击每个表头进行排序");
						txt_Sort.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
								btviewerror.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent e) {// 查看验证失败详情			
										
										if(ProcessVerifyFailList.count==0){
											ProcessVerifyFailList plist = new ProcessVerifyFailList(shell,0);
											plist.open();	
											
										}
									}
								});
				
						verify_button.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {
								btviewerror.setEnabled(false);// 每次点验证时将查看详情按钮disable掉
//								ServerIPaddress.setIp(txt_ServerIP.getText());
//								ServerIPaddress.setPort(Methods.StringtoInt(txt_ServerPort.getText()));
//								Methods.updateCommunicationSet();
								
								int res = 0;
								String server_ip = ServerIPaddress.getIp();
								int server_port = ServerIPaddress.getPort();
								
									if (server_ip.equals("")||server_port==0)	//ip地址或者端口号为空
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
													String swcom = "ALL";
													System.out
															.println("the selected software component:"
																	+ swcom);
													res = verifier.verifiy(server_ip,server_port,
															 state_label,prgBar,swcom,
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
				String ps1 =mb1.getSoftwareName();
				String ps2 =mb2.getSoftwareName();
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
	
	//添加导出功能
	private void saveFile(){
		FileDialog dialog = new FileDialog(shell,SWT.SAVE);
		dialog.setFilterPath(System.getProperty("SystemRoot"));
		dialog.setFilterExtensions(new String[]{"*.txt","*.*"});
		dialog.setFilterNames(new String[]{"Text Files(*.txt}","All Files(*.*)"});
		String file = dialog.open();
		if(file!=null){
			System.out.print(file);
			 try {
				   FileWriter fileWriter = new FileWriter(file);
		            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		           for (int i = 0; i < data_list.size(); i++) {
		        	  MeasurementBean merr = (MeasurementBean) (data_list.get(i));
		        	  String fName1 = merr.getProgram_name().trim(); 
	    			    File tempFile =new File( fName1.trim());  
	    		        String fileName = tempFile.getName();
		        	  bufferedWriter.write(String.valueOf(fileName) + " ");
		        	  bufferedWriter.write(String.valueOf(merr.getProgram_name()) + " ");
		              bufferedWriter.write(String.valueOf(merr.getMv()) + " ");
		              bufferedWriter.write(String.valueOf(merr.getTypeName()) + " ");
		              bufferedWriter.write(String.valueOf(merr.getProcessDescription()) + " ");
		              bufferedWriter.newLine();  
		            }
		            bufferedWriter.close();
		            fileWriter.close(); 
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		}
		
	}

	
	
	//添加表格过滤器实现查找功能	
	public class MyFilter extends ViewerFilter {
	    public boolean select(Viewer viewer, Object parentElement, Object element) {
	   
	       MeasurementBean mb = (MeasurementBean) element;
	       String fName1 = mb.getProgram_name().trim(); 
			  File tempFile =new File( fName1.trim());  
		      fileName = tempFile.getName();
	       return fileName.startsWith(text.getText());
	    
	    }
	}

	protected void createTableViewer(Composite parent) {
		// gd_table.verticalIndent = 10;
		// gd_table.widthHint = 500;
		// gd_table.minimumHeight = 100;
		// gd_table.minimumWidth = 500;
		// gd_table.heightHint = 400;
		// GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 5,
		// 1);
		// gd_table.widthHint = 669;
		// gd_table.heightHint = 297;

		refresh();

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void refresh() {		
		
		loadData();
		viewer.refresh();
		if (this.data_list.size() > 0) {

			Table table = viewer.getTable();
			total = table.getItemCount();			
		}
		
	}

	private void refreshnew() {

		loadData();
		viewer.refresh();
		int totalhere = 0;
		if (this.data_list.size() > 0) {

			Table table = viewer.getTable();
			totalhere = table.getItemCount();			
		}
		if(totalhere - total>0){
			label_Tips.setText("刷新完成！共" + totalhere + "个完整性值，增加了" + (totalhere - total)
				+ "个！");
			total = totalhere;
		}

	}

	private void comboRefresh(String component){
		loadData(component);
		viewer.refresh();
		if (this.data_list.size() > 0) {

			Table table = viewer.getTable();
			// total=table.getItemCount();
			for (int i = 0, n = table.getColumnCount(); i < n; i++) {
				// table.getColumn(i).pack();
			}
		}
	}

	/**
	 * 获取最新进程数据，无参
	 */
	private void loadData() {
		// this.data_list.clear();
		this.data_list = loader.getMeasurements();
//		System.out.println("data_list size:"+ data_list.size());
		
	}

	/**
	 * 获取最新进程数据，参数为All或者Active
	 */
	private void loadData(String component) {
		// this.data_list.clear();
		this.data_list = loader.getMeasurements(component);

	}

	private Set getComboSet() {
		Set<String> comboSet = new HashSet();
		for (int i = 0; i < this.data_list.size(); i++) {
			MeasurementBean pmb = (MeasurementBean) this.data_list.get(i);
			if (pmb.getType() == 1)
				comboSet.add(pmb.getSoftwareName());
		}

		//System.out.println("the number of the combo is:" + comboSet.size());
		return comboSet;
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
				MeasurementBean mBean = (MeasurementBean) element;

				//切割路径获取软件名
				  String fName1 = mBean.getProgram_name().trim(); 
				  File tempFile =new File( fName1.trim());  
			      String fileName = tempFile.getName();
			      
				
				switch (col) {
				case 0:
					return new Integer(mBean.getIndex()).toString();
				case 1:
					return mBean.getSoftwareName();
					//return fileName;
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
						if ( comboText.equals("All")){
							refreshnew();
							//System.out.println("this is All");
						}
						viewer.setItemCount(data_list.size());
						
						//System.out.println(comboText + "  " + data_list.size());
					}
				});

			}
		}
	}
}
