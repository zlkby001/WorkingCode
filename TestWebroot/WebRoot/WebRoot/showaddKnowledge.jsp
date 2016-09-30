<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"   %>
    
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>添加备注信息</title>
     
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
     
<script type="text/javascript" src="extjs3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs3/ext-all.js"></script>
<script type="text/javascript" src="js/myjs.js"></script>
 <link href="img/default.css" rel="stylesheet" type="text/css">   
 
 <script type="text/javascript">
   function addKnowledge() {
		var name = document.getElementById('name').value;
		
		var ver = document.getElementById('ver').value;
		
		var os = document.getElementById('os').value;
		
		var des = document.getElementById('des').value;
		
		//alert(name);
		var result = ajaxSyncCall('servlet/knowledgeListServlet?type=add&name=' + encodeURI(encodeURI(name))+'&ver='+ encodeURI(encodeURI(ver))+'&os='+encodeURI(encodeURI(os))+'&des='+encodeURI(encodeURI(des)), null);
		if (result.exist) {
		    	alert("已存在同名知识库，请修改名称!");
		    } 
		else if (result.success) {
		    	alert("添加知识库成功!");
		    	//thisGrid.getStore().reload();
		    	a123.action = "Javascript:window.history.go(-1)";
                a123.submit();
		    }else {
		    	alert(result.errorMsg);
		    	a123.action = "Javascript:window.history.go(-1)";
                a123.submit();
		    }
	}
 function ajaxSyncCall(urlStr, paramsStr) {
	var obj;
	if (window.ActiveXObject) {
		obj = new ActiveXObject('Microsoft.XMLHTTP');
	} else if (window.XMLHttpRequest) {
		obj = new XMLHttpRequest();
	}
	obj.open('POST', urlStr, false);
	obj.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	obj.send(paramsStr);
	var result = Ext.util.JSON.decode(obj.responseText);
	return result;
}
function doBack() {
   a123.action = "Javascript:window.history.go(-1)";
    a123.submit();
}
 
 </script>
 </head>
 <body> 
  
 <form name="a123" method="post"> 
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="50%">
		        <tbody>
		        
		        	<tr>
		                <td colspan="2" align="middle" height="30">
		                   <strong><font color="#cc0000">新建知识库</font></strong>
		                </td>
		            </tr>
		            
		         
		        <tr >
		        	<td  style="text-align:right"" width="40%">知识库名称：</td>
		        	<td style="padding-left: 2px;">
		        		<input id="name" type="text" name="name" size="30" value="新建知识库" >
		        	</td>

		        </tr>
		        <tr>
		        	<td style="text-align:right"" width="40%">知识库版本：</td>
		        	<td style="padding-left: 2px;">
		        		<input id="ver" type="text" name="ver" size="30" value="1.0">
		        	</td>

		        </tr>
		        <tr>
		        	<td style="text-align:right"" width="40%">操作系统版本:</td>
		        	<td style="padding-left: 2px;">
		        		<input id="os" type="text" name="os" size="30" value="windows" >
		        	</td>

		        </tr>
		        <tr>
		        	<td style="text-align:right"" width="40%">知识库描述信息:</td>
		        	<td style="padding-left: px;">
		        		<input id="des" type="text" name="des" size="30" value="知识库" >
		        	</td>

		        </tr>
		        		        
		      	
   					   			<tr bgcolor="#9AADCD">
   					   			<td width="20%">
   					   			
   					   			</td>
   					   			
   					   			<td>
   					   			<table bgcolor="#9AADCD">
   					   			<tbody><tr >
   					   			<td>
   					   			<center>
   					   					<input value="确定"  type=button onclick="addKnowledge()">
										<input value="返回"  type=button onclick="doBack()">
   					   			</center>
   					   					
   					   			</td>			                				                
			   	   			 </tr></tbody></table></td>      				              						                			                
              				</tr>
                </tbody>
                </table>					       
		 </form>       
  
  </body></html>