package com.june.controller.back.bussiness.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.AbstractDTO;
import com.june.common.BaseController;
import com.june.common.Constants;
import com.june.common.MessageDto;
import com.june.common.annotation.MethodLog;
import com.june.dto.back.bussiness.client.VersionDto;
import com.june.dto.back.bussiness.ftp.FtpDto;
import com.june.dto.back.common.TreeDto;
import com.june.service.back.bussiness.client.VersionService;

/**
 * 
 * 客户端版本管理controller <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月10日 下午2:02:48
 */
@Controller
@RequestMapping("/version")
public class VersionController extends BaseController {

	@Autowired
	protected VersionService versionService;

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
		VersionDto versionDto = new VersionDto();
		fillRequestDto(request, versionDto);
		return versionDto;
	}

	/**
	 * 客户端版本信息查询页面初始化
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/search/")
	public ModelAndView init(HttpServletRequest request) {
		String page = new String("business/client/version");
		ModelAndView result = initPage(request, page);
		return result;
	}

	/**
	 * 上传客户端版本页面
	 * 
	 * @param request
	 * @return
	 * @date 2016年6月21日 下午2:05:42
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/upload/")
	public ModelAndView upload(HttpServletRequest request) {
		String page = new String("business/client/upload");
		ModelAndView result = initPage(request, page);
		// 加载客户端版本下拉数据
		List<TreeDto> list = versionService.getDrops(null);
		result.addObject("list", list);
		return result;
	}

	/**
	 * 获取上传客户端版本ftp路径
	 * 
	 * @param request
	 * @param response
	 * @param guideDto
	 * @date 2016年6月21日 下午2:05:38
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/getFtpPath")
	public void getFtpPath(HttpServletRequest request, HttpServletResponse response, VersionDto versionDto) {
		versionDto = versionService.getDtoById(versionDto);
		toJson(versionDto, response);
	}

	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(@PathVariable String versionId,@PathVariable String name) throws IOException{
		HttpHeaders headers = new HttpHeaders();  
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
	    headers.setContentDispositionFormData("attachment", "dict.txt");
	    name = "96226.swf";
	    VersionDto versionDto = versionService.getDtoById(new VersionDto(versionId));
	    String path  = versionDto.getVersionPath();
	    ftpService.downloadFile(path, name);
	    String pathname = Constants.DIRECTORY_LOCAL_DOWNLOAD+name;
		File file= new File(pathname );
	    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED); 
	}
	
	@RequestMapping("/downloadFTPFile/{versionId}/{name}")
	public void downloadFTPFile(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String versionId,@PathVariable String name) throws Exception {
		VersionDto versionDto = versionService.getDtoById(new VersionDto(versionId));
		String fileName = name.replace("@", ".");//获取要下载的文件名称
		String filePath = Constants.DIRECTORY_LOCAL_DOWNLOAD + fileName;//
		if (versionDto != null) {
			String ftpPath = versionDto.getVersionPath();
			boolean result = ftpService.downloadFile(ftpPath, fileName);

			if (result) {
				// 然后下载到客户端
				download(filePath, response);
			}
		}
	}

	/**
	 * 获取客户端版本信息
	 * 
	 * @param request
	 * @param response
	 * @return void
	 */
	@RequestMapping("/getVersions")
	@MethodLog(module = "客户端版本管理", remark = "客户端版本查询", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getVersions(HttpServletRequest request, HttpServletResponse response) {
		VersionDto versionDto = new VersionDto();
		fillRequestDto(request, versionDto);
		versionDto = versionService.getPagedDtos(versionDto);
		toJson(versionDto, response);
	}

	@RequestMapping("/loadAllVersion")
	public void loadAllVersion(HttpServletRequest request, HttpServletResponse response) {
		VersionDto versionDto = new VersionDto();
		fillRequestDto(request, versionDto);
		List<VersionDto> list = versionService.getDtos(versionDto);
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
		VersionDto versionDto = new VersionDto();
		fillRequestDto(request, versionDto);
		versionDto = versionService.getDtoById(versionDto);
		// 判断客户端版本信息是否为空
		if (versionDto == null) {
			throwMessage("error_not_exist", MESSAGE_ERRO, response);
		} else {
			toJson(versionDto, response);
		}
	}

	/**
	 * 保存新增客户端版本
	 * 
	 * @param request
	 * @param response
	 * @param versionDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:12:41
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/newSave")
	@MethodLog(module = "客户端版本管理", remark = "新增客户端版本", operateType = Constants.OPERATE_TYPE_ADD)
	public void newSave(HttpServletRequest request, HttpServletResponse response,
			@Valid VersionDto versionDto, BindingResult bindingResult,
			@RequestParam MultipartFile[] myfiles) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			// 该客户端版本不存在的情况
			if (StringUtils.isBlank(versionDto.getVersionId())) {
				setCreater(versionDto, request);
				versionService.addDto(versionDto);// 添加客户端版本
				// 增加版本目录
				FtpDto ftp = new FtpDto();
				ftp.setFtpPath(versionDto.getVersionPath());// 设置ftp路径
				ftpService.createDirectory(ftp);
				//上传所选文件，单个文件
				uploadFiles(request, response, versionDto, myfiles);
				throwMessage("save_success", MESSAGE_INFO, response);
			} else {
				// 客户端版本存在的情况返回消息
				messageErrorExist(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 编辑保存客户端版本信息
	 * 
	 * @param request
	 * @param response
	 * @param versionDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:14:36
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveEdit")
	@MethodLog(module = "客户端版本管理", remark = "修改客户端版本", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void saveEdit(HttpServletRequest request, HttpServletResponse response,
			@Valid VersionDto versionDto, BindingResult bindingResult,
			@RequestParam MultipartFile[] myfiles) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			if (versionService.getDtoById(versionDto) == null) {
				// 客户端版本不存在的情况返回消息
				messageErrorNotExist(response);
			} else {
				// 客户端版本存在的情况进行更新
				versionService.updateDtoById(versionDto);// 客户端版本信息更新
				// 增加版本目录
				FtpDto ftp = new FtpDto();
				ftp.setFtpPath(versionDto.getVersionPath());// 设置ftp路径
				if(myfiles.length>0){//有文件才进行修改
					ftpService.deleteDirectory(ftp);//删除已经有的
					ftpService.createDirectory(ftp);//添加新修改的
					//上传文件
					uploadFiles(request, response, versionDto, myfiles);
				}
				messageUpdateSuccess(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 删除客户端版本，已及客户端版本-用户关联信息
	 * 
	 * @param request
	 * @param response
	 * @param versionDto
	 * @throws Exception
	 * @date 2016年5月10日 下午1:28:22
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/deleteSelected")
	@MethodLog(module = "客户端版本管理", remark = "删除客户端版本", operateType = Constants.OPERATE_TYPE_DELETE)
	public void deleteSelected(HttpServletRequest request, HttpServletResponse response,
			VersionDto versionDto) throws Exception {
		// 选中的客户端版本id
		String versionIds = versionDto.getVersionId();
		FtpDto ftpDto = new FtpDto();
		String ftpPath = "";
		if (versionIds != null) {
			String[] ids = versionIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				versionDto.setVersionId(ids[i]);
				ftpPath = versionService.getDtoById(versionDto).getVersionPath();
				versionService.deleteDtoById(versionDto);
				// 删除对应FTP目录
				ftpDto.setFtpPath(ftpPath);
				ftpService.deleteDirectory(ftpDto);
			}
		}
		messageDeleteSuccess(response);
	}

	/**
	 * 向ftp上传客户端版本程序
	 * 
	 * @param request
	 * @param response
	 * @param guideDto
	 * @param myfiles
	 * @throws Exception
	 * @date 2016年6月21日 下午2:13:10
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/uploadFiles")
	public void uploadFiles(HttpServletRequest request, HttpServletResponse response, VersionDto versionDto,
			@RequestParam MultipartFile[] myfiles) throws Exception {
		String fileName = "";//
		String ftpPath = versionDto.getVersionPath();
		FtpDto ftp = new FtpDto();
		ftp.setFtpPath(ftpPath);//设置ftp地址
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
		//throwMessage("upload_failed", MESSAGE_ERRO, response);
	}
	
}
