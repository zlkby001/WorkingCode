package com.ics.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ics.util.DBConnection;

public class ModifyKnowledgeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String process_name = "", hash_value = "", software_ver = "", os_ver = "", software_info = "", release_date = "", manufacturer = "", ic_bool = "", ics_name = "", id = "";

		if (request.getParameter("id") != "")
			id = request.getParameter("id");
		if (request.getParameter("process_name") != "")
			process_name = request.getParameter("process_name");
		if (request.getParameter("hash_value") != "")
			hash_value = request.getParameter("hash_value");
		if (request.getParameter("software_ver") != "")
			software_ver = request.getParameter("software_ver");
		if (request.getParameter("os_ver") != "")
			os_ver = request.getParameter("os_ver");
		if (request.getParameter("software_info") != "")
			software_info = request.getParameter("software_info");
		if (request.getParameter("release_date") != "")
			release_date = request.getParameter("release_date");
		if (request.getParameter("manufacturer") != "")
			manufacturer = request.getParameter("manufacturer");
		if (request.getParameter("ic_bool") != "")
			ic_bool = request.getParameter("ic_bool");
		//String ics_name1 = null;
		if (request.getParameter("ics_name") != null){
			ics_name = URLDecoder.decode(request.getParameter("ics_name"),"UTF-8");
			//ics_name1 = ics_name.replace("+", " ");
		}
		
		//modified by zhyjun
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			//ResultSet rs1 = null;

			stmt1 = con.createStatement();

			
			String zhyjun="UPDATE knowledge_item set software_info='"+software_info+"',release_date='"+release_date+"',Manufacturer='"+manufacturer+"',ic_bool='"+ic_bool+"' where id="+id;
			
			
			
			
			stmt1.executeUpdate(zhyjun);

			stmt1.close();
			con.close();
			
			String url ="ResultSuccess.jsp?back=2";
			request.getRequestDispatcher(url).forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
			//String url ="ResultSuccess.jsp?back=viewKnowledge.jsp?ics_name=" + URLEncoder.encode(ics_name,"UTF-8");
			String url ="ResultSuccess.jsp?back=2";
			request.getRequestDispatcher(url).forward(request,
					response);
		}
		
		/*
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null, stmt2 = null;
			ResultSet rs1 = null;

			stmt1 = con.createStatement();
			stmt2 = con.createStatement();
			
			String search="select process_name,software_ver from knowledge_basic where hash_value='"+hash_value+"'";
			ResultSet rstmp=stmt2.executeQuery(search);
			String pname="";
			String pver="";
			if(rstmp.next())
			{
				pname=rstmp.getString("process_name");
				pver=rstmp.getString("software_ver");
			}

			String sql11 = "UPDATE knowledge_basic SET os_ver ='" + os_ver
					+ "' where hash_value='" + hash_value + "'";
		
			
			String sql21 = "UPDATE knowledge_extension SET software_info='" + software_info + "',release_date='"
					+ release_date + "',manufacturer='" + manufacturer
					+ "',ic_bool ='" + ic_bool + "' where process_name='" + pname + "' and software_ver='"+pver+"'";  //modified by zhyjun at 2015/1/28
					//+ "',ic_bool ='" + ic_bool + "' where software_ver='"
					//+ software_ver + "' and process_name ='" + process_name + "'";
			String sql31 = "UPDATE knowledge_recommended SET ics_name='" + ics_name
					+ "',hash_value='" + hash_value + "' where hash_value='"
					+ hash_value + "'";

			String sql12 = "INSERT INTO knowledge_basic VALUES('" + hash_value
					+ "','" + process_name + "','" + software_ver + "','" + os_ver
					+ "')";
			String sql22 = "INSERT INTO knowledge_extension VALUES('"
					+ software_ver + "','" + process_name + "','" + software_info
					+ "','" + release_date + "','" + manufacturer + "','" + ic_bool
					+ "')";
			String sql32 = "INSERT INTO knowledge_recommended VALUES('" + ics_name
					+ "','" + hash_value + "')";

			String sql1 = "SELECT * FROM knowledge_basic WHERE hash_value = '"
					+ hash_value + "'";
			String sql2 = "SELECT * FROM knowledge_extension WHERE software_ver='"
					+ software_ver + "' and process_name ='" + process_name + "'";
			String sql3 = "SELECT * FROM knowledge_recommended WHERE hash_value='"
					+ hash_value + "' and ics_name = '" + ics_name + "'";

			String sql = "DELETE FROM knowledge_recommended WHERE hash_value='"
					+ hash_value + "' and ics_name = '"
					+ ics_name + "'";

			
			
			stmt2.executeUpdate(sql11);
			stmt2.executeUpdate(sql21);
			//stmt2.executeUpdate(sql31);
			rstmp.close();
			stmt2.close();
			con.close();
			
			String url ="ResultSuccess.jsp?back=2";
			request.getRequestDispatcher(url).forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
			//String url ="ResultSuccess.jsp?back=viewKnowledge.jsp?ics_name=" + URLEncoder.encode(ics_name,"UTF-8");
			String url ="ResultSuccess.jsp?back=2";
			request.getRequestDispatcher(url).forward(request,
					response);
		}*/
	}
}
