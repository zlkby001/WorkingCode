package cn.ac.sklois.imm.mappings;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Timestamp;

import cn.ac.sklois.imm.admin.Logtest;

public class AttIDtoNameDBBean {

	private Connection connection=null;
	Logtest log = new Logtest();
	AttIDtoNameDBBean(){//构造函数
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/immdb?user=tcc&password=tcc_123!&useUnicode=true&characterEncoding=utf-8");
			//connection=DriverManager.getConnection("jdbc:mysql://192.168.4.125:3306/immdb?user=tcc&password=tcc_123!&useUnicode=true&characterEncoding=utf-8");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public AttIDtoName findbyAttID(int aid){
		AttIDtoName a=new AttIDtoName();
		try {
			Statement statement=connection.createStatement();
			String strSql="select * from attidtoname where AttributeID = "+aid;
			ResultSet resultSet=statement.executeQuery(strSql);
			if(resultSet.next()){
				
				a.setAttributeID(aid);
				a.setAttributeName(resultSet.getString("AttributeName"));
				a.setAttributeClass(resultSet.getString("AttributeClass"));
				
				
			}else return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
	
	public AttIDtoName findbyAttName(String aname){
		AttIDtoName a=new AttIDtoName();
		try {
			Statement statement=connection.createStatement();
			String strSql="select * from attidtoname where AttributeName = '"+aname+"'";
			ResultSet resultSet=statement.executeQuery(strSql);
			if(resultSet.next()){
				
				a.setAttributeID(resultSet.getInt("AttributeID"));
				a.setAttributeName(resultSet.getString("AttributeName"));
				a.setAttributeClass(resultSet.getString("AttributeClass"));
				
				
			}else return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
	
	
	public ArrayList findAll(){
		ArrayList c=new ArrayList();
		try {
			Statement statement=connection.createStatement();
			String strSql="select * from attidtoname";
			ResultSet resultSet=statement.executeQuery(strSql);
			while(resultSet.next()){
				AttIDtoName a=new AttIDtoName();
				
				a.setAttributeID(resultSet.getInt("AttributeID"));
				a.setAttributeName(resultSet.getString("AttributeName"));
				a.setAttributeClass(resultSet.getString("AttributeClass"));
				
				c.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return c;
	}
	
	
	public ArrayList findbyAttIDNameClass(int aid,String aname,String aclass){
		ArrayList c=new ArrayList();
		try {
			Statement statement=connection.createStatement();
			String strSql="select * from attidtoname";
			
			if (aid!=-1||aname!=null||aclass!=null){
				strSql+=" where ";
			}
			if (aid!=-1){
				strSql+=" AttributeID = "+aid;
			}
			if (aname!=null){
				if (aid!=-1) strSql+=" and ";
				strSql+=" AttributeName LIKE '%"+aname+"%' ";
			}
			if (aclass!=null){
				if (aid!=-1||aname!=null) strSql+=" and ";
				strSql+=" AttributeClass = '"+aclass+"'";
			}
			log.logger.info(strSql);
			ResultSet resultSet=statement.executeQuery(strSql);
			while(resultSet.next()){
				AttIDtoName a=new AttIDtoName();
				
				a.setAttributeID(resultSet.getInt("AttributeID"));
				a.setAttributeName(resultSet.getString("AttributeName"));
				a.setAttributeClass(resultSet.getString("AttributeClass"));
				
				c.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return c;
	}
	
	public ArrayList findAttsbyAttName(String aname){
		ArrayList c=new ArrayList();
		try {
			Statement statement=connection.createStatement();
			String strSql="select * from attidtoname";
			
			if (aname!=null){
				strSql+=" where ";
				strSql+=" AttributeName LIKE '%"+aname+"%' ";
			}
			
			log.logger.info(strSql);
			ResultSet resultSet=statement.executeQuery(strSql);
			while(resultSet.next()){
				AttIDtoName a=new AttIDtoName();
				
				a.setAttributeID(resultSet.getInt("AttributeID"));
				a.setAttributeName(resultSet.getString("AttributeName"));
				a.setAttributeClass(resultSet.getString("AttributeClass"));
				
				c.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return c;
	}
	
	
	
	public boolean deletebyAttID(int aid){
		
		try {
			Statement statement=connection.createStatement();
			String strSql="delete from attidtoname where AttributeID = "+aid;
			statement.executeUpdate(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean add(AttIDtoName a){
		
		Statement statement;
		try {
			statement = connection.createStatement();
			String strSql="insert into attidtoname (AttributeID, AttributeName, AttributeClass) " +
					"value("+a.getAttributeID()+",'"+a.getAttributeName()+"', '"+a.getAttributeClass()+"')";//构建语句
			statement.executeUpdate(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	public boolean updatebyAttID(AttIDtoName a){
		try {
			Statement statement=connection.createStatement();
			String strSql="update attidtoname set AttributeID= "+a.getAttributeID()+" , AttributeName='"+a.getAttributeName()+"' , AttributeClass='"+a.getAttributeClass()+"' where AttributeID ="+a.getAttributeID();//构建语句
			statement.executeUpdate(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	
	
	public boolean destroy(){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
}
