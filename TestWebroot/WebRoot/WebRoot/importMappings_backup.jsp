<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>importMappings</title>
</head>
<body bgcolor="#4780C3" link="#DE7108" vlink="yellow">
选择要上传的文件：
<form   action="servlet/do_importServlet"   method="post" ENCTYPE="multipart/form-data">  
   <input   type="file"   name="mappings"   size="38">    
    <br>  
  <input   type="submit"   name="g"   value="提交">       
</form>

</body>
</html>