<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>



<HTML><HEAD>

<META http-equiv=Content-Type content="text/html; charset=UTF-8"><LINK 
href="img/flower.css" type=text/css rel=StyleSheet>
<STYLE type=text/css></STYLE>



<META content="Microsoft FrontPage 5.0" name=GENERATOR></HEAD>
<BODY onload=init()>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>

    <TD colSpan=3><IMG height=1 src="img/spacer.gif" width=1></TD></TR>
  <TR>
    <TD align=middle width=200 background=img/banner_bg.gif 
      height=29></TD>
    <TD background=img/banner_bg1.gif>&nbsp; </TD>
    <TD width=2><IMG height=29 src="img/banner_bg2.gif" width=2></TD></TR>
  <TR>
    <TD colSpan=3><IMG height=1 src="img/spacer.gif" 
  width=1></TD></TR>
  </TBODY></TABLE>


<FORM action=servlet/usbInfoServlet id="form1" name="form1" method=get target=attbottom >


<TABLE height="50px">
  <TBODY>
  <TR>
    
    <TD >制造商<br><INPUT type="text" name="manufacture"  size="10"> </TD>
    <TD >序列号<br><INPUT type="text" name="sn"  size="10"> </TD>
    <TD >终端<br><INPUT type="text" name="terminal"  size="10"> </TD>
    <TD >日期<br><INPUT type="text" name="date"  size="10"> </TD>
    
    <TD align="left">
    	<INPUT type=submit value=提交查询 name=commit>
    </TD>
    
  </TR>
  
 </TBODY></TABLE></FORM>
  <SCRIPT language=JavaScript>
if(window.name != "bencalie"){
    window.parent.frames[1].location.reload();
     window.name = "bencalie";
}
else{
     window.name = "";
}

function doSubmit() {
    document.getElementById('form1').action = "servlet/usbInfoServlet";
    document.getElementById('form1').submit();
}

    function init()
    {
 	 	doSubmit();
    }

	
</SCRIPT>
  </BODY></HTML>