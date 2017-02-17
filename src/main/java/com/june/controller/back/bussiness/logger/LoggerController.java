package com.june.controller.back.bussiness.logger;

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
import com.june.dto.back.bussiness.logger.LoggerDto;
import com.june.dto.back.common.TreeDto;
import com.june.service.back.bussiness.logger.LoggerService;

/**
 * 
 * Logger管理controller <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月10日 下午2:02:48
 */
@Controller
@RequestMapping("/logger")
public class LoggerController extends BaseController<LoggerDto> {

	@Autowired
	protected LoggerService loggerService;

	/**
	 * form表单后台验证
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return AbstractDTO
	 */
	@ModelAttribute
	public AbstractDTO validateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 将参数映射到对应的业务dto中并返回
		LoggerDto loggerDto = new LoggerDto();
		fillRequestDto(request, loggerDto);
		return loggerDto;
	}

	/**
	 * Logger信息查询页面初始化
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/search/")
	public ModelAndView init(HttpServletRequest request) {
		String page = new String("business/logger/logger");
		ModelAndView result = initPage(request, page);
		List<TreeDto> list = commonService.getErrorLevel();
		result.addObject("errorLevelDrops", list);
		result.addObject("impactVehicleDrops", null);//FIXME 需要前台删掉
		return result;
	}

	@RequestMapping("/import/")
	public ModelAndView importData(HttpServletRequest request) {
		String page = new String("business/logger/import");
		ModelAndView result = initPage(request, page);
		List<TreeDto> list = commonService.getErrorLevel();
		result.addObject("errorLevelDrops", list);
		result.addObject("impactVehicleDrops", null);//FIXME 需要前台删掉
		return result;
	}

	/**
	 * 获取Logger分页信息
	 * 
	 * @param request
	 * @param response
	 * @return void
	 */
	@RequestMapping("/pageList")
	public void pageList(HttpServletRequest request, HttpServletResponse response) {
		LoggerDto loggerDto = new LoggerDto();
		fillRequestDto(request, loggerDto);
		loggerDto = loggerService.getPagedDtos(loggerDto);
		toJson(loggerDto, response);
	}

	/**
	 * 获取所有数据信息
	 * 
	 * @param request
	 * @param response
	 * @date 2016年6月17日 下午3:10:53
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/allList")
	public void allList(HttpServletRequest request, HttpServletResponse response) {
		LoggerDto loggerDto = new LoggerDto();
		fillRequestDto(request, loggerDto);
		List<LoggerDto> list = loggerService.getDtos(loggerDto);
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
	public void checkDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoggerDto loggerDto = new LoggerDto();
		fillRequestDto(request, loggerDto);
		loggerDto = loggerService.getDtoById(loggerDto);
		// 判断Logger信息是否为空
		if (loggerDto == null) {
			message(response,"error_not_exist", MESSAGE_ERRO);
		} else {
			toJson(loggerDto, response);
		}
	}

	/**
	 * 保存新增Logger
	 * 
	 * @param request
	 * @param response
	 * @param loggerDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:12:41
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/newSave")
	public void newSave(HttpServletRequest request, HttpServletResponse response, @Valid LoggerDto loggerDto,
			BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			// 该Logger不存在的情况
			if (StringUtils.isBlank(loggerDto.getLoggerId())) {
				setCreater(loggerDto, request);
				loggerService.addDto(loggerDto);// 添加Logger
				message(response,"save_success", MESSAGE_INFO);
			} else {
				// Logger存在的情况返回消息
				messageErrorExist(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 编辑保存Logger信息
	 * 
	 * @param request
	 * @param response
	 * @param loggerDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:14:36
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveEdit")
	public void saveEdit(HttpServletRequest request, HttpServletResponse response, @Valid LoggerDto loggerDto,
			BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			if (loggerService.getDtoById(loggerDto) == null) {
				// Logger不存在的情况返回消息
				messageErrorNotExist(response);
			} else {
				// Logger存在的情况进行更新
				loggerService.updateDto(loggerDto);// Logger信息更新
				messageUpdateSuccess(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 删除Logger信息
	 * 
	 * @param request
	 * @param response
	 * @param loggerDto
	 * @throws Exception
	 * @date 2016年5月10日 下午1:28:22
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/deleteSelected")
	public void deleteSelected(HttpServletRequest request, HttpServletResponse response, LoggerDto loggerDto)
			throws Exception {
		// 选中的Loggerid
		String loggerIds = loggerDto.getLoggerId();
		if (loggerIds != null) {
			String[] ids = loggerIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				loggerDto.setLoggerId(ids[i]);
				loggerService.deleteDto(loggerDto);
			}
		}
		messageDeleteSuccess(response);
	}

	@RequestMapping("/drops")
	public void drops(HttpServletRequest request, HttpServletResponse response, LoggerDto loggerDto) {
		toJson(loggerService.getDrops(loggerDto), response);
	}

	/**
	 * 清空所有日志
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @date 2016年6月29日 下午2:57:23
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/clear")
	public void clear(HttpServletRequest request, HttpServletResponse response) throws Exception{
		loggerService.clear();
		messageDeleteSuccess(response);
	}
}
