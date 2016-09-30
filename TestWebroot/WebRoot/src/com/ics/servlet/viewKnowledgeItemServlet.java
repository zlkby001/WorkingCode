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

import cn.ac.sklois.imm.admin.databasebean;

import com.ics.util.DBConnection;
import com.java1234.util.ResponseUtil;

public class viewKnowledgeItemServlet extends HttpServlet {
	String type,start,limit,ics_name,pname,os,ptype;
	int ics_id=0;
	String sql="";
	Connection con;
	Statement stmt2;

	/**
	 * Constructor of the object.
	 */
	public viewKnowledgeItemServlet() {
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
		
		type = request.getParameter("type");
		pname = request.getParameter("name");
		os = request.getParameter("os");
		ptype = request.getParameter("software");
		
		start = request.getParameter("start");
		limit = request.getParameter("limit");
		if (limit == null || start == null || limit.equals("")
				|| start.equals("")) {
			start = "0";
			limit = "20";
		}
		
		ics_name = request.getParameter("ics_name");
		ics_name = URLDecoder.decode(ics_name,"UTF-8");
		ics_id = Integer.parseInt(request.getParameter("ics_id"));
		
		if(type!=null && type.equals("all"))
			request.getSession(true).setAttribute("KnowledgeSearchType", null);
		
		con = DBConnection.getConnection();
		if(databasebean.ifmysql)
			doMysql(request,response);
		else
			doBeyondDB(request,response);
		
		try {
			//Statement stmt1 = null;
			ResultSet rs1 = null;
			//stmt1 = con.createStatement();
			
			
			String sqlCount = "";
			/*
			if(type.equals("known")){
				sqlCount = "SELECT count(*) as count from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name and a.software_ver=b.software_ver "  //modified by zhyjun at 2015/1/28
						+ getSearchData()
						+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_id="+ ics_id +") and software_info != 'UNKNOWN' ";
			}
			else if(type.equals("unknown")){
				sqlCount = "SELECT count(*) as count from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name and a.software_ver=b.software_ver "
						+ getSearchData()
						+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_id="+ ics_id +") and software_info = 'UNKNOWN' ";
			}
			else{			
				sqlCount = "SELECT count(*) as count"
					+" from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name and a.software_ver=b.software_ver "
					+ getSearchData()
					+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_id="+ ics_id +")";
			}
			*/
			
			//modified by zhyjun at 2015.5.10
			if(type.equals("known")){
				sqlCount = "SELECT count(*) as count from knowledge_item a where a.ics_id= "+ics_id  //modified by zhyjun at 2015/1/28
						+ getSearchData()
						+ " and software_info != 'UNKNOWN' ";
			}
			else if(type.equals("unknown")){
				sqlCount = "SELECT count(*) as count from knowledge_item a where a.ics_id= "+ics_id
						+ getSearchData()
						+ " and software_info = 'UNKNOWN' ";
			}
			else{			
				sqlCount = "SELECT count(*) as count from knowledge_item a where a.ics_id= "+ics_id
					+ getSearchData();
			}
			
			stmt2 = con.createStatement();
			ResultSet rs2 = null;
			rs2 = stmt2.executeQuery(sqlCount);
			int count = 0;
			if (rs2.next()) {
				count = rs2.getInt("count");
			}
			
			rs1 = stmt2.executeQuery(sql);
			
			ResponseUtil.writeJson(response, rs1, count);
			rs2.close();

			rs1.close();
			//stmt1.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// request.setAttribute("name", name);
		// request.getRequestDispatcher("showKnowledge.jsp").forward(request,
		// response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	//modified by zhyjun at 2015.5.10
	private void doMysql(HttpServletRequest request, HttpServletResponse response){
		if(ics_name==null){
			ics_name = "";
		}
		
		sql = "SELECT * from knowledge_item a where a.ics_id = "+ics_id
				+ getSearchData()
				+ " limit "
				+ start + "," + limit;
		//System.out.print("=== viewKnowledge sql ===  "+ sql +"  ==============");
		
		if(type==null){
			//当type参数为Null时，当前session状态可能有三种，对应三种不同方式处理分页数据
			String sessionType = (String) request.getSession(true).getAttribute("KnowledgeSearchType");
			if(sessionType == null){
				type="";
			}
			else if(sessionType.equals("known")){
				type = "known";
				sql = "SELECT * from knowledge_item a where a.ics_id = "+ics_id
						+ getSearchData()
						+ " and software_info != 'UNKNOWN' limit "
				+ start + "," + limit;
			}
			else if(sessionType.equals("unknown")){
				type = "unknown";
				sql = "SELECT * from knowledge_item a where a.ics_id = "+ics_id
						+ getSearchData()
						+ "  and software_info = 'UNKNOWN' limit "
				+ start + "," + limit;
			}
		}	
		else if(type.equals("known"))
		{
			request.getSession(true).setAttribute("KnowledgeSearchType", "known");
			sql = "SELECT * from knowledge_item a where a.ics_id = "+ics_id
					+ getSearchData()
					+ "  and software_info != 'UNKNOWN' limit "
				+ start + "," + limit;
		}else if(type.equals("unknown")){
			request.getSession(true).setAttribute("KnowledgeSearchType", "unknown");
			sql = "SELECT * from knowledge_item a where a.ics_id = "+ics_id
					+ getSearchData()
					+ " and software_info = 'UNKNOWN' limit "
				+ start + "," + limit;
			
		}
		
	}
 
	
	//doMysql modified by zhyjun at 2015/1/28
	/*
	private void doMysql(HttpServletRequest request, HttpServletResponse response){
		if(ics_name==null){
			ics_name = "";
		}
		
		sql = "SELECT * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name and a.software_ver=b.software_ver "
				+ getSearchData()
				+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_id="+ ics_id +") limit "
				+ start + "," + limit;
		//System.out.print("=== viewKnowledge sql ===  "+ sql +"  ==============");
		
		if(type==null){
			//当type参数为Null时，当前session状态可能有三种，对应三种不同方式处理分页数据
			String sessionType = (String) request.getSession(true).getAttribute("KnowledgeSearchType");
			if(sessionType == null){
				type="";
			}
			else if(sessionType.equals("known")){
				type = "known";
				sql = "SELECT * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name and a.software_ver=b.software_ver "
						+ getSearchData()
						+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_id="+ ics_name +") and software_info != 'UNKNOWN' limit "
				+ start + "," + limit;
			}
			else if(sessionType.equals("unknown")){
				type = "unknown";
				sql = "SELECT * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name and a.software_ver=b.software_ver "
						+ getSearchData()
						+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_id="+ ics_id +") and software_info = 'UNKNOWN' limit "
				+ start + "," + limit;
			}
		}	
		else if(type.equals("known"))
		{
			request.getSession(true).setAttribute("KnowledgeSearchType", "known");
			sql = "SELECT * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name and a.software_ver=b.software_ver "
					+ getSearchData()
					+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_id="+ ics_id +") and software_info != 'UNKNOWN' limit "
				+ start + "," + limit;
		}else if(type.equals("unknown")){
			request.getSession(true).setAttribute("KnowledgeSearchType", "unknown");
			sql = "SELECT * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name and a.software_ver=b.software_ver "
					+ getSearchData()
					+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_id="+ ics_id +") and software_info = 'UNKNOWN' limit "
				+ start + "," + limit;
			
		}
		
	}
	*/
	
	/*
	private void doMysql(HttpServletRequest request, HttpServletResponse response){
		if(ics_name==null){
			ics_name = "";
		}
		
		sql = "SELECT * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
				+ getSearchData()
				+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') limit "
				+ start + "," + limit;
		//System.out.print("=== viewKnowledge sql ===  "+ sql +"  ==============");
		
		if(type==null){
			//当type参数为Null时，当前session状态可能有三种，对应三种不同方式处理分页数据
			String sessionType = (String) request.getSession(true).getAttribute("KnowledgeSearchType");
			if(sessionType == null){
				type="";
			}
			else if(sessionType.equals("known")){
				type = "known";
				sql = "SELECT * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
						+ getSearchData()
						+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info != 'UNKNOWN' limit "
				+ start + "," + limit;
			}
			else if(sessionType.equals("unknown")){
				type = "unknown";
				sql = "SELECT * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
						+ getSearchData()
						+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info = 'UNKNOWN' limit "
				+ start + "," + limit;
			}
		}	
		else if(type.equals("known"))
		{
			request.getSession(true).setAttribute("KnowledgeSearchType", "known");
			sql = "SELECT * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
					+ getSearchData()
					+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info != 'UNKNOWN' limit "
				+ start + "," + limit;
		}else if(type.equals("unknown")){
			request.getSession(true).setAttribute("KnowledgeSearchType", "unknown");
			sql = "SELECT * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
					+ getSearchData()
					+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info = 'UNKNOWN' limit "
				+ start + "," + limit;
			
		}
		
	}*/
	
	
	private void doBeyondDB(HttpServletRequest request, HttpServletResponse response){
		if(ics_name==null){
			ics_name = "";
		}
		
		if(start.equals("0")){
			
			sql = "SELECT first "+ limit +" * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
					+ getSearchData()
					+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') order by a.process_name ";
			
			if(type==null){
				//当type参数为Null时，当前session状态可能有三种，对应三种不同方式处理分页数据
				String sessionType = (String) request.getSession(true).getAttribute("KnowledgeSearchType");
				if(sessionType == null){
					type="";
				}
				else if(sessionType.equals("known")){
					type = "known";
					sql = "SELECT first "+ limit +" * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
							+ getSearchData()
							+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info != 'UNKNOWN'  order by a.process_name ";
				}
				else if(sessionType.equals("unknown")){
					type = "unknown";
					sql = "SELECT first "+ limit +" * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
							+ getSearchData()
							+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info = 'UNKNOWN'  order by a.process_name ";
				}
			}		
			else if(type.equals("known"))
			{
				request.getSession(true).setAttribute("KnowledgeSearchType", "known");
				sql = "SELECT first "+ limit +" * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
						+ getSearchData()
						+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info != 'UNKNOWN'  order by a.process_name ";
			}else if(type.equals("unknown")){
				request.getSession(true).setAttribute("KnowledgeSearchType", "unknown");
				sql = "SELECT first "+ limit +" * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
						+ getSearchData()
						+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info = 'UNKNOWN'  order by a.process_name ";
				
			}
		} //end of if-0
		else{
			sql = "SELECT first "+ start +" * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
					+ getSearchData()
					+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') order by a.process_name ";
			
			if(type==null){
				//当type参数为Null时，当前session状态可能有三种，对应三种不同方式处理分页数据
				String sessionType = (String) request.getSession(true).getAttribute("KnowledgeSearchType");
				if(sessionType == null){
					type="";
				}
				else if(sessionType.equals("known")){
					type = "known";
					sql = "SELECT first "+ start +" * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
							+ getSearchData()
							+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info != 'UNKNOWN'  order by a.process_name ";
				}
				else if(sessionType.equals("unknown")){
					type = "unknown";
					sql = "SELECT first "+ start +" * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
							+ getSearchData()
							+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info = 'UNKNOWN'  order by a.process_name ";
				}
			}		
			else if(type.equals("known"))
			{
				request.getSession(true).setAttribute("KnowledgeSearchType", "known");
				sql = "SELECT first "+ start +" * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
						+ getSearchData()
						+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info != 'UNKNOWN'  order by a.process_name ";
			}else if(type.equals("unknown")){
				request.getSession(true).setAttribute("KnowledgeSearchType", "unknown");
				sql = "SELECT first "+ start +" * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
						+ getSearchData()
						+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info = 'UNKNOWN'  order by a.process_name ";
				
			}
//			Statement statement2;
			String pname="";
			try {
				//Connection connection = DBConnection.getConnection();
				stmt2 = con.createStatement();
				ResultSet tmp = stmt2.executeQuery(sql);
				int mystart=Integer.parseInt(start);
				while(mystart>0)
				{
					tmp.next();
					mystart--;
				}
				//tmp.last();
				{
					pname=tmp.getString("process_name");
				}
				tmp.close();
				stmt2.close();
			} catch (Exception e) {				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(type==null||type.equals("")){
				sql = "SELECT first "+ start +" * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
						+ getSearchData()
						+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and a.process_name>'"+ pname +"' order by a.process_name ";
			}		
			else if(type.equals("known"))
			{
				request.getSession(true).setAttribute("KnowledgeSearchType", "known");
				sql = "SELECT first "+ start +" * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
						+ getSearchData()
						+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info != 'UNKNOWN' and a.process_name>'"+ pname +"'  order by a.process_name ";
			}else if(type.equals("unknown")){
				request.getSession(true).setAttribute("KnowledgeSearchType", "unknown");
				sql = "SELECT first "+ start +" * from knowledge_extension a LEFT JOIN knowledge_basic b on a.process_name = b.process_name "
						+ getSearchData()
						+ " WHERE b.hash_value in (SELECT hash_value FROM knowledge_recommended WHERE ics_name='"+ ics_name +"') and software_info = 'UNKNOWN' and a.process_name>'"+ pname +"'  order by a.process_name ";
				
			}
		}
		
	}
	
	//modified by zhyjun at 2015.5.10
	private String getSearchData()
	{
		String data = "";
		if(pname!=null&&!pname.equals(""))
			data += " and a.process_name like '%"+ pname + "%'";
		if(ptype!=null&&!ptype.equals(""))
			data += " and a.ic_bool like '%"+ ptype + "%'";
		if(os!=null&&!os.equals(""))
			data += " and a.os_ver like '%"+ os + "%' ";  //b.os_ver 改为 a.os_ver
		
		return data;
	}
	
	/*
	private String getSearchData()
	{
		String data = "";
		if(pname!=null&&!pname.equals(""))
			data += " and a.process_name like '%"+ pname + "%'";
		if(ptype!=null&&!ptype.equals(""))
			data += " and a.ic_bool like '%"+ ptype + "%'";
		if(os!=null&&!os.equals(""))
			data += " and b.os_ver like '%"+ os + "%' ";
		
		return data;
	}
	*/
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
