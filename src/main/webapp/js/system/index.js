//加载当前日期
function loadDate(){
	var time = new Date();
	var myYear = time.getFullYear();
	var myMonth = time.getMonth() + 1;
	var myDay = time.getDate();
	if (myMonth < 10) {
		myMonth = "0" + myMonth;
	}
	document.getElementById("day_day").innerHTML = myYear + "." + myMonth + "."	+ myDay;
}

/**
 * 隐藏或者显示侧边栏
 * 
 **/
function switchSysBar(flag){
	var side = $('#side');
    var left_menu_cnt = $('#left_menu_cnt');
	if( flag==true ){	// flag==true
		left_menu_cnt.show(500, 'linear');
		side.css({width:'280px'});
		$('#top_nav').css({width:'77%', left:'304px'});
    	$('#main').css({left:'280px'});
	}else{
        if ( left_menu_cnt.is(":visible") ) {
			left_menu_cnt.hide(10, 'linear');
			side.css({width:'60px'});
        	$('#top_nav').css({width:'100%', left:'60px', 'padding-left':'28px'});
        	$('#main').css({left:'60px'});
        	$("#show_hide_btn").find('img').attr('src', 'images/common/nav_show.png');
        } else {
			left_menu_cnt.show(500, 'linear');
			side.css({width:'280px'});
			$('#top_nav').css({width:'77%', left:'304px', 'padding-left':'0px'});
        	$('#main').css({left:'280px'});
        	$("#show_hide_btn").find('img').attr('src', 'images/common/nav_hide.png');
        }
	}
}

$(function(){
	loadDate();
	
	// 显示隐藏侧边栏
	$("#show_hide_btn").click(function() {
        switchSysBar();
    });
});

//切换菜单图标
$(function () {
	//为对应的li元素绑定点击事件
	$("#TabPage2 li").click(function () {
		//迭代所有的li元素,清除样式与图标
		$.each($("#TabPage2 li"), function (index,item) {
			//删除原有的样式
			$(item).removeClass("selected");
			//还原图标
			$(item).children("img").prop("src","/images/common/"+(index+1)+".jpg");
		});
		//为当前点击的li添加样式
		$(this).addClass("selected");
		//修改img图标
		var index = ($(this).index()+1);
		$(this).children("img").prop("src","/images/common/"+index+"_hover.jpg");

		//修改标题图片
		$("#nav_module").children("img").prop("src","/images/common/module_"+index+".png");

		//加载对应模块的菜单树
		var sn = $(this).data("rootmenu");
		loadMenu(sn);
	});
})


//菜单树
var setting = {//配置对象
	data:{//数据相关配置
		simpleData:{ //简单数据模式
			enable:true //启用简单数据模式,默认为false
		}
	},
	callback: {
		//点击
		onClick: function (event, treeId, treeNode) {
			if (treeNode.action){
				//修改iframe的src属性值
				$("#rightMain").prop("src",treeNode.action+".do");
				//显示导航条
				$("#here_area").html("当前位置>"+treeNode.getParentNode().name+"&nbsp;>&nbsp;"+treeNode.name);
			}
		}
	},
	async:{//异步加载
		enable:true,
		url:"/systemMenu/loadMenusByParentSn.do",
		autoParam:["sn=parentSn"]
	}
}

// 数据
var zNodes= {
	systemManage:{id:1,pId:0,name:"系统管理模块",isParent:true,sn:"system"},
	business:{id:2,pId:0,name:"业务模块",isParent:true,sn:"business"},
	charts:{id:3,pId:0,name:"报表模块",isParent:true,sn:"chart"},
}

$(function () {
	//页面加载完成后默认加载的树
	loadMenu("systemManage");
})

//加载菜单树
function loadMenu(sn){
	$.fn.zTree.init($("#dleft_tab1"),setting,zNodes[sn]);
}