package com.ics.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.sql.DriverManager;


import com.ics.dao.RegistDAO;
import com.ics.util.DBConnection;

public class RegistDAOImpl implements RegistDAO {

	public int max=10;
	@Override
	public int register(String pubkey, String sequence, String manufacture,
			String Ip, String mac, String os, String client_area, String Name,
			String device_info, String device_type, String EK) {

		// TODO Auto-generated method stub
		try {

			Connection con = DBConnection.getConnection();
			Statement stmt = null;
			ResultSet rs = null;
			stmt = con.createStatement();

			rs = stmt.executeQuery("select count(id) as count from users");
			if(rs.next())
			{
				int count= rs.getInt("count");
				if(count >= max)
					return 2;
			}
			else
				return 0;
			rs = stmt.executeQuery("SElECT * FROM users where pubkey = '"
					+ pubkey + "'or EK ='" + EK + "'");

			if (rs.next()) {
				rs.close();
				stmt.close();
				con.close();
				return 0;

			} else {
				String sql1="select c.id from l1 a,l2 b,l3 c where a.name='"+client_area+"' and b.name='"+Name+"' and c.name='"+device_type+"' and c.fid=b.id and b.fid=a.id";
				rs = stmt.executeQuery(sql1);
				if(!rs.next())
				{
					rs.close();
					stmt.close();
					con.close();
					return 0;
				}
				String id=rs.getString("id");
				/*
				String sql = "INSERT INTO users(pubkey, sequence, manufacture, Ip, mac, os, client_area, Name, device_type, device_info, EK,fid) VALUES('"
						+ pubkey
						+ "','"
						+ sequence
						+ "','"
						+ manufacture
						+ "','"
						+ Ip
						+ "','"
						+ mac
						+ "','"
						+ os
						+ "','"
						+ client_area
						+ "','"
						+ device_info
						+ "','"
						+ device_type
						+ "','" + Name + "','" + EK + "'" + ","+id+")";
				*/
				
				//alert”≈ªØ∫Û
				String sql = "INSERT INTO users(pubkey, sequence, manufacture, Ip, mac, os, client_area, Name, device_type, device_info, EK,fid,alertcount,latestalertcount,alertdate) VALUES('"
						+ pubkey
						+ "','"
						+ sequence
						+ "','"
						+ manufacture
						+ "','"
						+ Ip
						+ "','"
						+ mac
						+ "','"
						+ os
						+ "','"
						+ client_area
						+ "','"
						+ device_info
						+ "','"
						+ device_type
						+ "','" + Name + "','" + EK + "'" + ","+id+",0,0,'2011-01-01')";
				stmt.executeUpdate(sql);
				rs.close();
				stmt.close();
				con.close();
				return 1;
			}

		} catch (SQLException el) {
			el.printStackTrace();
			return 0;
		}

	}
	/*
	public boolean register(String pubkey, String sequence, String manufacture,
			String Ip, String mac, String os, String client_area, String Name,
			String device_info, String device_type, String EK) {

		// TODO Auto-generated method stub
		try {

			Connection con = DBConnection.getConnection();
			Statement stmt = null;
			ResultSet rs = null;
			stmt = con.createStatement();

			rs = stmt.executeQuery("SElECT * FROM users where pubkey = '"
					+ pubkey + "'or EK ='" + EK + "'");

			if (rs.next()) {
				rs.close();
				stmt.close();
				con.close();
				return false;

			} else {
				String sql = "INSERT INTO users(pubkey, sequence, manufacture, Ip, mac, os, client_area, Name, device_type, device_info, EK) VALUES('"
						+ pubkey
						+ "','"
						+ sequence
						+ "','"
						+ manufacture
						+ "','"
						+ Ip
						+ "','"
						+ mac
						+ "','"
						+ os
						+ "','"
						+ client_area
						+ "','"
						+ Name
						+ "','"
						+ device_type
						+ "','" + device_info + "','" + EK + "'" + ")";
				stmt.executeUpdate(sql);
				rs.close();
				stmt.close();
				con.close();
				return true;
			}

		} catch (SQLException el) {
			el.printStackTrace();
			return false;
		}

	}*/
	
	public String getListInfo()
	{
		String tree="";
		try
		{
			Connection con = DBConnection.getConnection();
			Statement stmt = null;
			stmt = con.createStatement();		
			String sql="select id,name,fid from l1";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next())
				tree+=rs.getString("id")+"&&&"+rs.getString("name")+"&&&"+rs.getString("fid")+";;;";
			tree+="***";
			sql="select id,name,fid from l2";
			rs=stmt.executeQuery(sql);
			while(rs.next())
				tree+=rs.getString("id")+"&&&"+rs.getString("name")+"&&&"+rs.getString("fid")+";;;";
			tree+="###";
			sql="select id,name,fid from l3";
			rs=stmt.executeQuery(sql);
			while(rs.next())
				tree+=rs.getString("id")+"&&&"+rs.getString("name")+"&&&"+rs.getString("fid")+";;;";
			
			rs.close();
			stmt.close();
			con.close();
			
			
		}catch(SQLException el)
		{
			el.printStackTrace();
			return null;
		}
		return tree;
	}
}
