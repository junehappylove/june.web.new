package com.june.controller.back.archive;

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
import com.june.dto.back.archive.ArchiveDto;
import com.june.service.back.archive.ArchiveService;
import com.june.utility.FileUpLoadDownload;
import com.june.utility.exception.CustomException;

@Controller
@RequestMapping("/archive")
public class ArchiveController extends BaseController<ArchiveDto> {

	@Autowired
	private ArchiveService archiveService;

	@RequestMapping("/")
	public ModelAndView init() {
		ModelAndView result = null;
		result = new ModelAndView("archive/archive");
		return result;
	}

	@RequestMapping("/searchInfo")
	public void searchInfo(HttpServletRequest request, HttpServletResponse response) {
		ArchiveDto archiveDto = new ArchiveDto();
		fillRequestDto(request, archiveDto);
		archiveDto = archiveService.getList(archiveDto);
		toJson(archiveDto, response);
	}

	@RequestMapping("/addNew")
	public void uploadpicture(@RequestParam MultipartFile[] myfiles, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ArchiveDto archiveDto = new ArchiveDto();
		fillRequestDto(request, archiveDto);
		// 将档案信息保存到数据库
		archiveService.saveArchiveInfo(archiveDto);
		// 循环进行文件上传处理
		for (int i = 0; i < myfiles.length; i++) {
			String ctp = request.getServletContext().getRealPath("/") + "picture\\";
			String filename = FileUpLoadDownload.getFileName(myfiles[i], null);
			String result = FileUpLoadDownload.uploadSingleFile(myfiles[i], request, response,
					ctp, filename);
			if (!result.equals("")) {
				throw new CustomException("图片上传失败，请重新上传");
			}
			archiveDto.setPictureName(filename);
			archiveService.saveArchivePic(archiveDto);
		}

		message(response,"new_save_success",MESSAGE_INFO,archiveDto.getDtoName());
	}

	@RequestMapping("/checkPic")
	public ModelAndView checkPic(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ModelAndView result = new ModelAndView("archive/piclist");
		List<ArchiveDto> list = archiveService.getPicList(id);
		String url = request.getContextPath() + "/picture/";
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPictureUrl(url + list.get(i).getPictureName());
		}
		result.addObject("list", list);
		return result;
	}
}
