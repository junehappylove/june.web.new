package com.june.controller.back.bussiness.vehicle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.AbstractDTO;
import com.june.common.BaseController;
import com.june.common.Constants;
import com.june.common.MessageDto;
import com.june.common.annotation.MethodLog;
import com.june.dto.back.bussiness.errorCode.ErrorCodeDto;
import com.june.dto.back.bussiness.ftp.FtpDto;
import com.june.dto.back.bussiness.mvb.MVBDto;
import com.june.dto.back.bussiness.vehicle.VehicleDto;
import com.june.dto.back.bussiness.vehicle.VehicleUser;
import com.june.dto.back.common.TreeDto;
import com.june.dto.back.system.base.UserInfoDto;
import com.june.service.back.bussiness.errorCode.ErrorCodeService;
import com.june.service.back.bussiness.mvb.MVBService;
import com.june.service.back.bussiness.vehicle.VehicleService;
import com.june.utility.FtpTree;

/**
 * 
 * 车型管理controller <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月10日 下午2:02:48
 */
@Controller
@RequestMapping("/vehicle")
public class VehicleController extends BaseController<VehicleDto> {

	@Autowired
	protected VehicleService vehicleService;
	@Autowired
	protected ErrorCodeService errorCodeService;
	@Autowired
	protected MVBService mvbService;

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
		VehicleDto vehicleDto = new VehicleDto();
		fillRequestDto(request, vehicleDto);
		return vehicleDto;
	}

	/**
	 * 车型信息查询页面初始化
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/search/")
	@MethodLog(module = "车型管理", remark = "车型管理页面初始化", operateType = Constants.OPERATE_TYPE_SEARCH)
	public ModelAndView init(HttpServletRequest request) {
		return initPage(request,"business/vehicle/vehicle");
	}
	
	@RequestMapping("/view/{id}")
	public ModelAndView view(HttpServletRequest request,@PathVariable String id) {
		ModelAndView result = null;
		result = new ModelAndView("business/vehicle/view");
		VehicleDto vehicleDto = vehicleService.getDtoById(new VehicleDto(id));
		result.addObject("vehicle", vehicleDto);
		return result;
	}

	/**
	 * 新增车型页面
	 * 
	 * @param request
	 * @return
	 * @date 2016年5月11日 上午11:02:33
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/add/")
	@MethodLog(module = "车型管理", remark = "添加车型信息", operateType = Constants.OPERATE_TYPE_ADD)
	public ModelAndView add(HttpServletRequest request) {
		return initPage(request,"business/vehicle/add");
	}

	@RequestMapping("/users/")
	@MethodLog(module = "车型管理", remark = "车型用户管理信息", operateType = Constants.OPERATE_TYPE_INIT)
	public ModelAndView users(HttpServletRequest request) {
		return initPage(request,"business/vehicle/users");
	}

	/**
	 * 获取车型信息
	 * 
	 * @param request
	 * @param response
	 * @return void
	 */
	@RequestMapping("/getVehicles")
	public void getVehicles(HttpServletRequest request, HttpServletResponse response) {
		VehicleDto vehicleDto = new VehicleDto();
		fillRequestDto(request, vehicleDto);
		vehicleDto = vehicleService.getPagedDtos(vehicleDto);
		toJson(vehicleDto, response);
	}

	@RequestMapping("/loadAllVehicle")
	@MethodLog(module = "车型管理", remark = "加载所有车型", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void loadAllVehicle(HttpServletRequest request, HttpServletResponse response) {
		VehicleDto vehicleDto = new VehicleDto();
		fillRequestDto(request, vehicleDto);
		List<VehicleDto> list = vehicleService.getDtos(vehicleDto);
		toJson(list, response);
	}
	
	@RequestMapping("/getFtpPath")
	public void getFtpPath(VehicleDto vehicleDto, HttpServletRequest request, HttpServletResponse response){
		vehicleDto = vehicleService.getDtoById(vehicleDto);
		String code = request.getParameter("code");
		if (StringUtils.isNotEmpty(code)) {
			vehicleDto.setFtpPath(vehicleDto.getFtpPath() + "/" + code);
		}
		toJson(vehicleDto, response);
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
	@MethodLog(module = "车型管理", remark = "编辑车型信息", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void checkDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		VehicleDto vehicleDto = new VehicleDto();
		fillRequestDto(request, vehicleDto);
		vehicleDto = vehicleService.getDtoById(vehicleDto);
		// 判断车型信息是否为空
		if (vehicleDto == null) {
			message(response,"error_not_exist", MESSAGE_ERRO);
		} else {
			toJson(vehicleDto, response);
		}
	}
	
	/**
	 * 查看
	 * @param request
	 * @param response
	 * @throws Exception
	 * @date 2016年5月20日 下午4:20:27
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/viewDetail")
	@MethodLog(module = "车型管理", remark = "查看车型信息", operateType = Constants.OPERATE_TYPE_VIEW)
	public void viewDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		VehicleDto vehicleDto = new VehicleDto();
		fillRequestDto(request, vehicleDto);
		vehicleDto = vehicleService.getDtoById(vehicleDto);
		ErrorCodeDto dto = new ErrorCodeDto();
		dto.setImpactVehicle(vehicleDto.getVehicleId());
		List<TreeDto> list = errorCodeService.getDrops(dto);
		if(list.size() > Constants.DEFAULT_ROW_DATA){//如果多对应的故障代码过多，就最大取30条数据
			list = list.subList(0, Constants.DEFAULT_ROW_DATA);
		}
		vehicleDto.setTree(list);
		toJson(vehicleDto, response);
	}

	/**
	 * 保存新增车型
	 * 
	 * @param request
	 * @param response
	 * @param vehicleDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:12:41
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/newSave")
	@MethodLog(module = "车型管理", remark = "保存新增的车型信息", operateType = Constants.OPERATE_TYPE_ADD)
	public void newSave(HttpServletRequest request, HttpServletResponse response,
			@Valid VehicleDto vehicleDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			// 该车型不存在的情况
			if (StringUtils.isEmpty(vehicleDto.getVehicleId())) {
				setCreater(vehicleDto, request);
				//设置ftp地址
				//vehicleDto.setFtpPath();
				vehicleService.addDto(vehicleDto);// 添加车型
				vehicleService.addVehicleUsers(vehicleDto);// 添加车型用户
				FtpDto ftp = new FtpDto();
				ftp.setFtpPath(vehicleDto.getFtpPath());//设置ftp路径
				ftp.setPaths(Constants.DIRECTORYS);//初始化目录集合
				ftpService.createDirectory(ftp);
				message(response,"save_success", MESSAGE_INFO);
			} else {
				// 车型存在的情况返回消息
				messageErrorExist(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 编辑保存车型信息
	 * 
	 * @param request
	 * @param response
	 * @param vehicleDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:14:36
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveEdit")
	@MethodLog(module = "车型管理", remark = "保存修改的车型信息", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void saveEdit(HttpServletRequest request, HttpServletResponse response,
			@Valid VehicleDto vehicleDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			if (vehicleService.getDtoById(vehicleDto) == null) {
				// 车型不存在的情况返回消息
				messageErrorNotExist(response);
			} else {
				// 车型存在的情况进行更新
				vehicleService.updateDtoById(vehicleDto);// 车型信息更新
				vehicleService.updateVehicleUser(vehicleDto);// 车型用户更新
				FtpDto ftp = new FtpDto();
				ftp.setFtpPath(vehicleDto.getFtpPath());//设置ftp路径
				ftp.setPaths(Constants.DIRECTORYS);//
				ftpService.createDirectory(ftp);
				messageUpdateSuccess(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 删除车型，已及车型-用户关联信息<br>
	 * 删除车型下的故障代码<br>
	 * 删除车型对应的FTP目录
	 * 
	 * @param request
	 * @param response
	 * @param vehicleDto
	 * @throws Exception
	 * @date 2016年5月10日 下午1:28:22
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/deleteSelected")
	@MethodLog(module = "车型管理", remark = "删除车型", operateType = Constants.OPERATE_TYPE_DELETE)
	public void deleteSelected(HttpServletRequest request, HttpServletResponse response,
			VehicleDto vehicleDto) throws Exception {
		// 选中的车型id
		String vehicleIds = vehicleDto.getVehicleId();
		FtpDto ftpDto = new FtpDto();
		String ftpPath = "";
		ErrorCodeDto edto = new ErrorCodeDto();
		MVBDto mvb = new MVBDto();
		if (vehicleIds != null) {
			String[] ids = vehicleIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				vehicleDto.setVehicleId(ids[i]);
				ftpPath = vehicleService.getDtoById(vehicleDto).getFtpPath();
				vehicleService.deleteDtoById(vehicleDto);
				ftpDto.setFtpPath(ftpPath);
				ftpService.deleteDirectory(ftpDto);
				// 删故障代码
				edto.setImpactVehicle(ids[i]);
				errorCodeService.deleteDtoById(edto);//根据车型删除
				// 删除MVB数据
				mvb.setVehicle(ids[i]);
				mvbService.deleteDtoById(mvb);
			}
		}
		messageDeleteSuccess(response);
	}

	/**
	 * 下拉列表
	 * 
	 * @param request
	 * @param response
	 * @date 2016年5月20日 下午2:19:44
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/drops")
	@MethodLog(module = "车型管理", remark = "获取车型信息下拉数据", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void drops(HttpServletRequest request, HttpServletResponse response) {
		toJson(vehicleService.getDrops(null), response);
	}

	@RequestMapping("/vehicleUsesSave")
	@MethodLog(module = "车型管理", remark = "保存车型与用户关联信息", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void vehicleUsesSave(HttpServletRequest request, HttpServletResponse response,
			VehicleUser vehicleUser) throws Exception {
		String vehicleId = request.getParameter("vehicleId");
		String userIds = request.getParameter("userId");
		if (StringUtils.isNotBlank(vehicleId) && StringUtils.isNotBlank(userIds)) {
			VehicleDto vehicleDto = new VehicleDto(vehicleId);
			List<UserInfoDto> users = new ArrayList<UserInfoDto>();
			UserInfoDto user = null;
			for (String userId : userIds.split(",")) {
				if (StringUtils.isNotBlank(userId)) {
					user = new UserInfoDto(userId);
					user.setAddUserId(vehicleUser.getSys_user());
					users.add(user);
				}
			}
			vehicleDto.setUsers(users);
			vehicleService.deleteVehicleUser(vehicleDto);// 先删除已经有的
			vehicleService.addVehicleUsers(vehicleDto);// 再添加新的
			messageSaveSuccess(response);
		} else {
			message(response,"请先选择车型", MESSAGE_ERRO);
		}
	}

	//////////////////////////////////////////////////////////////

	/**
	 * 加载FTP目录树
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @date 2016年6月27日 下午4:49:21
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/initFTPTree")
	public void initFTPTree(VehicleDto vehicleDto,HttpServletRequest request, HttpServletResponse response) throws IOException {
		vehicleDto = vehicleService.getDtoById(vehicleDto);
		String ftpPath = vehicleDto.getFtpPath();//取FTP目录
		//获取此目录下的目录结构
		List<FtpTree> trees = ftpService.getFtpTree(ftpPath);
		toJson(trees, response);
	}
	
	@RequestMapping("/downloadFTPFile")
	public void downloadFTPFile(HttpServletRequest request, HttpServletResponse response,
			VehicleDto vehicleDto) throws Exception {
		String filePath = vehicleDto.getFtpPath();
		String fileName = vehicleDto.getVehicleName();
		String newName = vehicleDto.getLastName();
		ArrayList<String> errList = new ArrayList<>();
		if(!can(newName)){
			errList.add("此格式文件不支持预览");
		}else{
			boolean result = false;
			if(vehicleDto!=null){
				result = ftpService.downloadFile(filePath, fileName, newName);
			}
			if(!result){
				errList.add("文件下载失败！");
			}
		}
		vehicleDto.setErrList(errList);
		toJson(vehicleDto, response);
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
	@Override
	@RequestMapping("/initRoleTree")
	public void initRoleTree(HttpServletRequest request, HttpServletResponse response) {
		super.initRoleTree(request,response);
	}

	/**
	 * 设置用户的默认车型
	 * @param request
	 * @param response
	 * @throws Exception
	 * @date 2016年6月29日 下午3:37:01
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/defaultVehicle")
	@MethodLog(module = "车型管理", remark = "设置用户默认车型", operateType = Constants.OPERATE_TYPE_OTHER)
	public void defaultVehicle(HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserInfoDto userInfoDto = new UserInfoDto();
		fillRequestDto(request, userInfoDto);
		userInfoDto.setUserId(this.loginUser().getUserId());
		userInfoService.setDefaultVehicle(userInfoDto);
		message(response,"设置成功！", MESSAGE_INFO);
	}

	@RequestMapping("/getErrorCodes")
	@MethodLog(module = "车型管理", remark = "获取车型关联的故障代码列表", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getErrorCodes(ErrorCodeDto errorCodeDto,HttpServletRequest request, HttpServletResponse response) {
		//ErrorCodeDto errorCodeDto = new ErrorCodeDto();
		//fillRequestDto(request, errorCodeDto);
		errorCodeDto.setErrorCode("");
		errorCodeDto.setErrorLevel("");
		errorCodeDto.setErrorReason("");
		errorCodeDto.setSubSystem("");
		errorCodeDto.setErrorDesc("");
		errorCodeDto.setVehicle("");
		errorCodeDto = errorCodeService.getPagedDtos(errorCodeDto);
		toJson(errorCodeDto, response);
	}
}
