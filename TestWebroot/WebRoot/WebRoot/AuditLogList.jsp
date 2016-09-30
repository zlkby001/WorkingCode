<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%			
			String oid = request.getParameter("oid");
			String attr = "servlet/auditListDetailServlet?oid=" + oid;
			String ro = request.getParameter("role");
			String role;
				if(ro==null||ro.equals("")||ro.equals("null"))
				role=null;
			else
				role=new String(ro.getBytes("ISO-8859-1"),"UTF-8");
					System.out.println("role:::"+role);
			if (role != null)
				attr += "&role=" + role;
				String user1 = request.getParameter("user");
			//System.out.println("user1:::"+user1);
			String user;
				if(user1==null||user1.equals("")||user1.equals("null"))
				user=null;
			else
				user=new String(user1.getBytes("ISO-8859-1"),"UTF-8");
				System.out.println("user:::"+user);
				if(user !=null)
				attr+="&user="+ user;
			String type1 = request.getParameter("type");
			String type;
			if(type1==null||type1.equals("")||type1.equals("null"))
				type=null;
			else
				type=new String(type1.getBytes("ISO-8859-1"),"UTF-8");
			if (type != null)
				attr += "&type=" + type;
			String act = request.getParameter("action");
			//System.out.println("logaction before-----"+act);
			String action;
			if(act==null||act.equals("")||act.equals("null"))
				action=null;
			else
				action=new String(act.getBytes("ISO-8859-1"),"UTF-8");
			//String action = new String(request.getParameter("action").getBytes("ISO-8859-1"), "gb2312");
			if (action != null)
				attr += "&action=" + action;
			//System.out.println("logaction-----"+action);
			String time = request.getParameter("time");
			if (time != null)
				attr += "&time=" + time;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'right.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css"
	href="extjs3/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css"
	href="extjs3/examples/shared/examples.css" />
<link rel="stylesheet" type="text/css"
	href="extjs3/examples/grid/grid-examples.css" />
<style type=text/css>
/* style rows on mouseover */
.x-grid3-row-over .x-grid3-cell-inner {
	font-weight: bold;
	background-color: #FFFFB3;
}

.x-grid-record-red table {
	color:#121212;
}

</style>

<script type="text/javascript" src="extjs3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs3/ext-all.js"></script>
<script type="text/javascript" src="js/myjs.js"></script>

<script type="text/javascript">
	var userId,thisGrid;

	Ext.onReady(function(){
    
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
        var store = new Ext.data.JsonStore({
			root : 'rows',
			totalProperty : 'total',
			
			fields : [ {
				name : 'userid',
				type : 'int'
			}, 'id', 'type', 'user', 'role', 'action', 'time', 
			//{name: 'lastpost', mapping: 'lastpost', type: 'date', dateFormat: 'timestamp'}

			],

			// load using script tags for cross domain, if the data in on the same domain as
			// this page, an HttpProxy would be better
			proxy : new Ext.data.HttpProxy({
				url : '<%=attr%>'
			})
		});
		//store.load({params:{start:0,limit:30}}); 
		//store.setDefaultSort('lastpost', 'desc');

		var  sm = new Ext.grid.CheckboxSelectionModel ({ singleSelect: false });//复选框
		var gdAuditDetail = new Ext.grid.GridPanel({
			id:'AuditDetaiGrid',
			// width:700,
			height : 300,
			title : '审计日志详情',
			store : store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			header : true,
			enableHdMenu:false,
			sm:sm,
			//cm:new Ext.grid.RowNumberer(),
			//layout:'fit',

			columns : [ 
			new Ext.grid.RowNumberer(),
			sm,
			{
				id : '_userid', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
				header : "用户ID",
				dataIndex : 'userid',
				hidden:true 
				//width: 50,
				//renderer: renderTopic,
			}, 
			//{
			//	id : '_acknowledge',
			//	header : "状态",
			//	dataIndex : 'acknowledge',
			//	hidden:true 
		//	}, 
			{
				width:100,
				header : "日志ID",
				dataIndex : 'id',
				hidden:true
			}, {
				width:100,
				sortable : true,
				header : "用户种类",
				dataIndex : 'role'
				
			}, {
				width:100,
				sortable : true,
				header : "用户名",
				dataIndex : 'user'
				
			}, {
				width:100,
				header : "操作种类",
				dataIndex : 'type'
			}, {
				width:100,
				header : "操作描述",
				dataIndex : 'action'
			}, {
				width:100,
				header : "时间",
				dataIndex : 'time'
			}
			],

			// customize view config
			viewConfig : {
				forceFit : true,
				enableRowBody : true,
				getRowClass : function(record, rowIndex, p, store) {
					if (record.data.acknowledge == 0) {
						return 'x-grid-record-red';
					} else {
						return '';
					}
				}
			},

			// paging bar on the bottom
			bbar : new Ext.PagingToolbar({
				pageSize : 30,
				store : store,
				displayInfo : true,
				displayMsg : "第 {0} - {1} 条, 共 {2} 条",
				emptyMsg : "没有符合条件的记录"
			})
		});
		store.load({params:{start:0,limit:30}}); 
		gdAuditDetail.render('divAuditDetail');
        var viewport = new Ext.Viewport({
            layout: 'border',
            items: [
             {
                // lazily created panel (xtype:'panel' is default)
                region: 'north',
                contentEl: 'divSearchPanel',
                split: true,
                height: 100,
                autoScroll:true,
                collapsible: true,
                title: '条件查询',
                header:true,
                margins: '0 0 0 0'
            },            
            {
                 region:'center',
                 layout: 'fit',
                 margins:'0 0 0 0',
                 cls:'empty',
                 bodyStyle:'background:#ffffff',
                 height:600,
                 //html:'<br/><br/>&lt;empty center panel&gt;'
                 //autoLoad:'t3.html'
                 //layout:'fit',
                 items: [gdAuditDetail]
            }]
        });
        thisGrid = gdAuditDetail;
        
    });
    
    //================================  functions  =================================
    
    function renderOperate(value, p, record){
    	var html = "<a href='javascript:void(0)' onclick='scanAlert()'>[详情]</a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='deleteAlerts(" 
    				+ record.data.id + ")'>[删除]</a>";
        return html;
    }
    
		//查看详情
		function scanAlert() {
			var row = thisGrid.getSelectionModel().getSelections();
			if (row.length == 0) {  
			    Ext.Msg.alert("提示信息", "请首先选中一行再操作!");  
			    return;
			}  
			var r = row[0];   
			
			var url = 'auditDetailInfo.jsp?role=' + r.data.role + '&user=' + r.data.user+'&type=' + r.data.type + '&action="' + r.data.action 
			+ '&time=' + r.data.time;
			createWin(500, 350, 300, 100,'审计日志信息',url);
		}
		
		function deleteRows(){
			var ids;
			var row = thisGrid.getSelectionModel().getSelections();
			var length = row.length;
			if (length == 0) {  
			    Ext.Msg.alert("提示信息", "请首先选中一行再操作!");  
			    return;
			}  
			
			ids = row[0].data.id;
			for(var i=1;i<length;i++){
				ids += ',';
				ids += row[i].data.id;
			}
			//alert(ids);
			deleteAlerts(ids);
		}
		
		//依照id删除记录
		function deleteAlerts(ids) {
		var val = confirm("您确定要删除选中的报警日志吗??");
		//var prams = 'delIds=' + ids;
		if (val) {
		    var result = ajaxSyncCall('servlet/deleteAlertDetailServlet?delIds=' + ids, null);
		    if (result.success) {
		    	//thisNode.remove();
		    	alert("已成功删除该日志!");
		    	thisGrid.getStore().reload();		
		    } else {
		    	alert(result.errorMsg);
		    }
		}
		
	}

function doSubmit() {
	
	var role = document.getElementById('role').value;
	var user = document.getElementById('user').value;
	var type = document.getElementById('type').value;
	var action = document.getElementById('action').value;
	var time = document.getElementById('time').value;
	var attr = 'AuditLogList.jsp?oid=<%=request.getParameter("oid")%>&role='+ role
					+ '&user='+ user
					+ '&type='+ type
					+ '&action='+ action
					+ '&time='+time;	//alert(attr);
					
			parent.loadmain(attr);

		}

function createWin(w, h, x, y,tl,url){
    var win;
    if (navigator.userAgent.indexOf('MSIE 6.0')>0) {	//判断是否IE浏览器
			win = new Ext.Window({
	        title: tl,		//窗体标题
	        layout: 'fit',    //设置布局模式为fit，能让frame自适应窗体大小
	        modal: true,    //打开遮罩层
	        height: 300,    //初始高度
	        width: 300,  //初始宽度
	        border: 0,    //无边框
	        //frame: false,    //去除窗体的panel框架
	        autoLoad:url
	    });
	} 
			
	else{
			win = new Ext.Window({
	        title: tl,		//窗体标题
	        layout: 'fit',    //设置布局模式为fit，能让frame自适应窗体大小
	        modal: true,    //打开遮罩层
	        height: 300,    //初始高度
	        width: 300,  //初始宽度
	        border: 0,    //无边框
	        html: '<iframe frameborder=0 width="100%" height="100%" src=' + url + '></iframe>'
    	});
	}
    
    win.setSize(w, h);    //w为设置的宽度，h为设置的高度
    win.setPosition(x, y);    //x为设置的x坐标，y为设置的y坐标
    win.show();    //显示窗口
}


		
/**
 * 通用JS 同步ajax调用 返回json Object
 */
function ajaxSyncCall(urlStr, paramsStr) {
	var obj;
	if (window.ActiveXObject) {
		obj = new ActiveXObject('Microsoft.XMLHTTP');
	} else if (window.XMLHttpRequest) {
		obj = new XMLHttpRequest();
	}
	obj.open('POST', urlStr, false);
	obj.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	obj.send(paramsStr);
	var result = Ext.util.JSON.decode(obj.responseText);
	return result;
}

</script>

</head>

<body>
	<div id="divSearchPanel" class="x-hide-display" >
		<br>
		<TABLE width="65%" style="overflow: hidden;font-size:12px;">
			<TBODY>
				<TR>
					<TD>用户种类(支持模糊查找)<br>
					<INPUT type="text" id="role" size="18"></TD>
					<TD>用户名<br>
					<INPUT type="text" id="user" size="18"></TD>
					<TD>操作种类<br>
					<INPUT type="text" id="type" size="18"></TD>
					<TD>操作描述<br>
					<INPUT type="text" id="action" size="18"></TD>
					<TD>时间<br> <input id="time" size="18"></TD>

					<TD width="15%" align="left"></TD>
					<TD><input value="提交查询" type=button onclick="doSubmit()">
				
					

				</TR>

			</TBODY>
		</TABLE>
	</div>
	
	<div id='divAuditDetail'></div>
</body>
</html>