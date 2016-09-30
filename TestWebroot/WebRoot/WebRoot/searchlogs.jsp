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

		<TABLE width="80%" height="180px" style="font-size:12px">
			<TBODY>
				<TR>
					<TD>终端IP地址<br>
					</TD>
					<TD width="67%"><INPUT id="ip" type="text" name="ip" size="15">
					</TD>
				</TR>
				<TR>
					<TD>用户名<br>
					</TD>
					<TD><INPUT id="username" type="text" name="username" size="15">
					</TD>
				</TR>
				<TR>
					<TD>用户公钥<br>
					</TD>
					<TD><INPUT id="pubkey" type="text" name="pubkey" size="15">
					</TD>
				</TR>
				<TR>
					<TD>证明时间<br>
					</TD>
					<TD>
					<INPUT id="verifydate" type="text" name="verifydate"
						size="15">
<!-- 						<input name="verifydate" class="easyui-datetimebox" id="verifydate" -->
<!-- 						data-options="required:false,showSeconds:false" -->
<!-- 						value="5/29/2014 2:2" style="width:150px"> -->
						</TD>
				</TR>
				<TR>
					<TD width="33%" align="left"><INPUT type=submit value="提交查询"
						name="commit" onclick="reloadTree()"></TD>
				</TR>
				</TR>
			</TBODY>
		</TABLE>


	<p>(支持模糊查找)</p>
	<script language="JavaScript">
		
		function reloadTree() {
			var ip = document.getElementById('ip').value;
			var username = document.getElementById('username').value
			var pubkey = document.getElementById('pubkey').value;
			var verifydate = document.getElementById('verifydate').value;
			var addr = 'userTree2.jsp?ip=' + ip + '&username=' + username
					+ '&pubkey=' + pubkey + '&verifydate=' + verifydate;
			var html="<iframe width=100% height=800px border=0 frameSpacing=0  frameBorder=NO src='"+ addr+"'/>";
			//alert(html);
			
			Ext.get('userTreePanel').update(html);
			Ext.getCmp('lefttab').setActiveTab(1);	
		}
	</script>
</BODY>
</HTML>