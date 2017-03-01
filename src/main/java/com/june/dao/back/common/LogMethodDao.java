package com.june.dao.back.common;

import com.june.common.BaseDao;
import com.june.dto.back.common.LogMethodDto;

/**
 * 方法日志记录
 * LogMethodDao <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年2月23日 下午4:19:17
 * @version 1.0.0
 */
public interface LogMethodDao extends BaseDao<LogMethodDto> {
	
	/**
	 * 判断用户是否存在
	 * @param userId
	 * @return >1表示存在，否则不存在
	 * @date 2017年2月23日 下午4:26:00
	 * @writer junehappylove
	 */
	java.lang.Integer userExit(String userId);
}
