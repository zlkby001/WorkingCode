package com.ics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import cn.ac.sklois.imm.admin.AdminService;
import cn.ac.sklois.imm.admin.databasebean;

import com.ics.util.DBConnection;
import com.java1234.util.ResponseUtil;

public class knowledgeListServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public knowledgeListServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		if(type==null)
		{
			getKnowledgeList(request, response);
		}
		else if(type.equals("add"))
		{
			addKnowledge(request, response);
		}
		else if(type.equals("delete"))
		{
			deleteKnowledge(request, response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	private void getKnowledgeList(HttpServletRequest request, HttpServletResponse response)
	{
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		if (limit == null || start == null || limit.equals("")
				|| start.equals("")) {
			start = "0";
			limit = "20";
		}
		String name = request.getParameter("name");
		String os = request.getParameter("os");
		String ver = request.getParameter("ver");
		String des = request.getParameter("des");
		String sql="select * from knowledge";
		String condition=" where id>0 ";
		if(name!=null&&name!="")
			condition+=" and name like '%"+name+"%'";
		if(os!=""&&os!=null)
			condition+=" and os like '%"+os+"%'";
		if(ver!=""&&ver!=null)
			condition+=" and version like '%"+ver+"%'";
		if(des!=""&&des!=null)
			condition+=" and des like '%"+des+"%'";
		sql=sql+condition;
		if(databasebean.ifmysql)
			sql+=" limit "+start+","+limit;

		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			ResultSet rs1 = null;
			stmt1 = con.createStatement();
			rs1 = stmt1.executeQuery(sql);
			String sqlCount = "select COUNT(*) as count  from knowledge ";
			sqlCount = sqlCount+condition;
			Statement stmt2 = con.createStatement();
			;
			ResultSet rs2 = stmt2.executeQuery(sqlCount);
			int count = 0;
			if (rs2.next()) {
				count = rs2.getInt("count");
			}
			ResponseUtil.writeJson(response, rs1, count);
			rs2.close();

			rs1.close();
			stmt1.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addKnowledge(HttpServletRequest request, HttpServletResponse response)
	{
		String name=request.getParameter("name");
		String ver=request.getParameter("ver");
		String os=request.getParameter("os");
		String des=request.getParameter("des");
		Connection con = DBConnection.getConnection();

		int r = 0;
		try {
			
			if(name!=null){
				name = URLDecoder.decode(name,"UTF-8");
			}
			
			if(ver!=null){
				ver = URLDecoder.decode(ver,"UTF-8");
			}
			
			if(os!=null){
				os = URLDecoder.decode(os,"UTF-8");
			}
			
			if(des!=null){
				des = URLDecoder.decode(des,"UTF-8");
			}
			
			String sql = "select COUNT(*) as count from knowledge where name='"+ name +"' and version='"+ver+"' and os='"+os+ "'";
			Statement st1 = con.createStatement();
			ResultSet check = st1.executeQuery(sql);
			int count = 0;
			if (check.next()) {
				count = check.getInt("count");
			}
			if(count>0){
				JSONObject result=new JSONObject();
				result.put("exist", "true");
				ResponseUtil.write(response, result);
				return;				
			}
			
			Statement statement = con.createStatement();
			String strSql = "insert into knowledge (name,version,os,des) VALUES ('"+ name +"','"+ver+"','"+os+"','"+des+"')";
			//System.out.println("===================  "+strSql+"  =======================");
			r = statement.executeUpdate(strSql);
			statement.close();
			con.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		JSONObject result=new JSONObject();
		if(r>0){
			result.put("success", "true");
		}
		else{
			result.put("errorMsg", "添加失败");
		}
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void deleteKnowledge(HttpServletRequest request, HttpServletResponse response)
	{
		String id=request.getParameter("id");
		
		Connection con = DBConnection.getConnection();

		int r = 0;
		int r1 = 0;
		try {
			
			if(id!=null){
				id = URLDecoder.decode(id,"UTF-8");
			}
			Statement statement = con.createStatement();
			String strSql = "DELETE from knowledge where id in('"+ id +"')";
			String itemsql = "delete from knowledge_item where ics_id in('"+ id +"')";  //modified by zhyjun at 2015.5.10
			//String itemsql = "delete from knowledge_recommended where ics_id in('"+ id +"')";
			r = statement.executeUpdate(strSql);
			r1 = statement.executeUpdate(itemsql);
			statement.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject result=new JSONObject();
		if(r>0){
			if(r1>=0){
				result.put("success", "true");
			}
			else{
				result.put("errorMsg", "删除失败");
			}
			//result.put("success", "true");
		}
		else{
			result.put("errorMsg", "删除失败");
		}
		
		
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
