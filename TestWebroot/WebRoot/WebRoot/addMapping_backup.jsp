<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>addMapping</title>
</head>
<body bgcolor="#4780C3" link="#DE7108" vlink="yellow"><br>

  <form method="post" action="servlet/addMappingServlet" name="AddMapping"><p>&nbsp;</p><p align="center">&nbsp;属性名： <input type="text" value="AttName" name="AttName"></p><p align="center">&nbsp;属性值：<input type="text" value="AttValue" name="AttValue"></p><p align="center">&nbsp;完整性Hash值个数： <input type="text" value="1" name="HashNum"></p><div align="center">
</div><p align="center"> </p><div align="center">
</div><p align="center"> </p><p align="center">&nbsp;完整性Hash值：<input type="text" value="HashValues" name="HashValues"></p><p align="center">&nbsp;<input type="submit" value="提交" name="submit"></p><p>&nbsp;</p></form></body>

</html>