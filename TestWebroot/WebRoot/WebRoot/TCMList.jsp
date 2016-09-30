<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.Iterator,cn.ac.sklois.imm.admin.UserBean"  %>
    
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
<script type="text/javascript" src="js/myjs.js"></script>
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
		var grid = new Ext.ux.grid.TableGrid("tbTcmList", {
			title : 'TCM信息',
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

	function deleteUser(id,name){		
		var val = confirm("您确定要删除用户 "  + name + " 吗??");
		if (val) {
		    var result = ajaxSyncCall('servlet/deleteUserServlet?oid=' + id, null);
		    if (result.success) {
		    	alert("已成功删除用户!");
		    	thisGrid.getStore().reload();		
		    } else {
		    	alert(result.errorMsg);
		    }
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
					<td colspan="3"><img src="img/spacer.gif" width="1" height="1">
					</td>
				</tr>
				<tr>
					<td align="middle" background="img/banner_bg.gif" width="200"
						height="29"><br></td>
					<td background="img/banner_bg1.gif"><br></td>
					<td width="2"><br></td>
				</tr>
				<tr>
					<td colspan="3"><br></td>
				</tr>
			</tbody>
		</table>


		<table id="tbTcmList" border="0" cellpadding="2" cellspacing="0"
			width="98%" height="100%">
			<thead>
				<tr align="center" bgcolor="#b6d3fc" height="20">

					<th width="10%">用户名</th>
					<th width="36%">用户公钥</th>
					<th width="36%">EK公钥</th>
					<!--      
            		 	<td width="22%">邮箱地址</td>
            			<td width="20%">电话号码</td>
            			-->
					<th>操 作</th>
				</tr>
			</thead>
			<tbody>

				<%
					ArrayList al = (ArrayList) request.getSession(true).getAttribute(
							"TCM");
					if (al == null) {
						out.println("no users!");
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

						UserBean a = null;

						int i = 0;
						while ((i + (currentpage - 1) * 20) < al.size() && i < 20) {
							Object obj = al.get(i + (currentpage - 1) * 20);
							a = (UserBean) obj;
							out.println("<tr style='background-color: rgb(254, 249, 226);' onmouseover='moveRow(this)' align='center' height='20'>");

							out.println("<td>");
							out.println("<a href='servlet/viewUserServlet?oid="
									+ a.getID() + "&flag=pass'>" + a.getName()
									+ "</a>&nbsp;&nbsp;&nbsp;");
							out.println("</td>");
							
							String str = a.getpubkey();
			        		String msg = str.substring(0,50) + "<br>"+ str.substring(51,100) + "<br>"+ str.substring(101,130);
							out.println("<td>" + msg + "</td>");
							
							String str2 = a.getEK();
			        		String ek = str2.substring(0,50) + "<br>"+ str2.substring(51,100) + "<br>"+ str2.substring(101,130);
							out.println("<td>" + ek + "</td>");

							out.println("<td>");
							//out.println("<br><SELECT name='avalue'> <OPTION value=2 selected>全部</OPTION><OPTION value=1>可信</OPTION> <OPTION value=0>不可信</OPTION></SELECT>");
							out.println("<a href='servlet/userDetailServlet?oid="
									+ a.getID()
									+ "&flag=pass' target='main'>白名单</a>&nbsp;&nbsp;&nbsp;");
							out.println("<a href='servlet/historyDetailServlet?oid="
									+ a.getID()
									+ "&flag=pass' target='main'>历史记录</a>&nbsp;&nbsp;&nbsp;");
							if (!a.getID().equals("1000")) {
								out.println("[<a href='javascript:void(0)' "
										+ " onclick=deleteUser(" + a.getID() + ",'" + a.getName() + "')>删除</a>]");
							} else
								out.println("[删除]");
							out.println("</td></tr>");
							i++;
						}

					}
				%>

			</tbody>
		</table>

		<table style= "font-size:12px">
				<tr>
		          <td ></td> 
		        <td width="40%"></td>
		          <td align=left>
				<%
					String url ="TCMList.jsp";
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
			</td></tr>
		</table>



	</center>
</body>
</html>