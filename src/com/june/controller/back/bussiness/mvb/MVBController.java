package com.june.controller.back.bussiness.mvb;

import java.io.FileInputStream;
import java.io.InputStream;
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
import com.june.dto.back.bussiness.mvb.MVBDto;
import com.june.dto.back.common.TreeDto;
import com.june.service.back.bussiness.mvb.MVBService;
import com.june.service.back.bussiness.vehicle.VehicleService;

import net.sf.json.JSONObject;

/**
 * 
 * MVB管理controller <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月10日 下午2:02:48
 */
@Controller
@RequestMapping("/mvb")
public class MVBController extends BaseController {

	@Autowired
	protected MVBService mvbService;
	@Autowired
	protected VehicleService vehicleService;

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
		MVBDto mvbDto = new MVBDto();
		fillRequestDto(request, mvbDto);
		return mvbDto;
	}

	/**
	 * MVB信息查询页面初始化
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/search/")
	public ModelAndView init(HttpServletRequest request) {
		String page = new String("business/mvb/search");
		ModelAndView result = initPage(request, page);
		List<TreeDto> list = commonService.getErrorLevel();
		result.addObject("errorLevelDrops", list);
		list = vehicleService.getDrops(null);
		result.addObject("impactVehicleDrops", list);
		return result;
	}
	
	@RequestMapping("/import/")
	public ModelAndView importData(HttpServletRequest request) {
		String page = new String("business/mvb/import");
		ModelAndView result = initPage(request, page);
		List<TreeDto> list = commonService.getErrorLevel();
		result.addObject("errorLevelDrops", list);
		list = vehicleService.getDrops(null);
		result.addObject("impactVehicleDrops", list);
		return result;
	}
	
	@RequestMapping("/excel")
	@MethodLog(module = "车型管理", remark = "MVB数据导入", operateType = Constants.OPERATE_TYPE_OTHER)
	public void excel(@RequestParam CommonsMultipartFile[] myfiles, HttpServletRequest request,
			HttpServletResponse response,MVBDto mvbDto) throws Exception {
		mvbDto.setUserId(super.loginUser().getUserId());
		// 循环进行文件上传处理
		for (int i = 0; i < myfiles.length; i++) {
			InputStream inputStream = null;
			String cache = myfiles[0].getStorageDescription();
			cache = cache.substring(4, cache.length()-1);
			inputStream = new FileInputStream(cache);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
			List<MVBDto> list = ExcelMVBData.getInstance().readXls(hssfWorkbook,0);
			list = tidyExcel(list,mvbDto);
			//对于广巨数据量的分批次处理方案
			int size = list.size();
			int fg = size / Constants.DATA_MAX;
			int ys = size % Constants.DATA_MAX;
			int count = ys == 0 ? fg : fg + 1;
			for(int j = 0; j < count; j++){
				if(j==count-1 && ys > 0){
					mvbService.addlist(list.subList(j*Constants.DATA_MAX, size));
				}else{
					mvbService.addlist(list.subList(j*Constants.DATA_MAX, (j+1)*Constants.DATA_MAX));
				}
			}
		}

		MessageDto messageDto = new MessageDto();
		JSONObject object = JSONObject.fromObject(messageDto);
		Converttojsonobjectajax(response, object);
	}
/*	public static void main(String[] args) {
		int num = 535;
		System.out.println(num/1000);
		System.out.println(num%1000);
	}*/
	private List<MVBDto> tidyExcel(List<MVBDto> list,MVBDto mvbDto) {
		String vehicle = mvbDto.getVehicle();
		for(MVBDto i:list){
			i.setVehicle(vehicle);
			i.setAddUserId(mvbDto.getUserId());
			i.setUpdateUserId(mvbDto.getUserId());
		}
		return list;
	}

	/**
	 * 获取MVB分页信息
	 * 
	 * @param request
	 * @param response
	 * @return void
	 */
	@RequestMapping("/pageList")
	@MethodLog(module = "车型管理", remark = "MVB数据查询", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void pageList(HttpServletRequest request, HttpServletResponse response) {
		MVBDto mvbDto = new MVBDto();
		fillRequestDto(request, mvbDto);
		mvbDto = mvbService.getPagedDtos(mvbDto);
		toJson(mvbDto, response);
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
		MVBDto mvbDto = new MVBDto();
		fillRequestDto(request, mvbDto);
		List<MVBDto> list = mvbService.getDtos(mvbDto);
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
		MVBDto mvbDto = new MVBDto();
		fillRequestDto(request, mvbDto);
		mvbDto = mvbService.getDtoById(mvbDto);
		// 判断MVB信息是否为空
		if (mvbDto == null) {
			throwMessage("error_not_exist", MESSAGE_ERRO, response);
		} else {
			toJson(mvbDto, response);
		}
	}

	/**
	 * 保存新增MVB
	 * 
	 * @param request
	 * @param response
	 * @param mvbDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:12:41
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/newSave")
	public void newSave(HttpServletRequest request, HttpServletResponse response,
			@Valid MVBDto mvbDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			// 该MVB不存在的情况
			if (StringUtils.isBlank(mvbDto.getMvbId())) {
				setCreater(mvbDto, request);
				mvbService.addDto(mvbDto);// 添加MVB
				throwMessage("save_success", MESSAGE_INFO, response);
			} else {
				// MVB存在的情况返回消息
				messageErrorExist(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 编辑保存MVB信息
	 * 
	 * @param request
	 * @param response
	 * @param mvbDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午1:14:36
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveEdit")
	public void saveEdit(HttpServletRequest request, HttpServletResponse response,
			@Valid MVBDto mvbDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			if (mvbService.getDtoById(mvbDto) == null) {
				// MVB不存在的情况返回消息
				messageErrorNotExist(response);
			} else {
				// MVB存在的情况进行更新
				mvbService.updateDtoById(mvbDto);// MVB信息更新
				messageUpdateSuccess(response);
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 删除MVB信息
	 * 
	 * @param request
	 * @param response
	 * @param mvbDto
	 * @throws Exception
	 * @date 2016年5月10日 下午1:28:22
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/deleteSelected")
	public void deleteSelected(HttpServletRequest request, HttpServletResponse response,
			MVBDto mvbDto) throws Exception {
		// 选中的MVBid
		String mvbIds = mvbDto.getMvbId();
		if (mvbIds != null) {
			String[] ids = mvbIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				mvbDto.setMvbId(ids[i]);
				mvbService.deleteDtoById(mvbDto);
			}
		}
		messageDeleteSuccess(response);
	}

	@RequestMapping("/drops")
	public void drops(HttpServletRequest request, HttpServletResponse response, MVBDto mvbDto){
		toJson(mvbService.getDrops(mvbDto), response); 
	}
	
}
