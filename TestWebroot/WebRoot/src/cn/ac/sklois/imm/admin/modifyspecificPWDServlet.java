package cn.ac.sklois.imm.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class modifyspecificPWDServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public modifyspecificPWDServlet() {
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

		String oldpwd=(String) request.getParameter("oldpwd");
		String pwd1=(String) request.getParameter("pwd1");
		String pwd2=(String) request.getParameter("pwd2");
		AdminBean a=(AdminBean)request.getSession(true).getAttribute("a");
		int OperatorID=Integer.parseInt(a.getOperatorID());
		
		
		if (!pwd1.equals(pwd2)){
			System.out.print("Passwords do not match!");
			request.setAttribute("result","两次输入的密码不一致，请检查，重新输入!");		
		}else{
			AdminService as=new AdminService();
			boolean b=as.modifyPassword(OperatorID, oldpwd, pwd1);
			if(b){//修改成功
				request.setAttribute("result","修改成功   !");
			}else{//修改失败
				request.setAttribute("result","修改失败，请检查旧密码是否输入正确  !");
			}
			
		}
		request.getRequestDispatcher("../modifyPWDresult.jsp").forward(request,response);
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
