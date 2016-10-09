package org.forms;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import ui.Loader;
import ui.MeasurementBean;
import ui.TCCUtilit;
import ui.Verifier;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class ProcessVerifyFailList extends Dialog {

	protected Object result;
	protected Shell shell;	
	private TableViewer viewer;
	private ArrayList white_list;
	private Loader loader = new Loader();
	private Label lbl_ItemCount;
	private int x=0;
	private int y=0;
	public static int count=0;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ProcessVerifyFailList(Shell parent, int style) {
		super(parent, style);
		setText("失败列表");
		
		count++;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
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
		shell = new Shell(getParent(), SWT.RESIZE | SWT.TITLE);
		shell.setSize(690, 436);
		
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 690;
		int h = 436;
		shell.setBounds((int)(scrSize.width-w)/2,(int)(scrSize.height-h)/2,w, h);
		shell.setText(getText());

		
		loadData();
		
		viewer = new TableViewer(shell,SWT.FULL_SELECTION |SWT.MULTI |SWT.BORDER);
		
		final Table table = viewer.getTable();
		table.setBounds(23, 53, 638, 285);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setRedraw(true);
		
		TableColumn tbCol_Time = new TableColumn(table, SWT.NONE);
		tbCol_Time.setWidth(69);
		tbCol_Time.setText("序号");		
		
		TableColumn tbCol_Path = new TableColumn(table, SWT.NONE);
		tbCol_Path.setWidth(127);
		tbCol_Path.setText("进程");	
		
		TableColumn tbCol_Info = new TableColumn(table, SWT.NONE);
		tbCol_Info.setWidth(123);
		tbCol_Info.setText("Hash值");
		
		TableColumn tbCol_Type = new TableColumn(table, SWT.NONE);
		tbCol_Type.setWidth(92);
		tbCol_Type.setText("类型");
		
		TableColumn tbCol_Hash = new TableColumn(table, SWT.NONE);
		tbCol_Hash.setWidth(196);
		tbCol_Hash.setText("描述信息");
		
		viewer.setContentProvider(new TableViewerContentProvider());
		viewer.setLabelProvider(new TableViewerLabelProvider());
		viewer.setInput(white_list);				//===== 设置数据源 ======
		
		refresh();
		
		lbl_ItemCount = new Label(shell, SWT.NONE);
		lbl_ItemCount.setBounds(43, 355, 194, 21);
		lbl_ItemCount.setText("未通过验证进程： "+ table.getItemCount() +"个");
		
		Button button = new Button(shell, SWT.NONE);
		button.setBounds(309, 344, 88, 25);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				count--;
				shell.close();
			}
		});
		button.setText("确定");
		
		Label label = new Label(shell, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		label.setBounds(301, 10, 118, 21);
		label.setText("详细信息");
		
		Label label_1 = new Label(shell, SWT.SEPARATOR|SWT.HORIZONTAL);
		label_1.setBounds(10, 45, 664, 2);

		
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
	
	
	private void refresh() {
		loadData();
		viewer.refresh();
	}
	
	/**
	 * 获取最新进程数据
	 */
	private void loadData() {
		// this.data_list.clear();
		this.white_list = loader.getFailLog();
//		System.out.println("Size:"+white_list.size());

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
	
	
}
