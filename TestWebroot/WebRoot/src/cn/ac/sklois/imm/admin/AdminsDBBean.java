package cn.ac.sklois.imm.admin;
import java.sql.*;
import java.util.ArrayList;

import com.java1234.util.GridPageBean;
import com.java1234.util.ResponseUtil;

public class AdminsDBBean {
	private Connection connection = null;
	//private static final String DBDRIVER = "com.beyondb.jdbc.BeyondbDriver"; // 驱动类类名
	//private static final String DBURL = "jdbc:beyondb://localhost:II7/immdb_new"; // 连接URL
	//private static final String DBUSER = "tcwg"; // 数据库用户名
	//private static final String DBPASSWORD = "123456"; // 数据库密码
	public AdminsDBBean() {// 锟斤拷锟届函锟斤拷
		try {
			if(!databasebean.ifmysql)
			{
				Class.forName(databasebean.DBDRIVER); // 注册驱动
				connection = DriverManager.getConnection(databasebean.DBURL, databasebean.DBUSER, databasebean.DBPASSWORD); // 获得连接对象
			}
//			PropertyConfigurator.configure("log4j.properties");
			else
			{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/immdb_new?user=root&password=tcwg&useUnicode=true&characterEncoding=utf8");
			}
			//Class.forName(DBDRIVER); // 注册驱动
			//connection = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD); // 获得连接对象
			// connection=DriverManager.getConnection("jdbc:mysql://192.168.4.125:3306/immdb?user=root&sklois&useUnicode=true&characterEncoding=utf-8");
			// connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/immdb","root","sklois");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AdminBean findbyOperatorID(int OperatorID) {
		AdminBean a = new AdminBean();
		try {
			Statement statement = connection.createStatement();
			String strSql = "select * from admins where OperatorID="
					+ OperatorID;
			ResultSet resultSet = statement.executeQuery(strSql);
			if (resultSet.next()) {
				String oid = Integer.toString(OperatorID);
				a.setOperatorID(oid);
				a.setPassword(resultSet.getString("Password"));
				a.setUsertype(resultSet.getString("UserType"));
				a.setName(resultSet.getString("Name"));
				a.setAddress(resultSet.getString("Address"));
				a.setPhoneNum(resultSet.getString("PhoneNum"));
				a.setEmail(resultSet.getString("Email"));
				// add by lh
				a.setGender(resultSet.getString("gender"));
				a.setCertclass(resultSet.getString("certclass"));
				a.setCertnumber(resultSet.getString("certnumber"));
				a.setPass(resultSet.getString("pass"));
				a.setEndType(resultSet.getString("endtype"));
				// end
			} else
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
	}

	public UserBean findUserbyID(int ID) {
		UserBean a = new UserBean();
		try {
			Statement statement = connection.createStatement();
			String strSql = "select * from users where ID=" + ID;
			
			ResultSet resultSet = statement.executeQuery(strSql);
			if (resultSet.next()) {
				String oid = Integer.toString(ID);
				a.setID(oid);
				a.setip(resultSet.getString("ip"));
				/*
				if(databasebean.ifmysql)
					a.setlab(resultSet.getString("lab"));
					*/
				//else
					a.setlab(resultSet.getString("client_area"));
				a.setmac(resultSet.getString("mac"));
				a.setmanufacture(resultSet.getString("manufacture"));
				a.setName(resultSet.getString("Name"));
				// add by lh
				a.setpubkey(resultSet.getString("pubkey"));
				a.setsequence(resultSet.getString("sequence"));
				a.setos(resultSet.getString("os"));
				a.setEK(resultSet.getString("EK"));
				String active = resultSet.getString("active");
				if(active!=null)
					a.setactive(Integer.parseInt(active));
			} else
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
	}

	public AdminBean findbyName(String name) {
		AdminBean a = new AdminBean();
		try {

			Logtest log = new Logtest();
			log.logger
					.info("************************into AdminBean*************************************");
			if (connection == null)
				log.logger
						.info("**********************connection == NULL******************************");
			Statement statement = connection.createStatement();
			String strSql = "select * from admins where Name='" + name + "' and pass ='Y' and endtype = 'server'" ;
			log.logger.info(strSql);
			ResultSet resultSet = statement.executeQuery(strSql);
			if (resultSet.next()) {
				String oid = Integer.toString(resultSet.getInt("OperatorID"));
				log.logger.info("resultSET oid:" + oid);
				a.setOperatorID(oid);
				a.setPassword(resultSet.getString("Password").trim());
				a.setName(name);
				a.setUsertype(resultSet.getString("UserType").trim());
				a.setEndType(resultSet.getString("EndType").trim());
				a.setAddress(resultSet.getString("Address").trim());
				a.setPhoneNum(resultSet.getString("PhoneNum").trim());
				a.setEmail(resultSet.getString("Email").trim());
				//
				// add by lh
				a.setGender(resultSet.getString("gender").trim());
				a.setCertclass(resultSet.getString("certclass").trim());
				a.setCertnumber(resultSet.getString("certnumber").trim());
				a.setPass(resultSet.getString("pass").trim());
				// end

			} else
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
	}
	public AdminBean findbyName1(String name) {
		AdminBean a = new AdminBean();
		try {

			Logtest log = new Logtest();
			log.logger
					.info("************************into AdminBean*************************************");
			if (connection == null)
				log.logger
						.info("**********************connection == NULL******************************");
			Statement statement = connection.createStatement();
			String strSql = "select * from admins where Name='" + name + "'";
			log.logger.info(strSql);
			ResultSet resultSet = statement.executeQuery(strSql);
			if (resultSet.next()) {
				String oid = Integer.toString(resultSet.getInt("OperatorID"));
				log.logger.info("resultSET oid:" + oid);
				a.setOperatorID(oid);
				a.setPassword(resultSet.getString("Password").trim());
				a.setName(name);
//				a.setUsertype(resultSet.getString("UserType").trim());
//				a.setEndType(resultSet.getString("EndTpye").trim());
				a.setAddress(resultSet.getString("Address").trim());
				a.setPhoneNum(resultSet.getString("PhoneNum").trim());
				a.setEmail(resultSet.getString("Email").trim());
				//
				// add by lh
				a.setGender(resultSet.getString("gender").trim());
				a.setCertclass(resultSet.getString("certclass").trim());
				a.setCertnumber(resultSet.getString("certnumber").trim());
				a.setPass(resultSet.getString("pass").trim());
				// end

			} else
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
	}


	public boolean deletebyOperatorID(int OperatorID) {
		try {
			Statement statement = connection.createStatement();
			String strSql = "delete from admins where OperatorID ='"
					+ OperatorID + "'";
			statement.executeUpdate(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public boolean dorestore(int newuserid,int olduserid){
		if(newuserid==olduserid)
			return true;
		UserBean newuser=this.findUserbyID(newuserid);
		//return true;
		try {
			int userid=newuserid;
			Statement statement = connection.createStatement();
			String strSql;
			//if(databasebean.ifmysql)
				//strSql = "delete from whitelist where userid ='" + userid + "'";
			strSql="delete from wlsnapshot where indexid in (select id from wlsnapshotindex where userid=" + userid + ")";
			statement.executeUpdate(strSql);
			strSql="delete from wlsnapshotindex where userid="+userid;
			statement.executeUpdate(strSql);
			/*else*/ strSql = "delete from whitelist_content where userid ='"+userid+"'";
			statement.executeUpdate(strSql);
			strSql = "delete from digest_table where userid ='" + userid + "'";
			statement.executeUpdate(strSql);
			strSql = "delete from alert where userid='" + userid + "'";
			statement.executeUpdate(strSql);
			strSql = "delete from verify_log where userid='" + userid + "'";
			statement.executeUpdate(strSql);
			strSql = "delete from usblocal where terminalid='" + userid + "'";
			statement.executeUpdate(strSql);
			strSql = "delete from usbmeasure where terminalid='" + userid + "'";
			statement.executeUpdate(strSql);
			
			//============restore================================================
			strSql = "insert into whitelist_content(tcm_pk,process_name,process_path,hash_value,whitelist_vern,userid) select '"+newuser.getpubkey()+"',process_name,process_path,hash_value,whitelist_vern,"+newuserid+" from whitelist_content where userid = "+olduserid;
			statement.executeUpdate(strSql);
			strSql = "insert into digest_table(filename,digest,edition,classid,trusted,createid,description,issuedate,softwarename,userid) select filename,digest,edition,classid,trusted,createid,description,issuedate,softwarename,"+newuserid+" from digest_table where userid = "+olduserid;
			statement.executeUpdate(strSql);
			strSql = "insert into alert(filename,digest,issuedate,acknowledge,userid) select filename,digest,issuedate,acknowledge,"+newuserid+" from alert where userid="+olduserid;
			statement.executeUpdate(strSql);
			strSql = "insert into verify_log(date,pubkey,ip,name,userid) select date,'"+newuser.getpubkey()+"',ip,'"+newuser.getName()+"',"+newuserid+" from verify_log where userid="+olduserid;
			statement.executeUpdate(strSql);
			strSql = "insert into usbmeasure(manufacture,sn,version,producer,date,terminalid) select manufacture,sn,version,producer,date,"+newuserid+" from usbmeasure where terminalid="+olduserid;
			statement.executeUpdate(strSql);
			strSql = "insert into usblocal(manufacture,sn,terminalid) select manufacture,sn,"+newuserid+" from usblocal where terminalid="+olduserid;
			statement.executeUpdate(strSql);
			strSql = "select id,issuedate from wlsnapshotindex where userid="+olduserid;
			ResultSet rs=statement.executeQuery(strSql);
			while(rs.next())
			{
				Statement statement1 = connection.createStatement();
				int oldindexid=rs.getInt("id");
				int newindexid=0;
				String issuedate=rs.getString("issuedate");
				//String description=rs.getString("description");
				strSql = "insert into wlsnapshotindex(description,issuedate,userid) select description,issuedate,userid"+newuserid+" from wlsnapshotindex where id="+oldindexid;
				statement1.executeUpdate(strSql);
				strSql = "select id from wlsnapshotindex where userid="+newuserid+" and issuedate='"+issuedate+"'";
				ResultSet rs1=statement1.executeQuery(strSql);
				if(rs1.next())
					newindexid=rs1.getInt("id");
				rs1.close();
				strSql = "insert into wlsnapshot(process_name,process_path,hash_value,indexid) select process_name,process_path,hash_value,"+newindexid+" from wlsnapshot where indexid="+oldindexid;
				statement1.executeUpdate(strSql);
				//strSql = "insert into wlsnapshot(issuedate) select issuedate"+newuserid+" from wlsnapshotindex where id="+oldindexid;
				//statement1.executeUpdate(strSql);
				statement1.close();
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}

	
	public boolean deleteuserbyid(int userid) {
		int r = 0;
		try {
			Statement statement = connection.createStatement();
			String strSql;
			//if(databasebean.ifmysql)
				//strSql = "delete from whitelist where userid ='" + userid + "'";
			strSql="delete from wlsnapshot where indexid in (select id from wlsnapshotindex where userid=" + userid + ")";
			statement.executeUpdate(strSql);
			strSql="delete from wlsnapshotindex where userid="+userid;
			statement.executeUpdate(strSql);
			/*else*/ strSql = "delete from whitelist_content where userid ='"+userid+"'";
			statement.executeUpdate(strSql);
			strSql = "delete from digest_table where userid ='" + userid + "'";
			statement.executeUpdate(strSql);
			strSql = "delete from alert where userid='" + userid + "'";
			statement.executeUpdate(strSql);
			strSql = "delete from verify_log where userid='" + userid + "'";
			statement.executeUpdate(strSql);
			strSql = "delete from usblocal where terminalid='" + userid + "'";
			statement.executeUpdate(strSql);
			strSql = "delete from users where ID='" + userid + "'";
			statement.executeUpdate(strSql);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			
			
			return false;
		}
		
		//statement.close();
			return true;

	}
	
	public boolean deleteAlertbyid(int userId) {
		int r = 0;
		try {
			Statement statement = connection.createStatement();
			String strSql = "delete from alert where userid ='" + userId + "'";
			//System.out.println("===================  "+strSql+"  =======================");
			r = statement.executeUpdate(strSql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(r>0)
			return true;
		else
			return false;
	}
	
	public boolean deleteAlertDetailbyid(String ids) {
		int r = 0;
		try {
			Statement statement = connection.createStatement();
			String strSql = "delete from alert where id in (" + ids + ")";
			
			//System.out.println(strSql);
			r = statement.executeUpdate(strSql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(r>0)
			return true;
		else
			return false;
	}

	
	public boolean updatebyOperatorID(int OperatorID, AdminBean a) {
		try {
			Statement statement = connection.createStatement();
			String strSql = "update admins set Password='" + a.getPassword()
					+ "', Name='" + a.getName() + "', Address='"
					+ a.getAddress() + "',PhoneNum='" + a.getPhoneNum()
					+ "', Email='" + a.getEmail() + "', gender='"
					+ a.getGender() + "', certclass='" + a.getCertclass()
					+ "', certnumber='" + a.getCertnumber() + "', pass='"
					+ a.getPass() + "' where OperatorID =" + OperatorID;// 锟斤拷锟斤拷锟斤拷锟�
			statement.executeUpdate(strSql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	
	public boolean changeAlertStatus(String ids) {
		int r = 0;
		try {
			Statement statement = connection.createStatement();
			String strSql = "update alert set acknowledge = '1' where userid = " + ids;
			//System.out.println("===================  "+strSql+"  =======================");
			r = statement.executeUpdate(strSql);
			
			//alert优化
			strSql="update users set latestalertcount=0,alertdate='2011-01-01'";						
			
			statement.executeUpdate(strSql);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(r>0)
			return true;
		else
			return false;
	}
	
	public boolean add1(AdminBean a) {
		Statement statement;
		try {
			statement = connection.createStatement();
			// modified by lh
			String strSql = "insert into admins (Password, Name, UserType, EndType,Address, PhoneNum, Email, gender, certclass, certnumber, pass) value('"
					+ a.getPassword()
					+ "','"
					+ a.getName()
					+ "','"
					+ a.getUsertype()
					+ "','"
					+ a.getEndType()
					+ "','"
					+ a.getAddress()
					+ "','"
					+ a.getPhoneNum()
					+ "','"
					+ a.getEmail()
					+ "','"
					+ a.getGender()
					+ "','"
					+ a.getCertclass()
					+ "','"
					+ a.getCertnumber()
					+ "','"
					+ a.getPass() + "')";// 锟斤拷锟斤拷锟斤拷锟�
			// end
			statement.executeUpdate(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}
	public boolean add(AdminBean a) {
		Statement statement;
		try {
			statement = connection.createStatement();
			// modified by lh
			String strSql = "insert into admins (Password, Name,EndType, Address, PhoneNum, Email, gender, certclass, certnumber, pass,UserType) value('"
					+ a.getPassword()
					+ "','"
					+ a.getName()
					+ "','"
					+ a.getEndType()
					+ "','"
					+ a.getAddress()
					+ "','"
					+ a.getPhoneNum()
					+ "','"
					+ a.getEmail()
					+ "','"
					+ a.getGender()
					+ "','"
					+ a.getCertclass()
					+ "','"
					+ a.getCertnumber()
					+ "','"
					+ a.getPass() 
					+
					"','"
					+ a.getUsertype()+"')";// 锟斤拷锟斤拷锟斤拷锟�
			// end
			statement.executeUpdate(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public ArrayList findAll() {
		ArrayList c = new ArrayList();
		try {
			Statement statement = connection.createStatement();
			String strSql = "select * from admins";
			ResultSet resultSet = statement.executeQuery(strSql);
			while (resultSet.next()) {
				String oid = Integer.toString(resultSet.getInt("OperatorID"));
				AdminBean a = new AdminBean();
				a.setOperatorID(oid);
				a.setPassword(resultSet.getString("Password"));
				a.setName(resultSet.getString("Name"));
				a.setAddress(resultSet.getString("Address"));
				a.setPhoneNum(resultSet.getString("PhoneNum"));
				a.setEmail(resultSet.getString("Email"));

				// add by lh
				a.setGender(resultSet.getString("gender"));
				a.setCertclass(resultSet.getString("certclass"));
				a.setCertnumber(resultSet.getString("certnumber"));
				a.setPass(resultSet.getString("pass"));
				// end

				c.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return c;
	}

	// added by lh
	public ArrayList findAllPassed() {
		ArrayList c = new ArrayList();
		try {
			Statement statement = connection.createStatement();
			String strSql = "select * from admins";
			ResultSet resultSet = statement.executeQuery(strSql);
			while (resultSet.next()) {
				String tmp = resultSet.getString("pass");
				if (tmp.equalsIgnoreCase("N"))
					continue;

				String oid = Integer.toString(resultSet.getInt("OperatorID"));
				AdminBean a = new AdminBean();
				a.setOperatorID(oid);
				a.setPassword(resultSet.getString("Password"));
				a.setName(resultSet.getString("Name"));
				a.setAddress(resultSet.getString("Address"));
				a.setPhoneNum(resultSet.getString("PhoneNum"));
				a.setEmail(resultSet.getString("Email"));

				// add by lh
				a.setGender(resultSet.getString("gender"));
				a.setCertclass(resultSet.getString("certclass"));
				a.setCertnumber(resultSet.getString("certnumber"));
				a.setPass(resultSet.getString("pass"));
				a.setEndType(resultSet.getString("endtype"));
				// end

				c.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return c;
	}

	public ArrayList findAllusers(String ip, String username, String pubkey) {
		// zhyjun add
		ArrayList c = new ArrayList();
		try {
			Statement statement = connection.createStatement();
			String strSql = "select * from users where id > 0";
			if (ip != null)
				strSql += " and ip LIKE '%" + ip + "%'";
			if (username != null)
				strSql += " and Name LIKE '%" + username + "%' ";
			if (pubkey != null)
				strSql += " and pubkey LIKE '%" + pubkey + "%'";
			ResultSet resultSet = statement.executeQuery(strSql);
			while (resultSet.next()) {
				UserBean a = new UserBean();
				a.setID(resultSet.getString("ID"));
				a.setName(resultSet.getString("Name"));
				a.setip(resultSet.getString("ip"));
				a.setlab(resultSet.getString("lab"));
				a.setmac(resultSet.getString("mac"));
				a.setmanufacture(resultSet.getString("manufacture"));
				a.setpubkey(resultSet.getString("pubkey"));
				a.setsequence(resultSet.getString("sequence"));
				a.setos(resultSet.getString("os"));
				c.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

	public ArrayList findactiveusers(String ip, String username, String pubkey,
			String EK) {
		// zhyjun add
		ArrayList c = new ArrayList();
		try {
			Statement statement = connection.createStatement();
			String strSql = "select * from users where active=1";
			if (ip != null)
				strSql += " and ip LIKE '%" + ip + "%'";
			if (username != null)
				strSql += " and Name LIKE '%" + username + "%' ";
			if (pubkey != null)
				strSql += " and pubkey LIKE '%" + pubkey + "%'";
			if (EK != null)
				strSql += " and EK LIKE '%" + EK + "%'";
			ResultSet resultSet = statement.executeQuery(strSql);
			while (resultSet.next()) {
				UserBean a = new UserBean();
				a.setID(resultSet.getString("ID"));
				a.setName(resultSet.getString("Name"));
				a.setip(resultSet.getString("ip"));
				a.setlab(resultSet.getString("lab"));
				a.setmac(resultSet.getString("mac"));
				a.setmanufacture(resultSet.getString("manufacture"));
				a.setpubkey(resultSet.getString("pubkey"));
				a.setsequence(resultSet.getString("sequence"));
				a.setos(resultSet.getString("os"));
				a.setEK(resultSet.getString("EK"));
				c.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

	public ArrayList ListVerifylog(String ip, String username, String pubkey,
			String verifydate) {
		// zhyjun add
		ArrayList c = new ArrayList();
		try {
			Statement statement = connection.createStatement();
			String strSql = "select * from verify_log where id > 0";
			if (ip != null)
				strSql += " and ip LIKE '%" + ip + "%'";
			if (username != null)
				strSql += " and Name LIKE '%" + username + "%' ";
			if (pubkey != null)
				strSql += " and pubkey LIKE '%" + pubkey + "%'";
			if (verifydate != null)
				strSql += " and date LIKE '%" + verifydate + "%'";
			ResultSet resultSet = statement.executeQuery(strSql);
			while (resultSet.next()) {
				VerifylogBean a = new VerifylogBean();
				// a.setID(resultSet.getString("ID"));
				a.setname(resultSet.getString("name"));
				a.setip(resultSet.getString("ip"));
				// a.setlab(resultSet.getString("lab"));
				// a.setmac(resultSet.getString("mac"));
				// a.setmanufacture(resultSet.getString("manufacture"));
				a.setpubkey(resultSet.getString("pubkey"));
				// a.setsequence(resultSet.getString("sequence"));
				// a.setos(resultSet.getString("os"));
				a.setdate(resultSet.getString("date"));
				a.setuserid(Integer.parseInt(resultSet.getString("userid")));
				c.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

	public ArrayList findAllUnpassed() {
		ArrayList c = new ArrayList();
		try {
			Statement statement = connection.createStatement();
			String strSql = "select * from admins";
			ResultSet resultSet = statement.executeQuery(strSql);
			while (resultSet.next()) {

				String tmp = resultSet.getString("pass");
				
				if (tmp.equalsIgnoreCase("Y"))
					continue;

				String oid = Integer.toString(resultSet.getInt("OperatorID"));
				AdminBean a = new AdminBean();
				a.setOperatorID(oid);
				a.setPassword(resultSet.getString("Password"));
				a.setName(resultSet.getString("Name"));
				a.setAddress(resultSet.getString("Address"));
				a.setPhoneNum(resultSet.getString("PhoneNum"));
				a.setEmail(resultSet.getString("Email"));

				// add by lh
				a.setGender(resultSet.getString("gender"));
				a.setCertclass(resultSet.getString("certclass"));
				a.setCertnumber(resultSet.getString("certnumber"));
				a.setPass(resultSet.getString("pass"));
				a.setEndType(resultSet.getString("endtype"));
				// end

				c.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return c;
	}

	// end

	public boolean destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// ================================ Get ResultSet Methods
	// ===================================

	/**
	 * 获取全部用户信息
	 * 
	 * @param ip
	 * @param username
	 * @param pubkey
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet GetAllusers(String ip, String username, String pubkey) {

		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			String strSql = "select * from users where id > 0";
			if (ip != null)
				strSql += " and ip LIKE '%" + ip + "%'";
			if (username != null)
				strSql += " and Name LIKE '%" + username + "%' ";
			if (pubkey != null)
				strSql += " and pubkey LIKE '%" + pubkey + "%'";
			resultSet = statement.executeQuery(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}
	
	public ResultSet getallrestoreusers(int newuserid) {

		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			String strSql = "select fid from users where id = "+newuserid;
			ResultSet rs=null;
			rs=statement.executeQuery(strSql);
			rs.next();
			int l3=rs.getInt("fid");
			//rs.close();
			strSql = "select * from users where id > 0";
			strSql += " and fid = ";
			strSql += l3;
			
			resultSet = statement.executeQuery(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}

	/**
	 * 获取报警信息
	 * 
	 * @throws SQLException
	 */
	public ResultSet GetAlerts(GridPageBean pb) {

		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			String strSql;
			if(databasebean.ifmysql)
			{
				/*
				       strSql = "select id,name,ip,"
						+ " (select count(*) from alert where acknowledge = 0 and userid = users.ID) as amount ,"
						+ " (select issuedate from alert where acknowledge = 0 and userid =users.ID ORDER BY issuedate desc LIMIT 1) as latest"
						+ " from users where ID in (select DISTINCT userid from alert) ORDER BY latest desc;";
						*/
				
				//alert优化
				strSql="select id,name,ip,latestalertcount as amount,alertdate as latest from users where alertcount>0";
				
			}
			else
				strSql="select users.id,users.name,users.ip,count(users.id) as amount,max(issuedate) as latest from alert,users where alert.userid=users.id and alert.acknowledge=0 group by users.id,users.name,users.ip "
					+ "union select users.id,users.name,users.ip,0 as amount,max(issuedate) as latest from alert,users where alert.userid=users.id and alert.acknowledge=1 and users.id not in (select userid from alert where acknowledge=0) group by users.id,users.name,users.ip ORDER BY amount desc";
			//System.out.println("======strSql=====  " +strSql+ "  =========");
			resultSet = statement.executeQuery(strSql);
			
			
			strSql="select count(id) as count  from users  where alertcount>0  ";
			Statement stmt2 = connection.createStatement();
			ResultSet rs2 = stmt2.executeQuery(strSql);
			int total = 0;
			if (rs2.next()) {
				total = rs2.getInt("count");
			}
			pb.setTotal(total+"");
			rs2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}
	

	/**
	 * 获取详细报警信息
	 * 
	 * @throws SQLException
	 */
	/*
	public ResultSet GetAlertsDetail(String oid,
			String filename,
			String digest,
			String sname,
			String time,
			String ip,
			GridPageBean pb) {

		String start = pb.getStart();
		String limit = pb.getLimit();
		ResultSet resultSet = null;
		String strSql;
		Statement statement;
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(databasebean.ifmysql)
		{
		strSql = "select a.*,b.name,b.ip from alert a left join users b on a.userid = b.id where a.userid>0 ";		
		if(oid != null && !oid.equals(""))
			strSql += " and a.userid = " + oid;
	  	if(filename != null && !filename.equals(""))
			strSql += " and a.filename LIKE '%" + filename + "%'";
		if(digest != null && !digest.equals(""))
			strSql += " and a.digest LIKE '%" + digest + "%'";
		if(sname != null && !sname.equals(""))
			strSql += " and b.name LIKE '%" + sname + "%'";
		if(ip != null && !ip.equals(""))
			strSql += " and b.ip LIKE '%" + ip + "%'";
		if(time != null && !time.equals(""))
			strSql += " and a.issuedate LIKE '%" + time + "%'";
		strSql += " ORDER BY issuedate desc limit "
				+ start + "," + limit;
		//System.out.println("----AlertDetail-----  "+strSql+"  ----------");
		}
		else
		{
			if(start.equals("0"))
			{
			strSql = "select first "+pb.getLimit()+" a.*,b.name,b.ip from alert a left join users b on a.userid = b.id where a.userid>0 ";		
			if(oid != null && !oid.equals(""))
				strSql += " and a.userid = " + oid;
		  	if(filename != null && !filename.equals(""))
				strSql += " and a.filename LIKE '%" + filename + "%'";
			if(digest != null && !digest.equals(""))
				strSql += " and a.digest LIKE '%" + digest + "%'";
			if(sname != null && !sname.equals(""))
				strSql += " and b.name LIKE '%" + sname + "%'";
			if(ip != null && !ip.equals(""))
				strSql += " and b.ip LIKE '%" + ip + "%'";
			if(time != null && !time.equals(""))
				strSql += " and a.issuedate LIKE '%" + time + "%'";
			strSql += " ORDER BY issuedate desc";
			}
			else
			{
				int mystart=Integer.parseInt(pb.getStart());
				//mystart--;
				strSql = "select first "+mystart+" a.*,b.name,b.ip from alert a left join users b on a.userid = b.id where a.userid>0 ";		
				if(oid != null && !oid.equals(""))
					strSql += " and a.userid = " + oid;
			  	if(filename != null && !filename.equals(""))
					strSql += " and a.filename LIKE '%" + filename + "%'";
				if(digest != null && !digest.equals(""))
					strSql += " and a.digest LIKE '%" + digest + "%'";
				if(sname != null && !sname.equals(""))
					strSql += " and b.name LIKE '%" + sname + "%'";
				if(ip != null && !ip.equals(""))
					strSql += " and b.ip LIKE '%" + ip + "%'";
				if(time != null && !time.equals(""))
					strSql += " and a.issuedate LIKE '%" + time + "%'";
				strSql += " ORDER BY issuedate desc";
				Statement statement2;
				String date="";
				try {
					statement = connection.createStatement();
					ResultSet tmp = statement.executeQuery(strSql);
					while(mystart>0)
					{
						tmp.next();
						mystart--;
					}
					//tmp.last();
					{
						date=tmp.getString("issuedate");
					}
					tmp.close();
					//statement2.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				strSql = "select first "+pb.getLimit()+" a.*,b.name,b.ip from alert a left join users b on a.userid = b.id where a.userid>0 and a.issuedate <= '"+date+"'";		
				if(oid != null && !oid.equals(""))
					strSql += " and a.userid = " + oid;
			  	if(filename != null && !filename.equals(""))
					strSql += " and a.filename LIKE '%" + filename + "%'";
				if(digest != null && !digest.equals(""))
					strSql += " and a.digest LIKE '%" + digest + "%'";
				if(sname != null && !sname.equals(""))
					strSql += " and b.name LIKE '%" + sname + "%'";
				if(ip != null && !ip.equals(""))
					strSql += " and b.ip LIKE '%" + ip + "%'";
				if(time != null && !time.equals(""))
					strSql += " and a.issuedate LIKE '%" + time + "%'";
				strSql += " ORDER BY issuedate desc";
			}
		}
		String sql=strSql;
		try {
			
			strSql = "select count(*) as count from alert a left join users b on a.userid = b.ID where a.userid>0 ";		
			if(oid != null && !oid.equals(""))
				strSql += " and a.userid = " + oid;
		  	if(filename != null && !filename.equals(""))
				strSql += " and a.filename LIKE '%" + filename + "%'";
			if(digest != null && !digest.equals(""))
				strSql += " and a.digest LIKE '%" + digest + "%'";
			if(sname != null && !sname.equals(""))
				strSql += " and b.name LIKE '%" + sname + "%'";
			if(ip != null && !ip.equals(""))
				strSql += " and b.ip LIKE '%" + ip + "%'";
			if(time != null && !time.equals(""))
				strSql += " and a.issuedate LIKE '%" + time + "%'";
			//strSql += " ORDER BY issuedate desc ";
			Statement stmt2 = connection.createStatement();
			;
			ResultSet rs2 = stmt2.executeQuery(strSql);
			int total = 0;
			if (rs2.next()) {
				total = rs2.getInt("count");
			}
			pb.setTotal(total+"");
			rs2.close();
			stmt2.close();
			
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}
	
	*/
	// public ResultSet GetOperatorID(int OperatorID){
	//
	// ResultSet resultSet = null;
	// Statement statement;
	// try {
	// statement = connection.createStatement();
	// String strSql="select * from admins where OperatorID="+OperatorID;
	// resultSet=statement.executeQuery(strSql);
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	//
	//
	// return resultSet;
	//
	// }
	
	public ResultSet GetAlertsDetail(String oid,
			String filename,
			String digest,
			String sname,
			String time,
			String ip,
			GridPageBean pb) {

		String start = pb.getStart();
		String limit = pb.getLimit();
		ResultSet resultSet = null;
		String strSql = null;
		Statement statement;
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(databasebean.ifmysql)
		{
			
					
			if(oid != null && !oid.equals(""))
				strSql = "select a.*,b.name,b.ip from alert a left join users b on a.userid = b.id where a.userid="+oid;
			else strSql = "select a.*,b.name,b.ip from alert a left join users b on a.userid = b.id where 1=1";	
		
		  	if(filename != null && !filename.equals(""))
				strSql += " and a.filename LIKE '%" + filename + "%'";
			if(digest != null && !digest.equals(""))
				strSql += " and a.digest LIKE '%" + digest + "%'";
			if(sname != null && !sname.equals(""))
				strSql += " and b.name LIKE '%" + sname + "%'";
			if(ip != null && !ip.equals(""))
				strSql += " and b.ip LIKE '%" + ip + "%'";
			if(time != null && !time.equals(""))
				strSql += " and a.issuedate LIKE '%" + time + "%'";
			strSql += " ORDER BY issuedate desc limit "
					//strSql += "  limit "
					+ start + "," + limit;
			//System.out.println("----AlertDetail-----  "+strSql+"  ----------");
			
			
			String sqlCount="";
			
			
			if((filename==null||filename.equals(""))&&(digest==null||digest.equals(""))&&(sname==null||sname.equals(""))&&(ip==null||ip.equals(""))&&(time==null||time.equals("")))
				//alert优化后
				 sqlCount = "select alertcount as count from users where id="+oid;
			
			
			//alert优化前
			
			else
			{
			if(oid != null && !oid.equals(""))
				sqlCount = "select count(*) as count from alert a left join users b on a.userid = b.ID where a.userid="+oid;
			else
				sqlCount = "select count(*) as count from alert a left join users b on a.userid = b.ID where 1=1";
		  	if(filename != null && !filename.equals(""))
		  		sqlCount += " and a.filename LIKE '%" + filename + "%'";
			if(digest != null && !digest.equals(""))
				sqlCount += " and a.digest LIKE '%" + digest + "%'";
			if(sname != null && !sname.equals(""))
				sqlCount += " and b.name LIKE '%" + sname + "%'";
			if(ip != null && !ip.equals(""))
				sqlCount += " and b.ip LIKE '%" + ip + "%'";
			if(time != null && !time.equals(""))
				sqlCount += " and a.issuedate LIKE '%" + time + "%'";
			//strSql += " ORDER BY issuedate desc ";
			}
			Statement stmt2;
			try {
				stmt2 = connection.createStatement();
				ResultSet rs2 = stmt2.executeQuery(sqlCount);
				int total = 0;
				if (rs2.next()) {
					total = rs2.getInt("count");
				}
				pb.setTotal(total+"");
				rs2.close();
				stmt2.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		else	//if beyondDB
		{
			strSql = "select a.id from alert a left join users b on a.userid = b.id where a.userid>0 ";
			if(oid != null && !oid.equals(""))
				strSql += " and a.userid = " + oid;
		  	if(filename != null && !filename.equals(""))
				strSql += " and a.filename LIKE '%" + filename + "%'";
			if(digest != null && !digest.equals(""))
				strSql += " and a.digest LIKE '%" + digest + "%'";
			if(sname != null && !sname.equals(""))
				strSql += " and b.name LIKE '%" + sname + "%'";
			if(ip != null && !ip.equals(""))
				strSql += " and b.ip LIKE '%" + ip + "%'";
			if(time != null && !time.equals(""))
				strSql += " and a.issuedate LIKE '%" + time + "%'";
			strSql += "order by a.id desc";
			ArrayList list=new ArrayList();
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(strSql);
				
				while(resultSet.next())
				{
					list.add(resultSet.getInt(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pb.setTotal(list.size()+"");
			int tmpstart=Integer.parseInt(pb.getStart());
			int tmpend=Integer.parseInt(pb.getLimit())+tmpstart-1;
			if(tmpend>=list.size())
				tmpend = list.size()-1;
			int mystart=(Integer) list.get(tmpstart);
			int myend=(Integer) list.get(tmpend);
			strSql = "select a.*,b.name,b.ip from alert a left join users b on a.userid = b.id where a.userid>0 and a.id<="+mystart+" and a.id>="+myend;		
			if(oid != null && !oid.equals(""))
				strSql += " and a.userid = " + oid;
		  	if(filename != null && !filename.equals(""))
				strSql += " and a.filename LIKE '%" + filename + "%'";
			if(digest != null && !digest.equals(""))
				strSql += " and a.digest LIKE '%" + digest + "%'";
			if(sname != null && !sname.equals(""))
				strSql += " and b.name LIKE '%" + sname + "%'";
			if(ip != null && !ip.equals(""))
				strSql += " and b.ip LIKE '%" + ip + "%'";
			if(time != null && !time.equals(""))
				strSql += " and a.issuedate LIKE '%" + time + "%'";
			strSql += " ORDER BY issuedate desc";
			
		}//end of beyondDB
		
		String sql=strSql;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}
//	public ResultSet GetAlerts(GridPageBean pb) {
//
//		ResultSet resultSet = null;
//		try {
//			Statement statement = connection.createStatement();
//			String strSql;
//			if(databasebean.ifmysql)
//			{
//				/*
//				       strSql = "select id,name,ip,"
//						+ " (select count(*) from alert where acknowledge = 0 and userid = users.ID) as amount ,"
//						+ " (select issuedate from alert where acknowledge = 0 and userid =users.ID ORDER BY issuedate desc LIMIT 1) as latest"
//						+ " from users where ID in (select DISTINCT userid from alert) ORDER BY latest desc;";
//						*/
//				
//				//alert优化
//				strSql="select id,name,ip,latestalertcount as amount,alertdate as latest from users where alertcount>0";
//				
//			}
//			else
//				strSql="select users.id,users.name,users.ip,count(users.id) as amount,max(issuedate) as latest from alert,users where alert.userid=users.id and alert.acknowledge=0 group by users.id,users.name,users.ip "
//					+ "union select users.id,users.name,users.ip,0 as amount,max(issuedate) as latest from alert,users where alert.userid=users.id and alert.acknowledge=1 and users.id not in (select userid from alert where acknowledge=0) group by users.id,users.name,users.ip ORDER BY amount desc";
//			//System.out.println("======strSql=====  " +strSql+ "  =========");
//			resultSet = statement.executeQuery(strSql);
//			
//			
//			strSql="select count(id) as count  from users  where alertcount>0  ";
//			Statement stmt2 = connection.createStatement();
//			ResultSet rs2 = stmt2.executeQuery(strSql);
//			int total = 0;
//			if (rs2.next()) {
//				total = rs2.getInt("count");
//			}
//			pb.setTotal(total+"");
//			rs2.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return resultSet;
//	}
//	
/**
	public ResultSet GetAuditDetail(String oid,
			String action,
			String role,
			String user,
			String time,
			String type,
			GridPageBean pb) {
	//	AdminBean ab = new AdminBean();
		
	
		String start = pb.getStart();
		String limit = pb.getLimit();
		System.out.println("start"+start);
		System.out.println("limit:"+limit);
		ResultSet resultSet = null;
		String strSql = null;
		Statement statement;
		//ab.setOperatorID(oid);
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		if(databasebean.ifmysql)
		{
		if(oid != null && !oid.equals(""))
			strSql = "select a.*,b.name,b.ip from auditlog a left join users b on a.userid = b.id where a.userid="+oid;
		else strSql = "select a.*,b.name,b.ip from auditlog a left join users b on a.userid = b.id where 1=1";	
//		if(oid != null && !oid.equals(""))
//			//strSql="select role,type,action,time from users";
//			strSql = "select * from auditlog where userid=" + oid;
//				
			
				
				
		
		
		  	if(action != null && !action.equals(""))
				strSql += " and a.action LIKE '%" + action + "%'";
			if(role != null && !role.equals(""))
				strSql += " and a.role LIKE '%" + role + "%'";
			if(user != null && !user.equals(""))
				strSql += " and a.user LIKE '%" + user + "%'";
			if(type != null && !type.equals(""))
				strSql += " and a.type LIKE '%" + type + "%'";
			if(time != null && !time.equals(""))
				strSql += " and a.time LIKE '%" + time + "%'";
			strSql += " ORDER BY time desc limit "
					//strSql += "  limit "
					+ start + "," + limit;
			//System.out.println("----AlertDetail-----  "+strSql+"  ----------");
			
			
			String sqlCount="";
			
			
			if((action==null||action.equals(""))&&(role==null||role.equals(""))&&(user==null||user.equals(""))&&(type==null||type.equals(""))&&(time==null||time.equals("")))
				//alert优化后
				 sqlCount = "select alertcount as count from users where id="+oid;
			
			
			//alert优化前
			
			else
			{
			if(oid != null && !oid.equals(""))
				sqlCount = "select count(*) as count from auditlog a left join users b on a.userid = b.id where a.userid="+oid;
			else
				sqlCount = "select count(*) as count from auditlog a left join users b on a.userid = b.id where 1=1";
		  	if(action != null && !action.equals(""))
		  		sqlCount += " and a.action LIKE '%" + action + "%'";
		
			if(role != null && !role.equals(""))
				sqlCount += " and a.role LIKE '%" + role + "%'";
			if(user != null && !user.equals(""))
				sqlCount += " and a.user LIKE '%" + user + "%'";
			if(type != null && !type.equals(""))
				sqlCount += " and a.type LIKE '%" + type + "%'";
			if(time != null && !time.equals(""))
				sqlCount += " and a.time LIKE '%" + time + "%'";
			//strSql += " ORDER BY issuedate desc ";
			}
			Statement stmt2;
			try {
				stmt2 = connection.createStatement();
				ResultSet rs2 = stmt2.executeQuery(sqlCount);
				int total = 0;
				if (rs2.next()) {
					total = rs2.getInt("count");
					System.out.println("total: "+total);
				}
				pb.setTotal(total+"");
				System.out.println("total 1: "+total);
				rs2.close();
				stmt2.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		else	//if beyondDB
		{
			strSql = "select a.id from auditlog a left join users b on a.userid = b.id where a.userid>0 ";
			//strSql = "select id from auditlog where userid>0 ";
			if(oid != null && !oid.equals(""))
				strSql += " and a.userid = " + oid;
		  	if(action != null && !action.equals(""))
				strSql += " and a.action LIKE '%" + action + "%'";
			if(role != null && !role.equals(""))
				strSql += " and a.role LIKE '%" + role + "%'";
			if(user != null && !user.equals(""))
				strSql += " and a.user LIKE '%" + user + "%'";
			if(type != null && !type.equals(""))
				strSql += " and a.type LIKE '%" + type + "%'";
			if(time != null && !time.equals(""))
				strSql += " and a.time LIKE '%" + time + "%'";
			strSql += "order by time desc";
			ArrayList list=new ArrayList();
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(strSql);
				
				while(resultSet.next())
				{
					list.add(resultSet.getInt(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("listsize"+list.size());
			pb.setTotal(list.size()+"");
			int tmpstart=Integer.parseInt(pb.getStart());
			int tmpend=Integer.parseInt(pb.getLimit())+tmpstart-1;
			System.out.println("tmpstart"+tmpstart);
			System.out.println("tmpend"+tmpend);
			if(tmpend>=list.size())
				tmpend = list.size()-1;
			int mystart=(Integer) list.get(tmpstart);
			int myend=(Integer) list.get(tmpend);
			strSql = "select a.*,b.name,b.ip from auditlog a left join users b on a.userid = b.id where a.userid>0 and a.id<="+mystart+" and a.id>="+myend;		
		//	strSql = "select role,type from auditlog where userid>0 and userid<="+mystart+" and userid>="+myend;		
			if(oid != null && !oid.equals(""))
				strSql += " and a.userid = " + oid;
		  	if(action != null && !action.equals(""))
				strSql += " and a.action LIKE '%" + action + "%'";
			if(role != null && !role.equals(""))
				strSql += " and a.role LIKE '%" + role + "%'";
			if(user != null && !user.equals(""))
				strSql += " and a.user LIKE '%" + user + "%'";
			if(type!= null && !type.equals(""))
				strSql += " and a.type LIKE '%" + type + "%'";
			if(time != null && !time.equals(""))
				strSql += " and a.time LIKE '%" + time + "%'";
			strSql += " ORDER BY issuedate desc";
			
		}//end of beyondDB
		
		String sql=strSql;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}
**/
	
	public ResultSet GetUsbLocal(String oid) {

		ResultSet resultSet = null;
		String strSql = null;
		Statement statement;
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(oid != null && !oid.equals("")){
			strSql = "select * from usblocal where terminalid=" + oid;
		}
		else return null;
		
			ArrayList list=new ArrayList();
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(strSql);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		String sql=strSql;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}
	
	


 public ResultSet GetAuditDetail(String oid,
			String action,
			String role,
			String user,
			String time,
			String type,
			GridPageBean pb) {
	 	String start = pb.getStart();
		String limit = pb.getLimit();
		System.out.println("start"+start);
		System.out.println("limit:"+limit);
	 ResultSet resultSet = null;
	 Statement statement;
	 String strSql = null;
	 try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 //信任全部
	 if(databasebean.ifmysql)
		{

		//String countSql = "select count(*) as count from auditlog where userid=" +oid;
		  String countSql = "select count(*) as count from auditlog";
		//strSql = "select * from auditlog where userid=" + oid;
		
		strSql = "select * from auditlog where userid like '%%'";
		 System.out.println("------------oid: "+oid);
			if(role != null && !role.equals(""))
				strSql += " and role LIKE '%" + role + "%'";
			if(user != null && !user.equals(""))
				strSql += " and user LIKE '%" + user + "%'";
			if(type != null && !type.equals(""))
				strSql += " and type LIKE '%" + type + "%'";
			if(action !=null&& !action.equals(""))
				strSql += " and action LIKE '%" + action + "%'";
			if(time != null && !time.equals(""))
				strSql += " and time LIKE '%" + time + "%'";
			strSql += " ORDER BY time desc limit "
					//strSql += "  limit "
					+ start + "," + limit;
			System.out.println("final-----role"+role);
			//System.out.println("finalstrSql-----"+strSql);
		//	resultSet = statement.executeQuery(strSql);
			Statement stmt2;
			try {
				stmt2 = connection.createStatement();
				ResultSet rs2 = stmt2.executeQuery(countSql);
				int total = 0;
				if (rs2.next()) {
					total = rs2.getInt("count");
					System.out.println("total: "+total);
				}
				pb.setTotal(total+"");
				System.out.println("total 1: "+total);
				rs2.close();
				stmt2.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			
			strSql = "select id from auditlog where userid>0 ";
			//strSql = "select id from auditlog where userid>0 ";
			if(oid != null && !oid.equals(""))
				strSql += " and userid = " + oid;
		  	if(action != null && !action.equals(""))
				strSql += " and action LIKE '%" + action + "%'";
			if(role != null && !role.equals(""))
				strSql += " and role LIKE '%" + role + "%'";
			if(user != null && !user.equals(""))
				strSql += " and user LIKE '%" + user + "%'";
			if(type != null && !type.equals(""))
				strSql += " and type LIKE '%" + type + "%'";
			if(time != null && !time.equals(""))
				strSql += " and time LIKE '%" + time + "%'";
			strSql += "order by time desc";
			ArrayList list=new ArrayList();
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(strSql);
				
				while(resultSet.next())
				{
					list.add(resultSet.getInt(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("listsize"+list.size());
			pb.setTotal(list.size()+"");
			int tmpstart=Integer.parseInt(pb.getStart());
			int tmpend=Integer.parseInt(pb.getLimit())+tmpstart-1;
			System.out.println("tmpstart"+tmpstart);
			System.out.println("tmpend"+tmpend);
			if(tmpend>=list.size())
				tmpend = list.size()-1;
			int mystart=(Integer) list.get(tmpstart);
			int myend=(Integer) list.get(tmpend);
			strSql = "select a.*,b.name,b.ip from auditlog a left join users b on a.userid = b.id where a.userid>0 and a.id<="+mystart+" and a.id>="+myend;		
		//	strSql = "select role,type from auditlog where userid>0 and userid<="+mystart+" and userid>="+myend;		
			if(oid != null && !oid.equals(""))
				strSql += " and a.userid = " + oid;
		  	if(action != null && !action.equals(""))
				strSql += " and a.action LIKE '%" + action + "%'";
			if(role != null && !role.equals(""))
				strSql += " and a.role LIKE '%" + role + "%'";
			if(user != null && !user.equals(""))
				strSql += " and user LIKE '%" + user + "%'";
			if(type!= null && !type.equals(""))
				strSql += " and type LIKE '%" + type + "%'";
			if(time != null && !time.equals(""))
				strSql += " and time LIKE '%" + time + "%'";
			strSql += " ORDER BY time desc";
			
		}//end of beyondDB
		
		String sql=strSql;
		try {
			System.out.println(sql);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}
		
	

	
	public ResultSet GetVerifylog(String ip, String username, String pubkey,
			String verifydate) {

		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			String strSql = "select * from verify_log where id > 0";
			if (ip != null)
				strSql += " and ip LIKE '%" + ip + "%'";
			if (username != null)
				strSql += " and Name LIKE '%" + username + "%' ";
			if (pubkey != null)
				strSql += " and pubkey LIKE '%" + pubkey + "%'";
			if (verifydate != null)
				strSql += " and date LIKE '%" + verifydate + "%'";
			resultSet = statement.executeQuery(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

}
