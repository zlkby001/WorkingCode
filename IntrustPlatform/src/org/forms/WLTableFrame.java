package org.forms;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

import org.dboperate.DBOperate;
import org.wsoperate.WSOperator;
import org.wsoperate.KnowledgeServiceStub;
import org.wsoperate.WhitelistServiceStub;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class WLTableFrame extends javax.swing.JDialog {
	private JButton jOK;
	private JScrollPane jTablePanel;
	private JTable jTable;
	private DefaultTableModel jTableModel;	
	final MyCheckBoxRenderer check = new MyCheckBoxRenderer();
	private boolean selectFlag = false;
	
	private DBOperate db;
	private String activePath = "C:/TCC/processtemp.log";//"C:/WINDOWS/processtemp.log";
	private String wlPath = "C:/TCC/whitelist.txt";//"C:/whitelist.txt";
	static final String insert ="insert into wl values ";
	private String clearsql = "delete from 'wl'";
	private BufferedReader reader = null;  
	private BufferedWriter writer = null;
    private File file = new File(activePath);
    private File wlfile = new File(wlPath);
    private String pubkeyPath = "C:/TCC/PubKey";//"C:/WINDOWS/PubKey";	        	         	
    private File pubkeyfile = new File(pubkeyPath);
    
    private boolean connectflag = false; 

	/**
	* Auto-generated main method to display this JFrame
	*/
	/*
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TableFrame inst = new TableFrame(str);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	*/

	public WLTableFrame(String tableHeadName[]) {
		super();		
		try {
			initGUI(tableHeadName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initGUI(String tableHeadName[]) throws FileNotFoundException {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setLocation(0,0);
			this.setModal(true);
			this.setLayout(null);			
			getContentPane().setBackground(Color.WHITE);
			{
				{
				jTablePanel = new JScrollPane();
				getContentPane().add(jTablePanel);
				jTablePanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				jTablePanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				jTablePanel.setBackground(Color.WHITE);
				jTablePanel.setOpaque(true);
				jTablePanel.setBounds(5, 35, 1200, 25);
				{
					jTableModel = new DefaultTableModel( new Object[0][5], tableHeadName) {
						@Override
						public Class<?> getColumnClass(int columnIndex) 
						{
							if(columnIndex==0) { 
								return Boolean.class;
							}
							return Object.class;
						}
					};						
					jTable = new JTable() {
						@Override
						public boolean isCellEditable(int row, int column) {
							if(column==0) return true;							 
							return false; 
						}
					};					
					jTable.setBackground(Color.WHITE);
					jTable.setFocusable(false);
					jTable.setRowHeight(25);				 
					jTable.setRowSelectionAllowed(true);   
					jTable.setSelectionBackground(new Color(227, 243, 248));									
					jTable.setShowHorizontalLines(true);      				
					jTable.setShowVerticalLines(true);
					jTable.setGridColor(Color.LIGHT_GRAY);
					JTableHeader jTableH = jTable.getTableHeader();
				    jTableH.setBackground(new Color(110, 167, 51));
				    jTableH.setForeground(Color.WHITE);
					jTable.setModel(jTableModel);					
					jTablePanel.setViewportView(jTable);				
					jTableModel.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							selectFlag = true;
							for(int i=0; i<jTable.getRowCount(); i++) {
								if(jTable.getModel().getValueAt(i,0) == (Object)false) {
									selectFlag = false; 
									break;
								}
							}
							if((selectFlag == true) && (jTable.getColumnCount() > 0)) check.setSelected(true);
							else check.setSelected(false);
							jTable.getTableHeader().repaint();
						}
					});				
					jTable.getColumn("全选").setHeaderRenderer(check);
					jTable.getTableHeader().addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(jTable.getColumnModel().getColumnIndexAtX(e.getX())==0){
								JCheckBox Checkbox = (JCheckBox)check;
							boolean b = !check.isSelected();
							check.setSelected(b);
							jTable.getTableHeader().repaint();
							for(int i=0;i<jTable.getRowCount();i++){
								jTable.getModel().setValueAt(b, i, 0);
							}
							}
						}
					});						
					{ //调整Table列宽
						jTable.getColumnModel().getColumn(0).setPreferredWidth(60);
						jTable.getColumnModel().getColumn(1).setPreferredWidth(140);
						jTable.getColumnModel().getColumn(2).setPreferredWidth(70);
						jTable.getColumnModel().getColumn(3).setPreferredWidth(100);
						jTable.getColumnModel().getColumn(4).setPreferredWidth(100);
						jTable.getColumnModel().getColumn(5).setPreferredWidth(100);
						jTable.getColumnModel().getColumn(6).setPreferredWidth(100);
						jTable.getColumnModel().getColumn(7).setPreferredWidth(100);
						jTable.getColumnModel().getColumn(8).setPreferredWidth(180);
						jTable.getColumnModel().getColumn(9).setPreferredWidth(180);
						jTable.getColumnModel().getColumn(10).setPreferredWidth(70);
						jTable.setSize(900, 1000000);
					}
					
					String liststr = "";
					String hashstr = "";
					db = org.forms.MainForm.db;
					{
						String pathlist0 = "";	//新增
						String namelist0 = "";
						String hashlist0 = "";
						String pathlist1 = "";	//更新
						String namelist1 = "";
						String hashlist1 = "";
						String pathlist2 = "";	//原有记录
						String namelist2 = "";
						String hashlist2 = "";
						String namelist3 = "";	//更新记录名单
						String pathlist3 = "";	//更新记录名单
						reader = new BufferedReader(new FileReader(file));
			        	String tmpString = reader.readLine();
			        	while((tmpString  != null) && !tmpString.equals("")) { 
			        		//从进程日志中读取数据      
			        	   	//进程id，路径和进程名，Hash值ֵ
			        	   	String wldata[] = null; //保存内容
			        	   	wldata = tmpString.split(" "); 
			        	   	String processpn[] = wldata[1].split("/");
			           		
			    	        tmpString = reader.readLine(); 
			    	        ResultSet rs;
			    	        try {
			    	        	//判断是否原有记录
								rs = db.selecttable("select * from wl where path = " + "'" + wldata[1] + "'" + " and " + "hashvalue =" + "'" + wldata[2] + "'", 1);
								if(rs.next()) {//原有记录
									continue;
								}
								rs.close();
								//判断是否更新记录
								rs = db.selecttable("select * from wl where processname = " + "'" + processpn[processpn.length-1] + "'" + " and " + "path =" + "'" + wldata[1] + "'", 1);
								if(rs.next()) {			
									pathlist1 = pathlist1 + wldata[1] + ";;;";
					    	        namelist1 = namelist1 + processpn[processpn.length-1] + ";;;";
					    	        hashlist1 = hashlist1 + wldata[2] + ";;;";
					    	        pathlist1 = pathlist1 + rs.getString("path") + ";;;";
					    	       	namelist1 = namelist1 + rs.getString("processname") + ";;;";
					    	        hashlist1 = hashlist1 + rs.getString("hashvalue") + ";;;";
					    	        namelist3 = namelist3 + rs.getString("processname") + ";;;";
					    	        pathlist3 = pathlist3 + rs.getString("path") + ";;;";
					    	       	while(rs.next()) {
					    	        	pathlist1 = pathlist1 + rs.getString("path") + ";;;";
						    	        namelist1 = namelist1 + rs.getString("processname") + ";;;";
						    	       	hashlist1 = hashlist1 + rs.getString("hashvalue") + ";;;";
					    	       	}
					    	       	continue;
								}
								rs.close();
								//新增记录
								pathlist0 = pathlist0 + wldata[1] + ";;;";
				    	        namelist0 = namelist0 + processpn[processpn.length-1] + ";;;";
				    	        hashlist0 = hashlist0 + wldata[2] + ";;;";
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    	        }
			        	reader.close();
			        	
			        	WSOperator wso = new WSOperator();
			        	if(wso.getWSStatus() == true) {
			        		connectflag = true;
			        		try {
								db.cleartable("delete from 'info'");
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			        	}
			        	else connectflag = false;
			        				        	
			        	//新增记录
			        	if(!pathlist0.equals("")) {
			        		String path0[] = pathlist0.split(";;;");
							String name0[] = namelist0.split(";;;");
							String hash0[] = hashlist0.split(";;;");
												
							if(connectflag == false) {// can't connect to ws
								//System.out.println("1:using lacal info-database!");
								for(int i=0; i<path0.length; i++) {								
									ResultSet rs;
									try {
										rs = db.selecttable("select * from info where hashvalue = " + "'" + hash0[i] + "'", 1);
										if(rs.next()) {
											liststr = liststr + "false0"  + "&&&" + name0[i] + "&&&" + rs.getString("version") + "&&&" + rs.getString("info") + "&&&" + rs.getString("industry") + "&&&" + rs.getString("date") + "&&&" + rs.getString("isipc") + "&&&" + "未知" + "&&&" + hash0[i] + "&&&" + path0[i] + "&&&" + "未知" + ";;;";
										}
										else {
											liststr = liststr + "false0"  + "&&&" + name0[i] + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + hash0[i] + "&&&" + path0[i] + "&&&" + "未知" + ";;;";
										}
										rs.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}				      	   	
								}
							}
							else {
								//System.out.println("1:using ws info-database!");
								BufferedReader pkreader = new BufferedReader(new FileReader(pubkeyfile));
								String TCM_PK = pkreader.readLine();								
								
								XMLReader xmlreader = new XMLReader();
							    String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("knowledgeop");
							    KnowledgeServiceStub stub = new KnowledgeServiceStub(null, trans_value);
							    KnowledgeServiceStub.Queryknowledge infofunction = new KnowledgeServiceStub.Queryknowledge();
							    infofunction.setIn0(TCM_PK);
							    infofunction.setIn1(hashlist0);
							    //System.out.println("hashlist0"+hashlist0);
							    //System.out.println("知识库返回值"+infofunction);
							    //System.out.println("知识库返回值"+stub);
							    //System.out.println("知识库返回值"+stub.queryknowledge(infofunction));
							    String result = stub.queryknowledge(infofunction).getOut();
							    //System.out.println("知识库返回值"+result);
							    String res[] = result.split(";;;");
								for(int i=0; i<path0.length; i++) {
									String temp[] = res[i].split("&&&");
									if(temp.length>5) {//"全选", "进程名称", "版本号", "描述信息", "厂商", "发布日期", "是否工控软件", "可信度", "hash值", "进程路径", "知识库推荐"
										liststr = liststr + "false0"  + "&&&" + name0[i] + "&&&" + temp[2] + "&&&" + temp[3] + "&&&" + temp[5] + "&&&" + temp[4] + "&&&" + temp[6] + "&&&" + temp[7] + "&&&" + hash0[i] + "&&&" + path0[i] + "&&&" + temp[8] + ";;;";
										//查询知识库中该软件信息是否存在
									    ResultSet rsinfo = db.selecttable("select * from info where hashvalue=" + "'" + hash0[i] + "'", 1);
										if(rsinfo.next()) {	//记录已存在
											rsinfo.close();
										}
										else {	//记录不存在
											rsinfo.close();
											String str[] = null;
											if(temp[2] == null) str[1]="UNKNOWN";	
											else str[1]=temp[2];
											if(temp[4] == null) str[2]="UNKNOWN";
											else str[2]=temp[4];
											if(temp[3] == null) str[3]="UNKNOWN";
											else str[3]=temp[3];
											if(temp[5] == null) str[4]="UNKNOWN";
											else str[4]=temp[5];		
											if(temp[6] == null) str[5]="UNKNOWN";
											else str[5]=temp[6];
											if(temp[7] == null) str[6]="UNKNOWN";
											else str[6]=temp[7];
											db.inserttable("insert into info values " 
													+ "(" + "'"+ hash0[i] + "'" + "," + "'"+ name0[i] + "'" + "," + "'"+ str[1] + "'" + "," + "'"+ str[2] + "'" + "," + "'"+ str[3] + "'" + "," + "'"+ str[4] + "'" + "," + "'"+ str[5] + "'" + "," + "'"+ str[6] + "'" + ")", 4);																	
							
											//db.inserttable("insert into info values " 
									             	//+ "(" + "'"+ hash0[i] + "'" + "," + "'"+ name0[i] + "'" + "," + "'"+ temp[2] + "'" + "," + "'"+ temp[4] + "'" + "," + "'"+ temp[3] + "'" + "," + "'"+ temp[5] + "'" + "," + "'"+ temp[6] + "'" + "," + "'"+ temp[7] + "'" + ")", 4);
										}																	
									}
									else {
										liststr = liststr + "false0"  + "&&&" + name0[i] + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + hash0[i] + "&&&" + path0[i] + "&&&" + "未知" + ";;;";
									}
								}																
							}
			        	}
						
						if(!pathlist1.equals("")) {
							//更改
							String path1[] = pathlist1.split(";;;");
							String name1[] = namelist1.split(";;;");
							String hash1[] = hashlist1.split(";;;");
							if(connectflag == false) {// can't connect to ws
								//System.out.println("2:using lacal info-database!");
								for(int i=0; i<path1.length; i++) {
									if(path1.equals("")) continue;
									ResultSet rs;
									try {
										rs = db.selecttable("select * from info where hashvalue = " + "'" + hash1[i] + "'", 1);
										Thread.sleep(5);
										if(rs.next()) {
											liststr = liststr + "false1"  + "&&&" + name1[i] + "&&&" + rs.getString("version") + "&&&" + rs.getString("info") + "&&&" + rs.getString("industry") + "&&&" + rs.getString("date") + "&&&" + rs.getString("isipc") + "&&&" + "未知" + "&&&" + hash1[i] + "&&&" + path1[i] + "&&&" + "未知" + ";;;";
										}
										else {
											liststr = liststr + "false1"  + "&&&" + name1[i] + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + hash1[i] + "&&&" + path1[i] + "&&&" + "未知" + ";;;";
										}
										rs.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}				      	   	
								}
							}
							else {
								//System.out.println("2:using ws info-database!");
								BufferedReader reader = new BufferedReader(new FileReader(pubkeyfile));
								String TCM_PK = reader.readLine();
								XMLReader xmlreader = new XMLReader();
							    String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("knowledgeop");
							    KnowledgeServiceStub stub = new KnowledgeServiceStub(null, trans_value);
							    KnowledgeServiceStub.Queryknowledge infofunction = new KnowledgeServiceStub.Queryknowledge();
							    infofunction.setIn0(TCM_PK);
							    infofunction.setIn1(hashlist1);
							    String result = stub.queryknowledge(infofunction).getOut();
							    String res[] = result.split(";;;");
								for(int i=0; i<path1.length; i++) {
									String temp[] = res[i].split("&&&");
									if(temp.length>5) {//"全选", "进程名称", "版本号", "描述信息", "厂商", "发布日期", "是否工控软件", "可信度", "hash值", "进程路径", "知识库推荐"
										liststr = liststr + "false1"  + "&&&" + name1[i] + "&&&" + temp[2] + "&&&" + temp[3] + "&&&" + temp[5] + "&&&" + temp[4] + "&&&" + temp[6] + "&&&" + temp[7] + "&&&" + hash1[i] + "&&&" + path1[i] + "&&&" + temp[8] + ";;;";
										//System.out.println(name1[i]);
										//System.out.println(path1[i]);
										//System.out.println(hash1[i]);
										//查询知识库中该软件信息是否存在
									    ResultSet rsinfo = db.selecttable("select * from info where hashvalue=" + "'" + hash1[i] + "'", 1);
										if(rsinfo.next()) {	//记录已存在
											rsinfo.close();
										}
										else {	//记录不存在
											rsinfo.close();
											String str[] = null;
											if(temp[2] == null) str[1]="UNKNOWN";	
											else str[1]=temp[2];
											if(temp[4] == null) str[2]="UNKNOWN";
											else str[2]=temp[4];
											if(temp[3] == null) str[3]="UNKNOWN";
											else str[3]=temp[3];
											if(temp[5] == null) str[4]="UNKNOWN";
											else str[4]=temp[5];		
											if(temp[6] == null) str[5]="UNKNOWN";
											else str[5]=temp[6];
											if(temp[7] == null) str[6]="UNKNOWN";
											else str[6]=temp[7];
											db.inserttable("insert into info values " 
													+ "(" + "'"+ hash1[i] + "'" + "," + "'"+ name1[i] + "'" + "," + "'"+ str[1] + "'" + "," + "'"+ str[2] + "'" + "," + "'"+ str[3] + "'" + "," + "'"+ str[4] + "'" + "," + "'"+ str[5] + "'" + "," + "'"+ str[6] + "'" + ")", 4);																	
							
											//db.inserttable("insert into info values " 
											         //+ "(" + "'"+ hash1[i] + "'" + "," + "'"+ name1[i] + "'" + "," + "'"+ temp[2] + "'" + "," + "'"+ temp[4] + "'" + "," + "'"+ temp[3] + "'" + "," + "'"+ temp[5] + "'" + "," + "'"+ temp[6] + "'" + "," + "'"+ temp[7] + "'" + ")", 4);
										}										
									}
									else {
										liststr = liststr + "false1"  + "&&&" + name1[i] + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + hash1[i] + "&&&" + path1[i] + "&&&" + "未知" + ";;;";
									}			      	   	
								}								
							}
						}
						
						//原有记录
						String name3[] = namelist3.split(";;;");
						String path3[] = pathlist3.split(";;;");
						String temp = "";
						/*
						for(int i=0; i<name3.length; i++) {
							if(i<name3.length-1)
								temp = temp + "'" + name3[i] + "'" + ",";
							else
								temp = temp + "'" + name3[i] + "'";
						}
						*/
						for(int i=0; i<path3.length; i++) {
							if(i<path3.length-1)
								temp = temp + "'" + path3[i] + "'" + ",";
							else
								temp = temp + "'" + path3[i] + "'";
						}
						try {
							ResultSet rs;
							rs = db.selecttable("select * from wl where path not in ( " + temp + ")" , 1);
							while(rs.next()) {
								pathlist2 = pathlist2 + rs.getString("path") + ";;;";
			    	        	namelist2 = namelist2 + rs.getString("processname") + ";;;";
			    	        	hashlist2 = hashlist2 + rs.getString("hashvalue") + ";;;";
			    	        	}
							rs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						if(!pathlist2.equals("")) {
							String path2[] = pathlist2.split(";;;");
							String name2[] = namelist2.split(";;;");
							String hash2[] = hashlist2.split(";;;");	
							WSOperator ws = new WSOperator();
							if(connectflag == false) {// can't connect to ws
								//System.out.println("3:using lacal info-database!");
								for(int i=0; i<path2.length; i++) {
									if(path2.equals("")) continue;
									ResultSet rs;
									try {
										rs = db.selecttable("select * from info where hashvalue = " + "'" + hash2[i] + "'", 1);
										if(rs.next()) {
											liststr = liststr + "true" + "&&&" + name2[i] + "&&&" + rs.getString("version") + "&&&" + rs.getString("info") + "&&&" + rs.getString("industry") + "&&&" + rs.getString("date") + "&&&" + rs.getString("isipc") + "&&&" + "未知" + "&&&" + hash2[i] + "&&&" + path2[i] + "&&&" + "未知" + ";;;";
										}
										else {
											liststr = liststr + "true" + "&&&" + name2[i] + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + hash2[i] + "&&&" + path2[i] + "&&&" + "未知" + ";;;";
										}
										rs.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}				      	   	
								}
							}
							else {
								//System.out.println("3:using ws info-database!");
								BufferedReader reader = new BufferedReader(new FileReader(pubkeyfile));
								String TCM_PK = reader.readLine();
								XMLReader xmlreader = new XMLReader();
							    String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("knowledgeop");
							    KnowledgeServiceStub stub = new KnowledgeServiceStub(null, trans_value);
							    KnowledgeServiceStub.Queryknowledge infofunction = new KnowledgeServiceStub.Queryknowledge();
							    infofunction.setIn0(TCM_PK);
							    infofunction.setIn1(hashlist2);
							    String result = stub.queryknowledge(infofunction).getOut();
							    String res[] = result.split(";;;");
								for(int i=0; i<path2.length; i++) {
									String tmp[] = res[i].split("&&&");
									if(tmp.length>5) {//"全选", "进程名称", "版本号", "描述信息", "厂商", "发布日期", "是否工控软件", "可信度", "hash值", "进程路径", "知识库推荐"
										liststr = liststr + "true"  + "&&&" + name2[i] + "&&&" + tmp[2] + "&&&" + tmp[3] + "&&&" + tmp[5] + "&&&" + tmp[4] + "&&&" + tmp[6] + "&&&" + tmp[7] + "&&&" + hash2[i] + "&&&" + path2[i] + "&&&" + tmp[8] + ";;;";
										//查询知识库中该软件信息是否存在
									    ResultSet rsinfo = db.selecttable("select * from info where hashvalue=" + "'" + hash2[i] + "'", 1);
										if(rsinfo.next()) {	//记录已存在
											rsinfo.close();
										}
										else {	//记录不存在
											rsinfo.close();
											String str[] = null;
											if(tmp[2] == null) str[1]="UNKNOWN";	
											else str[1]=tmp[2];
											if(tmp[4] == null) str[2]="UNKNOWN";
											else str[2]=tmp[4];
											if(tmp[3] == null) str[3]="UNKNOWN";
											else str[3]=tmp[3];
											if(tmp[5] == null) str[4]="UNKNOWN";
											else str[4]=tmp[5];		
											if(tmp[6] == null) str[5]="UNKNOWN";
											else str[5]=tmp[6];
											if(tmp[7] == null) str[6]="UNKNOWN";
											else str[6]=tmp[7];
											db.inserttable("insert into info values " 
													+ "(" + "'"+ hash2[i] + "'" + "," + "'"+ name2[i] + "'" + "," + "'"+ str[1] + "'" + "," + "'"+ str[2] + "'" + "," + "'"+ str[3] + "'" + "," + "'"+ str[4] + "'" + "," + "'"+ str[5] + "'" + "," + "'"+ str[6] + "'" + ")", 4);																	
							
											//db.inserttable("insert into info values " 
											         //+ "(" + "'"+ hash2[i] + "'" + "," + "'"+ name2[i] + "'" + "," + "'"+ tmp[2] + "'" + "," + "'"+ tmp[4] + "'" + "," + "'"+ tmp[3] + "'" + "," + "'"+ tmp[5] + "'" + "," + "'"+ tmp[6] + "'" + "," + "'"+ tmp[7] + "'" + ")", 4);
										}
									}
									else {
										liststr = liststr + "true"  + "&&&" + name2[i] + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + "未知" + "&&&" + hash2[i] + "&&&" + path2[i] + "&&&" + "未知" + ";;;";
									}			      	   	
								}								
							}
						}					
					}
					//System.out.println(liststr);
					//client.maininterface.MainFunc.activelogUpdate.start();
					tableUpdate(liststr);					
				}
				}
				{
					jOK = new JButton();
					getContentPane().add(jOK);
					jOK.setText("确定");
					jOK.setBounds(1000, 6, 100, 24);
					jOK.setFocusable(false);
					jOK.setFont(new java.awt.Font("宋体",0,12));
					jOK.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseClicked(java.awt.event.MouseEvent e) {
							FileWriter fw;
							PrintWriter bfWriter = null;
							String whitelistcontent="";
							try {
								fw = new FileWriter(wlfile);
								fw.write("");
								fw.close();
								bfWriter=new PrintWriter(new FileWriter(wlfile)); 
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}							
							int count = 0;
							String liststr = "";
							if(jTable.getRowCount() == 0) {
								JOptionPane.showMessageDialog(null, "没有可选择的记录！"); 
								return;
							}
							//
							try {
								db.cleartable(clearsql);
							} catch (Exception e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							//
							
							for(int i=0; i<jTable.getRowCount(); i++){
								if(Boolean.parseBoolean(jTable.getModel().getValueAt(i, 0).toString()) == true) {
									TableModel tm = jTable.getModel();
									liststr = tm.getValueAt(i, 8) + " " + tm.getValueAt(i, 1);
									bfWriter.println(liststr);
									String sql = "("  + "'"+ tm.getValueAt(i, 1) +"'" + "," + "'"+ tm.getValueAt(i, 8) + "'" + "," + "'"+ tm.getValueAt(i, 9) + "'" + ")";  	        	   		
		    	        	   		try {
										db.inserttable(insert + sql, 4);
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
		    	        	   		whitelistcontent = whitelistcontent + tm.getValueAt(i, 1) + "&&&" + tm.getValueAt(i, 9) + "&&&" + tm.getValueAt(i, 8) +";;;";
									count++;
								}								
							}
							try {
								db.commit();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							bfWriter.close();
							
							//set the version of wl
							try {
								db.cleartable("delete from 'wlversion'");
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							WSOperator ws = new WSOperator();						
							if(ws.getWSStatus() == false) {
								try {
									db.inserttable("insert into wlversion values " + "(" + "'-1'" + ")", 4);
									db.commit();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							else {
								BufferedReader reader = null;
								try {
									reader = new BufferedReader(new FileReader(pubkeyfile));
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								String TCM_PK = null;
								try {
									TCM_PK = reader.readLine();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								String result = null;	//the info of whitelist
								WhitelistServiceStub stub = null;
								int wlversion = 0;
								XMLReader xmlreader = new XMLReader();
								String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("whitelistop");
								try {												    							    
									stub = new WhitelistServiceStub(null, trans_value);
									WhitelistServiceStub.QueryWhitelistinfo infofunction = new WhitelistServiceStub.QueryWhitelistinfo();							
									infofunction.setIn0(TCM_PK);
									result = stub.queryWhitelistinfo(infofunction).getOut();	
								} catch (RemoteException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
								if(result.equals("non_existent")) {
									wlversion = 1;
								}
								else {
									String res[] = result.split("&&&");
									wlversion = Integer.parseInt(res[0])+1;
									//System.out.println("���°汾��:"+wlversion);
								}			
								WhitelistServiceStub.UploadWhitelist uploadfunction = new WhitelistServiceStub.UploadWhitelist();							
					        	uploadfunction.setIn0(TCM_PK);
					        	uploadfunction.setIn1(wlversion);
					        	uploadfunction.setIn2(whitelistcontent);
					        	boolean flag = false;
								try {
									flag = stub.uploadWhitelist(uploadfunction).getOut();
								} catch (RemoteException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
					        	//System.out.println("�Ƿ��ϴ��ɹ�"+flag);
								if(flag == true) {
									try {
										db.inserttable("insert into wlversion values " + "(" + "'" + wlversion + "'" + ")", 4);
										//System.out.println("insert into wlversion values " + "(" + "'" + wlversion + "'" + ")");
										db.commit();
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}									
								}							    
								else {
									try {
										db.inserttable("insert into wlversion values " + "(" + "'-1'" + ")", 4);
										db.commit();
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							}
							
							//client.maininterface.MainFunc.alarmlogUpdate.stop();
							//client.maininterface.MainFunc.measurementlogUpdate.start();
							JOptionPane.showMessageDialog(null, "写入完成! 已写如记录数目：" + count);							
							WLTableFrame.this.dispose();													
						}
					});
				}
			}
			pack();		
			this.setSize(1225, 580);	
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void tableUpdate(String tableList) {
		int count0=0, count1=0, count2=0;
    	String ary[] = tableList.split(";;;");
		int size = ary.length;	
		((DefaultTableModel) jTable.getModel()).getDataVector().clear();
		
		for(int i=0; i<size; i++) {
			//System.out.println(ary[i]);
		    String temp[] = ary[i].split("&&&");
		    if(temp.length!=11) continue;
		    Object temp2[] = new Object[11];
		    for(int j=0; j<11; j++) {
		    	if(temp[0].equals("false0")) {
		    		temp2[0] = false;
		    		count0++; 
		    	}
		    	else if(temp[0].equals("false1")){
		    		temp2[0] = false;
		    		count1++;
		    	}
		    	else {
		    		temp2[0] = true; 
		    		count2++;
		    	}
		    	if(temp[j].equals("non-existent")||temp[j].equals("null")||temp[j].equals("NULL")) {
		    		temp2[j] = "未知";
		    	}
		    	else {
		    		temp2[j] = temp[j];
		    	}
		    	}
		    	jTableModel.addRow(temp2);
		    }
		System.out.println(count0);
		System.out.println(count1);
		System.out.println(count2);
		jTable.getColumnModel().getColumn(1).setCellRenderer(new Myrender(count0/11, count1/11)); 
		jTable.getColumnModel().getColumn(2).setCellRenderer(new Myrender(count0/11, count1/11)); 
		jTable.getColumnModel().getColumn(3).setCellRenderer(new Myrender(count0/11, count1/11)); 
		jTable.getColumnModel().getColumn(4).setCellRenderer(new Myrender(count0/11, count1/11)); 
		jTable.getColumnModel().getColumn(5).setCellRenderer(new Myrender(count0/11, count1/11)); 
		jTable.getColumnModel().getColumn(6).setCellRenderer(new Myrender(count0/11, count1/11)); 
		jTable.getColumnModel().getColumn(7).setCellRenderer(new Myrender(count0/11, count1/11)); 
		jTable.getColumnModel().getColumn(8).setCellRenderer(new Myrender(count0/11, count1/11)); 
		jTable.getColumnModel().getColumn(9).setCellRenderer(new Myrender(count0/11, count1/11)); 
		jTable.getColumnModel().getColumn(10).setCellRenderer(new Myrender(count0/11, count1/11));
		if(size*25>=500) 
			jTablePanel.setSize(1200, 501);
		else
			jTablePanel.setSize(1200, size*25+26);
		
		((DefaultTableModel) jTable.getModel()).fireTableDataChanged();
		jTable.repaint();
		jTable.updateUI();	
    }	
}

class MyCheckBoxRenderer extends JCheckBox implements TableCellRenderer {
	public MyCheckBoxRenderer () {
		this.setBorderPainted(true);
		this.setText("全选");
		this.setBackground(new Color(110, 167, 51));
		this.setForeground(Color.WHITE);
		this.setFont(new java.awt.Font("宋体",0,12));
		}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		return this;
		}
	

	
}
	 