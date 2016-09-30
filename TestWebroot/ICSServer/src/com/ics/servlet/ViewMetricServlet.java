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

public class ViewMetricServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Metric> results = new ArrayList<Metric>();

		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null, stmt2 = null, stmt3 = null;
			ResultSet rs1 = null, rs2 = null, rs3 = null;

			String sql1 = "SELECT * FROM metric_log";
			stmt1 = con.createStatement();
			rs1 = stmt1.executeQuery(sql1);

			while (rs1.next()) {
				Metric metric = new Metric();
				metric.setProcess_name(rs1.getString("process_name"));
				metric.setProcess_path(rs1.getString("process_path"));
				metric.setHash_value(rs1.getString("hash_value"));
				metric.setMetric_time(rs1.getString("metric_time"));

				con = DBConnection.getConnection();
				String sql2 = "SELECT * FROM knowledge_basic WHERE hash_value ='"
						+ rs1.getString("hash_value")
						+ "' and process_name = '"
						+ rs1.getString("process_name") + "'";
				stmt2 = con.createStatement();
				rs2 = stmt2.executeQuery(sql2);
				if (rs2.next()) {
					if (rs2.getString("software_ver") != null)
						metric.setSoftware_ver(rs2.getString("software_ver"));
					else
						metric.setSoftware_ver("Unknown");
					if (rs2.getString("os_ver") != "")
						metric.setOs_ver(rs2.getString("os_ver"));
					else
						metric.setOs_ver("Unknown");

				} else {
					metric.setSoftware_ver("Unknown");
					metric.setOs_ver("Unknown");
				}
				rs2.close();
				stmt2.close();

				String sql3 = "SELECT * FROM knowledge_extension WHERE process_name ='"
						+ rs1.getString("process_name") + "'";
				stmt3 = con.createStatement();
				rs3 = stmt3.executeQuery(sql3);
				if (rs3.next()) {

					metric.setSoftware_info(rs3.getString("software_info"));
					metric.setRelease_date(rs3.getString("release_date"));
					metric.setManufacturer(rs3.getString("manufacturer"));
					metric.setIc_bool(rs3.getString("ic_bool"));
				} else {
					metric.setSoftware_info("Unknown");
					metric.setRelease_date("Unknown");
					metric.setManufacturer("Unknown");
					metric.setIc_bool("Unknown");
				}
				rs3.close();
				stmt3.close();

				String sql4 = "SELECT ics_name FROM knowledge_recommended WHERE hash_value ='"
						+ rs1.getString("hash_value") + "'";
				stmt3 = con.createStatement();
				rs3 = stmt3.executeQuery(sql4);
				String icsname = "";
				while (rs3.next()) {
					icsname += rs3.getString("ics_name") + ";";

				}
				// System.out.println(icsname);
				metric.setIcs_name(icsname);
				rs3.close();
				stmt3.close();
				results.add(metric);
			}
			System.out.println(results);
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
