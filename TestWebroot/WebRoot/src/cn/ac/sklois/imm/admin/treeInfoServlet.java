package cn.ac.sklois.imm.admin;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.java1234.util.DbUtil;
import com.java1234.util.JsonUtil;
import com.java1234.util.ResponseUtil;
import com.java1234.util.TreeUtil;


public class treeInfoServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public treeInfoServlet() {
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
		JSONArray jsonArray = null;
		
		String ip=request.getParameter("ip");
		String tmp=request.getParameter("username");
		String verifydate=request.getParameter("verifydate");
		String wldate = request.getParameter("wldate");
		String username;
		if(tmp==null||tmp.equals("")||tmp.equals("null"))
			username=null;
		else
			username=new String(tmp.getBytes("ISO-8859-1"),"UTF-8");
		String pubkey=request.getParameter("pubkey");		

		if(ip==null||ip.equals("")||ip.equals("null"))
			ip=null;
			
		if(pubkey==null||pubkey.equals("")||pubkey.equals("null")){
			pubkey=null;
		}		
		if(verifydate==null||verifydate.equals("")||verifydate.equals("null")){
			verifydate=null;
		}
		if(wldate==null||wldate.equals("")||wldate.equals("null")){
			wldate=null;
		}
		//System.out.println("ip:"+ip);
		String id = (String) request.getSession(true).getAttribute("id");
		TreeUtil tu = new TreeUtil(id);
	
		try {
			jsonArray=new TreeUtil(id).getJsonTree(ip,username,verifydate,wldate,pubkey);
//			result.put("nodes", jsonArray);
//			result.put("total", total);
//			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	
		
//			System.out.println("id"+id);
//			jsonArray.size();
//			System.out.println("jsonArray"+jsonArray.size());
//			System.out.println("jsonArray0"+jsonArray.get(0));
//			System.out.println("-----------------------------------");
//			System.out.println("jsonArray.getJSONObject(1)"+jsonArray.getJSONObject(0));
		
			//System.out.println("jsonArray0"+jsonArray.get(1));
	
		
			//request.getSession(true).setAttribute("id",a.getOperatorID());
		//System.out.println("========   " + jsonArray + "  ==========");	
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
        out.println(jsonArray);
        
   
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
