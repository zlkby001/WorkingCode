package org.forms;

import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UpdateWhiteList extends Dialog {

	protected Object result;
	protected Shell shell;
	public static int count=0;
	private int x=0;		//记录指针X坐标
	private int y=0;		//记录指针Y坐标

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public UpdateWhiteList(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
		
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
		shell = new Shell(getParent(), getStyle());
//		shell.setSize(741, 501);
		shell.setText(getText());
		shell.setBounds(200,230,800,500);
	
		
		Table table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 76, 704, 386);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tbCol_Time = new TableColumn(table, SWT.NONE);
		tbCol_Time.setWidth(65);
		tbCol_Time.setText("序号");		
		
		TableColumn tbCol_Path = new TableColumn(table, SWT.NONE);
		tbCol_Path.setWidth(157);
		tbCol_Path.setText("进程路径");	
		
		TableColumn tbCol_Info = new TableColumn(table, SWT.NONE);
		tbCol_Info.setWidth(100);
		tbCol_Info.setText("描述信息");
		
		TableColumn tbCol_Type = new TableColumn(table, SWT.NONE);
		tbCol_Type.setWidth(109);
		tbCol_Type.setText("类型");
		
		TableColumn tbCol_Hash = new TableColumn(table, SWT.NONE);
		tbCol_Hash.setWidth(184);
		tbCol_Hash.setText("Hash值");
		
		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText(new String[] {"", "c:\\notepad.exe", "记事本", "非工控软件", "5SDG4815ER15GDSFS"});
		
		TableItem tableItem_1 = new TableItem(table, SWT.NONE);
		tableItem_1.setText(new String[] {"", "d:\\Program Files\\QQ.exe", "腾讯QQ", "非工控软件", "SD1G8ER1G516DG12E65"});
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(80);
		tableColumn.setText("是否新增");
		
		Label lbl_1 = new Label(shell, SWT.NONE);
		lbl_1.setFont(SWTResourceManager.getFont("黑体", 16, SWT.NORMAL));
		lbl_1.setBounds(23, 48, 126, 22);
		lbl_1.setText("白名单列表");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(639, 29, 81, 32);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				parent.setVisible(false);
				shell.close();
				count--;
			}
		});
		
		btnNewButton.setText("确定");

	
	
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

}
