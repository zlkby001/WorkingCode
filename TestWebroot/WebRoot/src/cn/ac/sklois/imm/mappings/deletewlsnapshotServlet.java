package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.ac.sklois.imm.admin.AdminsDBBean;
import cn.ac.sklois.imm.admin.UserBean;

import com.ics.util.DBConnection;
import com.java1234.util.ResponseUtil;

public class deletewlsnapshotServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public deletewlsnapshotServlet() {
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
		int indexid = Integer.parseInt(request.getParameter("indexid"));
		
		
		try {
			
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			stmt1 = con.createStatement();
			String sql="delete from wlsnapshotindex where id="+indexid;
			JSONObject result=new JSONObject();
			try{
				stmt1.executeUpdate(sql);
			}catch (Exception e){
				result.put("errorMsg", "删除失败");
				e.printStackTrace();
			}
			sql="delete from wlsnapshot where indexid="+indexid;
			try{
				stmt1.executeUpdate(sql);
			}catch (Exception e){
				result.put("errorMsg", "删除失败");
				e.printStackTrace();
			}
				result.put("success", "true");
			try {
				ResponseUtil.write(response, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			stmt1.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//request.setAttribute("result", lines);	
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
