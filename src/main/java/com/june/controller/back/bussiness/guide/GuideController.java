package com.june.controller.back.bussiness.guide;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.AbstractDTO;
import com.june.common.BaseController;
import com.june.common.Constants;
import com.june.common.MessageDto;
import com.june.common.annotation.MethodLog;
import com.june.dto.back.bussiness.ftp.FtpDto;
import com.june.dto.back.bussiness.guide.GuideCodeDto;
import com.june.dto.back.bussiness.guide.GuideDto;
import com.june.dto.back.bussiness.guide.ImageXML;
import com.june.dto.back.bussiness.guide.Step;
import com.june.dto.back.bussiness.vehicle.VehicleDto;
import com.june.dto.back.common.TreeDto;
import com.june.service.back.bussiness.guide.GuideCodeService;
import com.june.service.back.bussiness.guide.GuideService;
import com.june.service.back.bussiness.vehicle.VehicleService;

/**
 * 
 * 操作指南管理controller <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月10日 下午2:02:48
 */
@Controller
@RequestMapping("/guide")
public class GuideController extends BaseController<GuideDto> {

	@Autowired
	protected GuideService guideService;
	@Autowired
	protected VehicleService vehicleService;
	@Autowired
	protected GuideCodeService guideCodeService;

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
		GuideDto guideDto = new GuideDto();
		fillRequestDto(request, guideDto);
		return guideDto;
	}

	/**
	 * 操作指南信息查询页面初始化
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/search/")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
		String page = new String("business/guide/guide");
		ModelAndView result = initPage(request, page);
		// 加载车型下拉
		List<TreeDto> list = vehicleService.getDrops(null);
		result.addObject("list", list);
		return result;
	}
	@RequestMapping("/add")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
		String page = new String("business/guide/add");
		ModelAndView result = initPage(request, page);
		// 加载车型下拉
		List<TreeDto> list = vehicleService.getDrops(null);
		result.addObject("list", list);
		return result;
	}
	@RequestMapping("/edit/{guideid}")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response,@PathVariable String guideid) {
		String page = new String("business/guide/edit");
		ModelAndView result = initPage(request, page);
		GuideDto guideDto = new GuideDto();
		guideDto.setGuideId(guideid);
		guideDto = guideService.getDtoById(guideDto);
		// 加载车型下拉
		List<TreeDto> list = vehicleService.getDrops(null);
		result.addObject("list", list);
		result.addObject("guideid",guideid);
		result.addObject("guide", guideDto);
		return result;
	}

	/**
	 * 上传操作指南页面
	 * 
	 * @param request
	 * @return
	 * @date 2016年5月11日 上午11:02:33
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/upload/")
	public ModelAndView upload(HttpServletRequest request) {
		String page = new String("business/guide/upload");
		ModelAndView result = initPage(request, page);
		//加载指南下拉
		List<TreeDto> list = guideService.getDrops(null);
		result.addObject("list", list);
		return result;
	}
	
	@RequestMapping("/ftp/")
	public ModelAndView ftp(HttpServletRequest request) {
		String page = new String("business/guide/ftp");
		ModelAndView result = initPage(request, page);
		return result;
	}

	/**
	 * 获取操作指南信息
	 * 
	 * @param request
	 * @param response
	 * @return void
	 */
	@RequestMapping("/getGuides")
	@MethodLog(module = "操作指南管理", remark = "操作指南查询", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getPagedGuids(HttpServletRequest request, HttpServletResponse response) {
		GuideDto guideDto = new GuideDto();
		fillRequestDto(request, guideDto);
		guideDto = guideService.getPagedDtos(guideDto);
		toJson(guideDto, response);
	}
	
	/**
	 * 故障代码与操作指南关联列表查询
	 * @param request
	 * @param response
	 * @date 2016年6月29日 上午10:49:22
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/guideCode")
	@MethodLog(module = "操作指南管理", remark = "操作指南与故障代码关联", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void guideCode(HttpServletRequest request, HttpServletResponse response) {
		GuideCodeDto gcDto = new GuideCodeDto();
		fillRequestDto(request, gcDto);
		gcDto = guideCodeService.noPagedDtos(gcDto);
		toJson(gcDto, response);
	}
	
	/**
	 * 获取所有的操作指南
	 * @param request
	 * @param response
	 * @date 2016年5月16日 下午2:43:19
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/loadAllGuide")
	public void loadAllGuide(HttpServletRequest request, HttpServletResponse response) {
		GuideDto guideDto = new GuideDto();
		fillRequestDto(request, guideDto);
		List<GuideDto> list = guideService.getDtos(guideDto);
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
		GuideDto guideDto = new GuideDto();
		fillRequestDto(request, guideDto);
		guideDto = guideService.getDtoById(guideDto);
		// 判断操作指南信息是否为空
		if (guideDto == null) {
			messageErrorNotExist(response);
		} else {
			toJson(guideDto, response);
		}
	}

	/**
	 * 保存新增操作指南
	 * 
	 * @param request
	 * @param response
	 * @param guideDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:12:41
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/newSave")
	@MethodLog(module = "操作指南管理", remark = "新增操作指南", operateType = Constants.OPERATE_TYPE_ADD)
	public void newSave(HttpServletRequest request, HttpServletResponse response,
			@Valid GuideDto guideDto, BindingResult bindingResult,
			@RequestParam MultipartFile[] imageFiles,@RequestParam MultipartFile[] videoFiles) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		String ftpPath = "";
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			// 该操作指南不存在的情况
			if (StringUtils.isBlank(guideDto.getGuideId())) {
				setCreater(guideDto, request);
				//设置FTP目录
				ftpPath = setFTP(guideDto); //设置这个ftp地址，同时也存储这个地址，以便于上传图文和视频使用
				guideService.addDto(guideDto);// 添加操作指南
				// 添加操作指南跟故障代码关联
				GuideCodeDto guideCodeDto = new GuideCodeDto();
				fillRequestDto(request, guideCodeDto);//设置errorId
				guideCodeDto.setGuideId(guideDto.getGuideId());//设置guideId
				guideCodeDto.setAddUserId(this.loginUser().getUserId());//设置当前用户
				guideCodeDto.setAddTime(new Timestamp(System.currentTimeMillis()));//设置当前时间
				String errorIds = guideCodeDto.getErrorId();
				String[] ids = errorIds.split(",");
				List<GuideCodeDto> list = new ArrayList<>();
				GuideCodeDto gcd = null;
				if(StringUtils.isNotEmpty(errorIds)){
					for(String id:ids){
						gcd = new GuideCodeDto();
						gcd.setGuideId(guideDto.getGuideId());
						gcd.setErrorId(id);
						gcd.setAddUserId(guideCodeDto.getAddUserId());
						list.add(gcd);
					}
					guideCodeService.addList(list);
				}
				//创建ftp目录
				FtpDto ftp = new FtpDto();
				ftp.setFtpPath(guideDto.getFtpPath());	//设置ftp路径
				ftp.setPaths(Constants.DIRECTORYS_2);	//设置文件，视频目录
				ftpService.createDirectory(ftp);
				// 如果有图文，上传图文
				if(imageFiles.length>0){
					//生成xml步骤，并上传
					upload_(ftpPath + Constants.DIRECTORY_ROOT + Constants.DIRECTORY_F, imageFiles, request);
				}
				// 如果有视频，上传视频
				if(videoFiles.length>0){
					upload_(ftpPath + Constants.DIRECTORY_ROOT + Constants.DIRECTORY_V, videoFiles, null);
				}
				messageSaveSuccess(response);
				//toJson(guideDto, response);//主要是给页面提供ftpPath参数或者页面自己组织地址
			} else {
				// 操作指南存在的情况返回消息
				messageErrorExist(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}
	
	/**
	 * 视频和文件的上传方法
	 * @param ftpPath
	 * @param myfiles
	 * @throws Exception
	 * @date 2016年7月4日 下午8:46:19
	 * @writer wjw.happy.love@163.com
	 */
	void upload_(String ftpPath, MultipartFile[] myfiles,HttpServletRequest request) throws Exception{
		ImageXML xml = new ImageXML();
		TreeSet<Step> steps = new TreeSet<>();//步骤有序
		Step step = null;
		String fileName = "",flag = Constants.DIV_STEP,ret = "";
		FtpDto ftp = new FtpDto();
		ftp.setFtpPath(ftpPath);
		Map<String, InputStream> map = new HashMap<String, InputStream>();
		InputStream is = null;
		for (int i = 0; i < myfiles.length; i++) {
			is = myfiles[i].getInputStream();
			fileName = myfiles[i].getOriginalFilename();
			map.put(fileName, is);
			if(request != null){
				ret = request.getParameter(flag+(i+1));
				step = new Step((i+1), fileName, ret);//生成步骤
				steps.add(step);
			}
		}
		if(request!=null){//视频不用上传xml
			xml.setStep(steps);
			xml.setPath(ftpPath);//设置存放路径
			map.put(Constants.FILE_STEP, bean2Stream(xml));
		}
		ftp.setFileMap(map);
		ftpService.createDirectory(ftp);//创建目录
		ftpService.uploadFile(ftp);//上传文件
	}
	
	/**
	 * 设置ftp目录
	 * @param guideDto
	 * @date 2016年7月4日 下午2:45:41
	 * @writer wjw.happy.love@163.com
	 */
	private String setFTP(GuideDto guideDto) {
		// TODO 设置ftp目录地址的
		String guideName = guideDto.getGuideName();
		String type = guideDto.getEmergency();//获取是否应急
		type = Constants.DIRECTORY_ROOT + (type.equals(Constants.FLAG_YES) ? Constants.DIRECTORY_Y : Constants.DIRECTORY_N);
		String vehicleId = guideDto.getVehicleId();
		String vehicleName = guideDto.getVehicleName();
		String path = type + Constants.DIRECTORY_ROOT + guideName;
		if(StringUtils.isEmpty(vehicleName)){
			// 查询车型表获取地址
			vehicleName = vehicleService.getDtoById(new VehicleDto(vehicleId)).getVehicleName();
		}
		path = Constants.DIRECTORY_ROOT + vehicleName + path;
		logger.debug("操作指南存放FTP地址:"+path);
		guideDto.setFtpPath(path);// 设置ftp地址
		return path;
	}

	/**
	 * 编辑保存操作指南信息
	 * 
	 * @param request
	 * @param response
	 * @param guideDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:14:36
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveEdit")
	@MethodLog(module = "操作指南管理", remark = "修改操作指南", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void saveEdit(HttpServletRequest request, HttpServletResponse response,
			@Valid GuideDto guideDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			if (guideService.getDtoById(guideDto) == null) {
				// 操作指南不存在的情况返回消息
				messageErrorNotExist(response);
			} else {
				//设置FTP目录
				setFTP(guideDto);
				// 操作指南存在的情况进行更新
				guideService.updateDtoById(guideDto);// 操作指南信息更新
				//创建ftp目录
				FtpDto ftp = new FtpDto();
				ftp.setFtpPath(guideDto.getFtpPath());//设置ftp路径
				ftp.setPaths(Constants.DIRECTORYS_2);
				ftpService.createDirectory(ftp);
				// 更新关联的故障代码信息
				// 添加操作指南跟故障代码关联
				GuideCodeDto guideCodeDto = new GuideCodeDto();
				fillRequestDto(request, guideCodeDto);//设置errorId
				guideCodeDto.setGuideId(guideDto.getGuideId());//设置guideId
				guideCodeDto.setAddUserId(this.loginUser().getUserId());//设置当前用户
				guideCodeDto.setAddTime(new Timestamp(System.currentTimeMillis()));//设置当前时间
				String guideId = guideCodeDto.getGuideId();
				String errorIds = guideCodeDto.getErrorId();
				//删除已有的故障代码
				guideCodeService.deleteDtoById(new GuideCodeDto(guideId));
				//添加现在的
				if(StringUtils.isNotEmpty(guideId) && StringUtils.isNotEmpty(errorIds)){
					String[] ids = errorIds.split(",");
					List<GuideCodeDto> list = new ArrayList<>();
					GuideCodeDto gcd = null;
					for(String id:ids){
						gcd = new GuideCodeDto();
						gcd.setGuideId(guideId);
						gcd.setErrorId(id);
						gcd.setAddUserId(guideCodeDto.getAddUserId());
						list.add(gcd);//需要添加的故障代码
						if(guideCodeService.getDtoById(gcd)!=null){
							guideCodeService.updateDtoById(gcd);//如果存着就修改
						}else{
							guideCodeService.addDto(gcd);//不存在添加
						}
					}
				}
				messageUpdateSuccess(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}
	
	/**
	 * 编辑图文上传方法
	 * @param request
	 * @param response
	 * @param guideDto
	 * @param imageEditFiles
	 * @throws Exception
	 * @date 2016年7月5日 下午5:26:47
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/uploadImageFiles")
	public void uploadImageFiles(HttpServletRequest request, HttpServletResponse response,
			GuideDto guideDto,@RequestParam MultipartFile[] imageEditFiles) throws Exception{
		guideDto = guideService.getDtoById(guideDto);
		String ftpPath = guideDto.getFtpPath();
		int size = imageEditFiles.length;
		if(StringUtils.isNotEmpty(ftpPath) && size > 0){
			ftpPath = ftpPath+Constants.DIRECTORY_ROOT+Constants.DIRECTORY_F;
			delete_(ftpPath);//删除这个文件目录下的所有文件
			upload_(ftpPath, imageEditFiles, request);
			// 更改文件时间
			Timestamp now = new Timestamp(System.currentTimeMillis());
			guideDto.setFileTime(now);
			guideService.updateDtoById(guideDto);
			message(response,"upload_success", MESSAGE_INFO);
		}else{
			message(response,"upload_failed", MESSAGE_ERRO);
		}
	}
	
	/**
	 * 编辑视频上传方法
	 * @param request
	 * @param response
	 * @param guideDto
	 * @param imageEditFiles
	 * @throws Exception
	 * @date 2016年7月5日 下午5:26:47
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/uploadVideoFiles")
	public void uploadVideoFiles(HttpServletRequest request, HttpServletResponse response,
			GuideDto guideDto,@RequestParam MultipartFile[] videoEditFiles) throws Exception{
		guideDto = guideService.getDtoById(guideDto);
		String ftpPath = guideDto.getFtpPath();
		int size = videoEditFiles.length;
		if(StringUtils.isNotEmpty(ftpPath) && size > 0){
			ftpPath = ftpPath + Constants.DIRECTORY_ROOT + Constants.DIRECTORY_V;
			delete_(ftpPath);//删除这个文件目录下的所有文件
			upload_(ftpPath, videoEditFiles, null);
			// 更改文件时间
			Timestamp now = new Timestamp(System.currentTimeMillis());
			guideDto.setVideoTime(now);
			guideService.updateDtoById(guideDto);
			message(response,"upload_success", MESSAGE_INFO);
		}else{
			message(response,"upload_failed", MESSAGE_ERRO);
		}
	}
	
	void delete_(String ftpPath) throws SocketException, IOException{
		ftpService.deleteFiles(ftpPath);
	}

	/**
	 * 删除操作指南，已及操作指南-用户关联信息
	 * 
	 * @param request
	 * @param response
	 * @param guideDto
	 * @throws Exception
	 * @date 2016年5月10日 下午1:28:22
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/deleteSelected")
	@MethodLog(module = "操作指南管理", remark = "删除操作指南", operateType = Constants.OPERATE_TYPE_DELETE)
	public void deleteSelected(HttpServletRequest request, HttpServletResponse response,
			GuideDto guideDto) throws Exception {
		// 选中的操作指南id
		String guideIds = guideDto.getGuideId();
		FtpDto ftpDto = new FtpDto();
		String ftpPath = "";
		if (guideIds != null) {
			String[] ids = guideIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				guideDto.setGuideId(ids[i]);
				ftpPath = guideService.getDtoById(guideDto).getFtpPath();
				guideService.deleteDtoById(guideDto);//删除操作指南
				//删除对应FTP目录
				ftpDto.setFtpPath(ftpPath);
				ftpService.deleteDirectory(ftpDto);
				// 删除关联的故障代码
				GuideCodeDto guideCodeDto = new GuideCodeDto();
				guideCodeDto.setGuideId(guideDto.getGuideId());
				guideCodeService.deleteDtoById(guideCodeDto);
			}
		}
		messageDeleteSuccess(response);
	}

	@RequestMapping("/getFtpPath")
	public void getFtpPath(HttpServletRequest request, HttpServletResponse response,
			GuideDto guideDto){
		guideDto = guideService.getDtoById(guideDto);
		toJson(guideDto, response);
	}
	@RequestMapping("/getVideoPath")
	public void getVideoPath(HttpServletRequest request, HttpServletResponse response,
			GuideDto guideDto) throws SocketException, IOException{
		guideDto = guideService.getDtoById(guideDto);
		String path = guideDto.getFtpPath()+Constants.DIRECTORY_ROOT+Constants.DIRECTORY_V;
		String name = "";
		List<String> names = ftpService.getFileList(new FtpDto(null,path));
		if(names!=null && names.size()>0){
			name = names.get(0);
			guideDto.setFtpPath(path+Constants.DIRECTORY_ROOT+name);
		}else{
			guideDto.setFtpPath("");//没有视频则为空
		}
		toJson(guideDto, response);
	}
	@RequestMapping("/getImageList")
	public void getImageList(HttpServletRequest request, HttpServletResponse response,
			GuideDto guideDto) throws SocketException, IOException{
		guideDto = guideService.getDtoById(guideDto);
		String path = guideDto.getFtpPath() + Constants.DIRECTORY_ROOT + Constants.DIRECTORY_F;
		String xmlName = Constants.FILE_STEP;//xml路径以及名称
		FtpDto ftp = new FtpDto();
		ftp.setFtpPath(path);
		ftp.setFtpFileName(xmlName);//
		InputStream is = ftpService.fileStream(ftp);
		ImageXML image = null;
		if (is != null) {
			image = super.xml2Bean(is);
		} else {
			image = new ImageXML();
		}
		toJson(image, response);
	}
	
	/**
	 * 上传文件
	 * @param request
	 * @param response
	 * @param guideDto
	 * @param myfiles
	 * @throws Exception
	 * @date 2016年6月22日 下午2:02:29
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/uploadFiles")
	@MethodLog(module = "操作指南管理", remark = "上传操作指南", operateType = Constants.OPERATE_TYPE_OTHER)
	public void uploadFiles(HttpServletRequest request, HttpServletResponse response,
			GuideDto guideDto,@RequestParam MultipartFile[] myfiles) throws Exception{
		String fileName = "";
		String flag = guideDto.getEmergency();//1 视频 0 文件
		guideDto = guideService.getDtoById(guideDto);
		String ftpPath = guideDto.getFtpPath();
		String emergency = flag.equals("1") ? Constants.DIRECTORY_V : Constants.DIRECTORY_F;
		ftpPath = ftpPath + File.separator + emergency;
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
		// 更改文件时间
		Timestamp now = new Timestamp(System.currentTimeMillis());
		guideDto = guideService.getDtoById(guideDto);
		if(StringUtils.isNotEmpty(flag)&&flag.equals("1")){
			//视频
			guideDto.setVideoTime(now);
		}else{
			//文件
			guideDto.setFileTime(now);
		}
		guideService.updateDtoById(guideDto);
		message(response,"upload_success", MESSAGE_INFO);
	}
	
	/**
	 * 提供文件下载服务
	 * @param request
	 * @param response
	 * @param guideId
	 * @param name
	 * @throws Exception
	 * @date 2016年6月29日 下午6:10:03
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/downloadFTPFile/{guideId}/{name}")
	public void downloadFTPFile(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String guideId,@PathVariable String name) throws Exception {
		//*//
		GuideDto guideDto = guideService.getDtoById(new GuideDto(guideId));
		String fileName = name.replace("@", ".");//获取要下载的文件名称
		String filePath = Constants.DIRECTORY_LOCAL_DOWNLOAD + fileName; // 路径+名称
		if (guideDto != null) {
			String ftpPath = guideDto.getFtpPath();
			//先下载ftp文件到web服务器
			boolean result = ftpService.downloadFile(ftpPath, fileName);
			if (result) {
				// 然后下载到客户端
				download(filePath, response);
			}
		}
	}
	
}
