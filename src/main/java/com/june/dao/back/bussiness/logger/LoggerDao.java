package com.june.dao.back.bussiness.logger;

import com.june.common.BaseDao;
import com.june.dto.back.bussiness.logger.LoggerDto;

/**
 * 
 * LoggerDao <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年6月17日 上午11:11:25
 */
public interface LoggerDao extends BaseDao<LoggerDto> {
	
	/**
	 * 批量添加MVB数据
	 * @param list
	 * @date 2016年6月17日 上午11:12:00
	 * @writer wjw.happy.love@163.com
	 */
	void addlist(java.util.List<LoggerDto> list);

	/**
	 * 清空所有日志信息
	 * 
	 * @date 2016年6月29日 下午2:58:16
	 * @writer wjw.happy.love@163.com
	 */
	void clear();

}
