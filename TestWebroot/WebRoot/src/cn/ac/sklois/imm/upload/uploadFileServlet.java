package cn.ac.sklois.imm.upload;
import java.io.*;
import java.io.File;
import java.text.SimpleDateFormat;
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


public class uploadFileServlet extends HttpServlet {
	
	 private ServletConfig config;   
	  
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
	public uploadFileServlet() {
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

		Logtest log = new Logtest();  
		   String resultMessage = "上传成功！"+"<br>";
		   StringBuffer strBuf = new StringBuffer(); 
		   SmartUpload mySmartUpload = new SmartUpload(); 
		   String filename = "";
		   String sPath ="";
		   String name = "";
		   String hash = "";
		   String softwareName = "";
		   //debug   
		   log.logger.info("Created smartupload");   
		  
		   try {   
		     // Initialization   
		     mySmartUpload.initialize(config,request,response);   
		  
		     //debug   
		     log.logger.info("Initialized smartupload");   
		  
		  
		     // Upload   
		     mySmartUpload.upload();   
		  
		     //debug   
		     log.logger.info("Invoked upload method");   
		  
		     if (mySmartUpload.getFiles().getCount() > 0) {   
		  
		       com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(0);   
		       filename=myFile.getFileName(); 

		       //debug   
		       log.logger.info("File name is " + filename);   
				String sWebRootPath = getServletContext().getRealPath("/");//得到web应用的根。
				sPath=sWebRootPath+"upload/";
				log.logger.info("sPath is:"+sPath);
				//如果不存在该目录，则新建一个
				File dir =new File(sPath);
				if (!dir.exists()){
					dir.mkdirs();
				}
		       // Save it only if this file exists   
		       if (!myFile.isMissing()) {   
		         myFile.saveAs("/upload/"+filename, SmartUpload.SAVE_VIRTUAL);   
		       }   
		  
		       //debug   
		       log.logger.info("saved my file");				  
			   }
		   }catch (Exception e) {   
			    resultMessage = "Upload failure: " + e.toString();
				request.setAttribute("flag",resultMessage);
				getServletContext().getRequestDispatcher("/VerifyFailResult.jsp").forward(request,response);
				return;
		   }
	        try {
	            FileReader fr = new FileReader(sPath+filename);//创建FileReader对象，用来读取字符流
	            BufferedReader br = new BufferedReader(fr);    //缓冲指定文件的输入

	            String myreadline;    //定义一个String类型的变量,用来每次读取一行
	            while (br.ready()) {
	                myreadline = br.readLine();//读取一行
	                int hashpos = myreadline.indexOf("=");
	                if(hashpos==-1)
	                {
	    				request.setAttribute("flag","上传文件不正确，请检查！");
	    				getServletContext().getRequestDispatcher("/VerifyFailResult.jsp").forward(request,response);
	                	return;
	                }
	                int RPMpos = myreadline.indexOf("(");
	                int namepos = myreadline.indexOf(")");
	                softwareName = myreadline.substring(0, RPMpos);
	                name = myreadline.substring(RPMpos+1, namepos);
	                hash = myreadline.substring(hashpos+1);
					request.setCharacterEncoding("UTF-8");
					//要查看AttributeID在AttIDtoName表中是否存在。
//					String aidstr=mySmartUpload.getRequest().getParameter("aid");
//					int aid =  Integer.parseInt(aidstr);
					String AttValue=mySmartUpload.getRequest().getParameter("avalue");
					int trusted = Integer.parseInt(AttValue);
					String sclass=mySmartUpload.getRequest().getParameter("sclass");	
					int classID = Integer.parseInt(sclass);
					
//					String sname=request.getParameter("sname");		
//					String HashValue=request.getParameter("hash");
//					String scopyright=mySmartUpload.getRequest().getParameter("scopyright");
//					if(null == scopyright)
//						scopyright = "";
					String sdescription=mySmartUpload.getRequest().getParameter("sdescription");
					if(null == sdescription)
						sdescription = "";
					String sissuetime=mySmartUpload.getRequest().getParameter("sissuetime");
					log.logger.info("sissuetime before change:"+sissuetime);
					if(null == sissuetime||sissuetime=="")
					{	
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
						sissuetime = df.format(new Date());

						log.logger.info("sissuetime after change:"+sissuetime);
					}
//					String smanufacturer=mySmartUpload.getRequest().getParameter("smanufacturer");
//					if(null == smanufacturer)
//						smanufacturer = "";
					String sedition=mySmartUpload.getRequest().getParameter("sedition");
					if(null == sedition)
						sedition = "";
					
					MappingService msname=new MappingService();
					
					
					String id=(String)request.getSession(true).getAttribute("id");
					if(id==null){
						System.out.println("您的账号长时间未使用，已经掉线，因此添加失败。请重新登录！");	
						request.setAttribute("flag","您的账号长时间未使用，已经掉线，因此添加失败。请重新登录！");
						getServletContext().getRequestDispatcher("/VerifyFailResult.jsp").forward(request,response);
						return;
					}
					
//					Date date = new Date();
//			        Timestamp ts = new Timestamp(date.getTime());
					
					
					FullInfoBean fb = new FullInfoBean();
					fb.setFileName(name);
					fb.setSoftwareName(softwareName);
					fb.setDigest(hash);
					fb.setEdition(sedition);
					fb.setClassID(classID);
					fb.setIssueTime(sissuetime);
					fb.setTrusted(trusted);
					fb.setDescription(sdescription);
					fb.setCreateID(Integer.parseInt(id));
					MappingService ms=new MappingService();

					boolean r;
					StringBuffer mes = new StringBuffer(); 
					r=ms.AddInfo(fb);
					if(r==false)
					{
						mes.append("操作失败记录存储在日志中，请查看！");
						strBuf.append(mes.toString()+"<br>");
					}
	                log.logger.info(name+"****"+hash);//在屏幕上输出
	            }
	            br.close();
	            br.close();
	            fr.close();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

			       try  {      
			           String  filePath  =  sPath+filename;          
			           java.io.File  myDelFile  =  new  java.io.File(filePath);
			           log.logger.info("删除文件"+filePath);
			           myDelFile.delete();      
			     
			       }      
			       catch  (Exception  e)  {      
			          log.logger.info("删除文件操作出错");      
			           e.printStackTrace();      
			     
			       }     
			    strBuf.append("<br>操作成功！");

				request.setAttribute("flag",resultMessage+strBuf.toString());

				
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
