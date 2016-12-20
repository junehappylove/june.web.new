package com.june.dao.back.common;

import com.june.common.BaseDao;
import com.june.dto.back.common.LogOperateDto;

public interface LogOperateDao extends BaseDao<LogOperateDto> {
	
	/**
	 * 添加日志记录
	 * @param logOperateDto
	 * @date 2016年12月20日 下午9:25:24
	 * @writer junehappylove
	 */
	public void addLogOperate(LogOperateDto logOperateDto);
}
