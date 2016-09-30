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

import com.ics.model.Metric;
import com.ics.util.DBConnection;

public class SearchMetricServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Metric> results = new ArrayList<Metric>();

		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null, stmt2 = null, stmt3 = null;
			ResultSet rs1 = null, rs2 = null, rs3 = null;

			String sql1 = "SELECT * FROM metric_log ";
			if (request.getParameter("process_name") != "") {
				sql1 += "WHERE process_name = '"
						+ request.getParameter("process_name") + "' ";
				if (request.getParameter("metric_time1") != "") {
					sql1 += "and metric_time >= '"
							+ request.getParameter("metric_time1") + "' ";
					if (request.getParameter("metric_time2") != "")
						sql1 += "and metric_time <= '"
								+ request.getParameter("metric_time2") + "' ";
				} else {
					if (request.getParameter("metric_time2") != "")
						sql1 += "and metric_time <= '"
								+ request.getParameter("metric_time2") + "' ";
				}
			} else {
				if (request.getParameter("metric_time1") != "") {
					sql1 += "WHERE metric_time >= '"
							+ request.getParameter("metric_time1") + "' ";
					if (request.getParameter("metric_time2") != "")
						sql1 += "and metric_time <= '"
								+ request.getParameter("metric_time2") + "' ";
				} else {
					if (request.getParameter("metric_time2") != "")
						sql1 += "WHERE metric_time <= '"
								+ request.getParameter("metric_time2") + "' ";
				}

			}
			
			System.out.println(sql1);
			stmt1 = con.createStatement();
			rs1 = stmt1.executeQuery(sql1);
			while (rs1.next()) {

				con = DBConnection.getConnection();
				String sql2 = "SELECT * FROM knowledge_basic WHERE hash_value ='"
						+ rs1.getString("hash_value") + "' ";
				if (request.getParameter("hash_value") != "")
					sql2 += "and hash_value ='"
							+ request.getParameter("hash_value") + "'";
				System.out.println(sql2);
				stmt2 = con.createStatement();
				rs2 = stmt2.executeQuery(sql2);
				if (rs2.next()) {
					Metric metric = new Metric();
					metric.setProcess_name(rs1.getString("process_name"));
					metric.setProcess_path(rs1.getString("process_path"));
					metric.setHash_value(rs1.getString("hash_value"));
					metric.setMetric_time(rs1.getString("metric_time"));
					metric.setSoftware_ver(rs2.getString("software_ver"));
					metric.setOs_ver(rs2.getString("os_ver"));
					
					con = DBConnection.getConnection();
					String sql3 = "SELECT * FROM knowledge_extension WHERE process_name ='"
							+ rs1.getString("process_name") + "'";
					stmt3 = con.createStatement();
					rs3 = stmt3.executeQuery(sql3);
					if (rs3.next()) {
						metric.setSoftware_ver(rs3.getString("software_info"));
						metric.setRelease_date(rs3.getString("release_date"));
						metric.setManufacturer(rs3.getString("manufacturer"));
						metric.setIc_bool(rs3.getString("ic_bool"));
					}
					rs3.close();
					stmt3.close();
					results.add(metric);
				}
				rs2.close();
				stmt2.close();
				// System.out.println(metric);
			}

			// System.out.println(results);
			rs1.close();
			stmt1.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("results", results);
		request.getRequestDispatcher("viewMetric.jsp").forward(request,
				response);
	}
}
