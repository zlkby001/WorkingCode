package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.UserBean;
import cn.ac.sklois.imm.admin.audit;
import cn.ac.sklois.imm.admin.loginservlet;

public class deleteAllPagesHistorylistServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public deleteAllPagesHistorylistServlet() {
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
		FullWhiteBean fmb=null;
		UserBean user=(UserBean)request.getSession(true).getAttribute("a");
		ArrayList al=(ArrayList)request.getSession(true).getAttribute("MappingCollection");
		HistorySearchBean hsearch=(HistorySearchBean)request.getSession(true).getAttribute("hsearch");
		/*int i=0;
		boolean result=true;
		while(i<al.size())
		{
			Object obj=al.get(i);
			fmb=(FullWhiteBean)obj;
			MappingService ms=new MappingService();
			result=ms.DeleteInfo(fmb.getID());
			i++;
			
		}*/
		MappingService ms=new MappingService();
		boolean result=ms.ClearHistory(hsearch,Integer.parseInt(user.getID()));
		
		int userid = Integer.parseInt(user.getID());
		if(result){
			//  audit.log_record(userid, loginservlet.usertype, loginservlet.name1, "删除白名单", "远程日志全部清空完成");
			request.setAttribute("result","删除历史记录成功");
		}else request.setAttribute("result","删除历史记录失败!");
		
		
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
