
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.ArrayList,java.util.Iterator,cn.ac.sklois.imm.mappings.*,cn.ac.sklois.imm.admin.*,com.ics.usb.*"%>
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



<script language="JavaScript">  
  
 var thisGrid,grid;
	  Ext.onReady(function(){
      // create the grid
      grid = new Ext.ux.grid.TableGrid("tbMain", {
      title : '添加USB --> 用户列表',
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
        
        grid.getColumnModel().getColumnById('tcol-0').sortable = false;
        thisGrid = grid;  
        //document.getElementById('ext-gen11').style.textAlign='left'; 
        grid.render();       
});

  
  var   selectRow=null;//记录上一次click过的行  
  var   pointRow=null;//记录上次mouseover过的行  
  var checkedAll = false; 
   

   
	function   moveRow(src){  
	        if(pointRow!=null)  
	              pointRow.style.backgroundColor="#FFFFFF";  
	        if(src!=selectRow){  
	              pointRow=src;  
	              pointRow.style.backgroundColor="#FEF9E2";  
	        }  
	  } 
		 function showquesTable(id) {
			if (id.style.display=="none"){
				id.style.display = "";
		
				}
			else{
				id.style.display = "none";
				}
	} 
	   
	function delete1(){
	 	if(confirm("确定要删除此映射关系吗！")){
			return true;
		}else{
			return false;
		}
	 }
	 
	  
	function selectAll(formName,checkboxName){
		
	//	var form = document.getElementsById(formName);
	    var elements = document.getElementsByName(checkboxName);
	
		for (var i=0;i<elements.length;i++){
			var e = elements[i];
			if(checkedAll){
				e.checked = false;
	
			} else {
				e.checked = true;
			}
		}
		if(checkedAll){
	//					form.alls.checked = false;
			checkedAll = false;
		} else {
		//				form.alls.checked = true;
			checkedAll = true;
		}
	}  
   
	
	function checkAll(formName,checkboxName){
		var hasCheck = false;
		var elements = document.getElementsByName(checkboxName);
		for (var i=0;i<elements.length;i++){
		var e = elements[i];
		if(e.checked){
		hasCheck = true;
		}
		}
		return hasCheck;
	}
	
	/* 执行操作 */
	function do_action(){
		if (!checkAll("formName","YesNo")){
		alert("没有元数据被选中，请至少选择一个！");
		return false;
		} else {
		return true;
		}
	} 

	function doSubmit() {
	  	if(do_action()){
	  	document.forms[0].target = "_self";
	  	//document.forms[0].action = "servlet/addAllWhitelistServlet";
	  	document.forms[0].action = "servlet/addUsbServlet?uid="+getQueryString('uid');
	    document.forms[0].submit();
	  	
	  	}
	    else
	    	return false;
	}
	
	
	function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }
	
	
	//==================暂时不用========================
	function doSubmitall() {
  	//document.forms[0].action = "servlet/adllAllPagesHistoryServlet";
  	document.forms[0].action = "servlet/dumphistoryintoknowledgeServlet";
    document.forms[0].submit();
	}




	function doDelete() {
	if(do_action()){
		document.forms[0].action = "servlet/deleteAllHistorylistServlet";
    	document.forms[0].submit();
	}
    else return false;	
	}
	
	function doDeleteall() {
		document.forms[0].action = "servlet/deleteAllPagesHistorylistServlet";
    	document.forms[0].submit();
	
	}
	
	//暂时不用
	function goDetail(n){
		//alert(n);
		var store = thisGrid.getStore();
		var line  = store.getAt(n);
	
		var manufacture  = line.get('tcol-1');
		var sn  = line.get('tcol-2');
		var version  = line.get('tcol-3');
		var producer  = line.get('tcol-4');
		var terminal  = line.get('tcol-5');
		var date  = line.get('tcol-6');
		
		var url = 'userMeasureInfo.jsp?manufacture='+ manufacture 
				+ '&sn='+ sn 
				+ '&version='+ version 
				+ '&producer='+ producer 
				+ '&terminal='+ terminal
				+ '&date='+ date;
		//alert(url);
		parent.location = url;	
	}

</script>

</head>
<body leftmargin="4" topmargin="0" style="font-size:12px;" marginheight="0"
	marginwidth="0">
	<center>


		<div style="overflow-x: auto; overflow-y: auto;">
	
			<table style="border-collapse: collapse;" border="1"
				bordercolor="#77aa33" cellpadding="0" cellspacing="0" width="100%">
				<tbody>
					<tr>
						<td align="middle" valign="top">
							<form name="a123" id="abc" method=post target='_parent'
								onsubmit="do_action()">
								
								<table id="tbMain" width=100%>
									<thead>
										<tr>
											<th field="f01" width="50px" align="center">全选<input
												type="checkbox" name="alls"
												onClick="selectAll('formName','YesNo')" title="全选/取消全选">
											</th> 
											<th field="f02" width="100px" align="center">终端</th>
											<th field="f03" width="100px" align="center">厂区</th>
											<th field="f04" width="100px" align="center">工艺</th>
											<th field="f05" width="100px" align="center">控制系统</th>											

										</tr>
									</thead>


									<tbody>

										<%
											ArrayList al = (ArrayList) request.getSession(true).getAttribute(
													"MappingCollection");
											UserBean user;

											String message = "Warning   Info   ... ";
											int size = al.size();
											int i = 0;
												while (i < size ) {
													Object obj = al.get(i);
													user = (UserBean) obj;
													out.println("<tr onmouseover='moveRow(this)' align='center' height='20'>");
													out.println("<td>");
													out.println("<input type='checkbox' name='YesNo' value='"
															+ user.getID() + "' title='选择/不选择'>");
													out.print("<input type='hidden' name='aid" + (i + 1)
															+ "' value='" + user.getID() + "'></td>");
													out.println("<td id='sn'>" + user.getName() + "</td>");
													out.print("<td id='manufacture'>" + user.getFactory());
													out.println("</td>");
													out.println("<td id='sn'>" + user.getTechnology() + "</td>");
													out.println("<td id='version'>" + user.getSystem() + "</td>");

												
													out.println("</tr>");
													i++;

											}
										%>


									</tbody>
								</table>


						<table style= "font-size:12px">
									<tr>

										<td></td>
										<!-- <td><input type="button" value="添加全部"
											onclick="doSubmitall()">
										</td> -->

										<td><input type="button" value="添加选中"
											onclick="doSubmit()">
										</td>
										<td>
											<input type="button" value="返回" onClick="history.back();"></td>
										</td>

									
										<td></td>
										<td width="40%"></td>
									</tr>

								</table>
							</form>
						</td>
					</tr>
				<tbody>
			</table>
		</div>



	</center>
	
</body>
</html>