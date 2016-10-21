package com.june.dao.back.bussiness.errorCode;

import java.util.List;

import com.june.common.BaseDao;
import com.june.dto.back.bussiness.errorCode.ErrorCode;
import com.june.dto.back.bussiness.errorCode.ErrorCodeDto;
import com.june.dto.back.common.TreeDto;

/**
 * @Description: 车型管理用Dao
 * @author 王俊伟
 * @date 2015年11月6日 下午2:30:30
 * @version V1.0
 */
public interface ErrorCodeDao extends BaseDao<ErrorCodeDto> {
	
	/**
	 * 批量添加故障代码数据
	 * @param list
	 * @date 2016年6月13日 下午2:32:56
	 * @writer wjw.happy.love@163.com
	 */
	void addlist(java.util.List<ErrorCodeDto> list);

	List<TreeDto> getDrops2(ErrorCodeDto t);
	List<TreeDto> getDropsByType(ErrorCodeDto t);
	List<TreeDto> getDropsByLevel(ErrorCodeDto t);
	List<TreeDto> getDropsBySystem(ErrorCodeDto t);
	List<TreeDto> getDropsByDesc(ErrorCodeDto t);
	
	List<TreeDto> errorCodeTree(ErrorCodeDto errorCodeDto);
	
	/**
	 * 为批量数量导入设计，只负责查询id，和code两个字段
	 * @param errorCodeDto
	 * @return
	 * @date 2016年7月11日 下午3:07:14
	 * @writer wjw.happy.love@163.com
	 */
	List<ErrorCode> getListByVehicle(ErrorCodeDto errorCodeDto);
	
	int exit(ErrorCodeDto errorCodeDto);
}
