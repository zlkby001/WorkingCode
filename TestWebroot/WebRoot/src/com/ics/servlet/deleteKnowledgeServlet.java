package com.ics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ics.model.Knowledge;
import com.ics.util.DBConnection;
import com.java1234.util.ResponseUtil;

public class deleteKnowledgeServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public deleteKnowledgeServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String delNames = request.getParameter("delNames");
		String names = URLDecoder.decode(delNames,"UTF-8");
		int lines = 0;
		String sql = "delete from knowledge where name in('"+ names +"');";
		String itemsql = "delete from knowledge_recommended where ics_name in('"+ names +"')";
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			stmt1 = con.createStatement();
			lines = stmt1.executeUpdate(sql);
			stmt1.executeUpdate(itemsql);
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
