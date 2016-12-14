package com.june.controller.back.bussiness.ftp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.AbstractDTO;
import com.june.common.BaseController;
import com.june.common.MessageDto;
import com.june.dto.back.bussiness.ftp.FtpDto;
import com.june.service.back.bussiness.ftp.FtpService;

/**
 * 
 * FTP设置管理controller <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月10日 下午2:02:48
 */
@Controller
@RequestMapping("/ftp")
public class FtpController extends BaseController<FtpDto> {

	@Autowired
	protected FtpService ftpService;

	/**
	 * form表单后台验证
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return AbstractDTO
	 */
	@ModelAttribute
	public AbstractDTO validateForm(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 将参数映射到对应的业务dto中并返回
		FtpDto ftpDto = new FtpDto();
		fillRequestDto(request, ftpDto);
		return ftpDto;
	}

	/**
	 * FTP设置信息查询页面初始化
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/search/")
	public ModelAndView init(HttpServletRequest request) {
		String page = new String("business/ftp/ftp");
		ModelAndView result = initPage(request, page);
		return result;
	}

	/**
	 * 获取FTP设置信息
	 * 
	 * @param request
	 * @param response
	 * @return void
	 */
	@RequestMapping("/getFtps")
	public void getFtps(HttpServletRequest request, HttpServletResponse response) {
		FtpDto ftpDto = new FtpDto();
		fillRequestDto(request, ftpDto);
		ftpDto = ftpService.getPagedDtos(ftpDto);
		toJson(ftpDto, response);
	}

	@RequestMapping("/loadAllFtp")
	public void loadAllFtp(HttpServletRequest request, HttpServletResponse response) {
		FtpDto ftpDto = new FtpDto();
		fillRequestDto(request, ftpDto);
		List<FtpDto> list = ftpService.getDtos(ftpDto);
		toJson(list, response);
	}

	/**
	 * 查看详细，编辑触发事件
	 * 
	 * @param request
	 * @param response
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping("/checkDetail")
	public void checkDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FtpDto ftpDto = new FtpDto();
		fillRequestDto(request, ftpDto);
		ftpDto = ftpService.getDtoById(ftpDto);
		// 判断FTP设置信息是否为空
		if (ftpDto == null) {
			message(response,"error_not_exist", MESSAGE_ERRO);
		} else {
			toJson(ftpDto, response);
		}
	}

	/**
	 * 保存新增FTP设置
	 * 
	 * @param request
	 * @param response
	 * @param ftpDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:12:41
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/newSave")
	public void newSave(HttpServletRequest request, HttpServletResponse response,
			@Valid FtpDto ftpDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			// 该FTP设置不存在的情况
			if (StringUtils.isBlank(ftpDto.getFtpId())) {
				setCreater(ftpDto, request);
				ftpService.addDto(ftpDto);// 添加FTP设置
				message(response,"save_success", MESSAGE_INFO);
			} else {
				// FTP设置存在的情况返回消息
				messageErrorExist(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 编辑保存FTP设置信息
	 * 
	 * @param request
	 * @param response
	 * @param ftpDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:14:36
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveEdit")
	public void saveEdit(HttpServletRequest request, HttpServletResponse response,
			@Valid FtpDto ftpDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			if (ftpService.getDtoById(ftpDto) == null) {
				// FTP设置不存在的情况返回消息
				messageErrorNotExist(response);
			} else {
				// FTP设置存在的情况进行更新
				ftpService.updateDtoById(ftpDto);// FTP设置信息更新
				messageUpdateSuccess(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 删除FTP设置，已及FTP设置-用户关联信息
	 * 
	 * @param request
	 * @param response
	 * @param ftpDto
	 * @throws Exception
	 * @date 2016年5月10日 下午1:28:22
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/deleteSelected")
	public void deleteSelected(HttpServletRequest request, HttpServletResponse response,
			FtpDto ftpDto) throws Exception {
		// 选中的FTP设置id
		String ftpIds = ftpDto.getFtpId();
		if (ftpIds != null) {
			String[] ids = ftpIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				ftpDto.setFtpId(ids[i]);
				ftpService.deleteDtoById(ftpDto);
			}
		}
		messageDeleteSuccess(response);
	}
}
