package com.ics.usb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.mappings.FullWhiteBean;
import cn.ac.sklois.imm.mappings.MappingService;

public class usbUserListServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public usbUserListServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request,response);
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");		
		String uid = request.getParameter("uid");
		
		if(uid==null || uid.equals("")||uid.equals("null"))	{		//多选项
			String YesNo[]=request.getParameterValues("YesNo");
			//request.setAttribute("usbid", YesNo);
			request.getSession(true).setAttribute("usbMeasureId",YesNo);
		}
		else{	//单选项
			uid=new String(uid.getBytes("ISO-8859-1"),"UTF-8");
		}
		
	
		MappingService ms=new MappingService();				
		ArrayList c=ms.getUserList();
		
		request.getSession(true).setAttribute("MappingCollection",c);
		getServletContext().getRequestDispatcher("/usbUserList.jsp?p=1&uid=" + uid ).forward(request,response);
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
