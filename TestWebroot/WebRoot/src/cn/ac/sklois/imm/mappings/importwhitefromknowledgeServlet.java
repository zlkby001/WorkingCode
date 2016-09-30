package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ics.util.DBConnection;
import com.java1234.util.ResponseUtil;

import cn.ac.sklois.imm.admin.AdminsDBBean;
import cn.ac.sklois.imm.admin.UserBean;
import cn.ac.sklois.imm.admin.audit;
import cn.ac.sklois.imm.admin.loginservlet;

public class importwhitefromknowledgeServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public importwhitefromknowledgeServlet() {
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

		String ics_id=request.getParameter("ics_id");
		if(ics_id!=null){
			ics_id = URLDecoder.decode(ics_id,"UTF-8");
		}
		int r=-1;
		UserBean a = (UserBean)request.getSession(true).getAttribute("a");
		int userid = Integer.parseInt(a.getID());
		String pk = a.getpubkey();
		Connection con = DBConnection.getConnection();
		try {
			Statement statement = con.createStatement();
			String strSql="delete from whitelist_content where userid="+userid+" and Hash_Value in (select DISTINCT hash_value from knowledge_item where ics_id="+ics_id+")";
			statement.executeUpdate(strSql);
			strSql="replace into whitelist_content(Hash_Value,Process_Name,Process_Path,TCM_PK,whitelist_vern,userid) select hash_value,process_name,process_path,'"+pk+"',-1,"+userid+" from knowledge_item where ics_id="+ics_id +" and id in (select min(id) from knowledge_item where ics_id="+ics_id+" group by hash_value)";
			r=statement.executeUpdate(strSql);
			statement.close();
			con.close();
		} catch (SQLException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject result=new JSONObject();
		request.setAttribute("result", "导入白名单成功");
		if(r>=0){
			 audit.log_record(userid, loginservlet.usertype, loginservlet.name1, "导入白名单", "知识库加入白名单");
				//result.put("success", "true");
				request.getRequestDispatcher("../result2.jsp").forward(request, response);
		}
		else{
			result.put("errorMsg", "导入失败");
		}
		
		
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
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
