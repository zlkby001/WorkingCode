package cn.ac.sklois.imm.mappings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;
import java.sql.*;


public class SearchServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SearchServlet() {
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
		response.setCharacterEncoding("UTF-8");
		
		
		String keyword = new String(request.getParameter("keyword").replaceAll(" ", "").getBytes("ISO-8859-1"),"GBK");//在tomcat下要转码 不然接收到的是乱码。
	      response.setContentType("text/xml; charset=UTF-8");
		
	      //String keyword1=(String)request.getParameter("keyword");
		//String keyword=(String)request.getParameter("keyword").replace(" ", "");
		
	     response.setHeader("Cache-Control", "no-cache");
	     PrintWriter out = response.getWriter();
	     try{
	       out = response.getWriter();
	     xml(keyword,out);
	        }
	     catch(Exception e)
	     {
	     out.print("");
	     }
	     
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
		//改用post吧
		//String keyword = new String(request.getParameter("keyword").replaceAll(" ", "").getBytes("ISO-8859-1"),"GBK");//在tomcat下要转码 不然接收到的是乱码。
		request.setCharacterEncoding("UTF-8");
		String keyword=(String)request.getParameter("keyword").replace(" ", "");  
		response.setContentType("text/xml; charset=UTF-8");
		
		
		//String keyword=(String)request.getParameter("keyword").replace(" ", "");
		
	     response.setHeader("Cache-Control", "no-cache");
	     PrintWriter out = response.getWriter();
	     try{
	       out = response.getWriter();
	     xml(keyword,out);
	        }
	     catch(Exception e)
	     {
	     out.print("");
	     }
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	private void xml(String keyword,PrintWriter out)throws Exception
	{
	   Connection conn = null;
	   Statement stmt=null;
	   ResultSet rs = null;
	   try {
		   Class.forName("com.mysql.jdbc.Driver").newInstance();
		   conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/immdb?user=root&password=sklois&useUnicode=true&characterEncoding=GBK");
		
	      
	        String sql="select * from attidtoname where AttributeName like '%"+keyword+"%' ";
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery(sql);
	    if(rs.next()) //创建xml文件
	    {
	      Element root = new Element("response");
	      Document Doc = new Document(root);
	       Element content = new Element("content");
	      Element name = new Element("name");
	      Element aid = new Element("aid");
	      
	      String AttributeName=rs.getString("AttributeName");
	      String AttributeID=rs.getString("AttributeID");
	      name.setText(AttributeName);
	      aid.setText(AttributeID);
	      content.addContent(name);
	      content.addContent(aid);
	      
	      root.addContent(content);
	     while(rs.next())
	     {
	      content = new Element("content");
	      name = new Element("name");
	      aid = new Element("aid");
	      
	      AttributeName=rs.getString("AttributeName");
	      AttributeID=rs.getString("AttributeID");
	      content.addContent(name.setText(AttributeName));
	      content.addContent(aid.setText(AttributeID));
	      
	      root.addContent(content);
	     }
	     XMLOutputter XMLOut = new XMLOutputter();
	     XMLOut.output(Doc,out);
	     
		try {   
			String sWebRootPath = getServletContext().getRealPath("/");//得到web应用的根。
			String sPath=sWebRootPath+"temp";
			File dir =new File(sPath);
			if (!dir.exists()){
				dir.mkdirs();
			}
			XMLOut.output(Doc, new FileOutputStream(sPath+"\\"+"test.xml"));   
			} catch (FileNotFoundException e) {   
				e.printStackTrace();   
			} catch (IOException e) {   
				e.printStackTrace();   
			} 
	     
	     
	     }
	    else{
	     out.println("无数据");
	    }
	   }catch(SQLException sqle){  
	          sqle.printStackTrace();
	       }
	       finally {
	           try {
	               if (rs != null)
	                   rs.close();
	           } catch(SQLException sqle1){
	               sqle1.printStackTrace();
	           }
	            finally {
	               try {
	                   if (stmt != null)
	                       stmt.close();
	               }catch(SQLException sqle2){
	                  sqle2.printStackTrace();
	               }
	               finally {
	                  if(conn!=null)
	                    try{
	                      conn.close();
	                    }catch(SQLException sqle3){
	                       sqle3.printStackTrace();
	                    }
	              }
	           }
	   
	}
	}
	
	
	

}
