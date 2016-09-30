<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Register</title>
<style><!--
		body,td,a,p{font-family:arial,sans-serif; font-size: 12px}
		.title{font-family:arial,sans-serif; font-size: 16px; color=#FF0000}
		.edit{font-family:arial,sans-serif; font-size: 12Px; border: #000000 1px solid}
	--></style>

</head>
<body>

<form action="servlet/registerservlet" method="post">

<table border="0" cellpadding="0" cellspacing="0"> 
	<tbody><tr><td><table bgcolor="#e3e3e3" border="0" cellpadding="0" cellspacing="0" width="400">
		<tbody><tr><td height="16"></td></tr>
		<tr><td>
			<i>注意:</i><br>
			&nbsp;&nbsp;黑体字段为必须输入字段.
		</td></tr>
		<tr><td height="16"></td></tr>
		<tr><td><table border="0" cellpadding="0" cellspacing="0"><tbody><tr>
			<td width="20"></td>
			<td class="title">1. 个人信息.</td>
			<td width="20"></td>
		</tr></tbody></table></td></tr><tr>
		</tr><tr><td height="12"></td></tr>
		<tr><td><table border="0" cellpadding="0" cellspacing="0"><tbody><tr>
			<td width="20"></td>
			<td>请填写个人信息.</td>
			<td width="20"></td>
		</tr></tbody></table></td></tr><tr>
		</tr><tr><td height="10"></td></tr>
		<tr><td align="center">
                <table border="0" cellpadding="0" cellspacing="2" width="369">
                  <tbody><tr><td align="right"><b>姓名:</b></td>
	                <td> 
                      <p> 
                        <input name="name" class="edit" style="width: 240px;" value="" type="text">
                        <br>
                        <b>(1-100)（请输中文或英文名！）</b></p>
                    </td>

	</tr><tr><td align="right"><b>电邮:</b></td>
	<td><input name="email" class="edit" style="width: 240px;" value="" type="text"><br><b>(1-20@1-100)</b></td>

	</tr><tr><td align="right">电话:</td>
	<td><input name="phonenum" class="edit" style="width: 240px;" value="" type="text">(3-100)</td>

	</tr><tr><td align="right">地址:</td>
	<td><input name="address" class="edit" style="width: 240px;" value="" type="text">
	  (3-100)
	    <b>	  </b></td>

	</tr><tr>
	  <td align="right">&nbsp;</td>
	  <td>格式：城市＋街道门牌号＋单位＋部门</td>
	  </tr></tbody></table>
		</td></tr>
		
		<tr><td><table border="0" cellpadding="0" cellspacing="0"><tbody><tr>
			<td width="20"></td>
			<td class="title">2. 密码.</td>
			<td width="20"></td>
		</tr></tbody></table></td></tr><tr>
		</tr><tr><td height="12"></td></tr>
		<tr><td><table border="0" cellpadding="0" cellspacing="0"><tbody><tr>
			<td width="20"></td>
			<td>请输入密码两遍.</td>
			<td width="20"></td>
		</tr></tbody></table></td></tr>
		
		<tr>
		</tr><tr><td height="10"></td></tr>
		<tr><td align="center">
            <table border="0" cellpadding="0" cellspacing="2" width="369">
            <tbody>
				<tr><td align="right"><b>密码:</b></td><td>
				<input name="password1" class="edit" style="width: 186px;" value="" type="password"></td>
				</tr>
				<tr><td align="right"><b>再输一遍:</b></td><td>
				<input name="password2" class="edit" style="width: 186px;" value="" type="password"></td>
				</tr>
			</tbody>
			</table>
		
		
		<tr><td height="12"></td></tr>
		<tr><td align="center">
			<table border="0" cellpadding="0" cellspacing="0">
				<tbody><tr>
					<td><input name="" value="提交" type="submit"></td>
					<td width="10"></td>
					<td></td>
					<td width="10"></td>
				</tr>
			</tbody></table>
		</td></tr>
		<tr><td height="12"></td></tr>
	</tbody></table></td></tr>
</tbody></table>


</form>


</body>
</html>