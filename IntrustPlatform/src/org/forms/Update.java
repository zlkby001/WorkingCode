package org.forms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import ui.Downloader;
import ui.LibMeasure;
import ui.PTM_connect;
import ui.ProcessInfoBean;
import ui.ServerIPaddress;
import ui.TCCUtilit;

import Integrity.EncodeUtil;
import Integrity.PlatformMeasureBean;

import com.sun.jna.ptr.IntByReference;
import org.eclipse.wb.swt.SWTResourceManager;

public class Update extends Composite {

	protected Shell shell;
	static Composite page;
	static String cppath = "C:\\TCC\\process.log";//"C:\\windows\\process.log";// 正常进程的文件路径
	static String kppath = "C:\\TCC\\killed.log";//"C:\\windows\\killed.log";// 已杀掉进程的文件路径
	static String kphistoryPath = "C:\\TCC\\killedall.log";//"C:\\windows\\killedall.log";// 所有杀掉进程历史记录的文件路径
	private ArrayList cp_list = new ArrayList(); // 活动进程
	private ArrayList kp_list = new ArrayList(); // 杀死进程
	ProgressBar prgBar;

	private Downloader downloader = new Downloader();
	private TCCUtilit utilit = new TCCUtilit();
	static String wl_FilterPath = "C:\\TCC\\wl";//"C:\\wl";// 默认白名单路径

	MessageBox mb;
	static Label dwl_state;
	private Table table_1;
	private TableViewer viewer;
	final Button start_re;
	public static Button cmm_button;
	public static Label mod_label;
	private Button start_button;
	private Button set_wl;
	private Button obtain_reh;
	private Button btn_Install;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Update(Composite parent, int style) {
		super(parent, style);
		setBounds(0, 110, 900, 500);
		setVisible(false);
		shell = (Shell) parent;

		GenUI(this);

		// step 2---更新白名单
		final Group group2 = new Group(page, SWT.NONE);
		group2.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		group2.setLocation(53, 119);
		group2.setSize(780, 92);
		group2.setText("第二步：请先安装需要更新的软件，然后重新进行平台证明，通知管理员制定新白名单后，再执行更新操作。");

		start_button = new Button(group2, SWT.PUSH);
		start_button.setEnabled(false);
		start_button.setBounds(21, 29, 89, 53);
		start_button.setText("白名单下载");

		start_button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
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

								res = downloader.download_whitelist(ip, port,
										dwl_state, prgBar, PTM_connect.pubkey,
										wl_FilterPath);

							} catch (NumberFormatException nfe) {
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
					set_wl.setEnabled(true);
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
					set_wl.setEnabled(true);
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
				res = 0;
				System.out.println(wl_FilterPath);

				if ((res == 0 || res == 1001)) {

					mb.setMessage("白名单下载成功");
					dwl_state.setText("白名单下载成功");
					dwl_state.update();

				}
				mb.open();
			}
		});

		dwl_state = new Label(group2, SWT.HORIZONTAL | SWT.CENTER | SWT.BORDER);
		dwl_state.setBounds(130, 60, 619, 22);
		// final GridData gd_dwl_state = new GridData(SWT.CENTER, SWT.CENTER,
		// false, true);
		// gd_dwl_state.widthHint = 400;
		// gd_dwl_state.heightHint = 33;
		// dwl_state.setLayoutData(gd_dwl_state);
		dwl_state.setText("等待下载");

		prgBar = new ProgressBar(group2, SWT.NONE);
		prgBar.setLocation(130, 27);
		prgBar.setSize(619, 27);

		// Step 4---终端修复
		final Group group4 = new Group(page, SWT.NONE);
		group4.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		group4.setBounds(53, 288, 780, 203);
		group4.setText("第四步：终端修复");
		// final GridLayout gridLayout_re= new GridLayout();
		// gridLayout_re.numColumns = 3;
		// group4.setLayout(gridLayout_re);

		start_re = new Button(group4, SWT.PUSH);
		start_re.setBounds(199, 169, 109, 32);
		// gb_set_wl.horizontalSpan=1;
		// start_re.setLayoutData(gb_set_re);
		start_re.setText("修  复");
		start_re.setEnabled(false);

		obtain_reh = new Button(group4, SWT.PUSH);
		//obtain_reh.setEnabled(false);
		obtain_reh.setBounds(403, 169, 100, 32);
		obtain_reh.setText("修复历史查询");

		viewer = new TableViewer(group4, SWT.BORDER | SWT.FULL_SELECTION);
		table_1 = viewer.getTable();
		// table_1.setToolTipText("000");
		// table_1.setSelection(1);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		table_1.setBounds(21, 27, 729, 136);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		TableColumn tableColumn = tableViewerColumn.getColumn();
		tableColumn.setWidth(119);
		tableColumn.setText("序号");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(viewer,
				SWT.NONE);
		TableColumn tableColumn_1 = tableViewerColumn_1.getColumn();
		tableColumn_1.setWidth(358);
		tableColumn_1.setText("进程路径");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(viewer,
				SWT.NONE);
		TableColumn tblclmnid = tableViewerColumn_2.getColumn();
		tblclmnid.setWidth(167);
		tblclmnid.setText("进程ID");

		// 设置内容器
		viewer.setContentProvider(new ContentProvider());
		// 设置标签器
		viewer.setLabelProvider(new TableLabelProvider());

		// 修复按扭
		start_re.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				LibMeasure lm = new LibMeasure();

				int ret = lm.TM_getCurrentMode();
				if (ret != 11) {
					mb.setMessage("请先设置白名单并开启白名单模式!");
					mb.open();
					return;
				}

				ret = lm.TM_startRepair();
				switch (ret) {
				case 0:
					mb.setMessage("Repair OK!");
					break;
				case 1:
					mb.setMessage("internal error of libmeasure!");
					break;
				case 101:
					mb.setMessage("error of calling libmeasure!");
					break;
				default:
					mb.setMessage("未知错误:" + ret);
				}
				if (ret == 0) {
					kp_loadData();
					mb.setMessage(mb.getMessage() + " " + (kp_list.size())
							+ "个活动进程已经被杀掉！");
				}

				mb.open();
				start_re.setEnabled(false);//add
			}
		});

		// 查询修复历史记录
		obtain_reh.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				{
					kphistory_loadData();
					mb.setMessage("修复共" + (kp_list.size()) + "个历史记录");
				}

				viewer.setInput(kp_list);
				table_1.removeAll();
				viewer.refresh();
				mb.open();
			}
		});

	}

	private void cp_loadData() {
		File efile = new File(cppath);
		// FileReader fr = null;
		BufferedReader br = null;
		if (cp_list.size() != 0) {
			cp_list.clear();
		}
		try {
			// fr = new FileReader(efile);
			// br = new BufferedReader(fr);
			// 注意，文件默然编码格式为ANSI,在简体中文系统下，ANSI 编码代表 GB2312 编码
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					efile), "GB2312"));

			String s = null;
			while ((s = br.readLine()) != null) {
				int p1 = s.indexOf(" ");
				int p2 = s.indexOf(" ", p1 + 1);

				int processID = Integer.parseInt(s.substring(0, p1));
				String path = s.substring(p1 + 1, p2);
				String mt = s.substring(p2 + 1);
				// 过滤掉两个系统进程
				if (path.equals("C:/WINDOWS/system32/smss.exe")
						|| path.equals("C:/WINDOWS/system32/csrss.exe"))
					continue;

				ProcessInfoBean pib = new ProcessInfoBean();
				pib.setMeasure(mt);
				pib.setProcessID(processID);
				pib.setFileName(path);
				cp_list.add(pib);

			}

		} catch (FileNotFoundException e) {
			System.out.println("文件未找到：" + efile);
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
	}

	private void kp_loadData() {
		File efile = new File(kppath);
		// FileReader fr = null;
		BufferedReader br = null;
		if (kp_list.size() != 0) {
			kp_list.clear();
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

				int processID = Integer.parseInt(s.substring(0, p1));
				String path = s.substring(p1 + 1, p2);
				String mt = s.substring(p2 + 1);

				ProcessInfoBean pib = new ProcessInfoBean();
				pib.setMeasure(mt);
				pib.setProcessID(processID);
				pib.setFileName(path);
				kp_list.add(pib);
			}
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到：" + efile);
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
	}

	private void kphistory_loadData() {
		File efile = new File(kphistoryPath);
		// FileReader fr = null;
		BufferedReader br = null;
		if (kp_list.size() != 0) {
			kp_list.clear();
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

				int processID = Integer.parseInt(s.substring(0, p1));
				String path = s.substring(p1 + 1, p2);
				String mt = s.substring(p2 + 1);

				ProcessInfoBean pib = new ProcessInfoBean();
				pib.setMeasure(mt);
				pib.setProcessID(processID);
				pib.setFileName(path);
				kp_list.add(0, pib);
			}
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到：" + efile);
		} catch (IOException ioe) {
			System.out.println("文件读取错误：" + efile);
		} finally {
			try {
				if (br != null) {
					br.close();
					br = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Composite GenUI(Composite parentCom) {
		mb = new MessageBox(shell, SWT.ICON_INFORMATION);

		page = new Composite(parentCom, SWT.NONE);
		page.setBounds(0, 0, 900, 518);
		// page.setLayout (new GridLayout());

		// Step 1---关闭白名单模式
		final Group group1 = new Group(page, SWT.NONE);
		group1.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		group1.setBounds(53, 10, 780, 92);
		group1.setText("第一步：关闭白名单模式");

		cmm_button = new Button(group1, SWT.PUSH);
		cmm_button.setBounds(21, 34, 84, 38);
		cmm_button.setText("关闭");

		mod_label = new Label(group1, SWT.HORIZONTAL | SWT.CENTER | SWT.BORDER);
		mod_label.setBounds(127, 34, 515, 38);
		final GridData gd_mod_label = new GridData(SWT.CENTER, SWT.CENTER,
				false, true);
		gd_mod_label.widthHint = 400;
		gd_mod_label.heightHint = 33;

		Color color1 = new Color(shell.getDisplay(), 255, 255, 159);
		mod_label.setBackground(color1);
		Color color2 = new Color(shell.getDisplay(), 120, 42, 105);
		mod_label.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		FontData newFontData = mod_label.getFont().getFontData()[0];
		newFontData.setStyle(SWT.BOLD);
		newFontData.setHeight(14);
		Font newFont = new Font(shell.getDisplay(), newFontData);
		mod_label.setFont(newFont);

		btn_Install = new Button(group1, SWT.NONE);
//		btn_Install.setEnabled(false);
		btn_Install.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				InstallingSoftware ins = new InstallingSoftware(shell, 0);
				ins.open();
				start_button.setEnabled(true);
			}
		});
		btn_Install.setText("安装软件");
		btn_Install.setBounds(666, 34, 84, 38);
		LibMeasure lms = new LibMeasure();
		if (lms.TM_getCurrentMode() == 11) {
			LibMeasure.whitemode = 1;
			// cmm_button.setEnabled(true);
			mod_label.setText("白名单模式");
		} else if (lms.TM_getCurrentMode() == 10) {
			LibMeasure.whitemode = 0;
			// cmm_button.setEnabled(false);
			mod_label.setText("普通模式");
		} else {
			mod_label.setText("未知模式,Error Code:" + lms.TM_getCurrentMode());
		}

		// Step 3---重启白名单模式
		final Group group3 = new Group(page, SWT.NONE);
		group3.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		group3.setBounds(53, 217, 780, 65);
		group3.setText("第三步：重启白名单模式");

		set_wl = new Button(group3, SWT.PUSH);
		set_wl.setEnabled(false);
		set_wl.setBounds(279, 22, 107, 33);
		set_wl.setText("重启管控");

		if (lms.TM_getCurrentMode() == 11) {
			LibMeasure.whitemode = 1;
			// set_wl.setEnabled(false);
			mod_label.setText("白名单模式");
		} else if (lms.TM_getCurrentMode() == 10) {
			LibMeasure.whitemode = 0;
			// set_wl.setEnabled(true);
			mod_label.setText("普通模式");
		} else {
			mod_label.setText("未知模式,Error Code:" + lms.TM_getCurrentMode());
		}

		set_wl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				if (addctrlnum()==false)
				{
					mb.setMessage("本功能试用结束，请获取授权后再使用!");
					mb.open();
					return;
				}
				
				
				Password pswd = new Password(shell, 0);
				pswd.open();
				if(!(pswd.isTrue)){
					return;					
				}
				
				
				LibMeasure lm = new LibMeasure();
				IntByReference count = new IntByReference();
				lm.TM_clearWhiteList();// 先清空白名单

				int ret = lm.TM_setWhiteList(wl_FilterPath, count);
				switch (ret) {
				case 0:
					mb.setMessage("setWhiteList OK!");
					mod_label.setText("设置白名单成功！");
					mod_label.update();
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
				ret = lm.TM_startWhiteMode();// 开启白名单模式
				if (ret == 0) {
					mod_label.setText(mod_label.getText() + "白名单模式已经开启！");
					mod_label.update();
					LibMeasure.whitemode = 1;
					Methods.updateWidgetsMode(); // 修改相关控件内容
					// obtain_reh.setEnabled(true);
					cmm_button.setEnabled(true);
					obtain_reh.setEnabled(true);
					start_button.setEnabled(false);
				}

				lm.TM_getCurrentProcess();
				cp_loadData();
				viewer.setInput(processCompare(wl_FilterPath, cp_list));
				table_1.removeAll();
				viewer.refresh();
				if (table_1.getItemCount() > 0) {
					start_re.setEnabled(true);
					Methods.Alert("发现 " + table_1.getItemCount()
							+ " 个活动进程不在白名单！");
				} else
					start_re.setEnabled(false);

				mb.open();
			}
		});
		final GridData gb_set_re = new GridData(SWT.CENTER, SWT.CENTER, true,
				true);
		gb_set_re.widthHint = 80;
		gb_set_re.heightHint = 40;

		// 开启普通模式，即关闭白名单模式
		cmm_button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Password pswd = new Password(shell, 0);
				pswd.open();
				if(!(pswd.isTrue)){
					return;					
				}
				
				
				cmm_button.setEnabled(false);
				set_wl.setEnabled(true);
				LibMeasure lm = new LibMeasure();
				// if (lm.TM_getCurrentMode() == 10) {
				// Methods.Alert("管控已经关闭！");
				// }

				int ret = lm.TM_startNormalMode();
				switch (ret) {
				case 0:
					mb.setMessage("白名单模式已经关闭！");
					mod_label.setText("白名单模式已经关闭！");
					mod_label.update();
					LibMeasure.whitemode = 0;
					Methods.updateWidgetsMode(); // 修改相关控件内容
					cmm_button.setEnabled(false);
					start_button.setEnabled(false);
					set_wl.setEnabled(false);
					// obtain_reh.setEnabled(false);
//					btn_Install.setEnabled(true);

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
				mb.open();
			}
		});

		return page;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * 比较活动进程与白名单，获取报警进程
	 * 
	 * @param strFile
	 * @param pro
	 * @return
	 */
	private ArrayList processCompare(String strFile, ArrayList pro) {
		System.out.println("\nPro size:" + pro.size());
		ArrayList result = new ArrayList();
		File efile = new File(strFile);
		BufferedReader br = null;

		try {
			// fr = new FileReader(efile);
			// br = new BufferedReader(fr);
			// 注意，文件默然编码格式为ANSI,在简体中文系统下，ANSI 编码代表 GB2312 编码

			for (int i = 0; i < pro.size(); i++) { // 遍历活动进程
				ProcessInfoBean bean = null;
				String s = null;
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(efile), "GB2312"));

				while (true) { // 遍历白名单
					Object element = pro.get(i);
					if (element instanceof ProcessInfoBean) {
						bean = (ProcessInfoBean) element;
					}

					if ((s = br.readLine()) == null) {
						result.add(bean);
						// System.out.println("Null Line.."+i);
						break;
					}

					int p1 = s.indexOf(" ");
					String value = s.substring(0, p1); // 白名单中进程度量值
					if (bean != null
							&& (bean.getMeasure().toUpperCase()).equals(value
									.toUpperCase())) {
						// System.out.println("Match value.."+i);

						break; // 若度量值相同，则退出本次查找
					}

				} // end of while
			} // end of for
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到：" + efile);
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

		System.out.println("  result size:" + result.size());

		return result;

	}

	class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof java.util.List) {
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
			if (element instanceof ProcessInfoBean) {
				ProcessInfoBean bean = (ProcessInfoBean) element;
				if (columnIndex == 0) {
					return table_1.getItemCount() + "";
				} else if (columnIndex == 1) {
					return bean.getFileName();
				} else if (columnIndex == 2) {
					return bean.getProcessID() + "";
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

	public void disableButtons() {
		// cmm_button.setEnabled(true);
		start_button.setEnabled(false);
		set_wl.setEnabled(false);
		//obtain_reh.setEnabled(false);
//		btn_Install.setEnabled(false);
	}
	
	public boolean addctrlnum(){
		//保存信息
		if (MainForm.auhorized==true)
			return true;
		
		
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
