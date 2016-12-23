$(function() {
	initDate();
	//datagrid初始化
	$('#leavegrid').bootstrapTable({
		cache : false,
		striped : true,
		pagination : true,
		toolbar : '#toolbar',
		pageSize : 5,
		pageNumber : 1,
		pageList : [ 5, 10, 20 ],
		clickToSelect : true,
		sidePagination : 'server',// 设置为服务器端分页
		columns : [ {
			field : "leaveId",
			title : "请假编号",
			align : "center",
			valign : "middle"
		}, {
			field : "leaveUser",
			title : "请假人",
			align : "center",
			valign : "middle"
		}, {
			field : "leaveDays",
			title : "请假天数",
			align : "center",
			valign : "middle"
		}, {
			field : 'leaveReason',
			title : '请假原因',
			width : '',
			align : 'center'
		}, {
			field : "leaveStart",
			title : "请假开始时间",
			align : "center",
			valign : "middle"
		}, {
			field : "leaveEnd",
			title : "请假结束时间",
			align : "center",
			valign : "middle"
		}, {
			field : "leaveStatus",
			title : "请假状态",
			align : "center",
			valign : "middle",
			formatter : function(value, row, index) {
				if (row.leaveStatus == '0') {
					var s = "等待办理";
					return s;
				}else if (row.leaveStatus == '1'){
					var s = '审核中';
					return s;
				}else if (row.leaveStatus == '2'){
					var s = '审核通过';
					return s;
				}else{
					var s = '审核未通过';
					return s;
				}
			}
		} ],
		onPageChange : function(size, number) {
			search();
		},
		formatNoMatches : function() {
			return NOT_FOUND_DATAS;
		}
	});

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
							leaveReason : {
								validators : {
									notEmpty : {
										message : getMessageFromList(
												"ErrorMustInput", [ '请假原因' ])
									},
									stringLength : {
										min : 1,
										max : 100,
										message : getMessageFromList(
												"ErrorLength2", [ '请假原因', '1',
														'100' ])
									}
								}
							},
							leaveStart : {
								validators : {
									notEmpty : {
										message : getMessageFromList(
												"ErrorMustInput", [ '请假开始时间' ])
									},
									date: {
					                    format: 'YYYY-MM-DD',
					                    message: getMessageFromList("ErrorFormat",[ '请假开始时间' ])
					                },
					                callback: {
					                    message: '开始日期不能大于结束日期',
					                    callback:function(){
					                      return checkEndTime("leaveStart","leaveEnd");
					                    }
					                  }
								}
							},
							leaveEnd : {
								validators : {
									notEmpty : {
										message : getMessageFromList(
												"ErrorMustInput", [ '请假结束时间' ])
									},
									date: {
					                    format: 'YYYY-MM-DD',
					                    message: getMessageFromList("ErrorFormat",[ '请假结束时间' ])
					                },
					                callback: {
					                    message: '结束日期不能小于开始日期',
					                    callback:function(){
					                      return checkEndTime("leaveStart","leaveEnd");
					                    }
					                  }
								}
							},
							leaveDays : {
								validators : {
									notEmpty : {
										message : getMessageFromList(
												"ErrorMustInput", [ '请假天数' ])
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
						var data = getFormJson("modalForm");
						data.leaveDays = $("#leaveDays").val();
						var $form = $(e.target);
						
							doAjax(
									POST,
									contextPath
											+ "/leave/saveleave",
											data, saveSuccess);
						

					});

});

function saveSuccess()
{
	search();
	closemodal()
}

$(function() {
	search();
});

//日期控件初始化
function initDate()
{
	$('#leaveStartConditiron').daterangepicker({
		singleDatePicker : true,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		showWeekNumbers: true,// 是否显示第几周的数字
		// 设置格式
		locale : {
			"format" : "YYYY-MM-DD",// 设置日期格式,默认为YYYY-MM-DD
			// "separator" : " - ",//设置日期范围的分隔符号
		}
	});
	$('#leaveEndConditiron').daterangepicker({
		singleDatePicker : true,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		showWeekNumbers: true,// 是否显示第几周的数字
		// 设置格式
		locale : {
			"format" : "YYYY-MM-DD",// 设置日期格式,默认为YYYY-MM-DD
			// "separator" : " - ",//设置日期范围的分隔符号
		}
	});
	// 开始日期的日期控件初始化
	$('#leaveStart').daterangepicker({
		singleDatePicker : true,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		showWeekNumbers: true,// 是否显示第几周的数字
		// 设置格式
		locale : {
			"format" : "YYYY-MM-DD",// 设置日期格式,默认为YYYY-MM-DD
			// "separator" : " - ",//设置日期范围的分隔符号
		}
	});
	
	// 结束日期的日期控件初始化
	$('#leaveEnd').daterangepicker({
		singleDatePicker : true,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		showWeekNumbers: true,// 是否显示第几周的数字
		// 设置格式
		locale : {
			"format" : "YYYY-MM-DD",// 设置日期格式,默认为YYYY-MM-DD
			// "separator" : " - ",//设置日期范围的分隔符号
		}
	});
	$('#leaveStartConditiron').val("");
	$('#leaveEndConditiron').val("");
	$('#leaveStart').val("");
	$('#leaveEnd').val("");
	// 给日期绑定change事件，日期改变时触发日期校验
	//开始时间和结束时间联合校验时，此change事件需要同时校验开始时间和结束时间
	$("#leaveStart").bind("change",function(){
		 $('#modalForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    //将之前的检测结果清除，以便重新进行检测
	    .updateStatus('leaveStart', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('leaveStart');
	    
	    $('#modalForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    // Mark the field as not validated, so it'll be re-validated when the
		// user change date
	    .updateStatus('leaveEnd', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('leaveEnd');
	    var start = $("#leaveStart").val();//获取开始日期
	    var end = $("#leaveEnd").val();//获取结束日期
	    if(end != null && end != "")
    		{
    			var diff = DateDiff(start,end)
    			$("#leaveDays").val(diff+1)
    		}
	});
	// 开始日期与结束日期联合校验时结束日期绑定校验事件
	//开始时间和结束时间联合校验时，此change事件需要同时校验开始时间和结束时间
	$("#leaveEnd").bind("change",function(){
		 $('#modalForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    //将之前的检测结果清除，以便重新进行检测
	    .updateStatus('leaveEnd', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('leaveEnd');
	    $('#modalForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    //将之前的检测结果清除，以便重新进行检测
	    .updateStatus('leaveStart', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('leaveStart');
	    var start = $("#leaveStart").val();//获取开始日期
	    var end = $("#leaveEnd").val();//获取结束日期
	    if(start != null && start != "")
    		{
    			var diff = DateDiff(start,end)
    			$("#leaveDays").val(diff+1)
    		}
	});
}

//查询请假记录
function search() {
	commonGetrowdatas("leavegrid", {}, contextPath + "/leave/getleave",
			"commonCallback", true);
}

function applyLeave() {
	$('#applyLeaveModal').modal({
		show : true,
		backdrop : 'static',
		keyboard : false
	});
}

function closemodal() {
	$('#modalForm').data('bootstrapValidator').resetForm(true);
	$('#applyLeaveModal').modal('hide');
}

//查询按钮压下触发事件
function searchLeaveInfo()
{
	var data = getFormJson("searchForm");
	commonGetrowdatas("leavegrid", data, contextPath + "/leave/getleave",
	"commonCallback", true);
}
