//初始化表格
	$(function() {
		$('#CheckInfoGrid')
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
				            	var content="待审核";
				            	return content;
				            }},
				            { title: '保存时间', field: 'saveTime', width: 150, align: 'center'},
				            { title: '提交时间', field: 'submitTime', width: 150, align: 'center'},
				            { title: '审核时间', field: 'checkTime', width: 150, align: 'center'},
				            { title: '操作', field: 'action', width: 150, align: 'center',formatter:function(value,row,index){
				            	t='<a href="#" style="color:blue" onclick="checkPass(\'' + row.contentId + '\')">通过审核</a> | ';
				            	s='<a href="#" style="color:blue" onclick="checkBack(\'' + row.contentId + '\')">驳回</a> | ';
				            	n='<a href="#" style="color:blue" onclick="checkView(\'' + row.contentId + '\')">预览</a>';
				            	return t + s + n;
				            	
				            }}
				            ]],
						});
		var p = $('#CheckInfoGrid').datagrid('getPager');
		$(p).pagination({
			pageSize : 10,//每页显示的记录条数，默认为10
			pageList : [ 5, 10, 15 ],//可以设置每页记录条数的列表
			beforePageText : '第',//页数文本框前显示的汉字
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			onChangePageSize : function(pagesize) {
				searchCheckInfo();
			},
			onSelectPage : function(pageNumber, pageSize) {
				searchCheckInfo();
			}
		});
	});
	
	
//页面加载时初始化表格数据
$(function(){
	searchCheckInfo();
});

//检索表格数据
function searchCheckInfo()
{
	commonGetrowdatas("CheckInfoGrid",getSearchData(),contextPath + "/portal/CheckInfo/search","commonCallback",true);
}

function getSearchData()
{
	var data = getFormJson("searchInfo");
	return data;
}

//审核通过
function checkPass(contentId)
{
	var data={
	    contentId:contentId
	}
	doAjax("post",contextPath + "/portal/CheckInfo/checkPass",data,"checkPassSuccess",null,false);
}

function checkPassSuccess()
{
	searchCheckInfo();
}

//驳回
function checkBack(contentId)
{
	var data={
	    contentId:contentId
	}
	doAjax("post",contextPath + "/portal/CheckInfo/checkBack",data,"checkPassSuccess",null,false);
}

//预览
function checkView(contentId)
{
	top.$('#myDialog').dialog({
	    title: '预览资讯',
	    width: 900,
	    height: 700,
	    closed: false,
	    cache: false,
	    href: contextPath + "/portal/CheckInfo/checkView?contentId=" + contentId,
	    modal: true
	});
}

function batchCheckPass()
{
	var selectRows = GetDataGridRows("CheckInfoGrid");
	if(selectRows>0)
	{
		var rows = $('#CheckInfoGrid').datagrid('getSelections');
		var contentId="";
		for(var i=0;i<rows.length;i++)
		{
			contentId=contentId + rows[i].contentId + ",";
		}
		var data={
			contentId:contentId
		}
		doAjax("post",contextPath + "/portal/CheckInfo/batchCheckPass",data,"checkPassSuccess",null,false);
	}else{
		showOnlyMessage("error",getMessageFromList("SelectRow",null))
	}
}

function batchCheckBack()
{
	var selectRows = GetDataGridRows("CheckInfoGrid");
	if(selectRows>0)
	{
		var rows = $('#CheckInfoGrid').datagrid('getSelections');
		var contentId="";
		for(var i=0;i<rows.length;i++)
		{
			contentId=contentId + rows[i].contentId + ",";
		}
		var data={
			contentId:contentId
		}
		doAjax("post",contextPath + "/portal/CheckInfo/batchCheckBack",data,"checkPassSuccess",null,false);
	}else{
		showOnlyMessage("error",getMessageFromList("SelectRow",null))
	}
}

