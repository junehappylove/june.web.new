/**
 * 用户信息js
 */
$(function() {
	$('#uploadForm').bootstrapValidator({
		feedbackIcons: {
		    valid: 'glyphicon glyphicon-ok',
		    invalid: 'glyphicon glyphicon-remove',
		    validating: 'glyphicon glyphicon-refresh'
		},
	    fields: {
	    	archiveName: {
	            validators: {
	                notEmpty: {
	                    message: getMessageFromList("ErrorMustInput",['档案名称'])
	                }
	            }
	        }
	      
	    }
	}).on('success.form.bv', function(e) {
		// 校验成功后执行以下方法
	    e.preventDefault();
	    doFileFormAjax("uploadForm","post",contextPath + "/archive/addNew",success);    
	});
	// 表格初始化
	$('#archiveTable')
			.bootstrapTable(
					{
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
								{
									field : "",
									checkbox : true
								},
								{
									field : "id",
									title : "ID",
									align : "center",
									valign : "middle"
								},
								{
									field : "archiveName",
									title : "名称",
									align : "center",
									valign : "middle"
								},
								{
									field : "operateUserId",
									title : "添加人",
									align : "center",
									valign : "middle"
								},
								{
									field : 'operateTime',
									title : '添加时间',
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
										var operation = '<a target="_blank" href="' + contextPath + '/archive/checkPic?id='+ row.id + '">查看图片</a>';
										return operation;
									}
								} 
								],
						onPageChange : function(size, number) {
							searchList();
						},
						formatNoMatches : function() {
							return NOT_FOUND_DATAS;
						}
					});
	initUploadControl();
});

$(function() {
	searchList();
});
function searchList()
{
	commonGetrowdatas("archiveTable", {}, contextPath
			+ "/archive/searchInfo", "commonCallback", true);
}


//新增档案
function addNew(){
	//$('#title').html('');
	//$('#myModal').modal('show');
	$('#myModal').modal({show:true,backdrop: 'static', keyboard: false});
}


//初始化上传控件
function initUploadControl()
{
	$("#uploadInput").fileinput({
		language: "zh",//语言设置
	    maxFileCount: 5,//上传文件的最大个数
	    showPreview : true,//是否显示预览，默认为true
	    allowedFileExtensions: ["jpg", "gif", "png", "txt"],//上传文件类型限制
	    showCaption: true,//是否显示输入框，默认为true
        showRemove: true,//是否显示删除按钮，默认为true
        showUpload: false,//是否显示上传按钮，默认为true
        maxFileSize: 1000
	});
	
	}

//关闭modal画面
function closemodal() {
	// 关闭前先清空modal画面的form表单
	$('#uploadForm').data('bootstrapValidator').resetForm(true);
	$('#myModal').modal('hide');
}

function success()
{
	closemodal();
	searchList();
}

function checkPic()
{
	
}
