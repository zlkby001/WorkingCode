package com.ics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.mappings.FullWhiteBean;

public class contrastKnowledgeServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public contrastKnowledgeServlet() {
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
		request.setCharacterEncoding("UTF-8");
		String driver = "com.mysql.jdbc.Driver";
	    String url1 = "jdbc:mysql://localhost:3306/immdb_new";
	    int flag,k;
	    String sql="";
	    ArrayList al = (ArrayList) request.getSession(true).getAttribute("MappingCollection");
	    Connection con = null;
	       try {
	        Class.forName(driver);
	        try {
	         con = DriverManager.getConnection(url1,"root","tcwg");
	         Statement stmt=con.createStatement();  

	         ResultSet res = stmt.executeQuery("SELECT hash_value FROM knowledge_item ");
	         while(res.next()){
			    flag=0;
				for (k = 0; k < al.size(); k++) {
					FullWhiteBean mw = (FullWhiteBean) (al.get(k));
		            //System.out.println(res.getString("hash_value"));
		           // System.out.println(mw.getDigest());
					if (res.getString("hash_value").toUpperCase().equals(mw.getDigest().toUpperCase())) {
						flag = 1;
						break;
					}
				}
				 if (flag == 0)
					 request.getRequestDispatcher("../HistoryList.jsp?k="+k).forward(request,response);  
				 flag = 0;
			}
	         stmt.close();  
	         con.close();  
	        } catch (SQLException e) {
	         e.printStackTrace();
	        }
	       } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	       }
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
