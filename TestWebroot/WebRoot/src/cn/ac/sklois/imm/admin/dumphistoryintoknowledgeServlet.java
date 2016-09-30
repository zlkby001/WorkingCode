package cn.ac.sklois.imm.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.mappings.FullWhiteBean;
import cn.ac.sklois.imm.mappings.HistorySearchBean;
import cn.ac.sklois.imm.mappings.MappingService;

public class dumphistoryintoknowledgeServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public dumphistoryintoknowledgeServlet() {
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
		//FullWhiteBean fmb=null;
		UserBean user=(UserBean)request.getSession(true).getAttribute("a");
		//ArrayList al=(ArrayList)request.getSession(true).getAttribute("MappingCollection");
		HistorySearchBean hsearch=(HistorySearchBean)request.getSession(true).getAttribute("hsearch");
		/*
		int i=0;
		boolean result=true;
		while(i<al.size())
		{
			Object obj=al.get(i);
			fmb=(FullWhiteBean)obj;
			MappingService ms=new MappingService();
			result=ms.AddWhite(fmb.getID(),Integer.parseInt(user.getID()));
			i++;
			
		}*/
		String kname=(String) request.getSession(true).getAttribute("kname");
		MappingService ms=new MappingService();
		boolean result=ms.DumpHistoryToKnowledge(hsearch,Integer.parseInt(user.getID()),kname);
		
		
		if(result){
			request.setAttribute("result","添加成功!");
		}else request.setAttribute("result","操作完成!");
				
		request.getRequestDispatcher("/addWhite.jsp").forward(request,response);
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
