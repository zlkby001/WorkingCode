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
import com.ics.model.Metric;
import com.ics.util.DBConnection;

public class ViewKnowledgeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		List<Knowledge> result = new ArrayList<Knowledge>();
		String ics_name = "";

		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null, stmt2 = null, stmt3 = null;
			ResultSet rs1 = null, rs2 = null, rs3 = null;

			ics_name = request.getParameter("ics_name");
			String sql1 = "SELECT hash_value FROM knowledge_recommended WHERE ics_name='"
					+ ics_name + "'";
			stmt1 = con.createStatement();
			rs1 = stmt1.executeQuery(sql1);
	

			while (rs1.next()) {
				Knowledge knowledge = new Knowledge();
				knowledge.setIcs_name(ics_name);
				knowledge.setHash_value(rs1.getString("hash_value"));

				con = DBConnection.getConnection();
				String sql2 = "SELECT * FROM knowledge_basic WHERE hash_value ='"
						+ rs1.getString("hash_value") + "'";
				stmt2 = con.createStatement();
				rs2 = stmt2.executeQuery(sql2);

				if (rs2.next()) {
					knowledge.setProcess_name(rs2.getString("process_name"));
					knowledge.setSoftware_ver(rs2.getString("software_ver"));
					knowledge.setOs_ver(rs2.getString("os_ver"));

					String sql3 = "SELECT * FROM knowledge_extension WHERE process_name ='"
							+ rs2.getString("process_name") + "'";
					stmt3 = con.createStatement();
					rs3 = stmt3.executeQuery(sql3);
					if (rs3.next()) {
						knowledge.setSoftware_info(rs3
								.getString("software_info"));
						knowledge
								.setRelease_date(rs3.getString("release_date"));
						knowledge
								.setManufacturer(rs3.getString("manufacturer"));
						knowledge.setIc_bool(rs3.getString("ic_bool"));
					}
					rs3.close();
					stmt3.close();
				} else {
					knowledge.setProcess_name("Unknown");
					knowledge.setSoftware_ver("Unknown");
					knowledge.setOs_ver("Unknown");
					knowledge.setSoftware_info("Unknown");
					knowledge.setRelease_date("Unknown");
					knowledge.setManufacturer("Unknown");
					knowledge.setIc_bool("Unknown");
				}
				rs2.close();
				stmt2.close();
				// System.out.println(knowledge);
				result.add(knowledge);
				con.close();
			}

			rs1.close();
			stmt1.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("result", result);
		request.getRequestDispatcher("viewKnowledge.jsp").forward(request,
				response);
	}
}
