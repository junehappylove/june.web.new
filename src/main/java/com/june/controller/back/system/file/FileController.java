/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.controller.back.system.file.FileController.java
 * 日期:2016年12月18日
 */
package com.june.controller.back.system.file;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.june.common.BaseController;
import com.june.common.Constants;
import com.june.common.annotation.MethodLog;
import com.june.dto.back.system.base.UserInfoDto;
import com.june.dto.back.system.file.BaseFile;
import com.june.dto.back.system.file.FileAppDTO;
import com.june.dto.back.system.file.FileDTO;
import com.june.utility.MD5Util;
import com.june.utility.MessageUtil;

/**
 * FileController <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月18日 下午9:36:21
 * @version 1.1.0
 */
@Controller
@RequestMapping("/system/file")
public class FileController extends BaseController<FileDTO> {

	@RequestMapping("/")
	@MethodLog(module = "系统文件管理", remark = "页面初始化", operateType = Constants.OPERATE_TYPE_SEARCH)
	public ModelAndView init(HttpServletRequest request) {
		return initPage(request,"system/file/file");
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @date 2016年12月20日 上午8:44:34
	 * @writer junehappylove
	 */
	@RequestMapping("/user/head")
	@MethodLog(module = "系统文件管理", remark = "修改头像页面", operateType = Constants.OPERATE_TYPE_SEARCH)
	public ModelAndView head(HttpServletRequest request) {
		return initPage(request,"system/file/head");
	}
	
	/**
	 * 用户头像上传修改
	 * @param request
	 * @param response
	 * @throws IllegalStateException
	 * @throws IOException
	 * @date 2016年12月20日 上午8:44:39
	 * @writer junehappylove
	 */
	@RequestMapping("/user/upload.do")
	public void upload(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		Result result = new Result();
		result.avatarUrls = new ArrayList<>();
		result.success = false;
		result.msg = "Failure!";
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();
			// 定义一个变量用以储存当前头像的序号
			int avatarNumber = 1;
			// 取服务器时间+8位随机码作为部分文件名，确保文件名无重复。
			String fileName = getFileName();
			// 基于原图的初始化参数
			String initParams = "";
			String file_filter = null;//文件过滤字段
			BaseFile baseFileTemp = null;//用于记录64*64的头像文件
			// 遍历表单域
			while (iter.hasNext()) {
				String fieldName = iter.next().toString();
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(fieldName);
				// 是否是原始图片 file 域的名称（默认的 file域的名称是__source，可在插件配置参数中自定义。参数名：src_field_name）
				Boolean isSourcePic = fieldName.equals("__source");
				// 文件名，如果是本地或网络图片为原始文件名（不含扩展名）、如果是摄像头拍照则为 *FromWebcam
				// String name = fileItem.getName();
				// 当前头像基于原图的初始化参数（即只有上传原图时才会发送该数据，且发送的方式为POST），
				// 用于修改头像时保证界面的视图跟保存头像时一致，提升用户体验度。
				// 修改头像时设置默认加载的原图url为当前原图url+该参数即可，可直接附加到原图url中储存，不影响图片呈现。
				if(fieldName.equals("__initParams")){
					// TODO 
					logger.debug("__initParams:"+initParams);
				}
				// 如果是原始图片 file 域的名称或者以默认的头像域名称的部分“__avatar”打头
				//(默认的头像域名称：__avatar1,2,3...，可在插件配置参数中自定义，参数名：avatar_field_names)  
				else if(isSourcePic || fieldName.startsWith("__avatar")){
					String file_name = "jsp_avatar" + avatarNumber + "_" + fileName;
					String virtualPath = "/upload/" + file_name + ".jpg";
					// 原始图片（默认的 file域的名称是__source，可在插件配置参数中自定义。参数名：src_field_name）
					if (isSourcePic) {
						result.sourceUrl = virtualPath = "/upload/jsp_source_" + fileName + ".jpg";//注意这句的写法
						file_name = "jsp_source_" + fileName;
					}else{// 头像图片（默认的 file 域的名称：__avatar1,2,3...，可在插件配置参数中自定义，参数名：avatar_field_names）
						result.avatarUrls.add(virtualPath);
						avatarNumber++;
					}
					
					if (file != null) {
						String path = getRealPath(request) + virtualPath.replace("/", "\\");
						//file.transferTo(image);
						file.getInputStream();
						//保存一张图片后就要向数据库中添加一条文件记录
						String file_md5 = MD5Util.getFileMD5String(file.getInputStream());
						file_filter = file_filter == null ? file_md5 : file_filter;// 注意这里的文件过滤条件设置
						//判断数据库中是否存在这个图片
						BaseFile baseFile = new BaseFile();
						baseFile.setFile_md5(file_md5);
						BaseFile temp = baseFileService.getDtoById(baseFile);
						if(temp == null){
							// SpringMVC 上传这张图片
							File image = new File(path);
							file.transferTo(image);
							BufferedImage bufferedImage = ImageIO.read(image); 
							int file_height = bufferedImage.getWidth();
							int file_width = bufferedImage.getHeight();
							long file_size = file.getSize();
							baseFile.setFile_height(file_height);
							baseFile.setFile_loc("/upload/");
							baseFile.setFile_md5(file_md5);
							baseFile.setFile_filter(file_filter);//文件过滤设置
							baseFile.setFile_name(file_name);
							baseFile.setFile_size(BigDecimal.valueOf(file_size));
							baseFile.setFile_time(0d);
							baseFile.setFile_type(".jpg");
							baseFile.setFile_width(file_width);
							
							baseFileService.addDto(baseFile);
							if(file_height==64&&file_width==64&&file_name.contains("avatar")){
								baseFileTemp = baseFile;
							}
						}else{
							//数据存在的话不需要操作了(也不需要去更新)，业务中关注的去更新sys_file表数据即可
							logger.debug(MessageUtil.$VALUE("image_exist", file_name,file_md5));
							if(temp.getFile_height()==64&&temp.getFile_width()==64&&temp.getFile_name().contains("avatar")){
								baseFileTemp = temp;
								//TODO 这个步骤是非常有必要的
								//这个file_name是当前用户操作的文件名，可能跟sys_file_base表中的文件名不一样
								//因为同一个文件的md5值是一样的，但是可以对同一个文件命名不同
								baseFileTemp.setFile_name(file_name);
								baseFileTemp.setFile_type(".jpg");//这里都默认为jpg图片文件了
							}
							// 根据MD5判断已经存在这个图片了，就不需要再去上传了
						}
					}
				}else{
					//TODO 上传接口附加的额外参数信息
				}
			}// end of while
			// 根据基础文件：baseFileTemp ，生成一条FileDTO信息和生成一条FileAppDto信息和修改用户头像操作
			//生成一条头像数据记录
			FileDTO userfile = new FileDTO();
			String file_code = IdWorker.getId()+"";
			String file_classic = "IMAGE";
			String file_common = "N";//非公共文件
			String file_delete = "N";//没有被删除
			String f_md5 = baseFileTemp.getFile_md5();
			String f_name = baseFileTemp.getFile_name();
			String file_text = "用户上传头像文件(64×64)";
			String file_type = baseFileTemp.getFile_type();
			userfile = new FileDTO();
			userfile.setFile_code(file_code);
			userfile.setFile_classic(file_classic);
			userfile.setFile_common(file_common);
			userfile.setFile_delete(file_delete);
			userfile.setFile_md5(f_md5);
			userfile.setFile_name(f_name);
			userfile.setFile_text(file_text);
			userfile.setFile_type(file_type);
			fileService.addDto(userfile);
			//将用户头像 64*64跟文件基表关联
			UserInfoDto user = this.loginUser();
			String appid = user.getUserId();//业务主键
			user = userInfoService.getDtoById(user);
			String userImage = file_code;
			user.setUserImage(userImage);
			userInfoService.updateDtoById(user);//要只更新用户的头像数据
			//生成一条关系信息记录,相应设置其他记录为历史数据
			String module_code = "SYSTEM";//所属模块
			FileAppDTO fadto = null;
			fadto = new FileAppDTO();
			fadto.setApp_code(appid);
			fadto.setModule_code(module_code);
			fadto.setFile_history("T");//是历史记录
			fileAppService.updateDtoById(fadto);
			fadto = new FileAppDTO(appid,file_code,module_code);
			fadto.setFile_history("F");//不是历史记录
			fileAppService.addDto(fadto);
			
			
			// TODO 不记录历史头像|如果记录历史头像，需要根据sys_user_setting表取数据然后进行相应操作
			// 同时还要操作 sys_file_app表的file_history字段
			
		}
		result.success = true;
		result.msg = "Success!";
		// 返回图片的保存结果（返回内容为json字符串，可自行构造，该处使用fastjson构造）
		PrintWriter out = response.getWriter();
		out.println(JSON.toJSONString(result));
	}
	
	/**
	 * 用户头像上传
	 * @deprecated 在spring mvc中下面的方法不好使了
	 * @param request
	 * @param myfiles
	 * @param response
	 * @throws FileUploadException
	 * @throws IOException
	 * @date 2016年12月19日 下午11:43:27
	 * @writer junehappylove
	 */
	@RequestMapping("/user/UploadAction.do")
	public void UploadAction(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IOException {
		String contentType = request.getContentType();
		if (contentType.indexOf("multipart/form-data") >= 0) {
			Result result = new Result();
			result.avatarUrls = new ArrayList<>();
			result.success = false;
			result.msg = "Failure!";
			
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			//ServletFileUpload upload = new ServletFileUpload();
			FileItemIterator fileItems = upload.getItemIterator(request);
			// 定义一个变量用以储存当前头像的序号
			int avatarNumber = 1;
			// 取服务器时间+8位随机码作为部分文件名，确保文件名无重复。
			String fileName = getFileName();
			// 基于原图的初始化参数
			String initParams = "";
			
			BufferedInputStream inputStream;
			BufferedOutputStream outputStream;
			// 遍历表单域
			while (fileItems.hasNext()) {
				FileItemStream fileItem = fileItems.next();
				String fieldName = fileItem.getFieldName();
				// 是否是原始图片 file 域的名称（默认的 file
				// 域的名称是__source，可在插件配置参数中自定义。参数名：src_field_name）
				Boolean isSourcePic = fieldName.equals("__source");
				// 文件名，如果是本地或网络图片为原始文件名（不含扩展名）、如果是摄像头拍照则为 *FromWebcam
				// String name = fileItem.getName();
				// 当前头像基于原图的初始化参数（即只有上传原图时才会发送该数据，且发送的方式为POST），
				// 用于修改头像时保证界面的视图跟保存头像时一致，提升用户体验度。
				// 修改头像时设置默认加载的原图url为当前原图url+该参数即可，可直接附加到原图url中储存，不影响图片呈现。
				if (fieldName.equals("__initParams")) {
					inputStream = new BufferedInputStream(fileItem.openStream());
					byte[] bytes = new byte[inputStream.available()];
					inputStream.read(bytes);
					initParams = new String(bytes, "UTF-8");
					inputStream.close();
				}
				// 如果是原始图片 file 域的名称或者以默认的头像域名称的部分“__avatar”打头
				//(默认的头像域名称：__avatar1,2,3...，可在插件配置参数中自定义，参数名：avatar_field_names)  
				else if (isSourcePic || fieldName.startsWith("__avatar")) {
					String virtualPath = "/upload/jsp_avatar" + avatarNumber + "_" + fileName + ".jpg";
					// 原始图片（默认的 file域的名称是__source，可在插件配置参数中自定义。参数名：src_field_name）。
					if (isSourcePic) {
						result.sourceUrl = virtualPath = "/upload/jsp_source_" + fileName + ".jpg";
					}
					// 头像图片（默认的 file 域的名称：__avatar1,2,3...，可在插件配置参数中自定义，参数名：avatar_field_names）。
					else {
						result.avatarUrls.add(virtualPath);
						avatarNumber++;
					}
					inputStream = new BufferedInputStream(fileItem.openStream());
					outputStream = new BufferedOutputStream(new FileOutputStream(request.getSession().getServletContext().getRealPath("/") + virtualPath.replace("/", "\\")));
					Streams.copy(inputStream, outputStream, true);
					inputStream.close();
					outputStream.flush();
					outputStream.close();
				}
				else{
//					//附加在接口中的其他参数...
//					//如下代码在上传接口upload.jsp中定义了一个userid=admin的参数：
//					//var swf = new fullAvatarEditor("swf", {
//					//	id: "swf",
//					//	upload_url: "upload.do?userid=admin"
//					//});
//					//即可如下获取userid的值admin
//				
					inputStream = new BufferedInputStream(fileItem.openStream());
					byte[] bytes = new byte [inputStream.available()];
					inputStream.read(bytes); 
					result.userid = new String(bytes, "UTF-8");
					inputStream.close();
				}
			}
			if (result.sourceUrl != null) {
				result.sourceUrl += initParams;
			}
			result.success = true;
			result.msg = "Success!";
			// 返回图片的保存结果（返回内容为json字符串，可自行构造，该处使用fastjson构造）
			PrintWriter out = response.getWriter();
			out.println(JSON.toJSONString(result));
			//toJson(result, response);
		}
	}

	/**
	 * 取服务器时间+8位随机码作为部分文件名，确保文件名无重复。
	 * @return
	 * @date 2016年12月19日 下午1:12:09
	 * @writer junehappylove
	 */
	private String getFileName() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
		String fileName = simpleDateFormat.format(new Date());
		Random random = new Random();
		String randomCode = "";
		for (int i = 0; i < 8; i++) {
			randomCode += Integer.toString(random.nextInt(36), 36);
		}
		fileName = fileName + randomCode;
		return fileName;
	}
	
	/**
	 * 私有内部类
	 * Result <br>
	 * 记录上传图片结果信息
	 * 
	 * @author 王俊伟 wjw.happy.love@163.com
	 * @blog https://www.github.com/junehappylove
	 * @date 2016年12月20日 上午8:48:36
	 * @version 1.0.0
	 */
	private class Result{
		@SuppressWarnings("unused")
		String userid;
		/**
		* 表示图片是否已上传成功。
		*/
		@SuppressWarnings("unused")
		Boolean success;
		/**
		* 自定义的附加消息。
		*/
		@SuppressWarnings("unused")
		String msg;
		/**
		* 表示原始图片的保存地址。
		*/
		String sourceUrl;
		/**
		* 表示所有头像图片的保存地址，该变量为一个数组。
		*/
		List<String> avatarUrls;
	}
}

