var api_getPagedList = contextPath + "/system/base/menu/getPagedList";
var api_saveEdit = contextPath + "/system/base/menu/saveEdit";
var api_saveNew = contextPath + "/system/base/menu/newSave";
var api_delete = contextPath + "/system/base/menu/deleteSelected";
var api_check = contextPath + "/system/base/menu/checkDetail";
var api_initMenuTree = contextPath + "/system/base/menu/initMenuTree";
var api_child_menu = contextPath + "/system/base/menu/childMenu";
var api_initOrgTree = contextPath + "/system/org/initOrgTree";
var api_selectQxsj = contextPath + "/system/base/menu/selectQxsj/";//后面需要链接当前菜单的id
/**
 * 权限信息js
 */
$(function() {
	//校验
	$('#modalForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			menu_id : {
				validators : {
					notEmpty : {
						message : $message("ErrorMustInput", [ '菜单ID' ])
					},
					stringLength : {
						min : 0, max : 64,
						message : $message("ErrorLength2", ['菜单ID', '0', '64' ])
					}
				}
			},
			menu_name : {
				validators : {
					stringLength : {
						min : 0, max : 64,
						message : $message("ErrorLength2", ['菜单名称', '0', '64' ])
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		var $form = $(e.target);
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
		pageSize : 10,
		pageNumber : 1,
		pageList : [ 5, 10, 20 ],
		clickToSelect : true,
		sidePagination : 'server',// 设置为服务器端分页
		columns : [
			{ field : "", checkbox : true },
			{ field : "menu_id", title : "ID", titleTooltip:"编码", align : "center", valign : "middle", visible:false},
			{ field : "menu_name", title : "名称", align : "center", valign : "middle" },
			{ field : "parent_menu_id", title : "父菜单", align : "center", valign : "middle" },
			{ field : 'menu_url', title : '地址', align : 'left', valign : 'middle' },
			{ field : "menu_icon", title : "图标", align : "center", valign : "middle" },
			{ field : "menu_notice", title : "描述", align : "left", valign : "middle" },
			{ field : "order_num", title : "排序", align : "center", valign : "middle" },
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
	// 菜单树初始化
	loadMenuTree();
});

function $BUTTON(value, row, index) {
	//XXX 自定义一个设置权限按钮
	//return editBtn(row.menu_id) + SP + detailBtn(row.menu_id);
	return detailBtn(row.menu_id);
}

function editDetail(id){
	// 为菜单置权
	layer.open({
		type:2,
		title:'选择权限按钮',
		shadeClose:true,
		area: ['480px', '90%'],
		content: api_selectQxsj + id
	});
}

function menuControll(){
	var selectRows = selectedCount("menuInfoTable");
	if (selectRows == 0) {
		showOnlyMessage(ERROR, $message("ErrorNoSelectEdit", null));
	} else if (selectRows > 1) {
		showOnlyMessage(ERROR, $message("ErrorSelectMultiEdit", null));
	} else {
		var row = selectedRows("menuInfoTable");
		var isParent = row[0].isParent;
		if(isParent==true||isParent=="true"){
			showOnlyMessage(WARN, $message("WarnNotAuth", null));
		}else{
			editDetail(row[0].menu_id);
		}
	}
}

function saveSuccess(response) {
	var errType = response.errType;
	if (errType != ERROR) {
		closemodal();
		searchInfo();
	} else {
		$("#saveBtn").removeAttr("disabled");
	}
}

// 查询表格信息
function searchInfo() {
	var data = getFormJson("searchForm");// 获取查询条件
	commonRowDatas("menuInfoTable", data, api_getPagedList, "commonCallback", true);
}
// 删除
function deleteRow() {
	var rowCount = selectedCount("menuInfoTable");
	if (rowCount > 0) {
		// 获取选中行
		var rows = selectedRows("menuInfoTable");
		var ids = "";
		var row = {};
		for ( var i = 0; i < rows.length; i++) {
			ids += rows[i].menu_id + ",";
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

//新增菜单
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

// 点击编辑按钮向后台请求要查询的数据
function editRow() {
	var selectRows = selectedCount("menuInfoTable");
	if (selectRows == 0) {
		showOnlyMessage(ERROR, $message("ErrorNoSelectEdit", null));
	} else if (selectRows > 1) {
		showOnlyMessage(ERROR, $message("ErrorSelectMultiEdit", null));
	} else {
		var row = selectedRows("menuInfoTable");
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

// 查看详细信息
function checkDetail(menu_id) {
	var data = {
		menu_id : menu_id
	};
	doAjax(POST, api_check, data, checkDetailSuccess);
}
// 详情回调
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

// 第一级菜单数据加载
function loadMenuTree() {
	var data = {};
	doAjax(POST, api_initMenuTree, data, menuTreeCallback);
}
// 菜单树回调
function menuTreeCallback(response) {
	var nodes = response;
	var setting = {
		async : {
			enable : true,
			url : api_child_menu,		 //取子节点
			autoParam : [ "id", "name" ] // 参数
		},
		callback : {
			onClick : clickNode
		}
	};
	$.fn.zTree.init($("#sycMenus"), setting, nodes);
}

/** 树节点点击 */
function clickNode(event, treeId, treeNode, clickFlag){
	//june.log(treeNode.name + treeNode.id + treeNode.ids);// 点击直接返回节点对象treeNode
	//点击节点做查询子菜单
	var id = treeNode.id;
	var data = {};
	//june.log(treeNode.isParent);
	if(treeNode.isParent == false || treeNode.isParent == 'false'){
		data = {menu_id:id};
	}else{
		data = {parent_menu_id:id};// 获取查询条件
	}
	//june.log(data);
	pagedRowDatas("menuInfoTable", data, api_getPagedList);
}

function menuRoot(){
	var data = {parent_menu_id:'0'};// 获取查询条件
	pagedRowDatas("menuInfoTable", data, api_getPagedList);
}

// 关闭角色选择modal
function closerolemodal() {
	$('#roleModal').modal('hide');
}

// 菜单选择节点被点击
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
	$('#modalForm').data('bootstrapValidator').updateStatus('roleName', 'NOT_VALIDATED', null).validateField('roleName');
	$('#treeModal').modal('hide');
	$('#roleModal').modal('hide');
}
