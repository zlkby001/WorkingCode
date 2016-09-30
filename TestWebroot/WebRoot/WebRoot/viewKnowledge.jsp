<%@ page language="java" import="java.util.*,java.net.*;" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
	request.getSession(true).setAttribute("KnowledgeSearchType", null);	
 	//String ics_name = URLDecoder.decode(request.getParameter("ics_name"),"UTF-8");;
 	String ics_name = request.getParameter("ics_name");  
 	String ics_id = request.getParameter("ics_id");  
 	String url = "servlet/viewKnowledgeItemServlet?ics_name=" + URLEncoder.encode(ics_name,"UTF-8")+"&ics_id="+ics_id;
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
 
 .deletepro{
 color: blue; text-decoration:underline
 }


</style>

<script type="text/javascript" src="extjs3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs3/ext-all.js"></script>
<script type="text/javascript" src="js/myjs.js"></script>

<script type="text/javascript">
	var userId,thisGrid;
	var store;
	
	var osType = new Array();
	osType = [ 
    ['XP系统','XP系统'], 
    ['Windows7','Windows7'], 
    ['WinServer2003','WinServer2003'], 
    ['WinServer2000','WinServer2000'], 
    ['Linux','Linux']
];

	var softwareType = new Array();
	softwareType = [ 
    ['工控软件','工控软件'], 
    ['应用软件','应用软件'],
    ['操作系统','操作系统'], 
    ['办公软件','办公软件'],
    ['系统进程','系统进程'],
    ['编程开发','编程开发'],
    ['杀毒安全','杀毒安全'],
    ['系统工具','系统工具'],     
    ['图形图像','图形图像'],
    ['多媒体','多媒体'],
    ['网络软件','网络软件'],
    ['游戏娱乐','游戏娱乐']   
];

	Ext.onReady(function(){
    
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
        var genus_store = new Ext.data.SimpleStore( {
		  fields : [ 'value', 'text' ],
		  data : [ [ '0', '已添加描述信息' ], [ '1', '未添加描述信息' ],[ '2', '全部进程信息' ]]
		 });
		        
        store = new Ext.data.JsonStore({
			root : 'rows',
			totalProperty : 'total',	
			remoteSort: true,		
			fields : [ 
			'id','process_name','hash_value','software_ver','os_ver','software_info','release_date','Manufacturer','ic_bool','ics_name'
			],

			// load using script tags for cross domain, if the data in on the same domain as
			// this page, an HttpProxy would be better
			proxy : new Ext.data.HttpProxy({
				url : '<%=url %>'
			})
		});
		store.load({params:{start:0,limit:30}}); 
		//store.setDefaultSort('lastpost', 'desc');
		
		var  sm = new Ext.grid.CheckboxSelectionModel ({ singleSelect: false });//复选框
		var gdViewKnowledge = new Ext.grid.GridPanel({
			id:'alertDetaiGrid',
			// width:700,
			height : 300,
			autoExpandMin:100,
			title : '知识库详细信息',
			store : store,
			sm:sm,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			header : true,
			enableHdMenu:false,
			 tbar:["类型",
			 {
		     xtype : 'combo',  
		     name : 'genus',
		     id : 'comboType',
		     width:100,
		     mode : 'local',
		     readOnly :true,
		     emptyText : '请选择类型...',
		     store : genus_store,
		     //hiddenName : 'genus',
		     triggerAction : 'all',  
		     editable:false,
		     valueField : 'value',
		     displayField : 'text'
		    },
			"-",
			"进程名",
		    {
		      id:'processName',
		      width:100,
			  emptyText : '请输入进程名...',
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
			"软件类型",
		    {
		      id:'softwareType',
		      width:100,
			  emptyText : '请选择软件类型...',
			  xtype:'combo',
			  store: new Ext.data.SimpleStore({
                fields: ['value', 'text'],
                data : softwareType
                }),
            valueField:'value',
            displayField:'text',
            typeAhead: true,
            mode: 'local',
            triggerAction: 'all',
            editable:true
			},
			"-",
		    {
		   text:"查询",
		   tooltip:"条件查询",
		   iconCls:"search",
		   handler:function(){doSearch();}
		   },"-",
		   {
		   text:"删除选中",
		   tooltip:"删除选中",
		   iconCls:"delete",
		   handler:function(){deleteRows();}
		   },
		   "-",
		    {
			   text:"返回知识库",
			   tooltip:"添加新信息",
			   iconCls:"info",
			   handler:function(){history.back();}
			   }],
			//cm:new Ext.grid.RowNumberer(),
			//layout:'fit',

			columns : [ new Ext.grid.RowNumberer(),sm,{
				id : 'name', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
				header : "进程名",
				dataIndex : 'process_name',
				hidden:false
				//width: 50,
				//renderer: renderTopic,
			}, {
				id : 'hash',
				header : "hash值",
				dataIndex : 'hash_value',
				hidden:false
			}, {
				id : 'softwareVer',
				header : "软件号",
				dataIndex : 'software_ver',
				//sortable:true,
				hidden:false
			}, {
				id : 'osVer',
				header : "操作系统号",
				dataIndex : 'os_ver',
				hidden:false
			}, {
				id : 'softwareInfo',
				header : "软件信息",
				dataIndex : 'software_info',
				//remoteSort: true,
				//sortable:true,
				hidden:false
			}, {
				id : 'releaseDate',
				header : "发布日期",
				dataIndex : 'release_date',
				hidden:false
			}, {
				id : 'Manufacturer',
				header : "制造商",
				dataIndex : 'Manufacturer',
				hidden:false
			}, {
				id : 'icBool',
				header : "工控软件",
				dataIndex : 'ic_bool',
				hidden:false
			}, {
				id : 'icsName',
				header : "知识库名",
				dataIndex : 'ics_name',
				renderer : renderKnowledgeName
			}, 
			{  
		        id : 'operateCol',  
		        width:100,
		        resizable:false,
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
				pageSize : 30,
				store : store,
				//plugins: new Ext.ux.ProgressBarPager(),
				displayInfo : true,
				displayMsg : "第 {0} - {1} 条, 共 {2} 条",
				emptyMsg : "没有符合条件的记录",
				listeners : {  
                    "beforechange" : function(bbar, params){  
                        var name = Ext.getCmp('processName').getValue();
				    	var os = Ext.getCmp('osType').getValue();
				    	var software = Ext.getCmp('softwareType').getValue();
				    	//alert(name);
				    	Ext.apply(params, {
				              name : name,
				              os :os,
				              software:software
				              });//增加自定义参数
				       
				        return true;
                    }  
                }  
     
			})
		});
		
		
		gdViewKnowledge.render('divAlertDetail');
        var viewport = new Ext.Viewport({
            layout: 'fit',
            items: [
            {
            	layout: 'fit',
                 region:'center',
                 margins:'0 0 0 0',
                 cls:'empty',
                 bodyStyle:'background:#ffffff',
                 height:600,
                 //html:'<br/><br/>&lt;empty center panel&gt;'
                 //autoLoad:'t3.html'
                 //layout:'fit',
                 items: [gdViewKnowledge]
            }]
        });
        thisGrid = gdViewKnowledge;

    });
    
     
    
    //================================  functions  =================================
    
    
    function renderOperate(value, p, record){
	    var addr = "modifyKnowledge.jsp?process_name=" + record.data.process_name + "&id=" + record.data.id + "&hash_value=" + record.data.hash_value + "&software_ver=" + encodeURI(record.data.software_ver) + "&os_ver=" + record.data.os_ver + "&software_info=" + encodeURI(record.data.software_info)
			 + "&release_date=" + record.data.release_date + "&manufacturer=" + record.data.Manufacturer + "&ic_bool=" + record.data.ic_bool + "&ics_name=" + getQueryString('ics_name');
		
    	//var html = "<a href="+ encodeURI(addr) + ">[修改]</a>&nbsp;&nbsp;<a class='deletepro' href='javascript:void(0)' onclick=deleteKnowledgeItem('" 
    				//+ record.data.hash_value + "')>[删除]</a>";
    	//modified by zhyjun at 2015.5.10
    	var html = "<a href="+ encodeURI(addr) + ">[修改]</a>&nbsp;&nbsp;<a class='deletepro' href='javascript:void(0)' onclick=deleteKnowledgeItem('" 
    				+ record.data.id + "')>[删除]</a>";
        return html;
    }
    
    function doSearch(){
    	var type = Ext.getCmp('comboType').getValue();
    	var name = Ext.getCmp('processName').getValue();
    	var os = Ext.getCmp('osType').getValue();
    	var software = Ext.getCmp('softwareType').getValue();
    	//alert(type);
    	if(type=='0'){
    		store.load({params:{start:0,limit:30,type:'known',name:name,os:os,software:software}}); 
    	}
    	else if(type=='1'){
    		store.load({params:{start:0,limit:30,type:'unknown',name:name,os:os,software:software}}); 
    	}
    	else if(type=='2'||type==''){
    		store.load({params:{start:0,limit:30,type:'all',name:name,os:os,software:software}}); 
    	}
    }
    
 	function renderKnowledgeName(value, p, record){
 		var pra = decodeURI(getQueryString('ics_name'));
    	//var name = pra.replace("+"," ") ;
        return pra;
    }
	
	function editKnowledgeItem(addr){		
		 //alert(addr);
		self.location = addr;
		//parent.loadmain(addr);
				
	}
	
	function deleteRows(){
			var values;
			var row = thisGrid.getSelectionModel().getSelections();
			var length = row.length;
			if (length == 0) {  
			    Ext.Msg.alert("提示信息", "请首先选中一行再操作!");  
			    return;
			}
			
			values = row[0].data.id;
			for(var i=1;i<length;i++){
				values += ",";
				values += row[i].data.id;
			}
			//values += "'";
			//alert(values);
			deleteKnowledgeItem(values);
	}

	//依照id删除记录
	function deleteKnowledgeItem(values) {
		//alert(values);
		var val = confirm("您确定要删除选中的知识库项目吗??");
		if (val) {
			//var url = "servlet/deleteKnowledgeItemServlet?hashValues='" + values +"'&ics_name=" 
		    				//+  getQueryString('ics_name');
		    
		    //modified by zhyjun at 2015.5.10
		    var url = "servlet/deleteKnowledgeItemServlet?id=" + values;

		    var result = ajaxSyncCall(url, null);
		    //alert(getQueryString('ics_name'));
		    if (result.success) {
		    	alert("已成功删除该知识库项目!");
		    	thisGrid.getStore().reload();		
		    } else {
		    	alert(result.errorMsg);
		    }
		}		
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

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }
</script>

</head>

<body>
	
	<div id='divAlertDetail'></div>
</body>
</html>