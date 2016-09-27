package org.forms;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import ui.ServerIPaddress;

public class SetupCommunicationSet extends Dialog {

	protected Object result;
	protected Shell shellCom;
	public static Text txt_Port;
	private int x=0;
	private int y=0;
	public static int count=0;
	public static IPText txt_IP;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SetupCommunicationSet(final Shell parent, int style) {
		super(parent, style);
		count++;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shellCom.open();
		shellCom.layout();
		Display display = getParent().getDisplay();
		while (!shellCom.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	public void createContents() {
		shellCom = new Shell(getParent(), SWT.RESIZE | SWT.TITLE);
		shellCom.setSize(420, 260);
		shellCom.setText("通信设置");
		//int x1 = shellCom.getParent().getLocation().x + shellCom.getParent().getSize().x/2;
		//int y1 = shellCom.getParent().getLocation().y + shellCom.getParent().getSize().y/2;
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 420;
		int h = 260;
		shellCom.setBounds((int)(scrSize.width-w)/2,(int)(scrSize.height-h)/2,w, h);
		
		//shellCom.setBounds(x1,y1,500,300);
		shellCom.setVisible(true);
		
		Group cgroup = new Group(shellCom,SWT.NONE);
		cgroup.setBounds(27, 46, 351, 155);
		
		Label lbl_Title = new Label(shellCom, SWT.NONE);
		lbl_Title.setFont(SWTResourceManager.getFont("楷体_GB2312", 16, SWT.BOLD));
		lbl_Title.setBounds(155, 10, 94, 30);
		lbl_Title.setText("通信设置");
		
		Button btn_Save = new Button(cgroup, SWT.NONE);
		btn_Save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ServerIPaddress.setIp(txt_IP.getText());
				ServerIPaddress.setPort( Methods.StringtoInt(txt_Port.getText()));	
				
				//更新XML配置文件中IP地址和端口号
			    XMLReader xmlreader = new XMLReader();
			    xmlreader.insertEle("WSIP", "http://"+txt_IP.getText()+":"+txt_Port.getText());
			    String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("registop");
			    
				Methods.updateCommunicationSet();
				count--;
				System.out.println(count);
				shellCom.close();
				
			}
		});
		btn_Save.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		btn_Save.setBounds(134, 109, 94, 36);//修改按钮位置
		btn_Save.setText("保存");
		
		Label lbl_IP = new Label(cgroup, SWT.NONE);
		lbl_IP.setFont(SWTResourceManager.getFont("宋体", 14, SWT.BOLD));
		lbl_IP.setBounds(32, 35, 92, 21);
		lbl_IP.setText("服务器IP：");
		
		txt_IP = new IPText(cgroup,SWT.BORDER);
		txt_IP.setBackground(SWTResourceManager.getColor(204, 232, 207));//设置背景颜色
		txt_IP.setBounds(136, 35, 179, 21);
		txt_IP.setText(ServerIPaddress.getIp()); 
		
		Label lbl_Port = new Label(cgroup, SWT.NONE);
		lbl_Port.setFont(SWTResourceManager.getFont("宋体", 14, SWT.BOLD));
		lbl_Port.setBounds(54, 62, 70, 21);
		lbl_Port.setText("端口号：");
		
		txt_Port = new Text(cgroup, SWT.BORDER | SWT.CENTER);
		txt_Port.setFont(SWTResourceManager.getFont("宋体", 12, SWT.BOLD));
		txt_Port.setBounds(136, 61, 179, 21);
		txt_Port.setText(ServerIPaddress.getPort() + "");
		
		//添加窗体鼠标事件
		shellCom.addMouseListener(new MouseListener(){

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
		shellCom.addMouseMoveListener(new MouseMoveListener(){
			@Override
			public void mouseMove(MouseEvent e) {
				// TODO Auto-generated method stub
				if(x>0)
				{
					shellCom.setLocation(e.x-x + shellCom.getLocation().x,
						e.y-y + shellCom.getLocation().y);
				}
			}
			
		});

	}

}
