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
Ext.BLANK_IMAGE_URL ="img/s.gif";
	var userid,gridAlert;

	Ext.onReady(function() {

		// create the Data Store
		var store = new Ext.data.JsonStore({
			root : 'rows',
			totalProperty : 'total',
			// idProperty: 'threadid',
			// remoteSort: true,

			fields : [ {
				name : 'id',
				type : 'int'
			}, 'name', 'ip', 'amount', 'latest'
			//{name: 'lastpost', mapping: 'lastpost', type: 'date', dateFormat: 'timestamp'}

			],

			// load using script tags for cross domain, if the data in on the same domain as
			// this page, an HttpProxy would be better
			proxy : new Ext.data.HttpProxy({
				url : 'servlet/alertListServlet'
			})
		});
		//store.setDefaultSort('lastpost', 'desc');

		var grid = new Ext.grid.GridPanel({
			id:'rightGrid',
			// width:700,
			height : 300,
			title : '报警日志',
			store : store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			header : false,

			// grid columns
			columns : [ new Ext.grid.RowNumberer(),{
				id : 'topic', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
				header : "用户名",
				dataIndex : 'name',
				//width: 50,
				//renderer: renderTopic,
				sortable : true
			}, {
				id : 'last',
				header : "新增报警",
				dataIndex : 'amount',
				//width: 150,
				//renderer: renderLast,
				sortable : true
			},
			{  
		        id : 'operateCol',  
		       // width:30,
		        sortable : false,  
		        header : '操作',  
		        resizable : false,  
		        renderer : renderOperate
		    }    
			],

			// customize view config
			viewConfig : {
				forceFit : true,
				enableRowBody : true,
				getRowClass : function(record, rowIndex, p, store) {
					if (record.data.amount > 0) {
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

		// render it
		grid.render('divAlert');

		// trigger the data store load
		store.load({
			params : {
				start : 0,
				limit : 30
			}
		});
		grid.addListener('rowclick', rowclick);
		grid.addListener('rowcontextmenu', rowcontextmenu);

		/**
		 * viewport
		 */
		var vp = new Ext.Viewport({
			layout : 'fit',
			items : [ grid ]
		});
		
		gridAlert = grid;
		refresh();
	});

	function rowclick(grid, rowIndex, columnIndex, e) {
		var record = grid.getStore().getAt(rowIndex); //Get the Record
		userid = record.get('id'); //获取table的选中行的id		
		//alert(userid);
		//changeAlertStaus();
		loadAlert();
		//var r = gridAlert.getSelectionModel().getSelections()
		//r[0].remove();

	}
	
	function rowcontextmenu(thisGrid, rowIndex, evtObj){
	    evtObj.stopEvent();
	    var record = thisGrid.getStore().getAt(rowIndex);
	    
		   thisGrid.rowCtxMenu = new Ext.menu.Menu({
		    items : [{
		      text : '全部报警日志',
		      handler:function(aa){	loadAllAlert();}
		     },{
		     text : '查看详情',
		     handler : function() {
		     loadAlert();
		     }},{
		     text : '取消',
		     handler :''
		     }]
		   });
		thisGrid.rowCtxMenu.showAt(evtObj.getXY());
		
	    //Ext.Msg.alert(rowIndex);
   }

	function changeAlertStaus(id) {
	//alert(id);
		var result = ajaxSyncCall('servlet/changeAlertStausServlet?oid='
				+ userid, null);
		if (result.success) {
			//var addr = 'servlet/alertListServlet?_01' + (new Date().getTime())
			//		^ Math.random();
			gridAlert.getStore().reload();
			//grid.load(addr);
		}
	}

	function loadAlert() {
		parent.loadmain('alertListDetail.jsp?oid=' + userid);
	}

	function refresh() {
		//var addr = 'servlet/alertListServlet?_02' + (new Date().getTime())
		//		^ Math.random();
		gridAlert.getStore().reload();
		//alert('refresh');
		setTimeout(function(){refresh()}, 30000);
	}

	function loadAllAlert() {
		parent.loadmain('alertListDetail.jsp?oid=');

	}
	
	function renderOperate(value, p, record){
        return String.format(
                //'<button type="button" onclick = "changeAlertStaus({0})" style="height:20px;"><span>清除<span></button>',
                '<input name="submitimage" type="image" src="extjs3/examples/shared/icons/fam/cross.gif" '
               + 'align="middle"  width="20px" height="16px" alt="删除"  onclick = "changeAlertStaus({0})">',
                record.data.id);
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
	<div id='divAlert'></div>
</body>
</html>