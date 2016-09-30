package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.Logtest;
import cn.ac.sklois.imm.admin.UserBean;

public class listWhiteServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public listWhiteServlet() {
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
		
		//String sclass=request.getParameter("sclass");
		String sname=new String(request.getParameter("sname").getBytes("ISO-8859-1"),"UTF-8");
		String sdigest=request.getParameter("sdigest");
		String description=request.getParameter("description");
		String time=request.getParameter("time");
		String type=request.getParameter("type");
		//String avalue=request.getParameter("avalue");
		//String sedition=request.getParameter("sedition");
		//String softwareName = request.getParameter("softwareName");
		UserBean a = (UserBean)request.getSession(true).getAttribute("a");
		int userid = Integer.parseInt(a.getID());
		
		//if(softwareName=="")
			//softwareName=null;
		Logtest log = new Logtest();

		//int classID = 0;
		
		//classID = Integer.parseInt(sclass);
		
		if(sname==""){
			sname=null;
		}
		if(sdigest==""){
			sdigest=null;
		}
		//int trusted = Integer.parseInt(avalue);
		
		//if(sedition==""){
			//sedition=null;
		//}
		WhitelistSearchBean wsearch=new WhitelistSearchBean();
		wsearch.setsname(sname);
		wsearch.setsdigest(sdigest);
		MappingService ms=new MappingService();
		
		
		ArrayList c=ms.ListWhite(sname,sdigest,userid,description,time,type);
		
		request.getSession(true).setAttribute("MappingCollection",c);
		request.getSession(true).setAttribute("wsearch",wsearch);
		
		getServletContext().getRequestDispatcher("/WhiteList.jsp?p=1").forward(request,response);
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
