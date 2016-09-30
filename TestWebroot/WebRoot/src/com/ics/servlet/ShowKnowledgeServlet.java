package com.ics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import cn.ac.sklois.imm.admin.AdminService;
import cn.ac.sklois.imm.admin.databasebean;

import com.ics.model.Knowledge;
import com.ics.util.DBConnection;
import com.java1234.util.ResponseUtil;

public class ShowKnowledgeServlet extends HttpServlet {
	/**
	 * Constructor of the object.
	 */
	public ShowKnowledgeServlet() {
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
		// List<Knowledge> name = new ArrayList<Knowledge>();

		// ==== 添加表格分页查询参数 7月29日 修改 =====================
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		if (limit == null || start == null || limit.equals("")
				|| start.equals("")) {
			start = "0";
			limit = "20";
		}
		
		String sql="";
		//if(databasebean.ifmysql)
		//{
		sql= "SELECT DISTINCT ics_name FROM knowledge_recommended limit "
				+ start + "," + limit;
		//}
		// ====================================================
	
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			ResultSet rs1 = null;
			stmt1 = con.createStatement();
			rs1 = stmt1.executeQuery(sql);
			
			// while (rs1.next()) {
			// Knowledge knowledge = new Knowledge();
			// knowledge.setIcs_name(rs1.getString("ics_name"));
			// name.add(knowledge);
			// System.out.println(rs1.getString("ics_name"));
			// }
			

			// ============== 7月29日 修改 =====================
			String sqlCount = "select COUNT(DISTINCT ics_name) as count FROM knowledge_recommended ";
			Statement stmt2 = con.createStatement();
			;
			ResultSet rs2 = stmt2.executeQuery(sqlCount);
			int count = 0;
			if (rs2.next()) {
				count = rs2.getInt("count");
			}
			ResponseUtil.writeJson(response, rs1, count);
			rs2.close();
			// ===============================================

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
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
