//add by zhyjun at 2015/10/28

package com.ics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ics.util.DBConnection;
import com.ics.util.syslog;
import com.ics.util.syslogthread;

public class SyslogServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SyslogServlet() {
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
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
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
		syslog.syslogIP=getServletConfig().getInitParameter("syslogIP");
		syslog.syslogPort=Integer.parseInt(getServletConfig().getInitParameter("syslogPort"));
		syslog.syslogOpen=getServletConfig().getInitParameter("syslogOpen");
		syslog.minCycle=Integer.parseInt(getServletConfig().getInitParameter("minCycle"));
		
		try {
			syslogthread.getLocalMac();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(syslog.syslogOpen.equals("1"))
		{
			transferallsyslogs();
		}
		
	}
	
	private void transferallsyslogs()
	{
		Connection con = DBConnection.getConnection();
		Statement stmt = null;
		//Statement stmt1 = null;
		ResultSet result = null;
		//ResultSet result1 = null;
		syslogthread sys=new syslogthread();
		try {
			stmt = con.createStatement();	
			String sql="select id,name,manufacture,client_area,sequence,ip,mac,os,EK,device_type,fid,pubkey from users";
			result=stmt.executeQuery(sql);
			while(result.next())
			{
				sys.users_id=result.getInt(1);
				sys.name=result.getString(2);
				sys.manufacture=result.getString(3);
				sys.client_area=result.getString(4);
				sys.sequence=result.getString(5);;
				sys.ip=result.getString(6);
				sys.mac=result.getString(7);
				sys.os=result.getString(8);
				sys.EK=result.getString(9);
				sys.device_type=result.getString(10);
				sys.fid=result.getInt(11);
				sys.pubkey=result.getString(12);
				
				//首先发送特定用户的进程报警日志
				sql="select id,filename,digest,issuedate,acknowledge from alert where userid = "+sys.users_id;
				Statement stmt1= con.createStatement();
				ResultSet result1=stmt1.executeQuery(sql);
				while(result1.next())
				{
					sys.Process_id=result1.getInt(1);
					sys.filename=result1.getString(2);
					sys.digest=result1.getString(3);
					sys.issuedate=result1.getString(4);
					sys.acknowledge=result1.getInt(5);
					sys.sendProcess();
					
				}
				result1.close();
				//stmt1.close();
				
				//接着发送该用户的USB报警日志
				sql="select id,manufacture,sn,version,producer,date from usbmeasure where terminalid = "+sys.users_id;
				result1=stmt1.executeQuery(sql);
				while(result1.next())
				{
					sys.Usbmeasure_id=result1.getInt(1);
					sys.usb_manufacture=result1.getString(2);
					sys.sn=result1.getString(3);
					sys.version=result1.getString(4);
					sys.producer=result1.getString(5);
					sys.date=result1.getString(6);
					sys.sendUsb();
				}
				result1.close();
				stmt1.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
	}

}
