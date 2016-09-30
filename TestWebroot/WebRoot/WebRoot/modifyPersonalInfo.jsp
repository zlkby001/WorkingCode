<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  import="cn.ac.sklois.imm.admin.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>修改个人信息</title>
     <link href="../img/default.css" rel="stylesheet" type="text/css">   
  </head><body>  
  <%
AdminBean a=(AdminBean)request.getAttribute("a");
String name=a.getName();
String oid=a.getOperatorID();
String address=a.getAddress();
String phonenum=a.getPhoneNum();
String email=a.getEmail();

String gender=a.getGender();
String certclass=a.getCertclass();
String certnumber=a.getCertnumber();

%>
<form action="modifyPersonalInfoServlet" method="post">
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="90%">
		        <tbody>
		        
		            <tr>
		                <td colspan="2" align="middle" height="40">
		                   
		                </td>

		            </tr>
		        	<tr>
		                <td colspan="2" align="middle" height="30">
		                   <strong><font color="#cc0000">个人信息</font></strong>
		                </td>
		            </tr>
		            
		            
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">ID号:</td>
		        	<td style="padding-left: 2px;">
		        		<%out.print(oid); %><input type="hidden" name="oid" value="<%out.print(oid);%>"> 
		        	</td>

		        </tr>
		        
		        <!--begin  -->   
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">用户名:</td>
		        	<td style="padding-left: 2px;"><input type="text" name="name" size="10" value="<%=name%>"></td>
		        </tr>
		        <!--end  -->
		        
		        
		        <tr> 
                    <td style="padding-left: 10px;" width="20%">性别：</td>
           		    <td style="padding-left: 2px;">           		    	
           		    		<%
           		    		String tmp=a.getGender();
           		    		
           		    		 %>   
           		    		 
                    <input type=radio name=gender value=M <%if(tmp.equalsIgnoreCase("M"))out.print("checked");%>>
                    男
                    <input type=radio name=gender value=F <%if(tmp.equalsIgnoreCase("F"))out.print("checked");%>>
                    女                            &nbsp;
                    </td>
                </tr>
                
                <tr>
		        
		        	<td style="padding-left: 10px;" width="20%">联系地址:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="address" size="68" value="<%=address%>">
		        	</td>
		        	
		        </tr>
                
                <tr>
   			    
		        	<td style="padding-left: 10px;" width="20%">电话号码:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="phonenum" size="38" value="<%=phonenum%>">
		        	</td>
		        	
		        </tr>
                
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">EMail地址:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="email" size="38" value="<%=email%>">
		        	</td>

		        </tr>
		        
   			    
		        
		        
                
		        

		        
		        <tr>
		        
		        	<td style="padding-left: 10px;" width="20%">证件类型:</td>
		        	<td style="padding-left: 2px;">
		        		<%out.print(certclass); %> 
		        	</td>
		        	
		        </tr>
		        
		        <tr>		        
		        	<td style="padding-left: 10px;" width="20%">证件号码:</td>
		        	<td style="padding-left: 2px;">
		        		<%out.print(certnumber); %>
		        		<br>
		        		<font color=red>证件信息不能随意修改。如需修改，请使用单独的“修改证件信息”功能！</font>
		        	</td>  	
		        </tr>
		        
		        
		        
		        
		        
		        
                
                
                
 				               
            	

                
                <tr> 
                <td height="20"></td>
                </tr>
                
   					   			
   					   			<tr bgcolor="#9AADCD">
   					   			<td width="30%"></td>
   					   			<td>

   					   			<table>
   					   			<tbody><tr>
   					   			<td style="padding-left: 0px;" align="center" width="33%"><input value="   提交修改   "  type="submit"></td>			                				                
			   	   			 </tr></tbody></table></td>      				              						                			                
              				</tr>

              				
                
                </tbody>
                </table>					       
		   </form>     
   
  
  </body></html>