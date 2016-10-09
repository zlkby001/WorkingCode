package org.forms;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.SQLException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
public class MainFrame extends Dialog{
	private Shell shell;
	private Text text;
	private Label label_1;
	private Text text_1;
	private Button button;
	private Button button_1;
	public static String imagePath ="images\\";
	private String SearchFileDirect = null;
	private MessageBox mb;
	private Document doc = null;
	private String input_softtype = null;
	private String outname = null;
	private String outXMLname = null;
	private static int x=0;		//记录指针X坐标
	private static int y=0;		//记录指针Y坐标
	protected static int count;
	private static int res=0;
	

	
	public MainFrame(Shell parent, int style) {
		// TODO Auto-generated constructor stub
		super(parent,style);
		count++;
		
	}

	public void open() {
		Display display = Display.getDefault();		
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	void createContents() {
		Display display = Display.getDefault();
		shell = new Shell(display, SWT.PRIMARY_MODAL);
		shell.setSize(565, 350);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 565;
		int h = 350;
		shell.setBounds((int)(scrSize.width-w)/2,(int)(scrSize.height-h)/2,w, h);
		
		shell.setText("知识库收集工具");
		
		
		
		Composite csTop = new Composite(shell, SWT.FLAT);
		
		csTop.setBounds(0, 0, 565, 69);
		csTop.setBackground(SWTResourceManager.getColor(0, 0, 128));
		csTop.setBackgroundImage(SWTResourceManager.getImage(imagePath + "back_collect.png"));
		
		ToolBar toolBar_1 = new ToolBar(csTop, SWT.FLAT | SWT.RIGHT);
		toolBar_1.setBackground(SWTResourceManager.getColor(33,34,126));
		toolBar_1.setBounds(508, 0, 57, 26);
		
		//窗口最小化
		ToolItem tbitm_Min = new ToolItem(toolBar_1, SWT.NONE);
		tbitm_Min.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.setMinimized(true);
			}
		});
		tbitm_Min.setImage(SWTResourceManager.getImage(imagePath + "Min.png"));
		
		//窗口关闭
		ToolItem tbitm_Close = new ToolItem(toolBar_1, SWT.NONE);
		tbitm_Close.setImage(SWTResourceManager.getImage(imagePath + "Close.png"));
		tbitm_Close.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(MessageDialog.openConfirm(shell, "确认", "您确定要退出吗？")){
					shell.close();
					count--;
				  }
			  }
			}
		);
		Group cgroup = new Group(shell,SWT.NONE);
		cgroup.setBounds(21, 86, 517, 241);
		Label label = new Label(cgroup, SWT.CENTER);
		label.setAlignment(SWT.CENTER);
		label.setFont(SWTResourceManager.getFont("楷体_GB2312", 14, SWT.BOLD));
		label.setBounds(40, 99, 127, 22);
		label.setText("知识库名称：");
		
		text = new Text(cgroup, SWT.BORDER);
		text.setFont(SWTResourceManager.getFont("宋体", 12, SWT.NORMAL));
		text.setBounds(178, 98, 235, 27);
		
		label_1 = new Label(cgroup, SWT.CENTER);
		label_1.setText("扫描目标路径：");
		label_1.setFont(SWTResourceManager.getFont("楷体_GB2312", 14, SWT.BOLD));
		label_1.setAlignment(SWT.CENTER);
		label_1.setBounds(20, 48, 147, 27);
		
		text_1 = new Text(cgroup, SWT.BORDER | SWT.READ_ONLY);
		text_1.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		text_1.setBounds(178, 48, 235, 27);
		
		Button btnNewButton = new Button(cgroup, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				
				try{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				/*JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("选择一个目录");
				jfc.setDialogType(JFileChooser.OPEN_DIALOG);
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int res = jfc.showOpenDialog(null);
				if(res == JFileChooser.APPROVE_OPTION){
					File dir = jfc.getSelectedFile();
					text_1.setText(dir.getAbsolutePath());
					SearchFileDirect = dir.getAbsolutePath();
				}*/
				DirectoryDialog dialog = new DirectoryDialog(shell,SWT.OPEN);
				dialog.setMessage("选择一个目录");
				dialog.setFilterPath("SystemRoot"); 
				String directory = dialog.open();
				if(directory!=null){
					text_1.setText(directory);
					SearchFileDirect = directory;
				}
			}
		});
		btnNewButton.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		btnNewButton.setBounds(429, 49, 63, 26);
		btnNewButton.setText("浏 览");
		
		Label label_2 = new Label(cgroup, SWT.CENTER);
		label_2.setText("知识库类型：");
		label_2.setFont(SWTResourceManager.getFont("楷体_GB2312", 14, SWT.BOLD));
		label_2.setAlignment(SWT.CENTER);
		label_2.setBounds(40, 146, 127, 22);
		
		final Combo combo = new Combo(cgroup, SWT.READ_ONLY);
		combo.setVisibleItemCount(4);
		combo.setItems(new String[] {"系统软件", "工控软件", "应用软件", "其他"});
		combo.setFont(SWTResourceManager.getFont("楷体_GB2312", 12, SWT.BOLD));
		combo.setBounds(178, 145, 102, 24);
		combo.select(0);
		
		button = new Button(cgroup, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				mb = new MessageBox(shell.getShell());
				
				if(SearchFileDirect == null){
					mb.setMessage("知识库收集路径不能为空！");
					mb.open();
					return;
				}
				
				if(text.getText().equals("")){
					mb.setMessage("知识库名称不能为空！");
					mb.open();
					return;
				}
				
				shell.setEnabled(false);
				boolean res = openExe();
				
				
				
				
				
				if(res==true){
					//mb.setMessage("知识库收集完毕！");
					//mb.open();
				}
				else{
					mb.setMessage("知识库收集出错！");
					mb.open();
				}
				//shell.setEnabled(true);
			}
		});
		button.setText("开始收集");
		button.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		button.setBounds(155, 200, 83, 29);
		
		button_1 = new Button(cgroup, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			
				outname = text.getText();
				if (outname==null || outname==""){
					outXMLname = "outfile.xml";
					outname = "outfile";
				}
				else{
					outXMLname = outname + ".xml";
				}
												
				mb = new MessageBox(shell.getShell());
				
				input_softtype = combo.getText();
		
				
//				new Thread() {   
//					public void  run() { 
//						
//						Runtime rt = Runtime.getRuntime();
//						Process p = null;
//						try {
//							p=rt.exec(readOutfileToXML(input_softtype));
//						} catch (Exception e1) {
//							shell.getDisplay().syncExec(new Runnable() {
//								public void run() {
//									
//									shell.setEnabled(true);
//									
//									improtdialog.timer.stop();
//									//进度条线程
//									improtdialog.prorefresh.stop();
//									improtdialog.prgBar.setSelection(100);
//									
//									improtdialog.btnNewButton.setEnabled(true);
//									improtdialog.lblNewLabel.setText("知识库收集完毕");
//									MessageBox mb1 = new MessageBox(shell.getShell());
//									mb1.setMessage("知识库收集完成，共用时：" + Waiting.unitFormat(improtdialog.minute) + "分" + Waiting.unitFormat(improtdialog.second) +"秒！");
//									mb1.open();
//									
//									
//								
//									
//								}
//							});+
//					}
//				}.start();
				
								
				int res = readOutfileToXML(input_softtype);
				
			
				switch(res)
				{
				case 0: break;
				
				case 1: mb.setMessage("知识库文件不存在！");
						mb.open();
						return;
						
				case 2: mb.setMessage("文件读写错误！");
						mb.open();
						return;
				
				case 3: mb.setMessage("知识库文件为空！");
						mb.open();
						return;
						
				case 4: mb.setMessage("xml文件初始化失败！");
						mb.open();
						return;
				
				default: mb.setMessage("未知错误！");
						mb.open();
						return;						
				}	
			
				try {
					Encrypt(outXMLname,outname);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					mb.setMessage("文件加密出错！");
					mb.open();
				}
				
				/*try {
					Decrypt(outname+".enc","decypt.xml");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					mb.setMessage("文件解密出错！");
					mb.open();
				}*/
				
				mb.setMessage("知识库文件导出完成！");
				mb.open();				
			
			}
		});
		button_1.setText("导 出");
		button_1.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		button_1.setBounds(304, 200, 73, 29);
		
		
	    MouseListener mouseListener = new MouseListener(){

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
				
		};
		
		MouseMoveListener mouseMoveListener = new MouseMoveListener(){
			@Override
			public void mouseMove(MouseEvent e) {
				// TODO Auto-generated method stub
				if(x>0)
				{
					shell.setLocation(e.x-x + shell.getLocation().x,
						e.y-y + shell.getLocation().y);
				}
			}			
		};
		csTop.addMouseListener(mouseListener);
		csTop.addMouseMoveListener(mouseMoveListener);
		

	}
	private int readOutfileToXML(String Soft_type){		
	
		// final Waiting improtdialog = new Waiting(shell, 0);
		//	improtdialog.open();
		String strFile = "outfile.txt";
		File mfile = new File(strFile);
		FileReader fr = null;
		BufferedReader br = null;
		Element root = null;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return 4;
		}
		doc = db.newDocument();
		
		
		
		try {
			fr = new FileReader(mfile);
			br = new BufferedReader(fr);

			String strline = null;
			
			if((strline = br.readLine()) != null){
								
				root = doc.createElement("KnowledgeBase");
				doc.appendChild(root);
			}
			else{
				return 3;
			}
//			new Thread() {   
//				public void  run() { 
//					
//					Runtime rt = Runtime.getRuntime();
//					Process p = null;
//					try {
//						 res = readOutfileToXML(input_softtype);
//					} catch (Exception e1) {
//						shell.getDisplay().syncExec(new Runnable() {
//							public void run() {
//								
//								shell.setEnabled(true);
//								
//								improtdialog.timer.stop();
//								//进度条线程
//								improtdialog.prorefresh.stop();
//								improtdialog.prgBar.setSelection(100);
//								
//								improtdialog.btnNewButton.setEnabled(true);
//								improtdialog.lblNewLabel.setText("知识库收集完毕");
//								MessageBox mb1 = new MessageBox(shell.getShell());
//								mb1.setMessage("知识库收集完成，共用时：" + Waiting.unitFormat(improtdialog.minute) + "分" + Waiting.unitFormat(improtdialog.second) +"秒！");
//								mb1.open();
//								
//								
//							
//								
//							}
//						});
//					}
//				}
//			}.start();
			do{	
				System.out.println("filename:"+strline);
				//strline.replace("\\n", "");
				String[] programItem = strline.split(" \\| ");
				
				programItem[1] = programItem[1].replaceAll("\'", "\'\'");
			
				Element KldElement = doc.createElement("KldElement");
				root.appendChild(KldElement);
		
								
				Element filename = doc.createElement("filename");
				KldElement.appendChild(filename);
				programItem[1] = programItem[1].replaceAll("\'", "\'\'");
				org.w3c.dom.Text tfilename = doc.createTextNode(programItem[1]);
				filename.appendChild(tfilename);
				
				// File Path
				Element filepath = doc.createElement("filepath");
				KldElement.appendChild(filepath);
				programItem[2] = programItem[2].replaceAll("\'", "\'\'");
				org.w3c.dom.Text tfilepath = doc.createTextNode(programItem[2]);
				filepath.appendChild(tfilepath);
				
				// File Hashvalue
				Element hashvalue = doc.createElement("hashvalue");
				KldElement.appendChild(hashvalue);				
				org.w3c.dom.Text thashvalue = doc.createTextNode(programItem[0]);
				hashvalue.appendChild(thashvalue);
				
				// Software Type
				Element softtype = doc.createElement("softtype");
				KldElement.appendChild(softtype);
				org.w3c.dom.Text tsofttype = doc.createTextNode(Soft_type);
				softtype.appendChild(tsofttype);
				
				// File Description
				Element filedesp = doc.createElement("filedesp");
				KldElement.appendChild(filedesp);				
				if(programItem[5].equals("")|| programItem[5].equals(" ")){
					programItem[5] = "unknown";
				}
				programItem[5] = programItem[5].replaceAll("\'", "\'\'");
				org.w3c.dom.Text tfiledesp = doc.createTextNode(programItem[5]);
				filedesp.appendChild(tfiledesp);
				
				// Company
				Element company = doc.createElement("company");
				KldElement.appendChild(company);
				if(programItem[3].equals("")|| programItem[3].equals(" ")){
					programItem[3] = "unknown";
				}
				programItem[3] = programItem[3].replaceAll("\'", "\'\'");
				org.w3c.dom.Text tcompany = doc.createTextNode(programItem[3]);
				company.appendChild(tcompany);
				
				// File Version
				Element fileversion = doc.createElement("fileversion");
				KldElement.appendChild(fileversion);
				if(programItem[4].equals("")|| programItem[4].equals(" ")){
					programItem[4] = "unknown";
				}
				programItem[4] = programItem[4].replaceAll("\'", "\'\'");
				org.w3c.dom.Text tfileversiony = doc.createTextNode(programItem[4]);
				fileversion.appendChild(tfileversiony);
				
				// OS Type
				Element ostype = doc.createElement("ostype");
				KldElement.appendChild(ostype);
				System.out.println("programitem:"+programItem[1]+"---"+programItem[6]);
				if(programItem[6].equals("")|| programItem[6].equals(" ")){
					programItem[6] = "unknown";
				}
				programItem[6] = programItem[6].replaceAll("\'", "\'\'");
				org.w3c.dom.Text tostype = doc.createTextNode(programItem[6]);
				ostype.appendChild(tostype);
				
			} while((strline = br.readLine()) != null);
			
			
			OutputStreamWriter osWriter = null;
			PrintWriter outWriter = null;
			XMLDocumentWriter xmlWriter = null;
			
			osWriter = new OutputStreamWriter(new FileOutputStream(
					outXMLname), "GBK");
			// osWriter = new OutputStreamWriter(new
			// FileOutputStream(getPFXMLFileName()));
			outWriter = new PrintWriter(osWriter);
			xmlWriter = new XMLDocumentWriter(outWriter);
			// set Indent on			
			xmlWriter.setIndentOn(true);
			//xmlWriter.initialforChinese();
			xmlWriter.write(doc);
			xmlWriter.close();
			outWriter.close();
			osWriter.close();
			
		} catch (FileNotFoundException e) {			
			System.out.println("文件未找到：" + mfile);
			return 1;
		} catch (IOException ioe) {			
			System.out.println("文件读写错误！");
			return 2;
		} finally {
			try {
				if (br != null) {
					br.close();
					br = null;
				}
				if (fr != null) {
					fr.close();
					fr = null;
				}				
			} catch (IOException e) {
				e.printStackTrace();				
			}			
		}
				
		return 0;
	}
	public void Encrypt(String ToEncFilename, String EncFilename) throws Exception{
		
		
		//创建Key gen  
		KeyGenerator keyGenerator = null;  
		Cipher cipher = null;  
		  
		keyGenerator = KeyGenerator.getInstance("AES");  
		keyGenerator.init(128, new SecureRandom("TCWG".getBytes()));  
		SecretKey secretKey = keyGenerator.generateKey();  
		byte[] codeFormat = secretKey.getEncoded();  
		SecretKeySpec key = new SecretKeySpec(codeFormat, "AES");  
		cipher = Cipher.getInstance("AES");  
		//初始化  
		cipher.init(Cipher.ENCRYPT_MODE, key);  
		 
		File toEncfile = new File(ToEncFilename);
		File encrypfile = new File(EncFilename + ".enc");;
		InputStream inputStream = null;  
		OutputStream outputStream = null;  
 
		inputStream = new FileInputStream(toEncfile);  
		//encrypfile = File.createTempFile(EncFilename, ".enc");  
		outputStream = new FileOutputStream(encrypfile);  
		 //Cipher cipher = initAESCipher(sKey,Cipher.ENCRYPT_MODE);  
		//以加密流写入文件  
		CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);  
		byte[] cache = new byte[1024];  
		int nRead = 0;  
		while ((nRead = cipherInputStream.read(cache)) != -1) {  
			outputStream.write(cache, 0, nRead);  
			outputStream.flush();  
		}  
		cipherInputStream.close();  
		inputStream.close();
		outputStream.close();
	
	}
	public boolean openExe(){
		//Runtime rt = Runtime.getRuntime();
		//Process p = null;
		
		final Waiting wtdialog = new Waiting(shell, 0);
		wtdialog.open();
		final String tempFileDir =  "\""+ SearchFileDirect  +"\"";
		//SearchFileDirect = SearchFileDirect + "\\";
		System.out.println(tempFileDir);
		//try{
						
			/*p = rt.exec("KnowledgeCollect.exe" +" " + tempFileDir);
			//p.waitFor();
			
			//获取进程的标准输入流  
			final InputStream is1 = p.getInputStream();   
			//获取进城的错误流  
			final InputStream is2 = p.getErrorStream();  
			
			//启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流  
			new Thread() {  
				public void run() {  
					BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));  
					try {  
						String line1 = null;  
						while ((line1 = br1.readLine()) != null) {  
						if (line1 != null){}  
						}  
					} catch (IOException e) {  
						e.printStackTrace();  
					}  
					finally{  
						try {  
							is1.close();  
						} catch (IOException e) {  
							e.printStackTrace();  
						}  
					}  
				}  
			}.start();  
                           
			new Thread() {   
				public void  run() {   
					BufferedReader br2 = new  BufferedReader(new  InputStreamReader(is2));   
					try {   
						String line2 = null ;   
						while ((line2 = br2.readLine()) !=  null ) {   
							if (line2 != null){}  
						}   
					} catch (IOException e) {   
						e.printStackTrace();  
					}   
					finally{  
						try {  
							is2.close();  
						} catch (IOException e) {  
							e.printStackTrace();  
						}  
					}  
				}   
			}.start(); */
			
		new Thread() {   
			public void  run() { 
				
				Runtime rt = Runtime.getRuntime();
				Process p = null;
				try {
					p = rt.exec("KnowledgeCollect.exe" +" " + tempFileDir);
				} catch (Exception e1) {
					shell.getDisplay().syncExec(new Runnable() {
						public void run() {
							shell.setEnabled(true);
							wtdialog.timer.stop();
							//进度条线程
							wtdialog.prorefresh.stop();
							wtdialog.prgBar.setSelection(0);
							
							wtdialog.btnNewButton.setEnabled(true);
							wtdialog.lblNewLabel.setText("知识库收集失败");
							MessageBox mb1 = new MessageBox(shell.getShell());
							mb1.setMessage("知识库收集出错！");
							mb1.open();
						}
					});
				}
				
				final InputStream is1 = p.getInputStream();   
				//获取进城的错误流  
				final InputStream is2 = p.getErrorStream();  
				
				//启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流  
				new Thread() {  
					public void run() {  
						BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));  
						try {  
							String line1 = null;  
							while ((line1 = br1.readLine()) != null) {  
							if (line1 != null){}  
							}  
						} catch (IOException e) {  
							e.printStackTrace();  
						}  
						finally{  
							try {  
								is1.close();  
							} catch (IOException e) {  
								e.printStackTrace();  
							}  
						}  
					}  
				}.start();  
	                           
				new Thread() {   
					public void  run() {   
						BufferedReader br2 = new  BufferedReader(new  InputStreamReader(is2));   
						try {   
							String line2 = null ;   
							while ((line2 = br2.readLine()) !=  null ) {   
								if (line2 != null){}  
							}   
						} catch (IOException e) {   
							e.printStackTrace();  
						}   
						finally{  
							try {  
								is2.close();  
							} catch (IOException e) {  
								e.printStackTrace();  
							}  
						}  
					}   
				}.start();
				
                
				try {
					p.waitFor();
				} catch (InterruptedException e) {
					shell.getDisplay().syncExec(new Runnable() {
						public void run() {
							shell.setEnabled(true);
							wtdialog.timer.stop();
							//进度条线程
							wtdialog.prorefresh.stop();
							wtdialog.prgBar.setSelection(0);
							
							wtdialog.btnNewButton.setEnabled(true);
							wtdialog.lblNewLabel.setText("知识库收集失败");
							MessageBox mb1 = new MessageBox(shell.getShell());
							mb1.setMessage("知识库收集出错！");
							mb1.open();
						}
					});
				}  
				p.destroy();  
				
				shell.getDisplay().syncExec(new Runnable() {
					public void run() {
						shell.setEnabled(true);
						wtdialog.timer.stop();
						//进度条线程
						wtdialog.prorefresh.stop();
						wtdialog.prgBar.setSelection(100);
						
						wtdialog.btnNewButton.setEnabled(true);
						wtdialog.lblNewLabel.setText("知识库收集完毕");
						MessageBox mb1 = new MessageBox(shell.getShell());
						mb1.setMessage("知识库收集完成，共用时：" + Waiting.unitFormat(wtdialog.minute) + "分" + Waiting.unitFormat(wtdialog.second) +"秒！");
						mb1.open();
					}
				});
				
								
			}   
		}.start(); 
			
			
                                
			//p.waitFor();  
			//p.destroy();   	
			
		/*	
		} catch(Exception e){
			try {
				//p.getErrorStream().close();
				//p.getInputStream().close();
				//p.getOutputStream().close();
			} catch (Exception e1) {wtdialog.close();}
			wtdialog.close();
			return false;
		}
		*/
		//wtdialog.close();
		//MessageBox mb1 = new MessageBox(shell.getShell());
		//mb1.setMessage("知识库文件导出完成，共用时：" + Waiting.unitFormat(wtdialog.minute) + "分" + Waiting.unitFormat(wtdialog.second) +"秒！");
		//mb1.open();	
		
		return true;
	}
}
