package ui;
import Integrity.IntegrityProducer;
import Integrity.PlatformMeasureBean;
import java.io.*;
import java.util.*;
import java.sql.*;

import org.dboperate.DBOperate;
import org.forms.Methods;

public class Loader {
	private IntegrityProducer producer = IntegrityProducerGenerator.getProducer();
	private ArrayList list = new ArrayList();
	private ArrayList white_list = new ArrayList();
	private ArrayList list1 = new ArrayList();
	private Connection dbcon=null;
	private Statement dbstate=null;
	private ResultSet dbrs=null;
	private DBOperate db=org.forms.MainForm.db;
	
	public Loader(){
		openConnection();
	}
//add by Yangbo
	/**
	 * 读取和设置全部报警信息
	 * @return
	 */
	public void getErrorlistItemDescription(ErrorListBean elb){
		
		String[] path = elb.getProgramName().split("/");			
		try{			
			String[] info = getDetails(path[path.length-1]);			
			elb.setTypeName(info[0]);
			elb.setProcessDescription(info[1]);	//知识库描述信息
		}catch(Exception e){
		}		
	}
	
	
	
	/**
	 * 读取全部进程信息
	 * @return
	 */
	public ArrayList getMeasurements(){
		list.clear();
		Vector vecrot = producer.getMeasurements();		
//		System.out.println();
		openConnection();		
		for(int i = 0; i < vecrot.size(); i++)
		{
			PlatformMeasureBean pmb = (PlatformMeasureBean)vecrot.get(i);
			// Program
			MeasurementBean mb = new MeasurementBean();
			//获取软件名
			String name1  = pmb.getProgramName().trim();
			File tempFile =new File( name1.trim()); 
		    String fileName = tempFile.getName();
			mb.setIndex(i+1);		//序号（自定义）
			mb.setProgram_name(pmb.getProgramName());		//进程名
			mb.setMv( pmb.getMeasureHexValue());		//度量值（Hash值）
			mb.setProcessID(pmb.getProcessID());//进程ID
			mb.setSoftwareName(fileName);
			//mb.setSoftwareName(pmb.getSoftwareName());	//软件名
			mb.setType(pmb.getType());		//进程类型
			
			String[] path = pmb.getProgramName().split("/");			
			try{			
				String[] info = getDetails(path[path.length-1]);			
				mb.setTypeName(info[0]);
				mb.setProcessDescription(info[1]);	//知识库描述信息
			}catch(Exception e){
			}

			list.add(mb); 
		}
//		System.out.println("Size of list_1:"+list.size());
		return list;
	}
	
	/**	
	 * 当combo选项为Active时，读取活动进程信息
	 * 
	 */
	public ArrayList getMeasurements(String component){
		list.clear();
		Vector vecrot = producer.getMeasurements(component);
		openConnection();
		
		for(int i = 0; i < vecrot.size(); i++)
		{
			PlatformMeasureBean pmb = (PlatformMeasureBean)vecrot.get(i);
			// Program
			MeasurementBean mb = new MeasurementBean();
			//获取软件名
			String name1  = pmb.getProgramName().trim();
			File tempFile =new File( name1.trim()); 
		    String fileName = tempFile.getName();
		
			mb.setIndex(i+1);
			mb.setProgram_name(pmb.getProgramName());
			mb.setMv( pmb.getMeasureHexValue());
			mb.setProcessID(pmb.getProcessID());
			mb.setSoftwareName(fileName);
			//mb.setSoftwareName(pmb.getSoftwareName());
			mb.setType(pmb.getType());
			String[] path = pmb.getProgramName().split("/");
			try{			
				String[] info = getDetails(path[path.length-1]);			
				mb.setTypeName(info[0]);
				mb.setProcessDescription(info[1]);	
				}catch(Exception e){
				}	
			
			list.add(mb); 
		}
//		System.out.println("Size of list_2:"+list.size());
		return list;
	}
	@SuppressWarnings("unchecked")
	public ArrayList getWhiteList1(){
		Comparator<MeasurementBean> comparator = new Comparator<MeasurementBean>(){
			   public int compare(MeasurementBean m1, MeasurementBean m2) {
			    //首先比较名字，如果相同则比较时间
				//   return m1.getMv().compareTo(m2.getMv());
				   int flag=m1.getProgram_name().compareTo(m1.getProgram_name());
				   if(flag==0){
				    return m1.getMv().compareTo(m1.getMv());
				   }else{
				    return flag;
				   } 
			    
			   }
		};
		if (list1.size() != 0) {
			list1.clear();
		}
		if (white_list.size() != 0) {
			white_list.clear();
		}
		
		list1.clear();
		Vector vector = producer.getWhiteList();
		openConnection();
//		if(vector==null)System.out.println("Null............");
		for(int i = 0; i < vector.size(); i++)
		{
			ProcessInfoBean prb = (ProcessInfoBean)vector.get(i);
			// Program
			String name  = prb.getFileName();	
			MeasurementBean mb = new MeasurementBean();
			mb.setIndex(i+1);
			mb.setProgram_name(name);
			mb.setMv( prb.getMeasure());			
			mb.setSoftwareName(name);	
		
			String[] path = name.split("/");
			try{			
				String[] info = getDetails(path[path.length-1]);	//取最后一个元素			
				mb.setTypeName(info[0]);
				mb.setProcessDescription(info[1]);	
				}catch(Exception e){
				}	
			
				list1.add(mb); 
		}
	//	System.out.println("list1:"+list1.size());
		Collections.sort(list1, comparator);
		int errlen = list1.size();
		String oldfilename = " ";
		String starttime = "";
		int index = 0;
		int j = 0, nums = 0;
		for(int i =0; i < errlen; i++)
		{
			MeasurementBean temp = (MeasurementBean)list1.get(i);
			//AlertLogMergerBean am = new AlertLogMergerBean();
			if(!temp.getMv().equals(oldfilename))
			{
				String pname = temp.getMv();
				oldfilename = pname;
			
			}
			if(i==errlen -1)
			{//the last one
				WhiteFilterBean am = new WhiteFilterBean();
				
				am.setProgramName(temp.getProgram_name());
				am.setMeasureHexValue(temp.getMv());
				index+=1;
				am.setIndex(index);
				am.setProcessDescription(temp.getProcessDescription());
				am.setSoftwareName(temp.getSoftwareName());
				am.setTypeName(temp.getTypeName());
				white_list.add(0, am);
				nums = 0;	
				break;
			}
			
			MeasurementBean tempx = (MeasurementBean)list1.get(i+1);
			if(tempx.getMv().equals(temp.getMv()))
			{
				nums++;
			}else
			{
				WhiteFilterBean am = new WhiteFilterBean();
			//	am.setStarttime(starttime);
				am.setProgramName(temp.getProgram_name());
				am.setMeasureHexValue(temp.getMv());
			
				//am.setEndtime(temp.getMeasuretime());
				//am.setLog_nums(nums+1);
				index+=1;
				am.setIndex(index);
				am.setProcessDescription(temp.getProcessDescription());
				am.setSoftwareName(temp.getSoftwareName());
				am.setTypeName(temp.getTypeName());
				white_list.add(0, am);
				nums = 0;			
			}				
		}
//		System.out.println("Size of list_2:"+list.size());
		//System.out.println("white_list"+white_list.size());
		
		return white_list;
	}
	
	/**
	 * 获取白名单
	 * @param component
	 * @return
	 */
	public ArrayList getWhiteList(){
		list.clear();
		Vector vector = producer.getWhiteList();
		openConnection();
//		if(vector==null)System.out.println("Null............");
		for(int i = 0; i < vector.size(); i++)
		{
			ProcessInfoBean prb = (ProcessInfoBean)vector.get(i);
			// Program
			String name  = prb.getFileName();	
			MeasurementBean mb = new MeasurementBean();
			mb.setIndex(i+1);
			mb.setProgram_name(name);
			mb.setMv( prb.getMeasure());			
			mb.setSoftwareName(name);			
			String[] path = name.split("/");
			try{			
				String[] info = getDetails(path[path.length-1]);	//取最后一个元素			
				mb.setTypeName(info[0]);
				mb.setProcessDescription(info[1]);	
				}catch(Exception e){
				}	
			
			list.add(mb); 
		}
//		System.out.println("Size of list_2:"+list.size());
		return list;
	}
	
	/**
	 * 获取验证失败列表
	 * @param component
	 * @return
	 */
	public ArrayList getFailLog(){
		list.clear();
		Vector vector = producer.getFailLog();
		openConnection();
		
		for(int i = 0; i < vector.size(); i++)
		{
			PlatformMeasureBean prb = (PlatformMeasureBean)vector.get(i);
			
			String name  = prb.getProgramName();			
			MeasurementBean mb = new MeasurementBean();
			mb.setIndex(i+1);
			mb.setProgram_name(name);
			mb.setMv( prb.getMeasureHexValue());			
			mb.setSoftwareName(name);			
			try{			
				String[] info = getDetails(name);			
				mb.setTypeName(info[0]);
				mb.setProcessDescription(info[1]);	
				}catch(Exception e){
				}	
			list.add(mb); 
		}
//		System.out.println("Size of list_2:"+list.size());
		return list;
	}
	
	
	
	/**
	 * 打开数据库连接
	 * @return
	 */
	public boolean openConnection(){
		try {
			Class.forName("org.sqlite.JDBC");
			//dbcon = DriverManager.getConnection("jdbc:sqlite:ExeRepository.db");
			Properties info=new Properties();
			info.put("characterEncoding", "UTF-8");
			//info.put("useUnicode", "FALSE");
			//this.dbcon = DriverManager.getConnection("jdbc:sqlite:ExeRepository.db", info);
			this.dbcon = DriverManager.getConnection("jdbc:sqlite:data.db", info);
			//this.dbstate= dbcon.createStatement();	
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace();
			return false;
		}
	}

	
	/**
	 * 读取进程信息
	 * @param name
	 * @return
	 */
	public String[] getDetails(String name){
		try {
			this.dbstate= dbcon.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String sql="select * from proginfo where pname like '"+ name +"';";
		String sql="select * from info where processname like '"+ name +"';";
		String[] info = new String[2];
		
		//首先检查是否存在数据，若不存在则退出	
		try {
			try {
				dbrs=db.selecttable(sql, 1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//dbrs=dbstate.executeQuery(sql);
			
			if(!dbrs.next()){
				info[0] = "未知";
				info[1] = "未知";
				return info;
			}
			//info[0] = dbrs.getString("stype");	//进程描述			
			//info[1] = dbrs.getString("description");		//进程类型	
			info[0] = dbrs.getString("isipc");	//进程描述			
			info[1] = dbrs.getString("info");		//进程类型	
			

//			System.out.print(sql);
//			System.out.print(info[0] + "  ");
//			System.out.println(info[1]);
			dbrs.close();
			this.dbstate.close();
			
			return info;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
			
			return null;
		}		
	}
	
	/**
	 * 关闭数据库连接
	 */
	public boolean closeConnection()
	{
		try {
			dbrs.close();
			dbstate.close();
			dbcon.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
}
