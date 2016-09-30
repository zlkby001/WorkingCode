<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<TITLE>title</TITLE>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<META content="MSHTML 6.00.6000.16788" name=GENERATOR>

<script type="text/javascript" src="extjs3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs3/ext-all-debug.js"></script>
<link rel="stylesheet" type="text/css"
	href="extjs3/resources/css/ext-all.css" />

 <script type="text/javascript">
   
	Ext.onReady(function(){
    Ext.QuickTips.init();
    
    var menu1 = new Ext.menu.Menu({
        id: 'mainMenu',
        style: {
            overflow: 'visible'     // For the Combo popup
        },
        items: [
            {
            	id:'scanPersonalInfo',
                text: '查看个人信息',
                clickHandler: onItemClick
            }, '-',
            {
            	id:'editPersonalInfo',
                text: '修改个人信息',
                clickHandler: onItemClick
            },
            {
                id:'editCertificateInfo',
                text: '修改证件信息'
            },
            {
                id:'editPassword',
                text: '修改密码'
            }
        ],
        listeners : {
            itemclick : function(item) {
                switch (item.id) {
                case 'scanPersonalInfo':
                	alert(item.id);
                	break;
                case 'editPersonalInfo':
                	alert(item.id);
                	break;
                case 'editCertificateInfo':
                	alert(item.id);
                	break;
                case 'editPassword':
                	alert(item.id);
                	break;
                }
                
                //item.parentMenu.hide();
            		}
            	}
    });

    var tb = new Ext.Toolbar();
    tb.render('divToolbar');

    tb.add({
            text:'个人信息管理',
            iconCls: 'bmenu',  // <-- icon
            menu: menu1  // assign menu by instance
        }, '-', 
        new Ext.Toolbar.Button({
            text: 'Button',
            handler: onButtonClick,
            tooltip: {text:'This is a an example QuickTip for a toolbar item', title:'Tip Title'},
            iconCls: 'blist'
        })
        );


    tb.doLayout();

    // functions to display feedback
    function onButtonClick(btn){
        Ext.example.msg('Button Click','You clicked the "{0}" button.', btn.text);
    }

    function onItemClick(item){
        Ext.example.msg('Menu Click', 'You clicked the "{0}" menu item.', item.text);
    }



});
    
</script>
    
    
    
  </head>
  
  <body>
	<div id="divTop" style="background:#D6E3F2;width:100%;height:100%">
	<div id="divToolbar" ></div>
	
	</div>
  </body>
</html>
