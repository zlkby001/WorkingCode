package org.dboperate;

//测试提示信息：服务器连接情况，度量日志数据库新增记录数目
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.forms.Methods;
import org.forms.XMLReader;
import org.wsoperate.KnowledgeServiceStub;

import org.wsoperate.WSOperator;

import ui.Verifier;

public class MeasurementLogUpdate {	
	private DBOperate db;	//数据库
	static final String insert ="insert into measurevalue values ";
	
	private String filePath = "C:/TCC/measurevalue";//"C:/WINDOWS/measurevalue";	//度量日志读取
	private File mlfile = new File(filePath);	
	private BufferedReader reader = null;         	         	      
    private String pubkeyPath = "C:/TCC/PubKey";//"C:/WINDOWS/PubKey";	//PK读取       	         	
    private File pubkeyfile = new File(pubkeyPath);
    private BufferedReader pkreader = null;   

    private int count = 0;
    
    private boolean wsstatus = false;
    public MeasurementLogUpdate(DBOperate maindb) {
    	db = maindb;
    }   
    
    public void changeString(String temp[]){
    	int i;
    	
    	for(i=0;i<temp.length;i++){
    		temp[i]= temp[i].replace("'", " ");
    	}    	
    }
    
	public void update() {	
		count = 0;
		String hashlist = "";
		String namelist = "";
		String pathlist = "";
		try {			
			pkreader = new BufferedReader(new FileReader(pubkeyfile));
			String TCM_PK = pkreader.readLine();		
			XMLReader xmlreader = new XMLReader();
		    String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("knowledgeop");
		    
		    WSOperator wso = new WSOperator();
        	if(wso.getWSStatus() == true) {
        		wsstatus = true;
        		System.out.println("服务器连接成功");
        	}
        	else {
        		wsstatus = false;
        		System.out.println("服务器连接失败");        		
        	}
        	
		    //reader = new BufferedReader(new FileReader(mlfile));
        	reader = new BufferedReader(new InputStreamReader(new FileInputStream(mlfile), "GB2312"));
        	
			String tmpString = reader.readLine();       	
			while((tmpString  != null) && !tmpString.equals("")) { 
				//从度量日志中读取数据      
				//序号，Hash值，路径和进程名，父进程名，Linux系统，父进程id，度量时间
				System.out.println(tmpString);
				String mldata[] = null; //保存内容
				mldata = tmpString.split(" "); 
				String processpn[] = mldata[2].split("/");
				ResultSet rs = db.selecttable("select * from measurevalue where processname = " + "'" + processpn[processpn.length-1] + "'" + " and " + "path=" + "'" + mldata[2] + "'" + " and " + "hashvalue=" + "'" + mldata[1] + "'" + " and " + "fathername=" + "'" + mldata[3] + "'" + " and " + "fatherid=" + "'" + mldata[5] + "'" + " and " + "time=" + "'" + mldata[6] + "'", 1);
				if(rs.next()) {	//度量日志记录已存在
					rs.close();
					tmpString = reader.readLine();
					if(wsstatus == true) {
						//查询知识库中该软件信息是否存在
					    ResultSet rsinfo = db.selecttable("select * from info where hashvalue=" + "'" + mldata[1] + "'", 1);
						if(rsinfo.next()) {	//记录已存在
							rsinfo.close();
						}
						else {	//记录不存在
							rsinfo.close();
							//查询服务器知识库
							hashlist = hashlist + mldata[1] + ";;;";
							namelist = namelist + processpn[processpn.length-1] + ";;;";
							pathlist = pathlist + mldata[2] + ";;;";
							/*
						    KnowledgeServiceStub stub = new KnowledgeServiceStub(null, trans_value);
						    KnowledgeServiceStub.Queryknowledge infofunction = new KnowledgeServiceStub.Queryknowledge();
						    infofunction.setIn0(TCM_PK);
						    infofunction.setIn1(mldata[1]);
						    String result = stub.queryknowledge(infofunction).getOut();
						    String res[] = result.split(";;;");
						    String temp[] = res[0].split("&&&");
							//"全选", "进程名称", "版本号", "描述信息", "厂商", "发布日期", "是否工控软件", "可信度", "hash值", "进程路径", "知识库推荐"
							db.inserttable("insert into info values " 
							           + "(" + "'"+ mldata[1] + "'" + "," + "'"+ processpn[processpn.length-1] + "'" + "," + "'"+ temp[2] + "'" + "," + "'"+ temp[4] + "'" + "," + "'"+ temp[3] + "'" + "," + "'"+ temp[5] + "'" + "," + "'"+ temp[6] + "'" + "," + "'"+ temp[7] + "'" + ")", 1);											
			        		*/
			        	}
					}
					continue;
				}
				else {	//度量日志记录不存在
					rs.close();	
					count++;
					//向度量日志数据库中写数据
					String sql = "("  + "'"+ processpn[processpn.length-1] +"'" + "," + "'"+ mldata[2] +"'" + "," + "'"+ mldata[1] +"'" + "," + "'"+ mldata[3] +"'" +  "," + "'"+ mldata[5] +"'" +  "," + "'"+ mldata[6] +"'" + ")";  	        	   		
					db.inserttable(insert + sql, 1);
					tmpString = reader.readLine();
					
					if(wsstatus == true) {
						//查询知识库中该软件信息是否存在
					    ResultSet rsinfo = db.selecttable("select * from info where hashvalue=" + "'" + mldata[1] + "'", 1);
						if(rsinfo.next()) {	//记录已存在
							rsinfo.close();
						}
						else {	//记录不存在
							rsinfo.close();
							//查询服务器知识库	
							hashlist = hashlist + mldata[1] + ";;;";
							namelist = namelist + processpn[processpn.length-1] + ";;;";
							pathlist = pathlist + mldata[2] + ";;;";
							/*
						    KnowledgeServiceStub stub = new KnowledgeServiceStub(null, trans_value);
						    KnowledgeServiceStub.Queryknowledge infofunction = new KnowledgeServiceStub.Queryknowledge();
						    infofunction.setIn0(TCM_PK);
						    infofunction.setIn1(mldata[1]);
						    String result = stub.queryknowledge(infofunction).getOut();
						    String res[] = result.split(";;;");
						    String temp[] = res[0].split("&&&");
							//"全选", "进程名称", "版本号", "描述信息", "厂商", "发布日期", "是否工控软件", "可信度", "hash值", "进程路径", "知识库推荐"
							db.inserttable("insert into info values " 
							           + "(" + "'"+ mldata[1] + "'" + "," + "'"+ processpn[processpn.length-1] + "'" + "," + "'"+ temp[2] + "'" + "," + "'"+ temp[4] + "'" + "," + "'"+ temp[3] + "'" + "," + "'"+ temp[5] + "'" + "," + "'"+ temp[6] + "'" + "," + "'"+ temp[7] + "'" + ")", 1);											
			        		*/
			        	}
					}				
				}        	   		      	   			      	   		
			}
			reader.close();
			if(wsstatus == true) {
				//查询服务器知识库	
			    KnowledgeServiceStub stub = new KnowledgeServiceStub(null, trans_value);
			    KnowledgeServiceStub.Queryknowledge infofunction = new KnowledgeServiceStub.Queryknowledge();
			    infofunction.setIn0(TCM_PK);
			    infofunction.setIn1(hashlist);
			    String result = stub.queryknowledge(infofunction).getOut();
			    if (result == null)
			    {
			    	Methods.Alert("下载失败！");
			    	return;
			    }
			   // String know=result;
			    String res[]=result.split(";;;");
			   
			    
			    
			    //String path[] = pathlist.split(";;;");
				//String name[] = namelist.split(";;;");
				//String hash[] = hashlist.split(";;;");
				//String tmp[]=res[res.length-1].split("^^^");
				//if(tmp.length>0)
				//res[res.length-1]=tmp[0];
				//String tree=tmp[1];
			    //db.cleartable("delete from info");
			    try {
					db.cleartable("delete from info");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    db.commit();
			    for(int i=0; i<res.length; i++) {
					String temp[] = res[i].split("&&&");
					//String temp[] = 
					
					
					changeString(temp);
					if(temp.length>5) {//"鍏ㄩ�", "杩涚▼鍚嶇О", "鐗堟湰鍙�, "鎻忚堪淇℃伅", "鍘傚晢", "鍙戝竷鏃ユ湡", "鏄惁宸ユ帶杞欢", "鍙俊搴�, "hash鍊�, "杩涚▼璺緞", "鐭ヨ瘑搴撴帹鑽�		
							//System.out.println(temp);
							db.inserttable("insert into info(processname,version,info,date,industry,isipc) values " 
					             	 + "(" + "'"+ temp[0] + "'" + "," + "'"+ temp[1] + "'" + "," + "'"+ temp[2] + "'" + "," + "'"+ temp[3] + "'" + "," + "'"+ temp[4] + "'" + "," + "'"+ temp[5] + "'" + ")", 4);																	
					}
				}				   
			}
			

			db.commit();
			    
			    
			    /*
			    String res[] = result.split(";;;");
			    
			    String path[] = pathlist.split(";;;");
				String name[] = namelist.split(";;;");
				String hash[] = hashlist.split(";;;");
				
			    for(int i=0; i<path.length; i++) {
					String temp[] = res[i].split("&&&");
					if(temp.length>5) {//"全选", "进程名称", "版本号", "描述信息", "厂商", "发布日期", "是否工控软件", "可信度", "hash值", "进程路径", "知识库推荐"					
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
						//db.inserttable("insert into info values " 
					             	 //+ "(" + "'"+ hash[i] + "'" + "," + "'"+ name[i] + "'" + "," + "'"+ temp[2] + "'" + "," + "'"+ temp[4] + "'" + "," + "'"+ temp[3] + "'" + "," + "'"+ temp[5] + "'" + "," + "'"+ temp[6] + "'" + "," + "'"+ temp[7] + "'" + ")", 4);																	
						db.inserttable("insert into info values " 
								+ "(" + "'"+ hash[i] + "'" + "," + "'"+ name[i] + "'" + "," + "'"+ str[1] + "'" + "," + "'"+ str[2] + "'" + "," + "'"+ str[3] + "'" + "," + "'"+ str[4] + "'" + "," + "'"+ str[5] + "'" + "," + "'"+ str[6] + "'" + ")", 4);																	
		
					}
				}
			}
			

			db.commit();*/
			System.out.println("度量日志数据库新增数据数目："+count);
			Methods.Alert("下载完成！");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
}