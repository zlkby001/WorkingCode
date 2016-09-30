package cn.ac.sklois.imm.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.java1234.util.DbUtil;
import com.java1234.util.GridPageBean;
import com.java1234.util.JsonUtil;
import com.java1234.util.ResponseUtil;

public class alertListServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public alertListServlet() {
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
		request.setCharacterEncoding("UTF-8");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		if (limit == null || start == null || limit.equals("")
				|| start.equals("")) {
			start = "0";
			limit = "30";
		}

		GridPageBean  pb = new GridPageBean("30");
		pb.setStart(start);
		pb.setLimit(limit);
		
		AdminService sas=new AdminService();
		ResultSet list =sas.ResultAlerts(pb);
		
		Connection con=null;
		DbUtil dbUtil=new DbUtil();
		try {
			//con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(list);
			int total=jsonArray.size(); 	//数据表行数
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
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
