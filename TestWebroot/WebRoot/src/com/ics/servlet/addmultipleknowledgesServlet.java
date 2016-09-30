package com.ics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.UserBean;
import cn.ac.sklois.imm.mappings.FullWhiteBean;
import cn.ac.sklois.imm.mappings.MappingService;

public class addmultipleknowledgesServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public addmultipleknowledgesServlet() {
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

		request.setCharacterEncoding("UTF-8");
		ArrayList c=new ArrayList();
		String YesNo[]=request.getParameterValues("YesNo");
		boolean res = true;
		StringBuffer mes = new StringBuffer();
		//UserBean user=(UserBean)request.getSession(true).getAttribute("a");
		//int userid=Integer.parseInt(user.getID());
		/*
		String id=(String)request.getSession(true).getAttribute("id");
		if(id==null){
			System.out.println("添加白名单失败！");	
			request.setAttribute("flag","添加白名单失败！");
			getServletContext().getRequestDispatcher("/VerifyFailResult.jsp").forward(request,response);
			return;
		}
		*/
		boolean b;
		boolean result=true;
		String kname=(String) request.getSession(true).getAttribute("kname");
		for(int i=0;i<YesNo.length;i++){
			FullWhiteBean fb = new FullWhiteBean();
			int aid=Integer.parseInt(request.getParameter("aid"+YesNo[i]));
			MappingService ms=new MappingService();
			b=ms.addknowledge(aid,kname);
			if(!b)
				result=false;
		}
		if(result){
			request.setAttribute("result","添加成功！");
	    }else request.setAttribute("result", "操作完成！");
		if(res == true)
			request.setAttribute("flag", "添加成功");
		else
			request.setAttribute("flag", "操作完成");
		
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
