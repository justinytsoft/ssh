//| 字符串转成日期类型   
function stringToDate(DateStr)  
{   
  var converted = Date.parse(DateStr);  
  var myDate = new Date(converted);  
  if (isNaN(myDate))  
  {   
	  //| 格式 YYYY-MM-dd HH:mm:ss
      var arys = DateStr.split('-');  //年、月、日、小时、分钟、秒
      var day = arys[2].split(" "); //日、小时、分钟、秒
      if(day.length==2){
    	  var hms = day[1].split(":"); //小时、分钟、秒
    	  if(hms.length==3){
    		  myDate = new Date(arys[0],--arys[1],day[0],hms[0],hms[1],hms[2]);  
    	  }else{
    		  myDate = new Date(arys[0],--arys[1],day[0],hms[0],hms[1]);  
    	  }
      }else{
    	  myDate = new Date(arys[0],--arys[1],arys[2]);  
      }
  }  
  return myDate;  
}  

//日期格式化 new Date().format("yyyy年MM月dd日 hh:mm");
Date.prototype.format = function(format){ 
	var o = { 
	"M+" : this.getMonth()+1, //month 
	"d+" : this.getDate(), //day 
	"h+" : this.getHours(), //hour 
	"m+" : this.getMinutes(), //minute 
	"s+" : this.getSeconds(), //second 
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
	"S" : this.getMilliseconds() //millisecond 
	} 

	if(/(y+)/.test(format)) { 
	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 

	for(var k in o) { 
	if(new RegExp("("+ k +")").test(format)) { 
	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
	} 
	} 
	return format; 
}

function menuLinkTo(path) {
	parent.document.getElementById('benchBody').src=path;
}

function exit(obj) {
	parent.window.location.href= obj;
}

function textOnfocus(obj) {
	if (obj.value == '输入文本...') {
		obj.value = "";
	}
}

function textOnblur(obj) {
	if ($.trim(obj.value) == '') {
		obj.value = "输入文本...";
	}
}

function getCodeNum(str) {
	var reg = /[1-9][0-9]*/g;
	return reg.exec(str);
}

function getCode(str) {
	var reg = /[a-zA-Z]+/g;
	return reg.exec(str)[0];
}

function isEmail(str) {
	emailReg = /^[\w\.\-]+@([\w\-]+\.)+[a-z]{2,4}$/;
	return emailReg.test(str);
}

//验证电话号码
function isPhoneNumber(str) {
	patrn = /^[\d\+\-\s]+$/;
    if(!patrn.exec(str)) {  
        return false;  
    }  
    return true;  
}

//验证手机号码
function isCellPhoneNumber(str) {
	patrn = /^[0-9]{11}$/;  
    if(!patrn.exec(str)) {  
        return false;  
    }  
    return true;
}

function isNumber(str) {
	numberReg = /^\d+$/;
	return numberReg.test(str);
}

function isPirce(str){
    var p =/^[1-9](\d+(\.\d{1,2})?)?$/;
    var p1=/^[0-9](\.\d{1,2})?$/;
    return p.test(str) || p1.test(str);
}

function isUsername(str) {
	var usernameReg = /^\w+$/;
	return usernameReg.test(str);
}


var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子   
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X   
function IdCardValidate(idCard) { 
    idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格                     
    if (idCard.length == 15) {   
        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证    
    } else if (idCard.length == 18) {   
        var a_idCard = idCard.split("");                // 得到身份证数组   
        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
            return true;   
        }else {   
            return false;   
        }   
    } else {   
        return false;   
    }   
}   
/**  
 * 判断身份证号码为18位时最后的验证位是否正确  
 * @param a_idCard 身份证号码数组  
 * @return  
 */  
function isTrueValidateCodeBy18IdCard(a_idCard) {   
    var sum = 0;                             // 声明加权求和变量   
    if (a_idCard[17].toLowerCase() == 'x') {   
        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作   
    }   
    for ( var i = 0; i < 17; i++) {   
        sum += Wi[i] * a_idCard[i];            // 加权求和   
    }   
    valCodePosition = sum % 11;                // 得到验证码所位置   
    if (a_idCard[17] == ValideCode[valCodePosition]) {   
        return true;   
    } else {   
        return false;   
    }   
}   
/**  
  * 验证18位数身份证号码中的生日是否是有效生日  
  * @param idCard 18位书身份证字符串  
  * @return  
  */  
function isValidityBrithBy18IdCard(idCard18){   
    var year =  idCard18.substring(6,10);   
    var month = idCard18.substring(10,12);   
    var day = idCard18.substring(12,14);   
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
    // 这里用getFullYear()获取年份，避免千年虫问题   
    if(temp_date.getFullYear()!=parseFloat(year)   
          ||temp_date.getMonth()!=parseFloat(month)-1   
          ||temp_date.getDate()!=parseFloat(day)){   
            return false;   
    }else{   
        return true;   
    }   
}   
  /**  
   * 验证15位数身份证号码中的生日是否是有效生日  
   * @param idCard15 15位书身份证字符串  
   * @return  
   */  
  function isValidityBrithBy15IdCard(idCard15){   
      var year =  idCard15.substring(6,8);   
      var month = idCard15.substring(8,10);   
      var day = idCard15.substring(10,12);   
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
      if(temp_date.getYear()!=parseFloat(year)   
              ||temp_date.getMonth()!=parseFloat(month)-1   
              ||temp_date.getDate()!=parseFloat(day)){   
                return false;   
        }else{   
            return true;   
        }   
  }   
//去掉字符串头尾空格   
function trim(str) {   
    return str.replace(/(^\s*)|(\s*$)/g, "");   
}

function checkImgFile(obj) {
	var filePath = $(obj).val();
	var pix = filePath.split("\.");
	var extension = pix[pix.length-1];
	//<![CDATA[
	if (extension !='jpg' && extension != 'jpeg' && extension != 'png') {
	//]]>
		$(obj).val("");
		alert("仅支持jpg格式图片！");
	}
}

//分页跳转
function paginationLink(actionUrl) {
	$("#searchForm").attr("action", actionUrl);
	$("#searchForm").submit();
}