<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.Iterator,cn.ac.sklois.imm.mappings.*"  %>
 
 
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<base href="<%=basePath%>">

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="extjs3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs3/ext-all-debug.js"></script>
<script type="text/javascript" src="extjs3/examples/ux/TableGrid.js"></script>
<link rel="stylesheet" type="text/css"
	href="extjs3/resources/css/ext-all.css" />


<script language="JavaScript">  
    Ext.onReady(function(){
        // create the grid
        var grid = new Ext.ux.grid.TableGrid("tbWhiteList", {
            title : '白名单信息',
			trackMouseOver : true,
			loadMask : true,
			header : true,
			enableHdMenu:false,
			collapsible: true,
            stripeRows: true, // stripe alternate rows
            viewConfig : {
				forceFit : true,
				enableRowBody : true
			}
        });
        grid.render();
});

</script>

</head><body leftmargin="4" topmargin="0" style="font-size:12px;" marginheight="0" marginwidth="0">
<center>



<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tbody>

  <tr>
    <td colspan="3"><img src="img/spacer.gif" width="1" height="1"></td>
  </tr>
  <tr><td align="middle" background="img/banner_bg.gif" width="200" height="29"><br></td>
  <td background="img/banner_bg1.gif"><br></td><td width="2"><br></td></tr><tr><td colspan="3"><br></td></tr></tbody></table>

<table style="border-collapse: collapse;" border="1" bordercolor="#77aa33" cellpadding="0" cellspacing="0" width="100%">
  <tbody>
  <tr>
  	<td align="middle" valign="top" >
  		
		<table id="tbWhiteList" height="300px"  title="白名单信息" width="100%" >
			<thead>
				<tr>
					<th field="f01" width="100px" align="center">文件名</th>
					<th field="f02" width="100px" align="center">度量值</th>
					<th field="f04" width="100px" align="center">描述信息</th>
					<th field="f05" width="100px" align="center">上传时间</th>
					<th field="f06" width="100px" align="center">软件类型</th>	
					<th field="f03" width="100px" align="center">操作</th>

				</tr>
			</thead>
			
  			<tbody></tbody>
	</table>
		     
	</td>
   </tr>
   
    <tbody>     
   </table>
          

</center>
</body></html>