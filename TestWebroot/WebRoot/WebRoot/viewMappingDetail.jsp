<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cn.ac.sklois.imm.mappings.*"   %>
  <script   language=javascript>   
function refreshParent()
 {     window.opener.location.href = window.opener.location.href;     
	if (window.opener.progressWindow)     
	{         window.opener.progressWindow.close();     }     
	window.close(); 
	}     
  </script>  
    
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>详细信息</title>
     
     <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
     
     
     <link href="img/default.css" rel="stylesheet" type="text/css">   
  </head><body>  
  
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="90%">
		        <tbody>
		        
		            <tr>
		                <td colspan="2" align="middle" height="40">
		                   
		                </td>

		            </tr>
		        	<tr>
		                <td colspan="2" align="middle" height="30">
		                   <strong><font color="#cc0000">详细信息</font></strong>
		                </td>
		            </tr>
		            
		            <%FullInfoBean mb=(FullInfoBean)request.getAttribute("mapping");
		            String Description;
		            String Edition;
		            if(mb.getDescription()==null)
		            	Description="";
		            else
		            	Description=mb.getDescription();
		            if(mb.getEdition()==null)
		            	Edition="";
		            else
		            	Edition=mb.getEdition();
		            %>
		        
		        
		           
		        <tr>
		        
		        	<td style="padding-left: 10px;" width="20%">软件类型:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(mb.getClassName()); %>
		        	</td>
		        	
		        </tr>
		        
		         <tr>
		        	<td style="padding-left: 10px;" width="20%">所属软件包:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(mb.getSoftwareName()); %>
		        	</td>

		        </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">文件名:</td>
		        	<td style="padding-left: 2px;">
		        	    <%mb.getFileName().replace(" ", "&nbsp;"); %>
		        		<% out.println(mb.getFileName()); %>
		        	</td>

		        </tr>
		        
		        <!--begin  -->   
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">版本号:</td>
		        	<td style="padding-left: 2px;"><% out.println(Edition); %></td>
		        </tr>
		        <!--end  -->
		        
		                        
                <tr>
		        
		        	<td style="padding-left: 10px;" width="20%">发布时间:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(mb.getIssueTime()); %>
		        	</td>
		        	
		        </tr>
                
                
                <tr>		        
		        	<td style="padding-left: 10px;" width="20%">软件完整性值:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(mb.getDigest()); %>
		        	</td>  	
		        </tr>
                
                
                
		        
		        <tr>		        
		        	<td style="padding-left: 10px;" width="20%">可信状态:</td>
		        	<td style="padding-left: 2px;">
		        		<%if(mb.getTrusted()==1)
		        				out.println("可信");
		        			else
		        				out.println("不可信");%>
		        	</td>

		        	
		        </tr>
		        
		        
		        <tr>
   			    
		        	<td style="padding-left: 10px;" width="20%">描述信息:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(Description); %>
		        	</td>
		        	
		        </tr>
                		        <tr>
   			    
		        	<td style="padding-left: 10px;" width="20%">操作人员:</td>
		        	<td style="padding-left: 2px;">	        		
		        		<% out.println(mb.getCreateName()); %>
		        	</td>
		        	
		        </tr>
                
                
 				               
            	

                
                <tr> 
                <td height="20"></td>
                </tr>
                
   					   			
   					   			<tr bgcolor="#9AADCD">
   					   			<td width="30%">
   					   			
   					   			</td>
   					   			
   					   			<td>

   					   			<table bgcolor="#9AADCD">
   					   			<tbody><tr >
   					   			<td><center>
   					   			
   					   	<a href="Javascript:window.history.go(-1)" >返回</a>  
   					   			
   					   			</center><br>
   					   			
   					   			
   					   			
   					   			</td>			                				                
			   	   			 </tr></tbody></table></td>      				              						                			                
              				</tr>
              				
                
                </tbody>
                </table>					       
		        
   
  
  </body></html>