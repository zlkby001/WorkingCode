package cn.ac.sklois.imm.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class adminCheckServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public adminCheckServlet() {
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

		String oidstr=request.getParameter("oid");
		int oid=Integer.parseInt(oidstr);
		SuperAdminService sas=new SuperAdminService();
		int i=sas.checkAdmin(oid);
		if(i==0){
			//String name=request.getParameter("username");
			loginservlet l = new loginservlet();
		
			String name = (String) request.getSession(true).getAttribute("username");
			System.out.println("审批name"+name+"loginservlet.usertype"+loginservlet.usertype);
			
			//AdminService as=new AdminService();
		//	String usertype=request.getParameter("usertype");
			//String usertype = (String) request.getSession(true).getAttribute("usertype");
			if(loginservlet.usertype.equalsIgnoreCase("manager")){
				loginservlet.usertype="管理员";
				}
			  audit.log_record(oid, loginservlet.usertype, loginservlet.name1, "审核新帐户", "批准");
			request.setAttribute("result","操作成功，该管理员帐户得到了批准  !");
			
		}else if(i==2){
			request.setAttribute("result","该帐户已经不存在，不能审批，请联系系统维护人员！");
		}else if(i==3){
			request.setAttribute("result","数据库操作失败，请联系系统维护人员！");
		}
		
		request.getRequestDispatcher("../checkAdminResult.jsp").forward(request,response);
		
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
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
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
