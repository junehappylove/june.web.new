/*
 * 消息文件定义js
 */

/** 定义全局消息列表 */
var MSG_LIST = new Array();

/**
 * 获取js消息
 * 
 * @param msgid
 * @param msgParams
 * @returns
 */
function $message(msgid, msgParams) {
	var msg = MSG_LIST[msgid];
	if ($.trim(msg) == "") {
		msg = "[" + msgid + "]" + "该消息不存在!";
		return msg;
	}
	msgParams = msgParams == undefined ? null : msgParams;
	if (msgParams != null) {
		for (var i = 0; i < msgParams.length; i++) {
			msg = msg.replace("{" + i + "}", msgParams[i]);
		}
	}
	return msg;
}

// 载入消息数据
$(document).ready(function() {
	MSG_LIST["ErrorMustInput"] = "请输入{0}。";
	MSG_LIST["ErrorMustSelect"] = "请选择{0}。";
	MSG_LIST["ErrorInteger"] = "{0}请输入正整数。";
	MSG_LIST["ErrorFormat"] = "{0}格式输入错误。";
	MSG_LIST["ErrorDateCopmare"] = "{0}大于{1}。";
	MSG_LIST["ErrorTelphone"] = "电话号码格式输入错误。";
	MSG_LIST["ErrorInputAlphaMark"] = "{0}只能输入半角英数字和符号。";
	MSG_LIST["ErrorLength"] = "{0}请输入{1}字以内。";
	MSG_LIST["ErrorLength2"] = "{0}请输入{1}字以上，{2}字以内。";
	MSG_LIST["Query"] = "是否进行查询？";
	MSG_LIST["Deleteuser"] = "是否要删除该用户？";
	MSG_LIST["DeleteParentmenu"] = "是否要删除该父菜单？";
	MSG_LIST["SelectParentMenu"] = "请先选择父菜单。";
	MSG_LIST["DeleteAuthority"] = "是否要删除该权限？";
	MSG_LIST["Deleteleave"] = "是否要删除此次请假？";
	MSG_LIST["Errornum"] = "{0}请输入数字。";
	MSG_LIST["ErrorExceleFile"] = "请选择excel文件。";
	MSG_LIST["DeleteVacation"] = "是否要删除该用户的假期信息？";
	MSG_LIST["editmultiselectError"] = "编辑时只能选中一行，请重新选择。";
	MSG_LIST["ErrorImageType"] = "请选择图片文件。"
	MSG_LIST["SubmitContent"] = "是否确认提交资讯，提交后将无法进行更改？";
	MSG_LIST["SelectSubmitRow"] = "请选择资讯状态是【待提交】或【审核未通过】的行进行提交。";
	MSG_LIST["DeleteRubbishDistInfo"] = "是否确认删除选中的垃圾分布信息？";
	MSG_LIST["showEChartsMultiSelectError"] = "查看统计图时只能选中一行，请重新选择。";
	MSG_LIST["ManageUserRoleMultiSelectError"] = "人员角色分配时只能选中一行，请重新选择。";
	MSG_LIST["lockUserConfirm"] = "是否确认锁定选中的用户信息？";
	MSG_LIST["unlockUserConfirm"] = "是否确认解锁选中的用户信息？";
	MSG_LIST["resetUserPasswordConfirm"] = "是否确认重置选中的用户的密码？";
	MSG_LIST["IdCardIllegal"] = "身份证号不合法！";
	MSG_LIST["DeleteMenuSelect"] = "请选择要删除的菜单！";
	MSG_LIST["DeleteMenuConfirm"] = "是否确认删除选中的菜单？<br>注意：删除时会将其连同其子菜单一并删除";
	MSG_LIST["assignMenumultiselectError"] = "分配菜单时只能选中一行！";
	MSG_LIST["assignUsermultiselectError"] = "分配用户时只能选中一行！";
	MSG_LIST["selectRoleError"] = "请选择要分配的角色！";
	MSG_LIST["assignRolemultiselectError"] = "分配角色时只能选中一行！";
	MSG_LIST["ErrorNoSelectEdit"] = "请先选中要编辑的行。";
	MSG_LIST["ErrorSelectMultiEdit"] = "编辑时只能选中一行。";
	MSG_LIST["ErrorSelectNoDelete"] = "请选择要删除的行。";
	MSG_LIST["ErrorSelect2Operate"] = "请选择要操作的行。";
	MSG_LIST["ErrorNoSelectAssign"] = "请先选中要分配的角色。";
	MSG_LIST["ErrorSelectMultiAssign"] = "为用户分配角色时只能选中一行。";
	MSG_LIST["InfoOfSave"] = "是否确认要添加?";
	MSG_LIST["InfoOfDelete"] = "是否确认要删除?";
	MSG_LIST["WarnNotAuth"]="请为其子菜单授权";
});