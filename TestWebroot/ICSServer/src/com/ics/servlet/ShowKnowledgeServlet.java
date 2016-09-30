package com.ics.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ics.model.Knowledge;
import com.ics.util.DBConnection;

public class ShowKnowledgeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Knowledge> name = new ArrayList<Knowledge>();
		String sql = "SELECT DISTINCT ics_name FROM knowledge_recommended";
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			ResultSet rs1 = null;
			stmt1 = con.createStatement();
			rs1 = stmt1.executeQuery(sql);
			while (rs1.next()) {
				Knowledge knowledge = new Knowledge();
				knowledge.setIcs_name(rs1.getString("ics_name"));
				name.add(knowledge);
				System.out.println(rs1.getString("ics_name"));
			}
			rs1.close();
			stmt1.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("name", name);
		request.getRequestDispatcher("showKnowledge.jsp").forward(request,
				response);
	}
}
