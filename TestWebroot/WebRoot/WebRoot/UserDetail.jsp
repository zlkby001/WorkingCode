<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cn.ac.sklois.imm.admin.*"   %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>详细信息</title>
     <link href="../img/default.css" rel="stylesheet" type="text/css"> 
     
<script type="text/javascript" src="extjs3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs3/ext-all-debug.js"></script>
<script type="text/javascript" src="extjs3/examples/ux/TableGrid.js"></script>
<link rel="stylesheet" type="text/css"
	href="extjs3/resources/css/ext-all.css" />
	
<style type=text/css>
/* style rows on mouseover */
.x-grid3-row-over .x-grid3-cell-inner {
	font-weight: bold;
	background-color: #FFFFB3;
}
</style>

<script language="JavaScript">  
var thisGrid;
       Ext.onReady(function(){
        // create the grid
        var grid = new Ext.ux.grid.TableGrid("tbUserDetail", {
      title : '终端信息',
			trackMouseOver : true,
			loadMask : true,
			header : true,
			enableHdMenu:false,
			collapsible: true,
            stripeRows: true,
            viewConfig : {
				forceFit : true,
				enableRowBody : true
			}
        });
        thisGrid = grid;
        grid.render();
});
</script>

</head>

<body>  
  
  <table id="tbUserDetail" style="border-collapse: collapse;overflow:scroll;font-size:12px;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="100%">
		       <thead>
		       		<tr>
		       		<th>条目</th>
		       		<th>数值</th>
		       		</tr>
		       </thead>
		       
		        <tbody>
		        
		            
		            
		            <%UserBean a=(UserBean)request.getAttribute("user");%>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">用户ID:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(a.getID()); %>
		        	</td>

		        </tr>
		        
		        <!--begin  -->   
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">用户名:</td>
		        	<td style="padding-left: 2px;"><% out.println(a.getName()); %></td>
		        </tr>
		        <!--end  -->
		        
		        <tr>
		        
		        	<td style="padding-left: 10px;" width="20%">PTM生产厂商:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(a.getmanufacture()); %>
		        	</td>
		        	
		        </tr>
                
                <tr>
		        
		        	<td style="padding-left: 10px;" width="20%">PTM序列号:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(a.getsequence()); %>
		        	</td>
		        	
		        </tr>
		        
		         <tr>
		        	<td style="padding-left: 10px; " width="20%">身份公钥:</td>
		        	<td style="padding-left: 2px; ">

		        		<% 
		        		String str = a.getpubkey();
		        		String msg = str.substring(0,50) + "<br>"+ str.substring(51,100) + "<br>"+ str.substring(101,str.length()-1);
		        		out.println(msg); %>
		        	</td>

		        </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px; " width="20%">EK公钥:</td>
		        	<td style="padding-left: 2px; ">
		        		<% 
		        		String str2 = a.getEK();
		        		String msg2 = str2.substring(0,50) + "<br>"+ str2.substring(51,100) + "<br>"+ str2.substring(101,str2.length()-1);
		        		out.println(msg2); %>
		        		
		        	</td>

		        </tr>
                
                <tr>
   			    
		        	<td style="padding-left: 10px;" width="20%">IP地址:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(a.getip()); %>
		        	</td>
		        	
		        </tr>
                
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">Mac地址:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(a.getmac()); %>
		        	</td>

		        </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">操作系统版本:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(a.getos()); %>
		        	</td>

		        </tr>
                
                
                
                </tbody>
                </table>	
                
    <table style="border-collapse: collapse;overflow:scroll;font-size:12px;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="100%">
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
   					   			
   					   			<%
   					   				//out.print("<a href='Javascript:window.history.go(-1)'>返回列表</a>");
   					   				
   					   			%>
   					   			<INPUT type=button value="返回列表" name="commit" onclick="parent.loadmain('UserList.jsp')">
   					   			</center><br>
   					   			</td>			                				                
			   	   			 </tr></tbody></table></td>      				              						                			                
              				</tr>
                    </table>
  </body>
</html>