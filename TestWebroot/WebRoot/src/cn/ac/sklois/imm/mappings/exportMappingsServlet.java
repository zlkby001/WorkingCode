package cn.ac.sklois.imm.mappings;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.Logtest;

public class exportMappingsServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public exportMappingsServlet() {
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
		String filepath = request.getParameter("filepath");
		Logtest log = new Logtest();
		log.logger.info("**********filepath:"+filepath+"**************");
		MappingService ms=new MappingService();
		
//		String sWebRootPath = getServletContext().getRealPath("/");//得到web应用的根。
//		String sPath=sWebRootPath+"download";
//		//如果不存在该目录，则新建一个
//		File dir =new File(sPath);
//		if (!dir.exists()){
//			dir.mkdirs();
//		}
//		String oid=(String)request.getSession(true).getAttribute("id");
//		String filename="mappings_"+oid+".txt";
//		
//		String fullFileName=sPath+"\\"+filename;
		String fullFileName=filepath;
		ArrayList c=new ArrayList();
		String YesNo[]=request.getParameterValues("YesNo");
		for(int i=0;i<YesNo.length;i++){
			int aid=Integer.parseInt(request.getParameter("aid"+YesNo[i]));
//			String avalue=request.getParameter("avalue"+YesNo[i]);
//			String hash=request.getParameter("hash"+YesNo[i]);
			FullInfoBean mb=ms.getMappingbyID(aid);
			c.add(mb);			
		}
		
		ms.ExportMappings(fullFileName, c);
		
		
		
		
		
		
		
////		String address="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/download/"+filename;
//		System.out.print(address);
		request.setAttribute("address",null);

		getServletContext().getRequestDispatcher("/downloadMappings.jsp").forward(request,response);
		
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
