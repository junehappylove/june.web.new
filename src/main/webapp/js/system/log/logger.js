//API定义区
var api_pagedList = contextPath + "/system/log/pageList";
var api_saveEdit = contextPath + "/system/log/saveEdit";
var api_saveNew = contextPath + "/system/log/newSave";
var api_delete = contextPath + "/system/log/deleteSelected";
var api_check = contextPath + "/system/log/checkDetail";
var api_add = contextPath + "/system/log/add";
var api_edit = contextPath + "/system/log/edit";
var api_info = contextPath + "/system/log/info";
var api_list = contextPath + "/system/log/list";

/**
 * 日志信息js
 */
$(function() {
	// 表格初始化
	$('#loggerTable').bootstrapTable({
		cache : false,
		striped : true,
		pagination : true,
		toolbar : '#toolbar',
		pageSize : 10,
		pageNumber : 1,
		pageList : [ 5, 10, 20 ],
		clickToSelect : true,
		sidePagination : 'server',// 设置为服务器端分页
		columns : [
			{ field : "", checkbox : true },
			{ field : "loggerId", title : "ID", titleTooltip:"编码", align : "center", valign : "middle", visible:false},
			{ field : "userId", title : "用户", align : "center", valign : "middle" },
			{ field : "operateType", title : "类型", align : "center", valign : "middle" },
			{ field : 'operateModule', title : '模块', align : 'left', valign : 'middle' },
			{ field : "operateRemark", title : "注释", align : "center", valign : "middle" },
			{ field : "operateMethod", title : "方法", align : "left", valign : "middle" },
			{ field : "operateParams", title : "参数", align : "center", valign : "middle" },
			{ field : "operateTime", title : "时间", align : "center", valign : "middle" },
			{ field : "opration", title : "操作", align : "center", valign : "middle", formatter : $BUTTON } 
		],
		onPageChange : function(size, number) {
			searchInfo();
		},
		formatNoMatches : function() {
			return NOT_FOUND_DATAS;
		}
	});
	//默认查询加载数据
	searchInfo();
});

function $BUTTON(value, row, index) {
	return detailBtn(row.loggerId);
}

//查看详细信息
function checkDetail(loggerId) {
	var data = {
		loggerId : loggerId
	};
	//doAjax(POST, api_check, data, checkDetailSuccess);
	infoDetail(loggerId);
}

function infoDetail(id){
	// 为菜单置权
	layer.open({
		type:2,
		title:'日志详情',
		icon:'',
		shadeClose:true,
		area: ['480px', '90%'],
		content: api_info+"?id="+id
	});
}

// 查询表格信息
function searchInfo() {
	var data = getFormJson("searchForm");// 获取查询条件
	commonRowDatas("loggerTable", data, api_pagedList, "commonCallback", true);
}
// 删除
function deleteRow() {
	var rowCount = selectedCount("loggerTable");
	if (rowCount > 0) {
		// 获取选中行
		var rows = selectedRows("loggerTable");
		var ids = "";
		var row = {};
		for ( var i = 0; i < rows.length; i++) {
			ids += rows[i].loggerId + ",";
		}
		var data = {
			ids : ids
		};
		showConfirm(sureDelete, IF_DELETE_INFO, POST, api_delete, data, searchInfo);
	} else {
		showOnlyMessage(ERROR, $message("ErrorSelectNoDelete", null));
	}
}
//删除调用
function sureDelete(type, url, data, success) {
	doAjax(POST, url, data, success);
}

// 详情回调
function checkDetailSuccess(response) {
	if (response.errList.length == 0) {
		$("#modalForm #loggerId").attr("readonly", "readonly");
		$("#modalForm #loggerId").val(response.loggerId);
		$("#modalForm #userId").val(response.userId);
		$("#modalForm #operateType").val(response.operateType);
		$("#modalForm #operateModule").val(response.operateModule);
		$("#modalForm #operateRemark").val(response.operateRemark);
		$("#modalForm #operateMethod").val(response.operateMethod);
		$("#modalForm #operateParams").val(response.operateParams);
		$("#modalForm #operateTime").val(response.operateTime);
		$('#title').html('');
		$('#title').append("编辑菜单信息");// 设置modal的标题
		$('#myModal').modal({
			show : true,
			backdrop : 'static',
			keyboard : false
		});
	}
}
