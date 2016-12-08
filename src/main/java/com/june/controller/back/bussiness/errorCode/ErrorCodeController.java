package com.june.controller.back.bussiness.errorCode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.AbstractDTO;
import com.june.common.BaseController;
import com.june.common.Constants;
import com.june.common.MessageDto;
import com.june.common.MethodLog;
import com.june.controller.back.common.ExcelErrorCodeData;
import com.june.dto.back.bussiness.errorCode.ErrorCode;
import com.june.dto.back.bussiness.errorCode.ErrorCodeDto;
import com.june.dto.back.bussiness.guide.GuideCodeDto;
import com.june.dto.back.bussiness.referenceFile.FileCodeDto;
import com.june.dto.back.common.TreeDto;
import com.june.service.back.bussiness.errorCode.ErrorCodeService;
import com.june.service.back.bussiness.guide.GuideCodeService;
import com.june.service.back.bussiness.referenceFile.FileCodeService;
import com.june.service.back.bussiness.vehicle.VehicleService;

import net.sf.json.JSONObject;

/**
 * 
 * 故障代码管理controller <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月10日 下午2:02:48
 */
@Controller
@RequestMapping("/errorCode")
public class ErrorCodeController extends BaseController {

	@Autowired
	protected ErrorCodeService errorCodeService;
	@Autowired
	protected VehicleService vehicleService;
	@Autowired
	protected GuideCodeService guideCodeService;
	@Autowired
	protected FileCodeService fileCodeService;

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
		ErrorCodeDto errorCodeDto = new ErrorCodeDto();
		fillRequestDto(request, errorCodeDto);
		return errorCodeDto;
	}

	/**
	 * 故障代码信息查询页面初始化
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/search/")
	public ModelAndView init(HttpServletRequest request) {
		String page = new String("business/errorCode/errorCode");
		ModelAndView result = initPage(request, page);
		List<TreeDto> list = commonService.getErrorLevel();
		result.addObject("errorLevelDrops", list);
		list = vehicleService.getDrops(null);
		result.addObject("impactVehicleDrops", list);
		return result;
	}

	/**
	 * 新增故障代码页面
	 * 
	 * @param request
	 * @return
	 * @date 2016年5月11日 上午11:02:33
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/add/")
	public ModelAndView add(HttpServletRequest request) {
		String page = new String("business/errorCode/add");
		ModelAndView result = initPage(request, page);
		List<TreeDto> list = commonService.getErrorLevel();
		result.addObject("errorLevelDrops", list);
		list = vehicleService.getDrops(null);
		result.addObject("impactVehicleDrops", list);
		return result;
	}

	@RequestMapping("/users/")
	public ModelAndView users(HttpServletRequest request) {
		String page = new String("business/errorCode/users");
		return initPage(request, page);
	}
	
	@RequestMapping("/import/")
	public ModelAndView importData(HttpServletRequest request) {
		String page = new String("business/errorCode/import");
		ModelAndView result = initPage(request, page);
		List<TreeDto> list = commonService.getErrorLevel();
		result.addObject("errorLevelDrops", list);
		list = vehicleService.getDrops(null);
		result.addObject("impactVehicleDrops", list);
		return result;
	}
	
	@RequestMapping("/excel")
	@MethodLog(module = "故障代码管理", remark = "故障代码数据导入", operateType = Constants.OPERATE_TYPE_OTHER)
	public void excel(@RequestParam CommonsMultipartFile[] myfiles, HttpServletRequest request,
			HttpServletResponse response,ErrorCodeDto errorCodeDto) throws Exception {
		errorCodeDto.setUserId(super.loginUser().getUserId());
		// 批量导入故障代码
		// 判断导入方式 增量，和覆盖
		String type = request.getParameter("import_");
		if(StringUtils.isNotEmpty(type)&&"0".equals(type)){
			// TODO 增量式（每次处理数量限制在1000条数据以内）
			// 增量式需要去重操作
			// 查询已有的数据
			List<ErrorCode> errors = errorCodeService.getListByVehicle(errorCodeDto);
			// 获取文件中的数据
			List<ErrorCodeDto> list = getFileDatas(myfiles, errorCodeDto);
			list = removeDuplicate(list);// 去除自身的重复数据
			// 去除同数据库中重复数据
			list = removeDuplicate(errors,list);
			// 将整理后的数据上传
			errorCodeService.addlist(list);
		}else{
			//覆盖式
			// 删除数据库中原有的数据
			errorCodeService.deleteDtoById(errorCodeDto);//根据车型删除所有这个车型的故障代码数据
			// 获取文件中的数据
			List<ErrorCodeDto> list = getFileDatas(myfiles, errorCodeDto);
			list = removeDuplicate(list);// 去除自身的重复数据
			// 添加新的数据
			errorCodeService.addlist(list);
		}

		MessageDto messageDto = new MessageDto();
		JSONObject object = JSONObject.fromObject(messageDto);
		Converttojsonobjectajax(response, object);
	}

	/**
	 * 移除自身的重复数据
	 * 此时的ErrorCodeDtod的errorId为空
	 * @param list
	 * @return
	 * @date 2016年7月11日 下午3:39:42
	 * @writer wjw.happy.love@163.com
	 */
	private List<ErrorCodeDto> removeDuplicate(List<ErrorCodeDto> list){
		HashSet<ErrorCodeDto> h = new HashSet<ErrorCodeDto>(list); 
		list.clear();
		list.addAll(h);
		return list;
	}
	
	/**
	 * 移除重复数据
	 * @param errors
	 * @param list
	 * @return
	 * @date 2016年7月11日 下午3:27:08
	 * @writer wjw.happy.love@163.com
	 */
	private List<ErrorCodeDto> removeDuplicate(List<ErrorCode> errors, List<ErrorCodeDto> list) {
		List<ErrorCodeDto> temp = new ArrayList<>();
		for(ErrorCodeDto ec:list){
			for(ErrorCode e:errors){
				if(e.getErrorCode().equalsIgnoreCase(ec.getErrorCode().trim())){
					temp.add(ec);//重复的元素
					break;
				}else
					continue;
			}
			
		}
		list.removeAll(temp);
		return list;
	}

	private List<ErrorCodeDto> getFileDatas(CommonsMultipartFile[] myfiles, ErrorCodeDto errorCodeDto)
			throws FileNotFoundException, IOException {
		InputStream inputStream = null;
		String cache = myfiles[0].getStorageDescription();
		cache = cache.substring(4, cache.length()-1);
		inputStream = new FileInputStream(cache);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
		List<ErrorCodeDto> list = ExcelErrorCodeData.getInstance().readXls(hssfWorkbook,2);
		list = tidyExcel(list,errorCodeDto);
		return list;
	}
	
	private List<ErrorCodeDto> tidyExcel(List<ErrorCodeDto> list,ErrorCodeDto errorCodeDto) {
		String vehicle = errorCodeDto.getImpactVehicle();
		for(ErrorCodeDto i:list){
			i.setImpactVehicle(vehicle);
			i.setAddUserId(errorCodeDto.getUserId());
			i.setUpdateUserId(errorCodeDto.getUserId());
		}
		return list;
	}

	/**
	 * 获取故障代码信息
	 * 
	 * @param request
	 * @param response
	 * @return void
	 */
	@RequestMapping("/getErrorCodes")
	public void getErrorCodes(HttpServletRequest request, HttpServletResponse response) {
		ErrorCodeDto errorCodeDto = new ErrorCodeDto();
		fillRequestDto(request, errorCodeDto);
		errorCodeDto = errorCodeService.getPagedDtos(errorCodeDto);
		toJson(errorCodeDto, response);
	}
	
	@RequestMapping("/getPageList")
	@MethodLog(module = "故障代码管理", remark = "故障代码数据查询", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getPageList(HttpServletRequest request, HttpServletResponse response,ErrorCodeDto errorCodeDto) {
		fillRequestDto(request, errorCodeDto);
		errorCodeDto = errorCodeService.getPagedDtos(errorCodeDto);
		toJson(errorCodeDto, response);
	}
	
	/**
	 * 故障代码和操作指南管理列表展示
	 * @param request
	 * @param response
	 * @date 2016年6月28日 下午4:12:52
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/codeGuide")
	public void codeGuide(HttpServletRequest request, HttpServletResponse response) {
		GuideCodeDto gcdto = new GuideCodeDto();
		fillRequestDto(request, gcdto);
		gcdto = guideCodeService.noPagedDtos(gcdto);
		
		toJson(gcdto, response);
	}
	
	@RequestMapping("/codeFile")
	public void codeFile(HttpServletRequest request, HttpServletResponse response) {
		FileCodeDto fcdto = new FileCodeDto();
		fillRequestDto(request, fcdto);
		fcdto = fileCodeService.noPagedDtos(fcdto);
		
		toJson(fcdto, response);
	}

	@RequestMapping("/loadAllErrorCode")
	public void loadAllErrorCode(HttpServletRequest request, HttpServletResponse response) {
		ErrorCodeDto errorCodeDto = new ErrorCodeDto();
		fillRequestDto(request, errorCodeDto);
		List<ErrorCodeDto> list = errorCodeService.getDtos(errorCodeDto);
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
		ErrorCodeDto errorCodeDto = new ErrorCodeDto();
		fillRequestDto(request, errorCodeDto);
		errorCodeDto = errorCodeService.getDtoById(errorCodeDto);
		// 判断故障代码信息是否为空
		if (errorCodeDto == null) {
			throwMessage("error_not_exist", MESSAGE_ERRO, response);
		} else {
			toJson(errorCodeDto, response);
		}
	}

	/**
	 * 保存新增故障代码
	 * 
	 * @param request
	 * @param response
	 * @param errorCodeDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:12:41
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/newSave")
	@MethodLog(module = "故障代码管理", remark = "新增故障代码", operateType = Constants.OPERATE_TYPE_ADD)
	public void newSave(HttpServletRequest request, HttpServletResponse response,
			@Valid ErrorCodeDto errorCodeDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			// 该故障代码不存在的情况
			if (StringUtils.isBlank(errorCodeDto.getErrorId())) {
				this.setCreater(errorCodeDto, request);
				//查询故障代码是否存在
				boolean exit = exit(errorCodeDto);
				if(!exit){
					errorCodeService.addDto(errorCodeDto);// 添加故障代码
					throwMessage("save_success", MESSAGE_INFO, response);
				}else{
					throwMessage("error_code_exit",new String[]{errorCodeDto.getErrorCode()}, MESSAGE_WARN, response);
				}
			} else {
				// 故障代码存在的情况返回消息
				messageErrorExist(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 根据故障代码和车型，查询是否存在
	 * @param errorCodeDto
	 * @return
	 * @date 2016年9月8日 下午3:12:59
	 * @writer wjw.happy.love@163.com
	 */
	private boolean exit(ErrorCodeDto errorCodeDto) {
		return errorCodeService.exit(errorCodeDto);
	}

	/**
	 * 编辑保存故障代码信息
	 * 
	 * @param request
	 * @param response
	 * @param errorCodeDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:14:36
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveEdit")
	@MethodLog(module = "故障代码管理", remark = "修改故障代码", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void saveEdit(HttpServletRequest request, HttpServletResponse response,
			@Valid ErrorCodeDto errorCodeDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			if (errorCodeService.getDtoById(errorCodeDto) == null) {
				// 故障代码不存在的情况返回消息
				messageErrorNotExist(response);
			} else {
				// 故障代码存在的情况进行更新
				errorCodeService.updateDtoById(errorCodeDto);// 故障代码信息更新
				messageUpdateSuccess(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 删除故障代码，已及故障代码-用户关联信息
	 * 
	 * @param request
	 * @param response
	 * @param errorCodeDto
	 * @throws Exception
	 * @date 2016年5月10日 下午1:28:22
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/deleteSelected")
	@MethodLog(module = "故障代码管理", remark = "删除故障代码", operateType = Constants.OPERATE_TYPE_DELETE)
	public void deleteSelected(HttpServletRequest request, HttpServletResponse response,
			ErrorCodeDto errorCodeDto) throws Exception {
		// 选中的故障代码id
		String errorCodeIds = errorCodeDto.getErrorId();
		if (errorCodeIds != null) {
			String[] ids = errorCodeIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				errorCodeDto.setErrorId(ids[i]);
				errorCodeService.deleteDtoById(errorCodeDto);
			}
		}
		messageDeleteSuccess(response);
	}

	@RequestMapping("/drops")
	public void drops(HttpServletRequest request, HttpServletResponse response, ErrorCodeDto errorCodeDto){
		toJson(errorCodeService.getDrops(errorCodeDto), response); 
	}
	@RequestMapping("/drops2")
	public void drops2(HttpServletRequest request, HttpServletResponse response, ErrorCodeDto errorCodeDto){
		toJson(errorCodeService.getDrops2(errorCodeDto), response); 
	}

	@RequestMapping("/dropsByType")
	public void dropsByType(HttpServletRequest request, HttpServletResponse response, 
			ErrorCodeDto errorCodeDto,@RequestParam String type){
		List<TreeDto> list = null ;
		switch (type) {
		case "1":
			list = errorCodeService.getDropsBySystem(errorCodeDto);
			break;
		case "2":
			list = errorCodeService.getDropsByType(errorCodeDto);
			break;
		case "3":
			list = errorCodeService.getDropsByLevel(errorCodeDto);
			break;
		case "4":
			list = errorCodeService.getDropsByDesc(errorCodeDto);
			break;
		default:
			break;
		}
		toJson(list, response); 
	}
	
	/**
	 * 组织树初始化
	 * 
	 * @param request
	 * @param response
	 * @return void
	 */
	@RequestMapping("/initOrgTree")
	public void initOrgTree(HttpServletRequest request, HttpServletResponse response) {
		List<TreeDto> list = commonService.getOrgTree();
		toJson(list, response);
	}

	/**
	 * 初始化用户树
	 * 
	 * @param request
	 * @param response
	 * @return void
	 */
	@RequestMapping("/errorCodeTree")
	public void errorCodeTree(HttpServletRequest request, HttpServletResponse response,ErrorCodeDto errorCodeDto) {
		TreeDto treeDto = new TreeDto();
		fillRequestDto(request, treeDto);
		errorCodeDto.setSubSystem(treeDto.getId());
		List<TreeDto> list = errorCodeService.errorCodeTree(errorCodeDto);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setIsParent("false");
		}
		toJson(list, response);
	}
}
