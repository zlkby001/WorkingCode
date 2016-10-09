package org.forms;
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




public class Waiting extends Dialog {

	protected Object result;
	protected Shell shell;
	protected Display display;
	
	public int minute = 0;
	public int second = 0;
	private Thread time = null;
	public Label lblNewLabel = null;
	private Label lblNewLabel_1 = null;
	public ProgressBar prgBar = null;
	public Button btnNewButton = null;
	public TimeRefresh timer = null;
	public ProgressRefresh prorefresh = null;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Waiting(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
		timer = new TimeRefresh();
		timer.start();
		prorefresh = new ProgressRefresh();
		prorefresh.start();
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
		/*
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}*/
		
		
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
		lblNewLabel.setText("\u77E5\u8BC6\u5E93\u6B63\u5728\u6536\u96C6\u4E2D\uFF0C\u8BF7\u7A0D\u7B49.....");
		
		prgBar = new ProgressBar(shell, SWT.NONE);
		prgBar.setBounds(22, 40, 203, 16);
		prgBar.setMinimum(0);  
		prgBar.setMaximum(100);
		
		lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("楷体_GB2312", 12, SWT.BOLD));
		lblNewLabel_1.setAlignment(SWT.CENTER);
		lblNewLabel_1.setBounds(224, 40, 105, 24);
		lblNewLabel_1.setText("\u7528\u65F6 00:00");
		
		btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setEnabled(false);
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
	
	public static String unitFormat(int i){
		String retStr = null;
		if(i>=0 && i<10)
			retStr = "0" + i;
		else
			retStr = "" + i;
		return retStr;
	}
	
	/**
	 * 新建线程，用于刷新数据
	 * 
	 * @author tcwg
	 * 
	 */
	class TimeRefresh extends Thread {
		int i = 0;
		public void run() {
			while(true) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (second == 59){
					minute++;
					second = 0;
				}
				else{
					second ++;	
				}
				
				shell.getDisplay().syncExec(new Runnable() {
					public void run() {
						lblNewLabel_1.setText("用时 " + unitFormat(minute) + ":" + unitFormat(second));
						//if(i==9)
							//btnOK.setEnabled(true);
						shell.redraw();
					}
				});
				
			}
		}
	}
	
	/**
	 * 新建线程，用于刷新进度
	 * 
	 * @author tcwg
	 * 
	 */
	class ProgressRefresh extends Thread{
		int num = 0;
		public void run() {
			while(true) {
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				shell.getDisplay().syncExec(new Runnable() {
					public void run() {
						prgBar.setSelection(num);
						
						shell.redraw();
					}
				});
				num+=10;
				if(num >= 100){
					num =0;
				}
			}	
		}
		
	}
	

}
