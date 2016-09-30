package cn.ac.sklois.imm.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class registerservlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public registerservlet() {
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
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
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
		response.setCharacterEncoding("UTF-8");
		
		String name=request.getParameter("name");
		String endtype= request.getParameter("endtype");
		String usertype= request.getParameter("usertype");
		String email=request.getParameter("email");
		String phonenum=request.getParameter("phonenum");
		String address=request.getParameter("address");
		String password1=request.getParameter("password1");
		String password2=request.getParameter("password2");
	//	String usertype = request.getParameter("usertype");
	//	String usertype = null;
		//add by lh
		String gender=request.getParameter("gender");
		String certclass=request.getParameter("certclass");
		String certnumber=request.getParameter("certnumber");
		//end
		if (!password1.equals(password2)){
			System.out.print("Passwords do not match!");
			request.setAttribute("result","两次输入的密码不一致!");
		}else if (certclass.equals("")||certnumber.equals("")){
			System.out.print("certclass is null!");
			request.setAttribute("result","证件信息为空，不能完成注册!");
		}else if(password1.equals("")||(password2.equals(""))){
			System.out.print("passward is null!");
			request.setAttribute("result","密码为空，不能完成注册!");
		}else if(name.equals("")){
			System.out.print("name is null!");
			request.setAttribute("result","用户名为空，不能完成注册!");
		}
		else{
		
			AdminService as=new AdminService();
			int i=as.register(password1, name,endtype, address, phonenum, email,gender,certclass,certnumber,usertype);
			if(i==1){
				System.out.print("名字已经存在!");
				request.setAttribute("result","名字已经存在,请重新选择名字!");
			}else if(i==2){
				System.out.print("注册失败!");
				request.setAttribute("result","注册失败!");
			}else if(i==0){
				System.out.print("注册成功!");
				request.setAttribute("result","注册成功   !");
				request.getRequestDispatcher("../registersuccess.jsp").forward(request,response);
				return;
			}
		}
		request.getRequestDispatcher("../registerresult.jsp").forward(request,response);
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
