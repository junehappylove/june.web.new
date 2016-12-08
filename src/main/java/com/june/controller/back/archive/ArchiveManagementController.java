package com.june.controller.back.archive;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.BaseController;
import com.june.common.MessageDto;
import com.june.dto.back.archive.ArchiveDto;
import com.june.service.back.archive.ArchiveService;
import com.june.utility.FileUpLoadDownload;
import com.june.utility.exception.CustomException;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/archiveManagement")
public class ArchiveManagementController extends BaseController{
	
	@Autowired
	private ArchiveService archiveService;
	
	@RequestMapping("/")
	public ModelAndView init()
	{
		ModelAndView result = null;
		result = new ModelAndView("archive/archiveManagement");
		return result;
	}
	
	@RequestMapping("/searchInfo")
	public void searchInfo(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		ArchiveDto archiveDto = new ArchiveDto();
		fillRequestDto(httpServletRequest, archiveDto);
		archiveDto = archiveService.getList(archiveDto);
		JSONObject jsonObject = JSONObject.fromObject(archiveDto);
		ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	
	@RequestMapping("/addNew")
	public void uploadpicture(@RequestParam MultipartFile[] myfiles,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception
	{
		ArchiveDto archiveDto = new ArchiveDto();
		fillRequestDto(httpServletRequest, archiveDto);
		//将档案信息保存到数据库
		archiveService.saveArchiveInfo(archiveDto);
		MessageDto messageDto = new MessageDto();
		ArrayList<String> errList = new ArrayList<String>();
		//循环进行文件上传处理
		for (int i = 0; i < myfiles.length; i++) {
			String ctp = httpServletRequest.getServletContext().getRealPath("/") + "picture\\";
			String filename = FileUpLoadDownload.getFileName(myfiles[i], null);
			String result = FileUpLoadDownload.uploadSingleFile(myfiles[i], httpServletRequest, httpServletResponse, ctp, filename);
			if (!result.equals("")) {
				throw new CustomException("图片上传失败，请重新上传");
			}
			archiveDto.setPictureName(filename);
			archiveService.saveArchivePic(archiveDto);
		}
		
		
        errList.add("添加成功");
        messageDto.setErrList(errList);
        messageDto.setErrType("info");
	     JSONObject object = JSONObject.fromObject(messageDto);
	     Converttojsonobjectajax(httpServletResponse, object);
	}

	@RequestMapping("/checkPic")
	public ModelAndView checkPic(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		String id = httpServletRequest.getParameter("id");
		ModelAndView result = new ModelAndView("archive/piclist");
		List<ArchiveDto> list = archiveService.getPicList(id);
		String url = httpServletRequest.getContextPath() + "/picture/";
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPictureUrl(url + list.get(i).getPictureName());
		}
		result.addObject("list",list);
		return result;
	}
}
