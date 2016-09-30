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
				System.out.println("�����˺ų�ʱ��δʹ�ã��Ѿ����ߣ�������ʧ�ܡ������µ�¼��");	
				request.setAttribute("flag","�����˺ų�ʱ��δʹ�ã��Ѿ����ߣ�������ʧ�ܡ������µ�¼��");
				getServletContext().getRequestDispatcher("/addResult.jsp").forward(request,response);
				return;
			}
			int createID = Integer.parseInt(id);
			String sWebRootPath = getServletContext().getRealPath("/");//�õ�webӦ�õĸ���
			String sPath=sWebRootPath+"temp";//�����ϴ�·��Ϊ��Ŀ¼�е�tempĿ¼��
			//��������ڸ�Ŀ¼�����½�һ��
			File dir =new File(sPath);
			if (!dir.exists()){
				dir.mkdirs();
			}
			String oid=(String)request.getSession(true).getAttribute("id");
			String filename="mappings_"+oid+".xml";
			
			InputStream   in=request.getInputStream();//��request��ȡ����
			
			//�����ַ���
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
			
			String tmp=new String(b,"UTF-8");//��UTF8תΪUnicode(javaĬ��)
			
			int pos=tmp.indexOf("\r\n");
			String boundary=(String) tmp.subSequence(0, pos);
			
			pos=tmp.indexOf("\r\n",pos+2);//����ǰ���Ǹ���һ����4��/r/n����ʽ����ǰ��
			pos=tmp.indexOf("\r\n",pos+2);
			pos=tmp.indexOf("\r\n",pos+2);
			
			
			int startpos=pos+2;//Խ�����λ��/r/n
			
			int endpos=tmp.indexOf(boundary,startpos);
			
			
			tmp=tmp.substring(startpos,endpos);//ȡ��endpos-1���Ǹ���ĸ�����Ǻ����Լ�ʵ�ֵġ�
			
			//o.write(b, startpos, endpos-startpos);//������endpos-startpos+1��Ϊ���ȣ������ѷָ���---------196565�еĵ�һ����-��д���ļ��
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
			
			File xmlfile=new File(sPath+"\\"+filename);//����File���󣬲����Ҫ�������ļ�·��
			xmlfile.delete();//ɾ���ļ�		
			
		}  
		catch(IOException   ee){
			System.out.print("�ϴ��ļ�ʧ�ܣ�");
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
