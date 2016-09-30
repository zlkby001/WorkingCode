package com.ics.usb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.Logtest;
import cn.ac.sklois.imm.admin.UserBean;
import cn.ac.sklois.imm.mappings.HistorySearchBean;
import cn.ac.sklois.imm.mappings.MappingService;

public class usbInfoServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public usbInfoServlet() {
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
		
		String manufacture = request.getParameter("manufacture");
		String sn = request.getParameter("sn");
		String terminal = request.getParameter("terminal");
		String date = request.getParameter("date");
		
		if(manufacture!=null)			
			manufacture=new String(manufacture.getBytes("ISO-8859-1"),"UTF-8");
		if(sn!=null)			
			sn=new String(sn.getBytes("ISO-8859-1"),"UTF-8");
		if(terminal!=null)			
			terminal=new String(terminal.getBytes("ISO-8859-1"),"UTF-8");
		if(date!=null)			
			date=new String(date.getBytes("ISO-8859-1"),"UTF-8");
		
		UsbBean sb = new UsbBean();	//searchBean
		sb.setManufacture(manufacture);
		sb.setSn(sn);
		sb.setTerminal(terminal);
		sb.setDate(date);
		
		MappingService ms=new MappingService();
		ArrayList c=ms.getUsbInfo(sb);
		
		request.getSession(true).setAttribute("MappingCollection",c);
		getServletContext().getRequestDispatcher("/usbInfoList.jsp?p=1").forward(request,response);
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
