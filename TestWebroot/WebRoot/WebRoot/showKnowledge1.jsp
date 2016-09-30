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
<link rel="stylesheet" type="text/css"
	href="css/icon.css" />
	
<style type=text/css>
/* style rows on mouseover */
.x-grid3-row-over .x-grid3-cell-inner {
	font-weight: bold;
	background-color: #FFFFB3;
}

 A:link {
  color: blue; text-decoration: decoration:underline
 }
 
 .blue{
 color: blue; text-decoration:underline
 }
 
</style>

<script type="text/javascript" src="extjs3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs3/ext-all.js"></script>
<script type="text/javascript" src="js/myjs.js"></script>

<script type="text/javascript">
	var uid = '';
	var thisGrid;
	var osType = new Array();
	osType = [ 
    ['XP系统','XP系统'], 
    ['Windows7','Windows7'], 
    ['WinServer2003','WinServer2003'], 
    ['WinServer2000','WinServer2000'], 
    ['Linux','Linux']
];

	Ext.onReady(function(){
    
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
        var store = new Ext.data.JsonStore({
			root : 'rows',
			totalProperty : 'total',			
			fields : [ 'id','name','version','os','des'
			],

			// load using script tags for cross domain, if the data in on the same domain as
			// this page, an HttpProxy would be better
			proxy : new Ext.data.HttpProxy({
				url : 'servlet/knowledgeListServlet'
			})
		});
		store.load({params:{start:0,limit:20}}); 
		//store.setDefaultSort('lastpost', 'desc');
		
		var  sm = new Ext.grid.CheckboxSelectionModel ({ singleSelect: false });//复选框
		var gdAlertDetail = new Ext.grid.GridPanel({
			id:'alertDetaiGrid',
			//margins: '2 2 2 2',  //为了不要与容器的边框重叠，设定2px的间距。
  			//region: 'center',
			// width:700,
			//height : 450,
			title : '知识库列表',
			store : store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			header : true,
			enableHdMenu:false,
			items: [tb1],
			sm: sm,//复选框
			//cm:new Ext.grid.RowNumberer(),
			//layout:'fit',
			
			tbar:[
			"知识库名",
		    {
		      id:'processName',
		      width:100,
			  emptyText : '请输入知识库名...',
			  xtype:'textfield'
			},"-",
			"版本",
		    {
		      id:'vers',
		      width:100,
			  emptyText : '请输入版本号...',
			  xtype:'textfield'
			},"-",
			"操作系统",
		    {
		      id:'osType',
		      width:100,
			  emptyText : '请选择操作系统...',
			  xtype:'combo',
			  store: new Ext.data.SimpleStore({
                fields: ['value', 'text'],
                data : osType
                }),
            valueField:'value',
            displayField:'text',
            typeAhead: true,
            mode: 'local',
            triggerAction: 'all',
            editable:true
			},"-",
			"备注",
		    {
		      id:'softwareType',
		      width:100,
			  emptyText : '请输入备注信息...',
			  xtype:'textfield'
			},"-",
		    {
		   text:"查询",
		   tooltip:"条件查询",
		   iconCls:"search",
		   handler:function(){doSearch();}
		   }],

			columns : [ 
			new Ext.grid.RowNumberer(),
			//sm,
			{  
		        id : 'knowledgeId',  
		       	dataIndex:'id',
		        sortable : false,  
		        header : '编号',
		        hidden:true
		    },{
				id : 'name', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
				header : "知识库名",
				dataIndex : 'name',
				hidden:false
				//width: 50,
				//renderer: renderTopic,
			}, 
			{
				id : 'version', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
				header : "软件版本",
				dataIndex : 'version',
				hidden:false
				//width: 50,
				//renderer: renderTopic,
			},
			{
				id : 'os', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
				header : "系统版本",
				dataIndex : 'os',
				hidden:false
				//width: 50,
				//renderer: renderTopic,
			},
			{
				id : 'des', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
				header : "备注",
				dataIndex : 'des',
				hidden:false
				//width: 50,
				//renderer: renderTopic,
			},
			{  
		        id : 'operateCol',  
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
				enableRowBody : true
			},

			// paging bar on the bottom
			bbar : new Ext.PagingToolbar({
				pageSize : 20,
				store : store,
				displayInfo : true,
				displayMsg : "第 {0} - {1} 条, 共 {2} 条",
				emptyMsg : "没有符合条件的记录"
     
			})
		});
		
		gdAlertDetail.render('divAlertDetail');
        var viewport = new Ext.Viewport({
            layout: 'fit',
            items: [            
            {
            	layout:'fit',
                 region:'center',
                 margins:'0 0 0 0',
                 cls:'empty',
                 bodyStyle:'background:#ffffff',
                 items: [gdAlertDetail]
            }]
        });
        thisGrid = gdAlertDetail;
        
    });
    
    var tb1=new Ext.Toolbar({
       
       items:[       
           "->","-"
		  ,{
		   text:"添加知识库",
		   tooltip:"添加新信息",
		   iconCls:"add",
		   handler:function(){ addKnowledge();}
		  }  
	   ]
	
	});

    
    
    //================================  functions  =================================
    
    
    function renderOperate(value, p, record){
    	var html = "<a class='blue' href='javascript:void(0)' onclick='scanKnowledge();return false;'>[详情]</a>&nbsp;&nbsp;<a class='blue' href='javascript:void(0)' onclick='importxml();return false;'>[导入]</a>&nbsp;&nbsp;<a class='blue' href='javascript:void(0)' onclick=deleteKnowledge('" 
    				+ record.data.id + "')>[删除]</a>";
    				//+ record.data.id + "')>[删除]</a>&nbsp;&nbsp;<a class='blue' href='javascript:void(0)' onclick=createWin('"+ encodeURI(record.data.name) +"')>[添加]</a>";
        return html;
    }
    
    function addKnowledge() {
		var name = prompt("请输入知识库名称：", "新建知识库");
		if (!name || !trim(name)) {
			return;
		}
		
		var ver = prompt("请输入知识库版本：", "1.0");
		if (!ver || !trim(ver)) {
			return;
		}
		
		var os = prompt("请输入操作系统版本：", "windows");
		if (!os || !trim(os)) {
			return;
		}
		
		var des = prompt("请输入知识库描述信息：", "知识库");
		if (!des || !trim(des)) {
			return;
		}
		
		//alert(thisNode.id.substr(1));
		var result = ajaxSyncCall('servlet/knowledgeListServlet?type=add&name=' + encodeURI(encodeURI(name))+'&ver='+ encodeURI(encodeURI(ver))+'&os='+encodeURI(encodeURI(os))+'&des='+encodeURI(encodeURI(des)), null);
		if (result.exist) {
		    	alert("已存在同名知识库，请修改名称!");
		    } 
		else if (result.success) {
		    	alert("添加知识库成功!");
		    	thisGrid.getStore().reload();
		    }else {
		    	alert(result.errorMsg);
		    }
	}
    
		//查看详情
		function scanKnowledge() {
			var row = thisGrid.getSelectionModel().getSelections();
			if (row.length == 0) {  
			    Ext.Msg.alert("提示信息", "请首先选择一行再操作!"); 
			    return;
			}  
			var r = row[0];			
			var url = 'viewKnowledge.jsp?ics_name=' + encodeURI(encodeURI(r.data.name))+'&ics_id='+r.data.id;
			//alert(url);
			//parent.loadmain(url);
			self.location = url;
		}
		
		function importxml()
		{
			var row = thisGrid.getSelectionModel().getSelections();
			if (row.length == 0) {  
			    Ext.Msg.alert("提示信息", "请首先选择一行再操作!"); 
			    return;
			}  
			var r = row[0];			
			var url = 'uploadknowledgefromxml.jsp?ics_id=' + r.data.id;
			//alert(url);
			//parent.loadmain(url);
			self.location = url;
		}
		
		//依照id删除记录
		function deleteKnowledge(ids) {
		//alert(names);
		var val = confirm("您确定要删除选择的知识库吗??");
		if (val) {
		    var result = ajaxSyncCall('servlet/knowledgeListServlet?type=delete&id=' + encodeURI(encodeURI(ids)), null);
		    if (result.success) {
		    	thisGrid.getStore().reload();		
		    	alert("已成功删除该知识库!");
		    } else {
		    	alert(result.errorMsg);
		    }
		}
		
	}

	function doSearch(){
	}	

  function createWin(name){
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
						url : 'servlet/comboServlet?type=verifydate&userId=' + uid
					}),
					//params: {type:'verifydate',userId:''},
					fields:[
						'date'
						]
				},
				listeners: {
				  focus: function () {
				  //alert('focus');
				  	var proxy = new Ext.data.HttpProxy({url:'servlet/comboServlet?type=verifydate&userId=' + uid});
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
	var win = new Ext.MyWindow();
	//win.setSize(w, h);    //w为设置的宽度，h为设置的高度
    win.setPosition(100, 100);    //x为设置的x坐标，y为设置的y坐标
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

function trim(str) {
		str = str.replace(/^(\s|\u00A0)+/, '');
		for ( var i = str.length - 1; i >= 0; i--) {
			if (/\S/.test(str.charAt(i))) {
				str = str.substring(0, i + 1);
				break;
			}
		}
		return str;
	}
</script>

</head>

<body>
	
	<div id='divAlertDetail'></div>
</body>
</html>