/**
 * 用户信息js
 */
$(function() {
	//日期初始化
	initDate();
	// modalform校验
	$('#modalForm').bootstrapValidator(
			{
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					userId : {
						validators : {
							notEmpty : {
								message : getMessageFromList("ErrorMustInput",[ '用户ID' ])
							},
							stringLength: {
			                    min: 5,
			                    max: 16,
			                    message: getMessageFromList("ErrorLength2",['用户ID','5','16'])
			                }
						}
					},
					userPassword : {
						validators : {
							notEmpty : {
								message : getMessageFromList("ErrorMustInput",[ '密码' ])
							}
						}
					},
					orgName : {
						validators : {
							notEmpty : {
								message : getMessageFromList("ErrorMustInput",[ '组织名' ])
							}
						}
					},
					userName : {
						validators : {
							notEmpty : {
								message : getMessageFromList("ErrorMustInput",[ '用户名' ])
							},
							stringLength: {
			                    min: 5,
			                    max: 16,
			                    message: getMessageFromList("ErrorLength2",['用户名','5','16'])
			                }
						}
					},
					userEmail : {
						validators : {
							emailAddress: {
								message: getMessageFromList("ErrorFormat",['邮箱'])
	                    	},
							stringLength: {
			                    min: 0,
			                    max: 50,
			                    message: getMessageFromList("ErrorLength",['邮箱','50'])
			                }
						}
					},
					userAddress : {
						validators : {
							stringLength: {
			                    min: 0,
			                    max: 255,
			                    message: getMessageFromList("ErrorLength",['地址','255'])
			                }
						}
					},
					userMobile : {
						validators : {
							stringLength: {
			                    min: 0,
			                    max: 11,
			                    message: getMessageFromList("ErrorLength",['手机','11'])
			                },
			                regexp: { 
								regexp: /^(1[0-9])\d{9}$/, 
								message: getMessageFromList("ErrorFormat",['手机号码'])
			                } 
						}
					},
					userTel : {
						validators : {
							stringLength: {
			                    min: 0,
			                    max: 13,
			                    message: getMessageFromList("ErrorLength",['电话号码','13'])
			                },
			                regexp: { 
								regexp: /^(0[0-9]{2,3}-)?([2-9][0-9]{6,7})$/, 
								message: getMessageFromList("ErrorFormat",['电话号码'])
			                } 
						}
					},
					roleName : {
						validators : {
							notEmpty : {
								message : getMessageFromList("ErrorMustInput",[ '角色' ])
								}
						}
					},
					userBirthday : {
						validators : {
								notEmpty : {
									message : getMessageFromList("ErrorMustInput",[ '生日' ])
								},
								date: {
				                    format: 'YYYY-MM-DD',
				                    message: getMessageFromList("ErrorFormat",[ '生日' ])
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
			doAjax("post",contextPath + "/SystemSet/UserInfo/saveEdit",$form.serialize(),saveSuccess);
		}
		else if($("#isNew").val() == 1)
		{
			//新增保存
			doAjax("post",contextPath + "/SystemSet/UserInfo/newSave",$form.serialize(),saveSuccess);
		}
	});
	
	bindChange();
	// 表格初始化
	$('#userInfoTable')
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
									field : "userId",
									title : "用户ID",
									align : "center",
									valign : "middle"
								},
								{
									field : "orgName",
									title : "所属机构",
									align : "center",
									valign : "middle"
								},
								{
									field : "userName",
									title : "用户名",
									align : "center",
									valign : "middle"
								},
								{
									title : '角色',
									field : 'roleName',
									width : '',
									align : 'center'
								},
								{
									field : "userEmail",
									title : "邮箱",
									align : "center",
									valign : "middle"
								},
								{
									field : "userBirthday",
									title : "生日",
									align : "center",
									valign : "middle"
								},
								{
									field : "userAddress",
									title : "地址",
									align : "center",
									valign : "middle"
								},
								{
									field : "userTel",
									title : "座机",
									align : "center",
									valign : "middle"
								},
								{
									field : "userMobile",
									title : "手机",
									align : "center",
									valign : "middle"
								},
								{
									title : '用户账号状态',
									field : 'userLocked',
									width : '',
									align : 'center'
								}
								,
								{
									field : "opration",
									title : "操作",
									align : "center",
									valign : "middle",
									formatter : function(value, row, index) {
										var operation = '<a href="#" onclick="checkDetail(\''+ row.userId + '\')">查看详细</a>';
										return operation;
									}
								} 
								],
						onPageChange : function(size, number) {
							searchUserInfo();
						},
						formatNoMatches : function() {
							return '无符合条件的记录';
						}
					});
});

$(function() {
	searchUserInfo();
});

function saveSuccess(response)
{
	var errType = response.errType;
	if(errType != "error")
	{
		closemodal();
		searchUserInfo();
	}else{
		//$('#modalForm').bootstrapValidator('validate');
		//return true;
		$("#saveBtn").removeAttr("disabled");
	}
	
}

//input框绑定change事件，用于校验
function bindChange()
{
	//日期绑定改变时间进行check
	$("#userBirthday").bind("change",function(){
		 $('#modalForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    //将之前的检测结果清除，以便重新进行检测
	    .updateStatus('userBirthday', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('userBirthday');
	   
	});
}

function initDate()
{
	// 开始日期的日期控件初始化
	$('#userBirthday').daterangepicker({
		singleDatePicker : true,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		showWeekNumbers: true,// 是否显示第几周的数字
		// 设置格式
		locale : {
			"format" : "YYYY-MM-DD",// 设置日期格式,默认为YYYY-MM-DD
			// "separator" : " - ",//设置日期范围的分隔符号
		}
	});
}


//查询表格信息
function searchUserInfo() {
	var data = getFormJson("searchForm");//获取查询条件
	commonGetrowdatas("userInfoTable", data, contextPath
			+ "/SystemSet/UserInfo/getUserInfos", "commonCallback", true)
}

function deleteRow() {
	var rowCount = GetDataGridRows("userInfoTable");
	if (rowCount > 0) {
		// 获取选中行
		var rows = GetSelectedRowsObj("userInfoTable");
		var rowIds = "";
		 for(var i=0;i<rows.length;i++)
		 {
			rowIds = rowIds + rows[i].userId + ",";
		 }
		 var data = {
		      userId:rowIds       
		 }

		showConfirm(sureDelete, "是否要删除选中的行？", "post", contextPath
				+ "/SystemSet/UserInfo/deleteSelected", data, searchUserInfo);
	} else {
		showOnlyMessage("error", getMessageFromList("ErrorSelectNoDelete", null));
	}

}

function sureDelete(type, url, data, success) {
	doAjax("post", url, data, success)
}

// 点击编辑按钮向后台请求要查询的数据
function editRow() {
	//
    var selectRows = GetDataGridRows("userInfoTable");
    if (selectRows == 0)
    {
    	showOnlyMessage("error",getMessageFromList("ErrorNoSelectEdit",null));
    }else if(selectRows > 1)
    {
    	showOnlyMessage("error",getMessageFromList("ErrorSelectMultiEdit",null));
    }
    else{
    	var row = GetSelectedRowsObj("userInfoTable");
    	$("#isNew").val('0');
    	checkDetail(row[0].userId);
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
	$("#modalForm #userId").removeAttr("readonly");
	$('#myModal').modal('hide');
}

//查看用户详细信息
function checkDetail(userId) {
	var data={
	    userId:userId
	}
	doAjax("post",contextPath + "/SystemSet/UserInfo/checkDetail",data,checkDetailSuccess)
}

function checkDetailSuccess(response)
{
	if(response.errList.length == 0)
	{
		$("#modalForm #userId").attr("readonly","readonly");
		$("#modalForm #userId").val(response.userId);
		$("#modalForm #userPassword").val(response.userPassword);
		$("#modalForm #orgName").val(response.orgName);
		$("#modalForm #orgId").val(response.orgId);
		$("#modalForm #userAddress").val(response.userAddress);
		$("#modalForm #userTel").val(response.userTel);
		$("#modalForm #userBirthday").val(response.userBirthday);
		$("#modalForm #userName").val(response.userName);
		$("#modalForm #userEmail").val(response.userEmail);
		$("#modalForm #userMobile").val(response.userMobile);
		$("#modalForm #roleName").val(response.roleName);
		$("#modalForm #roleId").val(response.roleId);
		$("#modalForm #userLocked").val(response.userLocked);
		$('#modalForm #userLocked').selectpicker('refresh');
		$('#title').html('');
		$('#title').append("编辑用户信息");//设置modal的标题
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
	doAjax("post",contextPath+"/SystemSet/UserInfo/initOrgTree",null,orgtreeCallback)
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
	doAjax("post",contextPath+"/SystemSet/UserInfo/initRoleTree",data,roleTreeCallback);
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

//新增用户
function addNew(){
	$('#title').html('');
	$('#title').append("添加用户");//设置modal的标题
	$('#userBirthday').val('');
	$("#isNew").val('1');
	//$('#myModal').modal('show');
	$('#myModal').modal({show:true,backdrop: 'static', keyboard: false});
}
//将密码用md5加密
function EncryptPassword(){
	Encrypt("userPassword");
	Encrypt("userPassword");
	//(Encrypt(Encrypt("userPassword")));
}