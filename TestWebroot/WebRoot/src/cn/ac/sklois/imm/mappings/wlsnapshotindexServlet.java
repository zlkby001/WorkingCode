package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ics.util.DBConnection;
import com.java1234.util.ResponseUtil;

public class wlsnapshotindexServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public wlsnapshotindexServlet() {
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
		int userid=Integer.parseInt(request.getParameter("oid"));
		//request.getSession(true).setAttribute("userid", userid);
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			ResultSet rs1 = null;
			stmt1 = con.createStatement();
			String sql="select count(*) as count from wlsnapshotindex where userid="+userid;
			rs1=stmt1.executeQuery(sql);
			int count=0;
			if (rs1.next()) {
				count = rs1.getInt("count");
			}
			sql="select id,description,issuedate from wlsnapshotindex where userid="+userid;
			rs1=stmt1.executeQuery(sql);
			ResponseUtil.writeJson(response, rs1, count);
			rs1.close();
			stmt1.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
