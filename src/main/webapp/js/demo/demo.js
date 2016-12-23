$(document).ready(function() {
initDate();//初始化日期控件，如果有form表单日期控件的校验，日期控件的初始化必须在校验初始化之前
$('#defaultForm').bootstrapValidator({
	feedbackIcons: {
	    valid: 'glyphicon glyphicon-ok',
	    invalid: 'glyphicon glyphicon-remove',
	    validating: 'glyphicon glyphicon-refresh'
	},
    fields: {
    	username: {
            validators: {
                notEmpty: {
                    message: getMessageFromList("ErrorMustInput",['用户名'])
                },
                stringLength: {
                    min: 6,
                    max: 30,
                    message: getMessageFromList("ErrorLength2",['username','6','30'])
                }
            }
        },
        email: {
            validators: {
                notEmpty: {
                    message: getMessageFromList("ErrorMustInput",['邮箱'])
                },
                emailAddress: {
                    message: getMessageFromList("ErrorFormat",['邮箱'])
                }
            }
        },
        password: {
            validators: {
                notEmpty: {
                    message: getMessageFromList("ErrorMustInput",['密码'])
                }
            }
        },
        meeting: {
            validators: {
                date: {
                    format: 'YYYY-MM-DD HH:MM',
                    message: 'The value is not a valid date'
                },
                notEmpty: {
                    message: getMessageFromList("ErrorMustInput",['日期'])
                }
            }
        },
        startDate: {
            validators: {
                date: {
                    format: 'YYYY-MM-DD',
                    message: 'The value is not a valid date'
                },
                notEmpty: {
                    message: getMessageFromList("ErrorMustInput",['开始日期'])
                },
                callback: {
                    message: '开始日期不能大于结束日期',
                    callback:function(){
                      return checkEndTime("startDate","endDate");
                    }
                  }
            }
        },
        endDate: {
            validators: {
            	notEmpty: {
                	message: getMessageFromList("ErrorMustInput",['结束日期'])
            	},
                date: {
                    format: 'YYYY-MM-DD',
                    message: 'The value is not a valid date'
                },
                callback: {
                    message: '结束日期不能小于开始日期',
                    callback:function(){
                      return checkEndTime("startDate","endDate");
                    }
                  }
            }
        },
        daterange: {
            validators: {
                notEmpty: {
                    message: 'The gender is required'
                },
                //日期范围正则表达式校验
				regexp: { 
					regexp: /((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\s-\s((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))/, 
					message: "日期范围格式错误！"
				} 
            }
        },
        gender: {
            validators: {
                notEmpty: {
                    message: 'The gender is required'
                }
            }
        },
        languages: {
            validators: {
                notEmpty: {
                    message: 'Please specify at least one language you can speak'
                }
            }
        },
        programs: {
            validators: {
                choice: {
                    min: 2,
                    max: 4,
                    message: 'Please choose 2 - 4 programming languages you are good at'
                }
            }
        }
      
    }
}).on('success.form.bv', function(e) {
	// 校验成功后执行以下方法
    e.preventDefault();
    var $form = $(e.target);
    var bv = $form.data('bootstrapValidator');
    // form表单提交时提示信息，确认执行回调函数submitForm进行ajax form提交，success函数是submitForm的回调函数
    showConfirm(submitForm,"确认提交？",POST,$form.attr('action'), $form.serialize(),success);
}); 

 $("#meeting").bind("change",function(){
 $('#defaultForm')
 // Get the bootstrapValidator instance
 .data('bootstrapValidator')
 // Mark the field as not validated, so it'll be re-validated when the user
 .updateStatus('meeting', 'NOT_VALIDATED', null)
 // Validate the field
 .validateField('meeting');
 });
	// 给日期绑定change事件，日期改变时触发日期校验
	//开始时间和结束时间联合校验时，此change事件需要同时校验开始时间和结束时间
	$("#startDate").bind("change",function(){
		 $('#defaultForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    //将之前的检测结果清除，以便重新进行检测
	    .updateStatus('startDate', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('startDate');
	    
	    $('#defaultForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    // Mark the field as not validated, so it'll be re-validated when the
		// user change date
	    .updateStatus('endDate', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('endDate')
	});
	// 开始日期与结束日期联合校验时结束日期绑定校验事件
	//开始时间和结束时间联合校验时，此change事件需要同时校验开始时间和结束时间
	$("#endDate").bind("change",function(){
		 $('#defaultForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    //将之前的检测结果清除，以便重新进行检测
	    .updateStatus('endDate', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('endDate');
	    $('#defaultForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    //将之前的检测结果清除，以便重新进行检测
	    .updateStatus('startDate', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('startDate');
	});
	
	$("#meeting").bind("change",function(){
		 $('#defaultForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    //将之前的检测结果清除，以便重新进行检测
	    .updateStatus('meeting', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('meeting');
	   
	});
	$("#daterange").bind("change",function(){
		 $('#defaultForm')
	    // Get the bootstrapValidator instance
	    .data('bootstrapValidator')
	    //将之前的检测结果清除，以便重新进行检测
	    .updateStatus('daterange', 'NOT_VALIDATED', null)
	    // Validate the field
	    .validateField('daterange');
	   
	});
	loadTree();//加载树  
	loadajaxtree();//异步加载树子节点
	loadComboTree();//加载combotree
	loadajaxcombotree();//异步加载combotree子节点
	initUploadControl();//初始化上传控件
});

//初始化上传控件
function initUploadControl()
{
	$("#uploadInput").fileinput({
		language: "zh",//语言设置
	    maxFileCount: 2,//上传文件的最大个数
	    showPreview : true,//是否显示预览，默认为true
	    allowedFileExtensions: ["jpg", "gif", "png", "txt"],//上传文件类型限制
	    showCaption: false,//是否显示输入框，默认为true
        showRemove: true,//是否显示删除按钮，默认为true
        showUpload: true,//是否显示上传按钮，默认为true
        maxFileSize: 1000,//文件的最大大小为1000kb
        //initialPreview: [
        //                 '<img src="/images/moon.jpg" class="file-preview-image" alt="The Moon" title="The Moon">',
         //                '<img src="/images/earth.jpg" class="file-preview-image" alt="The Earth" title="The Earth">'
        //             ], //初始化显示的图片
        //overwriteInitial: false,//是否覆盖初始化的图片，与initialPreview配合使用
	});
	
	$("#uploadInput1").fileinput({
		language: "zh",//语言设置
	    maxFileCount: 2,//上传文件的最大个数
	    showPreview : false,//是否显示预览，默认为true
	    allowedFileExtensions: ["jpg", "gif", "png", "txt"],//上传文件类型限制
	    showCaption: true,//是否显示输入框，默认为true
        showRemove: true,//是否显示删除按钮，默认为true
        showUpload: true,//是否显示上传按钮，默认为true
        maxFileSize: 1000,//文件的最大大小为1000kb
        //initialPreview: [
        //                 '<img src="/images/moon.jpg" class="file-preview-image" alt="The Moon" title="The Moon">',
         //                '<img src="/images/earth.jpg" class="file-preview-image" alt="The Earth" title="The Earth">'
        //             ], //初始化显示的图片
        //overwriteInitial: false,//是否覆盖初始化的图片，与initialPreview配合使用
	});
	
	 //上传初始化
	 $("#uploadForm").submit(function(event) {
			event.preventDefault(); //阻止当前提交事件，自行实现，否则会跳转
			//调用上传文件的共通方法
			//ajaxFileUpload(POST,contextPath + "/demo/uploadpicture","uploadInput",uploadSuccess);
			//ajax提交form表单并且表单中有file的调用以下共通方法
			doFileFormAjax("uploadForm",POST,contextPath + "/demo/uploadpicture",success);      
	 });
	 
	  //上传初始化
	 $("#uploadForm1").submit(function(event) {
			event.preventDefault(); //阻止当前提交事件，自行实现，否则会跳转
			//调用上传文件的共通方法
			ajaxFileUpload(POST,contextPath + "/demo/uploadpicture","uploadInput1",uploadSuccess);
			//ajax提交form表单并且表单中有file的调用以下共通方法
			//doFileFormAjax("uploadForm",POST,contextPath + "/demo/uploadpicture",success);      
	 });
	}
function uploadSuccess(response)
{
	$("#uploadForm")[0].reset();
}

// 日期控件初始化
function initDate()
{
	// 日期返回控件初始化
	$('#daterange').daterangepicker({
		autoApply: true,// 时间选择完成后是否自动关闭，如果有时间选择的话不起作用
		singleDatePicker : false,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		//timePicker : true,// 是否有时间选择
		//timePicker24Hour: true,// 时间选择是否是24小时制
		startDate : moment(),// 初始化显示当前日期
		showWeekNumbers: true,// 是否显示第几周的数字
		// 设置格式
		locale : {
			//"format" : "YYYY-MM-DD",// 设置日期格式
			"separator" : " - ",// 设置日期范围的分隔符号，如果不是设置一个日期范围的话此属性不需要设置
		},
		// 限制选择的日期范围，只能选择七天
		dateLimit: {
	        "days": 7
	    },
	    // 设置快捷输入的时间
	    ranges: {
	    	'今日': [moment(), moment()],
			'昨日': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
			'明日': [moment().add(1, 'days'), moment().add(1, 'days')],
			'过去7天': [moment().subtract(6, 'days'), moment()],
			'过去30天': [moment().subtract(29, 'days'), moment()],
			'本月': [moment().startOf('month'), moment().endOf('month')],
			'上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
			'今日开始到本周五': [moment(), moment().endOf('week').subtract(1, 'days')],
			'今日开始到本周日': [moment(), moment().endOf('week').add(1, 'days')],
			'今日开始的一周': [moment(), moment().add(7, 'days')],
			'今日到本月末尾': [moment(), moment().endOf('month')]
		},
	});
	
	// 开始日期的日期控件初始化
	$('#startDate').daterangepicker({
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
	$('#endDate').daterangepicker({
		singleDatePicker : true,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		showWeekNumbers: true,// 是否显示第几周的数字
		// 设置格式
		locale : {
			"format" : "YYYY-MM-DD",// 设置日期格式,默认为YYYY-MM-DD
			// "separator" : " - ",//设置日期范围的分隔符号
		}
	});
	// datetime日期控件初始化
	$('#meeting').daterangepicker({
		singleDatePicker : true,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		showWeekNumbers: true,// 是否显示第几周的数字
		timePicker : true,// 是否有时间选择
		timePicker24Hour: true,// 时间选择是否是24小时制
		// 设置格式
		locale : {
			"format" : "YYYY-MM-DD HH:MM"// 设置日期格式,默认为YYYY-MM-DD
		}
	});
	// 设置日期控件初始化为空，否则默认为当天的日期时间
	$('#meeting').val("");
	$('#startDate').val("");
	$('#endDate').val("");
	$('#daterange').val("");
	
}
//form表单提交
function submitForm(type,url,date,success)
{
	doAjax(type,url,date,success);
}

function success()
{
	alert("提交成功")
	// 重置表单
	$('#defaultForm').data('bootstrapValidator').resetForm(true);
}

// 级联select改变事件
function selectChange(s) 
{ 
	// 调用级联select共通的ajax请求
  doAjax(POST,contextPath+"/demo/selectChange",null,changeSuccess);
}

// 级联select请求成功回调函数
function changeSuccess(response)
{
	$('#select').empty();// 先将级联值的值清除
	$('#select').selectpicker('refresh');
	// 循环加载新的级联
	 $.each(response.rows, function(i, item) {
         $("<option value='" + item.code + "'>" + item.name + "</option>").appendTo($("#select"));                   
      });
     $('#select').selectpicker('refresh');// 动态加载值后需要 refresh
}

function showModal()
{
	$('#myModal').modal('show')
}

// 树初始化
function loadTree()
{
	doAjax(POST,contextPath+"/demo/initTree",null,treeCallback)
}

function treeCallback(response)
{
	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {  
			onClick: clickNode  
		}  
	};
	$.fn.zTree.init($("#treeDemo"), setting, response);
}

// 节点点击事件
function clickNode(event, treeId, treeNode,clickFlag)
{
	alert(treeNode.name + treeNode.id);// 点击直接返回节点对象treeNode
}

function loadajaxtree()
{
	// 获取一级节点
	doAjax(POST,contextPath+"/demo/initAjaxTree",null,ajaxtreeCallback)
}

function ajaxtreeCallback(response)
{
	var setting = {
		async: {
		enable: true,
		url:contextPath+"/demo/getAjaxNode",
		autoParam:["id", "name"] // 参数
		},
		callback: {  
			onClick: clickNode  
		}  
	};
	$.fn.zTree.init($("#ajaxtreeDemo"), setting, response);
}

// 树初始化
function loadComboTree()
{
	doAjax(POST,contextPath+"/demo/initTree",null,combotreeCallback)
}

function combotreeCallback(response)
{
	var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {  
			onClick: clickNode  
		}  
	};
	$.fn.zTree.init($("#combotreeDemo"), setting, response);
}

// ajax加载combotree
function loadajaxcombotree()
{
	// 获取一级节点
	doAjax(POST,contextPath+"/demo/initAjaxTree",null,ajaxcombotreeCallback)
}
function ajaxcombotreeCallback(response)
{
	var setting = {
		check: {
			enable: true
		},
		async: {
			enable: true,
			url:contextPath+"/demo/getAjaxNode",
			autoParam:["id", "name"] // 参数
		},
		callback: {  
			onClick: clickNode  
		}  
	};
	$.fn.zTree.init($("#ajaxcombotreeDemo"), setting, response);
}

function getchecked()
{
	var treeObj = $.fn.zTree.getZTreeObj("combotreeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	alert(nodes.length)

}
// 折叠全部节点
function expandAll()
{
	var treeObj = $.fn.zTree.getZTreeObj("combotreeDemo");
	treeObj.expandAll(false);
}

// 展开全部节点
function collapseAll()
{
	var treeObj = $.fn.zTree.getZTreeObj("combotreeDemo");
	treeObj.expandAll(true);
}



