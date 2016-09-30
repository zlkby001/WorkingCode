package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.audit;
import cn.ac.sklois.imm.admin.loginservlet;

public class revokeMappingServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public revokeMappingServlet() {
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
		String aidstr=request.getParameter("aid");
		String id = (String) request.getSession(true).getAttribute("id");
		int id1 = Integer.parseInt(id);
		int aid=Integer.parseInt(aidstr);
//		String avalue=request.getParameter("avalue");
//		String hash=request.getParameter("hash");
//		String aname=request.getParameter("aname");
		
//		Attribute att=new Attribute();
//		att.setAttributeID(aid);
//		att.setAttributeValue(avalue);
		
		if (aidstr==null){
			System.out.println("ɾ��ʧ�ܣ�");		
		}else{
			MappingService ms=new MappingService();
			
			boolean b=ms.DeleteInfo(aid);
			
			if(b){
				// audit.log_record(id1, loginservlet.usertype, loginservlet.name1, "删除白名单", "远程日志单个进程删除完成");
				System.out.println("成功");	
				request.setAttribute("flag","true");
				
			}else{
				System.out.println("ɾ��ʧ�ܣ�");	
				request.setAttribute("flag","false");
			}
			
			getServletContext().getRequestDispatcher("/RevokeMappingResult.jsp").forward(request,response);
		}
		
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
