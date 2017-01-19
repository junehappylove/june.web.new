package com.june.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.june.common.Constants;
import com.june.common.MessageDto;

/**
 * 文件上传下载类: FileUpLoadDownload <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月19日 下午7:54:10
 * @version 1.0.0
 */
public class FileUpLoadDownload {

	/**
	 * 上传多个文件
	 * @param myfiles
	 * @param request
	 * @param response
	 * @param path
	 * @param fileName
	 * @return
	 * @date 2017年1月19日 下午7:54:30
	 * @writer junehappylove
	 */
	public static MessageDto upload(MultipartFile[] myfiles, HttpServletRequest request, HttpServletResponse response,
			String path, String fileName) {
		MessageDto messageDto = new MessageDto();
		ArrayList<String> errList = new ArrayList<String>();
		// 这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
		String realPath = path;
		// 上传文件的原名(即上传前的文件名字)
		String originalFilename = null;
		// 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
		// 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
		// 上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的my
		for (MultipartFile myfile : myfiles) {
			if (myfile.isEmpty()) {
				errList.add("请选择文件后上传");
				messageDto.setErrList(errList);
			} else {
				// 设置文件的名字，如果有自定义名字的话则设置成自定义名字，否则还是原来的名字
				if (fileName == null || fileName.equals("")) {
					originalFilename = myfile.getOriginalFilename();
				} else {
					originalFilename = fileName;
				}

				try {
					// 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
					// 此处也可以使用Spring提供的MultipartFile.transferTo(File
					// dest)方法实现文件的上
					FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, originalFilename));
					errList.add("文件上传成功");
				} catch (IOException e) {
					errList.add("文件上传失败");
					messageDto.setErrList(errList);
				}
			}
		}
		messageDto.setErrList(errList);
		return messageDto;
	}

	/**
	 * 以文件流的形式下载文件
	 * 
	 * @param request
	 * @param response
	 * @param filePath
	 * @date 2016年6月27日 下午1:57:56
	 * @writer wjw.happy.love@163.com
	 */
	public void dowoload(HttpServletRequest request, HttpServletResponse response, String filePath) {
		String path = filePath;
		File file = new File(path);
		// 清空response
		response.reset();
		// 设置response的Header
		try {
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(file.getName().getBytes("gbk"), "iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} // 转码之后下载的文件不会出现中文乱码
		response.addHeader("Content-Length", "" + file.length());
		try {
			// 以流的形式下载文件
			InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取上传文件的名字
	 * @param myfile
	 * @param name
	 * @return
	 * @date 2017年1月19日 下午7:54:45
	 * @writer junehappylove
	 */
	public static String getFileName(MultipartFile myfile, String name) {
		String originalFilename = null;
		// 设置文件的名字，如果有自定义名字的话则设置成自定义名字，否则还是原来的名字
		if (name == null || name.trim().equals("")) {
			originalFilename = myfile.getOriginalFilename();
		} else {
			originalFilename = name;
		}

		return originalFilename;
	}

	/**
	 * 获取文件流
	 * @param myfile
	 * @return
	 * @throws Exception
	 * @date 2017年1月19日 下午7:54:52
	 * @writer junehappylove
	 */
	public static InputStream getFileStream(MultipartFile myfile) throws Exception {
		InputStream inputStream = null;
		// 获取文件流
		inputStream = myfile.getInputStream();
		return inputStream;
	}

	/**
	 * 上传一个文件
	 * @param myfile
	 * @param request
	 * @param response
	 * @param path
	 * @param fileName
	 * @return
	 * @throws IOException
	 * @date 2017年1月19日 下午7:54:58
	 * @writer junehappylove
	 */
	public static String uploadSingleFile(MultipartFile myfile, HttpServletRequest request,
			HttpServletResponse response, String path, String fileName) throws IOException {
		// 这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
		String realPath = path;

		String originalFilename = null; // 上传文件的原名(即上传前的文件名字)

		// 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
		// 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
		// 上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的my
		if (myfile.isEmpty()) {
			// errList.add("请选择文件后上传");
			// messageDto.setErrList(errList);
			return "请选择文件后上传";
		} else {
			// 设置文件的名字，如果有自定义名字的话则设置成自定义名字，否则设置成MD5值
			if (fileName == null || fileName.equals("")) {
				originalFilename = myfile.getOriginalFilename();
			} else {
				originalFilename = fileName;
			}
			try {
				// 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
				// 此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, originalFilename));

				return ""; // 上传成功不需要弹出框
			} catch (IOException e) {
				return Constants.UPLOAD_FAIL;
			}
		}

	}
}
