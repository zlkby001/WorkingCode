package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class addAttServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public addAttServlet() {
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

		String aid=request.getParameter("aid");
		String aname=request.getParameter("aname");
		String aclass=request.getParameter("aclass");
		
		
		
		
		AttIDtoName a=new AttIDtoName();
		a.setAttributeID(Integer.parseInt(aid));
		a.setAttributeName(aname);
		a.setAttributeClass(aclass);
		
		
		
		MappingService ms=new MappingService();
		int r=ms.AddAttID(a);
		
		if(r==0){//�ɹ����
			System.out.println("��ӳɹ���");	
			request.setAttribute("result","��ӳɹ���");
		}else if(r==1){//�����Զ�Ӧ��Mapping�Ѿ�����,����
			System.out.println("������ID�Ѿ�����,���飡");	
			request.setAttribute("result","������ID�Ѿ�����,���飡");
		}else if(r==2){//���ʧ��
			System.out.println("���ʧ�ܣ�");	
			request.setAttribute("result","���ʧ�ܣ�");
		}
		
		getServletContext().getRequestDispatcher("/addAttResult.jsp").forward(request,response);
		
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
