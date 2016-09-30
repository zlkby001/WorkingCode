<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset="UTF-8"><LINK 
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
  
<FORM action=servlet/verifyFailFindServlet method=get target=attbottom>


<TABLE width="65%">
  <TBODY>
  <TR>
  	<TD width="23%">客户端IP：<br><INPUT type="text" name="clientIP" size="20"> </TD>
  	<TD >软件名称(支持模糊查找)：<br><INPUT type="text" name="sname" size="20"> </TD>
    <TD >文件名(支持模糊查找)：<br><INPUT type="text" name="fname" size="20"> </TD>

    <TD width="20%">信任状态：<br><SELECT style="WIDTH: 130px" name="avalue"> 
        				<OPTION value=-1 selected>全部</OPTION>
        				<OPTION value=0>不可信</OPTION> 
        				<OPTION value=2>未登记</OPTION>
        				
        									
        			</SELECT>  </TD>
    <TD width="15%" align="left">
    	<INPUT type=submit value=提交查询 name=commit>
    </TD>
    
  </TR>
  
</TBODY></TABLE></FORM>
 <SCRIPT language=JavaScript>
if(window.name != "bencalie"){
window.parent.frames[1].location.reload();
//alert("test");
//alert(document.form1.sname.value);
//window.parent.frames[1].location.href=window.parent.frames[1].location.href;
//window.parent.frames[0].window.document.forms[0].submit();
//this.document.form1.submit();
//window.document.forms[0].submit();
//form1.submit();
     window.name = "bencalie";
}
else{
     window.name = "";
}
</SCRIPT>
</BODY></HTML>