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
<link rel="stylesheet" type="text/css"
	href="css/icon.css" />

<script type="text/javascript">
	var userId,thisGrid;

	Ext.onReady(function(){
    
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
        var store = new Ext.data.JsonStore({
			root : 'rows',
			totalProperty : 'total',
			
			fields : [ 'id', 'terminalid','manufacture', 'sn'
			//{name: 'lastpost', mapping: 'lastpost', type: 'date', dateFormat: 'timestamp'}

			],

			// load using script tags for cross domain, if the data in on the same domain as
			// this page, an HttpProxy would be better
			proxy : new Ext.data.HttpProxy({
				url : 'servlet/usbLocalServlet?oid=' + getQueryString('oid')
			})
		});
		store.load(); 
		//store.setDefaultSort('lastpost', 'desc');
		
		var  sm = new Ext.grid.CheckboxSelectionModel ({ singleSelect: false });//复选框
		var gdUserList = new Ext.grid.GridPanel({
			id:'alertDetaiGrid',
			// width:700,
			height : 300,
			title : 'USB白名单',
			store : store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			header : true,
			sm:sm,
			items:[tb1],
			enableHdMenu:false,
			//cm:new Ext.grid.RowNumberer(),
			//layout:'fit',

			columns : [ new Ext.grid.RowNumberer(),sm,{
				id : 'usbid', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
				header : "编号",
				dataIndex : 'id',
				hidden:true 
				//width: 50,
				//renderer: renderTopic,
			}, {
				id:'tid',
				width:100,
				header : "终端ID",
				dataIndex : 'terminalid',
				hidden:true 
			}, {
				id:'manu',
				width:100,
				sortable : true,
				header : "厂商",
				dataIndex : 'manufacture'
			}, {
				id:'sn',
				width:100,
				header : "编号",
				dataIndex : 'sn'
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
		
		gdUserList.render('divMain');
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
   
     var tb1=new Ext.Toolbar({
       
       items:[       
           "->","-"
		  ,{
		   text:"删除选中",
		   tooltip:"删除选中",
		   iconCls:"delete",
		   handler:function(){ deleteRows();}
		  },"-",{
		   text:"全部清空",
		   tooltip:"全部清空",
		   iconCls:"delete",
		   handler:function(){ deleteAll();}
		  }  
	   ]
	
	});
	
	
    
    //================================  functions  =================================
    
    function renderOperate(value, p, record){
    	var html = "<a href='javascript:void(0)' onclick='deleteOne()'>[删除]</a>" ;
        return html;
    }
    
    function deleteOne(){
		var row = thisGrid.getSelectionModel().getSelections();
		if (row.length == 0) {  
		    Ext.Msg.alert("提示信息", "请首先选中一行再操作!");  
		    return;
		}  
		var r = row[0]; 
		var val = confirm("您确定要删除U盘 "+ r.data.manufacture + " - " + r.data.sn +" 吗?");
		if (val) {
		   deleteUsb(r.data.id);
		}
	}
	
	function deleteRows(){
			var values;
			var row = thisGrid.getSelectionModel().getSelections();
			var length = row.length;
			if (length == 0) {  
			    Ext.Msg.alert("提示信息", "请首先选中一行再操作!");  
			    return;
			}
			
			var val = confirm("您确定要删除选中的U盘吗?");
			if (!val) {
				return;
			}
		
			values = row[0].data.id;
			for(var i=1;i<length;i++){
				values += "','";
				values += row[i].data.id;
			}
			//values += "'";
			//alert(values);
			deleteUsb(values);
	}
	
		
	function deleteUsb(ids){
		 var result = ajaxSyncCall('servlet/usbLocalServlet?type=delete&id=' + ids, null);
		    if (result.success) {
		    	alert("已成功删除U盘!");
		    	thisGrid.getStore().reload();		
		    } else {
		    	alert(result.errorMsg);
		    }
	}
		
	function deleteAll(){
		var row = thisGrid.getStore().getCount();
		if (row == 0) {  
		    Ext.Msg.alert("提示信息", "没有相关U盘信息!");  
		    return;
		}  
		var val = confirm("您确定要删除全部U盘信息吗?");
		if (val) {
		    var result = ajaxSyncCall('servlet/usbLocalServlet?type=deleteAll&tid=' + getQueryString('oid'), null);
		    if (result.success) {
		    	alert("清空完毕!");
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
    
   function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }
    
</script>

</head>

<body>
		
	<div id='divMain'></div>
</body>
</html>