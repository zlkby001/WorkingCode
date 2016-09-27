package org.forms;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import ui.ErrorListBean;
import ui.Loader;
import ui.MeasurementBean;
import ui.PTM_connect;
import ui.ServerIPaddress;
import ui.TCCUtilit;
import ui.Verifier;

public class ProcessMeasure extends Dialog {

	protected Object result;
	protected Shell shell;
	
	private Loader loader = new Loader();
	private Verifier verifier = new Verifier();
	private TableViewer viewer;
	private ArrayList data_list = new ArrayList();
	private TCCUtilit utilit = new TCCUtilit();

	private int total = 0;// 完整性个数
	private String comboText = "";
	private Group info_group;
	private Label label_Tips;
	private Color color;
	private  Table table;
	private Button btviewerror;
	private Label state_label;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ProcessMeasure(Shell parent, int style) {
		super(parent, style);
		setText("进程管理");
		shell = (Shell)parent;
		color = new Color(Display.getDefault(),new RGB(239,203,228));		
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		refresh(); // 刷新，显示当前全部进程		
		tableRefresh();
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
		shell = new Shell(getParent(), SWT.CLOSE | SWT.RESIZE | SWT.TITLE);
		shell.setSize(914, 546);
		shell.setText(getText());
		
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = shell.getSize().x;
		int h = shell.getSize().y;
		shell.setBounds((int) (scrSize.width - w) / 2,
				(int) (scrSize.height - h) / 2, w, h);
		
		Label label = new Label(shell, SWT.NONE);
		label.setText("进程度量");
		label.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		label.setBounds(397, 23, 114, 35);
		
		Label label_1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setText("New ");
		label_1.setBounds(36, 60, 814, 10);

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

		info_group = new Group(shell, SWT.NONE);
		info_group.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		info_group.setText("进程度量详情");
		info_group.setBounds(36, 132, 821, 372);

		info_group.setRedraw(true);
		info_group.setLayout(null);
		viewer = new TableViewer(info_group,SWT.FULL_SELECTION |SWT.MULTI |SWT.BORDER);
		viewer.setContentProvider(new TableViewerContentProvider());
		viewer.setLabelProvider(new TableViewerLabelProvider());
		viewer.setInput(data_list);				//===== 设置数据源 ======

		table = viewer.getTable();
		table.setBounds(10, 26, 801, 284);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setRedraw(true);

		final TableColumn newColumnTableColumn = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn.setResizable(true);
		newColumnTableColumn.setAlignment(SWT.CENTER);
		newColumnTableColumn.setWidth(50);
		newColumnTableColumn.setText("序号");

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

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnHash = new TableColumn(table, SWT.NONE);
		tblclmnHash.setWidth(119);
		tblclmnHash.setText("类型");

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(273);
		tableColumn.setText("描述信息");
		
		label_Tips = new Label(info_group, SWT.NONE);
		label_Tips.setBounds(20, 316, 249, 19);
		label_Tips.setText("全部进程数量："+table.getItemCount()+"个");
		
		Button button = new Button(info_group, SWT.NONE);
		button.setBounds(373, 316, 94, 31);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Methods.Alert("软件安装完成，请通知管理员验证最新软件信息！");
				shell.close();
			}
		});
		button.setText("确定");
		button.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		
		state_label = new Label(shell, SWT.SHADOW_IN);
		state_label.setText("实时验证状态显示，验证不正确时，可以点击左边的查看详情按钮！");
		state_label.setAlignment(SWT.CENTER);
		state_label.setBounds(173, 107, 525, 19);
		
		btviewerror = new Button(shell, SWT.NONE);
		btviewerror.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(ProcessVerifyFailList.count==0){
					ProcessVerifyFailList plist = new ProcessVerifyFailList(shell,0);
					plist.open();						
				}			
			}
		});
		btviewerror.setText("未授权进程");
		btviewerror.setEnabled(false);
		btviewerror.setBounds(726, 76, 103, 41);
		
		Button verify_button = new Button(shell, SWT.NONE);
		verify_button.setText("验证");
		verify_button.setBounds(46, 76, 102, 41);
		
		final ProgressBar prgBar = new ProgressBar(shell,SWT.NONE);
		prgBar.setBounds(173, 76, 525, 25);
		verify_button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btviewerror.setEnabled(false);// 每次点验证时将查看详情按钮disable掉
//				ServerIPaddress.setIp(txt_ServerIP.getText());
//				ServerIPaddress.setPort(Methods.StringtoInt(txt_ServerPort.getText()));
//				Methods.updateCommunicationSet();
				
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

	protected void createTableViewer(Composite parent) {
		
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
				
				switch (col) {
				case 0:
					return new Integer(mBean.getIndex()).toString();
				case 1:
					return mBean.getProgram_name();
				case 2:
					return mBean.getMv();
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
	 * 将度量信息与白名单比较，得出表格中需要着色显示的项
	 */
	private void tableRefresh() {
		ArrayList w = new Loader().getWhiteList(); // 白名单
		for (int i = 0; i < data_list.size(); i++) {
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
		}
		table.redraw();
	}
		
}
	
	
	