package org.forms;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SetupLogUpload extends Dialog {

	protected Object result;
	protected Shell shellLog;
	private Text txt_Time;
	private int x=0;		//记录指针X坐标
	private int y=0;		//记录指针Y坐标
	public static int count=0;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SetupLogUpload(final Shell parent, int style) {
		super(parent, style);
		count++;
		
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shellLog.open();
		shellLog.layout();
		Display display = getParent().getDisplay();
		while (!shellLog.isDisposed()) {
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
		shellLog = new Shell(getParent(), getStyle());
//		shellLog.setSize(500, 262);
		shellLog.setText("ConmunicationSet");
		shellLog.setBounds(300,300,500,300);
		shellLog.setVisible(true);
		
		Label lbl_Title = new Label(shellLog, SWT.NONE);
		lbl_Title.setFont(SWTResourceManager.getFont("黑体", 16, SWT.NORMAL));
		lbl_Title.setBounds(20, 20, 114, 21);
		lbl_Title.setText("日志上传");
		
		Button btn_ManualUpdate = new Button(shellLog, SWT.NONE);
		btn_ManualUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox =   
				   new MessageBox(shellLog,   
				    SWT.OK| 
				    SWT.ICON_INFORMATION);   
				 messageBox.setMessage("更新成功!");   
				 messageBox.open();  
			}
		});
		btn_ManualUpdate.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		btn_ManualUpdate.setBounds(171, 205, 94, 31);
		btn_ManualUpdate.setText("手动更新");
		
		Label lbl_Separator = new Label(shellLog, SWT.SEPARATOR|SWT.HORIZONTAL);
		lbl_Separator.setBounds(10, 51, 413, 10);
		lbl_Separator.setText("New ");
		
		Button btn_Close = new Button(shellLog, SWT.NONE);
		btn_Close.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellLog.close();
				count--;
			}
		});
		btn_Close.setBounds(396, 10, 42, 21);
		btn_Close.setText("关闭");
		
		Button ckbtn_AutoUpdate = new Button(shellLog, SWT.CHECK);
		ckbtn_AutoUpdate.setBounds(20, 67, 98, 17);
		ckbtn_AutoUpdate.setText("自动更新");
		
		Group group = new Group(shellLog, SWT.NONE);
		group.setBounds(20, 90, 403, 91);
		
		txt_Time = new Text(group, SWT.BORDER);
		txt_Time.setBounds(195, 34, 128, 31);
		
		Label lbl_SetTime = new Label(group, SWT.NONE);
		lbl_SetTime.setBounds(25, 35, 147, 31);
		lbl_SetTime.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		lbl_SetTime.setText("设置自动上传时间");
		
		//添加窗体鼠标事件
		shellLog.addMouseListener(new MouseListener(){

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
		shellLog.addMouseMoveListener(new MouseMoveListener(){
			@Override
			public void mouseMove(MouseEvent e) {
				// TODO Auto-generated method stub
				if(x>0)
				{
					shellLog.setLocation(e.x-x + shellLog.getLocation().x,
						e.y-y + shellLog.getLocation().y);
				}
			}
			
		});

	}
}
