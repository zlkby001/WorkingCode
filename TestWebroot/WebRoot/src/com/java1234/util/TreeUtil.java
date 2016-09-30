package com.java1234.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.ac.sklois.imm.admin.AdminBean;
import cn.ac.sklois.imm.admin.databasebean;

public class TreeUtil {
	private Connection connection = null;
	private Statement stm;
	
	private String ip;
	private String username;
	private String verifydate;
	private String wldate;
	private String pubkey;
	private String name1;
	private String identify;
	
	// private static final String DBDRIVER = "com.beyondb.jdbc.BeyondbDriver";
		// // 驱动类类名
		// private static final String DBURL =
		// "jdbc:beyondb://localhost:II7/immdb_new"; // 连接URL
		// private static final String DBUSER = "tcwg"; // 数据库用户名
		// private static final String DBPASSWORD = "123456"; // 数据库密码
		// static Statement stm3;

	public TreeUtil(String identify){
		this.identify = identify;
	}

	/**
	 * 生成第一层结点，开始创建树
	 * @return
	 */
	public JSONArray getJsonTree(String ip, String username,
			String verifydate, String wldate, String pubkey) {

		JSONArray arrayManu = new JSONArray(); // 工厂结点数组
		this.ip = ip;
		this.username = username;
		this.verifydate = verifydate;
		this.wldate = wldate;
		this.pubkey = pubkey;

		try {
			if (!databasebean.ifmysql) {
				Class.forName(databasebean.DBDRIVER); // 注册驱动
				connection = DriverManager.getConnection(databasebean.DBURL,
						databasebean.DBUSER, databasebean.DBPASSWORD); // 获得连接对象
			} else {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/immdb_new?user=root&password=tcwg&useUnicode=true&characterEncoding=utf8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// Statement statement1=connection.createStatement();
			stm = connection.createStatement();
			String strSql = "select DISTINCT x.id,x.name from l1 x";

			ResultSet rs = stm.executeQuery(strSql);
			while (rs.next()) {
				JSONObject l1 = new JSONObject();
				String id = rs.getString("id");
				String name = rs.getString("name");
				l1.put("id", EncodeId(id, "1"));
				l1.put("text", name);
				l1.put("state", "open");
				l1.put("iconCls", "factory");
				getl2tree(l1);
				arrayManu.add(l1);
			}

			stm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arrayManu;
	}

	/**
	 * 生成第二层结点
	 * @return
	 */
	public boolean getl2tree(JSONObject l1) {
		try {
			stm = connection.createStatement();
			String id = (String) l1.get("id");
			JSONArray l2 = new JSONArray();
			String strSql = "select DISTINCT x.id,x.name from l2 x where x.fid="
					+ DecodeId(id);

			ResultSet rs = stm.executeQuery(strSql);
			while (rs.next()) {
				JSONObject tmp = new JSONObject();
				String id2 = rs.getString("id");
				String name2 = rs.getString("name");
				// String fid2=rs.getString("fid");
				tmp.put("id", EncodeId(id2, "2"));
				tmp.put("text", name2);
				tmp.put("state", "open");
				tmp.put("iconCls", "technology");
				// tmp.put("fid", fid2);
				if (getl3tree(tmp))
					l2.add(tmp);
			}
			l1.put("children", l2);
			rs.close();
			stm.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	
	/**
	 * 生成第三层结点
	 * @return
	 */
	public boolean getl3tree(JSONObject l2) {
		try {
			stm = connection.createStatement();
			String id = (String) l2.get("id");
			JSONArray l3 = new JSONArray();
			String sql = "select id,name from l3 where fid=" + DecodeId(id);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				JSONObject tmp = new JSONObject();
				String id3 = rs.getString("id");
				String name3 = rs.getString("name");
				tmp.put("id", EncodeId(id3, "3"));
				tmp.put("text", name3);
				tmp.put("state", "closed");
				tmp.put("iconCls", "system");
				if (getclients(tmp))
					l3.add(tmp);
			}
			l2.put("children", l3);
			rs.close();
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 生成用户结点
	 * @param l3
	 * @return
	 */
	public boolean getclients(JSONObject l3) {
		try {
			stm = connection.createStatement();
			JSONArray userArray = new JSONArray(); // 用户结点集合
			String fid = l3.getString("id");
			if (!databasebean.ifmysql) {

				String strSql = "select DISTINCT id,name from users where fid="
						+ DecodeId(fid);
//				JSONArray info = new JSONArray();
//				ResultSet ids = stm.executeQuery(strSql);
//				int usernum = 0;
//				while (ids.next()) {
//					JSONObject tmp = new JSONObject();
//					tmp.put("id", ids.getString("id"));
//					tmp.put("text", ids.getString("name"));
//					info.add(tmp);
//					usernum++;
//				}

				strSql = "select DISTINCT a.* from users a left join digest_table b on a.ID = b.userid where a.id > 0 and a.fid=" +  DecodeId(fid);
				if (ip != null)
					strSql += " and ip LIKE '%" + ip + "%'";
				if (username != null)
					strSql += " and Name LIKE '%" + username + "%' ";
				if (wldate != null)
					strSql += " and wlsnapshotindex.issueDate LIKE '%" + wldate + "%'";
				if (verifydate != null)
					strSql += " and b.issueDate LIKE '%" + verifydate + "%'";
				if (pubkey != null)
					strSql += " and pubkey LIKE '%" + pubkey + "%'";
				ResultSet user = stm.executeQuery(strSql);
				
				JSONArray info = new JSONArray();
				ResultSet ids = stm.executeQuery(strSql);
				int usernum = 0;
				while (ids.next()) {
					JSONObject tmp = new JSONObject();
					tmp.put("id", ids.getString("id"));
					tmp.put("text", ids.getString("name"));
					info.add(tmp);
					usernum++;
				}
				
				int index = 0;
				// statement2.close();
				while (index < usernum) {
					JSONObject tmp = (JSONObject) info.get(index);
					String userid = tmp.getString("id");
					JSONObject userNode = new JSONObject();
					userNode.put("id", EncodeId(userid, "4"));// 用户结点
					userNode.put("text", tmp.getString("text"));
					userNode.put("state", "closed");
					userNode.put("iconCls", "station");
					System.out.println("userid:"+userid);
					if (databasebean.ifmysql)
						userNode.put(
								"children",
								userOperateNodes(user.getString("ID"), wldate,
										verifydate)); // 用户操作
					else
						userNode.put(
								"children",
								userOperateNodes_beyon(userid, wldate, verifydate,
										stm)); // 用户操作
					userArray.add(userNode);
					index++;
				}
			} // end of if not mysql
			else {
				String strSql = "select DISTINCT a.* from users a left join digest_table b on a.ID = b.userid where a.id > 0 and a.fid="
						+ DecodeId(fid);
				if (ip != null)
					strSql += " and ip LIKE '%" + ip + "%'";
				if (username != null)
					strSql += " and Name LIKE '%" + username + "%' ";
				if (wldate != null)
					strSql += " and wlsnapshotindex.issueDate LIKE '%" + wldate + "%'";
				if (verifydate != null)
					strSql += " and b.issueDate LIKE '%" + verifydate + "%'";
				if (pubkey != null)
					strSql += " and pubkey LIKE '%" + pubkey + "%'";
				System.out.println("====util strSql======  " + strSql
						+ "  ===========");
				ResultSet user = stm.executeQuery(strSql);
				// statement2.close();
				// String userNode = "";
				// JSONArray userArray = new JSONArray(); //用户结点集合
				int index = 0;
				// statement2.close();
				while (user.next()) {
					String userid = user.getString("ID");
					JSONObject userNode = new JSONObject();
					userNode.put("id", EncodeId(userid, "4"));// 用户结点    
					userNode.put("text", user.getString("Name"));
					userNode.put("state", "closed");
					userNode.put("iconCls", "station");
					if (databasebean.ifmysql)
						userNode.put(
								"children",
								userOperateNodes(user.getString("ID"), wldate,
										verifydate)); // 用户操作
					else
						userNode.put(
								"children",
								userOperateNodes_beyon(userid,wldate, verifydate,
										stm)); // 用户操作
					userArray.add(userNode);
					index++;
				}
			}
			l3.put("children", userArray);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	private JSONArray userOperateNodes(String id,String wldate, String verifydate) {
		JSONArray oprNode = new JSONArray(); // 用户操作结点集合

		JSONObject userOpr = new JSONObject(); // 用户操作结点
		userOpr.put("text", "终端信息");
		userOpr.put("state", "open");
		userOpr.put("leaf", "true");
		userOpr.put("iconCls", "terminal");

		JSONObject userOprAttr = new JSONObject(); // 用户操作属性结点
		userOprAttr.put("data-options", "");
		userOprAttr.put("url", "");
		userOprAttr.put("onclick", "");
		userOpr.put("attributes", userOprAttr);

	/*	JSONObject userOpr_2 = new JSONObject();
		userOpr_2.put("text", "白名单信息");
		userOpr_2.put("state", "open");
		userOpr_2.put("leaf", "true");
		userOpr_2.put("iconCls", "whitelist");

		JSONObject userOprAttr_2 = new JSONObject();
		userOprAttr_2.put("data-options", "");
		userOprAttr_2.put("url", "");
		userOprAttr_2.put("onclick", "");
		userOpr_2.put("attributes", userOprAttr_2);*/

		JSONObject userOpr_4 = new JSONObject();
		userOpr_4.put("text", "报警日志");
		userOpr_4.put("state", "open");
		userOpr_4.put("leaf", "true");
		userOpr_4.put("iconCls", "alert");

		JSONObject userOprAttr_4 = new JSONObject();
		userOprAttr_4.put("data-options", "");
		userOprAttr_4.put("url", "");
		userOprAttr_4.put("onclick", "");
		userOpr_4.put("attributes", userOprAttr_4);
		
//		JSONObject userOpr_5 = new JSONObject();
//		userOpr_5.put("text", "U盘白名单");
//		userOpr_5.put("state", "open");
//		userOpr_5.put("leaf", "true");
//		userOpr_5.put("iconCls", "usb");
//
//		JSONObject userOprAttr_5 = new JSONObject();
//		userOprAttr_5.put("data-options", "");
//		userOprAttr_5.put("url", "");
//		userOprAttr_5.put("onclick", "");
//		userOpr_5.put("attributes", userOprAttr_5);

		JSONObject userOpr_2 = new JSONObject();
		userOpr_2.put("text", "白名单信息");
		userOpr_2.put("state", "open");
		userOpr_2.put("iconCls", "whitelist");
		
		
		
		JSONObject userOpr_3 = new JSONObject();
		userOpr_3.put("text", "远程证明日志");
		userOpr_3.put("state", "open");
		userOpr_3.put("iconCls", "verify");
		
		JSONObject userOpr_6 = new JSONObject();
		userOpr_6.put("text", "审计信息");
		userOpr_6.put("state", "open");
		userOpr_6.put("leaf", "true");
		userOpr_6.put("iconCls", "auditloglist");
		
		JSONObject userOprAttr_6 = new JSONObject();
		userOprAttr_6.put("id", "");
		userOprAttr_6.put("text", "");
		
		JSONObject userOprAttr_2 = new JSONObject();
		userOprAttr_2.put("id", "");
		userOprAttr_2.put("text", "");

		JSONObject userOprAttr_3 = new JSONObject();
		userOprAttr_3.put("id", "");
		userOprAttr_3.put("text", "");
/*-----------------------------------------------------------------------------*/		
		JSONArray whiteArray = new JSONArray(); // 验证时间结点集合
		//Statement stm3 = null;
		ResultSet wlTimeSet = null;
		if (wldate == null || wldate.equals("null")) {
			wldate = "";
		}
		String strSql1 = "select DISTINCT issuedate from wlsnapshotindex where userid = "
				+ id + " and issuedate like '%" + wldate + "%'";
		try {
			stm = connection.createStatement();
			if(databasebean.ifmysql)
			strSql1 = "select DISTINCT issuedate from wlsnapshotindex where userid = "
				+ id + " and issuedate like '%" + wldate + "%' ORDER by issuedate DESC limit 5 ;";
			else
				strSql1 = "select first 5 DISTINCT issuedate from wlsnapshotindex where userid = "
						+ id + " and issuedate like '%" + wldate + "%' ORDER by issuedate DESC";
			wlTimeSet = stm.executeQuery(strSql1);
			System.out.println(wldate);
			// stm3.close();
			// System.out.println("======  " + strSql +"   =======");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (wlTimeSet.next()) {
				JSONObject wlNode = new JSONObject();
				wlNode.put("text", wlTimeSet.getString("issuedate"));
				wlNode.put("leaf", "true");
				// vfNode.put("iconCls","add");
				whiteArray.add(wlNode);
			}
			// stm3.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		userOpr_2.put("children", whiteArray);
		try {
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
/*-----------------------------------------------------------------------------*/

		JSONArray verifyArray = new JSONArray(); // 验证时间结点集合
		//Statement stm3 = null;
		ResultSet vfTimeSet = null;
		if (verifydate == null || verifydate.equals("null")) {
			verifydate = "";
		}
		String strSql = "select DISTINCT date from verify_log where userid = "
				+ id + " and date like '%" + verifydate + "%'";
		try {
			stm = connection.createStatement();
			if(databasebean.ifmysql)
			strSql = "select DISTINCT date from verify_log where userid = "
					+ id + " and date like '%" + verifydate
					+ "%' ORDER by date DESC limit 5 ;";
			else
				strSql = "select first 5 DISTINCT date from verify_log where userid = "
						+ id + " and date like '%" + verifydate
						+ "%' ORDER by date DESC";
			vfTimeSet = stm.executeQuery(strSql);
			// stm3.close();
			// System.out.println("======  " + strSql +"   =======");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (vfTimeSet.next()) {
				JSONObject vfNode = new JSONObject();
				vfNode.put("text", vfTimeSet.getString("date"));
				vfNode.put("leaf", "true");
				// vfNode.put("iconCls","add");
				verifyArray.add(vfNode);
			}
			// stm3.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		userOpr_3.put("children", verifyArray);
		try {
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	/**
		try{
			stm = connection.createStatement();
			String stringSql = "select * from admins where Name='" + name1 + "' and pass ='Y' and endtype = 'server'" ;
			ResultSet resultSet = stm.executeQuery(stringSql);
			while (resultSet.next()) {
			 name1 = resultSet.getString("Name");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(name1=="admin"){
			System.out.println("name:"+name1);
			oprNode.add(userOpr_6);
		}
		**/
		if(identify.equalsIgnoreCase("1000")){
		oprNode.add(userOpr);
		oprNode.add(userOpr_2);
		oprNode.add(userOpr_4);
		//oprNode.add(userOpr_5);
		oprNode.add(userOpr_3);
		oprNode.add(userOpr_6);
		}else{
			oprNode.add(userOpr);
			oprNode.add(userOpr_2);
			oprNode.add(userOpr_4);
			//oprNode.add(userOpr_5);
			oprNode.add(userOpr_3);
			//oprNode.add(userOpr_6);
			
		}
		return oprNode;

	}

	private JSONArray userOperateNodes_beyon(String id, String wldate,
			
			String verifydate, Statement stm3) {
		JSONArray oprNode = new JSONArray(); // 用户操作结点集合

		JSONObject userOpr = new JSONObject(); // 用户操作结点
		userOpr.put("text", "终端信息");
		userOpr.put("state", "open");
		userOpr.put("leaf", "true");
		userOpr.put("iconCls", "terminal");

		JSONObject userOprAttr = new JSONObject(); // 用户操作属性结点
		userOprAttr.put("data-options", "");
		userOprAttr.put("url", "");
		userOprAttr.put("onclick", "");
		userOpr.put("attributes", userOprAttr);

		/*JSONObject userOpr_2 = new JSONObject();
		userOpr_2.put("text", "白名单信息");
		userOpr_2.put("state", "open");
		userOpr_2.put("leaf", "true");
		userOpr_2.put("iconCls", "whitelist");

		JSONObject userOprAttr_2 = new JSONObject();
		userOprAttr_2.put("data-options", "");
		userOprAttr_2.put("url", "");
		userOprAttr_2.put("onclick", "");
		userOpr_2.put("attributes", userOprAttr_2);*/

		JSONObject userOpr_4 = new JSONObject();
		userOpr_4.put("text", "报警日志");
		userOpr_4.put("state", "open");
		userOpr_4.put("leaf", "true");
		userOpr_4.put("iconCls", "alert");
		
		JSONObject userOpr_5 = new JSONObject();
		userOpr_5.put("text", "U盘白名单");
		userOpr_5.put("state", "open");
		userOpr_5.put("leaf", "true");
		userOpr_5.put("iconCls", "usb");

		JSONObject userOprAttr_5 = new JSONObject();
		userOprAttr_5.put("data-options", "");
		userOprAttr_5.put("url", "");
		userOprAttr_5.put("onclick", "");
		userOpr_5.put("attributes", userOprAttr_5);
		
//		JSONObject userOpr_6 = new JSONObject();
//		userOpr_6.put("text", "审计信息");
//		userOpr_6.put("state", "open");
//		userOpr_6.put("leaf", "true");
//		userOpr_6.put("iconCls", "auditlog");
//
//		JSONObject userOprAttr_6 = new JSONObject();
//		userOprAttr_6.put("id", "");
//		userOprAttr_6.put("text", "");

		JSONObject userOprAttr_4 = new JSONObject();
		userOprAttr_4.put("data-options", "");
		userOprAttr_4.put("url", "");
		userOprAttr_4.put("onclick", "");
		userOpr_4.put("attributes", userOprAttr_4);

		JSONObject userOpr_2 = new JSONObject();
		userOpr_2.put("text", "白名单信息");
		userOpr_2.put("state", "open");
		userOpr_2.put("iconCls", "whitelist");

		JSONObject userOprAttr_2 = new JSONObject();
		userOprAttr_2.put("id", "");
		userOprAttr_2.put("text", "");
		
		JSONObject userOpr_3 = new JSONObject();
		userOpr_3.put("text", "远程证明日志");
		userOpr_3.put("state", "open");
		userOpr_3.put("iconCls", "verify");

		JSONObject userOprAttr_3 = new JSONObject();
		userOprAttr_3.put("id", "");
		userOprAttr_3.put("text", "");
/*-------------------------------------------------------------------------*/
		JSONArray whiteArray = new JSONArray(); // 验证时间结点集合
		//Statement stm3 = null;
		ResultSet wlTimeSet = null;
		if (wldate == null || wldate.equals("null")) {
			wldate = "";
		}
		String strSql1 = "select DISTINCT issuedate from wlsnapshotindex where userid = "
				+ id + " and issuedate like '%" + wldate + "%'";
		try {
			stm = connection.createStatement();
			if(databasebean.ifmysql)
			strSql1 = "select DISTINCT issuedate from wlsnapshotindex where userid = "
				+ id + " and issuedate like '%" + wldate + "%' ORDER by issuedate DESC limit 5 ;";
			else
				strSql1 = "select first 5 DISTINCT issuedate from wlsnapshotindex where userid = "
						+ id + " and issuedate like '%" + wldate + "%' ORDER by issuedate DESC";
			wlTimeSet = stm.executeQuery(strSql1);
			// stm3.close();
			// System.out.println("======  " + strSql +"   =======");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (wlTimeSet.next()) {
				JSONObject wlNode = new JSONObject();
				wlNode.put("text", wlTimeSet.getString("issuedate"));
				wlNode.put("leaf", "true");
				// vfNode.put("iconCls","add");
				whiteArray.add(wlNode);
			}
			// stm3.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		userOpr_2.put("children", whiteArray);
		try {
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
/*-------------------------------------------------------------------------*/		
		JSONArray verifyArray = new JSONArray(); // 验证时间结点集合
		// Statement stm3 = null;
		ResultSet vfTimeSet = null;
		if (verifydate == null || verifydate.equals("null")) {
			verifydate = "";
		}
		String strSql = "select DISTINCT date from verify_log where userid = "
				+ id + " and date like '%" + verifydate + "%'";
		try {
			strSql = "select first 5 DISTINCT date from verify_log where userid = "
					+ id + " and date like '%" + verifydate
					+ "%' ORDER by date DESC";
			vfTimeSet = stm3.executeQuery(strSql);
			// stm3.close();
			// System.out.println("======  " + strSql +"   =======");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (vfTimeSet.next()) {
				JSONObject vfNode = new JSONObject();
				vfNode.put("text", vfTimeSet.getString("date"));
				vfNode.put("leaf", "true");
				// vfNode.put("iconCls","add");
				verifyArray.add(vfNode);
			}
			// stm3.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		userOpr_3.put("children", verifyArray);
		try {
			stm3.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oprNode.add(userOpr);
		oprNode.add(userOpr_4);
		oprNode.add(userOpr_5);
		oprNode.add(userOpr_2);
		oprNode.add(userOpr_3);
		//oprNode.add(userOpr_6);

		return oprNode;

	}

	private String EncodeId(String id, String prefix) {
		if (id == null || id.equals(""))
			return "";
		if (prefix == null || prefix.equals(""))
			return id;

		return prefix + id;
	}

	private String DecodeId(String id) {
		if (id == null || id.equals(""))
			return "";

		int length = 1; // 前缀长度
		return id.substring(length);
	}


}
