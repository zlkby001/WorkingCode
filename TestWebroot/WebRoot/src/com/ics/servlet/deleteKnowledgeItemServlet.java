package com.ics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ics.util.DBConnection;
import com.java1234.util.ResponseUtil;

public class deleteKnowledgeItemServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public deleteKnowledgeItemServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	//modified by zhyjun at 2015.5.10
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		
		int lines = 0;
		String sql = "delete from knowledge_item where id in ("+ id +")";
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			stmt1 = con.createStatement();
			lines = stmt1.executeUpdate(sql);
			
			JSONObject result=new JSONObject();
			if(lines>0){
				result.put("success", "true");
			}
			else{
				result.put("errorMsg", "删除失败");
			}
			try {
				ResponseUtil.write(response, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			stmt1.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("result", lines);		
	}
	
	/*
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String hashValues = request.getParameter("hashValues");
		String ics_name = request.getParameter("ics_name");
		if(ics_name!=null)
			ics_name = URLDecoder.decode(ics_name,"UTF-8");
		
		int lines = 0;
		String sql = "delete from knowledge_recommended where hash_value in ("+ hashValues +") and ics_name='" + ics_name + "'";
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			stmt1 = con.createStatement();
			lines = stmt1.executeUpdate(sql);
			
			JSONObject result=new JSONObject();
			if(lines>0){
				result.put("success", "true");
			}
			else{
				result.put("errorMsg", "删除失败");
			}
			try {
				ResponseUtil.write(response, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			stmt1.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("result", lines);		
	}*/
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
