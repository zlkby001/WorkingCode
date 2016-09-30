<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style type="text/css">
<!--
BODY {
	background-color: #CEECFF;
}
#page {
	position:relative;
	top:100px;
	width:800px;
	height: 800px;
	background-color: ;
	margin-left: auto;
	margin-right: auto;
	
}
a.item:link {
	color: #666666;
	text-decoration: none
}
a.item:visited {
	color: #666666;
	text-decoration: none
}
a.item:hover {
	color: #ffffff;
	text-decoration: none
}
a.item:active {
	color: #666666;
	text-decoration: none
}
}
.STYLE1 {
	color: #000000
}
.STYLE2 {
	color: #FFFFFF;
	font-size:14px
}
.STYLE3 {
	font-size: 12px
}
-->
</style>
<html>
<head>
<title>平台身份管理系统 | 用户注册/User Register</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="forum/skin/blue/skin.css" rel="stylesheet" type="text/css">
<script src="js/ajax.js" language="javascript" charset="UTF-8"></script>
<style type="text/css">
<!--
.STYLE4 {
	color: #000000
}
.STYLE5 {
	color: #FF0000
}
-->
</style>
</head>

<body bgcolor="#FFFFFF" text="#000000">
<!--  <center>
<iframe frameborder="no" width="830" src = "top.html" ></iframe>
</center>-->
<div id=page>
  <% request.setCharacterEncoding("UTF-8");%>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
    <tbody>
      <tr>
        <td ><table width="100%" cellspacing="0" cellpadding="0" align="center" border="0" height="86">
            <tr>
              <td width="600px" height="100%" background="img/topback.jpg"/>
              <td width="40%" height="100%" style="background-color: #417cbe;"><table width="100%" height="79%" border="0" align="center" cellpadding="0" cellspacing="0" >
                  <tr>
                    <td width="100%" height="4%" align="center" valign="top" fontsize="">&nbsp;</td>
                  </tr>
                  <!--DWLayoutTable-->
                  <tr>
                    <td width="98%" height="%96" align="center" valign="top"><div align="right" class="STYLE3"><a href="login.jsp">首页</a>&nbsp;</div></td>
                    <td width="2%" height="%96">&nbsp;</td>
                  </tr>
                </table></td>
            </tr>
          </table></td>
      </tr>
    </tbody>
  </table>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
    <tbody>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF" ></td>
      </tr>
      <tr>
        <td width="36" height="25" bgcolor="#0066CC" >&nbsp;</td>
        <td width="794" bgcolor="#3366CC" valign="middle"><span class="STYLE2">新管理员注册&nbsp;&nbsp; <font color=red>*号为必填内容</font></span><br></td>
      </tr>
    </tbody>
  </table>
  <form method="post" action="servlet/registerservlet"  onsubmit="return VerifyInput()">
    <table width="100%" align=center cellspacing=0 cellpadding=0 border=0>
      <tr>
        <td bgcolor=#D3D3D3><table width=100% border=0 cellpadding=0 cellspacing=1>
            <tr>
              <td align=center bgcolor="#FFFFFF"><table border=0 cellpadding=0 cellspacing=0 width=100%>
                </table>
                <br>
                <table width=100% height="500px" border=0 cellpadding=0 cellspacing=1 bgcolor="#CCCCCC">
                  <tr>
                    <td height="28" align=left bgcolor="#FFFFFF">&nbsp;用户名:</td>
                    <td align=left bgcolor="#FFFFFF">&nbsp;
                      <input name="name" type=text size=20 maxlength="50">
                      <font color="#FF0000"> *&nbsp;</font> &nbsp;&nbsp;&nbsp; <font color="red"><span id="span_RegName"></span><span id="RegName"></span> </font></td>
                  </tr>
                  <tr>
                    <td height="28" align=left bgcolor="#FFFFFF">&nbsp;Email:</td>
                    <td align=left bgcolor="#FFFFFF">&nbsp;
                      <input name="email" type=text size=20 maxlength="50" ">
                      <font color="red"> * </font> &nbsp;&nbsp;&nbsp; <font color="red"><span id="span_RegEmail"></span><span id="RegEmail"></span> </font></td>
                  </tr>
                  <tr>
                    <td width="128" height="28" align=left bgcolor="#FFFFFF">&nbsp;密码:</td>
                    <td align=left valign="top" bgcolor="#FFFFFF"><br>
                      &nbsp;
                      <input name="password1" type=password size=20 maxlength="20">
                      
                    <font color="#FF0000">*</font> <span class="STYLE4"></span></td>
                  </tr>
                  <tr>
                  <td  width="128" height="28" align=left bgcolor="#FFFFFF">确认密码:
                    </td>
                    <td align=left valign="top" bgcolor="#FFFFFF">
                    <br>&nbsp;
                      <input name="password2" type=password size=20 maxlength="20">
                      <font color="#FF0000"> *</font><span class="STYLE4">
                      </td>  
                  </tr>
                  <tr>
                    <td height="28" align=left bgcolor="#FFFFFF">&nbsp;性别:</td>
                    <td height="25" align=left valign="middle" bgcolor="#FFFFFF">&nbsp;
                      <input type=radio name=gender value=M checked>
                      男
                      <input type=radio name=gender value=F>
                      女                    &nbsp;</td>
                  </tr>
                  <tr>
                    <td height="28" align=left bgcolor="#FFFFFF">&nbsp;通讯地址:</td>
                    <td height="25" align=left valign="middle" bgcolor="#FFFFFF">&nbsp;
                      <input name="address" type=text size="30" maxlength=50></td>
                  </tr>
                  <tr>
                    <td height="28" align=left bgcolor="#FFFFFF">&nbsp;联络电话：</td>
                    <td height="25" align=left valign="middle" bgcolor="#FFFFFF">&nbsp;
                      <input name="phonenum" type=text size="30" maxlength=50>
                      (您的联络电话，请写明区号，例如: 010-62661710)</td>
                  </tr>
                  <tr>
                    <td height="28" align=left bgcolor="#FFFFFF">&nbsp;证件类型:</td>
                    <td align=left valign="middle" bgcolor="#FFFFFF">&nbsp;
                      <input name="certclass" type=text size=20 maxlength="20">
                      <font color="#FF0000"> * </font><span class="STYLE1">例如：身份证，军官证</span></td>
                  </tr>
                  <tr>
                    <td height="28" align=left bgcolor="#FFFFFF">&nbsp;证件号:</td>
                    <td align=left valign="middle" bgcolor="#FFFFFF">&nbsp;
                      <input name="certnumber" type=text size=40 maxlength="20">
                      <font color="#FF0000"> * </font><span class="STYLE1">仅供审核用，不会泄露您的隐私！</span></td>
                  </tr>
                </table></td>
            </tr>
            <tr>
              <td bgcolor="#FFFFFF"><table border=0 cellpadding=0 cellspacing=0 width=100%>
                  <tr valign=bottom>
                    <td height=41 align="center" valign="middle" ><font color="#FF0000">&nbsp; </font>
                      <input name=Write type=submit value="确定">
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <input name=reset type=reset value="重置"></td>
                  </tr>
                </table></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </form>
</div>
</body>
</html>