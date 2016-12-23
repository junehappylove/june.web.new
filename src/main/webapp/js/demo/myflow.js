$(function(){
	
// modalform校验
	$('#modalForm')
			.bootstrapValidator(
					{
						feedbackIcons : {
							valid : 'glyphicon glyphicon-ok',
							invalid : 'glyphicon glyphicon-remove',
							validating : 'glyphicon glyphicon-refresh'
						},
						fields : {
							leaveNote : {
								validators : {
									notEmpty : {
										message : getMessageFromList(
												"ErrorMustInput", [ '批注' ])
									}
								}
							}
						}
					})
			.on(
					'success.form.bv',
					function(e) {
						// Prevent form submission
						e.preventDefault();
						var data = {};
						data.note = $("#leaveNote").val();
						data.id = $("#taskId").val();
						data.outcome = $("#agree").text();
						doAjax(POST,contextPath+ "/myflow/doprocess",data, doprocessSuccess);});
	//datagrid初始化
	$('#myFlow').bootstrapTable({
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 5,
		pageNumber : 1,
		pageList : [ 5, 10, 20 ],
		sidePagination : 'server',// 设置为服务器端分页
		columns : [ {
			field : "id",
			title : "任务ID",
			align : "center",
			valign : "middle"
		}, {
			field : "name",
			title : "任务名称",
			align : "center",
			valign : "middle"
		}, {
			field : "createTime",
			title : "创建时间",
			align : "center",
			valign : "middle"
		}, {
			field : 'assignee',
			title : '办理人',
			width : '',
			align : 'center'
		}, {
			field : "operation",
			title : "操做",
			align : "center",
			valign : "middle",
			formatter : function(value, row, index) {
				var s = '<a href="#" onclick="doprocessinit(\'' + row.id + '\')">办理任务</a> ';
				var t = '<a href="#" onclick="viewImage(\'' + row.id + '\')">查看流程图</a> ';
				return s + t;
			}
		}],
		onPageChange : function(size, number) {
			search();
		},
		formatNoMatches : function() {
			return NOT_FOUND_DATAS;
		}
	});
	
	//datagrid初始化
	$('#history').bootstrapTable({
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 5,
		pageNumber : 1,
		pageList : [ 5, 10, 20 ],
		//sidePagination : 'server',// 设置为服务器端分页
		columns : [ {
			field : "time",
			title : "时间",
			align : "center",
			valign : "middle"
		}, {
			field : "userId",
			title : "批注人",
			align : "center",
			valign : "middle"
		}, {
			field : "fullMessage",
			title : "批注信息",
			align : "center",
			valign : "middle"
		} ],
		formatNoMatches : function() {
			return NOT_FOUND_DATAS;
		}
	});
});

$(function(){
	search();
});

function search()
{
	commonGetrowdatas("myFlow",{},contextPath + "/myflow/getmyFlow","commonCallback",true);
}


function doprocessinit(id)
{
	var data={
		taskId:id
	};
	doAjax(POST,contextPath + "/myflow/doprocessinit",data,initSuccess);
	//查询历史
	commonGetrowdatas("history",data,contextPath + "/myflow/gethistory","commonCallback",true);
}
//初始化成功进行赋值
function initSuccess(response)
{
	$("#taskId").val(response.taskId);
	$("#leaveid").val(response.leaveid);
	$("#leaveDays").val(response.leaveDays);
	$("#leaveStart").val(response.leaveStart);
	$("#leaveEnd").val(response.leaveEnd);
	$("#leaveReason").val(response.leaveReason);
	var list = response.list;
	for(var i=0;i<list.length;i++)
	{
		if(response.list[i].name=="批准" || response.list[i].name=="提交")
		{
			$("#agree").text(response.list[i].name);
			if(list.length == 1)
			{
				$("#disagree").text("取消");
			}
		}else {
			
			$("#disagree").text(response.list[i].name);
		}
	}
	$('#processModal').modal({show:true,backdrop: 'static', keyboard: false});
	
}

function doprocessSuccess()
{
	search();
	closemodal();
}

//关闭modal框
function closemodal()
{
	// 关闭前先清空modal画面的form表单
	$('#modalForm').data('bootstrapValidator').resetForm(true);
	$('#processModal').modal('hide');
}
//驳回
function doreject()
{
	var outcome = $("#disagree").text();
	if(outcome == "取消")
	{
		closemodal();
	}else{
		var data = {};
		data.note = $("#leaveNote").val();
		data.id = $("#taskId").val();
		data.outcome = $("#disagree").text();
		doAjax(POST,contextPath+ "/myflow/doprocess",data, doprocessSuccess);
	}
}

function viewImage(id)
{
	$('#imageModal').modal({show:true,backdrop: 'static', keyboard: false});
	$("#viewimage").attr("src",contextPath + "/myflow/getimage?taskId=" + id)
}

function closeviewmodal()
{
	$('#imageModal').modal("hide");
}


