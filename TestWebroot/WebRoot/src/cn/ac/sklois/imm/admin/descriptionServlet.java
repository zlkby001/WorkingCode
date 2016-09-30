package cn.ac.sklois.imm.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class descriptionServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public descriptionServlet() {
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

		request.setCharacterEncoding("UTF-8");
		String des=request.getParameter("beizhu");
		String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/immdb_new";
	    String sql="";
	    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
		String date=df.format(new Date());
		
	       Connection con = null;
	       try {
	        Class.forName(driver);
	        try {
	         con = DriverManager.getConnection(url,"root","tcwg");
	         Statement stmt=con.createStatement();  
	         ResultSet res=stmt.executeQuery("SELECT * FROM wlsnapshotindex");
	         while(res.next())
	         {
	        	 String str = res.getString("id");
	        	 String str1 = res.getString("description");
	        	 String str2 = res.getString("issuedate");
	        	 System.out.println(str);
	        	 System.out.println(str1);
	        	 System.out.println(str2);
	         }
             stmt.executeUpdate("INSERT INTO wlsnapshotindex(description,issuedate) VALUES('"+des+"','"+date+"')");  
	            stmt.close();  
	             con.close();  
	        } catch (SQLException e) {
	         e.printStackTrace();
	        }
	       } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	       }
	       
	     //  request.getRequestDispatcher("../createwlsnapshotServlet").forward(request,response);
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
