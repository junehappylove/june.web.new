var api_getPagedList = contextPath + "/system/base/menu/getPagedList";
var api_saveEdit = contextPath + "/system/base/menu/saveEdit";
var api_saveNew = contextPath + "/system/base/menu/newSave";
var api_delete = contextPath + "/system/base/menu/deleteSelected";
var api_check = contextPath + "/system/base/menu/checkDetail";
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
					menu_id : {
						validators : {
							notEmpty : {
								message : getMessageFromList("ErrorMustInput", [ '菜单ID' ])
							},
							stringLength : {
								min : 0,
								max : 64,
								message : getMessageFromList("ErrorLength2", ['菜单ID', '0', '64' ])
							}
						}
					},
					menu_name : {
						validators : {
							stringLength : {
								min : 0,
								max : 64,
								message : getMessageFromList("ErrorLength2", ['菜单名称', '0', '64' ])
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
	$('#menuInfoTable').bootstrapTable({
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
								{ field : "", checkbox : true },
								{ field : "menu_id", title : "ID",titleTooltip:"编码", align : "center", valign : "middle" , visible:false},
								{ field : "menu_name", title : "名称", align : "center", valign : "middle" },
								{ field : "parent_menu_id", title : "父菜单", align : "center", valign : "middle" },
								{ field : 'menu_url', title : '地址', align : 'center', valign : 'middle' },
								{ field : "menu_icon", title : "图标", align : "center", valign : "middle" },
								{ field : "menu_notice", title : "描述", align : "center", valign : "middle" },
								{ field : "order_num", title : "排序", align : "center", valign : "middle" },
								{ field : "opration", title : "操作", align : "center", valign : "middle",
									formatter : function(value, row, index) {
										june.log(value+" "+row.menu_id);
										return showDetailHtml(row.menu_id);
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
	commonGetrowdatas("menuInfoTable", data, api_getPagedList, "commonCallback", true);
}

function deleteRow() {
	var rowCount = GetDataGridRows("menuInfoTable");
	if (rowCount > 0) {
		// 获取选中行
		var rows = GetSelectedRowsObj("menuInfoTable");
		var ids = "";
		for (var i = 0; i < rows.length; i++) {
			ids += rows[i].menu_id + ",";
		}
		var data = {
			menu_id : ids
		};
		showConfirm(sureDelete, IF_DELETE_INFO, POST, api_delete, data, searchUserInfo);
	} else {
		showOnlyMessage(ERROR, getMessageFromList("ErrorSelectNoDelete", null));
	}

}

function sureDelete(type, url, data, success) {
	doAjax(POST, url, data, success);
}

// 点击编辑按钮向后台请求要查询的数据
function editRow() {
	var selectRows = GetDataGridRows("menuInfoTable");
	if (selectRows == 0) {
		showOnlyMessage(ERROR, getMessageFromList("ErrorNoSelectEdit", null));
	} else if (selectRows > 1) {
		showOnlyMessage(ERROR, getMessageFromList("ErrorSelectMultiEdit", null));
	} else {
		var row = GetSelectedRowsObj("menuInfoTable");
		$("#isNew").val('0');
		checkDetail(row[0].menu_id);
	}

}

// 关闭modal画面
function closemodal() {
	// 关闭前先清空modal画面的form表单
	$('#modalForm').data('bootstrapValidator').resetForm(true);
	// 将hidden项清空
	$("#isNew").val('');
	$("#menu_id").val('');
	$('#title').html('');// 设置modal的标题
	$("#modalForm #menu_id").removeAttr("readonly");
	$('#myModal').modal('hide');
}

// 查看权限详细信息
function checkDetail(menu_id) {
	var data = {
		menu_id : menu_id
	};
	doAjax(POST, api_check, data, checkDetailSuccess);
}

function checkDetailSuccess(response) {
	if (response.errList.length == 0) {
		$("#modalForm #menu_id").attr("readonly", "readonly");
		$("#modalForm #menu_id").val(response.menu_id);
		$("#modalForm #menu_name").val(response.menu_name);
		$("#modalForm #menu_url").val(response.menu_url);
		$("#modalForm #menu_icon").val(response.menu_icon);
		$("#modalForm #parent_menu_id").val(response.parent_menu_id);
		$('#modalForm #parent_menu_id').selectpicker('refresh');
		$("#modalForm #order_num").val(response.order_num);
		$("#modalForm #menu_notice").val(response.menu_notice);
		$('#title').html('');
		$('#title').append("编辑菜单信息");// 设置modal的标题
		$('#myModal').modal({
			show : true,
			backdrop : 'static',
			keyboard : false
		});
	}

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
		id : $("#menu_id").val()
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
	$('#title').append("添加菜单");// 设置modal的标题
	$("#isNew").val('1');
	$('#myModal').modal({
		show : true,
		backdrop : 'static',
		keyboard : false
	});
}
