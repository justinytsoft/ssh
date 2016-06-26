/**
 * Created by wangjie on 2015/7/6.
 */

$(document).ready(function(){
	/*先判断当前用户是不是集团用户*/
	if($('#isGroup').val()=='false'){
		$('.conpany-info-tab li:eq(4)').last().hide();
		if($('#isDepartment').val()=='false'){
			$('.conpany-info-tab li:eq(3)').last().hide();
		}
		if($('#isDepartment').val()=='false'|| $('#isPublic_account').val()=='true'){
			$('.conpany-info-tab li:eq(2)').last().hide();
		}
		$('#header .activity').hide();
	}else{
		$('#header .activity').show();
	}
    fnEventBind(); //调用事件绑定方法
    /*首次登陆的话 需要先设置向导的位置 --- 以后需要先做处理 看是否是首次登陆*/
    $('#screen').css("width",document.documentElement.clientWidth+'px').css("height",document.documentElement.clientHeight+'px');
    window.onresize = function(){
        $('#screen').css("width",document.documentElement.clientWidth+'px').css("height",document.documentElement.clientHeight+'px');
    };
    /*首次登陆的话 需要先设置向导的位置*/
    /*给header导航栏的消息图标增加事件绑定*/
    $('.message').on('click',function(){
    	/*跳转到消息中心界面*/
    	window.location.href= ctx+'/manage/company/message_list.do';
    });
	$('#header .menmber').on('click',function(){
		if($('.right-popup').css('right')=='0px'){
			$('.right-popup').animate({
				'right':'-400px'
			},600);	
			$('#screen').delay(600).hide().css({
				'z-index' : 1001
			});
			$('#feed-content').val('');
			return;
		}
		$('.right-popup').animate({
			'right':'0px'
		},600);
		$('#screen').show().css({
			'z-index' : 998
		})
	});
	$('#feed-content').on('input propertychange',function(){
		var oThis = $(this);
		if(getByteLen(oThis.val())<20||getByteLen(oThis.val())>200){
			$('#feed-content').css('border','1px solid #ea2201');
			$('.feed-back-error').css('visibility','visible');
		}else{
			$('#feed-content').css('border','1px solid #e5e5e5');
			$('.feed-back-error').css('visibility','hidden');
		}
	});
	$('#feed-btn').on('click',function(){
    	var sContain = $('#feed-content').val(); //反馈内容
		var sEmail = $('#login_name').text(); //反馈邮箱
		if(getByteLen(sContain)<20||getByteLen(sContain)>200){
			$('#feed-content').css('border','1px solid #ea2201');
			$('.feed-back-error').css('visibility','visible');
			return;
		}
		var oParam = {};
		oParam['content'] = sContain;
		oParam['contactWay'] = sEmail;
		$.ajax({
			url:ctx+'/manage/auth/feedback_insert.json',
			type:'POST',
			data:oParam,
			cache : false,
			success:function(res){
				if(res['success']){
					$('.qtip-success').text('谢谢你的参与！').fadeIn('1000').delay(1000).fadeOut('3000');
				}
				else if(res['error']){
					$('.qtip-success').text('填写信息有误，请重新填写！').fadeIn('1000').delay(1000).fadeOut('3000');
				}
			},
			error:function(){
				console.log('错误');
			}
		});
    });
    $.ajax({
		url:ctx+'/manage/company/index.do',
		cache : false,
		success:function(res){
			var oData = res[0];
			var oCompany = oData ['company'];
			/*增加公司logo的赋值 http://www.zhaoluobo.com/picture/company/172_logo_2A128BD8-5582-4C39-9962-596A0FC1DB81.jpg*/
			var aCompanyLogo = oCompany.logo.split('/');
			var sCompanyLogo = aCompanyLogo[aCompanyLogo.length-1];
			if(sCompanyLogo!=''){
				$('#login_company_logo img').attr('src',ctx+'/general/downloads/view_image.do?file_name='+sCompanyLogo);
			}else{
				$('#login_company_logo img').attr('src',ctx+'/images/ui_change/luobo.png');
			}
		},
		error:function(){
			console.log('ajax错误！');
		}
	});
	$('#new_app_close').on('click',function(){
    	$('#new_app_load').css('display','none');
    	$('#screen').hide();
    	saveNewAppStatus();
    });
    $('#to_task').on('click',function(){
    	$('#new_app_load').css('display','none');
    	$('#screen').hide();
		saveNewAppStatus();
    	location.href = ctx+"/manage/intelligent.do";
    });
}).click (function (e){
	e = e || window.event;
	/*控制header部分的下拉菜单是否可以响应点击其他地方关闭菜单*/
    if(e.target == $('#screen')[0]||e.target == $('#header')[0]){
    	$('.right-popup').animate({
			'right':'-400px'
		},600);
		if($('#new_app_load').css('display')=='block'){
			return;
		}
		if($('#goodluck-div').css('display')=='block'){
			return;
		}
		if($('#lottery-result').css('display')=='block'){
			return;
		}
		if($('#auth_msg').css('display')=='block'){
			return;
		}
		if($('#no-lottery-result').css('display')=='block'){
			return;
		}
		if($('#lottery-no-start').css('display')=='block'){
			return;
		}
		if($('#lottery-end').css('display')=='block'){
			return;
		}
		if($('#login_first').css('display')=='block'){
			return;
		}
		if($('.conform').length<1){
			$('#screen').delay(600).hide().css({
				'z-index' : 1001
			});
		}
		$('#feed-content').val('');
    }
});

/* 绑定事件的方法
* create-wangjie
* */
function fnEventBind(){
    //左侧导航栏的收起展开按钮点击事件
    $('#right').on('click','.cancel',fnCancelOnClick);
    $('#right').on('click','.img-cancel',fnCancelOnClickImg);
    $('#right').on('mouseleave','.select_box',function(){
    	$(this).find('.select_option').hide();
    });
    $('.channel-popup').on('mouseleave',function(){
		$('body').on('click',fnClosePopup);
	});
	$('.channel-popup').on('mouseover',function(){
		$('body').unbind('click');
	});
}


/*
 * 表格页码部分html生成
 * oJson-数据的json对象
 * container-放置页码部分html的容器
 * create-wangjie on 2015/07/23
 * */
function fnCreatePage(oJson,container){
	container.empty();
	var iTotalNum = oJson['totalPage']; //总页数
	var iCurrentPage = oJson['pageNo']; //当前页码
	var aHtml=[];
	aHtml.push('<a class="prev">上一页</a>');
	/*for(var i=1; i<=iTotalNum; i++){
		//当前页码添加选中样式
		if(i == iCurrentPage){
			aHtml.push('<a class="currentPage">'+i+'</a>');
		}
		else{
			aHtml.push('<a>'+i+'</a>');
		}
	}*/
	if(iTotalNum<=4){
		for(var i=1; i<=iTotalNum; i++){
			if(i == iCurrentPage){
				aHtml.push('<a class="currentPage">'+i+'</a>');
			}
			else{
				aHtml.push('<a>'+i+'</a>');
			}
		}
	}else{
		if(iCurrentPage<=2){
			for(var i=1; i<=4; i++){
				if(i == iCurrentPage){
					aHtml.push('<a class="currentPage">'+i+'</a>');
				}
				else{
					aHtml.push('<a>'+i+'</a>');
				}
			}
		}else if(iCurrentPage+1>=iTotalNum){
			for(var i=iCurrentPage-3; i<=iCurrentPage; i++){
				if(i == iCurrentPage){
					aHtml.push('<a class="currentPage">'+i+'</a>');
				}
				else{
					aHtml.push('<a>'+i+'</a>');
				}
			}
		}else{
			for(var i=iCurrentPage-2; i<=iCurrentPage+1; i++){
				if(i == iCurrentPage){
					aHtml.push('<a class="currentPage">'+i+'</a>');
				}
				else{
					aHtml.push('<a>'+i+'</a>');
				}
			}
		}

	}
	aHtml.push('<a class="next">下一页</a>');
	aHtml.push('共<font class="totalPage">'+iTotalNum+'</font>页 &nbsp;');
	aHtml.push('跳转到 <input type="text" class="txt_pageNum" onkeyup="value=value.replace(/[^\\d]/g,\'\') "onbeforepaste="clipboardData.setData(\'text\',clipboardData.getData(\'text\').replace(/[^/d]/g,\'\'))" /> 页');
	aHtml.push('<input type="button" class="btn_pageNum" />');
	container.append(aHtml.join(''));
	//当前页为第一页时上一页不可点
	if(iCurrentPage == 1){
		container.children('.prev').addClass('unclick');
	}
	//当前页为最后一页是，下一页不可点
	if(iTotalNum == iCurrentPage){
		container.children('.next').addClass('unclick');
	}
}

function timeStamp2String(time){
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
}


/*
 * 生成提示弹出窗口方法  ---  带有确认和返回按钮
 * aText-文字数组
 * warning-需要强调的文字
 * sClass-按钮Class名称
 * index  ---  传递进来的索引值，根据该值进行操作
 * */
function fnCreateConform(aText,warning,sClass,index,btnValue,name){
	var sIndex = '';
	if(index!=undefined){
		sIndex = index;
	}
	var sName = '';
	if(index!=undefined){
		sName = name;
	}
	var btn = '确 认';
	if(btnValue!=undefined){
		btn = btnValue;
	}

	var aHtml = [];
	aHtml.push('<div class="conform">');
	aHtml.push('<div class="conform-text">');
	aHtml.push(aText[0]);
	if(aText.length > 1){
		aHtml.push('<span class="warning"> ');
		aHtml.push(warning);
		aHtml.push(' </span>');
		aHtml.push(aText[1]);
	}
	aHtml.push('</div>');
	aHtml.push('<input type="button" value="'+btn+'" class="ensure mr20 '+sClass+'" index="'+sIndex+'"  name="'+sName+'" />');
	aHtml.push('<input type="button" value="取 消" class="cancel" />');
	aHtml.push('</div>');
	$(aHtml.join('')).insertBefore('.r-body');	
	$('#screen').show();
	var sTimer = setInterval(function(){
		if($('.conform').length < 1){
			$('#screen').hide();
			clearInterval(sTimer);
		}
	},300);
}
/*
 * 带有图片的弹出确认框
 * img 为图标的路径
 * textObj = {
 * 		aText : [], 提示的文字
 * 		warning : 需要强调的文字
 * 		supply : 需要补充说明的文字
 * 		btnName : 确定按钮名称重命名
 * }
 * */
function fnCreateConformImg(textObj,sClass,img){
	var aHtml = [];
	aHtml.push('<div class="conform-img">');
	aHtml.push('<div style="margin-top:40px;margin-bottom:30px;">');
	aHtml.push('<div class="conform-image"><img src="'+img+'" /></div>');
	aHtml.push(textObj.aText[0]);
	if(textObj.aText.length > 1){
		aHtml.push('<span class="warning"> ');
		aHtml.push(textObj.warning);
		aHtml.push(' </span>');
		aHtml.push(textObj.aText[1]);
	}
	if(textObj.supply.length>0){
		aHtml.push('<div class="warning">'+textObj.supply+'</div>');
	}
	aHtml.push('</div>');
	aHtml.push('<input type="button" value="'+textObj.btnName+'" class="ensure mr20 '+sClass+'" />');
	aHtml.push('<input type="button" value="取 消" class="img-cancel" />');
	aHtml.push('</div>');
	$(aHtml.join('')).insertBefore('.r-body');	
	$('#screen').show();
	var sTimer = setInterval(function(){
		if($('.conform-img').length < 1){
			$('#screen').hide();
			clearInterval(sTimer);
		}
	},300);
}
function fnCancelOnClick(){
	$('.conform').remove();
	$('#screen').hide();
	if($(this).attr('href')){
		location.href=$(this).attr('href')+".do";
	}
}
function fnCancelOnClickImg(){
	$('.conform-img').remove();
	$('#screen').hide();
}
/*
 * 生成提示弹出窗口方法 --- 只包含确认按钮
 * sText-字符串
 * */
function fnCreateAlert(sText,className,btnValue,href){
	var aHtml = [];
	var btn = '确 认';
	if(btnValue!=undefined){
		btn = btnValue;
	}
	var _href = '';
	if(href!=undefined){
		_href = href;
	}
	aHtml.push('<div class="conform">');
	aHtml.push('<div class="conform-text" >');
	aHtml.push(sText);
	aHtml.push('</div>');
	aHtml.push('<input type="button" value="'+btn+'" class="cancel ensure " href="'+_href+'" />');
	aHtml.push('</div>');
	$(aHtml.join('')).insertBefore(className);
	$('#screen').show();
	var sTimer = setInterval(function(){
		if($('.conform').length < 1){
			$('#screen').hide();
			clearInterval(sTimer);
		}
	},300);
}
/*
 * 对要进行高亮词语的数据进行长度排序
 * 越长的词语位置越靠前
 * */
function sortArr(a,b){
	if (a.length>b.length)
        return -1;
    else if(a.length==b.length)
        return 0;
    else
        return 1;
}
/*
 * 数组进行去重复
 * 后续进行优化
 * */
function fnArrUnique(old){
	var res = [];
	var json = {};
	for(var i = 0; i < old.length; i++){
		if(!json[old[i]]){
			res.push(old[i]);
			json[old[i]] = 1;
		}
	}
	return res;
}
//验证是否为数字
function fnIsNumber(value){
	var iNum = parseInt(value);
	if(iNum > 0 && iNum < 100){  
        return true;  
    }
	else{
		return false;
	}
}

//验证是否为整数
function fnIsInteger(value){
	var ex = /^\d+$/;
	if(ex.test(value)) {
	   return true;
	}
	else{
		return false;
	}
}
//校验网站格式
function isURL(str_url) {// 验证url
	var strRegex="^((https|http|ftp|rtsp|mms)?://)"
		+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
		+ "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
		+ "|" // 允许IP和DOMAIN（域名）
		+ "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
		+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
		+ "[a-z]{2,6})" // first level domain- .com or .museum
		+ "(:[0-9]{1,4})?" // 端口- :80
		+ "((/?)|" // a slash isn't required if there is no file name
		+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
	var re=new RegExp(strRegex);
	return re.test(str_url);
}
//验证邮箱格式
function fnIsEmail(value){
	var reg = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d)|(([a-z]|\d)([a-z]|\d|-|\.|_|~)*([a-z]|\d)))\.)+(([a-z])|(([a-z])([a-z]|\d|-|\.|_|~)*([a-z])))$/i;
	//var reg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
	if (reg.test(value)) {
		return true;
	}
	else{
		return false;
	}
}

//验证手机号
function fnTestPhone(value){
	var telReg = value.match(/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/);
	//如果手机号码不能通过验证
	if(!telReg){
		return false;
	}
	else{
		return true;
	}
}

//学历对象
var _oEducationLevel = {
		'1':'不限',
		'2':'初中',
		'3':'高中',
		'4':'中专',
		'5':'大专',
		'6':'本科',
		'7':'硕士',
		'8':'博士'
};

//渠道对象
var _oChannelStatus = {
	'0':'-',
	'1':'正在处理',
	'2':'成功',
	'3':'成功<em class="table-ask">!</em>',
	'4':'失败'
};
/*
 * 记录当前是哪一个菜单被选中
 * id--0-4,用于标记当前需要选中那个左侧菜单
 * */
function fnMenuSelect(id){
	var left = $('#left .mb0 li');
	$.each(left,function(k,v){
		if(id==k){
			$(v).addClass('left_menu_selected');
			if(k!=5&&k!=6){
				$(v).children().first().children('span').css({
					"background":"url(/images/ui_change/left_menu_seletcted.png) no-repeat left center",
					"background-position": "0 -"+k*60+"px"
				});
			}else if(k==6){
				$(v).children().first().children('span').css({
					"background":"url(/images/ui_change/left_menu_seletcted.png) no-repeat left center",
					"background-position": "0 -"+5*60+"px"
				});
			}else{
				$(v).children().first().children('span').css({
					"background":"url(/images/ui_change/left_menu_seletcted_2.png) no-repeat left center",
					"background-position": "0 -367px"
				});
			}

		}else{
			$(v).removeClass('left_menu_selected');
		}
	});
	/**/
	$('#left_menu_logout a').attr("href",$('#left_menu_logout a').attr("href")+'#id='+id);
}

/*
 * 判断浏览器是否兼容placeholder属性
 * 
 */
function fnSetPlace(){
	//判断浏览器是否支持placeholder属性
	var supportPlaceholder = 'placeholder'in document.createElement('input'), 
	placeholder = function(input){
		var text = input.attr('placeholder');
	    defaultValue = input.defaultValue;
	    if(!defaultValue){
	      	if(input.attr("type")=='password'){
	      		$('.ph_span').show();
	    	}else{
	    		input.val(text).addClass("phcolor");
	    	}
	    }
	    input.focus(function(){
	    	if(input.attr("type")=='password'){
	    		$('.ph_span').hide();
	    	}else{
	    		if(input.val() == text){
		    		$(this).val("");
		        }
	    	}
	    });
	    input.blur(function(){
	    	if(input.val() == ""){
	    		if(input.attr("type")=='password'){
		    		$('.ph_span').show();
		    	}else{
		    		$(this).val(text).addClass("phcolor");
		    	}
	    	}
	    });
	    //输入的字符不为灰色
	    input.keydown(function(){
	    	if(input.attr("type")=='password'){
	    		$('.ph_span').hide();
	    	}else{
	    		$(this).removeClass("phcolor");
	    	}
	    });
	};
	//当浏览器不支持placeholder属性时，调用placeholder函数
	if(!supportPlaceholder){
	    $('input').each(function(){
	    	text = $(this).attr("placeholder");
	    	if($(this).attr("type") == "text"){
	    		placeholder($(this));
	    	}else if($(this).attr("type") == "password"){
	    		/*如果是密码输入框，则在其上添加一个span*/
	    		placeholder($(this));
	    		$('.ph_span').click(function(){
		    		$('.ph_span').hide();
		    	});
	    	}
	    });
	}
}
//返回val的字节长度 
function getByteLen(val) {
	var sVal = $.trim(val);
	var len = 0;
	for (var i = 0; i < sVal.length; i++) {
		if (sVal[i].match(/[^\x00-\xff]/ig) != null) //全角
			len += 2;
		else
			len += 1;
	}
	return len;
}
/*
 * 增加邮箱验证功能
 * */
function fnValidEmail(sEmail,error_id,input_id){
	var email_reg = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d)|(([a-z]|\d)([a-z]|\d|-|\.|_|~)*([a-z]|\d)))\.)+(([a-z])|(([a-z])([a-z]|\d|-|\.|_|~)*([a-z])))$/i;
	//var email_reg = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i;
    if(!email_reg.test(sEmail)&&sEmail.length>0){
    	/*输入了地址但是格式不正确*/
        $(error_id).css('display','block').html('<span class="error_symbol">!</span>请输入正确格式的邮箱地址');
        $(input_id).css("border","1px solid red");
        return false;
    }else if(sEmail.length>0){
    	/*格式正确*/
    	$(error_id).css('display','none');
    	$(input_id).css("border","1px solid #b0a296");
    	return true;
    }else{
    	/*没有输入任何值*/
    	$(error_id).css('display','block').html('<span class="error_symbol">!</span>请输入正确格式的邮箱地址');
        $(input_id).css("border","1px solid red");
        return false;
        
    }
}

var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?87e556718d990fb47e263a6e30143d0c";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();

Date.prototype.format =function(format)
{
    var o = {
    "M+" : this.getMonth()+1, //month
	"d+" : this.getDate(),    //day
	"h+" : this.getHours(),   //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3),  //quarter
	"S" : this.getMilliseconds() //millisecond
    };
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
    (this.getFullYear()+"").substr(4- RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,
    RegExp.$1.length==1? o[k] :
    ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
};

/*写cookie，保存简历是否读过的状态*/
function saveReadStatus(resumeId) {
	// 获取已查看的简历ID
	var ids = $.cookie("resume_read_status");
	if(ids==undefined){
		$.cookie("resume_read_status", resumeId, {expires:30});
		return;
	}
	var idsArr = ids.split(','); //得到一个id数组 
	var isExit = '';//加入一个状态，判断新的ID是否已经 存在
	$.each(idsArr,function(index,item){
		if(item==resumeId){
			isExit = true;
		}
	});
	if(!isExit){
		idsArr.push(resumeId);
	}
	$.cookie("resume_read_status", idsArr.join(), {expires:30});
}
/*写cookie，保存用户是否看到过新增功能提示*/
function saveNewAppStatus() {
	// 获取已查看的简历ID
	var ids = $.cookie("new_app_status");
	if(ids==undefined){
		$.cookie("new_app_status", 'new_app_load', {expires:30});
		return;
	}
	var idsArr = ids.split(','); //得到一个id数组 
	var isExit = '';//加入一个状态，判断新的ID是否已经 存在
	$.each(idsArr,function(index,item){
		if(item=='new_app_load'){
			isExit = true;
		}
	});
	if(!isExit){
		idsArr.push('new_app_load');
	}
	$.cookie("new_app_status", idsArr.join(), {expires:30});
}
/*写弹出层的状态，如果当前鼠标的位置在弹出层外，则值为true，否则为false
* divId -- 	需要进行判断的区域的ID，也可以是class
* inputId -- 隐藏表单的id  用于保存是否在弹出层外
*/
function fnValidIsOut(divId){
	$(divId).hover(function(){
        $(divId).attr('isout','false');
    }, function(){
    	if($(divId).hasClass('noHover')){
    		$(divId).attr('isout','').removeClass('noHover');
			return;
		}
        $(divId).attr('isout','true');
    });
}
function CenterImgPlay() {
    this.list = $(".imgbox").children(":first").children();
    this.indexs = [];
    this.length = this.list.length;
    //图片显示时间
    this.timer = 5000;
    this.showTitle = $(".title");

    var index = 0, self = this, pre = 0, handid, isPlay = false, isPagerClick = false;

    this.Start = function () {
        this.Init();
        //计时器，用于定时轮播图片
        handid = setInterval(self.Play, this.timer);
        //this.Pause();
    };
    //初始化
    this.Init = function () {
        var o = $(".pager ul li"), _i;
        for (var i = o.length - 1, n = 0; i >= 0; i--, n++) {
            this.indexs[n] = o.eq(i).click(self.PagerClick);
        }
    };
    this.Pause = function(){
    	/*鼠标悬浮在区域范围内的时候，暂停图片轮播时间*/
    	$(".imgbox").hover(
		    function(){
		        clearInterval(handid);
		    },function(){
		        self.Start();
		    }
	    );
    };
    this.Play = function () {
        isPlay = true;
        index++;
        if (index == self.length) {
            index = 0;
        }
        //先淡出，在回调函数中执行下一张淡入
        self.list.eq(pre).fadeOut(300, "linear", function () {
            var info = self.list.eq(index).fadeIn(500, "linear", function () {
                isPlay = false;
                if (isPagerClick) { handid = setInterval(self.Play, self.timer); isPagerClick = false; }
            }).attr("title");
            //显示标题
            self.showTitle.text(info);
            //图片序号背景更换
            self.indexs[index].css({
            	"background-color":"#B2884f",
            	"color":"#B2884f"
            });
            self.indexs[pre].css({
            	"background-color": "#e9e9e9",
            	"color": "#e9e9e9"
            });
            pre = index;
        });
    };
    //图片序号点击
    this.PagerClick = function () {
        if (isPlay) { return; }
        isPagerClick = true;
        clearInterval(handid);
        var oPager = $(this), i = parseInt(oPager.text()) - 1;
        if (i != pre) {
            index = i - 1;
            self.Play();
        }
    };
}
/**
 * [dostr description]
 * @param  {[string]} str [出现的多余的逗号的字符串]
 * @return {[string]}     [返回去除多余逗号的字符串]
 */
function dostr(str){
	str=$.trim(str);
	/*var strarry=unique(str.split(","));
	str=strarry.join(",");*/
	str=str.replace(/<null>/ig,'').replace(/，/ig,",");
	str=str.replace(new RegExp(',+',"gm"),',');
	if (str.substr(0,1)==',') str=str.substr(1);
	var reg=/,$/gi;
	str=str.replace(reg,"");
	return str;
}
/**
 * [fnTableLoading description]
 * @return {[type]} [description]
 */
function fnTableLoading(){
	$('.table_loading_screen').css({
		"height" : "100%",
		"width" : $('.contain-tab').width(),
		"opacity": "0.1",
		"position": "absolute"
	}).show();
	$('.table_loading').show();
}
function fnHideTableLoading(){
	if($('.table_loading').length >0){
		$('.table_loading').delay(100).hide(0,function(){
			$('.table_loading_screen').hide(0);
		});
	}else{
		$('.table_loading_screen').hide(0);
	}
}