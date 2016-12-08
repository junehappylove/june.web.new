//点击图片，更换验证码
 $(function(){
     $('#kaptchaImage').click(function () {//生成验证码
     	$(this).hide().attr('src', contextPath + '/getKaptchaImage?' + Math.floor(Math.random()*100) ).fadeIn();
 	    event.cancelBubble=true;
     });
 });

 //点击链接，更换验证码
 function changeCode() {
 	$('#kaptchaImage').hide().attr('src', contextPath + '/getKaptchaImage?' + Math.floor(Math.random()*100) ).fadeIn();
 	event.cancelBubble=true;
 }



 function sureClick()
 {
	 $('#roleForm').attr('action', contextPath + '/login/main');
	 $("#roleName").val($("#roleId").find("option:selected").text());
	 $("#roleForm").submit();
 }
 
 //角色选择画面退出按钮压下
 function logoutClick()
 {
	 $('#roleForm').attr('action', contextPath + '/logout');
	 $("#roleForm").submit();
 }
 
function loginCheck()
{
	 //Encrypt("password");
	 $('#defaultForm').attr('action', contextPath + '/logincheck');
	 $("#defaultForm").submit();
}

$(document).ready(function() {

    $('#defaultForm').bootstrapValidator({
//        live: 'disabled',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            
            username: {
                message: 'The username is not valid',
                validators: {
                    notEmpty: {
                        message: '请输入用户名'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '请输入密码'
                    }
//                ,//密码强度验证，字母+数字+特殊字符
//	                regexp: { 
//						regexp: /^(?!\d+$)(?![a-zA-Z]+$)(?![@#$%^&]+$)[\da-zA-Z@#$%^&]+$/, 
//						message: getMessageFromList("ErrorFormat",['电话号码'])
//				    } 
                }
            }
        }
    }).on('success.form.bv', function(e) {
    	Encrypt("password");//密码加密
        $('#defaultForm').attr('action', contextPath + '/logincheck');
    }); 

});


//将密码用md5加密
function EncryptPassword()
{
	Encrypt("password");
}