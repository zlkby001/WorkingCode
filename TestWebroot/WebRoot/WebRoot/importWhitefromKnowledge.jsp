<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String userid = request.getParameter("oid");
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

	Ext.onReady(function(){
    
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
        var store = new Ext.data.JsonStore({
			root : 'rows',
			totalProperty : 'total',			
			fields : [ 'id','name','version'
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
				header : "版本",
				dataIndex : 'version',
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
    	var html = "<a class='blue' href='javascript:void(0)' onclick='scanKnowledge();return false;'>[详情]</a>&nbsp;&nbsp;<a class='blue' href='javascript:void(0)' onclick='importKnowledge("+record.data.id+");return false;'>[加入白名单]";
        return html;
    }
    
    function importKnowledge(ids){
    var val = confirm("您确定要导入知识库吗??");
    
    if(val){
    var userid =<%=userid%>
    document.forms[0].action="servlet/importwhitefromknowledgeServlet?userid="+userid+'&ics_id='+encodeURI(encodeURI(ids));
    document.forms[0].submit();
    
    Ext.Msg.wait('文件导入中...','稍等',
    {
    	text: '进度',
    	scope: this
    });
    
    }
    return true
   
    }
    
    function addKnowledge() {
		var name = prompt("请输入知识库名称：", "新建知识库");
		if (!name || !trim(name)) {
			return;
		}
		
		var ver = prompt("请输入知识库版本：", "新建知识库");
		if (!ver || !trim(ver)) {
			return;
		}
		
		//alert(thisNode.id.substr(1));
		var result = ajaxSyncCall('servlet/knowledgeListServlet?type=add&name=' + encodeURI(encodeURI(name))+'&ver='+ encodeURI(encodeURI(ver)), null);
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
	<form name="fr" id="form1" action="" method="post" enctype="multipart/form-data" onsubmit="return importKnowledge(this);">
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="1116" height="191">
		        <tbody>
		        
		            <tr>
		                <td colspan="2" align="middle" height="40">
		                   
		                </td>

		            </tr>
		            
		            <tr>
		                <td colspan="2" align="middle" height="30">
		                   <center><strong><font color="#cc0000">导入知识库文件</font></strong></center>
		                </td>
		           </tr>
		           <tr>
		                <td colspan="2" align="middle" >
		                   <center><strong><font color="#cc0000">请选择正确文件格式！</font></strong></center>
		                </td>
		           </tr>
		            
		        	
		            
		          <tr> 
                <td height="20"></td>
                </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="10%">选择要上传的完整性元数据:</td>
		        	<td style="padding-left: 2px;">
		        		<input id = "fileText" type="file" name="upfile" size="50">
		        		<font color="#cc0000">*</font>
		        	</td>


  					 
		        </tr>
	
                <tr> 
                <td height="20"></td>
                </tr>
                
   					   			
   					   			<tr bgcolor="#9AADCD">
   					   			<td width="20%"></td>
   					   			<td>

   					   			<table>
   					   			<tbody><tr>
   					   			<td style="padding-left: 0px;" align="left" width="33%">
   					   			<input value="  提交   "  type="submit"  >
   					   			<!-- <input value="  提交   "  type="button" onclick="daoru();"  > -->
   					   			</td>		
   					   		<!-- 	<td style="padding-left: 0px;" align="left" width="33%">
   					   			<input type="button" value=" 返回   " onclick="history.back();">	
   					   		
   					   			</td>  -->               				                
			   	   			 </tr></tbody></table></td>      				              						                			                
              				</tr>

              				
                
                </tbody>
                </table>

    </form>	 
	<div id='divAlertDetail'></div>
</body>
</html>