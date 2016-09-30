package com.ics.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ics.dao.KnowledgeDAO;
import com.ics.util.DBConnection;

public class KnowledgeDAOImpl implements KnowledgeDAO {
	public String rootnode_name="l3";

	@Override
	
/*	
	public String queryknowledge(String TCM_PK, String hash_values) {
		// TODO Auto-generated method stub

		try {
			String knowledge = "";
			String[] hash_value = hash_values.split(";;;");

			Connection con = DBConnection.getConnection();
			Statement stmt = null;
			ResultSet rs1 = null, rs2 = null, rs3 = null, rs4 = null, hashcount1 = null, hashcount2 = null;
			stmt = con.createStatement();

			for (int i = 0; i < hash_value.length; i++) {

				String sql1 = "SELECT * FROM knowledge_basic WHERE hash_value='"
						+ hash_value[i] + "'";
				rs1 = stmt.executeQuery(sql1);

				if (rs1.next()) {

					String hashvalue1 = rs1.getString("hash_value"), processname = rs1
							.getString("process_name"), software = rs1
							.getString("software_ver"), osver = rs1
							.getString("os_ver");
					//knowledge += hashvalue1 + "&&&" + processname + "&&&"
					//		+ software + "&&&" + osver + "&&&";
					knowledge += hashvalue1 + "&&&" + processname + "&&&"
									+ software + "&&&" ;

					String sql2 = "SELECT software_info,release_date,manufacturer,ic_bool FROM knowledge_extension WHERE process_name='"
							+ rs1.getString("process_name")+"'";
							//+ "' and software_ver='"
							//+ rs1.getString("software_ver") + "'";
					//stmt.close();

					stmt = con.createStatement();
					rs2 = stmt.executeQuery(sql2);
					String releasedate = "NULL", manufacturer = "null", icbool = "null",softwareinfo="null";
					if (rs2.next()){
						softwareinfo=rs2.getString("software_info");
						releasedate = rs2.getString("release_date");
						manufacturer = rs2.getString("manufacturer");
						icbool = rs2.getString("ic_bool");
					}
					knowledge += softwareinfo+"&&&"+releasedate + "&&&"+ manufacturer + "&&&" + icbool + "&&&";
					//stmt.close();
					rs2.close();

					Statement stmt1 = null, stmt2 = null, stmt3 = null;
					stmt = con.createStatement();
					stmt1 = con.createStatement();
					stmt2 = con.createStatement();
					stmt3 = con.createStatement();
					String sql3 = "SELECT hash_value FROM knowledge_basic where process_name='"
							+ processname
							+ "'and software_ver='"
							+ software
							+ "'and os_ver='"
							+ osver
							+ "' and hash_value != '"
							+ hash_value[i] + "'";
					rs3 = stmt.executeQuery(sql3);

					int count1 = 0, count2 = 0;
					float count = 0, reliability = 0;
					if (rs3.next()) {
						hashcount1 = stmt1
								.executeQuery("SELECT hash_count FROM knowledge_stats where hash_value='"
										+ hashvalue1 + "'");
						if (hashcount1.next())
							count1 += hashcount1.getInt("hash_count");

						hashcount2 = stmt2
								.executeQuery("SELECT hash_count FROM knowledge_stats where hash_value='"
										+ rs3.getString("hash_value") + "'");
						if (hashcount2.next())
							count2 += hashcount2.getInt("hash_count");

						while (rs3.next()) {
							hashcount2 = stmt3
									.executeQuery("SELECT hash_count FROM knowledge_stats where hash_value='"
											+ rs3.getString("hash_value") + "'");
							if (hashcount2.next())
								count2 += hashcount2.getInt("hash_count");
						}

						count = count1 + count2;
						reliability = count1 / count;
						knowledge += reliability + "&&&";

					} else {
						reliability = 1;
						knowledge += reliability + "&&&";
					}
					rs3.close();
					//stmt3.close();
					//stmt2.close();
					//stmt1.close();
					//stmt.close();

					stmt = con.createStatement();
					rs4 = stmt
							.executeQuery("SELECT ics_name FROM knowledge_recommended WHERE hash_value='"
									+ hash_value[i] + "'");
					if (rs4.next()) {// 将is_recommended置为true
						knowledge += "true" + ";;;";
					} else {// 将is_recommended置为false
						knowledge += "false" + ";;;";
					}
					//stmt.close();
					rs4.close();
				} else {// 将该hash_value对应的其他部分就直接设为“non-existent”
					knowledge += hash_value[i] + "&&&" + "non-existent" + "&&&"
							+ "non-existent" + "&&&" + "non-existent" + "&&&"
							+ "non-existent" + "&&&" + "non-existent" + "&&&"
							+ "non-existent" + "&&&" + "non-existent" + "&&&"
							+ "non-existent" + ";;;";
				}

			}

			rs1.close();
			con.close();

			return knowledge;
		} catch (SQLException el) {
			el.printStackTrace();
			return null;
		}
	}
	*/
	
	public String queryknowledge(String TCM_PK, String hash_values) {
		// TODO Auto-generated method stub

		try {
			String knowledge = "";
			String[] hash_value = hash_values.split(";;;");

			Connection con = DBConnection.getConnection();
			Statement stmt = null,stmt2 = null, stmt3 = null;
			ResultSet rs = null,rs1 = null, rs2 = null, rs3 = null, rs4 = null, hashcount1 = null, hashcount2 = null;
			stmt = con.createStatement();
			stmt2 = con.createStatement();
			stmt3 = con.createStatement();
		//	String sql="select a.name from "+rootnode_name+" a,users b where b.pubkey = '"+TCM_PK+"' and b.fid=a.id";
			String sql="select fid from users where pubkey = '"+TCM_PK+"'";
			rs = stmt.executeQuery(sql);
			rs.next();
			String sid=rs.getString("fid");
			sql = "select name from "+rootnode_name+" where id = '"+sid+"' ";
			rs = stmt.executeQuery(sql);
			rs.next();
			String ics_name=rs.getString("name");
			
			sql = "select version from knowledge where name = '"+ics_name+"' ";
			rs=stmt.executeQuery(sql);
			rs.last();
			String str = rs.getString("version");
			sql = "select id from knowledge where name = '"+ics_name+"' and version = '"+str+"'"  ;
			//sql = "select id from knowledge where version = '"+str+"'"; 
			rs = stmt.executeQuery(sql);
			rs.next();
			String strid =rs.getString("id");
			//sql="select a.process_name,a.software_ver,software_info,release_date,Manufacture,ic_bool from knowledge_extension a";
			//sql="select DISTINCT b.process_name,b.software_ver,software_info,release_date,Manufacturer,ic_bool from knowledge_item a,knowledge_basic b,knowledge_recommended c , knowledge d where d.version = '"+str+"' and c.ics_name = '"+ics_name+"' and c.hash_value=b.hash_value and b.process_name=a.process_name";
			sql ="select DISTINCT process_name,software_ver,software_info,release_date,Manufacturer,ic_bool from knowledge_item where ics_id = '"+strid+"'";
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				knowledge+=rs.getString("process_name")+"&&&"+rs.getString("software_ver")+"&&&"+rs.getString("software_info")+"&&&"+rs.getString("release_date")+"&&&"+rs.getString("Manufacturer")+"&&&"+rs.getString("ic_bool")+";;;";
			}
			/*
			knowledge+="^^^";
			String tree="";
			sql="select id,name,fid from l1";
			rs=stmt.executeQuery(sql);
			while(rs.next())
				tree+=rs.getString("id")+"&&&"+rs.getString("name")+"&&&"+rs.getString("fid")+";;;";
			tree+="***";
			sql="select id,name,fid from l2";
			rs=stmt.executeQuery(sql);
			while(rs.next())
				tree+=rs.getString("id")+"&&&"+rs.getString("name")+"&&&"+rs.getString("fid")+";;;";
			tree+="###";
			sql="select id,name,fid from l3";
			rs=stmt.executeQuery(sql);
			while(rs.next())
				tree+=rs.getString("id")+"&&&"+rs.getString("name")+"&&&"+rs.getString("fid")+";;;";
			
			con.close();
			knowledge+=tree;
			*/

			return knowledge;
		} catch (SQLException el) {
			el.printStackTrace();
			return null;
		}
	}
	
}
