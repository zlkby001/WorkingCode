<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
 import="cn.ac.sklois.imm.mappings.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>
<base href="<%=basePath%>">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>修改属性</title>
     <link href="img/default.css" rel="stylesheet" type="text/css">   
  </head><body>  
  <%
AttIDtoName a=(AttIDtoName)request.getAttribute("a");
int aid=a.getAttributeID();
String aname=a.getAttributeName();
String aclass=a.getAttributeClass();
%>

<form action="servlet/ModifyAttServlet" method="post">
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="90%">
		        <tbody>
		        
		            <tr>
		                <td colspan="2" align="middle" height="40">
		                   
		                </td>

		            </tr>
		        	<tr>
		                <td colspan="2" align="middle" height="30">
		                   <strong><font color="#cc0000">安全属性</font></strong>
		                </td>
		            </tr>
		            
		            
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">属性ID:</td>
		        	<td style="padding-left: 2px;">
		        		<%out.print(aid); %><input type="hidden" name="aid" value="<%out.print(aid);%>"> 
		        	</td>

		        </tr>
		        
		        <!--begin  -->   
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">属性名:</td>
		        	<td style="padding-left: 2px;"><input type="text" name="aname" value="<%out.print(aname);%>"> </td>
		        </tr>
		        <!--end  -->
		        
		        
		        
		        <tr>
		        
		        	<td style="padding-left: 10px;" width="20%">属性等级:</td>
		        	<td style="padding-left: 2px;">
		        		<SELECT style="WIDTH: 150px" name="aclass"> 
        				<OPTION value=1 <%if(aclass.equalsIgnoreCase("1")) out.print("selected"); %> >1</OPTION> 
        				<OPTION value=2 <%if(aclass.equalsIgnoreCase("2")) out.print("selected"); %>>2</OPTION>
        				<OPTION value=3 <%if(aclass.equalsIgnoreCase("3")) out.print("selected"); %>>3</OPTION>
        				<OPTION value=4 <%if(aclass.equalsIgnoreCase("4")) out.print("selected"); %>>4</OPTION>
        				<OPTION value=5 <%if(aclass.equalsIgnoreCase("5")) out.print("selected"); %>>5</OPTION>
        				<OPTION value=6 <%if(aclass.equalsIgnoreCase("6")) out.print("selected"); %>>6</OPTION>
        				<OPTION value=7 <%if(aclass.equalsIgnoreCase("7")) out.print("selected"); %>>7</OPTION>
        				<OPTION value=8 <%if(aclass.equalsIgnoreCase("8")) out.print("selected"); %>>8</OPTION>
        				<OPTION value=9 <%if(aclass.equalsIgnoreCase("9")) out.print("selected"); %>>9</OPTION>
        				<OPTION value=10 <%if(aclass.equalsIgnoreCase("10")) out.print("selected"); %>>10</OPTION>	
        			</SELECT> 
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

