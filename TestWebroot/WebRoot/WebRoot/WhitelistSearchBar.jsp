<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="UTF-8"%>
<%@ page
	import="cn.ac.sklois.imm.admin.UserBean"%>

<%
UserBean a = (UserBean)request.getSession(true).getAttribute("a");
		int userid = Integer.parseInt(a.getID());
		
%>

<HTML><HEAD>

<META http-equiv=Content-Type content="text/html; charset=UTF-8"><LINK 
href="img/flower.css" type=text/css rel=StyleSheet>
<STYLE type=text/css></STYLE>



<META content="Microsoft FrontPage 5.0" name=GENERATOR></HEAD>
<BODY onload=init()>
    <link href="extjs3/resources/css/ext-all.css" rel="stylesheet" type="text/css">
     <link href="extjs3/resources/css/ext-all-notheme.css" rel="stylesheet" type="text/css">
     <link href="extjs3/resources/css/reset-min.css" rel="stylesheet" type="text/css">
     <link href="extjs3/resources/css/xtheme-blue.css" rel="stylesheet" type="text/css">
 <script src="extjs3/adapter/ext/ext-base.js" type="text/javascript"></script>    
 <script src="extjs3/ext-lang-zh_CN.js" type="text/javascript"></script> 
 <script type="text/javascript" src="extjs3/ext-all.js"> </script> 
 <script type="text/javascript" src="extjs3/ext-all-debug.js"> </script>  
<script language="JavaScript">

 
 
 function doSubmit() {
    document.getElementById('a123').action = "servlet/listWhiteServlet";
    document.getElementById('a123').submit();
}

function doSelf() {
   document.getElementById('a123').action = "selfAddWhite.jsp";
   document.getElementById('a123').target = "_parent";
    document.getElementById('a123').submit();
}

 function doSnapshot() {
    document.getElementById('a123').action = "servlet/createwlsnapshotServlet";
    document.getElementById('a123').submit();
}
function doRemark(){
  var re=prompt("请输入备注信息","");
  document.getElementById("beizhu").value = re;
  //alert(document.getElementById("beizhu").value);
  document.getElementById("beizhu").value = re;
  document.getElementById('a123').action = "servlet/createwlsnapshotServlet";
   document.getElementById('a123').submit();
/*  Ext.MessageBox.show(
                   {
                       name: "name",
                       title: '备注',
                       msg: '备注信息:',
                       width: 500 ,
                       height:10,
                       buttons: Ext.MessageBox.OKCANCEL,
                       multiline:  true ,
                       fn: showResult
         });  */ 
}
function doExport(){
	document.forms[0].action = "servlet/exportAllPagesWhitelistServlet";
    document.forms[0].submit();
}
function doUpload(){
 var userid=<%=userid%> 
 var filename=a123.importfile.value;
 alert(filename);
 document.getElementById('a123').method="post";
document.getElementById('a123').action = "servlet/uploadwhitelistServlet";
document.getElementById('a123').submit();
 
       
}

		function importxml()
		{
			
					
			
		//	window.location.href=;
			document.getElementById('a123').action ="importWhitelist.jsp";
			 document.getElementById('a123').target = "_parent";
    			document.getElementById('a123').submit();
		}

/* function  showResult(btn,text)
      {
      if(btn=="ok"){
                 
                document.getElementById("beizhu").value = text;
               document.getElementById('a123').action = "servlet/createwlsnapshotServlet";
              document.getElementById('a123').submit();
             
              }
      } */
function doImport() {
	var url="importWhitefromKnowledge.jsp?oid="+<%=userid%>;
	document.getElementById('a123').action = url;
   document.getElementById('a123').target = "_parent";
    document.getElementById('a123').submit();
/*
	Ext.MyWindow=Ext.extend(Ext.Window ,{
	xtype:"window",
	title:"选择用户和验证时间",
	width:400,
	height:250,
	layout:"absolute",
	initComponent: function(){
		this.items=[
			{
				xtype:"container",
				autoEl:"div",
				layout:"absolute"
			},
			{
				xtype:"label",
				text:"用户列表：",
				x:30,
				y:30
			},
			{
				id:'userList',
				xtype:"combo",
				triggerAction:"all",
				fieldLabel:"标签",
				anchor:"60%",
				x:100,
				y:20,
				valueField:'userid',
				displayField:'username',
				editable:false,
				store:{
					xtype:"jsonstore",
					autoLoad:false,
					proxy : new Ext.data.HttpProxy({
						url : 'servlet/comboServlet?type=userlist'
					}),
					fields:[
						'userid',
						'username'
						]
				},
				  listeners: {
				  select: function (a, b, c) {
					  uid = b.data.userid;
					  Ext.getCmp('verifyDate').clearValue();
					  //alert(uid);
					  }
				  }
			},
			{
				id:'verifyDate',
				xtype:"combo",
				triggerAction:"all",
				fieldLabel:"标签",
				anchor:"60%",
				x:100,
				y:50,
				valueField:'date',
				displayField:'date',
				editable:false,
				store:{
					xtype:"jsonstore",
					autoLoad:false,
					proxy : new Ext.data.HttpProxy({
						url : 'servlet/comboServlet?type=verifydate'
					}),
					//params: {type:'verifydate',userId:''},
					fields:[
						'date'
						]
				},
				listeners: {
				  focus: function () {
				  //alert('focus');
				  	var proxy = new Ext.data.HttpProxy({url:'servlet/comboServlet?type=verifydate'});
				  	var store = Ext.getCmp('verifyDate').getStore(); 	
					store.proxy = proxy;
					//alert(obj2str(store.proxy.url));
					store.reload();
					
					  }
				  }
			},
			{
				xtype:"label",
				text:"验证时间：",
				x:30,
				y:60
			},
			{
				xtype:"button",
				text:"确定",
				width:50,
				x:30,
				y:100,
				handler:function(){ 
				var user = Ext.getCmp('userList').getValue();
				var date = Ext.getCmp('verifyDate').getValue();
				if(user==''||date==''){
					Ext.Msg.alert("提示","用户名和验证时间不能为空！");
					return;
				}
				var url = 'servlet/gethistoryintoknowledgeServlet?oid=' +  user
							+ '&sdate='+ date
							+ '&kname=' + decodeURI(name);
				//alert(encodeURI(url));
				self.location = encodeURI(url);
				}
			}
		];
		Ext.MyWindow.superclass.initComponent.call(this);
	}
});
	var win = new top.Ext.MyWindow();
	//win.setSize(w, h);    //w为设置的宽度，h为设置的高度
    win.setPosition(100, 100);    //x为设置的x坐标，y为设置的y坐标
    win.show();    //显示窗口
    */
}
 
 </script>
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
  
 <form id="a123" name="a123" method=get target="attbottom"> 


<TABLE width="471" height="48">
  <TBODY>
  <TR>
  <!--  
    <TD width="23%">软件类型：<br><SELECT style="WIDTH: 150px" name="sclass"> 
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
        			
       
    <TD >软件名称(支持模糊查找)<br><INPUT type="text" name="softwareName" size="20"> </TD>
    -->
    <TD >文件名<br><INPUT type="text" name="sname" size="10"> </TD>
    <TD >完整性值<br><INPUT type="text" name="sdigest" size="10"> </TD>
    <!-- <TD >描述信息<br><INPUT type="text" name="description" size="18"> </TD>
    <TD >上传时间<br><INPUT type="text" name="time" size="18"> </TD>
    <TD >软件类型<br><INPUT type="text" name="type" size="18"> </TD> -->
    
    <!--  
    <TD width="20%">信任状态：<br><SELECT style="WIDTH: 130px" name="avalue"> 
        				<OPTION value=2 selected>全部</OPTION>
        				<OPTION value=1>可信</OPTION> 
        				<OPTION value=0>不可信</OPTION>
        				
        									
        			</SELECT>  </TD>
        			-->
        			
        <td>		 
    	<input value="提交查询"  type=button onClick="doSubmit()">
    	</TD>
    	<!--
    	<TD>
		<input value="自定义添加"  type=button onClick="doSelf()">
    </TD>
   
    <TD>
        <input value="创建快照"  type=button onClick="doRemark()">
	
    </TD>	
   -->
  
  
  
    <TD>
		<input value="导出白名单"  type=button onClick="doExport()">
		
    </TD>
       <TD>
		<input value="导入模板"  type=button onClick="doImport()">
		
    </TD>

    
      <TD>
		<input value="导入白名单"  type=button onClick="importxml()">
    </TD>
    <TD>
		<input type="text"  name ="beizhu" id = "beizhu" style="width:0px;height:0px;border:0px">

    </TD>		
    
	          
    
  </TR>
  
 </TBODY></TABLE>

 </FORM>

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
    function init()
    {
 	 	//document.forms[0].organization.selectedIndex=0;
    	//document.forms[0].attrtype.selectedIndex=0;
    	//document.forms[0].attrvalue.selectedIndex=0;
    	//document.forms[0].department.selectedIndex=0;
    	doSubmit();
    }

	
</SCRIPT>
  </BODY></HTML>