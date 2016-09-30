package com.java1234.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ResponseUtil {

	public static void write(HttpServletResponse response,Object o){
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(o.toString());
		out.flush();
		out.close();
	}
	
	public static void writeJson(HttpServletResponse response,ResultSet list,int total){
		response.setContentType("text/html;charset=utf-8");
		try {
			//con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(list);
			//int total=jsonArray.size(); 	//数据表行数
			result.put("rows", jsonArray);
			result.put("total", total);
			write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
