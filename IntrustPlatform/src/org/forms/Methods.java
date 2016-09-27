package org.forms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import Integrity.EncodeUtil;
import Integrity.PlatformMeasureBean;

import ui.LibMeasure;
import ui.ServerIPaddress;

public class Methods {
	
	/**
	 * 提示框
	 * @param s
	 */
	public static void Alert(String s) {
		MessageBox messageBox = new MessageBox(new Shell(), SWT.OK | SWT.ICON_WARNING);
		messageBox.setMessage("" + s);
		messageBox.open();
	}
	

	/**
	 * 数值转换
	 * 
	 * @param
	 */
	public static int StringtoInt(String s) {
		if (s == null || s.equals(""))
			return 0;
		else
			return Integer.parseInt(s);
	}
	
	/**
	 * 修改界面控件为白名单模式
	 * @param val
	 */
	public static void updateWidgetsMode(){
		Image img;
		if(LibMeasure.whitemode == 1){
			img = SWTResourceManager.getImage("images\\ProtectMode.png");
			Home.lbl_ModeImage.setImage(img);
			Home.lbl_Mode.setText("保护模式已开启");
			ProcessSet.mod_label.setText("白名单模式");
			ProcessSet.btn_set_wl.setEnabled(false);
			Update.mod_label.setText("白名单模式");
			Update.cmm_button.setEnabled(true);
			Setup.btn_CloseMonitor.setImage(
					SWTResourceManager.getImage("images\\CloseMonitor.png"));
			
		}
		else{
			img = SWTResourceManager.getImage("images\\UnprotectedMode.png");
			Home.lbl_ModeImage.setImage(img);
			Home.lbl_Mode.setText("保护模式已关闭");
			ProcessSet.mod_label.setText("普通模式");
			ProcessSet.btn_set_wl.setEnabled(true);
			Update.mod_label.setText("普通模式");
			Update.cmm_button.setEnabled(false);
			Setup.btn_CloseMonitor.setImage(
					SWTResourceManager.getImage("images\\StartMonitor.png"));
		}
	}
	
	/**
	 * 更新界面上的IP和端口号
	 */
	public static void updateCommunicationSet(){
		String ip = ServerIPaddress.getIp();
		String port = ServerIPaddress.getPort() + "";		
//		StatusMeasureLog.txt_ServerIP.setText(ip);
//		StatusMeasureLog.txt_ServerPort.setText(port);
		StatusActiveProcess.txt_ServerIP.setText(ip);
		StatusActiveProcess.txt_ServerPort.setText(port);
		Register.txt_ServerIp.setText(ip);
		Register.txt_ServerPort.setText(port);
		ServerIPaddress.setIpConfig(ip,port);
	}
	
	

}
