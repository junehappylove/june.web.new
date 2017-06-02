var POST = "POST";
var GET = "GET";
var ERROR = "error";
var INFO = "info";
var JSON = "json";
var LABELS = ['default','success','primary','info','warning','danger'];
var SP = "&nbsp;&nbsp;";
/** 常用格式 */
var FILE_TYPE_ARR = null;
/** 文件格式 */
var FILE_TYPE = ["pdf"];//[ "txt","zip","rar","htm","pdf","doc","docx","xls","xlsx"]
/** 视频格式 */
var VIDEO_TYPE = ["mp4","avi"]; //["flv","avi","wmv","mp4"]
/** 图片格式 */
var IMAGE_TYPE = ["jpg", "gif", "png","bmp"];//["jpg", "gif", "png","bmp"]
/** 运行程序格式 */
var PROGREM_TYPE = ["exe","zip","rar","dll","ocx","so"];//["exe","zip","rar","bin","jar","giz"]
/** 提示_是否删除 */
var IF_DELETE_INFO = "是否要删除选中的行?";
/** 提示_无符合条件的记录 */
var NOT_FOUND_DATAS = '无符合条件的记录';
//表示服务器缓存目录地址在tomcat的server.xml文件中配置
var HTTP_URL = "http://10.50.200.38:8091/ftp/";
//表示ftp服务器用hfs工具代理成http协议访问的地址
var HTTP_URL_ = "http://192.168.100.5:280/XXX_FTP_SEVER";

/**
 * 分页取得一览数据的方法
 * @param gridid 表格id
 * @param data 请求参数
 * @param url 请求url
 */
function pagedRowDatas(gridid, data, url) {
	var options = $('#' + gridid).bootstrapTable('getOptions');
	// 获取当前页
	var currpage = options.pageNumber;
	if (currpage == 0) {
		currpage = 1;
	}
	// 获取当前页显示数据条数
	var pageSize = options.pageSize;
	data.currpage = currpage;
	data.pageSize = pageSize;
	commonGridAjax(POST, url, data, "commonCallback", gridid, true);
}

/**
 * 不分页取得一览数据的方法
 * @param gridid 表格id
 * @param data 请求参数
 * @param url 请求url
 * @param success 回调函数
 */
function unpagedRowDatas(gridid, data, url, success) {
	commonGridAjax(POST, url, data, success, gridid, false);
}

/**
 * 共同取得一览数据的方法
 * @param gridid 表格id
 * @param data 请求参数
 * @param url 请求url
 * @param success 回调函数
 * @param boolean 是否分页
 */
function commonRowDatas(gridid, data, url, success, boolean) {
	if (boolean) {
		var options = $('#' + gridid).bootstrapTable('getOptions');
		// 获取当前页
		var currpage = options.pageNumber;
		if (currpage == 0) {
			currpage = 1;
		}
		// 获取当前页显示数据条数
		var pageSize = options.pageSize;
		data.currpage = currpage;
		data.pageSize = pageSize;
	}
	commonGridAjax(POST, url, data, success, gridid, boolean);
}

// 获取表格数据共通ajax方法，仅供内部调用
function commonGridAjax(type, url, data, success, gridid, boolean) {
	$.ajax({
		type : type,
		url : url,
		data : data,
		dataType : JSON,
		success : function(response) {
			var result = response;
			// 消息
			var errList = result.errList;
			// 返回的消息类型
			var errType = result.errType;
			// 后台有消息返回
			if (errList != undefined && errList.length != 0) {
				showErrMsgFromBack(errType, errList);// 页面显示错误消息
				if (success != null && success != "commonCallback") {
					eval(success)(response);
				} else if (success == "commonCallback") {
					commonCallback(response, gridid, url, data, boolean);
				}
				return;
			} else {
				// 如果回调函数=="commonCallback"则调用共通的回调函数，否则调用自定义回调函数
				if (success != null && success != "commonCallback") {
					eval(success)(response);
				} else if (success == "commonCallback") {
					commonCallback(response, gridid, url, data, boolean);
				}
			}
		},
		error : function(jqXHR, exception) {
			if (jqXHR.status === 0) {
				showOnlyMessage(ERROR, "服务器停止运行，请与管理员联系");
			} else if ((jqXHR.responseText).indexOf("401") > 0) {
				top.location.href = contextPath + "/login/error?error=401";
			} else if ((jqXHR.responseText).indexOf("403") > 0) {
				top.location.href = contextPath + "/login/error?error=403";
			} else if ((jqXHR.responseText).indexOf("500") > 0) {
				top.location.href = contextPath + "/login/error?error=500";
			} else if (exception === 'parsererror') {

				showOnlyMessage(ERROR, "json数据解析错误");
			} else if (exception === 'timeout') {
				showOnlyMessage(ERROR, "请求超时，请重试");
			} else {
				showOnlyMessage(ERROR, "系统异常，请与管理员联系");
			}
		}

	});
}

/**
 * 共同回调函数
 * @param response 后台传回的数据
 * @param gridid 表格控件的id
 * @param boolean 是否需要分页条件
 */
function commonCallback(response, gridid, url, data, boolean) {
	if (gridid != null && gridid != "") {
		$("#" + gridid).bootstrapTable('load', response);
		// db中数据被删除了，检索的后一页没有数据，页面显示前一页的数据
		if(boolean){
			if (response.rows.length == 0 && response.total > 0) {
				data.currpage = data.currpage - 1;
				commonRowDatas(gridid, data, url, "commonCallback", boolean);
			}
		}
	}
}

/**
 * 单独上传文件ajax共同方法
 * @param type
 * @param url
 * @param id
 * @param success
 */
function ajaxFileUpload(type, url, id, success) {
	// 执行上传文件操作的函数
	$.ajaxFileUpload({
		// 处理文件上传操作的服务器端地址
		url : url,
		type : type,
		secureuri : false, // 是否启用安全提交,默认为false
		fileElementId : id, // 文件选择框的id属性
		dataType : JSON, // 服务器返回的格式,可以是json或xml等
		success : function(data, status) { // 服务器响应成功时的处理函数
			// 返回结果转化成json格式
			var result = data;
			// 消息
			var errList = result.errList;
			// 返回的消息类型
			var errType = result.errType;
			if (errList != undefined && errList.length != 0) {
				showErrMsgFromBack(errType, errList);
				if (success != null) {
					eval(success)(data);
				}
				return;
			} else {
				eval(success)(data);
			}
		},
		error : function(data, status, e) { // 服务器响应失败时的处理函数
			showOnlyMessage(ERROR, "系统错误，请与管理员联系！");
		}
	});
}

/**
 * form表单中有file的form提交共同方法
 * @param id 表单id
 * @param type 提交类型 get / post
 * @param url 提交路径
 * @param success 提交成功的回调函数
 */
function doFileFormAjax(id, type, url, success) {
	var loading = $('#loddingModal');
	if(loading!=null||loading!=undefined){
		loading.modal({ show : true, backdrop : 'static', keyboard : false });
	}
	$("#" + id).ajaxSubmit({
		type : type,
		url : url,
		data : $("#" + id).formSerialize(),
		dataType : JSON,
		success : function(response) {
			// 返回结果转化成json格式
			var result = response;
			// 消息
			var errList = result.errList;
			// 返回的消息类型
			var errType = result.errType;
			if (errList.length != 0) {
				showErrMsgFromBack(errType, errList);
				if (success != null) {
					eval(success)(response);
				}
				return;
			} else {
				eval(success)(response);
			}
		},
		error : function(jqXHR, exception) {
			if (jqXHR.status === 0) {
				showOnlyMessage(ERROR, "服务器停止运行，请与管理员联系");
			} else if ((jqXHR.responseText).indexOf("401") > 0) {
				top.location.href = contextPath + "/login/error?error=401";
			} else if ((jqXHR.responseText).indexOf("403") > 0) {
				top.location.href = contextPath + "/login/error?error=403";
			} else if ((jqXHR.responseText).indexOf("500") > 0) {
				top.location.href = contextPath + "/login/error?error=500";
			} else if (exception === 'parsererror') {
				showOnlyMessage(ERROR, "json数据解析错误");
			} else if (exception === 'timeout') {
				showOnlyMessage(ERROR, "请求超时，请重试");
			} else {
				showOnlyMessage(ERROR, "系统异常，请与管理员联系");
			}
		}
	});
}

/**
 * 非表格数据用ajax请求
 * 调用共同ajax方法，外部接口
 */
function doAjax(type, url, data, success) {
	docommonAjax(type, url, data, success);
}

/**
 * ajax调用封装
 * @param url
 * @param data
 * @param callback
 */
function $ajax(url,data,callback){
	doAjax(POST,url,data,callback);
} 

/**
 * ajax共同方法
 * @param type
 * @param url
 * @param data
 * @param success
 */
function docommonAjax(type, url, data, success) {
	$.ajax({
		type : type,
		url : url,
		data : data,
		dataType : JSON,
		success : function(response) {
			var result = response;
			// 消息
			var errList = result.errList;
			// 返回的消息类型
			var errType = result.errType;
			// 后台有消息返回
			if (errList != undefined && errList.length != 0) {
				showErrMsgFromBack(errType, errList); // 显示错误消息
				// 回调函数
				eval(success)(response);
				return;
			} else {
				// 回调函数
				eval(success)(response);
			}
		},
		error : function(jqXHR, exception) {
			if (jqXHR.status === 0) {
				showOnlyMessage(ERROR, "服务器停止运行，请与管理员联系");
			} else if ((jqXHR.responseText).indexOf("401") > 0) {
				top.location.href = contextPath + "/login/error?error=401";
			} else if ((jqXHR.responseText).indexOf("403") > 0) {
				top.location.href = contextPath + "/login/error?error=403";
			} else if ((jqXHR.responseText).indexOf("500") > 0) {
				top.location.href = contextPath + "/login/error?error=500";
			} else if (exception === 'parsererror') {
				showOnlyMessage(ERROR, "json数据解析错误");
			} else if (exception === 'timeout') {
				showOnlyMessage(ERROR, "请求超时，请重试");
			} else {
				showOnlyMessage(ERROR, "系统异常，请与管理员联系");
			}
		}
	});
}

/**
 * 弹出提示消息，供外部调用用接口 消息内容是从js配置文件中获取
 * 
 * @param type 弹出消息类型 消息类型分为error、info和warning三种类型
 */
function showMessage(type, fromid) {
	if (main_Error_Array.length != 0) {
		// showErrMsg(type);
		showErrMsgLayer(type, fromid);
		return false;
	}
	return true;
}

/**
 * 弹出提示消息 不使用共通的check方式时弹出错误消息
 */
function showOnlyMessage(type, message) {
	showParamMessage(type, message);
}

// 弹出后台传回的消息消息
// 供内部调用
function showErrMsgFromBack(type, errList) {
	var str = "";
	for ( var i = 0; i < errList.length; i++) {
		str += (errList[i] + "</br>");
	}
	// 错误消息提示
	if (type == ERROR) {
		$.notify({
			icon : 'glyphicon glyphicon-remove-sign',
			title : '<strong>Error:</strong>',
			message : str
		}, {
			type : 'danger',
			placement : {
				from : "top",
				align : "center"
			}
		});
	}
	// 消息提示
	else if (type == "info") {
		$.notify({
			icon : 'glyphicon glyphicon-ok-sign',
			title : '<strong>Info:</strong>',
			message : str
		}, {
			type : 'success',
			placement : {
				from : "top",
				align : "center"
			}
		});
	}
	// 警告消息提示
	else if (type == "warning") {
		$.notify({
			icon : 'glyphicon glyphicon-warning-sign',
			title : '<strong>Warning:</strong>',
			message : str
		}, {
			type : 'warning',
			placement : {
				from : "top",
				align : "center"
			}
		});
	} else {
		$.notify({
			icon : 'glyphicon glyphicon-ok-sign',
			title : '<strong>Info:</strong>',
			message : str
		}, {
			type : 'success',
			placement : {
				from : "top",
				align : "center"
			}
		});
	}
}

// 求出两个日期相差的天数
function DateDiff(sDate1, sDate2) { 
	// sDate1和sDate2是yyyy-MM-dd格式
	var aDate, oDate1, oDate2, iDays;
	aDate = sDate1.split("-");
	oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]); // 转换为yyyy-MM-dd格式
	aDate = sDate2.split("-");
	oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); // 把相差的毫秒数转换为天数

	return iDays; // 返回相差天数
}

// 获取表格选中的行数
function selectedCount(id) {
	var rows = $('#' + id).bootstrapTable('getAllSelections').length;
	return rows;
}

// 获取选表格中行
function selectedRows(id) {
	var rowsObj = $('#' + id).bootstrapTable('getSelections');
	return rowsObj;
}

// 获取form中的数据，并将其转换成ajax需要的数据格式
function getFormJson(formId) {
	var o = {};
	var fid = "#" + formId;
	var a = $(fid).serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value.trim() || '');
		} else {
			o[this.name] = this.value.trim() || '';
		}
	});
	return o;
}

/**
 * 弹出确认框
 * @param funName
 * @param msg
 * @param type
 * @param url
 * @param date
 * @param success
 */
function showConfirm(funName, msg, type, url, date, success) {
	bootbox.setLocale("zh_CN");
	bootbox.confirm(msg, function(result) {
		if (result) {
			eval(funName)(type, url, date, success);
		}
	});
}

/**
 * 弹出确认框
 * @param msg
 * @param type
 * @param url
 * @param date
 * @param success
 */
function $confirm(msg, type, url, date, success){
	showConfirm("$sure",msg, type, url, date, success);
}

/**
 * 仅供确认对话框调用
 * @param type
 * @param url
 * @param data
 * @param success
 */
function $sure(type, url, data, success) {
	doAjax(POST, url, data, success);
}

/**
 * 弹出提示消息
 * @param type
 * @param message
 */
function showOnlyMessage(type, message) {
	showParamMessage(type, message);
}

/**
 * 对input框进行md5加密
 * @param id
 */
function Encrypt(id) {
	if ($("#" + id).val() != null && $("#" + id).val() != "") {
		$("#" + id).val($.md5($("#" + id).val()));
	}
}

// 获取checkboxtree所有选中的节点
function getSelectTree(treeId) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = treeObj.getCheckedNodes(true);
	return nodes;
}

// 比较开始日期和结束日期
function checkEndTime(startid, endid) {
	var startTime = $("#" + startid).val();
	// var start=new Date(startTime.replace("-", "/").replace("-", "/"));
	var start = new Date(startTime);
	var endTime = $("#" + endid).val();
	// var end=new Date(endTime.replace("-", "/").replace("-", "/"));
	var end = new Date(endTime);
	if (end < start) {
		return false;
	}
	return true;
}

/**
 * 详情btn
 * @param value
 * @returns {String}
 */
function detailBtn(value) {
	//var html = '<span class="label label-sm label-info"><a href="#" onclick="checkDetail(\'' + value + '\')"><i class="fa fa-info"></i>详情</a></span>';
	var html = '<a href="javascript:;" class="btn btn-outline btn-circle btn-sm purple" onclick="checkDetail(\'' + value + '\')"><i class="fa fa-info"></i>详情</a>';
	return html;
}

/**
 * 编辑btn
 * @param value
 * @returns {String}
 */
function editBtn(value) {
	var html = '<a href="javascript:;" class="btn btn-outline btn-circle btn-sm purple" onclick="editDetail(\'' + value + '\')"><i class="fa fa-edit"></i>编辑</a>';
	return html;
}
