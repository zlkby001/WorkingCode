package org.dboperate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

import org.dboperate.DBOperate;


public class AlarmLogUpdate { 
	private DBOperate db;
	private IntByReference count = new IntByReference();		
    static final String insert ="insert into errorlist values ";
    
    private String filePath = "C:/TCC/errorlist.log";//"C:/errorlist.log";	        	         	
    private File file = new File(filePath);	
    private BufferedReader reader = null; 
    
    private int number = 0;
    
    public AlarmLogUpdate(DBOperate maindb) {
    	db = maindb;
    }
    
	public void update() {
		if(!file.exists()) {
			System.out.println("报警日志文件不存在");
			return;
		}
		number = 0;		
		try {			
			reader = new BufferedReader(new FileReader(file));
			String tmpString = reader.readLine();        	   	
			while((tmpString  != null) && !tmpString.equals("")) { 
				//获取报警日志     
				String aldata[] = null;
				aldata = tmpString.split(" "); 
				String processpn[] = aldata[1].split("/");
				//查询数据库，判断该条报警日志是否已写入
				ResultSet rs = db.selecttable("select * from errorlist where processname = " + "'" + processpn[processpn.length-1] + "'" + " and " + "path=" + "'" + aldata[1] + "'" + " and " + "hashvalue=" + "'" + aldata[0] + "'" + " and " + "time=" + "'" + aldata[2] + "'", 2);
				if(rs.next()) {	//记录已存在
					rs.close();
					tmpString = reader.readLine();
					continue;
				}
				else {	//记录不存在
					rs.close();
					String sql = "("  + "'"+ processpn[processpn.length-1] +"'" + "," + "'" + aldata[0] + "'"+ "," + "'"+ aldata[2] +"'" + "," + "'"+ aldata[1] +"'" + ")";
					db.inserttable(insert + sql, 2);
					tmpString = reader.readLine();
					number++;
				}					      	   		
			}
			reader.close();
	   		db.commit();
	   		System.out.println("报警日志数据库新增记录数目："+number);
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
