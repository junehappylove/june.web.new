<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志添加</title>
<%@include file="../../common/import.jsp"%>
<script type="text/javascript" src="${ctx}/js/system/log/log_add.js"></script>
</head>
<body>
    <div class="modal-content">
        <form id="modalForm">
            <!--设置该属性，用于判断是新建还是编辑0：编辑，1新建  -->
            <input type="hidden" name="isNew" class="form-control" id="isNew">
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label for="menu_id">菜单ID</label> 
                            <input type="text" name="menu_id" class="form-control" id="menu_id">
                        </div>
                        <div class="form-group">
                            <label for="menu_name">菜单名称</label> 
                            <input type="text" name="menu_name" class="form-control" id="menu_name"> 
                        </div>
                        <div class="form-group">
                            <label for="parent_menu_id">上级菜单</label> 
                            <select class="selectpicker form-control" data-style="btn-success" id="parent_menu_id" name="parent_menu_id">
                                <option value="CUS">自定义</option>
                                <option value="COM">通用</option>
                                <!-- TODO 这里应该能够根据菜单id展示菜单的名称 （树型菜单列表）-->
                            </select>
                            <input type="hidden" name="qxsj_type_msk" class="form-control" id="qxsj_type_msk">
                        </div>
                        <div class="form-group">
                            <label for="menu_url">菜单地址</label> 
                            <input type="text" name="menu_url" class="form-control" id="menu_url">
                        </div>
                        <div class="form-group">
                            <label for="menu_icon">菜单图标</label> 
                            <input type="text" name="menu_icon" class="form-control" id="menu_icon">
                        </div>
                        <div class="form-group">
                            <label for="order_num">排序</label> 
                            <input type="text" name="order_num" class="form-control" id="order_num">
                        </div>
                        <div class="form-group">
                            <label for="menu_state">菜单状态</label> 
                            <select class="selectpicker form-control" data-style="btn-success" id="menu_state" name="menu_state">
                                <option value="Y">正常</option>
                                <option value="N">停用</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="menu_notice">菜单描述</label> 
                            <textarea name="menu_notice" class="form-control" id="menu_notice"></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <june:btn type="cancle"></june:btn>
                <june:btn type="save"></june:btn>
            </div>
        </form>
    </div>
</body>
</html>