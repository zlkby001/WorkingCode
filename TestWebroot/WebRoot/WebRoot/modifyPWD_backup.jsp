<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>修改密码</title>
</head>
<body bgcolor="#4780C3">

<form  action="servlet/modifyPWDServlet" method="post">
<p align="center">
旧密码：<input type="password" name="oldpwd" size="90" > 
</p>
 <p align="center">
新密码：<input type="password" name="pwd1" size="90" ><br>
再输入一遍：<input type="password" name="pwd2" size="90" >
<br>
<input type="submit" value="提交"> <input type="reset" value="重置">
</p>
  <br>
</form>


</body>
</html>