<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>login</title>
<style><!--
		body,td,a,p{font-family:arial,sans-serif; font-size: 12px}
		.edit{font-family:courier new; font-size: 12px; border: #000000 1px solid}
	--></style>
</head>
<body>

<form action="servlet/loginservlet" method="post" name="f">
<table border="0" cellpadding="0" cellspacing="0">
	<tbody><tr><td><img src="img/login.gif" alt="Login" width="366"></td></tr>
	<tr><td><table bgcolor="#e3e3e3" border="0" cellpadding="0" cellspacing="0" width="366">
		<tbody><tr><td height="12"></td></tr>

		<tr><td><table border="0" cellpadding="0" cellspacing="0">
            <tbody><tr> 
              <td width="20"></td>
              <td><strong>���ż���ƽ̨���Թ���ϵͳ:</strong></td>
            </tr>
          </tbody></table></td></tr><tr>
		</tr><tr><td><table border="0" cellpadding="0" cellspacing="2">
			<tbody><tr><td width="40"></td><td>1. ������ά�������˻�</td></tr>
			<tr><td width="40"></td><td>2. �½�ӳ���ϵ</td></tr>
			<tr><td width="40"></td><td>3. ��ѯӳ���ϵ������</td></tr>
		</tbody></table></td></tr><tr>

		</tr><tr><td height="6"></td></tr>
		<tr><td bgcolor="#ff0000" height="1"></td></tr>
		<tr><td height="6"></td></tr>

		<tr><td><table border="0" cellpadding="0" cellspacing="0"><tbody><tr>
			<td width="20"></td>
			<td>�������������������벢��GO��¼:</td>
			<td width="20"></td>
		</tr>
		
		
		</tbody></table></td></tr><tr>

		</tr><tr><td height="10"></td></tr>
		<tr><td align="center">
			<table border="0" cellpadding="0" cellspacing="8">
				<tbody><tr>
					<td align="right"><b>����:</b></td>
					<td><input name="username" class="edit" type="text"></td>
				</tr>
				<tr>
					<td align="right"><b>����:</b></td>
					<td><input name="password" class="edit" type="password"></td>
					<td><input src="img/go.gif" alt="GO" type="image"></td>
				</tr>
			</tbody></table>
		</td></tr>
		<tr><td height="10"></td></tr>
		<tr><td align="center"> <a href="XXX"><font size="+1"><strong>���ǵ������˻��������ˣ�</strong></font></a></td></tr>
		<tr><td height="12"></td></tr>
	</tbody></table></td></tr>
	<tr>
      <td><img src="img/first-time-users.gif" alt="First time users" width="367" height="14"></td>
    </tr>
	<tr><td><table bgcolor="#e3e3e3" border="0" cellpadding="0" cellspacing="0" width="366">
		<tbody><tr><td height="12"></td></tr>
		<tr><td><table border="0" cellpadding="0" cellspacing="0"><tbody><tr>
			<td width="20"></td>
			      <td><strong>������� <font size="+1">�״�ʹ��</font>, �뽨��һ�����˺�</strong></td>
		</tr></tbody></table></td></tr><tr>
		</tr><tr><td height="10"></td></tr>
		<tr><td align="center">
			<a href="register.jsp"><img src="img/create-profile.gif" alt="Create a profile" border="0"></a>
		</td></tr>
		<tr><td height="14"></td></tr>

		<tr><td bgcolor="#000000" height="1"></td></tr>
		<tr><td height="10"></td></tr>
		<tr><td><table border="0" cellpadding="0" cellspacing="0"><tbody><tr>
			<td width="20"></td>
			<td>�����κ����⣬��email��ϵ <a href="mailto:XXX@is.iscas.ac.cn">XXX@is.iscas.ac.cn</a></td>
		</tr></tbody></table></td></tr><tr>
		</tr><tr><td height="10"></td></tr>
		<tr><td bgcolor="#000000" height="1"></td></tr>
	</tbody></table></td></tr>
</tbody></table>

</form>

</body>
</html>