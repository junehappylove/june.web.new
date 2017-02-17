$(function(){
	initDate();
	//datagrid初始化
	$('#quartzList').bootstrapTable({
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 5,
		pageNumber : 1,
		pageList : [ 5, 10, 20 ],
		sidePagination : 'server',// 设置为服务器端分页
		toolbar : '#toolbar',
		columns : [ {
			field : "schedName",
			title : "任务名称",
			align : "center",
			valign : "middle"
		}, {
			field : "triggerName",
			title : "trigger名称",
			align : "center",
			valign : "middle"
		}, {
			field : "triggerGroup",
			title : "trigger分组",
			align : "center",
			valign : "middle"
		}, {
			field : 'jobName',
			title : 'job名称',
			align : "center",
			valign : "middle"
		}, {
			field : "jobGroup",
			title : "job分组",
			align : "center",
			valign : "middle"
		}, {
			field : "nextFireTime",
			title : "下次执行时间",
			align : "center",
			valign : "middle"
			}, {
			field : "prevFireTime",
			title : "上次执行时间",
			align : "center",
			valign : "middle"
		}, {
			field : "priority",
			title : "优先级",
			align : "center",
			valign : "middle"
		}, {
			field : "triggerState",
			title : "trigger状态",
			align : "center",
			valign : "middle",
			formatter : function(value, row, index) {
				var status = "";
				if(row.triggerState == "WAITING")
				{
					status = "等待";
				}else if(row.triggerState == "PAUSED")
				{
					status = "暂停";
				}else if(row.triggerState == "ACQUIRED")
				{
					status = "正常执行";
				}else if(row.triggerState == "BLOCKED")
				{
					status = "阻塞";
				}else if(row.triggerState == "ERROR")
				{
					status = "错误";
				}else if(row.triggerState == "PAUSED_BLOCKED")
				{
					status = "暂停阻塞";
				}else if(row.triggerState == "COMPLETE")
				{
					status = "完成";
				}
				return status;
			}
		}, {
			field : "startTime",
			title : "开始时间",
			align : "center",
			valign : "middle"
		}, {
			field : "endTime",
			title : "结束时间",
			align : "center",
			valign : "middle"
		}, {
			field : "operation",
			title : "操做",
			align : "center",
			valign : "middle",
			formatter : function(value, row, index) {
				if(row.triggerState != "COMPLETE")
				{
					var s = '<a href="#" onclick="pause(\'' + row.triggerName + '\',\'' + row.triggerGroup + '\',\'' + row.triggerState + '\')">暂停</a>';
					var t = '<a href="#" onclick="resume(\'' + row.triggerName + '\',\'' + row.triggerGroup + '\',\'' + row.triggerState + '\')">恢复</a>';
					var m = '<a href="#" onclick="removeTrigger(\'' + row.triggerName + '\',\'' + row.triggerGroup + '\',\'' + row.triggerState + '\')">删除</a>';
					var n = '<a href="#" onclick="runTrigger(\'' + row.jobName + '\',\'' + row.jobGroup + '\')">立即执行</a>';
					return s +"|"+ t + "|" + m + "|" + n;
				}else{
					return "-";
				}
				
			}
		}],
		onPageChange : function(size, number) {
		
		},
		formatNoMatches : function() {
			return NOT_FOUND_DATAS;
		}
	});
	
	$('#modalForm')
			.bootstrapValidator(
					{
						feedbackIcons : {
							valid : 'glyphicon glyphicon-ok',
							invalid : 'glyphicon glyphicon-remove',
							validating : 'glyphicon glyphicon-refresh'
						},
						fields : {
							triggerName : {
								validators : {
									notEmpty : {
										message : $message(
												"ErrorMustInput", [ 'trigger名称' ])
									}
								}
							},
							startTime : {
								validators : {
									notEmpty : {
										message : $message(
												"ErrorMustInput", [ '开始执行时间' ])
									},
									date: {
					                    format: 'YYYY-MM-DD HH:mm:ss',
					                    message: $message("ErrorFormat",['开始执行时间'])
					                },
					                callback: {
					                    message: '开始日期不能大于结束日期',
					                    callback:function(){
					                      return checkEndTime("startTime","endTime");
					                    }
					                  }
								}
							},
							endTime : {
								validators : {
									date: {
					                    format: 'YYYY-MM-DD HH:mm:ss',
					                    message: $message("ErrorFormat",['执行结束时间'])
					                },
					                callback: {
					                    message: '结束日期不能小于开始日期',
					                    callback:function(){
					                      return checkEndTime("startTime","endTime");
					                    }
					                  }
								}
							},
							repeat : {
								validators : {
				                 regexp: { 
									regexp: /^[-]{0,1}[0-9]{1,}$/, 
									message:  $message("Errornum",['执行次数'])
									} 
								}
							},
							interval : {
								validators : {
				                 regexp: { 
									regexp: /^[-]{0,1}[0-9]{1,}$/, 
									message:  $message("Errornum",['执行间隔'])
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
						var $form = $(e.target);
						var data = getFormJson("modalForm");
						data.jobName = $('#jobName option:selected').val();
						doAjax(POST,contextPath+ "/quatrz/addsimpletrigger",data, addsimplesuccess);
				});
				$('#cronModalForm')
				.bootstrapValidator(
						{
							feedbackIcons : {
								valid : 'glyphicon glyphicon-ok',
								invalid : 'glyphicon glyphicon-remove',
								validating : 'glyphicon glyphicon-refresh'
							},
							fields : {
								triggerName : {
									validators : {
										notEmpty : {
											message : $message(
													"ErrorMustInput", [ 'trigger名称' ])
										}
									}
								},
								cronExpress : {
									validators : {
										notEmpty : {
											message : $message(
													"ErrorMustInput", [ 'cron表达式' ])
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
							var $form = $(e.target);
							var data = getFormJson("cronModalForm");
							data.jobName = $('#cronModalForm #jobName option:selected').val();
							doAjax(POST,contextPath+ "/quatrz/addcrontrigger",data, addcronsuccess);
					});

});

//添加simpletrigger回调函数
function addsimplesuccess()
{
	search();
	closemodal();
}

//添加crontrigger回调函数
function addcronsuccess()
{
	search();
	closecronmodal()
}

$(function(){
	search();
});
function search()
{
	commonRowDatas("quartzList",{},contextPath + "/quatrz/getquartzlist","commonCallback",true);
}

function pause(name,group,state)
{
	if(state=='PAUSED'){
		showOnlyMessage(INFO, "该Trigger己经暂停！");
		return;
	}
	var data = {
		triggerName:name,
		triggerGroup:group
	}
	doAjax(POST,contextPath+ "/quatrz/pause",data, callback);
}

function callback()
{
	search();
}

function resume(name,group,state)
{
	if(state=='ACQUIRED'){
		showOnlyMessage(ERROR, "该Trigger已经启动！");
		return;
	}
	var data = {
		triggerName:name,
		triggerGroup:group
	}
	doAjax(POST,contextPath+ "/quatrz/resume",data, callback);
}

function removeTrigger(name,group,state)
{
	var data = {
		triggerName:name,
		triggerGroup:group
	}
	doAjax(POST,contextPath+ "/quatrz/remove",data, callback);
}

function addsimple()
{
	doAjax(POST,contextPath+ "/quatrz/getinitdata",{}, initcallback);
	
}
function initcallback(response)
{
	$('#jobName').empty();// 先将级联值的值清除
	$('#jobName').selectpicker('refresh');
	// 循环加载新的级联
	 $.each(response.rows, function(i, item) {
         $("<option value='" + item.jobName + "'>" + item.jobName + "</option>").appendTo($("#jobName"));                   
      });
     $('#jobName').selectpicker('refresh');// 动态加载值后需要 refresh
     
	$('#simpletriggermodal').modal({
		show : true,
		backdrop : 'static',
		keyboard : false
	});
}

function closemodal()
{
	$('#modalForm').data('bootstrapValidator').resetForm(true);
	$('#simpletriggermodal').modal('hide');
}

function initDate()
{
	// 开始日期的日期控件初始化
	$('#startTime').daterangepicker({
		singleDatePicker : true,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		showWeekNumbers: true,// 是否显示第几周的数字
		timePicker : true,// 是否有时间选择
		timePicker24Hour: true,// 时间选择是否是24小时制
		// 设置格式
		locale : {
			"format" : "YYYY-MM-DD HH:mm:ss",// 设置日期格式,默认为YYYY-MM-DD
			// "separator" : " - ",//设置日期范围的分隔符号
		}
	});
	// 开始日期的日期控件初始化
	$('#endTime').daterangepicker({
		singleDatePicker : true,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		showWeekNumbers: true,// 是否显示第几周的数字
		timePicker : true,// 是否有时间选择
		timePicker24Hour: true,// 时间选择是否是24小时制
		// 设置格式
		locale : {
			"format" : "YYYY-MM-DD HH:mm:ss",// 设置日期格式,默认为YYYY-MM-DD
			// "separator" : " - ",//设置日期范围的分隔符号
		}
	});
	
	$('#startTime').val("");
	$('#endTime').val("");
	
	// 给日期绑定change事件，日期改变时触发日期校验
	//开始时间和结束时间联合校验时，此change事件需要同时校验开始时间和结束时间
	$("#startTime").bind("change",function(){
		 $('#modalForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    //将之前的检测结果清除，以便重新进行检测
	    .updateStatus('startTime', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('startTime');
	    
	    $('#modalForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    // Mark the field as not validated, so it'll be re-validated when the
		// user change date
	    .updateStatus('endTime', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('endTime');
	});
	// 开始日期与结束日期联合校验时结束日期绑定校验事件
	//开始时间和结束时间联合校验时，此change事件需要同时校验开始时间和结束时间
	$("#endTime").bind("change",function(){
		 $('#modalForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    //将之前的检测结果清除，以便重新进行检测
	    .updateStatus('endTime', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('endTime');
	    $('#modalForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    //将之前的检测结果清除，以便重新进行检测
	    .updateStatus('startTime', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('startTime');
	});
}

function addcron()
{
	doAjax(POST,contextPath+ "/quatrz/getinitdata",{}, croncallback);
}

function closecronmodal()
{
	$('#cronModalForm').data('bootstrapValidator').resetForm(true);
	$('#crontriggermodal').modal("hide");
}

function croncallback(response)
{
	$('#cronModalForm #jobName').empty();// 先将级联值的值清除
	$('#cronModalForm #jobName').selectpicker('refresh');
	// 循环加载新的级联
	 $.each(response.rows, function(i, item) {
         $("<option value='" + item.jobName + "'>" + item.jobName + "</option>").appendTo($("#cronModalForm #jobName"));                   
      });
     $('#cronModalForm #jobName').selectpicker('refresh');// 动态加载值后需要 refresh
     
	$('#crontriggermodal').modal({
		show : true,
		backdrop : 'static',
		keyboard : false
	});
}

function runTrigger(jobName,jobGroup)
{
	var data={
	    jobName:jobName,
	    jobGroup:jobGroup
	}
	//runTrigger
	doAjax(POST,contextPath+ "/quatrz/runTrigger",data, runtriggersuccess);
}
function runtriggersuccess(response)
{
}