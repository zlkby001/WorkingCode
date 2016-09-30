package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import cn.ac.sklois.imm.admin.AdminsDBBean;
import cn.ac.sklois.imm.admin.UserBean;

import com.ics.util.DBConnection;
import com.java1234.util.ResponseUtil;
import com.mysql.jdbc.ResultSet;

public class recoverwlServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public recoverwlServlet() {
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
		String id = request.getParameter("indexid");
		int userid = Integer.parseInt(request.getParameter("userid"));
	 	String issuedate=request.getParameter("sdate");
		String name=request.getParameter("searchname");
		String digest=request.getParameter("searchdigest");
		String sql = "";
		AdminsDBBean ad=new AdminsDBBean();
		UserBean a=ad.findUserbyID(userid);
		String pk=a.getpubkey();
		if(userid<1)
			return;
		int lines = 0;
		
		try {
			
			Connection con = DBConnection.getConnection();
			Statement stmt1 = null;
			stmt1 = con.createStatement();
			if((id == null)||(id.equals("null"))){
			   sql = "select userid from wlsnapshotindex where id in (select id from wlsnapshotindex where issuedate='"+issuedate+"')";
			}
			else if((issuedate ==null)||(issuedate.equals("null"))||(issuedate.equals("")))
			{
				int indexid = Integer.parseInt(id);
				sql="select userid from wlsnapshotindex where id="+indexid;
			}
			java.sql.ResultSet rs=stmt1.executeQuery(sql);
			int uid=-1;
			if(rs.next())
				uid=rs.getInt("userid");
			else
				return;
			if(uid!=userid)
				return;
			sql = "delete from whitelist_content where whitelist_vern=-1 and userid=" + userid;
			lines = stmt1.executeUpdate(sql);
			if((id == null)||(id.equals("null"))){
			//sql="insert into whitelist_content(tcm_pk,process_name,process_path,hash_value,whitelist_vern,userid) select '" + pk + "',process_name,process_path,hash_value,-1," + userid +" from wlsnapshot where indexid=" + indexid;
			   sql="insert into whitelist_content(tcm_pk,process_name,process_path,hash_value,whitelist_vern,userid) select '" + pk + "',process_name,process_path,hash_value,-1," + userid +" from wlsnapshot where indexid in (select id from wlsnapshotindex where issuedate='"+issuedate+"')";
			}
			else if((issuedate == null)||(issuedate.equals("null"))||(issuedate.equals("")))
			{
				int indexid = Integer.parseInt(id);
				sql="insert into whitelist_content(tcm_pk,process_name,process_path,hash_value,whitelist_vern,userid) select '" + pk + "',process_name,process_path,hash_value,-1," + userid +" from wlsnapshot where indexid=" + indexid;
			}
			if(name!=null&&!name.equals(""))
				sql+=" and lower(process_path) like lower('%"+name+ "%')";
			if(digest!=null&&!digest.equals(""))
				sql+=" and lower(hash_value) like lower('%"+digest+ "%')";
			JSONObject result=new JSONObject();
			try{
				stmt1.executeUpdate(sql);
			}catch (Exception e){
				result.put("errorMsg", "删除失败");
				e.printStackTrace();
			}
			
				result.put("success", "true");
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
