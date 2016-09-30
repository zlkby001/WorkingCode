package cn.ac.sklois.imm.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class modifyPersonalInfoServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public modifyPersonalInfoServlet() {
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
		String oid=(String) request.getParameter("oid");
		
		String name=(String) request.getParameter("name");
		String address=(String) request.getParameter("address");
		String phoneNum=(String) request.getParameter("phonenum");
		String email=(String) request.getParameter("email");
		
		//added by lh
		String gender=(String) request.getParameter("gender");
		//end
		
		int OperatorID=Integer.parseInt(oid);
		AdminService as=new AdminService();
		int i=as.modifyPersonalInfo(OperatorID, name, address, phoneNum, email,gender);
		
		if(i==0){
			request.setAttribute("result","修改成功  !");
			request.getSession(true).setAttribute("name", name);
		}else if(i==1){
			request.setAttribute("result","名字已存在，请换一个名字！");
		}else if(i==2){
			request.setAttribute("result","该用户已经不存在，不能修改，请联系系统维护人员！");
		}else if(i==3){
			request.setAttribute("result","数据库操作失败，请联系系统维护人员！");
		}
		request.getRequestDispatcher("../modifyPIresult.jsp").forward(request,response);
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
