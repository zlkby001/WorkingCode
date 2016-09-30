<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>title</TITLE>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<META content="MSHTML 6.00.6000.16788" name=GENERATOR>


<script type="text/javascript" src="extjs3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs3/ext-all-debug.js"></script>
<script type="text/javascript" src="extjs3/ext-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css"
	href="extjs3/resources/css/ext-all.css" />


<script type="text/javascript">
	function myRender(p) {
		Ext.Msg.alert("提示", p.title + "渲染成功");
	}
	Ext.onReady(function() {
		var i = 4;
		//注意:每个Tab标签只渲染一次     
		var tabs = new Ext.TabPanel({
			renderTo : Ext.getBody(),//绑定在body标签上     
			activeTab : 1,//初始显示第几个Tab页     
			deferredRender : false,//是否在显示每个标签的时候再渲染标签中的内容.默认true     
			tabPosition : 'bottom',//表示TabPanel头显示的位置,只有两个值top和bottom.默认是top.     
			enableTabScroll : true,//当Tab标签过多时,出现滚动条     
			items : [ {//Tab的个数     
				title : '按条件',
				//html : 'A simple tab',
				autoLoad : 'searchlogs.jsp',
				listeners : {
					render : function() {//为每个Tab标签添加监听器.当标签渲染时触发     
						Ext.Msg.alert("Tab 1", "渲染Tab 1成功");
					}
				}
			}, {
				title : '按厂区',
				autoLoad : 'treeFrame.jsp',
				closable : false,
				listeners : {
					render : myRender
				}
			} ]
		});

		//把TabPanel组件充满整个body容器.     
		new Ext.Viewport({
			layout : 'fit',
			items : tabs
		});
	});
</script>

</HEAD>

<BODY>


</BODY>
</HTML>