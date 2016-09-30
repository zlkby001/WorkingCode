
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>	
<title>操作结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
BODY {FONT-SIZE: 9pt; COLOR: #2D54A1; LINE-HEIGHT: 16pt; FONT-FAMILY: "Tahoma", "宋体"; TEXT-DECORATION: none;padding-top:80px;}
TABLE {FONT-SIZE: 9pt; COLOR: #2D54A1; LINE-HEIGHT: 16pt; FONT-FAMILY: "Tahoma", "宋体"; TEXT-DECORATION: none}
TD {FONT-SIZE: 9pt; COLOR: #2D54A1; LINE-HEIGHT: 16pt; FONT-FAMILY: "Tahoma", "宋体"; TEXT-DECORATION: none}
BODY {SCROLLBAR-HIGHLIGHT-COLOR: buttonface; SCROLLBAR-SHADOW-COLOR: buttonface; SCROLLBAR-3DLIGHT-COLOR: buttonhighlight; SCROLLBAR-TRACK-COLOR: #eeeeee; BACKGROUND-COLOR: #ffffff}
A {FONT-SIZE: 9pt; COLOR: #ff0000; LINE-HEIGHT: 16pt; FONT-FAMILY: "Tahoma", "宋体"; TEXT-DECORATION: none}
A:hover {FONT-SIZE: 9pt; COLOR: #ff0000; LINE-HEIGHT: 16pt; FONT-FAMILY: "Tahoma", "宋体"; TEXT-DECORATION: underline}
H1 {FONT-SIZE: 14PX; FONT-FAMILY: "Tahoma", "宋体"}
hr{color:#ccc;}
</style>

<meta content="MSHTML 6.00.2800.1458" name="GENERATOR"></head><body>
<div style="border: 1px solid rgb(221, 221, 221); margin: auto; padding: 30px 45px 20px 20px; width: 600px;">
<table cepadding="0" align="center" border="0" cellspacing="0" width="600">
  <tbody>
  <tr colspan="2">
    <td align="middle" valign="top"> 
    </td><td>

    </td><td>
      <h1><img src="../img/result.gif" width="400" height="66"></h1>
	  <hr size="1" noshade="noshade">
      <p>☉ 操作结果：</p>
      <ul>
        <li><% String r=(String)request.getAttribute("result");		
			out.print(r);			
			%>
        </li>  
      </ul>
      
      <p>☉ <a href="../addAttribute.jsp">点击返回</a></p>
      <hr size="1" noshade="noshade">
      <p>☉ 如果您还有任何疑问、意见、建议、咨询，请<a href="mailto:XXX@gmail.com">联系管理员</a> 
      <br>
      &nbsp;&nbsp;&nbsp;<br>
      </p></td></tr></tbody></table>
	  </div>
</body></html>