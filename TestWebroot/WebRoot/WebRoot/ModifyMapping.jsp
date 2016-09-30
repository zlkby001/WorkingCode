<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList,cn.ac.sklois.imm.mappings.*"   %>
    
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>修改映射关系</title>
     
     <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
     
     
     <link href="img/default.css" rel="stylesheet" type="text/css">   
  </head><body> 
  <script language="JavaScript">
  function checkForm(form){
	//if(form.sname.value==""){
		//confirm("请输入所属软件包名！");
		//return false;	
	//}  
	if(form.shash.value == ""){
		confirm("请输入hash值");
		return false;
	}
	if(form.filename.value==""){
		confirm("请输入文件名！");
		return false;	
	} 
	//if(form.sissuetime.value==""){
		//confirm("请输入发布时间！");
		//return false;	
	//}  
	return true;
 } 
 
 function doSubmit() {
    a123.action = "servlet/ModifyMappingServlet";
    a123.submit();
}

function doBack() {
   a123.action = "Javascript:window.history.go(-1)";
    a123.submit();
}
 
 </script>
 <form name="a123" method="post" onsubmit="return checkForm(this);"> 
<!-- <form action="servlet/ModifyMappingServlet" method="post" onsubmit="return checkForm(this);"> -->
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="100%">
		        <tbody>
		        
		            <tr>
		                <td colspan="2" align="middle" height="40">
		                   
		                </td>

		            </tr>
		        	<tr>
		                <td colspan="2" align="middle" height="30">
		                   <strong><font color="#cc0000">详情</font></strong>
		                </td>
		            </tr>
		            
		            <%
		            	FullInfoBean mb=(FullInfoBean)request.getAttribute("mapping");
		            	String from=(String)request.getAttribute("from");
		            	
		             String Description;
		            if(mb.getDescription()==null)
		            	Description="";
		            else
		            	Description=mb.getDescription();
		            %>
		        
		        
		           <tr>
		                <td colspan="2" align="left" height="30">
		                   <strong><font color="#cc0000">软件信息</font></strong>
		                </td>
		            </tr>
		        
		        
		        
		        <tr>
		        
		        	<td style="padding-left: 10px;" width="20%">软件类型:</td>
		        	<td style="padding-left: 2px;">
						<%int classID=mb.getClassID(); %>
		        		<SELECT style="WIDTH: 150px" name="sclass"> 
        				<OPTION value=1 <%if(classID==1) out.print("selected"); %>>操作系统</OPTION> 
        				<OPTION value=2 <%if(classID==2) out.print("selected"); %>>网络软件</OPTION>
        				<OPTION value=3 <%if(classID==3) out.print("selected"); %>>系统工具</OPTION> 
        				<OPTION value=4 <%if(classID==4) out.print("selected"); %>>应用软件</OPTION>
        				<OPTION value=5 <%if(classID==5) out.print("selected"); %>>图形图像</OPTION>
        				<OPTION value=6 <%if(classID==6) out.print("selected"); %>>多媒体</OPTION>
        				<OPTION value=7 <%if(classID==7) out.print("selected"); %>>办公软件</OPTION>
        				<OPTION value=8 <%if(classID==8) out.print("selected"); %>>游戏娱乐</OPTION>        				
        				<OPTION value=9 <%if(classID==9) out.print("selected"); %>>编程开发</OPTION>
        				<OPTION value=10 <%if(classID==10) out.print("selected"); %>>杀毒安全</OPTION>
        				<OPTION value=11 <%if(classID==11) out.print("selected"); %>>系统补丁</OPTION>
        				<OPTION value=12 <%if(classID==12) out.print("selected"); %>>软件补丁</OPTION>
        				
        			</SELECT> <font color="#cc0000">*</font>
		      	
		        	</td>
		        	
		        </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">所属软件包:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="sname" size="15" value= <% out.println(mb.getSoftwareName());%>>
		        	<font color="#cc0000">*</font> </td>

		        </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">文件名:</td>
		        	<td style="padding-left: 2px;">
		        	<%mb.getFileName().replace(" ", "&nbsp;"); %>
		        		<input type="text" name="filename" size="15" value= "<% out.println(mb.getFileName());%>">
		        	<font color="#cc0000">*</font> </td>

		        </tr>
		        
		        <!--begin  -->   
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">版本号:</td>
		        	<td style="padding-left: 2px;">
		        	<input type="text" name="sedition" size="15" value= <% out.println(mb.getEdition()); %> >
		        	</td>
		        </tr>
		        <!--end  -->
		        

                
                <tr>
		        
		        	<td style="padding-left: 10px;" width="20%">发布时间:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="sissuetime" size="50" value= <% out.println(mb.getIssueTime()); %> >         		    	
           			<font color="#cc0000">*</font> </td>
		        	</td>
		        	
		        </tr>
                
                
                
		        
 
		        
		        
		        
		        <tr>		        
		        	<td style="padding-left: 10px;" width="20%">软件完整性值:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="shash" size="50" value= <% out.println(mb.getDigest()); %> >         		    	
           	 	
		        	<font color="#cc0000">*</font> </td>  	
		        </tr>
		        
		      

		        <tr> 
                    <td style="padding-left: 10px;" width="20%">可信状态:</td>
           		    <td style="padding-left: 2px;">           		    	
           		    	<SELECT style="WIDTH: 150px" name="avalue"> 
           		    		<%
           		    		if(mb.getTrusted()==1){
		        				out.print("<OPTION value=1 selected>可信</OPTION>");		        			
		        			}else{
		        				out.print("<OPTION value=1 >可信</OPTION>");
		        			} 
		        			
		        			if(mb.getTrusted()==0){
		        				out.print("<OPTION value=0 selected>不可信</OPTION>");		        			
		        			}else{
		        				out.print("<OPTION value=0 >不可信</OPTION>");
		        			} 
		        			
           		    		%>
        							
        			</SELECT> 
        			<font color="#cc0000">*</font>
                    </td>
                </tr>
		        
		           <tr>		        
		        	<td style="padding-left: 10px;" width="20%">描述信息:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="shash" size="50" value= <% out.println(mb.getDescription()); %> >         		    	
           	 	
		        	<font color="#cc0000">*</font> </td>  	
		        </tr>
		        
                
                
                
 				               
            	

                
               <tr> 
               <td height="20">
                <input type="hidden" name="oldaid" value=<% out.println(mb.getID()); %>>
                </td>
                <td height="20">
                <input type="hidden" name="from" value=<% out.println(from); %>>
                </td>
                </tr>
              				<tr bgcolor="#9AADCD">
   					   			<td width="20%">
   					   			
   					   			</td>
   					   			
   					   			<td>

   					   			<table bgcolor="#9AADCD">
   					   			<tbody><tr >
   					   			<td><center>
   					   			
   					   					
   					   					<!--  <input value="   提交修改   "  type="submit"> -->
   					   					<!--  <input value="提交修改"  type=button onclick="doSubmit()"> -->
										<input value="返回"  type=button onClick="doBack()">
   					   				
   					   			
   					   			
   					   			
   					   			
   					   			
   					   			
   					   			</center><br>
   					   			
   					   			
   					   			
   					   			</td>			                				                
			   	   			 </tr></tbody></table></td>	
                             </tr>
                </tbody>
                </table>					       
		 </form>       
   
  
  </body></html>