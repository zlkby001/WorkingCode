<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>身份管理系统 Identity Manage System —— 用户及系统管理</title>
<meta content="身份管理系统，安全方便的管理用户身份" name="description" >
<meta content="身份管理，安全，用户身份，用户管理，系统管理" name="keywords" >

<script language="JavaScript">

function checkForm(form)
{
	if(form.username.value==""){
		confirm("请输入用户名！");
		return false;	
	}
	if(form.password.value==""){
		confirm("请输入密码！");
		return false;	
	}

}

</script>

	
<style type="text/css">
.page {	
	
	margin-left: 20px;
	margin-right: 20px;
	
}
#login {
	background-color: #39C;
	width:490px;
	height:310px;
	position:relative;
	margin-top: 20px;
	margin-right: auto;
	margin-left: auto;
	background-image: url(img/LoginBack.png);
	font-family: "黑体";
	font-size: 16px;
}

#body{
	background-color: #666;
	background-image: url(img/backImg.jpg);		
	background-attachment: fixed;
}
#top{
	width:600px;
	position:relative;
	margin-top: 200px;
	margin-right: auto;
	margin-left: auto;
	text-align:center;
	color:#000;
	font-size:36px;
	font-weight: normal;
	font-family: "黑体";
}

ul, menu, dir {
display: block;
list-style-type: disc;
-webkit-margin-before: 1em;
-webkit-margin-after: 1em;
-webkit-margin-start: 0px;
-webkit-margin-end: 0px;
-webkit-padding-start: 40px;
}

.copyright {
	width:80%;
	height: 72px;		
	position:relative;
	margin-top: 220px;
	margin-right: auto;
	margin-left: auto;
	text-align: center;
	
}

</style>
</head>

<body id=body >
<div class=page>
<div id=top>广州石化炼化分公司三厂</div>
<div id=login>
  <form action="servlet/loginservlet" method="post" name="f" onSubmit="checkForm(this);">
    <table  align="left" cellpadding="10" cellspacing="0" border="0">
      <tr >
        <td><p>&nbsp;</p>
          <p><br>
          </p></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td align="right" width="300px"><font class="title">用户名</font>：</td>
        <td><!--<input name="emailname" type="text" size="25" onBlur="checkemail()"><font color="red"><span id="RegName"></span></font></td>
            -->
          <font color="red">
            <input name="username" type="text" style="width:200px" height="26px">
          </font></td>
      </tr>
      <tr>
        <td align="right">&nbsp;&nbsp;&nbsp;&nbsp;密&nbsp;码： </td>
        <td valign="middle">
        <input type="password" name="password" style="width:200px" height="26px"></td>
      <tr>
        <td></td>
        <td><input name="submitimage" type="image" src="img/login3.png" align="middle"  width="100px" height="30px">
          </td>
      </tr>
  
        <tr>
    	
    
		<td ><a href="register.jsp" target="_self" >注册新用户</a>
		<a href="register1.jsp" target="_self"  >注册客户端用户</a></td>
      </tr>
      <tr></tr>
    </table>
  </form>
</div>
</div>
<script type="text/javascript">
function loadXMLDoc(dname) 
{
  var xmlDoc;
try //Internet Explorer
  {
   xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
   xmlDoc.async=false;
   xmlDoc.load(dname);
   return(xmlDoc);
  }
catch(e)
  {
  try //Firefox, Mozilla, Opera, etc.
    {
    xmlDoc=document.implementation.createDocument("","",null);
    xmlDoc.async=false;
    xmlDoc.load(dname);
    return(xmlDoc);
    }
  catch(e)
    {
    try 
      {
      var xmlhttp = new window.XMLHttpRequest();  
      xmlhttp.open("GET",dname,false);  
      xmlhttp.send(null);  
      xmlDoc = xmlhttp.responseXML.documentElement; 
      return(xmlDoc);
      }
  catch(e) {
   alert(e.message)
     }
    }
  }

return(null);
}

var xmlDoc1=loadXMLDoc("nameFile.xml");
//document.getElementById("top").innerHTML=dom.getElementsByTagName("name")[0].childNodes[0].nodeValue;
document.getElementById("top").innerHTML=xmlDoc1.getElementsByTagName("name")[0].childNodes[0].nodeValue;
</script>
<div class="copyright">
								
                            <p><a href="http://www.tofinosecurity.com.cn/">青岛多芬诺信息安全技术有限公司</a><a>,</a><a href="http://tca.iscas.ac.cn/">中科院软件所可信计算与信息保障实验室</a> &nbsp;&nbsp;&nbsp; 版权所有</p>
                        <p>Copyright ©  2015 TOFINO,TCA All Rights Reserved </p>
</div>

</body>
</html>
