package com.june.service.back.bussiness.errorCode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.bussiness.errorCode.ErrorCodeDao;
import com.june.dto.back.bussiness.errorCode.ErrorCode;
import com.june.dto.back.bussiness.errorCode.ErrorCodeDto;
import com.june.dto.back.common.TreeDto;

/**
 * 
 * 故障代码信息Service <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月12日 下午5:42:59
 */
@Service
public class ErrorCodeService extends BaseService<ErrorCodeDao,ErrorCodeDto> {

	/**
	 * 故障代码信息dao
	 */
	@Autowired
	protected ErrorCodeDao errorCodeDao;

	/**
	 * 批量添加服务，供excel批量数据导入使用
	 * @param list
	 * @date 2016年6月13日 下午2:35:33
	 * @writer wjw.happy.love@163.com
	 */
	public void addlist(java.util.List<ErrorCodeDto> list){
		errorCodeDao.addlist(list);
	}
	public List<TreeDto> getDrops2(ErrorCodeDto t){
		return errorCodeDao.getDrops2(t);
	}
	public List<TreeDto> getDropsByType(ErrorCodeDto t){
		return errorCodeDao.getDropsByType(t);
	}
	public List<TreeDto> getDropsByLevel(ErrorCodeDto t){
		return errorCodeDao.getDropsByLevel(t);
	}
	public List<TreeDto> getDropsBySystem(ErrorCodeDto t){
		return errorCodeDao.getDropsBySystem(t);
	}
	public List<TreeDto> getDropsByDesc(ErrorCodeDto t){
		return errorCodeDao.getDropsByDesc(t);
	}

	public List<TreeDto> errorCodeTree(ErrorCodeDto errorCodeDto){
		return errorCodeDao.errorCodeTree(errorCodeDto);
	}
	
	public List<ErrorCode> getListByVehicle(ErrorCodeDto errorCodeDto){
		return errorCodeDao.getListByVehicle(errorCodeDto);
	}

	public boolean exit(ErrorCodeDto errorCodeDto) {
		int num = errorCodeDao.exit(errorCodeDto);
		return num > 0;
	}
}
