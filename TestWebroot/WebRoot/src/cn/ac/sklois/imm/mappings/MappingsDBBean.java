package cn.ac.sklois.imm.mappings;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ics.usb.UsbBean;
import com.java1234.util.auditconf;

import cn.ac.sklois.imm.admin.AdminBean;
import cn.ac.sklois.imm.admin.AdminsDBBean;
import cn.ac.sklois.imm.admin.Logtest;
import cn.ac.sklois.imm.admin.UserBean;
import cn.ac.sklois.imm.admin.databasebean;
import cn.ac.sklois.imm.upload.VerifyDigestBean;;

public class MappingsDBBean {
//    static Logger logger = Logger.getLogger(MappingsDBBean.class);
	private Connection connection=null;
	Logtest log = new Logtest();
	//private static final String DBDRIVER = "com.beyondb.jdbc.BeyondbDriver"; // 驱动类类名
	//private static final String DBURL = "jdbc:beyondb://localhost:II7/immdb_new"; // 连接URL
	//private static final String DBUSER = "tcwg"; // 数据库用户名
	//private static final String DBPASSWORD = "123456"; // 数据库密码
	private static String whitelist="";
	private static String digest="";
	private static String filename="";
	private static String softwarename="";
	private static int vern=-1;
	MappingsDBBean(){//���캯��
		/*
		if(databasebean.ifmysql)
		{
			whitelist="whitelist";
			digest="digest";
			filename="filename";
			softwarename="softwarename";
		}*/
		//else
		{
			whitelist="whitelist_content";
			digest="hash_value";
			filename="process_path";
			softwarename="process_name";
		}
		try {
			if(!databasebean.ifmysql)
			{
				Class.forName(databasebean.DBDRIVER); // 注册驱动
				connection = DriverManager.getConnection(databasebean.DBURL,databasebean. DBUSER, databasebean.DBPASSWORD); // 获得连接对象
			}
//			PropertyConfigurator.configure("log4j.properties");
			else
			{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/immdb_new?user=root&password=tcwg&useUnicode=true&characterEncoding=utf8");
			}
				//connection=DriverManager.getConnection("jdbc:mysql://192.168.4.125:3306/immdb?user=root&password=sklois&useUnicode=true&characterEncoding=utf-8");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 catch (SQLException e) {
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
	public VerifyDigestBean findVerifyInfobyID(int aid)
	{
		VerifyDigestBean a = new VerifyDigestBean();
		try
		{
			Statement statement = connection.createStatement();
			String strSql="select * from verifyfail_table ";
			strSql += " where id = '"+aid +"'";
			log.logger.info(strSql);
			ResultSet resultSet=statement.executeQuery(strSql);
			if(resultSet.next())
			{
				a.setID(aid);
				a.setName(resultSet.getString("name"));
				a.setDigest(resultSet.getString("digest"));
				a.setTrusted(Integer.parseInt(resultSet.getString("trusted")));
				a.setSoftware(resultSet.getString("softwareName"));
			}
			else
				return null;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
	public FullInfoBean findbyID(int aid){
		log.logger.info("step into mappingsDBBean findbyID ********************");
		FullInfoBean a=new FullInfoBean();
		try
		{
			Statement statement = connection.createStatement();
			String strSql="select * from digest_table a,class_des b, admins d ";
			strSql += "	where a.classid=b.id and a.createID=d.operatorID ";
			strSql += " and a.id = '"+aid +"'";
			log.logger.info(strSql);
			ResultSet resultSet=statement.executeQuery(strSql);
			if(resultSet.next())
			{
				a.setID(aid);
				a.setFileName(resultSet.getString("FileName"));
				a.setDigest(resultSet.getString("digest"));
				a.setEdition(resultSet.getString("edition"));
				a.setClassID(Integer.parseInt(resultSet.getString("classID")));
				a.setClassName(resultSet.getString("classDescription"));
				a.setDescription(resultSet.getString("description"));
				a.setTrusted(Integer.parseInt(resultSet.getString("trusted")));
				a.setCreateID(Integer.parseInt(resultSet.getString("operatorID")));
				a.setCreateName(resultSet.getString("name"));
				a.setIssueTime(resultSet.getString("issueDate"));
				a.setSoftwareName(resultSet.getString("SoftwareName"));
			}
			else
				return null;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
	public int checkaudit()
	{
		int res=0;
		try {
			Statement statement=connection.createStatement();
			String strSql="select count(*) as count from auditlog";
			//log.logger.info(strSql);
			ResultSet resultSet=statement.executeQuery(strSql);
			if(resultSet.next())
				res=resultSet.getInt("count");
			if(res>auditconf.max)
				return -1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
		
		return res;
	}

	public FullInfoBean findWhitebyID(int aid){
		log.logger.info("step into mappingsDBBean findbyID ********************");
		FullInfoBean a=new FullInfoBean();
		try
		{
			Statement statement = connection.createStatement();
			String strSql;
			/*
			if(databasebean.ifmysql)
			{
			 strSql="select * from whitelist a,class_des b, admins d ";
				strSql += "	where a.classid=b.id and a.createID=d.operatorID ";
				strSql += " and a.id = '"+aid +"'";
			}*/
			//else
			{
				strSql="select * from "+whitelist+" a";
				//strSql += "	where a.classid=b.id and a.createID=d.operatorID ";
				strSql += " where a.id = '"+aid +"'";
			}
			log.logger.info(strSql);
			ResultSet resultSet=statement.executeQuery(strSql);
			if(resultSet.next())
			{
				a.setID(aid);
				a.setFileName(resultSet.getString(filename));
				a.setDigest(resultSet.getString(digest));
				/*
				if(databasebean.ifmysql)
				{
					a.setEdition(resultSet.getString("edition"));
					a.setClassID(Integer.parseInt(resultSet.getString("classID")));
					a.setClassName(resultSet.getString("classDescription"));
					a.setDescription(resultSet.getString("description"));
					a.setTrusted(Integer.parseInt(resultSet.getString("trusted")));
					a.setCreateID(Integer.parseInt(resultSet.getString("operatorID")));
					a.setCreateName(resultSet.getString("name"));
					a.setIssueTime(resultSet.getString("issueDate"));
				}*/
				a.setSoftwareName(resultSet.getString(softwarename));
			}
			else
				return null;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
	
	public int getclassID(String cname)
	{
		try {
			Statement statement=connection.createStatement();
			String strSql = "select * from class_des ";
			strSql += "where description = '"+cname+"'";
			log.logger.info(strSql);
			ResultSet resultSet=statement.executeQuery(strSql);
			if(resultSet.next())
				return Integer.parseInt(resultSet.getString("id"));
			else
				return -1;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	//attid+attvalue+hash��Ψһȷ��һ��ֵ
	public MappingBean findbyAttributeAndHash(Attribute att,String hashvalue){
		MappingBean a=new MappingBean();
		try {
			Statement statement=connection.createStatement();
			String strSql="select * from mappings where AttributeID="+att.getAttributeID()+" and AttributeValue='"+att.getAttributeValue()+"' and HashValue='"+hashvalue+"' ";
			ResultSet resultSet=statement.executeQuery(strSql);
			if(resultSet.next()){
				a.setAtt(att);
				
				MeasurementValues mvs=new MeasurementValues();
				
				mvs.setHashValues(resultSet.getString("HashValue"));
				mvs.setCopyRight(resultSet.getString("CopyRight"));
				mvs.setDescription(resultSet.getString("Description"));
				mvs.setEdition(resultSet.getString("Edition"));
				mvs.setIssueTime(resultSet.getString("IssueTime"));
				mvs.setManufacturer(resultSet.getString("Manufacturer"));
				mvs.setSoftwareName(resultSet.getString("SoftwareName"));
				mvs.setSoftwareClass(resultSet.getString("Class"));
			    
				a.setMvs(mvs);
				
				Operation creation=new Operation();
				creation.setOperatorID(resultSet.getInt("CreateOid"));
				creation.setOperationTime(resultSet.getTimestamp("CreateTime"));
				a.setCreation(creation);
				
				Operation modification=new Operation();
				modification.setOperatorID(resultSet.getInt("ModifyOid"));
				modification.setOperationTime(resultSet.getTimestamp("ModifyTime"));
				a.setModification(modification);
				
			}else return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
	
/*	
	public boolean ModifybyAttributeAndHash(MappingBean mb){
		MappingBean a=new MappingBean();
		try {
			Statement statement=connection.createStatement();
			String strSql="update mappings set HashValue= '"+mb.getMvs().getHashValues()
			+"' , SoftwareName='"+mb.getMvs().getSoftwareName()+"' , Edition='"
			+mb.getMvs().getEdition()+"', Manufacturer='"+mb.getMvs().getManufacturer()+"', IssueTime='"
			+mb.getMvs().getIssueTime()+"', Description='"+mb.getMvs().getDescription()+"', CopyRight='"
			+mb.getMvs().getCopyRight()+"', Class='"+mb.getMvs().getSoftwareClass()+"', AttributeID="
			+mb.getAtt().getAttributeID()+", AttributeValue='"
			+mb.getAtt().getAttributeValue()+"', ModifyTime= '"
			+mb.getModification().getOperationTime().toString()+"', ModifyOid="
			+mb.getModification().getOperatorID()+"  where AttributeID="+mb.getAtt().getAttributeID()+" and AttributeValue='"+mb.getAtt().getAttributeValue()+"' and HashValue='"+mb.getMvs().getHashValues()+"' ";
			
			statement.executeUpdate(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
*/	
	public boolean findMappingsByID(int aid)
	{
		try {
			Statement statement=connection.createStatement();
			String strSql = "select * from mappings a ";
			strSql += "where a.id = "+aid;
			log.logger.info(strSql);
			ResultSet resultSet=statement.executeQuery(strSql);
			if(resultSet.next())
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean ModifybyID(int aid,FullInfoBean fb)
	{
		try {
			Statement statement=connection.createStatement();
			int newid = findByHashandName(fb.getFileName(),fb.getDigest());
			log.logger.info("********88fb.getID="+fb.getID());
			if((newid>0)&&(newid!=aid))
				return false;
			String strSql="update digest_table set digest= '"+fb.getDigest()
			+"' , fileName='"+fb.getFileName()+"', edition='"+fb.getEdition()+"' , classID= "+fb.getClassID()+", trusted= "
			+fb.getTrusted()+",SoftwareName='"+fb.getSoftwareName()+"',issueDate='"+fb.getIssueTime()+"',description='"+fb.getDescription()+"'  where ID="+aid;
			log.logger.info(strSql);
			statement.executeUpdate(strSql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		}
		return true;
	}	
	
	public boolean ModifyWhitelistbyID(int aid,FullInfoBean fb,int userid)
	{
		try {
			Statement statement=connection.createStatement();
			int newid = findWhiteByHashAndUserid(fb.getDigest(),userid);
			log.logger.info("********88fb.getID="+fb.getID());
			if((newid>0)&&(newid!=aid))
				return false;
			String strSql;
			/*
			if(databasebean.ifmysql)
			{
				strSql="update whitelist set digest= '"+fb.getDigest()
						+"' , fileName='"+fb.getFileName()+"', edition='"+fb.getEdition()+"' , classID= "+fb.getClassID()+", trusted= "
						+fb.getTrusted()+",SoftwareName='"+fb.getSoftwareName()+"',issueDate='"+fb.getIssueTime()+"',description='"+fb.getDescription()+"'  where ID="+aid;
			}
			*/
			//else
			{
			strSql="update "+whitelist+" set "+digest+"= '"+fb.getDigest()
			+"' , "+filename+"='"+fb.getFileName()+","+softwarename+"='"+fb.getSoftwareName()+"'  where ID="+aid;
			}
			log.logger.info(strSql);
			statement.executeUpdate(strSql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		}
		return true;
	}	
	
	
	
	public boolean ModifyTrustedbyID(int aid)
	{
		try {
			Statement statement=connection.createStatement();
			String strSql="update digest_table set trusted=1 where id="+aid;
			statement.executeUpdate(strSql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		}
		return true;
	}
	public boolean ModifybyAttributeAndHash(int aid,String avalue, String hash,MappingBean mb){
		MappingBean a=new MappingBean();
		try {
			
			Statement statement=connection.createStatement();
			String strSql="update mappings set HashValue= '"+mb.getMvs().getHashValues()
			+"' , SoftwareName='"+mb.getMvs().getSoftwareName()+"' , Edition='"
			+mb.getMvs().getEdition()+"', Manufacturer='"+mb.getMvs().getManufacturer()+"', IssueTime='"
			+mb.getMvs().getIssueTime()+"', Description='"+mb.getMvs().getDescription()+"', CopyRight='"
			+mb.getMvs().getCopyRight()+"', Class='"+mb.getMvs().getSoftwareClass()+"', AttributeID="
			+mb.getAtt().getAttributeID()+", AttributeValue='"
			+mb.getAtt().getAttributeValue()+"', ModifyTime= '"
			+mb.getModification().getOperationTime().toString()+"', ModifyOid="
			+mb.getModification().getOperatorID()+"  where AttributeID="+aid+" and AttributeValue='"+avalue+"' and HashValue='"+hash+"' ";
			
			statement.executeUpdate(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean createwlsnapshot(WhitelistSearchBean wsearch,int userid)
	{
		Statement statement;
		String strSql;
		try {
			statement = connection.createStatement();
		
			
			AdminsDBBean dbBean=new AdminsDBBean();
			UserBean user=dbBean.findUserbyID(userid);
			String pk=user.getpubkey();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
			String date=df.format(new Date());
			ResultSet res=statement.executeQuery("SELECT * FROM wlsnapshotindex");
	        res.last();
	        String str1 = res.getString("description");
			strSql="insert into wlsnapshotindex(description,issuedate,userid) values('"+str1+"','"+date+"',"+userid+")";
			String str2 = "delete from wlsnapshotindex where userid = '1';";
			//strSql="insert into wlsnapshotindex(description,issuedate,userid) values('无','"+date+"',"+userid+")";
			statement.executeUpdate(strSql);
			strSql="select id from wlsnapshotindex where issuedate='"+date+"' and userid="+userid;
			ResultSet rs=statement.executeQuery(strSql);
			int indexid=0;
			if(rs.next())
				indexid=rs.getInt("id");
			else
				return false;
			strSql="insert into wlsnapshot(process_name,process_path,hash_value,indexid) select process_name,process_path,hash_value,"+indexid+" from whitelist_content where userid="+userid+" and whitelist_vern=-1";
			if(wsearch.getsname()!=null)
				strSql+=" and process_name LIKE '%"+wsearch.getsname()+"%' ";
			if(wsearch.getsdigest()!=null)
				strSql+=" and hash_value like '%"+wsearch.getsdigest()+"%'";
			//if(!databasebean.ifmysql)
				//strSql+="and not exists (select 1 from "+whitelist+" where "+digest+"=digest_table.digest and "+filename+"=digest_table.filename and "+softwarename+"=digest_table.softwarename)";
			statement.executeUpdate(strSql);
			statement.executeUpdate(str2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public ArrayList findAll(){
		ArrayList c=new ArrayList();
		try {
			Statement statement=connection.createStatement();
			String strSql="select * from mappings";
			ResultSet resultSet=statement.executeQuery(strSql);
			while(resultSet.next()){
				MappingBean a=new MappingBean();
				
				Attribute att=new Attribute();
				att.setAttributeID(resultSet.getInt("AttributeID"));
				att.setAttributeValue(resultSet.getString("AttributeValue"));
				a.setAtt(att);
				
				MeasurementValues mvs=new MeasurementValues();
				mvs.setHashValues(resultSet.getString("HashValue"));
				mvs.setCopyRight(resultSet.getString("CopyRight"));
				mvs.setDescription(resultSet.getString("Description"));
				mvs.setEdition(resultSet.getString("Edition"));
				mvs.setIssueTime(resultSet.getString("IssueTime"));
				mvs.setManufacturer(resultSet.getString("Manufacturer"));
				mvs.setSoftwareName(resultSet.getString("SoftwareName"));
				mvs.setSoftwareClass(resultSet.getString("Class"));
				
				a.setMvs(mvs);
				
				Operation creation=new Operation();
				creation.setOperatorID(resultSet.getInt("CreateOid"));
				creation.setOperationTime(resultSet.getTimestamp("CreateTime"));
				a.setCreation(creation);
				
				Operation modification=new Operation();
				modification.setOperatorID(resultSet.getInt("ModifyOid"));
				modification.setOperationTime(resultSet.getTimestamp("ModifyTime"));
				a.setModification(modification);
				
				c.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return c;
	}

	/*
	public ArrayList findLimit(int start, int num){//
		
		ArrayList c=new ArrayList();
		try {
			Statement statement=connection.createStatement();
			String strSql="select * from mappings limit "+start+","+num;
			ResultSet resultSet=statement.executeQuery(strSql);
			while(resultSet.next()){
				MappingBean a=new MappingBean();
				
				Attribute att=new Attribute();
				att.setAttributeName(resultSet.getString("AttributeName"));
				att.setAttributeValue(resultSet.getString("AttributeValue"));
				a.setAtt(att);
				
				MeasurementValues mvs=new MeasurementValues();
				mvs.setHashNum(resultSet.getInt("HashNum"));
				mvs.setHashValues(resultSet.getString("HashValue"));
				a.setMvs(mvs);
				
				Operation creation=new Operation();
				creation.setOperatorID(resultSet.getInt("CreateOid"));
				creation.setOperationTime(resultSet.getTimestamp("CreateTime"));
				a.setCreation(creation);
				
				Operation modification=new Operation();
				modification.setOperatorID(resultSet.getInt("ModifyOid"));
				modification.setOperationTime(resultSet.getTimestamp("ModifyTime"));
				a.setModification(modification);
				
				c.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		return c;
	}
	
	
	public int getCountofMappings(){
		
		int totalnum=0;
		try {
			Statement statement=connection.createStatement();
			String sqlcount="select count(*) as num from mappings";
			ResultSet rs=statement.executeQuery(sqlcount);
			rs.next();
			totalnum=rs.getInt("num");
			//System.out.println("num="+totalnum);
		} catch (SQLException e1) {//û�л�ȡ����Ŀ���򷵻�-1��
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("����Ŀ���ȡʧ�ܣ�");
			return -1;
		}
		return totalnum;
	}
	*/
	
	//added by lh
	public ArrayList findFailbyName(String sname,String fname,int trusted,String clientIP)
	{
		ArrayList c=new ArrayList();
		try {
			Statement statement=connection.createStatement();
			String strSql="select * from verifyfail_table where 1=1 ";
			
			if (trusted!=-1)
				strSql+=" and trusted = '"+trusted+"'";
			
			if(sname!=null)
				strSql+=" and softwarename LIKE '%"+sname+"%' ";
			
			if(fname!=null)
				strSql+=" and name LIKE '%"+fname+"%' ";
			
			if(clientIP!=null)
				strSql+=" and ip = '"+clientIP+"'";


		      // BasicConfigurator replaced with PropertyConfigurator.

//		          logger.info("Entering application.");
		          log.logger.info(strSql);
//		          logger.info("Exiting application.");
			
			
			
			ResultSet resultSet=statement.executeQuery(strSql);
			while(resultSet.next()){
				VerifyDigestBean a = new VerifyDigestBean();
				a.setID(Integer.parseInt(resultSet.getString("id")));
				a.setName(resultSet.getString("name"));
				a.setDigest(resultSet.getString("digest"));
				a.setTrusted(Integer.parseInt(resultSet.getString("trusted")));
				a.setIP(resultSet.getString("ip"));
				a.setSoftware(resultSet.getString("softwarename"));
				c.add(a);				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return c;
	}
	
	public ArrayList findwhitebyAttAndSoftware(String sname, String sdigest,int userid,String description,String time,String type){
		ArrayList c=new ArrayList();
		try {
			Statement statement=connection.createStatement();
			String strSql;
			/*
			if(databasebean.ifmysql)
			{
				strSql="select * from whitelist a,class_des b,admins d";					
				strSql+=" where a.classid=b.id and a.createID=d.operatorID ";
				
				//String strSql="select * from whitelist a,class_des b,admins d";					
				//strSql+="  where a.classID=b.id and a.createID=d.operatorID ";
				
				strSql+=" and a.userid = '"+userid+"'";
			}*/
			//else
			{
			 strSql="select * from "+whitelist+" a";					
			//strSql+=" where a.classid=b.id ";
			
			//String strSql="select * from whitelist a,class_des b,admins d";					
			//strSql+="  where a.classID=b.id and a.createID=d.operatorID ";
			
			strSql+=" where a.userid = '"+userid+"' and a.whitelist_vern="+vern;
			}
			//if (trusted!=2)
				//strSql+=" and a.trusted = '"+trusted+"'";
			if(sname!=null)
				strSql+=" and a."+filename+" LIKE '%"+sname+"%' ";
			if(sdigest!=null)
				strSql+=" and a."+digest+"  like '%"+sdigest+"%'";
			/*
			if(databasebean.ifmysql)
			{
			//if(classID!=0)
				//strSql+=" and a.ClassID = '"+classID+"'";
			if(description!=null&&!description.equals(""))
				strSql+=" and a.description like '%"+description+"%' and a.description is not null ";
			if(time!=null)
				strSql+=" and a.issueDate like '%"+time+"%'";
			}*/
			if(type!=null)
				strSql+=" and a."+softwarename+" like '%"+type+"%'";

			strSql+=" order by "+filename+" asc";
			

		      // BasicConfigurator replaced with PropertyConfigurator.

//		          logger.info("Entering application.");
		          log.logger.info(strSql);
//		          logger.info("Exiting application.");
			
			
			
			ResultSet resultSet=statement.executeQuery(strSql);
			while(resultSet.next()){
				FullWhiteBean a = new FullWhiteBean();
				a.setID(Integer.parseInt(resultSet.getString("id")));
				a.setFileName(resultSet.getString(filename));
				a.setDigest(resultSet.getString(digest));
				/*
				if(databasebean.ifmysql)
				{
				a.setEdition(resultSet.getString("edition"));
				a.setClassID(Integer.parseInt(resultSet.getString("classID")));
				a.setClassName(resultSet.getString("classDescription"));
				a.setTrusted(Integer.parseInt(resultSet.getString("trusted")));
				a.setCreateID(Integer.parseInt(resultSet.getString("createID")));
				a.setCreateName(resultSet.getString("name"));
				
				a.setIssueTime(resultSet.getString("issueDate"));
				a.setDescription(resultSet.getString("description"));
				}*/
				a.setSoftwareName(resultSet.getString(softwarename));
				a.setuserid(Integer.parseInt(resultSet.getString("userid")));
				//a.setusername(resultSet.getString("username"));
				c.add(a);				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return c;
	}
	
	
	public ArrayList findbyAttAndSoftware(int trusted,String sname, int classID, String softwareName){
		ArrayList c=new ArrayList();
		try {
			Statement statement=connection.createStatement();
			String strSql="select * from digest_table a,class_des b,admins d";
			strSql+="  where a.classid=b.id and a.createID=d.operatorID ";
			if (trusted!=2)
				strSql+=" and a.trusted = '"+trusted+"'";
			if(sname!=null)
				strSql+=" and a.filename LIKE '%"+sname+"%' ";
			
			if(classID!=0)
				strSql+=" and a.ClassID = '"+classID+"'";
			if(softwareName!=null)
				strSql+=" and a.SoftwareName like '%"+softwareName+"%'";

			strSql+=" order by a.softwarename asc";
			

		      // BasicConfigurator replaced with PropertyConfigurator.

//		          logger.info("Entering application.");
		          log.logger.info(strSql);
//		          logger.info("Exiting application.");
			
			
			
			ResultSet resultSet=statement.executeQuery(strSql);
			while(resultSet.next()){
				FullInfoBean a = new FullInfoBean();
				a.setID(Integer.parseInt(resultSet.getString("id")));
				a.setFileName(resultSet.getString("fileName"));
				a.setDigest(resultSet.getString("digest"));
				a.setEdition(resultSet.getString("edition"));
				a.setClassID(Integer.parseInt(resultSet.getString("classID")));
				a.setClassName(resultSet.getString("classDescription"));
				a.setTrusted(Integer.parseInt(resultSet.getString("trusted")));
				a.setCreateID(Integer.parseInt(resultSet.getString("createID")));
				a.setCreateName(resultSet.getString("name"));
				a.setSoftwareName(resultSet.getString("SoftwareName"));
				c.add(a);				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return c;
	}
	
	public ArrayList findHistory(int trusted,String sname, int classID, String softwareName,int userid,String sdate){
		ArrayList c=new ArrayList();

		try {
			Statement statement=connection.createStatement();
			//String strSql="select * from digest_table a";
			String strSql;
			/*
			if(databasebean.ifmysql)
				strSql="select * from digest_table a,class_des b,admins d";
			*/
			//else
				strSql="select * from digest_table a,class_des b";
			//strSql+="  where a.classid=b.id and a.createID=d.operatorID ";
			strSql+="  where a.classid=b.id";
			strSql+=" and a.userid = '"+userid+"'";
			if (trusted!=2)
				strSql+=" and a.trusted = '"+trusted+"'";
			if(sname!=null)
				strSql+=" and a.filename LIKE '%"+sname+"%' ";
			
			if(classID!=0)
				strSql+=" and a.ClassID = '"+classID+"'";
			if(softwareName!=null)
				strSql+=" and a.SoftwareName like '%"+softwareName+"%'";
			if(sdate!=null)
				strSql+="and a.issueDate LIKE '%"+sdate+"%' ";
				//strSql+=" and a.issueDate = '"+sdate+"'";

			strSql+=" order by a.issueDate desc";
			
//System.out.println("====  "+strSql +"  ====");
		      // BasicConfigurator replaced with PropertyConfigurator.

//		          logger.info("Entering application.");
		          log.logger.info(strSql);
//		          logger.info("Exiting application.");
			
			
			
			ResultSet resultSet=statement.executeQuery(strSql);
			String date=new String();
			while(resultSet.next()){
				//ArrayList c=new ArrayList();
				FullWhiteBean a = new FullWhiteBean();
				a.setID(Integer.parseInt(resultSet.getString("id")));
				a.setFileName(resultSet.getString("fileName"));
				a.setDigest(resultSet.getString("digest"));
				a.setEdition(resultSet.getString("edition"));
				a.setClassID(Integer.parseInt(resultSet.getString("classID")));
				a.setClassName(resultSet.getString("classDescription"));
				a.setTrusted(Integer.parseInt(resultSet.getString("trusted")));
				a.setCreateID(Integer.parseInt(resultSet.getString("createID")));
				//if(databasebean.ifmysql)
					//a.setCreateName(resultSet.getString("name"));
				a.setSoftwareName(resultSet.getString("SoftwareName"));
				a.setuserid(Integer.parseInt(resultSet.getString("userid")));
				a.setIssueTime(resultSet.getString("issueDate"));
				
				/*
				if(date!=a.getIssueTime()){
					
					h.put(date,c);
					date=a.getIssueTime();
					ArrayList c=new ArrayList();
					c.add(a);
					//h.put(date,new ArrayList());
					//h.get(date).add(a);
				}
				else{
					c.add(a);
				}*/
				
				c.add(a);				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return c;
	}
	
	public HashMap findDate(int trusted,String sname, int classID, String softwareName,int userid){
		HashMap c=new HashMap();

		try {
			Statement statement=connection.createStatement();
			String strSql="select issueDate,count(issueDate) as count from digest_table a,class_des b,admins d";
			strSql+="  where a.classID=b.id and a.createID=d.operatorID ";
			strSql+=" and a.userid = '"+userid+"'";
			if (trusted!=2)
				strSql+=" and a.trusted = '"+trusted+"'";
			if(sname!=null)
				strSql+=" and a.filename LIKE '%"+sname+"%' ";
			
			if(classID!=0)
				strSql+=" and a.ClassID = '"+classID+"'";
			if(softwareName!=null)
				strSql+=" and a.SoftwareName like '%"+softwareName+"%'";

			strSql+="group by issueDate order by issueDate asc";
			

		      // BasicConfigurator replaced with PropertyConfigurator.

//		          logger.info("Entering application.");
		          log.logger.info(strSql);
//		          logger.info("Exiting application.");
			
			
			
			ResultSet resultSet=statement.executeQuery(strSql);
			String date=new String();
			int count=0;
			while(resultSet.next()){
				
				count=Integer.parseInt(resultSet.getString("count"));
				date=resultSet.getString("issueDate");
				c.put(date,count);
				
							
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return c;
	}
	
	public ArrayList findbyAtt(int aid,String avalue){
		ArrayList c=new ArrayList();
		try {
			Statement statement=connection.createStatement();
			String strSql="select * from mappings";
			
			if (avalue!=null){
				strSql+=" where ";
				strSql+=" AttributeID = "+aid;
				strSql+=" and AttributeValue = '"+avalue+"'";
			}else{
				strSql+=" where ";
				strSql+=" AttributeID = "+aid;
			}
			
			ResultSet resultSet=statement.executeQuery(strSql);
			while(resultSet.next()){
				MappingBean a=new MappingBean();
				
				Attribute att=new Attribute();
				att.setAttributeID(resultSet.getInt("AttributeID"));
				att.setAttributeValue(resultSet.getString("AttributeValue"));
				a.setAtt(att);
				
				MeasurementValues mvs=new MeasurementValues();
				mvs.setHashValues(resultSet.getString("HashValue"));
				mvs.setCopyRight(resultSet.getString("CopyRight"));
				mvs.setDescription(resultSet.getString("Description"));
				mvs.setEdition(resultSet.getString("Edition"));
				mvs.setIssueTime(resultSet.getString("IssueTime"));
				mvs.setManufacturer(resultSet.getString("Manufacturer"));
				mvs.setSoftwareName(resultSet.getString("SoftwareName"));
				mvs.setSoftwareClass(resultSet.getString("Class"));
				a.setMvs(mvs);
				
				Operation creation=new Operation();
				creation.setOperatorID(resultSet.getInt("CreateOid"));
				creation.setOperationTime(resultSet.getTimestamp("CreateTime"));
				a.setCreation(creation);
				
				Operation modification=new Operation();
				modification.setOperatorID(resultSet.getInt("ModifyOid"));
				modification.setOperationTime(resultSet.getTimestamp("ModifyTime"));
				a.setModification(modification);
				
				c.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return c;
	}
	
	//end
	
	
	/*
	public boolean deletebyAttribute(Attribute att){
		
		try {
			Statement statement=connection.createStatement();
			String strSql="delete from mappings where AttributeID='"+att.getAttributeID()+"' and AttributeValue='"+att.getAttributeValue()+"'";
			statement.executeUpdate(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	*/
public boolean deletebyID(int aid)
{
	try {
		Statement statement=connection.createStatement();
		String strSql="delete from digest_table where id="+aid;
		log.logger.info(strSql);
		statement.executeUpdate(strSql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	
	return true;
}

public boolean ClearHistory(HistorySearchBean hsearch,int userid)
{
	try {
		Statement statement=connection.createStatement();
		String strSql="delete from digest_table";
		//strSql+="  where a.classid=b.id and a.createID=d.operatorID ";
		strSql+=" where userid = '"+userid+"'";
		if (hsearch.getavalue()!=2)
			strSql+=" and trusted = '"+hsearch.getavalue()+"'";
		if(hsearch.getsname()!=null)
			strSql+=" and filename LIKE '%"+hsearch.getsname()+"%' ";
		
		if(hsearch.getsclass()!=0)
			strSql+=" and ClassID = '"+hsearch.getsclass()+"'";
		if(hsearch.getsoftwareName()!=null)
			strSql+=" and SoftwareName like '%"+hsearch.getsoftwareName()+"%'";
		if(hsearch.getsdate()!=null)
			strSql+="and issueDate LIKE '%"+hsearch.getsdate()+"%' ";

	    log.logger.info(strSql);

		
		
		
		statement.executeUpdate(strSql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	return true;
}
public ArrayList ExportWhitelist(WhitelistSearchBean wsearch,int userid)
{
	ArrayList c=new ArrayList();
	try {
		Statement statement=connection.createStatement();
		String strSql="select * from "+whitelist;
		//strSql+="  where a.classid=b.id and a.createID=d.operatorID ";
		/*
		if(databasebean.ifmysql)
			strSql+=" where userid = '"+userid+"'";
			*/
		//else
			strSql+=" where userid = '"+userid+"' and whitelist_vern="+vern;

		if(wsearch.getsname()!=null)
			strSql+=" and "+filename+" LIKE '%"+wsearch.getsname()+"%' ";
		
		if(wsearch.getsdigest()!=null)
			strSql+=" and "+digest+" = '"+wsearch.getsdigest()+"'";


	    log.logger.info(strSql);
		ResultSet resultSet=statement.executeQuery(strSql);
		while(resultSet.next()){
			FullWhiteBean a = new FullWhiteBean();
			//a.setID(Integer.parseInt(resultSet.getString("id")));
			a.setFileName(resultSet.getString(filename));
			a.setDigest(resultSet.getString(digest));
			/*
			if(databasebean.ifmysql)
			{
			a.setEdition(resultSet.getString("edition"));
			a.setClassID(Integer.parseInt(resultSet.getString("classID")));
			a.setClassName(resultSet.getString("classDescription"));
			a.setTrusted(Integer.parseInt(resultSet.getString("trusted")));
			a.setCreateID(Integer.parseInt(resultSet.getString("createID")));
			a.setCreateName(resultSet.getString("name"));
			
			a.setIssueTime(resultSet.getString("issueDate"));
			a.setDescription(resultSet.getString("description"));
			}*/
			//a.setSoftwareName(resultSet.getString(softwarename));
			//a.setuserid(Integer.parseInt(resultSet.getString("userid")));
			//a.setusername(resultSet.getString("username"));
			c.add(a);				
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


	return c;
		
		

}

public boolean ClearWhitelist(WhitelistSearchBean wsearch,int userid)
{
	try {
		Statement statement=connection.createStatement();
		String strSql="delete from "+whitelist;
		//strSql+="  where a.classid=b.id and a.createID=d.operatorID ";
		/*
		if(databasebean.ifmysql)
			strSql+=" where userid = '"+userid+"'";
			*/
		//else
			strSql+=" where userid = '"+userid+"' and whitelist_vern="+vern;

		if(wsearch.getsname()!=null)
			strSql+=" and "+filename+" LIKE '%"+wsearch.getsname()+"%' ";
		
		if(wsearch.getsdigest()!=null)
			strSql+=" and "+digest+" = '"+wsearch.getsdigest()+"'";


	    log.logger.info(strSql);

		
		
		
		statement.executeUpdate(strSql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	return true;
}


public boolean deleteUserHistorylist(int userid)
{
	try {
		Statement statement=connection.createStatement();
		String strSql="delete from digest_table where userid = "+userid;
		log.logger.info(strSql);
		statement.executeUpdate(strSql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	
	return true;
}

public boolean deleteWhitebyID(int aid)
{
	try {
		Statement statement=connection.createStatement();
		String strSql="delete from "+whitelist+" where id = "+aid;
		log.logger.info(strSql);
		statement.executeUpdate(strSql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	
	return true;
}

public boolean deleteVerifyInfobyID(int aid)
{
	try {
		Statement statement=connection.createStatement();
		String strSql="delete from verifyfail_table where id="+aid;
		statement.executeUpdate(strSql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	
	return true;
}

public boolean deletebyAttributeAndHash(Attribute att,String HashValue){
		
		try {
			Statement statement=connection.createStatement();
			String strSql="delete from mappings where AttributeID='"+att.getAttributeID()+"' and AttributeValue='"+att.getAttributeValue()+"' and HashValue='"+HashValue+"' ";
			statement.executeUpdate(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean add(MappingBean a){
		
		Statement statement;
		try {
			statement = connection.createStatement();
			String strSql="insert into mappings (HashValue,SoftwareName,Edition,Manufacturer,IssueTime,Description,CopyRight,Class,AttributeID, AttributeValue,  CreateTime, CreateOid, ModifyTime, ModifyOid ) " +
					"value('"+a.getMvs().getHashValues()+"','"+a.getMvs().getSoftwareName()+"','"+a.getMvs().getEdition()+
					"','"+a.getMvs().getManufacturer()+"','"+a.getMvs().getIssueTime()+"','"+a.getMvs().getDescription()+
					"','"+a.getMvs().getCopyRight()+"','"+a.getMvs().getSoftwareClass()+"', "+a.getAtt().getAttributeID()+",'"+a.getAtt().getAttributeValue()+
					"','"+a.getCreation().getOperationTime().toString()+"',"+a.getCreation().getOperatorID()+",'"+a.getModification().getOperationTime().toString()+"',"+a.getModification().getOperatorID()+")";//�������
			statement.executeUpdate(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean addWhitelistbyfilenameandhash(String filename,String digest,int userid){
		Statement statement;
		try {
			statement = connection.createStatement();
			String strSql="";
			//statement.executeUpdate(strSql);
			
			int whiteid=findWhiteByHashAndUserid(digest,userid);
			if(whiteid>0){
				strSql="update "+whitelist+" set "+this.digest+"= '"+digest
						+"' , "+this.filename+"='"+filename+"'  where ID="+whiteid;
						log.logger.info(strSql);
						//statement.executeUpdate(strSql);
			}
			else			
					strSql="insert into "+whitelist+" ("+this.filename+","+this.digest+",userid ) " +
					"value('"+filename+"','"+digest+"','"+userid+"')";//�������
			statement.executeUpdate(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
FullWhiteBean findHistoryByID(int id){
	Statement statement;
	FullWhiteBean wh=new FullWhiteBean();
	try {
		statement = connection.createStatement();
		String strSql="select * from digest_table a,class_des b,admins d";
		strSql+="  where a.classid=b.id and a.createID=d.operatorID ";
		strSql+=" and a.ID ='"+id+"'";//�������
		ResultSet resultSet=statement.executeQuery(strSql);
		if(resultSet.next()){
			wh.setID(id);
			wh.setFileName(resultSet.getString("FileName"));
			wh.setDigest(resultSet.getString("digest"));
			wh.setEdition(resultSet.getString("edition"));
			wh.setClassID(Integer.parseInt(resultSet.getString("classID")));
			wh.setClassName(resultSet.getString("classDescription"));
			wh.setDescription(resultSet.getString("description"));
			wh.setTrusted(Integer.parseInt(resultSet.getString("trusted")));
			wh.setCreateID(Integer.parseInt(resultSet.getString("operatorID")));
			wh.setCreateName(resultSet.getString("name"));
			wh.setIssueTime(resultSet.getString("issueDate"));
			wh.setSoftwareName(resultSet.getString("SoftwareName"));
			wh.setuserid(Integer.parseInt(resultSet.getString("userid")));
		}
		else
			return null;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	
	return wh;
}

public ArrayList getUsbInfo(UsbBean bean){
	
	ArrayList c=new ArrayList();	
	try {
		Statement statement=connection.createStatement();
		
		String manufacture = bean.getManufacture();
		String sn = bean.getSn();
		String date = bean.getDate();
		String name = bean.getTerminal();
		
		String strSql;
		strSql="select a.*,b.name from usbmeasure a left join users b on a.terminalid=b.id where a.id>0 ";
		if(manufacture!=null && !manufacture.equals(""))
			strSql += " and a.manufacture LIKE '%"+ manufacture +"%' ";
		if(sn!=null && !sn.equals(""))
			strSql += " and a.sn LIKE '%"+ sn +"%' ";
		if(date!=null && !date.equals(""))
			strSql += " and a.date LIKE '%"+ date +"%' ";
		if(name!=null && !name.equals(""))
			strSql += " and b.name LIKE '%"+ name +"%' ";		
		strSql+=" order by a.date desc";
		
	    log.logger.info(strSql);
		
		ResultSet resultSet=statement.executeQuery(strSql);
		while(resultSet.next()){
			//ArrayList c=new ArrayList();
			UsbBean a = new UsbBean();
			a.setId(Integer.parseInt(resultSet.getString("id")));
			a.setTerminalId(Integer.parseInt(resultSet.getString("terminalid")));
			a.setManufacture(resultSet.getString("manufacture"));
			a.setSn(resultSet.getString("sn"));
			a.setVersion(resultSet.getString("version"));
			a.setProducer(resultSet.getString("producer"));
			a.setTerminal(resultSet.getString("name"));
			a.setDate(resultSet.getString("date"));		
					
			c.add(a);				
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	return c;
}


public ArrayList getUserList(){
	
	ArrayList c=new ArrayList();	
	try {
		Statement statement=connection.createStatement();	

		String strSql;
		strSql="select a.id,a.name,b.name as system,c.name as technology,d.name as factory from users a "
				+ " left join l3 b on a.fid = b.id left join l2 c on b.fid = c.id left join l1 d on c.fid =d.id ";
		strSql+=" order by d.name,c.name,b.name,a.name";
		
	    log.logger.info(strSql);		
		
		ResultSet resultSet=statement.executeQuery(strSql);
		while(resultSet.next()){
			//ArrayList c=new ArrayList();
			UserBean a = new UserBean();
			a.setID(resultSet.getString("id"));
			a.setName(resultSet.getString("name"));
			a.setFactory(resultSet.getString("factory"));
			a.setTechnology(resultSet.getString("technology"));
			a.setSystem(resultSet.getString("system"));
			
			c.add(a);				
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	return c;
}




public int getcurrentwhitevern(int userid){
	int vern=0;
	Statement statement;
	String strSql;
	try {
		statement = connection.createStatement();
	//int whiteid=findWhiteByHashAndUserid(fb.getDigest(),userid);
	strSql="select max(whitelist_vern) as vern from "+whitelist+" where userid = '"+userid+"'";
	ResultSet resultSet=statement.executeQuery(strSql);
	if(resultSet.next()){
		vern=resultSet.getInt(1)+1;
	}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return -1;
	}
	return vern;
}

public boolean createwhitevern(int userid,int vern){
	Statement statement;
	String strSql;
	try {
		statement = connection.createStatement();
		
		AdminsDBBean dbBean=new AdminsDBBean();
		UserBean user=dbBean.findUserbyID(userid);
		String pk=user.getpubkey();
		//strSql="insert into whitelist_ver(tcm_pk,whitelist_vern,admin_revise,client_use,userid) values('"+pk+"',"+vern+",'1','1',"+userid+")";
		strSql="insert into whitelist_ver(tcm_pk,whitelist_vern,admin_revise,client_use,userid) values('"+pk+"','"+vern+"','1','1',"+userid+")";
		statement.executeUpdate(strSql);

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	
	return true;
}

public boolean addknowledge(int id,String kname)
{
	Statement statement;
	//Statement statement1;
	//String strSql;
	try {
		statement = connection.createStatement();
		//statement1 = connection.createStatement();
		FullWhiteBean fb=findHistoryByID(id);
		if(fb!=null){			
			String name=fb.getFileName();
			int pos = name.lastIndexOf("/");
			name = name.substring(pos + 1);
			String hash=fb.getDigest();
			hash=hash.toLowerCase();
			String strSql="insert into knowledge_recommended(ics_name,hash_value) values('"+kname+"','"+hash+"')";
			statement.executeUpdate(strSql);
			
			
			strSql="select * from knowledge_basic where hash_value = '"+hash+"'";
			ResultSet resultSet=statement.executeQuery(strSql);
			if(resultSet.next())
				return true;
			strSql="select * from knowledge_extension where process_name = '"+name+"'";
			resultSet=statement.executeQuery(strSql);
			if(resultSet.next())
			{
				
				String ver=resultSet.getString("software_ver");
				strSql="insert into knowledge_basic(hash_value,process_name,software_ver,os_ver) values('"+hash+"','"+name+"','"+ver+"','XP系统')";
				statement.executeUpdate(strSql);
			}
			else
			{
				//statement.close();
				strSql="insert into knowledge_basic(hash_value,process_name,software_ver,os_ver) values('"+hash+"','"+name+"','1.0','XP系统')";
				statement.executeUpdate(strSql);
				strSql="insert into knowledge_extension(process_name,software_ver) values('"+name+"','1.0')";
				statement.executeUpdate(strSql);
			}
			resultSet.close();
			statement.close();
			/*
			strSql="insert into knowledge_basic(hash_value,process_name) values('"+hash+"','"+name+"')";
			statement.executeUpdate(strSql);
			strSql="select process_name from knowledge_extension where process_name='"+name+"'";
			resultSet=statement.executeQuery(strSql);
			if(resultSet.next())
				return true;
			strSql="insert into knowledge_extension(process_name,software_ver) values('"+name+"','1.0')";
			statement.executeUpdate(strSql);
			resultSet.close();
			statement.close();
			}
			else
			{
				statement.close();
				return false;
			}*/
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
		return false;
	}
	
	return true;
}

/*
public boolean addknowledge(int id,String kname)
{
	Statement statement;
	//String strSql;
	try {
		statement = connection.createStatement();
		FullWhiteBean fb=findHistoryByID(id);
		if(fb!=null){			
			String name=fb.getFileName();
			int pos = name.lastIndexOf("/");
			name = name.substring(pos + 1);
			String hash=fb.getDigest();
			hash=hash.toLowerCase();
			String strSql="insert into knowledge_recommended(ics_name,hash_value) values('"+kname+"','"+hash+"')";
			statement.executeUpdate(strSql);
			strSql="insert into knowledge_basic(hash_value,process_name) values('"+hash+"','"+name+"')";
			statement.executeUpdate(strSql);
			strSql="select process_name from knowledge_extension where process_name='"+name+"'";
			ResultSet resultSet=statement.executeQuery(strSql);
			if(resultSet.next())
				return true;
			strSql="insert into knowledge_extension(process_name,software_ver) values('"+name+"','1.0')";
			statement.executeUpdate(strSql);
			resultSet.close();
			statement.close();
			}
			else
			{
				statement.close();
				return false;
			}
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
		return false;
	}
	
	return true;
}
*/
	
public boolean addWhiteByHistory(int id,int userid){
		
		Statement statement;
		String strSql;
		try {
			statement = connection.createStatement();
			FullWhiteBean fb=findHistoryByID(id);
			AdminsDBBean dbBean=new AdminsDBBean();
			UserBean user=dbBean.findUserbyID(userid);
			String pk=user.getpubkey();
			/*
			if(databasebean.ifmysql)
			{
				if(fb!=null){
					int whiteid=findWhiteByHashAndUserid(fb.getDigest(),userid);
					if(whiteid>0){
						strSql="update whitelist set digest= '"+fb.getDigest()
								+"' , fileName='"+fb.getFileName()+"', edition='"+fb.getEdition()+"' , classID= "+fb.getClassID()+", trusted= "
								+fb.getTrusted()+",SoftwareName='"+fb.getSoftwareName()+"',issueDate='"+fb.getIssueTime()+"',description='"+fb.getDescription()+"'  where ID="+whiteid;
								log.logger.info(strSql);
								//statement.executeUpdate(strSql);
					}
					else					
					     strSql="insert into whitelist (fileName,digest,userid,classID,SoftwareName,issueDate,description) " +
						"value('"+fb.getFileName()+"','"+fb.getDigest()+"','"+userid+"','"+fb.getClassID()+"','"+fb.getSoftwareName()+"','"+fb.getIssueTime()+"','"+fb.getDescription()+"')";
					statement.executeUpdate(strSql);
				}
				else
					return false;
			}*/
				//else
				//{
			if(fb!=null){
				int whiteid=findWhiteByHashAndUserid(fb.getDigest(),userid);			
				if(whiteid>0){
					strSql="update "+whitelist+" set "+digest+"= '"+fb.getDigest()
							+"' , "+filename+"='"+fb.getFileName()+","+softwarename+"='"+fb.getSoftwareName()+"'  where ID="+whiteid;
							log.logger.info(strSql);
							//statement.executeUpdate(strSql);
				}
				else				
				     strSql="insert into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") " +
					"values('"+pk+"','"+vern+"','"+fb.getFileName()+"','"+fb.getDigest()+"','"+userid+"','"+fb.getSoftwareName()+"')";//�������
			
				statement.executeUpdate(strSql);
				}
				else
					return false;
				//}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}


/*
public boolean DumpHistoryToKnowledge(HistorySearchBean hsearch,int userid,String kname)
{
	Statement statement;
	String strSql;
	try {
		statement = connection.createStatement();
		
	
		
		
		AdminsDBBean dbBean=new AdminsDBBean();
		UserBean user=dbBean.findUserbyID(userid);
		String pk=user.getpubkey();
		//strSql="replace into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") select '"+pk+"',"+vern+",fileName,digest,userid,SoftwareName from digest_table where userid='"+userid+"' and whitelist_vern="+vern;
		//strSql="insert into "+whitelist+" ("+filename+","+digest+",userid,"+softwarename+",tcm_pk,whitelist_vern) select fileName,digest,userid,SoftwareName,'"+pk+"',"+vern+" from digest_table where userid='"+userid+"'";

		
		
		
		//knowledge_recommended
		strSql="insert into knowledge_recommended (ics_name,hash_value) select '"+kname+"',digest from digest_table where userid='"+userid+"'";
		//strSql="insert into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") select '"+pk+"',"+vern+",fileName,digest,userid,SoftwareName from digest_table where userid='"+userid+"'";
		if (hsearch.getavalue()!=2)
			strSql+=" and trusted = '"+hsearch.getavalue()+"'";
		if(hsearch.getsname()!=null)
			strSql+=" and filename LIKE '%"+hsearch.getsname()+"%' ";
		if(hsearch.getsclass()!=0)
			strSql+=" and ClassID = '"+hsearch.getsclass()+"'";
		if(hsearch.getsoftwareName()!=null)
			
			strSql+=" and SoftwareName like '%"+hsearch.getsoftwareName()+"%'";
		if(hsearch.getsdate()!=null)
			strSql+="and issueDate LIKE '%"+hsearch.getsdate()+"%' ";
		strSql+="and not exists (select 1 from knowledge_recommended where hash_value=digest_table.digest and ics_name='"+kname+"')";
		//if(!databasebean.ifmysql)
			//strSql+="and not exists (select 1 from "+whitelist+" where "+digest+"=digest_table.digest and "+filename+"=digest_table.filename and "+softwarename+"=digest_table.softwarename)";
		statement.executeUpdate(strSql);
		
		//knowledge_basic
		strSql="insert into knowledge_basic (process_name,hash_value) select SoftwareName,digest from digest_table where userid='"+userid+"'";
		//strSql="insert into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") select '"+pk+"',"+vern+",fileName,digest,userid,SoftwareName from digest_table where userid='"+userid+"'";
		if (hsearch.getavalue()!=2)
			strSql+=" and trusted = '"+hsearch.getavalue()+"'";
		if(hsearch.getsname()!=null)
			strSql+=" and filename LIKE '%"+hsearch.getsname()+"%' ";
		if(hsearch.getsclass()!=0)
			strSql+=" and ClassID = '"+hsearch.getsclass()+"'";
		if(hsearch.getsoftwareName()!=null)
			
			strSql+=" and SoftwareName like '%"+hsearch.getsoftwareName()+"%'";
		if(hsearch.getsdate()!=null)
			strSql+="and issueDate LIKE '%"+hsearch.getsdate()+"%' ";
		strSql+="and not exists (select 1 from knowledge_basic where hash_value=digest_table.digest)";
		//if(!databasebean.ifmysql)
			//strSql+="and not exists (select 1 from "+whitelist+" where "+digest+"=digest_table.digest and "+filename+"=digest_table.filename and "+softwarename+"=digest_table.softwarename)";
		statement.executeUpdate(strSql);
		
		
		strSql="insert into knowledge_extension (process_name,software_ver) select DISTINCT SoftwareName,'1.0' from digest_table where userid='"+userid+"'";
		//strSql="insert into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") select '"+pk+"',"+vern+",fileName,digest,userid,SoftwareName from digest_table where userid='"+userid+"'";
		if (hsearch.getavalue()!=2)
			strSql+=" and trusted = '"+hsearch.getavalue()+"'";
		if(hsearch.getsname()!=null)
			strSql+=" and filename LIKE '%"+hsearch.getsname()+"%' ";
		if(hsearch.getsclass()!=0)
			strSql+=" and ClassID = '"+hsearch.getsclass()+"'";
		if(hsearch.getsoftwareName()!=null)
			
			strSql+=" and SoftwareName like '%"+hsearch.getsoftwareName()+"%'";
		if(hsearch.getsdate()!=null)
			strSql+="and issueDate LIKE '%"+hsearch.getsdate()+"%' ";
		//if(!databasebean.ifmysql)
		strSql+="and not exists (select 1 from knowledge_extension where process_name=digest_table.SoftwareName)";
		statement.executeUpdate(strSql);
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	
	return true;
}
*/


public boolean DumpHistoryToKnowledge(HistorySearchBean hsearch,int userid,String kname)
{
	Statement statement;
	String strSql;
	try {
		statement = connection.createStatement();
		
		/*
		FullWhiteBean fb=findHistoryByID(id);
		if(fb!=null){
			int whiteid=findWhiteByHashAndUserid(fb.getDigest(),userid);
			if(whiteid>0){
				strSql="update whitelist set digest= '"+fb.getDigest()
						+"' , fileName='"+fb.getFileName()+"', edition='"+fb.getEdition()+"' , classID= "+fb.getClassID()+", trusted= "
						+fb.getTrusted()+",SoftwareName='"+fb.getSoftwareName()+"',issueDate='"+fb.getIssueTime()+"',description='"+fb.getDescription()+"'  where ID="+whiteid;
						log.logger.info(strSql);
						//statement.executeUpdate(strSql);
			}
			else					
			     strSql="insert into whitelist (fileName,digest,userid,classID,SoftwareName,issueDate,description) " +
				"value('"+fb.getFileName()+"','"+fb.getDigest()+"','"+userid+"','"+fb.getClassID()+"','"+fb.getSoftwareName()+"','"+fb.getIssueTime()+"','"+fb.getDescription()+"')";//�������
		statement.executeUpdate(strSql);
		}
		else
			return false;
			*/
		
		/*
		strSql="create view tmp(fileName,digest,userid,classID,SoftwareName,issueDate,description) as select  fileName,digest,userid,classID,SoftwareName,issueDate,description from digest_table where userid='"+userid+"'";
		
		if (hsearch.getavalue()!=2)
			strSql+=" and trusted = '"+hsearch.getavalue()+"'";
		if(hsearch.getsname()!=null)
			strSql+=" and filename LIKE '%"+hsearch.getsname()+"%' ";
		if(hsearch.getsclass()!=0)
			strSql+=" and ClassID = '"+hsearch.getsclass()+"'";
		if(hsearch.getsoftwareName()!=null)
			strSql+=" and SoftwareName like '%"+hsearch.getsoftwareName()+"%'";
		if(hsearch.getsdate()!=null)
			strSql+="and issueDate LIKE '%"+hsearch.getsdate()+"%' ";
		
		statement.executeUpdate(strSql);
		//strSql="insert into whitelist (fileName,digest,userid,classID,SoftwareName,issueDate,description) select * from tmp where tmp.digest != whitelist.digest";
		strSql="insert into whitelist (fileName,digest,userid,classID,SoftwareName,issueDate,description) select * from tmp where not exists (select fileName,digest,userid,classID,SoftwareName,issueDate,description from whitelist where whitelist.digest = tmp.digest)" ;
		statement.executeUpdate(strSql);
		
		
		strSql="update whitelist inner join tmp on whitelist.digest=tmp.digest set whitelist.fileName=tmp.fileName,whitelist.digest=tmp.digest,whitelist.userid=tmp.userid,whitelist.classID=tmp.classID,whitelist.SoftwareName=tmp.SoftwareName,whitelist.issueDate=tmp.issueDate,whitelist.description=tmp.description";			
		statement.executeUpdate(strSql);
		//strSql+="and not exists (select * from  "
		strSql="drop view tmp";
		statement.executeUpdate(strSql);*/
		
		AdminsDBBean dbBean=new AdminsDBBean();
		UserBean user=dbBean.findUserbyID(userid);
		String pk=user.getpubkey();
		//strSql="replace into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") select '"+pk+"',"+vern+",fileName,digest,userid,SoftwareName from digest_table where userid='"+userid+"' and whitelist_vern="+vern;
		//strSql="insert into "+whitelist+" ("+filename+","+digest+",userid,"+softwarename+",tcm_pk,whitelist_vern) select fileName,digest,userid,SoftwareName,'"+pk+"',"+vern+" from digest_table where userid='"+userid+"'";
		/*
		if(databasebean.ifmysql)
			strSql="replace into whitelist (fileName,digest,userid,classID,SoftwareName,issueDate,description) select fileName,digest,userid,classID,SoftwareName,issueDate,description from digest_table where userid='"+userid+"'";
		else strSql="insert into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") select '"+pk+"',"+vern+",fileName,digest,userid,SoftwareName from digest_table where userid='"+userid+"'";
		*/
		
		
		
		//knowledge_recommended
		strSql="insert into knowledge_recommended (ics_name,hash_value) select DISTINCT '"+kname+"',lower(digest) from digest_table where userid='"+userid+"'";
		//strSql="insert into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") select '"+pk+"',"+vern+",fileName,digest,userid,SoftwareName from digest_table where userid='"+userid+"'";
		if (hsearch.getavalue()!=2)
			strSql+=" and trusted = '"+hsearch.getavalue()+"'";
		if(hsearch.getsname()!=null)
			strSql+=" and filename LIKE '%"+hsearch.getsname()+"%' ";
		if(hsearch.getsclass()!=0)
			strSql+=" and ClassID = '"+hsearch.getsclass()+"'";
		if(hsearch.getsoftwareName()!=null)
			
			strSql+=" and SoftwareName like '%"+hsearch.getsoftwareName()+"%'";
		if(hsearch.getsdate()!=null)
			strSql+="and issueDate LIKE '%"+hsearch.getsdate()+"%' ";
		strSql+="and not exists (select 1 from knowledge_recommended where lower(hash_value)=lower(digest_table.digest) and ics_name='"+kname+"')";
		//if(!databasebean.ifmysql)
			//strSql+="and not exists (select 1 from "+whitelist+" where "+digest+"=digest_table.digest and "+filename+"=digest_table.filename and "+softwarename+"=digest_table.softwarename)";
		statement.executeUpdate(strSql);
		
		strSql="insert into knowledge_extension (process_name,software_ver) select DISTINCT SoftwareName,'1.0' from digest_table where userid='"+userid+"'";
		//strSql="insert into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") select '"+pk+"',"+vern+",fileName,digest,userid,SoftwareName from digest_table where userid='"+userid+"'";
		if (hsearch.getavalue()!=2)
			strSql+=" and trusted = '"+hsearch.getavalue()+"'";
		if(hsearch.getsname()!=null)
			strSql+=" and filename LIKE '%"+hsearch.getsname()+"%' ";
		if(hsearch.getsclass()!=0)
			strSql+=" and ClassID = '"+hsearch.getsclass()+"'";
		if(hsearch.getsoftwareName()!=null)
			
			strSql+=" and SoftwareName like '%"+hsearch.getsoftwareName()+"%'";
		if(hsearch.getsdate()!=null)
			strSql+="and issueDate LIKE '%"+hsearch.getsdate()+"%' ";
		//if(!databasebean.ifmysql)
		strSql+="and not exists (select 1 from knowledge_extension where process_name=digest_table.SoftwareName)";
		statement.executeUpdate(strSql);
		
		//knowledge_basic
		
		strSql="insert into knowledge_basic (hash_value,process_name,software_ver,os_ver) select DISTINCT lower(digest),SoftwareName,'1.0','XP系统'  from digest_table,knowledge_extension where userid='"+userid+"'";
		//strSql="insert into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") select '"+pk+"',"+vern+",fileName,digest,userid,SoftwareName from digest_table where userid='"+userid+"'";
		if (hsearch.getavalue()!=2)
			strSql+=" and trusted = '"+hsearch.getavalue()+"'";
		if(hsearch.getsname()!=null)
			strSql+=" and filename LIKE '%"+hsearch.getsname()+"%' ";
		if(hsearch.getsclass()!=0)
			strSql+=" and ClassID = '"+hsearch.getsclass()+"'";
		if(hsearch.getsoftwareName()!=null)
			
			strSql+=" and SoftwareName like '%"+hsearch.getsoftwareName()+"%'";
		if(hsearch.getsdate()!=null)
			strSql+="and issueDate LIKE '%"+hsearch.getsdate()+"%' ";
		//strSql+="and not exists (select knowledge_basic.* from knowledge_basic,knowledge_extension,digest_table where lower(hash_value)=lower(digest_table.digest) and knowledge_basic.process_name=knowledge_extension.process_name)";
		strSql+=" and not exists (select knowledge_basic.* from knowledge_basic,digest_table where lower(hash_value)=lower(digest_table.digest))";
		strSql+=" and digest_table.softwareName != knowledge_extension.process_name";
		//strSql+=" and not exists (select knowledge_basic.* from knowledge_basic,knowledge_extension where knowledge_basic.process_name=knowledge_extension.process_name)";
		//strSql+="and not exists (select 1 from knowledge_basic where process_name=knowledge_extension.process_name)";
		//strSql+="and not exists (select 1 from knowledge_extension where knowledge_extension.process_name=digest_table.softwareName)";
		//if(!databasebean.ifmysql)
			//strSql+="and not exists (select 1 from "+whitelist+" where "+digest+"=digest_table.digest and "+filename+"=digest_table.filename and "+softwarename+"=digest_table.softwarename)";
		
		
		
		statement.executeUpdate(strSql);
		
		
		strSql="insert into knowledge_basic (hash_value,process_name,software_ver,os_ver) select DISTINCT lower(a.digest),a.SoftwareName,b.software_ver,'XP系统' from digest_table a,knowledge_extension b where userid='"+userid+"' and a.softwareName = b.process_name";
		//strSql="insert into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") select '"+pk+"',"+vern+",fileName,digest,userid,SoftwareName from digest_table where userid='"+userid+"'";
		if (hsearch.getavalue()!=2)
			strSql+=" and trusted = '"+hsearch.getavalue()+"'";
		if(hsearch.getsname()!=null)
			strSql+=" and filename LIKE '%"+hsearch.getsname()+"%' ";
		if(hsearch.getsclass()!=0)
			strSql+=" and ClassID = '"+hsearch.getsclass()+"'";
		if(hsearch.getsoftwareName()!=null)
			
			strSql+=" and SoftwareName like '%"+hsearch.getsoftwareName()+"%'";
		if(hsearch.getsdate()!=null)
			strSql+=" and issueDate LIKE '%"+hsearch.getsdate()+"%' ";
		strSql+="and not exists (select 1 from knowledge_basic where lower(hash_value)=lower(a.digest))";
		//if(!databasebean.ifmysql)
			//strSql+="and not exists (select 1 from "+whitelist+" where "+digest+"=digest_table.digest and "+filename+"=digest_table.filename and "+softwarename+"=digest_table.softwarename)";
		statement.executeUpdate(strSql);
		
		statement.close();
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	
	return true;
}
	


	public boolean DumpHistoryToWhite(HistorySearchBean hsearch,int userid)
	{
		Statement statement;
		String strSql;
		try {
			statement = connection.createStatement();
			
			/*
			FullWhiteBean fb=findHistoryByID(id);
			if(fb!=null){
				int whiteid=findWhiteByHashAndUserid(fb.getDigest(),userid);
				if(whiteid>0){
					strSql="update whitelist set digest= '"+fb.getDigest()
							+"' , fileName='"+fb.getFileName()+"', edition='"+fb.getEdition()+"' , classID= "+fb.getClassID()+", trusted= "
							+fb.getTrusted()+",SoftwareName='"+fb.getSoftwareName()+"',issueDate='"+fb.getIssueTime()+"',description='"+fb.getDescription()+"'  where ID="+whiteid;
							log.logger.info(strSql);
							//statement.executeUpdate(strSql);
				}
				else					
				     strSql="insert into whitelist (fileName,digest,userid,classID,SoftwareName,issueDate,description) " +
					"value('"+fb.getFileName()+"','"+fb.getDigest()+"','"+userid+"','"+fb.getClassID()+"','"+fb.getSoftwareName()+"','"+fb.getIssueTime()+"','"+fb.getDescription()+"')";//�������
			statement.executeUpdate(strSql);
			}
			else
				return false;
				*/
			
			/*
			strSql="create view tmp(fileName,digest,userid,classID,SoftwareName,issueDate,description) as select  fileName,digest,userid,classID,SoftwareName,issueDate,description from digest_table where userid='"+userid+"'";
			
			if (hsearch.getavalue()!=2)
				strSql+=" and trusted = '"+hsearch.getavalue()+"'";
			if(hsearch.getsname()!=null)
				strSql+=" and filename LIKE '%"+hsearch.getsname()+"%' ";
			if(hsearch.getsclass()!=0)
				strSql+=" and ClassID = '"+hsearch.getsclass()+"'";
			if(hsearch.getsoftwareName()!=null)
				strSql+=" and SoftwareName like '%"+hsearch.getsoftwareName()+"%'";
			if(hsearch.getsdate()!=null)
				strSql+="and issueDate LIKE '%"+hsearch.getsdate()+"%' ";
			
			statement.executeUpdate(strSql);
			//strSql="insert into whitelist (fileName,digest,userid,classID,SoftwareName,issueDate,description) select * from tmp where tmp.digest != whitelist.digest";
			strSql="insert into whitelist (fileName,digest,userid,classID,SoftwareName,issueDate,description) select * from tmp where not exists (select fileName,digest,userid,classID,SoftwareName,issueDate,description from whitelist where whitelist.digest = tmp.digest)" ;
			statement.executeUpdate(strSql);
			
			
			strSql="update whitelist inner join tmp on whitelist.digest=tmp.digest set whitelist.fileName=tmp.fileName,whitelist.digest=tmp.digest,whitelist.userid=tmp.userid,whitelist.classID=tmp.classID,whitelist.SoftwareName=tmp.SoftwareName,whitelist.issueDate=tmp.issueDate,whitelist.description=tmp.description";			
			statement.executeUpdate(strSql);
			//strSql+="and not exists (select * from  "
			strSql="drop view tmp";
			statement.executeUpdate(strSql);*/
			
			AdminsDBBean dbBean=new AdminsDBBean();
			UserBean user=dbBean.findUserbyID(userid);
			String pk=user.getpubkey();
			//strSql="replace into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") select '"+pk+"',"+vern+",fileName,digest,userid,SoftwareName from digest_table where userid='"+userid+"' and whitelist_vern="+vern;
			//strSql="insert into "+whitelist+" ("+filename+","+digest+",userid,"+softwarename+",tcm_pk,whitelist_vern) select fileName,digest,userid,SoftwareName,'"+pk+"',"+vern+" from digest_table where userid='"+userid+"'";
			/*
			if(databasebean.ifmysql)
				strSql="replace into whitelist (fileName,digest,userid,classID,SoftwareName,issueDate,description) select fileName,digest,userid,classID,SoftwareName,issueDate,description from digest_table where userid='"+userid+"'";
			else strSql="insert into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") select '"+pk+"',"+vern+",fileName,digest,userid,SoftwareName from digest_table where userid='"+userid+"'";
			*/
			strSql="insert into "+whitelist+" (tcm_pk,whitelist_vern,"+filename+","+digest+",userid,"+softwarename+") select '"+pk+"',"+vern+",fileName,digest,userid,SoftwareName from digest_table where userid='"+userid+"'";
			if (hsearch.getavalue()!=2)
				strSql+=" and trusted = '"+hsearch.getavalue()+"'";
			if(hsearch.getsname()!=null)
				strSql+=" and filename LIKE '%"+hsearch.getsname()+"%' ";
			if(hsearch.getsclass()!=0)
				strSql+=" and ClassID = '"+hsearch.getsclass()+"'";
			if(hsearch.getsoftwareName()!=null)
				
				strSql+=" and SoftwareName like '%"+hsearch.getsoftwareName()+"%'";
			if(hsearch.getsdate()!=null)
				strSql+="and issueDate LIKE '%"+hsearch.getsdate()+"%' ";
			//if(!databasebean.ifmysql)
				//strSql+="and not exists (select 1 from "+whitelist+" where "+digest+"=digest_table.digest and "+filename+"=digest_table.filename and "+softwarename+"=digest_table.softwarename)";
			strSql+="and not exists (select 1 from "+whitelist+" where "+digest+"=digest_table.digest and "+filename+"=digest_table.filename and "+softwarename+"=digest_table.softwarename and TCM_PK='"+pk+"')";
			statement.executeUpdate(strSql);
			//strSql="create view tmp(id) as select max(id) from "+whitelist+" group by "+digest;
			//statement.executeUpdate(strSql);
			//strSql="delete from "+whitelist+" where id not in (select id from tmp)";
			//statement.executeUpdate(strSql);
			//strSql="drop view tmp";
			//statement.executeUpdate(strSql);
			/*
			AdminsDBBean dbBean=new AdminsDBBean();
			UserBean user=dbBean.findUserbyID(userid);
			String pk=user.getpubkey();
			//int vern=0;
			//int whiteid=findWhiteByHashAndUserid(fb.getDigest(),userid);
			
			strSql="select max(whitelist_vern) as vern from "+whitelist+" where userid = '"+userid+"'";
			ResultSet resultSet=statement.executeQuery(strSql);
			if(resultSet.next()){
				vern=resultSet.getInt(1)+1;
			}
			strSql="insert into whitelist_ver(tcm_pk,whitelist_vern,admin_revise,client_use,userid) values('"+pk+"','"+vern+"','1','1',"+userid+")";
			statement.executeUpdate(strSql);
			strSql="insert into "+whitelist+" ("+filename+","+digest+",userid,"+softwarename+",tcm_pk,whitelist_vern) select fileName,digest,userid,SoftwareName,'"+pk+"',"+vern+" from digest_table where userid='"+userid+"'";
			if (hsearch.getavalue()!=2)
				strSql+=" and trusted = '"+hsearch.getavalue()+"'";
			if(hsearch.getsname()!=null)
				strSql+=" and filename LIKE '%"+hsearch.getsname()+"%' ";
			if(hsearch.getsclass()!=0)
				strSql+=" and ClassID = '"+hsearch.getsclass()+"'";
			if(hsearch.getsoftwareName()!=null)
				
				strSql+=" and SoftwareName like '%"+hsearch.getsoftwareName()+"%'";
			if(hsearch.getsdate()!=null)
				strSql+="and issueDate LIKE '%"+hsearch.getsdate()+"%' ";
			statement.executeUpdate(strSql);*/
			/*
			if(databasebean.ifmysql)
			{
				strSql="create view tmp(id) as select max(id) from whitelist group by digest";
				statement.executeUpdate(strSql);
				strSql="delete from whitelist where id not in (select id from tmp)";
				statement.executeUpdate(strSql);
				strSql="drop view tmp";
				statement.executeUpdate(strSql);
			}*/
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public int findByHashandName(String name,String hash)
	{
		try {
			Statement statement = connection.createStatement();
			String strSql="select * from digest_table where fileName = '" +name
					+"' and digest = '"+hash+"'";
			ResultSet resultSet=statement.executeQuery(strSql);
			resultSet.last(); //�Ƶ����һ��   
			if(resultSet.getRow()>1)
				return -2;
			else
				{
				resultSet.beforeFirst();
				if(resultSet.next())
					return Integer.parseInt(resultSet.getString("id"));
				else
					return 0;
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}
	
	public int findWhiteByNameAndUserid(String name,int userid)
	{
		try {
			Statement statement = connection.createStatement();
			String strSql;
			/*
			if(databasebean.ifmysql)
				strSql="select * from whitelist where fileName = '" +name+"' and userid = '"+userid+"'";
			*/
			//else
				strSql="select * from "+whitelist+" where "+filename+" = '" +name+"' and userid = '"+userid+"' and whitelist_vern="+vern;
			ResultSet resultSet=statement.executeQuery(strSql);
			resultSet.last(); //�Ƶ����һ��   
			if(resultSet.getRow()>1)
				return -2;
			else
				{
				resultSet.beforeFirst();
				if(resultSet.next())
					return Integer.parseInt(resultSet.getString("id"));
				else
					return 0;
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}
	
	
	
	public int findWhiteByHashAndUserid(String hash,int userid)
	{
		try {
			Statement statement = connection.createStatement();
			String strSql;
			/*
			if(databasebean.ifmysql)
				strSql="select * from whitelist where digest = '" +hash+"' and userid = '"+userid+"'";
			*/
			//else
				strSql="select * from "+whitelist+" where "+digest+" = '" +hash+"' and userid = '"+userid+"' and whitelist_vern="+vern;
			ResultSet resultSet=statement.executeQuery(strSql);
			resultSet.last(); //�Ƶ����һ��
			int row=resultSet.getRow();
			if(row>1)
				return -2;
			else
				{
				resultSet.beforeFirst();
				if(resultSet.next())
					return Integer.parseInt(resultSet.getString("id"));
				else
					return 0;
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}
	
	public int findByHashandNameandSN(String name,String hash,String softwarename)
	{
		try {
			Statement statement = connection.createStatement();
			String strSql="select * from digest_table where fileName = '" +name
					+"' and digest = '"+hash+"' and softwarename ='"+softwarename+"'";
			ResultSet resultSet=statement.executeQuery(strSql);
			resultSet.last(); //�Ƶ����һ��   
			if(resultSet.getRow()>1)
				return -2;
			else
				{
				resultSet.beforeFirst();
				if(resultSet.next())
					return Integer.parseInt(resultSet.getString("id"));
				else
					return 0;
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}
	
	public boolean add(FullInfoBean a)
	{
		try {
			Statement statement = connection.createStatement();
			int id = findByHashandNameandSN(a.getFileName(),a.getDigest(),a.getSoftwareName());
			if(id>1||id==-2)
			{
				log.logger.info("�����:"+a.getFileName()+" ������ֵ:"+a.getDigest()+"; ��ݿ����Ѿ����ڴ���ݣ����飡");
//				message.append("�����:"+a.getFileName()+" ������ֵ:"+a.getDigest()+"; ��ݿ����Ѿ����ڴ���ݣ����飡");
				return false;
			}
			else
			{
			String strSql="insert into digest_table (fileName,digest,Edition,classID,trusted,createID,SoftwareName,description,issueDate) " +
					"value('"+a.getFileName()+"','"+a.getDigest()+"','"+a.getEdition()+"',"+a.getClassID()+","
					+a.getTrusted()+","+a.getCreateID()+",'"+a.getSoftwareName()+"','"+a.getDescription()+"','"+a.getIssueTime()+"')";//�������
			log.logger.info("yangwensiinsert:"+strSql);
			statement.executeUpdate(strSql);
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			message.append("�������ʧ�ܣ�����ϵ����Ա��");
			return false;
		}
//		message.append("��ӳɹ���");
		return true;
	}
	
	public int deleteVerifyLog(String id,String date)
	{
		//String strSql="delete from digest_table where userid='" +id+ "' and issueDate='" +date+ "';";
		String strSql="delete from digest_table where userid='" +id+ "' and issueDate='" +date+ "'";
		String sql = "delete from verify_log where userid='" +id+ "' and date='" +date+ "'";
		log.logger.info(strSql);			
		try {
			PreparedStatement pstmt=connection.prepareStatement(strSql);
			int r = pstmt.executeUpdate();
			pstmt=connection.prepareStatement(sql);
			int lines = pstmt.executeUpdate();
			// System.out.println("--- Lines "+ r +"  " + lines+" ------  "+strSql+"  ----------");
			return lines;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public int addFactory(String name,String fid){	
		String strSql="insert into l1 (name,fid) VALUES ('"+ name +"',"+ fid +")";
		log.logger.info("addFactory:" + strSql);
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(strSql);
			if(databasebean.ifmysql)
				strSql = " SELECT last_insert_id() as 'newid'";
			else
				strSql = " select max(id) from l1 as newid";
			ResultSet resultSet=statement.executeQuery(strSql);
			int n = 0;
			if(resultSet.next())
			{
				if(databasebean.ifmysql)
					n = Integer.parseInt(resultSet.getString("newid"));
				else
					n = Integer.parseInt(resultSet.getString(1));
			}			
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public int updateFactory(String id,String name){
		String strSql="update l1 set name='"+ name +"' where id="+ id;
		log.logger.info("updateFactory:" + strSql);			
		try {
			PreparedStatement pstmt=connection.prepareStatement(strSql);
			int lines = pstmt.executeUpdate();
			return lines;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public int deleteFactory(String id){
		String strSql="delete from l1 where id='" + id + "'";
		log.logger.info("deleteFactory:" + strSql);			
		try {
			PreparedStatement pstmt=connection.prepareStatement(strSql);
			int lines = pstmt.executeUpdate();
			return lines;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public int addTechnology(String name,String fid){	
		String strSql="insert into l2 (name,fid) VALUES ('"+ name +"',"+ fid +")";
		log.logger.info("addTechnology:" + strSql);
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(strSql);
			if(databasebean.ifmysql)
				strSql = " SELECT last_insert_id() as 'newid'";
			else
				strSql = " select max(id) from l2 as newid";
			ResultSet resultSet=statement.executeQuery(strSql);
			int n = 0;
			if(resultSet.next())
			{
				if(databasebean.ifmysql)
					n = Integer.parseInt(resultSet.getString("newid"));
				else
					n = Integer.parseInt(resultSet.getString(1));
			}			
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public int deleteTechnology(String id){
		String strSql="delete from l2 where id='" + id + "'";
		log.logger.info("deleteTechnology:" + strSql);			
		try {
			PreparedStatement pstmt=connection.prepareStatement(strSql);
			int lines = pstmt.executeUpdate();
			return lines;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public int updateTechnology(String id,String name){
		String strSql="update l2 set name='"+ name +"' where id="+ id;
		log.logger.info("updateTechnology:" + strSql);			
		try {
			PreparedStatement pstmt=connection.prepareStatement(strSql);
			int lines = pstmt.executeUpdate();
			return lines;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	public int addSystem(String name,String fid){	
		String strSql="insert into l3 (name,fid) VALUES ('"+ name +"',"+ fid +")";
		log.logger.info("addSystem:" + strSql);
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(strSql);
			if(databasebean.ifmysql)
				strSql = " SELECT last_insert_id() as 'newid'";
			else
				strSql = " select max(id) from l3 as newid";
			ResultSet resultSet=statement.executeQuery(strSql);
			int n = 0;
			if(resultSet.next())
			{
				if(databasebean.ifmysql)
					n = Integer.parseInt(resultSet.getString("newid"));
				else
					n = Integer.parseInt(resultSet.getString(1));
			}			
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public int deleteSystem(String id){
		String strSql="delete from l3 where id='" + id + "'";
		log.logger.info("deleteSystem:" + strSql);			
		try {
			PreparedStatement pstmt=connection.prepareStatement(strSql);
			int lines = pstmt.executeUpdate();
			return lines;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public int updateSystem(String id,String name){
		String strSql="update l3 set name='"+ name +"' where id="+ id;
		log.logger.info("updateSystem:" + strSql);			
		try {
			PreparedStatement pstmt=connection.prepareStatement(strSql);
			int lines = pstmt.executeUpdate();
			return lines;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	
	public boolean destroy(){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
