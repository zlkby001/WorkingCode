<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  import="cn.ac.sklois.imm.admin.*"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>title</TITLE>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<script src="easyui/jquery-1.8.0.js" type="text/javascript"></script>
<!-- <script src="js/jquery-1.4.2.js" type="text/javascript"> -->
<script src="easyui/jquery.easyui.min.js" type="text/javascript"></script>
<link href="easyui/themes/default/easyui.css" rel="stylesheet"
	type="text/css" />
<link href="easyui/themes/icon.css" rel="stylesheet" type="text/css" />

<META content="MSHTML 6.00.6000.16788" name=GENERATOR>



</HEAD>

<BODY >

<input id="hd_userId" type="hidden" />
<ul id=tt01 class="easyui-tree" data-options="url:'servlet/treeInfoServlet?ip=null&username=null&pubkey=null&verifydate=null',animate:true,lines:true,"  ">
</ul>



    <div id="mm" class="easyui-menu" style="width:120px;">
    	<div id ="deleteUser"  onclick="deleteUser()" data-options="iconCls:'icon-remove'">删除用户</div>  
    	<div id ="scanAllUser" onclick="scanAllUser()" data-options="iconCls:'icon-reload'">查看全部用户</div> 
    	<div id ="scanAll" onclick="scanAllVerifylog()" data-options="iconCls:'icon-sum'">查看全部日志</div> 
        <div id ="scanDetail"  onclick="f2('远程证明日志')" data-options="iconCls:'icon-add'">查看详情</div>          
        <div id ="deleteLog"  onclick="deleteLog()" data-options="iconCls:'icon-remove'">删除本次日志</div>  
        <div class="menu-sep"></div>  
        <div id ="cancle"  onclick="">取消</div>  
    </div> 
    
    
    
<script language="JavaScript">
	
var currentNode;
var pId,userId,nodeText;
$(document).ready(function(){		
	

	$('#tt01').tree({

   		onClick: function(node){  
				$(this).tree('toggle', node.target);  
				var nd = $(this).tree('getParent', node.target);
				var id = $(this).tree('getParent', nd.target).id;
				var txt = $(this).tree('getParent', node.target).text;
			     f1(node.text,nd.id);
				f2(node.text,txt,id);
				   
				 nodeText = node.text;
				 pId = nd.id;
				 
				 },  
            onContextMenu: function(e,node){  
                e.preventDefault();                 
			    var nd = $(this).tree('getParent', node.target);
			    if(nd.text=='远程证明日志')	{
				    $(this).tree('select',node.target); 
				    var id = $(this).tree('getParent', nd.target).id;
				    $('#mm').menu('disableItem', $('#deleteUser'));
				    $('#mm').menu('enableItem', $('#scanDetail'));
			    	$('#mm').menu('enableItem', $('#deleteLog'));
			    	
	                $('#mm').menu('show',{
	                    left: e.pageX,  
	                    top: e.pageY  
	                }); 
	                nodeText = node.text;
					userId = id;
				    currentNode = node;
				    //alert($('#hd_userId').val());
			    }
			    else if(node.id){
			    $('#mm').menu('enableItem', $('#deleteUser'));
			    $('#mm').menu('disableItem', $('#scanDetail'));
			    $('#mm').menu('disableItem', $('#deleteLog'));
			    
			    $('#mm').menu('show',{  
	                    left: e.pageX,  
	                    top: e.pageY  
	                }); 
			    nodeText = node.text;
				userId = node.id;
				currentNode = node;
			    }
            }

});
		
	
});

function deleteLog(){
	$.messager.confirm("系统提示","您确定要删除 "+nodeText+" 的证明日志吗?",function(r){
					if(r){
						$.post('servlet/deleteDigestServlet',{delId:userId,delTime:nodeText},function(result){
							if(result.success){
								 $('#tt01').tree('remove', currentNode.target);  
                				//alert(currentNode.text);
								$.messager.alert("系统提示","已成功删除这条记录!");
								
								
							}else{
								$.messager.alert("系统提示",result.errorMsg);
							}
						},'json');
					}
				});

}

function deleteUser(){

$.messager.confirm("系统提示","您确定要删除用户 "+nodeText+" 吗?",function(r){
					if(r){
						$.post('servlet/deleteUserServlet',{oid:userId},function(result){
							if(result.success){
								 $('#tt01').tree('remove', currentNode.target);  
                				//alert(currentNode.text);
								$.messager.alert("系统提示","已成功删除该用户!");
								
								
							}else{
								$.messager.alert("系统提示",result.errorMsg);
							}
						},'json');
					}
				});
}

function f1(txt,id){
		
				if (txt == '终端信息') {
					$('#mainPanle').panel({
						href : 'servlet/viewUserServlet?oid=' + id,
					});
				} else if (txt == '白名单信息') {
					$('#mainPanle').panel({
						href : 'servlet/userDetailServlet?oid=' + id,
						 //onLoad:function(){
					     //   alert('loaded successfully');
					     //}
					});
				} else if (txt == '报警日志') {
					$('#mainPanle').panel({
						href : 'alertListDetail.jsp?oid=' + id,
						});
				}
			//document.getElementById("main").src= 'servlet/userDetailServlet?oid='+ n.id;
			//$("#mainPanle").children("iframe.1").attr('src','servlet/userDetailServlet?oid='+ n.id).css({"color":"red","border":"2px solid red"});
			//alert($("#mainPanle").children("iframe:src").val());
			//changeUrl('servlet/userDetailServlet?oid='+ n.id);
			//$("#main").src = 'servlet/userDetailServlet?oid='+ n.id;
 }


function f2(nodeTxt,txt,id){
				//alert(nodeTxt);
				 if (txt == '远程证明日志') {
					$('#mainPanle').panel({
						href : 'servlet/historyDetailServlet?oid=' + id + '&sdate='+nodeTxt,
						//onLoad:function(){
					    //    alert('loaded successfully');
					    //}
					});
				}
 }


function scanAllVerifylog(){
	$('#mainPanle').panel({
						href : 'servlet/historyDetailServlet?oid=' + userId + '&sdate=',
						//onLoad:function(){
					    //    alert('loaded successfully');
					    //}
					});

}

function scanAllUser(){
			var addr = 'userTree.jsp?ip=&username='
					+ '&pubkey=&verifydate=';
			var tabs = $('#tt').tabs().tabs('tabs');
			var length = tabs.length;
			var title = tabs[0].panel('options').tab.text();
			if ($("#tt").tabs('enableTab', '按厂区')) {
				$("#tt").tabs('select', '按厂区');
			}
			var tab = tabs[1]; // get selected panel
			$('#tt').tabs('update', {
				tab : tab,
				//method:'get',
				options : {
					href : addr
				// the new content URL
				}
			});
			// call 'refresh' method for tab panel to update its content
			//var tab = $('#tt').tabs('getSelected');  // get selected panel
			//tab.panel('refresh', addr);

}
 
function getUrlParam(name)
{
var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
var r = window.location.search.substr(1).match(reg);  //匹配目标参数
if (r!=null) return unescape(r[2]); return null; //返回参数值
} 

        
		
</script> 

</BODY>
</HTML>
