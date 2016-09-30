package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.UserBean;

public class ModifyMappingServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ModifyMappingServlet() {
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
		
		UserBean user=new UserBean();
		user=(UserBean)request.getSession(true).getAttribute("a");
		int userid=Integer.parseInt(user.getID());
		String sname=(String)request.getParameter("sname");
		String filename=(String)request.getParameter("filename");
		String sedition=(String)request.getParameter("sedition");
		String sissuetime=(String)request.getParameter("sissuetime");
		String sdescription=(String)request.getParameter("sdescription");
		String sclass=(String)request.getParameter("sclass");
		String shash=(String)request.getParameter("shash");
		String avalue=(String)request.getParameter("avalue");
		
		
		String from=(String)request.getParameter("from");
		String oldaidstr=(String)request.getParameter("oldaid");
		int oldaid=Integer.parseInt(oldaidstr);
		
	
		int sclassID=Integer.parseInt(sclass);
		int trusted=Integer.parseInt(avalue);
		FullInfoBean fb = new FullInfoBean();

		fb.setDigest(shash);		
		fb.setClassID(sclassID);
		fb.setSoftwareName(sname);
		fb.setEdition(sedition);
		fb.setTrusted(trusted);
		fb.setFileName(filename);
		fb.setIssueTime(sissuetime);

		if(null == sdescription)
			fb.setDescription("");
		else
			fb.setDescription(sdescription);
		

        String id=(String)request.getSession(true).getAttribute("id");
		if(id==null){
			System.out.println("����˺ų�ʱ��δʹ�ã��Ѿ����ߣ�������ʧ�ܡ������µ�¼��");	
		    request.setAttribute("flag","����˺ų�ʱ��δʹ�ã��Ѿ����ߣ�������ʧ�ܡ������µ�¼��");
			getServletContext().getRequestDispatcher("/addResult.jsp").forward(request,response);
			return;
		}
		int oid=Integer.parseInt(id);
        
//		Operation c=new Operation();
//		c.setOperationTime(ts);
//		c.setOperatorID(oid);
//		mb.setCreation(c);
//		
//		Operation m=new Operation();
//		m.setOperationTime(ts);
//		m.setOperatorID(oid);
//		mb.setModification(m);
		
		
		
		MappingService ms=new MappingService();
		boolean i=false;
		if(from.equals("history"))
			i=ms.ModifyMappingbyID(oldaid,fb);
		else
			if(from.equals("white"))
				i=ms.ModifyWhitebyID(oldaid,fb,userid);
		if(i){
			if(from.equals("history"))
				request.setAttribute("result","修改历史记录成功!");
			else
				request.setAttribute("result", "更新白名单成功");
			
		}else {
			if(from.equals("history"))
				request.setAttribute("result","修改历史记录失败!");
			else
				request.setAttribute("result", "更新白名单失败");
		}
		request.getRequestDispatcher("../modifyMappingResult.jsp").forward(request,response);
	
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
