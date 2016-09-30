package cn.ac.sklois.imm.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.PropertyConfigurator;

import com.ics.util.Cryptor;
import com.ics.util.DBConnection;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.mysql.jdbc.ResultSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class uploadknowledgefromxmlServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	private ServletConfig config;
	 final public void init(ServletConfig config) throws ServletException {   
		   this.config = config;   
		 }   
		  
		 final public ServletConfig getServletConfig() {   
		   return config;   
		 } 
	
	public uploadknowledgefromxmlServlet() {
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

		String ics_id=request.getParameter("ics_id");
		//String file=request.getParameter("upfile");
		Logtest log = new Logtest();  
		log.logger.info("the ics_id is"+ics_id);
		Connection con = DBConnection.getConnection();
		try {
			Statement statement = con.createStatement();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		String ics_id=request.getParameter("ics_id");
		int result=0;
		//System.out.println(request.getServletPath());
		//PropertyConfigurator.configure("log4j.properties");
		Logtest log = new Logtest();  
		log.logger.info("the ics_id is"+ics_id);
		SmartUpload mySmartUpload = new SmartUpload(); 
		mySmartUpload.initialize(config,request,response); 
		String filename="";
		String sPath="";
		String decfilename="";
		try {

			mySmartUpload.upload();
			 if (mySmartUpload.getFiles().getCount() > 0) {   
			      com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(0);   
			     filename=myFile.getFileName();
			      log.logger.info(filename);
			      String sWebRootPath = getServletContext().getRealPath("/");
				sPath=sWebRootPath+"upload\\";
				File dir =new File(sPath);
				if (!dir.exists()){
					dir.mkdirs();
				}
		       // Save it only if this file exists   
		       if (!myFile.isMissing()) {   
		         myFile.saveAs("/upload/"+filename, SmartUpload.SAVE_VIRTUAL);   
		       }   
		       Cryptor cryptor=new Cryptor();
		       decfilename=filename.replaceAll(".enc", ".xml");
		       try {
				cryptor.Decrypt(sPath+filename, sPath+decfilename);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("result", "解密知识库文件失败!");
				request.getRequestDispatcher("../addWhite.jsp").forward(request,response);
				return;
			}
			 }
			 else
				 return;
		} catch (SmartUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			request.setAttribute("result", "解析知识库文件失败!");
			request.getRequestDispatcher("../addWhite.jsp").forward(request,response);
			return;
		}
		Connection con = DBConnection.getConnection();
		String fname="";
        String filepath="";
        String hashvalue="";
        String softtype="";
        String filedesp="";
        String company="";
        String fileversion="";
        String ostype="";
		try {
			
			//statement.close();
			File f = new File(sPath+decfilename);
			File originf = new File(sPath+filename);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();

				//String path=sPath+filename;
				Document doc = dbBuilder.parse(f);
				Element root = doc.getDocumentElement();
				//log.logger.info("root node is"+node.getNodeName());
				String r=root.getNodeName();
				if(!root.getNodeName().equals("KnowledgeBase"))
				{
					result=-1;  //xml格式不对
					request.setAttribute("result", "知识库文件格式错误");
				    f.delete();
					originf.delete();
					request.getRequestDispatcher("../addWhite.jsp").forward(request,response);
					return;
				}
				NodeList list = root.getElementsByTagName("KldElement");
				Statement statement = con.createStatement();
				for(int i = 0; i< list.getLength() ; i ++){
	                Node node = list.item(i);
	               
	                Node head =  node.getFirstChild();
	                
	                head=head.getNextSibling();
	                if(!head.getNodeName().equals("filename"))
	                {
	                	result=-1;
	                	break;
	                }
	                fname=head.getFirstChild().getNodeValue().trim();
	                head= head.getNextSibling();
	                
	                head= head.getNextSibling();
	                if(!head.getNodeName().equals("filepath"))
	                {
	                	result=-1;
	                	break;
	                }
	                filepath=head.getFirstChild().getNodeValue().trim();
	                char s[]=filepath.toCharArray();
	                int index;
	                for(index=0;index<s.length;index++)
	                	if(s[index]=='\\')
	                		s[index]='/';
	                filepath=String.valueOf(s);
	                filepath=filepath+"/"+fname;
	                //filepath.replaceAll("\\\\", "x");
	                head= head.getNextSibling();
	                
	                head= head.getNextSibling();
	                if(!head.getNodeName().equals("hashvalue"))
	                {
	                	result=-1;
	                	break;
	                }
	                hashvalue=head.getFirstChild().getNodeValue().trim();
	                head= head.getNextSibling();
	                
	                head= head.getNextSibling();
	                if(!head.getNodeName().equals("softtype"))
	                {
	                	result=-1;
	                	break;
	                }
	                softtype=head.getFirstChild().getNodeValue().trim();
	                head= head.getNextSibling();
	                
	                head= head.getNextSibling();
	                if(!head.getNodeName().equals("filedesp"))
	                {
	                	result=-1;
	                	break;
	                }
	                filedesp=head.getFirstChild().getNodeValue().trim();
	                head= head.getNextSibling();

	                head= head.getNextSibling();
	                if(!head.getNodeName().equals("company"))
	                {
	                	result=-1;
	                	break;
	                }
	                company=head.getFirstChild().getNodeValue().trim();
	                head= head.getNextSibling();
	                
	                head= head.getNextSibling();
	                if(!head.getNodeName().equals("fileversion"))
	                {
	                	result=-1;
	                	break;
	                }
	                fileversion=head.getFirstChild().getNodeValue().trim();
	                head= head.getNextSibling();
	                
	                head= head.getNextSibling();
	                if(!head.getNodeName().equals("ostype"))
	                {
	                	result=-1;
	                	break;
	                }
	                ostype=head.getFirstChild().getNodeValue().trim();
	                head= head.getNextSibling();
	                
	                //if(fname.equals("twunk_16.exe"))
	                	//log.logger.info(fname);
	                String sql="select count(id) as count from knowledge_item where ics_id="+ics_id+" and hash_value='"+hashvalue+"' and process_name='"+fname+"' and software_ver='"+fileversion+"' and os_ver='"+ostype+"'";
	                
	               
	                
	                
	              //=========================知识库结构修改之后========================================
	                try {
	                	int count=0;
	                	java.sql.ResultSet rs=statement.executeQuery(sql);
	                	if (rs.next()) {
	        				count = rs.getInt("count");
	        			}
	                	if(count<=0)
	                	{
	                	 sql="insert into knowledge_item(ics_id,hash_value,process_name,process_path,software_ver,os_ver,software_info,release_date,Manufacturer,ic_bool) values("+ics_id+",'"+hashvalue+"','"+fname+"','"+filepath+"','"+fileversion+"','"+ostype+"','"+filedesp+"','UNKNOWN','"+company+"','"+softtype+"')";
						statement.executeUpdate(sql);
	                	}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	              //=========================知识库结构修改之后========================================
	                
	                
	                //=========================知识库结构修改之前========================================
	                /*
	                sql="insert into knowledge_extension values('"+fname+"','"+fileversion+"','"+filedesp+"','UNKNOWN','"+company+"','"+softtype+"')";
	                try {
						statement.executeUpdate(sql);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
	              
	                sql="insert into knowledge_basic values('"+hashvalue+"','"+fname+"','"+fileversion+"','"+ostype+"','"+filepath+"')";
	                try {
						statement.execute(sql);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
	             
	                sql="select name from knowledge where id="+ics_id;
	                
	                java.sql.ResultSet rs;
	                String ics_name="";
					try {
						rs = statement.executeQuery(sql);
						if(rs.next())
							ics_name=rs.getString("name");
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	               // String ics_name="";
	                //rs.close();
	                sql="insert into knowledge_recommended values('"+ics_name+"','"+hashvalue+"',"+ics_id+")";
	                try {
						statement.execute(sql);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
	                */
	              //=========================知识库结构修改之前========================================
	                
	                
				}
				if(result==-1)
				{
					//statement.close();
					request.setAttribute("result", "知识库文件格式错误!");
					//request.getRequestDispatcher("../result2.jsp").forward(request,response);
					//return;
				}
				//rs.close();
				statement.close();
				con.close();
				f.delete();
				originf.delete();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			//f.delete();
			e.printStackTrace();
			result=-1;
			request.setAttribute("result", "导入知识库文件失败!");
			//request.getRequestDispatcher("../result2.jsp").forward(request,response);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=-1;
			request.setAttribute("result", "导入知识库文件失败!");
			//request.getRequestDispatcher("../result2.jsp").forward(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result=-1;
			e.printStackTrace();
			request.setAttribute("result", "导入知识库文件失败!");
			//request.getRequestDispatcher("../result2.jsp").forward(request,response);
		}
		request.setAttribute("result", "导入知识库文件成功!");
		if(result==0)
			request.getRequestDispatcher("../result2.jsp").forward(request,response);
		else
			request.getRequestDispatcher("../addWhite.jsp").forward(request,response);
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
