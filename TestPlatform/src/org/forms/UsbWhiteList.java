package org.forms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.forms.ProcessMeasure.TableViewerContentProvider;
import org.forms.ProcessMeasure.TableViewerLabelProvider;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import Integrity.EncodeUtil;
import Integrity.PlatformMeasureBean;
import org.eclipse.wb.swt.SWTResourceManager;

public class UsbWhiteList extends Dialog {
	
	public static int count = 0;
	protected Object result;
	protected Shell shell;
	private Table table;
	private ArrayList usblist;
	private String usbWlFile = "C:\\TCC\\usbwl";//"C:\\usbwl";
	private int x=0;
	private int y=0;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public UsbWhiteList(Shell parent, int style) {
		super(parent, style);
		setText("USB 白名单");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		count++;
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
		shell = new Shell(getParent(), SWT.TITLE);
		shell.setSize(558, 300);
		shell.setText(getText());
		int w = 720;
		int h = 454;
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setBounds((int)(scrSize.width-w)/2,(int)(scrSize.height-h)/2,w, h);
		shell.setVisible(true);
		
		usblist = getUsbList();
		
		Button btn_Exit = new Button(shell, SWT.FLAT);
		btn_Exit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				count--;
				shell.close();
			}
		});
		btn_Exit.setBounds(307, 374, 98, 35);
		btn_Exit.setText("退出");
		
		Label lblUsb = new Label(shell, SWT.NONE);
		lblUsb.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		lblUsb.setBounds(294, 10, 111, 21);
		lblUsb.setText("USB白名单");
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(26, 44, 660, 2);
		
		TableViewer viewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(68, 52, 581, 316);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(68);
		tblclmnNewColumn.setText("序号");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(200);
		tblclmnNewColumn_2.setText("厂商");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(200);
		tblclmnNewColumn_1.setText("序列号");
		
		viewer.setContentProvider(new TableViewerContentProvider());
		viewer.setLabelProvider(new TableViewerLabelProvider());
		viewer.setInput(usblist);				//===== 设置数据源 ======
		
		
		//添加窗体鼠标事件
		shell.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub				
				x=e.x;
				y=e.y;				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				x=y=-1;
			}
		});
		
		//鼠标移动事件
		shell.addMouseMoveListener(new MouseMoveListener(){
			@Override
			public void mouseMove(MouseEvent e) {
				// TODO Auto-generated method stub
				if(x>0)
				{
					shell.setLocation(e.x-x + shell.getLocation().x,
						e.y-y + shell.getLocation().y);
				}
			}
		});
	
	}
	
	
	
	/**
	 * 获取本地USB白名单
	 * @return
	 */
	private ArrayList getUsbList() {
		ArrayList list = new ArrayList();
		File mfile = new File(usbWlFile);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(mfile);
			br = new BufferedReader(fr);

			String s = null;
			int id = 1;
			while ((s = br.readLine()) != null) {
				int p = s.indexOf(" ");	
				String manu = s.substring(0,p);
				String sn = s.substring(p + 1, s.length());
				usbBean usb = new usbBean();
				usb.setId(id);
				usb.setManufacture(manu);
				usb.setSn(sn);
				
				list.add(usb);
				id++;
			}
			
			return list;
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到：" + mfile);
			return null;
		} catch (IOException ioe) {
			System.out.println("文件读取错误：" + mfile);
			return null;
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
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
		}

		@Override
		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
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
		 * 若参数为String，则第一列显示其内容；否则显示Bean内容		
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
				usbBean usb = (usbBean) element;				
				
				switch (col) {
				case 0:
					return usb.getId()+"";
				case 1:
					return usb.getManufacture();
				case 2:
					return usb.getSn();

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
