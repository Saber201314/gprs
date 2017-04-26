/**
 * 右键菜单dom
 */
var rightMenu = "<div id=\"titleClickMenu\" class=\"layui-layer layui-layer-page layui-layer-dir layer-anim\" style=\"color:#009688;z-index: 19891015;display:none;\">"
	+"<div id=\"\" class=\"layui-layer-content\">"
	+"<ul class=\"layui-layer-wrap\" style=\"display: block;\">"
	+"<li id=\"closeAll\"><a href=\"#\"><i style=\"color:#009688;line-height: 26px;\" class=\"fa fa-times-circle-o\"></i><cite>关闭全部</cite></a></li>"
	+"<li id=\"closeOther\"><a href=\"#\"><i style=\"color:#009688;line-height: 26px;\" class=\"fa fa-times-circle\"></i><cite>关闭其他</cite></a></li>"
	+"<li id=\"openNew\"><a href=\"#\"><i style=\"color:#009688;line-height: 26px;\" class=\"fa fa-external-link\"></i><cite>新窗口打开</cite></a></li>"
	+"<li id=\"reflesh\"><a href=\"#\"><i style=\"color:#009688;line-height: 26px;\" class=\"fa fa-refresh\"></i><cite>刷新</cite></a></li>"
	+"</ul>"
	+"</div>"
	+"</div>";

/**
 * 对layui tab的封装
 * mainId  为 layui tab 的id
 */
function Tab(mainId) {
	this.mainId = mainId;
	
	//添加右键菜单
	$(document.body).append(rightMenu);
	
	//屏蔽右键菜单
	document.oncontextmenu = function(){
		doNothing();
	};
	
	//鼠标抬起如果有右击菜单隐藏它
	document.onmouseup = function(event) {
		var menu = $("#titleClickMenu");
		if(menu) {
			menu.hide();
		}
	};
	
	var This = this;
	layui.use('element', function(){
		var element = layui.element();
		element.on('tab('+mainId+')', function(data) {
			
			if(This.tabShowCallBack) {
				if(data.index == 0) {
					This.tabShowCallBack(0);
				}
				else {
					This.tabShowCallBack(This.tabsId[data.index-1]);
				}
			}
			
		});
	});
}

/**
 * 容器id
 */
Tab.prototype.mainId = null;

/**
 * 所有tab的id
 */
Tab.prototype.tabsId = new Array();

/**
 * 当tab被显示时的回调函数，把当前的id回带过去
 * 例如：
 * var tab = new Tab("content_tab");
	tab.tabShowCallBack = function(id) {
		alert(id);
	}
 */
Tab.prototype.tabShowCallBack;

/**
 * 标签是否最大化
 */
var isMaxSize = false;

/**
 * 刷新id
 * @param id
 */
Tab.prototype.reflesh = function(id) {
	if(this.isExist(id)) {
		//注释的是刷tab页 但是tab里面再点击链接 刷tab页不太友好
		//var title = $("#title_"+id);
		//this.add(id,title.attr("icon"),title.attr("title"),title.attr("url"));
		document.getElementById('iframe_'+id).contentWindow.location.reload(true);
	}
};

/**
 * 新开一个窗口打开
 * @param id
 */
Tab.prototype.openNew = function(id) {
	window.open($("#title_"+id).attr("url"));
};

Tab.prototype.isExist = function(id) {
	for(var i=0;i<this.tabsId.length;i++) {
		if(this.tabsId[i] == id) {
			return true;
		}
	}
	return false;
};

Tab.prototype.closeAll = function() {
	var element = layui.element();
	for(var i=0;i<this.tabsId.length;i++) {
		element.tabDelete(this.mainId, this.tabsId[i]);
	}
	this.tabsId = new Array();
};

Tab.prototype.closeOther = function(id) {
	var element = layui.element();
	for(var i=0;i<this.tabsId.length;i++) {
		if(id != this.tabsId[i]) {
			element.tabDelete(this.mainId, this.tabsId[i]);
		}
	}
	this.tabsId = new Array();
	this.tabsId[0] = id;
	
	element.tabChange(this.mainId,id);
};

/**
 * 添加一个标签
 */
Tab.prototype.add = function(id,icon,title,url) {
	var element = layui.element();
	//如果已经存在 切换显示
	if($("#title_"+id)[0]) {
		element.tabChange('content_tab',id);
		$("#iframe_"+id).attr("src",url);
		return ;
	}
	this.tabsId[this.tabsId.length] = id;
	
	element.tabAdd('content_tab', {
		title:'<span id="title_'+id+'" icon="'+icon+'" title="'+title+'" url="'+url+'" ><i class="'+icon+'" style="color:#009688"></i>'+title+"</span>",
		content: '<div class="layui-body layui-tab-content site-demo site-demo-body"><div class="layui-main"><iframe id="iframe_'+id+'" frameborder="0" scrolling="no" onload="setIframeHeight(this)" style="width: 100%;" src="'+url+'"/></div></div>',
		id:id
	});
	element.tabChange(this.mainId,id);
	
	var titleDom = $("#title_"+id);
	
	var This = this;
	var clickDom = titleDom.parent();
	clickDom.dblclick(function() {
		This.reSizeContent();
	});
	
	//如果是右击 显示右击菜单
	clickDom.mouseup(function(event) {
		//右击
		if (event.which == 3) {
			var menu = $("#titleClickMenu");
			menu.attr("tab_id",id);
			
			menu.css("top",event.clientY);
			menu.css("left",event.clientX);
			menu.show();
			event.stopPropagation();
			
			menu.find("#closeAll").unbind("click");
			menu.find("#closeAll").click(function() {
				This.closeAll();
			});
			
			menu.find("#closeOther").unbind("click");
			menu.find("#closeOther").click(function() {
				This.closeOther($("#titleClickMenu").attr("tab_id"));
			});
			
			
			menu.find("#openNew").unbind("click");
			menu.find("#openNew").click(function() {
				This.openNew($("#titleClickMenu").attr("tab_id"));
			});
			
			menu.find("#reflesh").unbind("click");
			menu.find("#reflesh").click(function() {
				This.reflesh($("#titleClickMenu").attr("tab_id"));
			});
			
		}
	});
	
};


Tab.prototype.reSizeContent = function() {
	var demo_title = $(".site-demo-title");
	var demo_body = $(".site-demo-body");
	
	if(!this.isMaxSize) {
		$(".header-demo").hide();
		$(".footer-demo").hide();
		$(".layui-bg-black").hide();
		
		demo_title.css("top","0px");
		demo_title.css("left","0px");
		demo_body.css("top","60px");
		demo_body.css("left","0px");
		demo_body.css("bottom","0px"); 
	}
	else {
		$(".header-demo").show();
		$(".footer-demo").show();
		$(".layui-bg-black").show();
		
		demo_title.css("top","65px");
		demo_title.css("left","200px");
		demo_body.css("top","106px");
		demo_body.css("left","200px");
		demo_body.css("bottom","82px");
	}
	this.isMaxSize = !this.isMaxSize;
};

/**
 * 设置内容的iframe 高度
 */
function setIframeHeight(iframe) {
	if (iframe) {
		var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
		if (iframeWin.document.body) {
			var height = (iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight);
			if(height < 400) {
				height = 400;
			}
			iframe.height = height;
		}
	}
}

/**
 * 屏蔽浏览器右击事件
 */
function doNothing(){  
    window.event.returnValue=false;  
    return false;  
}