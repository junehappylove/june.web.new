//初始化表格
	$(function() {
		$('#MyInfoGrid')
				.datagrid(
						{
						 height:400,
						 nowrap: true,
						 fitColumns:true,
						 pagination:true,//分页控件
						 singleSelect : false,
							columns : [[
							{field:'ck',checkbox:true},
							{ title: '资讯编号', field: 'contentId', width: 80, align: 'center' },
				            { title: '资讯模块id', field: 'channelId',hidden:true, width: 20, align: 'center'},
				            { title: '资讯模块', field: 'channelName', width: 100, align: 'center'},
				            { title: '资讯类别', field: 'contentType', width: 100, align: 'center'},
				            { title: '标题', field: 'title', width: 100, align: 'center'},
				            { title: '摘要', field: 'summary', width: 200, align: 'center'},
				            { title: '资讯状态', field: 'status', width: 80, align: 'center',formatter:function(value,row,index){
				            	var content="";
				            	if(value=="1")
				            	{
				            		content="待提交";
				            	}
				            	else if(value=="2")
				            	{
				            		content="待审核";
				            	}
				            	else if(value=="3")
				            	{
				            		content="审核通过"
				            	}
				            	else if(value=="4")
				            	{
				            		content="审核未通过";
				            	}
				            	else if(value=="5")
				            	{
				            		content="已删除";
				            	}
				            	return content;
				            }},
				            { title: '审批人', field: 'checkUser', width: 80, align: 'center'},
				            { title: '保存时间', field: 'saveTime', width: 150, align: 'center'},
				            { title: '提交时间', field: 'submitTime', width: 150, align: 'center'},
				            { title: '审核时间', field: 'checkTime', width: 150, align: 'center'},
				            { title: '点击数', field: 'view', width: 50, align: 'center'},
				            { title: '评论数', field: 'commentCount', width: 50, align: 'center'},
				            { title: '操作', field: 'action', width: 150, align: 'center',formatter:function(value,row,index){
				            	var t="";
				            	var s="";
				            	var n="";
				            	if(row.status=='1' || row.status=='4')
				            	{
				            		t='<a href="#" style="color:blue" onclick="submitRow(\'' + row.contentId + '\')">提交</a> | ';
				            		s='<a href="#" style="color:blue" onclick="editRow(\'' + row.contentId + '\')">修改</a> | ';
				            		n='<a href="#" style="color:blue" onclick="deleteRow(\'' + row.contentId + '\')">删除</a> | ';
				            	}
				            	var m = '<a href="#" style="color:blue" onclick="checkRow(\'' + row.contentId + '\')">预览</a>'
				            	return t + s + n + m;
				            	
				            }}
				            ]],
						});
		var p = $('#MyInfoGrid').datagrid('getPager');
		$(p).pagination({
			pageSize : 10,//每页显示的记录条数，默认为10
			pageList : [ 5, 10, 15 ],//可以设置每页记录条数的列表
			beforePageText : '第',//页数文本框前显示的汉字
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			onChangePageSize : function(pagesize) {
				searchMyInfo();
			},
			onSelectPage : function(pageNumber, pageSize) {
				searchMyInfo();
			}
		});
	});

//页面加载时初始化表格数据
$(function(){
	searchMyInfo();
});

//检索表格数据
function searchMyInfo()
{
	
	commonGetrowdatas("MyInfoGrid",getSearchData(),contextPath + "/portal/MyInfo/search","commonCallback",true);
}

function getSearchData()
{
	var data = getFormJson("searchInfo");
	data.status=$("#status").combobox('getValue');
	return data;
}
//点击行提交操作进行提交
function submitRow(contentId)
{
	var data={
	    contentId:contentId
	}
	doAjax("post",contextPath + "/portal/MyInfo/submitRow",data,"submitRowSuccess",null,false);
}

function submitRowSuccess()
{
	searchMyInfo();
}

//点击行修改，对行进行编辑，只有保存的才能进行修改，提交后无法进行修改
function editRow(contentId)
{
		top.$('#myDialog').dialog({
		    title: '修改资讯',
		    width: 900,
		    height: 700,
		    closed: false,
		    cache: false,
		    href: contextPath + "/portal/MyInfo/editRow?contentId=" + contentId,
		    modal: true
		});
}
function saveChangeSuccess()
{
	top.$('#myDialog').dialog("close")
	searchMyInfo();
}

function submitChangeSuccess()
{
	top.$('#myDialog').dialog("close")
	searchMyInfo();
}

//删除资讯，只能是未提交的进行操作
function deleteRow(contentId)
{
	var data={
	    contentId:contentId
	}
	doAjax("post",contextPath + "/portal/MyInfo/deleteRow",data,"submitRowSuccess",null,false);
}


//预览
function checkRow(contentId)
{
	top.$('#myDialog').dialog({
	    title: '预览资讯',
	    width: 900,
	    height: 700,
	    closed: false,
	    cache: false,
	    href: contextPath + "/portal/MyInfo/checkRow?contentId=" + contentId,
	    modal: true
	});
}
//批量提交资讯
function batchSubmit()
{
	var selectRows = GetDataGridRows("MyInfoGrid");
	if(selectRows>0)
	{
		var rows = $('#MyInfoGrid').datagrid('getSelections');
		var contentId="";
		for(var i=0;i<rows.length;i++)
		{
			if(rows[i].status == '1' || rows[i].status == '4')
			{
				contentId=contentId + rows[i].contentId + ",";
			}
		}
		if(contentId!="")
		{
			var data={
				contentId:contentId
			}
			doAjax("post",contextPath + "/portal/MyInfo/batchSubmit",data,"submitRowSuccess",null,false);
		}else{
			showOnlyMessage(ERROR,getMessageFromList("SelectSubmitRow",null));
		}
		
	}else{
		showOnlyMessage(ERROR,getMessageFromList("SelectRow",null));
	}
}

//批量删除资讯
function batchDel()
{
	var selectRows = GetDataGridRows("MyInfoGrid");
	if(selectRows>0)
	{
		var rows = $('#MyInfoGrid').datagrid('getSelections');
		var contentId="";
		for(var i=0;i<rows.length;i++)
		{
			if(rows[i].status == '1' || rows[i].status == '4')
			{
				contentId=contentId + rows[i].contentId + ",";
			}
		}
		if(contentId!="")
		{
			var data={
				contentId:contentId
			};
			doAjax("post",contextPath + "/portal/MyInfo/batchDel",data,"submitRowSuccess",null,false);
		}else{
			showOnlyMessage(ERROR,getMessageFromList("SelectSubmitRow",null));
		}
		
	}else{
		showOnlyMessage(ERROR,getMessageFromList("SelectRow",null));
	}
}
