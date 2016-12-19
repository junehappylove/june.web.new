<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户头像</title>
	<%@include file="../../common/import.jsp"%>
</head>
<body>
    <!-- <div style="width: 800px; margin: 0 auto;"> -->
	<div class="col-md-12">
        <h1 style="text-align: center">头像上传编辑器</h1>
        <div align="center">
            <p id="swfContainer">本组件需要安装Flash Player后才可使用，请从 
				<a href="http://www.adobe.com/go/getflashplayer">这里</a>下载安装。
            </p>
        </div>
	<div class="col-md-12" align="center">
       <!--  <button type="button" class="btn btn-outline btn-default" id="upload">上传</button> -->
    </div>
    </div>
    <script type="text/javascript">
        //控件参数参考：http://www.fullavatareditor.com/api.html#usage
        swfobject.addDomLoadEvent(function () {
            var swf = new fullAvatarEditor("swfContainer", {
                id: 'swf',
                //upload_url: '${ctx}/system/file/user/UploadAction.do?userid=admin',
                upload_url: '${ctx}/system/file/user/upload.do?userid=admin',
                method:'post',
                //src_url: "/samplePictures/Default.jpg",//默认加载的原图片的url
                src_upload: 2,//默认为0；是否上传原图片的选项，有以下值：0:不上传；1:上传；2 :显示复选框由用户选择
                isShowUploadResultIcon: false,//在上传完成时（无论成功和失败），是否显示表示上传结果的图标
                src_size: "2MB",//选择的本地图片文件所允许的最大值，必须带单位，如888Byte，88KB，8MB
                src_size_over_limit: "文件大小超出2MB，请重新选择图片。",//当选择的原图片文件的大小超出指定最大值时的提示文本。可使用占位符{0}表示选择的原图片文件的大小。
                src_box_width: "300",//原图编辑框的宽度
                src_box_height: "300",//原图编辑框的高度
                tab_visible: false,//是否显示选项卡*

                browse_box_width: "300",//图片选择框的宽度
                browse_box_height: "300",//图片选择框的高度

                avatar_sizes: "128*128|64*64|32*32", //|32*32|200*200| //表示一组或多组头像的尺寸。其间用"|"号分隔。
                avatar_sizes_desc: '128*128像素|64*64像素|32*32像素 ',	//|32*32像素 |200*200像素|
                // avatar_field_names: 'img_1|img_2|img_3',	//默认头像域名称：__avatar1,2,3...
            }, function (msg) {
                switch (msg.code) {
                    //case 1: alert("页面成功加载了组件！"); break;
                    //case 2: alert("已成功加载默认指定的图片到编辑面板。"); break;
                    case 3:
                        if (msg.type == 0) {
                            alert("摄像头已准备就绪且用户已允许使用。");
                        }
                        else if (msg.type == 1) {
                            alert("摄像头已准备就绪但用户未允许使用！");
                        }
                        else {
                            alert("摄像头被占用！");
                        }
                        break;
                    case 5:
                        if (msg.type == 0) {
                            if (msg.content.sourceUrl) {
                                alert("原图片已成功保存至服务器，url为：\n" + msg.content.sourceUrl);
                            }
                            alert("头像已成功保存至服务器，url为：\n" + msg.content.avatarUrls.join("\n"));
                        }
                        break;
                }
            }
            );
           /*  document.getElementById("upload").onclick = function () {
                swf.call("upload");
            }; */
        });
    </script>
</body>
</html>