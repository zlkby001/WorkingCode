<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page
	import="java.util.ArrayList,java.util.Iterator,cn.ac.sklois.imm.admin.AdminBean"%>

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

.x-grid3-viewport {
	text-align: left;
}
</style>


<script language="JavaScript">
	var thisGrid;
	Ext.onReady(function() {
		// create the grid
		var grid = new Ext.ux.grid.TableGrid("tbAdminList", {
			title : '用户管理',
			trackMouseOver : true,
			loadMask : true,
			header : true,
			enableHdMenu : false,
			collapsible : true,
			stripeRows : true,
			viewConfig : {
				forceFit : true,
				enableRowBody : true
			}
		});
		thisGrid = grid;

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
<body leftmargin="4" topmargin="0" style="font-size:12px;" marginheight="0"
	marginwidth="0">
	<center>

		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tbody>

				<tr>
					<td colspan="3">ng<img src="img/spacer.gif" width="1"
						height="1">
					</td>
				</tr>
				<tr>
					<td align="middle" background="img/banner_bg.gif" width="200"
						height="29"><br>
					</td>
					<td background="img/banner_bg1.gif"><br>
					</td>
					<td width="2"><br>
					</td>
				</tr>
				<tr>
					<td colspan="3"><br>
					</td>
				</tr>
			</tbody>
		</table>

		<table id="tbAdminList" border="0" cellpadding="2" cellspacing="0" 
			width="98%" height="100%">

			<thead>
				<tr>
					<th width="5%">ID号</th>
					<th width="15%">用户名</th>
					<th width="5%">性别</th>
					<th width="20%">用户类别</th>
					<th width="10%">邮箱地址</th>
					<th width="10%">电话号码</th>
					<th>操 作</th>
				</tr>
			</thead>
	
			<tbody>

				<%
					ArrayList al = (ArrayList) request.getAttribute("c");
					if (al == null) {
						out.println("twwwwssss");
					}
				%>

				<%
					AdminBean a = null;
					Iterator it = al.iterator();
					while (it.hasNext()) {
						Object obj = it.next();
						a = (AdminBean) obj;
						out.println("<tr style='background-color: rgb(254, 249, 226);' onmouseover='moveRow(this)' align='center' height='20'>");
						out.println("<td>" + a.getOperatorID() + "</td>");
						out.println("<td>" + a.getName() + "</td>");
						String gender = a.getGender();
						if (gender.equalsIgnoreCase("M")) {
							out.println("<td>男</td>");
						} else {
							out.println("<td>女</td>");
						}
						String user = a.getEndType();
						if(user.equalsIgnoreCase("client")){
							out.println("<td>客户端用户</td>");
						}else {
							out.println("<td>服务端管理员</td>");
						}
						out.println("<td>" + a.getEmail() + "</td>");
						out.println("<td>" + a.getPhoneNum() + "</td>");
						
						out.println("<td>");
						out.println("<a href='servlet/adminDetailServlet?oid="
								+ a.getOperatorID()
								+ "&flag=pass'>详情</a>&nbsp;&nbsp;&nbsp;");
						String id = (String) request.getSession(true).getAttribute("id");
						System.out.println("idid:"+id);
						if (!a.getOperatorID().equals(id)&&!a.getOperatorID().equals("1000")) {
							out.println("[<a href='servlet/changepasswordServlet?oid="
									+ a.getOperatorID()
									+ "&flag=pass'>修改</a>]");
							out.println("[<a href='servlet/deleteAdminServlet?oid="
									+ a.getOperatorID()
									+ "&flag=pass' onclick='return delete1();'>删除</a>]");
						} else
						{
							out.println("[修改]");
							out.println("[删除]");
						}	
						
						
						

						out.println("</td></tr>");
					}
				%>

			</tbody>
		</table>


	</center>
</body>
</html>