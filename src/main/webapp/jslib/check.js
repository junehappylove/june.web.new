/*
 * 校验
 */
var main_Error_Array = new Array();
var firstControlId = "";

function appendErrMsg(msg) {
	main_Error_Array.push(msg);
}

// 弹出提示消息
// 供内部调用
function showErrMsg(type) {
	var str = "";
	for (var i = 0; i < main_Error_Array.length; i++) {
		str += (main_Error_Array[i] + "</br>");
	}
	// 错误消息提示
	if (type == "error") {
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
	else if (type == INFO) {
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
	main_Error_Array = [];
}

// 弹出提示消息(layer风格)
// 供内部调用
function showErrMsgLayer(type, formid) {
	var str = "";
	for (var i = 0; i < main_Error_Array.length; i++) {
		str += (main_Error_Array[i] + "</br>");
	}
	// 错误消息提示
	if (type == "error") {
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
	else if (type == INFO) {
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
	main_Error_Array = [];
}

// 弹出提示消息
// 消息内容不是从js配置文件中获取
function showParamMessage(type, message) {
	// 错误消息提示
	if (type == "error") {
		$.notify({
			icon : 'glyphicon glyphicon-remove-sign',
			title : '<strong>Error:</strong>',
			message : message
		}, {
			type : 'danger',
			placement : {
				from : "top",
				align : "center"
			}
		});
	}
	// 消息提示
	else if (type == INFO) {
		$.notify({
			icon : 'glyphicon glyphicon-ok-sign',
			title : '<strong>Info:</strong>',
			message : message
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
			message : message
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
			message : message
		}, {
			type : 'success',
			placement : {
				from : "top",
				align : "center"
			}
		});
	}
}

// 设置初始化焦点
function focus(id) {
	$("#" + id).focus();
}
// easyui-text设置焦点
function easyuiTextFocus(id) {
	$("#" + id).textbox().next('span').find('input').focus();
}
// easyuicombox设置焦点
function easyuiComboxFocus(id) {
	$("#" + id).combobox().next('span').find('input').focus();
}
// easyui datetime和date控件设置焦点
function easyuiDateFocus(id) {
	$("#" + id).datebox().next('span').find('input').focus();
}
// easyui-combox设置焦点
// 检查输入框是否为空
// 空返回false,不为空true
function checkMustInput(id, msgParams) {
	// 去掉所有空格后再判断是否为空
	var value = $("#" + id).val();
	if (value.replace(/(^\s*)|(\s*$)/g, "") == "") {
		var errMsg = $message("ErrorMustInput", [ msgParams ]);
		appendErrMsg(errMsg);
		if (firstControlId == "") {
			firstControlId = id;
		}
		return false;
	}

}

// 日期控件必须输入check
function checkMustInputDate(id, msgParams) {
	// 去掉所有空格后再判断是否为空
	var value = $("#" + id).datebox('getValue');
	if (value.replace(/(^\s*)|(\s*$)/g, "") == "") {
		var errMsg = $message("ErrorMustInput", [ msgParams ]);
		appendErrMsg(errMsg);
		if (firstControlId == "") {
			firstControlId = id;
		}
		return false;
	}

}

// 下拉框必须 选择
function checkMustSelect(id, msgParams) {
	var value = $("#" + id).combobox('getValue');
	if (value < 0 || value == "--选择--"
			|| value.replace(/(^\s*)|(\s*$)/g, "") == "") {
		var errMsg = $message("ErrorMustSelect", [ msgParams ]);
		appendErrMsg(errMsg);
		if (firstControlId == "") {
			firstControlId = id;
		}
		return false;
	}
}
/*
 * 用途：检查输入对象的值是否符合整数格式 输入：控件id 返回：如果通过验证返回true,否则返回false
 */
function isInteger(id, msgParams) {
	var value = $("#" + id).val();
	if (value != "") {
		var regu = /^[-]{0,1}[0-9]{1,}$/;
		if (regu.test(value)) {
			return true;
		} else {
			var errMsg = $message("ErrorInteger", [ msgParams ]);
			appendErrMsg(errMsg);
			if (firstControlId == "") {
				firstControlId = id;
			}
			return false;
		}
	}
	return true;
}
/**
 * 手机号码格式校验 格式：11位 1开头 返回： 如果通过验证返回true,否则返回false
 */
function checkMobilPhone(id, msgParams) {
	var value = $("#" + id).val();
	if (value != "") {
		var reg = /^(1[0-9])\d{9}$/;
		if (reg.test(value)) {
			return true;
		} else {
			var errMsg = $message("ErrorMobile", [ msgParams ]);
			appendErrMsg(errMsg);
			if (firstControlId == "") {
				firstControlId = id;
			}
			return false;
		}
	}
	return false;
}
/*
 * 用途：检查输入字符串是否符合正整数格式 输入： 控件id 返回： 如果通过验证返回true,否则返回false
 * 
 */
function isPositiveInteger(id) {
	var value = $("#" + id).val();
	var regu = "^[0-9]+$";
	var re = new RegExp(regu);
	if (value.search(re) != -1) {
		return true;
	} else {
		if (firstControlId == "") {
			firstControlId = id;
		}
		return false;
	}
}
/*
 * 用途：检查输入对象的值是否符合E-Mail格式 输入：控件id 返回：如果通过验证返回true,否则返回false
 * 
 */
function isEmail(id, msgParams) {
	var value = $("#" + id).val();
	var myReg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
	if (myReg.test(value)) {
		return true;
	} else {
		var errMsg = $message("ErrorEmail", [ msgParams ]);
		appendErrMsg(errMsg);
		if (firstControlId == "") {
			firstControlId = id;
		}
		return false;
	}
}

/**
 * 日期格式校验
 * 
 * @param id
 *            校验的对象
 * @returns 校验的结果
 */
function checkDate(id, msgParams) {
	var value = $("#" + id).datebox('getValue');
	if (value != "") {
		value = value.replace(/\//g, "-");
		var regDate = /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/;
		if (!regDate.test(value)) {
			// {0}输入的日期类型格式不正确。
			var errMsg = $message("ErrorDate", [ msgParams ]);
			appendErrMsg(errMsg);
			return false;
		}
		var dateValues = value.split("-");
		var iYear = toInt(dateValues[0]);
		var iMonth = toInt(dateValues[1]);
		var iDay = toInt(dateValues[2]);
		if (iMonth == 2) {
			// 2月校验
			if (iDay == 31 || iDay == 30) {
				var errMsg = $message("ErrorDate", [ msgParams ]);
				appendErrMsg(errMsg);
				if (firstControlId == "") {
					firstControlId = id;
				}
				return false;
			} else if ((iYear % 4 != 0) && (iDay == 29)) {
				var errMsg = $message("ErrorDate", [ msgParams ]);
				appendErrMsg(errMsg);
				if (firstControlId == "") {
					firstControlId = id;
				}
				return false;
			}
		} else if (iMonth == 4 || iMonth == 6 || iMonth == 9 || iMonth == 11) {
			// 4,6,9,11月校验
			if (iDay == 31) {
				var errMsg = $message("ErrorDate", [ msgParams ]);
				appendErrMsg(errMsg);
				if (firstControlId == "") {
					firstControlId = id;
				}
				return false;
			}
		}
	}
	return true;
}

// 函数名：CheckDateTime
// 功能介绍：检查是否为日期时间

function IsDateTime(id, msgParams) {
	var value = $("#" + id).datetimebox('getValue');
	if (value.length != 0) {
		var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
		var r = value.match(reg);
		if (r == null) {
			var errMsg = $message("ErrorDate", [ msgParams ]);
			appendErrMsg(errMsg);
			if (firstControlId == "") {
				firstControlId = id;
			}
			return false;
		} else {
			checkDateTime(id, msgParams);
		}
	}

	return true;
}
// 校验datetime类型的年月日是否正确，仅供内部调用
function checkDateTime(id, msgParams) {
	var value = $("#" + id).datebox('getValue').substring(0, 10);
	if (value != "") {
		value = value.replace(/\//g, "-");
		var regDate = /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/;
		if (!regDate.test(value)) {
			// {0}输入的日期类型格式不正确。
			var errMsg = $message("ErrorDate", [ msgParams ]);
			appendErrMsg(errMsg);
			if (firstControlId == "") {
				firstControlId = id;
			}
			return false;
		}
		var dateValues = value.split("-");
		var iYear = toInt(dateValues[0]);
		var iMonth = toInt(dateValues[1]);
		var iDay = toInt(dateValues[2]);
		if (iMonth == 2) {
			// 2月校验
			if (iDay == 31 || iDay == 30) {
				var errMsg = $message("ErrorDate", [ msgParams ]);
				appendErrMsg(errMsg);
				if (firstControlId == "") {
					firstControlId = id;
				}
				return false;
			} else if ((iYear % 4 != 0) && (iDay == 29)) {
				var errMsg = $message("ErrorDate", [ msgParams ]);
				appendErrMsg(errMsg);
				if (firstControlId == "") {
					firstControlId = id;
				}
				return false;
			}
		} else if (iMonth == 4 || iMonth == 6 || iMonth == 9 || iMonth == 11) {
			// 4,6,9,11月校验
			if (iDay == 31) {
				var errMsg = $message("ErrorDate", [ msgParams ]);
				appendErrMsg(errMsg);
				if (firstControlId == "") {
					firstControlId = id;
				}
				return false;
			}
		}
	}
	return true;
}

/**
 * 用途： 且结束如期>=起始日期 输入： startDate：起始日期，id endDate：结束如期，id 返回：
 * 如果通过验证返回true,否则返回false
 */
function checkTwoDate(startDateid, endDateid, msgParams1, msgParams2) {
	var startDate = $("#" + startDateid).datebox('getValue');
	var endDate = $("#" + endDateid).datebox('getValue');
	if (!checkDate(startDateid, msgParams1)) {
		if (!checkDate(endDateid, msgParams2)) {
			if (firstControlId == "") {
				firstControlId = startDateid;
			}
			return false;
		}
		return false;
	} else if (!checkDate(endDateid, msgParams2)) {
		if (firstControlId == "") {
			firstControlId = endDateid;
		}
		return false;
	} else if (startDate > endDate) {
		var errMsg = $message("ErrorDateCopmare", [ msgParams1, msgParams2 ]);
		appendErrMsg(errMsg);
		if (firstControlId == "") {
			firstControlId = startDateid;
		}
		return false;
	}
	return true;
}

/**
 * 电话号码格式校验 格式为「###-###-####」、「###-####-####」、「#{10}」、「#{11}」
 * 
 * @param id
 *            校验的对象
 * @returns 校验的结果
 */
function checkTelphone(id, msgParams) {
	var value = $("#" + id).val();
	if (value != "") {
		var reg = /^(0[0-9]{2,3}-)?([2-9][0-9]{6,7})$/;
		if (reg.test(value)) {
			return true;
		} else {
			var errMsg = $message("ErrorTelphone", [ msgParams ]);
			appendErrMsg(errMsg);
			if (firstControlId == "") {
				firstControlId = id;
			}
			return false;
		}
	}
	return true;
}
/**
 * 半角英数字校验
 * 
 * @param id
 *            校验的对象
 * @returns 校验的结果
 */
function checkAlphaMark(id, msgParams) {
	var value = $("#" + id).val();
	if (value != "") {
		for (i = 0; i < value.length; i++) {
			var code = value.charCodeAt(i);
			if (code < 33 || code > 126) {
				// {0}只能输入半角英数字和符号。
				appendErrMsg($message("ErrorLength", [ msgParams ]));
				if (firstControlId == "") {
					firstControlId = id;
				}
				return false;
			}
		}
	}
	return true;
}
// 长度校验
// 参数：控件id，最大位数
function maxLength(id, val, msgParams1, msgParams2) {
	var length = $("#" + id).val().length;
	if (length > val) {
		appendErrMsg($message("ErrorLength", [ msgParams1, msgParams2 ]));
		if (firstControlId == "") {
			firstControlId = id;
		}
		return false;
	}
	return true;
}
function toInt(val) {
	try {
		var reNum = parseInt(val, 10);
		if (isNaN(reNum)) {
			reNum = 0;
		}
		return reNum;
	} catch (e) {
		return 0;
	}
}

// 是否为数字的校验
function checkNum(id, msgParams) {
	var value = $("#" + id).val();
	var myReg = /^[0-9]*$/;
	if (myReg.test(value)) {
		return true;
	} else {
		var errMsg = $message("Errornum", [ msgParams ]);
		appendErrMsg(errMsg);
		if (firstControlId == "") {
			firstControlId = id;
		}
		return false;
	}
}

// 是否为excel格式的文件
function checkExcelFile(id, msgParams) {
	var filename = $("#" + id).val();
	if (filename != "") {
		filename = filename.split("\\")[2];
		filename = filename.split('.')[1];
		if (filename == "xlsx" || filename == "xls") {
			return true;
		} else {
			var errMsg = $message("ErrorExceleFile", [ msgParams ]);
			appendErrMsg(errMsg);
			if (firstControlId == "") {
				firstControlId = id;
			}
			return false;
		}
	}
}

// 编辑时进行的判断
function checkEditRows(rows) {
	if (rows == 0) {
		var errMsg = $message("SelectRow");
		appendErrMsg(errMsg);
		return false;
	} else if (rows > 1) {
		var errMsg = $message("editmultiselectError");
		appendErrMsg(errMsg);
		return false;
	} else {
		return true;
	}
}

// 增加错误信息
function addErrMsg(msgType, msgParams) {
	var errMsg = $message(msgType, [ msgParams ]);
	appendErrMsg(errMsg);
	return false;
}

// 判断是否是图片
function checkImage(id, msgParams) {
	var filename = $("#" + id).val();
	var fileType = filename
			.substring(filename.lastIndexOf('.'), filename.lenth).toLowerCase();
	if (fileType == '.bmp' || fileType == '.gif' || fileType == '.jpeg'
			|| fileType == '.png' || fileType == '.jpg') {
		return true;
	} else {
		var errMsg = $message("ErrorImageType", [ msgParams ]);
		appendErrMsg(errMsg);
		if (firstControlId == "") {
			firstControlId = id;
		}
		return false;
	}
}

// check编辑框中是否输入内容
function checkFckEditor(id, msgParams) {
	var fckeditor = FCKeditorAPI.GetInstance(id);
	var value = fckeditor.GetHTML(); // 这就是内容
	// 去掉所有空格后再判断是否为空
	if (value.replace(/(^\s*)|(\s*$)/g, "") == "") {
		var errMsg = $message("ErrorMustInput", [ msgParams ]);
		appendErrMsg(errMsg);
		return false;
	}
}

/**
 * 身份证校验
 */
function checkIdentityCard(inputvalue) {
	if (!(isInteger(inputvalue)) || !(isLength(inputvalue))
			|| !(isLegal(inputvalue))) {
		var errMsg = $message("IdCardIllegal");
		appendErrMsg(errMsg);
		if (firstControlId == "") {
			firstControlId = id;
		}
		return false;
	}
	return true;
}

// 身份证校验：15位数字，18位数字+X
function isInteger(inputVal) {
	inputStr = inputVal.toString();
	var length = inputStr.length;
	if (length != 18) {
		for (var i = 0; i < inputStr.length; i++) {
			var onechar = inputStr.charAt(i);
			if (onechar < "0" || onechar > "9")
				return false;
		}
	} else {
		for (var i = 0; i < inputStr.length - 1; i++) {
			var onechar = inputStr.charAt(i);
			if (onechar < "0" || onechar > "9")
				return false;
		}
		var lastchar = inputStr.charAt(17);

		if ((lastchar < "0" || lastchar > "9")
				&& ((lastchar != 'x') && (lastchar != 'X')))
			return false;
	}

	return true;
}

// 身份证校验：长15位或18位
function isLength(inputVal) {
	inputStr = inputVal.toString();
	var length = inputStr.length;
	if ((length != 18) && (length != 15)) {
		return false;
	}
	return true;
}

/*
 * 身份证校验：算法校验
 */
function isLegal(inputVal) {
	var length = inputStr.length
	if (length == 18) {
		inputStr = inputVal.toString();
		var year = parseInt(inputStr.substring(6, 10));
		if (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/g;
			// 测试出生日期的合法性
		} else {
			ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/g;
			// 测试出生日期的合法性
		}
		if (!ereg.test(inputStr.substring(0, inputStr.length - 1))) {
			return false;
		} else {
			var legalcode;
			var maxcode = 0;
			for (var i = 0; i < 17; i++) {
				var ai = parseInt(inputStr.charAt(i));
				var wi = (Math.pow(2, (17 - i))) % 11;
				maxcode = maxcode + ai * wi;
			}
			var precode = maxcode % 11;
			switch (precode) {
			case 0: {
				legalcode = 1;
				if (legalcode != (parseInt(inputStr.charAt(17))))
					return false;
				break;
			}
			case 1: {
				legalcode = 0
				if (legalcode != (parseInt(inputStr.charAt(17))))
					return false;
				break;
			}
			case 2: {
				if (((inputStr.charAt(17)) != 'x')
						&& (inputStr.charAt(17) != 'X'))
					return false;
				break;
			}
			default: {
				legalcode = 12 - precode
				if (legalcode != (parseInt(inputStr.charAt(17))))
					return false;
				break;
			}
			}
		}
	} else {
		return (checkFifteenIdCard(inputVal));
	}
	return true;
}

// 校验15位身份证号
function checkFifteenIdCard(cardNum) {
	var digitalReg = /^[0-9]{15}$/g;
	var ereg;
	if (!digitalReg.test(cardNum)) {
		return false;
	} else {
		var year = parseInt(cardNum.substring(6, 8)) + 1900;
		if (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/g;
			// 测试出生日期的合法性
		} else {
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/g;
			// 测试出生日期的合法性
		}
		return ereg.test(cardNum);
	}
}

// 设置焦点
function setFocus(id) {
	if ($("#" + id).length > 0) {
		// 根据id获取控件的class属性
		var classAttr = $("#" + id).attr("class");
		// 获取控件的type属性
		var tpyeAttr = $("#" + id).attr("type");
		if (tpyeAttr == "checkbox" || tpyeAttr == "radio") {
			focus(id);
		}
		// 根据class属性设置焦点
		else if (classAttr == "" || classAttr == undefined) {
			focus(id);
		} else if (classAttr.split(' ')[0] == "easyui-textbox") {
			easyuiTextFocus(id);
		} else if (classAttr.split(' ')[0] == "combobox") {
			easyuiComboxFocus(id);
		} else if (classAttr.split(' ')[0] == "easyui-datebox"
				|| classAttr.split(' ')[0] == "easyui-datetimebox") {
			easyuiDateFocus(id);
		}
	}

}
