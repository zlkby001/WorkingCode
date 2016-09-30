package com.ics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import cn.ac.sklois.imm.admin.UserBean;
import cn.ac.sklois.imm.mappings.MappingService;

import com.ics.model.Knowledge;
import com.ics.util.DBConnection;
import com.java1234.util.ResponseUtil;

public class addmultipleknowledgeServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public addmultipleknowledgeServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ids=request.getParameter("aid");
		int id=Integer.parseInt(ids);
		//UserBean user=(UserBean)request.getSession(true).getAttribute("a");
		//int userid=Integer.parseInt(user.getID());
		
		MappingService ms=new MappingService();
		boolean b;
		String kname=(String) request.getSession(true).getAttribute("kname");
		b=ms.addknowledge(id,kname);
		if(b){
			request.setAttribute("result","添加成功!");
		}else request.setAttribute("result","操作完成!");
		request.getRequestDispatcher("../addWhite.jsp").forward(request,response);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
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
