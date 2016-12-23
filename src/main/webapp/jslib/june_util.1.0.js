/*
 * JUNE软件有限公司<br>
 * jslib.june_util.1.0.js
 * 日期：2015年12月8日
 */
// ////////////////////////////////////////////
// ////////////////////////////////////////////
// ///////////// 举例 //////////////////////////
// ////////////////////////////////////////////
// ////////////////////////////////////////////
// 调用方法:
// 动态加载CSS
// DynamicLoading.css("../css/blue.css");
// 动态加载JS
// DynamicLoading.js("../js/moment.js", "GBK");
// DynamicLoading.js("../js/moment.js", "UTF-8");
// 调用被加载的资源文件中的方法
/////////////////////////////////////////////////////
// 调用方法：june.info("hello world!");
// ///////////////////////////////////////////////////
var INFO = "info", ERROR = "error", WARN = "warning", QUESTION = "question";
var GET = "get", POST = "post", JSON = "json";
var FLAG = "WANG_JUN_WEI";
/**
 * 动态加载
 * 可以动态的加载css文件，和js文件<br>
 * <b>调用方法:</b><br>
 * 动态加载CSS<br>
 * june.css("../css/blue.css");<br>
 * 动态加载JS,默认以utf-8编码<br>
 * june.js("../js/moment.js", "UTF-8");<br>
 * 调用被加载的资源文件中的方法<br>
 */
june={
		info:function(info){
			$.messager.alert("提示",info,INFO);
		},
		error:function(info){
			$.messager.alert("错误",info,ERROR);
		},
		warning:function(info){
			$.messager.alert("警告",info,WARN);
		},
		question:function(info){
			$.messager.alert("疑问",info,QUESTION);
		},
		log:function(info){
			if(console){
				console.log(info);
			}
		},
		progress:function(title,info){
			$.messager.progress({
				title:title==''?"进度":title,
				msg:info,
				text:''
			});
		},
		progress_close:function(){
			$.messager.progress('close');
		},
		developing:function(id){
			$('#'+id).tooltip({
			    position: '',
			    content: '<span style="color:#FFF">此功能正抓紧时间研发中...</span>',
			    onShow: function(){
			        $(this).tooltip('tip').css({
			            backgroundColor: '#666',
			            borderColor: '#666'
			        });
			    }
			});
		},
		random:function(min,max){
			if(min==null||min==undefined)
				min = 0;
			if(max==null||max==undefined)
				max = 10;
			return Math.round(Math.random()*(max-min) + min);
		},
		timestamp:function(){
			return new Date().getTime();
		},
		date:function(){
			return new Date().toLocaleDateString();
		},
		time:function(){
			return new Date().toLocaleTimeString();
		},
		checkImageType:function(type){
			if(type==""){
				return false;
			}else{
				if(!/\.(gif|jpg|jpeg|png|bmp|BMP|JPEG|GIF|JPG|PNG)$/.test(type)){
					return false;
				}
			}
			return true;
		},
		checkVideoType:function(type){
			if(type==""){
				return false;
			}else{
				if(!/\.(mp4|wmv|rmvb|ogg|webm|MP4|WMV|RMVB|OGG|WEBM)$/.test(type)){
					return false;
				}
			}
			return true;
		},
		checkTextType:function(type){
			if(type==""){
				return false;
			}else{
				if(!/\.(txt|TXT|log|LOG|ini|INI)$/.test(type)){
					return false;
				}
			}
			return true;
		},
		checkExcelType:function(type){
			if(type==""){
				return false;
			}else{
				if(!/\.(xls|xlsx|XLS|XLSX)$/.test(type)){
					return false;
				}
			}
			return true;
		},
		checkPDFType:function(type){
			if(type==""){
				return false;
			}else{
				if(!/\.(pdf|PDF)$/.test(type)){
					return false;
				}
			}
			return true;
		},
		style:function(css){
			if (!css || css.length === 0) {
				throw new Error('CSS style is required !');
			}
			var head = document.getElementsByTagName('head')[0];
			$(head).append("<style type='text/css'>"+css+"</style>");
		},
		css : function(path) { // 动态加载CSS
			if (!path || path.length === 0) {
				throw new Error('CSS "path" is required !');
			}
			var head = document.getElementsByTagName('head')[0];
			$(head).append("<link type='text/css' rel='stylesheet' href='" + path + "' />");
		},
		js : function(path, charset) { // 动态加载JS
			var code = charset;
			if (!path || path.length === 0) {
				throw new Error('JS "path" is required !');
			}
			var head = document.getElementsByTagName('head')[0];
			if(charset==undefined||!charset ||charset.length === 0){
				code = "UTF-8";
			}
			$(head).append("<script type='text/javascript' language='javascript' src='" + path + "' charset='" + code + "' /></script>");
		}
		
};
win={
		open:function openWin(url, w, h) { 
		  var l = (screen.width - w) / 2; 
		  var t = (screen.height - h) / 2; 
		  var s = 'width=' + w + ', height=' + h + ', top=' + t + ', left=' + l; 
		  s += ', toolbar=no, scrollbars=no, menubar=no, location=no, resizable=no'; 
		  open(url, 'oWin', s); 
		},
		close:function(){
			window.close();    
		}
};
modal = {
		/**
		 * 打开一个modal
		 * @param modalid
		 * @param title
		 * @param url
		 */
		open : function(modalid,title,content,url){
			// bs-example-modal-sm  modal-sm
			var html =  '<div class="modal fade" id="'+modalid+
				'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'+
						'<div class="modal-diolog" style="height:auto;">'+
						'<div class="modal-content">'+
						'<div class="modal-header">'+
						'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'+
						'<h3 id="myModalLabel">'+title+'</h3>'+
						'<div class="modal-body">'+content+'</div>'+
						'<div class="modal-footer"></div>'+
						'</div></div></div></div>';
			var body = document.getElementsByTagName("body")[0];
			$(body).append(html);
			$("#"+modalid).modal({show : true, backdrop : 'static', keyboard : false, remote:url});
		},
		/**
		 * 关闭一个modal
		 * @param modalid
		 */
		close:function(modalid){
			$('#'+modalid).modal('hide');
			$('#'+modalid).remove();
		}
};

/**
 * 格式化日期
 * @param dateid 日期标签的ID
 * @param bool 是否展示详细日期
 */
function $date(dateid, bool){
	var format = "yyyy-mm-dd";
	if(bool){
		format = "yyyy-mm-dd hh:ii:ss";
	}
	$('#'+dateid).datetimepicker({
		language : 'zh-CN',
		format: format,
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0
	});
}

/**
 * 生日控件设置
 * @param id
 */
function birthday(id){
	$date(id);
}