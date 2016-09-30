package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.Logtest;

public class listMappingServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public listMappingServlet() {
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
		String sname=request.getParameter("sname");
		String avalue=request.getParameter("avalue");
		String sedition=request.getParameter("sedition");
		String softwareName = request.getParameter("softwareName");
		if(softwareName=="")
			softwareName=null;
		Logtest log = new Logtest();

		int classID = 0;
		
		classID = Integer.parseInt(sclass);
		
		if(sname==""){
			sname=null;
		}
		int trusted = Integer.parseInt(avalue);
		
		if(sedition==""){
			sedition=null;
		}
		
		MappingService ms=new MappingService();
		
		
		ArrayList c=ms.ListMappings(classID, sname,trusted,softwareName);
		
		request.getSession(true).setAttribute("MappingCollection",c);
		
		getServletContext().getRequestDispatcher("/MappingList.jsp?p=1").forward(request,response);
		
		
		
/*
		int p=0;
		p=Integer.parseInt(request.getParameter("p"));
		
		MappingsDBBean mdb=new MappingsDBBean();
		int totalnum=mdb.getCountofMappings();
		mdb.destroy();
		
		int n=20;//ÿҳ��ʾ����
		
		int totalpage=totalnum/n+1;
		
		MappingService ms=new MappingService();
		
		ArrayList al=ms.ListMappings((p-1)*n, n);
		
		//��MappingList���ݸ�MappingList.jsp������ʾ���ɡ�
		//request.setAttribute("s","ss");
		request.setAttribute("ML",al);
		request.setAttribute("cp",new Integer(p));
		request.setAttribute("tp",new Integer(totalpage));
		//response.sendRedirect("../PlatformList.jsp");
		try{
			getServletContext().getRequestDispatcher("/MappingList.jsp").forward(request,response);
		}catch (Throwable t){
			getServletContext().log(t.getMessage());
		}
	*/	
		//modified by lh
		
		
		
		
		
		
		
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
		//modified by lh
		request.setCharacterEncoding("UTF-8");
		
		
		String sclass=request.getParameter("sclass");
		String sname=request.getParameter("sname");
		String avalue=request.getParameter("avalue");
		String sedition=request.getParameter("sedition");
		String softwareName = request.getParameter("softwareName");
		if(softwareName=="")
			softwareName=null;
		Logtest log = new Logtest();

		int classID = 0;
		
		classID = Integer.parseInt(sclass);
		
		if(sname==""){
			sname=null;
		}
		int trusted = Integer.parseInt(avalue);
		
		if(sedition==""){
			sedition=null;
		}
		
		MappingService ms=new MappingService();
		
		
		ArrayList c=ms.ListMappings(classID, sname,trusted,softwareName);
		
		request.getSession(true).setAttribute("MappingCollection",c);
		
		getServletContext().getRequestDispatcher("/MappingList.jsp?p=1").forward(request,response);
		
		
		
		
		
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
