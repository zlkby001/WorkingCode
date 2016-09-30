package com.ics.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ics.util.DBConnection;

public class SupplyKnowledgeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String process_name = "", hash_value = "", software_ver = "", os_ver = "", software_info = "", release_date = "", manufacturer = "", ic_bool = "", ics_name = "";

		if (request.getParameter("process_name") != "")
			process_name = request.getParameter("process_name");
		if (request.getParameter("hash_value") != "")
			hash_value = request.getParameter("hash_value");
		if (request.getParameter("software_ver") != "")
			software_ver = request.getParameter("software_ver");
		if (request.getParameter("os_ver") != "")
			os_ver = request.getParameter("os_ver");
		if (request.getParameter("software_info") != "")
			software_info = request.getParameter("software_info");
		if (request.getParameter("release_date") != "")
			release_date = request.getParameter("release_date");
		if (request.getParameter("manufacturer") != "")
			manufacturer = request.getParameter("manufacturer");
		if (request.getParameter("ic_bool") != "")
			ic_bool = request.getParameter("ic_bool");
		if (request.getParameter("ics_name") != "")
			ics_name = request.getParameter("ics_name");

		String sql12 = "INSERT INTO knowledge_basic VALUES('" + hash_value
				+ "','" + process_name + "','" + software_ver + "','" + os_ver
				+ "')";
		String sql22 = "INSERT INTO knowledge_extension VALUES('"
				+ software_ver + "','" + process_name + "','" + software_info
				+ "','" + release_date + "','" + manufacturer + "','" + ic_bool
				+ "')";
		String sql32 = "INSERT INTO knowledge_recommended VALUES('" + ics_name
				+ "','" + hash_value + "')";

		String sql11 = "UPDATE knowledge_basic SET hash_value ='" + hash_value
				+ "',process_name ='" + process_name + "',software_ver = '"
				+ software_ver + "',os_ver ='" + os_ver
				+ "' where hash_value='" + hash_value + "'";
		String sql21 = "UPDATE knowledge_extension SET software_ver='"
				+ software_ver + "',process_name='" + process_name
				+ "',software_info='" + software_info + "',release_date='"
				+ release_date + "',manufacturer='" + manufacturer
				+ "',ic_bool ='" + ic_bool + "' where software_ver='"
				+ software_ver + "' and process_name ='" + process_name + "'";
		String sql31 = "UPDATE knowledge_recommended SET ics_name='" + ics_name
				+ "',hash_value='" + hash_value + "' where hash_value='"
				+ hash_value + "'";

		String sql1 = "SELECT * FROM knowledge_basic WHERE hash_value = '"
				+ hash_value + "'";
		String sql2 = "SELECT * FROM knowledge_extension WHERE software_ver='"
				+ software_ver + "' and process_name ='" + process_name + "'";
		String sql3 = "SELECT * FROM knowledge_recommended WHERE hash_value='"
				+ hash_value + "' and ics_name = '" + ics_name + "'";

		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null, stmt2 = null;
			ResultSet rs1 = null;

			stmt1 = con.createStatement();
			stmt2 = con.createStatement();
			rs1 = stmt1.executeQuery(sql1);
			if (rs1.next())
				stmt2.executeUpdate(sql11);
			else
				stmt2.executeUpdate(sql12);
			stmt2.close();
			rs1.close();
			stmt1.close();

			stmt1 = con.createStatement();
			stmt2 = con.createStatement();
			rs1 = stmt1.executeQuery(sql2);
			if (rs1.next())
				stmt2.executeUpdate(sql21);
			else
				stmt2.executeUpdate(sql22);
			stmt2.close();
			rs1.close();
			stmt1.close();

			stmt1 = con.createStatement();
			stmt2 = con.createStatement();
			rs1 = stmt1.executeQuery(sql3);
			if (rs1.next())
				stmt2.executeUpdate(sql31);
			else
				stmt2.executeUpdate(sql32);
			stmt2.close();
			rs1.close();
			stmt1.close();

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
