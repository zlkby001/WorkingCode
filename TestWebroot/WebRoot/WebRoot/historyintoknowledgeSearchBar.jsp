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
      height=29><BR><BR></TD>
    <TD background=img/banner_bg1.gif>&nbsp; </TD>
    <TD width=2><IMG height=29 src="img/banner_bg2.gif" width=2></TD></TR>
  <TR>
    <TD colSpan=3><IMG height=1 src="img/spacer.gif" 
  width=1></TD></TR>
  </TBODY></TABLE>
  <BR>
  
<FORM action=servlet/historyintoknowledgeServlet id="form1" name="form1" method=get target=attbottom >


<TABLE width="65%" height="50px">
  <TBODY>
  <TR>
    <TD width="23%">软件类型：<br><SELECT style="WIDTH: 150px" name="sclass"> 
        				<OPTION value=0 selected>全部类型</OPTION>d
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
    <TD >软件名称(支持模糊查找)<br><INPUT type="text" name="softwareName" size="20"> </TD>
    <TD >文件名(支持模糊查找)<br><INPUT type="text" name="sname" size="20"> </TD>
    <TD >验证日期(支持模糊查找)<br><INPUT type="text" name="sdate" size="20"> </TD>
    
    <TD width="20%">信任状态：<br><SELECT style="WIDTH: 130px" name="avalue"> 
        				<OPTION value=2 selected>全部</OPTION>
        				<OPTION value=1>可信</OPTION> 
        				<OPTION value=0>不可信</OPTION>
        				
        									
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

function doSubmit() {
    document.getElementById('form1').action = "servlet/historyintoknowledgeServlet";
    document.getElementById('form1').submit();
}

    function init()
    {
 	 	doSubmit();
    }

	
</SCRIPT>
  </BODY></HTML>