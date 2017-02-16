var api_getPagedList = contextPath + "/system/org/getPagedList";
/**
 * 组织信息js
 */
$(function() {
	// modalform校验
	$('#modalForm').bootstrapValidator(
			{
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					orgName : {
						validators : {
							notEmpty : {
								message : getMessageFromList("ErrorMustInput",[ '组织名' ])
							},
							stringLength: {
			                    min: 5,
			                    max: 16,
			                    message: getMessageFromList("ErrorLength2",['组织名','0','32'])
			                }
						}
					},
					remark : {
						validators : {
							stringLength: {
			                    min: 0,
			                    max: 255,
			                    message: getMessageFromList("ErrorLength",['地址','255'])
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
		if($("#isNew").val()== 0)
		{
			//编辑保存
			doAjax(POST,contextPath + "/system/org/saveEdit",$form.serialize(),saveSuccess);
		}
		else if($("#isNew").val() == 1)
		{
			//新增保存
			doAjax(POST,contextPath + "/system/org/newSave",$form.serialize(),saveSuccess);
		}
	});
	
	// 表格初始化
	$('#orgInfoTable')
			.bootstrapTable(
					{
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
									field : "orgId",
									title : "组织ID",
									align : "center",
									valign : "middle"
								},
								{
									field : "orgName",
									title : "组织名",
									align : "center",
									valign : "middle"
								},
								{
									field : "parentOrgId",
									title : "上级",
									align : "center",
									valign : "middle"
								},
								{
									title : '领导',
									field : 'orgLeader',
									width : '',
									align : 'center'
								},
								{
									field : "remark",
									title : "地址",
									align : "center",
									valign : "middle"
								},
								{
									field : "delFlag",
									title : "状态",
									align : "center",
									valign : "middle"
								},
								{
									field : "districtId",
									title : "地区",
									align : "center",
									valign : "middle"
								},
								{
									field : "opration",
									title : "操作",
									align : "center",
									valign : "middle",
									formatter : function(value, row, index) {
										var operation = '<a href="#" onclick="checkDetail(\''+ row.orgId + '\')">查看详细</a>';
										return operation;
									}
								} 
								],
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

function saveSuccess(response)
{
	var errType = response.errType;
	if(errType != ERROR)
	{
		closemodal();
		searchInfo();
	}else{
		//$('#modalForm').bootstrapValidator('validate');
		$("#saveBtn").removeAttr("disabled");
	}
	
}


//查询表格信息
function searchInfo() {
	var data = getFormJson("searchForm");//获取查询条件
	commonGetrowdatas("orgInfoTable", data, api_getPagedList, "commonCallback", true);
}

function deleteRow() {
	var rowCount = GetDataGridRows("orgInfoTable");
	if (rowCount > 0) {
		// 获取选中行
		var rows = GetSelectedRowsObj("orgInfoTable");
		var rowIds = "";
		for ( var i = 0; i < rows.length; i++) {
			rowIds += rows[i].orgId + ",";
		}
		var data = {
		    orgId:rowIds       
		}
		showConfirm(sureDelete, IF_DELETE_INFO, POST, contextPath
				+ "/system/org/deleteSelected", data, searchUserInfo);
	} else {
		showOnlyMessage(ERROR, getMessageFromList("ErrorSelectNoDelete", null));
	}

}

function sureDelete(type, url, data, success) {
	doAjax(POST, url, data, success)
}

// 点击编辑按钮向后台请求要查询的数据
function editRow() {
	//
    var selectRows = GetDataGridRows("orgInfoTable");
    if (selectRows == 0)
    {
    	showOnlyMessage(ERROR,getMessageFromList("ErrorNoSelectEdit",null));
    }else if(selectRows > 1)
    {
    	showOnlyMessage(ERROR,getMessageFromList("ErrorSelectMultiEdit",null));
    }
    else{
    	var row = GetSelectedRowsObj("orgInfoTable");
    	$("#isNew").val('0');
    	checkDetail(row[0].orgId);
    }
	
}


// 关闭modal画面
function closemodal() {
	// 关闭前先清空modal画面的form表单
	$('#modalForm').data('bootstrapValidator').resetForm(true);
	//将hidden项清空
	$("#isNew").val('');
	$("#orgId").val('');
	$("#roleId").val('');
	$('#title').html('');//设置modal的标题
	$("#modalForm #orgId").removeAttr("readonly");
	$('#myModal').modal('hide');
}

//查看组织详细信息
function checkDetail(orgId) {
	var data={
	    orgId:orgId
	}
	doAjax(POST,contextPath + "/system/org/checkDetail",data,checkDetailSuccess)
}

function checkDetailSuccess(response)
{
	if(response.errList.length == 0)
	{
		$("#modalForm #orgId").attr("readonly","readonly");
		$("#modalForm #orgId").val(response.orgId);
		$("#modalForm #orgLeader").val(response.orgLeader);
		$("#modalForm #orgName").val(response.orgName);
		$("#modalForm #remark").val(response.remark);
		$("#modalForm #districtId").val(response.districtId);
		$('#modalForm #districtId').selectpicker('refresh');
		$("#modalForm #parentOrgId").val(response.parentOrgId);
		$('#modalForm #parentOrgId').selectpicker('refresh');
		$("#modalForm #delFlag").val(response.delFlag);
		$('#modalForm #delFlag').selectpicker('refresh');
		$('#title').html('');
		$('#title').append("编辑组织信息");//设置modal的标题
		//$('#myModal').modal('show');
		$('#myModal').modal({show:true,backdrop: 'static', keyboard: false});
	}
	
}

function initOrg()
{
	loadOrgTree();
	//$('#treeModal').modal('show');
	$('#treeModal').modal({show:true,backdrop: 'static', keyboard: false});
}
function closeTreemodal()
{
	$('#treeModal').modal('hide');
}

// 组织树初始化
function loadOrgTree()
{
	doAjax(POST,contextPath+"/system/org/initOrgTree",null,orgtreeCallback)
}
//回调函数中加载组织树
function orgtreeCallback(response)
{
	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {  
			onClick: clickTreeNoed 
		}  
	};
	$.fn.zTree.init($("#orgTree"), setting, response);
}
// 节点点击事件
function clickTreeNoed(event, treeId, treeNode,clickFlag)
{
		$("#orgName").val(treeNode.name);
		$("#orgId").val(treeNode.id);
		//由于不是直接在输入框中输入，需要重新出发校验事件
		$('#modalForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    //将之前的检测结果清除，以便重新进行检测
	    .updateStatus('orgName', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('orgName');
		$('#treeModal').modal('hide');
}


//初始化角色树
function initRole()
{
	loadRoleTree();
	//$('#roleModal').modal('show');
	$('#roleModal').modal({show:true,backdrop: 'static', keyboard: false});
	
}
function loadRoleTree()
{
	var data={
	    id:$("#roleId").val()
	}
	doAjax(POST,contextPath+"/system/org/initRoleTree",data,roleTreeCallback);
}

function roleTreeCallback(response)
{
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
	$.fn.zTree.init($("#roleTree"), setting, response);
}
//关闭角色选择modal
function closerolemodal()
{
	$('#roleModal').modal('hide');
}
//角色选择画面确认按钮压下
function roleSureClick()
{
	var treeObj = $.fn.zTree.getZTreeObj("roleTree");
	var nodes = treeObj.getCheckedNodes(true);
	var nodeIds = "";
	var nodeNames = "";
	for(var i=0;i<nodes.length;i++)
	{
		if(i == nodes.length-1)
		{
			nodeIds = nodeIds + nodes[i].id;
			nodeNames = nodeNames + nodes[i].name;
		}else{
			nodeIds = nodeIds + nodes[i].id + ","
			nodeNames = nodeNames + nodes[i].name + ","
		}
	}
	$("#roleName").val(nodeNames);
	$("#roleId").val(nodeIds);
	//由于不是直接在输入框中输入，需要重新出发校验事件
	$('#modalForm')
    // Get the bootstrapValidator instance
    .data('bootstrapValidator')
    //将之前的检测结果清除，以便重新进行检测
    .updateStatus('roleName', 'NOT_VALIDATED', null)
    // Validate the field
    .validateField('roleName');
	$('#treeModal').modal('hide');
	$('#roleModal').modal('hide');
}

//新增组织
function addNew(){
	$('#title').html('');
	$('#title').append("添加组织");//设置modal的标题
	$("#isNew").val('1');
	//$('#myModal').modal('show');
	$('#myModal').modal({show:true,backdrop: 'static', keyboard: false});
}
