package org.forms;

import java.io.BufferedReader;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.dboperate.DBOperate;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;

public class SubUploadLog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	
	private static String rPath = "C:/TCC/ClientData/configuration.txt";//"C:/WINDOWS/ClientData/configuration.txt";
	private static BufferedReader reader = null;  
    private static File rfile = new File(rPath);
	private static String wPath = "C:/TCC/ClientData/configuration.txt";//"C:/WINDOWS/ClientData/configuration.txt";
	private static File wfile = new File(wPath);
	private String pubkeyPath = "C:/TCC/PubKey";//"C:/WINDOWS/PubKey";	        	         	
    private File pubkeyfile = new File(pubkeyPath);
    private BufferedReader pkreader = null;
    private String TCM_PK;
    
	private JButton jOK;
	private JButton jSave;	
	private static String upload;
	private static String upload1;
	private static String upload2;
	private static String uploadflag;
	private JPanel jPanel;
	private JLabel jLabel;
	private JLabel jLabel2;
	private JTextField jTimeField2;
	private JTextField jTimeField1;	
	private JCheckBox jCheckBox;
	
	private DBOperate db;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	
	public SubUploadLog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			dialogShell.setVisible(false);
			
			
			dialogShell.pack();			
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
