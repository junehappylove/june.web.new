package com.june.controller.back.bussiness.guide;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

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
import com.june.common.Constants;
import com.june.common.MessageDto;
import com.june.dto.back.bussiness.ftp.FtpDto;
import com.june.dto.back.bussiness.guide.GuideApproveDto;
import com.june.dto.back.bussiness.guide.GuideDto;
import com.june.dto.back.bussiness.guide.ImageXML;
import com.june.dto.back.bussiness.guide.Step;
import com.june.service.back.bussiness.guide.GuideApproveService;
import com.june.service.back.bussiness.guide.GuideService;

/**
 * 
 * 操作指南审批controller <br>
 * @deprecated 参见
 * {@link com.june.controller.back.bussiness.guide.approve.ApproveController}
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月10日 下午2:02:48
 */
@Controller
@RequestMapping("/guide/approve_old")
public class GuideApproveController extends BaseController<GuideApproveDto> {

	@Autowired
	protected GuideApproveService guideApproveService;
	@Autowired
	protected GuideService guideService;

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
		GuideApproveDto guideApproveDto = new GuideApproveDto();
		fillRequestDto(request, guideApproveDto);
		return guideApproveDto;
	}

	/**
	 * 操作指南审批信息查询页面初始化
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/search/")
	public ModelAndView search(HttpServletRequest request) {
		String page = new String("business/guide/approve");
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
	@RequestMapping("/getGuideApproves")
	public void getPagedGuidApproves(HttpServletRequest request, HttpServletResponse response) {
		GuideApproveDto guideApproveDto = new GuideApproveDto();
		fillRequestDto(request, guideApproveDto);
		guideApproveDto = guideApproveService.getPagedDtos(guideApproveDto);
		toJson(guideApproveDto, response);
	}

	/**
	 * 获取所有的操作指南
	 * @param request
	 * @param response
	 * @date 2016年5月16日 下午2:43:19
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/loadAllGuideApprove")
	public void loadAllGuidApprove(HttpServletRequest request, HttpServletResponse response) {
		GuideApproveDto guideApproveDto = new GuideApproveDto();
		fillRequestDto(request, guideApproveDto);
		List<GuideApproveDto> list = guideApproveService.getDtos(guideApproveDto);
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
		GuideApproveDto guideApproveDto = new GuideApproveDto();
		fillRequestDto(request, guideApproveDto);
		guideApproveDto = guideApproveService.getDtoById(guideApproveDto);
		// 判断操作指南信息是否为空
		if (guideApproveDto == null) {
			messageErrorNotExist(response);
		} else {
			// 审批时候，设置审批人，和审批时间，用户无需在页面填写
			guideApproveDto.setApproveUserId(loginUser().getUserId());//取当前用户
			guideApproveDto.setApproveTime(new Timestamp(System.currentTimeMillis()));//取当前时间
			toJson(guideApproveDto, response);
		}
	}

	/**
	 * 审批并编辑保存操作指南信息，
	 * 审批通过后将目标目录复制到目的目录中
	 * 并向指南表中添加一条记录
	 * 
	 * @param request
	 * @param response
	 * @param approveDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:14:36
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveEdit")
	public void saveEdit(HttpServletRequest request, HttpServletResponse response,
			@Valid GuideApproveDto approveDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		// 获取修改时候的必要数据
		String guideId = approveDto.getGuideId();												//取到要修改的指南的ID
		GuideDto guideDto = guideService.getDtoById(new GuideDto(guideId));						//取这个指南记录
		String path = guideDto.getFtpPath() + Constants.DIRECTORY_ROOT + Constants.DIRECTORY_F;	//原始图文路径
		// 
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			if (guideApproveService.getDtoById(approveDto) == null) {
				// 操作指南不存在的情况返回消息
				messageErrorNotExist(response);
			} else {
				// 操作指南存在的情况进行更新
				guideApproveService.updateDtoById(approveDto);// 操作指南信息更新
				guideApproveService.updateApproveNum();//修改待审批数目
				String state = approveDto.getApproveState();//1 表示审批通过
				if(StringUtils.isNotEmpty(state) && "1".equals(state)){
					// 审批通过后，根据操作类型进行必要的操作
					String type = approveDto.getOperateType();
					if(StringUtils.isNotEmpty(type) && "0".equals(type)){
						/////////////////////////////////////////////////////////////////
						// 增加新的操作指南
						// 直接将目标目录复制到目的目录下去，
						// 同时，在指南表中，添加一条记录，需要指南名称，描述，是否应急等必要字段，
						// 还需要将fileTime和videoTime跟新上去
						/////////////////////////////////////////////////////////////////
						String sourcePath = $dir(approveDto.getFtpPath());	//源目录
						String toPath = $dir(approveDto.getToPath());		//待存目录
						ftpService.copyDirectory(sourcePath,toPath);
						// 审批通过后还需要添加一条记录到指南表中
						GuideDto guide = getGuide(approveDto);
						guideService.addDto(guide);
					}else if(StringUtils.isNotEmpty(type) && "1".equals(type)){
						/////////////////////////////////////////////////////////////////
						// 替换某一个图文操作步骤
						// 生成新的步骤xml文件并上传，将新的图片复制到目标目录同时删除要被替换的图片
						// 更新fileTime字段
						/////////////////////////////////////////////////////////////////
						// 首先取得原始图文步骤信息
						ImageXML imageXml = getImageXml(path);
						if (imageXml != null) {
							// 如果原始信息存在
							String imgPath = imageXml.getPath();
							TreeSet<Step> tree = imageXml.getStep();
							for(Step s:tree){
								s.setPath(imgPath);
							}
							// 从组新的展示信息
							Step newStep = getNewStep(approveDto);
							
							// [修改存在指南步骤1]，只修改存在的步骤,不存在抛错
							int size = imageXml.getStep().size();
							int bz = newStep.getId();
							if (size >= bz) {
								tree.remove(newStep);	// 移除这个步骤
								tree.add(newStep);		// 添加新的步骤
								imageXml.setStep(tree);	// 生成新的操作步骤
								// 记录要删除的图片文件名
								// TODO 不删除，根据step.xml解析不用的图片得不到
								// 复制审批图文到目标目录下
								String imageName = approveDto.getXmlImage();
								String sourcePath = approveDto.getFtpPath();
								String targetPath = approveDto.getToPath();
								sourcePath = $dir(sourcePath.replace(imageName, ""));
								targetPath = $dir(targetPath)+Constants.DIRECTORY_ROOT+Constants.DIRECTORY_F;
								ftpService.copyFile(imageName, sourcePath, targetPath);
								
								// 保存新的操作步骤xml文件到目标目录下
								InputStream is = bean2Stream(imageXml);
								FtpDto ftp = new FtpDto();
								ftp.setFtpPath(targetPath);		// 设置要上传的目录
								Map<String, InputStream> map = new HashMap<>();
								map.put(Constants.FILE_STEP, is);
								ftp.setFileMap(map);
								ftpService.uploadFile(ftp); 	// 上传操作
							} else {
								// 步骤不存在
								throwMessage(response,"step_not_exist", MESSAGE_ERRO);
							}
						}else{
							// 如果原始图文信息不存在，则不能修改存在步骤，只能添加新步骤
							throwMessage(response,"update_step_error",MESSAGE_ERRO);
						}
						// 更新 fileTime
						guideDto.setFileTime(new Timestamp(System.currentTimeMillis()));
						guideService.updateDtoById(guideDto);
					}else if(StringUtils.isNotEmpty(type) && "2".equals(type)){
						/////////////////////////////////////////////////////////////////
						// 增加图文操作步骤
						// 生成新的图文步骤xml文件并上传，同时将新的步骤图片上传
						// 修改fileTime字段
						/////////////////////////////////////////////////////////////////
						// 首先取得原始图文步骤信息
						ImageXML imageXml = getImageXml(path);
						// 从组新的展示信息
						Step newStep = getNewStep(approveDto);
						String sourceDir = approveDto.getFtpPath();
						String targetDir = approveDto.getToPath();
						String imageName = approveDto.getXmlImage();
						sourceDir = $dir(sourceDir.replace(imageName, ""));
						targetDir = $dir(targetDir) + Constants.DIRECTORY_ROOT + Constants.DIRECTORY_F;// 目标存放是文件目录
						if (imageXml != null) {
							// 如果原始信息存在
							String imgPath = imageXml.getPath();
							TreeSet<Step> tree = imageXml.getStep();
							for(Step s:tree){
								s.setPath(imgPath);
							}
							// [增加指南步骤2] ,比较麻烦情况比较多【向后追加步骤;修改现有步骤，其他步骤依次后移】
							int size = imageXml.getStep().size();// 原来的总得步骤数目
							int bz = newStep.getId();// 要修改的第几步步骤
							if (bz > size) {
								// 直接向后追加
								int num = size + 1;
								newStep.setId(num);
								for (Step s : tree) {
									s.setPath(imgPath);
								}
								tree.add(newStep);
								imageXml.setStep(tree);
							} else {
								// 向中间插入步骤，其他步骤依次后移
								// 如果bz<1,则向第一步插入即bz=1
								if (bz <= 1) {
									bz = 1;
								}
								TreeSet<Step> tree_new = new TreeSet<>();
								SortedSet<Step> set1 = tree.headSet(new Step(bz));
								SortedSet<Step> set2 = tree.tailSet(new Step(bz));
								for (Step s : set1) {
									tree_new.add(s);
								}
								newStep.setId(bz);// 设置这个id，第几步
								tree_new.add(newStep);// 插入
								for (Step s : set2) { // 其他步骤依次后移
									s.setId(s.getId() + 1);
									tree_new.add(s);
								}
								imageXml.setStep(tree_new);// 设置新的步骤
							}
							// 生成操作步骤完毕
							// 因为不是修改操作步骤，所以只需把目标图片复制到相应目录中即可
						}else{
							// 如果为空，就添加新的一步图文操作
							imageXml = new ImageXML();
							TreeSet<Step> set = new TreeSet<>();
							newStep.setId(1);// 不存在就是第一步
							set.add(newStep);
							imageXml.setStep(set);
							imageXml.setPath(targetDir);
						}
						// 复制审批图片到目标目录下
						ftpService.copyFile(imageName, sourceDir, targetDir);
						
						// 保存新的操作步骤xml文件到目标目录下
						InputStream is = bean2Stream(imageXml);
						FtpDto ftp = new FtpDto();
						ftp.setFtpPath(targetDir);		// 设置要上传的目录
						Map<String, InputStream> map = new HashMap<>();
						map.put(Constants.FILE_STEP, is);
						ftp.setFileMap(map);
						ftpService.uploadFile(ftp); 	// 上传操作
						// 更新 fileTime
						guideDto.setFileTime(new Timestamp(System.currentTimeMillis()));
						guideService.updateDtoById(guideDto);
					}else if(StringUtils.isNotEmpty(type) && "3".equals(type)){
						/////////////////////////////////////////////////////////////////
						// 修改或者增加视频
						// 将原有的视频删除，替换成新的视频文件
						// 修改videoTime字段
						/////////////////////////////////////////////////////////////////
						String sourcDir = approveDto.getFtpPath(); 			// 审批的路径
						String targeDir = approveDto.getToPath(); 			// 目标路径
						targeDir = $dir(targeDir) + Constants.DIRECTORY_ROOT + Constants.DIRECTORY_V;
						String videoName = approveDto.getVideoName();		// 视频的名称
						sourcDir = $dir(sourcDir.replace(videoName, ""));	// 整理成标准目录形式
						ftpService.copyFile(videoName, sourcDir, targeDir);	// 完成拷贝工作
						// 更新 videoTime
						guideDto.setVideoTime(new Timestamp(System.currentTimeMillis()));
						guideService.updateDtoById(guideDto);
					}else{
						// 传入操作类型错误无法处理
						throwMessage(response,"operate_type_error", MESSAGE_ERRO);
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
	 * 根据审批的指南，生成一条操作指南记录数据
	 * @param approveDto
	 * @return
	 * @date 2016年7月6日 下午9:27:24
	 * @writer wjw.happy.love@163.com
	 */
	private GuideDto getGuide(GuideApproveDto approveDto) {
		GuideDto guide = new GuideDto();
		super.initDto(guide);
		approveDto = guideApproveService.getDtoById(approveDto);
		guide.setGuideName(approveDto.getGuideName());
		guide.setFtpPath($dir(approveDto.getToPath()));
		guide.setVehicleId(approveDto.getVehicleId());
		guide.setEmergency(approveDto.getEmergency());
		Timestamp time = new Timestamp(System.currentTimeMillis());
		guide.setFileTime(time);
		guide.setVideoTime(time);
		return guide;
	}

	/**
	 * 删除操作指南，已及操作指南-用户关联信息
	 * 
	 * @param request
	 * @param response
	 * @param guideApproveDto
	 * @throws Exception
	 * @date 2016年5月10日 下午1:28:22
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/deleteSelected")
	public void deleteSelected(HttpServletRequest request, HttpServletResponse response,
			GuideApproveDto guideApproveDto) throws Exception {
		// 选中的操作指南id
		String guideApproveIds = guideApproveDto.getApproveId();
		if (guideApproveIds != null) {
			String[] ids = guideApproveIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				guideApproveDto.setApproveId(ids[i]);
				guideApproveService.deleteDtoById(guideApproveDto);
			}
			guideApproveService.updateApproveNum();//修改待审批数目
		}
		messageDeleteSuccess(response);
	}
	
	/**
	 * 取原始视频播放地址+名称
	 * @param request
	 * @param response
	 * @param guideApproveDto
	 * @throws Exception
	 * @date 2016年7月6日 下午2:35:10
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/showOriginVideo")
	public void showOriginVideo(HttpServletRequest request, HttpServletResponse response,
			GuideDto guideDto) throws Exception {
		guideDto = guideService.getDtoById(guideDto);//得到这条guide
		if (guideDto != null) {
			// 取到视频的目录
			String ftpPath = guideDto.getFtpPath() + Constants.DIRECTORY_ROOT + Constants.DIRECTORY_V;
			logger.debug("取到的FTP地址:"+ftpPath);
			// 根据目录查询下的视频记录 名称
			String fileName = ftpService.getFileNameByType(ftpPath,".mp4");
			logger.debug("取到的文件名称:"+fileName);
			guideDto.setFtpPath(ftpPath+Constants.DIRECTORY_ROOT+fileName);
			toJson(guideDto, response);
		}else{
			throwMessage(response,"data_not_exist", MESSAGE_ERRO);
		}
	}
	
	/**
	 * 取原始图文信息
	 * @param request
	 * @param response
	 * @param guideDto
	 * @throws Exception
	 * @date 2016年7月6日 下午10:07:09
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/showOriginImage")
	public void showOriginImage(HttpServletRequest request, HttpServletResponse response,
			GuideDto guideDto) throws Exception {
		guideDto = guideService.getDtoById(guideDto);// 得到这条guide
		String path = guideDto.getFtpPath() + Constants.DIRECTORY_ROOT + Constants.DIRECTORY_F;
		ImageXML imageXml = getImageXml(path);
		toJson(imageXml == null ? new ImageXML() : imageXml, response);
	}
	
	/**
	 * 修改步骤或者添加图文步骤，展示新的图片
	 * json调用方法
	 * @param request
	 * @param response
	 * @param guideDto
	 * @throws Exception
	 * @date 2016年7月6日 下午5:21:00
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/showNewImage")
	public void showNewImage(HttpServletRequest request, HttpServletResponse response,
			GuideDto guideDto,GuideApproveDto approveDto) throws Exception {
		guideDto = guideService.getDtoById(guideDto);// 得到这条操作指南信息
		approveDto = guideApproveService.getDtoById(approveDto);//取得这条审批信息
		String path = guideDto.getFtpPath() + Constants.DIRECTORY_ROOT + Constants.DIRECTORY_F;
		// 首先取得原始图文步骤信息
		ImageXML imageXml = getImageXml(path);
		if (imageXml != null) {
			// 如果原始信息存在
			String imgPath = imageXml.getPath();
			TreeSet<Step> tree = imageXml.getStep();
			for(Step s:tree){
				s.setPath(imgPath);
			}
			// 从组新的展示信息
			Step newStep = getNewStep(approveDto);
			//取操作类型【这里只能是：1或者2】修改存在指南步骤1 增加指南步骤2
			// 其他情况抛错
			String type = approveDto.getOperateType();
			/////////////////////////////////////////////////////////////////////////////
			if(StringUtils.isNotEmpty(type) && "1".equals(type)){
				// 修改存在指南步骤1，只修改存在的步骤,不存在抛错
				int size = imageXml.getStep().size();
				int bz = newStep.getId();
				if (size >= bz) {
					tree.remove(newStep);//移除这个步骤
					tree.add(newStep);//添加新的步骤
					imageXml.setStep(tree);
					toJson(imageXml, response);
				} else {
					// 步骤不存在
					throwMessage(response,"stpe_not_exist", MESSAGE_ERRO);
				}
			}else if(StringUtils.isNotEmpty(type) && "2".equals(type)){
				// 增加指南步骤2 ,比较麻烦情况比较多【向后追加步骤;修改现有步骤，其他步骤依次后移】
				int size = imageXml.getStep().size();// 原来的总得步骤数目
				int bz = newStep.getId();// 要修改的第几步步骤
				if (bz > size) {
					// 直接向后追加
					int num = size + 1;
					newStep.setId(num);
					for (Step s : tree) {
						s.setPath(imgPath);
					}
					tree.add(newStep);
					imageXml.setStep(tree);
				} else {
					// 向中间插入步骤，其他步骤依次后移
					// 如果bz<1,则向第一步插入即bz=1
					if (bz <= 1) {
						bz = 1;
					}
					TreeSet<Step> tree_new = new TreeSet<>();
					SortedSet<Step> set1 = tree.headSet(new Step(bz));
					SortedSet<Step> set2 = tree.tailSet(new Step(bz));
					for (Step s : set1) {
						tree_new.add(s);
					}
					newStep.setId(bz);// 设置这个id，第几步
					tree_new.add(newStep);// 插入
					for (Step s : set2) { // 其他步骤依次后移
						s.setId(s.getId() + 1);
						tree_new.add(s);
					}
					imageXml.setStep(tree_new);// 设置新的步骤
				}
				toJson(imageXml, response);
			}else{
				throwMessage(response,"data_error_from_client", MESSAGE_ERRO);
			}
		}else{
			throwMessage(response,"data_not_exist", MESSAGE_ERRO);
		}
	}
	
	/**
	 * 获取操作步骤
	 * @param guideDto
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 * @date 2016年7月6日 下午9:57:03
	 * @writer wjw.happy.love@163.com
	 */
	private ImageXML getImageXml(String path) throws SocketException, IOException {
		String xmlName = Constants.FILE_STEP;// xml路径以及名称
		FtpDto ftp = new FtpDto();
		ftp.setFtpPath(path);
		ftp.setFtpFileName(xmlName);//
		InputStream is = ftpService.fileStream(ftp);
		ImageXML imageXml = null;
		if (is != null) {
			// 如果原始信息存在
			imageXml = super.xml2Bean(is);
		}
		return imageXml;
	}

	/**
	 * 根据审批记录生成新的一步图文操作步骤
	 * @param approveDto
	 * @return
	 * @date 2016年7月6日 下午9:53:32
	 * @writer wjw.happy.love@163.com
	 */
	private Step getNewStep(GuideApproveDto approveDto) {
		Step newStep = null;//新的步骤
		int id = Integer.parseInt(approveDto.getXmlStep());	//新的步骤
		String image = approveDto.getXmlImage();			//新的图片
		String text = approveDto.getXmlText();				//新的描述
		String path_ = approveDto.getFtpPath();				//所在审批目录
		//客户端传上来的path包含了图片的文件名称，这个名称跟 image是一样的，需要整理
		path_ = $dir(path_.replace(image, ""));				//整理后的审批地址
		newStep = new Step(id, image, text);
		newStep.setPath(path_);
		return newStep;
	}
	
	/**
	 * 取新的视频播放地址+名称
	 * @param request
	 * @param response
	 * @param guideApproveDto
	 * @throws Exception
	 * @date 2016年7月6日 下午2:35:10
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/showNewVideo")
	public void showNewVideo(HttpServletRequest request, HttpServletResponse response,
			GuideApproveDto approveDto) throws Exception {
		approveDto = guideApproveService.getDtoById(approveDto);//得到这条guide
		if (approveDto != null) {
			// 新的视频名称
			// String videoName = approveDto.getVideoName();
			// 取到视频的目录
			String ftpPath = super.$dir(approveDto.getFtpPath());// 新视频的审批目录
			// ftpPath = ftpPath + Constants.DIRECTORY_ROOT + videoName;
			logger.debug("新的视频播放地址:" + ftpPath);
			approveDto.setFtpPath(ftpPath);
			toJson(approveDto, response);
		}else{
			throwMessage(response,"data_not_exist", MESSAGE_ERRO);
		}
	}
	
}
