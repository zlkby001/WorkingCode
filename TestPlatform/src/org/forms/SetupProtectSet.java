package org.forms;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SetupProtectSet extends Dialog {

	protected Object result;
	protected static Shell shellProtect;
	private int x=0;
	private int y=0;
	public static int count=0;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 * 
	 * 
	 */
	public SetupProtectSet(final Shell parent, int style) {
		super(parent, style);	
		SetupProtectSet.count++;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shellProtect.open();
		shellProtect.layout();
		Display display = getParent().getDisplay();
		while (!shellProtect.isDisposed()) {
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
		shellProtect = new Shell(getParent(), getStyle());
//		shellProtect.setSize(457, 300);
		shellProtect.setText("ProtectSet");
		shellProtect.setBounds(300,300,500,300);
		shellProtect.setVisible(true);
		
		Label lbl_Title = new Label(shellProtect, SWT.NONE);
		lbl_Title.setFont(SWTResourceManager.getFont("黑体", 16, SWT.NORMAL));
		lbl_Title.setBounds(20, 20, 142, 21);
		lbl_Title.setText("保护功能设置");
		
		Label lbl_Separator = new Label(shellProtect, SWT.SEPARATOR|SWT.HORIZONTAL);
		lbl_Separator.setBounds(10, 51, 417, 10);
		lbl_Separator.setText("New ");
		
		Group group_1 = new Group(shellProtect, SWT.NONE);
		group_1.setBounds(10, 62, 417, 85);
		
		Label lbl_ProtectStatus = new Label(group_1, SWT.NONE);
		lbl_ProtectStatus.setBounds(55, 36, 114, 31);
		lbl_ProtectStatus.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lbl_ProtectStatus.setText("非保护状态");
		
		Button btn_Switch = new Button(group_1, SWT.NONE);
		btn_Switch.setBounds(259, 29, 114, 37);
		btn_Switch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox =   
				   new MessageBox(shellProtect,   
				    SWT.OK| 
				    SWT.ICON_INFORMATION);   
				 messageBox.setMessage("设置成功!");   
				 messageBox.open();  
			}
		});
		btn_Switch.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		btn_Switch.setText("打开/关闭");
		
		Group group_2 = new Group(shellProtect, SWT.NONE);
		group_2.setBounds(10, 153, 417, 92);
		
		Label lbl_WhiteListStatus = new Label(group_2, SWT.NONE);
		lbl_WhiteListStatus.setBounds(37, 29, 114, 52);
		lbl_WhiteListStatus.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lbl_WhiteListStatus.setText("白名单已过期\n最新版本为5.0.1");
		
		Button button_Download = new Button(group_2, SWT.NONE);
		button_Download.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		button_Download.setBounds(259, 20, 114, 40);
		button_Download.setText("白名单下载");
		
		Button btn_Close = new Button(shellProtect, SWT.NONE);
		btn_Close.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellProtect.close();
				SetupProtectSet.count--;
				
			}
		});
		btn_Close.setBounds(420, 10, 44, 21);
		btn_Close.setText("关闭");
		
		//添加窗体鼠标事件
		shellProtect.addMouseListener(new MouseListener(){

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
		shellProtect.addMouseMoveListener(new MouseMoveListener(){
			@Override
			public void mouseMove(MouseEvent e) {
				// TODO Auto-generated method stub
				if(x>0)
				{
					shellProtect.setLocation(e.x-x + shellProtect.getLocation().x,
						e.y-y + shellProtect.getLocation().y);
				}
			}
			
		});

	}
	
	/**
	 * 自定义SetVisible方法
	 */	
	public void SetVisible(boolean v){
		if(v==true)
			this.SetVisible(true);
		else
			this.SetVisible(false);
	}
}
