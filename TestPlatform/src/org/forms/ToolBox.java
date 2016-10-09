package org.forms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
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

public class ToolBox extends Composite {

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
	private String imagePath = MainForm.imagePath;
	MessageBox mb;
	static Label dwl_state;
	private Table table_1;
	private TableViewer viewer;
	//final Button start_re;
	private Button recovery;
	private Button btn_safestorage;
	public static Button cmm_button;
	public static Label mod_label;
	private Button start_button;
	private Button set_wl;
	private Button obtain_reh;
	private Button btn_Install;
//
//	/**
//	 * Create the composite.
//	 * 
//	 * @param parent
//	 * @param style
//	 */
	public ToolBox(Composite parent, int style) {
		super(parent, style);
		this.setBounds(0,110,900,500);
		this.setVisible(false);	
		shell = new Shell();
	
		Button knowledge = new Button(this, SWT.NONE);
		knowledge.setBounds(100, 55, 191, 185);
		knowledge.setText("knowledge");
		knowledge.setImage(SWTResourceManager.getImage(imagePath + "knowledge.png"));
		shell = (Shell) parent;
		knowledge.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(MainFrame.count==0){
					MainFrame re = new MainFrame(shell, 0);
					
					re.open();
					
				}

				}
			});
		
		//储存保护
		btn_safestorage = new Button(this, SWT.FLAT);
		btn_safestorage.setImage(SWTResourceManager.getImage(imagePath + "errorreport.png"));
		btn_safestorage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(PreAlertlog.count==0){
					PreAlertlog pa = new PreAlertlog(shell, 0);
					
					pa.createContents();
					
				}
//				if(mainDlg.count == 0){	
////				mainDlg md = new mainDlg(shell,0);
////				md.open();
////			}
//				mainDlg md = new mainDlg(shell, 0);
//				
//				md.store();
//			}
			}
		});
		btn_safestorage.setBounds(380, 55, 191, 188);
		btn_safestorage.setText("New Button");
//		//平台完整性保护
//		Button btn_platprotect = new Button(this, SWT.FLAT);
//		btn_platprotect.addSelectionListener(new SelectionAdapter() {
		
//			public void widgetSelected(SelectionEvent arg0) {
//				if(PlatformProt.count == 0){	
//					PlatformProt pfp = new PlatformProt(shell, 0);
//					
//					pfp.createContents();
//				}
//			}
//		});
//		btn_platprotect.setText("platprotect");
//		btn_platprotect.setImage(SWTResourceManager.getImage("images\\platprotect.png"));
//		btn_platprotect.setBounds(655, 55, 191, 185);
		
//		Button check = new Button(this, SWT.NONE);
//		check.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent arg0) {
//				if(SubUpload.count == 0){	
//					Match inst = new Match(shell,0);
//					inst.open();
//				}
//				TimeRefresh tr = new TimeRefresh();
//				sysTimeRefresh str =new sysTimeRefresh();
//				tr.start();
//				str.start();
				
				
//			}
//		});
//		check.setBounds(100, 312, 191, 185);
//		check.setText("check");
//		check.setImage(SWTResourceManager.getImage(imagePath + "match.png"));
//

	}

}
