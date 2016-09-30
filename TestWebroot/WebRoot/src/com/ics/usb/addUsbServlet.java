package com.ics.usb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.UserBean;
import cn.ac.sklois.imm.admin.databasebean;
import cn.ac.sklois.imm.mappings.FullWhiteBean;
import cn.ac.sklois.imm.mappings.MappingService;

public class addUsbServlet extends HttpServlet {
	private Connection conn = null;
	private Statement stm = null;
	boolean res = false;

	/**
	 * Constructor of the object.
	 */
	public addUsbServlet() {
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
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String YesNo[]=request.getParameterValues("YesNo");		
		String mid = request.getParameter("uid");
		
		conn = databasebean.getConnection();
		try {
			stm = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(mid==null || mid.equals("")||mid.equals("null"))	{		//多选项
			String mids[]= (String[])request.getSession(true).getAttribute("usbMeasureId");
			//System.out.println("====== 多选项 YesNo: ===== " + mids.length);
			//request.getSession(true).setAttribute("usbid",null);
			
			for(int i=0;i<YesNo.length;i++){
				for(int j=0;j<mids.length;j++){
					addUsb(YesNo[i],mids[j]);
				}
			}
		}
		else{	//单选项
			mid=new String(mid.getBytes("ISO-8859-1"),"UTF-8");
			//System.out.println("====== 单选项 uid: ===== " + mid);
			
			for(int i=0;i<YesNo.length;i++){
				addUsb(YesNo[i],mid);
			}
		}
		
		if(res == true)
			request.setAttribute("result", "添加成功");
		else
			request.setAttribute("result", "操作完成");
		
		request.getRequestDispatcher("../addWhite.jsp").forward(request,response);
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	private void addUsb(String uid,String mid){
		try {

			String strSql;
			strSql="select * from usbmeasure where id =" + mid;	
			String manufacture="",sn="";

			ResultSet rs = stm.executeQuery(strSql);
			if(rs.next()){
				manufacture = rs.getString("manufacture");
				sn = rs.getString("sn");
			}
			
//			strSql="select count(*) as count from usblocal where terminalid='"+ uid +"' and manufacture = '"+ manufacture +"' and sn = '"+ sn +"'";
//			ResultSet rsCheck = stm.executeQuery(strSql);
//			if(rsCheck.next()){
//				return;
//			}
			
			strSql="insert into usblocal (terminalid,manufacture,sn) values ("
						+ uid +",'"+ manufacture +"','"+ sn +"')";	
			stm.executeUpdate(strSql);
			res = true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (e instanceof SQLIntegrityConstraintViolationException) {
				return;
			}
		} 
		
	}

}
