package cn.ac.sklois.imm.mappings;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import com.jspsmart.upload.SmartUpload;




import cn.ac.sklois.imm.admin.UserBean;

public class exportAllPagesWhitelistServlet extends HttpServlet {
	private static String outname = null;
	private static File whitelistfile;
	private static String whitefile="C:/TCC/whitelistfile.txt";;
	private static final int BYTE_DWONLOAD=1024;
	private String strEncode="utf-8";
	private ServletConfig config;
	 final public void init(ServletConfig config) throws ServletException {   
		   this.config = config;  
		   DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		   
		 }   
		  
		 final public ServletConfig getServletConfig() {   
		   return config;   
		 } 
	/**
	 * Constructor of the object.
	 */
	public exportAllPagesWhitelistServlet() {
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
		FullWhiteBean fmb=null;
		boolean result1 = false;
		UserBean user=(UserBean)request.getSession(true).getAttribute("a");
		ArrayList al=(ArrayList)request.getSession(true).getAttribute("MappingCollection");
		WhitelistSearchBean wsearch=(WhitelistSearchBean)request.getSession(true).getAttribute("wsearch");
		/*
		int i=0;
		boolean result=true;
		while(i<al.size())
		{
			Object obj=al.get(i);
			fmb=(FullWhiteBean)obj;
			MappingService ms=new MappingService();
			result=ms.DeleteWhiteInfo(fmb.getID());
			i++;
			
		}*/
		
		MappingService ms=new MappingService();
		ArrayList result=ms.ExportWhitelist(wsearch,Integer.parseInt(user.getID()));
		if(result!=null){
			 result1 = true;
		}else{
			result1 = false;
		}
//		if(result){
//			request.setAttribute("result","导出白名单成功");
//	    }else request.setAttribute("result","导出白名单失败!");
		//String[] white = result.toArray(new String[result.size()]);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out =response.getWriter();
		String filename = "whitelist.txt";
		File deskdir= FileSystemView.getFileSystemView().getHomeDirectory();
		String desktoppath=deskdir.getAbsolutePath();
		 String filePath =this.getServletContext().getRealPath(filename);
		 String filePath1 =this.getServletContext().getRealPath("/");
		 File file =new File(desktoppath+"/whitelist.txt");
//		 if(!whitelistfile.exists()){
//			 try {
//				 whitelistfile.createNewFile();
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		 }
		 try {
			   FileWriter fileWriter = new FileWriter(file);
	            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	           for (int i = 0; i < result.size(); i++) {
	        	   FullWhiteBean wsearch1=(FullWhiteBean)(result.get(i));
	   		//	System.out.println(wsearch1.getDigest());
	   		//	bufferedWriter.write((String.valueOf(wsearch1.getFileName()) + " | "+String.valueOf(wsearch1.getDigest())).getBytes("UTF8"));
	   			bufferedWriter.write(String.valueOf(wsearch1.getFileName()) + " | "+String.valueOf(wsearch1.getDigest()));
	   		   bufferedWriter.newLine();  
	           }
	           
	            bufferedWriter.close();
	            fileWriter.close(); 
	            out.println("<font size='2'>文件导出完毕，路径:" +(file.getAbsolutePath()).replaceAll(".txt", ".enc")+"</font>");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 
           
			Encrypt encrypt = new Encrypt();
			outname=filename.replaceAll(".txt", "");
			System.out.println("outname"+outname);
			try {
				encrypt.Encrypt(desktoppath+"/"+filename, desktoppath+"/"+outname);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//file.delete();
           
		/**
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment;filename=testing.text");
		//ServletContext ctx = getServletContext();
		
		
		OutputStream os = null;
		for(int i=0; i<result.size();i++){
			
			FullWhiteBean wsearch1=(FullWhiteBean)(result.get(i));
			System.out.println(wsearch1.getFileName());
			InputStream input = new ByteArrayInputStream((String.valueOf(wsearch1.getFileName()) + " | "+String.valueOf(wsearch1.getDigest())).getBytes("UTF8"));
			int read = 0;
			byte[] bytes = new byte[256];
			byte[] newLine ="\r\n".getBytes();
			 os = response.getOutputStream();
			while((read=input.read(bytes))!=-1){
				os.write(bytes,0,read);
				os.write(newLine);
			}
		
			

		
			
//			bufferedWriter.write(String.valueOf(wsearch1.getFileName()) + " | ");
//			bufferedWriter.write(String.valueOf(wsearch1.getDigest()) + " | ");
//			 bufferedWriter.newLine();  
		}
		os.flush();
		os.close();
		**/
		
		
		

	//	request.getRequestDispatcher("/addWhite.jsp").forward(request,response);
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
