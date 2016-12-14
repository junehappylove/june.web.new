/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.controller.back.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.june.utility.FastDfsUtils;
import com.june.utility.FileUpLoadDownload;
import com.june.utility.MessageUtil;


/**  
* @Description: kindeditor图片上传到服务器
* @author caiyang
* @date 2016年6月7日17:55:36
* @version V1.0  
 */
@Controller
@RequestMapping("/uploadEditorPic")
public class UploadEditorPicController{

	
	/**  
	* @Description: kindeditor图片上传到服务器
	* @author caiyang
	* @date 2016年6月7日17:55:36
	* @version V1.0  
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/uploadPic")
	public void uploadPic(@RequestParam MultipartFile imgFile,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String ctp = request.getServletContext().getRealPath("/") + "kindeditor\\";
		String filename = FileUpLoadDownload.getFileName(imgFile,null);
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject(); 
		try {
			String result = FileUpLoadDownload.uploadSingleFile(imgFile, request, response, ctp, filename);
			if (!result.equals("")) {
				obj.put("error", 1);
				obj.put("message", "图片" + filename + "上传异常");
				out.print(obj.toJSONString());
			}else{
				String url = request.getContextPath() + "/kindeditor/" + filename;
				 obj.put("error", 0); 
				 obj.put("url", url); 
				 out.print(obj.toJSONString()); 
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			obj.put("error", 1);
			obj.put("message", "图片" + filename + "上传异常");
			out.print(obj.toJSONString());
		}
      
	}
	/**
	 * 上传图片到服务器
	 * @param upload
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @date 2016年5月13日 下午3:14:10
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/uploadEditorPicture")
	public String uploadEditorPicture(@RequestParam MultipartFile upload, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!upload.isEmpty()) {
			// 获取上传的文件类型
			String uploadContentType = upload.getContentType();
			// CKEditor提交的很重要的一个参数
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			// CKEditor提交的很重要的一个参数
			String callback = request.getParameter("CKEditorFuncNum");
			@SuppressWarnings("unused")
			String expandedName = ""; // 文件扩展名
			if (uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg")) {
				// IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg
				expandedName = ".jpg";
			} else if (uploadContentType.equals("image/png") || uploadContentType.equals("image/x-png")) {
				// IE6上传的png图片的headimageContentType是"image/x-png"
				expandedName = ".png";
			} else if (uploadContentType.equals("image/gif")) {
				expandedName = ".gif";
			} else if (uploadContentType.equals("image/bmp")) {
				expandedName = ".bmp";
			} else {
				out.println("<script type=\"text/javascript\">");
				out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'"
						+ MessageUtil.formatMessage("error_image_file") + "');");
				out.println("</script>");
				return null;
			}
			if (upload.getSize() > 6000 * 1024) {
				out.println("<script type=\"text/javascript\">");
				out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'"
						+ MessageUtil.formatMessage("error_file_size", new String[] { "6M" }) + "');");
				out.println("</script>");
				return null;
			}
			String filename = FileUpLoadDownload.getFileName(upload, null);
			InputStream inputStream = FileUpLoadDownload.getFileStream(upload);
			String fileName = FastDfsUtils.uploadCkeditorFile(inputStream, filename, "caiyang");
			// 返回“图像”选项卡并显示图片
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + fileName + "/" + "','')");
			out.println("</script>");
			return null;
		} else {
			return null;
		}

	}
}

