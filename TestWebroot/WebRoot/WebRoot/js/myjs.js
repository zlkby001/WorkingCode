
function obj2str(o) {
	var r = [];
	if (typeof o == "string" || o == null) {
		return o;
	}
	if (typeof o == "object") {
		if (!o.sort) {
			r[0] = "{"
			for ( var i in o) {
				r[r.length] = i;
				r[r.length] = ":";
				r[r.length] = obj2str(o[i]);
				r[r.length] = ",";
			}
			r[r.length - 1] = "}"
		} else {
			r[0] = "["
			for ( var i = 0; i < o.length; i++) {
				r[r.length] = obj2str(o[i]);
				r[r.length] = ",";
			}
			r[r.length - 1] = "]"
		}
		return r.join("");
	}
	return o.toString();
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
U盘白名单

function createWin(w, h, x, y,tl,url){
	var win = new Ext.Window({
        title: tl,		//窗体标题
        layout: 'fit',    //设置布局模式为fit，能让frame自适应窗体大小
        modal: true,    //打开遮罩层
        height: 300,    //初始高度
        width: 300,  //初始宽度
        border: 0,    //无边框
        frame: false,    //去除窗体的panel框架
        html: '<iframe frameborder=0 width="100%" height="100%" allowtransparency="true" scrolling=auto src=' + url + '></iframe>'
    });
    win.setSize(w, h);    //w为设置的宽度，h为设置的高度
    win.setPosition(x, y);    //x为设置的x坐标，y为设置的y坐标
    win.show();    //显示窗口
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }