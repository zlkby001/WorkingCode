package org.forms;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class InstallingSoftware extends Dialog {

	protected Object result;
	protected Shell shell;
	private Label label;
	private Button btnOK;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public InstallingSoftware(Shell parent, int style) {
		super(parent, style);
		setText("软件安装");

		new TimeRefresh().start();
	}

	/**
	 * Open the dialog.
	 * 
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
		shell = new Shell(getParent(), SWT.CLOSE | SWT.RESIZE | SWT.TITLE);
		shell.setSize(450, 300);
		shell.setText(getText());

		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 500;
		int h = 300;
		shell.setBounds((int) (scrSize.width - w) / 2,
				(int) (scrSize.height - h) / 2, w, h);

		
		final Group pgroup = new Group(shell,SWT.NONE);
		pgroup.setBounds(23, 66, 440, 170);
		
		label = new Label(pgroup, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("楷体_GB2312", 14, SWT.BOLD));
		label.setBounds(60, 41, 346, 34);
		label.setText("请安装软件，时间剩余 10 秒。。。");

		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		label_2.setBounds(201, 21, 100, 27);
		label_2.setText("安装软件");

		btnOK = new Button(pgroup, SWT.NONE);
		btnOK.setEnabled(false);
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ProcessMeasure pro = new ProcessMeasure(shell,0);
				pro.open();
				shell.close();
			}
		});
		btnOK.setBounds(168, 121, 100, 34);
		btnOK.setText("确定");

		Label label_3 = new Label(pgroup, SWT.NONE);
		label_3.setText("安装完成后，请运行软件各组件。");
		label_3.setFont(SWTResourceManager.getFont("楷体_GB2312", 14, SWT.NORMAL));
		label_3.setBounds(74, 81, 332, 34);
		
				Label label_1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
				label_1.setBounds(21, 41, 442, 21);

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
			for (; i < 10; i++) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				shell.getDisplay().syncExec(new Runnable() {
					public void run() {
						label.setText("请安装软件，时间剩余 " + (10 - i - 1) + " 秒。。。");
						if(i==9)
							btnOK.setEnabled(true);
						shell.redraw();
					}
				});
				
			}
		}
	}

}
