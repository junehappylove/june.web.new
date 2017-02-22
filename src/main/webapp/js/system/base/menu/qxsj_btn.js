var api_noPagedList = contextPath + "/system/base/qxsj/getAllList";

var api_save = contextPath + "/system/base/menu/menuQxsjSave";
var api_delete = contextPath + "/system/base/menu/menuQxsjDelete";

/**
 * 权限信息js
 */
$(function() {
	// 表格初始化
	$('#qxsjInfoTable').bootstrapTable({
		cache : false,
		striped : true,
		pagination : false,
		toolbar : '#toolbar',
		clickToSelect : true,
		columns : [
			{ field : "", checkbox : true, },
			{ field : "qxsj_stat", title : "状态", align : "center", valign : "middle" },
			{ field : "qxsj_code", title : "代码", align : "center", valign : "middle" },
			{ field : "qxsj_name", title : "名称", align : "center", valign : "middle" },
			{ field : "qxsj_type", title : "类型", align : "center", valign : "middle" },
		],
		formatNoMatches : function() {
			return NOT_FOUND_DATAS;
		},
		onLoadSuccess : function(data){ //此方法不好使
			$('#qxsjInfoTable').bootstrapTable("checkBy",{field:"qxsj_code",values:['btnAdd','search']});
		}
	});
	searchList();
});

/**
 * 常用按钮
 */
function useful(){
	$('#qxsjInfoTable').bootstrapTable("checkBy", {
		field : "qxsj_code",
		values : [ 'btnAdd', 'search', 'cancle', 'btnEditSave', 'btnAddSave', 'btnDelete', 'btnEdit']
	});
}

// 查询表格信息
function searchList() {
	var menu_id = $("input[name='menu_id']").val();
	var data = {appid:menu_id};
	commonRowDatas("qxsjInfoTable", data, api_noPagedList, "commonCallback", false);
}

//添加权限按钮
function saveMenuBtn(){
	var rowCount = selectedCount("qxsjInfoTable");
	if (rowCount > 0) {
		// 获取选中行
		var rows = selectedRows("qxsjInfoTable");
		var ids = "";
		var row = {};
		for ( var i = 0; i < rows.length; i++) {
			ids += rows[i].qxsj_code + ",";
		}
		var menu_id = $("input[name='menu_id']").val();
		var data = {
			ids : ids,
			menu_id:menu_id
		};
		var info = $message("InfoOfSave", null);
		$confirm(info, POST, api_save, data, searchList);
	} else {
		var error = $message("ErrorSelect2Operate", null);
		showOnlyMessage(ERROR, error);
	}
}
//删除权限按钮
function deleteMenuBtn(){
	var rowCount = selectedCount("qxsjInfoTable");
	if (rowCount > 0) {
		// 获取选中行
		var rows = selectedRows("qxsjInfoTable");
		var ids = "";
		var row = {};
		for ( var i = 0; i < rows.length; i++) {
			ids += rows[i].qxsj_code + ",";
		}
		var menu_id = $("input[name='menu_id']").val();
		var data = {
			ids : ids,
			menu_id:menu_id
		};
		var info = $message("InfoOfDelete", null);
		$confirm(info, POST, api_delete, data, searchList);
	} else {
		var error = $message("ErrorSelect2Operate", null);
		showOnlyMessage(ERROR, error);
	}
}

