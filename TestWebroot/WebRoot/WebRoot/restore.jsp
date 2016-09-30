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
	color: #FF0000;
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
			
			fields : [ 'id', 'name','pubkey', 'client_area', 'ip', 'mac'
			//{name: 'lastpost', mapping: 'lastpost', type: 'date', dateFormat: 'timestamp'}

			],

			// load using script tags for cross domain, if the data in on the same domain as
			// this page, an HttpProxy would be better
			proxy : new Ext.data.HttpProxy({
				url : 'servlet/listUsersServlet'
			})
		});
		store.load(); 
		//store.setDefaultSort('lastpost', 'desc');

		var gdUserList = new Ext.grid.GridPanel({
			id:'alertDetaiGrid',
			// width:700,
			height : 300,
			title : '请选择新终端用户',
			store : store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			header : true,
			enableHdMenu:false,
			//cm:new Ext.grid.RowNumberer(),
			//layout:'fit',

			columns : [ new Ext.grid.RowNumberer(),{
				id : 'user_userid', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
				header : "用户ID",
				dataIndex : 'id',
				hidden:true 
				//width: 50,
				//renderer: renderTopic,
			}, {
				id:'user_name',
				width:100,
				header : "用户名",
				dataIndex : 'name'
			}, {
				width:100,
				sortable : true,
				header : "部门",
				dataIndex : 'client_area'
			}, {
				width:100,
				header : "芯片公钥",
				dataIndex : 'pubkey'
			}, {
				width:100,
				header : "IP地址",
				dataIndex : 'ip'
			}, {
				width:100,
				header : "MAC地址",
				dataIndex : 'mac'
			},
			{  
		        id : 'userOperate',
		       	width:100,
		       	dataIndex:'',
		        sortable : false,  
		        header : '操作',  
		        //resizable : false,
		        renderer : renderOperate
		    }
			],
			
			viewConfig : {
				forceFit : true,
				enableRowBody : true,
				getRowClass : function(record, rowIndex, p, store) {}
			},


			// paging bar on the bottom
			bbar : new Ext.PagingToolbar({
				//pageSize : 100,
				store : store,
				displayInfo : true,
				displayMsg : "第 {0} - {1} 条, 共 {2} 条",
				emptyMsg : "没有符合条件的记录"
			})
		});
		
		gdUserList.render('divUserList');
        var viewport = new Ext.Viewport({
            layout: 'border',
            items: [                       
            {
                 region:'center',
                 layout:'fit',
                 margins:'0 0 0 0',
                 cls:'empty',
                 bodyStyle:'background:#ffffff',
                 height:600,
                 //html:'<br/><br/>&lt;empty center panel&gt;'
                 //autoLoad:'t3.html'
                 //layout:'fit',
                 items: [gdUserList]
            }]
        });
        thisGrid = gdUserList;
        
    });
    
    //================================  functions  =================================
    
    function renderOperate(value, p, record){
    	var addr = "servlet/startrestoreServlet?oid="+record.data.id;
    	var html = "<a class='blue' href="+ addr + ">[选择]</a>";
        return html;
    }
    
    function restore(){
    	var row = thisGrid.getSelectionModel().getSelections();
			if (row.length == 0) {  
			    Ext.Msg.alert("提示信息", "请首先选中一行再操作!");  
			    return;
			}  
			var r = row[0];
			parent.loadmain( 'restorelist.jsp?oid=' + r.data.id);
    }
    
    function deleteUser(){		
		var row = thisGrid.getSelectionModel().getSelections();
		if (row.length == 0) {  
		    Ext.Msg.alert("提示信息", "请首先选中一行再操作!");  
		    return;
		}  
		var r = row[0];  
		var val = confirm("您确定要删除用户 "  + r.data.name + " 吗??");
		if (val) {
		    var result = ajaxSyncCall('servlet/deleteUserServlet?oid=' + r.data.id, null);
		    if (result.success) {
		    	alert("已成功删除用户!");
		    	thisGrid.getStore().reload();		
		    } else {
		    	alert(result.errorMsg);
		    }
		}
	}
		
	function scanWhithList(){	
			var row = thisGrid.getSelectionModel().getSelections();
			if (row.length == 0) {  
			    Ext.Msg.alert("提示信息", "请首先选中一行再操作!");  
			    return;
			}  
			var r = row[0];
			parent.loadmain( 'servlet/userDetailServlet?oid=' + r.data.id);
		}
		
		
	function scanHistory(){	
			var row = thisGrid.getSelectionModel().getSelections();
			if (row.length == 0) {  
			    Ext.Msg.alert("提示信息", "请首先选中一行再操作!");  
			    return;
			}  
			var r = row[0];
			parent.loadmain( 'servlet/historyDetailServlet?oid=' + r.data.id );
		}
    
      
    
</script>

</head>

<body>
		
	<div id='divUserList'></div>
</body>
</html>