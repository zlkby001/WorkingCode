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

public class auditListDetailServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public auditListDetailServlet() { 
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
	//	response.setContentType("text/html;charset=utf-8");   
		
		request.setCharacterEncoding("UTF-8");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		if (limit == null || start == null || limit.equals("")
				|| start.equals("")) {
			start = "0";
			limit = "30";
		}

		String oid = request.getParameter("oid");
		String act = request.getParameter("action");
		String role = request.getParameter("role");
		String user = request.getParameter("user");
		
		String time = request.getParameter("time");
		String type = request.getParameter("type");
		//System.out.println("servletaction before-----"+act );
		String role1;
		if(role==null||role.equals("")||role.equals("null"))
			role1=null;
		else
			role1=new String(role.getBytes("ISO-8859-1"),"UTF-8");
	
		String user1;
		if(user==null||user.equals("")||user.equals("null"))
			user1=null;
		else
			user1=new String(user.getBytes("ISO-8859-1"),"UTF-8");
			
	
		
		String time1;
		if(time==null||time.equals("")||time.equals("null"))
			time1=null;
		else
			time1=new String(time.getBytes("ISO-8859-1"),"UTF-8");
		String action;

		
		if(act==null||act.equals("")||act.equals("null"))
			action=null;
		else
			action=new String(act.getBytes("ISO-8859-1"),"UTF-8");
		String type1;
		if(type==null||type.equals("")||type.equals("null"))
			type1=null;
		else
			type1=new String(type.getBytes("ISO-8859-1"),"UTF-8");
		//	System.out.println("servletaction-----"+action);
//		System.out.println("=== 参数详情: ====   "
//				+ "\noid:"+ oid 
//				+ "\nfilename:"+ filename 
//				+ "\ndigest:"+ digest 
//				+ "\nsname:"+ sname 
//				+ "\ntime:"+ time 
//				+ "\nip:"+ ip 
//				+ "\nstart:"+ start 
//				+ "\nlimit:"+ limit 
//				+ "\n#######################");
//		if(action==""){
//			action=null;
//		}
		GridPageBean  pb = new GridPageBean("30");
		pb.setStart(start);
		pb.setLimit(limit);
		
		AdminService sas=new AdminService();
		ResultSet list =sas.ResultAuditDetail(oid,action,role1,user1,time1,type1,pb);
		String id = (String) request.getSession(true).getAttribute("id");

		try {
			//con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(list);
			int total=jsonArray.size(); 	//数据表行数
			if(id.equalsIgnoreCase("1000")){
			result.put("rows", jsonArray);
			result.put("total", pb.getTotal());
			
			}
			
		
			else{
				//request.getRequestDispatcher("../result2.jsp").forward(request, response);
				result.put("error", "无超级管理员权限");
			}
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		
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
