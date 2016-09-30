package com.java1234.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import cn.ac.sklois.imm.admin.AdminService;
import cn.ac.sklois.imm.admin.databasebean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	static Connection connection = null;
	static String ip;
	static String username;
	static String verifydate;
	static String pubkey;

	
	/**
	 * 把ResultSet集合转换成JsonArray数组
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static JSONArray formatRsToJsonArray(ResultSet rs) throws Exception {
		ResultSetMetaData md = rs.getMetaData();
		int num = md.getColumnCount();
		JSONArray array = new JSONArray();
		while (rs.next()) {
			JSONObject mapOfColValues = new JSONObject();
			for (int i = 1; i <= num; i++) {
				String name = md.getColumnName(i);
				mapOfColValues.put(name, rs.getObject(i));
			}
			array.add(mapOfColValues);
		}
		return array;
	}

}
