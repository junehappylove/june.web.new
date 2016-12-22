
$(function(){
	///*长文本自动换行*/
	june.style('.chat-message{word-wrap:break-word;}');
	
	$("#send_btn").bind("click",function(){
		messend();
	});
	$('#chat_num').bind('click',function(){
		clean$();
	});
	document.onkeydown = function(event) {
		var e = event || window.event
				|| arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 27) { // 按 Esc 
			//关闭聊天框
			$("#mini_chat_close").click();
			$("#message_send").val('');
			$("#message_send").focus();
		}
		if (e && e.keyCode == 113) { // 按 F2 
			//打开聊天窗口
			$("#mini_chat_close").click();
			$("#message_send").val('');
			$("#message_send").focus();
		}
		if (e && e.keyCode == 13) { // enter 键
			//发送消息
			messend();
		}
	};
});

var ws = 'ws://' + window.location.host + ctx+'/minichat';
var url = "http://" + window.location.host + ctx+"/minichat";
var sock = new SockJS(url);//采用SockJS，跨浏览器支持，不直接使用html5的websocket api
var number = 0;
/* if ('WebSocket' in window) {
	sock = new WebSocket(ws);
} else if ('MozWebSocket' in window) {
	sock = new MozWebSocket(ws);
} else {
	sock = new SockJS(url);
} */
sock.onopen = function() {
	june.log('open');
};
sock.onmessage = function(e) {
	june.log('message:'+ e.data);
	tochatview(e.data,1);
	chatnum();
};
sock.onclose = function() {
	june.log('close');
};
sock.onerror = function(e){
	june.log('error' + e.data);
};
function sendMessage(mes){
	tochatview(mes,0);
	sock.send(mes);
	chatnum();
}
function exit(){
	sock.close();
}

function messend(){
	var con = $("input[name='message_send']").val();
	sendMessage(con);
	// 移动聊天的最底部
	//var height = $('#chat_content').height();
	//var chat = $('#content').get(0).scrollHeight;
	//var scrollTop = $('#content').scrollTop();
	//console.log(height+" "+chat+" "+scrollTop);
	//$('#content').scrollTop(height);
}

function tochatview(con, flag) {
	var obj = $("#chat_content");
	var html1 = '<div class="left"><div class="author-name">Server<small class="chat-date">'+june.time()+'</small></div><div class="chat-message active">'
			+ con + '</div></div>';
	var html2 = '<div class="right"><div class="author-name">Client<small class="chat-date">'+june.time()+'</small></div><div class="chat-message">'
			+ con + '</div></div>';
	if (flag == 1) {
		obj.prepend(html1);//append
		//$("#message_send").val('');
		//$("#message_send").focus();
	} else {
		obj.prepend(html2);//append
		$("#message_send").val('');
		$("#message_send").focus();
	}
}
function chatnum() {
	number++;
	$("#chat_num").html(number);
}
function clean$(){
	$("#chat_num").html("0");
	$("#chat_content").empty();
}