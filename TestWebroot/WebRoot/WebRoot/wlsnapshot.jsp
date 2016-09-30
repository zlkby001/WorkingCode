<%@ page language="java" import="java.util.*,java.net.*;" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
	//request.getSession(true).setAttribute("KnowledgeSearchType", null);	
 	//String ics_name = URLDecoder.decode(request.getParameter("ics_name"),"UTF-8");;
 	//String ics_name = request.getParameter("ics_name");    
  	String indexid=request.getParameter("indexid");
  	String userid=request.getParameter("userid");
 	String issuedate=request.getParameter("sdate");
  	//String back=request.getParameter("back");
  	//String back=(String)request.getSession(true).getAttribute("back");
  	
      String url = "servlet/wlsnapshotServlet?issuedate=" + issuedate +"&indexid="+indexid;
 	//if(back.equals("1"))
 		//url+="&back=1";
 	//else
 		//url+="&back=0";
 	
 	request.getSession(true).setAttribute("userid", userid);
 	request.getSession(true).setAttribute("issuedate", issuedate);
 	request.getSession(true).setAttribute("indexid", indexid);
 	//String name=request.getSession(true).getAttribute("name");
 	//String digest=request.getSession(true).getAttribute("digest");  //搜索框两个搜索条件
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'right.jsp' starting page</title>


<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">

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
//<meta http-equiv="pragma" content="no-cache">
//<meta http-equiv="cache-control" content="no-cache">

	var userId,thisGrid;
	var store;
	var searchdigest="" ;
    var	searchname="" ;
    //alert(searchdigest);
	Ext.onReady(function(){
    
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
        
        
		store = new Ext.data.JsonStore({
			root : 'rows',
			totalProperty : 'total',
			//baseParams : {
				//searchname:null,
				//searchdigest:null
			//},			
			fields : [ 
				'id','process_name','process_path','hash_value','indexid'
			],
			
			
			 
			// load using script tags for cross domain, if the data in on the same domain as
			// this page, an HttpProxy would be better
			proxy : new Ext.data.HttpProxy({
				url : '<%=url %>'
				//+'&searchname='+searchname+'&searchdigest='+searchdigest
			})
		});
		store.on("beforeload",function(thiz,options){
			thiz.baseParams["searchname"]=searchname;
			thiz.baseParams["searchdigest"]=searchdigest;
			//alert(Ext.get("processName").dom.value);
			//alert(searchname);
		});
		store.load({params:{start:0,limit:20}}); //******************加入start limit参数后请求url自动变为post方式，否则为get
		//store.load();
		//store.setDefaultSort('lastpost', 'desc');
		var  sm = new Ext.grid.CheckboxSelectionModel ({ singleSelect: false });//复选框
		var gdViewKnowledge = new Ext.grid.GridPanel({
			id:'alertDetaiGrid',
			// width:700,
			height : 300,
			autoExpandMin:100,
			title : '白名单快照详细信息',
			store : store,
			sm:sm,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			header : true,
			enableHdMenu:false,
			 tbar:[
			"进程名",
		    {
		      id:'processName',
		      width:100,
			  emptyText : '请输入进程名...',
			  xtype:'textfield'
			},"-",
			"完整性值",
		    {
		      id:'digest',
		      width:100,
			  emptyText : '请输入完整性值...',
			  xtype:'textfield'
			},"-",
		    {
		   text:"查询",
		   tooltip:"条件查询",
		   iconCls:"search",
		   handler:function(){doSearch();}
		   },"-",
		   {
		   text:"恢复快照",
		   tooltip:"恢复快照",
		   iconCls:"add",
		   handler:function(){recover();}
		   },
		   "-",
		    {
			   text:"返回",
			   tooltip:"添加新信息",
			   iconCls:"info",
			   handler:function(){history.back();}
			   }],
			//cm:new Ext.grid.RowNumberer(),
			//layout:'fit',

			columns: [ new Ext.grid.RowNumberer(),sm,{
				id : 'name', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
				width:150,
				header : "进程名",
				dataIndex : 'process_name',
				hidden:false
				//width: 50,
				//renderer: renderTopic,
			}, {
				id : 'hash',
				width:200,
				header : "hash值",
				dataIndex : 'hash_value',
				hidden:false
			},
			{
				id : 'id',
				header : "id",
				dataIndex : 'id',
				hidden:true
			},
			{
				id : 'path',
				header : "进程路径",
				dataIndex : 'process_path',
				hidden:true
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
			viewConfig : {
				forceFit : true,
				enableRowBody : true,
				getRowClass : function(record, rowIndex, p, store) {					
				}
			},

			// paging bar on the bottom
			bbar : new Ext.PagingToolbar({
				pageSize : 20,
				store : store,
				//plugins: new Ext.ux.ProgressBarPager(),
				displayInfo : true,
				displayMsg : "第 {0} - {1} 条, 共 {2} 条",
				emptyMsg : "没有符合条件的记录"
     
			})
		});
		
		
		//store.on("beforeload",function(thiz,options){
			//thiz.baseParams["searchname"]=Ext.get("processName").dom.value;
			//thiz.baseParams["searchdigest"]=Ext.get("digest").dom.value;
			//alert(Ext.get("processName").dom.value);
		//});
		//alert(Ext.get("processName").dom.value);
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
        
       // setTimeout(function(){alert(store.getTotalCount());},1000);

    });
    
    
     
    
    //================================  functions  =================================
    
    
    function renderOperate(value, p, record){
    	var userid='<%=userid%>';
    	var indexid='<%=indexid%>';
    	var issuedate='<%=issuedate%>';
    	//var addr = "wlsnapshotitem.jsp?id=" + record.data.id + "&process_name=" + record.data.process_name + "&process_path=" + record.data.process_path + "&hash_value=" + record.data.hash_value;
	    var addr = "wlsnapshotitem.jsp?id=" + record.data.id + "&process_name=" + record.data.process_name + "&process_path=" + record.data.process_path + "&hash_value=" + record.data.hash_value+"&searchname="+searchname+"&searchdigest="+searchdigest+"&indexid="+indexid+"&userid="+userid+"&issuedate="+issuedate;
		//var hash = "'" + record.data.hash_value +"'";
    	//var html = "<a class='blue' href='javascript:void(0)' onclick='wlsnapshotitem();return false;'>[详情]</a>";

        var html = "<a class='blue' href="+ addr + ">[详情]</a>";             
        return html;
    }
    
    function wlsnapshotitem(){
    		var row = thisGrid.getSelectionModel().getSelections();
			if (row.length == 0) {  
			    Ext.Msg.alert("提示信息", "请首先选择一行再操作!"); 
			    return;
			}  
			var record = row[0];
			//alert(record.data.process_name);	
			var addr = "wlsnapshotitem.jsp?id=" + record.data.id + "&process_name=" + record.data.process_name + "&process_path=" + record.data.process_path + "&hash_value=" + record.data.hash_value;
			self.location=addr;
    }
    
    function doSearch(){
    	searchdigest = Ext.getCmp('digest').getValue();
    	searchname = Ext.getCmp('processName').getValue();
    	//var ifsearch=1;
    	//alert(searchname);
    	
    	var url = 'servlet/savewlsnapshotsearchconditionServlet?searchname=' + searchname +'&searchdigest=' 
		    				+  searchdigest;
		    //alert(url);
		    ajaxSyncCall(url,  null);
		    //alert(getQueryString('ics_name'));
    	
    	store.load({params:{start:0,limit:20}}); 
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
			
			values = row[0].data.hash_value;
			for(var i=1;i<length;i++){
				values += "','";
				values += row[i].data.hash_value;
			}
			//values += "'";
			//alert(values);
			deleteKnowledgeItem(values);
	}
	
	function recover(){
			var val = confirm("您确定要恢复此白名单记录吗??");
		if (val) {
			var url = 'servlet/recoverwlServlet?indexid=' + '<%=indexid %>' +'&userid=' 
		    				+  '<%=userid %>' + '&searchname=' +searchname +'&searchdigest=' +searchdigest+'&sdate=' + '<%=issuedate %>';
		    //alert(url);
		    var result = ajaxSyncCall(url,  null);
		    //alert(getQueryString('ics_name'));
		    if (result.success) {
		    	alert("已成功恢复此白名单记录 !");
		    	//thisGrid.getStore().reload();		
		    } else {
		    	alert(result.errorMsg);
		    }
		    }
	}

	//依照id删除记录
	function deleteKnowledgeItem(values) {
		//alert(values);
		var val = confirm("您确定要删除选中的知识库项目吗??");
		if (val) {
			var url = "servlet/deleteKnowledgeItemServlet?hashValues='" + values +"'&ics_name=" 
		    				+  getQueryString('ics_name');
		    //alert(url);
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