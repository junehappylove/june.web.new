/*
 * 找回密码js
 */
//var kaptcha = $("#h_kaptcha").val();

//跳转至邮箱找回密码页面
function findPwdByEmail(){
	location = contextPath + "/findPassword/findPwdByEmail";
}

function isEmailExist() {
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
		$("#userEmail_ErrorMsg").css("color","red");
		$("#userEmail_ErrorMsg").html("邮箱号不存在");
		isExistFlag = false;
	}else if(response.errType == ERROR ){
		$("#userEmail_ErrorMsg").css("color","green");
		$("#userEmail_ErrorMsg").html("邮箱号填写正确");
		isExistFlag = true;
	}
}

//点击图片，更换验证码
$(function(){
    $('#kaptchaImage').click(function () {//生成验证码
    	$(this).hide().attr('src', contextPath + '/getKaptchaImage?' + Math.floor(Math.random()*100) ).fadeIn();
	    event.cancelBubble=true;
    });
});

//点击链接，更换验证码
function changeCode() {
	$('#kaptchaImage').hide().attr('src', contextPath + '/getKaptchaImage?' + Math.floor(Math.random()*100)).fadeIn();
	event.cancelBubble=true;
}

//判断验证码是否正确
function isKaptchaRight() {
	var isValid=$('#kaptcha').validatebox('isValid');
	var kaptcha = $("#kaptcha").val();
	if(isValid) {
		var data = {kaptcha:kaptcha};
		doAjax(POST,contextPath + "/isKaptchaRight",data,"kaptchacallback",false);
	}
}

var isRightFlag = false;
function kaptchacallback(response) {
	//返回的消息类型
	var errType = response.errType;
	if(errType == INFO) {
		$("#kaptcha_ErrorMsg").css("color","green");
		$("#kaptcha_ErrorMsg").html("验证码填写正确");
		isRightFlag = true;
	}else if(errType == ERROR) {
		$("#kaptcha_ErrorMsg").css("color","red");
		$("#kaptcha_ErrorMsg").html("验证码不正确");
		isRightFlag = false;
	}
}

function checkValidate() {
	return $("#findPwdByEmailForm").form("validate");
}

//发送找回密码请求
function surefindPwdByEmailBtn() {
	if(checkValidate()) {//验证通过
		if(!isExistFlag){
			$("#userEmail_ErrorMsg").css("color","red");
			$("#userEmail_ErrorMsg").html("邮箱号不存在");
		}
		
		if(!isRightFlag) {
			$("#kaptcha_ErrorMsg").css("color","red");
			$("#kaptcha_ErrorMsg").html("验证码不正确");
		}
		
		if(isExistFlag && isRightFlag){
			var data = getFormJson("findPwdByEmailForm");
			doAjax(POST,contextPath + "/findPassword/surefindPwdByEmail",data,findPwdCallBack,false);
		}
	}
}

function findPwdCallBack(response) {
	//返回的消息类型
	var errType = response.errType;
	if(errType == INFO) {
		location = contextPath + "/findPassword/findPwdSendEmail";
	}
}

function checkResetValidate() {
	return $("#resetPwdForm").form("validate");
}

//重置密码
function sureResetPwdBtn() {
	if(checkResetValidate()) {//验证通过
		var data = getFormJson("resetPwdForm");
		doAjax(POST,contextPath + "/findPassword/resetPassword",data,resetPwdCallBack,false);
	}
}

//重置密码成功，则弹出窗口
function resetPwdCallBack(response) {
	//返回的消息类型
	var errType = response.errType;
	if(errType == INFO) {
		 $('#popForwarLogin').dialog({
		  	    title: '跳回首页',
		  	    width: 800,
		  	    height: 520,
		  	    closed: false,
		  	    cache: false,
		  	    href: contextPath + '/findPassword/popForwarLogin?option=2',
		  	    modal: true
		  	}); 
	}
}