package org.forms;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.ice.jni.registry.*;

public class MeasureDeamon {
	String path = System.getProperty("user.dir")+"\\";
	
	
	/**
	 * 检查启动项，启动后台进程
	 * @return
	 */
	public boolean check(){
		boolean res = true;
		String measurePath = path + "MeasureDaemon.exe";
		String usbPath = path + "U_disk_test.exe";
		String platformpath = path + "platform.exe";
		
		MessageBox mb = new MessageBox(new Shell(), SWT.OK | SWT.ICON_ERROR);
		mb.setText("错误");
		
		//若启动项为空，则要求重新启动电脑
		if(!checkReg("MeasureDaemon",measurePath)){
			mb.setMessage("进程度量服务出错，请重新启动电脑后再使用可信终端平台！");
			mb.open();
			res = false;
		}
		if(!checkReg("USBDaemon",usbPath)){
			mb.setMessage("USB度量服务出错，请重新启动电脑后再使用可信终端平台！");
			mb.open();
			res = false;
		}
		if(!checkReg("platform",platformpath)){
			mb.setMessage("可信终端平台自启动服务出错，请重新启动电脑后再使用可信终端平台！");
			mb.open();
			res = false;
		}
		if(res==true){
			initTcm();
		}
		
		return res;		
	}
	
	/**
	 * 检查注册表中指定键值是否正确
	 * @param keyName
	 * @param pathName
	 */
	private boolean checkReg(String keyName,String pathName){
		String nodeValue = getValue(keyName);
		System.out.println("reg: " + nodeValue);
		
		if(nodeValue.equals("NoSuchKey")||nodeValue.equals("NoSuchValue")){
			setValue(keyName,pathName);
			//messageBox.setMessage("注册表为空");
			return false;
		}
		else{
			if(!nodeValue.trim().equals(pathName.trim())){
				setValue(keyName,pathName);	
				//messageBox.setMessage("键值差异");
				return false;	//系统目录变化
			}
			else
				try{
				File exeFile = new File(nodeValue);						
					return true;	//文件存在				
				}catch(Exception e){
					setValue(keyName,pathName);	
					//messageBox.setMessage("文件不存在");
					return false;	//文件不存在
				}			
		}
	}
	
	/** 
	 * 打开注册表项并读出相应的变量名的值
	 * @return
	 */
	public String getValue(String name) {	
		RegistryKey currentNode = Registry.HKEY_LOCAL_MACHINE;
		String path = "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run";
		String[] nodes = path.split("\\\\");
		String value="";
		try {
			for(int i=0;i<nodes.length;i++){							
				currentNode = currentNode.openSubKey(nodes[i]);
			}				
			//RegistryKey subKey = software.openSubKey(subKeyNode);
			value = currentNode.getStringValue(name);
			currentNode.closeKey();
		} catch (NoSuchKeyException e) {
			value = "NoSuchKey";
		// e.printStackTrace();
		} catch (NoSuchValueException e) {
			value = "NoSuchValue";
		// e.printStackTrace();
		} catch (RegistryException e) {
			e.printStackTrace();
			value = "";
		}
		return value;
	}
	
	/** 
	 * 设置启动项；
	  * Reg 参数说明 
	  * /v  所选项之下要添加或删除的值名 
	  * /t  RegKey 数据类型（reg_sz字符串） 
	  * /d  要分配给添加的注册表 ValueName 的数据 
	  * /f  不用提示就强行删除 
	  */  
	 public boolean setValue(String keyName,String exePath){  
		 String regKey = "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run";  
		 //String myAppName = "MeasureDaemon";  
		 //String exePath = "D:\\Java\\eclipse\\eclipse.exe";  
		 try {
			 	String delete = "REG DELETE " + regKey + " /v " + keyName + " /f";
			 	String cmd = "reg add "+regKey+" /v "+keyName+" /t reg_sz /d \""+exePath +"\"";
			 	System.out.println(delete);
			 	Runtime.getRuntime().exec(delete);
				Runtime.getRuntime().exec(cmd);
				return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}  
	 }   

	 	/** 
		 * 初始化TCM,软件启动后删除初始化程序
		 */  
		 public boolean initTcm(){  
			 File file=new File("TCMInit.exe");    
			 if(!file.exists())
				 return false;
			 file=new File("exportEK.exe");    
			 if(!file.exists())
				 return false;
			 
			 try {
				 	String del = "cmd /c del /f/q ";
				 	//System.out.println(delete);
				 	Runtime.getRuntime().exec("TCMInit.exe");
					Runtime.getRuntime().exec("exportEK.exe");
					Thread.sleep(2000);
					Runtime.getRuntime().exec(del + "TCMInit.exe");
					Runtime.getRuntime().exec(del + "exportEK.exe");
					return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
		 }   
}
