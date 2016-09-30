package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ics.util.DBConnection;
import com.java1234.util.ResponseUtil;

public class wlsnapshotServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public wlsnapshotServlet() {
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
		String sdate=request.getParameter("issuedate");
		String fid = request.getParameter("indexid");
		int count=0;
		

		String name="";
		String digest="";
        name=(String)request.getSession(true).getAttribute("searchname");
		digest=(String)request.getSession(true).getAttribute("searchdigest");
		String start=request.getParameter("start");
		String limit=request.getParameter("limit");
	  
		try {
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			ResultSet rs1 = null;
			ResultSet rs2 = null;
			stmt1 = con.createStatement();
			String sql="";
		    String condition="";
			String sid="";
			if(name!=null&&!name.equals(""))
				condition+=" and lower(process_path) like lower('%"+name+ "%')";
			if(digest!=null&&!digest.equals(""))
				condition+=" and lower(hash_value) like lower('%"+digest+ "%')";
				
			if(fid.equals("null") || fid.equals("")){
				
				sql="select id from wlsnapshot where indexid in (select id from wlsnapshotindex where issuedate='"+sdate+"')";
				sql+=condition;
				rs2=stmt1.executeQuery(sql);
				ArrayList list=new ArrayList();
				while(rs2.next())
				{ 
					list.add(rs2.getInt("id"));
				}
				int tmpstart=Integer.parseInt(start);
				int tmpend=Integer.parseInt(limit)+tmpstart-1;
				if(tmpend>=list.size())
					tmpend = list.size()-1;
				int mystart=(Integer) list.get(tmpstart);
				int myend=(Integer) list.get(tmpend);	
				sql="select count(*) as count from wlsnapshot where indexid in (select id from wlsnapshotindex where issuedate='"+sdate+"')";
				rs1=stmt1.executeQuery(sql);
					
				if (rs1.next()) {
					count = rs1.getInt("count");
				}
				sql="select id,process_name,process_path,hash_value,indexid from wlsnapshot where indexid in (select id from wlsnapshotindex where issuedate='"+sdate+"')";
				sql+=condition;
				sql+=" and id>=" + mystart + " and id <=" +myend;;
			}
			if(sdate.equals("null")||sdate.equals(""))
			{ 
				int indexid=Integer.parseInt(request.getParameter("indexid"));
				sql="select id from wlsnapshot where indexid="+indexid;
				sql+=condition;
				rs2=stmt1.executeQuery(sql);
				ArrayList list=new ArrayList();
				while(rs2.next())
				{
					list.add(rs2.getInt("id"));
				}
				int tmpstart=Integer.parseInt(start);
				int tmpend=Integer.parseInt(limit)+tmpstart-1;
				if(tmpend>=list.size())
					tmpend = list.size()-1;
				int mystart=(Integer) list.get(tmpstart);
				int myend=(Integer) list.get(tmpend);		
						
				sql="select count(*) as count from wlsnapshot where indexid="+indexid;
				sql+=condition;
				rs1=stmt1.executeQuery(sql);
				if (rs1.next()) {
					count = rs1.getInt("count");
				}
				sql="select id,process_name,process_path,hash_value,indexid from wlsnapshot where indexid="+indexid;
				sql+=condition;
				sql+=" and id>=" + mystart + " and id <=" +myend;;
			}
				rs1=stmt1.executeQuery(sql);
				ResponseUtil.writeJson(response, rs1, count);
				rs2.close();
				rs1.close();
				stmt1.close();
				con.close();	
			} catch (SQLException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
/*		if(sdate == null){
				int indexid=Integer.parseInt(request.getParameter("indexid"));
				sql="select id from wlsnapshot where indexid="+indexid;
				sql+=condition;
				rs2=stmt1.executeQuery(sql);
				ArrayList list=new ArrayList();
				while(rs2.next())
				{
					list.add(rs2.getInt("id"));
				}
				int tmpstart=Integer.parseInt(start);
				int tmpend=Integer.parseInt(limit)+tmpstart-1;
				if(tmpend>=list.size())
					tmpend = list.size()-1;
				int mystart=(Integer) list.get(tmpstart);
				int myend=(Integer) list.get(tmpend);		
						
				sql="select count(*) as count from wlsnapshot where indexid="+indexid;
				sql+=condition;
				rs1=stmt1.executeQuery(sql);
				if (rs1.next()) {
					count = rs1.getInt("count");
				}
				sql="select id,process_name,process_path,hash_value,indexid from wlsnapshot where indexid="+indexid;
				sql+=condition;
				sql+=" and id>=" + mystart + " and id <=" +myend;;
			}
		*/
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
