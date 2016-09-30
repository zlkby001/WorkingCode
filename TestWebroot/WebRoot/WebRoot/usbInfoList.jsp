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
.blue{
 color: blue; text-decoration:underline
}

</style>



<script language="JavaScript">  
  
 var thisGrid,grid;
	  Ext.onReady(function(){
      // create the grid
      grid = new Ext.ux.grid.TableGrid("tbMain", {
      title : 'USB度量信息表',
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
	  	//document.forms[0].action = "servlet/addAllWhitelistServlet";
	  	document.forms[0].action = "servlet/usbUserListServlet";
	    document.forms[0].submit();
	  	
	  	}
	    else
	    	return false;
	}
	
	function doDeleteall() {
		var val = confirm("您确定要删除全部度量信息吗?");
		if (!val) {
		   return;
		}
		document.forms[0].action = "servlet/deleteAllUsbMeasureServlet";
    	document.forms[0].submit();
	
	}
	
	function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }
	
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
		
		var url = 'usbMeasureInfo.jsp?manufacture='+ manufacture 
				+ '&sn='+ sn 
				+ '&version='+ version 
				+ '&producer='+ producer 
				+ '&terminal='+ terminal
				+ '&date='+ date;
				
		if (navigator.userAgent.indexOf('MSIE')>0) {	//判断是否IE浏览器
			parent.location ="../" + url;
		} 
		else{
		      parent.location = url;
		}
		//alert(url);
		//parent.location = url;	
	}
	
	//==================暂时不用========================
	function doSubmitall() {
  	//document.forms[0].action = "servlet/adllAllPagesHistoryServlet";
  	document.forms[0].action = "servlet/dumphistoryintoknowledgeServlet";
    document.forms[0].submit();
	}

	//暂时不用
	function doDelete() {
	if(do_action()){
		document.forms[0].action = "servlet/deleteAllHistorylistServlet";
    	document.forms[0].submit();
	}
    else return false;	
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
											<th field="f02" width="100px" align="center">制造商</th>
											<th field="f03" width="100px" align="center">序列号</th>
											<th field="f04" width="100px" align="center">版本号</th>
											<th field="f05" width="100px" align="center">生产商</th>
											<th field="f06" width="100px" align="center">终端</th>
											<th field="f07" width="100px" align="center">日期</th>
											<th field="f08" width="100px" align="center">操作</th>

										</tr>
									</thead>


									<tbody>

										<%
											ArrayList al = (ArrayList) request.getSession(true).getAttribute(
													"MappingCollection");
											UsbBean usb;

											String currentpagestr = (String) request.getParameter("p");
											int currentpage = Integer.parseInt(currentpagestr);
											int totalpage = 1;

											String message = "Warning   Info   ... ";
											int size = al.size();

											if (al != null) {

												if (al.size() == 0) {
													totalpage = 1;
												} else if (al.size() % 20 == 0) {
													totalpage = al.size() / 20;
												} else {
													totalpage = al.size() / 20 + 1;
												}

												int i = 0;
												while ((i + (currentpage - 1) * 20) < al.size() && i < 20) {
													Object obj = al.get(i + (currentpage - 1) * 20);
													usb = (UsbBean) obj;
													out.println("<tr onmouseover='moveRow(this)' align='center' height='20'>");
													out.println("<td>");
													out.println("<input type='checkbox' name='YesNo' value='"
															+ usb.getId() + "' title='选择/不选择'>");
													out.print("<input type='hidden' id='uid" + (i + 1)
															+ "' value='" + usb.getId() + "'></td>");
															
													out.print("<td id='manufacture'>" + usb.getManufacture());
													out.println("</td>");
													out.println("<td id='sn'>" + usb.getSn() + "</td>");
													out.println("<td id='version'>" + usb.getVersion() + "</td>");
													out.println("<td id='producer'>" + usb.getProducer() + "</td>");
													out.println("<td id='terminal'>" + usb.getTerminal() + "</td>");
													out.println("<td id='date'>" + usb.getDate() + "</td>");

													out.println("<td>");
													out.println("[<a class='blue' href='servlet/usbUserListServlet?uid="
															+ usb.getId() + "' target='_parent'>添加</a>]");
													out.println("[<a class='blue' href='javascript:void(0)'  onclick=goDetail("+ i +")>详情</a>]");
													out.println("</td></tr>");
													i++;

												}

											}
										%>


									</tbody>
								</table>


						<table style= "font-size:12px">
									<tr>

										<td></td>

										<td><input type="button" value="添加选中"
											onclick="doSubmit()">
										</td>
										
										<td><input type="button" value="全部清空"
											onclick="doDeleteall()">
										</td>

									
										<td></td>
										<td width="40%"></td>

										<td align=center>
											<%
												String url ="usbInfoList.jsp";
										 	out.println("<a href='" + url + "?p=1'><img src='img/firstPage.jpg' width=20px height=18px alt ='首页'></a>");
										 	int pre = currentpage-1;
										 	if(pre<1) pre = 1;
											out.println("<a href='" + url + "?p=" + pre + "'><img src='img/pre.jpg'  width=20px height=18px  alt ='上一页'></a>");
											out.println("第&nbsp;"+currentpage+"/"+totalpage+"&nbsp;页");
											int next = currentpage+1;
											if(next > totalpage ) next = totalpage;
											out.println("<a href='" + url + "?p="+ next +"'><img src='img/next.jpg'  width=20px height=18px  alt ='下一页'></a>");
										 	out.println("<a href='" + url + "?p="+totalpage+"'><img src='img/lastPage.jpg'  width=20px height=18px  alt ='尾页'></a>");
										 	 
											%>
										</td>
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