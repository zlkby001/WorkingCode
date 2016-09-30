package cn.ac.sklois.imm.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class historyDetailServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public historyDetailServlet() {
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
		String sdate=request.getParameter("sdate");
		//String flag=request.getParameter("flag");
		int oid=Integer.parseInt(oidstr);
		AdminService as=new AdminService();
		UserBean a=as.getUser(oid);
		request.getSession(true).setAttribute("a", a);
		//request.getSession(true).setAttribute("VID", oid);
		request.getSession(true).setAttribute("sdate", sdate);
		//System.out.println("======Detail sdate======  "+sdate+"  ==============");
		/*
		if(flag.equalsIgnoreCase("unpass")){
			request.getRequestDispatcher("../viewPDetail.jsp?flag=unpass").forward(request,response);
		}else{
			request.getRequestDispatcher("../viewPDetail.jsp?flag=pass").forward(request,response);
		}
		*/
		request.getRequestDispatcher("../viewHistoryDetailFrame.jsp").forward(request,response);
		
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
