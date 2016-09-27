package org.forms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.axis2.AxisFault;
import org.dboperate.DBOperate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.wsoperate.MetricServiceStub;
import org.wsoperate.WSOperator;

public class ActiveUploadJob implements Job{
	private String filePath = "C:/TCC/measurevalue";//"C:/WINDOWS/measurevalue";	//度量日志读取
	private File mlfile = new File(filePath);	
	private BufferedReader mlreader = null; 
	static final String insert ="insert into measurevalue values ";
	private String pubkeyPath = "C:/TCC/PubKey";//"C:/WINDOWS/PubKey";	        	         	
    private File pubkeyfile = new File(pubkeyPath);
    private BufferedReader pkreader = null;
    private String TCM_PK;
    
    private DBOperate db;
    
	public ActiveUploadJob() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		db = org.forms.MainForm.db;
		System.out.println(db);
		WSOperator ws = new WSOperator();
		if(ws.getWSStatus() == true) {	
			System.out.println("自动上传君：服务器连接成功");
		}
		else {
			System.out.println("自动上传君：服务器连接失败，没有办法自动上传啦哈哈");
			return;
		}
		try {
			pkreader = new BufferedReader(new FileReader(pubkeyfile));
			TCM_PK = pkreader.readLine();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
								
		String client_metrictime = "";
		String span_metrictime1 = "";
		String span_metrictime2 = "";
		//get the latest time in measurement database
		try {
			ResultSet rs = db.selecttable("select max(time) from measurevalue", 9);
			if(rs.next()) {
				client_metrictime = rs.getString("max(time)");
			}
			rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//System.out.println(client_metrictime);	
								
		
		//get the span of time to be uploaded												
		XMLReader xmlreader = new XMLReader();
	    String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("metricop");
	    MetricServiceStub metricstub = null;
		try {
			metricstub = new MetricServiceStub(null, trans_value);
		} catch (AxisFault e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	    MetricServiceStub.QueryMetricTime mtfunction = new MetricServiceStub.QueryMetricTime();	
	    mtfunction.setIn0(TCM_PK);
	    mtfunction.setIn1(client_metrictime);
	    String mtresult = null;
		try {
			mtresult = metricstub.queryMetricTime(mtfunction).getOut();
		} catch (RemoteException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	    
	    //upload the measurevalue
		String measurelist = "";
	    if(mtresult.equals("all")) {
	    	try {
				ResultSet rs = db.selecttable("select * from measurevalue where time<='" +client_metrictime+ "'", 9);
				while(rs.next()) {
					measurelist = measurelist + "null" + "&&&" + rs.getString("processname") + "&&&" 
					            + rs.getString("path") + "&&&" + rs.getString("hashvalue") + "&&&"
					            + rs.getString("fathername") + "&&&" + rs.getString("fatherid") + "&&&"
					            + rs.getString("time") + ";;;";
				}
				System.out.println(measurelist);
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
	    	
	    	if(measurelist.equals("")) {
	    		System.out.println("自动上传君：数据库中没有可上传数据");
	    		return;
	    	}
	    	
	    	MetricServiceStub.UploadMetricLog upmfunction = new MetricServiceStub.UploadMetricLog();
		    upmfunction.setIn0(TCM_PK);
		    upmfunction.setIn1(measurelist);
			try {
				boolean upmres = metricstub.uploadMetricLog(upmfunction).getOut();
				if(upmres == false) {
					System.out.println("自动上传君：度量日志上传错误!");
					return;
			    	//JOptionPane.showMessageDialog(null, "度量日志上传错误!");
			    }
				else {
					//clear measurement database
					try {
						db.cleartable("delete from 'measurevalue'");
						mlreader = new BufferedReader(new FileReader(mlfile));
						String tmpString = mlreader.readLine();       	
						while((tmpString  != null) && !tmpString.equals("")) { 
							//从度量日志中读取数据      
							//序号，Hash值，路径和进程名，父进程名，Linux系统，父进程id，度量时间
							String mldata[] = null; //保存内容
							mldata = tmpString.split(" "); 
							String processpn[] = mldata[2].split("/");
							
							//向度量日志数据库中写数据
							String sql = "("  + "'"+ processpn[processpn.length-1] +"'" + "," + "'"+ mldata[2] +"'" + "," + "'"+ mldata[1] +"'" + "," + "'"+ mldata[3] +"'" +  "," + "'"+ mldata[5] +"'" +  "," + "'"+ mldata[6] +"'" + ")";  	        	   		
							db.inserttable(insert + sql, 1);
							tmpString = mlreader.readLine();					
						}        	   		      	   			      	   		
						mlreader.close();
						db.commit();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    }
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	    }
	    else {
	    	String temp[] = mtresult.split(";;;");
			span_metrictime1 = temp[0];
			span_metrictime2 = temp[1];
			if(!span_metrictime1.equals(span_metrictime2)) {
				try {
					ResultSet rs = db.selecttable("select * from measurevalue where time>'" +span_metrictime1+ "' and time<='" +span_metrictime2+ "'", 9);
					while(rs.next()) {
						measurelist = measurelist + "null" + "&&&" + rs.getString("processname") + "&&&" 
						            + rs.getString("path") + "&&&" + rs.getString("hashvalue") + "&&&"
						            + rs.getString("fathername") + "&&&" + rs.getString("fatherid") + "&&&"
						            + rs.getString("time") + ";;;";
					}
					System.out.println(measurelist);
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(measurelist.equals("")) {
		    		System.out.println("自动上传君：数据库中没有可上传数据");
		    		return;
		    	}
				
				MetricServiceStub.UploadMetricLog upmfunction = new MetricServiceStub.UploadMetricLog();
			    upmfunction.setIn0(TCM_PK);
			    upmfunction.setIn1(measurelist);
				try {
					boolean upmres = metricstub.uploadMetricLog(upmfunction).getOut();
					if(upmres == false) {
						System.out.println("自动上传君：度量日志上传错误!");
						return;
				    	//JOptionPane.showMessageDialog(null, "度量日志上传错误!");
				    }
					else {
						//clear measurement database
						try {
							db.cleartable("delete from 'measurevalue'");
							mlreader = new BufferedReader(new FileReader(mlfile));
							String tmpString = mlreader.readLine();       	
							while((tmpString  != null) && !tmpString.equals("")) { 
								//从度量日志中读取数据      
								//序号，Hash值，路径和进程名，父进程名，Linux系统，父进程id，度量时间
								String mldata[] = null; //保存内容
								mldata = tmpString.split(" "); 
								String processpn[] = mldata[2].split("/");
								
								//向度量日志数据库中写数据
								String sql = "("  + "'"+ processpn[processpn.length-1] +"'" + "," + "'"+ mldata[2] +"'" + "," + "'"+ mldata[1] +"'" + "," + "'"+ mldata[3] +"'" +  "," + "'"+ mldata[5] +"'" +  "," + "'"+ mldata[6] +"'" + ")";  	        	   		
								db.inserttable(insert + sql, 1);
								tmpString = mlreader.readLine();					
							}        	   		      	   			      	   		
							mlreader.close();
							db.commit();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					    }
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}				
			}
			else {
				System.out.println("自动上传君：度量日志无需上传！");
			}
			
	    }
	    System.out.println("自动度量日志上传成功！！！注意：我是自动上传的！！！");
	}

}
