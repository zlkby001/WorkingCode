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



<script language="JavaScript">  
  
  var thisGrid,grid;
	  Ext.onReady(function(){
      // create the grid
      grid = new Ext.ux.grid.TableGrid("tbHistory", {
      title : '远程证明日志',
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
       //alert(grid.getColumnModel().getColumnById('tcol-0').header);
        
});

  
  var   selectRow=null;//记录上一次click过的行  
  var   pointRow=null;//记录上次mouseover过的行  
  var checkedAll = false; 
  
function changeRed(i){
  return  document.getElementById("totable").rows[i].bgColor = "#FF00FF";
}   
//添加
   
function   moveRow(src){  
        if(pointRow!=null)  
              pointRow.style.backgroundColor="#FFFFFF";  
        if(src!=selectRow){  
              pointRow=src;  
              pointRow.style.backgroundColor="#FEF9E2";  
        }  
  } 
  function   moveRow2(src){  
        if(pointRow!=null)  
              pointRow.style.backgroundColor="#FFFFFF";  
        if(src!=selectRow){  
              pointRow=src;  
              pointRow.style.backgroundColor="#DEEBFE";  
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
		//form.alls.checked = false;
		checkedAll = false;
	} else {
		//form.alls.checked = true;
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
  	document.forms[0].action = "servlet/addAllWhitelistServlet";
    document.forms[0].submit();
  	
  	}
    else
    	return false;
	}
	
	function doSubmitall() {
  	document.forms[0].action = "servlet/adllAllPagesHistoryServlet";
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

</script>

</head>
<body leftmargin="4" topmargin="0" style="font-size:12px;" marginheight="0" marginwidth="0">

	<center>
		<div style="overflow-x: auto; overflow-y: auto;">
	
			<table style="border-collapse: collapse;" border="1" bordercolor="#77aa33" cellpadding="0" cellspacing="0" width="100%">
				<tbody>
					<tr>
						<td align="middle" valign="top">
							<form name="a123" id="abc" method=post target='_parent' onsubmit="do_action()">
								<!--   		<table border="0" cellpadding="2" cellspacing="0" width="98%" height="90%"> -->
								<table id="tbHistory" width=100%>

									<thead>
										<tr>
											<th field="f01" width="50px" align="center">全选<input
												type="checkbox" name="alls"
												onClick="selectAll('formName','YesNo')" title="全选/取消全选">
											</th>
											<th field="f02" width="100px" align="center">文件名</th>
											<th field="f03" width="100px" align="center">上传日期</th>
											<th field="f04" width="100px" align="center">信任状态</th>
											<th field="f05" width="100px" align="center">操作</th>

										</tr>
									</thead>


									<tbody id="totable">
										<!--   			<tr><td colspan="4" align="middle" height="30"></td></tr> -->
										<!-- 		        <tr align="center" bgcolor="#b6d3fc" height="20"> -->

										<!--             		    <td width="10%">全选/取消<input type="checkbox" name="alls" onClick="selectAll('formName','YesNo')" title="全选/取消全选"> -->
										<!-- </td> -->
										<!--             			<td width="20%">文件名</td>  -->
										<!--             			<td width="20%">上传日期</td> -->
										<!-- 						<td width="20%">信任状态</td> -->
										<!--             			<td>操 作</td>            			     			 -->
										<!--             	</tr> -->

										<%
											ArrayList al = (ArrayList) request.getSession(true).getAttribute(
													"MappingCollection");
											//HashMap hm=(HashMap)request.getSession(true).getAttribute("DateCollection");
											UserBean u = (UserBean) request.getSession(true).getAttribute("a");
											//System.out.println("======al size ======  " + al.size()+ "  ===============");
											//Set keys=hm.keySet();
											//ArrayList list=new ArrayList(keys);
											//Iterator iter=al.entrySet().iterator();

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

												FullWhiteBean fmb = null;

												int i = 0;
												while ((i + (currentpage - 1) * 20) < al.size() && i < 20) {
													Object obj = al.get(i + (currentpage - 1) * 20);
													fmb = (FullWhiteBean) obj;
													//String date=(String)list.get(i);
													String filename = fmb.getFileName();
													String Edition;
													if (fmb.getEdition() == null)
														Edition = "";
													else
														Edition = fmb.getEdition();
													int pos = filename.lastIndexOf("/");
													filename = filename.substring(pos + 1);
													out.println("<tr style='background-color: rgb(254, 249, 226);' onmouseover='moveRow(this)' align='center' height='20'>");
													out.println("<td>");
													out.println("<input type='checkbox' name='YesNo' value='"
															+ (i + 1) + "' title='选择/不选择'>");
													out.print("<input type='hidden' name='aid" + (i + 1)
															+ "' value='" + fmb.getID() + "'>");
													out.print("<input type='hidden' name='trusted" + (i + 1)
															+ "' value='" + fmb.getTrusted() + "'>");
													out.println("</td>");
													out.println("<td>" + filename + "</td>");
													out.println("<td>" + fmb.getIssueTime() + "</td>");

													if (fmb.getTrusted() == 1)
														out.println("<td>可信</td>");
													else
														out.println("<td>不可信</td>");

													out.println("<td>");

													//应该还需要AValue和Hash才能定位Mapping。
													out.println("[<a href='servlet/addWhiteServlet?aid="
															+ fmb.getID() + "' target='_parent'>添加</a>]");
													out.println("[<a href='servlet/viewAndModifyMappingServlet?aid="
															+ fmb.getID()
															+ "&from=history' target='_parent'>详情</a>]");
													out.println("[<a href='servlet/revokeMappingServlet?aid="
															+ fmb.getID()
															+ "' target='_parent' onclick='return delete1();'>删除</a>]");

													out.println("</td></tr>");

													/*
													Object obj=al.get(i+(currentpage-1)*20);
													fmb=(FullInfoBean)obj;
													String filename = fmb.getFileName();
													String Edition;
													            if(fmb.getEdition()==null)
													            	Edition="";
													            else
													            	Edition=fmb.getEdition();
													int pos = filename.lastIndexOf("/");
													filename = filename.substring(pos+1); 
													out.println("<tr style='background-color: rgb(254, 249, 226);' onmouseover='moveRow(this)' align='center' height='20'>");
													
													out.println("<td>"+fmb.getSoftwareName()+"</td>");
													out.println("<td>"+filename+"</td>");
													out.println("<td>"+Edition+"</td>");
													out.println("<td>"+fmb.getClassName()+"</td>");

													
													
													if(fmb.getTrusted()==1)
														out.println("<td>可信</td>");
													else
														out.println("<td>不可信</td>");
													
													out.println("<td>"+fmb.getCreateName()+"</td>");
													out.println("<td>"); 
													
													//应该还需要AValue和Hash才能定位Mapping。
													out.println("[<a href='servlet/viewMappingServlet?aid="+fmb.getID()+"' target='_parent'>详细</a>]");
													out.println("[<a href='servlet/viewAndModifyMappingServlet?aid="+fmb.getID()+"' target='_parent'>修改</a>]");
													out.println("[<a href='servlet/revokeMappingServlet?aid="+fmb.getID()+"' target='_parent' onclick='return delete1();'>删除</a>]");         			 
													    			 
													
													
													out.println("</td></tr>");
													 */

													i++;

												}

											}
										%>
                                    

									</tbody>
								</table>


						<table style= "font-size:12px">
									<tr>

										<td></td>
										<td><input type="button" value="信任全部"
											onclick="doSubmitall()">
										</td>
										<td><input type="button" value="信任选中"
											onclick="doSubmit()">
										</td>
										<td><input type="button" value="删除选中"
											onclick="doDelete()">
										</td>
										<td><input type="button" value="全部清空"
											onclick="doDeleteall()">
										</td>
										<!-- <td><input type="button" value="对比"
											onclick="dotiao()">
										</td> -->
										<td></td>                                                     
										<td width="40%"></td>

										<td align=center>
											<%
												String url ="HistoryList.jsp";
										 	out.println("<a href='" + url + "?p=1'><img src='img/firstPage.jpg' width=20px height=18px alt ='首页'></a>");
										 	int pre = currentpage-1;
										 	if(pre<1) pre = 1;
											out.println("<a href='" + url + "?p=" + pre + "'><img src='img/pre.jpg'  width=20px height=18px  alt ='上一页'></a>");
											out.println("第&nbsp;"+currentpage+"/"+totalpage+"&nbsp;页");
											int next = currentpage+1;
											if(next > totalpage ) next = totalpage;
											out.println("<a href='" + url + "?p="+ next +"'><img src='img/next.jpg'  width=20px height=18px  alt ='下一页'></a>");
										 	out.println("<a href='" + url + "?p="+totalpage+"'><img src='img/lastPage.jpg'  width=20px height=18x  alt ='尾页'></a>");
										 	 
 	 
												
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