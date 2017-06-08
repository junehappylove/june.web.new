/**
 * 中科方德软件有限公司<br>
 * june.web.new:js/system/log.log_add.js
 * 日期：2017年6月6日
 */

$(function(){
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
		doAjax(POST, api_saveNew, $form.serialize(), saveSuccess);
	});
});


function saveSuccess(response) {
	var errType = response.errType;
	if (errType != ERROR) {
		closemodal();
		searchInfo();
	} else {
		$("#saveBtn").removeAttr("disabled");
	}
}


//查询表格信息
function searchInfo() {
	var data = getFormJson("searchForm");// 获取查询条件
	commonRowDatas("menuInfoTable", data, api_getPagedList, "commonCallback", true);
}


//关闭modal画面
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