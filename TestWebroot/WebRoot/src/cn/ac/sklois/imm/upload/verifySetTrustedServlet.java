package cn.ac.sklois.imm.upload;
import java.io.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.jspsmart.upload.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.Logtest;
import cn.ac.sklois.imm.mappings.FullInfoBean;
import cn.ac.sklois.imm.mappings.MappingService;


public class verifySetTrustedServlet extends HttpServlet {
	
	 private ServletConfig config;   
		Logtest log = new Logtest();
	  
	 // Init the servlet   
	 final public void init(ServletConfig config) throws ServletException {   
	   this.config = config;   
	 }   
	  
	 final public ServletConfig getServletConfig() {   
	   return config;   
	 } 
	/**
	 * Constructor of the object.
	 */
	public verifySetTrustedServlet() {
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
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
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
		String id=(String)request.getSession(true).getAttribute("id");
		if(id==null){
			System.out.println("您的账号长时间未使用，已经掉线，因此添加失败。请重新登录！");	
			request.setAttribute("flag","您的账号长时间未使用，已经掉线，因此添加失败。请重新登录！");
			getServletContext().getRequestDispatcher("/VerifyFailResult.jsp").forward(request,response);
			return;
		}
		for(int i=0;i<YesNo.length;i++){
			FullInfoBean fb = new FullInfoBean();
			int aid=Integer.parseInt(request.getParameter("aid"+YesNo[i]));
			MappingService ms=new MappingService();
			VerifyDigestBean mb=ms.getVerifyInfobyID(aid);
			if(mb.getTrusted()==2)
			{
				fb.setFileName(mb.getName());
//				fb.setSoftwareName("基础数据");
				fb.setSoftwareName(mb.getSoftware());
				fb.setDigest(mb.getDigest());
				fb.setClassID(4);
				fb.setTrusted(1);
				fb.setCreateID(Integer.parseInt(id));
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
				String sissuetime = df.format(new Date());
				fb.setIssueTime(sissuetime);
				StringBuffer strBuf = new StringBuffer();
				boolean r=ms.AddInfo(fb);
				if(r==false)
				{
					res = false;
					mes.append("软件名："+mb.getName()+" 哈希值："+mb.getDigest()+" 添加出错！请检查数据库中是否存在此记录！<br>");

					log.logger.info(mes);
				}
				else
				{
					r = ms.DeleteVerifyFailInfo(aid);
					if(r==false)
					{
						res = false;
						log.logger.info("软件名："+mb.getName()+" 哈希值："+mb.getDigest()+" 删除出错！\n");
//						mes.append("软件名："+mb.getName()+" 哈希值："+mb.getDigest()+" 删除出错！<br>");
					}
				}
			}
			else if(mb.getTrusted()==0)
			{
				int digestID = ms.getIDByNameHashSN(mb.getName(),mb.getDigest(),mb.getSoftware());
				boolean r = ms.ModifyTustedbyID(digestID);
				if(r==false)
				{
					log.logger.info("软件名："+mb.getName()+" 哈希值："+mb.getDigest()+" 更新出错！\n");
//					mes.append("软件名："+mb.getName()+" 哈希值："+mb.getDigest()+" 更新出错！<br>");
					res = false;
				}
				else
				{
					r = ms.DeleteVerifyFailInfo(aid);
					if(r==false)
					{
						res = false;
						log.logger.info("软件名："+mb.getName()+" 哈希值："+mb.getDigest()+" 删除出错！\n");
//						mes.append("软件名："+mb.getName()+" 哈希值："+mb.getDigest()+" 删除出错！<br>");
					}
				}
			}
		}
		if(res == true)
			request.setAttribute("flag", "操作成功！");
		else
			request.setAttribute("flag", "操作成功！<br> 操作失败记录存储在日志中，请查看！");
		
		getServletContext().getRequestDispatcher("/VerifyFailResult.jsp").forward(request,response);
	
		        
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
