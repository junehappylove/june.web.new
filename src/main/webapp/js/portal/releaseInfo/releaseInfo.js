
//保存或者修改资讯
function saveInfo()
{
	 var stemTxt=CKEDITOR.instances.content.document.getBody().getText();//获取ckeditor的纯文本内容
	 var stem = CKEDITOR.instances.content.getData();//获取ckeditor的html内容
	 alert(stem);
//	if(check())
//	{
//		var data = getFormJson("releaseInfo");
//		var fckeditor = FCKeditorAPI.GetInstance('content_text');
//		var str = fckeditor.GetHTML(); //这就是内容
//		data.contentText= str;
//		doAjax(POST,contextPath + "/portal/ReleaseInfo/saveContent",data,"savesuccess",null,false);
//	}
}

//回调函数
function savesuccess(response)
{
	$("#contentId").val(response.contentId);
	//$("#saveInfo").val("修改");
}

//提交资讯
function submitInfo()
{
	
		var data = getFormJson("releaseInfo");
		var value = getFckContent('content_text');
		data.contentText= value;
		doAjax(POST,contextPath + "/portal/ReleaseInfo/submitContent",data,"submitsuccess",null,false);
	
}

//提交资讯前弹出确认消息
function showConfirmbebeforeSubmit()
{
	if(check())
	{
		showConfirm("submitInfo",getMessageFromList("SubmitContent",null),null,null,contextPath + "/portal/ReleaseInfo/submitContent",submitsuccess,false);
	}

}

function submitsuccess(response)
{
	//提交后清空页面数据
	var data = $('#channel').combobox('getData');
	 $("#channel").combobox('select',data[0].value);
	 $("#commentState").prop("checked",true);
	 $("#commentState").attr("value","true");
	 $("#title").textbox('setValue','');//赋值
	 $("#summary").textbox('setValue','');//赋值
	 clearFckContent("content_text");
}

function check()
{
	checkMustInput("title","标题");
	checkMustInput("summary","摘要");
	checkFckEditor("content_text","资讯内容");
	maxLength("title",255,"标题","255");
	maxLength("summary",255,"摘要","255");
	return showMessage(ERROR);
}