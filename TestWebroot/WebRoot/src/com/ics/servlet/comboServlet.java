package com.ics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.databasebean;

import com.ics.util.DBConnection;
import com.java1234.util.JsonUtil;
import com.java1234.util.ResponseUtil;

public class comboServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public comboServlet() {
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
		
		String type = request.getParameter("type");
		
		//String name = URLDecoder.decode(ics_name,"UTF-8");
		
		String sql = "";
		//System.out.print("=== viewKnowledge sql ===  "+ sql +"  ==============");
		if(type.equals("userlist"))
		{
			sql = "select id as userid,name as username from users";
		}else if(type.equals("verifydate")){
			String userId = request.getParameter("userId");
			if(userId==null||userId.equals("")){
				ResponseUtil.write(response,"{\"date\":\"\"}");
				return;
			}
			if(databasebean.ifmysql)
				sql = "select DISTINCT date from verify_log WHERE userid=" + userId + " ORDER BY date desc LIMIT 5";			
			else
				sql = "select first 5 DISTINCT date from verify_log WHERE userid=" + userId + " ORDER BY date desc";
		}
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			ResultSet rs1 = null;
			stmt1 = con.createStatement();
			rs1 = stmt1.executeQuery(sql);
			
			ResponseUtil.write(response, JsonUtil.formatRsToJsonArray(rs1));

			rs1.close();
			stmt1.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// request.setAttribute("name", name);
		// request.getRequestDispatcher("showKnowledge.jsp").forward(request,
		// response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
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
