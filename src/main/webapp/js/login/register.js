/*
 * 注册 js
 */
function isUserExist() {
	var isValid=$('#userEmail').validatebox('isValid');
	var userEmail = $("#userEmail").val();
	if(isValid) {
		var data = {userEmail:userEmail};
		doAjax(POST,contextPath + "/register/isUserExist",data,"existcallback",false);
	}
}

// 判断邮箱是否存在
var isExistFlag = false;
function existcallback(response)
{
	if(response.errType == INFO) {
		$("#userEmail_ErrorMsg").css("color","green");
		$("#userEmail_ErrorMsg").html("邮箱号填写正确");
		isExistFlag = true;
	}else if(response.errType == ERROR ){
		$("#userEmail_ErrorMsg").css("color","red");
		$("#userEmail_ErrorMsg").html("该邮箱号已注册，请点击右侧登录按钮");
		isExistFlag = false;
	}
}

var InterValObj; // timer变量，控制时间
var count = 600; // 间隔函数，1秒执行，验证码有效期为10分钟
var curCount;// 当前剩余秒数

//发送邮件
function sendEmail() {
	//邮箱是否已经通过验证
    var isValid=$('#userEmail').validatebox('isValid');
    var userEmail = $("#userEmail").val();
    if(isValid && isExistFlag) {
		 curCount = count;
		 var min = Math.floor(curCount/60);  //向下取整
		 var sec = curCount%60;  //取余
		// 设置button效果，开始计时
	     $("#sendEmailBtn").attr("disabled", "true");
	     $("#sendEmailBtn").css("background","gray");
	     $("#sendEmailBtn").val(min + "分" + sec + "秒");
	     InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次
	     // 向后台发送处理数据
	     var data = {userEmail:userEmail};
	     doAjax(POST,contextPath + "/register/sendmail",data,"sendcallback",false);
     }else{
 		$("#registerForm").form("validate");
 	}
}

// timer处理函数
var isTimeout = true;
function SetRemainTime() {
      if (curCount == 0) {//验证码失效
          window.clearInterval(InterValObj);// 停止计时器
          $("#sendEmailBtn").removeAttr("disabled");// 启用按钮
          $("#sendEmailBtn").css("background","url(" + contextPath + "/images/btnbg.png) no-repeat");
          $("#sendEmailBtn").val("重新发送验证码");
          
          $("#identifyCode_ErrorMsg").css("color","red");
  		  $("#identifyCode_ErrorMsg").html("验证码失效，请重新获取");
          isTimeout = false;  //失效
      }
      else {
          curCount--;
          var min = Math.floor(curCount/60);  //向下取整
 		  var sec = curCount%60;  //取余
 		  if(min == 0) {
 			  $("#sendEmailBtn").val(sec + "秒");  //如果分钟数为0，则只显示秒
 		  }else {
 			 $("#sendEmailBtn").val(min + "分" + sec + "秒");
 		  }
      }
}

var identifyCodeBack;
function sendcallback(response) {
	identifyCodeBack = response.identifyCode; //获取后台验证码
}

var isRight = false;
function isIdentifyCodeRight() {
	// location.reload(); //重新加载页面，判断session是否超时失效
	var identifyCode = $("#identifyCode").val();
	if(isTimeout) {  //判断是否失效
		if(identifyCodeBack != null && identifyCodeBack != "null" && identifyCodeBack.replace(/(^\s*)|(\s*$)/g, "")!="") {
	 		if(identifyCode == identifyCodeBack) {
	 			$("#identifyCode_ErrorMsg").css("color","green");
				$("#identifyCode_ErrorMsg").html("验证码正确");
				isRight = true;
	 		}else {
	 			$("#identifyCode_ErrorMsg").css("color","red");
				$("#identifyCode_ErrorMsg").html("验证码不正确");
				isRight = false;
	 		}
		}
	}else {
		$("#identifyCode_ErrorMsg").css("color","red");
		$("#identifyCode_ErrorMsg").html("验证码失效，请重新获取");
		isRight = false;
	}
}

function checkValidate() {
	return $("#registerForm").form("validate");
}

function sureRegisterBtn() {
	if(checkValidate()) {//验证通过
		if(!isExistFlag){
			$("#userEmail_ErrorMsg").css("color","red");
			$("#userEmail_ErrorMsg").html("该邮箱号已注册，请点击右侧登录按钮");
		}
		
		if(!isTimeout) { //验证码失效
			$("#identifyCode_ErrorMsg").css("color","red");
			$("#identifyCode_ErrorMsg").html("验证码失效，请重新获取");
		}
		
		if(!isRight) {
			$("#identifyCode_ErrorMsg").css("color","red");
			$("#identifyCode_ErrorMsg").html("验证码不正确");
		}
		
		if(isExistFlag && isRight && isTimeout){
			var data = getFormJson("registerForm");
			data.userId = data.userEmail;   // 如果用邮箱注册，用户id设置为邮箱号
			doAjax(POST,contextPath + "/register/sureRegister",data,"registerCallBack",false);
		}
	}
}

//注册成功，则弹出窗口
function registerCallBack(response) {
	//返回的消息类型
	var errType = response.errType;
	if(errType == INFO) {
		 $('#popToLogin').dialog({
		  	    title: '跳回首页',
		  	    width: 800,
		  	    height: 520,
		  	    closed: false,
		  	    cache: false,
		  	    href: contextPath + '/findPassword/popForwarLogin?option=1',
		  	    modal: true
		  	}); 
	}
}

// 密码强度
function pwdStrength() {
	var pwdValid=$('#userPassword').validatebox('isValid');
	var value = $("#userPassword").val();
	if(pwdValid){
        var strength = checkStrong(value);
        if(strength == 1) {
        	$("#pwd_strength").html("弱");
        }else if(strength == 2) {
        	$("#pwd_strength").html("中");
        }else if(strength == 3) {
        	$("#pwd_strength").html("强");
        }
	}
}

//检测密码强度
function checkStrong(sValue) {
    var modes = 0;
    //正则表达式验证符合要求的
    if (/\d/.test(sValue)) modes++; //数字
    if (/[a-z]/.test(sValue) || /[A-Z]/.test(sValue)) modes++; //字母大小写
    if (/\W/.test(sValue)) modes++; //特殊字符
    return modes;
}

// checkbox是否选中
function ckAgree() {
	var ischeck = $("#ck_agree").is(':checked');
	if(!ischeck) {
		$("#registerBtn").attr('disabled',"true"); // 添加disabled属性
		$("#registerBtn").css("background","gray");
	}else{
		$("#registerBtn").removeAttr("disabled"); // 移除disabled属性
		$("#registerBtn").css("background","url(" + contextPath + "/images/btnbg.png) no-repeat");
	}
}
