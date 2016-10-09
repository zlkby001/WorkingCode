package org.forms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

import ui.AlertLogMergerBean;
import ui.ErrorListBean;
import ui.Loader;
import ui.MeasurementBean;
import org.eclipse.swt.widgets.Text;

//add by vonwaist for alertlog merger
public class AlertLogMerger extends Dialog{
	
	public static int count = 0;
	protected Object result;
	protected Shell shell;
	private Table table;
	static String el_FilterPath = "C:\\TCC\\errorlist.log";
	private ArrayList error_list = new ArrayList();
	private ArrayList merger_list = new ArrayList();
	private TableViewer viewer; 
	private Loader loader = new Loader();
	private int total = 0;
	private Text text;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AlertLogMerger(Shell parent, int style) {
		super(parent, style);
		setText("Merger AlertLog");
		
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
		lblUsb.setBounds(200, 10, 250, 34);
		lblUsb.setText("报警日志归并界面");
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(26, 50, 660, 2);
		
		viewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		viewer.setContentProvider(new TableViewerContentProvider());
		viewer.setLabelProvider(new TableViewerLabelProvider());
		viewer.setInput(merger_list);				//===== 设置数据源 ======
		
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
		tblName.setWidth(200);
		tblName.setText("进程");
		
		TableColumn newColumnTableColumn_1 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_1.setAlignment(SWT.CENTER);
		newColumnTableColumn_1.setWidth(150);
		newColumnTableColumn_1.setText("开始时间");
		
		TableColumn newColumnTableColumn_2 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_2.setAlignment(SWT.CENTER);
		newColumnTableColumn_2.setWidth(150);
		newColumnTableColumn_2.setText("结束时间");
		
		TableColumn newColumnTableColumn_3 = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_3.setAlignment(SWT.CENTER);
		newColumnTableColumn_3.setWidth(50);
		newColumnTableColumn_3.setText("次数");
		
		//TableColumn tblclmnHash = new TableColumn(table, SWT.NONE);
		//tblclmnHash.setWidth(107);
		//tblclmnHash.setText("类型");
		
		//TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		//tableColumn.setWidth(273);
		//tableColumn.setText("描述信息");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//viewer.addFilter(new MyFilter());
				refresh();
				table.removeAll();
				viewer.refresh();
			}
		});
		btnNewButton.setBounds(255, 59, 72, 22);
		btnNewButton.setText("刷新");
		
		//text = new Text(shell, SWT.BORDER);
		//text.setBounds(345, 59, 105, 22);
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
	                    	AlertLogMergerBean mbean = (AlertLogMergerBean)item.getData();
                        	String fName1 = mbean.getProgramName().trim(); 
              			    File tempFile =new File( fName1.trim());  
              		        String fileName = tempFile.getName();
	                        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	                        StringSelection contents=new StringSelection(fileName+"  " + mbean.getMeasureHexValue() +
	                        		"  "+ mbean.getStarttime()+"  " + mbean.getEndtime()+"  "+mbean.getLog_nums());
	                        clipboard.setContents( contents,null); 
	                    
	                      
	                    } });  
	            }  
	          
		}); 

	
	}

	/*
	public class MyFilter extends ViewerFilter {
	    public boolean select(Viewer viewer, Object parentElement, Object element) {
	       MeasurementBean mb = (MeasurementBean) element;
	       String fName1 = mb.getProgram_name().trim(); 
			  File tempFile =new File( fName1.trim());  
		      String fileName = tempFile.getName();
	       return fileName.startsWith(text.getText());
	    
	    }
	}
	*/
	
   private void refresh() {		
		
		loadData();
		table.removeAll();
		viewer.refresh();
		if (this.merger_list.size() > 0) {

			Table table = viewer.getTable();
			total = table.getItemCount();			
		}
		
	}
   
	@SuppressWarnings({ "deprecation", "null" })
	private void loadData() {
		// this.data_list.clear();
		//this.data_list = loader.getMeasurements();
//		System.out.println("data_list size:"+ data_list.size());
		Comparator<ErrorListBean> comparator = new Comparator<ErrorListBean>(){
			   public int compare(ErrorListBean s1, ErrorListBean s2) {
			    //首先比较名字，如果相同则比较时间
				   int flag=s1.getProgramName().compareTo(s2.getProgramName());
				   if(flag==0){
				    return s1.getMeasuredate().compareTo(s2.getMeasuredate());
				   }else{
				    return flag;
				   }  
			    
			   }
		};
			  
		File efile = new File(el_FilterPath);

		BufferedReader br = null;
		if (error_list.size() != 0) {
			error_list.clear();
		}
		if (merger_list.size() != 0) {
			merger_list.clear();
		}
		
		try {
			
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
				Calendar  mdate = Calendar.getInstance(); 
				//mdate.clear();
				String mtyear = mt.substring(0, mp1);
				String mtmonth = mt.substring(mp1+1, mp2);
				String mtri = mt.substring(mp2+1,mp3);
				mdate.set(Integer.parseInt(mtyear), 
						Integer.parseInt(mtmonth),
						Integer.parseInt(mtri), 
						Integer.parseInt(mth), 
						Integer.parseInt(mtm), 
						Integer.parseInt(mts));
				//mdate.setYear(Integer.parseInt(mtyear));
				//mdate.setMonth(Integer.parseInt(mtmonth));
				//mdate.setDate(Integer.parseInt(mtri));
				//mdate.setHours(Integer.parseInt(mth));
				//mdate.setMinutes(Integer.parseInt(mtm));
				//mdate.setSeconds(Integer.parseInt(mts));
				
				
				ErrorListBean elb = new ErrorListBean();
				elb.setMeasuretime(mtnew);
				elb.setMeasureValue(mv);
				elb.setProgramName(pname);
				elb.setMeasuredate(mdate);
				//add 
				loader.getErrorlistItemDescription(elb);			
				
				error_list.add(0, elb);
				//error_list.
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
		
		//sort
		Collections.sort(error_list, comparator);
		//to AlertLogMergerBean
		int errlen = error_list.size();
		String oldfilename = " ";
		String starttime = "";
		int index = 0;
		int j = 0, nums = 0;
		for(int i =0; i < errlen; i++)
		{
			ErrorListBean temp = (ErrorListBean)error_list.get(i);
			//AlertLogMergerBean am = new AlertLogMergerBean();
			if(!temp.getProgramName().equals(oldfilename))
			{
				String pname = temp.getProgramName();
				oldfilename = pname;
				starttime = temp.getMeasuretime();
			}
			if(i==errlen -1)
			{//the last one
				AlertLogMergerBean am = new AlertLogMergerBean();
				am.setStarttime(starttime);
				am.setProgramName(temp.getProgramName());
				am.setMeasureHexValue(temp.getMeasureValue());
				am.setEndtime(temp.getMeasuretime());
				am.setLog_nums(nums+1);
				index+=1;
				am.setIndex(index);
				merger_list.add(0, am);
				nums = 0;	
				break;
			}
			ErrorListBean tempx = (ErrorListBean)error_list.get(i+1);
			if(tempx.getProgramName().equals(temp.getProgramName()))
			{
				nums++;
			}else
			{
				AlertLogMergerBean am = new AlertLogMergerBean();
				am.setStarttime(starttime);
				am.setProgramName(temp.getProgramName());
				am.setMeasureHexValue(temp.getMeasureValue());
				am.setEndtime(temp.getMeasuretime());
				am.setLog_nums(nums+1);
				index+=1;
				am.setIndex(index);
				merger_list.add(0, am);
				nums = 0;			
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

		
		 //设置数据源与列序号之间的对应关系；
		 //若参数为String，则第一列显示其内容；否则显示Bean内容		 
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
				AlertLogMergerBean ambean = (AlertLogMergerBean) element;
				
				switch (col) {
				case 0:
					return new Integer(ambean.getIndex()).toString();
				case 1:
					return ambean.getProgramName();
					//return fileName;
				case 2:
					return ambean.getStarttime();
				case 3:
					return ambean.getEndtime();
				case 4:					
					return new Integer(ambean.getLog_nums()).toString();
				//case 5:
				//	return mBean.getProcessDescription();		

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
