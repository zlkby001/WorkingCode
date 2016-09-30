<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	import="cn.ac.sklois.imm.admin.*"%>



<HTML>
<HEAD>

<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<LINK href="img/flower.css" type=text/css rel=StyleSheet>
<STYLE type=text/css></STYLE>

<META content="Microsoft FrontPage 5.0" name=GENERATOR>
</HEAD>
<BODY onload=init()>
	<TABLE cellSpacing=0 cellPadding=0 width="100%"  border=0>
		<TBODY>

			<TR>
				<TD width=200  align=middle background=img/banner_bg.gif ><BR>
					<BR></TD>
			  <TD background=img/banner_bg1.gif>&nbsp;</TD>
				<TD width=2><IMG  src="img/banner_bg2.gif" width=2>
				</TD>
			</TR>

		</TBODY>
	</TABLE>


	<FORM action=servlet/verifylogServlet name="form1" method=post
		target=attbottom1>


		<TABLE width="100%" height="50px">
			<TBODY>
				<TR>
					<TD>终端IP地址(支持模糊查找)<br> <INPUT id="ip" type="text" name="ip"
						size="20">
					</TD>
					<TD>用户名(支持模糊查找)<br> <INPUT id="username" type="text"
						name="username" size="20"></TD>
					<TD>用户公钥(支持模糊查找)<br> <INPUT id="pubkey" type="text" name="pubkey"
						size="20">
					</TD>
					<TD>证明时间(支持模糊查找)<br> <INPUT id="verifydate" type="text"
						name="verifydate" size="20"></TD>
				

				<TD width="33%" align="left"><INPUT type=submit value=提交查询
					name=commit>
				</TD>

				</TR>
			</TBODY>
		</TABLE>
	</FORM>

	<SCRIPT language=JavaScript>
		if (window.name != "bencalie") {
			window.parent.frames[1].location.reload();
			window.name = "bencalie";
		} else {
			window.name = "";
		}
		function init() {
			document.forms[0].organization.selectedIndex = 0;
			document.forms[0].attrtype.selectedIndex = 0;
			document.forms[0].attrvalue.selectedIndex = 0;
			document.forms[0].department.selectedIndex = 0;
		}

		function reloadTree() {
			var ip = $("#ip").attr("value");
			var username = $("#username").attr("value");
			var pubkey = $("#pubkey").attr("value");
			var verifydate = $("#verifydate").attr("value");
			var addr = 'userTree.jsp?ip=' + ip + '&username=' + username
					+ '&pubkey=' + pubkey + '&verifydate=' + verifydate;
			var tabs = $('#tt').tabs().tabs('tabs');
			var length = tabs.length;
			var title = tabs[0].panel('options').tab.text();
			if ($("#tt").tabs('enableTab', '按厂区')) {
				$("#tt").tabs('select', '按厂区');
			}
			var tab = tabs[1]; // get selected panel
			$('#tt').tabs('update', {
				tab : tab,
				//method:'get',
				options : {
					href : addr
				// the new content URL
				}
			});
			// call 'refresh' method for tab panel to update its content
			//var tab = $('#tt').tabs('getSelected');  // get selected panel
			//tab.panel('refresh', addr);
		}
	</SCRIPT>
</BODY>
</HTML>