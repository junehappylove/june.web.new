$(function(){
	//datagrid初始化
	 $('#gnxwlist').datagrid({
		 width:800,
		 height:300,
		 pagination:true,//分页控件
		 singleSelect : true,
		 toolbar: [{
				iconCls: 'icon-add',
				text:'添加',
				handler: newadd
			}],
		 columns: [[
					{ title: 'ID', field: 'id', width: '150', align: 'center' },
		            { title: '作者', field: 'author', width: '150', align: 'center'},
		            { title: '标题', field: 'title', width: '150', align: 'center'},
		            { title: '摘要', field: 'summary', width: '150', align: 'center'},
		            { title: '操作', field: 'operation', width: '150', align: 'center', 
		            	formatter : function(value, row, index) {
						var s = '<a href="'+contextPath+'/gnxw/detail/'+row.id+'" target="_blank">查看</a> ';
						return s;
		            }}
		            ]]
	 });
	    var p = $('#gnxwlist').datagrid('getPager');
	    $(p).pagination({
	        pageSize: 10,//每页显示的记录条数，默认为10
	        pageList: [5,10,15],//可以设置每页记录条数的列表
	        beforePageText: '第',//页数文本框前显示的汉字
	        afterPageText: '页    共 {pages} 页',
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	        onChangePageSize:function(pagesize){
	        	commonGetrowdatas("datagrid",getdata(),contextPath + "/contact/getcontacts.do","commonCallback",true);
	         },
	         onSelectPage:function(pageNumber,pageSize){
	        	 commonGetrowdatas("datagrid",getdata(),contextPath + "/contact/getcontacts.do","commonCallback",true);
	          }
	    });
});

//页面加载时执行该方法，查询表格内容
$(function(){
	commonGetrowdatas("gnxwlist",getdata(),contextPath + "/gnxw/getgnxwlist","commonCallback",true);
});
function getdata()
{
	var data = {};
	return data;
}
function newadd()
{
	var data = {
		title:"test",
		author:"caiyang",
		summary:"summary",
		content:"content"
	}
	doAjax(POST,contextPath + "/gnxw/add",data,"addsuccess",false);
}

function addsuccess()
{
	commonGetrowdatas("gnxwlist",getdata(),contextPath + "/gnxw/getgnxwlist","commonCallback",true);
}


