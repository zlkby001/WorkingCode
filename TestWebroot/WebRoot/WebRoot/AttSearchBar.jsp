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

<META content="MSHTML 6.00.6000.16788" name=GENERATOR></HEAD>
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
  width=1></TD></TR></TBODY></TABLE><BR>
  
<FORM action=servlet/attFindServlet method=post target=attbottom>


<TABLE width="100%">
  <TBODY>
  <TR>
    <TD width="30%">属性等级：<br><SELECT style="WIDTH: 150px" name="aclass"> 
        				<OPTION value=" " selected>全部</OPTION>
        				<OPTION value=1>1</OPTION> 
        				<OPTION value=2>2</OPTION>
        				<OPTION value=3>3</OPTION>
        				<OPTION value=4>4</OPTION>
        				<OPTION value=5>5</OPTION>
        				<OPTION value=6>6</OPTION>
        				<OPTION value=7>7</OPTION>
        				<OPTION value=8>8</OPTION>
        				<OPTION value=9>9</OPTION>
        				<OPTION value=10>10</OPTION>
        				
        			</SELECT> </TD>
    <TD width="20%">属性ID：<br> <INPUT type="text" name="aid"> </TD>
    <TD width="50%">属性名(支持模糊查找)：<br> <INPUT type="text" name="aname" size="55"> </TD>
  </TR>
  
  <TR>
    <TD align=right colSpan=6><INPUT type=submit value=提交查询 name=commit> 
  </TD></TR></TBODY></TABLE></FORM></BODY></HTML>
