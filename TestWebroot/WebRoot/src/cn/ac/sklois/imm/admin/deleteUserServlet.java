package cn.ac.sklois.imm.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java1234.util.ResponseUtil;

import net.sf.json.JSONObject;

public class deleteUserServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public deleteUserServlet() {
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
//		System.out.println("===========  Delete Method   ===============");
//		System.out.println();
		int userid=Integer.parseInt(request.getParameter("oid"));
		boolean b;
		AdminService as=new AdminService();
		b=as.deleteuser(userid);
		JSONObject result=new JSONObject();
		if(b){
			result.put("success", "true");
//			request.setAttribute("result","删除用户成功!");
		}
		else{
			result.put("errorMsg", "删除失败");
//			request.setAttribute("result","删除用户失败!");
		}
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		request.getRequestDispatcher("../deleteUserResult.jsp").forward(request,response);
		System.out.println("===========  Delete Method End "+  request.getAttribute("result") +"  ===============");
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
