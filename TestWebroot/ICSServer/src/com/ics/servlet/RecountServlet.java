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

public class RecountServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Connection con = DBConnection.getConnection();
			Statement Stmt1 = null, Stmt2 = null, Stmt3 = null, Stmt4 = null, Stmt5 = null;
			ResultSet rs1 = null, rs2 = null, rs3 = null, rs5 = null;

			Stmt1 = con.createStatement();
			String sql1 = "SELECT current_statsid FROM knowledge_updatetab";
			rs1 = Stmt1.executeQuery(sql1);// 查询当前统计标识

			int a = 0;
			if (rs1.next()) {
				a = rs1.getInt("current_statsid");

				Stmt2 = con.createStatement();
				String sql2 = "SELECT metric_id,hash_value,tcm_pk FROM metric_log WHERE metric_id >"
						+ a;
				rs2 = Stmt2.executeQuery(sql2);// 查询大于统计标识的id

				String hashvalue = "", pk = "";
				int id = 0;
				while (rs2.next()) {
					hashvalue = rs2.getString("hash_value");
					pk = rs2.getString("tcm_pk");
					id = rs2.getInt("metric_id");

					Stmt3 = con.createStatement();
					String sql3 = "SELECT hash_count FROM knowledge_stats WHERE hash_value='"
							+ hashvalue + "'";
					rs3 = Stmt3.executeQuery(sql3);

					if (rs3.next()) {

						Stmt5 = con.createStatement();
						String sql5 = "SELECT metric_id FROM metric_log WHERE hash_value='"
								+ hashvalue
								+ "'and tcm_pk ='"
								+ pk
								+ "'and metric_id <" + id;
						System.out.println(sql5);
						rs5 = Stmt5.executeQuery(sql5);
						if (rs5.next()) {
						} else {
							int b = rs3.getInt("hash_count") + 1;
							Stmt4 = con.createStatement();
							String sql4 = "UPDATE knowledge_stats SET hash_count = "
									+ b
									+ " WHERE hash_value='"
									+ hashvalue
									+ "'";
							System.out.println(sql4);
							Stmt4.executeUpdate(sql4);
							Stmt4.close();
						}
						rs5.close();
						Stmt5.close();

					} else {

						Stmt4 = con.createStatement();
						String sql4 = "INSERT INTO knowledge_stats VALUES('"
								+ hashvalue + "'," + 1 + ")";
						Stmt4.executeUpdate(sql4);
						Stmt4.close();

					}
					rs3.close();
					Stmt3.close();
				}
				rs2.close();
				Stmt2.close();

				Stmt2 = con.createStatement();
				Stmt3 = con.createStatement();
				String sql6 = "SELECT max(metric_id) as maxid FROM metric_log";
				rs2 = Stmt2.executeQuery(sql6);
				if (rs2.next()) {
					String sql7 = "UPDATE knowledge_updatetab SET current_statsid ='"
							+ rs2.getInt("maxid") + "'";
					Stmt3.executeUpdate(sql7);
				}
				rs2.close();
				Stmt2.close();
				Stmt3.close();

			} else {
				// 之前没有统计过

				Stmt2 = con.createStatement();
				Stmt2 = con.createStatement();
				String sql2 = "SELECT metric_id,hash_value,tcm_pk FROM metric_log WHERE metric_id >"
						+ a;
				rs2 = Stmt2.executeQuery(sql2);// 查询大于统计标识的id

				String hashvalue = "", pk = "";
				int id = 0;
				while (rs2.next()) {
					hashvalue = rs2.getString("hash_value");
					pk = rs2.getString("tcm_pk");
					id = rs2.getInt("metric_id");

					Stmt3 = con.createStatement();
					String sql3 = "SELECT hash_count FROM knowledge_stats WHERE hash_value='"
							+ hashvalue + "'";
					rs3 = Stmt3.executeQuery(sql3);

					if (rs3.next()) {

						Stmt5 = con.createStatement();
						String sql5 = "SELECT metric_id FROM metric_log WHERE hash_value='"
								+ hashvalue
								+ "'and tcm_pk ='"
								+ pk
								+ "'and metric_id <" + id;
						System.out.println(sql5);
						rs5 = Stmt5.executeQuery(sql5);

						if (rs5.next()) {
						} else {
							int b = rs3.getInt("hash_count") + 1;
							Stmt4 = con.createStatement();
							String sql4 = "UPDATE knowledge_stats SET hash_count = "
									+ b
									+ " WHERE hash_value='"
									+ hashvalue
									+ "'";
							Stmt4.executeUpdate(sql4);
							Stmt4.close();
						}
						rs5.close();
						Stmt5.close();

					} else {

						Stmt4 = con.createStatement();
						String sql4 = "INSERT INTO knowledge_stats VALUES('"
								+ hashvalue + "'," + 1 + ")";
						Stmt4.executeUpdate(sql4);
						Stmt4.close();

					}
					rs3.close();
					Stmt3.close();
				}
				rs2.close();
				Stmt2.close();

				Stmt2 = con.createStatement();
				Stmt3 = con.createStatement();
				String sql6 = "SELECT max(metric_id) as maxid FROM metric_log";
				rs2 = Stmt2.executeQuery(sql6);
				if (rs2.next()) {
					String sql7 = "Insert INTO knowledge_updatetab VALUES('"
							+ rs2.getInt("maxid") + "')";
					Stmt3.executeUpdate(sql7);
				}
				rs2.close();
				Stmt2.close();
				Stmt3.close();
			}
			rs1.close();
			Stmt1.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
