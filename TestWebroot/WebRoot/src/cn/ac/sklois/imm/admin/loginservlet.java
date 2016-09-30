package cn.ac.sklois.imm.admin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.mappings.FullWhiteBean;

import com.ics.util.DBConnection;

public class loginservlet extends HttpServlet {
	String user = "server";
	public static String name1 = null;
	String type = null;
	String action = null;
	public static String usertype=null;
	
	/**
	 * Constructor of the object.
	 */
	public loginservlet() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
	
		String name=request.getParameter("username");
		Logtest log = new Logtest();
		log.logger.info("**********************************username is:"+name+"*********");
		String password=request.getParameter("password");
		log.logger.info("**********************************username is:"+password+"*********");
		AdminService as=new AdminService();
	    AdminBean a=null;
	    a=as.loginbyName(name, password);
	    if(a==null){
	    	
			request.setAttribute("result","用户名或密码输入错误！");
		    request.getRequestDispatcher("../loginresult.jsp").forward(request,response);
	    }else if(!a.getPassword().equals(password)){
			request.setAttribute("result","用户名或密码输入错误！");
			request.getRequestDispatcher("../loginresult.jsp").forward(request,response);
    	}else {
			request.setAttribute("result","登陆错误 ");
			//锟矫伙拷锟斤拷ID锟斤拷锟斤拷session   
			AuthVerify verifier=new AuthVerify();
			int verifyres=verifier.Verify();
			log.logger.info("**********************************authorization result is:"+verifyres+"*********");
			//request.setAttribute("verifyres", verifyres);
			if(verifyres==-1||verifyres==-3)
			{
				if(verifyres==-1)
					request.setAttribute("result", "授权验证出错或授权验证失败");
				/*if(verifyres==-2)
					request.setAttribute("result", "授权已经过期");*/
				if(verifyres==-3)
					request.setAttribute("result", "试用期已经过期");
				request.getRequestDispatcher("../authverifyfail.jsp").forward(request,response);
				//return;
			}
			else
			{
				 
			//if(verifyres.)
			request.getSession(true).setAttribute("name",a.getName());
			request.getSession(true).setAttribute("id",a.getOperatorID());
			//added by lh  
			request.getSession(true).setAttribute("pass",a.getPass());
			//request.getSession(true).setAttribute("usertype",a.getUsertype());
			//UserBean ab = (UserBean)request.getSession(true).getAttribute("a");
			int userid = Integer.parseInt(a.getOperatorID());
			request.getSession(true).setAttribute("UserType",a.getUsertype());
			 usertype = a.getUsertype();	
			type="登陆与退出";
			action="登陆";
			name1=name;
			if(usertype.equalsIgnoreCase("supermanager")){
				 usertype = "超级管理员";
				audit.log_record(userid, usertype, name, type, action);
				System.out.println("userid: "+userid+"usertype "+usertype+"user: "+name+"type: "+type+"action: "+action);
			}else{
				String usertype="管理员";
				audit.log_record(userid, usertype, name, type, action);
				System.out.println("userid: "+userid+"usertype "+usertype+"user: "+name+"type: "+type+"action: "+action);
			}
		
		
			System.out.println("userid: "+userid+"usertype "+usertype+"user: "+user+"type: "+type+"action: "+action);
			File file =new File("logininfo.txt");
			 try {
				   FileWriter fileWriter = new FileWriter(file);
		            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		   
		        	 
		   		//	System.out.println(wsearch1.getDigest());
		   		//	bufferedWriter.write((String.valueOf(wsearch1.getFileName()) + " | "+String.valueOf(wsearch1.getDigest())).getBytes("UTF8"));
		   			bufferedWriter.write(name);
		   		   bufferedWriter.newLine(); 
		   		   bufferedWriter.write(usertype);
		   		  bufferedWriter.newLine(); 
		   		  System.out.println("userid---------------------"+userid);
		   		  bufferedWriter.write(a.getOperatorID());
		           
		           
		            bufferedWriter.close();
		            fileWriter.close(); 
		            System.out.println(file.getAbsolutePath());
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			//request.getSession(true).setAttribute("", arg1)
			//end
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//			String time= df.format(new Date());// new Date()为获取当前系统时间
//			
//			UserBean ab = (UserBean)request.getSession(true).getAttribute("a");
//			int userid = Integer.parseInt(ab.getID());
//			a = (AdminBean)request.getSession(true).getAttribute("a");
//			//String usertype = request.getParameter("")
//			action ="登陆";
//			type="登陆与退出";
//			
//			String LogContent = user + "#" + name1+ "#" + type + "#" +action + "#" + time + "\n";
//			System.out.println("Log"+LogContent);
//			Connection con1 = DBConnection.getConnection();
//			Statement stmt = null;
//		//	ResultSet result1 = null;
//			try {
//				stmt = con1.createStatement();
//				String sql1="insert into auditlog(userid,role,user,type,action,time) values("
//						+userid
//						+",'"
//						+name1
//						+"','"
//						+user
//						+"','"
//						+type
//						+"','"
//						+action
//						+"','"	
//						+time
//						+"')"
//						;
//			
//					stmt.executeUpdate(sql1);
//				//	result1.close();
//					stmt.close();
//					con1.close();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
			}
			request.getRequestDispatcher("../index2.jsp?verifyres="+verifyres).forward(request,response);
			
			}
			
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
