<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset=UTF-8"><LINK 
href="img/flower.css" type=text/css rel=StyleSheet>
<STYLE type=text/css></STYLE>

<SCRIPT language=JavaScript>

    function init()
    {
 	 	document.forms[0].organization.selectedIndex=0;
    	document.forms[0].attrtype.selectedIndex=0;
    	document.forms[0].attrvalue.selectedIndex=0;
    	document.forms[0].department.selectedIndex=0;
    }

	
</SCRIPT>

<META content="Microsoft FrontPage 5.0" name=GENERATOR></HEAD>
<BODY onload=init()>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>

    <TD colSpan=3><IMG height=1 src="img/spacer.gif" width=1></TD></TR>
  <TR>
    <TD align=middle width=200 background=img/banner_bg.gif 
      height=29><BR><BR></TD>
    <TD background=img/banner_bg1.gif>&nbsp; </TD>
    <TD width=2><IMG height=29 src="img/banner_bg2.gif" width=2></TD></TR>
  <TR>
    <TD colSpan=3><IMG height=1 src="img/spacer.gif" 
  width=1></TD></TR>
  </TBODY></TABLE>
  <BR>
  
<FORM action=servlet/exportListMappingServlet method=post target=attbottom>


<TABLE width="70%">
  <TBODY>
  <TR>
    <TD width="20%">软件类型：<br><SELECT style="WIDTH: 150px" name="sclass"> 
        				<OPTION value=0 selected>全部类型</OPTION>
        				<OPTION value=1>操作系统</OPTION> 
        				<OPTION value=2>网络软件</OPTION>
        				<OPTION value=3>系统工具</OPTION>
        				<OPTION value=4>应用软件</OPTION>
        				<OPTION value=5>图形图像</OPTION>
        				<OPTION value=6>多媒体</OPTION>
        				<OPTION value=7>办公软件</OPTION>
        				<OPTION value=8>游戏娱乐</OPTION>        				
        				<OPTION value=9>编程开发</OPTION>
        				<OPTION value=10>杀毒安全</OPTION>
        				<OPTION value=11>系统补丁</OPTION>
        				<OPTION value=12>软件补丁</OPTION>   				
        			</SELECT> </TD>
    <TD width="15%">软件名(支持模糊查找)：<br> <INPUT type="text" name="sname" size="20"> </TD>
    <TD width="15%">软件版本：<br> <INPUT type="text" name="sedition" size="20"> </TD>
    
    <TD width="30%">属性名(支持模糊查找)：<br> <INPUT type="text" name="aname" size="30"> </TD>
    <TD width="18%">属性值：<br><SELECT style="WIDTH: 130px" name="avalue"> 
        				<OPTION value=2 selected>全部</OPTION>
        				<OPTION value=1>可信</OPTION> 
        				<OPTION value=0>不可信</OPTION>
        				
        									
        			</SELECT>  </TD>
    
  </TR>
  
  <TR>
    <TD align="center"  width="60%">
  </TD>
  <TD align="center"  width="60%">
  </TD>
  <TD align="center"  width="60%"><INPUT type=submit value=提交查询 name=commit> 
  </TD>
  
  </TR></TBODY></TABLE></FORM></BODY></HTML>