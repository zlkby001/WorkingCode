<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�޸�����</title>
</head>
<body bgcolor="#4780C3">

<form  action="servlet/modifyPWDServlet" method="post">
<p align="center">
�����룺<input type="password" name="oldpwd" size="90" > 
</p>
 <p align="center">
�����룺<input type="password" name="pwd1" size="90" ><br>
������һ�飺<input type="password" name="pwd2" size="90" >
<br>
<input type="submit" value="�ύ"> <input type="reset" value="����">
</p>
  <br>
</form>


</body>
</html>