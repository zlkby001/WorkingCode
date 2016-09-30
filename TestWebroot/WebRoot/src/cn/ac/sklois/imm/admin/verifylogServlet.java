package cn.ac.sklois.imm.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class verifylogServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public verifylogServlet() {
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
		
		
		String ip=request.getParameter("ip");
		String tmp=request.getParameter("username");
		String verifydate=request.getParameter("verifydate");
		String username;
		if(tmp=="")
			username=null;
		else
			username=new String(tmp.getBytes("ISO-8859-1"),"UTF-8");
		
		String pubkey=request.getParameter("pubkey");		


		
		
		if(ip=="")
			ip=null;
		//Logtest log = new Logtest();
		if(username==""){
			username=null;
		}
		
		if(pubkey==""){
			pubkey=null;
		}
		
		if(verifydate==""){
			verifydate=null;
		}

		AdminService ms=new AdminService();
		
		
		ArrayList verifylog=ms.ListVerifylog(ip,username,pubkey,verifydate);	
		request.getSession(true).setAttribute("verifylog",verifylog);
		getServletContext().getRequestDispatcher("/VerifylogList.jsp?p=1").forward(request,response);
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
