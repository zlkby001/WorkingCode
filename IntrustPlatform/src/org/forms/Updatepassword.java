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

public class Updatepassword extends Dialog{

	protected Object result;
	protected Shell shell;
	private Label inform;
	static Text oldword;
	static Text newword;
	private Button btn_ok;
	private MessageBox mb;
	private Label label_1;
	boolean isOpened = true;
	public Boolean isTrue = false;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Updatepassword(Shell parent, int style) {
		super(parent, style);		
		
		setText("SWT Dialog");

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
		shell.setText("Update Password");
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
		inform.setText("输入密码");
		inform.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		inform.setBounds(0, 10, 354, 27);
		Label label = new Label(pgroup, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("仿宋_GB2312", 14, SWT.BOLD));
		label.setBounds(40, 23, 70, 25);
		label.setText("旧密码：");
		label_1 = new Label(pgroup, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("仿宋_GB2312", 14, SWT.BOLD));
		label_1.setBounds(40, 66, 70, 23);
		label_1.setText("新密码：");
	
		
		oldword=new Text(pgroup,SWT.PASSWORD|SWT.BORDER);//带边框
		oldword.setBounds(116,20,153,25);
		newword=new Text(pgroup,SWT.PASSWORD|SWT.BORDER);//带边框
		newword.setBounds(116,64,153,25);
		
		

		btn_ok = new Button(pgroup, SWT.FLAT);
		btn_ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if(Updatepassword.newword.getText()!=""){
					
					Confirm cf = new Confirm(shell,0);
					cf.open();
					shell.close();
					
				}else{
					mb = new MessageBox(shell.getShell());
					mb.setMessage("密码不能为空！");			
					mb.open();
					
				}
				//shell.close();
//				try {
//					Class.forName("org.sqlite.JDBC");
//					Connection con = DriverManager.getConnection("jdbc:sqlite://C:/TCC/user.db");
//					con.setAutoCommit(false);
//					Statement stat = con.createStatement();
//					ResultSet rs = stat.executeQuery("SELECT * FROM userInfo;");
//					if(rs.getString("passWord").equals(oldword.getText()))
//					{
//						isTrue = true;
//					    String sql = "UPDATE userInfo set passWord ='"+ newword.getText()+"'"+" where userId = 1";
//					    stat.executeUpdate(sql);
//					    mb = new MessageBox(shell.getShell());
//						mb.setMessage("密码修改成功！");			
//						mb.open();
//					}
//					else
//					{
//						mb = new MessageBox(shell.getShell());
//						mb.setMessage("密码错误,请重新输入！");			
//						mb.open();
//						Updatepassword up = new Updatepassword(shell,0);
//						up.open();
//					}
//					con.commit();
//					stat.close();
//					con.close();
//					shell.close();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				return;
			}
		});
		btn_ok.setText("修 改");
		btn_ok.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		btn_ok.setBounds(127, 106, 70, 30);
		
	
		
		
	}
}
