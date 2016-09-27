package org.forms;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import org.apache.axis2.addressing.AddressingConstants.Final;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;



public class Password extends Dialog{

	protected Object result;
	protected Shell shell;
	private int x=0;
	private int y=0;
	public static int count=0;
	
	private MessageBox mb;
    
	

	
	boolean isOpened = true;

	private Label inform;
	private Button btn_close;
	private Text textword;
	private Button btn_ok;
	
	//private static String pswd = "123";
	private static String pswd;
	public Boolean isTrue = false;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Password(Shell parent, int style) {
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
	/**
	 * 
	 */
	private void createContents() {
		
		shell = new Shell(getParent(), SWT.RESIZE | SWT.TITLE);
		shell.setSize(350, 146);
		//shell.setSize(712, 431);
		shell.setText("监控控制");
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 350;
		int h = 230;
		shell.setBounds((int)(scrSize.width-w)/2,(int)(scrSize.height-h)/2,w, h);
		shell.setVisible(true);
		
		mb = new MessageBox(shell.getShell());
		
		final Group pgroup = new Group(shell,SWT.NONE);
		pgroup.setBounds(21, 33, 302, 146);
		
		inform = new Label(shell, SWT.CENTER);
		inform.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		inform.setText("请输入口令");
		inform.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		inform.setBounds(0, 10, 354, 27);
		
		textword=new Text(pgroup,SWT.PASSWORD|SWT.BORDER|SWT.CENTER);//带边框
		textword.setBounds(51,36,200,25);
	    
		
		

		btn_ok = new Button(pgroup, SWT.FLAT);
		btn_ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String input = textword.getText();
				try {
					Class.forName("org.sqlite.JDBC");
					Connection con = DriverManager.getConnection("jdbc:sqlite://C:/TCC/user.db");
					con.setAutoCommit(false);
					Statement stat = con.createStatement();
					
					ResultSet rs = stat.executeQuery("SELECT * FROM userInfo;");
					if(input.equals(rs.getString("passWord"))){
						isTrue = true;
						shell.close();
						count--;
					}
					else{
						mb.setMessage("口令错误！");			
						mb.open();
					}
					con.commit();
					stat.close();
					con.close();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
							
			}
		});
		btn_ok.setText("确 定");
		btn_ok.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		btn_ok.setBounds(51, 101, 70, 30);
		
		
		btn_close = new Button(pgroup, SWT.FLAT);
		btn_close.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				isTrue = false;
				shell.close();
				count--;
			}
		});
		btn_close.setText("退 出");
		btn_close.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		btn_close.setBounds(187, 101, 70, 30);
		
		
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
