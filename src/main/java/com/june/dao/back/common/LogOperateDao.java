package com.june.dao.back.common;

import com.june.common.BaseDao;
import com.june.dto.back.common.LogOperateDto;

public interface LogOperateDao extends BaseDao<LogOperateDto> {
	
	/**
	 * @Description 添加日志记录
	 * @param logOperateDto
	 */
	public void addLogOperate(LogOperateDto logOperateDto);
}
