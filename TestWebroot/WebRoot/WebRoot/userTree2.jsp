<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="cn.ac.sklois.imm.admin.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>title</TITLE>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<META content="MSHTML 6.00.6000.16788" name=GENERATOR>

<script type="text/javascript" src="extjs3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs3/ext-all-debug.js"></script>

<link rel="stylesheet" type="text/css"
	href="extjs3/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="css/icon.css" />

<%
	String ip = request.getParameter("ip");
	String username = request.getParameter("username");
	String pubkey = request.getParameter("pubkey");
	String verifydate = request.getParameter("verifydate");
    String wldate = request.getParameter("wldate");
    String indexid=request.getParameter("indexid");
    String url = "servlet/wlsnapshotServlet?indexid=" + indexid;
    String userid=request.getParameter("userid");
 	String issuedate=request.getParameter("issuedate");
 	request.getSession(true).setAttribute("issuedate", issuedate);
 	request.getSession(true).setAttribute("indexid", indexid);
	String addr = "servlet/treeInfoServlet?ip=" + ip + "&username="
    + username + "&pubkey=" + pubkey + "&verifydate="
    + verifydate+ "&wldate="+ wldate;
%>

<script language="JavaScript">
	Ext.BLANK_IMAGE_URL ="img/s.gif";
	var thisTree,thisNode,userId;	

	Ext.onReady(function() {
    	/**
    	 * 树面板
    	 */
    	var leftTree = new Ext.tree.TreePanel(
        {
        	id : 'leftTree',
        	//title: '左侧树',
        	//region: 'west',
        	title : '拓扑结构',
        	border : false,
        	header : false,
        	root : new Ext.tree.AsyncTreeNode({
        		id : 'root',
        		text : '服务器',
        		expanded : true,
        		iconCls:'computer',
        		leaf : false
        	}),
        	width: 200,
        	autoScroll : true,
        	split : true,
        	rootVisible : true,
        	collapsible : true,
        	contextMenu : new Ext.menu.Menu(
            {
            	id:'treeMenu',
            	minWidth:100,
            	width:120,
            	
            	items : [ {
            		id: 'deleteUser',
            		iconCls : 'delete',
            		text : '删除用户'
            	}, {
            		id: 'scanAllUser',
            		iconCls : 'table_refresh',
            		text : '查看全部用户'
            	}, {
            		id: 'scanAll',
            		iconCls : 'add',
            		text : '查看全部日志'
            	}, {
            		id: 'importKnowledge',
            		iconCls : 'grid',
            		text : '导入白名单模板'
            	}, {
            		id: 'scanDetail',
            		iconCls : 'grid',
            		text : '查看详情'
            	}, {
            		id: 'contrastLog',
            		iconCls : 'contrast',
            		text : '对比日志'
            	} , {
            		id: 'recoveryWhite',
            		iconCls : 'recovery',
            		text : '恢复快照'
            	}, {
            		id: 'deleteLog',
            		iconCls : 'delete',
            		text : '删除本次日志'
            	} , {
            		id: 'addFac',
            		iconCls : 'add',
            		text : '添加厂区'
            	}, {
            		id: 'deleteFac',
            		iconCls : 'delete',
            		text : '删除厂区'
            	}, {
            		id: 'renameFac',
            		iconCls : 'info',
            		text : '重命名厂区'
            	}, {
            		id: 'addTec',
            		iconCls : 'add',
            		text : '添加工艺'
            	}, {
            		id: 'deleteTec',
            		iconCls : 'delete',
            		text : '删除工艺'
            	}, {
            		id: 'renameTec',
            		iconCls : 'info',
            		text : '重命名工艺'
            	},{
            		id: 'addSys',
            		iconCls : 'add',
            		text : '添加控制系统'
            	}, {
            		id: 'deleteSys',
            		iconCls : 'delete',
            		text : '删除控制系统'
            	}, {
            		id: 'renameSys',
            		iconCls : 'info',
            		text : '重命名控制系统'
            	}, "-",{
            		id: 'cancle',
            		iconCls : '',
            		text : '取消'
            	}],
            	listeners : {
            		itemclick : function(item) {
                var nodeDataDel = item.parentMenu.contextNode.attributes;		//当前结点属性集合
                var parentNodeData = item.parentMenu.contextNode.parentNode;	//父结点
                //var nodeData = orgPanel.getSelectionModel().getSelectedNode();
                switch (item.id) {
                case 'deleteUser':
                	deleteUser(nodeDataDel);
                	break;
                case 'scanAllUser':
                	scanAllUser();
                	break;
                case 'scanAll':
                	if(thisNode.parentNode.text=='远程证明日志')
                		parent.loadmain('servlet/historyDetailServlet?oid='
		        			+ thisNode.parentNode.parentNode.id.substr(1) + '&sdate=');
		        	else
		        		parent.loadmain('servlet/historyDetailServlet?oid='
		        			+ thisNode.id.substr(1) + '&sdate=');
                	break;
                case 'scanDetail':
                	parent.loadmain('servlet/historyDetailServlet?oid='
		        		+ thisNode.parentNode.parentNode.id.substr(1) + '&sdate='+thisNode.text);
                	break;
                case 'contrastLog':
                	contrastLog();
                	break;
                case 'recoveryWhite':
                	recoveryWhite();
                	break;	
                case 'deleteLog':
                	deleteLog();
                	break;
                case 'addFac':
                	addFactory();
                	break;
              	case 'deleteFac':
                	deleteFactory();
                	break;
               	case 'renameFac':
                	renameFactory();
                	break;
                case 'addTec':
                	addTechnology();
                	break;
                case 'deleteTec':
                	deleteTechnology();
                	break;
                case 'renameTec':
                	renameTechnology();
                	break;
                case 'addSys':
                	addSystem();
                	break;
                case 'deleteSys':
                	deleteSystem();
                	break;
                case 'renameSys':
                	renameSystem();
                	break;
                case 'importKnowledge':
                	parent.loadmain('importWhitefromKnowledge.jsp?oid='
		        			+ thisNode.id.substr(1));
                	break;
                }
                
                item.parentMenu.hide();
            		}
            	}
            }),
        	loader : new Ext.tree.TreeLoader(
            {
            	dataUrl : '<%=addr%>',
            	listeners : {
            		'beforeload' : function(treeLoader,
                	node) {
                		this.baseParams.text = node.attributes.text;
            		}
            	}
            }),
            
        	listeners : {
        		'click' : loadNodeInfo,
        		afterrender : function(t) {
            treeMask = new Ext.LoadMask(t.getEl(), {
            	msg : "数据加载中..."
            })
            //orgTreePanel.root.expand(true);
            //orgPanel = t;
        		},
        	contextmenu : function(node, e) {
            appContextmenu = false;
            node.select();
            var type = node.id.substr(0,1);
            switch(type){
            	case '1':
            		Ext.getCmp('scanAllUser').hide(); 
            		Ext.getCmp('scanAll').hide(); 
            		Ext.getCmp('scanDetail').hide(); 
            		Ext.getCmp('deleteLog').hide(); 
            		Ext.getCmp('deleteUser').hide(); 
            		Ext.getCmp('scanAllUser').hide(); 
            		Ext.getCmp('scanAll').hide(); 
            		Ext.getCmp('scanDetail').hide(); 
            		Ext.getCmp('contrastLog').hide();
            		Ext.getCmp('recoveryWhite').hide();
            		Ext.getCmp('deleteLog').hide();
            		Ext.getCmp('addFac').hide(); 
            		Ext.getCmp('deleteFac').show();
            		Ext.getCmp('addTec').show(); 
            		Ext.getCmp('deleteTec').hide(); 
            		Ext.getCmp('addSys').hide(); 
            		Ext.getCmp('deleteSys').hide(); 
            		Ext.getCmp('renameFac').show(); 
            		Ext.getCmp('renameTec').hide(); 
            		Ext.getCmp('renameSys').hide(); 
            		Ext.getCmp('importKnowledge').hide();
            		break;
				case '2':	  
            		Ext.getCmp('scanAllUser').hide(); 
            		Ext.getCmp('scanAll').hide(); 
            		Ext.getCmp('scanDetail').hide(); 
            		Ext.getCmp('deleteLog').hide(); 
            		Ext.getCmp('deleteUser').hide(); 
            		Ext.getCmp('scanAllUser').hide(); 
            		Ext.getCmp('scanAll').hide(); 
            		Ext.getCmp('scanDetail').hide(); 
            		Ext.getCmp('deleteLog').hide();
            		Ext.getCmp('recoveryWhite').hide();
            		Ext.getCmp('contrastLog').hide();
            		Ext.getCmp('addFac').hide(); 
            		Ext.getCmp('deleteFac').hide();
            		Ext.getCmp('addTec').hide(); 
            		Ext.getCmp('deleteTec').show(); 
            		Ext.getCmp('addSys').show(); 
            		Ext.getCmp('deleteSys').hide();  
            		Ext.getCmp('renameFac').hide(); 
            		Ext.getCmp('renameTec').show(); 
            		Ext.getCmp('renameSys').hide(); 
            		Ext.getCmp('importKnowledge').hide();
            		break;
            	case '3':
            		Ext.getCmp('scanAllUser').hide(); 
            		Ext.getCmp('scanAll').hide(); 
            		Ext.getCmp('scanDetail').hide(); 
            		Ext.getCmp('deleteLog').hide(); 
            		Ext.getCmp('deleteUser').hide(); 
            		Ext.getCmp('scanAllUser').hide(); 
            		Ext.getCmp('scanAll').hide(); 
            		Ext.getCmp('scanDetail').hide(); 
            		Ext.getCmp('contrastLog').hide();
            		Ext.getCmp('recoveryWhite').hide();
            		Ext.getCmp('deleteLog').hide();
            		Ext.getCmp('addFac').hide(); 
            		Ext.getCmp('deleteFac').hide();
            		Ext.getCmp('addTec').hide(); 
            		Ext.getCmp('deleteTec').hide(); 
            		Ext.getCmp('addSys').hide(); 
            		Ext.getCmp('deleteSys').show();  
            		Ext.getCmp('renameFac').hide(); 
            		Ext.getCmp('renameTec').hide(); 
            		Ext.getCmp('renameSys').hide();
            		Ext.getCmp('importKnowledge').hide(); 
            		break;
            	
            	case '4':
            		Ext.getCmp('addFac').hide(); 
            		Ext.getCmp('deleteFac').hide();
            		Ext.getCmp('addTec').hide(); 
            		Ext.getCmp('deleteTec').hide(); 
            		Ext.getCmp('addSys').hide(); 
            		Ext.getCmp('deleteSys').hide();  
            		Ext.getCmp('deleteUser').show(); 
            		Ext.getCmp('scanAllUser').show(); 
            		Ext.getCmp('scanAll').show(); 
            		Ext.getCmp('importKnowledge').show();
            		Ext.getCmp('scanDetail').hide(); 
            		Ext.getCmp('contrastLog').hide();
            		Ext.getCmp('recoveryWhite').hide();
            		Ext.getCmp('deleteLog').hide(); 
            		Ext.getCmp('renameFac').hide(); 
            		Ext.getCmp('renameTec').hide(); 
            		Ext.getCmp('renameSys').hide(); 
            		break;           	
            
            }
            
            var parent = node.parentNode;
            //alert(parent.text);
            if(!parent){
            		Ext.getCmp('scanAllUser').hide(); 
            		Ext.getCmp('scanAll').hide(); 
            		Ext.getCmp('scanDetail').hide(); 
            		Ext.getCmp('deleteLog').hide(); 
            		Ext.getCmp('deleteUser').hide(); 
            		Ext.getCmp('scanAllUser').hide(); 
            		Ext.getCmp('scanAll').hide(); 
            		Ext.getCmp('scanDetail').hide();
            		Ext.getCmp('contrastLog').hide(); 
            		Ext.getCmp('recoveryWhite').hide();
            		Ext.getCmp('deleteLog').hide();
            		Ext.getCmp('addFac').show(); 
            		Ext.getCmp('deleteFac').hide();
            		Ext.getCmp('addTec').hide(); 
            		Ext.getCmp('deleteTec').hide(); 
            		Ext.getCmp('addSys').hide(); 
            		Ext.getCmp('deleteSys').hide();  
            		Ext.getCmp('renameFac').hide(); 
            		Ext.getCmp('renameTec').hide(); 
            		Ext.getCmp('renameSys').hide(); 
            		Ext.getCmp('importKnowledge').hide();
            }
            else if(parent.text=='远程证明日志')
            {
            		Ext.getCmp('addFac').hide(); 
            		Ext.getCmp('deleteFac').hide();
            		Ext.getCmp('addTec').hide(); 
            		Ext.getCmp('deleteTec').hide(); 
            		Ext.getCmp('addSys').hide(); 
            		Ext.getCmp('deleteSys').hide();  
            		Ext.getCmp('deleteUser').hide(); 
            		Ext.getCmp('scanAllUser').show(); 
            		Ext.getCmp('scanAll').show(); 
            		Ext.getCmp('scanDetail').show(); 
            		Ext.getCmp('contrastLog').show();
            		Ext.getCmp('recoveryWhite').hide();
            		Ext.getCmp('deleteLog').show(); 
            		Ext.getCmp('renameFac').hide(); 
            		Ext.getCmp('renameTec').hide(); 
            		Ext.getCmp('renameSys').hide(); 
            		Ext.getCmp('importKnowledge').hide();
            }
            else if(parent.text=='白名单信息')
            {
            		Ext.getCmp('addFac').hide(); 
            		Ext.getCmp('deleteFac').hide();
            		Ext.getCmp('addTec').hide(); 
            		Ext.getCmp('deleteTec').hide(); 
            		Ext.getCmp('addSys').hide(); 
            		Ext.getCmp('deleteSys').hide();  
            		Ext.getCmp('deleteUser').hide(); 
            		Ext.getCmp('scanAllUser').show(); 
            		Ext.getCmp('scanAll').hide(); 
            		Ext.getCmp('scanDetail').hide(); 
            		Ext.getCmp('contrastLog').hide();
            		Ext.getCmp('recoveryWhite').show();
            		Ext.getCmp('deleteLog').hide(); 
            		Ext.getCmp('renameFac').hide(); 
            		Ext.getCmp('renameTec').hide(); 
            		Ext.getCmp('renameSys').hide(); 
            		Ext.getCmp('importKnowledge').hide();
            }   
            else if(parent.text=='审计信息')
            {
            		Ext.getCmp('addFac').hide(); 
            		Ext.getCmp('deleteFac').hide();
            		Ext.getCmp('addTec').hide(); 
            		Ext.getCmp('deleteTec').hide(); 
            		Ext.getCmp('addSys').hide(); 
            		Ext.getCmp('deleteSys').hide();  
            		Ext.getCmp('deleteUser').hide(); 
            		Ext.getCmp('scanAllUser').show(); 
            		Ext.getCmp('scanAll').hide(); 
            		Ext.getCmp('scanDetail').hide(); 
            		Ext.getCmp('contrastLog').hide();
            		Ext.getCmp('recoveryWhite').show();
            		Ext.getCmp('deleteLog').hide(); 
            		Ext.getCmp('renameFac').hide(); 
            		Ext.getCmp('renameTec').hide(); 
            		Ext.getCmp('renameSys').hide(); 
            		Ext.getCmp('importKnowledge').hide();
            }
           
            
            if(!parent||!isNaN(type)||parent.text=='远程证明日志'){            
            	var tree = node.getOwnerTree();
            	var c = tree.contextMenu;
            	c.contextNode = node;
            	c.showAt(e.getXY());
            	thisNode = node;
            	//setPublicUserId();
            	//node.expand();
            	//alert(rootNode.childNodes.length);
            }
            else if(!parent||!isNaN(type)||parent.text=='白名单信息'){            
            	var tree = node.getOwnerTree();
            	var c = tree.contextMenu;
            	c.contextNode = node;
            	c.showAt(e.getXY());
            	thisNode = node;
            	//setPublicUserId();
            	//node.expand();
            	//alert(rootNode.childNodes.length);
            }
            else if(!parent||!isNaN(type)||parent.text=='审计信息'){            
            	var tree = node.getOwnerTree();
            	var c = tree.contextMenu;
            	c.contextNode = node;
            	c.showAt(e.getXY());
            	thisNode = node;
            	//setPublicUserId();
            	//node.expand();
            	//alert(rootNode.childNodes.length);
            }
        		}

        	}
        });

    	var vp = new Ext.Viewport({
    		layout : 'fit',
    		items : [ leftTree ]
    	});
    	
    	thisTree = leftTree;
		setTimeout(function(){expand();}, 500);
		//alert(rootNode.childNodes.length);
       
    });    
    
	
	/** 
	 *	展开三级树
	 */
	function expand(){
	 var rootNode = thisTree.getRootNode();
	 rootNode.expand();//alert(rootNode.hasChildNodes());
	 rootNode.eachChild(function(child){
	 //alert(1);
		 	child.expand();
		 	child.eachChild(function(child){
				 	child.expand();
				 	child.eachChild(function(child){
						 	child.expand();
					     });
			     });
	     });
	 //tree.expandAll();
}

	function deleteFactory(){
		if(thisNode.childNodes.length>0){
			alert('该结点存在关联子结点，不允许删除！');
			return;
		}
		var val = confirm("您确定要删除厂区 " + thisNode.text + " 吗?");
		var prams = 'type=delete&id=' + thisNode.id.substr(1) + '&id=1';	
		if (val) {
			var result = ajaxSyncCall('servlet/factoryServlet', prams);
			if (result.success) {
				thisNode.remove();
				alert("已成功删除该厂区!");
				

			} else {
				alert(result.errorMsg);
			}
		}
	
	}
	
	function addFactory(){
		var name=prompt("请输入厂区名称：","新建厂区");
	    if(!name||!trim(name))
	    {
	        return;
	    }
	    var prams = 'type=add&name=' + encodeURI(encodeURI(name)) + '&fid=1';	
		var result = ajaxSyncCall('servlet/factoryServlet', prams);
		if (result.newid) {
		var n = {
				id:'1' + result.newid,
				text : name,
				leaf : true,
				iconCls : "factory"
			};
			thisNode.appendChild(n);
			thisNode.leaf = false;
			thisNode.expand();
			
			alert("添加厂区成功!");
		} else {
			alert("添加厂区失败！ 请重新添加。");
		}
	}
	
	function renameFactory(){
		var name=prompt("请输入厂区新名称：",thisNode.text);
	    if(!name||!trim(name))
	    {
	        return;
	    }
		var prams = 'type=edit&id=' + thisNode.id.substr(1) + '&name=' + encodeURI(encodeURI(name));
		var result = ajaxSyncCall('servlet/factoryServlet', prams);
		if (result.success) {
			thisNode.text = name;			
			thisTree.getRootNode().reload();
			alert("修改厂区名称成功!");
			setTimeout(function(){expand()}, 200);
			
		} else {
			alert(result.errorMsg);
		}
	}

	function deleteTechnology() {
		if(thisNode.childNodes.length>0){
			alert('该结点存在关联子结点，不允许删除！');
			return;
		}
		var val = confirm("您确定要删除工艺 " + thisNode.text + " 吗?");
		var prams = 'type=delete&id=' + thisNode.id.substr(1) + '&id=1';
		if (val) {
			var result = ajaxSyncCall('servlet/technologyServlet', prams);
			if (result.success) {
				thisNode.remove();
				alert("已成功删除该工艺!");

			} else {
				alert(result.errorMsg);
			}
		}

	}

	function addTechnology() {
		var name = prompt("请输入工艺名称：", "新建工艺");
		if (!name || !trim(name)) {
			return;
		}
		//alert(thisNode.id.substr(1));
		var prams = 'type=add&name=' + encodeURI(encodeURI(name)) + '&fid='
				+ thisNode.id.substr(1);
		var result = ajaxSyncCall('servlet/technologyServlet', prams);
		if (result.newid) {
			var n = {
				id : '2' + result.newid,
				text : name,
				leaf : true,
				iconCls : "technology"
			};
			thisNode.appendChild(n);
			thisNode.leaf = false;
			thisNode.expand();
			alert("添加工艺成功!");
		} else {
			alert("添加工艺失败！ 请重新添加。");
		}
	}

	function renameTechnology() {
		var name = prompt("请输入工艺新名称：", thisNode.text);
		if (!name || !trim(name)) {
			return;
		}
		var prams = 'type=edit&id=' + thisNode.id.substr(1) + '&name=' + encodeURI(encodeURI(name));
		var result = ajaxSyncCall('servlet/technologyServlet', prams);
		if (result.success) {
			thisNode.text = name;
			thisTree.getRootNode().reload();
			alert("修改工艺名称成功!");
			setTimeout(function(){expand()}, 200);
		} else {
			alert(result.errorMsg);
		}
	}

	function deleteSystem() {
		if(thisNode.childNodes.length>0){
			alert('该控制系统中存在相关用户信息，不允许删除！');
			return;
		}
		var val = confirm("您确定要删除控制系统 " + thisNode.text + " 吗?");
		var prams = 'type=delete&id=' + thisNode.id.substr(1) + '&id=1';
		if (val) {
			var result = ajaxSyncCall('servlet/systemServlet', prams);
			if (result.success) {
				thisNode.remove();
				alert("已成功删除该控制系统!");

			} else {
				alert(result.errorMsg);
			}
		}

	}

	function addSystem() {
		var name = prompt("请输入控制系统名称：", "新建控制系统");
		if (!name || !trim(name)) {
			return;
		}
		var prams = 'type=add&name=' + encodeURI(encodeURI(name)) + '&fid='
				+ thisNode.id.substr(1);
		var result = ajaxSyncCall('servlet/systemServlet', prams);
		if (result.newid) {
			var n = {
				id : '3' + result.newid,
				text : name,
				leaf : true,
				iconCls : "system"
			};
			thisNode.appendChild(n);
			thisNode.leaf = false;
			thisNode.expand();
			alert("添加控制系统成功!");
		} else {
			alert("添加控制系统失败！ 请重新添加。");
		}
	}

	function renameSystem() {
		var name = prompt("请输入控制系统新名称：", thisNode.text);
		if (!name || !trim(name)) {
			return;
		}
		var prams = 'type=edit&id=' + thisNode.id.substr(1) + '&name=' + encodeURI(encodeURI(name));
		var result = ajaxSyncCall('servlet/systemServlet', prams);
		if (result.success) {
			thisNode.text = name;
			thisTree.getRootNode().reload();
			alert("修改控制系统名称成功!");
			setTimeout(function(){expand()}, 200);
		} else {
			alert(result.errorMsg);
		}
	}

	/** 
	 *	删除日志
	 */
	function deleteLog() {
		if (thisNode.parentNode.text != '远程证明日志')
			return;
		//alert(n.id);
		var val = confirm("您确定要删除 " + thisNode.text + " 的远程证明日志吗??");
		var prams = 'delId=' + thisNode.parentNode.parentNode.id.substr(1) + '&delTime=' + thisNode.text;
		if (val) {
			var result = ajaxSyncCall('servlet/deleteDigestServlet', prams);
			if (result.success) {
				thisNode.remove();
				alert("已成功删除该日志!");

			} else {
				alert(result.errorMsg);
			}
		}
	}
   /** 
	 *	对比日志
	 */
	function contrastLog() {
	     var url='contrast.jsp?userid='+ thisNode.parentNode.parentNode.id.substr(1)+ '&sdate=' +thisNode.text;
         window.open (url,'对比结果')  
    }
	 /** 
	 *	恢复快照
	 */
	 function recoveryWhite(){
			var val = confirm("您确定要恢复此白名单记录吗??");
		if (val) {
			var url = 'servlet/recoverwlServlet?userid='
					+ thisNode.parentNode.parentNode.id.substr(1)+ '&sdate=' +thisNode.text;
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
	/** 
	 *	查看全部用户
	 */
	function scanAllUser() {
		var addr = 'userTree2.jsp?ip=&username=&pubkey=&verifydate=';
		parent.setTreePanel(addr);
	}

	/** 
	 *	删除用户
	 */
	function deleteUser(n) {
		if (isNaN(n.id))
			return;
		//alert(n.id);
		var val = confirm("您确定要删除用户 " + n.text + " 吗?");
		var prams = 'oid=' + n.id.substr(1);
		if (val) {
			var result = ajaxSyncCall('servlet/deleteUserServlet', prams);
			if (result.success) {
				thisNode.remove();
				alert("已成功删除该用户!");

			} else {
				alert(result.errorMsg);
			}
		}
	}

	/** 
	 *通用JS 同步ajax调用 返回json Object 
	 */
	function ajaxSyncCall(urlStr, paramsStr) {
		var obj;
		if (window.ActiveXObject) {
			obj = new ActiveXObject('Microsoft.XMLHTTP');
		} else if (window.XMLHttpRequest) {
			obj = new XMLHttpRequest();
		}
		obj.open('POST', urlStr, false);
		obj.setRequestHeader('Content-Type',
				'application/x-www-form-urlencoded');
		obj.send(paramsStr);
		var result = Ext.util.JSON.decode(obj.responseText);
		return result;
	}

	/** 
	 *终端信息、白名单信息、报警日志、远程证明日志，点击后在中央展示详情 
	 */
	var loadNodeInfo = function(node, e) {
		//userId = node.attributes.id;
		thisNode = node;
		if (thisNode.text == '终端信息') {
			parent.loadmain('servlet/viewUserServlet?oid='
					+ thisNode.parentNode.id.substr(1));
		} else if (thisNode.text == '白名单信息') {
			parent.loadmain('servlet/userDetailServlet?oid='
					+ thisNode.parentNode.id.substr(1));	
		}else if (thisNode.text == '审计信息') {
			parent.loadmain('AuditLogList.jsp?oid='
					+ thisNode.parentNode.id.substr(1));	
		} else if (thisNode.text == '报警日志') {
			parent.loadmain('alertListDetail.jsp?oid='
							+ thisNode.parentNode.id.substr(1));
		}  
		
	//	else if (thisNode.text == 'U盘白名单') {
	//		parent.loadmain('usbLocal.jsp?oid='
		//					+ thisNode.parentNode.id.substr(1));
	//	} 
		else if (thisNode.parentNode.text == '远程证明日志') {
		//parent.loadmain('servlet/gethistoryintoknowledgeServlet?oid='
			parent.loadmain('servlet/historyDetailServlet?oid='
					+ thisNode.parentNode.parentNode.id.substr(1) + '&sdate='
					+ thisNode.text);
				
		}else if (thisNode.parentNode.text == '白名单信息') {
			parent.loadmain('wlsnapshot.jsp?userid='
					+ thisNode.parentNode.parentNode.id.substr(1) + '&sdate='
					+ thisNode.text);
				
		}

		if (!id) {
			Ext.Msg.alert('提示信息', '节点必须设置唯一的id');
			return;
		}

	};
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

</HEAD>

<BODY>


</BODY>
</HTML>
