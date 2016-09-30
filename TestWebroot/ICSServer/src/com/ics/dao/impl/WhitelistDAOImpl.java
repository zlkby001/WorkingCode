package com.ics.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ics.dao.WhitelistDAO;
import com.ics.util.DBConnection;

public class WhitelistDAOImpl implements WhitelistDAO {

	@Override
	public boolean uploadWhitelist(String TCM_PK, int whitelist_vern,
			String whitelistcontent) {
		// TODO Auto-generated method stub
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = null;
			ResultSet rs = null;
			stmt = con.createStatement();
			int userid = 0;
			String sql2 = "SElECT id FROM users where pubkey = '" + TCM_PK
					+ "'";
			rs = stmt.executeQuery(sql2);
			if (rs.next())
				userid = rs.getInt("id");
			rs.close();
			stmt.close();
			stmt = con.createStatement();
			String sql1 = "INSERT INTO whitelist_ver VALUES('" + TCM_PK + "','"
					+ whitelist_vern + "','false','true','false'," + userid
					+ ")";
			stmt.executeUpdate(sql1);
			stmt.close();
			stmt = con.createStatement();
			String[] content1 = whitelistcontent.split(";;;");
			for (int i = 0; i < content1.length; i++) {
				String[] content2 = content1[i].split("&&&");
				String sql = "INSERT INTO whitelist_content(TCM_PK,Process_Name,Process_Path,Hash_Value,whitelist_vern,userid) VALUES('" + TCM_PK
						+ "','" + content2[0] + "','" + content2[1] + "','"
						+ content2[2] + "','" + whitelist_vern + "','" + userid
						+ "')";
		
				stmt.executeUpdate(sql);
			}

			stmt.close();
			con.close();
			return true;
		} catch (SQLException el) {
			el.printStackTrace();
			return false;
		}
	}

	@Override
	public String downloadWhitelist(String TCM_PK, int whitelist_vern) {
		// TODO Auto-generated method stub
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt = null;
			ResultSet rs = null;
			stmt = con.createStatement();
			String sql = "SELECT process_name,hash_value,process_path FROM whitelist_content WHERE TCM_PK ='"
					+ TCM_PK + "' and whitelist_vern =" + whitelist_vern;
			rs = stmt.executeQuery(sql);
			String whitelistdown = whitelist_vern + "###";
			while (rs.next()) {
				whitelistdown += rs.getString("process_name") + "&&&"
						+ rs.getString("hash_value").trim() + "&&&"
						+ rs.getString("process_path").trim() + ";;;";
			}
			rs.close();
			stmt.close();
			con.close();
			return whitelistdown;
		} catch (SQLException el) {
			el.printStackTrace();
			return null;
		}
	}

	@Override
	public String queryWhitelistinfo(String TCM_PK) {
		// TODO Auto-generated method stub
		try {
			String whitelistinfo = "", recommend_whitelist_vern = "", recommend_admin_revise = "";
			Connection con = DBConnection.getConnection();
			Statement stmt = null, stmt1 = null, stmt2 = null, stmt3 = null;
			ResultSet rs0 = null, rs1 = null, rs2 = null, rs3 = null;

			stmt = con.createStatement();
			String sql0 = "SELECT * FROM whitelist_ver";
			rs0 = stmt.executeQuery(sql0);

			if (rs0.next()) {
				stmt1 = con.createStatement();
				String sql1 = "SELECT max(whitelist_vern) as maxvern FROM whitelist_ver WHERE TCM_PK = '"
						+ TCM_PK + "'";
				rs1 = stmt1.executeQuery(sql1);

				stmt2 = con.createStatement();
				int vern = 0;
				if (rs1.next())
					vern = rs1.getInt("maxvern");
				String sql2 = "SELECT admin_revise FROM whitelist_ver WHERE TCM_PK = '"
						+ TCM_PK + "' and whitelist_vern =" + vern;
				rs2 = stmt2.executeQuery(sql2);

				if (rs2.next()) {
					whitelistinfo += vern + "&&&"
							+ rs2.getString("admin_revise").trim() + "&&&";

					stmt3 = con.createStatement();
					String sql3 = "SELECT whitelist_vern,admin_revise FROM whitelist_ver WHERE TCM_PK = '"
							+ TCM_PK + "' and client_recommend = 'true'";
					rs3 = stmt3.executeQuery(sql3);
					if (rs3.next()) {
					
						recommend_whitelist_vern = Integer.toString(rs3
								.getInt("whitelist_vern"));
						recommend_admin_revise = rs3.getString("admin_revise");
					} else {
						recommend_whitelist_vern = "non_existent";
						recommend_admin_revise = "non_existent";
					}
					whitelistinfo += recommend_whitelist_vern + "&&&"
							+ recommend_admin_revise;
					rs3.close();
					stmt3.close();
				} else {
					whitelistinfo = "non_existent";
				}

				rs2.close();
				rs1.close();
				stmt2.close();
				stmt1.close();
			} else {
				whitelistinfo = "non_existent";
			}

			rs0.close();
			stmt.close();
			con.close();
			return whitelistinfo;

		} catch (SQLException el) {
			el.printStackTrace();
			return null;
		}
	}
}
