package cn.ac.sklois.imm.mappings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ics.util.Cryptor;
import com.ics.util.DBConnection;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import cn.ac.sklois.imm.admin.AdminsDBBean;
import cn.ac.sklois.imm.admin.Logtest;
import cn.ac.sklois.imm.admin.UserBean;
import cn.ac.sklois.imm.admin.loginservlet;

public class uploadwhitelistServlet extends HttpServlet {
	private ServletConfig config;
	private static int vern=-1;
	String user = "";
	String name = "";
	String type = "白名单";
	String action = "导入白名单";
	 final public void init(ServletConfig config) throws ServletException {   
		   this.config = config;   
		 }   
		  
		 final public ServletConfig getServletConfig() {   
		   return config;   
		 } 
	/**
	 * Constructor of the object.
	 */
	public uploadwhitelistServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserBean a = (UserBean)request.getSession(true).getAttribute("a");
		int userid = Integer.parseInt(a.getID());
		int result=0;
		Logtest log = new Logtest();  
		log.logger.info("the userid is"+userid);
		Connection con = DBConnection.getConnection();
		try {
			Statement statement = con.createStatement();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//doPost(request, response);
	}


	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserBean a = (UserBean)request.getSession(true).getAttribute("a");
		int userid = Integer.parseInt(a.getID());
		//String userid=request.getParameter("userid");
		int result=0;
		//System.out.println(request.getServletPath());
		//PropertyConfigurator.configure("log4j.properties");
		Logtest log = new Logtest();  
		log.logger.info("the userid is"+userid);
		SmartUpload mySmartUpload = new SmartUpload(); 
		mySmartUpload.initialize(config,request,response); 
		Connection con = DBConnection.getConnection();
		String filename="";
		String filenamelist=null;
		String sPath="";
		String decfilename="";
		AdminsDBBean dbBean=new AdminsDBBean();
		UserBean user1=dbBean.findUserbyID(userid);
		String pk=user1.getpubkey();
		//System.out.println("pk:"+pk);
		try {

			mySmartUpload.upload();
			 if (mySmartUpload.getFiles().getCount() > 0) {   
			      com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(0);   
			     filename=myFile.getFileName();
			      log.logger.info(filename);
			      String sWebRootPath = getServletContext().getRealPath("/");
				sPath=sWebRootPath+"upload\\";
				File dir =new File(sPath);
				if (!dir.exists()){
					dir.mkdirs();
				}
		       // Save it only if this file exists   
		       if (!myFile.isMissing()) {   
		         myFile.saveAs("/upload/"+filename, SmartUpload.SAVE_VIRTUAL);   
		       }   
		       Cryptor cryptor=new Cryptor();
		       decfilename=filename.replaceAll(".enc", ".txt");
		       try {
				cryptor.Decrypt(sPath+filename, sPath+decfilename);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("result", "解密知识库文件失败!");
				request.getRequestDispatcher("../addWhite.jsp").forward(request,response);
				return;
			}
			 }
			 else
				 return;
		} catch (SmartUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			request.setAttribute("result", "解析知识库文件失败!");
			request.getRequestDispatcher("../addWhite.jsp").forward(request,response);
			return;
		}
		 File file =new File(sPath+decfilename);
		try{
			 FileReader fileReader = new FileReader(file);
	            BufferedReader bufferedreader = new BufferedReader(fileReader);
	            String[] arr = null;
	            String s;
	    		Statement statement = con.createStatement();
	            while(bufferedreader.readLine()!=null){
	            	s=bufferedreader.readLine();
	            	//System.out.println("whitelist"+s);
	            	arr = s.split(" | ");
	            	//System.out.println("arr0"+arr[0]);
	            	filenamelist=arr[0].trim();
	            	String Filename = filenamelist.substring(filenamelist.lastIndexOf("/")+1);
	            	String hashvalue = arr[2];
	            	//System.out.println("Filename:"+Filename+" "+"path:"+hashvalue);
	            String sql="select count(id) as count from whitelist_content where userid="+userid+" and Hash_Value='"+hashvalue+"' and Process_Name='"+Filename+"'";
	            try {
                	int count=0;
                	java.sql.ResultSet rs=statement.executeQuery(sql);
                	if (rs.next()) {
        				count = rs.getInt("count");
        			}
                	//信任全部
                	if(count<=0)
                	{
                		// sql="insert into knowledge_item(ics_id,hash_value,process_name,process_path,software_ver,os_ver,software_info,release_date,Manufacturer,ic_bool) values("+ics_id+",'"+hashvalue+"','"+fname+"','"+filepath+"','"+fileversion+"','"+ostype+"','"+filedesp+"','UNKNOWN','"+company+"','"+softtype+"')";
                	 sql="insert into whitelist_content(TCM_PK,Process_Name,Process_Path,Hash_Value,userid,whitelist_vern) values('"+pk+"','"+Filename+"','"+filenamelist+"','"+hashvalue+"','"+userid+"','"+vern+"')";
					statement.executeUpdate(sql);
                	}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            }
	            statement.close();
				con.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			result=-1;
			e.printStackTrace();
			request.setAttribute("result", "导入白名单文件失败!");
			//request.getRequestDispatcher("../result2.jsp").forward(request,response);
		}catch (Exception e) {
			result=-1;
            e.printStackTrace();
            request.setAttribute("result", "导入白名单文件失败!");
        }
		request.setAttribute("result", "导入白名单成功!");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time= df.format(new Date());// new Date()为获取当前系统时间
		
	
		String LogContent = user + "#" + name+ "#" + type + "#" +action + "#" + time + "\n";
		System.out.println("Log"+LogContent);
		Connection con1 = DBConnection.getConnection();
		Statement stmt = null;
	//	ResultSet result1 = null;
		if(loginservlet.usertype.equalsIgnoreCase("manager")){
			loginservlet.usertype="管理员";
		}
		try {
			stmt = con1.createStatement();
			String sql1="insert into auditlog(userid,role,user,type,action,time) values("
					+userid
					+",'"
					+loginservlet.usertype
					+"','"
					+loginservlet.name1
					+"','"
					+type
					+"','"
					+action
					+"','"	
					+time
					+"')"
					;
		
				stmt.executeUpdate(sql1);
			//	result1.close();
				stmt.close();
				con1.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	
		if(result==0)
			request.getRequestDispatcher("../result2.jsp").forward(request,response);
			else
		request.getRequestDispatcher("../addWhite.jsp").forward(request,response);
		
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
