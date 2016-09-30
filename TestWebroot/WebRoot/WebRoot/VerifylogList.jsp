<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page
	import="java.util.ArrayList,java.util.Iterator,cn.ac.sklois.imm.admin.VerifylogBean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

var thisGrid;
       Ext.onReady(function(){
        // create the grid
        var grid = new Ext.ux.grid.TableGrid("tbVerifyLog", {
      title : '日志查询',
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
        //document.getElementById('ext-gen11').style.textAlign='left';
        grid.render();
});


	var selectRow = null;//记录上一次click过的行  
	var pointRow = null;//记录上次mouseover过的行  

	function moveRow(src) {
		if (pointRow != null)
			pointRow.style.backgroundColor = "#FFFFFF";
		if (src != selectRow) {
			pointRow = src;
			pointRow.style.backgroundColor = "#FEF9E2";
		}
	}
	function moveRow2(src) {
		if (pointRow != null)
			pointRow.style.backgroundColor = "#FFFFFF";
		if (src != selectRow) {
			pointRow = src;
			pointRow.style.backgroundColor = "#DEEBFE";
		}
	}
	function showquesTable(id) {
		if (id.style.display == "none") {
			id.style.display = "";

		} else {
			id.style.display = "none";

		}
	}

	function delete1() {
		if (confirm("确定要删除此帐户吗？")) {
			return true;
		} else {
			return false;
		}
	}
</script>

</head>
<body leftmargin="4" topmargin="0" bgcolor="#ffffff" marginheight="0"
	marginwidth="0">
	<center>

		<table style="border-collapse: collapse;" border="1"
			bordercolor="#77aa33" cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr>
					<td align="middle" valign="top">
						<!--   		<table border="0" cellpadding="2" cellspacing="0" width="98%" height="90%"> -->

						<table id="tbVerifyLog" width=100%>

							<thead>
								<tr>
									<th field="f01" width="100px" align="center">用户名</th>
									<th field="f02" width="100px" align="center">证明时间</th>
									<th field="f03" width="100px" align="center">ip地址</th>
									<th field="f04" width="100px" align="center">操作</th>

								</tr>
							</thead>


							<tbody>


								<%
									ArrayList al = (ArrayList) request.getSession(true).getAttribute(
											"verifylog");
									if (al == null) {
										out.println("verifylog is clean!");
									}

									String currentpagestr = (String) request.getParameter("p");
									int currentpage = Integer.parseInt(currentpagestr);
									int totalpage = 1;

									String message = "Warning   Info   ... ";
									//int size = al.size();

									if (al != null) {

										if (al.size() == 0) {
											totalpage = 1;
										} else if (al.size() % 20 == 0) {
											totalpage = al.size() / 20;
										} else {
											totalpage = al.size() / 20 + 1;
										}

										VerifylogBean a = null;

										int i = 0;
										while ((i + (currentpage - 1) * 20) < al.size() && i < 20) {
											Object obj = al.get(i + (currentpage - 1) * 20);
											a = (VerifylogBean) obj;
											out.println("<tr style='background-color: rgb(254, 249, 226);' onmouseover='moveRow(this)' align='center' height='20'>");
											//out.println("<td>"+a.getname()+"</td>");
											out.println("<td>");
											out.println("<a href='servlet/viewUserServlet?oid="
													+ a.getuserid() + "&flag=pass'>" + a.getname()
													+ "</a>&nbsp;&nbsp;&nbsp;");
											out.println("</td>");

											out.println("<td>" + a.getdate() + "</td>");

											out.println("<td>" + a.getip() + "</td>");

											out.println("<td>");
											//out.println("<br><SELECT name='avalue'> <OPTION value=2 selected>全部</OPTION><OPTION value=1>可信</OPTION> <OPTION value=0>不可信</OPTION></SELECT>");
											//out.println("<a href='servlet/userDetailServlet?oid="+a.getID()+"&flag=pass' target='main'>白名单</a>&nbsp;&nbsp;&nbsp;");
											out.println("<a href='servlet/verifyloghistoryServlet?userid="
													+ a.getuserid()
													+ "&flag=pass&date="
													+ a.getdate()
													+ "' target='main'>详情</a>&nbsp;&nbsp;&nbsp;");
											//if(!a.getID().equals("1000")){
											//out.println("[<a href='servlet/deleteUserServlet?oid="+a.getID()+"&flag=pass' target='main' onclick='return delete1();'>删除</a>]");
											//}else out.println("[删除]");           			 	
											out.println("</td></tr>");
											i++;
										}

									}
								%>
							
							<tbody>
						</table>

						<table style= "font-size:12px">

							<tr>
								<td></td>
								<td></td>
								<td align=left>
									<%
										String url ="VerifylogList.jsp";
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

							</tbody>
						</table></td>
				</tr>
		</table>


	</center>
</body>
</html>