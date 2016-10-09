package org.forms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;






public class countDownShell extends Dialog{
	
	protected Object result;
	protected Shell shell;
	protected Display display;
	protected TableViewer viewer;
	private ArrayList dll_list = new ArrayList();
	public int minute = 0;
	public int second = 0;
	private Thread time = null;
	private Label lblNewLabel = null;
	//private Label lblNewLabel_1 = null;
	public TimRefresh timer = null;
	public Button btnNewButton = null;


	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public countDownShell(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
		
		timer = new TimRefresh();
		timer.start();
		
		//new TimeRefresh().start();
	}
	

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		display = getParent().getDisplay();
		Rectangle displayBounds = display.getPrimaryMonitor().getBounds();
		Rectangle shellBounds = shell.getBounds();
		int x = displayBounds.x + (displayBounds.width - shellBounds.width) >> 1;
		int y = displayBounds.y + (displayBounds.height - shellBounds.height) >> 1;
		shell.setLocation(x, y);
						
		shell.open();
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		
		return result;
	}

	/*public void close() {		
		shell.close();
		time.interrupt();
		time.stop();
		//display.close();
		
	}*/
	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.NONE);
		shell.setSize(340, 107);
		shell.setText(getText());
		
		lblNewLabel = new Label(shell, SWT.CENTER);
		lblNewLabel.setFont(SWTResourceManager.getFont("楷体_GB2312", 14, SWT.BOLD));
		lblNewLabel.setBounds(22, 10, 307, 24);
		lblNewLabel.setText("登陆次数过多，请5分钟后登陆");
		

		
		
		btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setEnabled(true);
		btnNewButton.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		btnNewButton.setBounds(124, 69, 82, 26);
		btnNewButton.setText("确 定");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				shell.close();
				
			}
		});		
	}
	
	
	/**
	 * 新建线程，用于刷新数据
	 * 
	 * @author tcwg
	 * 
	 */
	class TimRefresh extends Thread {
		int i = 0;
		public void run() {
			while(i<300) {
				try {
					sleep(1000);
					i++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			
				System.out.println("i:"+i);
				
//				
//				shell.getDisplay().syncExec(new Runnable() {
//					public void run() {
//						
//						//if(i==9)
//							//btnOK.setEnabled(true);
//					
//					}
//				});
				
			}
			if(i>=300){
				MainForm.count=0;
			}
		}
	}
	
			

	

}
