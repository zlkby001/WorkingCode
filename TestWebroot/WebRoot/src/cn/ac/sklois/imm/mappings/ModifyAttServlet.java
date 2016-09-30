package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.AdminBean;
import cn.ac.sklois.imm.admin.AdminService;

public class ModifyAttServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ModifyAttServlet() {
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

		request.setCharacterEncoding("UTF-8");
		
		String aidstr=(String) request.getParameter("aid");
		
		String aname=(String)request.getParameter("aname");
		String aclass=(String)request.getParameter("aclass");
		
		
		
		int aid=Integer.parseInt(aidstr);
		
		AttIDtoName a=new AttIDtoName();
		a.setAttributeID(aid);
		a.setAttributeName(aname);
		a.setAttributeClass(aclass);
		
		
		MappingService ms=new MappingService();
		boolean i=ms.modifyAttID(a);
		
		if(i){
			request.setAttribute("result","修改成功  !");
			
		}else {
			request.setAttribute("result","操作失败，请联系系统维护人员！");
		}
		request.getRequestDispatcher("../modifyAttResult.jsp").forward(request,response);
	
		
		
		
		
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
