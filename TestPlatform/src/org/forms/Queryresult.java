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
import java.awt.datatransfer.StringSelection;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
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
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.forms.ProcessMeasure.TableViewerContentProvider;
import org.forms.ProcessMeasure.TableViewerLabelProvider;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import Integrity.EncodeUtil;
import Integrity.PlatformMeasureBean;
import org.eclipse.wb.swt.SWTResourceManager;

import ui.Loader;
import ui.MeasurementBean;
import org.eclipse.swt.widgets.Text;

public class Queryresult extends Dialog{
	
	public static int count = 0;
	protected Object result;
	protected Shell shell;
	private Table table;
	private ArrayList data_list = new ArrayList();
	private TableViewer viewer; 
	private Loader loader = new Loader();
	private int total = 0;
	private Text text;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Queryresult(Shell parent, int style) {
		super(parent, style);
		setText("Queryresult Dialog");
		
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
		refresh();
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
		shell = new Shell(getParent(), SWT.CLOSE | SWT.TITLE);
		shell.setSize(720, 450);
		shell.setText(getText());
		int w = 720;
		int h = 450;
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setBounds((int)(scrSize.width-w)/2,(int)(scrSize.height-h)/2,w, h);
		shell.setVisible(true);
		
		loadData();
		
		Label lblUsb = new Label(shell, SWT.NONE);
		lblUsb.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		lblUsb.setBounds(294, 10, 111, 34);
		lblUsb.setText("查询界面");
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(26, 50, 660, 2);
		
		viewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		viewer.setContentProvider(new TableViewerContentProvider());
		viewer.setLabelProvider(new TableViewerLabelProvider());
		viewer.setInput(data_list);				//===== 设置数据源 ======
		
		table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(44, 87, 630, 291);
		
		TableColumn newColumnTableColumn = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn.setResizable(true);
		newColumnTableColumn.setAlignment(SWT.CENTER);
		newColumnTableColumn.setWidth(50);
		newColumnTableColumn.setText("序号");;
		
		TableColumn tblName = new TableColumn(table, SWT.CENTER);
		tblName.setResizable(true);
		tblName.setAlignment(SWT.CENTER);
		tblName.setWidth(107);
		tblName.setText("进程");
		
		TableColumn newColumnTableColumn_1 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_1.setResizable(true);
		newColumnTableColumn_1.setWidth(151);
		newColumnTableColumn_1.setText("进程路径");
		
		TableColumn newColumnTableColumn_2 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_2.setAlignment(SWT.CENTER);
		newColumnTableColumn_2.setWidth(164);
		newColumnTableColumn_2.setText("Hash值");
		
		TableColumn tblclmnHash = new TableColumn(table, SWT.NONE);
		tblclmnHash.setWidth(107);
		tblclmnHash.setText("类型");
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(273);
		tableColumn.setText("描述信息");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.addFilter(new MyFilter());
			}
		});
		btnNewButton.setBounds(255, 59, 72, 22);
		btnNewButton.setText("查询");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(345, 59, 105, 22);
		//viewer.addFilter(new MyFilter());
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
              		        String fileName = tempFile.getName();
	                        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	                        StringSelection contents=new StringSelection(fileName+"  " + mbean.getMv() +
	                        		"  "+ mbean.getTypeName()+"  " + mbean.getProcessDescription());
	                        clipboard.setContents( contents,null); 
	                    
	                      
	                    } });  
	            }  
	          
		}); 

	
	}
	
	public class MyFilter extends ViewerFilter {
	    public boolean select(Viewer viewer, Object parentElement, Object element) {
	       MeasurementBean mb = (MeasurementBean) element;
	       String fName1 = mb.getProgram_name().trim(); 
			  File tempFile =new File( fName1.trim());  
		      String fileName = tempFile.getName();
	       return fileName.startsWith(text.getText());
	    
	    }
	}
	
   private void refresh() {		
		
		loadData();
		viewer.refresh();
		if (this.data_list.size() > 0) {

			Table table = viewer.getTable();
			total = table.getItemCount();			
		}
		
	}
   
	private void loadData() {
		// this.data_list.clear();
		this.data_list = loader.getMeasurements();
//		System.out.println("data_list size:"+ data_list.size());
		
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
}
