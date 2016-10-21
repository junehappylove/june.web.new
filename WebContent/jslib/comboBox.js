/*
 * 公用下拉框专用js，包括combotree及combobox 方法名要有区分
 */

//异步加载行政区划树形下拉框公共方法
//<input id="a_districtId" name="districtId" class="easyui-combotree" style="width:156px;"/>
//id 为控件的id，pid为初始根节点，如果需要加载全国则初始化pid=0，否则初始化pid=1
function asynCombotreeDistrictInit(id,pid){
	$('#'+id).combotree({
			url : contextPath + "/ComboBox/ComboTree/initDistrictTree/"+pid,
			onBeforeExpand:function(node){
				$('#'+id).combotree("tree").tree("options").url =
						contextPath + "/ComboBox/ComboTree/initDistrictTree/"+node.id;		
			}
	})
}	

//同步加载行政区划树形下拉框公共方法
function synCombotreeDistrictInit(id,pid){
	$('#'+id).combotree({
			url : contextPath + "/ComboBox/ComboTree/initDistrictTreeSyn/"+pid
//			onBeforeExpand:function(node){
//				$('#'+id).combotree("tree").tree("options").url =
//						contextPath + "/ComboBox/ComboTree/initDistrictTreeSyn/"+node.id;		
//			}
	})
}	


//异步加载部门机构树形下拉框公共方法
//<input id="a_orgId" name="orgId" class="easyui-combotree" style="width:156px;"/>
//id 为控件的id
function asynCombotreeOrgInit(id,orgId){
	$('#'+id).combotree({
			url : contextPath + "/ComboBox/ComboTree/initOrgTree/"+orgId,
			onBeforeExpand:function(node){
				$('#'+id).combotree("tree").tree("options").url =
						contextPath + "/ComboBox/ComboTree/getOrgTree/"+node.id;		
			}
	})
}

//同步加载行政区划树形下拉框公共方法
function synCombotreeOrgInit(id,orgId){
	$('#'+id).combotree({
			url : contextPath + "/ComboBox/ComboTree/initOrgTreeSyn/"+orgId
	})
}
