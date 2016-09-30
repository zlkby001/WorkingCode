package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.UserBean;
import cn.ac.sklois.imm.admin.audit;
import cn.ac.sklois.imm.admin.loginservlet;

public class deleteAllHistorylistServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public deleteAllHistorylistServlet() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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

		request.setCharacterEncoding("UTF-8");
		ArrayList c=new ArrayList();
		String YesNo[]=request.getParameterValues("YesNo");
		boolean res = true;
		StringBuffer mes = new StringBuffer();
		UserBean user=(UserBean)request.getSession(true).getAttribute("a");
		int userid=Integer.parseInt(user.getID());
		String id=(String)request.getSession(true).getAttribute("id");
		if(id==null){
			System.out.println("失败！");
			request.setAttribute("flag","失败！");
			getServletContext().getRequestDispatcher("/VerifyFailResult.jsp").forward(request,response);
			return;
		}
		boolean b;
		boolean result=true;
		for(int i=0;i<YesNo.length;i++){
			FullWhiteBean fb = new FullWhiteBean();
			int aid=Integer.parseInt(request.getParameter("aid"+YesNo[i]));
			MappingService ms=new MappingService();
			b=ms.DeleteInfo(aid);
			if(!b)
				result=false;
			/*
			VerifyDigestBean mb=ms.getVerifyInfobyID(aid);
			if(mb.getTrusted()==2)
			{
				fb.setFileName(mb.getName());
//				fb.setSoftwareName("锟斤拷锟斤拷锟�);
				fb.setSoftwareName(mb.getSoftware());
				fb.setDigest(mb.getDigest());
				fb.setClassID(4);
				fb.setTrusted(1);
				fb.setCreateID(Integer.parseInt(id));
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//锟斤拷锟斤拷锟斤拷锟节革拷式
				String sissuetime = df.format(new Date());
				fb.setIssueTime(sissuetime);
				StringBuffer strBuf = new StringBuffer();
				boolean r=ms.AddInfo(fb);
				if(r==false)
				{
					res = false;
					log.logger.info(mes);
				}
				else
				{
					r = ms.DeleteVerifyFailInfo(aid);
					if(r==false)
					{
						res=false;
					}
				}
			}
			else if(mb.getTrusted()==0)
			{
				int digestID = ms.getIDByNameHashSN(mb.getName(),mb.getDigest(),mb.getSoftware());
				boolean r = ms.ModifyTustedbyID(digestID);
				if(r==false)
				{
					res = false;
				}
				else
				{
					r = ms.DeleteVerifyFailInfo(aid);
					if(r==false)
					{
						res = false;
					}
				}
			}*/
		}
		if(result){
			// audit.log_record(userid, loginservlet.usertype, loginservlet.name1, "删除白名单", "远程日志选中删除完成");
			request.setAttribute("result","删除历史记录成功");
		}else request.setAttribute("result","删除历史记录失败!");
		if(res == true)
			request.setAttribute("flag", "成功");
		else
			request.setAttribute("flag", "失败！");
		
		request.getRequestDispatcher("/addWhite.jsp").forward(request,response);
	
		        
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
