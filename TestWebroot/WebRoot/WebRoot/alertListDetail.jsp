<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
			String oid = request.getParameter("oid");
			String attr = "servlet/alertListDetailServlet?oid=" + oid;
			String sname = request.getParameter("sname");
			if (sname != null)
				attr += "&sname=" + sname;
			String ip = request.getParameter("ip");
			if (ip != null)
				attr += "&ip=" + ip;
			String filename = request.getParameter("filename");
			if (filename != null)
				attr += "&filename=" + filename;
			String sdigest = request.getParameter("sdigest");
			if (sdigest != null)
				attr += "&sdigest=" + sdigest;
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
			
			fields : [ {
				name : 'userid',
				type : 'int'
			}, 'id','acknowledge', 'ip', 'name', 'filename', 'digest', 'issuedate', 'operate'
			//{name: 'lastpost', mapping: 'lastpost', type: 'date', dateFormat: 'timestamp'}

			],

			// load using script tags for cross domain, if the data in on the same domain as
			// this page, an HttpProxy would be better
			proxy : new Ext.data.HttpProxy({
				url : '<%=attr%>'
			})
		});
		store.load({params:{start:0,limit:30}}); 
		//store.setDefaultSort('lastpost', 'desc');

		var  sm = new Ext.grid.CheckboxSelectionModel ({ singleSelect: false });//复选框
		var gdAlertDetail = new Ext.grid.GridPanel({
			id:'alertDetaiGrid',
			// width:700,
			height : 300,
			title : '报警日志详情',
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
			}, {
				id : '_acknowledge',
				header : "状态",
				dataIndex : 'acknowledge',
				hidden:true 
			}, {
				width:100,
				header : "日志ID",
				dataIndex : 'id',
				hidden:true
			}, {
				width:100,
				sortable : true,
				header : "用户名",
				dataIndex : 'name'
				
			}, {
				width:100,
				header : "IP地址",
				dataIndex : 'ip'
			}, {
				width:100,
				header : "文件名",
				dataIndex : 'filename'
			}, {
				width:100,
				header : "度量值",
				dataIndex : 'digest'
			}, {
				width:100,
				header : "时间",
				dataIndex : 'issuedate'
			}, 
			{  
		        id : 'operateCol',  
		        width:100,
		       	dataIndex:'',
		        sortable : false,  
		        header : '操作',  
		        resizable : false , 
		        renderer : renderOperate
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
		
		gdAlertDetail.render('divAlertDetail');
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
                 items: [gdAlertDetail]
            }]
        });
        thisGrid = gdAlertDetail;
        
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
			
			var url = 'alertDetailInfo.jsp?username=' + r.data.name + '&ip=' + r.data.ip + '&filename="' + encodeURIComponent(r.data.filename) 
			+ '"&digest=' + encodeURIComponent(r.data.digest) + '&issuedate=' + r.data.issuedate;
			createWin(500, 350, 300, 100,'报警日志信息',url);
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
	var sname = document.getElementById('sname').value;
	var ip = document.getElementById('ip').value;
	var filename = document.getElementById('filename').value;
	var sdigest = document.getElementById('sdigest').value;
	var time = document.getElementById('time').value;
	var attr = 'alertListDetail.jsp?oid=<%=request.getParameter("oid")%>&sname='+ sname
					+ '&ip='+ ip
					+ '&filename='+ filename
					+ '&sdigest=' + sdigest + '&time='+time;	//alert(attr);
					
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
					<TD>用户名(支持模糊查找)<br>
					<INPUT type="text" id="sname" size="18"></TD>
					<TD>IP地址<br>
					<INPUT type="text" id="ip" size="18"></TD>
					<TD>文件名<br>
					<INPUT type="text" id="filename" size="18"></TD>
					<TD>度量值<br>
					<INPUT type="text" id="sdigest" size="18"></TD>
					<TD>时间<br> <input id="time" value="" style=" width:150px"></TD>

					<TD width="15%" align="left"></TD>
					<TD><input value="提交查询" type=button onclick="doSubmit()">
					</TD>
					<TD><input value="删除选中项" type=button onclick="deleteRows();">
					</TD>

				</TR>

			</TBODY>
		</TABLE>
	</div>
	
	<div id='divAlertDetail'></div>
</body>
</html>