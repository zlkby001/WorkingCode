package com.ics.usb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.ac.sklois.imm.admin.AdminService;

import com.ics.util.DBConnection;
import com.java1234.util.GridPageBean;
import com.java1234.util.JsonUtil;
import com.java1234.util.ResponseUtil;

public class usbLocalServlet extends HttpServlet {
	String oid = "";

	/**
	 * Constructor of the object.
	 */
	public usbLocalServlet() {
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

		String type = request.getParameter("type");
		if(type==null||type.equals("")){
			type = "all";
		}
		
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
//		GridPageBean  pb = new GridPageBean("30");
//		pb.setStart(start);
//		pb.setLimit(limit);
		
		if(type.equals("all")){
			getUsbList(request,response);
		}
		else if(type.equals("delete")){
			deleteUsb(request,response);
		}
		else if(type.equals("deleteAll")){
			deleteAll(request,response);
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
	
	private void getUsbList(HttpServletRequest request, HttpServletResponse response){		
		oid = request.getParameter("oid");
		AdminService sas=new AdminService();
		//ResultSet list =sas.ResultAlertsDetail(oid,filename,digest,sname,time,ip,pb);
		ResultSet list =sas.ResultUsbLocal(oid);
		
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
		}
	}
	
	private void deleteUsb(HttpServletRequest request, HttpServletResponse response){
		
		String id = request.getParameter("id");
		int lines = 0;
		String sql = "delete from usblocal where id in('"+ id +"')";
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			stmt1 = con.createStatement();
			lines = stmt1.executeUpdate(sql);
			
			JSONObject result=new JSONObject();
			if(lines>0){
				result.put("success", "true");
			}
			else{
				result.put("errorMsg", "删除失败");
			}
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
		request.setAttribute("result", lines);		
	}
	
private void deleteAll(HttpServletRequest request, HttpServletResponse response){
		String tid = request.getParameter("tid");
		int lines = 0;
		String sql = "delete from usblocal where terminalid="+ tid ;
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			stmt1 = con.createStatement();
			lines = stmt1.executeUpdate(sql);
			
			JSONObject result=new JSONObject();
			if(lines>0){
				result.put("success", "true");
			}
			else{
				result.put("errorMsg", "删除失败");
			}
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
		request.setAttribute("result", lines);		
	}
	

}
