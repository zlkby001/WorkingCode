<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<style type="text/css">
* {
margin: 0;
padding: 0;
border: 1;
}

html,body {
height:100%;   
}

#abc {
height:100%;

}

#abc_last {
position:absolute; 
top:35%; 
left:35%;
}

#b{ 

position: absolute; 
margin-top: -57px;
margin-bottom: -57px; 
margin-left: 0px;
overflow: hidden;
} 


 
</style>



<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>importMappings</title>



</head>
<body >
<form   action="servlet/do_importServlet"   method="post" ENCTYPE="multipart/form-data">  
<div id="abc">
<div id="abc_last" >
 <table  border="1"  cellpadding="0" cellspacing="0" > 
<tbody><tr><td> 

<table>
<tbody>
<tr><td>
<br>
</td></tr>

<tr height="14" ><td>
 选择要导入的xml文件：
</td></tr>
<tr height="30"><td height="30">
   <input   type="file"   name="mappings"   size="40">    
    </td></tr>  
    <tr><td>
  <center><input   type="submit"   name="g"   value="提交"> </center>      
</td></tr> 


<tr height="30"><td>
<ul>
您所提交的xml文件必须为：
<li>☉本系统导出的xml文件</li>
<li>☉或者符合标准格式的文件。
</li>
<li>☉<a href="xmlexample.xml">点击查看本系统支持的xml格式</a></li>
<ul>
</td></tr>


</tbody>
</table>

</td></tr> 
</tbody>
</table>
</div>
</div>  

</form> 
</body> 
</html>