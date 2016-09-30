package cn.ac.sklois.imm.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.mappings.HistorySearchBean;
import cn.ac.sklois.imm.mappings.MappingService;

public class historyintoknowledgeServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public historyintoknowledgeServlet() {
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
		
		
		String sclass=request.getParameter("sclass");
		String name = request.getParameter("sname");
		String sname = null;
		if(name!=null)			
			sname=new String(name.getBytes("ISO-8859-1"),"UTF-8");
		//String sname=request.getParameter("sname");
		String avalue=request.getParameter("avalue");
		//String sedition=new String(request.getParameter("sedition").getBytes("ISO-8859-1"),"UTF-8");
		String software = request.getParameter("softwareName");
		String softwareName = null;
		if(software!=null)	
			softwareName = new String(software.getBytes("ISO-8859-1"),"UTF-8");
		String sdate = (String)request.getSession(true).getAttribute("sdate");	
		String d = request.getParameter("sdate");
		if(d!=null&&!d.equals(""))		sdate= d;		
		
		
		
		//System.out.println("======sdate======  "+sdate+"  ==============");
		UserBean a = (UserBean)request.getSession(true).getAttribute("a");
		String id = a.getID();
		int userid = Integer.parseInt(id);


		
		if(softwareName!=null && softwareName.equals(""))
			softwareName=null;
		Logtest log = new Logtest();

		int classID = 0;		
		if(sclass!=null && !sclass.equals("")){
			classID = Integer.parseInt(sclass);
		}
		
		
		
		
		if(sname!=null && sname.equals("")){
			sname=null;
		}
		
		if(sdate!=null && sdate.equals("")){
			sdate=null;
		}
		
		int trusted = 0;
		if(avalue!=null && !avalue.equals("")){
		trusted = Integer.parseInt(avalue);
		}
		
		//if(sedition==""){
			//sedition=null;
		//}
		
		MappingService ms=new MappingService();
		HistorySearchBean hsearch=new HistorySearchBean();
		hsearch.setsclass(classID);
		hsearch.setsdate(sdate);
		hsearch.setsname(sname);
		hsearch.setsoftwareName(softwareName);
		hsearch.setavalue(trusted);
		
		ArrayList c=ms.ListHistory(classID, sname,trusted,softwareName,userid,sdate);
		//HashMap date=ms.ListDate(classID, sname,trusted,softwareName,userid);
		
		request.getSession(true).setAttribute("MappingCollection",c);
		request.getSession(true).setAttribute("hsearch",hsearch);
		//request.getSession(true).setAttribute("DateCollection",date);
		getServletContext().getRequestDispatcher("/historyintoknowledgelist.jsp?p=1").forward(request,response);
		
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
