/**
 * 中科方德软件有限公司<br>
 * june.web.new:js/system/log.log_add.js
 * 日期：2017年6月6日
 */

var api_check = contextPath + "/system/log/checkDetail";
$(function(){
	checkDetail();
});

//关闭modal画面
function closemodal() {
	
}

// 查看详细信息
function checkDetail() {
	var loggerId = $("input[name='loggerId']").val();
	var data = {
		loggerId : loggerId
	};
	doAjax(POST, api_check, data, checkDetailSuccess);
}

// 详情回调
function checkDetailSuccess(response) {
	if (response.errList.length == 0) {
		$("#modalForm #loggerId").attr("readonly", "readonly");
		$("#modalForm #loggerId").val(response.loggerId);
		$("#modalForm #userId").val(response.userId);
		$("#modalForm #userName").val(response.userName);
		$("#modalForm #operateType").val(response.operateType);
		$("#modalForm #operateModule").val(response.operateModule);
		$("#modalForm #operateRemark").val(response.operateRemark);
		$("#modalForm #operateMethod").val(response.operateMethod);
		$("#modalForm #operateParams").val(response.operateParams);
		$("#modalForm #operateTime").val(response.operateTime);
	}
}