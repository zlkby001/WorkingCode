<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String verifyres=request.getParameter("verifyres");
	String authalert="0";
	if(verifyres.length()==8)
	{
		verifyres=verifyres.substring(0,8);
		authalert="1";
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<TITLE>工业控制系统安全管理平台</TITLE>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<META content="MSHTML 6.00.6000.16788" name=GENERATOR>

<script type="text/javascript" src="extjs3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs3/ext-all-debug.js"></script>
<script type="text/javascript" src="extjs3/ext-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css"
	href="extjs3/resources/css/ext-all.css" />
	
<link rel="stylesheet" type="text/css" href="css/icon.css" />
<script type="text/javascript" src="js/top.js"></script>
<style>
.footer {
	width: 100%;
	text-align: center;
	font-size:12px;
}

.top-bg {
	background-color: #d8e4fe;
	height: 80px;
}

#mainPanel{
	width:100%;
	height:100%;
}

#userTreePanel{
	width:100%;
	height:100%;
}

</style>

	
    <script type="text/javascript">
    Ext.BLANK_IMAGE_URL ="img/s.gif";
    
    function loadmain(url){
	    //Ext.get("mainPanel").load({
	    //    url: url,
	    //    scripts: true,
	    //    //params: "param1=foo&param2=bar",
	    //    text: "Loading ..."
	    //});
	   var html='<iframe name="main" style="width:100%;height:100%" border=0 frameSpacing=0  frameBorder=NO src="' + url + '"/>';
   		Ext.get('mainPanel').update(html);
    }
    
     function setTreePanel(url){
     	//alert('加载 '+url);
     	var html='<iframe style="width:100%;height:100%" border=0 frameSpacing=0  frameBorder=NO src="' + url + '"/>';
   		Ext.get('userTreePanel').update(html);
   }
   
   function refresh() {
		var mygrid = alertFarme.window.gridAlert;
		mygrid.getStore().reload();
		alert('refresh');
		setTimeout(function(){refresh()}, 3000);
	}
	 function checkauditfrequently(){
   		var result = ajaxSyncCall('servlet/checkauditServlet',null);
   		
		if(result==-1)
 			alert("审计日志数量已达上限,请及时清理");
		//if(result>0)
			//alert("目前版本为试用版，试用期还剩"+result+"天");
   		   
   		setTimeout(function(){checkauthorizefrequently()}, 60000);   //1 min
   }
	//退出系统
   
   function checkauthorizefrequently() {
   		//alert("hello");
   		var result = ajaxSyncCall('servlet/checkauthorizeServlet',null);
   		if(result==-3)
			alert("试用期已过，请更新授权");
		if(result==-1)
 			alert("授权验证失败，请检查授权文件是否合法");
		if(result>0)
			alert("目前版本为试用版，试用期还剩"+result+"天");
   		   
   		setTimeout(function(){checkauthorizefrequently()}, 60000*60);   //1 hour
   }
   
   
   
    function myRender(p) {
		//Ext.Msg.alert("提示", p.title + "渲染成功");
	}
	
	
    Ext.onReady(function(){
    	//var authalert=<%=authalert%>;
    	//if(authalert=='1')
    		//alert("试用期版本，请联系厂商进行授权！");
    
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
        var viewport = new Ext.Viewport({
            layout: 'border',
            items: [
            // create instance immediately
           new Ext.BoxComponent({
                region: 'north',
                height: 100, // give north and south regions a height
                autoEl: 'divTop'
            }), {
                // lazily created panel (xtype:'panel' is default)
                region: 'south',
                contentEl: 'south',
                split: true,
                height: 40,
                minSize: 100,
                maxSize: 200,
                collapsible: true,
                title: 'South',
                header:false,
                margins: '0 0 0 0'
            }, 
         
            //======================  West(Left)  ======================
            
            {
                region: 'west',
                title: '拓扑结构',
                collapsible: true,
                split: true,
                width: 225, // give east and west regions a width
                minSize: 175,
                maxSize: 400,
                margins: '0 5 0 0',
                layout: 'fit', // specify layout manager for items
                items:            // this TabPanel is wrapped by another Panel so the title will be applied
                new Ext.TabPanel({
                	id:'lefttab',
                    border: false, // already wrapped so don't add another border
                    activeTab: 1, // second tab initially active
                    tabPosition: 'bottom',
                    items: [{
                    	id: 'conditionSearchPanel',
                        html: '<p>A TabPanel component can be a region.</p>',
                        title: '按条件',
                        //autoLoad : 'searchlogs.jsp',
                        autoLoad:{url:'searchlogs.jsp',scripts:true}, 
                        autoScroll: true
                    }, {
                    	id: 'userTreePanel',
                        title : '按厂区',
						autoLoad : 'treeFrame.jsp',
						closable : false,
						listeners : {
							render : myRender
						}
                    }]
                })
            },
            
            //======================  East(Right)  ======================
            
             {
                region: 'east',
                id: 'west-panel', // see Ext.getCmp() below
                title: '报警日志',
                split: true,
                width: 225,
                frame: true,
                html: '<iframe name="alertFarme" border=0 frameSpacing=0  frameBorder=NO style="width:100%;height:100%"  src="right.jsp"></iframe>',
                //autoLoad:'AlertLog.jsp',
                //minSize: 175,
                //maxSize: 400,
                collapsible: true,
                margins: '0 0 0 0',
                layout: {
                    type: 'accordion',
                    animate: true
                }
                
            },
            
            // =====================  Center  ======================
            
            {
                 region:'center',
                 margins:'0 0 0 0',
                 cls:'empty',
                 bodyStyle:'background:#ffffff',
                 //html:'<br/><br/>&lt;empty center panel&gt;'
                 //autoLoad:'t3.html'
                 layout:'fit',
                 items: [{
                         id:'mainPanel',
                         height:800,
		                 region:'center',
		                 margins:'0 0 0 0',
		                 cls:'empty'
                    }]
            }]
        });
        
        //========================================================================================================
        
       creatTopMenu();
       checkauditfrequently();
       checkauthorizefrequently();
       loadmain('UserList.jsp');
       
    });
    function checkLeave(){
     event.returnValue="请确认数据已保存，再离开此页面！";
   }
    
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
<body onbeforeunload="checkLeave()">

    <!-- use class="x-hide-display" to prevent a brief flicker of the content -->
    <div id="_01" class="x-hide-display">
    	<br><br>
        <p style="color:red;font-size:30px;">您的浏览器版本过低，请升级浏览器或者使用谷歌、火狐浏览器打开本页。</p>
    </div>
    
    <div id="divTop" style="background:#D6E3F2;width:100%;height:100%">
<input name="submitimage" type="image" src="img/logo06.png" 
	align="middle"  width="378px" height="68px" alt="Logo" />
	<!-- <div id="divToolbar" style="width:430px;height:40px;position:absolute;top:72;right:0"></div> -->
	<div id="auth" style="text-align:right;position:absolute;top:38;right:0">auth</div>
	<script type="text/javascript">
    var ver = <%=verifyres%>;
	if(ver == 0){
	   document.getElementById("auth").innerHTML="已注册授权";
	} 
	else{
	document.getElementById("auth").innerHTML="未授权，试用期到 "+<%=verifyres%>;
	document.getElementById('auth').style.color = "#FF0000";
	}
	</script>
		<div id="divToolbar" style="width:430px;height:40px;position:absolute;top:72;right:0">
		
	</div>

    <div id="south" class="x-hide-display footer">
<!-- <img src="img/TcaLogo.jpg"  width="40" height="20" /> -->
		Copyright&nbsp;©&nbsp;2015,&nbsp;
			<a href="http://tca.iscas.ac.cn/">中科院软件所可信计算与信息保障实验室</a>
			<a href="http://www.tofinosecurity.com.cn/">青岛多芬诺信息安全技术有限公司</a>
    </div>
</body>
</html>