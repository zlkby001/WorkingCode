<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0045)http://192.168.5.64:8080/webdgs/admin/top.jsp -->
<HTML>
<HEAD>
<TITLE>top</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<LINK href="img/default.css" type=text/css rel=stylesheet>
<META content="MSHTML 6.00.6000.16788" name=GENERATOR>
<STYLE type=text/css>
#toptable {
	background-color: green;
	width: 100%;
	color: blue;
	height: 20px;
}
</STYLE>
</HEAD>
<BODY leftMargin=0 topMargin=0>
	<TABLE id="toptable" cellSpacing=0 cellPadding=0 border=0>
		<TBODY>
			<TR>
				<TD width=5% height=27>&nbsp;</TD>


				<TD style="PADDING-RIGHT: 20px">
					<TABLE class=wht width=65% align=left>
						<TBODY>

							<TR>
								<TD class=wht align=left width=300>欢迎您！&nbsp;管理员 <%
									String name = (String) request.getSession().getAttribute("name");
									out.print(name);
								%>
								</TD>
								<TD class=wht align=left width=100></TD>
							</TR>
						</TBODY>
					</TABLE>
					<TABLE class=wht width=30% align=left>
						<TD width=300 align=right>&nbsp;<a href="logout.jsp"
							target="_parent">退出系统</a></TD>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</BODY>
</HTML>

