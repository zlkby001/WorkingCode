package cn.ac.sklois.imm.mappings;

import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.Logtest;

public class addMappingServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public addMappingServlet() {
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
		//要查看AttributeID在AttIDtoName表中是否存在。
		
//		String aidstr=request.getParameter("aid");		//软件用途
//		int aid =  Integer.parseInt(aidstr);
		String AttValue=request.getParameter("avalue");   //是否可信
		int trusted = Integer.parseInt(AttValue);
		String sclass=request.getParameter("sclass");	//软件类型
		int classID = Integer.parseInt(sclass);
		
		String sname=request.getParameter("sname");		//软件名
		String HashValue=request.getParameter("hash");	//hash值

		String sdescription=request.getParameter("sdescription");	//描述信息
		if(null == sdescription)
			sdescription = "";
		String sissuetime=request.getParameter("sissuetime");		//发布信息，默认为当前系统时间
		Logtest log = new Logtest();
		log.logger.info("sissuetime before change"+sissuetime);
		if(null == sissuetime||sissuetime=="")
		{		
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			sissuetime = df.format(new Date());
			log.logger.info("sissuetime after change"+sissuetime);
		}
		


//		String smanufacturer=request.getParameter("smanufacturer");	//厂商
//		if(null == smanufacturer)
//			smanufacturer = "";
		String sedition=request.getParameter("sedition");
		if(null == sedition)
			sedition = "";
		String filename = request.getParameter("filename");
		
		MappingService msname=new MappingService();
//		boolean fb=msname.AttNameIsExisted(AttName);
//		if(!fb){//不存在
//			System.out.println("添加失败，不支持该属性名，请重新输入！");	
//			request.setAttribute("flag","添加失败，不支持该属性名，请重新输入！");
//			getServletContext().getRequestDispatcher("/addResult.jsp").forward(request,response);
//			return;
//		}
		
//		AttIDtoName aiton=new AttIDtoName();
//		aiton=msname.getAttbyName(AttName);
		
		
		String id=(String)request.getSession(true).getAttribute("id");
		if(id==null){
			System.out.println("您的账号长时间未使用，已经掉线，因此添加失败。请重新登录！");	
			request.setAttribute("flag","您的账号长时间未使用，已经掉线，因此添加失败。请重新登录！");
			getServletContext().getRequestDispatcher("/addResult.jsp").forward(request,response);
			return;
		}
		
//		int ttt;
//		int oid=Integer.parseInt(id);
//		
//		Date date = new Date();
//        Timestamp ts = new Timestamp(date.getTime());
		
		
		FullInfoBean fb = new FullInfoBean();
		fb.setSoftwareName(sname);
		fb.setDigest(HashValue);
		fb.setEdition(sedition);
		fb.setClassID(classID);
		fb.setIssueTime(sissuetime);
		fb.setTrusted(trusted);
		fb.setDescription(sdescription);
		fb.setCreateID(Integer.parseInt(id));
		fb.setFileName(filename);

		
//		Operation c=new Operation();
//		c.setOperationTime(ts);
//		c.setOperatorID(oid);
//		
//		Operation m=new Operation();
//		m.setOperationTime(ts);
//		m.setOperatorID(oid);
		
		
		MappingService ms=new MappingService();
		StringBuffer strBuf = new StringBuffer();
		boolean r=ms.AddInfo(fb);
		
		if(r==true)
			request.setAttribute("flag","添加成功！");
		else
			request.setAttribute("flag","添加失败！操作失败记录存储在日志中，请查看！");
//		request.setAttribute("flag", strBuf.toString());
		
		getServletContext().getRequestDispatcher("/addResult.jsp").forward(request,response);
		
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
