package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.UserBean;
import cn.ac.sklois.imm.admin.audit;
import cn.ac.sklois.imm.admin.loginservlet;

public class addWhiteServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public addWhiteServlet() {
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

		String ids=request.getParameter("aid");
		int id=Integer.parseInt(ids);
		UserBean user=(UserBean)request.getSession(true).getAttribute("a");
		int userid=Integer.parseInt(user.getID());
		
		MappingService ms=new MappingService();
		boolean b;
		b=ms.AddWhite(id,userid);
		if(b){
			  audit.log_record(userid, loginservlet.usertype, loginservlet.name1, "导入白名单", "远程日志选中添加完成");
			request.setAttribute("result","添加白名单成功!");
		}else request.setAttribute("result","添加白名单失败!");
		request.getRequestDispatcher("../addWhite.jsp").forward(request,response);
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

		String ids=request.getParameter("aid");
		int id=Integer.parseInt(ids);
		UserBean user=(UserBean)request.getSession(true).getAttribute("a");
		int userid=Integer.parseInt(user.getID());
		
		MappingService ms=new MappingService();
		boolean b;
		b=ms.AddWhite(id,userid);
		if(b){
			request.setAttribute("result","添加白名单成功!");
			  audit.log_record(userid, loginservlet.usertype, loginservlet.name1, "导入白名单", "远程日志选中添加完成");
		}else request.setAttribute("result","添加白名单失败!");
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
