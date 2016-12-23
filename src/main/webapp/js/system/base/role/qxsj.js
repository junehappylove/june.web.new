var api_getPagedList = contextPath + "/system/base/qxsj/getPagedList";
var api_saveEdit = contextPath + "/system/base/qxsj/saveEdit";
var api_saveNew = contextPath + "/system/base/qxsj/newSave";
var api_delete = contextPath + "/system/base/qxsj/deleteSelected";
var api_check = contextPath + "/system/base/qxsj/checkDetail";
var api_initOrgTree = contextPath + "/system/org/initOrgTree";
var api_initRoleTree = contextPath + "/system/org/initRoleTree";
/**
 * 权限信息js
 */
$(function() {
	$('#modalForm').bootstrapValidator(
			{
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					qxsj_code : {
						validators : {
							notEmpty : {
								message : getMessageFromList("ErrorMustInput",
										[ '权限代码' ])
							},
							stringLength : {
								min : 0,
								max : 64,
								message : getMessageFromList("ErrorLength2", [
										'权限代码', '0', '64' ])
							}
						}
					},
					qxsj_name : {
						validators : {
							stringLength : {
								min : 0,
								max : 128,
								message : getMessageFromList("ErrorLength2", [
										'权限名称', '0', '128' ])
							}
						}
					}
				}
			}).on('success.form.bv', function(e) {
		// Prevent form submission
		e.preventDefault();
		// Get the form instance
		var $form = $(e.target);
		// Get the BootstrapValidator instance
		var bv = $form.data('bootstrapValidator');
		if ($("#isNew").val() == 0) {
			// 编辑保存
			doAjax(POST, api_saveEdit, $form.serialize(), saveSuccess);
		} else if ($("#isNew").val() == 1) {
			// 新增保存
			doAjax(POST, api_saveNew, $form.serialize(), saveSuccess);
		}
	});

	// 表格初始化
	$('#qxsjInfoTable').bootstrapTable({
						cache : false,
						striped : true,
						pagination : true,
						toolbar : '#toolbar',
						pageSize : 5,
						pageNumber : 1,
						pageList : [ 5, 10, 20 ],
						clickToSelect : true,
						sidePagination : 'server',// 设置为服务器端分页
						columns : [
								{
									field : "",
									checkbox : true
								},
								{
									field : "qxsj_code",
									title : "代码",
									align : "center",
									valign : "middle"
								},
								{
									field : "qxsj_name",
									title : "名称",
									align : "center",
									valign : "middle"
								},
								{
									field : "qxsj_type",
									title : "类型",
									align : "center",
									valign : "middle"
								},
								{
									field : 'qxsj_menu',
									title : '菜单',
									width : '',
									align : 'center'
								},
								{
									field : "qxsj_sort",
									title : "排序",
									align : "center",
									valign : "middle"
								},
								{
									field : "qxsj_used",
									title : "状态",
									align : "center",
									valign : "middle"
								},
								{
									field : "qxsj_text",
									title : "描述",
									align : "center",
									valign : "middle"
								},
								{
									field : "opration",
									title : "操作",
									align : "center",
									valign : "middle",
									formatter : function(value, row, index) {
										var operation = '<a href="#" onclick="checkDetail(\''
												+ row.qxsj_code
												+ '\')">查看详细</a>';
										return operation;
									}
								} ],
						onPageChange : function(size, number) {
							searchInfo();
						},
						formatNoMatches : function() {
							return NOT_FOUND_DATAS;
						}
					});
});

$(function() {
	searchInfo();
});

function saveSuccess(response) {
	var errType = response.errType;
	if (errType != ERROR) {
		closemodal();
		searchInfo();
	} else {
		// $('#modalForm').bootstrapValidator('validate');
		$("#saveBtn").removeAttr("disabled");
	}

}

// 查询表格信息
function searchInfo() {
	var data = getFormJson("searchForm");// 获取查询条件
	commonGetrowdatas("qxsjInfoTable", data, api_getPagedList, "commonCallback",
			true);
}

function deleteRow() {
	var rowCount = GetDataGridRows("qxsjInfoTable");
	if (rowCount > 0) {
		// 获取选中行
		var rows = GetSelectedRowsObj("qxsjInfoTable");
		var qxsj_codes = "";
		for (var i = 0; i < rows.length; i++) {
			qxsj_codes = qxsj_codes + rows[i].qxsj_code + ",";
		}
		var data = {
			qxsj_code : qxsj_codes
		}
		showConfirm(sureDelete, IF_DELETE_INFO, POST, api_delete, data,
				searchUserInfo);
	} else {
		showOnlyMessage(ERROR,
				getMessageFromList("ErrorSelectNoDelete", null));
	}

}

function sureDelete(type, url, data, success) {
	doAjax(POST, url, data, success);
}

// 点击编辑按钮向后台请求要查询的数据
function editRow() {
	var selectRows = GetDataGridRows("qxsjInfoTable");
	if (selectRows == 0) {
		showOnlyMessage(ERROR, getMessageFromList("ErrorNoSelectEdit", null));
	} else if (selectRows > 1) {
		showOnlyMessage(ERROR, getMessageFromList("ErrorSelectMultiEdit", null));
	} else {
		var row = GetSelectedRowsObj("qxsjInfoTable");
		$("#isNew").val('0');
		checkDetail(row[0].qxsj_code);
	}

}

// 关闭modal画面
function closemodal() {
	// 关闭前先清空modal画面的form表单
	$('#modalForm').data('bootstrapValidator').resetForm(true);
	// 将hidden项清空
	$("#isNew").val('');
	$("#qxsj_code").val('');
	$('#title').html('');// 设置modal的标题
	$("#modalForm #qxsj_code").removeAttr("readonly");
	$('#myModal').modal('hide');
}

// 查看权限详细信息
function checkDetail(qxsj_code) {
	var data = {
			qxsj_code : qxsj_code
	};
	doAjax(POST, api_check, data, checkDetailSuccess);
}

function checkDetailSuccess(response) {
	if (response.errList.length == 0) {
		$("#modalForm #qxsj_code").attr("readonly", "readonly");
		$("#modalForm #qxsj_code").val(response.qxsj_code);
		$("#modalForm #qxsj_name").val(response.qxsj_name);
		$("#modalForm #qxsj_menu").val(response.qxsj_menu);
		$("#modalForm #qxsj_sort").val(response.qxsj_sort);
		$("#modalForm #qxsj_type").val(response.qxsj_type);
		$('#modalForm #qxsj_type').selectpicker('refresh');
		$("#modalForm #qxsj_used").val(response.qxsj_used);
		$('#modalForm #qxsj_used').selectpicker('refresh');
		$("#modalForm #qxsj_text").val(response.qxsj_text);
		$('#title').html('');
		$('#title').append("编辑权限信息");// 设置modal的标题
		$('#myModal').modal({
			show : true,
			backdrop : 'static',
			keyboard : false
		});
	}

}

function initOrg() {
	loadOrgTree();
	$('#treeModal').modal({
		show : true,
		backdrop : 'static',
		keyboard : false
	});
}
function closeTreemodal() {
	$('#treeModal').modal('hide');
}

// 权限树初始化
function loadOrgTree() {
	doAjax(POST, api_initOrgTree, null, orgtreeCallback);
}
// 回调函数中加载权限树
function orgtreeCallback(response) {
	var setting = {
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : clickTreeNoed
		}
	};
	$.fn.zTree.init($("#orgTree"), setting, response);
}
// 节点点击事件
function clickTreeNoed(event, treeId, treeNode, clickFlag) {
	$("#orgName").val(treeNode.name);
	$("#qxsj_code").val(treeNode.id);
	// 由于不是直接在输入框中输入，需要重新出发校验事件
	$('#modalForm')
	// Get the bootstrapValidator instance
	.data('bootstrapValidator')
	// 将之前的检测结果清除，以便重新进行检测
	.updateStatus('qxsj_name', 'NOT_VALIDATED', null)
	// Validate the field
	.validateField('qxsj_name');
	$('#treeModal').modal('hide');
}

// 初始化角色树
function initRole() {
	loadRoleTree();
	$('#roleModal').modal({
		show : true,
		backdrop : 'static',
		keyboard : false
	});

}
function loadRoleTree() {
	var data = {
		id : $("#qxsj_code").val()
	}
	doAjax(POST, api_initRoleTree, data, roleTreeCallback);
}

function roleTreeCallback(response) {
	var setting = {
		check : {
			enable : true
		},
		data : {
			simpleData : {
				enable : true
			}
		}
	};
	$.fn.zTree.init($("#roleTree"), setting, response);
}
// 关闭角色选择modal
function closerolemodal() {
	$('#roleModal').modal('hide');
}
// 角色选择画面确认按钮压下
function roleSureClick() {
	var treeObj = $.fn.zTree.getZTreeObj("roleTree");
	var nodes = treeObj.getCheckedNodes(true);
	var nodeIds = "";
	var nodeNames = "";
	for (var i = 0; i < nodes.length; i++) {
		if (i == nodes.length - 1) {
			nodeIds = nodeIds + nodes[i].id;
			nodeNames = nodeNames + nodes[i].name;
		} else {
			nodeIds = nodeIds + nodes[i].id + ","
			nodeNames = nodeNames + nodes[i].name + ","
		}
	}
	$("#roleName").val(nodeNames);
	$("#roleId").val(nodeIds);
	// 由于不是直接在输入框中输入，需要重新出发校验事件
	$('#modalForm')
	// Get the bootstrapValidator instance
	.data('bootstrapValidator')
	// 将之前的检测结果清除，以便重新进行检测
	.updateStatus('roleName', 'NOT_VALIDATED', null)
	// Validate the field
	.validateField('roleName');
	$('#treeModal').modal('hide');
	$('#roleModal').modal('hide');
}

// 新增权限
function addNew() {
	$('#title').html('');
	$('#title').append("添加权限");// 设置modal的标题
	$("#isNew").val('1');
	$('#myModal').modal({
		show : true,
		backdrop : 'static',
		keyboard : false
	});
}
