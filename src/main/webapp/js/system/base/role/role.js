/*
 * api定义
 */
var api_edit = contextPath + "/system/base/role/saveEditRole";
var api_add = contextPath + "/system/base/role/saveAddRole";
var api_detail = contextPath + "/system/base/role/checkDetail";
var api_menu_tree = contextPath+"/system/base/role/initMenuTree";
var api_menus = contextPath + "/system/base/role/authorityMenus";
var api_user_tree = contextPath+"/system/base/role/initUserTree";
var api_role_list = contextPath + "/system/base/role/getRoleInfo";	
var api_role_dele = contextPath + "/system/base/role/delRole";

var api_assgin_users = contextPath + "/system/base/role/assginUsers";
//初始化表格
$(function() {
	$('#roleInfoTable').bootstrapTable({
		cache : false,
		striped : true,
		pagination : true,
		toolbar : '#toolbar',
		pageSize : 5,
		pageNumber : 1,
		pageList : [ 5, 10, 20],
		clickToSelect : true,
		sidePagination : 'server',// 设置为服务器端分页
		columns : [
			{
				field : "",
				checkbox : true
			},
			{
				field : "roleId",
				title : "角色编号",
				align : "center",
				valign : "middle"
			},
			{
				field : "roleName",
				title : "角色名称",
				align : "center",
				valign : "middle"
			},
			{
				field : "roleDesc",
				title : "角色描述",
				align : "center",
				valign : "middle"
			},
			{
				field : 'addUser',
				title : '添加人',
				width : '',
				align : 'center'
			},
			{
				field : "addTime",
				title : "添加时间",
				align : "center",
				valign : "middle"
			},
			{
				field : "updateUser",
				title : "修改人",
				align : "center",
				valign : "middle"
			},
			{
				field : "updateTime",
				title : "修改时间",
				align : "center",
				valign : "middle"
			}],
		onPageChange : function(size, number) {
			searchRoleInfo();
		},
		formatNoMatches : function() {
			return NOT_FOUND_DATAS;
		}
	});
	// modalform校验
	$('#modalForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			roleName : {
				group: '.group',
				validators : {
					notEmpty : {
						message : getMessageFromList("ErrorMustInput",[ '角色名' ])
					},
					stringLength: {
	                    min: 1,
	                    max: 40,
	                    message: getMessageFromList("ErrorLength2",['角色名','1','40'])
	                }
				}
			},
			roleDesc : {
				group: '.group',
				validators : {
					notEmpty : {
						message : getMessageFromList("ErrorMustInput",[ '角色描述' ])
					},
					stringLength: {
	                    min: 1,
	                    max: 50,
	                    message: getMessageFromList("ErrorLength2",['角色描述','1','50'])
	                }
				}
			}
		}
	}).on('success.form.bv', function(e) {
		// Prevent form submission
		e.preventDefault();
		var $form = $(e.target);
		if($("#isNew").val()== 0) {
			//编辑保存
			doAjax(POST, api_edit, $form.serialize(), saveSuccess);
		}
		else if($("#isNew").val() == 1)	{
			//新增保存
			doAjax(POST, api_add, $form.serialize(), saveSuccess);
		}
	});
});

//页面加载时初始化表格数据
$(function(){
	searchRoleInfo();
});

//检索表格数据
function searchRoleInfo(){
	var data = getFormJson("searchForm");//获取查询条件
	commonGetrowdatas("roleInfoTable", data, api_role_list, "commonCallback", true)
}

//新增角色
function addNew(){
	$('#title').html('');
	$('#title').append("添加角色");//设置modal的标题
	$("#isNew").val('1');
	//显示modal
	$('#myModal').modal({show:true, backdrop: 'static', keyboard: false});
}

// 点击编辑按钮向后台请求要查询的数据
function editRow() {
    var selectRows = GetDataGridRows("roleInfoTable");
    if (selectRows == 0) {
    	showOnlyMessage(ERROR,getMessageFromList("ErrorNoSelectEdit",null));
    }else if(selectRows > 1) {
    	showOnlyMessage(ERROR,getMessageFromList("ErrorSelectMultiEdit",null));
    }else{
    	var row = GetSelectedRowsObj("roleInfoTable");
    	$("#isNew").val('0');
    	checkDetail(row[0].roleId);
    }
}

//查看用户详细信息
function checkDetail(roleId) {
	var data={
		roleId:roleId
	};
	doAjax(POST, api_detail, data, checkDetailSuccess);
}

function checkDetailSuccess(response){
	var errType = response.errType;
	if(errType != ERROR){
		$("#modalForm #roleId").val(response.roleId);
		$("#modalForm #roleName").val(response.roleName);
		$("#modalForm #roleDesc").val(response.roleDesc);
		
		$('#title').html('');
		$('#title').append("编辑角色信息");//设置modal的标题
		$('#myModal').modal({show:true,backdrop: 'static', keyboard: false});
	}
}

//删除选中行
function deleteRow() {
	var rowCount = GetDataGridRows("roleInfoTable");
	if (rowCount > 0) {
		// 获取选中行
		var rows = GetSelectedRowsObj("roleInfoTable");
		var rowIds = "";
		for(var row in rows){
			rowIds += row.roleId + ",";
		}
		var data = {
			roleId:rowIds       
		};
		showConfirm(sureDelete, IF_DELETE_INFO, POST, api_role_dele, data, searchRoleInfo);
	} else {
		showOnlyMessage(ERROR, getMessageFromList("ErrorSelectNoDelete", null));
	}
}

function sureDelete(type, url, data, success) {
	doAjax(POST, url, data, success);
}

// 关闭modal画面
function closemodal() {
	// 关闭前先清空modal画面的form表单
	$('#modalForm').data('bootstrapValidator').resetForm(true);
	//将hidden项清空
	$("#isNew").val('');
	$('#title').html('');//设置modal的标题
	$('#myModal').modal('hide');
}

//保存成功的回调函数
function saveSuccess(response){
	var errType = response.errType;
	if(errType != ERROR){
		closemodal();
		searchRoleInfo();
	}else{
		$("#saveBtn").removeAttr("disabled");
	}
	
}

//初始化菜单树
function initMenu(){
	var selectRows = GetDataGridRows("roleInfoTable");
    if (selectRows == 0)	    {
    	showOnlyMessage(ERROR,getMessageFromList("ErrorNoSelectAssign",null));
    }else if(selectRows > 1)	    {
    	showOnlyMessage(ERROR,getMessageFromList("ErrorSelectMultiAssign",null));
    } else{
    	var row = GetSelectedRowsObj("roleInfoTable");
    	//initMenu(row[0].roleId);
    	loadMenuTree(row[0].roleId);
    	//$('#menuModal').modal('show');
    	$('#menuModal').modal({show:true,backdrop: 'static', keyboard: false});
    }
}

//加载菜单树
function loadMenuTree(roleId){
	doAjax(POST, api_menu_tree, {roleId:roleId}, menuTreeCallback);
}

function menuTreeCallback(response){
	var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		} 
	};
	$.fn.zTree.init($("#menuTree"), setting, response);
}

function assignMenu(){
	//获取选中的节点
	var nodes = getSelectTree("menuTree");
	//获取选中行
	var row = GetSelectedRowsObj("roleInfoTable");
	//alert(nodes[0].id)
	var ids = "";
	for(var i=0;i<nodes.length;i++)	{
		ids = ids + nodes[i].id + ",";
	}
	var data={
		authorityMenusId:ids,
		roleId:row[0].roleId
	}
	doAjax(POST, api_menus, data, authorityMenuSuccess);
}

function authorityMenuSuccess(){
	$('#menuModal').modal('hide');
	searchRoleInfo();
}

function closemenumodal(){
	$('#menuModal').modal('hide');
}

//初始化用户树
function initUser(){
	var selectRows = GetDataGridRows("roleInfoTable");
    if (selectRows == 0) {
    	showOnlyMessage(ERROR,getMessageFromList("ErrorNoSelectAssign",null));
    }else if(selectRows > 1) {
    	showOnlyMessage(ERROR,getMessageFromList("ErrorSelectMultiAssign",null));
    } else{
    	var row = GetSelectedRowsObj("roleInfoTable");
    	loadUserTree(row[0].roleId);
    	//$('#userModal').modal('show');
    	$('#userModal').modal({show:true,backdrop: 'static', keyboard: false});
    }
}
//加载用户树
function loadUserTree(roleId){
	doAjax(POST, api_user_tree, {roleId:roleId}, userTreeCallback);
}

function userTreeCallback(response){
	var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		} 
	};
	$.fn.zTree.init($("#userTree"), setting, response);
}

function closeusermodal(){
	$('#userModal').modal('hide');
}
//为用户分配角色
function assignUser(){
	//获取选中的节点
	var nodes = getSelectTree("userTree");
	//获取选中行
	var row = GetSelectedRowsObj("roleInfoTable");
	var ids = "";
	for(var i=0;i<nodes.length;i++)	{
		if(nodes[i].id != '-1'){
			ids = ids + nodes[i].id + ",";
		}
	}
	var data={
		authorityMenusId:ids,
		roleId:row[0].roleId
	}
	doAjax(POST, api_assgin_users, data, authorityUserSuccess);
}

function authorityUserSuccess(){
	$('#userModal').modal('hide');
}