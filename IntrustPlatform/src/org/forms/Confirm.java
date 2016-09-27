package org.forms;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.eclipse.swt.SWT;
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

public class Confirm extends Dialog{

	protected Object result;
	protected Shell shell;
	private Label inform;
	private Button btn_ok;
	private MessageBox mb;
	private MessageBox mb1;
	boolean isOpened = true;
	public Boolean isTrue = false;
	private Button button;
	public static int count = 0;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Confirm(Shell parent, int style) {
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
		
		shell = new Shell(getParent(), SWT.CLOSE | SWT.RESIZE | SWT.TITLE);
		shell.setSize(350, 146);
		//shell.setSize(712, 431);
		shell.setText("");
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 350;
		int h = 230;
		shell.setBounds((int)(scrSize.width-w)/2,(int)(scrSize.height-h)/2,w, h);
		shell.setVisible(true);
	
		final Group pgroup = new Group(shell,SWT.NONE);
		pgroup.setFont(SWTResourceManager.getFont("宋体", 12, SWT.NORMAL));
		pgroup.setBounds(21, 43, 302, 143);
		
		inform = new Label(shell, SWT.CENTER);
		inform.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		inform.setText("确认修改密码？");
		inform.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		inform.setBounds(0, 10, 354, 27);
		
		

		btn_ok = new Button(pgroup, SWT.FLAT);
		btn_ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				Updatepassword up = new Updatepassword(shell,0);
				try {
					Class.forName("org.sqlite.JDBC");
					Connection con = DriverManager.getConnection("jdbc:sqlite://C:/TCC/user.db");
					con.setAutoCommit(false);
					Statement stat = con.createStatement();
					ResultSet rs = stat.executeQuery("SELECT * FROM userInfo;");
					if(rs.getString("passWord").equals(Updatepassword.oldword.getText()))
					{
						isTrue = true;
					    String sql = "UPDATE userInfo set passWord ='"+Updatepassword.newword.getText()+"'"+" where userId = 1";
					    stat.executeUpdate(sql);
					    mb = new MessageBox(shell.getShell());
						mb.setMessage("密码修改成功！");			
						mb.open();
					//	Updatepassword up =new Updatepassword(shell, 0);
					}
					else
					{
						mb1 = new MessageBox(shell.getShell());
						mb1.setMessage("密码错误,请重新输入！");			
						mb1.open();
						
					}
					con.commit();
					stat.close();
					con.close();
					shell.close();
					//up.shell.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btn_ok.setText("确 认");
		btn_ok.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		btn_ok.setBounds(41, 75, 70, 30);
		
		button = new Button(pgroup, SWT.FLAT);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
				count--;
			}
		});
		button.setText("取 消");
		button.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		button.setBounds(183, 75, 70, 30);
		
	
		
		
	}
}
