package cn.ac.sklois.imm.mappings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ac.sklois.imm.admin.Logtest;

public class do_importServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public do_importServlet() {
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
		
		MappingService ms=new MappingService();
		ArrayList result=null;
		
		try{  
			String id=(String)request.getSession(true).getAttribute("id");
			if(id==null){
				System.out.println("您的账号长时间未使用，已经掉线，因此添加失败。请重新登录！");	
				request.setAttribute("flag","您的账号长时间未使用，已经掉线，因此添加失败。请重新登录！");
				getServletContext().getRequestDispatcher("/addResult.jsp").forward(request,response);
				return;
			}
			int createID = Integer.parseInt(id);
			String sWebRootPath = getServletContext().getRealPath("/");//得到web应用的根。
			String sPath=sWebRootPath+"temp";//声明上传路径为根目录中的temp目录下
			//如果不存在该目录，则新建一个
			File dir =new File(sPath);
			if (!dir.exists()){
				dir.mkdirs();
			}
			String oid=(String)request.getSession(true).getAttribute("id");
			String filename="mappings_"+oid+".xml";
			
			InputStream   in=request.getInputStream();//从request获取的流
			
			//试验字符集
			String x=(new InputStreamReader(request.getInputStream()).getEncoding());//x=GBK....
			Logtest log = new Logtest();
			log.logger.info(x);
			//BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
			
			File   f=new   File(sPath,filename); 
			//FileOutputStream   o=new   FileOutputStream(f); 
			FileWriter o = new FileWriter(f);  
			
			
			
			int formDataLength = request.getContentLength(); 
			byte b[] = new byte[formDataLength];
			int byteRead = 0;
			int totalBytesRead = 0; 
			
			while(totalBytesRead < formDataLength){
				byteRead = in.read(b,totalBytesRead,formDataLength);
				totalBytesRead += byteRead;
			} 
			
			String tmp=new String(b,"UTF-8");//从UTF8转为Unicode(java默认)
			
			int pos=tmp.indexOf("\r\n");
			String boundary=(String) tmp.subSequence(0, pos);
			
			pos=tmp.indexOf("\r\n",pos+2);//加上前面那个，一共有4个/r/n在正式内容前面
			pos=tmp.indexOf("\r\n",pos+2);
			pos=tmp.indexOf("\r\n",pos+2);
			
			
			int startpos=pos+2;//越过最后定位的/r/n
			
			int endpos=tmp.indexOf(boundary,startpos);
			
			
			tmp=tmp.substring(startpos,endpos);//取到endpos-1的那个字母，这是函数自己实现的。
			
			//o.write(b, startpos, endpos-startpos);//不能用endpos-startpos+1作为长度，否则会把分隔符---------196565中的第一个“-”写在文件里。
			//o.write(tmp);
			//o.close(); 
			FileOutputStream fos = null;   
	        OutputStreamWriter osw = null;   
	        
	        fos = new FileOutputStream(f);   
	        osw = new OutputStreamWriter(fos, "UTF-8");   
	        osw.write(tmp);
	        osw.close();
			
			
			
			in.close(); 
			
			
			result=ms.ImportMappings(sPath+"\\"+filename,createID);
			
			File xmlfile=new File(sPath+"\\"+filename);//创建File对象，并获得要操作的文件路径
			xmlfile.delete();//删除文件		
			
		}  
		catch(IOException   ee){
			System.out.print("上传文件失败！");
		}
		
		request.setAttribute("result",result);
		getServletContext().getRequestDispatcher("/importResult.jsp").forward(request,response);
		
		
		
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
