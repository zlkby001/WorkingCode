package cn.ac.sklois.imm.upload;
import java.io.*;
import java.io.File;
import java.util.ArrayList;

import com.jspsmart.upload.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.Logtest;
import cn.ac.sklois.imm.mappings.FullInfoBean;
import cn.ac.sklois.imm.mappings.MappingService;


public class verifyFailFindServlet extends HttpServlet {
	
	 private ServletConfig config;   
	  
	 // Init the servlet   
	 final public void init(ServletConfig config) throws ServletException {   
	   this.config = config;   
	 }   
	  
	 final public ServletConfig getServletConfig() {   
	   return config;   
	 } 
	/**
	 * Constructor of the object.
	 */
	public verifyFailFindServlet() {
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
		
		String sname=request.getParameter("sname");
		Logtest log = new Logtest();
		log.logger.info("yangwensisname"+sname);
		String fname=request.getParameter("fname");
		String avalue=request.getParameter("avalue");
		String clientIP = request.getParameter("clientIP");
		int trusted = Integer.parseInt(avalue);

		if(sname==""){
			sname=null;
		}
		if(clientIP=="")
			clientIP=null;
		MappingService ms=new MappingService();
		ArrayList c = ms.ListVerifyFailMappings(sname,fname,trusted,clientIP);
		
		request.getSession(true).setAttribute("VerifyFailListCollection",c);
		
		getServletContext().getRequestDispatcher("/VerifyFailDataList.jsp?p=1").forward(request,response);
		
		
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

//		request.setCharacterEncoding("UTF-8");
//		
//		String sname=request.getParameter("sname");
//		String avalue=request.getParameter("avalue");
//		String clientIP = request.getParameter("clientIP");
//		int trusted = Integer.parseInt(avalue);
//
//		if(sname==""){
//			sname=null;
//		}
//		if(clientIP=="")
//			clientIP=null;
//		MappingService ms=new MappingService();
//		ArrayList c = ms.ListVerifyFailMappings(sname, trusted,clientIP);
//		
//		request.getSession(true).setAttribute("VerifyFailListCollection",c);
//		
//		getServletContext().getRequestDispatcher("/VerifyFailDataList.jsp").forward(request,response);
//		
//		
		        
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
