package com.june.controller.back.bussiness.referenceFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.AbstractDTO;
import com.june.common.BaseController;
import com.june.common.Constants;
import com.june.common.MessageDto;
import com.june.common.annotation.MethodLog;
import com.june.dto.back.bussiness.errorCode.ErrorCodeDto;
import com.june.dto.back.bussiness.ftp.FtpDto;
import com.june.dto.back.bussiness.referenceFile.FileCodeDto;
import com.june.dto.back.bussiness.referenceFile.ReferenceFileDto;
import com.june.dto.back.bussiness.vehicle.VehicleDto;
import com.june.dto.back.common.TreeDto;
import com.june.service.back.bussiness.errorCode.ErrorCodeService;
import com.june.service.back.bussiness.referenceFile.FileCodeService;
import com.june.service.back.bussiness.referenceFile.ReferenceFileService;

/**
 * 
 * 参考文件管理controller <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月10日 下午2:02:48
 */
@Controller
@RequestMapping("/referenceFile")
public class ReferenceFileController extends BaseController {

	@Autowired
	protected ReferenceFileService referenceFileService;
	@Autowired
	protected FileCodeService fileCodeService;
	@Autowired
	protected ErrorCodeService errorCodeService;

	/**
	 * form表单后台验证
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws Exception
	 * @return AbstractDTO
	 */
	@ModelAttribute
	public AbstractDTO validateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 将参数映射到对应的业务dto中并返回
		ReferenceFileDto referenceFileDto = new ReferenceFileDto();
		fillRequestDto(request, referenceFileDto);
		return referenceFileDto;
	}

	/**
	 * 参考文件信息查询页面初始化
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/search/")
	public ModelAndView search(HttpServletRequest request) {
		String page = new String("business/referenceFile/referenceFile");
		ModelAndView result = initPage(request, page);
		// 加载车型下拉
		List<TreeDto> list = vehicleService.getDrops(null);
		result.addObject("list", list);
		if(list!=null&&list.size()>0){
			
		}
		return result;
	}

	/**
	 * 上传参考文件页面
	 * 
	 * @param httpServletRequest
	 * @return
	 * @date 2016年5月11日 上午11:02:33
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/upload/")
	public ModelAndView upload(HttpServletRequest request) {
		String page = new String("business/referenceFile/upload");
		ModelAndView result = initPage(request, page);
		// 加载参考文件下拉
		List<TreeDto> list = referenceFileService.getDrops(null);
		result.addObject("list", list);
		// 加载子系统下拉

		return result;
	}

	@RequestMapping("/ftp/")
	public ModelAndView ftp(HttpServletRequest request) {
		String page = new String("business/referenceFile/ftp");
		ModelAndView result = initPage(request, page);
		return result;
	}

	/**
	 * 获取参考文件信息
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return void
	 */
	@RequestMapping("/getReferenceFiles")
	@MethodLog(module = "参考文件管理", remark = "参考文件查询", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getPagedReferenceFiles(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		ReferenceFileDto referenceFileDto = new ReferenceFileDto();
		fillRequestDto(httpServletRequest, referenceFileDto);
		referenceFileDto = referenceFileService.getPagedDtos(referenceFileDto);
		toJson(referenceFileDto, httpServletResponse);
	}
	
	/**
	 * 故障代码与参考文件关联列表查询
	 * @param request
	 * @param response
	 * @date 2016年6月29日 上午10:49:22
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/fileCode")
	public void fileCode(HttpServletRequest request, HttpServletResponse response) {
		FileCodeDto fcDto = new FileCodeDto();
		fillRequestDto(request, fcDto);
		fcDto = fileCodeService.noPagedDtos(fcDto);
		toJson(fcDto, response);
	}

	/**
	 * 获取所有的参考文件
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年5月16日 下午2:43:19
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/loadAllReferenceFile")
	public void loadAllReferenceFile(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		ReferenceFileDto referenceFileDto = new ReferenceFileDto();
		fillRequestDto(httpServletRequest, referenceFileDto);
		List<ReferenceFileDto> list = referenceFileService.getDtos(referenceFileDto);
		toJson(list, httpServletResponse);
	}

	/**
	 * 查看详细，编辑触发事件
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping("/checkDetail")
	public void checkDetail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws Exception {
		ReferenceFileDto referenceFileDto = new ReferenceFileDto();
		fillRequestDto(httpServletRequest, referenceFileDto);
		referenceFileDto = referenceFileService.getDtoById(referenceFileDto);
		// 判断参考文件信息是否为空
		if (referenceFileDto == null) {
			messageErrorNotExist(httpServletResponse);
		} else {
			toJson(referenceFileDto, httpServletResponse);
		}
	}

	/**
	 * 保存新增参考文件
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param referenceFileDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:12:41
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/newSave")
	@MethodLog(module = "参考文件管理", remark = "新增参考文件", operateType = Constants.OPERATE_TYPE_ADD)
	public void newSave(HttpServletRequest request, HttpServletResponse response,
			@Valid ReferenceFileDto referenceFileDto, BindingResult bindingResult,
			@RequestParam MultipartFile[] myfiles) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			// 该参考文件不存在的情况
			if (StringUtils.isBlank(referenceFileDto.getReferenceFileId())) {
				setCreater(referenceFileDto, request);
				//设置FTP目录
				String vpath = getVehiclePath(referenceFileDto.getVehicleId());
				String ftpPath = vpath + Constants.DIRECTORY_ROOT + Constants.DIRECTORY_R + Constants.DIRECTORY_ROOT
						+ referenceFileDto.getSystem();
				referenceFileDto.setFtpPath(ftpPath);// 必须设置
				referenceFileService.addDto(referenceFileDto);// 添加参考文件
				////////////////////////////////////////////////////////////////
				// 添加跟参考文件与故障代码关联
				FileCodeDto fileCodeDto = new FileCodeDto();
				initDto(fileCodeDto);// 初始化这个
				fileCodeDto.setFileId(referenceFileDto.getReferenceFileId());
				fileCodeDto.setErrorId(referenceFileDto.getErrorCode());
				String ids = fileCodeDto.getErrorId();
				String[] idss = ids.split(",");
				List<FileCodeDto> list = new ArrayList<>();
				FileCodeDto fcd = null;
				String fileId = fileCodeDto.getFileId();
				String addUserId = fileCodeDto.getAddUserId();
				for(String i:idss){
					fcd = new FileCodeDto(i, fileId);
					fcd.setAddUserId(addUserId);
					list.add(fcd);
				}
				fileCodeService.addList(list);
				////////////////////////////////////////////////////////////////
				// 创建ftp目录
				FtpDto ftp = new FtpDto();
				ftp.setFtpPath(referenceFileDto.getFtpPath());// 设置ftp路径
				ftpService.createDirectory(ftp);
				// 上传所选文件
				uploadFiles(request, response, referenceFileDto, myfiles);
				messageSaveSuccess(response);
			} else {
				// 参考文件存在的情况返回消息
				messageErrorExist(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 编辑保存参考文件信息
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param referenceFileDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:14:36
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveEdit")
	@MethodLog(module = "参考文件管理", remark = "修改参考文件", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void saveEdit(HttpServletRequest request, HttpServletResponse response,
			@Valid ReferenceFileDto referenceFileDto, BindingResult bindingResult,
			@RequestParam MultipartFile[] myfiles) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			if (referenceFileService.getDtoById(referenceFileDto) == null) {
				// 参考文件不存在的情况返回消息
				messageErrorNotExist(response);
			} else {
				//设置FTP目录
				String vpath = getVehiclePath(referenceFileDto.getVehicleId());
				String ftpPath = vpath + Constants.DIRECTORY_ROOT + Constants.DIRECTORY_R + Constants.DIRECTORY_ROOT
						+ referenceFileDto.getSystem();
				referenceFileDto.setFtpPath(ftpPath);// 必须设置
				// 参考文件存在的情况进行更新
				referenceFileService.updateDtoById(referenceFileDto);// 参考文件信息更新
				////////////////////////////////////////////////////////////////
				// 修改参考文件跟故障代码关联
				FileCodeDto fileCodeDto = new FileCodeDto();
				initDto(fileCodeDto);// 初始化这个
				fileCodeDto.setFileId(referenceFileDto.getReferenceFileId());
				fileCodeDto.setErrorId(referenceFileDto.getErrorCode());
				String fileId = null, errorIds = null;
				fileId = fileCodeDto.getFileId();
				errorIds = fileCodeDto.getErrorId();
				//删除已有的故障代码
				fileCodeService.deleteDtoById(new FileCodeDto(fileId));
				//添加现在的
				if(StringUtils.isNotEmpty(fileId) && StringUtils.isNotEmpty(errorIds)){
					String[] ids = errorIds.split(",");
					FileCodeDto fcd = null;
					for(String id:ids){
						fcd = new FileCodeDto(id, fileId);
						fcd.setAddUserId(fileCodeDto.getAddUserId());
						if(fileCodeService.getDtoById(fcd)!=null){
							fileCodeService.updateDtoById(fcd);
						}else{
							fileCodeService.addDto(fcd);
						}
					}
				}
				////////////////////////////////////////////////////////////////
				// 创建ftp目录
				FtpDto ftp = new FtpDto();
				ftp.setFtpPath(referenceFileDto.getFtpPath());// 设置ftp路径
				if(myfiles.length>0){//有文件才进行修改
					ftpService.deleteDirectory(ftp);//删除已经有的
					ftpService.createDirectory(ftp);//添加新修改的
					// 上传所选文件
					uploadFiles(request, response, referenceFileDto, myfiles);
				}
				messageUpdateSuccess(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 删除参考文件，已及参考文件-用户关联信息
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param referenceFileDto
	 * @throws Exception
	 * @date 2016年5月10日 下午1:28:22
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/deleteSelected")
	@MethodLog(module = "参考文件管理", remark = "删除参考文件", operateType = Constants.OPERATE_TYPE_DELETE)
	public void deleteSelected(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			ReferenceFileDto referenceFileDto) throws Exception {
		// 选中的参考文件id
		String referenceFileIds = referenceFileDto.getReferenceFileId();
		FileCodeDto fileCodeDto = new FileCodeDto();
		FtpDto ftpDto = new FtpDto();//
		String ftpPath = "";
		if (referenceFileIds != null) {
			String[] ids = referenceFileIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				referenceFileDto.setReferenceFileId(ids[i]);
				ftpPath = referenceFileService.getDtoById(referenceFileDto).getFtpPath();
				referenceFileService.deleteDtoById(referenceFileDto);
				// 删除对应的故障代码关联
				fileCodeDto.setFileId(ids[i]);
				fileCodeService.deleteDtoById(fileCodeDto);
				// 删除对应FTP目录
				ftpDto.setFtpPath(ftpPath);
				ftpService.deleteDirectory(ftpDto);
			}
		}
		messageDeleteSuccess(httpServletResponse);
	}

	@RequestMapping("/getFtpPath")
	public void getFtpPath(HttpServletRequest request, HttpServletResponse response,
			ReferenceFileDto referenceFileDto) {
		referenceFileDto = referenceFileService.getDtoById(referenceFileDto);
		toJson(referenceFileDto, response);
	}

	@RequestMapping("/getReferencePath")
	public void getReferencePath(HttpServletRequest request, HttpServletResponse response, VehicleDto vehicleDto,
			@RequestParam String system) {
		//String fileName = vehicleDto.getVehicleName();
		vehicleDto = vehicleService.getDtoById(vehicleDto);
		if (StringUtils.isEmpty(system)) {
			system = Constants.DIRECTORY_O;
		}
		//vehicleDto.setFtpPath(vehicleDto.getFtpPath() + "/" + Constants.DIRECTORY_R + "/" + system + "/" + fileName);
		vehicleDto.setFtpPath(vehicleDto.getFtpPath() + "/" + Constants.DIRECTORY_R + "/" + system);
		toJson(vehicleDto, response);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param referenceFileDto
	 * @param myfiles
	 * @param system
	 *            子系统，可以为空
	 * @throws Exception
	 * @date 2016年6月20日 下午5:49:18
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/uploadFiles")
	@MethodLog(module = "参考文件管理", remark = "上传参考文件", operateType = Constants.OPERATE_TYPE_OTHER)
	public void uploadFiles(HttpServletRequest request, HttpServletResponse response, ReferenceFileDto referenceFileDto,
			@RequestParam MultipartFile[] myfiles) throws Exception {
		String fileName = "";
		String ftpPath = referenceFileDto.getFtpPath();
		FtpDto ftp = new FtpDto();
		ftp.setFtpPath(ftpPath);
		Map<String, InputStream> map = new HashMap<String, InputStream>();
		InputStream is = null;
		for (int i = 0; i < myfiles.length; i++) {
			is = myfiles[i].getInputStream();
			fileName = myfiles[i].getOriginalFilename();
			map.put(fileName, is);
		}
		ftp.setFileMap(map);
		ftpService.createDirectory(ftp);//创建目录
		ftpService.uploadFile(ftp);//上传文件
		throwMessage("upload_success", MESSAGE_INFO, response);
	}

	@RequestMapping("/systemDrops")
	public void systemDrops(HttpServletRequest request, HttpServletResponse response,
			ReferenceFileDto referenceFileDto) {
		referenceFileDto = referenceFileService.getDtoById(referenceFileDto);
		if (referenceFileDto != null) {
			String vehicleId = referenceFileDto.getVehicleId();
			List<TreeDto> list = null;
			if (StringUtils.isNotEmpty(vehicleId)) {
				ErrorCodeDto t = new ErrorCodeDto();
				t.setImpactVehicle(vehicleId);
				list = errorCodeService.getDropsBySystem(t);
			}
			toJson(list, response);
		} else {
			toJson("[]", response);
		}
	}

	@RequestMapping("/downloadFTPFile")
	public void downloadFTPFile(HttpServletRequest request, HttpServletResponse response,
			ReferenceFileDto referenceFileDto) throws Exception {
		String filePath = referenceFileDto.getFtpPath(); // 路径+名称
		referenceFileDto.setReferenceFileName(filePath.substring(filePath.lastIndexOf("/")));
		if (filePath != null) {
			//先下载ftp文件到web服务器
			boolean result = ftpService.downloadFile(filePath);
			if (result) {
				// 然后下载到客户端
				// download(filePath, response);
			}
		}
		toJson(referenceFileDto, response);
	}
}
