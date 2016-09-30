function creatTopMenu() {
	Ext.BLANK_IMAGE_URL ="img/s.gif";

	var menu1 = new Ext.menu.Menu({
		id : 'topMenuPerson',
		style : {
			overflow : 'visible' // For the Combo popup
		},
		items : [ {
			id : 'scanPersonalInfo',
			text : '查看个人信息'
		}, '-', {
			id : 'editPersonalInfo',
			text : '修改个人信息'
		}, {
			id : 'editCertificateInfo',
			text : '修改证件信息'
		}, {
			id : 'editPassword',
			text : '修改密码'
		} ],
		listeners : {
			itemclick : function(item) {
				switch (item.id) {
				case 'scanPersonalInfo':
					loadmain('servlet/personalInfoServlet');
					break;
				case 'editPersonalInfo':
					loadmain('servlet/viewAndModifyPIServlet');
					break;
				case 'editCertificateInfo':
					loadmain('servlet/viewAndModifyCertServlet');
					break;
				case 'editPassword':
					loadmain('modifyPWD.jsp');
					break;
				}

				// item.parentMenu.hide();
			}
		}
	});
	
	var menu2 = new Ext.menu.Menu({
		id : 'topMenuAdmin',
		style : {
			overflow : 'visible' // For the Combo popup
		},
		items : [ {
			id : 'adminList',
			text : '用户管理列表'
		}, '-', {
			id : 'unpassUser',
			text : '审核新账户'
		}],
		listeners : {
			itemclick : function(item) {
				switch (item.id) {
				case 'adminList':
					loadmain('servlet/listAdminsServlet');
					break;
				case 'unpassUser':
					loadmain('servlet/listUnPassAdminsServlet');
					break;
				}

				// item.parentMenu.hide();
			}
		}
	});
	
	var menu3 = new Ext.menu.Menu({
		id : 'topMenuData',
		style : {
			overflow : 'visible' // For the Combo popup
		},
		items : [ {
			id : 'userInfo',
			text : '用户信息管理'
		}, '-',{
			id : 'knowledgeInfo',
			text : '知识库管理'
		},],
		listeners : {
			itemclick : function(item) {
				switch (item.id) {
				case 'userInfo':
					loadmain('UserList.jsp');
					break;
				case 'knowledgeInfo':
					loadmain('showKnowledge.jsp');
					break;
			
				}

				// item.parentMenu.hide();
			}
		}
	});

	var tb = new Ext.Toolbar();
	tb.render('divToolbar');

	tb.add('->',{
		text : '个人信息管理',
		iconCls : 'detail', // <-- icon
		menu : menu1
	// assign menu by instance
	}, '-', 
	{
		text : '管理员管理',
		iconCls : 'detail', // <-- icon
		menu : menu2
	// assign menu by instance
	}, '-',
	{
		text : '完整性元数据管理',
		iconCls : 'detail', // <-- icon
		menu : menu3
	// assign menu by instance
	}, '-',
	{
		text : '退出系统',
		iconCls : 'exit',
		listeners : {
			click:function(){
				exitSystem();
			}
		}
	}
	);

	tb.doLayout();

	// functions to display feedback
	function onButtonClick(btn) {
		Ext.example.msg('Button Click', 'You clicked the "{0}" button.',
				btn.text);
	}

	function onItemClick(item) {
		Ext.example.msg('Menu Click', 'You clicked the "{0}" menu item.',
				item.text);
	}
	
	function exitSystem(){
		if (navigator.userAgent.indexOf('MSIE')>0) {	//判断是否IE浏览器
			location.href ="../logout.jsp";
		} 
		else{
		      location.href ="logout.jsp";
		}
	}


}