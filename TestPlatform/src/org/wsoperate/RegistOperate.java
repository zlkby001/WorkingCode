package org.wsoperate;

import java.rmi.RemoteException;
import java.sql.SQLException;

import org.apache.axis2.AxisFault;
import org.dboperate.DBOperate;
import org.forms.XMLReader;

public class RegistOperate {

	public RegistOperate() {
		// TODO Auto-generated constructor stub
	}
	
	public String getlistinfo() {
		XMLReader xmlreader = new XMLReader();
		String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("registop");
		String result = null;
		try {
			RegistServiceStub stub = new RegistServiceStub(null, trans_value);
			RegistServiceStub.GetListInfo regfunction = new RegistServiceStub.GetListInfo();
			result = stub.getListInfo(regfunction).getOut();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}		
		;
		return result;		
	}
	
	public boolean store_tree(String result)
	{
		DBOperate db=org.forms.MainForm.db;
		int cur1=result.indexOf("***");
	    int cur2=result.indexOf("###");
	    String l1=result.substring(0,cur1);
	    String l2=result.substring(cur1+3, cur2);
	    String l3=result.substring(cur2+3,result.length());
	    try {
			db.cleartable("delete from l1");
			 db.commit();
			    db.cleartable("delete from l2");
			    db.commit();
			    db.cleartable("delete from l3");
			    db.commit();
				
			    String l1s[]=l1.split(";;;");
			    
			    for(int i=0;i<l1s.length;i++)
			    {
			    	String temp[]=l1s[i].split("&&&");
			    	if(temp.length==3)
			    	{
			    		db.inserttable("insert into l1(id,name,fid) values "+"('"+temp[0]+"','"+temp[1]+"','"+temp[2]+"')",4);		
			    	}
			    }
			    String l2s[]=l2.split(";;;");
			    
			    for(int i=0;i<l2s.length;i++)
			    {
			    	String temp[]=l2s[i].split("&&&");
			    	if(temp.length==3)
			    	{
			    		db.inserttable("insert into l2(id,name,fid) values "+"('"+temp[0]+"','"+temp[1]+"','"+temp[2]+"')",4);		
			    	}
			    }
			    
			    String l3s[]=l3.split(";;;");
			    for(int i=0;i<l3s.length;i++)
			    {
			    	String temp[]=l3s[i].split("&&&");
			    	if(temp.length==3)
			    	{
			    		db.inserttable("insert into l3(id,name,fid) values "+"('"+temp[0]+"','"+temp[1]+"','"+temp[2]+"')",4);		
			    	}
			    }
			    db.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	   
		return true;
	}

}
