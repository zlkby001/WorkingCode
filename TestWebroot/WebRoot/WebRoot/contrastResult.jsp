<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.ArrayList,java.util.Iterator,cn.ac.sklois.imm.mappings.*,java.sql.*,cn.ac.sklois.imm.admin.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<base href="<%=basePath%>">

<meta http-equiv="content-type" content="text/html; charset=UTF-8">


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
.x-grid3-viewport{
	text-align:left;
}
</style>
  </head>
  
  <body>
    <script type="text/javascript">
    Ext.onReady(function() {
      var myPanel = new Ext.Panel({
        layout : 'fit',
        html : '<iframe src="contrast.jsp"  width="100%"  height="100%"></iframe>',
        frame : true
        })
        var win = new Ext.Window({
                    title : '对比结果',
                    width : 1750,
                    height :550,
                    border: false,
                    collapsible : true,
                    constrain:true,
                    resizable : false,
                    closable : true,
                    closeAction:'hide',
                    draggable : false,
                    resizable : false,
                    layout : 'fit',
                    modal : false,
                    plain : true, // 表示为渲染window body的背景为透明的背景
                    bodyStyle:'background-color:white;padding:5px 5px 5px;border: 0px',
                    items : [myPanel ]
                 
                });
                win.show();
          
    });
    </script>
  </body>
</html>
