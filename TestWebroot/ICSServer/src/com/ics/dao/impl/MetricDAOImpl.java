package com.ics.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ics.dao.MetricDAO;
import com.ics.util.DBConnection;

public class MetricDAOImpl implements MetricDAO {

	@Override
	public String queryMetricTime(String TCM_PK, String metric_time) {
		// TODO Auto-generated method stub
		try {
			String metrictime = "", metric_time1 = "", metric_time2 = "";
			Connection con = DBConnection.getConnection();
			Statement stmt = null;
			ResultSet rs1 = null, rs2 = null;
			stmt = con.createStatement();
			rs1 = stmt.executeQuery("SElECT * FROM metric_log WHERE TCM_PK ='"
					+ TCM_PK + "'");
			if (rs1.next()) {
				rs2 = stmt
						.executeQuery("SElECT max(metric_time) as maxtime FROM metric_log WHERE TCM_PK ='"
								+ TCM_PK + "'");
				if (rs2.next())
					metric_time1 = rs2.getString("maxtime").trim();
				metric_time2 = metric_time;
				metrictime += metric_time1 + ";;;" + metric_time2;
				rs2.close();
			} else {
				metrictime += "all";
			}

			rs1.close();
			stmt.close();
			con.close();
			return metrictime;

		} catch (SQLException el) {
			el.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean uploadMetricLog(String TCM_PK, String metriccontent) {
		// TODO Auto-generated method stub
		try {
			Connection con = DBConnection.getConnection();
			Statement Stmt = null;
			Stmt = con.createStatement();
			String[] content1 = metriccontent.split(";;;");
			for (int i = 0; i < content1.length; i++) {
				String[] content2 = content1[i].split("&&&");

				String sql = "Insert INTO metric_log(tcm_pk, client_id, process_name, process_path, hash_value, pprocess_name, pprocess_id, metric_time) VALUES('"
						+ TCM_PK;
				for (int j = 0; j < content2.length; j++) {
					sql += "','" + content2[j];
				}
				sql += "')";
				Stmt.executeUpdate(sql);
			}

			Stmt.close();
			con.close();
			return true;
		} catch (SQLException el) {
			el.printStackTrace();
			return false;
		}

	}
}
